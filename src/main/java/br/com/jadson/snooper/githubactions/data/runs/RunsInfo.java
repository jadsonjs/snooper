package br.com.jadson.snooper.githubactions.data.runs;

import br.com.jadson.snooper.utils.CustomDateDeserializer;
import br.com.jadson.snooper.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunsInfo {

    public Long id;
    public String name;
    public String node_id;
    public String head_branch;
    public String head_sha;
    public String path;
    public int run_number;
    public String event;

    /**
     * completed = the build has finished
     */
    public String status;

    /**
     * success or failure
     */
    public String conclusion;
    public Long workflow_id;
    public Object check_suite_id;
    public String check_suite_node_id;
    public String url;
    public String html_url;
    public List<Object> pull_requests;

    /**
     * Build Duration = updated_at - created_at
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date created_at;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date updated_at;

    public ActorInfo actor;
    public int run_attempt;
    public List<Object> referenced_workflows;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date run_started_at;
    public TriggeringActorInfo triggering_actor;
    public String jobs_url;
    public String logs_url;
    public String check_suite_url;
    public String artifacts_url;
    public String cancel_url;
    public String rerun_url;
    public Object previous_attempt_url;
    public String workflow_url;
    public HeadCommitInfo head_commit;
    public RepositoryInfo repository;
    public HeadRepositoryInfo head_repository;

}
