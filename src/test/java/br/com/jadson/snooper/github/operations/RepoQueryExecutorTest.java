package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.repo.GitHubRepoInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RepoQueryExecutorTest {

    @Test
    void getRepositoryStarsNumberTest() {

        RepoQueryExecutor executor = new RepoQueryExecutor();
        executor.setTestEnvironment(true);

        GitHubRepoInfo repo =  executor.getRepoInfo("webauthn4j/webauthn4j");

        // number of stars of repository
        Assertions.assertTrue(repo.stargazers_count > 0);
    }
}