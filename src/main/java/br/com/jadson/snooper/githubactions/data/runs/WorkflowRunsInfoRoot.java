package br.com.jadson.snooper.githubactions.data.runs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowRunsInfoRoot {
    public int total_count;
    public List<WorkflowRunsInfo> workflow_runs;
}
