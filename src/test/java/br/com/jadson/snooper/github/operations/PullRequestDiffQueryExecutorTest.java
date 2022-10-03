package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.diff.GitHubPullRequestDiffInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class PullRequestDiffQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");

    /**
     * Basic test
     */
    @Test
    void testPullRequestDiff(){

        PullRequestDiffQueryExecutor gitHub = new PullRequestDiffQueryExecutor();
        gitHub.setTestEnvironment(true);
        gitHub.setGithubToken(token);

        GitHubPullRequestDiffInfo info =  gitHub.pullRequestsDiff("vuejs/vue-cli", 5356l);

        System.out.println(info.id);

        Assertions.assertTrue(info.id > 0);
    }

}