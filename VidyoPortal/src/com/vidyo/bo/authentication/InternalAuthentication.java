package com.vidyo.bo.authentication;


public class InternalAuthentication implements  Authentication {
    @Override
    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.INTERNAL;
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.PORTAL;
    }
}
