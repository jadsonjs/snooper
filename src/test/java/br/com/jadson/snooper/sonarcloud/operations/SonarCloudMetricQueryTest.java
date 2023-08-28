package br.com.jadson.snooper.sonarcloud.operations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SonarCloudMetricQueryTest {

    /**
     * Simple metric for the Sonar cloud API
     */
    @Test
    @Disabled
    void getMetricsTest() {
        SonarMetricsQueryExecutor query = new SonarMetricsQueryExecutor();
        query.setTestEnvironment(true);
        Assertions.assertTrue(query.searchMetrics().size() > 0 );
    }
}