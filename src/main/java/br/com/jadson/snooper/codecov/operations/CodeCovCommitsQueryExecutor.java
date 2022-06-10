package br.com.jadson.snooper.codecov.operations;

import br.com.jadson.snooper.codecov.data.CodeCovCommit;
import br.com.jadson.snooper.codecov.data.CodeCovRootInfo;
import br.com.jadson.snooper.utils.DateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Return information from Codecov
 *
 * Codecov is the leading code coverage reporting solution.
 * The company helps thousands of development teams confidently deliver stable and bug-free code with highly
 * integrated tools to surface coverage insights where they are needed.
 */
public class CodeCovCommitsQueryExecutor extends AbstractCodeCovQueryExecutor {


    /**
     * Commit return information about coverage associated with the commit
     *
     * @param projectName
     * @param base
     * @return
     */
    public List<CodeCovCommit> getAllCommits(String projectName, CODE_COV_BASE base){

        List<CodeCovCommit> commits = new ArrayList<>();

        ResponseEntity<CodeCovRootInfo> result;

        int page = 1;

        String parameters = "";

        CodeCovRootInfo codeCov = null;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"&page="+page+"&limit="+limit;
            else
                parameters = "?page="+page+"&limit="+limit;


            String query = CODE_COV_API_URL +"/"+ base.getName()+"/"+projectName+"/commit"+parameters;

            System.out.println("Getting Next CodeCov Commit: "+query);


            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            //headers.set("Authorization", "token "+CodeCovAPIToken+"");

            // allow '%' character in URL
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(query);
            URI uri = builder.build(false).toUri();

            HttpEntity entity = new HttpEntity<>(headers);
            result = restTemplate.exchange( uri, HttpMethod.GET, entity, CodeCovRootInfo.class);

            codeCov = result.getBody();
            commits.addAll(codeCov.commits);

            page++;

        }while ( result != null && codeCov.meta.status == 200 && codeCov.commits.size() > 0 );

        return commits;

    }

    /**
     * Get commits between specific dates
     *
     * @param projectName
     * @param base
     * @param init
     * @param end
     * @return
     */
    public List<CodeCovCommit> getCommits(String projectName, CODE_COV_BASE base, LocalDateTime init, LocalDateTime end){
        /**
         * from	any date and/or time string in yyyy-MM-dd HH:mm:ss format	Only query commits starting from this date
         * to	any date and/or time string in yyyy-MM-dd HH:mm:ss format	Only query commits up to this date
         */
        queryParameters = "from="+dateUtils.toString(init)+"&to="+dateUtils.toString(end);
        return getAllCommits(projectName, base);
    }


    public CodeCovCommitsQueryExecutor setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public CodeCovCommitsQueryExecutor setQueryParameters(String queryParameters) {
        this.queryParameters = queryParameters;
        return this;
    }
}
