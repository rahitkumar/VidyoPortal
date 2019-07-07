package com.vidyo.ws.cac;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.TransportHeaders;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.ssl.Base64;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTF8String;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.security.token.PersistentTokenService;
import com.vidyo.service.security.token.request.TokenCreationRequest;
import com.vidyo.service.security.token.response.TokenCreationResponse;

public class VidyoPortalCACServiceSkeleton implements
		VidyoPortalCACServiceSkeletonInterface {
	/** Logger for this class and subclasses */

	protected final Logger logger = LoggerFactory
			.getLogger(VidyoPortalCACServiceSkeleton.class.getName());
	private IUserService userService;
	private IMemberService member;
	private PersistentTokenService persistentTokenService;
	private LicensingService licensingService;
	private ITenantService tenantService;
	private IServiceService serviceService;
	private IConferenceService conferenceService;
	private IRoomService roomService;
	private ISystemService systemService;
	private static final String X509_BEGIN = "-----BEGIN CERTIFICATE-----";
	private static final String X509_END = "-----END CERTIFICATE-----";
	/** ObjectID for UPN .print the certificate you can see this id */
	public static final String UPN_OBJECTID = "1.3.6.1.4.1.311.20.2.3";
	//copied from member service
	private static final String REGEX_USERNAME = "^[a-zA-Z0-9-_\\@\\-\\.]+$";


	public void setLicensingService(LicensingService licensingService) {
		this.licensingService = licensingService;
	}
	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}
	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public void setServiceService(IServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public void setConferenceService(IConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public void setMember(IMemberService member) {
		this.member = member;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setPersistentTokenService(
			PersistentTokenService persistentTokenService) {
		this.persistentTokenService = persistentTokenService;
	}

	@Override
	public GenerateAuthTokenResponse generateAuthToken(
			GenerateAuthTokenRequest generateAuthTokenRequest)

					throws EndpointNotBoundFaultException, NotLicensedFaultException, InvalidArgumentFaultException,
					GeneralFaultException, SeatLicenseExpiredFaultException  {
		logger.debug("Entering generateAuthToken() of VidyoPortalCACServiceSkeleton");

			checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

			String userName = fetchUserName();

			int tenantId = TenantContext.getTenantId();
			User user = userService.getUserByUsernameWithOutAuth(userName, tenantId);
			if (user == null || user.getMemberID() == 0) {
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("Invalid User");
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (generateAuthTokenRequest.getEndpointId() == null
					|| generateAuthTokenRequest.getEndpointId().trim().length() == 0) {
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("Invalid EndpointId");
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			TokenCreationRequest tokenCreationRequest = new TokenCreationRequest();
			tokenCreationRequest.setEndpointId(generateAuthTokenRequest.getEndpointId().trim());
			tokenCreationRequest.setTenantId(user.getTenantID());
			tokenCreationRequest.setMemberId(user.getMemberID());
			tokenCreationRequest.setUsername(user.getUsername());
			//tokenCreationRequest.setValidityTime(generateAuthTokenRequest.getValidityTime().getValidityTime_type0());

			TokenCreationResponse tokenCreationResponse = persistentTokenService
					.createPersistentToken(tokenCreationRequest);

			if (tokenCreationResponse.getStatus() != TokenCreationResponse.SUCCESS) {
				if (tokenCreationResponse.getStatus() == TokenCreationResponse.ENDPOINT_NOT_BOUND_TO_USER) {
					EndpointNotBoundFault boundFault = new EndpointNotBoundFault();
					boundFault.setErrorMessage("Endpoint not bound to the user");
					EndpointNotBoundFaultException notBoundFaultException = new EndpointNotBoundFaultException(
							boundFault.getErrorMessage());
					notBoundFaultException.setFaultMessage(boundFault);
					throw notBoundFaultException;
				}
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage(tokenCreationResponse.getMessage());
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			GenerateAuthTokenResponse authTokenResponse = new GenerateAuthTokenResponse();
			authTokenResponse.setAuthToken(tokenCreationResponse.getToken());
			logger.debug("Exiting generateAuthToken() of VidyoPortalCACServiceSkeleton");
			return authTokenResponse;
			}

	private void validateUser(User user, String userName) throws GeneralFaultException {
		if (user == null || user.getMemberID() == 0) {
			generateFaultException("Invalid User.User "+userName+" not found in the database");
		}

	}




	private void generateFaultException(String message)
			throws GeneralFaultException {
		GeneralFault fault = new GeneralFault();
		fault.setErrorMessage(message);
		GeneralFaultException exception = new GeneralFaultException(
				fault.getErrorMessage());
		exception.setFaultMessage(fault);
		logger.error(message);
		throw exception;
	}

	@Override
	public LinkEndpointResponse linkEndpoint(
			LinkEndpointRequest linkEndpointRequest)
			throws InvalidArgumentFaultException, GeneralFaultException,
			SeatLicenseExpiredFaultException, AccessRestrictedFaultException,
			NotLicensedFaultException {
		if (logger.isDebugEnabled()){
			logger.debug("Entering linkEndpoint() of User API v.1.1");
		}

		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
		LinkEndpointResponse resp = new LinkEndpointResponse();
		String endpointGUID = linkEndpointRequest.getEID();
		String userName = fetchUserName();

		int tenantId = TenantContext.getTenantId();
		User user = userService.getUserByUsernameWithOutAuth(userName, tenantId);
		validateUser(user,userName);
		validateEndPoints(linkEndpointRequest, user);

		com.vidyo.bo.Entity entity = member.getContact(user.getMemberID());
		// The above call returns the memberId and personal/legacy roomId always
		Map<Integer, Integer> memberIdPersonalRoomIdMap = new HashMap<Integer, Integer>();
		memberIdPersonalRoomIdMap.put(entity.getMemberID(), entity.getRoomID());

		Entity_type0 wsEntity = getWSEntityFromBOEntity(user, entity,
				memberIdPersonalRoomIdMap);


		// LinkEndpoint Request will inform the portal which prng should be used

		userService.linkEndpointToUser(endpointGUID,
				linkEndpointRequest.getClientType(),
				linkEndpointRequest.getPak2(),user);

		logger.debug("completed link end point");;

		String vrIP = linkEndpointRequest.getVrIP();
		if (vrIP != null && !vrIP.equalsIgnoreCase("")) {
			userService.updateEndpointIPaddress(endpointGUID, vrIP);
		}
		// for CDRv3
		CDRinfo2 cdrinfo = new CDRinfo2();
		cdrinfo.setApplicationName(linkEndpointRequest.getApplicationName());
		cdrinfo.setApplicationVersion(linkEndpointRequest
				.getApplicationVersion());
		cdrinfo.setApplicationOs(linkEndpointRequest.getApplicationOs());
		cdrinfo.setDeviceModel(linkEndpointRequest.getDeviceModel());
		MessageContext msgContext = MessageContext.getCurrentMessageContext();
		cdrinfo.setEndpointPublicIPAddress((String) msgContext
				.getProperty(MessageContext.REMOTE_ADDR));
		userService.updateEndpointCDRInfo(endpointGUID, cdrinfo);

		resp.setEntity(wsEntity);

		if (logger.isDebugEnabled()){
			logger.debug("Exiting linkEndpoint() of User API v.1.1");
			logger.debug("completed"+resp.toString());
		}
		return resp;

	}

	private void validateEndPoints(LinkEndpointRequest linkEndpointRequest,
			User user) throws InvalidArgumentFaultException,
			AccessRestrictedFaultException {
		if (linkEndpointRequest.getEID().equalsIgnoreCase("")) {
			generateInvalidArgumentFaultException("Invalid Endpoint ID: " + linkEndpointRequest.getEID());
		}
		if (linkEndpointRequest.getClientType() != null
				&& (linkEndpointRequest.getClientType().equalsIgnoreCase("I") || linkEndpointRequest
						.getClientType().equalsIgnoreCase("A"))) { // Check to
																	// validate
																	// if mobile
																	// login is
																	// allowed
																	// for this
																	// tenant
			Tenant tenant = tenantService.getTenant(user.getTenantID());

			if (tenant == null) {
				generateInvalidArgumentFaultException("Invalid Tenant ID: "
						+ user.getTenantID());
			}
			if (tenant.getMobileLogin() == 0) {
				generateAccessRestrictedFaultException("Mobile Access Restricted for this User "
						+ user.getUsername());
			}
		}
	}

	private void generateAccessRestrictedFaultException(String message)
			throws AccessRestrictedFaultException {
		AccessRestrictedFault fault = new AccessRestrictedFault();
		fault.setErrorMessage(message);
		AccessRestrictedFaultException exception = new AccessRestrictedFaultException(
				fault.getErrorMessage());
		exception.setFaultMessage(fault);
		logger.error(message);
		throw exception;
	}

	private void generateInvalidArgumentFaultException(String message)
			throws InvalidArgumentFaultException {
		InvalidArgumentFault fault = new InvalidArgumentFault();
		fault.setErrorMessage(message);
		InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
				fault.getErrorMessage());
		exception.setFaultMessage(fault);
		logger.error(message);
		throw exception;
	}

	private Entity_type0 getWSEntityFromBOEntity(User user,
			com.vidyo.bo.Entity entity,
			Map<Integer, Integer> memberIdPersonalRoomIdMap) {
		Entity_type0 wsEntity = new Entity_type0();

		EntityID entityID = new EntityID();
		entityID.setEntityID(String.valueOf(entity.getRoomID()));
		wsEntity.setEntityID(entityID);

		if (entity.getRoomType() != null) {
			if (entity.getRoomType().equalsIgnoreCase("Personal")) {
				wsEntity.setEntityType(EntityType_type0.Member);
				wsEntity.setCanCallDirect(true);
				wsEntity.setCanJoinMeeting(entity.getRoomEnabled() == 1);
				EntityID ownerID = new EntityID();
				ownerID.setEntityID(String.valueOf(entity.getRoomID()));
				wsEntity.setOwnerID(ownerID);
			} else if (entity.getRoomType().equalsIgnoreCase("Public")) {
				wsEntity.setEntityType(EntityType_type0.Room);
				wsEntity.setCanCallDirect(false);
				wsEntity.setCanJoinMeeting(entity.getRoomEnabled() == 1);
				EntityID ownerID = new EntityID();
				if(memberIdPersonalRoomIdMap != null && memberIdPersonalRoomIdMap.get(entity.getMemberID()) != null) {
					ownerID.setEntityID(String.valueOf(memberIdPersonalRoomIdMap.get(entity.getMemberID())));
				} else {
					ownerID.setEntityID(String.valueOf(entity.getRoomID()));
				}
				wsEntity.setOwnerID(ownerID);
			} else if (entity.getRoomType().equalsIgnoreCase("Legacy")) {
				wsEntity.setEntityType(EntityType_type0.Legacy);
				wsEntity.setCanCallDirect(true);
				wsEntity.setCanJoinMeeting(false);
				EntityID ownerID = new EntityID();
				ownerID.setEntityID(String.valueOf(entity.getRoomID()));
				wsEntity.setOwnerID(ownerID);
			} else if(entity.getRoomType().equalsIgnoreCase("Scheduled")) {
				wsEntity.setEntityType(EntityType_type0.Room);
				wsEntity.setCanCallDirect(false);
				wsEntity.setCanJoinMeeting(true);
				EntityID ownerID = new EntityID();
				if(memberIdPersonalRoomIdMap != null && memberIdPersonalRoomIdMap.get(entity.getMemberID()) != null) {
					ownerID.setEntityID(String.valueOf(memberIdPersonalRoomIdMap.get(entity.getMemberID())));
				} else {
					ownerID.setEntityID(String.valueOf(entity.getRoomID()));
				}
				wsEntity.setOwnerID(ownerID);
			}
		} else {
			wsEntity.setEntityType(EntityType_type0.Member);
			wsEntity.setCanCallDirect(false);
			wsEntity.setCanJoinMeeting(false);
		}

		if(entity.getAllowRecording() == 1) {
			wsEntity.setCanRecordMeeting(true);
		} else {
			wsEntity.setCanRecordMeeting(false);
		}

		if (entity.getMemberID() != 0) {
			//Member member = memberService.getMember(entity.getMemberID()); // Perf Issue - unnecessary call just for email address

			wsEntity.setEmailAddress(entity.getEmailAddress());
			wsEntity.setTenant(entity.getTenantName());

			switch (entity.getLangID()) {
			case 1:
				wsEntity.setLanguage(Language_type0.en);
				break;
			case 2:
				wsEntity.setLanguage(Language_type0.fr);
				break;
			case 3:
				wsEntity.setLanguage(Language_type0.ja);
				break;
			case 4:
				wsEntity.setLanguage(Language_type0.zh_CN);
				break;
			case 5:
				wsEntity.setLanguage(Language_type0.es);
				break;
			case 6:
				wsEntity.setLanguage(Language_type0.it);
				break;
			case 7:
				wsEntity.setLanguage(Language_type0.de);
				break;
			case 8:
				wsEntity.setLanguage(Language_type0.ko);
				break;
			case 9:
				wsEntity.setLanguage(Language_type0.pt);
				break;
			case 10:
				wsEntity.setLanguage(Language_type0.Factory
						.fromValue(systemService.getSystemLang(
								entity.getTenantID()).getLangCode()));
				break;
			case 11:
				wsEntity.setLanguage(Language_type0.fi);
				break;
			case 12:
				wsEntity.setLanguage(Language_type0.pl);
				break;
			case 13:
				wsEntity.setLanguage(Language_type0.zh_TW);
				break;
			case 14:
				wsEntity.setLanguage(Language_type0.th);
				break;
			case 15:
				wsEntity.setLanguage(Language_type0.ru);
				break;
			case 16:
				wsEntity.setLanguage(Language_type0.tr);
				break;
			default:
				wsEntity.setLanguage(Language_type0.Factory
						.fromValue(systemService.getSystemLang(
								entity.getTenantID()).getLangCode()));
				break;
			}

			if (entity.getModeID() == 1) {
				wsEntity.setMemberMode(MemberMode_type0.Available);
			} else if (entity.getModeID() == 2) {
				wsEntity.setMemberMode(MemberMode_type0.Away);
			} else if (entity.getModeID() == 3) {
				wsEntity.setMemberMode(MemberMode_type0.DoNotDisturb);
			}
		} else {
			EntityID ownerID = new EntityID();
			ownerID.setEntityID(String.valueOf(0));
			wsEntity.setOwnerID(ownerID);
			wsEntity
					.setLanguage(Language_type0.Factory.fromValue(systemService
							.getSystemLang(entity.getTenantID()).getLangCode()));
			wsEntity.setMemberMode(MemberMode_type0.Available);
		}

		wsEntity.setDisplayName(StringUtils.isNotBlank(entity.getDisplayName()) ? entity.getDisplayName() : entity.getName());
		wsEntity.setExtension(entity.getExt());
		wsEntity.setDescription(entity
                .getDescription());

		if (entity.getMemberStatus() == 0) {
			wsEntity.setMemberStatus(MemberStatus_type0.Offline);
		} else if (entity.getMemberStatus() == 1) {
			wsEntity.setMemberStatus(MemberStatus_type0.Online);
		} else if (entity.getMemberStatus() == 2) {
			wsEntity.setMemberStatus(MemberStatus_type0.Busy);
		} else if (entity.getMemberStatus() == 3) {
			wsEntity.setMemberStatus(MemberStatus_type0.Ringing);
		} else if (entity.getMemberStatus() == 4) {
			wsEntity.setMemberStatus(MemberStatus_type0.RingAccepted);
		} else if (entity.getMemberStatus() == 5) {
			wsEntity.setMemberStatus(MemberStatus_type0.RingRejected);
		} else if (entity.getMemberStatus() == 6) {
			wsEntity.setMemberStatus(MemberStatus_type0.RingNoAnswer);
		} else if (entity.getMemberStatus() == 7) {
			wsEntity.setMemberStatus(MemberStatus_type0.Alerting);
		} else if (entity.getMemberStatus() == 8) {
			wsEntity.setMemberStatus(MemberStatus_type0.AlertCancelled);
		} else if (entity.getMemberStatus() == 9) {
			wsEntity.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
		} else if (entity.getMemberStatus() == 12) {
			wsEntity.setMemberStatus(MemberStatus_type0.Busy); // WaitForConfirm
		}

		RoomMode_type0 roomMode = new RoomMode_type0();
		// Room URL
		if (entity.getRoomKey() != null && !entity.getRoomKey().isEmpty() && user.getMemberID() == entity.getMemberID()) {
			StringBuilder path = new StringBuilder();
			Tenant tenant = tenantService.getTenant(entity.getTenantID());
			String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
			/*path.append(transportName).append("://").append(tenant.getTenantURL());
			path.append("/flex.html?roomdirect.html&key=");
			path.append(entity.getRoomKey());*/

			String joinURL = roomService.getRoomURL(systemService, transportName,
					tenant.getTenantURL(), entity.getRoomKey());

			path.append(joinURL);
			if (tenantService.isTenantNotAllowingGuests()) {
				path.append("&noguest");
			}

			roomMode.setRoomURL(path.toString());
		}
		// Room Lock
		roomMode.setIsLocked(entity.getRoomLocked() == 1);
		// Room PIN
		roomMode.setHasPIN(entity.getRoomPinned() == 1);
		String roomPIN = entity.getRoomPIN();
		if (roomPIN != null && !roomPIN.equalsIgnoreCase("") && user.getMemberID() == entity.getMemberID()) {
			roomMode.setRoomPIN(roomPIN); // roomPIN
		} else {
			// Mask room PIN if the user is not the owner
			roomMode.setRoomPIN("****");
		}
		// Moderator PIN
		roomMode.setHasModeratorPIN(entity.getRoomModeratorPIN() != null);
		String moderatorPIN = entity.getRoomModeratorPIN();
		if (moderatorPIN != null && !moderatorPIN.equalsIgnoreCase("") && user.getMemberID() == entity.getMemberID()) {
			roomMode.setModeratorPIN(moderatorPIN); // moderatorPIN
		} else {
			// Mask moderator PIN if the user is not the owner
			roomMode.setModeratorPIN("****");
		}
		// WebCast related values - only in case of webCastURL is present
/* REMOVED FROM WSDL FILE
		if (entity.getWebCastURL() != null) {
			roomMode.setWebCastURL(entity.getWebCastURL());
			roomMode.setHasWebCastPIN(entity.getWebCastPinned() == 1);
			String webCastPIN = entity.getWebCastPIN();
			if (webCastPIN != null && !webCastPIN.equalsIgnoreCase("")) {
				roomMode.setWebCastPIN(webCastPIN); // webCastPIN
			}
		}
*/

		wsEntity.setRoomMode(roomMode);

		if (entity.getRoomStatus() == 0) {
			wsEntity.setRoomStatus(RoomStatus_type0.Empty);
		} else if (entity.getRoomStatus() == 1) {
			wsEntity.setRoomStatus(RoomStatus_type0.Occupied);
		} else if (entity.getRoomStatus() == 2) {
			wsEntity.setRoomStatus(RoomStatus_type0.Full);
		}

		wsEntity.setVideo(entity.getVideo() == 1);
		wsEntity.setAudio(entity.getAudio() == 1);
		// ToDo - share?
		wsEntity.setAppshare(true);

		if (entity.getRoomOwner() == 1) {
			wsEntity.setCanControl(true);
		} else {
			wsEntity.setCanControl(false);
		}

		if (entity.getSpeedDialID() != 0) {
			wsEntity.setIsInMyContacts(true);
		} else {
			wsEntity.setIsInMyContacts(false);
		}

		if (entity.getRoomType() != null && entity.getRoomType().equalsIgnoreCase("Personal")) {
			if (entity.getPhone1() != null && ! entity.getPhone1().isEmpty()){
				wsEntity.setPhone1(entity.getPhone1());
			}

			if (entity.getPhone2() != null && ! entity.getPhone2().isEmpty()){
				wsEntity.setPhone2(entity.getPhone2());
			}

			if (entity.getPhone3() != null && ! entity.getPhone3().isEmpty()){
				wsEntity.setPhone3(entity.getPhone3());
			}

			if (entity.getDepartment() != null && ! entity.getDepartment().isEmpty()){
				wsEntity.setDepartment(entity.getDepartment());
			}

			if (entity.getTitle() != null && ! entity.getTitle().isEmpty()){
				wsEntity.setTitle(entity.getTitle());
			}

			if (entity.getInstantMessagerID() != null && ! entity.getInstantMessagerID().isEmpty()){
				wsEntity.setInstantMessagerID(entity.getInstantMessagerID());
			}

			if (entity.getLocation() != null && ! entity.getLocation().isEmpty()){
				wsEntity.setLocation(entity.getLocation());
			}

			if (entity.getThumbnailUpdateTime() != null){
				Configuration userImageConf = systemService.getConfiguration("USER_IMAGE");
				if (userImageConf != null && StringUtils.isNotBlank(userImageConf.getConfigurationValue()) && userImageConf.getConfigurationValue().equalsIgnoreCase("1")){
					TenantConfiguration tenantConfiguration = this.tenantService
						.getTenantConfiguration(TenantContext.getTenantId());
					if (tenantConfiguration.getUserImage() == 1) {
						Calendar updateTime = Calendar.getInstance();;
						updateTime.setTime(entity.getThumbnailUpdateTime());
						wsEntity.setThumbnailUpdateTime(updateTime);
					}
				}
			}
		}
		return wsEntity;
	}

	@Override
	public GetUserNameResponse getUserName(GetUserNameRequest getUserNameRequest)
			throws InvalidArgumentFaultException, GeneralFaultException,
			NotLicensedFaultException {
		if (logger.isDebugEnabled()){
			logger.debug("Entering getUserName() of User API v.1.1");
		}

		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		String userName = fetchUserName();

		GetUserNameResponse resp = new GetUserNameResponse();
		resp.setRealUserName(userName);

		if (logger.isDebugEnabled()){
			logger.debug("Exiting getUserName() of User API v.1.1");
		}

		return resp;
	}

	@Override
	public LogInResponse logIn(LogInRequest logInRequest)
			throws InvalidArgumentFaultException, GeneralFaultException,
			SeatLicenseExpiredFaultException, NotLicensedFaultException {

		if (logger.isDebugEnabled())
			logger.debug("Entering logIn() of User API v.1.1");
		String userName = fetchUserName();
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		LogInResponse resp = new LogInResponse();
		int tenantId = TenantContext.getTenantId();
		User user = userService
				.getUserByUsernameWithOutAuth(userName, tenantId);
		// checking if user is valid
		validateUser(user,userName);

		// Below condition takes for restricting login for mobile clients, if
		// the access is disabled at Tenant Level
		if (logInRequest.getClientType() != null
				&& (logInRequest.getClientType().getValue()
						.equalsIgnoreCase("I") || logInRequest.getClientType()
						.getValue().equalsIgnoreCase("A"))) {
			// Check to validate if mobile login is allowed for this tenant
			Tenant tenant = tenantService.getTenant(user.getTenantID());

			if (tenant == null) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
						"Invalid Tenant ID: " + user.getTenantID());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (tenant.getMobileLogin() == 0) {
				// If Mobile Access is restricted, don't allow login
				GeneralFault fault = new GeneralFault();
				GeneralFaultException exception = new GeneralFaultException(
						"Mobile Access Restricted");
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		PortalAccessKeys portalAccessKeys = userService
				.generatePAKforMember(user.getMemberID());
		// String PAK = userService.getPAKforMember(user.getMemberID());

		if (portalAccessKeys != null) {
			resp.setPak(portalAccessKeys.getPak());
			// TODO - uncomment the below line to include pak2
			resp.setPak2(portalAccessKeys.getPak2());
		}

		// set VM address
		try {
			String vm = conferenceService.getVMConnectAddress();

			if (vm != null && !vm.trim().equals("")) {
				resp.setVmaddress(vm);
			}
		} catch (NoVidyoManagerException ignored) {
			logger.error("No VidyoManager found, ignoring the Error");
		}

		String proxies = serviceService.getProxyCSVList(user.getMemberID());
		if (proxies != null && !proxies.trim().equals("")) {
			resp.setProxyaddress(proxies);
		}

		// set Location Tag
		String locTag = serviceService.getLocationTagForMember(user.getMemberID());
		if (locTag != null && !locTag.equalsIgnoreCase("")) {
			resp.setLoctag(locTag);
		} else {
			resp.setLoctag("Default");
		}
		MessageContext msgContext = MessageContext.getCurrentMessageContext();
		String remoteIpAddress = (String) msgContext.getProperty(MessageContext.REMOTE_ADDR);
		if(remoteIpAddress == null || remoteIpAddress.isEmpty()) {
			resp.setEndpointExternalIPAddress("Unknown");
		} else {
			resp.setEndpointExternalIPAddress(remoteIpAddress);
		}

		EndpointSettings es = this.systemService.getAdminEndpointSettings(TenantContext.getTenantId());
		resp.setMinMediaPort(es.getMinMediaPort());
		resp.setMaxMediaPort(es.getMaxMediaPort());
		resp.setVrProxyConfig(es.isAlwaysUseVidyoProxy() ? "ALWAYS" : "AUTO");

		resp.setMinimumPINLength(systemService.getMinPINLengthForTenant(TenantContext.getTenantId()));
		resp.setMaximumPINLength(SystemServiceImpl.PIN_MAX);

		if (logger.isDebugEnabled()){
			logger.debug("Exiting logIn() of User API v.1.1");
		}

		return resp;

	}


	/**
	 * This will read the certificate and extract the userName
	 *
	 * @param HttpServletRequest
	 *            request
	 * @return string
	 * @throws GeneralFaultException
	 * @throws IOException
	 * @throws CertificateException
	 */
	private String fetchUserName() throws GeneralFaultException {
		String userName = null;
		try {
			MessageContext context = MessageContext.getCurrentMessageContext();
			HttpServletRequest request = ((HttpServletRequest) context
					.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST));
			X509Certificate certs[] =   (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
			X509Certificate clientCert =null;

			if(certs!=null && certs.length>0){
				 clientCert = certs[0];
			}
			if (clientCert == null) {
				generateFaultException("No certificate found");
			}
			// parising the certificate
			//cert = parseCertificate(certicatePEM);

			javax.security.auth.x500.X500Principal principal = clientCert
					.getSubjectX500Principal();
			// getting the username
			userName = getUserNameFromCertificate(clientCert, principal);
			logger.warn("------User name ---------" +userName+". This user name should be exist in the database");

		} catch (CertificateException e) {
			generateFaultException(e.getMessage());

		} catch (IOException e) {
			generateFaultException(e.getMessage());
		}

		catch (Exception e) {
			// this way no need of doing another null check. the only way
			// throwing null is going through exception. Exception will throw
			// back the propogation
			generateFaultException(e.getMessage());
		}

		return userName;

	}

	/**
	 * @param cert
	 * @param principal
	 * @throws IOException
	 * @throws CertificateException
	 * @throws InvalidNameException
	 */
	private String getUserNameFromCertificate(X509Certificate cert,
			javax.security.auth.x500.X500Principal principal)
			throws IOException, CertificateException, InvalidNameException {
		// getting the stuff either from Common Name or subject alternative name
		// based on the db. we will decide from where to fetch
		String userName = null;
		// in cac UI, there is a drop down user can specify from where system
		// should fetch values .
		if (logger.isDebugEnabled()){
			logger.debug("certificate CN "+principal.getName());
			logger.debug("certificate Subject AN "+cert.getSubjectAlternativeNames().toString());
		}
		String commonNameDropDownValue = systemService
				.getCACUserNameExtractType(TenantContext.getTenantId());
		if (commonNameDropDownValue != null
				&& commonNameDropDownValue.equalsIgnoreCase("0")) {
			LdapName ldap=new LdapName(principal.getName());
			for(Rdn rdn: ldap.getRdns()) {
			    if(rdn.getType().equalsIgnoreCase("CN")){
			    	userName=(String) rdn.getValue();
			    }
			}
			if (logger.isDebugEnabled()){
				logger.debug(userName + "userName from the CN before validating");
			}
			userName = validateUserName(userName);
			if (logger.isDebugEnabled()){
				logger.debug(userName + "userName from the CN after validating");
			}


			return userName;

		} else {
			if (logger.isDebugEnabled()){
				logger.debug(" userName from the Principle ");
			}


			// getting the subject alternatives
			Iterator<List<?>> iterator = cert.getSubjectAlternativeNames()
					.iterator();
			while (iterator.hasNext()) {
				List<?> obj = iterator.next();
				String edipi = getAltnameSequence((List<?>) obj);
				if(edipi!=null){
				// if it is a valid edipi , it is in the format of <edipi>@mil
					if (edipi.toString().contains("@")) {

						if (logger.isDebugEnabled()){
							logger.debug(edipi + "edipi from the principal before validating");
						}

						userName= validateUserName(edipi.toString().split("@")[0]);
					} else {
						if (logger.isDebugEnabled()){
							logger.debug(" userName is different from  <edipi>@mil ");
						}


						// in case if they changed the plan!
						userName = validateUserName(edipi);
					}
					//return back the username since we found it from the certficate. the job of this function is over
					return userName;
				}
			}
		}
		return userName;
	}

private String validateUserName(String userName) throws CertificateException {
	logger.warn("------User name before validating---------" +userName);

	if(userName==null || userName.isEmpty()){
		throw new CertificateException("User Name from the certificate is empty");
	}


	char[] tempArrays=userName.toCharArray();
	StringBuffer  specialCharSkippedVersion =new StringBuffer();
	for(char  c:tempArrays){
		String str=String.valueOf(c);
		if(str.matches(REGEX_USERNAME)){
			specialCharSkippedVersion.append(str);
		}
	}
	
	if(specialCharSkippedVersion.length()>80){
		return specialCharSkippedVersion.substring(0,80);
	}

	return specialCharSkippedVersion.toString();

}


private  String getUPNStringFromSequenceNew(ASN1Sequence seq) throws IOException {
	if (seq != null) {
		Enumeration<?> secEnum = seq.getObjects();
		Object obj=null;

		while(secEnum.hasMoreElements()){
			obj=secEnum.nextElement();


			if( obj.toString().equalsIgnoreCase(UPN_OBJECTID)){
				if(secEnum.hasMoreElements()){
					ASN1Encodable asn1Obj=(ASN1Encodable)secEnum.nextElement();
					DEREncodable derEnc = ((DERTaggedObject) asn1Obj).getObjectParser(1, true);
					DEREncodable derEnc2 = ((DERTaggedObject) derEnc).getObjectParser(0, true);
					return ((DERUTF8String) derEnc2.getDERObject()).toString();


				}
			}
		}
		if (logger.isDebugEnabled()){
			logger.debug(" Couldnt find UPN_OBJECTID in the certificate");
		}

	}
	return null;
	}
private  String getAltnameSequence(List<?> listitem)throws IOException {
	Integer key = (Integer) listitem.get(0);//other name alias principle name index is 0. this is universal format. [0,object]
	if (key.intValue() == 0) {
		//converting to asn1 stream
		ASN1InputStream bIn = new ASN1InputStream(new ByteArrayInputStream((byte [])listitem.get(1)));
	    DERObject obj = bIn.readObject();
	    ASN1Sequence seq=(ASN1Sequence)obj;
	    bIn.close();

	 return    getUPNStringFromSequenceNew(seq) ;


	}

	return null;
}



	/**
	 * Will decode back from base 64. This will remove the begin and end
	 * statement from the pem string and decode back
	 *
	 * @param certicatePEM
	 * @return
	 * @throws CertificateException
	 * @throws GeneralFaultException
	 */
	public X509Certificate parseCertificate(String certicatePEM)
			throws CertificateException, GeneralFaultException {
		if (certicatePEM == null) {
			generateFaultException("No certificate found");
		}
		byte[] decoded = Base64.decodeBase64(certicatePEM
				.replaceAll(X509_BEGIN, "").replaceAll(X509_END, "")
				.replaceAll("\\s", "").getBytes());
		return (X509Certificate) CertificateFactory.getInstance("X.509")
				.generateCertificate(new ByteArrayInputStream(decoded));
	}

	private void checkLicenseForAllowUserAPIs(MessageContext context)
			throws NotLicensedFaultException {
		TransportHeaders headers = (TransportHeaders) context
				.getProperty(MessageContext.TRANSPORT_HEADERS);
		String userAgent = headers.get("user-agent");
		if (userAgent == null) {
			userAgent = "";
		}

		if (!userAgent.matches("VidyoDesktop")) {
			SystemLicenseFeature licensed = licensingService
					.getSystemLicenseFeature("AllowUserAPIs");
			if (licensed == null
					|| licensed.getLicensedValue().equalsIgnoreCase("false")) {
				NotLicensedFault fault = new NotLicensedFault();
				fault.setErrorMessage("Operation is not licensed");
				NotLicensedFaultException exception = new NotLicensedFaultException(
						fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}
	}

}
