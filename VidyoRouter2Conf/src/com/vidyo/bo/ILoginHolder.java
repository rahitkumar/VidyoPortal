package com.vidyo.bo;

import java.util.Calendar;

public interface ILoginHolder {
	public void setFromHost(String h);
	public String getFromHost();
	public void setUsername(String u);
	public String getUsername();
	public void setPassword(String p);
	public String getPassword();
	public void setLastLoginTime(Calendar c);
	public Calendar getLastLoginTime();
	public void setLastLoginStatus(boolean b);
	public boolean getLastLoginStatus();
	public String getMethod();
	public void setMethod(String m);
}
