package br.com.jadson.snooper.coverall.operations;

import br.com.jadson.snooper.coverall.data.CoveAllBuildInfo;
import br.com.jadson.snooper.coverall.data.CoveAllRepositoryInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoveAllBuildsQueryExecutorTest {

    @Test
    void getBuildsInfo() {

        // On intellij: edit configuration -> environment variable -> COVEALL_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        String token = System.getenv("COVEALL_TOKEN");

        CoveAllBuildsQueryExecutor executor = new CoveAllBuildsQueryExecutor(token);
        executor.setTestEnvironment(true);

        List<CoveAllBuildInfo> builds =  executor.getBuildsInfo("microsoft/msphpsql", AbstractCoveAllQueryExecutor.CODE_ALL_SERVICE.GITHUB);

        Assertions.assertTrue(builds != null && builds.size() > 0);
        Assertions.assertEquals("microsoft/msphpsql", builds.get(0).repo_name);
    }

    @Test
    void getLastBuildsInfo() {

        // On intellij: edit configuration -> environment variable -> COVEALL_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        String token = System.getenv("COVEALL_TOKEN");

        CoveAllBuildsQueryExecutor executor = new CoveAllBuildsQueryExecutor(token);
        executor.setTestEnvironment(true);

        CoveAllBuildInfo build =  executor.getLastBuildsInfo("microsoft/msphpsql", AbstractCoveAllQueryExecutor.CODE_ALL_SERVICE.GITHUB);

        Assertions.assertTrue(build != null);
        Assertions.assertEquals("microsoft/msphpsql", build.repo_name);
    }
}