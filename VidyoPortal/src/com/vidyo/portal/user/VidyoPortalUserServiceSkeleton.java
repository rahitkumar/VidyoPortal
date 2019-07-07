/**
 * VidyoPortalUserServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.portal.user;

import com.vidyo.bo.*;
import com.vidyo.bo.Entity;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.bo.room.RoomTypes;
import com.vidyo.bo.search.simple.RoomIdSearchResult;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.db.security.token.VidyoPersistentTokenRepository;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.*;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.conference.request.JoinConferenceRequest;
import com.vidyo.service.exceptions.*;
import com.vidyo.service.member.response.MemberManagementResponse;
import com.vidyo.utils.UserAgentUtils;
import com.vidyo.utils.VendorUtils;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.TransportHeaders;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.lang.Exception;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VidyoPortalUserServiceSkeleton java skeleton for the axisService
 */
public class VidyoPortalUserServiceSkeleton implements VidyoPortalUserServiceSkeletonInterface {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(VidyoPortalUserServiceSkeleton.class.getName());

	private IUserService user;
	private IMemberService member;
	private IRoomService room;
	private IConferenceService conference;
	private IServiceService service;
	private LicensingService license;
	private ISystemService system;
	private ITenantService tenantService;
	private IFederationConferenceService federation;
	private ReloadableResourceBundleMessageSource ms;
	
	private ConferenceAppService conferenceAppService;
	
	private VidyoPersistentTokenRepository vidyoPersistentTokenRepository;		

	/**
	 * @param vidyoPersistentTokenRepository the vidyoPersistentTokenRepository to set
	 */
	public void setVidyoPersistentTokenRepository(
			VidyoPersistentTokenRepository vidyoPersistentTokenRepository) {
		this.vidyoPersistentTokenRepository = vidyoPersistentTokenRepository;
	}

	/**
	 * @param conferenceAppService the conferenceAppService to set
	 */
	public void setConferenceAppService(ConferenceAppService conferenceAppService) {
		this.conferenceAppService = conferenceAppService;
	}

	public void setUser(IUserService user) {
		this.user = user;
	}

	public void setMember(IMemberService member) {
		this.member = member;
	}

	public void setRoom(IRoomService room) {
		this.room = room;
	}

	public void setConference(IConferenceService conference) {
		this.conference = conference;
	}

	public void setService(IServiceService service) {
		this.service = service;
	}

