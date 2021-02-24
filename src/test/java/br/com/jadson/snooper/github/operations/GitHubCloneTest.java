package br.com.jadson.snooper.github.operations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GitHubCloneTest {


    /**
     * Basic test
     */
    @Test
    void testDownload(){

        CloneExecutor gitHub = new CloneExecutor("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        String localRepo = gitHub.clone("https://github.com/vuejs/vue-cli","/tmp/");

        Assertions.assertEquals("/tmp/vuejs/vue-cli", localRepo);
    }

}