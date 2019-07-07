package com.vidyo.service;

import java.util.Map;
import com.vidyo.bo.Room;

public interface ExtIntegrationService {

	boolean isGlobalEpicEnabled();
	
	boolean isTenantEpicEnabled();
	
	boolean isGlobalTytoCareEnabled();
	
	boolean isTenantTytoCareEnabled();
	
	boolean validateExternalDataAndDecrypt(String externalData,
			Map<String, String> parametersFromExternalData, StringBuilder decryptedExternalData);
	
	Room createRoomIfNotExist(String sessionId);
}
