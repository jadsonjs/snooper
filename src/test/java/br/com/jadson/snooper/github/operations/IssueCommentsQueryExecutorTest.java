package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.comments.GithubIssueCommentsInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class IssueCommentsQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");

    @Test
    void getIssuesCommentsInfo() {
        IssueCommentsQueryExecutor executor = new IssueCommentsQueryExecutor();
        executor.setGithubToken(token);
        executor.setTestEnvironment(true);
        executor.setPageSize(1);
        List<GithubIssueCommentsInfo> commentsInfoList =  executor.getIssueCommentsInfo("typegoose/typegoose", 243);

        System.out.println(commentsInfoList.size());
        for (GithubIssueCommentsInfo info : commentsInfoList){
            System.out.println("-------------------------");
            System.out.println(info.body);
            System.out.println("-------------------------");
        }
        Assertions.assertTrue(commentsInfoList.size() > 0);
    }

    @Test
    void getAllIssueCommentsInfo() {
        IssueCommentsQueryExecutor executor = new IssueCommentsQueryExecutor();
        executor.setGithubToken(token);
        executor.setTestEnvironment(true);
        executor.setPageSize(1);
        List<GithubIssueCommentsInfo> commentsInfoList =  executor.getIssuesCommentsInfo("typegoose/typegoose");

        System.out.println(commentsInfoList.size());
        for (GithubIssueCommentsInfo info : commentsInfoList){
            System.out.println("-------------------------");
            System.out.println(info.body);
            System.out.println("-------------------------");
        }
        Assertions.assertTrue(commentsInfoList.size() > 0);
    }
}