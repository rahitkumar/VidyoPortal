/**
 * 
 */
package com.vidyo.db;

import java.util.List;

import com.vidyo.bo.TenantDialInCountry;

/**
 * @author ysakurikar
 *
 */
public interface TenantDialInDao {

	/**
	 * Get Tenant dial in country list
	 * @param tenantId
	 * @return
	 */
	public List<TenantDialInCountry> getTenantDialInCounties (int tenantId);
	
	/**
	 * Create/Update the TenantDialInCountries in the database. 
	 * @param tenantDialInCountries
	 * @return
	 */
	public List<TenantDialInCountry> saveAllTenantDialInCountries(int tenantId, List<TenantDialInCountry> tenantDialInCountries);
	
	/**
	 * Delete the TenantDialInCountry
	 * @param tenantId
	 */
	public boolean deleteTenantDialInCountryForTenant( int tenantId);
	
	public com.vidyo.bo.tenant.Tenant getTenantDetails(int tenantId);

}
