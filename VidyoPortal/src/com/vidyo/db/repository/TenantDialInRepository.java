/**
 * 
 */
package com.vidyo.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vidyo.bo.TenantDialInCountry;
import com.vidyo.bo.TenantDialInCountryPK;

/**
 * JPA Repository for TenantDialInCountry, responsible top query for all tenant dial data and filter based on parameters.
 * @author ysakurikar
 *
 */
@Repository
public interface TenantDialInRepository extends JpaRepository<TenantDialInCountry, TenantDialInCountryPK> {

	/**
	 * Fetch the TenantDialInCountries by tenantId
	 * @param tenantId
	 * @return
	 */
	public List<TenantDialInCountry> findByTenantTenantIdOrderByCountryNameAsc(@Param ("tenantId") int tenantId);

	/**
	 * Delete the TenantDialInCountry for tenantId
	 * @param tenantId
	 * @return
	 */
	public int deleteByTenantTenantId(@Param ("tenantId") int tenantId);

}
