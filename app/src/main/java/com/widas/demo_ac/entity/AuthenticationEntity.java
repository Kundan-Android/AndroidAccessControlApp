package com.widas.demo_ac.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by ganesh on 13/02/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationEntity implements Serializable {

    /*
    d = display name
    issuer = tenant name
    tk = tenant key
    l = logo url
    sub = sub
    cid = authenticator client id
    rurl = redirect url
    eid = exchange id
    secret = totp_secret (only for totp)
    t = verification type
    */

    // display name
    private String displayName;
    // tenant name
    private String issuer;
    // tenant key
    private String tenantKey;
    // Logo url
    private String logoURL;
    // User id (sub)
    private String userId;
    // Authenticator client id
    private String clientId;
    // only for TOTP - code
    private String secret;
    // Verification Type
    private String verificationType;
    // Base url
    private String baseURL;
    // Redirect url
    private String redirectURL;
    // Exchange Id
    private String exchange_id;
    // Random numbers for SMART-PUSH
    private String[] randomNumbers;

   /* Commented since new V2 MFA model
    private String statusId;
    private String authorizationURL;
    private String tokenURL;
    private String userInfoURL;
    private String socialURL;
    private String viewType;
    private String verificationServiceURL;
    private String userDeviceId;
    private String verifierPassword;

    private MultipartBody.Part verifierbioMetrics;
    private boolean isLoginRemembered;*/


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getTenantKey() {
        return tenantKey;
    }

    public void setTenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(String exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String[] getRandomNumbers() {
        return randomNumbers;
    }

    public void setRandomNumbers(String[] randomNumbers) {
        this.randomNumbers = randomNumbers;
    }

    /*
    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getAuthorizationURL() {
        return authorizationURL;
    }

    public void setAuthorizationURL(String authorizationURL) {
        this.authorizationURL = authorizationURL;
    }

    public String getTokenURL() {
        return tokenURL;
    }

    public void setTokenURL(String tokenURL) {
        this.tokenURL = tokenURL;
    }

    public String getUserInfoURL() {
        return userInfoURL;
    }

    public void setUserInfoURL(String userInfoURL) {
        this.userInfoURL = userInfoURL;
    }

    public String getSocialURL() {
        return socialURL;
    }

    public void setSocialURL(String socialURL) {
        this.socialURL = socialURL;
    }

    public String getLogoutURL() {
        return logoutURL;
    }

    public void setLogoutURL(String logoutURL) {
        this.logoutURL = logoutURL;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getVerificationServiceURL() {
        return verificationServiceURL;
    }

    public void setVerificationServiceURL(String verificationServiceURL) {
        this.verificationServiceURL = verificationServiceURL;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getVerifierPassword() {
        return verifierPassword;
    }

    public void setVerifierPassword(String verifierPassword) {
        this.verifierPassword = verifierPassword;
    }

    public String getRandomNumbers() {
        return randomNumbers;
    }

    public void setRandomNumbers(String randomNumbers) {
        this.randomNumbers = randomNumbers;
    }




    public boolean isLoginRemembered() {
        return isLoginRemembered;
    }

    public void setLoginRemembered(boolean loginRemembered) {
        isLoginRemembered = loginRemembered;
    }

    public MultipartBody.Part getVerifierbioMetrics() {
        return verifierbioMetrics;
    }

    public void setVerifierbioMetrics(MultipartBody.Part verifierbioMetrics) {
        this.verifierbioMetrics = verifierbioMetrics;
    }
    */
}
