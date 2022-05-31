package br.com.jadson.snooper.travisci.operations;

import br.com.jadson.snooper.travisci.data.builds.TravisBuildsInfo;
import br.com.jadson.snooper.travisci.data.repo.TravisRepoInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        List<TravisBuildsInfo> builds = executor.getBuilds("JodaOrg/joda-time");

        Assertions.assertTrue(builds != null && builds.size() > 0 );
    }

    @Test
    void hasMinimumBuildsTest() {

        TravisCIQueryExecutor executor = new TravisCIQueryExecutor();
        Assertions.assertTrue(executor.hasMinimumBuilds("JodaOrg/joda-time", 20) );
    }

}