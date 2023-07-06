package br.com.jadson.snooper.gitlab.data.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabAssetsInfo {

    public int count;
    public ArrayList<GitLabSourceInfo> sources;
    public ArrayList<Object> links;
}
