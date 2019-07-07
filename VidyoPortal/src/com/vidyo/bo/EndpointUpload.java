package com.vidyo.bo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name="EndpointUpload")
public class EndpointUpload implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    int endpointUploadID;
	
    int  tenantID;
    String endpointUploadFile;
    int endpointUploadTime;
    String endpointUploadType;
    int endpointUploadActive;
    String whoUploadFile;
    String endpointUploadVersion;
    
    public int getEndpointUploadID() {
        return endpointUploadID;
    }

    public void setEndpointUploadID(int endpointUploadID) {
        this.endpointUploadID = endpointUploadID;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public String getEndpointUploadFile() {
        return endpointUploadFile;
    }

    public void setEndpointUploadFile(String endpointUploadFile) {
        this.endpointUploadFile = endpointUploadFile;
    }

    public int getEndpointUploadTime() {
        return endpointUploadTime;
    }

    public void setEndpointUploadTime(int endpointUploadTime) {
        this.endpointUploadTime = endpointUploadTime;
    }

    public String getEndpointUploadType() {
        return endpointUploadType;
    }

    public void setEndpointUploadType(String endpointUploadType) {
        this.endpointUploadType = endpointUploadType;
    }

    public int getEndpointUploadActive() {
        return endpointUploadActive;
    }

    public void setEndpointUploadActive(int endpointUploadActive) {
        this.endpointUploadActive = endpointUploadActive;
    }

    public String getWhoUploadFile() {
        return whoUploadFile;
    }

    public void setWhoUploadFile(String whoUploadFile) {
        this.whoUploadFile = whoUploadFile;
    }

	public String getEndpointUploadVersion() {
		return endpointUploadVersion;
	}

	public void setEndpointUploadVersion(String endpointUploadVersion) {
		this.endpointUploadVersion = endpointUploadVersion;
	}

    
    
}
