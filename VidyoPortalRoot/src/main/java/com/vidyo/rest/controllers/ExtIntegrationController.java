package com.vidyo.rest.controllers;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.vidyo.bo.Room;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.configuration.enums.ExternalDataTypeEnum;
import com.vidyo.service.exceptions.DataValidationException;
import com.vidyo.service.exceptions.GeneralException;

@RestController
@RequestMapping(value = "/api/extintegration/v1")
//API path : /api/extintegration/v1
public class ExtIntegrationController {

	protected final Logger logger = LoggerFactory.getLogger(ExtIntegrationController.class.getName());
	
	@Autowired
	public ExtIntegrationService extIntegrationService;

	public void setExtIntegrationService(ExtIntegrationService extIntegrationService) {
		this.extIntegrationService = extIntegrationService;
	}

	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String getRoomKey(@RequestBody Map<String, String> configValues) throws GeneralException {
		
		if (!extIntegrationService.isTenantEpicEnabled()) {
			throw new GeneralException("Epic is not enabled");
		}
		
		String externalData = configValues.get("extData");
		if ((externalData == null) || externalData.isEmpty()) {
			throw new DataValidationException("Invalid extData value - " + externalData);
		}
		
		try {
			int extDataType = Integer.valueOf(configValues.get("extDataType"));
			if (extDataType != ExternalDataTypeEnum.EPIC.ordinal()) throw new Exception();
		} catch (Exception e) {
			throw new DataValidationException("Invalid extDataType value - " + configValues.get("extDataType"));
		}
		
		Map<String, String> parametersFromExternalData = new HashMap<String, String>();
		StringBuilder decryptedExternalData = new StringBuilder();
		boolean isExternalDataInvalid = extIntegrationService.validateExternalDataAndDecrypt(externalData, 
				parametersFromExternalData, decryptedExternalData);
				
		String sessionId = parametersFromExternalData.get("SessionID");
		if ((sessionId == null) || sessionId.isEmpty()) isExternalDataInvalid = true;

		if (isExternalDataInvalid || (parametersFromExternalData.size() == 0)) { // invalid external data
			throw new DataValidationException("Invalid extData value - " + externalData);
		}
		
		// Prepend session id with TenantId
		sessionId = TenantContext.getTenantId() + "_" + sessionId;

		// check is room for conference exists and create if not
		Room room = extIntegrationService.createRoomIfNotExist(sessionId);
		String roomKey = room.getRoomKey();
		
		return "{\"roomKey\" : \"" + roomKey + "\"}";
	}

	@ExceptionHandler(value = { DataValidationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected List<String> handleDataValidationException(final DataValidationException ex) {
		final List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return errors;
	}
	
	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected List<String> handleExceptions(Exception ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return errors;
	}
}
