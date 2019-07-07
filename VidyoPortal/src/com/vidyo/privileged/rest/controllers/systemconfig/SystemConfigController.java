package com.vidyo.privileged.rest.controllers.systemconfig;

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

import com.vidyo.service.configuration.ConfigurationService;
import com.vidyo.service.exceptions.ConfigValidationException;
import com.vidyo.service.exceptions.DataValidationException;
import com.vidyo.service.exceptions.GeneralException;

@RestController
@RequestMapping(value = "/service/systemconfig/v1")
// API path : /service/systemconfig/v1
public class SystemConfigController {

	@Autowired
	private ConfigurationService configurationService;

	public void setconfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@GetMapping(value = "/configurationkey", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<String> getSystemConfigKeys() {
		return configurationService.fetchSystemConfigKeys();
	}

	@GetMapping(value = "/configuration", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> getSystemConfigs() {
		return configurationService.fetchSystemConfigs();
	}

	@GetMapping(value = "/configuration/{configName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> getSystemConfigByConfigName(@PathVariable String configName) {
		return configurationService.fetchSystemConfigByConfigName(configName);
	}

	@PutMapping(value = "/configuration", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String updateSystemConfigs(@RequestBody Map<String, Object> configValues) throws GeneralException {
		configurationService.updateSystemConfigs(configValues);
		return "{\"status\" : \"SUCCESS\"}";
	}

	@GetMapping(value = "/tenant/configurationkey", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<String> getTenantConfigKeys() {
		return configurationService.fetchTenantConfigKeys();
	}

	@GetMapping(value = "/tenant/{tenantId}/configuration", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> getTenantConfigs(@PathVariable("tenantId") int tenantId) {
		return configurationService.fetchTenantConfigs(tenantId);
	}

	@GetMapping(value = "/tenant/{tenantId}/configuration/{configName}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> getTenantConfigByConfigName(@PathVariable("tenantId") int tenantId,
			@PathVariable String configName) {
		return configurationService.fetchTenantConfigByConfigName(tenantId, configName);
	}

	@PutMapping(value = "/tenant/{tenantId}/configuration", consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String updateTenantConfig(@PathVariable("tenantId") int tenantId,
			@RequestBody Map<String, Object> configValues) throws GeneralException {
		configurationService.updateTenantConfigs(tenantId, configValues);
		return "{\"status\" : \"SUCCESS\"}";
	}

	@ExceptionHandler(value = { DataValidationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected List<String> handleAuthenticationException(final DataValidationException ex) {
		final List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return errors;
	}

	@ExceptionHandler(value = { ConfigValidationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected List<String> handleConfigValidationException(ConfigValidationException ex) {
		return ex.getValidationErrors();
	}

}
