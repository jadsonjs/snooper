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
 * br.com.jadson.snooper.github.operations
 * GitHubCommitExecutor
 * 26/01/21
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.association.AssociationCommitPullRequestInfo;
import br.com.jadson.snooper.github.data.association.PullRequestNodeInfo;
import br.com.jadson.snooper.github.data.association.graphql.AssociatedPullRequestsEdge;
import br.com.jadson.snooper.github.data.association.graphql.CommitNode;
import br.com.jadson.snooper.github.data.association.graphql.ResultGraphQLRepository;
import br.com.jadson.snooper.github.data.commit.FileChanged;
import br.com.jadson.snooper.github.data.commit.CommitInfo;
import br.com.jadson.snooper.github.data.stats.FileStats;
import br.com.jadson.snooper.github.data.stats.GitHubCommitStatsInfo;
import br.com.jadson.snooper.github.data.stats.graphql.CommitStatsNode;
import br.com.jadson.snooper.github.data.stats.graphql.GraphQLCommitResponse;
import br.com.jadson.snooper.github.data.stats.mapper.GitHubCommitStatsMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Executes queries of commits
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class CommitQueryExecutor extends AbstractGitHubQueryExecutor {


    /**
     * Return all commits of a project
     *
     * Get the commits between dates with the param: since=2021-03-01T22:26:45Z&until=2021-04-08T22:26:45Z&page=1&per_page=50
     *
     * @param repoFullName
     * @return
     */
    public List<CommitInfo> getCommits(String repoFullName) {

        validateRepoName(repoFullName);

        int page = 1;

        // IMPORTANTE state=all for bring all PR
        String parameters = "";

        List<CommitInfo> allPull = new ArrayList<>();

        ResponseEntity<CommitInfo[]> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/commits"+parameters;

            System.out.println("Getting Next Commit: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, CommitInfo[].class);

            allPull.addAll(  Arrays.asList(result.getBody()) );

            page++;


        }while ( result != null && result.getBody().length > 0   && !testEnvironment);

        return allPull;
    }



    /**
     * Return all commits of a reference (can be a branch or tag)
     *
     * More information at: https://docs.github.com/en/rest/commits/commits?apiVersion=2022-11-28#get-a-commit
     *
     * Exemple:
     * https://api.github.com/repos/traPtitech/traQ/commits/master?page=1&per_page=1
     *
     * @param repoFullName
     * @param ref a branch or a tag name
     * @return all commits form a branch or tag
     */
    public CommitInfo getCommitsOfReference(String repoFullName, String ref) {

        validateRepoName(repoFullName);

        int page = 1;

        String parameters = "";

        CommitInfo commitInfo = null;

        ResponseEntity<CommitInfo> result;

       // do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/commits"+"/"+ref+parameters;

            System.out.println("Getting Next Commit: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, CommitInfo.class);

            commitInfo = result.getBody();

            page++;


      //  }while ( result != null && result.getBody() != null   && !testEnvironment);

        return commitInfo;
    }


    /**
     * Return all commits associated with the PR
     *
     * @param repoFullName
     * @return
     */
    public List<CommitInfo> commitsOfPullRequest(String repoFullName, long prNumber) {

        validateRepoName(repoFullName);

        int page = 1;

        // IMPORTANTE state=all for bring all PR
        String parameters = "";

        List<CommitInfo> allPull = new ArrayList<>();

        ResponseEntity<CommitInfo[]> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/pulls/"+prNumber+"/commits"+parameters;

            System.out.println("Getting Next Commit of PULL REQUEST: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, CommitInfo[].class);

            allPull.addAll(  Arrays.asList(result.getBody()) );

            page++;


        }while ( result != null && result.getBody().length > 0   && !testEnvironment);

        return allPull;
    }








    /**
     * Return the history of commits of a project with the associated PULL REQUEST to this commits
     *
     * This method use the API V4 of github with GraphQL
     * @param projectFullName
     * @return
     */
    public List<AssociationCommitPullRequestInfo> getHistoryOfCommitsWithPullRequestsQuery(String projectFullName) {

        final int pageSize = 100;

        String afterPosition = "";

        List<AssociationCommitPullRequestInfo> results = new ArrayList<>();


        System.out.println(" executing AssociatedPullRequestsQuery for " + projectFullName + "  ");

        boolean hasNextPage = true;

        String names[] = projectFullName.split("/");

        String[] symbols = new String[]{"|", "/", "-", "|", "/", "-", "\\"};

        int index = 0;
        int symbolsIndex = 0;

        while (hasNextPage && index <= 100) {   // for all commits in project

            System.out.println(" executing next commit page  "+symbols[symbolsIndex]);
            symbolsIndex =  ( symbolsIndex == 6 ? 0 : symbolsIndex+1);

            /**
             * return the commit history for a project with associated pull requests to it.
             *
             * {
             *     "query":  " query {  repository(owner:\"octocat\", name:\"Hello-World\") { object(expression: \"master\") { ... on Commit { history(first:10) { pageInfo {  hasNextPage,  endCursor  },  nodes {  commitUrl  associatedPullRequests(first:20){ edges {  node {  id  number  } } } } }  } } }  } "
             * }
             */
            String query = "   " +
                    " repository(owner: " +"\\\""+ names[0] +"\\\""+ ", name: " +"\\\""+ names[1] +"\\\""+ ") { " +
                    "   object(expression: \\\"master\\\") { " +
                    "     ... on Commit { " +
                    "         history(first:" + pageSize +" "+afterPosition+") { " +
                    "             pageInfo {  " +
                    "                 hasNextPage,  " +
                    "                 endCursor  " +
                    "             },  " +
                    "             nodes {  " +
                    "                 commitUrl  " +   /// commmit hash
                    "                 associatedPullRequests(first:20){  " +
                    "                     edges {  " +
                    "                         node {  " +
                    "                             id  " +
                    "                             number  " +   // the number of PR of the commit
                    "                         } " +
                    "                     } " +
                    "                 } " +
                    "             } " +
                    "         }  " +
                    "     }  " +
                    "   } " +
                    " } " ;


            /**
             * Convert the result of query to a object simplification and return it
             */
            ResultGraphQLRepository queryResult = executeQueryAssociatedPR(query);

            if( queryResult != null && queryResult.data.repository.object != null ) {
                if(queryResult.data.repository.object.history != null) {
                    for (CommitNode commitNode : queryResult.data.repository.object.history.nodes) {
                        AssociationCommitPullRequestInfo association = new AssociationCommitPullRequestInfo();
                        association.commitUrl = commitNode.commitUrl;
                        for (AssociatedPullRequestsEdge a1 : commitNode.associatedPullRequests.edges) {
                            association.addPullRequestInfo(new PullRequestNodeInfo(a1.node.id, a1.node.number));
                        }
                        results.add(association);
                    }
                }
            }

            if ( queryResult == null || queryResult.data.repository.object == null || ! queryResult.data.repository.object.history.pageInfo.hasNextPage) {
                System.out.println("!!! End query has no more pages !!!");
                hasNextPage = false;
            }else {
                afterPosition = ", after:\\\"" + queryResult.data.repository.object.history.pageInfo.endCursor + "\\\" ";
                hasNextPage = true;
            }

            if(index == 100)
                System.out.println("!!! End query limit 100 requests achieved !!!");

            index++;

        } // while

        return results;
    }

    public List<GitHubCommitStatsInfo> getCommitsWithStats(String projectFullName, LocalDateTime sinceDate, LocalDateTime untilDate) {
        validateRepoName(projectFullName);

        List<GitHubCommitStatsInfo> allCommits = new ArrayList<>();
        String[] repoParts = projectFullName.split("/");
        String owner = repoParts[0];
        String repo = repoParts[1];

        String since = sinceDate.toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        String until = untilDate.plusDays(1).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

        String cursor = null;
        boolean hasNextPage = true;

        System.out.println("Executing getCommitsWithStats for " + projectFullName + " from " + since + " to " + until);

        while (hasNextPage) {
            String afterClause = (cursor == null) ? "" : ", after: \\\"" + cursor + "\\\"";

            String query = String.format(""+
                    "repository(owner: \\\"%s\\\", name: \\\"%s\\\") {"+
                    "  defaultBranchRef {"+
                    "    target {"+
                    "      ... on Commit {"+
                    "        history(since: \\\"%s\\\", until: \\\"%s\\\", first: 100%s) {"+
                    "          pageInfo {"+
                    "            endCursor"+
                    "            hasNextPage"+
                    "          }"+
                    "          nodes {"+
                    "            oid "+
                    "            id "+
                    "            url "+
                    "            comments { totalCount } "+
                    "            author { user { url login } name email date avatarUrl } "+
                    "            committer { user { url login } name email date } "+
                    "            additions "+
                    "            deletions "+
                    "            changedFiles: changedFilesIfAvailable "+
                    "            message "+
                    "          }"+
                    "        }"+
                    "      }"+
                    "    }"+
                    "  }"+
                    "}", owner, repo, since, until, afterClause);

            GraphQLCommitResponse queryResult = executeCommitStatsQuery(query);

            if (queryResult != null && queryResult.data != null && queryResult.data.repository != null &&
                    queryResult.data.repository.defaultBranchRef != null && queryResult.data.repository.defaultBranchRef.target != null &&
                    queryResult.data.repository.defaultBranchRef.target.history != null) {

                for (CommitStatsNode node : queryResult.data.repository.defaultBranchRef.target.history.nodes) {
                    GitHubCommitStatsInfo statsInfo = GitHubCommitStatsMapper.mapToCommitStatsInfo(node);
                    allCommits.add(statsInfo);
                }

                hasNextPage = queryResult.data.repository.defaultBranchRef.target.history.pageInfo.hasNextPage;
                cursor = queryResult.data.repository.defaultBranchRef.target.history.pageInfo.endCursor;
                System.out.println("Page fetched. Commits so far: " + allCommits.size() + ". Has next page: " + hasNextPage);

            } else {
                System.err.println("!!! No more data or error in GraphQL response. Stopping pagination. !!!");
                hasNextPage = false;
            }
        }

        return allCommits;
    }

    public List<FileChanged> getCommitFiles(String repoFullName, CommitInfo commit){
        validateRepoName(repoFullName);
        // IMPORTANTE state=all for bring all PR
        String parameters = "";
        ResponseEntity<CommitInfo> result;

        String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/commits/"+commit.sha;

        System.out.println("Getting files for commit");

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, CommitInfo.class);

        if (result.getBody() != null && result.getBody().files != null) {
            return result.getBody().files;
        }

        return new ArrayList<>();
    }

    public FileStats getFileStats(String projectFullName, String filePath, LocalDateTime sinceDate, LocalDateTime untilDate){
        validateRepoName(projectFullName);

        String[] repoParts = projectFullName.split("/");
        String owner = repoParts[0];
        String repo = repoParts[1];

        String since = sinceDate.toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        String until = untilDate.plusDays(1).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

        System.out.println("Executing getFileStats for " + filePath + " from " + since + " to " + until);

        String query = String.format(
                "repository(owner: \\\"%s\\\", name: \\\"%s\\\") { " +
                        "  defaultBranchRef { " +
                        "    target { " +
                        "      ... on Commit { " +
                        "        history(path: \\\"%s\\\", since: \\\"%s\\\", until: \\\"%s\\\") { " +
                        "          totalCount " +
                        "        } " +
                        "      } " +
                        "    } " +
                        "  } " +
                        "} ",
                owner, repo, filePath, since, until
        );

        GraphQLCommitResponse queryResult = executeCommitStatsQuery(query);

        FileStats fileStats = new FileStats();
        fileStats.churn = queryResult.data.repository.defaultBranchRef.target.history.totalCount;
        fileStats.path = filePath;

        return fileStats;

    }



    private ResultGraphQLRepository executeQueryAssociatedPR(String query)  {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+githubToken+"");
        headers.set("Accept", "application/json");

        String body =  " {    \"query\": \" query {  "+query+"   } \"   } " ;

        ObjectMapper objectMapper = new ObjectMapper();

        HttpEntity<String> request =  new HttpEntity<>(body, headers);

        String json = restTemplate.postForObject("https://api.github.com/graphql", request, String.class);

        ResultGraphQLRepository result = null;
        try {
            result = objectMapper.readValue(json, ResultGraphQLRepository.class);
        } catch (JsonProcessingException e) {
            System.err.println("--------------------JsonProcessingException----------------------------");
            e.printStackTrace();
            System.err.println("-----------------------------------------------------------------------");
        }
        return result;

    }

    private GraphQLCommitResponse executeCommitStatsQuery(String query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);
        headers.set("Accept", "application/json");

        String body = "{\"query\": \"query getCommitsByDateRange {" + query + "}\"}";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        String jsonResponse = restTemplate.postForObject("https://api.github.com/graphql", request, String.class);

        GraphQLCommitResponse result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(jsonResponse, GraphQLCommitResponse.class);
        } catch (JsonProcessingException e) {
            System.err.println("----------- JsonProcessingException while parsing commit stats -----------");
            System.err.println("Response JSON: " + jsonResponse);
            e.printStackTrace();
            System.err.println("--------------------------------------------------------------------------");
        }
        return result;
    }

}


