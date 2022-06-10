package br.com.jadson.snooper.codecov.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeCovRootInfo {
    public CodeCovOwner owner;
    public ArrayList<CodeCovCommit> commits;
    public CodeCovMeta meta;
    public CodeCovRepo repo;
}
