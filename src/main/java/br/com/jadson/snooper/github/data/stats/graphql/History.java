package br.com.jadson.snooper.github.data.stats.graphql;

import br.com.jadson.snooper.github.data.association.graphql.PageInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;


/**
 * Commit history returned by the GitHub GraphQL API.
 *
 * Yuri Filgueira - yurimedeiros141@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class History {
    public PageInfo pageInfo;
    public List<CommitStatsNode> nodes;
    public int totalCount;
}