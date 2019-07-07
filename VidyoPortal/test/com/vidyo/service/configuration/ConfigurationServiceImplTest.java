package com.vidyo.service.configuration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.CryptService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.configuration.enums.LegacySystemConfigurationEnum;
import com.vidyo.service.configuration.enums.LegacyTenantConfigurationEnum;
import com.vidyo.service.configuration.enums.SystemConfigurationEnum;
import com.vidyo.service.configuration.enums.TenantConfigurationEnum;
import com.vidyo.service.exceptions.ConfigValidationException;
import com.vidyo.service.exceptions.DataValidationException;

public class ConfigurationServiceImplTest {

	@InjectMocks
	private ConfigurationServiceImpl configurationServiceImpl;

	@Mock
	private ITenantService tenantService;

	@Mock
	private ISystemService systemService;
	
	@Mock
	private CryptService cryptService;

	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);
	}

	private List<Configuration> constructConfigurations(String[][] configData) {
		List<Configuration> configurations = new ArrayList<>();

		for (String[] configDetails : configData) {
			Configuration config = new Configuration();
			config.setConfigurationName(configDetails[0]);
			config.setConfigurationValue(configDetails[1]);
			configurations.add(config);
		}

		return configurations;
	}

	@Test
	public void testTenantConfigurationEnumNoOfEntries() {
		Field[] fields = TenantConfiguration.class.getDeclaredFields();
		// Excluding the serialId and TenantId
		int fieldCount = fields.length - 2;

		int configEnumCount = TenantConfigurationEnum.values().length;

		Assert.assertEquals(fieldCount, configEnumCount);
	}

	@Test
	public void testTenantConfigurationEnumOrdering() {
		boolean status = true;
		String previousEntry = null;
		for (TenantConfigurationEnum configEnum : TenantConfigurationEnum.values()) {
			if (previousEntry != null && status && previousEntry.compareToIgnoreCase(configEnum.name()) > 0) {
				status = false;
				previousEntry = configEnum.name();
				break;
			}
			previousEntry = configEnum.name();
		}

		Assert.assertTrue(status,
				String.format("Config key : %s, is placed out of order in TenantConfigurationEnum", previousEntry));
	}

	@Test
	public void testLegacyTenantConfigurationEnumOrdering() {
		boolean status = true;
		String previousEntry = null;
		for (LegacyTenantConfigurationEnum configEnum : LegacyTenantConfigurationEnum.values()) {
			if (previousEntry != null && status && previousEntry.compareToIgnoreCase(configEnum.name()) > 0) {
				status = false;
				previousEntry = configEnum.name();
				break;
			}
			previousEntry = configEnum.name();
		}

		Assert.assertTrue(status, String
				.format("Config key : %s, is placed out of order in LegacyTenantConfigurationEnum", previousEntry));
	}

	@Test
	public void testSystemConfigurationEnumOrdering() {
		boolean status = true;
		String previousEntry = null;
		for (SystemConfigurationEnum configEnum : SystemConfigurationEnum.values()) {
			if (previousEntry != null && status && previousEntry.compareToIgnoreCase(configEnum.name()) > 0) {
				status = false;
				previousEntry = configEnum.name();
				break;
			}
			previousEntry = configEnum.name();
		}

		Assert.assertTrue(status,
				String.format("Config key : %s, is placed out of order in SystemConfigurationEnum", previousEntry));
	}

	@Test
	public void testLegacySystemConfigurationEnumOrdering() {
		boolean status = true;
		String previousEntry = null;
		for (LegacySystemConfigurationEnum configEnum : LegacySystemConfigurationEnum.values()) {
			if (previousEntry != null && status && previousEntry.compareToIgnoreCase(configEnum.name()) > 0) {
				status = false;
				previousEntry = configEnum.name();
				break;
			}
			previousEntry = configEnum.name();
		}

		Assert.assertTrue(status, String
				.format("Config key : %s, is placed out of order in LegacySystemConfigurationEnum", previousEntry));
	}

	@Test
	public void testFetchTenantConfigs() {
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);
		String[][] configData = { { "SystemLanguage", "ja" }, { "NumInstalls", "7" }, { "NumSeats", "5" },
				{ "NumPorts", "2" }, { "GuestGroupID", "1" }, { "GuestLogin", "0" }, { "NumExecutives", "0" },
				{ "NumPanoramas", "2" }, { "NotificationEmailFrom", "user1@vvp0.com" } };
		List<Configuration> configs = constructConfigurations(configData);
		when(systemService.getConfigurationsByTenantAndConfigNames(anyInt(), anyList())).thenReturn(configs);

		Map<String, Object> result = configurationServiceImpl.fetchTenantConfigs(3);

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 59);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(systemService, times(1)).getConfigurationsByTenantAndConfigNames(anyInt(), anyList());
	}

	@Test
	public void testFetchTenantConfigsForInvalidTenant() {
		boolean status = false;
		when(tenantService.getTenant(3)).thenReturn(null);

		try {
			configurationServiceImpl.fetchTenantConfigs(3);
		} catch (Exception e) {
			if (e instanceof DataValidationException && e.getMessage().contains("Invalid Tenant")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
		verify(tenantService, times(1)).getTenant(3);
	}

	@Test
	public void testUpdateTenantConfigsForNullConfigs() {
		boolean status = false;

		try {
			configurationServiceImpl.updateTenantConfigs(3, null);
		} catch (Exception e) {
			if (e instanceof DataValidationException && e.getMessage().contains("Invalid Request Data")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
	}

	@Test
	public void testUpdateTenantConfigsForEmptyConfigs() {
		boolean status = false;

		try {
			configurationServiceImpl.updateTenantConfigs(3, new HashMap<>());
		} catch (Exception e) {
			if (e instanceof DataValidationException && e.getMessage().contains("Invalid Request Data")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
	}

	@Test
	public void testUpdateTenantConfigsForInvalidTenant() {
		boolean status = false;
		when(tenantService.getTenant(3)).thenReturn(null);

		Map<String, Object> configs = new HashMap<>();
		configs.put("SystemLanguage", "en");
		try {
			configurationServiceImpl.updateTenantConfigs(3, configs);
		} catch (Exception e) {
			if (e instanceof DataValidationException && e.getMessage().contains("Invalid Tenant")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
		verify(tenantService, times(1)).getTenant(3);
	}

	@Test
	public void testUpdateTenantConfigs() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("SystemLanguage", "en");
		configs.put("roomCountThreshold", 41);
		boolean result = configurationServiceImpl.updateTenantConfigs(3, configs);

		Assert.assertTrue(result);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(tenantService, times(1)).updateTenantConfiguration(any(TenantConfiguration.class));
		verify(systemService, times(1)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateTenantConfigsWithInvalidConfigKey() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("SystemLanguage", "en");
		configs.put("roomCountThreshold", 41);
		configs.put("someConfig", "someValue");

		boolean status = false;
		try {
			configurationServiceImpl.updateTenantConfigs(3, configs);
		} catch (Exception e) {
			if (e instanceof ConfigValidationException) {
				ConfigValidationException ex = (ConfigValidationException) e;
				if (ex.getValidationErrors().size() == 1
						&& ex.getValidationErrors().get(0).contains("Invalid Config Key : someConfig")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(tenantService, times(0)).updateTenantConfiguration(any(TenantConfiguration.class));
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateTenantConfigsWithChangesInTenantConfigurationOnly() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("roomCountThreshold", 41);
		boolean result = configurationServiceImpl.updateTenantConfigs(3, configs);

		Assert.assertTrue(result);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(tenantService, times(1)).updateTenantConfiguration(any(TenantConfiguration.class));
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateTenantConfigsWithChangesInLegacyTenantConfigurationOnly() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("SystemLanguage", "en");
		boolean result = configurationServiceImpl.updateTenantConfigs(3, configs);

		Assert.assertTrue(result);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(tenantService, times(0)).updateTenantConfiguration(any(TenantConfiguration.class));
		verify(systemService, times(1)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateTenantConfigsWithInvalidTenantConfigurationValue() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("roomCountThreshold", "sdfsjhd");

		boolean status = false;
		try {
			configurationServiceImpl.updateTenantConfigs(3, configs);
		} catch (Exception e) {
			if (e instanceof ConfigValidationException) {
				ConfigValidationException ex = (ConfigValidationException) e;
				if (ex.getValidationErrors().size() == 1 && ex.getValidationErrors().get(0)
						.contains("Invalid config value for key : roomCountThreshold")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(tenantService, times(0)).updateTenantConfiguration(any(TenantConfiguration.class));
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateTenantConfigsWithInvalidLegacyTenantConfigurationValue() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("AuthenticationFlag", "4");

		boolean status = false;
		try {
			configurationServiceImpl.updateTenantConfigs(3, configs);
		} catch (Exception e) {
			if (e instanceof ConfigValidationException) {
				ConfigValidationException ex = (ConfigValidationException) e;
				if (ex.getValidationErrors().size() == 1 && ex.getValidationErrors().get(0)
						.contains("Invalid config value for key : AuthenticationFlag")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(tenantService, times(0)).updateTenantConfiguration(any(TenantConfiguration.class));
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateTenantConfigsWithInvalidIntegerLegacyTenantConfigurationValue() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("AuthenticationFlag", Long.MAX_VALUE);

		boolean status = false;
		try {
			configurationServiceImpl.updateTenantConfigs(3, configs);
		} catch (Exception e) {
			if (e instanceof ConfigValidationException) {
				ConfigValidationException ex = (ConfigValidationException) e;
				if (ex.getValidationErrors().size() == 1 && ex.getValidationErrors().get(0)
						.contains("Invalid config value for key : AuthenticationFlag")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(tenantService, times(0)).updateTenantConfiguration(any(TenantConfiguration.class));
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testFetchTenantConfigByConfigNameWithLegacyTenantConfig() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(systemService.getConfigValue(3, "AboutInfo")).thenReturn("Some text here");

		Map<String, Object> result = configurationServiceImpl.fetchTenantConfigByConfigName(3, "AboutInfo");

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 1);
		verify(tenantService, times(1)).getTenant(3);
		verify(systemService, times(1)).getConfigValue(3, "AboutInfo");
	}

	@Test
	public void testFetchTenantConfigByConfigNameWithTenantConfig() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());

		Map<String, Object> result = configurationServiceImpl.fetchTenantConfigByConfigName(3, "alwaysUseProxy");

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), TenantConfigurationEnum.values().length);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
	}

	@Test
	public void testFetchTenantConfigByConfigNameWithInvalidConfig() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		Map<String, Object> result = configurationServiceImpl.fetchTenantConfigByConfigName(3, "someconfig");

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 0);
		verify(tenantService, times(1)).getTenant(3);
	}

	@Test
	public void testFetchTenantConfigKeys() {
		List<String> result = configurationServiceImpl.fetchTenantConfigKeys();

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(),
				TenantConfigurationEnum.values().length + LegacyTenantConfigurationEnum.values().length);
	}

	@Test
	public void testFetchSystemConfigKeys() {
		List<String> result = configurationServiceImpl.fetchSystemConfigKeys();

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(),
				SystemConfigurationEnum.values().length + LegacySystemConfigurationEnum.values().length);
	}

	@Test
	public void testFetchSystemConfigs() {

		String[][] systemConfigData = { { "USER_IMAGE", "1" }, { "SHOW_FORGOT_PASSWORD_LINK", "YES" },
				{ "MOBILE_LOGIN_MODE", "1" }, { "ScheduledRoom2", "1" }, { "ShowWelcomeBanner", "No" } };
		String[][] legacySystemConfigData = { { "SESSION_EXP_PERIOD", "720" }, { "WEBRTC_TEST_MEDIA_SERVER", "" },
				{ "UPLOAD_USER_IMAGE", "0" }, { "OPUS_AUDIO", "1" }, { "MEMBER_BAK_INACTIVE_LIMIT_IN_MINS", "360" },
				{ "USER_LOCKOUT_TIME_LIMT_SECS", "60" } };

		List<Configuration> systemConfigs = constructConfigurations(systemConfigData);
		List<Configuration> legacySystemConfigs = constructConfigurations(legacySystemConfigData);
		when(systemService.getConfigurationsByTenantAndConfigNames(anyInt(), anyList())).thenReturn(systemConfigs)
				.thenReturn(legacySystemConfigs);

		Map<String, Object> result = configurationServiceImpl.fetchSystemConfigs();

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 11);
		verify(systemService, times(2)).getConfigurationsByTenantAndConfigNames(anyInt(), anyList());
	}

	@Test
	public void testFetchSystemConfigByConfigNameWithSystemConfig() {
		when(systemService.getConfigValue(0, "AUTH_TOKEN_INACTIVITY_LIMIT")).thenReturn("720");

		Map<String, Object> result = configurationServiceImpl
				.fetchSystemConfigByConfigName("AUTH_TOKEN_INACTIVITY_LIMIT");

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 1);
		verify(systemService, times(1)).getConfigValue(0, "AUTH_TOKEN_INACTIVITY_LIMIT");
	}

	@Test
	public void testFetchSystemConfigByConfigNameWithLegacySystemConfig() {
		when(systemService.getConfigValue(1, "DBVersion")).thenReturn("17.3.1");

		Map<String, Object> result = configurationServiceImpl.fetchSystemConfigByConfigName("DBVersion");

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 1);
		verify(systemService, times(1)).getConfigValue(1, "DBVersion");
	}

	@Test
	public void testFetchSystemConfigByConfigNameWithInvalidConfig() {

		Map<String, Object> result = configurationServiceImpl.fetchSystemConfigByConfigName("someconfig");

		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 0);
	}

	@Test
	public void testUpdateSystemConfigsForNullConfigs() {
		boolean status = false;

		try {
			configurationServiceImpl.updateSystemConfigs(null);
		} catch (Exception e) {
			if (e instanceof DataValidationException && e.getMessage().contains("Invalid Request Data")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
	}

	@Test
	public void testUpdateSystemConfigsForEmptyConfigs() {
		boolean status = false;

		try {
			configurationServiceImpl.updateSystemConfigs(new HashMap<>());
		} catch (Exception e) {
			if (e instanceof DataValidationException && e.getMessage().contains("Invalid Request Data")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
	}

	@Test
	public void testUpdateSystemConfigs() {
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(3).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("USER_IMAGE", "0");
		configs.put("SHOW_FORGOT_PASSWORD_LINK", "NO");
		configs.put("SESSION_EXP_PERIOD", "360");
		configs.put("USER_LOCKOUT_TIME_LIMT_SECS", "65");

		boolean result = configurationServiceImpl.updateSystemConfigs(configs);

		Assert.assertTrue(result);
		verify(systemService, times(2)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateSystemConfigsForTenantId0() {
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(3);

		Map<String, Object> configs = new HashMap<>();
		configs.put("USER_IMAGE", "0");
		configs.put("SHOW_FORGOT_PASSWORD_LINK", "NO");
		configs.put("SESSION_EXP_PERIOD", "360");

		boolean result = configurationServiceImpl.updateSystemConfigs(configs);

		Assert.assertTrue(result);
		verify(systemService, times(1)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateSystemConfigsForTenantId1() {
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("USER_LOCKOUT_TIME_LIMT_SECS", "65");

		boolean result = configurationServiceImpl.updateSystemConfigs(configs);

		Assert.assertTrue(result);
		verify(systemService, times(1)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateSystemConfigsWithInvalidConfigKey() {
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("USER_IMAGE", "0");
		configs.put("SHOW_FORGOT_PASSWORD_LINK", "NO");
		configs.put("SESSION_EXP_PERIOD", "360");
		configs.put("USER_LOCKOUT_TIME_LIMT_SECS", "60");
		configs.put("someConfig", "someValue");

		boolean status = false;
		try {
			configurationServiceImpl.updateSystemConfigs(configs);
		} catch (Exception e) {
			if (e instanceof ConfigValidationException) {
				ConfigValidationException ex = (ConfigValidationException) e;
				if (ex.getValidationErrors().size() == 1
						&& ex.getValidationErrors().get(0).contains("Invalid Config Key : someConfig")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateSystemConfigsWithInvalidSystemConfigurationValue() {
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("USER_IMAGE", "sdfsjhd");

		boolean status = false;
		try {
			configurationServiceImpl.updateSystemConfigs(configs);
		} catch (Exception e) {
			if (e instanceof ConfigValidationException) {
				ConfigValidationException ex = (ConfigValidationException) e;
				if (ex.getValidationErrors().size() == 1
						&& ex.getValidationErrors().get(0).contains("Invalid config value for key : USER_IMAGE")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}

	@Test
	public void testUpdateSystemConfigsWithInvalidLegacySystemConfigurationValue() {
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("USER_LOCKOUT_TIME_LIMT_SECS", "sdfsjhd");

		boolean status = false;
		try {
			configurationServiceImpl.updateSystemConfigs(configs);
		} catch (Exception e) {
			if (e instanceof ConfigValidationException) {
				ConfigValidationException ex = (ConfigValidationException) e;
				if (ex.getValidationErrors().size() == 1 && ex.getValidationErrors().get(0)
						.contains("Invalid config value for key : USER_LOCKOUT_TIME_LIMT_SECS")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}
	
	@Test
	public void testPasswordEncryption () {
		when(tenantService.getTenant(1)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(1)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);
		Map<String, Object> configs = new HashMap<>();
		configs.put(TenantConfigurationEnum.tytoPassword.name(), "asdff35");
		configurationServiceImpl.updateTenantConfigs(1, configs);
		verify(cryptService,times(1)).encrypt(any());
	}
	
	@Test
	public void testUpdateTenantConfigsWithInvalidEpicIntegrationTenantConfigurationValue() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("externalIntegrationMode", 2);

		boolean status = false;
		try {
			configurationServiceImpl.updateTenantConfigs(3, configs);
		} catch (Exception e) {
			if (e instanceof ConfigValidationException) {
				ConfigValidationException ex = (ConfigValidationException) e;
				if (ex.getValidationErrors().size() == 1 && ex.getValidationErrors().get(0)
						.contains("Invalid config value for key : externalIntegrationMode")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(tenantService, times(0)).updateTenantConfiguration(any(TenantConfiguration.class));
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}
	
	@Test
	public void testUpdateTenantConfigsWithInvalidTytoIntegrationTenantConfigurationValue() {
		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		when(tenantService.getTenantConfiguration(3)).thenReturn(new TenantConfiguration());
		when(tenantService.updateTenantConfiguration(any(TenantConfiguration.class))).thenReturn(1);
		when(systemService.updateConfigurations(anyInt(), anyList())).thenReturn(1);

		Map<String, Object> configs = new HashMap<>();
		configs.put("tytoIntegrationMode", 2);

		boolean status = false;
		try {
			configurationServiceImpl.updateTenantConfigs(3, configs);
		} catch (Exception e) {
			if (e instanceof ConfigValidationException) {
				ConfigValidationException ex = (ConfigValidationException) e;
				if (ex.getValidationErrors().size() == 1 && ex.getValidationErrors().get(0)
						.contains("Invalid config value for key : tytoIntegrationMode")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
		verify(tenantService, times(1)).getTenant(3);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(tenantService, times(0)).updateTenantConfiguration(any(TenantConfiguration.class));
		verify(systemService, times(0)).updateConfigurations(anyInt(), anyList());
	}
}
