package br.com.jadson.snooper.github;

import br.com.jadson.snooper.github.operations.PullRequestQuery;
import br.com.jadson.snooper.github.data.pull.GitHubPullRequestInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PullRequestQueryTest {

    /**
     * Basic test
     */
    @Test
    void testPullRequest(){

        PullRequestQuery gitHubClient = new PullRequestQuery();
        gitHubClient.setTestEnvironment(true);
        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j/webauthn4j");

        Assertions.assertTrue(list.size() > 0);
    }

    /**
     * Basic test with token
     */
    @Test
    void testPullRequestWithToken(){

        PullRequestQuery gitHubClient = new PullRequestQuery();
        gitHubClient.setTestEnvironment(true);

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j/webauthn4j");

        Assertions.assertTrue(list.size() > 0);
    }

    /**
     * Basic test
     */
    @Test
    void testPullRequestOwnerAndName(){

        PullRequestQuery gitHubClient = new PullRequestQuery();
        gitHubClient.setTestEnvironment(true);

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j", "webauthn4j");

        Assertions.assertTrue(list.size() > 0);
    }


    /**
     * Test page size
     */
    @Test
    void testPullRequestPageSize(){

        PullRequestQuery gitHubClient = new PullRequestQuery();
        gitHubClient.setTestEnvironment(true);
        gitHubClient.setPageSize(10);

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j/webauthn4j");

        Assertions.assertTrue(list.size() > 0);
    }

    /**
     * Test page size and parameters
     */
    @Test
    void testPullRequestPageSizeAndParameters(){

        PullRequestQuery gitHubClient = new PullRequestQuery();
        gitHubClient.setTestEnvironment(true);
        gitHubClient.setPageSize(10);
        gitHubClient.setQueryParameters(new String[]{"state=all"});

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j/webauthn4j");

        Assertions.assertTrue(list.size() > 0);
    }

    
}