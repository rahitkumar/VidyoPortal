package com.vidyo.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidyo.bo.Banners;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.AuthenticationType;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.interportal.IpcConfigurationService;
import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.VendorUtils;

@Service(value = "PortalService")
public class PortalServiceImpl implements PortalService {

	@Autowired
	private ISystemService systemService;

	@Autowired
	private ITenantService tenantService;

	@Autowired
	private IpcConfigurationService ipcConfigurationService;
	
	@Autowired
	private ExtIntegrationService extIntegrationService;

	private static final String FEATURE_DELIMITER = ":";

	@Override
	public String getEncodedPortalFeatures() {
		Map<PortalFeaturesEnum, Boolean> portalFeatures = getPortalFeatures();

		StringBuilder enabledFeatures = new StringBuilder();
		if (portalFeatures != null) {
			for (Map.Entry<PortalFeaturesEnum, Boolean> feature : portalFeatures.entrySet()) {
				if (feature.getValue()) {
					enabledFeatures.append(feature.getKey().getFeatureAbbreviatedName()).append(FEATURE_DELIMITER);
				}
			}
		}
		// Remove the unnecessary delimiter at the end
		if (enabledFeatures.length() > 0) {
			enabledFeatures.deleteCharAt(enabledFeatures.length() - 1);
		}

		return PortalUtils.encodeBase64(enabledFeatures.toString());
	}

