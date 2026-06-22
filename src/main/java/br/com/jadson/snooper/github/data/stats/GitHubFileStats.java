package br.com.jadson.snooper.github.data.stats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Churn (number of commits) information of a file in a GitHub repository.
 *
 * Luisa Ferreira - luisaferreirass08@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubFileStats {
    public String path;
    public int churn;
}
