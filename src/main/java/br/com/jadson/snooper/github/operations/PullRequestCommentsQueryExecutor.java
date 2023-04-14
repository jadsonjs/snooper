package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.comments.GithubCommentsInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This returns the Pull request review comments
 *
 * Pull request review comments are comments made on a portion of the unified diff during a pull request review. These are different from commit comments and issue comments in a pull request.
 */
public class PullRequestCommentsQueryExecutor extends AbstractGitHubQueryExecutor{


    public PullRequestCommentsQueryExecutor(){ }

    public PullRequestCommentsQueryExecutor(String githubToken){
        super(githubToken);
    }


    /**
     * Return all PR review comments of a repository
     *
     * curl \
     *   -H "Accept: application/vnd.github+json" \
     *   -H "Authorization: Bearer <YOUR-TOKEN>" \
     *   https://api.github.com/repos/OWNER/REPO/pulls/comments
     *
     * @param repoFullName
     * @return
     */
    public List<GithubCommentsInfo> getPullCommentsInfo(String repoFullName) {

        int page = 1;

        List<GithubCommentsInfo> allComments = new ArrayList<>();

        ResponseEntity<GithubCommentsInfo[]> result;

        do {

            /**
             * GET ***REVIEWS*** COMMENTS
             *
             * https://stackoverflow.com/questions/16198351/get-list-of-comments-from-github-pull-request
             *
             * curl \
             *   -H "Accept: application/vnd.github+json" \
             *   -H "Authorization: Bearer <YOUR-TOKEN>" \
             *   https://api.github.com/repos/OWNER/REPO/pulls/PULL_NUMBER/comments
             */
            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/pulls/comments"+"?page="+page+"&per_page="+pageSize;

            System.out.println("Getting Review Comments of PR: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, GithubCommentsInfo[].class);

            allComments.addAll(  Arrays.asList(result.getBody()) );

            page++;


        }while ( result != null && result.getBody().length > 0 && ! testEnvironment);


        return allComments;
    }

    /**
     * Return all PR review comments of a PR
     *
     * @param repoFullName
     * @return
     */
    public List<GithubCommentsInfo> getPullCommentsInfo(String repoFullName, long prNumber) {

        int page = 1;

        List<GithubCommentsInfo> allComments = new ArrayList<>();

        ResponseEntity<GithubCommentsInfo[]> result;

        do {

            /**
             * GET ***REVIEWS*** COMMENTS
             *
             * https://stackoverflow.com/questions/16198351/get-list-of-comments-from-github-pull-request
             *
             * curl \
             *   -H "Accept: application/vnd.github+json" \
             *   -H "Authorization: Bearer <YOUR-TOKEN>" \
             *   https://api.github.com/repos/OWNER/REPO/pulls/PULL_NUMBER/comments
             */
            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/pulls/"+prNumber+"/comments"+"?page="+page+"&per_page="+pageSize;

            System.out.println("Getting Review Comments of PR: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, GithubCommentsInfo[].class);

            allComments.addAll(  Arrays.asList(result.getBody()) );

            page++;


        }while ( result != null && result.getBody().length > 0 && ! testEnvironment);


        return allComments;

    }

}
