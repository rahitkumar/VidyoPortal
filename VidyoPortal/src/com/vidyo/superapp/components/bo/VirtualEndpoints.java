package com.vidyo.superapp.components.bo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

@Entity
@Table(name = "VirtualEndpoints")
public class VirtualEndpoints implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    int endpointID;
	
    @JsonSerialize(using=StringSerializer.class)
    String gatewayID;
    String displayName;
    String displayExt;
    String endpointGUID;
    int direction; //0 - fromLegacy, 1 - toLegacy
    int status;
    String prefix;
    int tenantID;
    Timestamp updateTime;
	int entityID;
	long sequenceNum;

	@ManyToOne
	@JoinColumn(name = "serviceID", insertable = false, updatable = false)
	@JsonIgnore
	private VidyoGateway vidyoGateway;

    public int getEndpointID() {
        return endpointID;
    }

    public void setEndpointID(int endpointID) {
        this.endpointID = endpointID;
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

	public VidyoGateway getVidyoGateway() {
		return vidyoGateway;
	}

	public void setVidyoGateway(VidyoGateway vidyoGateway) {
		this.vidyoGateway = vidyoGateway;
	}
	
}