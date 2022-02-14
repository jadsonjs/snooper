package br.com.jadson.snooper.codecov.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CodeCovRepo {

    public boolean using_integration;
    public String name;
    public String language;
    public boolean deleted;
    public Object bot_username;
    public boolean activated;
    @JsonProperty("private")
    public boolean myprivate;
    public Date updatestamp;
    public String branch;
    public boolean active;
    public String service_id;
    public String image_token;
}
