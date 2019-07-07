/**
 * VidyoPortalGuestServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.portal.guest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vidyo.service.configuration.enums.ExternalDataTypeEnum;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.databinding.types.URI;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vidyo.bo.Banners;
import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.EndpointBehavior;
import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.Guest;
import com.vidyo.bo.Guestconf;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.AuthenticationType;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.db.endpoints.EndpointFeatures;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.model.CallDetails;
import com.vidyo.service.EndpointUploadService;
import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.ILoginService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.TestCallService;
import com.vidyo.service.TestCallServiceImpl;
import com.vidyo.service.PortalService.PortalFeaturesEnum;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.conference.request.GuestJoinConfRequest;
import com.vidyo.service.conference.response.JoinConferenceResponse;
import com.vidyo.service.endpointbehavior.EndpointBehaviorService;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.exceptions.EmailAddressNotFoundException;
import com.vidyo.service.exceptions.GeneralException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.exceptions.NotificationEmailAddressNotConfiguredException;
import com.vidyo.service.exceptions.ScheduledRoomCreationException;
import com.vidyo.service.exceptions.TestCallException;
import com.vidyo.service.interportal.IpcConfigurationService;
import com.vidyo.service.lecturemode.LectureModeService;
import com.vidyo.service.lecturemode.request.GuestHandUpdateRequest;
import com.vidyo.service.lecturemode.response.HandUpdateResponse;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.utils.SecureRandomUtils;

/**
 * VidyoPortalGuestServiceSkeleton java skeleton for the axisService
 */
public class VidyoPortalGuestServiceSkeleton implements VidyoPortalGuestServiceSkeletonInterface {

	protected static final Logger logger = LoggerFactory.getLogger(VidyoPortalGuestServiceSkeleton.class.getName());

	private IUserService user;
	private IMemberService memberService;
	private ISystemService system;
	private IConferenceService conference;
	private IServiceService service;
	private IRoomService room;
	private ITenantService tenant;
	private IpcConfigurationService ipcConfigurationService;
	private ConferenceAppService conferenceAppService;
	private EndpointUploadService endpointUploadService;
	private String upload_path;

	@Autowired
	private LectureModeService lectureModeService;
	private ILoginService loginService;
    private EndpointService endpointService;

    @Autowired
    private EndpointBehaviorService endpointBeaviorService;
    
    @Autowired
	private ExtIntegrationService extIntegrationService;

	private TestCallService testCallService;

	//VPTL-7615 - Integrate test call feature with WebRTC.
	private static final String TEST_CALL_SERVER_USERNAME = "WEBRTC_TEST_CALL_USERNAME";
	private static final String DEFAULT_TEST_CALL_USERNAME = "Test-call";
	private static final String WEBRTC_TEST_MEDIA_SERVER ="WEBRTC_TEST_MEDIA_SERVER";
	private static final String SCHEDULED_ROOM_PREFIX = "SCHEDULED_ROOM_PREFIX";

	/**
	 * @param conferenceAppService the conferenceAppService to set
	 */
	public void setConferenceAppService(ConferenceAppService conferenceAppService) {
		this.conferenceAppService = conferenceAppService;
	}

