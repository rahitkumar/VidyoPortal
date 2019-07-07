package com.vidyo.bo.authentication;

import java.io.Serializable;

public class WsAuthentication implements Authentication, Serializable {

    private String wsurl;
    private String wsusername;
    private String wspassword;

    @Override
    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.WS;
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.PORTAL;
    }

    public String getURL() {
        return this.wsurl;
    }

    public void setURL(String url) {
        this.wsurl = url;
    }

    public String getUsername() {
        return this.wsusername;
    }

    public void setUsername(String user) {
        this.wsusername = user;
    }

    public String getPassword() {
        return this.wspassword;
    }

    public void setPassword(String pwd) {
        this.wspassword = pwd;
    }

}
