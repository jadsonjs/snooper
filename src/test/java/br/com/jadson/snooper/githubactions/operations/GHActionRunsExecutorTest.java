package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.githubactions.data.runs.WorkflowRunsInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GHActionRunsExecutorTest {

    @Test
    void testRuns(){

        GHActionRunsExecutor runs = new GHActionRunsExecutor();
        runs.setPageSize(1);
        runs.setTestEnvironment(true);

        List<WorkflowRunsInfo> list =  runs.runs("jadsonjs/snooper", 27792816);

        Assertions.assertTrue(list.size() > 0);
        Assertions.assertTrue(list.get(0).id > 0);

    }

}