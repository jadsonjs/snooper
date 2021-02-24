package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.association.AssociationCommitPullRequestInfo;
import br.com.jadson.snooper.github.data.commit.GitHubCommitInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

class CommitQueryExecutorTest {

    /**
     * Basic test
     */
    @Test
    void testCommitsOfProject(){

        CommitQueryExecutor commitExecutor = new CommitQueryExecutor();
        commitExecutor.setPageSize(100);
        List<GitHubCommitInfo> commitsInfo = commitExecutor.commitsOfPullRequest("octocat/Hello-World",796);

        Assertions.assertTrue(commitsInfo.size() > 0);
        Assertions.assertEquals("5e100cfdca24f3fb781c72348c7c63b418e4246d", commitsInfo.get(0).sha);

    }


    /***
     * THis test can not run bacause need a github token to be execute
     */
    @Disabled
    @Test
    void getHistoryOfCommitsWithPullRequestsQuery() {

        CommitQueryExecutor commitExecutor = new CommitQueryExecutor();
        commitExecutor.setGithubToken("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        List<AssociationCommitPullRequestInfo> commits =  commitExecutor.getHistoryOfCommitsWithPullRequestsQuery("octocat/Hello-World");

        Assertions.assertEquals(3, commits.size());

        Assertions.assertEquals(0, commits.get(0).pullRequestNodeInfos.size());
        Assertions.assertEquals(1, commits.get(1).pullRequestNodeInfos.size());
        Assertions.assertEquals(0, commits.get(2).pullRequestNodeInfos.size());

        Assertions.assertEquals(6, commits.get(1).pullRequestNodeInfos.get(0).number);
    }
}