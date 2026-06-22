package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.commit.GitLabCommitInfo;
import br.com.jadson.snooper.gitlab.data.repo.GitLabTreeItem;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Performs queries in GitLab repositories.
 *
 * This class retrieves repository data from GitLab, such as files and directories
 * from the repository tree.
 */
public class GitLabRepoQueryExecutor extends AbstractGitLabQueryExecutor {

    public GitLabRepoQueryExecutor() {

    }

    /**
     * Creates a GitLab repository query executor.
     *
     * @param gitlabURL
     * @param gitlabToken
     */
    public GitLabRepoQueryExecutor(String gitlabURL, String gitlabToken) {
        this(gitlabURL, gitlabToken, false);
    }

    /**
     * Creates a GitLab repository query executor with SSL verification configuration.
     *
     * @param gitlabURL
     * @param gitlabToken
     * @param disableSslVerification
     */

    public GitLabRepoQueryExecutor(String gitlabURL, String gitlabToken, boolean disableSslVerification) {
        if (gitlabToken == null || gitlabToken.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab Token: " + gitlabToken);
        }
        if (gitlabURL == null || gitlabURL.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab URL: " + gitlabURL);
        }

        this.gitlabURL = gitlabURL;
        this.gitlabToken = gitlabToken;
        this.disableSslVerification = disableSslVerification;
    }

    /**
     * Return all files and directories from a GitLab repository tree.
     *
     * This method uses the REST API of GitLab.
     *
     * @param repoFullName
     * @return
     */
    public List<GitLabTreeItem> getAllFiles(String repoFullName) {
        int page = 1;
        String parameters = "";
        ArrayList allPull = new ArrayList();


        ResponseEntity result;
        do {
                parameters = "?recursive=true&page=" + page + "&per_page=" + this.pageSize;

            String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/repository/tree" + parameters;

            // encode "/" in "%2F" supported
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            System.out.println("Getting Next File: " + query);
            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();


            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabTreeItem[].class);
            allPull.addAll(Arrays.asList((GitLabTreeItem[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabTreeItem[])result.getBody()).length > 0 && !this.testEnvironment);

        return allPull;
    }

}
