/**
 * 
 */
package com.vidyo.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.vidyo.bo.tenant.Tenant;

/**
 * This table will hold the data for dial in numbers per country for Tenant.
 * For same country tenant can have multiple numbers.
 * @author ysakurikar
 *
 */
@Entity
@Table (name="tenant_country_mapping")
public class TenantDialInCountry implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private TenantDialInCountryPK tenantDialInCountryPK; 

	@MapsId ("countryId")
	@JoinColumn (name="countryID", referencedColumnName="countryID", insertable=false, updatable=false)
	@ManyToOne (fetch = FetchType.EAGER)
	private Country country;
	
	@MapsId ("tenantId")
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="tenantID", referencedColumnName="tenantID", insertable=false, updatable=false)
	private Tenant tenant;
	
	@Column (name="dialin_label")
	private String dialInLabel;
	
	/**
	 * @return the dialInLabel
	 */
	public String getDialInLabel() {
		return dialInLabel;
	}

	/**
	 * @param dialInLabel the dialInLabel to set
	 */
	public void setDialInLabel(String dialInLabel) {
		this.dialInLabel = dialInLabel;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the tenant
	 */
	public Tenant getTenant() {
		return tenant;
	}

	/**
	 * @param tenant the tenant to set
	 */
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	/**
	 * @return the tenantDialInCountryPK
	 */
	public TenantDialInCountryPK getTenantDialInCountryPK() {
		return tenantDialInCountryPK;
	}

	/**
	 * @param tenantDialInCountryPK the tenantDialInCountryPK to set
	 */
	public void setTenantDialInCountryPK(TenantDialInCountryPK tenantDialInCountryPK) {
		this.tenantDialInCountryPK = tenantDialInCountryPK;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tenantDialInCountryPK == null) ? 0 : tenantDialInCountryPK.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TenantDialInCountry)) {
			return false;
		}
		TenantDialInCountry other = (TenantDialInCountry) obj;
		if (tenantDialInCountryPK == null) {
			if (other.tenantDialInCountryPK != null) {
				return false;
			}
		} else if (!tenantDialInCountryPK.equals(other.tenantDialInCountryPK)) {
			return false;
		}
		return true;
	}
	
	
	
}
