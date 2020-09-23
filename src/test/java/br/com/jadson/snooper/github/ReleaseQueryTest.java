package br.com.jadson.snooper.github;

import br.com.jadson.snooper.model.pull.GitHubPullRequestInfo;
import br.com.jadson.snooper.model.release.GitHubReleaseInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.management.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReleaseQueryTest {

    /**
     * Basic test
     */
    @Test
    void testPullRequest(){

        ReleaseQuery gitHub = new ReleaseQuery();

        List<GitHubReleaseInfo> list =  gitHub.releases("webauthn4j/webauthn4j");

        System.out.println(list.get(0).id);

        Assertions.assertTrue(list.size() > 0);
    }

}