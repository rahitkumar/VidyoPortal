package com.vidyo.bo;

import java.io.Serializable;

public class User implements Serializable{
    String username;
    String memberName;
    int memberID;
    int tenantID;
    int roomID;
    int langID;
    String userType = "R";
    boolean allowedToParticipate;
    int roomEnabled;
    String emailAddress;
    
    /**
     * 
     */
    private String sak;
    
    /**
     * 
     */
    private String pak;
    
    /**
     * 
     */
    private String pak2;
    
    /**
	 * @return the pak
	 */
	public String getPak() {
		return pak;
	}

	/**
	 * @param pak the pak to set
	 */
	public void setPak(String pak) {
		this.pak = pak;
	}

	/**
	 * @return the pak2
	 */
	public String getPak2() {
		return pak2;
	}

	/**
	 * @param pak2 the pak2 to set
	 */
	public void setPak2(String pak2) {
		this.pak2 = pak2;
	}

	/**
     * 
     */
    private long bindUserRequestID;
	private String userRole;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getRoomEnabled() {
        return roomEnabled;
    }

    public void setRoomEnabled(int roomEnabled) {
        this.roomEnabled = roomEnabled;
    }
    
    public int getLangID() {
        return langID;
    }

    public void setLangID(int langID) {
        this.langID = langID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public boolean isAllowedToParticipate() {
        return allowedToParticipate;
    }

    public void setAllowedToParticipate(boolean allowedToParticipate) {
        this.allowedToParticipate = allowedToParticipate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

	/**
	 * @return the sak
	 */
	public String getSak() {
		return sak;
	}

	/**
	 * @param sak the sak to set
	 */
	public void setSak(String sak) {
		this.sak = sak;
	}

	/**
	 * @return the bindUserRequestID
	 */
	public long getBindUserRequestID() {
		return bindUserRequestID;
	}

	/**
	 * @param bindUserRequestID the bindUserRequestID to set
	 */
	public void setBindUserRequestID(long bindUserRequestID) {
		this.bindUserRequestID = bindUserRequestID;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}