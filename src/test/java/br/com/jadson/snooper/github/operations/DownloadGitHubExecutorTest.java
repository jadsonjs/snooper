package br.com.jadson.snooper.github.operations;

import graphql.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DownloadGitHubExecutorTest {

    /**
     * Basic test
     */
    @Test
    void testDownload(){

        DownloadGitHubExecutor gitHub = new DownloadGitHubExecutor();

        String localRepo = gitHub.download("vuejs/vue-cli","/tmp/");

        Assertions.assertEquals("/tmp/vuejs/vue-cli-master.zip", localRepo);
    }


    /**
     * Basic test with empty url
     */
    @Test
    void testEmptyRepoURLDownload(){

        DownloadGitHubExecutor gitHub = new DownloadGitHubExecutor();

        String repoURL = "";

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                ()->{ gitHub.download(repoURL,"/tmp/");  });

        Assert.assertTrue( exception.getMessage().contains("Invalid repo url: "+repoURL));

    }


    /**
     * Basic download and unzip
     */
    @Test
    void testDownloadAndUnzip(){

        DownloadGitHubExecutor gitHub = new DownloadGitHubExecutor();

        String localRepo = gitHub.download("vuejs/vue-cli","/tmp/", true);

        Assertions.assertEquals("/tmp/vuejs/vue-cli-master.zip", localRepo);
    }


}