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
 * br.com.jadson.snooper.sonarcloud.operations
 * SonarCloudProjectsQuery
 * 20/10/20
 */
package br.com.jadson.snooper.sonarcloud.operations;

import br.com.jadson.snooper.sonarcloud.data.metric.SonarMetricInfo;
import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectInfo;
import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectRoot;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sonar Cloud Project informations
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class SonarCloudProjectsQuery extends AbstractSonarCloudQuery{


    public List<SonarProjectInfo> getProjectsOfOrganization(String organization){

        int page = 1;

        String parameters = "";

        List<SonarProjectInfo> all = new ArrayList<>();

        ResponseEntity<SonarProjectRoot> result;

        int total = 0;
        int current = 0;
        do {
            parameters = "?organization="+organization+"&qualifiers=TRK"+"&p="+page+"&ps="+pageSize;

            String query = SONAR_CLOUD_API_URL +"/components/search"+parameters;

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            if(! sonarCloudAPIToken.isEmpty())
                headers.set("Authorization", "token "+sonarCloudAPIToken+"");

            HttpEntity entity = new HttpEntity<>(headers);

            result = restTemplate.exchange( query, HttpMethod.GET, entity, SonarProjectRoot.class);

            SonarProjectRoot root = result.getBody();

            current = root.paging.pageIndex;
            total = root.paging.total;

            all.addAll(  new ArrayList<>( root.components ) );

            page++;

        }while (result != null && current <= total);

        return all;

    }


    /**
     * Return all project form specific metric
     *
     * @param metric
     * @return
     */
    public List<SonarMetricInfo> getProjectsByMetric(String metric){

        int page = 1;

        String parameters = "";

        List<SonarMetricInfo> all = new ArrayList<>();

        ResponseEntity<SonarMetricInfo[]> result;

        do {
            parameters = "?metricKeys="+metric+"&p="+page+"&ps="+pageSize;

            String query = SONAR_CLOUD_API_URL +"/measures/component"+parameters;

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            if(! sonarCloudAPIToken.isEmpty())
                headers.set("Authorization", "token "+sonarCloudAPIToken+"");

            HttpEntity entity = new HttpEntity<>(headers);

            result = restTemplate.exchange( query, HttpMethod.GET, entity, SonarMetricInfo[].class);

            all.addAll(  Arrays.asList(result.getBody()) );

            page++;

        }while (result != null && result.getBody().length > 0 );

        return all;

    }

}
