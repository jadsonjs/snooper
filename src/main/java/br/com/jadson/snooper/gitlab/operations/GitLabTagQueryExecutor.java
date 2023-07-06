package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.issue.GitLabIssueInfo;
import br.com.jadson.snooper.gitlab.data.tag.GitLabTagInfo;
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
 * https://docs.gitlab.com/ee/api/tags.html
 */
public class GitLabTagQueryExecutor extends AbstractGitLabQueryExecutor {

    public GitLabTagQueryExecutor() {
    }

    public GitLabTagQueryExecutor(String gitlabDomain, String gitlabToken) {
        if (gitlabToken == null || gitlabToken.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab Token: " + gitlabToken);
        }
        if (gitlabDomain == null || gitlabDomain.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab URL: " + gitlabDomain);
        }

        this.gitlabDomain = gitlabDomain;
        this.gitlabToken = gitlabToken;
    }


    /**
     * Return the tags of a project
     * @param repoFullName
     * @return
     */
    public List<GitLabTagInfo> tags(String repoFullName) {

        ArrayList all = new ArrayList();

        ResponseEntity result;

        String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/repository/tags";

        // encode "/" in "%2F" supported
        URI uri = null;
        try {
            uri = new URI(query);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Getting Tags Info: " + query);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
        result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabTagInfo[].class);
        all.addAll(Arrays.asList((GitLabTagInfo[])result.getBody()));

        return all;
    }


}
