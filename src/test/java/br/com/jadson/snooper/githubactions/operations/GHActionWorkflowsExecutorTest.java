package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.githubactions.data.workflow.WorkflowInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GHActionWorkflowsExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");


    @Test
    void testWorkFlow(){

        GHActionWorkflowsExecutor executor = new GHActionWorkflowsExecutor();
        executor.setPageSize(1);
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);

        List<WorkflowInfo> list =  executor.getWorkflows("jadsonjs/snooper");

        Assertions.assertTrue(list.size() > 0);
        Assertions.assertTrue(list.get(0).id > 0);

    }

    @Test
    void testWorkFlowRealProject(){

        GHActionWorkflowsExecutor executor = new GHActionWorkflowsExecutor();
        executor.setPageSize(100);
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);

        List<WorkflowInfo> list =  executor.getWorkflows("amplication/amplication");

        System.out.println(list.size());

        Assertions.assertTrue(list.size() > 0);
        Assertions.assertTrue(list.get(0).id > 0);

    }

}