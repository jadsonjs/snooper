package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.commit.CommitDiff;
import br.com.jadson.snooper.gitlab.data.commit.GitLabCommitInfo;
import br.com.jadson.snooper.gitlab.data.commit.FileChanged;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GitLabCommitQueryExecutor extends AbstractGitLabQueryExecutor {

    public GitLabCommitQueryExecutor() {

    }

    public GitLabCommitQueryExecutor(String gitlabURL, String gitlabToken) {
        this(gitlabURL, gitlabToken, false);
    }

    public GitLabCommitQueryExecutor(String gitlabURL, String gitlabToken, boolean disableSslVerification) {
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
     * Return a list of commit form a repository on gitlab
     * @param repoFullName
     * @return
     */
    public List<GitLabCommitInfo> getCommits(String repoFullName) {
        int page = 1;
        String parameters = "";
        ArrayList allPull = new ArrayList();


        ResponseEntity result;
        do {
            if (this.queryParameters != null && !this.queryParameters.isEmpty()) {
                parameters = "?" + this.queryParameters + "with_stats=true&page=" + page + "&per_page=" + this.pageSize;
            } else {
                parameters = "?page=" + page + "&per_page=" + this.pageSize;
            }

            String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/repository/commits" + parameters;

            // encode "/" in "%2F" supported
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            System.out.println("Getting Next Commit: " + query);
            RestTemplate restTemplate = new RestTemplate();

            checkDisableSslVerification();


            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, GitLabCommitInfo[].class);
            allPull.addAll(Arrays.asList((GitLabCommitInfo[])result.getBody()));
            ++page;
        } while(result != null && ((GitLabCommitInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return allPull;
    }

    public List<FileChanged> getCommitsFiles(String repoFullName, GitLabCommitInfo commit){
        validateRepoName(repoFullName);

        ResponseEntity<CommitDiff[]> result;

        String query = getGitLabAPIURL()
                + encodeProjectName(repoFullName)
                + "/repository/commits/"
                + commit.id
                + "/diff";

        System.out.println(query);

        System.out.println("Getting files for GitLab commit");

        URI uri = null;
        try {
            uri = new URI(query);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        checkDisableSslVerification();


        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
        result = restTemplate.exchange(uri, HttpMethod.GET, entity, CommitDiff[].class);



        if (result.getBody() == null) {
            return new ArrayList<>();
        }

        List<FileChanged> files = new ArrayList<>();

        for (CommitDiff diff : result.getBody()) {
            FileChanged file = new FileChanged();

            file.filename = diff.newPath != null ? diff.newPath : diff.oldPath;
            file.additions = countAdditions(diff.diff);
            file.deletions = countDeletions(diff.diff);

            files.add(file);
        }

        return files;
    }

    private int countAdditions(String diff){
        if (diff == null || diff.isEmpty()) {
            return 0;
        }

        int additions = 0;

        for (String line : diff.split("\n")) {
            if (line.startsWith("+") && !line.startsWith("+++")) {
                additions++;
            }
        }

        return additions;
    }

    private int countDeletions(String diff) {
        if (diff == null || diff.isEmpty()) {
            return 0;
        }

        int deletions = 0;

        for (String line : diff.split("\n")) {
            if (line.startsWith("-") && !line.startsWith("---")) {
                deletions++;
            }
        }

        return deletions;
    }
    }