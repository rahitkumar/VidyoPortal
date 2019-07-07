package com.vidyo.bo;

import java.io.Serializable;
import java.util.Date;

public class NEConfiguration implements Serializable {
    String  identifier;         //Network Element System ID
    String  displayName;
    String  componentType;
    String  ipAddress;
    String  data;
    int     runningVersion;
    int     version;
    String  alarm;
    String  swVer;
    int    expirySeconds;
    String  status;
    Date    lastModified;
    Date    heartbeat;

	// new fields
	String URL;
    String webApplicationURL;

    public NEConfiguration(){
        
    }

    public String getIdentifier(){
        return identifier;
    }
    public void setIdentifier(String iden) {
        this.identifier = iden;
    }

    public String getDisplayName() {
        return this.displayName;
    }
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public String getComponentType() {
        return this.componentType;
    }
    public void setComponentType(String type){
        this.componentType = type;
    }

    public String getIpAddress(){
        return this.ipAddress;
    }
    public void setIpAddress(String ip) {
        this.ipAddress = ip;
    }

    public String getData(){
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public int getRunningVersion(){
        return this.runningVersion;
    }
    public void setRunningVersion(int ver){
        this.runningVersion = ver;
    }

    public int getVersion(){
        return this.version;
    }
    public void setVersion(int ver){
        this.version = ver;
    }

    public int getExpirySeconds() {
        return this.expirySeconds;
    }
    public void setExpirySeconds(int dt){
        this.expirySeconds = dt;
    }

    public String getStatus(){
        return this.status;
    }
    public void setStatus(String st){
        this.status = st;
    }

    public Date getLastModified() {
        return this.lastModified;
    }
    public void setLastModified(Date dt){
        this.lastModified = dt;
    }


    public String getAlarm(){
        return this.alarm;
    }
    public void setAlarm(String t){
        this.alarm = t;
    }

    public String getSwVer() {
        return this.swVer;
    }
    public void setSwVer(String ver){
        this.swVer = ver;
    }

    public Date getHeartbeat(){
        return this.heartbeat;
    }
    public void setHeartbeat(Date ht){
        this.heartbeat = ht;
    }

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}
    public String getWebApplicationURL() {
		return webApplicationURL;
	}

	public void setWebApplicationURL(String webApplicationURL) {
		this.webApplicationURL = webApplicationURL;
	}
}
