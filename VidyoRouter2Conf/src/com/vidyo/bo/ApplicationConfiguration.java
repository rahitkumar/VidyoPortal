/**
 * 
 */
package com.vidyo.bo;

import java.io.Serializable;

/**
 * @author Ganesh
 * 
 */
public class ApplicationConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private int id;

	/**
	 * 
	 */
	private String appName;

	/**
	 * 
	 */
	private String networkInterface;

	/**
	 * 
	 */
	private int securePort = 0;
	
	/**
	 * 
	 */
	private int unsecurePort = 0;
	
	/**
	 * 
	 */
	private boolean ocsp;

	/**
	 * @return the securePort
	 */
	public int getSecurePort() {
		return securePort;
	}

	/**
	 * @param securePort the securePort to set
	 */
	public void setSecurePort(int securePort) {
		this.securePort = securePort;
	}

	/**
	 * @return the unsecurePort
	 */
	public int getUnsecurePort() {
		return unsecurePort;
	}

	/**
	 * @param unsecurePort the unsecurePort to set
	 */
	public void setUnsecurePort(int unsecurePort) {
		this.unsecurePort = unsecurePort;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the networkInterface
	 */
	public String getNetworkInterface() {
		return networkInterface;
	}

	/**
	 * @param networkInterface
	 *            the networkInterface to set
	 */
	public void setNetworkInterface(String networkInterface) {
		this.networkInterface = networkInterface;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the ocsp
	 */
	public boolean isOcsp() {
		return ocsp;
	}

	/**
	 * @param ocsp the ocsp to set
	 */
	public void setOcsp(boolean ocsp) {
		this.ocsp = ocsp;
	}

}