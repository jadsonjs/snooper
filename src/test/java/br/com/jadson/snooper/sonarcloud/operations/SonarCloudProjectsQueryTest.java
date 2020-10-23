package br.com.jadson.snooper.sonarcloud.operations;

import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SonarCloudProjectsQueryTest {

    @Test
    void getProjectsOfOrganization() {
        SonarCloudProjectsQuery query = new SonarCloudProjectsQuery();
        List<SonarProjectInfo> projects = query.getProjectsOfOrganization("microsoft");

        for (SonarProjectInfo pi : projects){
            System.out.println(pi.name);
        }
        Assertions.assertTrue(projects.size() > 0 );
    }
}