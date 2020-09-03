package com.widas.demo_ac.entity;

import com.example.cidaasv2.VerificationV2.data.Entity.ExcangeId.ExchangeIDEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewPushNotificationEntity implements Serializable {

    private String sub;
    private ExchangeIDEntity exchange_id;
    transient private String tenant_name;
    private long request_time;
    private String tenant_key;
    private String verification_type;
    private String[] requested_types;
    private String formatted_address;


    public NewPushNotificationEntity() {
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public ExchangeIDEntity getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(ExchangeIDEntity exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public long getRequest_time() {
        return request_time;
    }

    public void setRequest_time(long request_time) {
        this.request_time = request_time;
    }

    public String getTenant_key() {
        return tenant_key;
    }

    public void setTenant_key(String tenant_key) {
        this.tenant_key = tenant_key;
    }

    public String getVerification_type() {
        return verification_type;
    }

    public void setVerification_type(String verification_type) {
        this.verification_type = verification_type;
    }

    public String[] getRequested_types() {
        return requested_types;
    }

    public void setRequested_types(String[] requested_types) {
        this.requested_types = requested_types;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
}
