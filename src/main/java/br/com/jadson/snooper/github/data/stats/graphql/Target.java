package br.com.jadson.snooper.github.data.stats.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Target object returned by the GitHub GraphQL API.
 *
 * Yuri Filgueira - yurimedeiros141@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Target {
    public History history;
}