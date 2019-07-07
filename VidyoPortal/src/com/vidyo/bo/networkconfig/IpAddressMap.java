/**
 *
 */
package com.vidyo.bo.networkconfig;

import java.io.Serializable;

/**
 * @author Gokul
 *
 */
public class IpAddressMap implements Serializable{
    private int  addrMapID;
    private String localAddr;
    private String remoteAddr;

    /**
	 * @return the addrMapID
	 */
	public int getAddrMapID() {
		return addrMapID;
	}

	/**
	 * @param addrMapID the addrMapID to set
	 */
	public void setAddrMapID(int addrMapID) {
		this.addrMapID = addrMapID;
	}

    /**
	 * @return the localAddr
	 */
	public String getLocalAddr() {
		return localAddr;
	}

	/**
	 * @param localip the localAddr to set
	 */
	public void setLocalAddr(String localip) {
		this.localAddr = localip;
	}

    /**
	 * @return the remoteAddr
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}

	/**
	 * @param remoteAddr the remoteAddr to set
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
}
