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
        ArrayList allPull = new ArrayList();

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
            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabMergeRequestInfo[].class);
            allPull.addAll(Arrays.asList((GitLabMergeRequestInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabMergeRequestInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return allPull;
    }

    /**
     * Return the merge request created in specific period
     * @param repoFullName
     * @param start
     * @param end
     * @return
     */
    public List<GitLabMergeRequestInfo> mergeRequestsCreatedInPeriod(String repoFullName, LocalDateTime start, LocalDateTime end) {

        List<GitLabMergeRequestInfo> issues = new ArrayList();

        List<GitLabMergeRequestInfo> allIssues = mergeRequests(repoFullName);

        DateUtils dateUtils = new DateUtils();

        for (GitLabMergeRequestInfo issue : allIssues) {

            if (issue.created_at != null) {
                LocalDateTime startIssue = issue.created_at.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (dateUtils.isBetweenDates(startIssue, start, end)) {
                    issues.add(issue);
                }
            }
        }

        return issues;
    }


}
