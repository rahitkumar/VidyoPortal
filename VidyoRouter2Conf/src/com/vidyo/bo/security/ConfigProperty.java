package com.vidyo.bo.security;

public class ConfigProperty {
	private String name;
    private String value;
    
    public ConfigProperty(){
    	name = "";
    	value = "";
    }
    
    public void setName(String n){
    	name = n;
    }
    public void setValue(String v){
    	value = v;
    }
    public String getName(){
    	return name;
    }
    public String getValue(){
    	return value;
    }
    
    
    
}
