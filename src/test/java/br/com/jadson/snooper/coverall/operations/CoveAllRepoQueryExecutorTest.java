package br.com.jadson.snooper.coverall.operations;

import br.com.jadson.snooper.coverall.data.CoveAllRepositoryInfo;
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

        CoveAllRepoQueryExecutor executor = new CoveAllRepoQueryExecutor(token);
        executor.setTestEnvironment(true);

        CoveAllRepositoryInfo repo =  executor.getRepoInfo("microsoft/msphpsql", AbstractCoveAllQueryExecutor.CODE_ALL_SERVICE.GITHUB);

        Assertions.assertTrue(repo != null);
        Assertions.assertEquals("microsoft/msphpsql", repo.name);

    }
}