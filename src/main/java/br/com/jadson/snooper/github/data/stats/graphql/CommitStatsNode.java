package br.com.jadson.snooper.github.data.stats.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Information about a commit node returned by the GitHub GraphQL API.
 *
 * Yuri Filgueira - yurimedeiros141@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitStatsNode {
    public String oid;
    public String id;
    public String url;
    public Comments comments;
    public GitActor author;
    public GitActor committer;
    public int additions;
    public int deletions;
    @JsonProperty("changedFiles")
    public int changedFiles;
    public String message;
}