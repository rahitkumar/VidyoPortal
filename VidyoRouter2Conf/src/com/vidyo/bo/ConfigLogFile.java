package com.vidyo.bo;

public class ConfigLogFile implements Comparable<ConfigLogFile>{
	private String fileName;
	private String lastModified;
    private long length;
    private long lastModifiedLong;
	
    public ConfigLogFile(){
    	fileName = "";
    	lastModified = "";
    	lastModifiedLong = 0;
    	length = 0;
    }
    
    public void setFileName(String f){
    	fileName = f;
    }
    
    public void setLastModified(String m){
    	lastModified = m;
    }
    public void setLength(long l){
    	length = l;
    }
    public void setLastModifiedLong(long l){
    	lastModifiedLong = l;
    }
    
    public String getFileName(){
    	return fileName;
    }
    public String getLastModified(){
    	return lastModified;
    }
    public long getLength(){
    	return length;
    }
    public long getLastModifiedLong(){
    	return lastModifiedLong;
    }
    
    public int compareTo(ConfigLogFile compareObject) {
    	if (getLastModifiedLong() < compareObject.getLastModifiedLong()) return 1; 
    	else if (getLastModifiedLong() == compareObject.getLastModifiedLong())    		return 0; 
    	else return -1; 
    } 
    
}
