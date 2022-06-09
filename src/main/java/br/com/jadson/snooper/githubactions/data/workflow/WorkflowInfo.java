package br.com.jadson.snooper.githubactions.data.workflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowInfo {

    public long id;
    public String node_id;
    public String name;
    public String path;
    public String state;
    public Date created_at;
    public Date updated_at;
    public String url;
    public String html_url;
    public String badge_url;
}
