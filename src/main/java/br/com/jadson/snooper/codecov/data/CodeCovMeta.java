package br.com.jadson.snooper.codecov.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeCovMeta {
    public int status;
    public int limit;
    public int page;
}
