package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.github.operations.AbstractGitHubQueryExecutor;
import br.com.jadson.snooper.githubactions.data.runs.RunsInfo;
import br.com.jadson.snooper.githubactions.data.runs.WorkflowRunsInfoRoot;
import br.com.jadson.snooper.githubactions.data.workflow.WorkflowInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Get Information about the runs on github actions. That is equivalent to builds on travis CI.
 *
 * Runs represent an execution of a workflow.
 *
 */
public class GHActionRunsExecutor extends AbstractGitHubQueryExecutor {

    public GHActionRunsExecutor(){ }

    public GHActionRunsExecutor(String githubToken){
        super(githubToken);
    }


    /**
     * Return all runs of a workflow
     *
     * @param repoFullName
     * @param workflowId  if of a workflow. A project can have several workflows.
     * @return
     */
    public List<RunsInfo> runs(String repoFullName, long workflowId) {

        validateRepoName(repoFullName);

        int page = 1;

        String parameters = "";

        List<RunsInfo> all = new ArrayList<>();

        ResponseEntity<WorkflowRunsInfoRoot> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/actions/workflows/"+workflowId+"/runs"+parameters;

            System.out.println("Getting Next workflow Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, WorkflowRunsInfoRoot.class);

            all.addAll(  result.getBody().workflow_runs );

            page++;


        }while ( result != null && result.getBody().workflow_runs.size() > 0   && !testEnvironment);

        return all;
    }



    /**
     * Return information about the last run of a repository
     *
     * @param repoFullName
     * @return
     */
    public RunsInfo lastRun(String repoFullName) {

        validateRepoName(repoFullName);

        GHActionWorkflowsExecutor workFlowExecutor = new GHActionWorkflowsExecutor();
        if(githubToken != null && ! githubToken.trim().equals(""))
            workFlowExecutor.setGithubToken(githubToken);

        List<WorkflowInfo> workflowInfos = workFlowExecutor.getWorkflows(repoFullName);

        RunsInfo lastRunInfo = null;

        for (WorkflowInfo wflow : workflowInfos){
            RunsInfo runInfoTemp =  this.lastRun(repoFullName, wflow.id);

            if( lastRunInfo == null || ( runInfoTemp != null && runInfoTemp.run_started_at.after(lastRunInfo.run_started_at) )  ){
                lastRunInfo = runInfoTemp;
            }

        }

        return lastRunInfo;
    }



    /**
     * Return information about the last runs of a workflow
     *
     * @param repoFullName
     * @param workflowId  if of a workflow. A project can have several workflows.
     * @return
     */
    public RunsInfo lastRun(String repoFullName, long workflowId) {

        validateRepoName(repoFullName);

        int page = 1;
        pageSize = 1;

        String parameters = "";

        RunsInfo all = null;

        ResponseEntity<WorkflowRunsInfoRoot> result;

        if(queryParameters != null && ! queryParameters.isEmpty())
            parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
        else
            parameters = "?page="+page+"&per_page="+pageSize;

        String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/actions/workflows/"+workflowId+"/runs"+parameters;

        System.out.println("Getting Next workflow Info: "+query);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, WorkflowRunsInfoRoot.class);

        if(result.getBody().workflow_runs.size() > 0 )
            all =  result.getBody().workflow_runs.get(0);

        return all;
    }

}
