package br.com.jadson.snooper.codecov.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeCovOwner {
    public String username;
    public String name;
    public String service;
    public Date updatestamp;
    public Object avatar_url;
    public String service_id;
}
