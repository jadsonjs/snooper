package br.com.jadson.snooper.github.data.stats.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Root response returned by the GitHub GraphQL API.
 *
 * Yuri Filgueira - yurimedeiros141@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphQLCommitResponse {
    public Data data;
}