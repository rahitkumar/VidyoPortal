package com.vidyo.service.configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.framework.security.htmlcleaner.AntiSamyHtmlCleaner;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.service.CryptService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.configuration.enums.LegacySystemConfigurationEnum;
import com.vidyo.service.configuration.enums.LegacyTenantConfigurationEnum;
import com.vidyo.service.configuration.enums.SystemConfigurationEnum;
import com.vidyo.service.configuration.enums.TenantConfigurationEnum;
import com.vidyo.service.exceptions.ConfigValidationException;
import com.vidyo.service.exceptions.DataValidationException;
import com.vidyo.utils.HtmlUtils;

@Service(value = "ConfigurationService")
public class ConfigurationServiceImpl implements ConfigurationService {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private ITenantService tenantService;

	@Autowired
	private ISystemService systemService;
	
	@Autowired
	private CryptService cryptService;

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public List<String> fetchTenantConfigKeys() {
		List<String> configKeys = new ArrayList<>();

		for (TenantConfigurationEnum tenantConfigKey : TenantConfigurationEnum.values()) {
			configKeys.add(tenantConfigKey.name());
		}
		for (LegacyTenantConfigurationEnum legacyTenantConfigKey : LegacyTenantConfigurationEnum.values()) {
			configKeys.add(legacyTenantConfigKey.name());
		}

		return configKeys;
	}

	public Map<String, Object> fetchTenantConfigs(int tenantId) {
		validateTenant(tenantId);

		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(tenantId);
		Map<String, Object> configs = mapper.convertValue(tenantConfiguration,
				new TypeReference<Map<String, Object>>() {
				});
		configs.remove("tenantId");

		List<String> configNames = new ArrayList<>();
		for (LegacyTenantConfigurationEnum legacyConfig : LegacyTenantConfigurationEnum.values()) {
			configNames.add(legacyConfig.name());
		}

		List<Configuration> tenantConfigs = systemService.getConfigurationsByTenantAndConfigNames(tenantId,
				configNames);
		if (CollectionUtils.isNotEmpty(tenantConfigs)) {
			for (Configuration config : tenantConfigs) {
				configs.put(config.getConfigurationName(), config.getConfigurationValue());
			}
		}

		return configs;
	}

	private void validateTenant(int tenantId) {
		Tenant tenant = tenantService.getTenant(tenantId);
		if (tenant == null) {
			throw new DataValidationException("Invalid Tenant");
		}
	}

	public Map<String, Object> fetchTenantConfigByConfigName(int tenantId, String configName) {
		validateTenant(tenantId);

		Map<String, Object> configData = new HashMap<>();

		LegacyTenantConfigurationEnum legacyConfig = null;
		try {
			legacyConfig = LegacyTenantConfigurationEnum.valueOf(configName);
		} catch (Exception e) {
		}
		if (legacyConfig != null) {
			String value = systemService.getConfigValue(tenantId, legacyConfig.name());
			configData.put(configName, value);
			return configData;
		}

		TenantConfigurationEnum tenantConfig = null;
		try {
			tenantConfig = TenantConfigurationEnum.valueOf(configName);
		} catch (Exception e) {
		}
		if (tenantConfig != null) {
			TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(tenantId);
			configData = mapper.convertValue(tenantConfiguration, new TypeReference<Map<String, Object>>() {
			});
			configData.remove("tenantId");
			return configData;
		}

		return configData;
	}

