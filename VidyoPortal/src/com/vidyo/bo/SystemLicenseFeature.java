package com.vidyo.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SystemLicenseFeature implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5067681806226264974L;
	String name;
    String licensedValue;
    String currentValue;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof SystemLicenseFeature)) {
			return false;
		}

		SystemLicenseFeature slf = (SystemLicenseFeature) o;

		return name.equals(slf.getName());
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public SystemLicenseFeature(String name, String licensedValue, String currentValue) {
		this.name = name;
		this.licensedValue = licensedValue;
		this.currentValue = currentValue;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getLicensedValue() {
		return licensedValue;
	}

	public void setLicensedValue(String licensedValue) {
		this.licensedValue = licensedValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Overridden toString implementation
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
