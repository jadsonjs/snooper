package br.com.jadson.snooper.gitlab.data.pipeline;

import br.com.jadson.snooper.gitlab.data.basic.GitLabAuthorInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabPipelineInfo {

    public int id;
    public int iid;
    public int project_id;
    public String sha;
    public String ref;
    public String status;
    public String source;
    public Date created_at;
    public Date updated_at;
    public String web_url;
    public String before_sha;
    public boolean tag;
    public Object yaml_errors;
    public GitLabAuthorInfo user;
    public Date started_at;
    public Date finished_at;
    public Object committed_at;

    /**
     * In seconds
     */
    public int duration;
    public int queued_duration;
    public Object coverage;
    public DetailedStatus detailed_status;
}
