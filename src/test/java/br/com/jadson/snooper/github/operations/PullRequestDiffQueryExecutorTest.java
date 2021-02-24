package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.diff.GitHubPullRequestDiffInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class PullRequestDiffQueryExecutorTest {

    /**
     * Basic test
     */
    @Test
    void testPullRequestDiff(){

        PullRequestDiffQueryExecutor gitHub = new PullRequestDiffQueryExecutor();
        gitHub.setTestEnvironment(true);

        GitHubPullRequestDiffInfo info =  gitHub.pullRequestsDiff("vuejs/vue-cli", 5356l);

        System.out.println(info.id);

        Assertions.assertTrue(info.id > 0);
    }

}