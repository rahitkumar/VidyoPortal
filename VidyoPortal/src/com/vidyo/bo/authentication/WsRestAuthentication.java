package com.vidyo.bo.authentication;

import java.io.Serializable;

public class WsRestAuthentication  implements Authentication, Serializable {

	private static final long serialVersionUID = -8840362496520648208L;
	private String restUrl;

	public String getRestUrl() {
			return restUrl;
	}

	public void setRestUrl(String restUrl) {
			this.restUrl = restUrl;
	}
    @Override
    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.WS_REST;
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.PORTAL;
    }

    
}
