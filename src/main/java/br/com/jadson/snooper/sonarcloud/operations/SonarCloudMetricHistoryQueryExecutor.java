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
 * SonarCloudMetricHistoryQueryExecutor
 * 06/01/21
 */
package br.com.jadson.snooper.sonarcloud.operations;

import br.com.jadson.snooper.sonarcloud.data.history.MeasureHistoryInfo;
import br.com.jadson.snooper.sonarcloud.data.history.SonarHistoryEntry;
import br.com.jadson.snooper.sonarcloud.data.history.SonarMetricHistoryRoot;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Query to recovery history of metrics from sonar cloud.
 * 
 * Jadson Santos - jadsonjs@gmail.com
 */
public class SonarCloudMetricHistoryQueryExecutor extends AbstractSonarCloudQueryExecutor{


    /**
     * Get the history of metric in a period of time.
     *
     * https://stackoverflow.com/questions/45157407/sonarqube-6-0-get-code-coverage-for-a-specific-version-of-a-project
     *
     * @param componentKey
     * @param metric
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<SonarHistoryEntry> getProjectMetricHistory(String componentKey, String metric, LocalDateTime fromDate, LocalDateTime toDate){

        int page = 1;

        String parameters = "";

        List<SonarHistoryEntry> all = new ArrayList<>();

        ResponseEntity<SonarMetricHistoryRoot> result;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String timeZone = "+0200"; // "%2B0200"; // +0200

        int total = 0;
        int current = 0;
        do {

            /*
            * Get:
            * https://sonarcloud.io/api/measures/search_history?component=simgrid_simgrid&metrics=coverage&"from"=2021-01-05T00:00:00%2B0200&to=2021-01-06T23:59:59%2B0200
            *
            * Return:
            *
            * {
            *    "paging": {
            *         "pageIndex": 1,
            *         "pageSize": 100,
            *        "total": 2
            *    },
            *   "measures": [
            *         {
            *             "metric": "coverage",
            *             "history": [
            *                 {
            *                     "date": "2021-01-05T06:33:37+0100",
            *                     "value": "81.8"
            *                 },
            *                 {
            *                     "date": "2021-01-06T06:12:24+0100",
            *                     "value": "81.7"
            *                 }
            *             ]
            *         }
            *     ]
            * }
            *
            */
            parameters = "?component="+componentKey+"&metrics="+metric+"&from="+formatter.format(fromDate)+timeZone+"&to="+formatter.format(toDate)+timeZone+"&p="+page+"&ps="+pageSize;


            String query = SONAR_CLOUD_API_URL +"/measures/search_history"+parameters;

            System.out.println(query);

            // allow '%' character in URL
            //UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(query);
            //URI uri = builder.build(false).toUri();

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            if(! sonarCloudAPIToken.isEmpty())
                headers.set("Authorization", "token "+sonarCloudAPIToken+"");

            HttpEntity entity = new HttpEntity<>(headers);

            result = restTemplate.exchange( query, HttpMethod.GET, entity, SonarMetricHistoryRoot.class);

            SonarMetricHistoryRoot root = result.getBody();

            current = root.paging.pageIndex * root.paging.pageSize;
            total = root.paging.total;

            for(MeasureHistoryInfo measure : root.measures){
                for(SonarHistoryEntry entry : measure.history){
                    all.add(  entry );
                }
            }

            page++;

        }while ( ( result != null && current <= total) && ! testEnvironment  && (page*pageSize) <= LIMIT_SONAR_CLOUD_API_RESULTS);

        return all;

    }

}
