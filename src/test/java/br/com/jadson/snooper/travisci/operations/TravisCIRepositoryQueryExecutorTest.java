package br.com.jadson.snooper.travisci.operations;

import br.com.jadson.snooper.travisci.data.repo.TravisRepoInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class TravisCIRepositoryQueryExecutorTest {

    @Test
    void getRepoInfoTest() {

        TravisCIRepositoryQueryExecutor executor = new TravisCIRepositoryQueryExecutor();
        TravisRepoInfo info = executor.getRepoInfo("jadsonjs/continuous-delivery");

        Assertions.assertTrue(info != null && info.id > 0 );
    }
}
