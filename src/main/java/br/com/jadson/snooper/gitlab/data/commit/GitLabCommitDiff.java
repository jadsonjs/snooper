package br.com.jadson.snooper.gitlab.data.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information about the diff of a file changed in a GitLab commit.
 *
 * Luisa Ferreira - luisaferreirass08@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabCommitDiff {
    @JsonProperty("old_path")
    public String oldPath;

    @JsonProperty("new_path")
    public String newPath;

    @JsonProperty("new_file")
    public Boolean newFile;

    @JsonProperty("renamed_file")
    public Boolean renamedFile;

    @JsonProperty("deleted_file")
    public Boolean deletedFile;

    @JsonProperty("diff")
    public String diff;

    @JsonProperty("collapsed")
    public Boolean collapsed;

    @JsonProperty("too_large")
    public Boolean tooLarge;

    @JsonProperty("generated_file")
    public Boolean generatedFile;
}


