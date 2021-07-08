package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.issue.GitHubIssueInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class IssueQueryExecutorTest {

    @Test
    void issuesTest() {

        IssueQueryExecutor executor = new IssueQueryExecutor();
        executor.setTestEnvironment(true);
        executor.setQueryParameters(new String[]{"stage=all"});
        List<GitHubIssueInfo> list =  executor.issues("octocat/hello-world");

        Assertions.assertTrue(list.size() > 0);
    }
}