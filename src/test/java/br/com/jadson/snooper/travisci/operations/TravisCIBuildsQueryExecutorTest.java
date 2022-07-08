package br.com.jadson.snooper.travisci.operations;

import br.com.jadson.snooper.travisci.data.builds.TravisBuildsInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TravisCIBuildsQueryExecutorTest {

    @Test
    void getBuildsInfoTest() {

        TravisCIBuildsQueryExecutor executor = new TravisCIBuildsQueryExecutor();
        List<TravisBuildsInfo> builds = executor.getBuilds("JodaOrg/joda-time");

        Assertions.assertTrue(builds != null && builds.size() > 0 );
    }

    @Test
    void hasMinimumBuildsTest() {

        TravisCIBuildsQueryExecutor executor = new TravisCIBuildsQueryExecutor();
        Assertions.assertTrue(executor.hasMinimumBuilds("JodaOrg/joda-time", 20) );
    }


    @Test
    void getFisrtBuildsInfoTest() {

        TravisCIBuildsQueryExecutor executor = new TravisCIBuildsQueryExecutor();
        TravisBuildsInfo build = executor.getFirstBuild("JodaOrg/joda-time");

        System.out.println(" First build date: "+build.started_at);

        Assertions.assertTrue(build != null && build.duration > 0 && build.started_at != null );
    }

}