package br.com.jadson.snooper.gitlab.data.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Information about a file changed in a GitLab commit.
 *
 * Luisa Ferreira - luisaferreirass08@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabFileChanged {
    public String filename;
    public int additions;
    public int deletions;
}
