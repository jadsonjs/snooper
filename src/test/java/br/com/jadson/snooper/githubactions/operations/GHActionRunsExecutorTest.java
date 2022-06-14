package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.githubactions.data.runs.RunsInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GHActionRunsExecutorTest {

    @Test
    void testRuns(){

        GHActionRunsExecutor runs = new GHActionRunsExecutor();
        runs.setPageSize(1);
        runs.setTestEnvironment(true);

        List<RunsInfo> list =  runs.runs("jadsonjs/snooper", 27792816);

        Assertions.assertTrue(list.size() > 0);
        Assertions.assertTrue(list.get(0).id > 0);

    }


    /**
     * REturn the last run of all workflows
     */
    @Test
    void testLastRuns(){

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        executor.setTestEnvironment(true);

        RunsInfo lastRunInfo =  executor.lastRun("jadsonjs/snooper");

        System.out.println("name: "+lastRunInfo.name);
        System.out.println("run_started_at: "+lastRunInfo.run_started_at);

        Assertions.assertTrue( lastRunInfo != null );
        Assertions.assertTrue(lastRunInfo.id > 0);

    }

    /**
     * REturn the last run of all workflows
     */
    @Test
    void testLastRunsRelevantProject(){

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        executor.setTestEnvironment(true);

        RunsInfo lastRunInfo =  executor.lastRun("vuejs/vue"); // java-diff-utils/java-diff-utils

        System.out.println("name: "+lastRunInfo.name);
        System.out.println("run_started_at: "+lastRunInfo.run_started_at);

        Assertions.assertTrue( lastRunInfo != null );
        Assertions.assertTrue(lastRunInfo.id > 0);

    }

    @Test
    void testLastRunsNotExist(){

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        executor.setTestEnvironment(true);

        RunsInfo lastRunInfo =  executor.lastRun("torvalds/linux");

        Assertions.assertTrue( lastRunInfo == null );

    }

}