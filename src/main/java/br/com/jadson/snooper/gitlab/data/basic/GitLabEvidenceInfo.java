package br.com.jadson.snooper.gitlab.data.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabEvidenceInfo {
    public String sha;
    public String filepath;
    public Date collected_at;
}
