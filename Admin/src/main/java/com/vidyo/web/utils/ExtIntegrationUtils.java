package com.vidyo.web.utils;

import org.apache.commons.lang.StringUtils;

import com.vidyo.bo.TenantConfiguration;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.service.SystemServiceImpl;

public class ExtIntegrationUtils {

	public static String prepareEpicNotificationPasswordValue(TenantConfiguration config, String notificationUser,
			String password) {

		if (isPasswordChanged(password)) {
			password = VidyoUtil.encrypt(password);
		} else if (StringUtils.isEmpty(password) && StringUtils.isEmpty(notificationUser)) {
			// Removing the username/password only if both are empty
			password = "";
		} else {
			// If both are not empty, set the old username and password
			password = config.getExternalPassword();
		}

		return password;
	}

	public static boolean isPasswordChanged(String password) {
		return !StringUtils.isEmpty(password) && !SystemServiceImpl.PASSWORD_UNCHANGED.equals(password);
	}

	public static String prepareTytoPasswordValue(TenantConfiguration tenantConfig, String tytoPassword) {

		if (StringUtils.isEmpty(tytoPassword)) {
			tytoPassword = "";
		} else if (SystemServiceImpl.PASSWORD_UNCHANGED.equals(tytoPassword)) {
			tytoPassword = VidyoUtil.decrypt(tenantConfig.getTytoPassword());
		}
		return tytoPassword;
	}

	public static String prepareTytoPasswordValue(TenantConfiguration config, String tytoUsername,
			String tytoPassword) {
		String password = null;

		if (isPasswordChanged(tytoPassword)) {
			password = VidyoUtil.encrypt(tytoPassword);
		} else if (StringUtils.isEmpty(tytoPassword) && StringUtils.isEmpty(tytoUsername)) {
			// Removing the username/password only if both are empty
			password = "";
		} else {
			// If both are not empty, set the old username and password
			password = config.getTytoPassword();
		}

		return password;
	}
}
