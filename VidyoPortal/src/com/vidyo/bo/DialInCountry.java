/**
 *
 */
package com.vidyo.bo;

import java.util.Map;

/**
 * @author ysakurikar
 *
 */
public class DialInCountry {
	int countryId;
	String countryName;
	String countryFlagPath;

	Map<String, String> dialinLabelToNumberMap;
	/**
	 * @return the countryId
	 */
	public int getCountryId() {
		return countryId;
	}
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/**
	 * @return the countryFlagPath
	 */
	public String getCountryFlagPath() {
		return countryFlagPath;
	}
	/**
	 * @param countryFlagPath the countryFlagPath to set
	 */
	public void setCountryFlagPath(String countryFlagPath) {
		this.countryFlagPath = countryFlagPath;
	}
	/**
	 * @return the dialinLabelToNumberMap
	 */
	public Map<String, String> getDialinLabelToNumberMap() {
		return dialinLabelToNumberMap;
	}
	/**
	 * @param dialinLabelToNumberMap the dialinLabelToNumberMap to set
	 */
	public void setDialinLabelToNumberMap(Map<String, String> dialinLabelToNumberMap) {
		this.dialinLabelToNumberMap = dialinLabelToNumberMap;
	}

}
