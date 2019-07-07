package com.vidyo.bo.security;

import java.io.Serializable;


public class ApplParameters implements Serializable, IApplParameters{
	
	private static final long serialVersionUID = 1L;
	private String command;
	private String lang;
	private String securityTab;
	
	public void setSecurityTab(String s){
		securityTab = s;
	}
	
    public void setCommand(String c){
    	command = c;
    }
    public String getCommand(){
    	return command;
    }
    public void setLang(String l){
    	lang = l;
    }
    public String getLang(){
    	return lang;
    }
    
    public String getSecurityTab(){
    	return securityTab;
    }
    
}