	public boolean updateTenantConfigs(int tenantId, Map<String, Object> configValues) {
		if (configValues == null || configValues.isEmpty()) {
			throw new DataValidationException("Invalid Request Data");
		}

		validateTenant(tenantId);

		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(tenantId);
		List<Configuration> legacyConfigurations = new ArrayList<>();
		List<String> errors = new ArrayList<>();
		int configCounter = 0;
		int legacyConfigCounter = 0;
		for (Map.Entry<String, Object> config : configValues.entrySet()) {
			// Process configs from the table : TenantConfiguration
			TenantConfigurationEnum configKey = null;
			try {
				configKey = TenantConfigurationEnum.valueOf(config.getKey());
			} catch (IllegalArgumentException e) {
			}

			if (configKey != null) {
				try {
					processTenantConfigValue(configKey, config.getValue(), tenantConfiguration);
					configCounter++;
				} catch (DataValidationException e) {
					errors.add(e.getMessage());
				}
				continue;
			}

			// Process legacy tenant configs from the table : Configuration
			LegacyTenantConfigurationEnum legacyConfigKey = null;
			try {
				legacyConfigKey = LegacyTenantConfigurationEnum.valueOf(config.getKey());
			} catch (IllegalArgumentException e) {
			}

			if (legacyConfigKey != null) {
				try {
					processLegacyTenantConfigValue(legacyConfigKey, config.getValue(), legacyConfigurations);
					legacyConfigCounter++;
				} catch (DataValidationException e) {
					errors.add(e.getMessage());
				}
				continue;
			}

			// Config is invalid if it is neither listed in
			// TenantConfigurationEnum nor LegacyTenantConfigurationEnum
			errors.add("Invalid Config Key : " + config.getKey());
		}

		if (errors.size() > 0) {
			throw new ConfigValidationException(errors);
		}

		// Persist the config changes
		if (configCounter > 0) {
			tenantService.updateTenantConfiguration(tenantConfiguration);
		}
		if (legacyConfigCounter > 0) {
			systemService.updateConfigurations(tenantId, legacyConfigurations);
		}

		return true;
	}

	private void processLegacyTenantConfigValue(LegacyTenantConfigurationEnum key, Object value,
			List<Configuration> legacyConfigurations) {
		String configValue = null;
		switch (key) {
		case AboutInfo:
			configValue = validateString(key.name(), value);
			configValue = HtmlUtils.sanitize(configValue);
			break;
		case AuthenticationFlag:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case AuthenticationPassword:
			configValue = validateString(key.name(), value);
			break;
		case AuthenticationURL:
			configValue = validateString(key.name(), value);
			break;
		case AuthenticationUsername:
			configValue = validateString(key.name(), value);
			break;
		case CACAuthenticationFlag:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case CACAuthenticationType:
			configValue = validateString(key.name(), value);
			break;
		case CACLDAPAuthenticationBase:
			configValue = validateString(key.name(), value);
			break;
		case CACLDAPAuthenticationFilter:
			configValue = validateString(key.name(), value);
			break;
		case CACLDAPAuthenticationFlag:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case CACLDAPAuthenticationPassword:
			configValue = validateString(key.name(), value);
			break;
		case CACLDAPAuthenticationScope:
			configValue = validateString(key.name(), value);
			break;
		case CACLDAPAuthenticationURL:
			configValue = validateString(key.name(), value);
			break;
		case CACLDAPAuthenticationUsername:
			configValue = validateString(key.name(), value);
			break;
		case CACNonceCheck:
			configValue = validateString(key.name(), value);
			break;
		case CACOCSPDirtyFlag:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case CACOCSPResponderCheck:
			configValue = validateString(key.name(), value);
			break;
		case CACOCSPResponderText:
			configValue = validateString(key.name(), value);
			break;
		case CACOCSPRevocationCheck:
			configValue = validateString(key.name(), value);
			break;
		case ContactInfo:
			configValue = validateString(key.name(), value);
			configValue = HtmlUtils.sanitize(configValue);
			break;
		case DefaultLocationTagID:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case DSCP_MEDIA_AUDIO:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case DSCP_MEDIA_DATA:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case DSCP_MEDIA_VIDEO:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case DSCP_SIGNALING:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case EnableNewAccountNotification:
			configValue = validateString(key.name(), value); // true/false
			break;
		case GuestGroupID:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case GuestLocationID:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case GuestLogin:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case GuestProxyID:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case InvitationEmailContent:
			configValue = validateString(key.name(), value);
			break;
		case InvitationEmailContentHtml:
			configValue = validateString(key.name(), value);
			break;
		case InvitationEmailSubject:
			configValue = validateString(key.name(), value);
			break;
		case LDAPAuthenticationAttributeMappingFlag:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case LDAPAuthenticationBase:
			configValue = validateString(key.name(), value);
			break;
		case LDAPAuthenticationFilter:
			configValue = validateString(key.name(), value);
			break;
		case LDAPAuthenticationFlag:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case LDAPAuthenticationPassword:
			configValue = validateString(key.name(), value);
			break;
		case LDAPAuthenticationScope:
			configValue = validateString(key.name(), value);
			break;
		case LDAPAuthenticationURL:
			configValue = validateString(key.name(), value);
			break;
		case LDAPAuthenticationUsername:
			configValue = validateString(key.name(), value);
			break;
		case logo:
			configValue = validateString(key.name(), value);
			break;
		case logoImage:
			configValue = validateString(key.name(), value);
			break;
		case NotificationEmailFrom:
			configValue = validateString(key.name(), value);
			break;
		case NotificationEmailTo:
			configValue = validateString(key.name(), value);
			break;
		case NumExecutives:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case NumInstalls:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case NumPanoramas:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case NumPorts:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case NumSeats:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case REQUEST_SIGNED:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case RestAuthenticationFlag:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case RestAuthenticationURL:
			configValue = validateString(key.name(), value);
			break;
		case SAML_AUTH_FLAG:
			configValue = validateString(key.name(), value); // YES/NO
			break;
		case SAML_AUTH_PROVISION_TYPE:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case SAML_IDP_ATTRIBUTE_FOR_USERNAME:
			configValue = validateString(key.name(), value);
			break;
		case SAML_IDP_METADATA:
			configValue = validateString(key.name(), value);
			break;
		case SAML_SP_ENTITY_ID:
			configValue = validateString(key.name(), value);
			break;
		case SAML_SP_PROTOCOL:
			configValue = validateString(key.name(), value); // YES/NO
			break;
		case SAML_SP_SECURITY_PROFILE:
			configValue = validateString(key.name(), value);
			break;
		case SAML_SP_SIGN_METADATA:
			configValue = validateString(key.name(), value); // YES/NO
			break;
		case SAML_SP_SSL_PROFILE:
			configValue = validateString(key.name(), value);
			break;
		case SystemLanguage:
			configValue = validateString(key.name(), value);
			break;
		case VidyoWebEnabled:
			configValue = validateString(key.name(), value); // true/false
			break;
		case VoiceOnlyContent:
			configValue = validateString(key.name(), value);
			break;
		case WANT_ASSERTION_ASSIGNED:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case WebcastContent:
			configValue = validateString(key.name(), value);
			break;
		}

		Configuration configuration = new Configuration();
		configuration.setConfigurationName(key.name());
		configuration.setConfigurationValue(configValue);
		legacyConfigurations.add(configuration);
	}

