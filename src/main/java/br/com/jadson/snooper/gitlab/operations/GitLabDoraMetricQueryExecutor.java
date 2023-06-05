package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.gitlab.data.dora.DoraInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Return the DORA metrics for a project
 *
 * Not working yet.
 * 401Unauthorized "You do not have permission to access dora metrics."
 *
 * https://stackoverflow.com/questions/76382478/permission-denied-for-gitlab-dora-metrics-through-api
 */
@Deprecated
public class GitLabDoraMetricQueryExecutor extends AbstractGitLabQueryExecutor{


    /**
     * How deployment frequency is calculated
     *
     * In GitLab, Deployment frequency is measured by the average number of deployments per day to a given environment, based on the deployment’s
     * end time (its finished_at property). GitLab calculates the deployment frequency from the number of finished deployments on the given day.
     *
     * The calculation takes into account the production environment tier or the environments named production/prod.
     * The environment must be part of the production deployment tier for its deployment information to appear on the graphs
     * @param repoFullName
     * @return
     */
    public List<DoraInfo> deploymentFrequency(String repoFullName) {
        this.setQueryParameters(new String[]{"metric=deployment_frequency"});
        return doraMetric(repoFullName);
    }


    /**
     * How lead time for changes is calculated
     *
     * GitLab calculates Lead time for changes base on the number of seconds to successfully deliver a commit into production - from
     * code committed to code successfully running in production, without adding the coding_time to the calculation
     * @param repoFullName
     * @return
     */
    public List<DoraInfo> leadTimeForChanges(String repoFullName) {
        this.setQueryParameters(new String[]{"metric=lead_time_for_changes"});
        return doraMetric(repoFullName);
    }


    /**
     * How time to restore service is calculated
     *
     * In GitLab, Time to restore service is measured as the median time an incident was open for on a production environment.
     * GitLab calculates the number of seconds an incident was open on a production environment in the given time period. This assumes:
     *
     *     GitLab incidents are tracked.
     *     All incidents are related to a production environment.
     *     Incidents and deployments have a strictly one-to-one relationship.
     *     An incident is related to only one production deployment, and any production
     *     deployment is related to no more than one incident
     * @param repoFullName
     * @return
     */
    public List<DoraInfo> timeToRestoreService(String repoFullName) {
        this.setQueryParameters(new String[]{"metric=time_to_restore_service"});
        return doraMetric(repoFullName);
    }


    /**
     * How change failure rate is calculated
     *
     * In GitLab, Change failure rate is measured as the percentage of deployments that cause an incident in production
     * in the given time period. GitLab calculates this by the number of incidents divided by the number of deployments to a production environment. This assumes:
     *
     *     GitLab incidents are tracked.
     *     All incidents are related to a production environment.
     *     Incidents and deployments have a strictly one-to-one relationship.
     *     An incident is related to only one production deployment, and any production deployment is related to no more
     *     than one incident.
     * @param repoFullName
     * @return
     */
    public List<DoraInfo> changeFailureRate(String repoFullName) {
        this.setQueryParameters(new String[]{"metric=change_failure_rate"});
        return doraMetric(repoFullName);
    }


    /**
     * Get DORA metric of a project by the QueryParameters defined.
     * @param repoFullName
     * @return
     */
    private List<DoraInfo> doraMetric(String repoFullName) {
        int page = 1;
        String parameters = "";
        ArrayList all = new ArrayList();

        ResponseEntity result;
        do {
            if (this.queryParameters != null && !this.queryParameters.isEmpty()) {
                parameters = "?" + this.queryParameters + "page=" + page + "&per_page=" + this.pageSize;
            } else {
                parameters = "?page=" + page + "&per_page=" + this.pageSize;
            }

            String query = getGitLabAPIURL() + encodeProjectName(repoFullName) + "/dora/metrics" + parameters;

            // encode "/" in "%2F" supported
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            System.out.println("Getting Next Dora Metric: " + query);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity entity = new HttpEntity(this.getDefaultHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, entity, DoraInfo[].class);
            all.addAll(Arrays.asList((DoraInfo[])result.getBody()));
            ++page;
        } while(result != null && ((DoraInfo[])result.getBody()).length > 0 && !this.testEnvironment);

        return all;
    }

}
