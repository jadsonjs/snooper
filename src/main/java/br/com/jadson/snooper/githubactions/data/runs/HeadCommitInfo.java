package br.com.jadson.snooper.githubactions.data.runs;

import br.com.jadson.snooper.github.data.commit.Author;
import br.com.jadson.snooper.github.data.commit.Committer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadCommitInfo {

    public String id;
    public String tree_id;
    public String message;
    public Date timestamp;
    public Author author;
    public Committer committer;
}
