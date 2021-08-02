/*
 * Universidade Federal do Rio Grande do Norte
 * Instituto Metrópole Digital
 * Diretoria de Tecnologia da Informação
 *
 * snooper
 * UserQueryExecutor
 * @since 02/08/2021
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.issue.GitHubIssueInfo;
import br.com.jadson.snooper.github.data.users.GitHubUserInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Information about Github Users
 *
 * @author Jadson Santos - jadson.santos@ufrn.br
 */
public class UserQueryExecutor extends AbstractGitHubQueryExecutor{

    public UserQueryExecutor(){ }

    public UserQueryExecutor(String githubToken){
        super(githubToken);
    }


    /**
     * Return information about user
     *
     * @param userLogin
     * @return
     */
    public GitHubUserInfo user(String userLogin) {

        String parameters = "";

        ResponseEntity<GitHubUserInfo> result;

        if(queryParameters != null && ! queryParameters.isEmpty())
            parameters = "?"+queryParameters;

        String query = GIT_HUB_API_URL +"/users/"+userLogin+""+parameters;

        System.out.println("Getting User Info: "+query);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity entity = new HttpEntity(getDefaultHeaders());

        result = restTemplate.exchange( query, HttpMethod.GET, entity, GitHubUserInfo.class);

        return result.getBody();

    }
}
