package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.operations.GitHubDownload;
import graphql.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GitHubDownloadTest {

    /**
     * Basic test
     */
    @Test
    void testDownload(){

        GitHubDownload gitHub = new GitHubDownload();

        String localRepo = gitHub.download("https://github.com/vuejs/vue-cli","/tmp/");

        Assertions.assertEquals("/tmp/vue-cli-master.zip", localRepo);
    }


    /**
     * Basic test with empty url
     */
    @Test
    void testEmptyRepoURLDownload(){

        GitHubDownload gitHub = new GitHubDownload();

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

        GitHubDownload gitHub = new GitHubDownload();

        String localRepo = gitHub.download("https://github.com/vuejs/vue-cli","/tmp/", true);

        Assertions.assertEquals("/tmp/vue-cli-master.zip", localRepo);
    }


}