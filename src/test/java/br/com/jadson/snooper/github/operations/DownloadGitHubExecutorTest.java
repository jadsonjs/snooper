package br.com.jadson.snooper.github.operations;

import graphql.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class DownloadGitHubExecutorTest {

    /**
     * Basic test
     */
    @Test
    void testDownload(){

        DownloadGitHubExecutor gitHub = new DownloadGitHubExecutor();

        String localRepo = gitHub.download("vuejs/vue-cli","/tmp");

        Assertions.assertEquals("/tmp/vuejs/vue-cli-master.zip", localRepo);

        File f = new File("/tmp/vuejs/vue-cli-master.zip");

        Assertions.assertTrue(f.exists());

        try {
            FileUtils.deleteDirectory(new File("/tmp/vuejs/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Basic test with empty url
     */
    @Test
    void testEmptyRepoURLDownload(){

        DownloadGitHubExecutor gitHub = new DownloadGitHubExecutor();

        String repoURL = "";

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                ()->{ gitHub.download(repoURL,"/tmp");  });

        Assert.assertTrue( exception.getMessage().contains("Invalid repo name: "+repoURL));

    }


    /**
     * Basic download and unzip
     */
    @Test
    void testDownloadAndUnzip(){

        DownloadGitHubExecutor gitHub = new DownloadGitHubExecutor();

        String localRepo = gitHub.download("vuejs/vue-cli","/tmp", true);

        Assertions.assertEquals("/tmp/vuejs/vue-cli-master.zip", localRepo);

        File f = new File("/tmp/vuejs/vue-cli-master.zip");

        Assertions.assertTrue(f.exists());

        try {
            FileUtils.deleteDirectory(new File("/tmp/vuejs/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}