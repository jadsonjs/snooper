package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.merge.GitLabMergeRequestInfo;
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

public class GitLabMergeRequestQueryExecutor extends AbstractGitLabQueryExecutor {

    public GitLabMergeRequestQueryExecutor() {
    }

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

            String query = "https://gitlab.com/api/v4/projects/" + repoFullName + "/merge_requests" + parameters;
            System.out.println("Getting Next Merge Info: " + query);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(query, HttpMethod.GET, entity, GitLabMergeRequestInfo[].class, new Object[0]);
            allPull.addAll(Arrays.asList((GitLabMergeRequestInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabMergeRequestInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return allPull;
    }

    public List<GitLabMergeRequestInfo> mergeRequestsCreatedInPeriod(String repoFullName, LocalDateTime start, LocalDateTime end) {
        List<GitLabMergeRequestInfo> mergeRequests = new ArrayList();
        List<GitLabMergeRequestInfo> allMergeRequests = this.mergeRequests(repoFullName);
        DateUtils dateUtils = new DateUtils();
        Iterator var7 = allMergeRequests.iterator();

        while(var7.hasNext()) {
            GitLabMergeRequestInfo mr = (GitLabMergeRequestInfo)var7.next();
            if (mr.created_at != null) {
                LocalDateTime createdDate = LocalDateTime.ofInstant(mr.created_at.toInstant(), ZoneId.systemDefault());
                if (dateUtils.isBetweenDates(createdDate, start, end)) {
                    mergeRequests.add(mr);
                }
            }
        }

        return mergeRequests;
    }


}
