package com.widas.demo_ac.AccessControl.Model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "available_actions",
        "current_status"
})
public class Data {

    @JsonProperty("available_actions")
    private List<String> availableActions = null;
    @JsonProperty("current_status")
    private String currentStatus;

    @JsonProperty("available_actions")
    public List<String> getAvailableActions() {
        return availableActions;
    }

    @JsonProperty("available_actions")
    public void setAvailableActions(List<String> availableActions) {
        this.availableActions = availableActions;
    }

    @JsonProperty("current_status")
    public String getCurrentStatus() {
        return currentStatus;
    }

    @JsonProperty("current_status")
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

}