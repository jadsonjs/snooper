package br.com.jadson.snooper.coverall.operations;

import br.com.jadson.snooper.coverall.data.CoveAllRepositoryInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

class CoveAllRepoQueryExecutorTest {

    @Value("${coveall.token}")
    public String token;

    @Test
    void getRepoInfo() {

        CoveAllRepoQueryExecutor executor = new CoveAllRepoQueryExecutor(token);

        CoveAllRepositoryInfo repo =  executor.getRepoInfo("microsoft/msphpsql", AbstractCoveAllQueryExecutor.CODE_ALL_SERVICE.GITHUB);

        Assertions.assertTrue(repo != null);
        Assertions.assertEquals("microsoft/msphpsql", repo.name);

    }
}