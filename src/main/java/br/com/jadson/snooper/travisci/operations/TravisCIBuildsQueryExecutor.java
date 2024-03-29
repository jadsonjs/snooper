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


import br.com.jadson.snooper.travisci.data.builds.TravisBuildsInfo;
import br.com.jadson.snooper.travisci.data.builds.TravisBuildsRoot;
import br.com.jadson.snooper.utils.DateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Recovery data from build on travis CI
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class TravisCIBuildsQueryExecutor extends AbstractTravisCIQueryExecutor {





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
    public List<TravisBuildsInfo> getBuilds(String projectNameWithOwner) {

        Integer afterBuildNumber = null;

        List<TravisBuildsInfo> result = new ArrayList<>();
        List<TravisBuildsInfo> tempResult = new ArrayList<>();

        if(testEnvironment)
            afterBuildNumber = 2; // just last 2 builds

        do {

            String searchUrl = TRAVIS_CI_API_URL + "/repos/" + projectNameWithOwner + "/builds" + (afterBuildNumber != null ? "?after_number=" + afterBuildNumber : "" );

            System.out.println("Getting Next Travis Build Info: "+searchUrl);

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
     * Return builds between dates
     *
     * @param projectNameWithOwner
     * @param start
     * @param end
     * @return
     */
    public List<TravisBuildsInfo> getBuilds(String projectNameWithOwner, LocalDateTime start, LocalDateTime end) {

        List<TravisBuildsInfo> builds = new ArrayList<>();

        List<TravisBuildsInfo> allBuilds = getBuilds(projectNameWithOwner);

        DateUtils dateUtils = new DateUtils();

        for (TravisBuildsInfo build : allBuilds) {

            if(build.started_at != null) {

                LocalDateTime startBuild = LocalDateTime.parse(build.started_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH));

                if(dateUtils.isBetweenDates(startBuild, start, end)){ // this build is between dates

                    builds.add(build);
                }
            }
        }

        return builds;
    }


    /**
     * Checks is a project has a number minimum of builds
     *
     *
     * @param projectNameWithOwner
     * @return
     */
    public boolean hasMinimumBuilds(String projectNameWithOwner, final int limit) {

        Integer afterBuildNumber = null;

        List<TravisBuildsInfo> result = new ArrayList<>();
        List<TravisBuildsInfo> tempResult = new ArrayList<>();

        if(testEnvironment)
            afterBuildNumber = 2; // just last 2 builds

        System.out.println("Counting Builds for : "+projectNameWithOwner);

        long totalbuilds = 0l;

        do {

            String searchUrl = TRAVIS_CI_API_URL + "/repos/" + projectNameWithOwner + "/builds" + (afterBuildNumber != null ? "?after_number=" + afterBuildNumber : "" );

            System.out.println("Getting Next Travis Build Info: "+searchUrl);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            ResponseEntity<TravisBuildsRoot> resp = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, TravisBuildsRoot.class);

            totalbuilds += Arrays.asList(resp.getBody()).size();

            tempResult = resp.getBody().builds;
            totalbuilds += tempResult.size();

            // the last result has the last build number. paga size = 50
            // https://stackoverflow.com/questions/34277366/how-to-list-all-builds-of-a-given-project-through-travis-api
            if(tempResult.size() > 0 ) {
                result.addAll(tempResult);
                afterBuildNumber = tempResult.get(tempResult.size()-1).number;
            }

        }while (tempResult.size() > 0 && ! testEnvironment && totalbuilds < limit );

        System.out.println("project: "+projectNameWithOwner+" total of builds: "+totalbuilds);

        return totalbuilds >= limit;
    }





    /**
     * Return information about the first builds of project. Build with the number 1. https://developer.travis-ci.com/
     *
     * Example: https://api.travis-ci.org/repos/JodaOrg/joda-time/builds?after_number=2
     *
     * where JodaOrg/joda-time is the github project user with name
     *
     * @param projectNameWithOwner
     * @return
     */
    public TravisBuildsInfo getFirstBuild(String projectNameWithOwner) {


        TravisBuildsInfo firstBuild = null;

        Integer  afterBuildNumber = 2; // just last 1 build

        String searchUrl = TRAVIS_CI_API_URL + "/repos/" + projectNameWithOwner + "/builds" + (afterBuildNumber != null ? "?after_number=" + afterBuildNumber : "" );

        System.out.println("Getting Next Travis Build Info: "+searchUrl);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        ResponseEntity<TravisBuildsRoot> resp = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, TravisBuildsRoot.class);

        if(resp.getBody().builds.size() > 0){
            firstBuild = resp.getBody().builds.get(0);
        }


        return firstBuild;
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
