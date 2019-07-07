/**
 * @author Hojin	
 *
 */
package com.vidyo.bo.authentication;

import java.io.Serializable;

public class CACAuthentication implements Authentication, Serializable {

    private String userNameExtractfrom;
    private String oscpcheck;
    private String oscprespondercheck;
    private String oscpresponder;
    private String oscpnonce;
    private String ldapurl;
    private String ldapusername;
    private String ldappassword;
    private String ldapbase;
    private String ldapfilter;
    private String ldapscope;
    
    private String username;
	private String password;
    
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

	public String getUserNameExtractfrom() {
		return userNameExtractfrom;
	}

	public void setUserNameExtractfrom(String userNameExtractfrom) {
		this.userNameExtractfrom = userNameExtractfrom;
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

	
	public String getOscpcheck() {
		return oscpcheck;
	}

	public void setOscpcheck(String oscpcheck) {
		this.oscpcheck = oscpcheck;
	}

	public String getOscprespondercheck() {
		return oscprespondercheck;
	}

	public void setOscprespondercheck(String oscprespondercheck) {
		this.oscprespondercheck = oscprespondercheck;
	}

	public String getOscpresponder() {
		return oscpresponder;
	}

	public void setOscpresponder(String oscpresponder) {
		this.oscpresponder = oscpresponder;
	}

	public String getOscpnonce() {
		return oscpnonce;
	}

	public void setOscpnonce(String oscpnonce) {
		this.oscpnonce = oscpnonce;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.CAC; 
  }

    @Override
    public LoginType getLoginType() {
        return LoginType.PORTAL;
    }

  

}
