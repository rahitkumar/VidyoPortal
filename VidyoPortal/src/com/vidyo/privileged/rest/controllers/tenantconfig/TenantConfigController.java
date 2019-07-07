package com.vidyo.privileged.rest.controllers.tenantconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.configuration.ConfigurationService;
import com.vidyo.service.exceptions.ConfigValidationException;
import com.vidyo.service.exceptions.DataValidationException;
import com.vidyo.service.exceptions.GeneralException;

@RestController
@RequestMapping(value = "/service/tenantconfig/v1")
// API path : /service/tenantconfig/v1
public class TenantConfigController {

	@Autowired
	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@GetMapping(value = "/configurationkey", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<String> getTenantConfigKeys() {
		return configurationService.fetchTenantConfigKeys();
	}

	@GetMapping(value = "/configuration", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> getTenantConfig() {
		return configurationService.fetchTenantConfigs(TenantContext.getTenantId());
	}

	@GetMapping(value = "/configuration/{configName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> getTenantConfigByConfigName(@PathVariable String configName) {
		return configurationService.fetchTenantConfigByConfigName(TenantContext.getTenantId(), configName);
	}

	@PutMapping(value = "/configuration", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String updateTenantConfig(@RequestBody Map<String, Object> configValues) throws GeneralException {
		configurationService.updateTenantConfigs(TenantContext.getTenantId(), configValues);
		return "{\"status\" : \"SUCCESS\"}";
	}

	@ExceptionHandler(value = { ConfigValidationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected List<String> handleConfigValidationException(ConfigValidationException ex) {
		return ex.getValidationErrors();
	}

	@ExceptionHandler(value = { DataValidationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected List<String> handleDataValidationException(DataValidationException ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return errors;
	}

}
