package br.com.jadson.snooper.sonarcloud.operations;

import br.com.jadson.snooper.sonarcloud.data.history.SonarHistoryEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SonarCloudMetricHistoryQueryExecutorTest {

    @Test
    void getProjectMetricHistoryTest() {

        SonarCloudMetricHistoryQueryExecutor query = new SonarCloudMetricHistoryQueryExecutor();
        query.setTestEnvironment(true);
        LocalDateTime from = LocalDateTime.of(2021,01,05,00,00,00);
        LocalDateTime to = LocalDateTime.of(2021,01,06,23,59,59);
        List<SonarHistoryEntry> historyEntries = query.getProjectMetricHistory("simgrid_simgrid", "coverage", from, to);
        Assertions.assertTrue(  historyEntries.size() == 2);
        Assertions.assertEquals("81.8", historyEntries.get(0).value);
        Assertions.assertEquals("81.7", historyEntries.get(1).value);
    }


    @Test
    void getProjectMetricHistoryTest2() {

        SonarCloudMetricHistoryQueryExecutor query = new SonarCloudMetricHistoryQueryExecutor();
        query.setTestEnvironment(true);
        LocalDateTime from = LocalDateTime.of(2020,11,12,13,46,11);
        LocalDateTime to = LocalDateTime.of(2020,11,12,17,50,05);
        List<SonarHistoryEntry> historyEntries = query.getProjectMetricHistory("maibornwolff-gmbh_codecharta_visualization", "coverage", from, to);
        Assertions.assertTrue(  historyEntries.size() == 0 );
    }
}