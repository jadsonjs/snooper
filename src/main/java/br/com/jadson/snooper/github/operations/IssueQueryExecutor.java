/*
 * Federal University of Rio Grande do Norte
 * Department of Informatics and Applied Mathematics
 * Collaborative & Automated Software Engineering (CASE) Research Group
 *
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
 * br.com.jadson.snooper.github.operations
 * IssueQueryExecutor
 * 01/03/21
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.issue.GitHubIssueInfo;
import br.com.jadson.snooper.github.data.pull.GitHubQTDPullRequestInfo;
import br.com.jadson.snooper.travisci.data.builds.TravisBuildsInfo;
import br.com.jadson.snooper.utils.DateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class IssueQueryExecutor extends AbstractGitHubQueryExecutor{


    public IssueQueryExecutor(){ }

    public IssueQueryExecutor(String githubToken){
        super(githubToken);
    }

    public List<GitHubIssueInfo> issues(String repoOwner, String repoName) {
        return issues(repoOwner+"/"+repoName);
    }

    /**
     * Return all Issues of a project
     *
     * @param repoFullName
     * @return
     */
    public List<GitHubIssueInfo> issues(String repoFullName) {

        validateRepoName(repoFullName);

        int page = 1;

        // IMPORTANTE state=all for bring all Issues
        String parameters = "";

        List<GitHubIssueInfo> all = new ArrayList<>();

        ResponseEntity<GitHubIssueInfo[]> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/issues"+parameters;

            System.out.println("Getting Next Issues Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, GitHubIssueInfo[].class);

            all.addAll(  Arrays.asList(result.getBody()) );

            page++;


        }while ( result != null && result.getBody().length > 0   && !testEnvironment);

        return all;
    }

    /**
     * Return a specific issue information. Here we can get more information about issue, as the use that close the issue
     *
     * @param repoFullName
     * @return
     */
    public GitHubIssueInfo issue(String repoFullName, int issueNumber) {

        validateRepoName(repoFullName);


        ResponseEntity<GitHubIssueInfo> result;

        String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/issues/"+issueNumber;

        System.out.println("Getting Issues Info: "+query);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, GitHubIssueInfo.class);

        return result.getBody();

    }

    /**
     * Return all issues between dates.
     *
     * @param repoFullName
     * @param start
     * @param end
     * @return
     */
    public List<GitHubIssueInfo> issues(String repoFullName, LocalDateTime start, LocalDateTime end) {

        List<GitHubIssueInfo> issues = new ArrayList();

        List<GitHubIssueInfo> allIssues = issues(repoFullName);

        DateUtils dateUtils = new DateUtils();

        for (GitHubIssueInfo issue : allIssues) {

            if (issue.created_at != null) {
                LocalDateTime startIssue = issue.created_at.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (dateUtils.isBetweenDates(startIssue, start, end)) {
                    issues.add(issue);
                }
            }
        }

        return issues;
    }


    /**
     * Return the qtd of ISSUES of a project
     *
     * https://stackoverflow.com/questions/13094712/github-api-get-number-of-pull-requests
     * https://docs.github.com/en/free-pro-team@latest/github/searching-for-information-on-github/searching-issues-and-pull-requests
     *
     * @param repoFullName
     * @return
     */
    public int getQtdIssues(String repoFullName) {

        validateRepoName(repoFullName);

        String query = GIT_HUB_API_URL +"/search/issues?q=type:issue+repo:"+repoFullName+"&sort=created&order=asc";

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
