package br.com.jadson.snooper.github;

import br.com.jadson.snooper.github.operations.ReleaseQuery;
import br.com.jadson.snooper.github.data.release.GitHubReleaseInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ReleaseQueryTest {

    /**
     * Basic test
     */
    @Test
    void testGetReleasesInfo(){

        ReleaseQuery gitHub = new ReleaseQuery();
        gitHub.setTestEnvironment(true);

        List<GitHubReleaseInfo> list =  gitHub.releases("webauthn4j/webauthn4j");

        System.out.println(list.get(0).id);

        Assertions.assertTrue(list.size() > 0);
    }

}