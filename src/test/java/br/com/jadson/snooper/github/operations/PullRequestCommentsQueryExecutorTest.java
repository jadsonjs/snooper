package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.comments.GithubCommentsInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PullRequestCommentsQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");

    /**
     * Get comments and review comments of a PULL REQUEST
     */
    @Test
    void getPullCommentsInfo() {

        PullRequestCommentsQueryExecutor executor = new PullRequestCommentsQueryExecutor();
        executor.setGithubToken(token);
        executor.setTestEnvironment(true);
        executor.setPageSize(100);
        List<GithubCommentsInfo> commentsInfoList =  executor.getPullCommentsInfo("microsoft/terminal", 13746l);

        System.out.println(commentsInfoList.size());
        for (GithubCommentsInfo info : commentsInfoList){
            System.out.println("-------------------------");
            System.out.println(info.body);
            System.out.println("-------------------------");
        }
        Assertions.assertTrue(commentsInfoList.size() > 0);
    }

    /**
     * Get comments and review comments of a PULL REQUEST
     */
    @Test
    void getAllPullCommentsInfo() {

        PullRequestCommentsQueryExecutor executor = new PullRequestCommentsQueryExecutor();
        executor.setGithubToken(token);
        executor.setTestEnvironment(true);
        executor.setPageSize(1);
        List<GithubCommentsInfo> commentsInfoList =  executor.getPullCommentsInfo("microsoft/terminal");

        System.out.println(commentsInfoList.size());
        for (GithubCommentsInfo info : commentsInfoList){
            System.out.println("-------------------------");
            System.out.println(info.body);
            System.out.println(info.pull_request_url);
            System.out.println("-------------------------");
        }
        Assertions.assertTrue(commentsInfoList.size() > 0);
    }
}