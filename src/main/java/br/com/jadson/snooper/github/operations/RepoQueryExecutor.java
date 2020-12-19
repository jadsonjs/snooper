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
 * RepoQueryExecutor
 * 18/12/20
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.diff.GitHubPullRequestDiffInfo;
import br.com.jadson.snooper.github.data.pull.GitHubPullRequestInfo;
import br.com.jadson.snooper.github.data.release.GitHubReleaseInfo;
import br.com.jadson.snooper.github.data.repo.GitHubRepoInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Return information about repository of github
 *
 * https://api.github.com/repos/webauthn4j/webauthn4j
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class RepoQueryExecutor extends AbstractGitHubQueryExecutor{

    public RepoQueryExecutor(){ }

    public RepoQueryExecutor(String githubToken){
        super(githubToken);
    }

    /**
     * Return all information about Github repository
     *
     * @param repoOwner
     * @param repoName
     * @return
     */
    public GitHubRepoInfo getRepoInfo(String repoOwner, String repoName) {
        return getRepoInfo(repoOwner+"/"+repoName);
    }

    /**
     * Return all information about Github repository
     *
     * @param repoFullName
     * @return
     */
    public GitHubRepoInfo getRepoInfo(String repoFullName) {

        ResponseEntity<GitHubRepoInfo> result;

        String query = GIT_HUB_API_URL +"/repos/"+repoFullName;

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, GitHubRepoInfo.class);

        return result.getBody();
    }

}
