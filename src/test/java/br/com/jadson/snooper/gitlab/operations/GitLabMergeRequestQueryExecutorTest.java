package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.issue.GitLabIssueInfo;
import br.com.jadson.snooper.gitlab.data.merge.GitLabMergeRequestInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitLabMergeRequestQueryExecutorTest {

    String token = System.getenv("GITLAB_TOKEN");

    @Disabled
    @Test
    void mergeRequestsTest() {

        GitLabMergeRequestQueryExecutor commitExecutor = new GitLabMergeRequestQueryExecutor();
        commitExecutor.setQueryParameters(new String[]{"state=all"});
        commitExecutor.setPageSize(100);
        commitExecutor.setGitlabToken(token);
        commitExecutor.setTestEnvironment(true);
        List<GitLabMergeRequestInfo> mergeRequests = commitExecutor.mergeRequests("jadsonjs/holter-ci");

        Assertions.assertTrue(mergeRequests.size() > 0);
        Assertions.assertEquals("suport to gitlab api", mergeRequests.get(0).description);
    }


    @Disabled
    @Test
    void mergeRequestsInPeriodTest() {

        GitLabMergeRequestQueryExecutor commitExecutor = new GitLabMergeRequestQueryExecutor();
        commitExecutor.setQueryParameters(new String[]{"state=all"});
        commitExecutor.setPageSize(100);
        commitExecutor.setGitlabToken(token);
        commitExecutor.setTestEnvironment(true);
        List<GitLabMergeRequestInfo> mergeRequests = commitExecutor.mergeRequestsInPeriod("jadsonjs/holter-ci",
                LocalDateTime.of(2023, 6, 1, 0,0,0),
                LocalDateTime.of(2023, 6, 10, 0,0,0) );

        Assertions.assertTrue(mergeRequests.size() > 0);
        Assertions.assertEquals("suport to gitlab api", mergeRequests.get(0).description);
    }
}