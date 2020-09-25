package br.com.jadson.snooper.github;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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