	public void setConference(IConferenceService conference) {
		this.conference = conference;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public void setUser(IUserService user) {
		this.user = user;
	}

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	public void setService(IServiceService service) {
		this.service = service;
	}

	public void setRoom(IRoomService room) {
		this.room = room;
	}

	public void setTenant(ITenantService tenant) {
		this.tenant = tenant;
	}

	public void setIpcConfigurationService(IpcConfigurationService ipcConfigurationService) {
		this.ipcConfigurationService = ipcConfigurationService;
	}

	public void setEndpointUploadService(EndpointUploadService endpointUploadService) {
		this.endpointUploadService = endpointUploadService;
	}

	public void setUpload_path(String upload_path) {
		this.upload_path = upload_path.trim();
	}

	public void setLectureModeService(LectureModeService lectureModeService) {
		this.lectureModeService = lectureModeService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

    public EndpointService getEndpointService() {
        return endpointService;
    }

    public void setEndpointService(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

	public TestCallService getTestCallService() {
		return testCallService;
	}

	public void setTestCallService(TestCallService testCallService) {
		this.testCallService = testCallService;
	}


	/**
	 * Auto generated method signature
	 *
	 * @param logInAsGuestRequest
	 * @return logInAsGuestResponse
	 * @throws InvalidArgumentFaultException
	 * @throws ResourceNotAvailableFaultException
	 * @throws GeneralFaultException
	 */
	public com.vidyo.portal.guest.LogInAsGuestResponse logInAsGuest
		(
			com.vidyo.portal.guest.LogInAsGuestRequest logInAsGuestRequest
		)
		throws
		InvalidArgumentFaultException,
		ResourceNotAvailableFaultException,
		GeneralFaultException,
		RoomIsFullFaultException
	{
		LogInAsGuestResponse resp = new LogInAsGuestResponse();

		String clientType = logInAsGuestRequest.getClientType();
		if (clientType != null && (clientType.equalsIgnoreCase("I") || clientType.equalsIgnoreCase("A"))) {
			// Check to validate if mobile login is allowed for this tenant
			Tenant t = this.tenant.getTenantForRequest();

			if (t != null && t.getMobileLogin() == 0) {
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("Mobile Access Restricted for the tenant URL - " + t.getTenantURL());
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		String roomKey = logInAsGuestRequest.getRoomKey();
		if (roomKey == null || roomKey.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("roomKey is required");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room = this.room.getRoomDetailsByRoomKey(roomKey, TenantContext.getTenantId());

		if(room == null) {
			// Check if it is a scheduled room
			String[] extPin = roomKey.split(":");
			if(extPin.length == 2){
				ScheduledRoomResponse scheduledRoomResponse = conferenceAppService.validateScheduledRoom(extPin[0], extPin[1]);
				if(scheduledRoomResponse.getStatus() == 0) {
					room = scheduledRoomResponse.getRoom();
					room.setRoomPIN(String.valueOf(scheduledRoomResponse.getPin()));
				}
			}
		}

		if (room == null) {
			ResourceNotAvailableFault fault = new ResourceNotAvailableFault();
			fault.setErrorMessage("Room not found for the roomKey = " + roomKey);
			ResourceNotAvailableFaultException exception = new ResourceNotAvailableFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getRoomEnabled() != 1) {
			ResourceNotAvailableFault fault = new ResourceNotAvailableFault();
			fault.setErrorMessage("Room for the roomKey = " + roomKey + " is disabled");
			ResourceNotAvailableFaultException exception = new ResourceNotAvailableFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!this.room.areGuestAllowedToThisRoom()) {
			ResourceNotAvailableFault fault = new ResourceNotAvailableFault();
			fault.setErrorMessage("Guests not allowed to the room for the roomKey = " + roomKey);
			ResourceNotAvailableFaultException exception = new ResourceNotAvailableFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if(this.room.getRoomStatus(room.getRoomID(), room.getGroupID()) == 2) { //room is "Full"
			RoomIsFullFault fault = new RoomIsFullFault();
			fault.setErrorMessage("Room is full");
			RoomIsFullFaultException exception = new RoomIsFullFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setIsLocked(room.getRoomLocked() == 1);
		resp.setHasPin(room.getRoomPIN() != null);

		String guestName = logInAsGuestRequest.getGuestName();
		int guestID;
		PortalAccessKeys portalAccessKeys = null;
		if (guestName != null && !guestName.equalsIgnoreCase("")) {
			String username = SecureRandomUtils.generateHashKey(16);
			guestID = this.user.addGuestUser(guestName, username, room.getRoomID());
			portalAccessKeys = this.user.generatePAKforGuest(guestID); // generate PAK before pass it to Endpoint
		} else {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("guestName is required");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		resp.setGuestID(guestID);

		User guest = this.user.getUserForGuestID(guestID);
		resp.setUn(guest.getUsername());

		//String PAK = this.user.getPAKforGuest(guestID);
		if(portalAccessKeys != null) {
			resp.setPak(portalAccessKeys.getPak());
			//TODO - uncomment the below line to include pak2
			resp.setPak2(portalAccessKeys.getPak2());
		}

		// Portal WS endpoint
		Tenant t = this.tenant.getTenantForRequest();
		MessageContext context = MessageContext.getCurrentMessageContext();

		StringBuffer sb = new StringBuffer();
		sb.append(context.getIncomingTransportName()).append("://");
		sb.append(t.getTenantURL()).append("/services/");
		resp.setPortal(sb.toString());

		// set VM address
		try {
			String vm = this.conference.getVMConnectAddress();
			if (vm != null && !vm.trim().equals("")) {
				resp.setVmaddress(vm);
			}
		} catch (NoVidyoManagerException e) {
			ResourceNotAvailableFault fault = new ResourceNotAvailableFault();
			fault.setErrorMessage(e.getMessage());
			ResourceNotAvailableFaultException exception = new ResourceNotAvailableFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		// Common settings for All guests
		Guestconf guestconf = this.system.getGuestConf();

		// set proxies addresses
		String proxies = null;
        if (guestconf != null && guestconf.getProxyID() != 0) {
            proxies = this.service.getVP(guestconf.getProxyID()).getUrl();
        }
		if (proxies != null && !proxies.trim().equals("")) {
			resp.setProxyaddress(proxies);
		}

		// Location Tag
		String locTag = "";
		if (guestID != 0) { // guest user
			if (guestconf.getLocationID() != 0) {
				locTag = this.service.getLocation(guestconf.getLocationID()).getLocationTag();
			}
		}

		if (locTag != null && !locTag.equalsIgnoreCase("")) {
			resp.setLoctag(locTag);
		} else {
			resp.setLoctag("Default");
		}

		// Portal version
		String portalVersion = "";
		portalVersion = this.system.getPortalVersion();
		if (portalVersion != null && !portalVersion.equalsIgnoreCase("")) {
			resp.setPortalVersion(portalVersion);
		}

		MessageContext msgContext = MessageContext.getCurrentMessageContext();
		String remoteIpAddress = (String) msgContext.getProperty(MessageContext.REMOTE_ADDR);
		if(remoteIpAddress == null || remoteIpAddress.isEmpty()) {
			resp.setEndpointExternalIPAddress("Unknown");
		} else {
			resp.setEndpointExternalIPAddress(remoteIpAddress);
		}

		resp.setRoomName(room.getRoomName());
		resp.setRoomDisplayName(room.getDisplayName());

		EndpointSettings es = this.system.getAdminEndpointSettings(TenantContext.getTenantId());
		resp.setMinMediaPort(es.getMinMediaPort());
		resp.setMaxMediaPort(es.getMaxMediaPort());
		resp.setVrProxyConfig(es.isAlwaysUseVidyoProxy() ? "ALWAYS" : "AUTO");

        // Check if the EndpointBehavior is enabled for the Tenant
        if (logInAsGuestRequest.getReturnEndpointBehavior()
        		&& endpointBeaviorService.isEndpointBehaviorForTenant(TenantContext.getTenantId())) {
        	// If the request has set with the return EndpointBehavior, then only
        	// response will have it.
			// Get the EndpointBehavior for the tenant if exists.
			EndpointBehavior endpointBehavior = null;
	    	 List<EndpointBehavior> endpointBehaviors = endpointBeaviorService.getEndpointBehaviorByTenant(TenantContext.getTenantId());
			   if (endpointBehaviors != null && endpointBehaviors.size() > 0){
				   endpointBehavior = endpointBehaviors.get(0);
			   };

			if (endpointBehavior != null ){
				resp.setEndpointBehavior((EndpointBehaviorDataType)
						endpointBeaviorService.makeEndpointBehavioDataType(endpointBehavior,  new EndpointBehaviorDataType()));
			}
        }
		return resp;
	}

    @Override
    public SetEndpointDetailsResponse setEndpointDetails(SetEndpointDetailsRequest setEndpointDetailsRequest) throws
            EndpointNotBoundFaultException, InvalidArgumentFaultException, GeneralFaultException {
        SetEndpointDetailsResponse resp = new SetEndpointDetailsResponse();

        int guestID = setEndpointDetailsRequest.getGuestID();
        if (guestID <= 0) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Bad guestID = " + guestID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String endpointGUID = setEndpointDetailsRequest.getEID();
        if (endpointGUID.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Endpoint ID: " + endpointGUID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        // save endpoint features
        EndpointFeatures endpointFeatures = new EndpointFeatures();
        EndpointFeature_type0[] features = setEndpointDetailsRequest.getEndpointFeature();
        if (features != null && features.length > 0) {
            EndpointFeatureName epfeatureName = null;
            String featureName = "";
            boolean isFeatureEnabled = false;
            for (EndpointFeature_type0 feature : features) {
                epfeatureName = feature.getFeature();
                if (epfeatureName != null) {
                    featureName = epfeatureName.getEndpointFeatureName();
                    isFeatureEnabled = feature.getEnable();
                    // check for features by name
                    if ("LectureMode".equalsIgnoreCase(featureName)) {
                        endpointFeatures.setLectureModeSupported(isFeatureEnabled);
                    }
                }
            }
        }

        int updatedCount = endpointService.updateEndpointFeaturesForGuest(guestID, endpointGUID, endpointFeatures);

        if (updatedCount == 0) { // this means guestID and endpointGUID don't go together
            EndpointNotBoundFault boundFault = new EndpointNotBoundFault();
            boundFault.setErrorMessage("Endpoint not bound to the guest");
            EndpointNotBoundFaultException notBoundFaultException = new EndpointNotBoundFaultException(
                    boundFault.getErrorMessage());
            notBoundFaultException.setFaultMessage(boundFault);
            throw notBoundFaultException;
        }

        // for CDRv3
        CDRinfo2 cdrinfo = new CDRinfo2();
        cdrinfo.setApplicationName(setEndpointDetailsRequest.getApplicationName());
        cdrinfo.setApplicationVersion(setEndpointDetailsRequest.getApplicationVersion());
        cdrinfo.setApplicationOs(setEndpointDetailsRequest.getApplicationOs());
        cdrinfo.setDeviceModel(setEndpointDetailsRequest.getDeviceModel());
        MessageContext msgContext = MessageContext.getCurrentMessageContext();
        cdrinfo.setEndpointPublicIPAddress((String) msgContext.getProperty(MessageContext.REMOTE_ADDR));
        if(setEndpointDetailsRequest.isExtDataSpecified() && StringUtils.isNotEmpty(setEndpointDetailsRequest.getExtData())) {
        		cdrinfo.setExtData(setEndpointDetailsRequest.getExtData());
        }
        if(setEndpointDetailsRequest.isExtDataTypeSpecified()) {
        	cdrinfo.setExtDataType(
					ExternalDataTypeEnum.validateExtDataType(
							setEndpointDetailsRequest.getExtDataType()
					));
        }
        user.updateEndpointCDRInfo(endpointGUID,cdrinfo);
        resp.setOK(OK_type0.OK);
        return resp;
    }

    @Override
    public GetRoomDetailsByRoomKeyResponse getRoomDetailsByRoomKey(GetRoomDetailsByRoomKeyRequest getRoomDetailsByRoomKeyRequest)
            throws  InvalidArgumentFaultException,
            ResourceNotAvailableFaultException,
            GeneralFaultException {

        if (logger.isDebugEnabled()) {
            logger.debug("Entering the getRoomDetailsByRoomKey method with roomKey " + getRoomDetailsByRoomKeyRequest.getRoomKey());
        }
        String roomKey = getRoomDetailsByRoomKeyRequest.getRoomKey();
        if (StringUtils.isBlank(roomKey)){
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("roomKey is required");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Room room = this.room.getRoomDetailsByRoomKey(roomKey, TenantContext.getTenantId());

        if (room == null) {
            ResourceNotAvailableFault fault = new ResourceNotAvailableFault();
            fault.setErrorMessage("Room not found for the roomKey = " + roomKey);
            ResourceNotAvailableFaultException exception = new ResourceNotAvailableFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        GetRoomDetailsByRoomKeyResponse roomDetailsByRoomKeyResponse = new GetRoomDetailsByRoomKeyResponse();
        EntityID entityID = new EntityID();
        entityID.setEntityID(room.getRoomID());
        roomDetailsByRoomKeyResponse.setEntityID(entityID);
        roomDetailsByRoomKeyResponse.setDisplayName(room.getDisplayName());
        roomDetailsByRoomKeyResponse.setDescription(room.getRoomDescription());
        roomDetailsByRoomKeyResponse.setExtension(room.getRoomExtNumber());
        roomDetailsByRoomKeyResponse.setEnabled(room.getRoomEnabled() == 1 ? true:false);
        roomDetailsByRoomKeyResponse.setLocked(room.getRoomLocked() == 1 ? true: false);
        roomDetailsByRoomKeyResponse.setPinned(room.getRoomPinned() == 1 ? true: false);
        roomDetailsByRoomKeyResponse.setName(room.getRoomName());
        roomDetailsByRoomKeyResponse.setType(room.getRoomType());
        return roomDetailsByRoomKeyResponse;
    }
    /**
	 * Auto generated method signature
	 *
	 * @param linkEndpointToGuestRequest
	 * @return linkEndpointToGuestResponse
	 * @throws AccessRestrictedFaultException
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */
	public com.vidyo.portal.guest.LinkEndpointToGuestResponse linkEndpointToGuest
		(
			com.vidyo.portal.guest.LinkEndpointToGuestRequest linkEndpointToGuestRequest
		)
		throws
		AccessRestrictedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		LinkEndpointToGuestResponse resp = new LinkEndpointToGuestResponse();

		String clientType = linkEndpointToGuestRequest.getClientType();
		if (clientType != null && (clientType.equalsIgnoreCase("I") || clientType.equalsIgnoreCase("A"))) {
			// Check to validate if mobile login is allowed for this tenant
			Tenant t = this.tenant.getTenantForRequest();

			if (t != null && t.getMobileLogin() == 0) {
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("Mobile Access Restricted for the tenant URL - " + t.getTenantURL());
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		String endpointGUID = linkEndpointToGuestRequest.getEID();
		if (endpointGUID.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Bad EID = " + endpointGUID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		int guestID = linkEndpointToGuestRequest.getGuestID();
		if (guestID <= 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Bad guestID = " + guestID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		this.user.linkEndpointToGuest(guestID, endpointGUID, linkEndpointToGuestRequest.getPak2());

		String vrIP = linkEndpointToGuestRequest.getVrIP();
		if (vrIP != null && !vrIP.equalsIgnoreCase("")) {
			this.user.updateEndpointIPaddress(endpointGUID, vrIP);
		}
		// for CDRv3
		CDRinfo2 cdrinfo = new CDRinfo2();
		cdrinfo.setApplicationName(linkEndpointToGuestRequest.getApplicationName());
		cdrinfo.setApplicationVersion(linkEndpointToGuestRequest.getApplicationVersion());
		cdrinfo.setApplicationOs(linkEndpointToGuestRequest.getApplicationOs());
		cdrinfo.setDeviceModel(linkEndpointToGuestRequest.getDeviceModel());
		MessageContext msgContext = MessageContext.getCurrentMessageContext();
		cdrinfo.setEndpointPublicIPAddress((String) msgContext.getProperty(MessageContext.REMOTE_ADDR));
		user.updateEndpointCDRInfo(endpointGUID,cdrinfo);

        resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param guestJoinConferenceRequest
	 * @return guestJoinConferenceResponse
	 * @throws InvalidArgumentFaultException
	 * @throws ResourceNotAvailableFaultException
	 * @throws GeneralFaultException
	 * @throws ConferenceLockedFaultException
	 * @throws WrongPinFaultException
	 */
	public com.vidyo.portal.guest.GuestJoinConferenceResponse guestJoinConference
		(
			com.vidyo.portal.guest.GuestJoinConferenceRequest guestJoinConferenceRequest
		)
		throws
		InvalidArgumentFaultException,
		ResourceNotAvailableFaultException,
		GeneralFaultException,
		RoomIsFullFaultException,
		ConferenceLockedFaultException,
		WrongPinFaultException,
		AllLinesInUseFaultException
	{
		GuestJoinConferenceResponse resp = new GuestJoinConferenceResponse();

		int guestID = guestJoinConferenceRequest.getGuestID();
		if (guestID <= 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid guestID = " + guestID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();
		guestJoinConfRequest.setGuestId(guestID);
		guestJoinConfRequest.setPin(guestJoinConferenceRequest.getPin());
		Integer tenantId = TenantContext.getTenantId();
		guestJoinConfRequest.setTenantId(tenantId);
		JoinConferenceResponse joinConferenceResponse = conferenceAppService
				.joinConferenceAsGuest(guestJoinConfRequest);

		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.INVALID_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Conference ID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.WRONG_PIN) {
			WrongPinFault fault = new WrongPinFault();
			fault.setErrorMessage("Wrong PIN");
			WrongPinFaultException exception = new WrongPinFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.ROOM_LOCKED) {
			ConferenceLockedFault fault = new ConferenceLockedFault();
			fault.setErrorMessage("Conference is Locked");
			ConferenceLockedFaultException exception = new ConferenceLockedFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.ROOM_CAPACITY_REACHED) {
			RoomIsFullFault fault = new RoomIsFullFault();
			fault.setErrorMessage("Room capacity reached");
			RoomIsFullFaultException exception = new RoomIsFullFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.OUT_OF_PORTS_ERROR) {
			AllLinesInUseFault fault = new AllLinesInUseFault();
			fault.setErrorMessage("All lines in use");
			AllLinesInUseFaultException exception = new AllLinesInUseFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() != JoinConferenceResponse.SUCCESS) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(joinConferenceResponse.getDisplayMessage());
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String referenceNumber = guestJoinConferenceRequest.getReferenceNumber();
		Guest guest = memberService.getGuest(guestID);
		String guid = guest.getEndpointGUID();
		this.user.updateEndpointReferenceNumber(guid, referenceNumber);
		
	    String extData = null;
	    if(guestJoinConferenceRequest.isExtDataSpecified() && StringUtils.isNotEmpty(guestJoinConferenceRequest.getExtData())) {
	      	extData = guestJoinConferenceRequest.getExtData();
	       	int extDataType = 0;
	        	
	      	if(guestJoinConferenceRequest.isExtDataTypeSpecified()) {
	           	extDataType = guestJoinConferenceRequest.getExtDataType();    
	       	}
	      	this.user.updateEndpointExtData(guid, extDataType, extData);
	    }
		resp.setOK(OK_type0.OK);
		return resp;
	}


    /**
     * Auto generated method signature
     *
     * @param getPortalVersionRequest2
     * @return getPortalVersionResponse3
     * @throws GeneralFaultException
     */

    public com.vidyo.portal.guest.GetPortalVersionResponse getPortalVersion
    (
       com.vidyo.portal.guest.GetPortalVersionRequest getPortalVersionRequest2
    )
    throws GeneralFaultException{
		logger.debug("Entering getPortalVersion() of VidyoPortalGuestServiceSkeleton");
		String portalVersion = system.getPortalVersion();
		GetPortalVersionResponse getPortalVersionResponse = new GetPortalVersionResponse();
		getPortalVersionResponse.setPortalVersion(portalVersion);
		logger.debug("Exiting getPortalVersion() of VidyoPortalGuestServiceSkeleton");
		return getPortalVersionResponse;
    }

	@Override
	public UnraiseHandResponse unraiseHand(UnraiseHandRequest unraiseHandRequest) throws InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering unraiseHand() of Guest API");
		}

		UnraiseHandResponse response = new UnraiseHandResponse();

		GuestHandUpdateRequest serviceRequest = new GuestHandUpdateRequest();
		serviceRequest.setGuestID(unraiseHandRequest.getGuestID());
		serviceRequest.setUsername(unraiseHandRequest.getUsername());
		serviceRequest.setHandRaised(false);

		HandUpdateResponse serviceResponse =  lectureModeService.updateHand(serviceRequest);

		if (serviceResponse.getStatus() == HandUpdateResponse.SUCCESS) {
			response.setOK(OK_type0.OK);
		} else {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage(serviceResponse.getMessage());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting unraiseHand() of Guest API");

		return response;
	}

	/**
     * Auto generated method signature
     * Get the current active client version
                                 * @param clientVersionRequest8
         * @return clientVersionResponse9
         * @throws InvalidArgumentFaultException
         * @throws GeneralFaultException
     */

    public com.vidyo.portal.guest.ClientVersionResponse getClientVersion
    (
        com.vidyo.portal.guest.ClientVersionRequest clientVersionRequest8
    )
    throws InvalidArgumentFaultException,GeneralFaultException{
    	ClientVersionResponse resp = new ClientVersionResponse();

		MessageContext context = MessageContext.getCurrentMessageContext();

		String type = clientVersionRequest8.getClientType().getValue();

		Map<String,String> clientVerMap = this.endpointUploadService.getClientVersion(type, this.upload_path, context.getTo().getAddress());
		resp.setCurrentTag(clientVerMap.get("CurrentTag"));
		resp.setInstallerURI(clientVerMap.get("InstallerURI"));

		return resp;
    }

	@Override
	public RaiseHandResponse raiseHand(RaiseHandRequest raiseHandRequest) throws InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering raiseHand() of Guest API");
		}

		RaiseHandResponse response = new RaiseHandResponse();

		GuestHandUpdateRequest serviceRequest = new GuestHandUpdateRequest();
		serviceRequest.setGuestID(raiseHandRequest.getGuestID());
		serviceRequest.setUsername(raiseHandRequest.getUsername());
		serviceRequest.setHandRaised(true);

		HandUpdateResponse serviceResponse =  lectureModeService.updateHand(serviceRequest);

		if (serviceResponse.getStatus() == HandUpdateResponse.SUCCESS) {
			response.setOK(OK_type0.OK);
		} else {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage(serviceResponse.getMessage());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting raiseHand() of Guest API");

		return response;
	}

	/**
     * NOTE: ADD NEW FEATURES TO THE BOTTOM OF THE LIST (see VPTL-5857)
     *
     * @param getPortalFeaturesRequest8
     * @return getPortalFeaturesResponse9
     * @throws GeneralFaultException
     */
    public GetPortalFeaturesResponse getPortalFeatures(GetPortalFeaturesRequest getPortalFeaturesRequest8)
        throws GeneralFaultException{
    	if (logger.isDebugEnabled()) {
			logger.debug("Entering getPortalFeatures() of Guest API");
		}

		GetPortalFeaturesResponse resp = new GetPortalFeaturesResponse();

		Configuration scheduledRoomconfig = this.system.getConfiguration("SCHEDULED_ROOM_PREFIX");

		Tenant tenant = null;
		Integer tenantId =  TenantContext.getTenantId();

		tenant = this.tenant.getTenant(tenantId);

		// FEATURE - ScheduledRoom
		boolean scheduledRoomEnabled = false;
		// If no prefix is set then scheduledRoom is disable
		if (scheduledRoomconfig == null
				|| scheduledRoomconfig.getConfigurationValue() == null
				|| scheduledRoomconfig.getConfigurationValue().trim().length() == 0)
		{
			scheduledRoomEnabled = false;
		} else {
			if (tenant != null) {
				scheduledRoomEnabled = tenant.getScheduledRoomEnabled() == 1;
			}
		}

		// FEATURE - Guest login for tenant
		PortalFeature_type0 guestFeature = new PortalFeature_type0();
		PortalFeatureName guestFeatureName = new PortalFeatureName();
		guestFeatureName.setPortalFeatureName("Guest");
		guestFeature.setFeature(guestFeatureName);
		guestFeature.setEnable(!this.tenant.isTenantNotAllowingGuests());

		resp.addPortalFeature(guestFeature);

		// FEATURE - IPC outbound and IPC inbound for tenant
		boolean IPCoutbound = false;
		boolean IPCinbound = false;
		if (tenantId != null) {
			IpcConfiguration interPortalConference = ipcConfigurationService.getIpcConfiguration((Integer)tenantId);
			IPCoutbound = interPortalConference.getOutbound() == 1;
			IPCinbound = interPortalConference.getInbound() == 1;
		}
		PortalFeature_type0 ipcOutboundFeature = new PortalFeature_type0();
		PortalFeatureName ipcOutboundFeatureName = new PortalFeatureName();
		ipcOutboundFeatureName.setPortalFeatureName("IPCoutbound");
		ipcOutboundFeature.setFeature(ipcOutboundFeatureName);
		ipcOutboundFeature.setEnable(IPCoutbound);

		resp.addPortalFeature(ipcOutboundFeature);

		PortalFeature_type0 ipcInboundFeature = new PortalFeature_type0();
		PortalFeatureName ipcInboundFeatureName = new PortalFeatureName();
		ipcInboundFeatureName.setPortalFeatureName("IPCinbound");
		ipcInboundFeature.setFeature(ipcInboundFeatureName);
		ipcInboundFeature.setEnable(IPCinbound);

		resp.addPortalFeature(ipcInboundFeature);

		// Is Moderator URL enabled always?
        PortalFeature_type0 moderatorURLFeature = new PortalFeature_type0();
        PortalFeatureName modePortalFeatureName = new PortalFeatureName();
        modePortalFeatureName.setPortalFeatureName("ModeratorURL");
        moderatorURLFeature.setFeature(modePortalFeatureName);
        moderatorURLFeature.setEnable(true);

        resp.addPortalFeature(moderatorURLFeature);

        // FEATURE - TlsTunneling
        boolean tlsTunneling = this.system.getTLSProxyConfiguration();

        PortalFeature_type0 tlsTunnelingFeature = new PortalFeature_type0();
        PortalFeatureName tlPortalFeatureName = new PortalFeatureName();
        tlPortalFeatureName.setPortalFeatureName("TlsTunneling");
        tlsTunnelingFeature.setFeature(tlPortalFeatureName);
        tlsTunnelingFeature.setEnable(tlsTunneling);

        resp.addPortalFeature(tlsTunnelingFeature);

        // FEATURES - Welcome and Login banners
        Banners banners = this.system.getBannersInfo();

        PortalFeature_type0 loginBannerFeature = new PortalFeature_type0();
        PortalFeatureName loginPortalFeatureName = new PortalFeatureName();
        loginPortalFeatureName.setPortalFeatureName("LoginBanner");
        loginBannerFeature.setFeature(loginPortalFeatureName);
        loginBannerFeature.setEnable(banners.getShowLoginBanner());

        resp.addPortalFeature(loginBannerFeature);

        PortalFeature_type0 welcomeBannerFeature = new PortalFeature_type0();
        PortalFeatureName welBannerPortalFeatureName = new PortalFeatureName();
        welBannerPortalFeatureName.setPortalFeatureName("WelcomeBanner");
        welcomeBannerFeature.setFeature(welBannerPortalFeatureName);
        welcomeBannerFeature.setEnable(banners.getShowWelcomeBanner());

        resp.addPortalFeature(welcomeBannerFeature);

        // FEATURE - EndpointPrivateChat
        PortalFeature_type0 endpointPrivateChatFeature = new PortalFeature_type0();
        PortalFeatureName endpointPrivateChatFeatureName = new PortalFeatureName();
        endpointPrivateChatFeatureName.setPortalFeatureName("EndpointPrivateChat");
        endpointPrivateChatFeature.setFeature(endpointPrivateChatFeatureName);

        // FEATURE - EndpointPrivateChat
        PortalFeature_type0 endpointPublicChatFeature = new PortalFeature_type0();
        PortalFeatureName endpointPublicChatFeatureName = new PortalFeatureName();
        endpointPublicChatFeatureName.setPortalFeatureName("EndpointPublicChat");
        endpointPublicChatFeature.setFeature(endpointPublicChatFeatureName);

        TenantConfiguration tenantConfiguration = this.tenant.getTenantConfiguration(tenantId);
        PortalChat portalChat = system.getPortalChat();
        if(portalChat.isChatAvailable()) {
            endpointPrivateChatFeature.setEnable(tenantConfiguration.getEndpointPrivateChat() == 1);

            endpointPublicChatFeature.setEnable(tenantConfiguration.getEndpointPublicChat() == 1);
        } else {
            endpointPrivateChatFeature.setEnable(false);

            endpointPublicChatFeature.setEnable(false);
        }

        resp.addPortalFeature(endpointPrivateChatFeature);
        resp.addPortalFeature(endpointPublicChatFeature);

		// FEATURE - CDR2.1
		PortalFeature_type0 cdrEnhancement = new PortalFeature_type0();
		PortalFeatureName cdrEnhancementFeatureName = new PortalFeatureName();
		cdrEnhancementFeatureName.setPortalFeatureName("CDR2_1");
		cdrEnhancement.setFeature(cdrEnhancementFeatureName);
		cdrEnhancement.setEnable(true);

		resp.addPortalFeature(cdrEnhancement);

        // FEATURE - ability send endpoint features to portal
        PortalFeature_type0 endpointDetails = new PortalFeature_type0();
        PortalFeatureName endpointDetailsName = new PortalFeatureName();
        endpointDetailsName.setPortalFeatureName("EndpointDetails");
        endpointDetails.setFeature(endpointDetailsName);
        endpointDetails.setEnable(true);

        resp.addPortalFeature(endpointDetails);


        // FEATURE - Make 16 tiles layout available on your VidyoPortal
        if(system.isTiles16Available()) {
            PortalFeature_type0 tiles16Features = new PortalFeature_type0();
            PortalFeatureName tiles16FeaturesName = new PortalFeatureName();
            tiles16FeaturesName.setPortalFeatureName("16TILES");
            tiles16Features.setFeature(tiles16FeaturesName);
            tiles16Features.setEnable(system.isTiles16Available());
            resp.addPortalFeature(tiles16Features);
        }

        // FEATURE - Indicate that this version supports the new password change page by reporting "HTMLChangePswd"
        PortalFeature_type0 htmlChangePswd = new PortalFeature_type0();
        PortalFeatureName htmlChangePswdName = new PortalFeatureName();
        htmlChangePswdName.setPortalFeatureName("HTMLChangePswd");
        htmlChangePswd.setFeature(htmlChangePswdName);

		AuthenticationConfig authConfig = system.getAuthenticationConfig(tenantId);
		AuthenticationType authType = authConfig.toAuthentication().getAuthenticationType();
		htmlChangePswd.setEnable(authType.equals(AuthenticationType.INTERNAL) ? true : false);

        resp.addPortalFeature(htmlChangePswd);

        // FEATURE - Indicate that the router is passing additional participant information starting in 3.3.
        PortalFeature_type0 routerParticipantInformation = new PortalFeature_type0();
        PortalFeatureName routerParticipantInformationName = new PortalFeatureName();
        routerParticipantInformationName.setPortalFeatureName("RouterParticipantInformation");
        routerParticipantInformation.setFeature(routerParticipantInformationName);

        routerParticipantInformation.setEnable(true);

        resp.addPortalFeature(routerParticipantInformation);

		// bind ack in VP 3.4.3 and above
		PortalFeature_type0 bindAckFeature = new PortalFeature_type0();
		PortalFeatureName bindAckFeatureName = new PortalFeatureName();
		bindAckFeatureName.setPortalFeatureName("BindAck");
		bindAckFeature.setFeature(bindAckFeatureName);
		bindAckFeature.setEnable(true);
		resp.addPortalFeature(bindAckFeature);


		// neo direct call flow supported in VP3.4.4 and above only
		// scheduled room + recurring + conference appData info
		PortalFeature_type0 neoDirectCallSupport = new PortalFeature_type0();
		PortalFeatureName neoDirectCallFeatureName = new PortalFeatureName();
		neoDirectCallFeatureName.setPortalFeatureName("NeoDirectCall");
		neoDirectCallSupport.setFeature(neoDirectCallFeatureName);
		neoDirectCallSupport.setEnable(scheduledRoomEnabled);

		resp.addPortalFeature(neoDirectCallSupport);

		Configuration createPublicRoomconfig = this.system.getConfiguration("CREATE_PUBLIC_ROOM_FLAG");

		boolean createPublicRoomEnabled = false;

		if (createPublicRoomconfig == null
				|| createPublicRoomconfig.getConfigurationValue() == null
				|| createPublicRoomconfig.getConfigurationValue().trim().length() == 0
				|| createPublicRoomconfig.getConfigurationValue().trim().equals("0"))
		{
			createPublicRoomEnabled = false;
		} else {
			createPublicRoomEnabled = true;
		}

		PortalFeature_type0 createPublicRoomFeature = new PortalFeature_type0();
		PortalFeatureName createPublicRoomFeatureName = new PortalFeatureName();
		createPublicRoomFeatureName.setPortalFeatureName("CreatePublicRoom");
		createPublicRoomFeature.setFeature(createPublicRoomFeatureName);
		createPublicRoomFeature.setEnable(createPublicRoomEnabled);

		resp.addPortalFeature(createPublicRoomFeature);
		
		// TytoCare
		PortalFeature_type0 tytoCareFeature = new PortalFeature_type0();
		PortalFeatureName tytoCareFeatureFeatureName = new PortalFeatureName();
		tytoCareFeatureFeatureName.setPortalFeatureName(PortalFeaturesEnum.TytoCare.name());
		tytoCareFeature.setFeature(tytoCareFeatureFeatureName);
		tytoCareFeature.setEnable(extIntegrationService.isTenantTytoCareEnabled());
		resp.addPortalFeature(tytoCareFeature);

		// FEATURE - If SDK 2,2 router feature enabled
		Configuration opusAudio = system.getConfiguration("OPUS_AUDIO");

		if (opusAudio != null
				&& StringUtils.isNotBlank(opusAudio.getConfigurationValue()))
		{
			PortalFeature_type0 opusAudioFeature = new PortalFeature_type0();
			PortalFeatureName opusAudioFeatureName = new PortalFeatureName();
			opusAudioFeatureName.setPortalFeatureName("OpusAudio");
			opusAudioFeature.setFeature(opusAudioFeatureName);
			opusAudioFeature.setEnable(opusAudio.getConfigurationValue().equalsIgnoreCase("1"));

			resp.addPortalFeature(opusAudioFeature);
		}

		Configuration sdk220 = system.getConfiguration("SDK220");
		if(sdk220 != null && StringUtils.isNotBlank(sdk220.getConfigurationValue())) {
			PortalFeature_type0 sdk220Feature = new PortalFeature_type0();
			PortalFeatureName sdk220FeatureName = new PortalFeatureName();
			sdk220FeatureName.setPortalFeatureName("SDK220");
			sdk220Feature.setFeature(sdk220FeatureName);
			sdk220Feature.setEnable(sdk220.getConfigurationValue().equalsIgnoreCase("1"));
			resp.addPortalFeature(sdk220Feature);
			Configuration vp9 = system.getConfiguration("VP9");
			if(sdk220.getConfigurationValue().equalsIgnoreCase("1") && vp9 != null && StringUtils.isNotBlank(vp9.getConfigurationValue())) {
				PortalFeature_type0 vp9Feature = new PortalFeature_type0();
				PortalFeatureName vp9FeatureName = new PortalFeatureName();
				vp9FeatureName.setPortalFeatureName("VP9");
				vp9Feature.setFeature(vp9FeatureName);
				vp9Feature.setEnable(vp9.getConfigurationValue().equalsIgnoreCase("1"));
				resp.addPortalFeature(vp9Feature);
			}
		}

		if(tenantConfiguration != null) {
			PortalFeature_type0 testCallFeature = new PortalFeature_type0();
			PortalFeatureName testCallFeatureName = new PortalFeatureName();
			testCallFeatureName.setPortalFeatureName("TestCall");
			testCallFeature.setFeature(testCallFeatureName);
			testCallFeature.setEnable(tenantConfiguration.getTestCall() == 1);
			resp.addPortalFeature(testCallFeature);

			PortalFeature_type0 personalRoomFeature = new PortalFeature_type0();
			PortalFeatureName personalRoomFeatureName = new PortalFeatureName();
			personalRoomFeatureName.setPortalFeatureName("PersonalRoom");
			personalRoomFeature.setFeature(personalRoomFeatureName);
			personalRoomFeature.setEnable(tenantConfiguration.getPersonalRoom() == 1);
			resp.addPortalFeature(personalRoomFeature);

		}



		if (logger.isDebugEnabled()) {
			logger.debug("Exiting getPortalFeatures() of User API v.1.1");
		}

		return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param recoverPasswordRequest
     * @throws GeneralFaultException :
     * @throws NotificationEmailNotConfiguredFaultException :
     */
    public RecoverPasswordResponse recoverPassword(RecoverPasswordRequest recoverPasswordRequest)
        throws EmailAddressNotFoundFaultException,GeneralFaultException,NotificationEmailNotConfiguredFaultException {

    	if (logger.isDebugEnabled()) {
			logger.debug("Entering recoverPassword() of Guest API");
		}

    	String emailAddress = recoverPasswordRequest.getEmailAddress();

    	if(emailAddress == null || emailAddress.isEmpty()) {
        	logger.error("emailAddress is empty");
        	EmailAddressNotFoundFault fault = new EmailAddressNotFoundFault();
			fault.setErrorMessage("emailAddress is empty");
			EmailAddressNotFoundFaultException exception = new EmailAddressNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);

			throw exception;
        }

    	MessageContext msgContext = MessageContext.getCurrentMessageContext();
    	HttpServletRequest request = (HttpServletRequest)msgContext.getProperty("transport.http.servletRequest");
    	String scheme = request.getScheme();
    	String host = request.getHeader("host");
    	String contextPath = request.getContextPath();

    	try {
			loginService.forgotAPIPassword(TenantContext.getTenantId(), emailAddress, scheme, host, contextPath);
		} catch (NotificationEmailAddressNotConfiguredException e) {
			String errMsg = "Notification emailAddress(From) is not configured";
			logger.error(errMsg);
			NotificationEmailNotConfiguredFault fault = new NotificationEmailNotConfiguredFault();
			fault.setErrorMessage(errMsg);
			NotificationEmailNotConfiguredFaultException exception = new NotificationEmailNotConfiguredFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);

			throw exception;
		} catch (EmailAddressNotFoundException e) {
			String errMsg = "emailAddress " + emailAddress +" is not found";
			logger.error(errMsg);
        	EmailAddressNotFoundFault fault = new EmailAddressNotFoundFault();
			fault.setErrorMessage(errMsg);
			EmailAddressNotFoundFaultException exception = new EmailAddressNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);

			throw exception;
		} catch (GeneralException e) {
			String errMsg = e.getMessage();
			logger.error(errMsg);
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(errMsg);
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);

			throw exception;
		}

    	RecoverPasswordResponse resp = new RecoverPasswordResponse();
    	resp.setOK(OK_type0.OK);

    	if (logger.isDebugEnabled()) {
			logger.debug("Exiting recoverPassword() of Guest API");
		}

    	return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param whatIsMyIPAddressRequest10
     * @return whatIsMyIPAddressResponse11
     * @throws GeneralFaultException
     */

    public WhatIsMyIPAddressResponse whatIsMyIPAddress(WhatIsMyIPAddressRequest whatIsMyIPAddressRequest10)
        throws GeneralFaultException{

        if (logger.isDebugEnabled()) {
            logger.debug("Entering whatIsMyIPAddress() of Guest API v.1.1");
        }

        MessageContext msgContext = MessageContext.getCurrentMessageContext();

        WhatIsMyIPAddressResponse response = new WhatIsMyIPAddressResponse();
        String remoteIpAddress = (String) msgContext.getProperty(MessageContext.REMOTE_ADDR);
        if(remoteIpAddress == null || remoteIpAddress.isEmpty()) {
            response.setEndpointExternalIPAddress("Unknown");
        } else {
            response.setEndpointExternalIPAddress(remoteIpAddress);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting whatIsMyIPAddress() of Guest API v.1.1");
        }

        return response;
    }

	@Override
	public CreateTestcallRoomResponse createTestcallRoom(CreateTestcallRoomRequest createTestcallRoomRequest)
			throws TestcallRoomCreationFaultException, GeneralFaultException {

		boolean canCreateTestcallRoom = true;
		String errorMessage = null;
		Tenant tenantDetails = tenant.getTenant(TenantContext.getTenantId());
		TenantConfiguration tenantConfiguration = tenant.getTenantConfiguration(TenantContext.getTenantId());
		Configuration systemConfiguration = system.getConfiguration(WEBRTC_TEST_MEDIA_SERVER);
		String configurationURL = tenantDetails.getTenantWebRTCURL();
		TestCallService.PortConfig portConfig = TestCallService.PortConfig.TENANT;

		if (tenantDetails == null || tenantDetails.getGuestLogin() == 0) {
			canCreateTestcallRoom = false;
			errorMessage = "Guest login is disabled";
		}
		if(canCreateTestcallRoom) {
			Configuration scheduledRoomPrefixConfiguration = system.getConfiguration(SCHEDULED_ROOM_PREFIX);
			boolean schRoomEnabled = (scheduledRoomPrefixConfiguration == null
					|| StringUtils.isEmpty(scheduledRoomPrefixConfiguration.getConfigurationValue())) ? false : true;
			if (!schRoomEnabled) {
				canCreateTestcallRoom = false;
				errorMessage = "Scheduled Room creation is disabled";
			}
		}
		if (canCreateTestcallRoom) {
			if (tenantConfiguration == null || tenantConfiguration.getTestCall() == 0) {
				canCreateTestcallRoom = false;
				errorMessage = "Testcall feature is disabled";
			}
		}
		if (canCreateTestcallRoom) {
			if (tenantDetails == null || tenantDetails.getScheduledRoomEnabled() == 0) {
				canCreateTestcallRoom = false;
				errorMessage = "Scheduled Room creation is disabled";
			}
		}
		//VPTL-7773 - Fallback to system level configuration if the webRTC is not present for tenant.
		if (canCreateTestcallRoom) {
			if (StringUtils.isBlank(configurationURL)) {
				if (systemConfiguration == null  || StringUtils.isBlank(systemConfiguration.getConfigurationValue())){
					//fallback to system level configuration of WebRTC. But if the url is not configured throw error
					canCreateTestcallRoom = false;
					errorMessage = "WebRTC server URL is not configured.";
				} else{
					// Now safe to fallback to system level configuration of WebRTC
					configurationURL = systemConfiguration.getConfigurationValue();
					portConfig = TestCallServiceImpl.PortConfig.SYSTEM;
					logger.info("WebRTC configuration set to system level setting.");
				}
			}
		}

		if (!canCreateTestcallRoom) {
			logger.error(String.format("Error while creating a testcall room. %s. Tenant Id : %d", errorMessage, TenantContext.getTenantId()));
			TestcallRoomCreationFault faultObj = new TestcallRoomCreationFault();
			faultObj.setErrorMessage(errorMessage);
			TestcallRoomCreationFaultException testcallRoomCreationFaultException = new TestcallRoomCreationFaultException();
			testcallRoomCreationFaultException.setFaultMessage(faultObj);
			throw testcallRoomCreationFaultException;
		}

		ScheduledRoomResponse scheduledRoomResponse = null;
		try {
			scheduledRoomResponse = testCallService.createScheduledRoomForTestCall(null);
		} catch (ScheduledRoomCreationException exceptionObj) {
			TestcallRoomCreationFault fault = new TestcallRoomCreationFault();
			fault.setErrorMessage(exceptionObj.getMessage());
			TestcallRoomCreationFaultException exception = new TestcallRoomCreationFaultException();
			exception.setFaultMessage(fault);
			throw exception;
		}
		//VPTL-7615 - Integrate test call feature with WebRTC.
		CallDetails callDetails = new CallDetails();
		Tenant tenant = this.tenant.getTenant(TenantContext.getTenantId());
		String tenantURL = tenant.getTenantURL();
		callDetails.setPortal(tenantURL);
		// Get room url & parse the key - (key= or /join/)
		String roomKeyStr = scheduledRoomResponse.getRoom().getRoomKey();
		callDetails.setRoomKey(roomKeyStr);
		// get webrtc url
		//Set the call name from the configuration.
		Configuration testCallUserNameConfig = this.system.getConfiguration(TEST_CALL_SERVER_USERNAME);
		callDetails.setName(StringUtils.defaultIfBlank(testCallUserNameConfig.getConfigurationValue(), DEFAULT_TEST_CALL_USERNAME));

		// rest template and set the json with all properties.
		//Now invoke the testCall service to make the test call.
		try {
			testCallService.testCall(callDetails, configurationURL, portConfig);
		} catch (TestCallException exception){
			//There is an exception while establishing the connection with WebRTC server.
			logger.warn("Deleting the scheduled room entry from DB after test call failed:: TenantID : "+TenantContext.getTenantId()+"roomKey: "+roomKeyStr);
			room.deleteScheduledRoomByRoomKey(roomKeyStr);
			TestcallRoomCreationFault faultObj = new TestcallRoomCreationFault();
			faultObj.setErrorMessage(exception.getMessage());
			TestcallRoomCreationFaultException testcallRoomCreationFaultException = new TestcallRoomCreationFaultException();
			testcallRoomCreationFaultException.setFaultMessage(faultObj);
			throw testcallRoomCreationFaultException;
		}
		//If there is no exception return the response.
		long pin = scheduledRoomResponse.getPin();

		Pin_type1 roomPin = new Pin_type1();
		roomPin.setPin_type0(Long.toString(pin));


		CreateTestcallRoomResponse createTestcallRoomResponse = new CreateTestcallRoomResponse();

		String extension = scheduledRoomResponse.getRoomExtn();

		if (extension != null ) {
			Extension_type1 resExtension = new Extension_type1();
			resExtension.setExtension_type0(extension);
			createTestcallRoomResponse.setExtension(resExtension);
		}
		try {
			createTestcallRoomResponse.setRoomURL(new URI(scheduledRoomResponse.getRoom().getRoomURL()));
		} catch (URI.MalformedURIException e) {
			e.printStackTrace();
		}
		createTestcallRoomResponse.setRoomKey(roomKeyStr);

		return createTestcallRoomResponse;
	}
}
