package br.com.jadson.snooper.github.data.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Information about a file changed in a GitHub commit.
 *
 * Luisa Ferreira - luisaferreirass08@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubFileChanged {
    public String filename;
    public int additions;
    public int deletions;
    public int changes;
}
