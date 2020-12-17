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
 * br.com.jadson.snooper.travisci.operations
 * TravisCIQuery
 * 29/09/20
 */
package br.com.jadson.snooper.travisci.operations;

import org.springframework.http.HttpHeaders;

/**
 * Travis CI client
 *
 * https://docs.travis-ci.com/user/developer/
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
abstract class AbstractTravisCIQueryExecutor {

    public static final String TRAVIS_CI_API_URL = "https://api.travis-ci.org";

    protected String travisAPIToken = "";

    /** Default page size of pagination*/
    protected int pageSize = 50;

    protected HttpHeaders getDefaultHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Snooper");
        headers.set("Accept", "application/vnd.travis-ci.2.1+json");
        headers.set("Host", "api.travis-ci.org");

        return headers;
    }

    protected void setTravisAPIToken(HttpHeaders headers) {
        headers.set("Authorization", "token "+travisAPIToken);
    }

}
