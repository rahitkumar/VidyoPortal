package com.vidyo.bo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table (name="TenantConfiguration")
public class TenantConfiguration implements Serializable {

	private static final long serialVersionUID = -7093463620764085818L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int tenantId = -1; // id -1 can't exist, must be manually set to something valid

	private int extnLength = 4;

	private int endpointPrivateChat = 1;
    private int endpointPublicChat = 1;

	private int zincEnabled = 0;
	private String zincUrl = null;

	private int lectureModeAllowed = 1; // lecture mode feature available
    private int waitingRoomsEnabled = 0; // all rooms in waiting room mode (aka lecture mode) by default
    private int waitUntilOwnerJoins = 0; // cancel lecture mode when owner joins room that is in waiting mode (when waitingRoomsEnabled = 1)
    private int lectureModeStrict = 0; // disconnect endpoints that don't support lecture mode

    private int minPINLength = 3;

    private int sessionExpirationPeriod;
    private int maxCreatePublicRoomUser=0;
    private int maxCreatePublicRoomTenant=0;
    private int createPublicRoomEnable=0;

	private int minMediaPort = 0;
	private int maxMediaPort = 0;
	private int alwaysUseProxy = -1; // 0 = AUTO, 1 = ALWAYS

	private int userImage=0; // 0 = disabled, 1 = enabled
	private int uploadUserImage=0; // 0 = disabled, 1 = enabled

	private int vidyoNeoWebRTCGuestEnabled=1;  // Neo WebRTC for Guest enabled if 1
	private int vidyoNeoWebRTCUserEnabled=0;   // Neo WebRTC for User enabled if 1

	private int logAggregation=0;

	private int endpointBehavior=0;

	private int personalRoom = 0;

	private int testCall = 0;
	
	private String mobileProtocol;
	
	private String androidPackageName;
	
	private String iOSAppId;
	
	private String iOSAppLink;
	
	private String iOSBundleId;
	
	private String androidAppLink;
	
	private String extIntegrationSharedSecret;
	
	private int externalIntegrationMode;
	
	private String externalNotificationUrl;
	
	private String externalUsername;
	
	private String externalPassword;
	
	private int tytoIntegrationMode;
	
	private String tytoUrl;
	
	private String tytoUsername;
	
	private String tytoPassword;
	
	private String desktopProtocol;
	
	private String endpointUploadMode;
	
	private int roomCountThreshold = 40;
	
	private String vidyoNotificationUrl;
	
	private String vidyoUsername;
	
	private String vidyoPassword;
	
	private String tc;
	
	private int tcVersion;
	
	private String pp;
	
	private int ppVersion;

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public int getTcVersion() {
		return tcVersion;
	}

	public void setTcVersion(int tcVersion) {
		this.tcVersion = tcVersion;
	}

	public String getPp() {
		return pp;
	}

	public void setPp(String pp) {
		this.pp = pp;
	}

	public int getPpVersion() {
		return ppVersion;
	}

	public void setPpVersion(int ppVersion) {
		this.ppVersion = ppVersion;
	}

	public String getExternalUsername() {
		return externalUsername;
	}

	public void setExternalUsername(String externalUsername) {
		this.externalUsername = externalUsername;
	}

	public String getExternalPassword() {
		return externalPassword;
	}

	public void setExternalPassword(String externalPassword) {
		this.externalPassword = externalPassword;
	}

	public int getRoomCountThreshold() {
		return roomCountThreshold;
	}

	public void setRoomCountThreshold(int roomCountThreshold) {
		this.roomCountThreshold = roomCountThreshold;
	}

	public int getExtnLength() {
		return extnLength;
	}

	public void setExtnLength(int extnLength) {
		this.extnLength = extnLength;
	}

    public int getEndpointBehavior() {
		return endpointBehavior;
	}

	public void setEndpointBehavior(int endpointBehavior) {
		this.endpointBehavior = endpointBehavior;
	}

	public int getLogAggregation() {
		return logAggregation;
	}

	public void setLogAggregation(int logAggregation) {
		this.logAggregation = logAggregation;
	}

	public int getUserImage() {
		return userImage;
	}

	public void setUserImage(int userImage) {
		this.userImage = userImage;
	}

	public int getUploadUserImage() {
		return uploadUserImage;
	}

	public void setUploadUserImage(int uploadUserImage) {
		this.uploadUserImage = uploadUserImage;
	}

	public int getMaxCreatePublicRoomUser() {
		return maxCreatePublicRoomUser;
	}

	public void setMaxCreatePublicRoomUser(int maxCreatePublicRoomUser) {
		this.maxCreatePublicRoomUser = maxCreatePublicRoomUser;
	}

	public int getMaxCreatePublicRoomTenant() {
		return maxCreatePublicRoomTenant;
	}

	public void setMaxCreatePublicRoomTenant(int maxCreatePublicRoomTenant) {
		this.maxCreatePublicRoomTenant = maxCreatePublicRoomTenant;
	}