	@Override
	public Map<PortalFeaturesEnum, Boolean> getPortalFeatures() {

		Map<PortalFeaturesEnum, Boolean> portalFeatures = new LinkedHashMap<PortalFeaturesEnum, Boolean>();

		Integer tenantId = TenantContext.getTenantId();

		// FEATURE - Guest login for tenant
		portalFeatures.put(PortalFeaturesEnum.Guest, !tenantService.isTenantNotAllowingGuests());

		// FEATURE - IPC outbound and IPC inbound for tenant
		boolean IPCoutbound = false;
		boolean IPCinbound = false;
		if (tenantId != null) {
			IpcConfiguration interPortalConference = ipcConfigurationService.getIpcConfiguration((Integer) tenantId);
			IPCoutbound = interPortalConference.getOutbound() == 1;
			IPCinbound = interPortalConference.getInbound() == 1;
		}

		portalFeatures.put(PortalFeaturesEnum.IPCoutbound, IPCoutbound);
		portalFeatures.put(PortalFeaturesEnum.IPCinbound, IPCinbound);

		portalFeatures.put(PortalFeaturesEnum.ModeratorURL, true);

		// FEATURE - TlsTunneling
		boolean tlsTunneling = systemService.getTLSProxyConfiguration();
		portalFeatures.put(PortalFeaturesEnum.TlsTunneling, tlsTunneling);

		// FEATURES - Welcome and Login banners
		Banners banners = systemService.getBannersInfo();
		portalFeatures.put(PortalFeaturesEnum.LoginBanner, banners.getShowLoginBanner());
		portalFeatures.put(PortalFeaturesEnum.WelcomeBanner, banners.getShowWelcomeBanner());

		// FEATURE - EndpointPrivateChat & EndpointPublicChat
		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(tenantId);
		PortalChat portalChat = systemService.getPortalChat();
		boolean endpointPrivateChatEnabled = false;
		boolean endpointPublicChatEnabled = false;
		if (portalChat.isChatAvailable() && tenantConfiguration != null) {
			endpointPrivateChatEnabled = (tenantConfiguration.getEndpointPrivateChat() == 1);
			endpointPublicChatEnabled = (tenantConfiguration.getEndpointPublicChat() == 1);
		}
		portalFeatures.put(PortalFeaturesEnum.EndpointPrivateChat, endpointPrivateChatEnabled);
		portalFeatures.put(PortalFeaturesEnum.EndpointPublicChat, endpointPublicChatEnabled);

		// FEATURE - CDR2.1
		portalFeatures.put(PortalFeaturesEnum.CDR2_1, true);

		// FEATURE - ability send endpoint features to portal
		portalFeatures.put(PortalFeaturesEnum.EndpointDetails, true);

		// FEATURE - Make 16 tiles layout available on your VidyoPortal
		if (systemService.isTiles16Available()) {
			portalFeatures.put(PortalFeaturesEnum.Tiles16, systemService.isTiles16Available());
		}

		// FEATURE - Indicate that this version supports the new password change
		// page by reporting "HTMLChangePswd"
		AuthenticationConfig authConfig = systemService.getAuthenticationConfig(tenantId);
		AuthenticationType authType = authConfig.toAuthentication().getAuthenticationType();
		portalFeatures.put(PortalFeaturesEnum.HTMLChangePswd,
				authType.equals(AuthenticationType.INTERNAL) ? true : false);

		// FEATURE - Indicate that the router is passing additional participant
		// information starting in 3.3.
		portalFeatures.put(PortalFeaturesEnum.RouterParticipantInformation, true);

		// bind ack in VP 3.4.3 and above
		portalFeatures.put(PortalFeaturesEnum.BindAck, true);

		// neo direct call flow supported in VP3.4.4 and above only
		// scheduled room + recurring + conference appData info
		Configuration scheduledRoomconfig = systemService.getConfiguration("SCHEDULED_ROOM_PREFIX");
		Tenant tenant = tenantService.getTenant(tenantId);
		boolean scheduledRoomEnabled = false;
		// If no prefix is set then scheduledRoom is disable
		if (scheduledRoomconfig == null || scheduledRoomconfig.getConfigurationValue() == null
				|| scheduledRoomconfig.getConfigurationValue().trim().length() == 0) {
			scheduledRoomEnabled = false;
		} else {
			if (tenant != null) {
				scheduledRoomEnabled = tenant.getScheduledRoomEnabled() == 1;
			}
		}
		portalFeatures.put(PortalFeaturesEnum.NeoDirectCall, scheduledRoomEnabled);

		// CreatePublicRoom
		Configuration createPublicRoomconfig = systemService.getConfiguration("CREATE_PUBLIC_ROOM_FLAG");
		boolean createPublicRoomEnabled = false;
		if (createPublicRoomconfig == null || createPublicRoomconfig.getConfigurationValue() == null
				|| createPublicRoomconfig.getConfigurationValue().trim().length() == 0
				|| createPublicRoomconfig.getConfigurationValue().trim().equals("0")) {
			createPublicRoomEnabled = false;
		} else {
			createPublicRoomEnabled = true;
		}
		portalFeatures.put(PortalFeaturesEnum.CreatePublicRoom, createPublicRoomEnabled);
		
		// TytoCare
		portalFeatures.put(PortalFeaturesEnum.TytoCare, extIntegrationService.isTenantTytoCareEnabled());

		// FEATURE - If SDK 2,2 router feature enabled
		Configuration opusAudio = systemService.getConfiguration("OPUS_AUDIO");
		boolean opusAudioEnabled = false;
		if (opusAudio != null && StringUtils.isNotBlank(opusAudio.getConfigurationValue())) {
			opusAudioEnabled = opusAudio.getConfigurationValue().equalsIgnoreCase("1");
		}
		portalFeatures.put(PortalFeaturesEnum.OpusAudio, opusAudioEnabled);

		Configuration sdk220 = systemService.getConfiguration("SDK220");
		boolean sdk220Enabled = false;
		if (sdk220 != null && StringUtils.isNotBlank(sdk220.getConfigurationValue())) {
			sdk220Enabled = sdk220.getConfigurationValue().equalsIgnoreCase("1");
		}
		portalFeatures.put(PortalFeaturesEnum.SDK220, sdk220Enabled);

		boolean testCallEnabled = false;
		boolean personalRoomEnabled = false;
		if (tenantConfiguration != null) {
			testCallEnabled = tenantConfiguration.getTestCall() == 1;
			personalRoomEnabled = tenantConfiguration.getPersonalRoom() == 1;
		}
		portalFeatures.put(PortalFeaturesEnum.TestCall, testCallEnabled);
		portalFeatures.put(PortalFeaturesEnum.PersonalRoom, personalRoomEnabled);

		portalFeatures.put(PortalFeaturesEnum.ScheduledRoom2, scheduledRoomEnabled);
		portalFeatures.put(PortalFeaturesEnum.ScheduledRoom, scheduledRoomEnabled);

		// FEATURE - Thumbnail photo feature
		Configuration thumbnailConfig = systemService.getConfiguration("USER_IMAGE");
		boolean isUserImageEnabled = false;
		if (thumbnailConfig == null || thumbnailConfig.getConfigurationValue() == null
				|| thumbnailConfig.getConfigurationValue().trim().length() == 0
				|| thumbnailConfig.getConfigurationValue().trim().equals("0")) {
			isUserImageEnabled = false;
		} else {
			if (tenantConfiguration != null) {
				isUserImageEnabled = tenantConfiguration.getUserImage() == 1;
			}
		}
		portalFeatures.put(PortalFeaturesEnum.ThumbnailPhoto, isUserImageEnabled);

		boolean isModeratedConferenceAllowed = false;
		if (VendorUtils.isModeratedConferenceAllowed()) {
			isModeratedConferenceAllowed = true;
		}
		portalFeatures.put(PortalFeaturesEnum.ModeratedConference, isModeratedConferenceAllowed);

		return portalFeatures;
	}
}