	public void setLicense(LicensingService license) {
		this.license = license;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public void setFederation(IFederationConferenceService federation) {
		this.federation = federation;
	}

	public void setMs(ReloadableResourceBundleMessageSource ms) {
		this.ms = ms;
	}

	private void checkLicenseForAllowUserAPIs(MessageContext context)
			throws NotLicensedFaultException {
		TransportHeaders headers = (TransportHeaders) context.getProperty(MessageContext.TRANSPORT_HEADERS);
		String user_agent = (String) headers.get("user-agent");
		if (user_agent == null) {
			user_agent = "";
		}

		if (!UserAgentUtils.isVidyoEndpoint(user_agent)) {
			SystemLicenseFeature licensed = this.license.getSystemLicenseFeature("AllowUserAPIs");
			if (licensed == null || licensed.getLicensedValue().equalsIgnoreCase("false")) {
				NotLicensedFault fault = new NotLicensedFault();
				NotLicensedFaultException exception = new NotLicensedFaultException("Operation is not licensed");
				exception.setFaultMessage(fault);
				throw exception;
			}
		}
	}

	private Entity_type0 getWSentityFromBOentity(User user, Entity entity) {
		Entity_type0 ws_entity = new Entity_type0();

		EntityID entityID = new EntityID();
		entityID.setEntityID(String.valueOf(entity.getRoomID()));
		ws_entity.setEntityID(entityID);

		if (entity.getRoomType() != null) {
			if (entity.getRoomType().equalsIgnoreCase("Personal")) {
				ws_entity.setEntityType(EntityType_type0.Member);
				ws_entity.setCanCallDirect(true);
				ws_entity.setCanJoinMeeting(entity.getRoomEnabled() == 1);
			} else if (entity.getRoomType().equalsIgnoreCase("Public")) {
				ws_entity.setEntityType(EntityType_type0.Room);
				ws_entity.setCanCallDirect(false);
				ws_entity.setCanJoinMeeting(entity.getRoomEnabled() == 1);
			} else if (entity.getRoomType().equalsIgnoreCase("Legacy")) {
				ws_entity.setEntityType(EntityType_type0.Legacy);
				ws_entity.setCanCallDirect(true);
				ws_entity.setCanJoinMeeting(false);
			}
		} else {
			ws_entity.setEntityType(EntityType_type0.Member);
			ws_entity.setCanCallDirect(false);
			ws_entity.setCanJoinMeeting(false);
		}

		if (entity.getMemberID() != 0) {
			Member member = this.member.getMember(entity.getMemberID());
			EntityID ownerID = new EntityID();
			ownerID.setEntityID(String.valueOf(member.getRoomID()));
			ws_entity.setOwnerID(ownerID);

			ws_entity.setEmailAddress(member.getEmailAddress());
			ws_entity.setTenant(member.getTenantName());
			int langId = entity.getLangID();
			
			

			if (langId == 1) {
				ws_entity.setLanguage(Language_type0.en);
			} else if (langId == 2) {
				ws_entity.setLanguage(Language_type0.fr);
			} else if (langId == 3) {
				ws_entity.setLanguage(Language_type0.ja);
			} else if (langId == 4) {
				ws_entity.setLanguage(Language_type0.zh_CN);
			} else if (langId == 5) {
				ws_entity.setLanguage(Language_type0.es);
			} else if (langId == 6) {
				ws_entity.setLanguage(Language_type0.it);
			} else if (langId == 7) {
				ws_entity.setLanguage(Language_type0.de);
			} else if (langId == 8) {
				ws_entity.setLanguage(Language_type0.ko);
			} else if (langId == 9) {
				ws_entity.setLanguage(Language_type0.pt);
			} else if (langId == 10) {
				ws_entity.setLanguage(Language_type0.Factory.fromValue(this.system.getSystemLang(user.getTenantID()).getLangCode()));
			} else if (langId == 11) {
				ws_entity.setLanguage(Language_type0.fi);
			} else if (langId == 12) {
				ws_entity.setLanguage(Language_type0.pl);
			} else if (langId == 13) {
				ws_entity.setLanguage(Language_type0.zh_TW);
			} else if (langId == 14) {
				ws_entity.setLanguage(Language_type0.th);
			} else if (langId == 15) {
				ws_entity.setLanguage(Language_type0.ru);
			}

			if (entity.getModeID() == 1) {
				ws_entity.setMemberMode(MemberMode_type0.Available);
			} else if (entity.getModeID() == 2) {
				ws_entity.setMemberMode(MemberMode_type0.Away);
			} else if (entity.getModeID() == 3) {
				ws_entity.setMemberMode(MemberMode_type0.DoNotDisturb);
			}
		} else {
			EntityID ownerID = new EntityID();
			ownerID.setEntityID(String.valueOf(0));
			ws_entity.setOwnerID(ownerID);
			ws_entity.setLanguage(Language_type0.Factory.fromValue(this.system.getSystemLang(entity.getTenantID()).getLangCode()));
			ws_entity.setMemberMode(MemberMode_type0.Available);
		}

        ws_entity.setDisplayName(entity.getName());
		ws_entity.setExtension(entity.getExt());
		ws_entity.setDescription(entity.getDescription());

		if (entity.getMemberStatus() == 0) {
			ws_entity.setMemberStatus(MemberStatus_type0.Offline);
		} else if (entity.getMemberStatus() == 1) {
			ws_entity.setMemberStatus(MemberStatus_type0.Online);
		} else if (entity.getMemberStatus() == 2) {
			ws_entity.setMemberStatus(MemberStatus_type0.Busy);
		} else if (entity.getMemberStatus() == 3) {
			ws_entity.setMemberStatus(MemberStatus_type0.Ringing);
		} else if (entity.getMemberStatus() == 4) {
			ws_entity.setMemberStatus(MemberStatus_type0.RingAccepted);
		} else if (entity.getMemberStatus() == 5) {
			ws_entity.setMemberStatus(MemberStatus_type0.RingRejected);
		} else if (entity.getMemberStatus() == 6) {
			ws_entity.setMemberStatus(MemberStatus_type0.RingNoAnswer);
		} else if (entity.getMemberStatus() == 7) {
			ws_entity.setMemberStatus(MemberStatus_type0.Alerting);
		} else if (entity.getMemberStatus() == 8) {
			ws_entity.setMemberStatus(MemberStatus_type0.AlertCancelled);
		} else if (entity.getMemberStatus() == 9) {
			ws_entity.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
		} else if (entity.getMemberStatus() == 12) {
			ws_entity.setMemberStatus(MemberStatus_type0.Busy); // WaitForConfirm
		}

		RoomMode_type0 roomMode = new RoomMode_type0();
		roomMode.setIsLocked(entity.getRoomLocked() == 1);
		roomMode.setHasPin(entity.getRoomPinned() == 1);
		if (entity.getRoomPIN() != null && !entity.getRoomPIN().equalsIgnoreCase("") && user.getMemberID() == entity.getMemberID()) {
			roomMode.setRoomPIN(entity.getRoomPIN());
		} else {
			roomMode.setRoomPIN("****");
		}
		if (entity.getRoomKey() != null && !entity.getRoomKey().isEmpty() && user.getMemberID() == entity.getMemberID()) {
			StringBuffer path = new StringBuffer();
			Tenant tenant = this.tenantService.getTenant(entity.getTenantID());
			String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
			/*path.append(transportName).append("://").append(tenant.getTenantURL());
			path.append("/flex.html?roomdirect.html&key=");
			path.append(entity.getRoomKey());*/

			String joinURL = this.room.getRoomURL(system, transportName, 
					tenant.getTenantURL(), entity.getRoomKey());

			path.append(joinURL);
			if (tenantService.isTenantNotAllowingGuests()) {
				path.append("&noguest");
			}

			roomMode.setRoomURL(path.toString());
		}
		ws_entity.setRoomMode(roomMode);

		if (entity.getRoomStatus() == 0) {
			ws_entity.setRoomStatus(RoomStatus_type0.Empty);
		} else if (entity.getRoomStatus() == 1) {
			ws_entity.setRoomStatus(RoomStatus_type0.Occupied);
		} else if (entity.getRoomStatus() == 2) {
			ws_entity.setRoomStatus(RoomStatus_type0.Full);
		}

		ws_entity.setVideo(entity.getVideo() == 1);
		ws_entity.setAudio(entity.getAudio() == 1);
		// ToDo - share?
		ws_entity.setAppshare(true);

		if (entity.getRoomOwner() == 1) {
			ws_entity.setCanControl(true);
		} else {
			ws_entity.setCanControl(false);
		}

		return ws_entity;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param logInRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.LogInResponse logIn(
			com.vidyo.portal.user.LogInRequest logInRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		LogInResponse resp = new LogInResponse();
		User user = this.user.getLoginUser();
		// Below condition takes for restricting login for mobile clients, if
		// the access is disabled at Tenant Level
		if (logInRequest.getClientType() != null && (logInRequest.getClientType().getValue().equalsIgnoreCase("I") || logInRequest.getClientType().getValue().equalsIgnoreCase("A"))) {
			// Check to validate if mobile login is allowed for this tenant
			Tenant tenant = tenantService.getTenant(user.getTenantID());

			if (tenant == null) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid Tenant ID: " + user.getTenantID());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (tenant.getMobileLogin() == 0) {
				// If Mobile Access is restricted, don't allow login
				GeneralFault fault = new GeneralFault();
				GeneralFaultException exception = new GeneralFaultException("Mobile Access Restricted");
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		PortalAccessKeys portalAccessKeys = this.user.generatePAKforMember(user.getMemberID());
		//String PAK = this.user.getPAKforMember(user.getMemberID());

		if(portalAccessKeys != null) {
			resp.setPak(portalAccessKeys.getPak());
			//TODO - uncomment the below line to include pak2
			resp.setPak2(portalAccessKeys.getPak2());
		}

		// set VM address
		try {
			String vm = this.conference.getVMConnectAddress();

			if (vm != null && !vm.trim().equals("")) {
				resp.setVmaddress(vm);
			}
		} catch (NoVidyoManagerException ignored) {
		}

		// set proxies addresses
		String proxies = this.service.getProxyCSVList(this.user.getLoginUser()
				.getMemberID());
		if (proxies != null && !proxies.trim().equals("")) {
			resp.setProxyaddress(proxies);
		}

		// set Location Tag
		String locTag = this.service.getLocationTagForMember(this.user.getLoginUser().getMemberID());
		if (locTag != null && !locTag.equalsIgnoreCase("")) {
			resp.setLoctag(locTag);
		} else {
			resp.setLoctag("Default");
		}

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param linkEndpointRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.LinkEndpointResponse linkEndpoint(
			com.vidyo.portal.user.LinkEndpointRequest linkEndpointRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		LinkEndpointResponse resp = new LinkEndpointResponse();

		String endpointGUID = linkEndpointRequest.getEID();
		if (endpointGUID.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad EID = " + endpointGUID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		User user = this.user.getLoginUser();

		if (linkEndpointRequest.getClientType() != null && (linkEndpointRequest.getClientType().equalsIgnoreCase("I") || linkEndpointRequest.getClientType().equalsIgnoreCase("A"))) {
			// Check to validate if mobile login is allowed for this tenant
			Tenant tenant = tenantService.getTenant(user.getTenantID());

			if (tenant == null) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid Tenant ID: " + user.getTenantID());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (tenant.getMobileLogin() == 0) {
				GeneralFault fault = new GeneralFault();
				GeneralFaultException exception = new GeneralFaultException("Mobile Access Restricted for this User " + user.getUsername());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		com.vidyo.bo.Entity entity = this.member.getContact(user.getMemberID());

		Entity_type0 ws_entity = getWSentityFromBOentity(user, entity);

		this.user.linkEndpointToUser(endpointGUID, linkEndpointRequest.getClientType(), false);

		String vrIP = linkEndpointRequest.getVrIP();
		if (vrIP != null && !vrIP.equalsIgnoreCase("")) {
			this.user.updateEndpointIPaddress(endpointGUID, vrIP);
		}

		resp.setEntity(ws_entity);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param logOutRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.LogOutResponse logOut(
			com.vidyo.portal.user.LogOutRequest logOutRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		LogOutResponse resp = new LogOutResponse();
		User user = this.user.getLoginUser();

		Member member = this.member.getMember(user.getMemberID());

		// Unbind user from Endpoint
		this.user.unlinkEndpointFromUser(user.getMemberID(), member.getEndpointGUID(), UserUnbindCode.LOGOUT_REQUEST);

		if (member.getDefaultEndpointGUID() != null) {
			if (!member.getDefaultEndpointGUID().equalsIgnoreCase("")) {
				this.user.linkEndpointToUser(member.getDefaultEndpointGUID(), null, false);
			}
		}
		
		// Delete the auth token for the EID if the authentication is using token
		Object authByToken = RequestContextHolder.getRequestAttributes().getAttribute("AUTH_USING_TOKEN",
				RequestAttributes.SCOPE_REQUEST);
		
		String endpointId = null;
		if (authByToken != null && authByToken instanceof Boolean && Boolean.valueOf(authByToken.toString())) {
			Object eid = RequestContextHolder.getRequestAttributes().getAttribute("ENDPOINT_ID",
						RequestAttributes.SCOPE_REQUEST);
			// get the eid if authentication is by token
			endpointId = (eid != null) ? (String) eid : null;
			vidyoPersistentTokenRepository.removeUserTokens(user.getUsername(), TenantContext.getTenantId(), endpointId);
		}			

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param searchRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.SearchResponse search(
			com.vidyo.portal.user.SearchRequest searchRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		SearchResponse resp = new SearchResponse();
		User user = this.user.getLoginUser();

		Filter_type0 input_param = searchRequest.getFilter();
		EntityFilter filter = null;
		// map ws_filter to entity filter
		if (input_param != null) {
			filter = new EntityFilter();
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			if (input_param.getLimit() >= 0) {
				filter.setLimit(input_param.getLimit());
			}
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
					filter.setSortBy("roomID");
				} else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSortBy("name");
				} else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSortBy("ext");
				} else {
					filter.setSortBy("");
				}
			}
			if (input_param.getEntityType() != null) {
				// mapping between WS and SQL
				if (input_param.getEntityType().getValue().equalsIgnoreCase("Member")) {
					filter.setEntityType("Personal");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Room")) {
					filter.setEntityType("Public");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Legacy")) {
					filter.setEntityType("Legacy");
				}
			} else {
				filter.setEntityType("All");
			}
			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
			if (filter.getQuery() != null && filter.getQuery().equalsIgnoreCase("*")) {
				if (filter.getEntityType().equalsIgnoreCase("Public") || filter.getEntityType().equalsIgnoreCase("Legacy")) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Cannot use * with specified Entity Type - " + filter.getEntityType());
					exception.setFaultMessage(fault);
					throw exception;
				}
			}
		}

		List<RoomIdSearchResult> roomIdSearchResults = room.searchRoomIds(filter, user);
		
		if(!roomIdSearchResults.isEmpty() && roomIdSearchResults.get(0).getTotalCount() > 0) {
			resp.setTotal(roomIdSearchResults.get(0).getTotalCount());
			List<Integer> roomIds = new ArrayList<Integer>();
			for(RoomIdSearchResult roomIdSearchResult : roomIdSearchResults) {
				roomIds.add(roomIdSearchResult.getRoomId());
			}
			List<com.vidyo.bo.Entity> entities = room.getEntities(roomIds, user, filter);
			List<Integer> memberIds = new ArrayList<Integer>(entities.size());
			for(com.vidyo.bo.Entity entity : entities) {
				// If the entity is public room, get the personal room id of the member
				if(entity.getRoomTypeID() == RoomTypes.PUBLIC.getId()) {
					memberIds.add(entity.getMemberID());
				}
			}
			Map<Integer, Integer> memberIdPersonalRoomIdMap = null;
			if(!memberIds.isEmpty()) {
				memberIdPersonalRoomIdMap = member.getPersonalRoomIds(memberIds);
			}				
			// map list of entities to ws_entities
			for (com.vidyo.bo.Entity entity : entities) {
				Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);
				resp.addEntity(ws_entity);
			}
		} else {
			resp.setTotal(0);
		}

		return resp;
	}
	
	private Entity_type0 getWSEntityFromBOEntity(User user,
			com.vidyo.bo.Entity entity, Map<Integer, Integer> memberIdPersonalRoomIdMap) {
		Entity_type0 ws_entity = new Entity_type0();

		EntityID entityID = new EntityID();
		entityID.setEntityID(String.valueOf(entity.getRoomID()));
		ws_entity.setEntityID(entityID);

		if (entity.getRoomType() != null) {
			if (entity.getRoomType().equalsIgnoreCase("Personal")) {
				ws_entity.setEntityType(EntityType_type0.Member);
				ws_entity.setCanCallDirect(true);
				ws_entity.setCanJoinMeeting(entity.getRoomEnabled() == 1);
				EntityID ownerID = new EntityID();
				ownerID.setEntityID(String.valueOf(entity.getRoomID()));
				ws_entity.setOwnerID(ownerID);
			} else if (entity.getRoomType().equalsIgnoreCase("Public")) {
				ws_entity.setEntityType(EntityType_type0.Room);
				ws_entity.setCanCallDirect(false);
				ws_entity.setCanJoinMeeting(entity.getRoomEnabled() == 1);
				EntityID ownerID = new EntityID();
				if(memberIdPersonalRoomIdMap != null && memberIdPersonalRoomIdMap.get(entity.getMemberID()) != null) {
					ownerID.setEntityID(String.valueOf(memberIdPersonalRoomIdMap.get(entity.getMemberID())));
				} else {
					ownerID.setEntityID(String.valueOf(entity.getRoomID()));
				}
				ws_entity.setOwnerID(ownerID);				
			} else if (entity.getRoomType().equalsIgnoreCase("Legacy")) {
				ws_entity.setEntityType(EntityType_type0.Legacy);
				ws_entity.setCanCallDirect(true);
				ws_entity.setCanJoinMeeting(false);
				EntityID ownerID = new EntityID();
				ownerID.setEntityID(String.valueOf(entity.getRoomID()));
				ws_entity.setOwnerID(ownerID);
			} else if(entity.getRoomType().equalsIgnoreCase("Scheduled")) {
				ws_entity.setEntityType(EntityType_type0.Room);
				ws_entity.setCanCallDirect(false);
				ws_entity.setCanJoinMeeting(true);
				EntityID ownerID = new EntityID();
				if(memberIdPersonalRoomIdMap != null && memberIdPersonalRoomIdMap.get(entity.getMemberID()) != null) {
					ownerID.setEntityID(String.valueOf(memberIdPersonalRoomIdMap.get(entity.getMemberID())));
				} else {
					ownerID.setEntityID(String.valueOf(entity.getRoomID()));
				}
				ws_entity.setOwnerID(ownerID);				
			}
		} else {
			ws_entity.setEntityType(EntityType_type0.Member);
			ws_entity.setCanCallDirect(false);
			ws_entity.setCanJoinMeeting(false);
		}


		if (entity.getMemberID() != 0) {
			//Member member = memberService.getMember(entity.getMemberID()); // Perf Issue - unnecessary call just for email address
			
			ws_entity.setEmailAddress(entity.getEmailAddress());
			ws_entity.setTenant(entity.getTenantName());
			
			switch (entity.getLangID()) {
			case 1:
				ws_entity.setLanguage(Language_type0.en);
				break;
			case 2:
				ws_entity.setLanguage(Language_type0.fr);
				break;
			case 3:
				ws_entity.setLanguage(Language_type0.ja);
				break;
			case 4:
				ws_entity.setLanguage(Language_type0.zh_CN);
				break;
			case 5:
				ws_entity.setLanguage(Language_type0.es);
				break;
			case 6:
				ws_entity.setLanguage(Language_type0.it);
				break;
			case 7:
				ws_entity.setLanguage(Language_type0.de);
				break;
			case 8:
				ws_entity.setLanguage(Language_type0.ko);
				break;
			case 9:
				ws_entity.setLanguage(Language_type0.pt);
				break;
			case 10:
				ws_entity.setLanguage(Language_type0.Factory
						.fromValue(system.getSystemLang(
								entity.getTenantID()).getLangCode()));
				break;
			case 11:
				ws_entity.setLanguage(Language_type0.fi);
				break;
			case 12:
				ws_entity.setLanguage(Language_type0.pl);
				break;
			case 13:
				ws_entity.setLanguage(Language_type0.zh_TW);
				break;
			case 14:
				ws_entity.setLanguage(Language_type0.th);
				break;
			case 15:
				ws_entity.setLanguage(Language_type0.ru);
				break;
			default:
				ws_entity.setLanguage(Language_type0.Factory
						.fromValue(system.getSystemLang(
								entity.getTenantID()).getLangCode()));
				break;
			}

			if (entity.getModeID() == 1) {
				ws_entity.setMemberMode(MemberMode_type0.Available);
			} else if (entity.getModeID() == 2) {
				ws_entity.setMemberMode(MemberMode_type0.Away);
			} else if (entity.getModeID() == 3) {
				ws_entity.setMemberMode(MemberMode_type0.DoNotDisturb);
			}
		} else {
			EntityID ownerID = new EntityID();
			ownerID.setEntityID(String.valueOf(0));
			ws_entity.setOwnerID(ownerID);
			ws_entity
					.setLanguage(Language_type0.Factory.fromValue(system
							.getSystemLang(entity.getTenantID()).getLangCode()));
			ws_entity.setMemberMode(MemberMode_type0.Available);
		}

		ws_entity.setDisplayName(entity.getName());
		ws_entity.setExtension(entity.getExt());
		ws_entity.setDescription(entity
                .getDescription());

		if (entity.getMemberStatus() == 0) {
			ws_entity.setMemberStatus(MemberStatus_type0.Offline);
		} else if (entity.getMemberStatus() == 1) {
			ws_entity.setMemberStatus(MemberStatus_type0.Online);
		} else if (entity.getMemberStatus() == 2) {
			ws_entity.setMemberStatus(MemberStatus_type0.Busy);
		} else if (entity.getMemberStatus() == 3) {
			ws_entity.setMemberStatus(MemberStatus_type0.Ringing);
		} else if (entity.getMemberStatus() == 4) {
			ws_entity.setMemberStatus(MemberStatus_type0.RingAccepted);
		} else if (entity.getMemberStatus() == 5) {
			ws_entity.setMemberStatus(MemberStatus_type0.RingRejected);
		} else if (entity.getMemberStatus() == 6) {
			ws_entity.setMemberStatus(MemberStatus_type0.RingNoAnswer);
		} else if (entity.getMemberStatus() == 7) {
			ws_entity.setMemberStatus(MemberStatus_type0.Alerting);
		} else if (entity.getMemberStatus() == 8) {
			ws_entity.setMemberStatus(MemberStatus_type0.AlertCancelled);
		} else if (entity.getMemberStatus() == 9) {
			ws_entity.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
		} else if (entity.getMemberStatus() == 12) {
			ws_entity.setMemberStatus(MemberStatus_type0.Busy); // WaitForConfirm
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

			String joinURL = room.getRoomURL(system, transportName, 
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
		String roomPIN = entity.getRoomPIN();
		if (roomPIN != null && !roomPIN.equalsIgnoreCase("") && user.getMemberID() == entity.getMemberID()) {
			roomMode.setRoomPIN(roomPIN); // roomPIN
		} else {
			// Mask room PIN if the user is not the owner
			roomMode.setRoomPIN("****");
		}

		ws_entity.setRoomMode(roomMode);

		if (entity.getRoomStatus() == 0) {
			ws_entity.setRoomStatus(RoomStatus_type0.Empty);
		} else if (entity.getRoomStatus() == 1) {
			ws_entity.setRoomStatus(RoomStatus_type0.Occupied);
		} else if (entity.getRoomStatus() == 2) {
			ws_entity.setRoomStatus(RoomStatus_type0.Full);
		}

		ws_entity.setVideo(entity.getVideo() == 1);
		ws_entity.setAudio(entity.getAudio() == 1);
		// ToDo - share?
		ws_entity.setAppshare(true);

		if (entity.getRoomOwner() == 1) {
			ws_entity.setCanControl(true);
		} else {
			ws_entity.setCanControl(false);
		}

		return ws_entity;
	}	

	/**
	 * Auto generated method signature
	 * 
	 * @param searchMyContactsRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.SearchMyContactsResponse searchMyContacts(
			com.vidyo.portal.user.SearchMyContactsRequest searchMyContactsRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		SearchMyContactsResponse resp = new SearchMyContactsResponse();
		User user = this.user.getLoginUser();

		Filter_type0 input_param = searchMyContactsRequest.getFilter();
		EntityFilter filter = null;
		// map ws_filter to entity filter
		if (input_param != null) {
			filter = new EntityFilter();
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			if (input_param.getLimit() >= 0) {
				filter.setLimit(input_param.getLimit());
			}
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
					filter.setSortBy("roomID");
				} else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSortBy("name");
				} else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSortBy("ext");
				} else {
					filter.setSortBy("");
				}
			}
			if (input_param.getEntityType() != null) {
				// mapping between WS and SQL
				if (input_param.getEntityType().getValue().equalsIgnoreCase("Member")) {
					filter.setEntityType("Personal");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Room")) {
					filter.setEntityType("Public");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Legacy")) {
					filter.setEntityType("Legacy");
				}
			} else {
				filter.setEntityType("All");
			}
			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
			if (filter.getQuery() != null && filter.getQuery().equalsIgnoreCase("*")) {
				if (filter.getEntityType().equalsIgnoreCase("Public") || filter.getEntityType().equalsIgnoreCase("Legacy")) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Cannot use * with specified Entity Type - " + filter.getEntityType());
					exception.setFaultMessage(fault);
					throw exception;
				}
			}
		}

		List<Entity> list = this.member.getContacts(user.getMemberID(), filter);

		// map list of entities to ws_entities
		for (com.vidyo.bo.Entity entity : list) {
			Entity_type0 ws_entity = getWSentityFromBOentity(user, entity);
			resp.addEntity(ws_entity);
		}

		// total records in DB
		int total = this.member.getCountContacts(user.getMemberID(), filter);
		resp.setTotal(total);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param addToMyContactsRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */

	public com.vidyo.portal.user.AddToMyContactsResponse addToMyContacts(
			com.vidyo.portal.user.AddToMyContactsRequest addToMyContactsRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		AddToMyContactsResponse resp = new AddToMyContactsResponse();
		User user = this.user.getLoginUser();

		int memberID = user.getMemberID();
		int roomID = Integer.valueOf(addToMyContactsRequest.getEntityID().getEntityID());

		if (this.member.isInSpeedDialEntry(memberID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Entity already in list of Contacts");
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			this.room.getRoom(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Illegal entity ID: " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		this.member.addSpeedDialEntry(memberID, roomID);

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param removeFromMyContactsRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.RemoveFromMyContactsResponse removeFromMyContacts(
			com.vidyo.portal.user.RemoveFromMyContactsRequest removeFromMyContactsRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		RemoveFromMyContactsResponse resp = new RemoveFromMyContactsResponse();
		User user = this.user.getLoginUser();

		int memberID = user.getMemberID();
		int roomID = Integer.valueOf(removeFromMyContactsRequest.getEntityID().getEntityID());

		if (!this.member.isInSpeedDialEntry(memberID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Entity not found in list of Contacts");
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			this.room.getRoom(roomID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Illegal entity ID: " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		this.member.removeSpeedDialEntry(memberID, roomID);

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param myEndpointStatusRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.MyEndpointStatusResponse myEndpointStatus(
			com.vidyo.portal.user.MyEndpointStatusRequest myEndpointStatusRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		MyEndpointStatusResponse resp = new MyEndpointStatusResponse();
		User user = this.user.getLoginUser();

		Entity entity = this.member.getContact(user.getMemberID());

		if (entity.getMemberStatus() == 0) {
			resp.setMemberStatus(MemberStatus_type0.Offline);
		} else if (entity.getMemberStatus() == 1) {
			resp.setMemberStatus(MemberStatus_type0.Online);
		} else if (entity.getMemberStatus() == 2) {
			resp.setMemberStatus(MemberStatus_type0.Busy);
		} else if (entity.getMemberStatus() == 3) {
			resp.setMemberStatus(MemberStatus_type0.Ringing);
		} else if (entity.getMemberStatus() == 4) {
			resp.setMemberStatus(MemberStatus_type0.RingAccepted);
		} else if (entity.getMemberStatus() == 5) {
			resp.setMemberStatus(MemberStatus_type0.RingRejected);
		} else if (entity.getMemberStatus() == 6) {
			resp.setMemberStatus(MemberStatus_type0.RingNoAnswer);
		} else if (entity.getMemberStatus() == 7) {
			resp.setMemberStatus(MemberStatus_type0.Alerting);
		} else if (entity.getMemberStatus() == 8) {
			resp.setMemberStatus(MemberStatus_type0.AlertCancelled);
		} else if (entity.getMemberStatus() == 9) {
			resp.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
		} else if (entity.getMemberStatus() == 12) {
			resp.setMemberStatus(MemberStatus_type0.Busy); // WaitForConfirm
		}

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param createRoomPINRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.CreateRoomPINResponse createRoomPIN(
			com.vidyo.portal.user.CreateRoomPINRequest createRoomPINRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		CreateRoomPINResponse resp = new CreateRoomPINResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(createRoomPINRequest.getRoomID().getEntityID());
		String roomPIN = createRoomPINRequest.getPIN();
		if (!system.isPINLengthAcceptable(TenantContext.getTenantId(), roomPIN)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("PIN should be a " +
					system.getMinPINLengthForTenant(TenantContext.getTenantId()) +
					"-" + SystemServiceImpl.PIN_MAX + " digit number");
			exception.setFaultMessage(fault);
			throw exception;
		}		

		Room room = this.room.getRoom(roomID);

		if (roomID != 0 && room != null) {
			if (room.getMemberID() != user.getMemberID()) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid RoomID = " + roomID);
				exception.setFaultMessage(fault);
				throw exception;
			}
			room.setPinSetting("enter");
			room.setRoomPIN(roomPIN);
			this.room.updateRoom(roomID, room);
		} else {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param removeRoomPINRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.RemoveRoomPINResponse removeRoomPIN(
			com.vidyo.portal.user.RemoveRoomPINRequest removeRoomPINRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		RemoveRoomPINResponse resp = new RemoveRoomPINResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(removeRoomPINRequest.getRoomID().getEntityID());

		Room room = this.room.getRoom(roomID);

		if (roomID != 0 && room != null) {
			if (room.getMemberID() != user.getMemberID()) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of room for roomID = " + roomID);
				exception.setFaultMessage(fault);
				throw exception;
			}
			room.setPinSetting("remove");
			this.room.updateRoom(roomID, room);
		} else {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param createRoomURLRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.CreateRoomURLResponse createRoomURL(
			com.vidyo.portal.user.CreateRoomURLRequest createRoomURLRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		CreateRoomURLResponse resp = new CreateRoomURLResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(createRoomURLRequest.getRoomID().getEntityID());

		Room room = null;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Room not exist for roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (roomID != 0 && room != null) {
			if (room.getMemberID() != user.getMemberID()) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of room for roomID = " + roomID);
				exception.setFaultMessage(fault);
				throw exception;
			}
			this.room.generateRoomKey(room);
		} else {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param removeRoomURLRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.RemoveRoomURLResponse removeRoomURL(
			com.vidyo.portal.user.RemoveRoomURLRequest removeRoomURLRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		RemoveRoomURLResponse resp = new RemoveRoomURLResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(removeRoomURLRequest.getRoomID().getEntityID());

		Room room = this.room.getRoom(roomID);

		if (roomID != 0 && room != null) {
			if (room.getMemberID() != user.getMemberID()) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of room for roomID = " + roomID);
				exception.setFaultMessage(fault);
				throw exception;
			}
			this.room.removeRoomKey(room);
		} else {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param myAccountRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.MyAccountResponse myAccount(
			com.vidyo.portal.user.MyAccountRequest myAccountRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		MyAccountResponse resp = new MyAccountResponse();
		User user = this.user.getLoginUser();

		Entity entity = this.member.getContact(user.getMemberID());

		Entity_type0 ws_entity = getWSentityFromBOentity(user, entity);

		resp.setEntity(ws_entity);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param updateLanguageRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.UpdateLanguageResponse updateLanguage(
			com.vidyo.portal.user.UpdateLanguageRequest updateLanguageRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		UpdateLanguageResponse resp = new UpdateLanguageResponse();
		User user = this.user.getLoginUser();

		Language_type0 langCode = updateLanguageRequest.getLanguage();
		Member member = this.member.getMember(user.getMemberID());

		if (langCode.getValue().equalsIgnoreCase(Language_type0._en)) {
			member.setLangID(1);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._fr)) {
			member.setLangID(2);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._ja)) {
			member.setLangID(3);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._zh_CN)) {
			member.setLangID(4);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._es)) {
			member.setLangID(5);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._it)) {
			member.setLangID(6);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._de)) {
			member.setLangID(7);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._ko)) {
			member.setLangID(8);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._pt)) {
			member.setLangID(9);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._fi)) {
			member.setLangID(11);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._pl)) {
			member.setLangID(12);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._zh_TW)) {
			member.setLangID(13);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._th)) {
			member.setLangID(14);
		} else if (langCode.getValue().equalsIgnoreCase(Language_type0._ru)) {
			member.setLangID(15);
		}

		this.member.updateMemberLanguage(member);

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param updatePasswordRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 */
	public com.vidyo.portal.user.UpdatePasswordResponse updatePassword(
			com.vidyo.portal.user.UpdatePasswordRequest updatePasswordRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		UpdatePasswordResponse resp = new UpdatePasswordResponse();
		User user = this.user.getLoginUser();

		String password = updatePasswordRequest.getPassword();
		Member member = this.member.getMember(user.getMemberID());
        member.setPassword(password);

        int retVal = this.member.updateMemberPassword(member);
        if(retVal== MemberManagementResponse.PASSWORD_DOES_NOT_MEET_REQUIREMENTS){
            GeneralFault fault = new GeneralFault();
            GeneralFaultException exception = new GeneralFaultException("Password does not meet requirements");
            exception.setFaultMessage(fault);
            throw exception;
        }else if (retVal==MemberManagementResponse.PASSWORD_OF_IMPORTED_USER_CAN_NOT_BE_UPDATED){
            GeneralFault fault = new GeneralFault();
            GeneralFaultException exception = new GeneralFaultException("The password of imported user cannot be updated");
            exception.setFaultMessage(fault);
            throw exception;
        }else if (retVal==MemberManagementResponse.PASSWORD_ENCODING_EXCEPTION){
            GeneralFault fault = new GeneralFault();
            GeneralFaultException exception = new GeneralFaultException("Could not update member Password");
            exception.setFaultMessage(fault);
            throw exception;
        }else if (retVal==0){
            GeneralFault fault = new GeneralFault();
            GeneralFaultException exception = new GeneralFaultException("Could not update member Password");
            exception.setFaultMessage(fault);
            throw exception;
        }

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param joinConferenceRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws ConferenceLockedFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 * @throws WrongPinFaultException
	 *             :
	 */
	public com.vidyo.portal.user.JoinConferenceResponse joinConference(
			com.vidyo.portal.user.JoinConferenceRequest joinConferenceRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, ConferenceLockedFaultException,
			SeatLicenseExpiredFaultException, WrongPinFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		JoinConferenceResponse resp = new JoinConferenceResponse();
		User user = this.user.getLoginUser();

		int roomIdToJoin = Integer.valueOf(joinConferenceRequest.getConferenceID().getEntityID());
		String pin = joinConferenceRequest.getPin();

		JoinConferenceRequest conferenceRequest = new JoinConferenceRequest();
		conferenceRequest.setJoiningMemberId(user.getMemberID());
		conferenceRequest.setRoomId(roomIdToJoin);
		conferenceRequest.setPin(pin);
		com.vidyo.service.conference.response.JoinConferenceResponse joinConferenceResponse = conferenceAppService
				.joinConferenceInRoom(conferenceRequest);
		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.WRONG_PIN) {
			WrongPinFault fault = new WrongPinFault();
			WrongPinFaultException exception = new WrongPinFaultException("Wrong PIN");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.ROOM_LOCKED) {
			ConferenceLockedFault fault = new ConferenceLockedFault();
			ConferenceLockedFaultException exception = new ConferenceLockedFaultException("Room is locked");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() != com.vidyo.service.conference.response.JoinConferenceResponse.SUCCESS) {
			GeneralFault fault = new GeneralFault();
			GeneralFaultException exception = new GeneralFaultException(joinConferenceResponse.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param joinIPCConferenceRequest:
	 * @return joinIPCConferenceResponse:
	 * @throws NotLicensedFaultException:
	 * @throws InvalidArgumentFaultException:
	 * @throws GeneralFaultException:
	 * @throws ConferenceLockedFaultException:
	 * @throws SeatLicenseExpiredFaultException:
	 * @throws WrongPinFaultException:
	 */
	public com.vidyo.portal.user.JoinIPCConferenceResponse joinIPCConference
	(
			com.vidyo.portal.user.JoinIPCConferenceRequest joinIPCConferenceRequest
	)
			throws NotLicensedFaultException,InvalidArgumentFaultException,
			GeneralFaultException,ConferenceLockedFaultException,
			SeatLicenseExpiredFaultException,WrongPinFaultException
	{
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		JoinIPCConferenceResponse resp = new JoinIPCConferenceResponse();
		User user = this.user.getLoginUser();

		String remoteUser = joinIPCConferenceRequest.getLocalPart();
		if (remoteUser == null || remoteUser.equalsIgnoreCase("")) {
			InvalidArgumentFault invalidArgumentFault = new InvalidArgumentFault();
			InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException("local-part is invalid " + remoteUser);
			invalidArgumentFaultException.setFaultMessage(invalidArgumentFault);
			throw invalidArgumentFaultException;
		}

		String remoteHost = joinIPCConferenceRequest.getDomain();
		if (remoteHost == null || remoteHost.equalsIgnoreCase("")) {
			InvalidArgumentFault invalidArgumentFault = new InvalidArgumentFault();
			InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException("domain is invalid " + remoteHost);
			invalidArgumentFaultException.setFaultMessage(invalidArgumentFault);
			throw invalidArgumentFaultException;
		}

		String pin = joinIPCConferenceRequest.getPin();
		if (pin == null) {
			pin = "";
		}

		try {
			try {
				this.federation.joinRemoteConference(user.getMemberID(), remoteUser, remoteHost, pin, false); // try http
			} catch (RemoteException e) {
				this.federation.joinRemoteConference(user.getMemberID(), remoteUser, remoteHost, pin, true); // try https
			}
		} catch (RemoteException e) {
			GeneralFault generalFault = new GeneralFault();
			GeneralFaultException generalFaultException = new GeneralFaultException(e.getMessage());
			generalFaultException.setFaultMessage(generalFault);
			throw generalFaultException;
		} catch (OutOfPortsException e) {
			GeneralFault generalFault = new GeneralFault();
			GeneralFaultException generalFaultException = new GeneralFaultException(e.getMessage());
			generalFaultException.setFaultMessage(generalFault);
			throw generalFaultException;
		} catch (JoinConferenceException e) {
			GeneralFault generalFault = new GeneralFault();
			GeneralFaultException generalFaultException = new GeneralFaultException(e.getMessage());
			generalFaultException.setFaultMessage(generalFault);
			throw generalFaultException;
		} catch (FederationNotAllowedException e) {
			GeneralFault generalFault = new GeneralFault();
			GeneralFaultException generalFaultException = new GeneralFaultException(e.getMessage());
			generalFaultException.setFaultMessage(generalFault);
			throw generalFaultException;
		} catch (UserNotFoundException e) {
			InvalidArgumentFault invalidArgumentFault = new InvalidArgumentFault();
			InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException(e.getMessage());
			invalidArgumentFaultException.setFaultMessage(invalidArgumentFault);
			throw invalidArgumentFaultException;
		} catch (WrongPinException e) {
			WrongPinFault wrongPinFault = new WrongPinFault();
			WrongPinFaultException wrongPinFaultException = new WrongPinFaultException(e.getMessage());
			wrongPinFaultException.setFaultMessage(wrongPinFault);
			throw wrongPinFaultException;
		} catch (ConferenceLockedException e) {
			ConferenceLockedFault conferenceLockedFault = new ConferenceLockedFault();
			ConferenceLockedFaultException conferenceLockedFaultException = new ConferenceLockedFaultException(e.getMessage());
			conferenceLockedFaultException.setFaultMessage(conferenceLockedFault);
			throw conferenceLockedFaultException;
		} catch(ResourceNotAvailableException rnae) {
			GeneralFault generalFault = new GeneralFault();
			GeneralFaultException generalFaultException = new GeneralFaultException(rnae.getMessage());
			generalFaultException.setFaultMessage(generalFault);
			throw generalFaultException;			
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getParticipantsRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.GetParticipantsResponse getParticipants(
			com.vidyo.portal.user.GetParticipantsRequest getParticipantsRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		GetParticipantsResponse resp = new GetParticipantsResponse();
		User user = this.user.getLoginUser();

		int confID = Integer.valueOf(getParticipantsRequest.getConferenceID().getEntityID());

		Room room = this.room.getRoom(confID);
		if (room.getMemberID() != user.getMemberID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of conference for conferenceID = " + confID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		Filter_type0 input_param = getParticipantsRequest.getFilter();
		EntityFilter filter = null;
		// map ws_filter to entity filter
		if (input_param != null) {
			filter = new EntityFilter();
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			if (input_param.getLimit() >= 0) {
				filter.setLimit(input_param.getLimit());
			}
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
					filter.setSortBy("endpointID");
				} else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSortBy("name");
				} else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSortBy("ext");
				} else {
					filter.setSortBy("");
				}
			}
			if (input_param.getEntityType() != null) {
				// mapping between WS and SQL
				if (input_param.getEntityType().getValue().equalsIgnoreCase("Member")) {
					filter.setEntityType("Personal");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Room")) {
					filter.setEntityType("Public");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Legacy")) {
					filter.setEntityType("Legacy");
				}
			} else {
				filter.setEntityType("All");
			}
			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
		}

		List<Entity> list = this.conference.getParticipants(confID, filter, user);

		// map list of entities to ws_entities
		for (com.vidyo.bo.Entity entity : list) {
			Entity_type0 ws_entity = getWSentityFromBOentity(user, entity);

			EntityID p_id = new EntityID();
			p_id.setEntityID(String.valueOf(entity.getEndpointID())); // endpointID as ParticipantID
			ws_entity.setParticipantID(p_id);

			resp.addEntity(ws_entity);
		}

		// total records in DB
		Long total = this.conference.getCountParticipants(confID);
		resp.setTotal(total.intValue());
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param leaveConferenceRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.LeaveConferenceResponse leaveConference(
			com.vidyo.portal.user.LeaveConferenceRequest leaveConferenceRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		LeaveConferenceResponse resp = new LeaveConferenceResponse();
		User user = this.user.getLoginUser();

		int confID = Integer.valueOf(leaveConferenceRequest.getConferenceID().getEntityID());

		Room room;
		try {
			room = this.room.getRoom(confID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("ConferenceID is invalid");
			exception.setFaultMessage(fault);
			throw exception;
		}
		if (room.getMemberID() != user.getMemberID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of conference for conferenceID = " + confID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		int endpointID = Integer.valueOf(leaveConferenceRequest.getParticipantID().getEntityID());

		try {
			this.conference.leaveTheConference(endpointID, confID, CallCompletionCode.BY_SELF);
		} catch (Exception e) {
			GeneralFault fault = new GeneralFault();
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param directCallRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.DirectCallResponse directCall(
			com.vidyo.portal.user.DirectCallRequest directCallRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		DirectCallResponse resp = new DirectCallResponse();
		User user = this.user.getLoginUser();

		Member member = this.member.getMember(user.getMemberID());

		Invite invite = new Invite();
		invite.setFromMemberID(member.getMemberID());
		invite.setFromRoomID(member.getRoomID());

		if (directCallRequest.getDirectCallRequestChoice_type0().getEntityID() != null) {
			int roomID = Integer.valueOf(directCallRequest.getDirectCallRequestChoice_type0().getEntityID().getEntityID());

			Room toRoom = null;
			int status = 1;
			boolean isLegacy = false;
			try {
				toRoom = this.room.getRoom(roomID);
				invite.setToMemberID(toRoom.getMemberID());

				Member invitee = this.member.getMember(toRoom.getMemberID());
				if (invitee.getRoleID() == 6) {
					isLegacy = true;
				}

				int toEndpointID = 0;
				if (!isLegacy) {
					toEndpointID = this.conference.getEndpointIDForMemberID(toRoom.getMemberID());
					invite.setToEndpointID(toEndpointID);
					// check if status of Endpoint is Online
					status = this.conference.getEndpointStatus(toEndpointID);
				}
			} catch (Exception e) {
				invite.setToMemberID(0);
				invite.setToEndpointID(0);
			}

			if (toRoom != null && toRoom.getMemberID() == user.getMemberID()) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Cannot do a direct call to myself");
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (!isLegacy && status != 1) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Status of invited member is not Online");
				exception.setFaultMessage(fault);
				throw exception;
			}

		} else if (directCallRequest.getDirectCallRequestChoice_type0().getInvite() != null) {
			String search = directCallRequest.getDirectCallRequestChoice_type0().getInvite();
			invite.setSearch(search);
		}

		try {
			this.conference.twoPartyConference(invite);
		} catch (Exception e) {
			GeneralFault fault = new GeneralFault();
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param inviteToConferenceRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.InviteToConferenceResponse inviteToConference(
			com.vidyo.portal.user.InviteToConferenceRequest inviteToConferenceRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		InviteToConferenceResponse resp = new InviteToConferenceResponse();

		// Validate the ConferenceID/RoomID to join
		int roomId = 0;
		try {
			roomId = Integer.valueOf(inviteToConferenceRequest
					.getConferenceID().getEntityID());
		} catch (NumberFormatException nfe) {
			logger.warn("ConfID is not a valid integer {} ",
					inviteToConferenceRequest.getConferenceID().getEntityID());
		}

		// Validate Invitee's roomId
		int inviteeEntityId = 0;
		if (inviteToConferenceRequest
				.getInviteToConferenceRequestChoice_type0().getEntityID() != null) {
			try {
				inviteeEntityId = Integer.valueOf(inviteToConferenceRequest
						.getInviteToConferenceRequestChoice_type0().getEntityID()
						.getEntityID());
			} catch (NumberFormatException nfe) {
				logger.warn("Invitee EntityId is not a valid integer {} ",
						inviteToConferenceRequest.getConferenceID().getEntityID());
			}
		}

		String inviteeInput = inviteToConferenceRequest
				.getInviteToConferenceRequestChoice_type0().getInvite();

		inviteeInput = inviteeInput != null ? inviteeInput.trim()
				: inviteeInput;

		com.vidyo.service.conference.request.InviteToConferenceRequest conferenceRequest = new com.vidyo.service.conference.request.InviteToConferenceRequest();
		conferenceRequest.setInviteeId(inviteeEntityId);
		conferenceRequest.setInviterId(user.getLoginUser().getMemberID());
		conferenceRequest.setLegacy(inviteeInput);
		conferenceRequest.setRoomId(roomId);

		com.vidyo.service.conference.response.JoinConferenceResponse joinConferenceResponse = conferenceAppService
				.inviteParticipantToConference(conferenceRequest);

		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.REQUIRED_PARAMS_NOT_PRESENT) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					joinConferenceResponse.getDisplayMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.INVALID_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					"Conference ID is invalid");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.CANNOT_CONTROL_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					"You are not an owner of conference for conferenceID = "
							+ roomId);
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (joinConferenceResponse.getStatus() != com.vidyo.service.conference.response.JoinConferenceResponse.SUCCESS) {
			GeneralFault fault = new GeneralFault();
			GeneralFaultException exception = new GeneralFaultException(
					joinConferenceResponse.getDisplayMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param muteAudioRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.MuteAudioResponse muteAudio(
			com.vidyo.portal.user.MuteAudioRequest muteAudioRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		MuteAudioResponse resp = new MuteAudioResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(muteAudioRequest.getConferenceID().getEntityID());
		int endpointID = Integer.valueOf(muteAudioRequest.getParticipantID().getEntityID());

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("ConferenceID is invalid");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getMemberID() != user.getMemberID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of the Conference");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!this.conference.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Participant not found in Conference");
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			this.conference.muteAudio(endpointGUID);
		} catch (Exception e) {
			GeneralFault fault = new GeneralFault();
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param unmuteAudioRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */

	public com.vidyo.portal.user.UnmuteAudioResponse unmuteAudio(
			com.vidyo.portal.user.UnmuteAudioRequest unmuteAudioRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		UnmuteAudioResponse resp = new UnmuteAudioResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(unmuteAudioRequest.getConferenceID()
				.getEntityID());
		int endpointID = Integer.valueOf(unmuteAudioRequest.getParticipantID()
				.getEntityID());

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("ConferenceID is invalid");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getMemberID() != user.getMemberID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of the Conference");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!this.conference.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Participant not found in Conference");
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			this.conference.unmuteAudio(endpointGUID);
		} catch (Exception e) {
			GeneralFault fault = new GeneralFault();
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param stopVideoRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.StopVideoResponse stopVideo(
			com.vidyo.portal.user.StopVideoRequest stopVideoRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		StopVideoResponse resp = new StopVideoResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(stopVideoRequest.getConferenceID().getEntityID());
		int endpointID = Integer.valueOf(stopVideoRequest.getParticipantID().getEntityID());

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("ConferenceID is invalid");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getMemberID() != user.getMemberID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of the Conference");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!this.conference.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Participant not found in Conference");
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			this.conference.stopVideo(endpointGUID);
		} catch (Exception e) {
			GeneralFault fault = new GeneralFault();
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param startVideoRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.StartVideoResponse startVideo(
			com.vidyo.portal.user.StartVideoRequest startVideoRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		StartVideoResponse resp = new StartVideoResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(startVideoRequest.getConferenceID().getEntityID());
		int endpointID = Integer.valueOf(startVideoRequest.getParticipantID().getEntityID());

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("ConferenceID is invalid");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getMemberID() != user.getMemberID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of the Conference");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!this.conference.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Participant not found in Conference");
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			this.conference.startVideo(endpointGUID);
		} catch (Exception e) {
			GeneralFault fault = new GeneralFault();
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param lockRoomRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.LockRoomResponse lockRoom(
			com.vidyo.portal.user.LockRoomRequest lockRoomRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		LockRoomResponse resp = new LockRoomResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(lockRoomRequest.getRoomID().getEntityID());
		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("RoomID is invalid");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getMemberID() != user.getMemberID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of room for roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		this.room.lockRoom(roomID);

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param unlockRoomRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.UnlockRoomResponse unlockRoom(
			com.vidyo.portal.user.UnlockRoomRequest unlockRoomRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		UnlockRoomResponse resp = new UnlockRoomResponse();
		User user = this.user.getLoginUser();

		int roomID = Integer.valueOf(unlockRoomRequest.getRoomID().getEntityID());
		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("RoomID is invalid");
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getMemberID() != user.getMemberID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of room for roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		this.room.unlockRoom(roomID);

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param searchByEntityIDRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.SearchByEntityIDResponse searchByEntityID(
			com.vidyo.portal.user.SearchByEntityIDRequest searchByEntityIDRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		SearchByEntityIDResponse resp = new SearchByEntityIDResponse();
		User user = this.user.getLoginUser();

		String sEntityID = searchByEntityIDRequest.getEntityID().getEntityID();
		int entityID;

		try {
			entityID = Integer.parseInt(sEntityID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad entityID = " + sEntityID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = this.room.getRoom(entityID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad entityID = " + sEntityID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		Filter_type0 input_param = searchByEntityIDRequest.getFilter();
		EntityFilter filter = null;
		// map ws_filter to entity filter
		if (input_param != null) {
			filter = new EntityFilter();
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			if (input_param.getLimit() >= 0) {
				filter.setLimit(input_param.getLimit());
			}
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
					filter.setSortBy("roomID");
				} else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSortBy("name");
				} else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSortBy("ext");
				} else {
					filter.setSortBy("");
				}
			}
			if (input_param.getEntityType() != null) {
				// mapping between WS and SQL
				if (input_param.getEntityType().getValue().equalsIgnoreCase("Member")) {
					filter.setEntityType("Personal");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Room")) {
					filter.setEntityType("Public");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Legacy")) {
					filter.setEntityType("Legacy");
				}
			} else {
				filter.setEntityType("All");
			}
			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
			if (filter.getQuery().equalsIgnoreCase("*")) {
				if (filter.getEntityType().equalsIgnoreCase("Public") || filter.getEntityType().equalsIgnoreCase("Legacy")) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Cannot use * with specified Entity Type - " + filter.getEntityType());
					exception.setFaultMessage(fault);
					throw exception;
				}
			}
		}

		List<Entity> list = this.room.getEntityByOwnerID(filter, user, room.getMemberID());

		// map list of entities to ws_entities
		for (com.vidyo.bo.Entity entity : list) {
			Entity_type0 ws_entity = getWSentityFromBOentity(user, entity);
			resp.addEntity(ws_entity);
		}

		// total records in DB
		Long total = this.room.getCountEntityByOwnerID(filter, user, room.getMemberID());
		resp.setTotal(total.intValue());

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param searchByEmailRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.SearchByEmailResponse searchByEmail(
			com.vidyo.portal.user.SearchByEmailRequest searchByEmailRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		SearchByEmailResponse resp = new SearchByEmailResponse();
		User user = this.user.getLoginUser();

		String emailAddress = searchByEmailRequest.getEmailAddress();

		Filter_type0 input_param = searchByEmailRequest.getFilter();
		EntityFilter filter = null;
		// map ws_filter to entity filter
		if (input_param != null) {
			filter = new EntityFilter();
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			if (input_param.getLimit() >= 0) {
				filter.setLimit(input_param.getLimit());
			}
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
					filter.setSortBy("roomID");
				} else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSortBy("name");
				} else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSortBy("ext");
				} else {
					filter.setSortBy("");
				}
			}
			if (input_param.getEntityType() != null) {
				// mapping between WS and SQL
				if (input_param.getEntityType().getValue().equalsIgnoreCase("Member")) {
					filter.setEntityType("Personal");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Room")) {
					filter.setEntityType("Public");
				} else if (input_param.getEntityType().getValue().equalsIgnoreCase("Legacy")) {
					filter.setEntityType("Legacy");
				}
			} else {
				filter.setEntityType("All");
			}
			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
			if (filter.getQuery().equalsIgnoreCase("*")) {
				if (filter.getEntityType().equalsIgnoreCase("Public") || filter.getEntityType().equalsIgnoreCase("Legacy")) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Cannot use * with specified Entity Type - " + filter.getEntityType());
					exception.setFaultMessage(fault);
					throw exception;
				}
			}
		}

		List<Entity> list = this.room.getEntityByEmail(filter, user, emailAddress);

		// map list of entities to ws_entities
		for (com.vidyo.bo.Entity entity : list) {
			Entity_type0 ws_entity = getWSentityFromBOentity(user, entity);
			resp.addEntity(ws_entity);
		}

		// total records in DB
		Long total = this.room.getCountEntityByEmail(filter, user, emailAddress);
		resp.setTotal(total.intValue());
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param createRoomRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.CreateRoomResponse createRoom(
			com.vidyo.portal.user.CreateRoomRequest createRoomRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
		User user = this.user.getLoginUser();

		CreateRoomResponse resp = new CreateRoomResponse();
		String name = createRoomRequest.getName();
		String ext = createRoomRequest.getExtension();
		
		if(StringUtils.isEmpty(name)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					"Invalid Room Name - " + name);
			exception.setFaultMessage(fault);
			throw exception;			
		}

		if(StringUtils.isEmpty(ext) || ext.length() > 64) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					"Invalid Room Extension - " + ext);
			exception.setFaultMessage(fault);
			throw exception;			
		}
		

		if (this.room.isRoomExistForRoomName(name, 0)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Room exist for name = " + name);
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (this.room.isRoomExistForRoomExtNumber(ext, 0)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Room exist for extension = " + ext);
			exception.setFaultMessage(fault);
			throw exception;
		}

		Member member = this.member.getMember(user.getMemberID());

		Room room = new Room();
		room.setRoomEnabled(1); // active by default
		room.setRoomTypeID(2); // public room
		room.setRoomName(name);
		room.setRoomExtNumber(ext);
		room.setPinSetting("leave");
		room.setGroupID(member.getGroupID());
		room.setMemberID(member.getMemberID());

		if (VendorUtils.isRoomsLockedByDefault()) {
			room.setRoomLocked(1);
		}
		int roomID = this.room.insertRoom(room);

		if (roomID > 0) {
			Entity entity = this.room.getOneEntity(roomID, user);
			Entity_type0 ws_entity = getWSentityFromBOentity(user, entity);
			resp.setEntity(ws_entity);
		}

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param deleteRoomRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.DeleteRoomResponse deleteRoom(
			com.vidyo.portal.user.DeleteRoomRequest deleteRoomRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		DeleteRoomResponse resp = new DeleteRoomResponse();
		String entityID = deleteRoomRequest.getRoomID().getEntityID();
		int roomID;
		Room room;
		User loggedInUser = user.getLoginUser();

		try {
			roomID = Integer.parseInt(entityID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad roomID = " + entityID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Room not found for roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		if (loggedInUser.getMemberID() != room.getMemberID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}		

		if (room.getRoomType().equalsIgnoreCase("Personal")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Cannot delete Personal type of Room for roomID = " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		int rc = this.room.deleteRoom(room.getRoomID());

		if (rc > 0) {
			resp.setOK(OK_type0.OK);
		}

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param setMemberModeRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.SetMemberModeResponse setMemberMode(
			com.vidyo.portal.user.SetMemberModeRequest setMemberModeRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
		User user = this.user.getLoginUser();

		SetMemberModeResponse resp = new SetMemberModeResponse();
		MemberMode_type0 mode = setMemberModeRequest.getMemberMode();

		int modeID = 1;
		if (mode.getValue().equalsIgnoreCase(MemberMode_type0._Available)) {
			modeID = 1;
		} else if (mode.getValue().equalsIgnoreCase(MemberMode_type0._Away)) {
			modeID = 2;
		} else if (mode.getValue().equalsIgnoreCase(MemberMode_type0._DoNotDisturb)) {
			modeID = 3;
		}

		if (member != null) {
			this.member.updateMemberMode(user.getMemberID(), modeID);
		}

		resp.setOK(OK_type0.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getEntityByEntityIDRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.user.GetEntityByEntityIDResponse getEntityByEntityID(
			com.vidyo.portal.user.GetEntityByEntityIDRequest getEntityByEntityIDRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
		User user = this.user.getLoginUser();

		GetEntityByEntityIDResponse resp = new GetEntityByEntityIDResponse();

		EntityID[] list = getEntityByEntityIDRequest.getEntityID();

		for (EntityID id : list) {
			try {
				com.vidyo.bo.Entity entity = this.room.getOneEntity(Integer.valueOf(id.getEntityID()), user);
				Entity_type0 ws_entity = getWSentityFromBOentity(user, entity);
				resp.addEntity(ws_entity);
			} catch (Exception ignored) {
			}
		}

		resp.setTotal(resp.getEntity().length);

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getPortalVersionRequest:
	 * @throws NotLicensedFaultException:
	 * @throws InvalidArgumentFaultException:
	 * @throws GeneralFaultException:
	 * @throws SeatLicenseExpiredFaultException:
	 */
	@Override
	public GetPortalVersionResponse getPortalVersion(
			GetPortalVersionRequest getPortalVersionRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException
	{
		GetPortalVersionResponse getPortalVersionResponse = new GetPortalVersionResponse();
		String portalVersion = system.getPortalVersion();
		getPortalVersionResponse.setPortalVersion(portalVersion);
		return getPortalVersionResponse;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getUserNameRequest:
	 * @return getUserNameResponse
	 * @throws NotLicensedFaultException
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */
	public com.vidyo.portal.user.GetUserNameResponse getUserName(
			com.vidyo.portal.user.GetUserNameRequest getUserNameRequest)
			throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException
	{
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
		User user = this.user.getLoginUser();

		GetUserNameResponse resp = new GetUserNameResponse();
		resp.setRealUserName(user.getUsername());

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getEntityByRoomKeyRequest
	 * @return getEntityByRoomKeyResponse
	 * @throws NotLicensedFaultException
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */
	public com.vidyo.portal.user.GetEntityByRoomKeyResponse getEntityByRoomKey
		(
			com.vidyo.portal.user.GetEntityByRoomKeyRequest getEntityByRoomKeyRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
		User user = this.user.getLoginUser();

		GetEntityByRoomKeyResponse resp = new GetEntityByRoomKeyResponse();

		String roomKey = getEntityByRoomKeyRequest.getRoomKey();
		if (roomKey == null || roomKey.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad roomKey");
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room = this.room.getRoomForKey(roomKey);
		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Room not found for roomKey = " + roomKey);
			exception.setFaultMessage(fault);
			throw exception;
		}

		Entity entity = this.room.getOneEntity(room.getRoomID(), user);

		Entity_type0 ws_entity = getWSentityFromBOentity(user, entity);

		resp.setEntity(ws_entity);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getInviteContentRequest
	 * @return getInviteContentResponse
	 * @throws NotLicensedFaultException
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */
	public com.vidyo.portal.user.GetInviteContentResponse getInviteContent
	(
			com.vidyo.portal.user.GetInviteContentRequest getInviteContentRequest
	)
			throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException
	{
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
		User user = this.user.getLoginUser();
		Locale loc = Locale.getDefault();

		EntityID entityID = getInviteContentRequest.getRoomID();
		int roomID;
		Room room = null;

		if (entityID != null) {
			try {
				roomID = Integer.parseInt(entityID.getEntityID());
			} catch (Exception e) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Bad roomID = " + entityID.getEntityID());
				exception.setFaultMessage(fault);
				throw exception;
			}

			try {
				room = this.room.getRoom(roomID);
			} catch (Exception e) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Room not found for roomID = " + roomID);
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (room.getMemberID() != user.getMemberID()) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException("You are not an owner of room for roomID = " + roomID);
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		String content = this.system.getTenantInvitationEmailContent();
		if (content == null || content.trim().length() == 0) {
			content = this.system.getSuperInvitationEmailContent();
			if (content == null || content.trim().length() == 0) {
				content = this.ms.getMessage("defaultInvitationEmailContent", null, "", loc);
			}
		}

		String dialString;
		String pinStr;
		//create the roomlink, tenantDailIn+ext, for replacing [ROOMLINK] and [EXTENSION]
		Entity myroom;
		if (room != null) {
			myroom = this.room.getOneEntity(room.getRoomID(), user);
		} else {
			myroom = this.member.getContact(user.getMemberID());
		}

		String extension = myroom.getExt();

		String dialIn = this.room.getTenantDialIn();
		if (dialIn != null && dialIn.trim().length() > 0) {
			dialString = dialIn.concat("x").concat(extension);
		} else {
			dialString = "";
		}

		if (!this.room.areGuestAllowedToThisRoom()) {
			if (myroom.getRoomKey() != null) {
				myroom.setRoomKey(myroom.getRoomKey()+"&noguest");
			}
		}

		StringBuffer path = new StringBuffer();
		Tenant tenant = this.tenantService.getTenant(user.getTenantID());
		String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
		/*path.append(transportName).append("://").append(tenant.getTenantURL());
		path.append("/flex.html?roomdirect.html&key=");
		path.append(myroom.getRoomKey());*/

		String joinURL = this.room.getRoomURL(system, transportName, 
				tenant.getTenantURL(), myroom.getRoomKey());

		path.append(joinURL);

		if (tenantService.isTenantNotAllowingGuests()) {
			path.append("&noguest");
		}

		if (myroom.getRoomPinned() == 1) {
			path.append(" (Room PIN: ").append(myroom.getRoomPIN()).append(") ");
			pinStr = " (Room PIN: " + myroom.getRoomPIN() + ") ";
			if(dialString != null) {
				dialString = dialString.concat("*").concat( myroom.getRoomPIN());	
			}
			if(extension != null) {
				extension = extension.concat("*").concat(myroom.getRoomPIN());
			}
		} else {
			path.append(" ");
			pinStr = "";			
		}

		content = content.replaceAll("\\[PIN\\]", pinStr);

		if(extension != null) {
			content = content.replaceAll("\\[EXTENSION\\]", extension);	
		}
		
		if(dialString != null) {
			content = content.replaceAll("\\[DIALSTRING\\]", dialString);	
		}
		
		//Determine type of room to set DisplayName/RoomName
		if(myroom.getRoomType().equalsIgnoreCase("Public")) {
			content = content.replaceAll("\\[DISPLAYNAME\\]", myroom.getRoomName());
		}else {
			content = content.replaceAll("\\[DISPLAYNAME\\]", myroom.getName());
		}
		
		//Room Name is always the room name and not display name
		content = content.replaceAll("\\[ROOMNAME\\]", myroom.getRoomName());

		content = content.replaceAll("\\[TENANTURL\\]", tenant.getTenantURL());

		content = content.replaceAll("\\[ROOMLINK\\]", path != null ? path.toString() : "");
		// first try
		String windowsGuide = "";
		try {
			windowsGuide = system.getDesktopUserGuide("windows", loc.toString()); // lang_Country
		} catch (IOException ignored) {
			//Log the error
			logger.error("Exception While Accessing portal.properties", ignored.getMessage());
		}
		// second try
		if (windowsGuide == null || windowsGuide == "") {
			try {
				windowsGuide = system.getDesktopUserGuide("windows", loc.getLanguage()); // just a lang
			} catch (IOException ignored) {
				logger.error("Exception While Accessing portal.properties", ignored.getMessage());
			}
		}
		content = content.replaceAll("\\[WINDOWS_GUIDE\\]", windowsGuide != null ? windowsGuide : "");

		// first try
		String macGuide = "";
		try {
			macGuide = system.getDesktopUserGuide("mac", loc.toString()); // lang_Country
		} catch (IOException ignored) {
			logger.error("Exception While Accessing portal.properties", ignored.getMessage());
		}
		// second try
		if (macGuide == null || macGuide == "") {
			try {
				macGuide = system.getDesktopUserGuide("mac", loc.getLanguage()); // just a lang
			} catch (IOException ignored) {
				logger.error("Exception While Accessing portal.properties", ignored.getMessage());
			}
		}
		content = content.replaceAll("\\[MAC_GUIDE\\]", macGuide != null ? macGuide : "");

		GetInviteContentResponse resp = new GetInviteContentResponse();
		resp.setContent(content);

		return resp;
	}

}