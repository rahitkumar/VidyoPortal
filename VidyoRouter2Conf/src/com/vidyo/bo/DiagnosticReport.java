package com.vidyo.bo;

import java.util.Date;

public class DiagnosticReport {
    String fileName;
    String timestamp;

    public DiagnosticReport(String fn, String d){
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
}
