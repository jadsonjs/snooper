package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.issue.GitHubIssueInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
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


    @Test
    void qtdIssuesTest() {

        IssueQueryExecutor executor = new IssueQueryExecutor();
        executor.setTestEnvironment(true);
        int qtd =  executor.getQtdIssues("octocat/hello-world");
        System.out.println("QTD ISSUES: "+qtd);
        Assertions.assertTrue(qtd > 0);
    }


    /**
     * Teste selecting issues with labels
     */
    @Test
    void issuesWithLabel() {

        IssueQueryExecutor executor = new IssueQueryExecutor();
        executor.setTestEnvironment(true);
        executor.setQueryParameters(new String[]{"stage=all", "labels=bug"});


        List<GitHubIssueInfo> list =  executor.issues("vuejs/vue");

        Assertions.assertTrue(list.size() > 0);
    }

    /**
     * Teste selecting issues between dates
     */
    @Test
    void issuesWithBetweenDates() {

        IssueQueryExecutor executor = new IssueQueryExecutor();
        executor.setTestEnvironment(true);
        executor.setQueryParameters(new String[]{"stage=all", "labels=bug"});

        LocalDateTime start = LocalDateTime.of(2021, 3, 1, 23, 59, 59);
        LocalDateTime end = LocalDateTime.of(2021, 3, 30, 23, 59, 59);

        List<GitHubIssueInfo> list =  executor.issues("vuejs/vue", start, end);

        Assertions.assertTrue(list.size() > 0);

        LocalDateTime createIssue = list.get(0).created_at.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(list.get(0).number +" "+ list.get(0).created_at);

        Assertions.assertTrue(createIssue.isAfter(start) && createIssue.isBefore(end));

    }
}