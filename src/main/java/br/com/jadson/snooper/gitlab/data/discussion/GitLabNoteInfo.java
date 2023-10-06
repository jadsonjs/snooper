package br.com.jadson.snooper.gitlab.data.discussion;

import br.com.jadson.snooper.gitlab.data.basic.GitLabAuthorInfo;

import java.util.Date;

public class GitLabNoteInfo {

    public int id;
    public String type;
    public String body;
    public Object attachment;
    public GitLabAuthorInfo author;
    public Date created_at;
    public Date updated_at;
    public boolean system;
    public int noteable_id;
    public String noteable_type;
    public int project_id;
    public Object noteable_iid;
    public boolean resolved;
    public boolean resolvable;
    public Object resolved_by;
    public Object resolved_at;
}
