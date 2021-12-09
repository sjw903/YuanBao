package com.yuanbaogo.homevideo.main.bean;


public class LiveAuthenticationBean {

    private boolean authentication;
    private boolean ban;
    private String banDuration;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public String getBanDuration() {
        return banDuration;
    }

    public void setBanDuration(String banDuration) {
        this.banDuration = banDuration;
    }
}
