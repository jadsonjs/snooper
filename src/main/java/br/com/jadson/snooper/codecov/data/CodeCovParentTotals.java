package br.com.jadson.snooper.codecov.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeCovParentTotals {

    @JsonProperty("C")
    public int C;
    public int b;
    public int d;
    public int f;
    public int h;
    @JsonProperty("M")
    public int M;

    /**
     * This information is the coverage
     * "c": "83.52222"
     */
    public String c;

    @JsonProperty("N")
    public int N;
    public int p;
    public int m;
    public ArrayList<Object> diff;
    public int s;
    public int n;
}
