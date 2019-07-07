/**
 * 
 */
package com.vidyo.bo.networkconfig;

import java.io.Serializable;

/**
 * @author Ganesh
 *
 */
public class RouterPool implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String routerPoolID;
	
	/**
	 * 
	 */
	private String routerPoolName;
	
	/**
	 * 
	 */
	private boolean ipcFlag;

	/**
	 * @return the routerPoolID
	 */
	public String getRouterPoolID() {
		return routerPoolID;
	}

	/**
	 * @param routerPoolID the routerPoolID to set
	 */
	public void setRouterPoolID(String routerPoolID) {
		this.routerPoolID = routerPoolID;
	}

	/**
	 * @return the routerPoolName
	 */
	public String getRouterPoolName() {
		return routerPoolName;
	}

	/**
	 * @param routerPoolName the routerPoolName to set
	 */
	public void setRouterPoolName(String routerPoolName) {
		this.routerPoolName = routerPoolName;
	}

	/**
	 * @return the ipcFlag
	 */
	public boolean isIpcFlag() {
		return ipcFlag;
	}

	/**
	 * @param ipcFlag the ipcFlag to set
	 */
	public void setIpcFlag(boolean ipcFlag) {
		this.ipcFlag = ipcFlag;
	}

}