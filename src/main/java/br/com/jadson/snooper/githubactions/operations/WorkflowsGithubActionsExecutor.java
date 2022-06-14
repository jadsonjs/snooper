package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.github.operations.AbstractGitHubQueryExecutor;
import br.com.jadson.snooper.githubactions.data.workflow.WorkflowInfo;
import br.com.jadson.snooper.githubactions.data.workflow.WorkflowInfoRoot;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class collect data about workflows on Github Actions
 *
 * @Author Jadson Santos - jadsonjs@gmail.com
 */
public class WorkflowsGithubActionsExecutor extends AbstractGitHubQueryExecutor {

    public WorkflowsGithubActionsExecutor(){ }

    public WorkflowsGithubActionsExecutor(String githubToken){
        super(githubToken);
    }


    /**
     * Return all workflows of a project.
     *
     * If workflows.lenght == 0, project do not use GH Actions.
     *
     * @param repoFullName
     * @return
     */
    public List<WorkflowInfo> getWorkflows(String repoFullName) {

        validateRepoName(repoFullName);

        int page = 1;

        String parameters = "";

        List<WorkflowInfo> all = new ArrayList<>();

        ResponseEntity<WorkflowInfoRoot> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/actions/workflows"+parameters;

            System.out.println("Getting Next workflow Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, WorkflowInfoRoot.class);

            all.addAll(  result.getBody().workflows );

            page++;


        }while ( result != null && result.getBody().workflows.size() > 0   && !testEnvironment);

        return all;
    }
}
