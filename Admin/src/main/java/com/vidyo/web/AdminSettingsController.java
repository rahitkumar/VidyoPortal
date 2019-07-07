package com.vidyo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.WsRestAuthentication;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.configuration.enums.ExternalDataTypeEnum;
import com.vidyo.web.utils.ExtIntegrationUtils;

@Controller
public class AdminSettingsController {

	protected static final Logger logger = LoggerFactory.getLogger(AdminSettingsController.class);

	private static final String FAILURE = "FAILURE";
	private static final String SUCCESS = "SUCCESS";

	@Autowired
	private ExtIntegrationService extIntegrationService;

	@Autowired
	private ITenantService tenant;

	@Autowired
	private ISystemService system;

	@Autowired
	private IMemberService member;

	@Autowired
	private ReloadableResourceBundleMessageSource ms;

	@RequestMapping(value = "/getepicintegration.ajax", method = RequestMethod.GET)
	public ModelAndView getEpicIntegrationAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		TenantConfiguration tenantConfig = this.tenant.getTenantConfiguration(TenantContext.getTenantId());
		model.put("enableEpicIntegration", extIntegrationService.isTenantEpicEnabled() ? 1 : 0);

		String sharedSecret = tenantConfig.getExtIntegrationSharedSecret();
		if ((sharedSecret != null) && !sharedSecret.isEmpty()) {
			model.put("sharedSecret", sharedSecret);
		} else {
			model.put("sharedSecret", "");
		}
		String notificationUrl = tenantConfig.getExternalNotificationUrl();
		if ((notificationUrl != null) && !notificationUrl.isEmpty()) {
			model.put("notificationUrl", notificationUrl);
		} else {
			model.put("notificationUrl", "");
		}
		String notificationUsername = tenantConfig.getExternalUsername();
		if ((notificationUsername != null) && !notificationUsername.isEmpty()) {
			model.put("notificationUser", notificationUsername);
		} else {
			model.put("notificationUser", "");
		}
		String notificationPassword = tenantConfig.getExternalPassword();
		if ((notificationPassword != null) && !notificationPassword.isEmpty()) {
			model.put("notificationPassword", SystemServiceImpl.PASSWORD_UNCHANGED);
		} else {
			model.put("notificationPassword", "");
		}

