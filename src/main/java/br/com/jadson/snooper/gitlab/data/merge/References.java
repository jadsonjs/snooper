package br.com.jadson.snooper.gitlab.data.merge;

import com.fasterxml.jackson.annotation.JsonProperty;

public class References {
    @JsonProperty("short")
    public String myshort;
    public String relative;
    public String full;
}