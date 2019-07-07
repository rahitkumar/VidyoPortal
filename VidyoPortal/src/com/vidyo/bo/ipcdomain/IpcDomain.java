/**
 * 
 */
package com.vidyo.bo.ipcdomain;

import java.io.Serializable;

/**
 * @author Ganesh
 *
 */
public class IpcDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private int domainID;

	/**
	 * 
	 */
	private String domainName;
	
	/**
	 * 
	 */
	private int whiteFlag;

	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}

	/**
	 * @param domainName the domainName to set
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	/**
	 * @return the whiteFlag
	 */
	public int getWhiteFlag() {
		return whiteFlag;
	}

	/**
	 * @param whiteFlag the whiteFlag to set
	 */
	public void setWhiteFlag(int whiteFlag) {
		this.whiteFlag = whiteFlag;
	}

	/**
	 * @return the domainID
	 */
	public int getDomainID() {
		return domainID;
	}

	/**
	 * @param domainID the domainID to set
	 */
	public void setDomainID(int domainID) {
		this.domainID = domainID;
	}
}
