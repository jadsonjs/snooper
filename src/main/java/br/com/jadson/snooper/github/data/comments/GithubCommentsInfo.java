package br.com.jadson.snooper.github.data.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubCommentsInfo {

    public String url;
    public long pull_request_review_id;
    public long id;
    public String node_id;
    public String diff_hunk;
    public String path;
    public String position = null;
    public float original_position;
    public String commit_id;
    public String original_commit_id;

    public String body;
    public Date created_at;
    public Date updated_at;
    public String html_url;
    public String pull_request_url;
    public String author_association;
    public String start_line = null;
    public String original_start_line = null;
    public String start_side = null;
    public int line = 0;
    public float original_line;
    public String side;


}
