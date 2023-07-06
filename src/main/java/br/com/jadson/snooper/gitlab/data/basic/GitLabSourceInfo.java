package br.com.jadson.snooper.gitlab.data.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabSourceInfo {
    public String format;
    public String url;
}
