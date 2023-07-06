package br.com.jadson.snooper.gitlab.data.issue;

import br.com.jadson.snooper.gitlab.data.basic.GitLabAuthorInfo;
import br.com.jadson.snooper.gitlab.data.merge.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabIssueInfo {
        public String state;
        public String description;
        public GitLabAuthorInfo author;
        public Milestone milestone;
        public int project_id;
        public List<Assignee> assignees;
        public Assignee assignee;
        public String type;
        public Date updated_at;
        public Date closed_at;
        public GitLabAuthorInfo closed_by;
        public int id;
        public String title;
        public Date created_at;
        public Object moved_to_id;
        public int iid;
        public List<String> labels;
        public int upvotes;
        public int downvotes;
        public int merge_requests_count;
        public int user_notes_count;
        public String due_date;
        public String web_url;
        public References references;
        public TimeStats time_stats;
        public boolean has_tasks;
        public String task_status;
        public boolean confidential;
        public boolean discussion_locked;
        public String issue_type;
        public String severity;
        public Links _links;
        public TaskCompletionStatus task_completion_status;
}
