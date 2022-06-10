package br.com.jadson.snooper.coverall.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoveAllBuildInfo {

    public Date created_at;
    public Object url;
    public String commit_message;
    public String branch;
    public String committer_name;
    public String committer_email;
    public String commit_sha;
    public String repo_name;
    public String badge_url;
    public double coverage_change;
    /**
     * The information of coverage comes here !!!!!
     */
    public double covered_percent;
}
