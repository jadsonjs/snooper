package br.com.jadson.snooper.github.operations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CloneGitHubExecutorTest {


    /**
     * Basic test
     */
    @Test
    void testDownload(){

        CloneGitHubExecutor gitHub = new CloneGitHubExecutor("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        String localRepo = gitHub.clone("vuejs/vue-cli","/tmp/");

        Assertions.assertEquals("/tmp/vuejs/vue-cli", localRepo);
    }

}