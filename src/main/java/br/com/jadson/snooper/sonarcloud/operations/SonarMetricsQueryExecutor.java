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
 * SonarCloudMetricQuery
 * 20/10/20
 */
package br.com.jadson.snooper.sonarcloud.operations;

import br.com.jadson.snooper.sonarcloud.data.metric.SonarMetricInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sonar metric search
 * Jadson Santos - jadsonjs@gmail.com
 */
public class SonarMetricsQueryExecutor extends AbstractSonarCloudQueryExecutor {


    public SonarMetricsQueryExecutor() {

    }

    public SonarMetricsQueryExecutor(String sonarDomain, String sonarToken) {
        if (sonarToken == null || sonarToken.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab Token: " + sonarToken);
        }
        if (sonarDomain == null || sonarDomain.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab URL: " + sonarDomain);
        }

        this.sonarDomain = sonarDomain;
        this.sonarToken = sonarToken;
    }

    /**
     * Return all metric supported by intance of sonar.
     *
     * You can access a local instance
     * --header 'Authorization: Bearer MY_TOKEN'
     * http://localhost:9000/api/metrics/search
     *
     * or the cloud instance
     *
     * https://sonarqube.com/api/metrics/search
     *
     * @return
     */

    public List<SonarMetricInfo> searchMetrics(){

        int page = 1;

        String parameters = "";

        List<SonarMetricInfo> all = new ArrayList<>();

        ResponseEntity<SonarMetricInfo[]> result;

        do {
            parameters = "?p="+page+"&ps="+pageSize;

            String query = getSonarAPIURL()  + "/metrics/search" + parameters;

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity<>(this.getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, SonarMetricInfo[].class);

            all.addAll(  Arrays.asList(result.getBody()) );

            page++;

        }while ( ( result != null && result.getBody().length > 0 ) || ! testEnvironment);

        return all;

    }

}
