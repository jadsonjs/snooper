package br.com.jadson.snooper.coveralls.operations;

import br.com.jadson.snooper.coveralls.data.CoverallsBuildInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class CoveAllBuildsQueryExecutorTest {

    @Test
    void getBuildsInfo() {

        // On intellij: edit configuration -> environment variable -> COVEALL_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        String token = System.getenv("COVEALL_TOKEN");

        CoverallsBuildsQueryExecutor executor = new CoverallsBuildsQueryExecutor(token);
        executor.setTestEnvironment(true);

        List<CoverallsBuildInfo> builds =  executor.getBuildsInfo("microsoft/msphpsql", AbstractCoverallsQueryExecutor.CODE_ALL_SERVICE.GITHUB);

        Assertions.assertTrue(builds != null && builds.size() > 0);
        Assertions.assertEquals("microsoft/msphpsql", builds.get(0).repo_name);
    }

    @Test
    void getLastBuildsInfo() {

        // On intellij: edit configuration -> environment variable -> COVEALL_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        String token = System.getenv("COVEALL_TOKEN");

        CoverallsBuildsQueryExecutor executor = new CoverallsBuildsQueryExecutor(token);
        executor.setTestEnvironment(true);

        CoverallsBuildInfo build =  executor.getLastBuildsInfo("microsoft/msphpsql", AbstractCoverallsQueryExecutor.CODE_ALL_SERVICE.GITHUB);

        Assertions.assertTrue(build != null);
        Assertions.assertEquals("microsoft/msphpsql", build.repo_name);
    }
}