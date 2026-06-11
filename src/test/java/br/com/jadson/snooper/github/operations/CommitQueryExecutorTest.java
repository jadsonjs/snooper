package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.association.AssociationCommitPullRequestInfo;
import br.com.jadson.snooper.github.data.commit.CommitInfo;
import br.com.jadson.snooper.github.data.stats.GitHubCommitStatsInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

class CommitQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");

    /**
     * Basic test to get commit form a date of a project
     */
    @Test
    void testCommitsOfProject(){


        CommitQueryExecutor commitExecutor = new CommitQueryExecutor();
        commitExecutor.setQueryParameters(new String[]{"since=2021-03-01T22:26:45Z", "until=2021-04-08T22:26:45Z"});
        commitExecutor.setPageSize(100);
        commitExecutor.setGithubToken(token);
        commitExecutor.setTestEnvironment(true);
        List<CommitInfo> commitsInfo = commitExecutor.getCommits("vuejs/vue-router");

        Assertions.assertTrue(commitsInfo.size() == 10);
        Assertions.assertEquals("677f3c1f714fb61cc495345e535409b1cbb90429", commitsInfo.get(0).sha);

    }


    @Test
    void testCommitsOfReferemce(){

        CommitQueryExecutor commitExecutor = new CommitQueryExecutor();
        commitExecutor.setPageSize(1);
        commitExecutor.setGithubToken(token);
        commitExecutor.setTestEnvironment(true);
        CommitInfo commitsInfo = commitExecutor.getCommitsOfReference("traPtitech/traQ/", "master");

        // Assertions.assertTrue(commitsInfo.size() == 1);
        Assertions.assertNotNull(commitsInfo.sha);

    }


    /**
     * Basic test
     */
    @Test
    void testCommitsOfProjectByPullRequest(){

        CommitQueryExecutor commitExecutor = new CommitQueryExecutor();
        commitExecutor.setPageSize(100);
        commitExecutor.setGithubToken(token);
        List<CommitInfo> commitsInfo = commitExecutor.commitsOfPullRequest("octocat/Hello-World",796);

        Assertions.assertTrue(commitsInfo.size() > 0);
        Assertions.assertEquals("5e100cfdca24f3fb781c72348c7c63b418e4246d", commitsInfo.get(0).sha);

    }


    /***
     * THis test can not run bacause need a github token to be execute
     */
    @Test
    void getHistoryOfCommitsWithPullRequestsQuery() {

        CommitQueryExecutor commitExecutor = new CommitQueryExecutor();
        commitExecutor.setGithubToken(token);
        List<AssociationCommitPullRequestInfo> commits =  commitExecutor.getHistoryOfCommitsWithPullRequestsQuery("octocat/Hello-World");

        Assertions.assertEquals(3, commits.size());

        Assertions.assertEquals(0, commits.get(0).pullRequestNodeInfos.size());
        Assertions.assertEquals(1, commits.get(1).pullRequestNodeInfos.size());
        Assertions.assertEquals(0, commits.get(2).pullRequestNodeInfos.size());

        Assertions.assertEquals(6, commits.get(1).pullRequestNodeInfos.get(0).number);
    }


    /*
    * Tests fetching commits with their stats for a repository within a given date range.
    * */
    @Test
    void testGetCommitsWithStats() {
        CommitQueryExecutor commitExecutor = new CommitQueryExecutor();
        commitExecutor.setGithubToken(token);

        LocalDateTime init = LocalDateTime.of(2011, Month.JANUARY, 26, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2012, Month.MARCH, 6, 0, 0, 0);

        List<GitHubCommitStatsInfo> commitStatsInfos = commitExecutor.getCommitsWithStats("octocat/Hello-World", init, end);

        Assertions.assertNotNull(commitStatsInfos);
        Assertions.assertEquals(3, commitStatsInfos.size());

        Assertions.assertEquals("7fd1a60b01f91b314f59955a4e4d4e80d8edf11d",  commitStatsInfos.get(0).sha);
        Assertions.assertEquals(1,  commitStatsInfos.get(0).commitStats.additions);
        Assertions.assertEquals(1, commitStatsInfos.get(1).commitStats.additions);
        Assertions.assertEquals(1, commitStatsInfos.get(1).commitStats.deletions);
    }
}