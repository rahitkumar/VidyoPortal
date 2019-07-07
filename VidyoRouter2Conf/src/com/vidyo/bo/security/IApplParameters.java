package com.vidyo.bo.security;

public interface IApplParameters {
	public void setSecurityTab(String s);
    public void setCommand(String c);
    public String getCommand();
    public void setLang(String l);
    public String getLang();
    public String getSecurityTab();
}
