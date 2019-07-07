/**
 * 
 */
package com.vidyo.service.dialin;

import java.util.List;

import com.vidyo.bo.TenantDialInCountry;
import com.vidyo.bo.tenant.Tenant;

/**
 * @author ysakurikar
 *
 */
public interface TenantDialInService {
	/**
	 * Get Tenant dial in country list
	 * @param tenantId
	 * @return
	 */
	public List<TenantDialInCountry> getTenantDialInCounties (int tenantId);
	
	/**
	 * Delete the TenantDialInCountry
	 * @param tenantId
	 */
	public boolean deleteTenantDialInCountryForTenant( int tenantId);

	/**
	 * Save all the dial in numbers for the Tenant
	 * @param dialInNumbers
	 * @param tenantId
	 * @return
	 */
	public List<TenantDialInCountry> saveUpdateDialInNumbers(String dialInNumbers, int tenantId);


	public Tenant getTenantDetails(int tenantId);
}
