package br.com.jadson.snooper.github.data.stats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Statistics of changes made in a GitHub commit.
 *
 * Luisa Ferreira - luisaferreirass08@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubCommitStats {
    public int additions;
    public int deletions;
    public int total;
}
