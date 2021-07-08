package br.com.jadson.snooper.github.operations;
/*
 * snooper
 * GitHubSearchExecutorTest
 * @since 08/07/2021
 */

import br.com.jadson.snooper.github.data.repo.GitHubRepoInfo;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 *
 * @author Jadson Santos - jadson.santos@ufrn.br
 */
class GitHubSearchExecutorTest {

    @Test
    void testURLRepositoriesInformationByGitHubSearch(){

        GitHubSearchExecutor search = new GitHubSearchExecutor();

        List<GitHubRepoInfo> listOfProjects = search.searchRepositories(null, 100, 1000, "stars", "desc");

        for (GitHubRepoInfo info : listOfProjects){
            System.out.println(info.url);
        }

        Assertions.assertTrue(true);

    }

}