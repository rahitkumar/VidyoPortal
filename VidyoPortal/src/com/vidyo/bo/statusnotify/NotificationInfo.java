/**
 *
 */
package com.vidyo.bo.statusnotify;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.Room;

/**
 * @author ganesh
 *
 */
public class NotificationInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1075373095388235509L;

    /**
     *
     */
    private long sequenceNum;

    /**
     *
     */
    private Room roomNotification;

    /**
     *
     */
    private CDRinfo2 userNotification;

    /**
     *
     */
    private Alert alert;

    /**
     *
     */
    private long creationTimestamp;

    /**
     *
     */
    private long queueTimestamp;

    /**
     *
     */
    private long wireTimestamp;
   
    private String externalStatusNotificationUrl;
    
	private String externalUsername;
	
	private String externalPassword;
	
	private String plainTextExternalPassword;
	
	private String vidyoStatusNotificationUrl;
	
	private String vidyoUsername;
	
	private String vidyoPassword;
	
	private String plainTextVidyoPassword;
	
	private int tenantId;
	
    public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the externalStatusNotificationUrl
	 */
	public String getExternalStatusNotificationUrl() {
		return externalStatusNotificationUrl;
	}

	/**
	 * @param externalStatusNotificationUrl the externalStatusNotificationUrl to set
	 */
	public void setExternalStatusNotificationUrl(String externalStatusNotificationUrl) {
		this.externalStatusNotificationUrl = externalStatusNotificationUrl;
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

	public String getPlainTextExternalPassword() {
		return plainTextExternalPassword;
	}

	public void setPlainTextExternalPassword(String plainTextExternalPassword) {
		this.plainTextExternalPassword = plainTextExternalPassword;
	}

	public String getPlainTextVidyoPassword() {
		return plainTextVidyoPassword;
	}

	public void setPlainTextVidyoPassword(String plainTextVidyoPassword) {
		this.plainTextVidyoPassword = plainTextVidyoPassword;
	}

	/**
     * @return the creationTimestamp
     */
    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * @param creationTimestamp the creationTimestamp to set
     */
    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * @return the queueTimestamp
     */
    public long getQueueTimestamp() {
        return queueTimestamp;
    }

    /**
     * @param queueTimestamp the queueTimestamp to set
     */
    public void setQueueTimestamp(long queueTimestamp) {
        this.queueTimestamp = queueTimestamp;
    }

    /**
     * @return the wireTimestamp
     */
    public long getWireTimestamp() {
        return wireTimestamp;
    }

    /**
     * @param wireTimestamp the wireTimestamp to set
     */
    public void setWireTimestamp(long wireTimestamp) {
        this.wireTimestamp = wireTimestamp;
    }

    /**
     * @return the alert
     */
    public Alert getAlert() {
        return alert;
    }

    /**
     * @param alert the alert to set
     */
    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    /**
     * @return the sequenceNum
     */
    public long getSequenceNum() {
        return sequenceNum;
    }

    /**
     * @param sequenceNum
     *            the sequenceNum to set
     */
    public void setSequenceNum(long sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    /**
     * @return the roomNotification
     */
    public Room getRoomNotification() {
        return roomNotification;
    }

    /**
     * @param roomNotification the roomNotification to set
     */
    public void setRoomNotification(Room roomNotification) {
        this.roomNotification = roomNotification;
    }

    /**
     * @return the userNotification
     */
    public CDRinfo2 getUserNotification() {
        return userNotification;
    }

    /**
     * @param userNotification the userNotification to set
     */
    public void setUserNotification(CDRinfo2 userNotification) {
        this.userNotification = userNotification;
    }

	/**
	 * @return the vidyoStatusNotificationUrl
	 */
	public String getVidyoStatusNotificationUrl() {
		return vidyoStatusNotificationUrl;
	}

	/**
	 * @param vidyoStatusNotificationUrl the vidyoStatusNotificationUrl to set
	 */
	public void setVidyoStatusNotificationUrl(String vidyoStatusNotificationUrl) {
		this.vidyoStatusNotificationUrl = vidyoStatusNotificationUrl;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
