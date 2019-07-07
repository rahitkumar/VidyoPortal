package com.vidyo.bo;

import java.io.Serializable;
import java.sql.Timestamp;

public class RecorderEndpoint implements Serializable {
    int endpointID;
    int serviceID;
    String recID;
    String description;
    String endpointGUID;
    int status;
    String prefix;
    Timestamp updateTime;
    int webcast = 0;
    int entityID;
	long sequenceNum;

    public int getEndpointID() {
        return endpointID;
    }

    public void setEndpointID(int endpointID) {
        this.endpointID = endpointID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getRecID() {
        return recID;
    }

    public void setRecID(String recID) {
        this.recID = recID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndpointGUID() {
        return endpointGUID;
    }

    public void setEndpointGUID(String endpointGUID) {
        this.endpointGUID = endpointGUID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getWebcast() {
        return webcast;
    }

    public void setWebcast(int webcast) {
        this.webcast = webcast;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

	public long getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(long sequenceNum) {
		this.sequenceNum = sequenceNum;
	}
}