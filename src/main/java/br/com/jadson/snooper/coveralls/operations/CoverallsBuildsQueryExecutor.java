package br.com.jadson.snooper.coveralls.operations;

import br.com.jadson.snooper.coveralls.data.CoverallsBuildInfo;
import br.com.jadson.snooper.coveralls.data.CoverallsBuildRootInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Return information of build in the CoveAll, build is the struct that have caverage information.
 */
public class CoverallsBuildsQueryExecutor extends AbstractCoverallsQueryExecutor {

    public CoverallsBuildsQueryExecutor(){ }

    public CoverallsBuildsQueryExecutor(String token){
        if(token == null || token.trim().equals(""))
            throw new RuntimeException("Invalid Token: "+token);

        this.token = token;
    }


    /**
     * Return information about build of a project on coverall
     * @param projectName
     * @param service
     * @return
     */
    public List<CoverallsBuildInfo> getBuildsInfo(String projectName, CODE_ALL_SERVICE service){

        int page = 1;

        String parameters = "";

        List<CoverallsBuildInfo> all = new ArrayList<>();

        ResponseEntity<CoverallsBuildRootInfo> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page; //+"&per_page="+pageSize; there is no support to pagesize :(
            else
                parameters = "?page="+page;// +"&per_page="+pageSize; there is no support to pagesize :(

            /****************************************************************************
             * https://docs.coveralls.io/api-introduction
             *
             * https://coveralls.io/github/lemurheavy/coveralls-ruby.json?page=1
             * (get 10 builds at a time)
             ***************************************************************************/
            String query = CODE_ALL_API_URL+"/"+ service.getValue()+"/"+projectName+".json"+parameters;

            System.out.println("Getting Next workflow Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, CoverallsBuildRootInfo.class);

            all.addAll(  result.getBody().builds );

            page++;


        }while ( result != null && result.getBody().builds.size() > 0   && !testEnvironment);

        return all;

    }

    /**
     * Get information of last build on coveall
     * @param projectName
     * @param service
     * @return
     */
    public CoverallsBuildInfo getLastBuildsInfo(String projectName, CODE_ALL_SERVICE service){

        ResponseEntity<CoverallsBuildInfo> result;

        try {

            /****************************************************************************
             * https://docs.coveralls.io/api-introduction
             *
             * https://coveralls.io/github/lemurheavy/coveralls-ruby.json
             * (latest build)
             ***************************************************************************/
            String query = CODE_ALL_API_URL + "/" + service.getValue() + "/" + projectName + ".json";

            System.out.println("Getting Next workflow Info: " + query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange(query, HttpMethod.GET, entity, CoverallsBuildInfo.class);

            return result.getBody();

        }catch (Exception ex){
            return null;
        }

    }
}
