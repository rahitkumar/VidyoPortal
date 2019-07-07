package com.vidyo.service.configuration;

import java.util.List;
import java.util.Map;

public interface ConfigurationService {

	List<String> fetchTenantConfigKeys();

	Map<String, Object> fetchTenantConfigs(int tenantId);

	Map<String, Object> fetchTenantConfigByConfigName(int tenantId, String configName);

	boolean updateTenantConfigs(int tenantId, Map<String, Object> configValues);

	List<String> fetchSystemConfigKeys();

	Map<String, Object> fetchSystemConfigs();

	Map<String, Object> fetchSystemConfigByConfigName(String configName);

	boolean updateSystemConfigs(Map<String, Object> configValues);

}