	private void processTenantConfigValue(TenantConfigurationEnum key, Object value,
			TenantConfiguration tenantConfiguration) {
		switch (key) {
		case alwaysUseProxy:
			int alwaysUseProxyValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setAlwaysUseProxy(alwaysUseProxyValue);
			break;
		case createPublicRoomEnable:
			int createPublicRoomEnableValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setCreatePublicRoomEnable(createPublicRoomEnableValue);
			break;
		case endpointBehavior:
			int endpointBehaviorValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setEndpointBehavior(endpointBehaviorValue);
			break;
		case endpointPrivateChat:
			int endpointPrivateChatValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setEndpointPrivateChat(endpointPrivateChatValue);
			break;
		case endpointPublicChat:
			int endpointPublicChatValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setEndpointPublicChat(endpointPublicChatValue);
			break;
		case endpointUploadMode:
			String endpointUploadModeValue = validateString(key.name(), value);
			tenantConfiguration.setEndpointUploadMode(endpointUploadModeValue);
			break;
		case extnLength:
			int extnLengthValue = validateIntegerWithRange(key.name(), value, 4, 15);
			tenantConfiguration.setExtnLength(extnLengthValue);
			break;
		case lectureModeAllowed:
			int lectureModeAllowedValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setLectureModeAllowed(lectureModeAllowedValue);
			break;
		case lectureModeStrict:
			int lectureModeStrictValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setLectureModeStrict(lectureModeStrictValue);
			break;
		case logAggregation:
			int logAggregationValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setLogAggregation(logAggregationValue);
			break;
		case maxCreatePublicRoomTenant:
			int maxCreatePublicRoomTenantValue = validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE);
			tenantConfiguration.setMaxCreatePublicRoomTenant(maxCreatePublicRoomTenantValue);
			break;
		case maxCreatePublicRoomUser:
			int maxCreatePublicRoomUserValue = validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE);
			tenantConfiguration.setMaxCreatePublicRoomUser(maxCreatePublicRoomUserValue);
			break;
		case maxMediaPort:
			int maxMediaPortValue = validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE);
			tenantConfiguration.setMaxMediaPort(maxMediaPortValue);
			break;
		case minMediaPort:
			int minMediaPortValue = validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE);
			tenantConfiguration.setMinMediaPort(minMediaPortValue);
			break;
		case minPINLength:
			int minPINLengthValue = validateIntegerWithRange(key.name(), value, 3, Integer.MAX_VALUE);
			tenantConfiguration.setMinPINLength(minPINLengthValue);
			break;
		case personalRoom:
			int personalRoomValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setPersonalRoom(personalRoomValue);
			break;
		case roomCountThreshold:
			int roomCountThresholdValue = validateIntegerWithRange(key.name(), value, 40, 70);
			tenantConfiguration.setRoomCountThreshold(roomCountThresholdValue);
			break;
		case sessionExpirationPeriod:
			int sessionExpirationPeriodValue = validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE);
			tenantConfiguration.setSessionExpirationPeriod(sessionExpirationPeriodValue);
			break;
		case testCall:
			int testCallValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setTestCall(testCallValue);
			break;
		case uploadUserImage:
			int uploadUserImageValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setUploadUserImage(uploadUserImageValue);
			break;
		case userImage:
			int userImageValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setUserImage(userImageValue);
			break;
		case vidyoNeoWebRTCGuestEnabled:
			int vidyoNeoWebRTCGuestEnabledValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setVidyoNeoWebRTCGuestEnabled(vidyoNeoWebRTCGuestEnabledValue);
			break;
		case vidyoNeoWebRTCUserEnabled:
			int vidyoNeoWebRTCUserEnabledValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setVidyoNeoWebRTCUserEnabled(vidyoNeoWebRTCUserEnabledValue);
			break;
		case waitingRoomsEnabled:
			int waitingRoomsEnabledValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setWaitingRoomsEnabled(waitingRoomsEnabledValue);
			break;
		case waitUntilOwnerJoins:
			int waitUntilOwnerJoinsValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setWaitUntilOwnerJoins(waitUntilOwnerJoinsValue);
			break;
		case zincEnabled:
			int zincEnabledValue = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setZincEnabled(zincEnabledValue);
			break;
		case androidAppLink:
			String androidAppLink = validateString(key.name(), value);
			tenantConfiguration.setAndroidAppLink(androidAppLink);
			break;
		case androidPackageName:
			String androidPackageName = validateString(key.name(), value);
			tenantConfiguration.setAndroidPackageName(androidPackageName);
			break;
		case iOSAppLink:
			String iOSAppLink = validateString(key.name(), value);
			tenantConfiguration.setiOSAppLink(iOSAppLink);
			break;
		case iOSBundleId:
			String iOSBundleId = validateString(key.name(), value);
			tenantConfiguration.setiOSBundleId(iOSBundleId);
			break;
		case mobileProtocol:
			String mobileProtocol = validateString(key.name(), value);
			tenantConfiguration.setMobileProtocol(mobileProtocol);
			break;
		case externalIntegrationMode:
			int externalIntegrationMode = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setExternalIntegrationMode(externalIntegrationMode);
			break;
		case extIntegrationSharedSecret:
			String extIntegrationSharedSecret = validateString(key.name(), value);
			tenantConfiguration.setExtIntegrationSharedSecret(extIntegrationSharedSecret);
			break;
		case externalNotificationUrl:
			String externalNotificationUrl = validateString(key.name(), value);
			tenantConfiguration.setExternalNotificationUrl(externalNotificationUrl);
			break;
		case externalUsername:
			String externalUsername = validateString(key.name(), value);
			tenantConfiguration.setExternalUsername(externalUsername);
			break;
		case externalPassword:
			String externalPassword = validateString(key.name(), value);
			if ((externalPassword != null) && !externalPassword.isEmpty()) {
				externalPassword = VidyoUtil.encrypt(externalPassword);
			} else {
				externalPassword = "";
			}
			tenantConfiguration.setExternalPassword(externalPassword);
			break;
		case tytoIntegrationMode:
			int tytoIntegrationMode = validateIntegerWithRange(key.name(), value, 0, 1);
			tenantConfiguration.setTytoIntegrationMode(tytoIntegrationMode);
			break;
		case tytoUrl:
			String tytoUrl = validateString(key.name(), value);
			tenantConfiguration.setTytoUrl(tytoUrl);
			break;
		case tytoUsername:
			String tytoUsername = validateString(key.name(), value);
			tenantConfiguration.setTytoUsername(tytoUsername);
			break;
		case tytoPassword:
			String tytoPassword = validateString(key.name(), value);
			String tytoPasswordEncrypted = cryptService.encrypt(tytoPassword);
			tenantConfiguration.setTytoPassword(tytoPasswordEncrypted);
			break;
		case desktopProtocol:
			String desktopProtocol = validateString(key.name(), value);
			tenantConfiguration.setDesktopProtocol(desktopProtocol);
			break;
		case iOSAppId:
			String iOSAppId = validateString(key.name(), value);
			tenantConfiguration.setiOSAppId(iOSAppId);
			break;
		case vidyoNotificationUrl:
			String vidyoNotificationUrl = validateString(key.name(), value);
			tenantConfiguration.setVidyoNotificationUrl(vidyoNotificationUrl);
			break;
		case vidyoUsername:
			String vidyoUsername = validateString(key.name(), value);
			tenantConfiguration.setVidyoUsername(vidyoUsername);
			break;	
		case vidyoPassword:
			String vidyoPassword = validateString(key.name(), value);
			if ((vidyoPassword != null) && !vidyoPassword.isEmpty()) {
				vidyoPassword = VidyoUtil.encrypt(vidyoPassword);
			} else {
				vidyoPassword = "";
			}
			tenantConfiguration.setVidyoPassword(vidyoPassword);
			break;
		case tc:
			String tc = validateString(key.name(), value, 2048);
			validateURL(tc);
			tenantConfiguration.setTc(tc);
			break;
		case tcVersion:
			int tcVersion = validateIntegerWithRange(key.name(), value, 1, 1000);
			tenantConfiguration.setTcVersion(tcVersion);
			break;
		case pp:
			String pp = validateString(key.name(), value, 2048);
			validateURL(pp);
			tenantConfiguration.setPp(pp);
			break;
		case ppVersion:
			int ppVersion = validateIntegerWithRange(key.name(), value, 1, 1000);
			tenantConfiguration.setPpVersion(ppVersion);
			break;
		}
	}
	
	private void validateURL(String url) {
		if ((url == null) || (url.length() == 0)) return; // set empty/NULL to remove value
		
		try {
			URL urlObject = new URL(url); // throws exception if not url
			urlObject.toURI(); // validate uri according - http://www.ietf.org/rfc/rfc2396.txt
		} catch (Exception e) {
			throw new DataValidationException(e.getMessage());
		}
	}

	private int validateIntegerWithRange(String key, Object object, int min, int max) {
		Integer value = null;
		try {
			if (object != null) {
				value = Integer.valueOf(object.toString());
			} else {
				value = (Integer) object;
			}
		} catch (Exception e) {
			throw new DataValidationException("Invalid config value for key : " + key);
		}

		if (value < min || value > max) {
			throw new DataValidationException("Invalid config value for key : " + key);
		}

		return value.intValue();
	}

	private String validateString(String key, Object object) {
		String value = null;
		try {
			value = (String) object;
		} catch (Exception e) {
			throw new DataValidationException("Invalid config value for key : " + key);
		}
		return value;
	}
	
	private String validateString(String key, Object object, int maxLength) {
		String value = validateString(key, object);

		if (value.length() > maxLength) {
			throw new DataValidationException("Invalid value length for key : " + key);
		}
		
		return value;
	}

	public List<String> fetchSystemConfigKeys() {
		List<String> configKeys = new ArrayList<>();

		for (SystemConfigurationEnum systemConfigKey : SystemConfigurationEnum.values()) {
			configKeys.add(systemConfigKey.name());
		}
		for (LegacySystemConfigurationEnum legacySystemConfigKey : LegacySystemConfigurationEnum.values()) {
			configKeys.add(legacySystemConfigKey.name());
		}

		return configKeys;
	}

	public Map<String, Object> fetchSystemConfigs() {
		Map<String, Object> configs = new HashMap<>();

		List<String> configNames = new ArrayList<>();
		for (SystemConfigurationEnum config : SystemConfigurationEnum.values()) {
			configNames.add(config.name());
		}

		List<Configuration> systemConfigs = systemService.getConfigurationsByTenantAndConfigNames(0, configNames);
		if (CollectionUtils.isNotEmpty(systemConfigs)) {
			for (Configuration config : systemConfigs) {
				configs.put(config.getConfigurationName(), config.getConfigurationValue());
			}
		}

		configNames.clear();
		for (LegacySystemConfigurationEnum config : LegacySystemConfigurationEnum.values()) {
			configNames.add(config.name());
		}

		systemConfigs.clear();
		systemConfigs = systemService.getConfigurationsByTenantAndConfigNames(1, configNames);
		if (CollectionUtils.isNotEmpty(systemConfigs)) {
			for (Configuration config : systemConfigs) {
				configs.put(config.getConfigurationName(), config.getConfigurationValue());
			}
		}

		return configs;
	}

	public Map<String, Object> fetchSystemConfigByConfigName(String configName) {
		Map<String, Object> configData = new HashMap<>();

		SystemConfigurationEnum systemConfig = null;
		try {
			systemConfig = SystemConfigurationEnum.valueOf(configName);
		} catch (Exception e) {
		}
		if (systemConfig != null) {
			String value = systemService.getConfigValue(0, systemConfig.name());
			configData.put(configName, value);
			return configData;
		}

		LegacySystemConfigurationEnum legacySystemConfig = null;
		try {
			legacySystemConfig = LegacySystemConfigurationEnum.valueOf(configName);
		} catch (Exception e) {
		}
		if (legacySystemConfig != null) {
			String value = systemService.getConfigValue(1, legacySystemConfig.name());
			configData.put(configName, value);
			return configData;
		}

		return configData;
	}

	public boolean updateSystemConfigs(Map<String, Object> configValues) {
		if (configValues == null || configValues.isEmpty()) {
			throw new DataValidationException("Invalid Request Data");
		}

		List<Configuration> systemConfigurations = new ArrayList<>();
		List<Configuration> legacySystemConfigurations = new ArrayList<>();
		List<String> errors = new ArrayList<>();
		int systemConfigCounter = 0;
		int legacySystemConfigCounter = 0;
		for (Map.Entry<String, Object> config : configValues.entrySet()) {
			// Process configs from the table : Configuration, with tenantId : 0
			SystemConfigurationEnum systemConfigKey = null;
			try {
				systemConfigKey = SystemConfigurationEnum.valueOf(config.getKey());
			} catch (IllegalArgumentException e) {
			}

			if (systemConfigKey != null) {
				try {
					processSystemConfigValue(systemConfigKey, config.getValue(), systemConfigurations);
					systemConfigCounter++;
				} catch (DataValidationException e) {
					errors.add(e.getMessage());
				}
				continue;
			}

			// Process configs from the table : Configuration, with tenantId : 1
			LegacySystemConfigurationEnum legacySystemConfigKey = null;
			try {
				legacySystemConfigKey = LegacySystemConfigurationEnum.valueOf(config.getKey());
			} catch (IllegalArgumentException e) {
			}

			if (legacySystemConfigKey != null) {
				try {
					processLegacySystemConfigValue(legacySystemConfigKey, config.getValue(),
							legacySystemConfigurations);
					legacySystemConfigCounter++;
				} catch (DataValidationException e) {
					errors.add(e.getMessage());
				}
				continue;
			}

			// Config is invalid if it is neither listed in
			// SystemConfigurationEnum nor LegacySystemConfigurationEnum
			errors.add("Invalid Config Key : " + config.getKey());
		}

		if (errors.size() > 0) {
			throw new ConfigValidationException(errors);
		}

		// Persist the config changes
		if (systemConfigCounter > 0) {
			systemService.updateConfigurations(0, systemConfigurations);
		}
		if (legacySystemConfigCounter > 0) {
			systemService.updateConfigurations(1, legacySystemConfigurations);
		}

		return true;
	}

	private void processSystemConfigValue(SystemConfigurationEnum key, Object value,
			List<Configuration> systemConfigurations) {
		String configValue = null;
		switch (key) {
		case adminguidelocation_de:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_en:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_es:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_fi:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_fr:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_it:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_ja:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_ko:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_pl:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_pt:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_ru:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_th:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_tr:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_zh_CN:
			configValue = validateString(key.name(), value);
			break;
		case adminguidelocation_zh_TW:
			configValue = validateString(key.name(), value);
			break;
		case AUTH_TOKEN_INACTIVITY_LIMIT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case CDN_OPTIONAL_PARAMETER:
			configValue = validateString(key.name(), value);
			break;
		case chatAvailableFlag:
			configValue = validateString(key.name(), value); // true/false
			break;
		case COMPONENTS_ENCRYPTION:
			configValue = validateString(key.name(), value); // true/false
			break;
		case COUNTRYFLAG_IMAGE_PATH:
			configValue = validateString(key.name(), value);
			break;
		case CREATE_PUBLIC_ROOM_FLAG:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case DISABLE_PASSWORD_RECOVERY_SUPER:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case ENABLE_PERSONAL_ROOM_FLAG:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case ENDPOINTBEHAVIOR:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case EndpointCustomizationOverride:
			configValue = validateString(key.name(), value); // true/false
			break;
		case EPIC_INTEGRATION_SUPPORT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case TYTOCARE_INTEGRATION_SUPPORT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case EVENTS_NOTIFICATION:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case HEARTBEAT_EXPIRY_SECONDS:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case HIDE_USER_IDENTITY:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case INTERNAL_VERSION:
			configValue = validateString(key.name(), value);
			break;
		case IPC_ROUTER_POOL:
			configValue = validateString(key.name(), value);
			break;
		case ipcAllowDomainFlag:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case ipcSuperManaged:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case LogAggregationServer:
			configValue = validateString(key.name(), value);
			break;
		case logoImage:
			configValue = validateString(key.name(), value);
			break;
		case MAIL_NOTIFICATIONS:
			configValue = validateString(key.name(), value); // on/off
			break;
		case MANAGE_ENDPOINT_UPLOAD_MODE:
			configValue = validateString(key.name(), value);
			break;
		case MAX_CREATE_PUBLIC_ROOM:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case MAX_CREATE_PUBLIC_ROOM_USER:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case MAX_PERMITTED_USER_GROUPS_COUNT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case MAX_USER_GROUPS_IMPORTED_PER_USER:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case MAX_USER_IMAGE_SIZE_KB:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case MEMBER_BAK_INACTIVE_LIMIT_IN_MINS:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case MINIMUM_PIN_LENGTH_SUPER:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case MOBILE_LOGIN_MODE:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case OPUS_AUDIO:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case PASSWORD_VALIDITY_DAYS:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case PRIMARY_EVENTS_SERVER:
			configValue = validateString(key.name(), value);
			break;
		case PRIMARY_EVENTS_SERVER_PORT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case privateChatEnabledDefaultFlag:
			configValue = validateString(key.name(), value); // true/false
			break;
		case publicChatEnabledDefaultFlag:
			configValue = validateString(key.name(), value); // true/false
			break;
		case ROOM_KEY_LENGTH:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case ROOM_LINK_FORMAT:
			configValue = validateString(key.name(), value);
			break;
		case SCHEDULED_ROOM_BATCH_LIMIT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SCHEDULED_ROOM_INACTIVE_LIMIT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SCHEDULED_ROOM_PREFIX:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case ScheduledRoom2:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case SDK220:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case SECONDARY_EVENTS_SERVER:
			configValue = validateString(key.name(), value);
			break;
		case SECONDARY_EVENTS_SERVER_PORT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SELECTED_MAX_ROOM_EXT_LENGTH:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SESSION_EXP_PERIOD:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SHOW_CUSTOMIZE_BANNER:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case SHOW_FORGOT_PASSWORD_LINK:
			configValue = validateString(key.name(), value); // YES/NO
			break;
		case ShowDisabledRooms:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case smtpHost:
			configValue = validateString(key.name(), value);
			break;
		case smtpPassword:
			configValue = validateString(key.name(), value);
			break;
		case smtpPort:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case smtpSecure:
			configValue = validateString(key.name(), value);
			break;
		case smtpSslTrustAll:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case smtpUsername:
			configValue = validateString(key.name(), value);
			break;
		case SUPER_ALWAYS_USE_VIDYO_PROXY:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case SUPER_DSCP_MEDIA_AUDIO:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SUPER_DSCP_MEDIA_DATA:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SUPER_DSCP_MEDIA_VIDEO:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SUPER_DSCP_SIGNALING:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SUPER_MAX_MEDIA_PORT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SUPER_MIN_MEDIA_PORT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case SuperEmailFrom:
			configValue = validateString(key.name(), value);
			break;
		case SuperEmailTo:
			configValue = validateString(key.name(), value);
			break;
		case tiles16AvailableFlag:
			configValue = validateString(key.name(), value); // true/false
			break;
		case TLS_PROXY_ENABLED:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case UPLOAD_USER_IMAGE:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case USER_IMAGE:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case VIDYONEO_FOR_WEBRTC_FOR_GUESTS:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case VIDYONEO_FOR_WEBRTC_FOR_USERS:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case VidyoWebAvailable:
			configValue = validateString(key.name(), value); // true/false
			break;
		case VidyoWebEnabled:
			configValue = validateString(key.name(), value); // true/false
			break;
		case WEBRTC_TEST_CALL_USERNAME:
			configValue = validateString(key.name(), value);
			break;
		case WEBRTC_TEST_MEDIA_SERVER:
			configValue = validateString(key.name(), value);
			break;
		case T_C:
			configValue = validateString(key.name(), value, 2048);
			validateURL(configValue);
			break;
		case T_C_VERSION:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 1, 1000));
			break;
		case P_P:
			configValue = validateString(key.name(), value, 2048);
			validateURL(configValue);
			break;
		case P_P_VERSION:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 1, 1000));
			break;
		}

		Configuration configuration = new Configuration();
		configuration.setConfigurationName(key.name());
		configuration.setConfigurationValue(configValue);
		systemConfigurations.add(configuration);
	}

	private void processLegacySystemConfigValue(LegacySystemConfigurationEnum key, Object value,
			List<Configuration> legacySystemConfigurations) {
		String configValue = null;
		switch (key) {
		case CdrAccessFromHost:
			configValue = validateString(key.name(), value);
			break;
		case CdrAccessPassword:
			configValue = validateString(key.name(), value);
			break;
		case CdrAllowDelete:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case CdrAllowDeny:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case CdrEnabled:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case CdrFormat:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case DBVersion:
			configValue = validateString(key.name(), value);
			break;
		case DefaultUserPortalLogo:
			configValue = validateString(key.name(), value);
			break;
		case EnableVidyoCloud:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case FORCE_PASSWORD_CHANGE:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case INACTIVE_DAYS_LIMIT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case LOGIN_FAILURE_COUNT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case LOGIN_HISTORY_COUNT:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case LoginBannerInfo:
			configValue = validateString(key.name(), value);
			try {
				configValue = AntiSamyHtmlCleaner.cleanHtml(configValue).getCleanHTML();
			} catch (ScanException | PolicyException e) {
				throw new DataValidationException("Invalid value length for key : " + key);
			}
			break;
		case MEMBER_PASSWORD_RULE:
			configValue = validateString(key.name(), value);
			break;
		case PortalVersion:
			configValue = validateString(key.name(), value);
			break;
		case ShowLoginBanner:
			configValue = validateString(key.name(), value); // YES/NO
			break;
		case ShowWelcomeBanner:
			configValue = validateString(key.name(), value); // YES/NO
			break;
		case StatusNotificationFlag:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, 1));
			break;
		case StatusNotificationPassword:
			configValue = validateString(key.name(), value);
			break;
		case StatusNotificationURL:
			configValue = validateString(key.name(), value);
			break;
		case StatusNotificationUsername:
			configValue = validateString(key.name(), value);
			break;
		case SuperAboutInfo:
			configValue = validateString(key.name(), value);
			configValue = HtmlUtils.sanitize(configValue);
			break;
		case SuperContactInfo:
			configValue = validateString(key.name(), value);
			configValue = HtmlUtils.sanitize(configValue);
			break;
		case SuperInvitationEmailContent:
			configValue = validateString(key.name(), value);
			break;
		case SuperInvitationEmailContentHtml:
			configValue = validateString(key.name(), value);
			break;
		case SuperInvitationEmailSubject:
			configValue = validateString(key.name(), value);
			break;
		case SuperPortalLogo:
			configValue = validateString(key.name(), value);
			break;
		case SuperVoiceOnlyContent:
			configValue = validateString(key.name(), value);
			break;
		case SuperWebcastContent:
			configValue = validateString(key.name(), value);
			break;
		case USER_DEFAULT_PASSWORD:
			configValue = validateString(key.name(), value);
			break;
		case USER_LOCKOUT_TIME_LIMT_SECS:
			configValue = String.valueOf(validateIntegerWithRange(key.name(), value, 0, Integer.MAX_VALUE));
			break;
		case VidyoProxyDNS:
			configValue = validateString(key.name(), value);
			break;
		case VidyoProxyIpAndPort:
			configValue = validateString(key.name(), value);
			break;
		case WelcomeBannerInfo:
			configValue = validateString(key.name(), value);
			try {
				configValue = AntiSamyHtmlCleaner.cleanHtml(configValue).getCleanHTML();
			} catch (ScanException | PolicyException e) {
				throw new DataValidationException("Invalid value length for key : " + key);
			}
			break;
		}

		Configuration configuration = new Configuration();
		configuration.setConfigurationName(key.name());
		configuration.setConfigurationValue(configValue);
		legacySystemConfigurations.add(configuration);
	}

}
