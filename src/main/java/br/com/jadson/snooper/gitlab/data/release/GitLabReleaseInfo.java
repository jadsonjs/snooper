package br.com.jadson.snooper.gitlab.data.release;

import br.com.jadson.snooper.gitlab.data.basic.GitLabAssetsInfo;
import br.com.jadson.snooper.gitlab.data.basic.GitLabEvidenceInfo;
import br.com.jadson.snooper.gitlab.data.basic.GitLabLinksInfo;
import br.com.jadson.snooper.gitlab.data.commit.GitLabCommitInfo;
import br.com.jadson.snooper.gitlab.data.basic.GitLabAuthorInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabReleaseInfo {
    public String name;
    public String tag_name;
    public String description;
    public Date created_at;
    public Date released_at;
    public boolean upcoming_release;
    public GitLabAuthorInfo author;
    public GitLabCommitInfo commit;
    public String commit_path;
    public String tag_path;
    public GitLabAssetsInfo assets;
    public ArrayList<GitLabEvidenceInfo> evidences;
    public GitLabLinksInfo _links;
}
