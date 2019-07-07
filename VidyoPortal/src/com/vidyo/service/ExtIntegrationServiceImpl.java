package com.vidyo.service;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Room;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.configuration.enums.SystemConfigurationEnum;
import com.vidyo.service.exceptions.ScheduledRoomCreationException;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.utils.AESEncryptDecryptUtil;

@Service("ExtIntegrationService")
public class ExtIntegrationServiceImpl implements ExtIntegrationService {

	protected final Logger logger = LoggerFactory.getLogger(ExtIntegrationServiceImpl.class.getName());

	@Autowired
	private ITenantService tenantService;
	
	@Autowired
	private IUserService user;

	@Autowired
	private IRoomService roomService;

	@Autowired
	private ISystemService systemService;

	@Autowired
	@Qualifier("TestCallService")
	private TestCallService testCallService;

	@Override
	public boolean isGlobalEpicEnabled() {
		Configuration conf = this.systemService.getConfiguration(SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name());
		int enableEpicIntegration = 0;
		 
		if (conf!=null) {
			if (conf.getConfigurationValue()!=null && "1".equalsIgnoreCase(conf.getConfigurationValue()))
				enableEpicIntegration = 1;
		}
		return enableEpicIntegration == 1;
	}
	
	@Override
	public boolean isGlobalTytoCareEnabled() {
		Configuration conf = this.systemService.getConfiguration(SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name());
		int enableTytoCareIntegration = 0;
		 
		if ((conf != null) && (conf.getConfigurationValue() != null) && 
				"1".equalsIgnoreCase(conf.getConfigurationValue())) {
			enableTytoCareIntegration = 1;
		}
		return enableTytoCareIntegration == 1;
	}
	
	@Override
	public boolean isTenantEpicEnabled() {
		boolean isGlobalEpicEnabled = isGlobalEpicEnabled();
		boolean isTenantEpicEnabled = false;
		
		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
		if (tenantConfiguration.getExternalIntegrationMode() == 1) { // 1 - Epic
			isTenantEpicEnabled = true;
		}

		return isGlobalEpicEnabled && isTenantEpicEnabled; // both enabled
	}
	
	@Override
	public boolean isTenantTytoCareEnabled() {
		boolean isGlobalTytoCareEnabled = isGlobalTytoCareEnabled();
		boolean isTenantTytoCareEnabled = false;
		
		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
		if (tenantConfiguration.getTytoIntegrationMode() == 1) {  // 1 - TytoCare
			isTenantTytoCareEnabled = true;
		}

		return isGlobalTytoCareEnabled && isTenantTytoCareEnabled; // both enabled
	}
	
	@Override
	public boolean validateExternalDataAndDecrypt(String externalData, Map<String, String> parametersFromExternalData,
			StringBuilder decryptedExternalDataBuilder) {
		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
		String sharedSecret = tenantConfiguration.getExtIntegrationSharedSecret();
		//String notificationUrl = tenantConfiguration.getExternalNotificationUrl();
		String decryptedExternalData = null;

		boolean isExternalDataInvalid = false;
		if ((externalData == null) || externalData.isEmpty()) {
			isExternalDataInvalid = true;
		} else {
			try {
				externalData = externalData.replaceAll(" ", "+"); // return + back
				// String initVector = externalData.substring(0, 24);
				// String piece = externalData.substring(24);
				decryptedExternalData = AESEncryptDecryptUtil.decryptAESWithBase64(sharedSecret, externalData);
			} catch (Exception e) {
				isExternalDataInvalid = true;
				logger.error("error while decrypting ->"+ externalData);
			}

			if ((decryptedExternalData == null) || decryptedExternalData.isEmpty())
				isExternalDataInvalid = true;
		}

		if (!isExternalDataInvalid) { // external data is valid
			String[] parts = decryptedExternalData.split("&");

			if ((parts != null) && (parts.length > 0)) {
				for (String part : parts) {
					String[] nameValue = part.split("=");
					if ((nameValue != null) && (nameValue.length == 2)) {
						parametersFromExternalData.put(nameValue[0], nameValue[1]);
					} else if ((nameValue != null) && (nameValue.length == 1)) { 
						parametersFromExternalData.put(nameValue[0], "");
					} else {
						isExternalDataInvalid = true;
						break;
					}
				}
			} else {
				isExternalDataInvalid = true;
			}
		}
		
		decryptedExternalDataBuilder.append(decryptedExternalData);
		
		return isExternalDataInvalid;
	}
	
    /**
     * Get room by sessionId(create if not exist)
     * 
     * @param roomService room service
     * @param testCallService test call service
     * @param sessionId
     * @return room 
     */
	@Override
	public Room createRoomIfNotExist(String sessionId) {
		Room room = null;
		
		int roomId = roomService.getRoomIdForExternalRoomId(sessionId);
		if (roomId == 0) { // create new room
			try {
				logger.info("Try to create scheduled room with externalRoomId : " + sessionId);
				ScheduledRoomResponse roomCreationResponse = testCallService
						.createScheduledRoomForTestCallOneAttempt(null, sessionId);

				// If the response status is non-zero [failure], check the status code
				if (roomCreationResponse.getStatus() == 0) { // success
					room = roomCreationResponse.getRoom();
					logger.info("Scheduled room with externalRoomId : " + sessionId + " created successfully");
				} else { // create failed
					logger.error("Failed to create scheduled room with externalRoomId : " + sessionId);
					throw new ScheduledRoomCreationException();
				}
			} catch (ScheduledRoomCreationException e) {
				roomId = roomService.getRoomIdForExternalRoomId(sessionId);

				if (roomId != 0) {
					room = roomService.getRoom(roomId);
					logger.info("Successful lookup scheduled room with externalRoomId : " + sessionId);
				} else {
					room = null; // invalid room
				}
			}
		} else { // return existing
			room = roomService.getRoom(roomId);
		}
		
		return room;
	}
}
