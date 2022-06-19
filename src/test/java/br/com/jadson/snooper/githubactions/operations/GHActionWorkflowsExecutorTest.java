package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.githubactions.data.workflow.WorkflowInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GHActionWorkflowsExecutorTest {

    @Test
    void testWorkFlow(){

        GHActionWorkflowsExecutor workFlows = new GHActionWorkflowsExecutor();
        workFlows.setPageSize(1);
        workFlows.setTestEnvironment(true);

        List<WorkflowInfo> list =  workFlows.getWorkflows("jadsonjs/snooper");

        Assertions.assertTrue(list.size() > 0);
        Assertions.assertTrue(list.get(0).id > 0);

    }

}