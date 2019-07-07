/**
 * 
 */
package com.vidyo.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author ysakurikar
 *
 */
@Embeddable 
public class TenantDialInCountryPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column (name = "countryID")
	private int countryId;
	
	@Column (name = "tenantID")
	private int tenantId;

	@Column (name = "dialin_number")
	private String dialInNumber;

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
	 * @return the tenantId
	 */
	public int getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the dialInNumber
	 */
	public String getDialInNumber() {
		return dialInNumber;
	}

	/**
	 * @param dialInNumber the dialInNumber to set
	 */
	public void setDialInNumber(String dialInNumber) {
		this.dialInNumber = dialInNumber;
	}

	/** 
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countryId;
		result = prime * result + ((dialInNumber == null) ? 0 : dialInNumber.hashCode());
		result = prime * result + tenantId;
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TenantDialInCountryPK)) {
			return false;
		}
		TenantDialInCountryPK other = (TenantDialInCountryPK) obj;
		if (countryId != other.countryId) {
			return false;
		}
		if (dialInNumber == null) {
			if (other.dialInNumber != null) {
				return false;
			}
		} else if (!dialInNumber.equals(other.dialInNumber)) {
			return false;
		}
		if (tenantId != other.tenantId) {
			return false;
		}
		return true;
	}


	
}
