package com.vidyo.bo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name="Tenant")
public class Tenant implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tenantID;
	
	private String tenantName;
	private String tenantURL;
	private String tenantPrefix;
	private String tenantDialIn;
	private String description;
	private String callTo;
	private String routers;
	private String services;
	private String locations;
	private int installs;
	private int seats;
	private int ports;
	private int guestLogin;
	private int executives;
	private int panoramas;
	private int publicRooms;
	private int logAggregation;
	private int endpointBehavior;
	private String tenantReplayURL;
	private String tenantWebRTCURL;
	private String endpointUploadMode;

    private String vidyoGatewayControllerDns;
    
    @Transient
    private List<Integer> allowedGateways;
    @Transient
    private List<Integer> allowedReplays;
    @Transient
    private List<Integer> allowedRecorders;
    @Transient
    private List<Integer> allowedLocationTags;
    @Transient
    private List<Integer> allowedProxies;

    /**
     * Flag indicating inboundIPC enabling
     */
    private Integer inbound;

    /**
     * Flag indicating outboundIPC enabling
     */
    private Integer outbound;

    /**
     * Flag indicating if MobileLogin is enabled/disabled
     */
    private int mobileLogin;

    /**
     *
     */
    private int scheduledRoomEnabled;

    /**
     *
     */
    private String requestScheme;
    
    /**
     * 
     */
    private String enableEpic;
    
    /**
     * 
     */
    private String sharedSecret; 
    
    /**
     * 
     */
    private String notificationUrl;
    
    private String notificationUser;
    
    private String notificationPassword;


	public String getNotificationUser() {
		return notificationUser;
	}

	public void setNotificationUser(String notificationUser) {
		this.notificationUser = notificationUser;
	}

	public String getNotificationPassword() {
		return notificationPassword;
	}

	public void setNotificationPassword(String notificationPassword) {
		this.notificationPassword = notificationPassword;
	}

	/**
	 * @return the notificationUrl
	 */
    public String getNotificationUrl() {
		return notificationUrl;
	}

    /**
	 * @param notificationUrl the notificationUrl to set
	 */
	public void setNotificationUrl(String notificationUrl) {
		this.notificationUrl = notificationUrl;
	}

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

    public String getCallTo() {
        return callTo;
    }

    public void setCallTo(String callTo) {
        this.callTo = callTo;
    }

    public int getInstalls() {
        return installs;
    }

    public void setInstalls(int installs) {
        this.installs = installs;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getPorts() {
        return ports;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }

    public int getGuestLogin() {
        return guestLogin;
    }

    public void setGuestLogin(int guestLogin) {
        this.guestLogin = guestLogin;
    }

    public String getRouters() {
        return routers;
    }

    public void setRouters(String routers) {
        this.routers = routers;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getTenantReplayURL() {
        return tenantReplayURL;
    }

    public void setTenantReplayURL(String tenantReplayURL) {
        this.tenantReplayURL = tenantReplayURL;
    }

    public String getVidyoGatewayControllerDns() {
    	return vidyoGatewayControllerDns;
    }

    public void setVidyoGatewayControllerDns(String vidyoGatewayControllerDns) {
    	this.vidyoGatewayControllerDns = vidyoGatewayControllerDns;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public int getExecutives() {
        return executives;
    }

    public void setExecutives(int executives) {
        this.executives = executives;
    }

	public int getPanoramas() {
		return panoramas;
	}

	public void setPanoramas(int panoramas) {
		this.panoramas = panoramas;
	}

	/**
	 * @return the inbound
	 */
	public Integer getInbound() {
		return inbound;
	}

	/**
	 * @param inbound the inbound to set
	 */
	public void setInbound(Integer inbound) {
		if (inbound == null)
			inbound = 0;
		this.inbound = inbound;
	}

	/**
	 * @return the outbound
	 */
	public Integer getOutbound() {
		return outbound;
	}

	/**
	 * @param outbound the outbound to set
	 */
	public void setOutbound(Integer outbound) {
		if (outbound == null)
			outbound = 0;
		this.outbound = outbound;
	}

	/**
	 * @return the requestScheme
	 */
	public String getRequestScheme() {
		return requestScheme;
	}

	/**
	 * @param requestScheme the requestScheme to set
	 */
	public void setRequestScheme(String requestScheme) {
		this.requestScheme = requestScheme;
	}

	public int getPublicRooms() {
		return publicRooms;
	}

	public void setPublicRooms(int publicRooms) {
		this.publicRooms = publicRooms;
	}

	public String getTenantWebRTCURL() {
		return tenantWebRTCURL;
	}

	public void setTenantWebRTCURL(String tenantWebRTCURL) {
		this.tenantWebRTCURL = tenantWebRTCURL;
	}

	public int getLogAggregation() {
		return logAggregation;
	}

	public void setLogAggregation(int logAggregation) {
		this.logAggregation = logAggregation;
	}

	public String getEndpointUploadMode() {
		return endpointUploadMode;
	}

	public void setEndpointUploadMode(String endpointUploadMode) {
		this.endpointUploadMode = endpointUploadMode;
	}
	
	public int getEndpointBehavior() {
		return endpointBehavior;
	}

	public void setEndpointBehavior(int endpointBehavior) {
		this.endpointBehavior = endpointBehavior;
	}
	
	public int getCustomRole() {
		return endpointBehavior;
	}

	public void setCustomRole(int endpointBehavior) {
		this.endpointBehavior = endpointBehavior;
	}

	/**
	 * @return the allowedGateways
	 */
	public List<Integer> getAllowedGateways() {
		return allowedGateways;
	}

	/**
	 * @param allowedGateways the allowedGateways to set
	 */
	public void setAllowedGateways(List<Integer> allowedGateways) {
		this.allowedGateways = allowedGateways;
	}

	/**
	 * @return the allowedReplays
	 */
	public List<Integer> getAllowedReplays() {
		return allowedReplays;
	}

	/**
	 * @param allowedReplays the allowedReplays to set
	 */
	public void setAllowedReplays(List<Integer> allowedReplays) {
		this.allowedReplays = allowedReplays;
	}

	/**
	 * @return the allowedRecorders
	 */
	public List<Integer> getAllowedRecorders() {
		return allowedRecorders;
	}

	/**
	 * @param allowedRecorders the allowedRecorders to set
	 */
	public void setAllowedRecorders(List<Integer> allowedRecorders) {
		this.allowedRecorders = allowedRecorders;
	}

	/**
	 * @return the allowedLocationTags
	 */
	public List<Integer> getAllowedLocationTags() {
		return allowedLocationTags;
	}

	/**
	 * @param allowedLocationTags the allowedLocationTags to set
	 */
	public void setAllowedLocationTags(List<Integer> allowedLocationTags) {
		this.allowedLocationTags = allowedLocationTags;
	}

	/**
	 * @return the allowedProxies
	 */
	public List<Integer> getAllowedProxies() {
		return allowedProxies;
	}

	/**
	 * @param allowedProxies the allowedProxies to set
	 */
	public void setAllowedProxies(List<Integer> allowedProxies) {
		this.allowedProxies = allowedProxies;
	}

	/**
	 * @return the enableEpic
	 */
	public String getEnableEpic() {
		return enableEpic;
	}

	/**
	 * @param enableEpic the enableEpic to set
	 */
	public void setEnableEpic(String enableEpic) {
		this.enableEpic = enableEpic;
	}

	/**
	 * @return the sharedSecret
	 */
	public String getSharedSecret() {
		return sharedSecret;
	}

	/**
	 * @param sharedSecret the sharedSecret to set
	 */
	public void setSharedSecret(String sharedSecret) {
		this.sharedSecret = sharedSecret;
	}
}
