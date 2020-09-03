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
        "_id",
        "controllers",
        "resource_id",
        "resource_name",
        "resource_type",
        "id"
})
public class AssetsListData
{
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("controllers")
    private List<Controllers> controllers;
    @JsonProperty("resource_id")
    private String resource_id;

    @JsonProperty("resource_name")
    private String resource_name;
    @JsonProperty("resource_type")
    private String resource_type;
    @JsonProperty("id")
    private String id;

    @JsonProperty("_id")
    public void set_id(String _id){
        this._id = _id;
    }
    @JsonProperty("_id")
    public String get_id(){
        return this._id;
    }
    @JsonProperty("controllers")
    public void setControllers(List<Controllers> controllers){
        this.controllers = controllers;
    }
    @JsonProperty("controllers")
    public List<Controllers> getControllers(){
        return this.controllers;
    }
    @JsonProperty("resource_id")
    public void setResource_id(String resource_id){
        this.resource_id = resource_id;
    }
    @JsonProperty("resource_id")
    public String getResource_id(){
        return this.resource_id;
    }
    @JsonProperty("resource_name")
    public void setResource_name(String resource_name){
        this.resource_name = resource_name;
    }
    @JsonProperty("resource_name")
    public String getResource_name(){
        return this.resource_name;
    }
    @JsonProperty("resource_type")
    public void setResource_type(String resource_type){
        this.resource_type = resource_type;
    }
    @JsonProperty("resource_type")
    public String getResource_type(){
        return this.resource_type;
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
