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
 * br.com.jadson.snooper.sonarcloud
 * SonarCloudQuery
 * 20/10/20
 */
package br.com.jadson.snooper.sonarcloud.operations;

/**
 * Information to connect to SonarCloud API.
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
abstract class AbstractSonarCloudQueryExecutor {

    public static final String SONAR_CLOUD_API_URL = "https://sonarcloud.io/api";

    protected String sonarCloudAPIToken = "";

    /** Default page size of pagination*/
    protected int pageSize = 50;

    /** if is executing a test, or ir real query. */
    protected boolean testEnvironment = false;

    /**
     * it's not possible to browse more than 10.000 issues. We need to refine your search.
     * https://community.sonarsource.com/t/cannot-get-more-than-10000-results-through-web-api/3662/2
     */
    protected final int LIMIT_SONAR_CLOUD_API_RESULTS = 10000;


    public void setTestEnvironment(boolean testEnvironment) {
        this.testEnvironment = testEnvironment;
    }


    public void setSonarCloudAPIToken(String sonarCloudAPIToken) {
        if(sonarCloudAPIToken == null || sonarCloudAPIToken.trim().isEmpty())
            throw new IllegalArgumentException("invalid Sonar Cloud API Token: "+sonarCloudAPIToken);
        this.sonarCloudAPIToken = sonarCloudAPIToken;
    }

    public void setPageSize(int pageSize) {
        if(pageSize <= 0 || pageSize > 10000)
            throw new IllegalArgumentException("page size should be between 1 and 10.000");
        this.pageSize = pageSize;
    }
}
