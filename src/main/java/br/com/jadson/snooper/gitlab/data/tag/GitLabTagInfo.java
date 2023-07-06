package br.com.jadson.snooper.gitlab.data.tag;

import br.com.jadson.snooper.gitlab.data.commit.GitLabCommitInfo;
import br.com.jadson.snooper.gitlab.data.release.GitLabReleaseInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabTagInfo {

    public String name;
    public String message;
    public String target;
    public GitLabCommitInfo commit;
    public GitLabReleaseInfo release;
    @JsonProperty("protected")
    public boolean myprotected;
}
