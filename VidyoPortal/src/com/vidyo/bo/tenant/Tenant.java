/**
 * 
 */
package com.vidyo.bo.tenant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ysakurikar
 *
 */
@Entity
@Table (name="Tenant")
public class Tenant implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tenantId;

	@Column (name="tenantName")
	private String tenantName;

	@Column (name="tenantUrl")
	private String tenantURL;

	private String tenantPrefix;

	private String tenantDialIn;

	private String description;
	

	private String tenantWebRTCURL;

    private String vidyoGatewayControllerDns;

    /**
     * Flag indicating if MobileLogin is enabled/disabled
     */
    private int mobileLogin;

    /**
     *
     */
    private int scheduledRoomEnabled;


    /**
	 * @return the scheduledRoomEnabled
	 */
	public int getScheduledRoomEnabled() {
		return scheduledRoomEnabled;
	}

	/**
	 * @param scheduledRoomEnabled the scheduledRoomEnabled to set
	 */
	public void setScheduledRoomEnabled(int scheduledRoomEnabled) {
		this.scheduledRoomEnabled = scheduledRoomEnabled;
	}

	/**
	 * @return the mobileLogin
	 */
	public int getMobileLogin() {
		return mobileLogin;
	}

	/**
	 * @param mobileLogin the mobileLogin to set
	 */
	public void setMobileLogin(int mobileLogin) {
		this.mobileLogin = mobileLogin;
	}

	public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantURL() {
        return tenantURL;
    }

    public void setTenantURL(String tenantURL) {
        this.tenantURL = tenantURL;
    }

    public String getTenantPrefix() {
        return tenantPrefix;
    }

    public void setTenantPrefix(String tenantPrefix) {
        this.tenantPrefix = tenantPrefix;
    }

    public String getTenantDialIn() {
        return tenantDialIn;
    }

    public void setTenantDialIn(String tenantDialIn) {
        this.tenantDialIn = tenantDialIn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVidyoGatewayControllerDns() {
    	return vidyoGatewayControllerDns;
    }

    public void setVidyoGatewayControllerDns(String vidyoGatewayControllerDns) {
    	this.vidyoGatewayControllerDns = vidyoGatewayControllerDns;
    }

	public String getTenantWebRTCURL() {
		return tenantWebRTCURL;
	}

	public void setTenantWebRTCURL(String tenantWebRTCURL) {
		this.tenantWebRTCURL = tenantWebRTCURL;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tenantId;
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Tenant)) {
			return false;
		}
		Tenant other = (Tenant) obj;
		if (tenantId != other.tenantId) {
			return false;
		}
		return true;
	}
	
	
	

}
