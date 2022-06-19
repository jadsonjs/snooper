package br.com.jadson.snooper.travisci.operations;

import br.com.jadson.snooper.travisci.data.repo.TravisRepoInfo;
import br.com.jadson.snooper.travisci.data.repo.TravisRepoRoot;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TravisCIRepositoryQueryExecutor extends AbstractTravisCIQueryExecutor {

    /**
     * Return information about a repository on travis CI
     *
     * https://docs.travis-ci.com/api/?http#repositories
     *
     * @param projectNameWithOwner
     * @return
     */
    public TravisRepoInfo getRepoInfo(String projectNameWithOwner ) {

        String searchUrl = TRAVIS_CI_API_URL+"/repos/" + projectNameWithOwner;

        System.out.println("Getting Next Travis Repo Info: "+searchUrl);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        ResponseEntity<TravisRepoRoot> result = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, TravisRepoRoot.class);

        return result.getBody().repo;
    }
}
