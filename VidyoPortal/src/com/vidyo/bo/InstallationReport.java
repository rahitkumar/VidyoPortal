package com.vidyo.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstallationReport implements Comparable<InstallationReport> {
    private String fileName;
    private String timestamp;
    
    protected static final Logger logger = LoggerFactory.getLogger(InstallationReport.class);

    public InstallationReport(String fn, String d){
        this.fileName = fn;
        this.timestamp = d;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setFileName(String fn){
        this.fileName = fn;
    }

    public String getTimestamp(){
        return this.timestamp;
    }

    public void setTimestamp(String d){
        this.timestamp = d;
    }

    public String toString() {
        return "fileName="+fileName+",timestamp=" + timestamp;
    }

	@Override
	public int compareTo(InstallationReport o) {
		int compareTimestamp = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		try {
			Date thisTimestamp = sdf.parse(timestamp);
			Date otherTimestamp = sdf.parse(o.getTimestamp());
			
			compareTimestamp = otherTimestamp.compareTo(thisTimestamp);
		} catch (ParseException e) {
			logger.error("Excetion occure during timstamp comperotion.", e.getMessage()); 
		}
		return compareTimestamp;
	}
}
