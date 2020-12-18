/*
 * Federal University of Rio Grande do Norte
 * Department of Informatics and Applied Mathematics
 * Collaborative & Automated Software Engineering (CASE) Research Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 * snooper
 * br.com.jadson.snooper.travisci.operations
 * TravisCIBuildDuration
 * 29/09/20
 */
package br.com.jadson.snooper.travisci.operations;


import br.com.jadson.snooper.travisci.data.builds.TravisBuildsRoot;
import br.com.jadson.snooper.travisci.data.builds.TravisBuildsInfo;
import br.com.jadson.snooper.travisci.data.repo.TravisRepoInfo;
import br.com.jadson.snooper.travisci.data.repo.TravisRepoRoot;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Recovery data from build on travis CI
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class TravisCIQueryExecutor extends AbstractTravisCIQueryExecutor {


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

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        ResponseEntity<TravisRepoRoot> result = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, TravisRepoRoot.class);

        return result.getBody().repo;
    }


    /**
     * Return information about builds of projects. https://developer.travis-ci.com/
     *
     * Example: https://api.travis-ci.org/repos/JodaOrg/joda-time/builds
     *
     * where JodaOrg/joda-time is the github project user with name
     *
     * @param projectNameWithOwner
     * @return
     */
    public List<TravisBuildsInfo> getBuildsInfo(String projectNameWithOwner) {

        Integer afterBuildNumber = null;

        List<TravisBuildsInfo> result = new ArrayList<>();
        List<TravisBuildsInfo> tempResult = new ArrayList<>();

        if(testEnvironment)
            afterBuildNumber = 2; // just last 2 builds

        do {

            String searchUrl = TRAVIS_CI_API_URL + "/repos/" + projectNameWithOwner + "/builds" + (afterBuildNumber != null ? "?after_number=" + afterBuildNumber : "" );

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            ResponseEntity<TravisBuildsRoot> resp = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, TravisBuildsRoot.class);

            tempResult = resp.getBody().builds;

            // the last result has the last build number. paga size = 50
            // https://stackoverflow.com/questions/34277366/how-to-list-all-builds-of-a-given-project-through-travis-api
            if(tempResult.size() > 0 ) {
                result.addAll(tempResult);
                afterBuildNumber = tempResult.get(tempResult.size()-1).number;
            }

        }while (tempResult.size() > 0 && ! testEnvironment);

        return result;
    }




    /**
     * Return build form specific release of a system by the date of build
     *
     * @param projectNameWithOwner
     * @return
     */
    public List<TravisBuildsInfo> getBuildsOfRelease(String projectNameWithOwner, LocalDateTime startRelease, LocalDateTime endRelease) {

        List<TravisBuildsInfo> buildsOfRelease = new ArrayList<>();

        List<TravisBuildsInfo> builds = getBuildsInfo(projectNameWithOwner);

        for (TravisBuildsInfo build : builds) {

            LocalDateTime startBuild = LocalDateTime.parse(build.started_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH));

            if( startBuild.isAfter(startRelease) && startBuild.isBefore(endRelease) ){ // this build if of this relese
                buildsOfRelease.add(build);
            }
        }

        return buildsOfRelease;
    }



//    /**
//     * Return build information on travis api v3.
//     *
//     * @param projectNameWithOwner
//     * @return
//     */
//    public List<TravisBuildsInfo> getBuildsV3(String projectNameWithOwner, Integer afterBuildNumber) {
//
//        List<TravisBuildsInfo> releaseBuilds = new ArrayList<>();
//
//        String searchUrl = TRAVIS_CI_API_URL+"/repos/" + projectNameWithOwner + "/builds" + (afterBuildNumber != null ? "?after_number="+afterBuildNumber : " ");
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = getDefaultHeaders();
//        setTravisAPIToken(headers);
//        headers.set("Travis-API-Version", "3");
//
//        HttpEntity entity = new HttpEntity(headers);
//
//        ResponseEntity<TravisBuildsRoot> result = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, TravisBuildsRoot.class);
//
//        TravisBuildsRoot root = result.getBody();
//
//        return root.builds;
//    }


//    public void build(){
//
//        String query = TRAVIS_CI_API_URL +"/repo/{provider}/{repository.id}/builds";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept", "application/json");
//        headers.set("Travis-API-Version", "3");
//        if(! travisAPIToken.isEmpty())
//            headers.set("Authorization", "token "+travisAPIToken+"");
//
//        HttpEntity entity = new HttpEntity<>(headers);
//
//        restTemplate.exchange( query, HttpMethod.GET, entity, String.class);
//
//    }



}
