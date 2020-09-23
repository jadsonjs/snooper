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
package br.com.jadson.snooper.github;

import br.com.jadson.snooper.model.pull.GitHubPullRequestInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Queries about Pull Request
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
class PullRequestQuery extends GigHubQuery {

    public PullRequestQuery(){ }

    public PullRequestQuery(String githubToken){
        super(githubToken);
    }

    public List<GitHubPullRequestInfo> pullRequests(String repoOwner, String repoName) {
        return pullRequests(repoOwner+"/"+repoName);
    }

    public List<GitHubPullRequestInfo> pullRequests(String repoFullName) {

        validateRepoName(repoFullName);

        int page = 1;

        // IMPORTANTE ?state=all for bring all PR
        String parameters = "";

        List<GitHubPullRequestInfo> allPull = new ArrayList<>();

        ResponseEntity<GitHubPullRequestInfo[]> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"&page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_URL+"/repos/"+repoFullName+"/pulls"+parameters;

            System.out.println("Getting Next Pull Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            if(! githubToken.isEmpty())
                headers.set("Authorization", "token "+githubToken+"");
            headers.set("Accept", "application/json");
            HttpEntity entity = new HttpEntity<>(headers);

            result = restTemplate.exchange( query, HttpMethod.GET, entity, GitHubPullRequestInfo[].class);

            allPull.addAll(  Arrays.asList(result.getBody()) );

            page++;


        }while (result != null && result.getBody().length > 0 );

        return allPull;
    }

}
