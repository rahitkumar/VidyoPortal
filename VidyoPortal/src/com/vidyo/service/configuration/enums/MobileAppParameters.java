/**
 * 
 */
package com.vidyo.service.configuration.enums;

/**
 * @author ganesh
 *
 */
public enum MobileAppParameters {

	IOSPROTOCOL("vidyo"), IOSAPPID("1103823278");

	private String value;

	MobileAppParameters(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
