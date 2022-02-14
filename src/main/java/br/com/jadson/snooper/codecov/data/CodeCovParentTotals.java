package br.com.jadson.snooper.codecov.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CodeCovParentTotals {

    @JsonProperty("C")
    public int C;
    public int b;
    public int d;
    public int f;
    public int h;
    @JsonProperty("M")
    public int M;
    public String c;
    @JsonProperty("N")
    public int N;
    public int p;
    public int m;
    public ArrayList<Object> diff;
    public int s;
    public int n;
}
