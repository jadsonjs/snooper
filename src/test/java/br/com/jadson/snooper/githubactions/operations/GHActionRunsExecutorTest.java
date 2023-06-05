package br.com.jadson.snooper.githubactions.operations;

import br.com.jadson.snooper.githubactions.data.runs.RunsInfo;
import br.com.jadson.snooper.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class GHActionRunsExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");


    @Test
    void testAllRunsRepository(){

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        LocalDateTime startCIDate = LocalDateTime.of(2022, 7, 1, 0, 0, 0);
        LocalDateTime endCIDate = LocalDateTime.of(2022, 7, 30, 23, 59, 59);
        // created=2022-07-01..2022-07-30
        executor.setQueryParameters(new String[]{ "created=" + new DateUtils().toIso8601(startCIDate)+".."+new DateUtils().toIso8601(endCIDate) });
        executor.setPageSize(10);
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);

        List<RunsInfo> list =  executor.runs("jadsonjs/snooper");

        Assertions.assertTrue(list.size() == 4);

    }

    @Test
    void testRuns(){

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        executor.setPageSize(1);
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);

        List<RunsInfo> list =  executor.runs("jadsonjs/snooper", 27792816);

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
        executor.setGithubToken(token);

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
        executor.setGithubToken(token);

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
        executor.setGithubToken(token);

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
        executor.setGithubToken(token);

        RunsInfo lastRunInfo =  executor.lastRun("torvalds/linux");

        Assertions.assertTrue( lastRunInfo == null );

    }


    @Test
    void testFirstRuns(){

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);

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
        executor.setGithubToken(token);

        RunsInfo firstRunInfo =  executor.firstRun("jadsonjs/snooper");

        System.out.println("name: "+firstRunInfo.name);
        System.out.println("created_at: "+firstRunInfo.created_at);
        System.out.println("run_started_at: "+firstRunInfo.run_started_at);

        Assertions.assertTrue( firstRunInfo != null );
        Assertions.assertTrue(firstRunInfo.id > 0);
        Assertions.assertTrue(firstRunInfo.created_at != null);

    }


    // new exist run
//    @Test
//    void firstRunNotExistTest() {
//
//        GHActionRunsExecutor executor = new GHActionRunsExecutor();
//        executor.setTestEnvironment(true);
//        executor.setGithubToken(token);
//
//        RunsInfo firstRunInfo =  executor.firstRun("amplication/amplication", 4497880);
//
//        System.out.println("firstRunInfo: "+firstRunInfo);
//
//        Assertions.assertTrue( firstRunInfo == null );
//    }

    @Test
    void firstRunNotExistTest2() {

        GHActionRunsExecutor executor = new GHActionRunsExecutor();
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);

        RunsInfo firstRunInfo =  executor.firstRun("amplication/amplication");

        System.out.println("firstRunInfo: "+firstRunInfo);

        Assertions.assertTrue( firstRunInfo != null );
    }

}