	public int getCreatePublicRoomEnable() {
		return createPublicRoomEnable;
	}

	public void setCreatePublicRoomEnable(int createPublicRoomEnable) {
		this.createPublicRoomEnable = createPublicRoomEnable;
	}

    /**
	 * @return the sessionExpirationPeriod
	 */
	public int getSessionExpirationPeriod() {
		return sessionExpirationPeriod;
	}

	/**
	 * @param sessionExpirationPeriod the sessionExpirationPeriod to set
	 */
	public void setSessionExpirationPeriod(int sessionExpirationPeriod) {
		this.sessionExpirationPeriod = sessionExpirationPeriod;
	}

	public int getTenantId() {
    	return tenantId;
    }

    public void setTenantId(int tenantId) {
    	this.tenantId = tenantId;
    }

    public int getEndpointPrivateChat() {
    	return endpointPrivateChat;
    }

    public void setEndpointPrivateChat(int endpointPrivateChat) {
    	this.endpointPrivateChat = endpointPrivateChat;
    }

    public int getEndpointPublicChat() {
    	return endpointPublicChat;
    }

    public void setEndpointPublicChat(int endpointPublicChat) {
    	this.endpointPublicChat = endpointPublicChat;
    }

	public int getZincEnabled() {
		return zincEnabled;
	}

	public void setZincEnabled(int zincEnabled) {
		this.zincEnabled = zincEnabled;
	}

	public String getZincUrl() {
		return zincUrl;
	}

	public void setZincUrl(String zincUrl) {
		this.zincUrl = zincUrl;
	}

	public int getLectureModeAllowed() {
		return lectureModeAllowed;
	}

	public void setLectureModeAllowed(int lectureModeAllowed) {
		this.lectureModeAllowed = lectureModeAllowed;
	}

    public int getWaitingRoomsEnabled() {
        return waitingRoomsEnabled;
    }

    public void setWaitingRoomsEnabled(int waitingRoomsEnabled) {
        this.waitingRoomsEnabled = waitingRoomsEnabled;
    }

    public int getWaitUntilOwnerJoins() {
        return waitUntilOwnerJoins;
    }

    public void setWaitUntilOwnerJoins(int waitUntilOwnerJoins) {
        this.waitUntilOwnerJoins = waitUntilOwnerJoins;
    }

    public int getLectureModeStrict() {
        return lectureModeStrict;
    }

    public void setLectureModeStrict(int lectureModeStrict) {
        this.lectureModeStrict = lectureModeStrict;
    }

    public int getMinPINLength() {
        return minPINLength;
    }

    public void setMinPINLength(int minPinLength) {
        this.minPINLength = minPinLength;
    }

	public int getMinMediaPort() {
		return minMediaPort;
	}

	public void setMinMediaPort(int minMediaPort) {
		this.minMediaPort = minMediaPort;
	}

	public int getMaxMediaPort() {
		return maxMediaPort;
	}

	public void setMaxMediaPort(int maxMediaPort) {
		this.maxMediaPort = maxMediaPort;
	}

	public int getAlwaysUseProxy() {
		return alwaysUseProxy;
	}

	public void setAlwaysUseProxy(int alwaysUseProxy) {
		this.alwaysUseProxy = alwaysUseProxy;
	}

	public int getVidyoNeoWebRTCGuestEnabled() {
		return vidyoNeoWebRTCGuestEnabled;
	}

	public void setVidyoNeoWebRTCGuestEnabled(int vidyoNeoWebRTCGuestEnabled) {
		this.vidyoNeoWebRTCGuestEnabled = vidyoNeoWebRTCGuestEnabled;
	}

	public int getVidyoNeoWebRTCUserEnabled() {
		return vidyoNeoWebRTCUserEnabled;
	}

	public void setVidyoNeoWebRTCUserEnabled(int vidyoNeoWebRTCUserEnabled) {
		this.vidyoNeoWebRTCUserEnabled = vidyoNeoWebRTCUserEnabled;
	}

	public String getEndpointUploadMode() {
		return endpointUploadMode;
	}

	public void setEndpointUploadMode(String endpointUploadMode) {
		this.endpointUploadMode = endpointUploadMode;
	}

	public int getPersonalRoom() {
		return personalRoom;
	}

	public void setPersonalRoom(int personalRoom) {
		this.personalRoom = personalRoom;
	}

	public int getTestCall() {
		return testCall;
	}

	public void setTestCall(int testCall) {
		this.testCall = testCall;
	}

	/**
	 * @return the mobileProtocol
	 */
	public String getMobileProtocol() {
		if(StringUtils.isNotBlank(this.mobileProtocol)) {
			return mobileProtocol;
		} else {
			return "vidyo";
		}
	}

	/**
	 * @param mobileProtocol the mobileProtocol to set
	 */
	public void setMobileProtocol(String mobileProtocol) {
		this.mobileProtocol = mobileProtocol;
	}

