/**
 * 
 */
package com.vidyo.bo;

import java.io.Serializable;

/**
 * @author Ganesh
 *
 */
public class Configuration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private int configurationID;
	
	/**
	 * 
	 */
	private String configurationName;
	
	/**
	 * 
	 */
	private String configurationValue;

	/**
	 * @return the configurationID
	 */
	public int getConfigurationID() {
		return configurationID;
	}

	/**
	 * @param configurationID the configurationID to set
	 */
	public void setConfigurationID(int configurationID) {
		this.configurationID = configurationID;
	}

	/**
	 * @return the configurationName
	 */
	public String getConfigurationName() {
		return configurationName;
	}

	/**
	 * @param configurationName the configurationName to set
	 */
	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	/**
	 * @return the configurationValue
	 */
	public String getConfigurationValue() {
		return configurationValue;
	}

	/**
	 * @param configurationValue the configurationValue to set
	 */
	public void setConfigurationValue(String configurationValue) {
		this.configurationValue = configurationValue;
	} 

}
