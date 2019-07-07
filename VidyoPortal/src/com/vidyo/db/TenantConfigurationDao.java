package com.vidyo.db;

import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.vidyo.bo.TenantConfiguration;

public interface TenantConfigurationDao {
	
	int insertTenantConfiguration(TenantConfiguration tenantConfiguration);
	int deleteTenantConfiguration(int tenantID);
	TenantConfiguration getTenantConfiguration(int tenantID);
	
	/**
	 * Updates Tenant Configurations
	 * @param tenantId
	 * @param tenantConfiguration
	 * @return
	 */
	int updateTenantConfiguration(@PartialCacheKey int tenantId, TenantConfiguration tenantConfiguration);
	
	public int updateSessionExpConfig(int tenantId, int sessionExpPeriod);
	public int updateTenantCnfPblcRoomSettings(int createPublicRoomEnable, int maxCreatePublicRoomUser);
	public int updateTenantCnfPblcRoomSettingsResetMax(int maxCreatePublicRoomUser);
	int updateTenantCnfUserAttributes(String userImage, String uploadUserImage);
	int updateTenantCnfUserAttrUsrUploadUsage(String userImageUpload);
	int updateTenantAllLogAggregation(int logAggregation);
	int updateTenantAllCustomRole(int customRoleFlag);
}
