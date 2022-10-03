package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.release.GitHubReleaseInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ReleaseQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");

    /**
     * Basic test
     */
    @Test
    void testGetReleasesInfo(){

        ReleaseQueryExecutor executor = new ReleaseQueryExecutor();
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);
        executor.setQueryParameters(new String[]{"state=all"});

        List<GitHubReleaseInfo> list =  executor.releases("unbounce/iidy");

        System.out.println(list.get(0).id);

        Assertions.assertTrue(list.size() > 0);
    }

}