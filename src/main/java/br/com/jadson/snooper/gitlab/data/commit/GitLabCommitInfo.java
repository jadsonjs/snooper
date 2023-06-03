package br.com.jadson.snooper.gitlab.data.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabCommitInfo {
    public String id;
    public String short_id;
    public String title;
    public String author_name;
    public String author_email;
    public Date authored_date;
    public String committer_name;
    public String committer_email;
    public Date committed_date;
    public Date created_at;
    public String message;
    public List<String> parent_ids;
    public String web_url;

    public GitLabCommitInfo() {
    }
}
