package br.com.jadson.snooper.github.data.pull;

import br.com.jadson.snooper.github.data.repo.GitHubRepoInfo;
import br.com.jadson.snooper.github.data.users.GitHubUserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubPullRequestBaseInfo {

    public String label;
    /**
     * The branch where the pull request of merged
     */
    public String ref;
    public String sha;
    public GitHubUserInfo user;
    public GitHubRepoInfo repo;
}
