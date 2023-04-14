package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.LabelInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Labels API supports managing labels for a repository and adding or removing labels to issues and pull requests.
 * Every pull request is an issue, but not every issue is a pull request. For this reason, "shared" actions for both
 * features, like managing assignees, labels, and milestones, are provided within Issues API.
 */
public class LabelQueryExecutor extends AbstractGitHubQueryExecutor{


    public LabelQueryExecutor(){ }

    public LabelQueryExecutor(String githubToken){
        super(githubToken);
    }


    /**
     * Return all labels of a repository
     *
     * curl \
     *   -H "Accept: application/vnd.github+json" \
     *   -H "Authorization: Bearer <YOUR-TOKEN>" \
     *   https://api.github.com/repos/OWNER/REPO/labels
     *
     * @param repoFullName
     * @return
     *
     * [
     *   {
     *     "id": 208045946,
     *     "node_id": "MDU6TGFiZWwyMDgwNDU5NDY=",
     *     "url": "https://api.github.com/repos/octocat/Hello-World/labels/bug",
     *     "name": "bug",
     *     "description": "Something isn't working",
     *     "color": "f29513",
     *     "default": true
     *   },
     *   {
     *     "id": 208045947,
     *     "node_id": "MDU6TGFiZWwyMDgwNDU5NDc=",
     *     "url": "https://api.github.com/repos/octocat/Hello-World/labels/enhancement",
     *     "name": "enhancement",
     *     "description": "New feature or request",
     *     "color": "a2eeef",
     *     "default": false
     *   }
     * ]
     */
    public List<LabelInfo> labels(String repoFullName) {

        validateRepoName(repoFullName);

        int page = 1;

        String parameters = "";

        List<LabelInfo> all = new ArrayList<>();

        ResponseEntity<LabelInfo[]> result;

        do {

            if(queryParameters != null && ! queryParameters.isEmpty())
                parameters = "?"+queryParameters+"page="+page+"&per_page="+pageSize;
            else
                parameters = "?page="+page+"&per_page="+pageSize;

            String query = GIT_HUB_API_URL +"/repos/"+repoFullName+"/labels"+parameters;

            System.out.println("Getting Next Label Info: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            result = restTemplate.exchange( query, HttpMethod.GET, entity, LabelInfo[].class);

            all.addAll(  Arrays.asList(result.getBody()) );

            page++;


        }while ( result != null && result.getBody().length > 0   && !testEnvironment);

        return all;
    }
}
