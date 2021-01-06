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
    }
}