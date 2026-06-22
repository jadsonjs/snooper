package br.com.jadson.snooper.github.data.stats.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 * Information about the author or committer of a GitHub commit.
 *
 * Yuri Filgueira - yurimedeiros141@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitActor {
    public User user;
    public String name;
    public String email;
    public Date date;
    public String avatarUrl;
}