package br.com.jadson.snooper.gitlab.data.pipeline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedStatus {

    public String icon;
    public String text;
    public String label;
    public String group;
    public String tooltip;
    public boolean has_details;
    public String details_path;
    public Object illustration;
    public String favicon;
}
