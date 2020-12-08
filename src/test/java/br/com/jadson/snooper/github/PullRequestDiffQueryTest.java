package br.com.jadson.snooper.github;

import br.com.jadson.snooper.github.operations.PullRequestDiffQuery;
import br.com.jadson.snooper.github.data.diff.GitHubPullRequestDiffInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class PullRequestDiffQueryTest {

    /**
     * Basic test
     */
    @Test
    void testPullRequestDiff(){

        PullRequestDiffQuery gitHub = new PullRequestDiffQuery();
        gitHub.setTestEnvironment(true);

        GitHubPullRequestDiffInfo info =  gitHub.pullRequestsDiff("vuejs/vue-cli", 5356l);

        System.out.println(info.id);

        Assertions.assertTrue(info.id > 0);
    }

}