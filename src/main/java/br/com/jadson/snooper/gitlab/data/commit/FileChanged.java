package br.com.jadson.snooper.gitlab.data.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileChanged {
    public String filename;
    public int additions;
    public int deletions;
}
