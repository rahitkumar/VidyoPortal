/**
 * 
 */
package com.vidyo.db;

import java.util.List;
import com.vidyo.bo.EndpointBehavior;

/**
 * @author ysakurikar
 *
 */
public interface EndpointBehaviorDao {

	/**
	 * Fetch the EndpointBehavior using endpointBehaviorKey. 
	 * @param endpointBehaviorKey
	 * @return
	 */
	public EndpointBehavior getEndpointBehaviorByKey(String endpointBehaviorKey);
	
	/**
	 * Get the EndpointBehavior by tenant
	 * @param tenantId
	 * @return
	 */
	public List<EndpointBehavior> getEndpointBehaviorByTenant(int tenantID);
	
	/**
	 * Create/Update the EndpointBehavior in the database. 
	 * @param endpointBehavior
	 * @return
	 */
	public EndpointBehavior saveEndpointBehavior (EndpointBehavior endpointBehavior);

	/**
	 * Delete the EndpointBehavior identified by EndpointBehaviorKey
	 * @param endpointBehaviorKey
	 */
	public boolean deleteEndpointBehaviorByKey(String endpointBehaviorKey);

	/**
	 * Delete the EndpointBehavior identified by tenantId
	 * @param tenantID
	 * @return
	 */
	public boolean deleteEndpointBehaviorByTenantID(int tenantID);	
}
