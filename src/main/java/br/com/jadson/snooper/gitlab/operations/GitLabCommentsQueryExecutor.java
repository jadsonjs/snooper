package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.discussion.GitLabDiscussionInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GitLabCommentsQueryExecutor extends AbstractGitLabQueryExecutor {

    public GitLabCommentsQueryExecutor() {

    }

    public GitLabCommentsQueryExecutor(String gitlabDomain, String gitlabToken) {
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
     * Gets a list of all discussion items for a single merge request.
     *
     * discussion = comments
     * @param repoFullName
     * @return
     */
    public List<GitLabDiscussionInfo> getMergeRequestDiscussion(String repoFullName, int mergeRequestIID) {
        int page = 1;
        String parameters = "";
        ArrayList allPull = new ArrayList();


        ResponseEntity result;
        do {
            if (this.queryParameters != null && !this.queryParameters.isEmpty()) {
                parameters = "?" + this.queryParameters + "page=" + page + "&per_page=" + this.pageSize;
            } else {
                parameters = "?page=" + page + "&per_page=" + this.pageSize;
            }

            // projects/:id/merge_requests/:merge_request_iid/discussions
            String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/merge_requests/"+ mergeRequestIID +"/discussions" + parameters;

            // encode "/" in "%2F" supported
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            System.out.println("Getting Next Comment of Merge Request: " + query);
            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();

            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabDiscussionInfo[].class);
            allPull.addAll(Arrays.asList((GitLabDiscussionInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabDiscussionInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return allPull;
    }


    /**
     * Gets a list of all discussion items for a single issue.
     *
     * discussion = comments
     * @param repoFullName
     * @return
     */
    public List<GitLabDiscussionInfo> getIssueDiscussion(String repoFullName, int issueIID) {
        int page = 1;
        String parameters = "";
        ArrayList allPull = new ArrayList();


        ResponseEntity result;
        do {
            if (this.queryParameters != null && !this.queryParameters.isEmpty()) {
                parameters = "?" + this.queryParameters + "page=" + page + "&per_page=" + this.pageSize;
            } else {
                parameters = "?page=" + page + "&per_page=" + this.pageSize;
            }

            // projects/:id/merge_requests/:merge_request_iid/discussions
            String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/issues/"+ issueIID +"/discussions" + parameters;

            // encode "/" in "%2F" supported
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            System.out.println("Getting Next Comment of Merge Request: " + query);
            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();

            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabDiscussionInfo[].class);
            allPull.addAll(Arrays.asList((GitLabDiscussionInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabDiscussionInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return allPull;
    }
}
