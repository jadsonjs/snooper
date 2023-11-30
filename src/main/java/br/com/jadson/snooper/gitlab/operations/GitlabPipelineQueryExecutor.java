package br.com.jadson.snooper.gitlab.operations;


import br.com.jadson.snooper.gitlab.data.pipeline.GitLabPipelineInfo;
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
 * performs queries in gitlab pipelines.
 *
 * pipelines in gitlab is a concept close to ''build'' on travis Ci and ''runners'' on GitHub Actions
 */
public class GitlabPipelineQueryExecutor extends AbstractGitLabQueryExecutor {

    public GitlabPipelineQueryExecutor() {

    }

    public GitlabPipelineQueryExecutor(String gitlabDomain, String gitlabToken) {
        if (gitlabToken == null || gitlabToken.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab Token: " + gitlabToken);
        }
        if (gitlabDomain == null || gitlabDomain.trim().equals("") ) {
            throw new RuntimeException("Invalid GitLab URL: " + gitlabDomain);
        }

        this.gitlabURL = gitlabDomain;
        this.gitlabToken = gitlabToken;
    }


    /**
     * Return a list of pipelines of a project
     * @param repoFullName
     * @return
     */
    public List<GitLabPipelineInfo> getPipeLines(String repoFullName) {
        int page = 1;
        String parameters = "";
        List allPipelines = new ArrayList<GitLabPipelineInfo>();


        ResponseEntity result;
        do {
            if (this.queryParameters != null && !this.queryParameters.isEmpty()) {
                parameters = "?" + this.queryParameters + "page=" + page + "&per_page=" + this.pageSize;
            } else {
                parameters = "?page=" + page + "&per_page=" + this.pageSize;
            }

            String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/pipelines/" + parameters;

            // encode "/" in "%2F" supported
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            System.out.println("Getting Next Pipeline: " + query);
            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();

            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabPipelineInfo[].class);
            allPipelines.addAll(Arrays.asList((GitLabPipelineInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabPipelineInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return allPipelines;
    }


    /**
     * Return a specific Pipelines information (more complete than a list of pipelines)
     *
     * @param repoFullName
     * @return
     */
    public GitLabPipelineInfo getPipeLine(String repoFullName, long pipelineNumber) {

        validateRepoName(repoFullName);

        ResponseEntity<GitLabPipelineInfo> result;

        String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/pipelines/" + pipelineNumber;

        // encode "/" in "%2F" supported
        URI uri = null;
        try {
            uri = new URI(query);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Getting Pipeline: " + query);
        RestTemplate restTemplate = new RestTemplate();

        checkDisableSslVerification();

        HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
        result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabPipelineInfo.class);

        return result.getBody();
    }

}
