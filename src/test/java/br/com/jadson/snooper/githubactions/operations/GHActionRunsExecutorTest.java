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

    @Test
    void testLastRunsWithOutStartDate(){

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        executor.setTestEnvironment(true);

        RunsInfo lastRunInfo =  executor.lastRun("PGMDev/PGM");

        System.out.println("name: "+lastRunInfo.name);
        System.out.println("created_at: "+lastRunInfo.created_at);
        System.out.println("run_started_at: "+lastRunInfo.run_started_at);

        Assertions.assertTrue( lastRunInfo != null );
        Assertions.assertTrue(lastRunInfo.id > 0);
        Assertions.assertTrue(lastRunInfo.created_at != null);

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


    @Test
    void testFirstRuns(){

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        executor.setTestEnvironment(true);

        RunsInfo firstRunInfo =  executor.firstRun("jadsonjs/snooper");

        System.out.println("name: "+firstRunInfo.name);
        System.out.println("run_started_at: "+firstRunInfo.run_started_at);

        Assertions.assertTrue( firstRunInfo != null );
        Assertions.assertTrue(firstRunInfo.id > 0);

    }

    /**
     * workflow 455721  "created_at": "2020-01-28T19:37:11Z"
     * workflow 1446  "created_at": *** "2019-12-19T01:00:22Z", ***
     */
    @Test
    void testFirstRunsWithOutRunDate(){

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        executor.setTestEnvironment(true);

        RunsInfo firstRunInfo =  executor.firstRun("PGMDev/PGM");

        System.out.println("name: "+firstRunInfo.name);
        System.out.println("created_at: "+firstRunInfo.created_at);
        System.out.println("run_started_at: "+firstRunInfo.run_started_at);

        Assertions.assertTrue( firstRunInfo != null );
        Assertions.assertTrue(firstRunInfo.id > 0);
        Assertions.assertTrue(firstRunInfo.created_at != null);

    }



}