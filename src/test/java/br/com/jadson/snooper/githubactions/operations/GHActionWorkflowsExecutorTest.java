package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.githubactions.data.workflow.WorkflowInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

class GHActionWorkflowsExecutorTest {

    @Test
    void testWorkFlow(){

        GHActionWorkflowsExecutor executor = new GHActionWorkflowsExecutor();
        executor.setPageSize(1);
        executor.setTestEnvironment(true);

        List<WorkflowInfo> list =  executor.getWorkflows("jadsonjs/snooper");

        Assertions.assertTrue(list.size() > 0);
        Assertions.assertTrue(list.get(0).id > 0);

    }

    @Disabled // 403 rate limit exceeded
    @Test
    void testWorkFlowRealProject(){

        GHActionWorkflowsExecutor executor = new GHActionWorkflowsExecutor();
        executor.setPageSize(100);
        executor.setTestEnvironment(true);

        List<WorkflowInfo> list =  executor.getWorkflows("amplication/amplication");

        System.out.println(list.size());

        Assertions.assertTrue(list.size() > 0);
        Assertions.assertTrue(list.get(0).id > 0);

    }

}