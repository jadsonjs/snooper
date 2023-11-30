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

import br.com.jadson.snooper.sonarcloud.data.analyses.SonarAnalysesInfo;
import br.com.jadson.snooper.sonarcloud.data.analyses.SonarAnalysesRoot;
import br.com.jadson.snooper.sonarcloud.data.links.ProjectLinkRoot;
import br.com.jadson.snooper.sonarcloud.data.links.ProjectsLinks;
import br.com.jadson.snooper.sonarcloud.data.measures.ProjectMeasuresRoot;
import br.com.jadson.snooper.sonarcloud.data.project.SonarOrganizationProjectInfo;
import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectInfo;
import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectRoot;
import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectSearchRoot;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Sonar Cloud Project information queries
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class SonarProjectsQueryExecutor extends AbstractSonarCloudQueryExecutor {

    /**
     * it's not possible to browse more than 10.000 issues. We need to refine your search.
     * https://community.sonarsource.com/t/cannot-get-more-than-10000-results-through-web-api/3662/2
     */
    public final int LIMIT_SONAR_CLOUD_API = 10000;

    public SonarProjectsQueryExecutor(){}

    public SonarProjectsQueryExecutor(String sonarDomain, String sonarToken) {
        if (sonarToken == null || sonarToken.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab Token: " + sonarToken);
        }
        if (sonarDomain == null || sonarDomain.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab URL: " + sonarDomain);
        }

        this.sonarURL = sonarDomain;
        this.sonarToken = sonarToken;
    }

    public SonarProjectsQueryExecutor(int pageSize){
        this.setPageSize(pageSize);
    }

    /**
     * Return all project from sonar cloud
     *
     * Access the sonar explore:  https://sonarcloud.io/explore/projects?languages=java&sort=-size
     *
     * and see the api begin calling in network tab
     *
     * https://community.sonarsource.com/t/list-of-all-public-projects-on-sonarcloud-using-api/33551/2
     *
     * Example of query generate by sonar explore to API:
     *
     * https://sonarcloud.io/api/components/search_projects?boostNewProjects=false&facets=ncloc,languages&filter=languages=java&s=ncloc&asc=false&p=1&ps=100
     *
     * @param languages languages of project
     * @return
     */
    public List<SonarProjectInfo> getSonarProjects(String languages, String sortBy){

        if(languages == null && languages.length() == 0)
            throw new IllegalArgumentException("languages is mandatory");
        if(sortBy == null && sortBy.length() == 0)
            throw new IllegalArgumentException("sortBy is mandatory");

        int page = 1;

        String parameters = "";

        List<SonarProjectInfo> all = new ArrayList<>();

        ResponseEntity<SonarProjectSearchRoot> result;

        int total = 0;
        int current = 0;
        do {

            parameters = "?boostNewProjects=false&facets=ncloc,languages&filter=languages="+languages+"&s="+sortBy+"&asc=false"+"&p="+page+"&ps="+pageSize;


            String query = getSonarAPIURL()  + "/components/search_history" + parameters;

            System.out.println("Recovering: "+(page*pageSize)+" elements of: "+total);
            System.out.println("page: "+page);
            System.out.println("pageSize: "+pageSize);

            System.out.println(query);

            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();

            HttpEntity entity = new HttpEntity<>(this.getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, SonarProjectSearchRoot.class);

            SonarProjectSearchRoot root = result.getBody();

            current = root.paging.pageIndex * root.paging.pageSize;
            total = root.paging.total;

            all.addAll(  new ArrayList<>( root.components ) );

            page++;

            try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); } // 10s simulate a page navigation

        }while ( ( result != null && current <= total) && ! testEnvironment  && (page*pageSize) <= LIMIT_SONAR_CLOUD_API  );

        return all;

    }

    /**
     * Return project of one organization.
     * @param organization
     * @return
     */
    public List<SonarOrganizationProjectInfo> getProjectsOfOrganization(String organization){

        int page = 1;

        String parameters = "";

        List<SonarOrganizationProjectInfo> all = new ArrayList<>();

        ResponseEntity<SonarProjectRoot> result;

        int total = 0;
        int current = 0;
        do {
            parameters = "?organization="+organization+"&qualifiers=TRK"+"&p="+page+"&ps="+pageSize;


            String query = getSonarAPIURL()  + "/components/search" + parameters;

            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();

            HttpEntity entity = new HttpEntity<>(this.getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, SonarProjectRoot.class);

            SonarProjectRoot root = result.getBody();

            current = root.paging.pageIndex * root.paging.pageSize;
            total = root.paging.total;

            all.addAll(  new ArrayList<>( root.components ) );

            page++;

        }while ( ( result != null && current <= total) && ! testEnvironment  && (page*pageSize) <= LIMIT_SONAR_CLOUD_API );

        return all;

    }




    /**
     * Return a metric value form a project
     *
     * @param metrics
     * @return
     */
    public ProjectMeasuresRoot getProjectMeasure(String projectKey, String metrics){

        ResponseEntity<ProjectMeasuresRoot> result;

        String parameters = "?componentKey="+projectKey+"&metricKeys="+metrics;

        String query = getSonarAPIURL()  + "/measures/component" + parameters;

        RestTemplate restTemplate = new RestTemplate();

        checkDisableSslVerification();

        HttpEntity entity = new HttpEntity<>(this.getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, ProjectMeasuresRoot.class);

        return result.getBody();

    }


    /**
     * Return analysis of a specific project.
     *
     * @param projectKey
     * @return
     */
    public List<SonarAnalysesInfo> getProjectAnalyses(String projectKey, String category){

        int page = 1;

        String parameters = "";

        List<SonarAnalysesInfo> all = new ArrayList<>();

        ResponseEntity<SonarAnalysesRoot> result;

        int total = 0;
        int current = 0;
        do {
            parameters = "?project="+projectKey;
            if(category != null && ! category.trim().equals(""))
                parameters += "&category="+category;

            parameters +="&p="+page+"&ps="+pageSize;

            String query = getSonarAPIURL()  + "/project_analyses/search" + parameters;

            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();

            HttpEntity entity = new HttpEntity<>(this.getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, SonarAnalysesRoot.class);

            SonarAnalysesRoot root = result.getBody();

            current = root.paging.pageIndex * root.paging.pageSize;
            total = root.paging.total;

            all.addAll(  new ArrayList<>( root.analyses ) );

            page++;

        }while ( ( result != null && current <= total) && ! testEnvironment  && (page*pageSize) <= LIMIT_SONAR_CLOUD_API );

        return all;

    }


    /**
     * Return just the total of analyses of project
     *
     * @param category
     * @return
     */
    public int getTotalAnalysesOfProject(String projectKey, String category){

        ResponseEntity<SonarAnalysesRoot> result;

        String parameters =  parameters = "?project="+projectKey;
        if(category != null && ! category.trim().equals(""))
            parameters += "&category="+category;


        String query = getSonarAPIURL()  + "/project_analyses/search" + parameters;

        RestTemplate restTemplate = new RestTemplate();

        checkDisableSslVerification();

        HttpEntity entity = new HttpEntity<>(this.getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, SonarAnalysesRoot.class);

        return result.getBody().paging.total;

    }


    /**
     * Return links of the project. With the links we can link the project
     *
     * @param projectKey
     * @return
     */
    public List<ProjectsLinks> getProjectLinks(String projectKey){

        ResponseEntity<ProjectLinkRoot> result;

        String parameters = "?projectKey="+projectKey;

        String query = getSonarAPIURL()  + "/project_links/search" + parameters;

        RestTemplate restTemplate = new RestTemplate();

        checkDisableSslVerification();

        HttpEntity entity = new HttpEntity<>(this.getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, ProjectLinkRoot.class);

        return result.getBody().links;

    }

}
