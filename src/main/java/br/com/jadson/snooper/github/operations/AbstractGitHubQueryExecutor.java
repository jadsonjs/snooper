/*
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
 * GitHubClient
 * 22/09/20
 */
package br.com.jadson.snooper.github.operations;

import org.springframework.http.HttpHeaders;

/**
 * A client for github
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
abstract class AbstractGitHubQueryExecutor {

    public static final String GIT_HUB_API_URL = "https://api.github.com";

    protected String githubToken = "";

    /**
     * Default page size of pagination
     * maximum 100
     * https://docs.github.com/en/free-pro-team@latest/rest/guides/traversing-with-pagination
     */
    protected int pageSize = 50;

    protected String queryParameters;

    /** if is executing a test, or ir real query. */
    protected boolean testEnvironment = false;

    public AbstractGitHubQueryExecutor(){ }

    public AbstractGitHubQueryExecutor(String githubToken){
        if(githubToken == null || githubToken.trim().equals(""))
            throw new RuntimeException("Invalid GitHub Token: "+githubToken);

        this.githubToken = githubToken;
    }

    public void setPageSize(int pageSize) {
        if(pageSize < 0 || pageSize > 100)
            throw new RuntimeException("Invalid Page Size: "+pageSize+" see: https://docs.github.com/en/free-pro-team@latest/rest/guides/traversing-with-pagination");
        this.pageSize = pageSize;
    }

    public void setQueryParameters(String[] queryParameters) {
        if(queryParameters == null || queryParameters.length == 0)
            throw new RuntimeException("Invalid Query Parameters: "+queryParameters);

        for (String p :queryParameters){
            this.queryParameters += p+"&";
        }
    }

    protected final void validateRepoName(String repoFullName){
        if(repoFullName == null || repoFullName.trim().equals(""))
            throw new RuntimeException("Invalid GitHub repo full name: "+repoFullName);

        if(! repoFullName.contains("/"))
            throw new RuntimeException("Invalid GitHub repo full name: "+repoFullName+". The name should be owner/repo");
    }

    protected HttpHeaders getDefaultHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        if(! githubToken.isEmpty())
            headers.set("Authorization", "token "+githubToken+"");
        return headers;
    }

    public void setGithubToken(String githubToken) {
        this.githubToken = githubToken;
    }

    public void setTestEnvironment(boolean testEnvironment) {
        this.testEnvironment = testEnvironment;
    }


}
