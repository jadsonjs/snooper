package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.issue.GitLabIssueInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GitLabIssueQueryExecutorTest {

    String token = System.getenv("GITLAB_TOKEN");


    @Test
    void testIssuesProject(){

        GitLabIssueQueryExecutor commitExecutor = new GitLabIssueQueryExecutor();
        commitExecutor.setQueryParameters(new String[]{"scope=all"});
        commitExecutor.setPageSize(100);
        commitExecutor.setGitlabToken(token);
        commitExecutor.setTestEnvironment(true);
        List<GitLabIssueInfo> commitsInfo = commitExecutor.issues("jadsonjs/holter-ci");

        Assertions.assertTrue(commitsInfo.size() == 1);
        Assertions.assertEquals("gitlab integration", commitsInfo.get(0).title);

    }


}