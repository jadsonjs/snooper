package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.issue.GitLabIssueInfo;
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

public class GitLabIssueQueryExecutor extends AbstractGitLabQueryExecutor {

    public GitLabIssueQueryExecutor() {
    }

    public GitLabIssueQueryExecutor(String gitlabDomain, String gitlabToken) {
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
     * Return the issues of a project
     * @param repoFullName
     * @return
     */
    public List<GitLabIssueInfo> issues(String repoFullName) {
        int page = 1;
        String parameters = "";
        ArrayList all = new ArrayList();

        ResponseEntity result;
        do {
            if (this.queryParameters != null && !this.queryParameters.isEmpty()) {
                parameters = "?" + this.queryParameters + "page=" + page + "&per_page=" + this.pageSize;
            } else {
                parameters = "?page=" + page + "&per_page=" + this.pageSize;
            }

            String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/issues" + parameters;

            // encode "/" in "%2F" supported
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


            System.out.println("Getting Next Issues Info: " + query);
            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();

            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabIssueInfo[].class);
            all.addAll(Arrays.asList((GitLabIssueInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabIssueInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return all;
    }

    public List<GitLabIssueInfo> issuesInPeriod(String repoFullName, LocalDateTime start, LocalDateTime end) {

        List<GitLabIssueInfo> issues = new ArrayList();

        List<GitLabIssueInfo> allIssues = issues(repoFullName);

        DateUtils dateUtils = new DateUtils();

        for (GitLabIssueInfo issue : allIssues) {

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
