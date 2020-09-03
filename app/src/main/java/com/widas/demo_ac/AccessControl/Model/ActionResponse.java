package com.widas.demo_ac.AccessControl.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "action"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionResponse {

    @JsonProperty("action")
    private String action;

    public ActionResponse() {
    }

    public ActionResponse(String action) {
        this.action = action;
    }

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }

}