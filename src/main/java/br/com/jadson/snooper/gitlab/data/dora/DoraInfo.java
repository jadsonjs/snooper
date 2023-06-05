package br.com.jadson.snooper.gitlab.data.dora;

/**
 * https://docs.gitlab.com/ee/api/dora/metrics.html
 * You can also retrieve DORA metrics with the GraphQL API.
 * All methods require at least the Reporter role
 *
 * Example response:
 *
 * [
 *   { "date": "2021-03-01", "value": 3 },
 *   { "date": "2021-03-02", "value": 6 },
 *   { "date": "2021-03-03", "value": 0 },
 *   { "date": "2021-03-04", "value": 0 },
 *   { "date": "2021-03-05", "value": 0 },
 *   { "date": "2021-03-06", "value": 0 },
 *   { "date": "2021-03-07", "value": 0 },
 *   { "date": "2021-03-08", "value": 4 }
 * ]
 */
public class DoraInfo {
    public String date;
    public int value;
}
