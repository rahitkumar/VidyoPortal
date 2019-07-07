package com.vidyo.bo.authentication;


import java.io.Serializable;

public class LdapAuthentication implements Authentication, Serializable {

    private String ldapurl;
    private String ldapusername;
    private String ldappassword;
    private String ldapbase;
    private String ldapfilter;
    private String ldapscope;
    private boolean ldapmappingflag;

	private String username;
	private String password;

    @Override
    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.LDAP; // LDAP server does the auth check
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.PORTAL; // portal collects the username/password
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

	public boolean isLdapmappingflag() {
		return ldapmappingflag;
	}

	public void setLdapmappingflag(boolean ldapmappingflag) {
		this.ldapmappingflag = ldapmappingflag;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