		return new ModelAndView("ajax/getepicintegration_ajax", "model", model);
	}

	@RequestMapping(value = "/gettytocareintegration.ajax", method = RequestMethod.GET)
	public ModelAndView getTytoCareIntegrationAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		TenantConfiguration tenantConfig = this.tenant.getTenantConfiguration(TenantContext.getTenantId());
		model.put("enableTytoCareIntegration", extIntegrationService.isTenantTytoCareEnabled() ? 1 : 0);

		String tytoUrl = tenantConfig.getTytoUrl();
		if ((tytoUrl != null) && !tytoUrl.isEmpty()) {
			model.put("tytoUrl", tytoUrl);
		} else {
			model.put("tytoUrl", "");
		}
		String tytoUsername = tenantConfig.getTytoUsername();
		if ((tytoUsername != null) && !tytoUsername.isEmpty()) {
			model.put("tytoUsername", tytoUsername);
		} else {
			model.put("tytoUsername", "");
		}
		String tytoPassword = tenantConfig.getTytoPassword();
		if ((tytoPassword != null) && !tytoPassword.isEmpty()) {
			model.put("tytoPassword", SystemServiceImpl.PASSWORD_UNCHANGED);
		} else {
			model.put("tytoPassword", "");
		}

		return new ModelAndView("ajax/gettytocareintegration_ajax", "model", model);
	}

	@RequestMapping(value = "/saveepicintegration.ajax", method = RequestMethod.POST)
	public ModelAndView saveEpicIntegrationAdminAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Entering saveEpicIntegrationAdminAjax() of SettingsController");
		}

		if (!extIntegrationService.isGlobalEpicEnabled()) {
			response.sendError(400); // global feature is disabled
			return null;
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();
		TenantConfiguration config = tenant.getTenantConfiguration(TenantContext.getTenantId());
		config.setTenantId(TenantContext.getTenantId());

		int enableEpicIntegration = ServletRequestUtils.getIntParameter(request, "enableEpicIntegration", 0);
		String sharedSecret = ServletRequestUtils.getStringParameter(request, "sharedSecret", null);
		String notificationUrl = ServletRequestUtils.getStringParameter(request, "notificationUrl", null);
		String notificationUser = ServletRequestUtils.getStringParameter(request, "notificationUser", null);
		String notificationPassword = ServletRequestUtils.getStringParameter(request, "notificationPassword", null);

		// Set the mode to value 1 - EPIC
		if ((enableEpicIntegration == 1) && StringUtils.isNotBlank(sharedSecret)) {
			config.setExternalIntegrationMode(ExternalDataTypeEnum.EPIC.ordinal());
			
			config.setExtIntegrationSharedSecret(sharedSecret);
			config.setExternalNotificationUrl(notificationUrl);
			config.setExternalUsername(notificationUser);
			String password = ExtIntegrationUtils.prepareEpicNotificationPasswordValue(config, 
					notificationUser, notificationPassword);
			config.setExternalPassword(password);
		}
		if (enableEpicIntegration == 0) {
			config.setExternalIntegrationMode(ExternalDataTypeEnum.VIDYO.ordinal());
		}
		
		int status = tenant.updateTenantConfiguration(config);
		if (status == 0) {
			FieldError fe = new FieldError("EpicIntegration", "EpicIntegration",
					ms.getMessage("failed.to.save.epic", null, "", loc));
			errors.add(fe);
			system.auditLogTransaction("TENANT LEVEL Epic Integration UPDATE", String.valueOf(enableEpicIntegration),
					FAILURE);
		} else {
			system.auditLogTransaction("TENANT LEVEL Epic Integration UPDATE", String.valueOf(enableEpicIntegration),
					SUCCESS);
			member.updateAllMembersLastModifiedDateExt(TenantContext.getTenantId(), null);
		}
		logger.debug("Exiting saveEpicIntegrationAdminAjax() of SettingsController");

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/savetytocareintegration.ajax", method = RequestMethod.POST)
	public ModelAndView saveTytoCareIntegrationAdminAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Entering saveTytoCareIntegrationAdminAjax() of SettingsController");
		}

		if (!extIntegrationService.isGlobalTytoCareEnabled()) {
			response.sendError(400); // global feature is disabled
			return null;
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();
		TenantConfiguration config = tenant.getTenantConfiguration(TenantContext.getTenantId());
		config.setTenantId(TenantContext.getTenantId());

		int enableTytoCareIntegration = ServletRequestUtils.getIntParameter(request, "enableTytoCareIntegration", 0);
		String tytoUrl = ServletRequestUtils.getStringParameter(request, "tytoUrl", null);
		String tytoUsername = ServletRequestUtils.getStringParameter(request, "tytoUsername", null);
		String tytoPassword = ServletRequestUtils.getStringParameter(request, "tytoPassword", null);

		// Set the mode to value 1 - TytoCare
		if ((enableTytoCareIntegration == 1) && StringUtils.isNotBlank(tytoUsername)) {
			config.setTytoIntegrationMode(1);
			
			config.setTytoUrl(tytoUrl);
			config.setTytoUsername(tytoUsername);
			String password = ExtIntegrationUtils.prepareTytoPasswordValue(config, tytoUsername, tytoPassword);
			config.setTytoPassword(password);
		} else {
			config.setTytoIntegrationMode(ExternalDataTypeEnum.VIDYO.ordinal());
		}

		int status = tenant.updateTenantConfiguration(config);
		if (status == 0) {
			FieldError fe = new FieldError("TytoCareIntegration", "TytoCareIntegration",
					ms.getMessage("failed.to.save.tytocare", null, "", loc));
			errors.add(fe);
			system.auditLogTransaction("TENANT LEVEL TytoCare Integration UPDATE",
					String.valueOf(enableTytoCareIntegration), FAILURE);
		} else {
			system.auditLogTransaction("TENANT LEVEL TytoCare Integration UPDATE",
					String.valueOf(enableTytoCareIntegration), SUCCESS);
			member.updateAllMembersLastModifiedDateExt(TenantContext.getTenantId(), null);
		}
		logger.debug("Exiting saveTytoCareIntegrationAdminAjax() of SettingsController");

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	@RequestMapping(value = "/testtytocareconnection.ajax", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    public Map<String, Object> testTytoCareConnection(HttpServletRequest request, 
    		HttpServletResponse response) throws Exception {
    	
        Locale loc = LocaleContextHolder.getLocale();
        
    	if (!extIntegrationService.isGlobalTytoCareEnabled()) {
    		response.sendError(400); // global feature is disabled
    		return null;
    	}
		Map<String, Object> restResponse = new HashMap<>();

		String tytoUrl = ServletRequestUtils.getStringParameter(request, "tytoUrl", null);
		String tytoUsername = ServletRequestUtils.getStringParameter(request, "tytoUsername", null);
		String tytoPassword = ServletRequestUtils.getStringParameter(request, "tytoPassword", null);
		
		TenantConfiguration config = tenant.getTenantConfiguration(TenantContext.getTenantId());
		tytoPassword = ExtIntegrationUtils.prepareTytoPasswordValue(config, tytoPassword);

		AuthenticationConfig authConfig = new AuthenticationConfig();
		authConfig.setRestUrl(tytoUrl);
		authConfig.setUsername(tytoUsername);
		authConfig.setPassword(tytoPassword);
		authConfig.setRestFlag(true);
		
		WsRestAuthentication authType = (WsRestAuthentication) authConfig.toAuthentication();
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        //If invalid URL, just skip to result jsp
        if(!urlValidator.isValid(authType.getRestUrl())){
            restResponse.put("httpStatus", "400");
            restResponse.put("message", "Invalid address. Please check the url");
        } else {
        	// URL is valid, test the authentication
        	String message = "Test successful. HTTP Status code - 200";
            ResponseEntity<String> responseEntity = this.system.restTytoCareAuthentication(tytoUrl +
          		   "/api/v1/integration/stations", authConfig.getUsername(), 
          		   authConfig.getPassword());
			if (responseEntity == null || responseEntity.getStatusCodeValue() != 200) {
                message = "Test Failed because of HTTP error code " + responseEntity.getStatusCodeValue();
			}
            restResponse.put("httpStatus", responseEntity.getStatusCodeValue());
            restResponse.put("message", message);
        }
        return restResponse;
    }
}
