/**
 * 
 */
package com.vidyo.db.repository.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vidyo.bo.tenant.Tenant;

/**
 * @author ysakurikar
 *
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer>{

	/**
	 * 
	 * @param tenantId
	 * @return
	 */
	public Tenant findByTenantId(@Param ("tenantId") int tenantId);
}
