package com.widas.demo_ac.AccessControl.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "success",
        "status",
        "data"
})
public class ActionPerformStatus {

    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("data")
    private Data1 data;

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("data")
    public Data1 getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data1 data) {
        this.data = data;
    }

}