package com.vidyo.bo.security;

public class CSRObject {
	private String countryName;
	private String stateOrProvinceName;
	private String localityName;
	private String organizationName;
	private String organizationalUnitName;
	private String commonName;
	private String emailAddress;
	
	public CSRObject(){
		countryName         = "";
		stateOrProvinceName = "";
		localityName        = "";
		organizationName    = "";
		commonName          = "";
		emailAddress        = "";
		organizationalUnitName = "";
	}
	
	public void setCountryName(String s){
		countryName = s;
	}
	public void setStateOrProvinceName(String s){
		stateOrProvinceName = s;
	}
	public void setLocalityName(String s){
		localityName = s;
	}
	public void setOrganizationName(String s){
		organizationName = s;
	}
	public void setOrganizationalUnitName(String s){
		organizationalUnitName = s;
	}
	public void setCommonName(String s){
		commonName = s;
	}
	public void setEmailAddress(String s){
		emailAddress = s;
	}
	
	public String getCountryName(){
		return countryName;
	}
	public String getStateOrProvinceName(){
		return stateOrProvinceName;
	}
	public String getLocalityName(){
		return localityName;
	}
	public String getOrganizationName(){
		return organizationName;
	}
	public String getOrganizationalUnitName(){
		return organizationalUnitName;
	}
	public String getCommonName(){
		return commonName;
	}
	public String getEmailAddress(){
		return emailAddress;
	}
}
