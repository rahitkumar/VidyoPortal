package com.vidyo.bo.authentication;

import java.io.Serializable;

public class SamlAuthentication implements Authentication, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String SECURITY_PROFILE_METAIOP = "METAIOP";
    public static final String SECURITY_PROFILE_PKIX = "PKIX";
    public static final String SSLTLS_PROFILE_METAIOP = "METAIOP";
    public static final String SSLTLS_PROFILE_PKIX = "PKIX";

    private String idpXml;
    private String securityProfile;
    private String sslTlsProfile;
    private boolean signMetadata = true;
    private boolean isHttps = false;
    private SamlProvisionType authProvisionType = SamlProvisionType.SAML;
    private String idpAttributeForUsername;
    private String spEntityId;
    
    @Override
    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.SAML_BROWSER;
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.EXTERNAL; // username and password captured by external service
    }

	public String getIdentityProviderMetadata() {
        return this.idpXml;
    }

    public void setIdentityProviderMetadata(String xml) {
        this.idpXml = xml;
    }

    public void setSecurityProfile(String type) {
        this.securityProfile  = type;
    }

    public String getSecurityProfile() {
        return this.securityProfile;
    }

    public void setSslTlsProfile(String type) {
        this.sslTlsProfile = type;
    }

    public String getSslTlsProfile() {
        return this.sslTlsProfile;
    }

    public boolean isSignMetadata() {
        return this.signMetadata;
    }

    public void setSignMetadata(boolean flag) {
        this.signMetadata = flag;
    }

	public boolean isHttps() {
		return isHttps;
	}

	public void setHttps(boolean isHttps) {
		this.isHttps = isHttps;
	}

	public SamlProvisionType getAuthProvisionType() {
		return authProvisionType;
	}

	public void setAuthProvisionType(SamlProvisionType authProvisionType) {
		this.authProvisionType = authProvisionType;
	}

	public String getIdpAttributeForUsername() {
		return idpAttributeForUsername;
	}

	public void setIdpAttributeForUsername(String idpAttributeForUsername) {
		this.idpAttributeForUsername = idpAttributeForUsername;
	}

	public String getSpEntityId() {
		return spEntityId;
	}

	public void setSpEntityId(String spEntityId) {
		this.spEntityId = spEntityId;
	}
}
