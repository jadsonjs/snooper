package br.com.jadson.snooper.coverall.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoveAllRepositoryInfo {

    /**
     * Github, gitlab or bitbucket
     */
    public String service;

    /**
     * name of repository
     */
    public String name;
    /**
     * Whether comments will be posted on pull requests
     */
    public boolean comment_on_pull_requests;
    /**
     * Whether build status will be sent to the git provider
     */
    public boolean send_build_status;
    /**
     * Minimum coverage that must be present on a build for the build to pass (null means that any decrease is a failure)
     */
    public String commit_status_fail_threshold;
    /**
     * If coverage decreases, the maximum allowed amount of decrease that will be allowed for the build
     * to pass (null means that any decrease is a failure)
     */
    public String commit_status_fail_change_threshold;
    /**
     * Timestamp of the creation of this Repo
     */
    public Date created_at;
    /**
     *  Timestamp of the creation of this Repo
     */
    public Date updated_at;
}
