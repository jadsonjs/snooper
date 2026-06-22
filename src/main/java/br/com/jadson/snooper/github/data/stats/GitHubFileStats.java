package br.com.jadson.snooper.github.data.stats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileStats {
    public String path;
    public int churn;
}
