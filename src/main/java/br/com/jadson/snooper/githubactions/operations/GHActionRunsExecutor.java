package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.github.operations.AbstractGitHubQueryExecutor;
import br.com.jadson.snooper.githubactions.data.runs.WorkflowRunsInfo;
import br.com.jadson.snooper.githubactions.data.runs.WorkflowRunsInfoRoot;
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
     * Return all runs of a workflow
     *
     * @param repoFullName
     * @param workflowId  if of a workflow. A project can have several workflows.
     * @return
     */
    public List<WorkflowRunsInfo> runs(String repoFullName, long workflowId) {

        validateRepoName(repoFullName);

        int page = 1;

        String parameters = "";

        List<WorkflowRunsInfo> all = new ArrayList<>();

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
}
