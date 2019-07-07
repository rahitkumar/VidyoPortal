package com.vidyo.bo.authentication;

import java.io.Serializable;

public class AuthenticationConfig implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2291677037679755200L;
	
	private boolean wsflag = false;
	private String wsurl;
	private String wsusername;
	private String wspassword;

	private boolean ldapflag = false;
	private String ldapurl;
	private String ldapusername;
	private String ldappassword;
	private String ldapbase;
	private String ldapfilter;
	private String ldapscope;

	private String username; // test user for LDAP service
	private String password; // test password for LDAP service

	private String authFor;
	private boolean ldapmappingflag;

    // saml config
	private boolean samlflag = false;
	private String samlIdpMetadata;
	private String samlSpEntityId;
	private String samlSecurityProfile;
	private String samlSSLProfile;
	private String samlSignMetadata;
	private boolean isHttpsRequest = false;
	private SamlProvisionType samlAuthProvisionType = SamlProvisionType.SAML;
	private String idpAttributeForUsername;

	//added for CAC
	private boolean cacflag = false;
    private String cacldapflag="0";
    private String cacAuthType;
	private String userNameExtractfrom;
	private String ocspcheck;
	private String ocsprespondercheck;
	private String ocspresponder;
	private String ocspnonce;

	//added for REST
	
	private boolean restFlag = false;
	private String restUrl;
	
	public boolean isRestFlag() {
		return restFlag;
	}

	public void setRestFlag(boolean restFlag) {
		this.restFlag = restFlag;
	}

	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

	public String getCacAuthType() {
		return cacAuthType;
	}

	public void setCacAuthType(String cacAuthType) {
		this.cacAuthType = cacAuthType;
	}
 
    public String getCacldapflag() {
		return cacldapflag;
	}

	public void setCacldapflag(String cacldapflag) {
		this.cacldapflag = cacldapflag;
	}

	public boolean isWsflag() {
        return wsflag;
    }

    public void setWsflag(boolean wsflag) {
        this.wsflag = wsflag;
    }

    public String getWsurl() {
        return wsurl;
    }

    public void setWsurl(String wsurl) {
        this.wsurl = wsurl;
    }

    public String getWsusername() {
        return wsusername;
    }

    public void setWsusername(String wsusername) {
        this.wsusername = wsusername;
    }

    public String getWspassword() {
        return wspassword;
    }

    public void setWspassword(String wspassword) {
        this.wspassword = wspassword;
    }

    public boolean isLdapflag() {
        return ldapflag;
    }

    public void setLdapflag(boolean ldapflag) {
        this.ldapflag = ldapflag;
    }

    public String getLdapurl() {
        return ldapurl;
    }

    public void setLdapurl(String ldapurl) {
        this.ldapurl = ldapurl;
    }

    public String getLdapusername() {
        return ldapusername;
    }

    public void setLdapusername(String ldapusername) {
        this.ldapusername = ldapusername;
    }

    public String getLdappassword() {
        return ldappassword;
    }

    public void setLdappassword(String ldappassword) {
        this.ldappassword = ldappassword;
    }

    public String getLdapbase() {
        return ldapbase;
    }

    public void setLdapbase(String ldapbase) {
        this.ldapbase = ldapbase;
    }

    public String getLdapfilter() {
        return ldapfilter;
    }

    public void setLdapfilter(String ldapfilter) {
        this.ldapfilter = ldapfilter;
    }

    public String getLdapscope() {
        return ldapscope;
    }

    public void setLdapscope(String ldapscope) {
        this.ldapscope = ldapscope;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isCacflag() {
		return cacflag;
	}

	public void setCacflag(boolean cacflag) {
		this.cacflag = cacflag;
	}

	public String getUserNameExtractfrom() {
		return userNameExtractfrom;
	}

	public void setUserNameExtractfrom(String userNameExtractfrom) {
		this.userNameExtractfrom = userNameExtractfrom;
	}

	public String getOcspcheck() {
		return ocspcheck;
	}

	public void setOcspcheck(String ocspcheck) {
		this.ocspcheck = ocspcheck;
	}

	public String getOcsprespondercheck() {
		return ocsprespondercheck;
	}

	public void setOcsprespondercheck(String ocsprespondercheck) {
		this.ocsprespondercheck = ocsprespondercheck;
	}

	public String getOcspresponder() {
		return ocspresponder;
	}

	public void setOcspresponder(String ocspresponder) {
		this.ocspresponder = ocspresponder;
	}

	public String getOcspnonce() {
		return ocspnonce;
	}

	public void setOcspnonce(String ocspnonce) {
		this.ocspnonce = ocspnonce;
	}

	
	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthFor() {
        return authFor;
    }

    public void setAuthFor(String authFor) {
        this.authFor = authFor;
    }

    public boolean isLdapmappingflag() {
        return ldapmappingflag;
    }

    public void setLdapmappingflag(boolean ldapmappingflag) {
        this.ldapmappingflag = ldapmappingflag;
    }

    public boolean isSamlflag() {
        return samlflag;
    }

    public void setSamlflag(boolean samlflag) {
        this.samlflag = samlflag;
    }

    public String getSamlIdpMetadata() {
        return samlIdpMetadata;
    }

    public void setSamlIdpMetadata(String samlIdpMetadata) {
        this.samlIdpMetadata = samlIdpMetadata;
    }

    public String getSamlSpEntityId() {
		return samlSpEntityId;
	}

	public void setSamlSpEntityId(String samlSpEntityId) {
		this.samlSpEntityId = samlSpEntityId;
	}

	public String getSamlSecurityProfile() {
        return samlSecurityProfile;
    }

    public void setSamlSecurityProfile(String samlSecurityProfile) {
        this.samlSecurityProfile = samlSecurityProfile;
    }

    public String getSamlSSLProfile() {
        return samlSSLProfile;
    }

    public void setSamlSSLProfile(String samlSSLProfile) {
        this.samlSSLProfile = samlSSLProfile;
    }

    public String getSamlSignMetadata() {
        return samlSignMetadata;
    }

    public void setSamlSignMetadata(String samlSignMetadata) {
        this.samlSignMetadata = samlSignMetadata;
    }
    
    public boolean isHttpsRequest() {
        return isHttpsRequest;
    }

    public void setHttpsRequest(boolean isHttpsRequest) {
        this.isHttpsRequest = isHttpsRequest;
    }
    
    public SamlProvisionType getSamlAuthProvisionType() {
		return samlAuthProvisionType;
	}

	public void setSamlAuthProvisionType(SamlProvisionType samlAuthProvisionType) {
		this.samlAuthProvisionType = samlAuthProvisionType;
	}

    public String getIdpAttributeForUsername() {
		return idpAttributeForUsername;
	}

	public void setIdpAttributeForUsername(String idpAttributeForUsername) {
		this.idpAttributeForUsername = idpAttributeForUsername;
	}

	public Authentication toAuthentication() {

		if (isLdapflag()) {
			LdapAuthentication ldapAuth = new LdapAuthentication();
			ldapAuth.setLdapurl(getLdapurl());
			ldapAuth.setLdapusername(getLdapusername());
			ldapAuth.setLdappassword(getLdappassword());
			ldapAuth.setLdapbase(getLdapbase());
			ldapAuth.setLdapfilter(getLdapfilter());
			ldapAuth.setLdapscope(getLdapscope());
			ldapAuth.setLdapmappingflag(isLdapmappingflag());
			// username and password for testing
			ldapAuth.setUsername(getUsername());
			ldapAuth.setPassword(getPassword());
			return ldapAuth;
		} else if (isWsflag()) {
			WsAuthentication wsAuth = new WsAuthentication();
			wsAuth.setURL(getWsurl());
			wsAuth.setUsername(getWsusername());
			wsAuth.setPassword(getWspassword());
			return wsAuth;

		} else if (isRestFlag()) {
			WsRestAuthentication restAuth = new WsRestAuthentication();
			restAuth.setRestUrl(getRestUrl());
			return restAuth;
		} else if (isSamlflag()) {
			SamlAuthentication samlAuth = new SamlAuthentication();
			samlAuth.setIdentityProviderMetadata(getSamlIdpMetadata());
			samlAuth.setSpEntityId(getSamlSpEntityId());
			samlAuth.setSecurityProfile(getSamlSecurityProfile());
			samlAuth.setSslTlsProfile(getSamlSSLProfile());
			samlAuth.setSignMetadata("YES".equals(getSamlSignMetadata()));
			samlAuth.setHttps(isHttpsRequest());
			samlAuth.setAuthProvisionType(getSamlAuthProvisionType());
			samlAuth.setIdpAttributeForUsername(getIdpAttributeForUsername());
			return samlAuth;
		} else if (cacflag) {
			CACAuthentication auth = new CACAuthentication();
			auth.setOscpcheck(getOcspcheck());
			auth.setOscpnonce(getOcspnonce());
			auth.setOscpresponder(getOcspresponder());
			auth.setOscprespondercheck(getOcsprespondercheck());
			auth.setUserNameExtractfrom(getUserNameExtractfrom());
			auth.setLdapbase(getLdapbase());
			auth.setLdapfilter(getLdapfilter());
			auth.setLdappassword(getLdappassword());
			auth.setLdapscope(getLdapscope());
			auth.setLdapurl(getLdapurl());
			auth.setLdapusername(getLdapusername());
			auth.setUsername(getUsername());
			auth.setPassword(getPassword());
			return auth;
		}

		else {
			InternalAuthentication nativeAuth = new InternalAuthentication();
			return nativeAuth;
		}
	}
}