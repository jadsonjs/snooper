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
 * ReleaseQuery
 * 23/09/20
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.release.GitHubReleaseInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Queries about Releases
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class ReleaseQueryExecutor extends AbstractGitHubQueryExecutor {

    public ReleaseQueryExecutor(){ }

    public ReleaseQueryExecutor(String githubToken){
        super(githubToken);
    }

    public List<GitHubReleaseInfo> releases(String repoOwner, String repoName) {
        return releases(repoOwner+"/"+repoName);
    }


    public List<GitHubReleaseInfo> releases(String repoFullName) {

        validateRepoName(repoFullName);

        int page = 1;

        // IMPORTANTE ?state=all for bring all PR
        String parameters = "";

        List<GitHubReleaseInfo> all = new ArrayList<>();

        ResponseEntity<GitHubReleaseInfo[]> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"&page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/releases"+parameters;

            System.out.println("Getting Next Release: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, GitHubReleaseInfo[].class);

            all.addAll(  Arrays.asList(result.getBody()) );

            page++;


        }while ( (result != null && result.getBody().length > 0 ) || !testEnvironment);

        return all;
    }

}
