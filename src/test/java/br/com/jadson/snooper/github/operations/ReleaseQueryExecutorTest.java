package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.operations.ReleaseQueryExecutor;
import br.com.jadson.snooper.github.data.release.GitHubReleaseInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ReleaseQueryExecutorTest {

    /**
     * Basic test
     */
    @Test
    void testGetReleasesInfo(){

        ReleaseQueryExecutor gitHub = new ReleaseQueryExecutor();
        gitHub.setTestEnvironment(true);

        List<GitHubReleaseInfo> list =  gitHub.releases("unbounce/iidy");

        System.out.println(list.get(0).id);

        Assertions.assertTrue(list.size() > 0);
    }

}