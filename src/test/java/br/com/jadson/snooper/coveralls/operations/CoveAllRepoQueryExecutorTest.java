package br.com.jadson.snooper.coveralls.operations;

import br.com.jadson.snooper.coveralls.data.CoverallsRepositoryInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CoveAllRepoQueryExecutorTest {


    /**
     * Test recovery information of a repository on CoveAll service
     */
    @Test
    void getRepoInfo() {

        // On intellij: edit configuration -> environment variable -> COVEALL_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        String token = System.getenv("COVEALL_TOKEN");

        CoverallsRepoQueryExecutor executor = new CoverallsRepoQueryExecutor(token);
        executor.setTestEnvironment(true);

        CoverallsRepositoryInfo repo =  executor.getRepoInfo("microsoft/msphpsql", AbstractCoverallsQueryExecutor.CODE_ALL_SERVICE.GITHUB);

        Assertions.assertTrue(repo != null);
        Assertions.assertEquals("microsoft/msphpsql", repo.name);

    }
}