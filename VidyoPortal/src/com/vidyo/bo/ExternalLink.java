package com.vidyo.bo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExternalLink implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int externalLinkID;

    String requestID;

    String fromSystemID;
    String fromTenantHost;
    String fromConferenceName;

    String toSystemID;
    String toTenantHost;
    String toConferenceName;

    String localMediaAddress;
    String localMediaAdditionalInfo;

    String remoteMediaAddress;
    String remoteMediaAdditionalInfo;

	/**
     * 
     */
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.toString();
	}

    int status;
    
    /**
     * 
     */
    private String uniqueCallID;
    
    /**
     * 
     */
    private Date creationTime;
    
    /**
     * 
     */
    private Date modificationTime;
    
    /**
     * Remote Portal HTTP/HTTPS flag
     */
    private String secure;

    /**
	 * @return the secure
	 */
	public String getSecure() {
		return secure;
	}

	/**
	 * @param secure the secure to set
	 */
	public void setSecure(String secure) {
		this.secure = secure;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return modificationTime;
	}

	/**
	 * @param modificationTime the modificationTime to set
	 */
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	/**
	 * @return the uniqueCallId
	 */
	public String getUniqueCallID() {
		return uniqueCallID;
	}

	/**
	 * @param uniqueCallId the uniqueCallId to set
	 */
	public void setUniqueCallID(String uniqueCallID) {
		this.uniqueCallID = uniqueCallID;
	}

	public int getExternalLinkID() {
        return externalLinkID;
    }

    public void setExternalLinkID(int externalLinkID) {
        this.externalLinkID = externalLinkID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getFromSystemID() {
        return fromSystemID;
    }

    public void setFromSystemID(String fromSystemID) {
        this.fromSystemID = fromSystemID;
    }

    public String getFromTenantHost() {
        return fromTenantHost;
    }

    public void setFromTenantHost(String fromTenantHost) {
        this.fromTenantHost = fromTenantHost;
    }

    public String getFromConferenceName() {
        return fromConferenceName;
    }

    public void setFromConferenceName(String fromConferenceName) {
        this.fromConferenceName = fromConferenceName;
    }

    public String getToSystemID() {
        return toSystemID;
    }

    public void setToSystemID(String toSystemID) {
        this.toSystemID = toSystemID;
    }

    public String getToTenantHost() {
        return toTenantHost;
    }

    public void setToTenantHost(String toTenantHost) {
        this.toTenantHost = toTenantHost;
    }

    public String getToConferenceName() {
        return toConferenceName;
    }

    public void setToConferenceName(String toConferenceName) {
        this.toConferenceName = toConferenceName;
    }

    public String getLocalMediaAddress() {
        return localMediaAddress;
    }

    public void setLocalMediaAddress(String localMediaAddress) {
        this.localMediaAddress = localMediaAddress;
    }

    public String getLocalMediaAdditionalInfo() {
        return localMediaAdditionalInfo;
    }

    public void setLocalMediaAdditionalInfo(String localMediaAdditionalInfo) {
        this.localMediaAdditionalInfo = localMediaAdditionalInfo;
    }

    public String getRemoteMediaAddress() {
        return remoteMediaAddress;
    }

    public void setRemoteMediaAddress(String remoteMediaAddress) {
        this.remoteMediaAddress = remoteMediaAddress;
    }

    public String getRemoteMediaAdditionalInfo() {
        return remoteMediaAdditionalInfo;
    }

    public void setRemoteMediaAdditionalInfo(String remoteMediaAdditionalInfo) {
        this.remoteMediaAdditionalInfo = remoteMediaAdditionalInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
