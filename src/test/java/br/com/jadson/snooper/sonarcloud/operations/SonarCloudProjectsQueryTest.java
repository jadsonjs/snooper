package br.com.jadson.snooper.sonarcloud.operations;

import br.com.jadson.snooper.sonarcloud.data.analyses.SonarAnalysesInfo;
import br.com.jadson.snooper.sonarcloud.data.analyses.SonarEventInfo;
import br.com.jadson.snooper.sonarcloud.data.links.ProjectsLinks;
import br.com.jadson.snooper.sonarcloud.data.measures.ProjectMeasuresRoot;
import br.com.jadson.snooper.sonarcloud.data.project.SonarOrganizationProjectInfo;
import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SonarCloudProjectsQueryTest {

    @Test
    void getSonarProjectsTest() {

        SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();
        query.setTestEnvironment(true);
        query.pageSize = 1;

        List<SonarProjectInfo> projects = query.getSonarProjects("java", "ncloc");

        Assertions.assertTrue(projects.size() > 0 );
    }

    @Test
    void getProjectsOfOrganization() {

        SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();
        query.setTestEnvironment(true);

        List<SonarOrganizationProjectInfo> projects = query.getProjectsOfOrganization("microsoft");

        for (SonarOrganizationProjectInfo pi : projects){
            System.out.println("key: "+pi.key);
            System.out.println("name: "+pi.name);
            System.out.println("project: "+pi.project);
            System.out.println("organization: "+pi.organization);
            System.out.println("qualifier: "+pi.qualifier);
            System.out.println("language: "+pi.language);
        }
        Assertions.assertTrue(projects.size() > 0 );
    }

    @Test
    void testGetProjectsOfOrganization() {

        SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();
        query.setTestEnvironment(true);

        List<SonarOrganizationProjectInfo> list = query.getProjectsOfOrganization("jadsonjs");

        for (SonarOrganizationProjectInfo i : list){
            System.out.println(i.key);
            System.out.println(i.name);
        }

        Assertions.assertEquals("pipeline-demo", list.get(0).name);
        Assertions.assertEquals("pipe-line-demo", list.get(0).key);
    }

    @Test
    void getProjectMeasure() {

        SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();
        query.setTestEnvironment(true);

        ProjectMeasuresRoot data = query.getProjectMeasure("pipe-line-demo", "coverage");

        Assertions.assertEquals("coverage", data.component.measures.get(0).metric);
        Assertions.assertEquals("0.0", data.component.measures.get(0).value);

    }

    @Test
    void getProjectAnalysesTest() {

        SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();
        query.setTestEnvironment(true);

        List<SonarAnalysesInfo> list = query.getProjectAnalyses("pipe-line-demo", null);

        for (SonarAnalysesInfo i : list){
            System.out.println(i.key);
            for (SonarEventInfo eventInfo : i.events) {
                System.out.println(eventInfo.name+" "+eventInfo.category);
            }
        }

        Assertions.assertTrue(list.size() > 0 );
    }

    @Test
    void getTotalAnalysesOfProject() {

        SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();
        query.setTestEnvironment(true);

        int analyses = query.getTotalAnalysesOfProject("pipe-line-demo", null);
        Assertions.assertTrue(analyses > 0 );
    }


    @Test
    void getProjectLinkTeste() {

        SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();
        query.setTestEnvironment(true);

        List<ProjectsLinks> links = query.getProjectLinks("jbosstools_jbosstools-integration-tests");
        Assertions.assertTrue(links.size() > 0 );
    }
}