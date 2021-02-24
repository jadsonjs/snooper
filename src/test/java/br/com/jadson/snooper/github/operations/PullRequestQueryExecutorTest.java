package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.pull.GitHubPullRequestInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PullRequestQueryExecutorTest {

    /**
     * Basic test
     */
    @Test
    void testPullRequest(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setTestEnvironment(true);
        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j/webauthn4j");

        Assertions.assertTrue(list.size() > 0);
    }

    /**
     * Basic test with token
     */
    @Test
    void testPullRequestWithToken(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setTestEnvironment(true);

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j/webauthn4j");

        Assertions.assertTrue(list.size() > 0);
    }

    /**
     * Basic test
     */
    @Test
    void testPullRequestOwnerAndName(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setTestEnvironment(true);

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j", "webauthn4j");

        Assertions.assertTrue(list.size() > 0);
    }


    /**
     * Test page size
     */
    @Test
    void testPullRequestPageSize(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
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

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setTestEnvironment(true);
        gitHubClient.setPageSize(10);
        gitHubClient.setQueryParameters(new String[]{"state=all"});

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j/webauthn4j");

        Assertions.assertTrue(list.size() > 0);

        Assertions.assertTrue(list.get(0).user.id > 0);
        Assertions.assertTrue(list.get(0).user.followers_url != null);

    }


    /**
     * Basic test
     */
    @Test
    void testMinimumPullRequest(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setQueryParameters(new String[]{"state=all"});
        gitHubClient.setPageSize(21);
        boolean has = gitHubClient.hasMinimumPullRequest("webauthn4j/webauthn4j", 20);

        Assertions.assertTrue(has);
    }


    @Test
    void getQtdPullRequests() {
        PullRequestQueryExecutor gitHubExecutor = new PullRequestQueryExecutor();
        int qtd = gitHubExecutor.getQtdPullRequests("atomix/atomix");
        System.out.println("QTD PR: "+qtd);
        Assertions.assertTrue(qtd > 0);
    }


    @Test
    void testQueryParameter(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setTestEnvironment(true);
        gitHubClient.setPageSize(10);
        gitHubClient.setQueryParameters(new String[]{"state=all"});


        Assertions.assertEquals(gitHubClient.getQueryParameters(), "state=all&");

    }

}