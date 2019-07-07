package com.vidyo.bo;

import java.io.Serializable;

public class CdrAccess implements Serializable {
	int enabled;
	String format;
	int allowdeny;
    String id = "cdraccess";
    String ip;
    String password;
    int allowdelete;

	public int getAllowdelete() {
		return allowdelete;
	}

	public void setAllowdelete(int allowdelete) {
		this.allowdelete = allowdelete;
	}

	public int getAllowdeny() {
		return allowdeny;
	}

	public void setAllowdeny(int allowdeny) {
		this.allowdeny = allowdeny;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}