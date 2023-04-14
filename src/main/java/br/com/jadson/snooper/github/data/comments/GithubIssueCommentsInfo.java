package br.com.jadson.snooper.github.data.comments;

import br.com.jadson.snooper.github.data.issue.GitHubIssueUserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Information about comments of Issue or PR.
 * because all PR is an issue on GitHub.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubIssueCommentsInfo {
    public String url;
    public String html_url;
    public String issue_url;
    public int id;
    public String node_id;
    public GitHubIssueUserInfo user;
    public Date created_at;
    public Date updated_at;
    public String author_association;
    public String body;
    // public Reactions reactions;
    public Object performed_via_github_app;
}
