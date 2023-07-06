package br.com.jadson.snooper.gitlab.data.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabAuthorInfo {
    public int id;
    public String name;
    public String username;
    public String state;
    public Object avatar_url;
    public String web_url;
}
