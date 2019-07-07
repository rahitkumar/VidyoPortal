/**
 * 
 */
package com.vidyo.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.vidyo.bo.EndpointBehavior;

/**
 * JPA Repository for EndpointBehavior. This will perform all CRUD operations on EndpointBehavior. 
 * @author ysakurikar
 *
 */
@Repository
public interface EndpointBehaviorRepository extends JpaRepository <EndpointBehavior, Integer>{

	/**
	 * Fetch the EndpointBehavior by endpointBehaviorKey
	 * @param endpointBehaviorKey
	 * @return
	 */
	public EndpointBehavior findByEndpointBehaviorKey(@Param ("endpointBehaviorKey") String endpointBehaviorKey);
	
	/**
	 * Fetch the EndpointBehavior by tenantID
	 * @param tenantID
	 * @return
	 */
	public List<EndpointBehavior> findByTenantTenantID(@Param ("tenantID") int tenantID);
	
	/**
	 * Delete the EndpointBehavior identified by EndpointBehaviorKey
	 * @param endpointBehaviorKey
	 * @return
	 */
	public Integer deleteByEndpointBehaviorKey(@Param ("endpointBehaviorKey") String endpointBehaviorKey);
	
	/**
	 * Delete the EndpointBehavior identified by tenantId
	 * @param endpointBehaviorKey
	 * @return
	 */
	public Integer deleteByTenantTenantID(@Param ("tenantID") int tenantID);
}
