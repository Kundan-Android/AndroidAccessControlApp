package com.widas.demo_ac.MainScreen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "className",
        "device_id",
        "_id",
        "createdTime",
        "updatedTime",
        "id"
})
public class Controllers
{
    @JsonProperty("className")
    private String className;
    @JsonProperty("device_id")
    private String device_id;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("createdTime")
    private String createdTime;
    @JsonProperty("updatedTime")
    private String updatedTime;
    @JsonProperty("id")
    private String id;
    @JsonProperty("className")
    public void setClassName(String className){
        this.className = className;
    }
    @JsonProperty("className")
    public String getClassName(){
        return this.className;
    }
    @JsonProperty("device_id")
    public void setDevice_id(String device_id){
        this.device_id = device_id;
    }
    @JsonProperty("device_id")
    public String getDevice_id(){
        return this.device_id;
    }
    @JsonProperty("_id")
    public void set_id(String _id){
        this._id = _id;
    }
    @JsonProperty("_id")
    public String get_id(){
        return this._id;
    }
    @JsonProperty("createdTime")
    public void setCreatedTime(String createdTime){
        this.createdTime = createdTime;
    }
    @JsonProperty("createdTime")
    public String getCreatedTime(){
        return this.createdTime;
    }
    @JsonProperty("updatedTime")
    public void setUpdatedTime(String updatedTime){
        this.updatedTime = updatedTime;
    }
    @JsonProperty("updatedTime")
    public String getUpdatedTime(){
        return this.updatedTime;
    }
    @JsonProperty("id")
    public void setId(String id){
        this.id = id;
    }
    @JsonProperty("id")
    public String getId(){
        return this.id;
    }
}
