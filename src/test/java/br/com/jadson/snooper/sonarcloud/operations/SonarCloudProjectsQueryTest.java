package br.com.jadson.snooper.sonarcloud.operations;

import br.com.jadson.snooper.sonarcloud.data.measures.ProjectMeasuresRoot;
import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SonarCloudProjectsQueryTest {

    @Test
    void getProjectsOfOrganization() {

        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
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
    void getProjectsByMetric() {
        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
        ProjectMeasuresRoot m = query.getProjectsMeasure("microsoft_terraform-provider-azuredevops", "coverage");

        System.out.println(m.component.name);
        System.out.println(m.component.measures);
        System.out.println(m.metrics);
        System.out.println(m.periods);


    }
}