package br.com.jadson.snooper.githubactions.data.runs;

import br.com.jadson.snooper.github.data.commit.Author;
import br.com.jadson.snooper.github.data.commit.Committer;
import br.com.jadson.snooper.utils.CustomDateDeserializer;
import br.com.jadson.snooper.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadCommitInfo {

    public String id;
    public String tree_id;
    public String message;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date timestamp;
    public Author author;
    public Committer committer;
}
