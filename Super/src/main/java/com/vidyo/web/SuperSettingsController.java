package com.vidyo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.configuration.enums.SystemConfigurationEnum;

@Controller
public class SuperSettingsController {

	protected static final Logger logger = LoggerFactory.getLogger(SuperSettingsController.class);

	private static final String FAILURE = "FAILURE";
	private static final String SUCCESS = "SUCCESS";

	@Autowired
	private ExtIntegrationService extIntegrationService;

	@Autowired
	private ISystemService system;

	@Autowired
	private ITenantService tenant;

	@Autowired
	private ReloadableResourceBundleMessageSource ms;

	@RequestMapping(value = "/epicintegration.html", method = RequestMethod.GET)
	public ModelAndView getEpicIntegrationAttributeHtml(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enableEpicIntegration", extIntegrationService.isGlobalEpicEnabled());
	    
		return new ModelAndView("super/epic_integration_attributes_html", "model", model);
	}
	
	@RequestMapping(value = "/tytocareintegration.html", method = RequestMethod.GET)
	public ModelAndView getTytoCareIntegrationAttributeHtml(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enableTytoCareIntegration", extIntegrationService.isGlobalTytoCareEnabled());

		return new ModelAndView("super/tytocare_integration_attributes_html", "model", model);
	}

	@RequestMapping(value = "/saveenableepic.ajax", method = RequestMethod.POST)
	public ModelAndView saveEnableEpicIntegrationAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering saveEnableEpicIntegrationAjax() of SettingsController");
		}

		int status = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();

		String enableEpicIntegration = ServletRequestUtils.getStringParameter(request, "enableEpicIntegration", null);
		if (enableEpicIntegration != null) {
			status = system.updateSystemConfig(SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name(),
					enableEpicIntegration);

			if (status == 0) {
				FieldError fe = new FieldError("EnableEpicIntegration", "EnableEpicIntegration",
						ms.getMessage("Failed to save epic integration feature", null, "", loc));
				errors.add(fe);
			}

			if ("0".equalsIgnoreCase(enableEpicIntegration)) {
				try {
					tenant.updateTenantAllEpicIntegration(0);
				} catch (Exception e) {
					FieldError fe = new FieldError("EnableEpicIntegration", "EnableEpicIntegration",
							ms.getMessage("Failed to disable epic integration feature", null, "", loc));
					errors.add(fe);
				}
			}
		} else {
			FieldError fe = new FieldError("EnableEpicIntegration", "EnableEpicIntegration",
					ms.getMessage("Input Value is null. No action taken", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			system.auditLogTransaction("SYSTEM LEVEL Epic Integration UPDATE", enableEpicIntegration, FAILURE);
		} else {
			model.put("success", Boolean.TRUE);
			system.auditLogTransaction("SYSTEM LEVEL Epic Integration UPDATE", enableEpicIntegration, SUCCESS);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/saveenabletytocare.ajax", method = RequestMethod.POST)
	public ModelAndView saveEnableTytoCareIntegrationAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering saveEnableTytoCareIntegrationAjax() of SettingsController");
		}

		int status = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();

		String enableTytoCareIntegration = ServletRequestUtils.getStringParameter(request, "enableTytoCareIntegration",
				null);
		if (enableTytoCareIntegration != null) {
			status = system.updateSystemConfig(SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name(),
					enableTytoCareIntegration);

			if (status == 0) {
				FieldError fe = new FieldError("EnableTytoCareIntegration", "EnableTytoCareIntegration",
						ms.getMessage("Failed to save TytoCare integration feature", null, "", loc));
				errors.add(fe);
			}

			if ("0".equalsIgnoreCase(enableTytoCareIntegration)) {
				try {
					tenant.updateTenantAllTytoCareIntegration(0);
				} catch (Exception e) {
					FieldError fe = new FieldError("EnableTytoCareIntegration", "EnableTytoCareIntegration",
							ms.getMessage("Failed to disable TytoCare integration feature", null, "", loc));
					errors.add(fe);
				}
			}
		} else {
			FieldError fe = new FieldError("EnableTytoCareIntegration", "EnableTytoCareIntegration",
					ms.getMessage("Input Value is null. No action taken", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			system.auditLogTransaction("SYSTEM LEVEL TytoCare Integration UPDATE", enableTytoCareIntegration, FAILURE);
		} else {
			model.put("success", Boolean.TRUE);
			system.auditLogTransaction("SYSTEM LEVEL TytoCare Integration UPDATE", enableTytoCareIntegration, SUCCESS);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}
}
