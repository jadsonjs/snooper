package br.com.jadson.snooper.sonarcloud.operations;

import br.com.jadson.snooper.sonarcloud.data.analyses.SonarAnalysesInfo;
import br.com.jadson.snooper.sonarcloud.data.analyses.SonarEventInfo;
import br.com.jadson.snooper.sonarcloud.data.links.ProjectLinkRoot;
import br.com.jadson.snooper.sonarcloud.data.links.ProjectsLinks;
import br.com.jadson.snooper.sonarcloud.data.measures.ProjectMeasuresRoot;
import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectComponent;
import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SonarCloudProjectsQueryTest {

    @Test
    void getSonarProjectsTest() {

        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
        query.setTestEnvironment(true);
        query.pageSize = 1;

        List<SonarProjectComponent> projects = query.getSonarProjects("java", "ncloc");

        Assertions.assertTrue(projects.size() > 0 );
    }

    @Test
    void getProjectsOfOrganization() {

        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
        query.setTestEnvironment(true);

        List<SonarProjectInfo> projects = query.getProjectsOfOrganization("microsoft");

        for (SonarProjectInfo pi : projects){
            System.out.println("key: "+pi.key);
            System.out.println("name: "+pi.name);
            System.out.println("project: "+pi.project);
            System.out.println("organization: "+pi.organization);
        }
        Assertions.assertTrue(projects.size() > 0 );
    }

    @Test
    void testGetProjectsOfOrganization() {

        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
        query.setTestEnvironment(true);

        List<SonarProjectInfo> list = query.getProjectsOfOrganization("jadsonjs");

        for (SonarProjectInfo i : list){
            System.out.println(i.key);
            System.out.println(i.name);
        }

        Assertions.assertEquals("pipeline-demo", list.get(0).name);
        Assertions.assertEquals("pipe-line-demo", list.get(0).key);
    }

    @Test
    void getProjectMeasure() {

        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
        query.setTestEnvironment(true);

        ProjectMeasuresRoot data = query.getProjectMeasure("pipe-line-demo", "coverage");

        Assertions.assertEquals("coverage", data.component.measures.get(0).metric);
        Assertions.assertEquals("0.0", data.component.measures.get(0).value);

    }

    @Test
    void getProjectAnalysesTest() {

        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
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

        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
        query.setTestEnvironment(true);

        int analyses = query.getTotalAnalysesOfProject("pipe-line-demo", null);
        Assertions.assertTrue(analyses > 0 );
    }


    @Test
    void getProjectLinkTeste() {

        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
        query.setTestEnvironment(true);

        List<ProjectsLinks> links = query.getProjectLinks("jbosstools_jbosstools-integration-tests");
        Assertions.assertTrue(links.size() > 0 );
    }
}