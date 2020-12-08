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
 * br.com.jadson.snooper.github
 * PullRequestDiffQuery
 * 23/09/20
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.diff.GitHubPullRequestDiffInfo;
import br.com.jadson.snooper.github.data.pull.GitHubPullRequestInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Pull Request Diff Query
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class PullRequestDiffQuery extends AbstractGitHubQuery {

    public PullRequestDiffQuery(){ }

    public PullRequestDiffQuery(String githubToken){
        super(githubToken);
    }

    public GitHubPullRequestDiffInfo pullRequestsDiff(String repoOwner, String repoName, Long pullNumber) {
        return pullRequestsDiff(repoOwner+"/"+repoName, pullNumber);
    }

    public GitHubPullRequestDiffInfo pullRequestsDiff(String repoFullName, Long pullNumber) {

        validateRepoName(repoFullName);

        // IMPORTANTE ?state=all for bring all PR
        String parameters = "";

        List<GitHubPullRequestInfo> allPull = new ArrayList<>();

        ResponseEntity<GitHubPullRequestDiffInfo> result;

        String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/pulls/"+pullNumber+".diff";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        if(! githubToken.isEmpty())
            headers.set("Authorization", "token "+githubToken+"");
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity<>(headers);

        result = restTemplate.exchange( query, HttpMethod.GET, entity, GitHubPullRequestDiffInfo.class);

        return result.getBody();

    }

}
