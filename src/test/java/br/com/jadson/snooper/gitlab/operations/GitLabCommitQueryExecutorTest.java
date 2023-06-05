package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.commit.GitLabCommitInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GitLabCommitQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //
    // to build:  Gradle ->  build tasks -> Modify Run Configuration ...
    String token = System.getenv("GITLAB_TOKEN");


    @Test
    void testCommitsOfProject(){

        GitLabCommitQueryExecutor commitExecutor = new GitLabCommitQueryExecutor();
        commitExecutor.setQueryParameters(new String[]{"since=2022-05-18T22:26:45Z", "until=2023-06-01T22:26:45Z"});
        commitExecutor.setPageSize(100);
        commitExecutor.setGitlabToken(token);
        commitExecutor.setTestEnvironment(true);
        List<GitLabCommitInfo> commitsInfo = commitExecutor.getCommits("jadsonjs/holter-ci");

        Assertions.assertTrue(commitsInfo.size() == 18);
        Assertions.assertEquals("jadson.santos@ufrn.br", commitsInfo.get(0).committer_email);

    }


}