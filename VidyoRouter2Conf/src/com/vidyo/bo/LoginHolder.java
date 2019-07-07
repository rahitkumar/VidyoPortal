package com.vidyo.bo;

import java.util.Calendar;

public class LoginHolder implements ILoginHolder {
	private String fromhost;
	private String username;
	private String password;
	private Calendar lastLoginTime;
	private boolean lastLoginStatus;
	private String method;
	
	public LoginHolder(){
		fromhost = "";
		username = "";
		password = "";
		lastLoginStatus = false;
		method   = "";
	}
	public void setFromHost(String h){
		fromhost = h;
	}
	public String getFromHost(){
		return fromhost;
	}
	
	public void setUsername(String u){
		username = u;
	}
	public String getUsername(){
		return username;
	}
	public String getMethod(){
		return method;
	}
	
	public void setPassword(String p){
		password = p;
	}
	public String getPassword(){
		return password;
	}
	
	public void setLastLoginTime(Calendar c){
		lastLoginTime = c;
	}
	public Calendar getLastLoginTime(){
		return lastLoginTime;
	}
	
	public void setLastLoginStatus(boolean b){
		lastLoginStatus = b;
	}
	public boolean getLastLoginStatus(){
		return lastLoginStatus;
	}
	public void setMethod(String m){
		method = m;
	}

}
