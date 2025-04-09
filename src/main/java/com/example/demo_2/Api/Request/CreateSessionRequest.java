package com.example.demo_2.Api.Request;

import com.example.demo_2.Api.Auth;
import com.example.demo_2.Api.Payment;

public class CreateSessionRequest {
    private String locale = "es_CO";
    private Auth auth;
    private Payment payment;
    private String expiration;
    private String returnUrl;
    private String ipAddress;
    private String userAgent;

    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public Auth getAuth() {
        return auth;
    }
    public void setAuth(Auth auth) {
        this.auth = auth;
    }
    public String getExpiration() {
        return expiration;
    }
    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
    public String getReturnUrl() {
        return returnUrl;
    }
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getUserAgent() {
        return userAgent;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
