package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.repo.GitHubRepoInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RepoQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");

    @Test
    void getRepositoryStarsNumberTest() {

        RepoQueryExecutor executor = new RepoQueryExecutor();
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);

        GitHubRepoInfo repo =  executor.getRepoInfo("webauthn4j/webauthn4j");

        // number of stars of repository
        Assertions.assertTrue(repo.stargazers_count > 0);
    }
}