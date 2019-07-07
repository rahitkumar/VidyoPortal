/**
 * 
 */
package com.vidyo.bo;

import java.io.Serializable;

/**
 * @author Ganesh
 *
 */
public class TenantIpc implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private int ipcWhiteListId;
	
	/**
	 * 
	 */
	private int ipcID;
	
	/**
	 * 
	 */
	private int inbound;
	
	/**
	 * 
	 */
	private int outbound;
	
	/**
	 * 
	 */
	private int tenantID;
	
	/**
	 * 
	 */
	private int superManaged;
	
	/**
	 * 
	 */
	private String hostName;
	
	/**
	 * 
	 */
	private int whiteList;

	/**
	 * @return the tenantID
	 */
	public int getTenantID() {
		return tenantID;
	}

	/**
	 * @param tenantID the tenantID to set
	 */
	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the whiteList
	 */
	public int getWhiteList() {
		return whiteList;
	}

	/**
	 * @param whiteList the whiteList to set
	 */
	public void setWhiteList(int whiteList) {
		this.whiteList = whiteList;
	}

	/**
	 * @return the superManaged
	 */
	public int getSuperManaged() {
		return superManaged;
	}

	/**
	 * @param superManaged the superManaged to set
	 */
	public void setSuperManaged(int superManaged) {
		this.superManaged = superManaged;
	}

	/**
	 * @return the ipcWhiteListId
	 */
	public int getIpcWhiteListId() {
		return ipcWhiteListId;
	}

	/**
	 * @param ipcWhiteListId the ipcWhiteListId to set
	 */
	public void setIpcWhiteListId(int ipcWhiteListId) {
		this.ipcWhiteListId = ipcWhiteListId;
	}

	/**
	 * @return the ipcID
	 */
	public int getIpcID() {
		return ipcID;
	}

	/**
	 * @param ipcID the ipcID to set
	 */
	public void setIpcID(int ipcID) {
		this.ipcID = ipcID;
	}

	/**
	 * @return the inbound
	 */
	public int getInbound() {
		return inbound;
	}

	/**
	 * @param inbound the inbound to set
	 */
	public void setInbound(int inbound) {
		this.inbound = inbound;
	}

	/**
	 * @return the outbound
	 */
	public int getOutbound() {
		return outbound;
	}

	/**
	 * @param outbound the outbound to set
	 */
	public void setOutbound(int outbound) {
		this.outbound = outbound;
	}
	

}
