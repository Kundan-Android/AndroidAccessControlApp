package com.widas.demo_ac.AccessControl.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ack_id"
})
public class Data1 {

    @JsonProperty("ack_id")
    private String ackId;

    @JsonProperty("ack_id")
    public String getAckId() {
        return ackId;
    }

    @JsonProperty("ack_id")
    public void setAckId(String ackId) {
        this.ackId = ackId;
    }

}
