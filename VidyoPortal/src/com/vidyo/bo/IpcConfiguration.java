/**
 * 
 */
package com.vidyo.bo;

import java.io.Serializable;

/**
 * @author Ganesh
 * 
 */
public class IpcConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private int ipcID;

	/**
	 * 
	 */
	private int tenantID;

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
	private int inboundLines;

	/**
	 * 
	 */
	private int outboundLines;
	
	/**
	 * 
	 */
	private int allowed;

	/**
	 * @return the tenantID
	 */
	public int getTenantID() {
		return tenantID;
	}

	/**
	 * @param tenantID
	 *            the tenantID to set
	 */
	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * @return the inbound
	 */
	public int getInbound() {
		return inbound;
	}

	/**
	 * @param inbound
	 *            the inbound to set
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
	 * @param outbound
	 *            the outbound to set
	 */
	public void setOutbound(int outbound) {
		this.outbound = outbound;
	}

	/**
	 * @return the inboundLines
	 */
	public int getInboundLines() {
		return inboundLines;
	}

	/**
	 * @param inboundLines
	 *            the inboundLines to set
	 */
	public void setInboundLines(int inboundLines) {
		this.inboundLines = inboundLines;
	}

	/**
	 * @return the outboundLines
	 */
	public int getOutboundLines() {
		return outboundLines;
	}

	/**
	 * @param outboundLines
	 *            the outboundLines to set
	 */
	public void setOutboundLines(int outboundLines) {
		this.outboundLines = outboundLines;
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

	public int getAllowed() {
		return allowed;
	}

	public void setAllowed(int allowed) {
		this.allowed = allowed;
	}

}
