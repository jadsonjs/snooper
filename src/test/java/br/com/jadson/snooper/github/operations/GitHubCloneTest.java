package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.operations.GitHubClone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GitHubCloneTest {


    /**
     * Basic test
     */
    @Test
    void testDownload(){

        GitHubClone gitHub = new GitHubClone("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        String localRepo = gitHub.clone("https://github.com/vuejs/vue-cli","/tmp/");

        Assertions.assertEquals("/tmp/", localRepo);
    }

}