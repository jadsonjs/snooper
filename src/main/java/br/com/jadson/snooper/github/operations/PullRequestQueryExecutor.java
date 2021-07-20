/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 * snooper
 * br.com.jadson.snooper.github
 * GitHubPullRequest
 * 23/09/20
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.pull.GitHubPullRequestInfo;
import br.com.jadson.snooper.github.data.pull.GitHubQTDPullRequestInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Queries about Pull Request
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class PullRequestQueryExecutor extends AbstractGitHubQueryExecutor {

    public PullRequestQueryExecutor(){ }

    public PullRequestQueryExecutor(String githubToken){
        super(githubToken);
    }

    public List<GitHubPullRequestInfo> pullRequests(String repoOwner, String repoName) {
        return pullRequests(repoOwner+"/"+repoName);
    }

    /**
     * Return all PR of a project
     *
     * @param repoFullName
     * @return
     */
    public List<GitHubPullRequestInfo> pullRequests(String repoFullName) {

        validateRepoName(repoFullName);

        int page = 1;

        // IMPORTANTE state=all for bring all PR
        String parameters = "";

        List<GitHubPullRequestInfo> allPull = new ArrayList<>();

        ResponseEntity<GitHubPullRequestInfo[]> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/pulls"+parameters;

            System.out.println("Getting Next Pull Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, GitHubPullRequestInfo[].class);

            allPull.addAll(  Arrays.asList(result.getBody()) );

            page++;


        }while ( result != null && result.getBody().length > 0   && !testEnvironment);

        return allPull;
    }
    
    /**
     * Return all PR between dates of a project
     *
     * @param repoFullName
     * @param start
     * @param end
     * @return
     */
    public List<GitHubPullRequestInfo> pullRequests(String projectNameWithOwner, LocalDateTime start, LocalDateTime end) {
        
        List<GitHubPullRequestInfo> pullRequests = new ArrayList();
        
        List<GitHubPullRequestInfo> allPullRequests = this.pullRequests(projectNameWithOwner);
        
        Iterator var6 = allPullRequests.iterator();

        while(var6.hasNext()) {
            
            GitHubPullRequestInfo pr = (GitHubPullRequestInfo)var6.next();
            
            if (pr.created_at != null) {
                LocalDateTime createdPR = LocalDateTime.ofInstant(pr.created_at.toInstant(), ZoneId.systemDefault());
                if (createdPR.isAfter(start) && createdPR.isBefore(end)) {
                    pullRequests.add(pr);
                }
            }
        }

        return pullRequests;
    }

    
    /**
     * Verify the minimum number of PR for a projects.
     * 
     * @deprecated  @see {@link this#getQtdPullRequests(String)}
     *
     * @param repoFullName
     * @param limit
     * @return
     */
    @Deprecated
    public boolean hasMinimumPullRequest(String repoFullName, final int limit) {

        validateRepoName(repoFullName);

        System.out.println("Counting Pull Requests for : "+repoFullName);

        long totalPROfProject = 0l;

        int page = 1;
        int perPage = 50;

        // IMPORTANTE state=all for bring all PR
        // gitHubClient.setQueryParameters(new String[]{"state=all"});
        String parameters = "";

        ResponseEntity<GitHubPullRequestInfo[]> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/pulls"+parameters;

            System.out.println("Getting Next Pull Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, GitHubPullRequestInfo[].class);

            totalPROfProject += Arrays.asList(result.getBody()).size();

            page++;

        }while (result != null && result.getBody().length > 0 && totalPROfProject < limit );

        System.out.println("project: "+repoFullName+" total of PR: "+totalPROfProject);

        return totalPROfProject >= limit;
    }

    /**
     * Return the qtd of PULL Request of a project
     *
     * https://stackoverflow.com/questions/13094712/github-api-get-number-of-pull-requests
     * https://docs.github.com/en/free-pro-team@latest/github/searching-for-information-on-github/searching-issues-and-pull-requests
     *
     * @param repoFullName
     * @return
     */
    public int getQtdPullRequests(String repoFullName) {

        validateRepoName(repoFullName);


        String query = GIT_HUB_API_URL +"/search/issues?q=type:pr+repo:"+repoFullName+"&sort=created&order=asc";

        // allow '+' character in URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(query);
        URI uri = builder.build(false).toUri();


        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = getDefaultHeaders();
        headers.set("Accept", "application/vnd.github.v3+json");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<GitHubQTDPullRequestInfo> result = restTemplate.exchange( uri, HttpMethod.GET, entity, GitHubQTDPullRequestInfo.class);

        return result.getBody().total_count;
    }

}
