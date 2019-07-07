package com.vidyo.db.repository.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.bo.TenantConfiguration;

@Repository
@Transactional
public interface TenantConfigurationRepository extends JpaRepository<TenantConfiguration, Integer>{

	@Modifying
	@Query("UPDATE TenantConfiguration SET externalIntegrationMode=:externalIntegrationMode WHERE externalIntegrationMode=1")
	int updateTenantAllEpicIntegration(@Param("externalIntegrationMode") Integer externalIntegrationMode);
	
	@Modifying
	@Query("UPDATE TenantConfiguration SET tytoIntegrationMode=:tytoIntegrationMode WHERE tytoIntegrationMode=1")
	int updateTenantAllTytoCareIntegration(@Param("tytoIntegrationMode") Integer tytoIntegrationMode);
}
