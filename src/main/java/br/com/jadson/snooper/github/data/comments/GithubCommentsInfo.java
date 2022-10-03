package br.com.jadson.snooper.github.data.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubCommentsInfo {

    private String url;
    private float pull_request_review_id;
    private float id;
    private String node_id;
    private String diff_hunk;
    private String path;
    private String position = null;
    private float original_position;
    private String commit_id;
    private String original_commit_id;

    private String body;
    private String created_at;
    private String updated_at;
    private String html_url;
    private String pull_request_url;
    private String author_association;
    private String start_line = null;
    private String original_start_line = null;
    private String start_side = null;
    private String line = null;
    private float original_line;
    private String side;


    // Getter Methods

    public String getUrl() {
        return url;
    }

    public float getPull_request_review_id() {
        return pull_request_review_id;
    }

    public float getId() {
        return id;
    }

    public String getNode_id() {
        return node_id;
    }

    public String getDiff_hunk() {
        return diff_hunk;
    }

    public String getPath() {
        return path;
    }

    public String getPosition() {
        return position;
    }

    public float getOriginal_position() {
        return original_position;
    }

    public String getCommit_id() {
        return commit_id;
    }

    public String getOriginal_commit_id() {
        return original_commit_id;
    }

    public String getBody() {
        return body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getPull_request_url() {
        return pull_request_url;
    }

    public String getAuthor_association() {
        return author_association;
    }

    public String getStart_line() {
        return start_line;
    }

    public String getOriginal_start_line() {
        return original_start_line;
    }

    public String getStart_side() {
        return start_side;
    }

    public String getLine() {
        return line;
    }

    public float getOriginal_line() {
        return original_line;
    }

    public String getSide() {
        return side;
    }

    // Setter Methods

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPull_request_review_id(float pull_request_review_id) {
        this.pull_request_review_id = pull_request_review_id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public void setDiff_hunk(String diff_hunk) {
        this.diff_hunk = diff_hunk;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setOriginal_position(float original_position) {
        this.original_position = original_position;
    }

    public void setCommit_id(String commit_id) {
        this.commit_id = commit_id;
    }

    public void setOriginal_commit_id(String original_commit_id) {
        this.original_commit_id = original_commit_id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public void setPull_request_url(String pull_request_url) {
        this.pull_request_url = pull_request_url;
    }

    public void setAuthor_association(String author_association) {
        this.author_association = author_association;
    }

    public void setStart_line(String start_line) {
        this.start_line = start_line;
    }

    public void setOriginal_start_line(String original_start_line) {
        this.original_start_line = original_start_line;
    }

    public void setStart_side(String start_side) {
        this.start_side = start_side;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setOriginal_line(float original_line) {
        this.original_line = original_line;
    }

    public void setSide(String side) {
        this.side = side;
    }

}
