package br.com.jadson.snooper.gitlab.data.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabLinksInfo {
    public String closed_issues_url;
    public String closed_merge_requests_url;
    public String edit_url;
    public String merged_merge_requests_url;
    public String opened_issues_url;
    public String opened_merge_requests_url;
    public String self;
}
