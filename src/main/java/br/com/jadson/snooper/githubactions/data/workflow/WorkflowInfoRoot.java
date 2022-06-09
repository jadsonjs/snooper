package br.com.jadson.snooper.githubactions.data.workflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowInfoRoot {
    public int total_count;
    public List<WorkflowInfo> workflows;
}
