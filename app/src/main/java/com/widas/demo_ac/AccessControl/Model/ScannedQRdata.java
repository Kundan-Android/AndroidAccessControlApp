
package com.widas.demo_ac.AccessControl.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "di",
    "dn",
    "iss",
    "ph_id"
})
public class ScannedQRdata {

    @JsonProperty("di")
    private String di;
    @JsonProperty("dn")
    private String dn;
    @JsonProperty("iss")
    private String iss;
    @JsonProperty("ph_id")
    private String phId;

    @JsonProperty("di")
    public String getDi() {
        return di;
    }

    @JsonProperty("di")
    public void setDi(String di) {
        this.di = di;
    }

    @JsonProperty("dn")
    public String getDn() {
        return dn;
    }

    @JsonProperty("dn")
    public void setDn(String dn) {
        this.dn = dn;
    }

    @JsonProperty("iss")
    public String getIss() {
        return iss;
    }

    @JsonProperty("iss")
    public void setIss(String iss) {
        this.iss = iss;
    }

    @JsonProperty("ph_id")
    public String getPhId() {
        return phId;
    }

    @JsonProperty("ph_id")
    public void setPhId(String phId) {
        this.phId = phId;
    }

}
