package br.com.jadson.snooper.github.operations;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class CloneGitHubExecutorTest {


    /**
     * Basic test
     */
    @Test
    void testDownload(){

        CloneGitHubExecutor gitHub = new CloneGitHubExecutor("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        String localRepo = gitHub.clone("vuejs/vue-cli","/tmp");

        Assertions.assertEquals("/tmp/vuejs/vue-cli", localRepo);

        File f = new File("/tmp/vuejs/");

        Assertions.assertTrue(f.exists() && f.isDirectory());

        try {
            FileUtils.deleteDirectory(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}