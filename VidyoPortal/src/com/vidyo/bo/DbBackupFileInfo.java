package com.vidyo.bo;

import java.util.*;


public class DbBackupFileInfo implements Comparable<DbBackupFileInfo> {
    String fileName;
    Date   timeStamp;
    long   timeStampMS;

    public DbBackupFileInfo(String fn, Date d){
        this.fileName = fn;
        this.timeStamp = d;
        timeStampMS = d.getTime();
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setFileName(String fn){
        this.fileName = fn;
    }

    public Date getTimeStamp(){
        return this.timeStamp;
    }

    public void setTimeStamp(Date d){
        this.timeStamp = d;
    }

    public long getTimeStampMS(){
        return this.timeStampMS;
    }

    public void getTimeStampMS(long ms){
        this.timeStampMS = ms;
    }

    //implements Comparable interface
    public int compareTo(DbBackupFileInfo o) {
        if(o.timeStampMS == this.timeStampMS)
            return -1;
        else
            return (int)((o.timeStampMS-this.timeStampMS) / Math.abs(o.timeStampMS-this.timeStampMS));
    }
}
