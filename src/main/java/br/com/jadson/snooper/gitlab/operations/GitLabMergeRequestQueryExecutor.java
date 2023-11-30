package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.merge.GitLabMergeRequestInfo;
import br.com.jadson.snooper.utils.DateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GitLabMergeRequestQueryExecutor extends AbstractGitLabQueryExecutor {

    public GitLabMergeRequestQueryExecutor() {
    }

    public GitLabMergeRequestQueryExecutor(String gitlabDomain, String gitlabToken) {
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
     * Return merge request of a project
     *
     * ?state=all = recovery all
     *
     * @param repoFullName
     * @return
     */
    public List<GitLabMergeRequestInfo> mergeRequests(String repoFullName) {
        int page = 1;
        String parameters = "";
        ArrayList allMerge = new ArrayList();

        ResponseEntity result;
        do {
            if (this.queryParameters != null && !this.queryParameters.isEmpty()) {
                parameters = "?" + this.queryParameters + "page=" + page + "&per_page=" + this.pageSize;
            } else {
                parameters = "?page=" + page + "&per_page=" + this.pageSize;
            }

            String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/merge_requests" + parameters;

            // encode "/" in "%2F" supported
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            System.out.println("Getting Next Merge Info: " + query);
            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();

            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabMergeRequestInfo[].class);
            allMerge.addAll(Arrays.asList((GitLabMergeRequestInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabMergeRequestInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return allMerge;
    }

    /**
     * Return the merge request created in specific period
     * @param repoFullName
     * @param start
     * @param end
     * @return
     */
    public List<GitLabMergeRequestInfo> mergeRequestsInPeriod(String repoFullName, LocalDateTime start, LocalDateTime end) {

        List<GitLabMergeRequestInfo> mergeRequests = new ArrayList();

        List<GitLabMergeRequestInfo> allMergesRequests = mergeRequests(repoFullName);

        DateUtils dateUtils = new DateUtils();

        for (GitLabMergeRequestInfo merge : allMergesRequests) {

            if (merge.created_at != null) {
                LocalDateTime startIssue = merge.created_at.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (dateUtils.isBetweenDates(startIssue, start, end)) {
                    mergeRequests.add(merge);
                }
            }
        }

        return mergeRequests;
    }

    /**
     * Return the list of all merge request associated to a commmit.
     *
     * https://projetos.imd.ufrn.br/api/v4/projects/:ID/repository/commits/SHA/merge_requests
     *
     * @param repoFullName
     * @param commitSHA
     * @return
     */
    public List<GitLabMergeRequestInfo> listMergeRequestsAssociatedwithCommit(String repoFullName, String commitSHA) {
        int page = 1;
        String parameters = "";
        ArrayList allMerges = new ArrayList();

        ResponseEntity result;
        do {
            if (this.queryParameters != null && !this.queryParameters.isEmpty()) {
                parameters = "?" + this.queryParameters + "page=" + page + "&per_page=" + this.pageSize;
            } else {
                parameters = "?page=" + page + "&per_page=" + this.pageSize;
            }

            // https://projetos.imd.ufrn.br/api/v4/projects + "group/project" + /repository/commits/   SHA
            String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/repository/commits/" +commitSHA+ "/merge_requests" + parameters;

            // encode "/" in "%2F" supported
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            System.out.println("Getting Next Merge Info: " + query);
            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();

            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabMergeRequestInfo[].class);
            allMerges.addAll(Arrays.asList((GitLabMergeRequestInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabMergeRequestInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return allMerges;
    }



}
