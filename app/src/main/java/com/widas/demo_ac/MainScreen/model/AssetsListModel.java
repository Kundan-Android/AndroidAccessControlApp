package com.widas.demo_ac.MainScreen.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "success",
        "status",
        "data"
})
public class AssetsListModel {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("status")
    private int status;
    @JsonProperty("data")
    private List<AssetsListData> data;
    @JsonProperty("success")
    public void setSuccess(boolean success){
        this.success = success;
    }
    @JsonProperty("success")
    public boolean getSuccess(){
        return this.success;
    }
    @JsonProperty("status")
    public void setStatus(int status){
        this.status = status;
    }
    @JsonProperty("status")
    public int getStatus(){
        return this.status;
    }
    @JsonProperty("data")
    public void setData(ArrayList<AssetsListData> data){
        this.data = data;
    }
    @JsonProperty("data")
    public List<AssetsListData> getData(){
        return this.data;
    }
}


