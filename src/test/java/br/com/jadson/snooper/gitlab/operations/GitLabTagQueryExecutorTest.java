package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.issue.GitLabIssueInfo;
import br.com.jadson.snooper.gitlab.data.tag.GitLabTagInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitLabTagQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //
    // to build:  Gradle ->  build tasks -> Modify Run Configuration ...
    String token = System.getenv("GITLAB_TOKEN");

    @Test
    void tags() {

        GitLabTagQueryExecutor executor = new GitLabTagQueryExecutor();
        executor.setGitlabToken(token);
        executor.setTestEnvironment(true);
        List<GitLabTagInfo> tags = executor.tags("jadsonjs/holter-ci");

        Assertions.assertTrue(tags.size() > 0);
        Assertions.assertEquals("0.10", tags.get(0).name);
    }
}