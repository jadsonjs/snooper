package br.com.jadson.snooper.codecov.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeCovAuthor {
    public String username;
    public String service_id;
    public String name;
    public String service;
    public String email;
}
