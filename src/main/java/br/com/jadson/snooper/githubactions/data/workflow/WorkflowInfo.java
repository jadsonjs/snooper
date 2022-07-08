package br.com.jadson.snooper.githubactions.data.workflow;

import br.com.jadson.snooper.utils.CustomDateDeserializer;
import br.com.jadson.snooper.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowInfo {

    public long id;
    public String node_id;
    public String name;
    public String path;
    public String state;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date created_at;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date updated_at;

    public String url;
    public String html_url;
    public String badge_url;
}
