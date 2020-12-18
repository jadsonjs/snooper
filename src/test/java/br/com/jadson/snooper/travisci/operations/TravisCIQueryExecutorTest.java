package br.com.jadson.snooper.travisci.operations;

import br.com.jadson.snooper.sonarcloud.data.project.SonarProjectComponent;
import br.com.jadson.snooper.sonarcloud.operations.SonarCloudProjectsQueryExecutor;
import br.com.jadson.snooper.travisci.data.builds.TravisBuildsInfo;
import br.com.jadson.snooper.travisci.data.repo.TravisRepoInfo;
import br.com.jadson.snooper.travisci.data.repo.TravisRepoRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TravisCIQueryExecutorTest {

    @Test
    void getRepoInfoTest() {

        TravisCIQueryExecutor executor = new TravisCIQueryExecutor();
        TravisRepoInfo info = executor.getRepoInfo("jadsonjs/continuous-delivery");

        Assertions.assertTrue(info != null && info.id > 0 );
    }

    @Test
    void getBuildsInfoTest() {

        TravisCIQueryExecutor executor = new TravisCIQueryExecutor();
        List<TravisBuildsInfo> builds = executor.getBuildsInfo("JodaOrg/joda-time");

        Assertions.assertTrue(builds != null && builds.size() > 0 );
    }
}