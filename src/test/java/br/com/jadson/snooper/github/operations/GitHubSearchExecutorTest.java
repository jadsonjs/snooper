package br.com.jadson.snooper.github.operations;
/*
 * snooper
 * GitHubSearchExecutorTest
 * @since 08/07/2021
 */

import br.com.jadson.snooper.github.data.repo.GitHubRepoInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 *
 *
 * @author Jadson Santos - jadson.santos@ufrn.br
 */
class GitHubSearchExecutorTest {

    /***
     * Test the search:
     *
     * https://github.com/search?o=desc&q=stars%3A%3E%3D100000+size%3A%3E%3D100000&s=stars&type=Repositories
     */
    @Test
    void testURLRepositoriesInformationByGitHubSearch(){

        GitHubSearchExecutor search = new GitHubSearchExecutor();
        search.setTestEnvironment(true);

        List<GitHubRepoInfo> listOfProjects = search.searchRepositories(null, 100000, 100000, "stars", "desc");

        for (GitHubRepoInfo repo : listOfProjects){
            System.out.println("["+repo.full_name+"] "+repo.url);
        }
        Assertions.assertTrue(listOfProjects.size() > 0);
    }

    /**
     * https://github.com/search?l=&o=desc&q=stars%3A%3E%3D100+size%3A%3E%3D1000+language%3AJava&s=stars&type=Repositories
     */
    @Test
    void testURLRepositoriesInformationByGitHubSearchWithPagination(){

        GitHubSearchExecutor search = new GitHubSearchExecutor();
        search.setTestEnvironment(true);

        // java projects with more the 100 stars and 1MB
        List<GitHubRepoInfo> listOfProjects = search.searchRepositories("Java", 100, 1000, "stars", "desc");

        int i = 1;
        for (GitHubRepoInfo repo : listOfProjects){
            System.out.println("("+i+")["+repo.full_name+"] "+repo.url);
            i++;
        }

        Assertions.assertTrue(listOfProjects.size() > 0);
    }

}