	/**
	 * @return the packageName
	 */
	public String getAndroidPackageName() {
		if(StringUtils.isNotBlank(this.androidPackageName)) {
			return androidPackageName;
		} else {
			return "com.vidyo.neomobile";
		}
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setAndroidPackageName(String androidPackageName) {
		this.androidPackageName = androidPackageName;
	}

	/**
	 * @return the iOSAppLink
	 */
	public String getiOSAppLink() {
		return iOSAppLink;
	}

	/**
	 * @param iOSAppLink the iOSAppLink to set
	 */
	public void setiOSAppLink(String iOSAppLink) {
		this.iOSAppLink = iOSAppLink;
	}

	/**
	 * @return the androidAppLink
	 */
	public String getAndroidAppLink() {
		return androidAppLink;
	}

	/**
	 * @param androidAppLink the androidAppLink to set
	 */
	public void setAndroidAppLink(String androidAppLink) {
		this.androidAppLink = androidAppLink;
	}

	/**
	 * @return the iOSBundleId
	 */
	public String getiOSBundleId() {
		return iOSBundleId;
	}

	/**
	 * @param iOSBundleId the iOSBundleId to set
	 */
	public void setiOSBundleId(String iOSBundleId) {
		this.iOSBundleId = iOSBundleId;
	}

	/**
	 * @return the externalNotificationUrl
	 */
	public String getExternalNotificationUrl() {
		return externalNotificationUrl;
	}

	/**
	 * @param externalNotificationUrl the externalNotificationUrl to set
	 */
	public void setExternalNotificationUrl(String externalNotificationUrl) {
		this.externalNotificationUrl = externalNotificationUrl;
	}

	/**
	 * @return the extIntegrationSharedSecret
	 */
	public String getExtIntegrationSharedSecret() {
		return extIntegrationSharedSecret;
	}

	/**
	 * @param extIntegrationSharedSecret the extIntegrationSharedSecret to set
	 */
	public void setExtIntegrationSharedSecret(String extIntegrationSharedSecret) {
		this.extIntegrationSharedSecret = extIntegrationSharedSecret;
	}

	/**
	 * @return the externalIntegrationMode
	 */
	public int getExternalIntegrationMode() {
		return externalIntegrationMode;
	}

	/**
	 * @param externalIntegrationMode the externalIntegrationMode to set
	 */
	public void setExternalIntegrationMode(int externalIntegrationMode) {
		this.externalIntegrationMode = externalIntegrationMode;
	}

	/**
	 * @return the desktopProtocol
	 */
	public String getDesktopProtocol() {
		return desktopProtocol != null ? this.desktopProtocol : "vidyo";
	}

	/**
	 * @param desktopProtocol the desktopProtocol to set
	 */
	public void setDesktopProtocol(String desktopProtocol) {
		this.desktopProtocol = desktopProtocol;
	}

	/**
	 * @return the iOSAppId
	 */
	public String getiOSAppId() {
		return iOSAppId;
	}

	/**
	 * @param iOSAppId the iOSAppId to set
	 */
	public void setiOSAppId(String iOSAppId) {
		this.iOSAppId = iOSAppId;
	}

	/**
	 * @return the vidyoNotificationUrl
	 */
	public String getVidyoNotificationUrl() {
		return vidyoNotificationUrl;
	}

	/**
	 * @param vidyoNotificationUrl the vidyoNotificationUrl to set
	 */
	public void setVidyoNotificationUrl(String vidyoNotificationUrl) {
		this.vidyoNotificationUrl = vidyoNotificationUrl;
	}

	/**
	 * @return the vidyoUsername
	 */
	public String getVidyoUsername() {
		return vidyoUsername;
	}

	/**
	 * @param vidyoUsername the vidyoUsername to set
	 */
	public void setVidyoUsername(String vidyoUsername) {
		this.vidyoUsername = vidyoUsername;
	}

	/**
	 * @return the vidyoPassword
	 */
	public String getVidyoPassword() {
		return vidyoPassword;
	}

	/**
	 * @param vidyoPassword the vidyoPassword to set
	 */
	public void setVidyoPassword(String vidyoPassword) {
		this.vidyoPassword = vidyoPassword;
	}

	public int getTytoIntegrationMode() {
		return tytoIntegrationMode;
	}

	public void setTytoIntegrationMode(int tytoIntegrationMode) {
		this.tytoIntegrationMode = tytoIntegrationMode;
	}

	public String getTytoUrl() {
		return tytoUrl;
	}

	public void setTytoUrl(String tytoUrl) {
		this.tytoUrl = tytoUrl;
	}

	public String getTytoUsername() {
		return tytoUsername;
	}

	public void setTytoUsername(String tytoUsername) {
		this.tytoUsername = tytoUsername;
	}

	public String getTytoPassword() {
		return tytoPassword;
	}

	public void setTytoPassword(String tytoPassword) {
		this.tytoPassword = tytoPassword;
	}
}
