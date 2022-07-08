/*
 * Universidade Federal do Rio Grande do Norte
 * Instituto Metrópole Digital
 * Diretoria de Tecnologia da Informação
 *
 * snooper
 * GitHubUserInfo
 * @since 02/08/2021
 */
package br.com.jadson.snooper.github.data.users;

import br.com.jadson.snooper.utils.CustomDateDeserializer;
import br.com.jadson.snooper.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 *
 * @author Jadson Santos - jadson.santos@ufrn.br
 */
public class GitHubUserInfo {

    public String login;
    public int id;
    public String node_id;
    public String avatar_url;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public boolean site_admin;
    public String name;
    public String company;
    public String blog;
    public String location;
    public String email;
    public String hireable;
    public String bio;
    public String twitter_username;
    public int public_repos;
    public int public_gists;
    public int followers;
    public int following;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date created_at;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date updated_at;

}
