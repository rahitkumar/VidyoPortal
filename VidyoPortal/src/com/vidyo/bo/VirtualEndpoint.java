package com.vidyo.bo;

import java.io.Serializable;
import java.sql.Timestamp;

public class VirtualEndpoint implements Serializable {
    int endpointID;
    int serviceID;
    String gatewayID;
    String displayName;
    String displayExt;
    String endpointGUID;
    int direction; //0 - fromLegacy, 1 - toLegacy
    int status;
    String prefix;
    int tenantID;
    String tenantName;
    Timestamp updateTime;
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

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEndpointGUID() {
        return endpointGUID;
    }

    public void setEndpointGUID(String endpointGUID) {
        this.endpointGUID = endpointGUID;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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

    public String getDisplayExt() {
        return displayExt;
    }

    public void setDisplayExt(String displayExt) {
        this.displayExt = displayExt;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

	public long getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(long sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public int getEntityID() {
		return entityID;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

}