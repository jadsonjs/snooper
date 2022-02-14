package br.com.jadson.snooper.codecov.data;

import java.util.ArrayList;

public class CodeCovRootInfo {
    public CodeCovOwner owner;
    public ArrayList<CodeCovCommit> commits;
    public CodeCovMeta meta;
    public CodeCovRepo repo;
}
