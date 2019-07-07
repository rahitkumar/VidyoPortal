package com.vidyo.utils;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.configuration.enums.SystemConfigurationEnum;

public class TCAndPrivacyPolicyUtils {

	public static String getPrivacyPolicyUrl(ISystemService systemService, ITenantService tenantService) {
		String result = "http://www.vidyo.com/privacy-policy/"; // default

		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
		if (org.apache.commons.lang.StringUtils.isNotBlank(tenantConfiguration.getPp())) { // tenant
			result = tenantConfiguration.getPp();
		} else { // system
			Configuration ppConfig = systemService.getConfiguration(SystemConfigurationEnum.P_P.name());
			if ((ppConfig != null)
					&& org.apache.commons.lang.StringUtils.isNotBlank(ppConfig.getConfigurationValue())) {
				result = ppConfig.getConfigurationValue();
			}
		}

		return result;
	}

	public static String getTermsAndConditionsUrl(ISystemService systemService, ITenantService tenantService) {
		String result = "http://www.vidyo.com/hosted-user-terms-and-conditions/"; // default

		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
		if (org.apache.commons.lang.StringUtils.isNotBlank(tenantConfiguration.getTc())) { // tenant
			result = tenantConfiguration.getTc();
		} else { // system
			Configuration tcConfig = systemService.getConfiguration(SystemConfigurationEnum.T_C.name());
			if ((tcConfig != null)
					&& org.apache.commons.lang.StringUtils.isNotBlank(tcConfig.getConfigurationValue())) {
				result = tcConfig.getConfigurationValue();
			}
		}

		return result;
	}
}
