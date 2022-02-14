package br.com.jadson.snooper.codecov.data;

public class CodeCovCommit {

    public int pullid;
    public CodeCovAuthor author;
    public Object deleted;
    public String timestamp;
    public CodeCovParentTotals parent_totals;
    public String state;
    public CodeCovTotals totals;
    public String commitid;
    public boolean ci_passed;
    public String branch;
    public String message;
    public boolean merged;
}
