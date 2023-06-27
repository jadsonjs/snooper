package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.pull.GitHubPullRequestInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

class PullRequestQueryExecutorTest {


    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");

    /**
     * Basic test
     */
    @Test
    void testPullRequest(){

        PullRequestQueryExecutor executor = new PullRequestQueryExecutor();
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);
        List<GitHubPullRequestInfo> list =  executor.pullRequests("octocat/hello-world");

        Assertions.assertTrue(list.size() > 0);
    }


    @Test
    void testListPullRequestsAssociatedwithCommit(){

        PullRequestQueryExecutor executor = new PullRequestQueryExecutor();
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);
        List<GitHubPullRequestInfo> list =  executor.listPullRequestsAssociatedwithCommit("traPtitech/traQ", "43b707020d19fe6bc8dce8b3fdb0e2e0f367b6c7");

        System.out.println(list.get(0));

        Assertions.assertTrue(list.size() > 0);
    }


    /**
     * Basic test with token
     */
    @Test
    void testPullRequestWithToken(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setTestEnvironment(true);
        gitHubClient.setGithubToken(token);

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("octocat/hello-world");

        Assertions.assertTrue(list.size() > 0);
    }
    
    /**
     * Test PR between dates whit token
     */
    @Test
    void testPullRequestBetweenDatesWithToken(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setQueryParameters(new String[]{"state=all"});
        gitHubClient.setTestEnvironment(true);
        gitHubClient.setGithubToken(token);

        LocalDateTime start = LocalDateTime.of(2020, 3, 1, 23, 59, 59);
        LocalDateTime end = LocalDateTime.of(2020, 5, 01, 23, 59, 59);

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequestsCreatedInPeriod("torvalds/linux", start, end);

        Assertions.assertTrue(list.size() > 0);

        LocalDateTime createPR = list.get(0).created_at.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(list.get(0).number +" "+ list.get(0).created_at);

        Assertions.assertTrue(createPR.isAfter(start) && createPR.isBefore(end));
    }

    /**
     * Basic test
     */
    @Test
    void testPullRequestOwnerAndName(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setTestEnvironment(true);
        gitHubClient.setGithubToken(token);

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("octocat", "hello-world");

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
        gitHubClient.setGithubToken(token);

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("octocat/hello-world");

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
        gitHubClient.setGithubToken(token);
        gitHubClient.setQueryParameters(new String[]{"state=all"});

        List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("webauthn4j/webauthn4j");

        Assertions.assertTrue(list.size() > 0);

        Assertions.assertTrue(list.get(0).user.id > 0);
        Assertions.assertNotNull(list.get(0).user.followers_url);

    }



    @Test
    void getQtdPullRequests() {
        PullRequestQueryExecutor gitHubExecutor = new PullRequestQueryExecutor();
        gitHubExecutor.setGithubToken(token);
        gitHubExecutor.setTestEnvironment(true);
        int qtd = gitHubExecutor.getQtdPullRequests("atomix/atomix");
        System.out.println("QTD PR: "+qtd);
        Assertions.assertTrue(qtd > 0);
    }


    @Test
    void testQueryParameter(){

        PullRequestQueryExecutor gitHubClient = new PullRequestQueryExecutor();
        gitHubClient.setTestEnvironment(true);
        gitHubClient.setGithubToken(token);
        gitHubClient.setPageSize(10);
        gitHubClient.setQueryParameters(new String[]{"state=all"});


        Assertions.assertEquals(gitHubClient.getQueryParameters(), "state=all&");

    }

    /**
     * Test the return o closed user data for a specific pull request
     */
    @Test
    void testPullRequestClosedUser(){

        PullRequestQueryExecutor executor = new PullRequestQueryExecutor();
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);
        GitHubPullRequestInfo info =  executor.pullRequest("juicedata/juicefs", 1);

        Assertions.assertTrue(info.merged_by != null);
        Assertions.assertEquals("davies", info.merged_by.login);
    }

}
