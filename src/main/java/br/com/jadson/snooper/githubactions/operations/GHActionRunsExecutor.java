package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.github.operations.AbstractGitHubQueryExecutor;
import br.com.jadson.snooper.githubactions.data.runs.RunsInfo;
import br.com.jadson.snooper.githubactions.data.runs.RunsInfoRoot;
import br.com.jadson.snooper.githubactions.data.workflow.WorkflowInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
     * Lists all workflow runs for a repository. You can use parameters to narrow the list of results.
     *
     * curl \
     *   -H "Accept: application/vnd.github+json" \
     *   -H "Authorization: Bearer <YOUR-TOKEN>" \
     *   https://HOSTNAME/api/v3/repos/OWNER/REPO/actions/runs
     *
     *  To return run os specific date, use a parameter.
     *  Example:
     *      https://api.github.com/repos/jadsonjs/snooper/actions/runs?created=2022-07-01..2022-07-30&status=timed_out&page=1&per_page=10
     *
     *  https://docs.github.com/pt/github-ae@latest/rest/actions/workflow-runs#list-workflow-runs-for-a-repository
     *
     * @param repoFullName
     * @return
     */
    public List<RunsInfo> runs(String repoFullName) {

        validateRepoName(repoFullName);

        int page = 1;

        String parameters = "";

        List<RunsInfo> all = new ArrayList<>();

        ResponseEntity<RunsInfoRoot> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/actions/runs"+parameters;

            System.out.println("Getting Next workflow Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, RunsInfoRoot.class);

            all.addAll(  result.getBody().workflow_runs );

            page++;


        }while ( result != null && result.getBody().workflow_runs.size() > 0   && !testEnvironment);

        return all;
    }

    /**
     * Return information about all runs of a workflow
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

        ResponseEntity<RunsInfoRoot> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/actions/workflows/"+workflowId+"/runs"+parameters;

            System.out.println("Getting Next workflow Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, RunsInfoRoot.class);

            all.addAll(  result.getBody().workflow_runs );

            page++;


        }while ( result != null && result.getBody().workflow_runs.size() > 0   && !testEnvironment);

        return all;
    }



    /**
     * Return information about the last run of a repository.
     *
     * Last run is the first run returned by query, because the API return in descending order
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

        // last run info of several workflows
        RunsInfo lastRunInfo = null;

        for (WorkflowInfo wflow : workflowInfos){
            RunsInfo runInfoTemp =  this.lastRun(repoFullName, wflow.id);

            // run_started_at can be null, so we use always created_at
            if( lastRunInfo == null || ( lastRunInfo != null && lastRunInfo.created_at != null && runInfoTemp != null && runInfoTemp.created_at != null
                    && runInfoTemp.created_at.after(lastRunInfo.created_at) )  ){
                lastRunInfo = runInfoTemp;
            }

        }

        return lastRunInfo;
    }


    /**
     * Return information about the last runs of a workflow.  Last run is the first in the results, because the API return the decreasing order
     *
     * @param repoFullName
     * @param workflowId  if of a workflow. A project can have several workflows.
     * @return
     */
    public RunsInfo lastRun(String repoFullName, long workflowId) {

        validateRepoName(repoFullName);

        // the last result if the first of first page
        int page = 1;
        pageSize = 1;

        String parameters = "";

        RunsInfo all = null;

        ResponseEntity<RunsInfoRoot> result;

        if(queryParameters != null && ! queryParameters.isEmpty())
            parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
        else
            parameters = "?page="+page+"&per_page="+pageSize;

        String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/actions/workflows/"+workflowId+"/runs"+parameters;

        System.out.println("Getting Next run Info: "+query);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, RunsInfoRoot.class);

        if(result.getBody().workflow_runs.size() > 0 )
            all =  result.getBody().workflow_runs.get(0);

        return all;
    }


    /**
     * Return information about the first runs of a repository.
     * First run is the last result of the query, because the API return in descending order
     *
     * @param repoFullName
     * @return
     */
    public RunsInfo firstRun(String repoFullName) {

        validateRepoName(repoFullName);

        GHActionWorkflowsExecutor workFlowExecutor = new GHActionWorkflowsExecutor();
        if(githubToken != null && ! githubToken.trim().equals(""))
            workFlowExecutor.setGithubToken(githubToken);

        List<WorkflowInfo> workflowInfos = workFlowExecutor.getWorkflows(repoFullName);

        RunsInfo firstRunInfo = null;

        for (WorkflowInfo wflow : workflowInfos){

            RunsInfo runInfoTemp =  this.firstRun(repoFullName, wflow.id);

            // run_started_at can be null, so we use the created_at
            // if is null or if before the first of previous workflow.
            if( firstRunInfo == null ||
                    (firstRunInfo != null && runInfoTemp != null && runInfoTemp.created_at != null && firstRunInfo.created_at != null && runInfoTemp.created_at.before(firstRunInfo.created_at) )  ){
                firstRunInfo = runInfoTemp;
            }

        }
        return firstRunInfo;
    }



    /**
     * Return information about the first runs of a workflow
     *
     * @param repoFullName
     * @param workflowId  if of a workflow. A project can have several workflows.
     * @return
     */
    public RunsInfo firstRun(String repoFullName, long workflowId) {

        validateRepoName(repoFullName);

        this.pageSize = 1;
        // first consult the root just to see the quantity of results
        // see how many run have, take the last one, because the api came in descending order
        RunsInfoRoot root = getRunsRoot(repoFullName, 1, workflowId);

        RunsInfo runsInfo = null;

        if(root.total_count > 0) {
            int page = root.total_count;
            RunsInfoRoot root2 = getRunsRoot(repoFullName, page, workflowId);
            runsInfo = root2.workflow_runs.get(0);
        }
        return runsInfo;
    }






    //////////////////////////////////////////////////////////////

    /**
     * Return information about runs root.  Important return the root to see the total of results.
     *
     * @param repoFullName
     * @param workflowId  if of a workflow. A project can have several workflows.
     * @return
     */
    private RunsInfoRoot getRunsRoot(String repoFullName, int page, long workflowId) {

        String parameters = "";

        ResponseEntity<RunsInfoRoot> result;

        if(queryParameters != null && ! queryParameters.isEmpty())
            parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
        else
            parameters = "?page="+page+"&per_page="+pageSize;

        String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/actions/workflows/"+workflowId+"/runs"+parameters;

        System.out.println("Getting Next run Info: "+query);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, RunsInfoRoot.class);

        return result.getBody();
    }



}
