package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.issue.GitLabIssueInfo;
import br.com.jadson.snooper.utils.DateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GitLabIssueQueryExecutor extends AbstractGitLabQueryExecutor {

    public GitLabIssueQueryExecutor() {
    }

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

            String query = "https://gitlab.com/api/v4/projects/" + repoFullName + "/issues" + parameters;
            System.out.println("Getting Next Issues Info: " + query);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(query, HttpMethod.GET, entity, GitLabIssueInfo[].class, new Object[0]);
            all.addAll(Arrays.asList((GitLabIssueInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabIssueInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return all;
    }

    public List<GitLabIssueInfo> issuesClosedInPeriod(String repoFullName, LocalDateTime start, LocalDateTime end) {
        List<GitLabIssueInfo> issues = new ArrayList();
        List<GitLabIssueInfo> allIssues = this.issues(repoFullName);
        DateUtils dateUtils = new DateUtils();
        Iterator var7 = allIssues.iterator();

        while(var7.hasNext()) {
            GitLabIssueInfo issue = (GitLabIssueInfo)var7.next();
            if (issue.closed_at != null) {
                LocalDateTime closedDate = issue.closed_at.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (dateUtils.isBetweenDates(closedDate, start, end)) {
                    issues.add(issue);
                }
            }
        }

        return issues;
    }
}
