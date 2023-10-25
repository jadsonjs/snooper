package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.release.GitLabReleaseInfo;
import br.com.jadson.snooper.gitlab.data.tag.GitLabTagInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitLabReleaseQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //
    // to build:  Gradle ->  build tasks -> Modify Run Configuration ...
    String token = System.getenv("GITLAB_TOKEN");

    @Disabled
    @Test
    void releases() {

        GitLabReleaseQueryExecutor executor = new GitLabReleaseQueryExecutor();
        executor.setGitlabToken(token);
        executor.setTestEnvironment(true);
        List<GitLabReleaseInfo> releases = executor.releases("jadsonjs/holter-ci");

        Assertions.assertTrue(releases.size() > 0);
        Assertions.assertEquals("0.10", releases.get(0).name);
    }

}