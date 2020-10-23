package br.com.jadson.snooper.sonarcloud.operations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SonarCloudMetricQueryTest {

    /**
     * Simple metric for the Sonar cloud API
     */
    @Test
    void getMetricsTest() {
        SonarCloudMetricQuery query = new SonarCloudMetricQuery();
        Assertions.assertTrue(query.getMetrics().size() > 0 );
    }
}