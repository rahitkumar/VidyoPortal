/**
 * VidyoPortalAdminService_v1_1Skeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.portal.admin.v1_1;

import com.vidyo.bo.*;
import com.vidyo.bo.Group;
import com.vidyo.bo.Location;
import com.vidyo.bo.Room;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.bo.email.InviteEmailContentForInviteRoom;
import com.vidyo.bo.member.MemberKey;
import com.vidyo.bo.profile.RoomProfile;
import com.vidyo.bo.room.RoomMini;
import com.vidyo.bo.room.RoomTypes;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.dao.BaseQueryFilter;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.framework.util.ValidatorUtil;
import com.vidyo.service.*;
import com.vidyo.service.conference.response.ConferenceControlResponse;
import com.vidyo.service.endpointbehavior.EndpointBehaviorService;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.service.exceptions.JoinConferenceException;
import com.vidyo.service.exceptions.NoVidyoReplayException;
import com.vidyo.service.exceptions.OutOfPortsException;
import com.vidyo.service.gateway.GatewayServiceImpl;
import com.vidyo.service.lecturemode.LectureModeService;
import com.vidyo.service.lecturemode.request.LectureModeControlRequest;
import com.vidyo.service.lecturemode.request.LectureModeParticipantControlRequest;
import com.vidyo.service.lecturemode.request.LectureModeParticipantsRequest;
import com.vidyo.service.lecturemode.response.LectureModeControlResponse;
import com.vidyo.service.lecturemode.response.LectureModeParticipantsResponse;
import com.vidyo.service.room.PublicRoomCreateRequest;
import com.vidyo.service.room.RoomCreationResponse;
import com.vidyo.service.room.SchRoomCreationRequest;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.superapp.components.bo.RecorderEndpoints;
import com.vidyo.superapp.components.service.ComponentsService;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.VendorUtils;
import com.vidyo.utils.room.RoomUtils;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VidyoPortalAdminServiceSkeleton implements VidyoPortalAdminServiceSkeletonInterface {

	protected static final Logger logger = LoggerFactory.getLogger(VidyoPortalAdminServiceSkeleton.class.getName());
	
	private enum Operation {
		CREATE,
		UPDATE;
	};
	
	private IMemberService memberService;
	private IGroupService groupService;
	private IRoomService roomService;
	private IConferenceService conferenceService;
	private ISystemService systemService;
	private LicensingService licenseService;
	private IUserService userService;
	private ITenantService tenantService;
	private IServiceService serviceService;

	@Autowired
	private IReplayService replayService;
	private ReloadableResourceBundleMessageSource ms;

	@Autowired
    private TransactionService transaction;

	@Autowired
    private EndpointUploadService endpointUploadService;

	@Autowired
	private EndpointBehaviorService endpointBehaviorService;

	@Autowired
	private ComponentsService componentService;

	@Autowired
	private LectureModeService lectureModeService;

	private JmsTemplate jmsProducerTemplate;
	private Topic adminOrOperatorDeleteMessageMQtopic;

	private static final String FAILURE = "FAILURE";
	private static final String SUCCESS = "SUCCESS";

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}

	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	public void setConferenceService(IConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public void setLicenseService(LicensingService licenseService) {
		this.licenseService = licenseService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public void setServiceService(IServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public void setJmsProducerTemplate(JmsTemplate jmsProducerTemplate) {
		this.jmsProducerTemplate = jmsProducerTemplate;
	}

	public void setAdminOrOperatorDeleteMessageMQtopic(Topic adminOrOperatorDeleteMessageMQtopic) {
		this.adminOrOperatorDeleteMessageMQtopic = adminOrOperatorDeleteMessageMQtopic;
	}

	public void setLectureModeService(LectureModeService lectureModeService) {
		this.lectureModeService = lectureModeService;
	}

	public void setMs(ReloadableResourceBundleMessageSource ms) {
        this.ms = ms;
    }

	/**
	 * Auto generated method signature
	 *
	 * @param getParticipantsRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetParticipantsResponse getParticipants
		(
			GetParticipantsRequest getParticipantsRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getParticipants() of VidyoPortalAdminService_v1_1Skeleton");

		User user = this.userService.getLoginUser();

		GetParticipantsResponse resp = new GetParticipantsResponse();

		int confID = getParticipantsRequest.getConferenceID().getEntityID();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(confID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid " + confID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid " + confID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Conference for ConferenceID = " + confID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Filter_type0 input_param = getParticipantsRequest.getFilter();
		EntityFilter filter = new EntityFilter();
		// map ws_filter to entity filter
		if (input_param != null) {
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			validateAndSetFilterLimitParam(filter, input_param);
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

			filter.setEntityType("All");

			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
		} else {
			filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
			filter.setEntityType("All");
		}

		// total participants except recorder
		Long total = conferenceService.getCountParticipants(confID);
		List<com.vidyo.bo.Entity> list = conferenceService.getParticipants(confID, filter, null);
		// map list of entities to ws_entities
		for (com.vidyo.bo.Entity entity : list) {
			// if the type is Recorder, dont add it to Entity list
			if (entity.getRoomType() != null && entity.getRoomType().equalsIgnoreCase("Recorder")) {
				resp.setRecorderID(entity.getEndpointID());
				resp.setRecorderName(entity.getName());
				resp.setWebcast(entity.getWebcast() == 1);
				resp.setPaused(entity.getVideo() == 0);
				total--;
			} else {
				Entity_type0 ws_entity = new Entity_type0();
				EntityID id = new EntityID();
				id.setEntityID(entity.getRoomID()); // roomID as EntityID
				ws_entity.setEntityID(id);

				EntityID p_id = new EntityID();
				p_id.setEntityID(entity.getEndpointID()); // endpointID as
															// ParticipantID
				ws_entity.setParticipantID(p_id);

				if (entity.getRoomType() != null) {
					if (entity.getRoomType().equalsIgnoreCase("Personal")) {
						ws_entity.setEntityType(EntityType_type0.Member);
						ws_entity.setCanCallDirect(true);
						ws_entity.setCanJoinMeeting(true);
					} else if (entity.getRoomType().equalsIgnoreCase("Public")) {
						ws_entity.setEntityType(EntityType_type0.Room);
						ws_entity.setCanCallDirect(false);
						ws_entity.setCanJoinMeeting(true);
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
					com.vidyo.bo.Member member = memberService.getMember(entity.getMemberID());

					if (member.getLangID() == 1) {
						ws_entity.setLanguage(Language_type0.en);
					} else if (member.getLangID() == 2) {
						ws_entity.setLanguage(Language_type0.fr);
					} else if (member.getLangID() == 3) {
						ws_entity.setLanguage(Language_type0.ja);
					} else if (member.getLangID() == 4) {
						ws_entity.setLanguage(Language_type0.zh_CN);
					} else if (member.getLangID() == 5) {
						ws_entity.setLanguage(Language_type0.es);
					} else if (member.getLangID() == 6) {
						ws_entity.setLanguage(Language_type0.it);
					} else if (member.getLangID() == 7) {
						ws_entity.setLanguage(Language_type0.de);
					} else if (member.getLangID() == 8) {
						ws_entity.setLanguage(Language_type0.ko);
					} else if (member.getLangID() == 9) {
						ws_entity.setLanguage(Language_type0.pt);
					} else if (member.getLangID() == 10) {
						ws_entity.setLanguage(Language_type0.Factory.fromValue(systemService.getSystemLang(member.getTenantID()).getLangCode()));
					} else if (member.getLangID() == 11) {
						ws_entity.setLanguage(Language_type0.fi);
					} else if (member.getLangID() == 12) {
						ws_entity.setLanguage(Language_type0.pl);
					} else if (member.getLangID() == 13) {
						ws_entity.setLanguage(Language_type0.zh_TW);
					} else if (member.getLangID() == 14) {
						ws_entity.setLanguage(Language_type0.th);
					} else if (member.getLangID() == 15) {
						ws_entity.setLanguage(Language_type0.ru);
					} else if (member.getLangID() == 16) {
						ws_entity.setLanguage(Language_type0.tr);
					}

					if (entity.getModeID() == 1) {
						ws_entity.setMemberMode(MemberMode_type0.Available);
					} else if (entity.getModeID() == 2) {
						ws_entity.setMemberMode(MemberMode_type0.Away);
					} else if (entity.getModeID() == 3) {
						ws_entity.setMemberMode(MemberMode_type0.DoNotDisturb);
					}
				} else {
					ws_entity.setLanguage(Language_type0.Factory.fromValue(systemService.getSystemLang(entity.getTenantID()).getLangCode()));
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

				if (entity.getRoomPIN() != null) {
					roomMode.setRoomPIN(entity.getRoomPIN());
					roomMode.setHasPIN(StringUtils.isNotEmpty(entity.getRoomPIN()));
				}
				if (entity.getRoomKey() != null) {
					StringBuffer path = new StringBuffer();
					Tenant tenant = tenantService.getTenant(entity.getTenantID());
					/*path.append(://").append();
					path.append("/flex.html?roomdirect.html&key=");
					path.append(entity.getRoomKey());*/

					String joinURL = roomService.getRoomURL(systemService, "http",
							tenant.getTenantURL(), entity.getRoomKey());

					path.append(joinURL);

					if (tenantService.isTenantNotAllowingGuests()) {
						path.append("&noguest");
					}

					roomMode.setRoomURL(path.toString());
				}

				if (entity.getRoomModeratorPIN() != null) {
					roomMode.setModeratorPIN(entity.getRoomModeratorPIN());
					roomMode.setHasPIN(StringUtils.isNotEmpty(entity.getRoomModeratorPIN()));
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

				resp.addEntity(ws_entity);
			}
		}

		resp.setTotal(total.intValue());
		if (logger.isDebugEnabled())
			logger.debug("Exiting getParticipants() of Admin API v.1.1");
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param leaveConferenceRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public LeaveConferenceResponse leaveConference
		(
			LeaveConferenceRequest leaveConferenceRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering leaveConference() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int confID = leaveConferenceRequest.getConferenceID().getEntityID();
		int endpointID = leaveConferenceRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(confID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid " + confID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid " + confID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Conference for ConferenceID = " + confID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			conferenceService.leaveTheConference(endpointID, confID, CallCompletionCode.BY_SOMEONE_ELSE);
		} catch (java.lang.Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		LeaveConferenceResponse leaveConferenceResponse = new LeaveConferenceResponse();
		leaveConferenceResponse.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting leaveConference() of Admin API v.1.1");

		return leaveConferenceResponse;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getRoomRequest
	 * @throws NotLicensedFaultException :
	 * @throws RoomNotFoundFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetRoomResponse getRoom
		(
			GetRoomRequest getRoomRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		RoomNotFoundFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getRoom() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = getRoomRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("RoomID is invalid " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoom(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not found for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("Room not found for roomID = " + roomID);
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String requestScheme = request.getScheme();
		GetRoomResponse resp = new GetRoomResponse();
		com.vidyo.portal.admin.v1_1.Room ws_room = getWSRoomFromBORoom(room, requestScheme);
		resp.setRoom(ws_room);

		if (logger.isDebugEnabled())
			logger.debug("Exiting getRoom() of Admin API v.1.1");

		return resp;
	}

	@Override
	public TransferParticipantResponse transferParticipant(TransferParticipantRequest transferParticipantRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, NotLicensedFaultException {

		User user = this.userService.getLoginUser();

		int endpointId = transferParticipantRequest.getParticipantID().getEntityID();
		int roomId = transferParticipantRequest.getConferenceID().getEntityID();

		if (endpointId <= 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ParticipantID");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		if (roomId <= 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceID");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		// determine if virtual endpoint
		VirtualEndpoint endpoint = null;
		try {
			endpoint = conferenceService.getVirtualEndpointForEndpointID(endpointId);
		} catch (Exception e) {
			// endpoint not found
		}
		if (endpoint == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ParticipantID is not a virtual endpoint");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (conferenceService.isEndpointIDinRoomID(endpointId, roomId)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant cannot be transferred to conference they are already in.");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (endpoint.getTenantID() != TenantContext.getTenantId()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ParticipantID for " + endpointId + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoom(roomId);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceID and/or PIN."); // invalid ConferenceID
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceID and/or PIN."); // invalid ConferenceID
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceID and/or PIN."); // room belongs to other tenant
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getRoomEnabled() == 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room is disabled.");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getRoomLocked() == 1) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room is locked.");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getRoomStatus() == 2) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room is full.");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String toExtension = room.getRoomExtNumber();
		String toGUID = endpoint.getEndpointGUID();
		String roomPIN = transferParticipantRequest.getRoomPIN();

		if (room.getRoomType().equalsIgnoreCase("Scheduled")) {
			if (!StringUtils.isBlank(roomPIN) && // pin provided
					!StringUtils.isBlank(room.getRoomPIN())) { // pin exists
				ScheduledRoomResponse scheduledRoomResponse = roomService.generateSchRoomExtPin(
						room.getMemberID(),
						Integer.parseInt(room.getRoomPIN()));
				if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.SUCCESS) {
					toExtension = scheduledRoomResponse.getRoomExtn();
				}
			} else if (StringUtils.isBlank(roomPIN) && // pin not provided
					!StringUtils.isBlank(room.getRoomPIN())) { // pin exists
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid ConferenceID and/or PIN.");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
			ScheduledRoomResponse scheduledRoomResponse = roomService.validateScheduledRoom(toExtension, roomPIN, room.getTenantID());
			if (scheduledRoomResponse.getStatus() != ScheduledRoomResponse.SUCCESS) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid ConferenceID and/or PIN.");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		} else {
			if (!StringUtils.isBlank(room.getRoomPIN()) && !room.getRoomPIN().equals(roomPIN)) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid ConferenceID and/or PIN.");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		// strip out incoming room PIN if actual room doesn't have a PIN
		if (StringUtils.isBlank(room.getRoomPIN())) {
			roomPIN = null;
		}

		// send VCAP message
		try {
			conferenceService.sendMessageToEndpoint(toGUID,
					GatewayServiceImpl.getTransferParticipantVCAPMsg(toGUID, toExtension, roomPIN));
			logger.info("Sent transfer VCAP msg to: " + toGUID + " regarding transfer of: " + endpoint.getEndpointGUID() + " to: " + toExtension);
		} catch (Exception e) {
			logger.error("Could not send transfer VCAP msg to: " + toGUID + " regarding transfer of: " + endpoint.getEndpointGUID() + " to: " + toExtension);

			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Unable to communicate with endpoint.");
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		TransferParticipantResponse response = new TransferParticipantResponse();
		response.setOK(OK_type0.OK);
		return response;

	}



	@Override
	public SetLayoutResponse setLayout(SetLayoutRequest setLayoutRequest) throws InvalidArgumentFaultException, GeneralFaultException, NotLicensedFaultException {
		User user = this.userService.getLoginUser();

		int endpointId = setLayoutRequest.getParticipantID().getEntityID();
		String layout = setLayoutRequest.getLayout();
		setLayoutRequest.isMaxParticipantsSpecified();
		BigInteger maxParticipants = setLayoutRequest.getMaxParticipants();

		if (endpointId <= 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ParticipantID");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (StringUtils.isNotBlank(layout)) {
			if (layout.length() > 100) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Layout (String too long)");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		if (maxParticipants != null) {
			if (maxParticipants.intValue() <= 0 || maxParticipants.intValue() > 100) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid MaxParticipants");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		// determine if virtual endpoint
		VirtualEndpoint endpoint = null;
		try {
			endpoint = conferenceService.getVirtualEndpointForEndpointID(endpointId);
		} catch (Exception e) {
			// endpoint not found
		}
		if (endpoint == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ParticipantID is not a virtual endpoint");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room = null;

		try {
			int roomId = conferenceService.getRoomIDForEndpointID(endpointId);
			room = roomService.getRoomDetailsForConference(roomId);
		} catch (Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Unable to determine room for endpoint.");
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if(room == null) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Unable to determine room for endpoint.");
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getTenantID() != TenantContext.getTenantId()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ParticipantID for " + endpointId + " not in room that belongs to this tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String toGUID = endpoint.getEndpointGUID();

		// send VCAP message
		try {
			conferenceService.sendMessageToEndpoint(toGUID,
					GatewayServiceImpl.getSetLayoutVCAPMsg(toGUID, layout, maxParticipants));
			logger.info("Sent transfer VCAP msg to: " + toGUID + " regarding setLayout of: " + endpoint.getEndpointGUID());
		} catch (Exception e) {
			logger.error("Could not send transfer VCAP msg to: " + toGUID + " regarding setLayout of: " + endpoint.getEndpointGUID());

			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Unable to communicate with endpoint.");
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		SetLayoutResponse response = new SetLayoutResponse();
		response.setOK(OK_type0.OK);
		return response;

	}


	@Override
    public DeleteScheduledRoomResponse deleteScheduledRoom(DeleteScheduledRoomRequest deleteScheduledRoomRequest) throws NotLicensedFaultException, RoomNotFoundFaultException, InvalidArgumentFaultException, GeneralFaultException {
        if (logger.isDebugEnabled())
            logger.debug("Entering deleteScheduledRoom() of Admin API v.1.1");

        User user = userService.getLoginUser();
        int tenantId = TenantContext.getTenantId();

        String extension = deleteScheduledRoomRequest.getExtension().getExtension_type6();
        Pin_type5 pinObj = deleteScheduledRoomRequest.getPin();
        String pin = null;
        if (pinObj != null) {
            pin = pinObj.getPin_type4();
        }

        ScheduledRoomResponse result = roomService.validateScheduledRoom(extension, pin, tenantId);
        if (result.getStatus() != ScheduledRoomResponse.SUCCESS) {
            RoomNotFoundFault fault = new RoomNotFoundFault();
            fault.setErrorMessage("No such scheduled room found in tenant.");
            RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        if (result.getRoom().getTenantID() != tenantId) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Unauthorized, room not owned by this tenant. ");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        int rowsDeleted = roomService.deleteRoom(result.getRoom().getRoomID());
        if (rowsDeleted < 1) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Unable to delete room.");
            GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        DeleteScheduledRoomResponse resp = new DeleteScheduledRoomResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled())
            logger.debug("Exiting deleteScheduledRoom() of Admin API v.1.1");

        return resp;
    }

    @Override
	public GetLectureModeParticipantsResponse getLectureModeParticipants(GetLectureModeParticipantsRequest getLectureModeParticipantsRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {

		if (logger.isDebugEnabled())
			logger.debug("Entering getLectureModeParticipants() of Admin API v.1.1");

		GetLectureModeParticipantsResponse resp = new GetLectureModeParticipantsResponse();

		int roomID = Integer.valueOf(getLectureModeParticipantsRequest.getConferenceID().getEntityID());

		String moderatorPIN =  "noModeratorPIN";

		LectureModeParticipantsRequest serviceRequest = new LectureModeParticipantsRequest();
		serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
		serviceRequest.setTenantID(TenantContext.getTenantId());
		serviceRequest.setRoomID(roomID);
		serviceRequest.setModeratorPIN(moderatorPIN);


		Filter_type0 input_param = getLectureModeParticipantsRequest.getFilter();
		EntityFilter filter = new EntityFilter();
		// map ws_filter to entity filter
		if (input_param != null) {
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
				} else if (input_param.getSortBy()
						.equalsIgnoreCase("extension")) {
					filter.setSortBy("ext");
				} else {
					filter.setSortBy("");
				}
			}

			// admin has no entityType, unlike user service
			/*
			if (input_param.getEntityType() != null) {
				// mapping between WS and SQL
				if (input_param.getEntityType().getValue()
						.equalsIgnoreCase("Member")) {
					filter.setEntityType("Personal");
				} else if (input_param.getEntityType().getValue()
						.equalsIgnoreCase("Room")) {
					filter.setEntityType("Public");
				} else if (input_param.getEntityType().getValue()
						.equalsIgnoreCase("Legacy")) {
					filter.setEntityType("Legacy");
				}
			} else {
				filter.setEntityType("All");
			}
			*/

			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}

			filter.setEntityType("All");
		}

		serviceRequest.setEntityFilter(filter);

		LectureModeParticipantsResponse serviceResponse = lectureModeService.getLectureModeParticipants(serviceRequest);

		int status = serviceResponse.getStatus();
		if(status == ConferenceControlResponse.INVALID_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceId");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Server internal error");
			GeneralFaultException exception = new GeneralFaultException("Server internal error");
			exception.setFaultMessage(fault);
			throw exception;
		}

		// total participants except recorder
		int total = serviceResponse.getTotal();
		List<com.vidyo.bo.Entity> list = serviceResponse.getEntities();
		// map list of entities to ws_entities
		for (com.vidyo.bo.Entity entity : list) {

			LectureModeParticipant_type0 ws_entity = getLectureModeWSEntityFromBOEntity(entity);

			EntityID p_id = new EntityID();
			// endpointId as participantId
			p_id.setEntityID(entity.getEndpointID());
			ws_entity.setParticipantID(p_id);

			resp.addLectureModeParticipant(ws_entity);
		}
		resp.setRecorderID(serviceResponse.getRecorderID());
		resp.setRecorderName(serviceResponse.getRecorderName());
		resp.setWebcast(serviceResponse.isWebcast());
		resp.setPaused(serviceResponse.isPaused());
		resp.setLectureMode(serviceResponse.isLectureMode());
		resp.setTotal(total);

		if (logger.isDebugEnabled())
			logger.debug("Exiting getLectureModeParticipants() of Admin API v.1.1");

		return resp;
	}

	private LectureModeParticipant_type0 getLectureModeWSEntityFromBOEntity(com.vidyo.bo.Entity entity) {
		LectureModeParticipant_type0 ws_entity = new LectureModeParticipant_type0();

		EntityID entityID = new EntityID();
		entityID.setEntityID(entity.getRoomID());
		ws_entity.setEntityID(entityID);

		if (entity.getRoomType() != null) {
			if (entity.getRoomType().equalsIgnoreCase("Personal")) {
				ws_entity.setEntityType(EntityType_type0.Member);
			} else if (entity.getRoomType().equalsIgnoreCase("Public")) {
				ws_entity.setEntityType(EntityType_type0.Room);
			} else if (entity.getRoomType().equalsIgnoreCase("Legacy")) {
				ws_entity.setEntityType(EntityType_type0.Legacy);
			} else if(entity.getRoomType().equalsIgnoreCase("Scheduled")) {
				ws_entity.setEntityType(EntityType_type0.Room);
			}
		} else {
			ws_entity.setEntityType(EntityType_type0.Member);
		}

		ws_entity.setDisplayName(entity.getName());
		ws_entity.setExtension(entity.getExt());
		ws_entity.setVideo(entity.getVideo() == 1);
		ws_entity.setAudio(entity.getAudio() == 1);
		ws_entity.setAppshare(true);

		if (entity.getHandRaised() == 1) {
			ws_entity.setHandRaised(true);
		} else {
			ws_entity.setHandRaised(false);
		}

		if (entity.getPresenter() != 0) {
			ws_entity.setPresenter(true);
		} else {
			ws_entity.setPresenter(false);
		}

		return ws_entity;
	}

	@Override
	public StartLectureModeResponse startLectureMode(StartLectureModeRequest startLectureModeRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {

		if (logger.isDebugEnabled())
			logger.debug("Entering startLectureMode() of Admin API v.1.1");

		int roomEntityID = startLectureModeRequest.getConferenceID().getEntityID();

		String moderatorPIN = "noModeratorPIN";

		LectureModeControlRequest serviceRequest = new LectureModeControlRequest();
		serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
		serviceRequest.setTenantID(TenantContext.getTenantId());
		serviceRequest.setRoomID(roomEntityID);
		serviceRequest.setModeratorPIN(moderatorPIN);

		LectureModeControlResponse serviceResponse = lectureModeService.startLectureMode(serviceRequest);

		int status =  serviceResponse.getStatus();
		if (status == ConferenceControlResponse.INVALID_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceId");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.LECTURE_MODE_ALREADY_STARTED) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Lecture mode is already on for conference");
			GeneralFaultException exception = new GeneralFaultException("Lecture mode is already on for conference");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.LECTURE_MODE_FEATURE_NOT_ALLOWED) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Lecture mode feature disabled by tenant admin.");
			GeneralFaultException exception = new GeneralFaultException("Lecture mode feature disabled by tenant admin.");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Server internal error");
			GeneralFaultException exception = new GeneralFaultException("Server internal error");
			exception.setFaultMessage(fault);
			throw exception;
		}

		StartLectureModeResponse response = new StartLectureModeResponse();
		response.setOK(OK_type0.OK);


		if (logger.isDebugEnabled())
			logger.debug("Exiting startLectureMode() of Admin API v.1.1");

		return response;


	}

	@Override
	public DismissAllRaisedHandResponse dismissAllRaisedHand(DismissAllRaisedHandRequest dismissAllRaisedHandRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled())
			logger.debug("Entering dismissAllRaisedHand() of Admin API v.1.1");

		int roomEntityID = dismissAllRaisedHandRequest.getConferenceID().getEntityID();

		String moderatorPIN = "noModeratorPIN";

		LectureModeControlRequest serviceRequest = new LectureModeControlRequest();
		serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
		serviceRequest.setTenantID(TenantContext.getTenantId());
		serviceRequest.setRoomID(roomEntityID);
		serviceRequest.setModeratorPIN(moderatorPIN);

		LectureModeControlResponse serviceResponse = lectureModeService.dismissAllRaisedHands(serviceRequest);

		int status =  serviceResponse.getStatus();
		if (status == ConferenceControlResponse.INVALID_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceId");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.LECTURE_MODE_NOT_STARTED) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Lecture mode is not on for conference");
			GeneralFaultException exception = new GeneralFaultException("Lecture mode is not on for conference");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Server internal error");
			GeneralFaultException exception = new GeneralFaultException("Server internal error");
			exception.setFaultMessage(fault);
			throw exception;
		}

		DismissAllRaisedHandResponse response = new DismissAllRaisedHandResponse();
		response.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting dismissAllRaisedHand() of Admin API v.1.1");

		return response;

	}

	@Override
	public EnableScheduledRoomResponse enableScheduledRoom(EnableScheduledRoomRequest enableScheduledRoomRequest) throws NotLicensedFaultException, RoomNotFoundFaultException, InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled())
			logger.debug("Entering enableScheduledRoom() of Admin API v.1.1");

		User user = userService.getLoginUser();
		int tenantId = TenantContext.getTenantId();

		String extension = enableScheduledRoomRequest.getExtension().getExtension_type8();
		Pin_type7 pinObj = enableScheduledRoomRequest.getPin();
		String pin = null;
		if (pinObj != null) {
			pin = pinObj.getPin_type6();
		}

		ScheduledRoomResponse result = roomService.validateScheduledRoom(extension, pin, tenantId);
		if (result.getStatus() != ScheduledRoomResponse.SUCCESS) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("No such scheduled room found in tenant.");
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		if (result.getRoom().getTenantID() != tenantId) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Unauthorized, room not owned by this tenant. ");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room roomObj = roomService.getRoom(result.getRoom().getRoomID());
		roomObj.setRoomEnabled(enableScheduledRoomRequest.getEnabled() ? 1 : 0);
		EnableScheduledRoomResponse resp = new EnableScheduledRoomResponse();

		if (roomService.updateRoom(roomObj.getRoomID(), roomObj) > 1) {
			resp.setOK(OK_type0.OK);
		} else {GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Unable to update scheduled room");
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting enableScheduledRoom() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param updateRoomRequest
	 * @throws NotLicensedFaultException :
	 * @throws RoomNotFoundFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws RoomAlreadyExistsFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public UpdateRoomResponse updateRoom
		(
			UpdateRoomRequest updateRoomRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		RoomNotFoundFaultException,
		RoomAlreadyExistsFaultException,
		InvalidModeratorPINFormatFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering updateRoom() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		UpdateRoomResponse resp = new UpdateRoomResponse();
		com.vidyo.portal.admin.v1_1.Room ws_room = updateRoomRequest.getRoom();

		int roomID;
		roomID = updateRoomRequest.getRoomID().getEntityID();
		if (roomID <= 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid RoomID " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String name = ws_room.getName();
		if(StringUtils.isEmpty(name) || name.length() > 80) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room Name ( 1 - 80 characters) - " + name);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		/*if (!name.matches(ValidationUtils.USERNAME_REGEX)) {   
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room Name - " + name);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}*/

		String ext = ws_room.getExtension();
		// Alpha numeric extensions needs to supported for API customers - https://jira.vidyo.com/browse/VPTL-9351
		if (RoomUtils.isNotValidAlphaNumericExtension(ws_room.getExtension())) {
			InvalidArgumentFault invalidArgumentFault = new InvalidArgumentFault();
			invalidArgumentFault.setErrorMessage("Invalid Extension - Invalid format or Length should be maximum 64 characters - " + ws_room.getExtension());
			InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException(
					"Invalid Room Extension: " + ws_room.getExtension());
			invalidArgumentFaultException.setFaultMessage(invalidArgumentFault);
			throw invalidArgumentFaultException;
		}

		boolean isValidExt = RoomUtils.isValidExtensionByPrefix(tenantService, ws_room.getExtension());

		if (!isValidExt) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Extension - Extension does not start with Tenant Prefix"
					+ ws_room.getExtension());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(systemService, ws_room.getExtension());
		if (extExists > 0){
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Extension matches Schedule Room prefix. Please choose a different extension and try again. Extension = " + ws_room.getExtension());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room = null;
		try {
			room = roomService.getRoom(roomID);
		} catch (java.lang.Exception e) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		// Check only if the updated room name is different than the one in system
		if (!name.equalsIgnoreCase(room.getRoomName()) && roomService.isRoomExistForRoomName(name, roomID)) {
			RoomAlreadyExistsFault fault = new RoomAlreadyExistsFault();
			fault.setErrorMessage("Room exist for name = " + name);
			RoomAlreadyExistsFaultException exception = new RoomAlreadyExistsFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		// Check only if the updated room extension is different than the one in system
		if (!ext.equalsIgnoreCase(room.getRoomExtNumber()) && roomService.isRoomExistForRoomExtNumber(ext, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room exist for extension = " + ext);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		room.setRoomEnabled(1); // active by default
		room.setRoomName(name);
		room.setDisplayName(name);
		room.setRoomDescription(ws_room.getDescription());
		room.setRoomExtNumber(ext);

		// room PIN
		if (ws_room.getRoomMode().getHasPIN()) {
			String roomPIN = ws_room.getRoomMode().getRoomPIN();
			if (roomPIN == null || roomPIN.equalsIgnoreCase("")) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("roomPIN not provided");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			} else {
				if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), roomPIN)) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("PIN should be a " +
							systemService.getMinPINLengthForTenant(TenantContext.getTenantId()) +
							"-" + SystemServiceImpl.PIN_MAX + " digit number");
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				room.setPinSetting("enter");
				room.setRoomPIN(roomPIN);
			}
		} else {
			room.setPinSetting("remove");
		}

		// moderator PIN
		if (ws_room.getRoomMode().getHasModeratorPIN()) {
			String moderatorPIN = ws_room.getRoomMode().getModeratorPIN();
			if (moderatorPIN == null || moderatorPIN.equalsIgnoreCase("")) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("moderatorPIN not provided");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			} else {
				if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), moderatorPIN)) {
					InvalidModeratorPINFormatFault fault = new InvalidModeratorPINFormatFault();
					fault.setErrorMessage("PIN should be a " +
							systemService.getMinPINLengthForTenant(TenantContext.getTenantId()) +
							"-" + SystemServiceImpl.PIN_MAX + " digit number");
					InvalidModeratorPINFormatFaultException exception = new InvalidModeratorPINFormatFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				room.setModeratorPinSetting("enter");
				room.setRoomModeratorPIN(moderatorPIN);
			}
		} else {
			room.setModeratorPinSetting("remove");
		}

		String groupName = ws_room.getGroupName();
		Group group = null;
		try {
			if(StringUtils.isNotBlank(groupName) && !groupName.trim().equalsIgnoreCase(room.getGroupName())) {
				group = groupService.getGroupByName(groupName);
				room.setGroupID(group.getGroupID());
			}
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Group not found for name = " + groupName);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String ownerName = ws_room.getOwnerName();
		if(!ownerName.equalsIgnoreCase(room.getOwnerName())) {
			int memberId = memberService.getMemberID(ownerName, TenantContext.getTenantId());
			if(memberId != 0) {
				room.setMemberID(memberId);
			} else {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Member not found for ownerName = " + ownerName);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
			// If personal room type ,room owner can't be changed
			if(RoomTypes.PERSONAL.getType().equals(room.getRoomType())) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Cannot change personal room's owner");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;				
			}
		}

		if (ws_room.getRoomMode().getIsLocked()) {
			room.setRoomLocked(1);
		} else {
			room.setRoomLocked(0);
		}

		roomService.updateRoom(roomID, room);

		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting updateRoom() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getGroupsRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetGroupsResponse getGroups
		(
			GetGroupsRequest getGroupsRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getGroups() of Admin API v.1.1");

		Filter_type0 filter = getGroupsRequest.getFilter();
		GroupFilter groupFilter = new GroupFilter();
		// Filter Criterion
		if (filter != null) {
			if (filter.getStart() >= 0) {
				groupFilter.setStart(filter.getStart());
			}
			validateAndSetFilterLimitParam(groupFilter, filter);
			if (filter.getDir() != null) {
				groupFilter.setDir(filter.getDir().getValue());
			}
			if (filter.getSortBy() != null) {
				// mapping between WS and SQL
				if (filter.getSortBy().equalsIgnoreCase("groupID")) {
					groupFilter.setSort("groupID");
				} else if (filter.getSortBy().equalsIgnoreCase("name")) {
					groupFilter.setSort("groupName");
				} else {
					groupFilter.setSort("");
				}
			}
			if (filter.getQuery() != null) {
				groupFilter.setQuery(filter.getQuery());
			}
		} else {
			groupFilter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
		}

		List<Group> groups = groupService.getGroups(groupFilter);
		GetGroupsResponse resp = new GetGroupsResponse();
		for (Group group : groups) {
			resp.addGroup(getWSGroupFromBOGroup(group));
		}

		int total = groupService.getCountGroups(groupFilter).intValue();
		resp.setTotal(total);

		if (logger.isDebugEnabled())
			logger.debug("Exiting getGroups() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param deleteMemberRequest
	 * @throws MemberNotFoundFaultException :
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public DeleteMemberResponse deleteMember
		(
			DeleteMemberRequest deleteMemberRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		MemberNotFoundFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering deleteMember() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int memberID = 0;
		try {
			memberID = deleteMemberRequest.getMemberID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid memberID = " + memberID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		com.vidyo.bo.Member deletingMember = memberService.getMember(TenantContext.getTenantId(), memberID);
		try {
			memberService.deleteMember(TenantContext.getTenantId(), memberID);
		} catch (AccessRestrictedException e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid memberID = " + memberID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if(deletingMember != null) {
			MemberKey memberKey = new MemberKey();
			memberKey.setTenantID(TenantContext.getTenantId());
			memberKey.setMemberID(memberID);
			memberKey.setRoleID(deletingMember.getRoleID());

			sendAdminOrOperatorDeleteMessageMQtopic(memberKey);
		}

		DeleteMemberResponse resp = new DeleteMemberResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting deleteMember() of Admin API v.1.1");

		return resp;
	}

	private void sendAdminOrOperatorDeleteMessageMQtopic(final MemberKey memberKey) {
		try {
			jmsProducerTemplate.send(adminOrOperatorDeleteMessageMQtopic.getTopicName(), new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(memberKey);
				}
			});
			logger.info("Tomcat node : " + ManagementFactory.getRuntimeMXBean().getName() + " . Admin or Operator delete message is fired");
		} catch (Exception e) {
			logger.error("Cannot send admin or operator delete: (" + memberKey.getMemberID() + ") message to MQ", e.getMessage());
		}
	}

	/**
	 * Auto generated method signature
	 *
	 * @param updateMemberRequest
	 * @throws MemberNotFoundFaultException :
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public UpdateMemberResponse updateMember
		(
			UpdateMemberRequest updateMemberRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		MemberNotFoundFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering updateMember() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		UpdateMemberResponse resp = new UpdateMemberResponse();
		Member ws_member = updateMemberRequest.getMember();

		int memberID = 0;
		memberID = updateMemberRequest.getMemberID().getEntityID();
		if (memberID <= 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Member not exist for memberID = " + memberID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		if (ws_member.getDisplayName().isEmpty()  || ws_member.getDisplayName().length() > 80) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Display Name - Must be 1 to 80 characters ");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		com.vidyo.bo.Member member = memberService.getMember(memberID);
		if(member == null) {
			MemberNotFoundFault fault = new MemberNotFoundFault();
			fault.setErrorMessage("Invalid memberID = " + memberID);
			MemberNotFoundFaultException exception = new MemberNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		int tenantId = TenantContext.getTenantId();
		if (tenantId != member.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid memberID = " + memberID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		String email = ws_member.getEmailAddress();
		if (!email.matches(ValidationUtils.MEMBER_EMAIL_REGEX) || (email.length() > 255)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Email Address = " + email);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!member.getUsername().trim().equalsIgnoreCase(ws_member.getName().trim())) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Forbidden to change name of existing account = " + member.getUsername().trim());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if(!memberService.isUserEligibleToCreateUpdateMember(user, member)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("There are not enough privileges to update a member with role name = " + member.getRoleName());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String roleName = ws_member.getRoleName().getValue();

		String groupName = ws_member.getGroupName();
		if(groupName != null && !groupName.equalsIgnoreCase(member.getGroupName())) {
			Group group = null;
			try {
				group = groupService.getGroupByName(groupName);
				member.setGroupID(group.getGroupID());
			} catch (java.lang.Exception e) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Group not found for name = " + groupName);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		int rc = 0;
		if (member.getRoleName().equalsIgnoreCase("Legacy")) { // update a Legacy Device
			// Validate only if the extension is changed
			if(!member.getRoomExtNumber().equalsIgnoreCase(ws_member.getExtension())) {
				boolean roomExists = roomService.isExtensionExistForLegacyExtNumber(ws_member.getExtension(), member.getRoomID());

				if(roomExists) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Extension already in use "
							+ ws_member.getExtension());
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				
				if (StringUtils.isBlank(ws_member.getExtension()) || ws_member.getExtension().length() > 64) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Invalid Extension - should be 64 characters or less."
							+ ws_member.getExtension());
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
			}

			member.setRoleID(6);
			member.setRoomTypeID(3);
			member.setActive(1); // active by default
			member.setUsername(ws_member.getName());
			member.setMemberName(ws_member.getDisplayName());
			if (ws_member.getDescription() != null) {
				member.setDescription(ws_member.getDescription());
			}
			member.setEmailAddress(ws_member.getEmailAddress());
			member.setRoomExtNumber(ws_member.getExtension());
			member.setProfileID(1);
			member.setLangID(1);

			rc = memberService.updateLegacy(tenantId, memberID, member);

		} else {
			if(licenseService.lineLicenseExpired()){
				logger.error("Line license expired");
				NotLicensedFault fault = new NotLicensedFault();
				fault.setErrorMessage("Line license expired");
				NotLicensedFaultException exception = new NotLicensedFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
			// Perform extension validation only if it is changed
			if(!member.getRoomExtNumber().equalsIgnoreCase(ws_member.getExtension())) {
				boolean isValidExt = RoomUtils.isValidExtensionByPrefix(tenantService, ws_member.getExtension());

				if (!isValidExt) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Invalid Extension - Extension does not start with Tenant Prefix "
							+ ws_member.getExtension());
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}

				// Alpha numeric extensions needs to supported for API customers - https://jira.vidyo.com/browse/VPTL-9351
				if (RoomUtils.isNotValidAlphaNumericExtension(ws_member.getExtension())) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Invalid Extension - Invalid format or Length should be maximum 64 characters"
							+ ws_member.getExtension());
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}

				boolean roomExists = roomService.isRoomExistForRoomExtNumber(ws_member.getExtension(), member.getRoomID());

				if(roomExists) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Extension already in use "
							+ ws_member.getExtension());
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}

				int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(systemService, ws_member.getExtension());
				if (extExists > 0){
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Extension matches Schedule Room prefix. Please choose a different extension and try again. Extension = " + ws_member.getExtension());
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
			}
			if (ws_member.getPhone1() != null && StringUtils.isNotBlank(ws_member.getPhone1()) && ws_member.getPhone1().length() > 64) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Phone1 - must be 64 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getPhone2() != null && StringUtils.isNotBlank(ws_member.getPhone2()) && ws_member.getPhone2().length() > 64) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Phone2 - must be 64 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getPhone3() != null && StringUtils.isNotBlank(ws_member.getPhone3()) && ws_member.getPhone3().length() > 64) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Phone3 - must be 64 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getDepartment() != null && StringUtils.isNotBlank(ws_member.getDepartment()) && ws_member.getDepartment().length() > 128) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Department - must be 128 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getTitle() != null && StringUtils.isNotBlank(ws_member.getTitle()) && ws_member.getTitle().length() > 128) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Title - must be 128 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getLocation() != null && StringUtils.isNotBlank(ws_member.getLocation()) && ws_member.getLocation().length() > 128) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Location - must be 128 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getInstantMessagerID() != null && StringUtils.isNotBlank(ws_member.getInstantMessagerID()) && ws_member.getInstantMessagerID().length() > 128) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid InstantMessagerID - must be 128 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			int oldActive = member.getActive();
			// Check if the enabled flag is specified 
			if(ws_member.isEnabledSpecified()) {
				member.setActive(ws_member.getEnabled() ? 1 : 0); // If specified, use the falg to enable or disable
			} else {
				member.setActive(oldActive); // if not specified, set the old flag
			}
			member.setRoomTypeID(1); // personal room
			member.setMemberName(ws_member.getDisplayName());
			if (ws_member.getDescription() != null) {
				member.setDescription(ws_member.getDescription());
			}
			member.setEmailAddress(ws_member.getEmailAddress());
			member.setRoomExtNumber(ws_member.getExtension());

			if (ws_member.getPhone1() != null && StringUtils.isNotBlank(ws_member.getPhone1())){
				member.setPhone1(ws_member.getPhone1());
			}

			if (ws_member.getPhone2() != null && StringUtils.isNotBlank(ws_member.getPhone2())){
				member.setPhone2(ws_member.getPhone2());
			}

			if (ws_member.getPhone3() != null && StringUtils.isNotBlank(ws_member.getPhone3())){
				member.setPhone3(ws_member.getPhone3());
			}

			if (ws_member.getDepartment() != null && StringUtils.isNotBlank(ws_member.getDepartment())){
				member.setDepartment(ws_member.getDepartment());
			}

			if (ws_member.getTitle() != null && StringUtils.isNotBlank(ws_member.getTitle())){
				member.setTitle(ws_member.getTitle());
			}

			if (ws_member.getLocation() != null && StringUtils.isNotBlank(ws_member.getLocation())){
				member.setLocation(ws_member.getLocation());
			}

			if (ws_member.getInstantMessagerID() != null && StringUtils.isNotBlank(ws_member.getInstantMessagerID())){
				member.setInstantMessagerID(ws_member.getInstantMessagerID());
			}

			String langCode = ws_member.getLanguage().getValue();
			if (langCode.equalsIgnoreCase(Language_type0._en)) {
				member.setLangID(1);
			} else if (langCode.equalsIgnoreCase(Language_type0._fr)) {
				member.setLangID(2);
			} else if (langCode.equalsIgnoreCase(Language_type0._ja)) {
				member.setLangID(3);
			} else if (langCode.equalsIgnoreCase(Language_type0._zh_CN)) {
				member.setLangID(4);
			} else if (langCode.equalsIgnoreCase(Language_type0._es)) {
				member.setLangID(5);
			} else if (langCode.equalsIgnoreCase(Language_type0._it)) {
				member.setLangID(6);
			} else if (langCode.equalsIgnoreCase(Language_type0._de)) {
				member.setLangID(7);
			} else if (langCode.equalsIgnoreCase(Language_type0._ko)) {
				member.setLangID(8);
			} else if (langCode.equalsIgnoreCase(Language_type0._pt)) {
				member.setLangID(9);
			} else if (langCode.equalsIgnoreCase(Language_type0._fi)) {
				member.setLangID(11);
			} else if (langCode.equalsIgnoreCase(Language_type0._pl)) {
				member.setLangID(12);
			} else if (langCode.equalsIgnoreCase(Language_type0._zh_TW)) {
				member.setLangID(13);
			} else if (langCode.equalsIgnoreCase(Language_type0._th)) {
				member.setLangID(14);
			} else if (langCode.equalsIgnoreCase(Language_type0._ru)) {
				member.setLangID(15);
			} else if (langCode.equalsIgnoreCase(Language_type0._tr)) {
				member.setLangID(16);
			}

			try {
				String pswd = ws_member.getPassword();
				if(pswd != null && !pswd.isEmpty()) {
					String encodedNewPassword = PasswordHash.createHash(pswd);
					if(!encodedNewPassword.equals(member.getPassword()) &&
							!memberService.isValidMemberPassword(ws_member.getPassword(), memberID)) {
						InvalidArgumentFault fault = new InvalidArgumentFault();
						fault.setErrorMessage("Password does not meet requirements");
						InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
						exception.setFaultMessage(fault);
						throw exception;
					}
					member.setPassword(encodedNewPassword);
				}
			} catch (InvalidArgumentFaultException iafEx) {
				throw iafEx;
			} catch (java.lang.Exception ignored) {
				logger.error("Exception while encoding the member Password");

				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("Failed while encoding the member Password");
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if(!member.getRoleName().equalsIgnoreCase(roleName)) {
				int oldRole = member.getRoleID();
				MemberRoles role = null;
				if(roleName.equalsIgnoreCase("Panorama")) {
					roleName = MemberRoleEnum.VIDYO_PANORAMA.getMemberRole();
				}
				role = memberService.getMemberRoleByName(roleName);
				if(role == null) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Role not found for name = " + roleName);
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				member.setRoleID(role.getRoleID());
				member.setRoleName(role.getRoleName());
				if(!memberService.isUserEligibleToCreateUpdateMember(user, member)) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("There are not enough privileges to update a member's role to " + roleName);
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				int newRole = member.getRoleID();

				if(newRole == MemberRoleEnum.EXECUTIVE.getMemberRoleID()) {
					long allowedExecutives = this.licenseService.getAllowedExecutives();
					final String msg = "Cannot update member, executive license exceeded";
					checkAllowedSeatsExececutivesPanoramas(oldRole, newRole, oldActive, member.getActive(), allowedExecutives, msg);
				} else if(newRole == MemberRoleEnum.VIDYO_PANORAMA.getMemberRoleID()) {
					long allowedPanoramas = this.licenseService.getAllowedPanoramas();
					final String msg = "Cannot update member, panorama license exceeded";
					checkAllowedSeatsExececutivesPanoramas(oldRole, newRole, oldActive, member.getActive(), allowedPanoramas, msg);
				} else if(newRole == MemberRoleEnum.ADMIN.getMemberRoleID() || newRole == MemberRoleEnum.OPERATOR.getMemberRoleID()
						|| newRole == MemberRoleEnum.NORMAL.getMemberRoleID()) {
					long allowedSeats = this.licenseService.getAllowedSeats();
					final String msg = "Cannot update member, seat license exceeded";
					checkAllowedSeatsExececutivesPanoramas(oldRole, newRole, oldActive, member.getActive(), allowedSeats, msg);
				}
			}

			String proxyName = ws_member.getProxyName();
			if (proxyName != null && !(proxyName.trim().equalsIgnoreCase("") || proxyName.trim().equalsIgnoreCase("No Proxy"))) {
				Proxy proxy;
				try {
					proxy = memberService.getProxyByName(proxyName);
				} catch (java.lang.Exception e) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Proxy not found for name = " + proxyName);
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				if (proxy != null) {
					member.setProxyID(proxy.getProxyID());
				} else {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Proxy not found for name = " + proxyName);
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
			} else {
				member.setProxyID(0);
			}

			// Validate the Location Tag
			String locationTag = ws_member.getLocationTag();
			if (locationTag == null || locationTag.equalsIgnoreCase("")) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Location Tag " + locationTag);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if(!member.getLocationTag().equalsIgnoreCase(locationTag)) {
				int locationID = serviceService.getLocationIdByLocationTag(locationTag);
				if (locationID == 0) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Invalid Location Tag " + locationTag);
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				member.setLocationID(locationID);
			}

			member.setNeoRoomPermanentPairingDeviceUser(ws_member.getNeoRoomPermanentPairingDeviceUser());

			try {
				rc = memberService.updateMember(tenantId, memberID, member);
			} catch (AccessRestrictedException e) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Access restricted to memberID = " + memberID);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		if(rc > 0) {
			resp.setOK(OK_type0.OK);
		} else {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Failed to update memberID = " + memberID);
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting updateMember() of Admin API v.1.1");

		return resp;
	}

	private void checkAllowedSeatsExececutivesPanoramas(int oldRole, int newRole, int oldActive, int newActive, long allowedSeats,
			String msg) throws NotLicensedFaultException {
		boolean rolesAreEqual = false;

		if(oldRole == newRole ||
				(oldRole == MemberRoleEnum.ADMIN.getMemberRoleID() || oldRole == MemberRoleEnum.OPERATOR.getMemberRoleID()
					|| oldRole == MemberRoleEnum.NORMAL.getMemberRoleID())
				&&
				(newRole == MemberRoleEnum.ADMIN.getMemberRoleID() || newRole == MemberRoleEnum.OPERATOR.getMemberRoleID()
					|| newRole == MemberRoleEnum.NORMAL.getMemberRoleID())) {
			rolesAreEqual = true;
		}

		if (oldActive == 0 && newActive == 1 && allowedSeats < 1 ||
				oldActive == 1 && newActive == 1 && !rolesAreEqual && allowedSeats < 1) {
			logger.error(msg);

			NotLicensedFault fault = new NotLicensedFault();
			fault.setErrorMessage(msg);
			NotLicensedFaultException exception = new NotLicensedFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
        }
	}

	/**
	 * Auto generated method signature
	 *
	 * @param deleteRoomRequest
	 * @throws NotLicensedFaultException :
	 * @throws RoomNotFoundFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public DeleteRoomResponse deleteRoom
		(
			DeleteRoomRequest deleteRoomRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		RoomNotFoundFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering deleteRoom() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = deleteRoomRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("Room not found for roomID = " + roomID);
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getRoomType().equalsIgnoreCase("Personal")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Cannot delete Personal type of Room for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		roomService.deleteRoom(room.getRoomID());
		DeleteRoomResponse resp = new DeleteRoomResponse();

		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting deleteRoom() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param unmuteAudioRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public UnmuteAudioResponse unmuteAudio
		(
			UnmuteAudioRequest unmuteAudioRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering unmuteAudio() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		UnmuteAudioResponse resp = new UnmuteAudioResponse();

		int roomID = unmuteAudioRequest.getConferenceID().getEntityID();
		int endpointID = unmuteAudioRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			conferenceService.unmuteAudio(endpointGUID);
		} catch (java.lang.Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting unmuteAudio() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param inviteToConferenceRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public InviteToConferenceResponse inviteToConference
		(
			InviteToConferenceRequest inviteToConferenceRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering inviteToConference() of Admin API v.1.1");

		User user = userService.getLoginUser();

		Invite invite = new Invite();
		invite.setFromMemberID(user.getMemberID());

		int confID = inviteToConferenceRequest.getConferenceID().getEntityID();
		invite.setFromRoomID(confID);

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(confID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Conference for conferenceID = " + confID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		int toEntityID = 0;
		if (inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0().getEntityID() != null) {
			toEntityID = inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0().getEntityID().getEntityID();
		}
		
        if(inviteToConferenceRequest.isCallFromIdentifierSpecified()) {
    			invite.setCallFromIdentifier(inviteToConferenceRequest.getCallFromIdentifier());
        }

		Room toRoom;
		int status = 1;
		boolean isLegacy = false;
		try {
			toRoom = roomService.getRoom(toEntityID);
			invite.setToMemberID(toRoom.getMemberID());

			com.vidyo.bo.Member invitee = memberService.getMember(toRoom.getMemberID());
			if (invitee.getRoleID() == 6) {
				isLegacy = true;
			}

			int toEndpointID;
			if (!isLegacy) {
				toEndpointID = conferenceService.getEndpointIDForMemberID(toRoom.getMemberID());
				invite.setToEndpointID(toEndpointID);
				// check if status of Endpoint is Online
				status = conferenceService.getEndpointStatus(toEndpointID);
			}
		} catch (java.lang.Exception e) {
			invite.setToMemberID(0);
			invite.setToEndpointID(0);
		}
		// VPTL-8551 - Remove the endpoint status check for non-legacy endpoints as VM knows the status better than the portal database
		/*if (!isLegacy && status != 1) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Status of invited member is not Online.");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}*/
		// Let VM handle the call success/failure based on the endpoint status

		String search = "";
		if (inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0().getInvite() != null) {
			search = inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0().getInvite();
		}
		
		invite.setSearch(search);

		try {
			conferenceService.inviteToConference(invite);
		} catch (java.lang.Exception e) {
			// Handling all exceptions in the generic catch block, as
			// GeneralFault is thrown for all cases
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		InviteToConferenceResponse resp = new InviteToConferenceResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting inviteToConference() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param cancelOutboundCallRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public CancelOutboundCallResponse cancelOutboundCall(CancelOutboundCallRequest cancelOutboundCallRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering cancelOutboundCall() of Admin API v.1.1");
		}

		User user = userService.getLoginUser();

		Invite invite = new Invite();
		invite.setFromMemberID(user.getMemberID());

		int confID = cancelOutboundCallRequest.getConferenceID().getEntityID();
		invite.setFromRoomID(confID);

		Room room;
		try {
			room = roomService.getRoom(confID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Conference for conferenceID = " + confID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		int toEntityID = cancelOutboundCallRequest.getEntityID().getEntityID();


		Room toRoom;
		int status = 3;
		boolean isLegacy = false;
		try {
			toRoom = roomService.getRoom(toEntityID);
			invite.setToMemberID(toRoom.getMemberID());

			com.vidyo.bo.Member invitee = memberService.getMember(toRoom.getMemberID());
			if (invitee.getRoleID() == 6) {
				isLegacy = true;
			}

			int toEndpointID;
			if (!isLegacy) {
				toEndpointID = conferenceService.getEndpointIDForMemberID(toRoom.getMemberID());
				invite.setToEndpointID(toEndpointID);
				// check if status of Endpoint is Online or Ringing
				status = conferenceService.getEndpointStatus(toEndpointID);
			}
		} catch (java.lang.Exception e) {
			invite.setToMemberID(0);
			invite.setToEndpointID(0);
		}
		if (!isLegacy && status != 3) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Status of cancelling member is not Ringing.");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			conferenceService.cancelInviteToConference(invite);
		} catch (java.lang.Exception e) {
			// Handling all exceptions in the generic catch block, as
			// GeneralFault is thrown for all cases
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		CancelOutboundCallResponse resp = new CancelOutboundCallResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled()) {
			logger.debug("Exiting cancelOutboundCall() of Admin API v.1.1");
		}

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param deleteGroupRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GroupNotFoundFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public DeleteGroupResponse deleteGroup
		(
			DeleteGroupRequest deleteGroupRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		GroupNotFoundFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering deleteGroup() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int groupID = 0;
		try {
			groupID = deleteGroupRequest.getGroupID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid groupID = " + groupID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Group defaultGroup = this.groupService.getDefaultGroup();
		if (defaultGroup != null && groupID == defaultGroup.getGroupID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Default group cannot be deleted");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Group group;
		try {
			group = groupService.getGroup(groupID);
		} catch (java.lang.Exception e) {
			GroupNotFoundFault fault = new GroupNotFoundFault();
			fault.setErrorMessage("Group not found for groupID = " + groupID);
			GroupNotFoundFaultException exception = new GroupNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != group.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Group for groupID = " + groupID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		groupService.deleteGroup(group.getGroupID());

		DeleteGroupResponse resp = new DeleteGroupResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting deleteGroup() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param createRoomURLRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public CreateRoomURLResponse createRoomURL
		(
			CreateRoomURLRequest createRoomURLRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering createRoomURL() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		CreateRoomURLResponse resp = new CreateRoomURLResponse();

		int roomID = 0;
		try {
			roomID = createRoomURLRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		roomService.generateRoomKey(room);

		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting createRoomURL() of Admin API v.1.1");

		return resp;
	}

	@Override
	public RemovePresenterResponse removePresenter(RemovePresenterRequest removePresenterRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled())
			logger.debug("Entering removePresenter() of Admin API v.1.1");

		int roomEntityID = removePresenterRequest.getConferenceID().getEntityID();

		String moderatorPIN = "noModeratorPIN";

		int endpointID = Integer.valueOf(removePresenterRequest.getParticipantID().getEntityID());

		LectureModeParticipantControlRequest serviceRequest = new LectureModeParticipantControlRequest();
		serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
		serviceRequest.setTenantID(TenantContext.getTenantId());
		serviceRequest.setRoomID(Integer.valueOf(roomEntityID));
		serviceRequest.setModeratorPIN(moderatorPIN);
		serviceRequest.setEndpointID(endpointID);

		LectureModeControlResponse serviceResponse = lectureModeService.removePresenter(serviceRequest);

		int status =  serviceResponse.getStatus();
		if(status == ConferenceControlResponse.INVALID_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceId");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.PARTICIPANT_NOT_IN_CONFERENCE) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.PARTICIPANT_IS_NOT_PRESENTING) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not prsenting in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.LECTURE_MODE_NOT_STARTED) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Lecture mode is not on for conference");
			GeneralFaultException exception = new GeneralFaultException("Lecture mode is not on for conference");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Server internal error");
			GeneralFaultException exception = new GeneralFaultException("Server internal error");
			exception.setFaultMessage(fault);
			throw exception;
		}

		RemovePresenterResponse response = new RemovePresenterResponse();
		response.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting removePresenter() of Admin API v.1.1");

		return response;

	}

	/**
	 * Auto generated method signature
	 *
	 * @param updateGroupRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GroupNotFoundFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public UpdateGroupResponse updateGroup
		(
			UpdateGroupRequest updateGroupRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		GroupNotFoundFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering updateGroup() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		com.vidyo.portal.admin.v1_1.Group ws_group = updateGroupRequest.getGroup();

		int groupID;
		groupID = updateGroupRequest.getGroupID().getEntityID();
		if (groupID <= 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Group not exist for groupID = " + groupID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		//Invalid Group Names
		if(StringUtils.isBlank(ws_group.getName()) || !ws_group.getName().matches(ValidationUtils.GROUP_NAME_REGEX) || ws_group.getName().length() > 80) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Group Name");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;			
		}
		
		if (!NumberUtils.isNumber(ws_group.getRoomMaxUsers()) || Integer.valueOf(ws_group.getRoomMaxUsers()) < 2) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Max Participants");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!NumberUtils.isNumber(ws_group.getUserMaxBandWidthIn())
				|| (Integer.valueOf(ws_group.getUserMaxBandWidthIn()) < 1
						|| Integer.valueOf(ws_group.getUserMaxBandWidthIn()) > 100000)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Max BandWidth In");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!NumberUtils.isNumber(ws_group.getUserMaxBandWidthOut())
				|| (Integer.valueOf(ws_group.getUserMaxBandWidthOut()) < 1
						|| Integer.valueOf(ws_group.getUserMaxBandWidthOut()) > 100000)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Max BandWidth Out");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (groupService.isGroupExistForGroupName(ws_group.getName(), groupID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Group exist for name = " + ws_group.getName());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		Group group = null;
		try {
			group = groupService.getGroup(groupID);
		} catch (java.lang.Exception e) {
			GroupNotFoundFault fault = new GroupNotFoundFault();
			fault.setErrorMessage("Group not exist for groupID = " + groupID);
			GroupNotFoundFaultException exception = new GroupNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != group.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Group for groupID = " + groupID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		group.setDefaultFlag(1);
		group.setGroupName(ws_group.getName());
		group.setGroupDescription(ws_group.getDescription());
		group.setRoomMaxUsers(Integer.valueOf(ws_group.getRoomMaxUsers()));
		group.setUserMaxBandWidthIn(Integer.valueOf(ws_group.getUserMaxBandWidthIn()));
		group.setUserMaxBandWidthOut(Integer.valueOf(ws_group.getUserMaxBandWidthOut()));

		group.setAllowRecording((ws_group.getAllowRecording() ? 1 : 0));

		groupService.updateGroup(groupID, group);

		UpdateGroupResponse resp = new UpdateGroupResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting updateGroup() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getMemberRequest
	 * @throws MemberNotFoundFaultException :
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetMemberResponse getMember
		(
			GetMemberRequest getMemberRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		MemberNotFoundFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getMember() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		GetMemberResponse resp = new GetMemberResponse();

		int memberID = 0;
		try {
			memberID = getMemberRequest.getMemberID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid memberID = " + memberID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		com.vidyo.bo.Member member = memberService.getMember(memberID);
		if(member == null) {
			MemberNotFoundFault fault = new MemberNotFoundFault();
			fault.setErrorMessage("Invalid memberID = " + memberID);
			MemberNotFoundFaultException exception = new MemberNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != member.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid memberID = " + memberID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Configuration userImageConf = systemService.getConfiguration("USER_IMAGE");
		TenantConfiguration tenantConfiguration = this.tenantService
				.getTenantConfiguration(TenantContext.getTenantId());
		Member ws_member = getWSMemberFromBOMember(member, userImageConf, tenantConfiguration);
		resp.setMember(ws_member);

		if (logger.isDebugEnabled())
			logger.debug("Exiting getMember() of Admin API v.1.1");

		return resp;
	}

	@Override
	public StopLectureModeResponse stopLectureMode(StopLectureModeRequest stopLectureModeRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled())
			logger.debug("Entering stopLectureMode() of Admin API v.1.1");

		int roomEntityID = stopLectureModeRequest.getConferenceID().getEntityID();

		String moderatorPIN = "noModeratorPIN";

		LectureModeControlRequest serviceRequest = new LectureModeControlRequest();
		serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
		serviceRequest.setTenantID(TenantContext.getTenantId());
		serviceRequest.setRoomID(roomEntityID);
		serviceRequest.setModeratorPIN(moderatorPIN);

		LectureModeControlResponse serviceResponse = lectureModeService.stopLectureMode(serviceRequest);

		int status =  serviceResponse.getStatus();
		if (status == ConferenceControlResponse.INVALID_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceId");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.LECTURE_MODE_ALREADY_STOPPED) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Lecture mode is already off for conference");
			GeneralFaultException exception = new GeneralFaultException("Lecture mode is already off for conference");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Server internal error");
			GeneralFaultException exception = new GeneralFaultException("Server internal error");
			exception.setFaultMessage(fault);
			throw exception;
		}

		StopLectureModeResponse response = new StopLectureModeResponse();
		response.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting stopLectureMode() of Admin API v.1.1");

		return response;

	}

	/**
	 * Auto generated method signature
	 *
	 * @param addMemberRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws MemberAlreadyExistsFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public AddMemberResponse addMember
		(
			AddMemberRequest addMemberRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		MemberAlreadyExistsFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering addMember() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		Member ws_member = addMemberRequest.getMember();

		// Validate unique member name
		String name = ws_member.getName();
		if (this.memberService.isMemberExistForUserName(name, 0)) {
			MemberAlreadyExistsFault fault = new MemberAlreadyExistsFault();
			fault.setErrorMessage("Member exist for name = " + name);
			MemberAlreadyExistsFaultException exception = new MemberAlreadyExistsFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String displayName = ws_member.getDisplayName();

		// Validate email address
		String email = ws_member.getEmailAddress();
		if (!email.matches(ValidationUtils.MEMBER_EMAIL_REGEX) || (email.length() > 255)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Email Address = " + email);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		// Validate the Location Tag
		int locationID;
		String locationTag = ws_member.getLocationTag();
		if (locationTag == null || locationTag.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Location Tag = " + locationTag);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		locationID = this.serviceService.getLocationIdByLocationTag(locationTag);
		if (locationID == 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Location Tag = " + locationTag);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		int memberID;
		String roleName = ws_member.getRoleName().getValue();

		String groupName = ws_member.getGroupName();
		Group group;
		try {
			group = this.groupService.getGroupByName(groupName);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Group not found for name = " + groupName);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String ext = ws_member.getExtension();
		int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(systemService, ext);
		if (extExists > 0){
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Extension matches Schedule Room prefix. Please choose a different extension and try again. Extension = " + ext);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (this.roomService.isRoomExistForRoomName(ws_member.getName(), 0)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room exist for name = "+ws_member.getName());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
	       }

		if (roleName.equalsIgnoreCase("Legacy")) { // create a Legacy Device
			if (this.roomService.isExtensionExistForLegacyExtNumber(ext, 0)) {
				MemberAlreadyExistsFault fault = new MemberAlreadyExistsFault();
				fault.setErrorMessage("Room exist for extension = " + ext);
				MemberAlreadyExistsFaultException exception = new MemberAlreadyExistsFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (StringUtils.isBlank(ext) 
					|| ext.length() > 64) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Extension - must be 64 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}


			if(name.length() > 80 || !MemberServiceImpl.isValidLegacyUsername(name)) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Name = " + name);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if(StringUtils.isBlank(displayName) || displayName.length() > 80 ) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Display Name = " + displayName);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			com.vidyo.bo.Member member = new com.vidyo.bo.Member();
			// Legacy Device
			member.setRoleID(6);
			member.setRoleName(roleName);
			member.setRoomTypeID(3);
			member.setActive(1); // active by default
			member.setAllowedToParticipate(1); // allowed by default
			member.setUsername(ws_member.getName());
			member.setPassword("");
			member.setMemberName(ws_member.getDisplayName());
			if (ws_member.getDescription() != null) {
				member.setDescription(ws_member.getDescription());
			}
			member.setEmailAddress(ws_member.getEmailAddress());
			member.setRoomExtNumber(ws_member.getExtension());
			member.setGroupID(group.getGroupID());
			member.setProfileID(1);
			member.setLangID(1);
			member.setLocation("");
			member.setMemberCreated((int) (new Date().getTime() * .001));

			if(!memberService.isUserEligibleToCreateUpdateMember(user, member)) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("There are not enough privileges to create a member with role name = " + roleName);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			memberID = this.memberService.insertMember(member);

			// create a Personal Room for the member
			Room room = new Room();
			room.setMemberID(memberID);
			room.setRoomDescription(member.getUsername() + "-Legacy Device");
			room.setGroupID(member.getGroupID());
			room.setRoomEnabled(member.getActive());
			room.setRoomExtNumber(member.getRoomExtNumber());
			room.setRoomName(member.getUsername());
			room.setRoomTypeID(member.getRoomTypeID());
			roomService.insertRoom(room);
		} else {
			// Validate unique extension
			boolean isValid = RoomUtils.isValidExtensionByPrefix(tenantService, ext);

			if (!isValid) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Extension - Extension does not start with Tenant Prefix");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			// Alpha numeric extensions needs to supported for API customers - https://jira.vidyo.com/browse/VPTL-9351
			if (RoomUtils.isNotValidAlphaNumericExtension(ws_member.getExtension())) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Extension - Invalid format or Length should be maximum 64 characters - " + ws_member.getExtension());
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (this.roomService.isRoomExistForRoomExtNumber(ext, 0)) {
				MemberAlreadyExistsFault fault = new MemberAlreadyExistsFault();
				fault.setErrorMessage("Room exist for extension = " + ext);
				MemberAlreadyExistsFaultException exception = new MemberAlreadyExistsFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if(name.length() > 80 || !name.matches(ValidationUtils.USERNAME_REGEX)) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Name = " + name);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if(StringUtils.isBlank(displayName) || displayName.length() > 80 ) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Display Name = " + displayName);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}


			if (ws_member.getPhone1() != null && StringUtils.isNotBlank(ws_member.getPhone1()) && ws_member.getPhone1().length() > 64) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Phone1 - must be 64 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getPhone2() != null && StringUtils.isNotBlank(ws_member.getPhone2()) && ws_member.getPhone2().length() > 64) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Phone2 - must be 64 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getPhone3() != null && StringUtils.isNotBlank(ws_member.getPhone3()) && ws_member.getPhone3().length() > 64) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Phone3 - must be 64 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getDepartment() != null && StringUtils.isNotBlank(ws_member.getDepartment()) && ws_member.getDepartment().length() > 128) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Department - must be 128 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getTitle() != null && StringUtils.isNotBlank(ws_member.getTitle()) && ws_member.getTitle().length() > 128) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Title - must be 128 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getLocation() != null && StringUtils.isNotBlank(ws_member.getLocation()) && ws_member.getLocation().length() > 128) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid Location - must be 128 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			if (ws_member.getInstantMessagerID() != null && StringUtils.isNotBlank(ws_member.getInstantMessagerID()) && ws_member.getInstantMessagerID().length() > 128) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid InstantMessagerID - must be 128 characters or less");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			com.vidyo.bo.Member member = new com.vidyo.bo.Member();
			// Check if the enabled flag is specified 
			if(ws_member.isEnabledSpecified()) {
				member.setActive(ws_member.getEnabled() ? 1 : 0);
			} else {
				member.setActive(1); // active by default if not specified
			}
			member.setAllowedToParticipate(1); // allowed by default
			member.setRoomTypeID(1); // personal room
			member.setUsername(ws_member.getName());
			member.setMemberName(ws_member.getDisplayName());
			if (ws_member.getDescription() != null) {
				member.setDescription(ws_member.getDescription());
			}
			member.setEmailAddress(ws_member.getEmailAddress());
			member.setRoomExtNumber(ws_member.getExtension());

			if (ws_member.getPhone1() != null && StringUtils.isNotBlank(ws_member.getPhone1())){
				member.setPhone1(ws_member.getPhone1());
			}

			if (ws_member.getPhone2() != null && StringUtils.isNotBlank(ws_member.getPhone2())){
				member.setPhone2(ws_member.getPhone2());
			}

			if (ws_member.getPhone3() != null && StringUtils.isNotBlank(ws_member.getPhone3())){
				member.setPhone3(ws_member.getPhone3());
			}

			if (ws_member.getDepartment() != null && StringUtils.isNotBlank(ws_member.getDepartment())){
				member.setDepartment(ws_member.getDepartment());
			}

			if (ws_member.getTitle() != null && StringUtils.isNotBlank(ws_member.getTitle())){
				member.setTitle(ws_member.getTitle());
			}

			if (ws_member.getLocation() != null && StringUtils.isNotBlank(ws_member.getLocation())){
				member.setLocation(ws_member.getLocation());
			}

			if (ws_member.getInstantMessagerID() != null && StringUtils.isNotBlank(ws_member.getInstantMessagerID())){
				member.setInstantMessagerID(ws_member.getInstantMessagerID());
			}

			String langCode = ws_member.getLanguage().getValue();
			if (langCode.equalsIgnoreCase(Language_type0._en)) {
				member.setLangID(1);
			} else if (langCode.equalsIgnoreCase(Language_type0._fr)) {
				member.setLangID(2);
			} else if (langCode.equalsIgnoreCase(Language_type0._ja)) {
				member.setLangID(3);
			} else if (langCode.equalsIgnoreCase(Language_type0._zh_CN)) {
				member.setLangID(4);
			} else if (langCode.equalsIgnoreCase(Language_type0._es)) {
				member.setLangID(5);
			} else if (langCode.equalsIgnoreCase(Language_type0._it)) {
				member.setLangID(6);
			} else if (langCode.equalsIgnoreCase(Language_type0._de)) {
				member.setLangID(7);
			} else if (langCode.equalsIgnoreCase(Language_type0._ko)) {
				member.setLangID(8);
			} else if (langCode.equalsIgnoreCase(Language_type0._pt)) {
				member.setLangID(9);
			} else if (langCode.equalsIgnoreCase(Language_type0._fi)) {
				member.setLangID(11);
			} else if (langCode.equalsIgnoreCase(Language_type0._pl)) {
				member.setLangID(12);
			} else if (langCode.equalsIgnoreCase(Language_type0._zh_TW)) {
				member.setLangID(13);
			} else if (langCode.equalsIgnoreCase(Language_type0._th)) {
				member.setLangID(14);
			} else if (langCode.equalsIgnoreCase(Language_type0._ru)) {
				member.setLangID(15);
			} else if (langCode.equalsIgnoreCase(Language_type0._tr)) {
				member.setLangID(16);
			}

			member.setMemberCreated((int) (new Date().getTime() * .001));
			try {
				String pswd = ws_member.getPassword();
				if(pswd == null) {
					pswd = "";
				}
				// Empty passwords are allowed as the WSDL definition allows nillable password and it is optional
				if(!memberService.isValidMemberPassword(pswd, 0)) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Password does not meet requirements");
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}

				member.setPassword(PasswordHash.createHash(pswd));
			} catch (InvalidArgumentFaultException iafEx) {
				throw iafEx;
			} catch (java.lang.Exception ignored) {
				logger.error("Exception while encoding the member Password");

				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("Failed while encoding the member Password");
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			member.setGroupID(group.getGroupID());

			MemberRoles role = null;

			if(roleName.equalsIgnoreCase("Panorama")) {
				roleName = "VidyoPanorama";
			}
			role = memberService.getMemberRoleByName(roleName);
			if(role == null) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Role not found for name = " + roleName);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
			member.setRoleID(role.getRoleID());
			member.setRoleName(role.getRoleName());

	        if(licenseService.lineLicenseExpired()){
	            logger.error("Line license expired");

	            NotLicensedFault fault = new NotLicensedFault();
				fault.setErrorMessage("Line license expired");
				NotLicensedFaultException exception = new NotLicensedFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
	        }

			if("Executive".equalsIgnoreCase(roleName)) {
		        com.vidyo.bo.MemberFilter filter = new com.vidyo.bo.MemberFilter();
		        filter.setLimit(Integer.MAX_VALUE);
		        filter.setType("Executive");
		        Long numExecutive = memberService.getCountMembers(filter);

		        if (numExecutive >= memberService.getLicensedExecutive()) {
		            logger.error("Cannot add member, executive license exceeded");

		            NotLicensedFault fault = new NotLicensedFault();
					fault.setErrorMessage("Cannot add member, executive license exceeded");
					NotLicensedFaultException exception = new NotLicensedFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
		        }
	        } else if("VidyoPanorama".equalsIgnoreCase(roleName)){
	        	com.vidyo.bo.MemberFilter filter = new com.vidyo.bo.MemberFilter();
	            filter.setLimit(Integer.MAX_VALUE);
	            filter.setType("VidyoPanorama");
	            Long numPanorama = memberService.getCountMembers(filter);

	            if (numPanorama >= memberService.getLicensedPanorama()) {
	                logger.error("Cannot add member, panorama license exceeded");

	                NotLicensedFault fault = new NotLicensedFault();
					fault.setErrorMessage("Cannot add member, panorama license exceeded");
					NotLicensedFaultException exception = new NotLicensedFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
	            }
	        } else if("Admin".equalsIgnoreCase(roleName) || "Operator".equalsIgnoreCase(roleName)
	                || "Normal".equalsIgnoreCase(roleName)) {
	        	long allowedSeats = licenseService.getAllowedSeats();

	            if (allowedSeats < 1) {
	                logger.error("Cannot add member, seat license exceeded");

	                NotLicensedFault fault = new NotLicensedFault();
					fault.setErrorMessage("Cannot add member, seat license exceeded");
					NotLicensedFaultException exception = new NotLicensedFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
	            }
	        }

			String proxyName = ws_member.getProxyName();
			if (proxyName != null && !(proxyName.trim().equalsIgnoreCase("") || proxyName.trim().equalsIgnoreCase("No Proxy"))) {
				Proxy proxy;
				try {
					proxy = memberService.getProxyByName(proxyName);
				} catch (java.lang.Exception e) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Proxy not found for name = " + proxyName);
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				if (proxy != null) {
					member.setProxyID(proxy.getProxyID());
				} else {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Proxy not found for name = " + proxyName);
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
			} else {
				member.setProxyID(0);
			}

			member.setLocationID(locationID);

			if(!memberService.isUserEligibleToCreateUpdateMember(user, member)) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("There are not enough privileges to create a member with role name = " + roleName);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}

			member.setNeoRoomPermanentPairingDeviceUser(ws_member.getNeoRoomPermanentPairingDeviceUser());

			memberID = memberService.insertMember(member);

			// create a Personal Room for the member
			Room room = new Room();
			room.setMemberID(memberID);
			room.setRoomDescription(member.getUsername() + "-Personal Room");
			room.setGroupID(member.getGroupID());
			room.setRoomEnabled(member.getActive());
			room.setRoomExtNumber(member.getRoomExtNumber());
			room.setRoomName(member.getUsername());
			room.setRoomTypeID(member.getRoomTypeID());
			room.setDisplayName(member.getMemberName());
			if (VendorUtils.isRoomsLockedByDefault()) {
				room.setRoomLocked(1);
			}
			roomService.insertRoom(room);
		}

		AddMemberResponse resp = new AddMemberResponse();
		resp.setOK(OK_type0.OK);

		if(addMemberRequest.getReturnObjectInResponse()!=null && "true"==addMemberRequest.getReturnObjectInResponse().getValue()){
			if (logger.isDebugEnabled()){
				logger.debug("Return Object is requested , so passing object back to responese.Member id is  "+memberID);
			}
			EntityID entityId=new EntityID();
			entityId.setEntityID(memberID);
			ws_member.setMemberID(entityId);
			resp.setMember(ws_member);
		}
		if (logger.isDebugEnabled())
			logger.debug("Exiting addMember() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param addGroupRequest
	 * @throws GroupAlreadyExistsFaultException :
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public AddGroupResponse addGroup
		(
			AddGroupRequest addGroupRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		GroupAlreadyExistsFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering addGroup() of Admin API v.1.1");

		com.vidyo.portal.admin.v1_1.Group ws_group = addGroupRequest.getGroup();

		String name = ws_group.getName();
		
		//Invalid Group Names
		if(StringUtils.isBlank(name) || !name.matches(ValidationUtils.GROUP_NAME_REGEX) || name.length() > 80) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Group Name");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;			
		}
		
		if (!NumberUtils.isNumber(ws_group.getRoomMaxUsers()) || Integer.valueOf(ws_group.getRoomMaxUsers()) < 2) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Max Participants");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!NumberUtils.isNumber(ws_group.getUserMaxBandWidthIn())
				|| (Integer.valueOf(ws_group.getUserMaxBandWidthIn()) < 1
						|| Integer.valueOf(ws_group.getUserMaxBandWidthIn()) > 100000)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Max BandWidth In");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!NumberUtils.isNumber(ws_group.getUserMaxBandWidthOut())
				|| (Integer.valueOf(ws_group.getUserMaxBandWidthOut()) < 1
						|| Integer.valueOf(ws_group.getUserMaxBandWidthOut()) > 100000)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Max BandWidth Out");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		if (groupService.isGroupExistForGroupName(name, 0)) {
			GroupAlreadyExistsFault fault = new GroupAlreadyExistsFault();
			fault.setErrorMessage("Group exist for name = " + name);
			GroupAlreadyExistsFaultException exception = new GroupAlreadyExistsFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Group group = new Group();
		group.setGroupName(ws_group.getName());
		group.setGroupDescription(ws_group.getDescription());
		group.setRoomMaxUsers(Integer.valueOf(ws_group.getRoomMaxUsers()));
		group.setUserMaxBandWidthIn(Integer.valueOf(ws_group.getUserMaxBandWidthIn()));
		group.setUserMaxBandWidthOut(Integer.valueOf(ws_group.getUserMaxBandWidthOut()));
		group.setAllowRecording(ws_group.getAllowRecording() ? 1 : 0);
		// Not default group
		group.setDefaultFlag(0);

		groupService.insertGroup(group);

		AddGroupResponse resp = new AddGroupResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting addGroup() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param stopVideoRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public StopVideoResponse stopVideo
		(
			StopVideoRequest stopVideoRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering stopVideo() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		StopVideoResponse resp = new StopVideoResponse();

		int roomID = stopVideoRequest.getConferenceID().getEntityID();
		int endpointID = stopVideoRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null || TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			conferenceService.stopVideo(endpointGUID);
		} catch (java.lang.Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting stopVideo() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getMembersRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetMembersResponse getMembers
		(
			GetMembersRequest getMembersRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getMembers() of Admin API v.1.1");

		com.vidyo.bo.MemberFilter filter = new com.vidyo.bo.MemberFilter();
		Filter_type0 input_param = getMembersRequest.getFilter();
		if (input_param != null) {
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			validateAndSetFilterLimitParam(filter, input_param);
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("memberID")) {
					filter.setSort("memberID");
				} else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSort("username");
				} else if (input_param.getSortBy().equalsIgnoreCase("displayName")) {
					filter.setSort("memberName");
				} else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSort("roomExtNumber");
				} else {
					filter.setSort("");
				}
			}

			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
		} else {
			filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
		}

		GetMembersResponse resp = new GetMembersResponse();

		List<com.vidyo.bo.Member> list = memberService.searchMembers(TenantContext.getTenantId(), filter);
		Configuration userImageConf = systemService.getConfiguration("USER_IMAGE");
		TenantConfiguration tenantConfiguration = this.tenantService
				.getTenantConfiguration(TenantContext.getTenantId());
		for (com.vidyo.bo.Member member : list) {
			Member ws_member = getWSMemberFromBOMember(member, userImageConf, tenantConfiguration);
			resp.addMember(ws_member);
		}

		int limit = memberService.countMembers(TenantContext.getTenantId(), filter);
		resp.setTotal(limit);

		if (logger.isDebugEnabled())
			logger.debug("Exiting getMembers() of VidyoPortalAdminServiceV2Skeleton");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param startVideoRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public StartVideoResponse startVideo
		(
			StartVideoRequest startVideoRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering startVideo() of VidyoPortalAdminServiceV2Skeleton");

		User user = this.userService.getLoginUser();

		int roomID = startVideoRequest.getConferenceID().getEntityID();
		int endpointID = startVideoRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null || TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			conferenceService.startVideo(endpointGUID);
		} catch (java.lang.Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		StartVideoResponse resp = new StartVideoResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting startVideo() of VidyoPortalAdminServiceV2Skeleton");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getRoomsRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetRoomsResponse getRooms
		(
			GetRoomsRequest getRoomsRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getRooms() of Admin API v.1.1");

		RoomFilter filter = new RoomFilter();
		Filter_type0 input_param = getRoomsRequest.getFilter();
		if (input_param != null) {
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			validateAndSetFilterLimitParam(filter, input_param);
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("roomID")) {
					filter.setSort("roomID");
				} else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSort("roomName");
				} else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSort("roomExtNumber");
				} else {
					filter.setSort("");
				}
			}

			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
		} else {
			filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
		}

		GetRoomsResponse resp = new GetRoomsResponse();
		int total = roomService.getCountRooms(filter).intValue();
		if(total > 0) {
			List<Room> list = roomService.getRooms(filter);
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			String requestScheme = request.getScheme();
			for (Room room : list) {
				com.vidyo.portal.admin.v1_1.Room ws_room = getWSRoomFromBORoom(room, requestScheme);
				resp.addRoom(ws_room);
			}
		}

		resp.setTotal(total);

		if (logger.isDebugEnabled())
			logger.debug("Exiting getRooms() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param removeRoomURLRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public RemoveRoomURLResponse removeRoomURL
		(
			RemoveRoomURLRequest removeRoomURLRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering removeRoomURL() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = removeRoomURLRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room = null;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			logger.error("Exception while retireving the Room", e);
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		roomService.removeRoomKey(room);

		RemoveRoomURLResponse resp = new RemoveRoomURLResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting removeRoomURL() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param muteAudioRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public MuteAudioResponse muteAudio
		(
			MuteAudioRequest muteAudioRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering muteAudio() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = muteAudioRequest.getConferenceID().getEntityID();
		int endpointID = muteAudioRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null || TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			conferenceService.muteAudio(endpointGUID);
		} catch (java.lang.Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		MuteAudioResponse resp = new MuteAudioResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting muteAudio() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param removeRoomPINRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public RemoveRoomPINResponse removeRoomPIN
		(
			RemoveRoomPINRequest removeRoomPINRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering removeRoomPIN() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = removeRoomPINRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		room.setPinSetting("remove");
		roomService.updateRoomPIN(room);

		RemoveRoomPINResponse resp = new RemoveRoomPINResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting removeRoomPIN() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param addRoomRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws RoomAlreadyExistsFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public AddRoomResponse addRoom
		(
			AddRoomRequest addRoomRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		RoomAlreadyExistsFaultException,
		InvalidModeratorPINFormatFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering addRoom() of Admin API v.1.1");

		com.vidyo.portal.admin.v1_1.Room ws_room = addRoomRequest.getRoom();

		if (ws_room.getRoomType().getValue().equalsIgnoreCase(RoomType_type0._Personal)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Use addMember to create a Personal room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		// Alpha numeric extensions needs to supported for API customers - https://jira.vidyo.com/browse/VPTL-9351
		if (RoomUtils.isNotValidAlphaNumericExtension(ws_room.getExtension())) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Extension - Invalid format or Length should be maximum 64 characters - " + ws_room.getExtension());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String name = ws_room.getName();
		if (StringUtils.isEmpty(name) || ws_room.getName().length() > 80) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room Name ( 1 - 80 characters) - " + name);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		/*if (!name.matches(ValidationUtils.USERNAME_REGEX)) {   
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room Name - " + name);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}*/

		boolean isValidExt = RoomUtils.isValidExtensionByPrefix(tenantService, ws_room.getExtension());

		if (!isValidExt) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Extension - Extension does not start with Tenant Prefix"
					+ ws_room.getExtension());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (roomService.isRoomExistForRoomExtNumber(ws_room.getExtension(), 0)) {
			RoomAlreadyExistsFault fault = new RoomAlreadyExistsFault();
			fault.setErrorMessage("Room exist for extension = " + ws_room.getExtension());
			RoomAlreadyExistsFaultException exception = new RoomAlreadyExistsFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(systemService, ws_room.getExtension());
		if (extExists > 0){
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Extension matches Schedule Room prefix. Please choose a different extension and try again. Extension = " + ws_room.getExtension());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (roomService.isRoomExistForRoomName(name, 0)) {
			RoomAlreadyExistsFault fault = new RoomAlreadyExistsFault();
			fault.setErrorMessage("Room exist for name = " + ws_room.getName());
			RoomAlreadyExistsFaultException exception = new RoomAlreadyExistsFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room = new Room();
		room.setRoomEnabled(1); // active by default
		room.setRoomTypeID(2); // public room
		room.setRoomName(ws_room.getName());
		room.setRoomDescription(ws_room.getDescription());
		room.setRoomExtNumber(ws_room.getExtension());

		// room PIN
		if (ws_room.getRoomMode().getHasPIN()) {
			String roomPIN = ws_room.getRoomMode().getRoomPIN();
			if (roomPIN == null || roomPIN.equalsIgnoreCase("")) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("roomPIN not provided");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			} else {
				if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), roomPIN)) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("PIN should be a " +
							systemService.getMinPINLengthForTenant(TenantContext.getTenantId()) +
							"-" + SystemServiceImpl.PIN_MAX + " digit number");
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				room.setPinSetting("enter");
				room.setRoomPIN(roomPIN);
			}
		}

		// moderator PIN
		if (ws_room.getRoomMode().getHasModeratorPIN()) {
			String moderatorPIN = ws_room.getRoomMode().getModeratorPIN();
			if (moderatorPIN == null || moderatorPIN.equalsIgnoreCase("")) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("moderatorPIN not provided");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			} else {
				if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), moderatorPIN)) {
					InvalidModeratorPINFormatFault fault = new InvalidModeratorPINFormatFault();
					fault.setErrorMessage("PIN should be a " +
							systemService.getMinPINLengthForTenant(TenantContext.getTenantId()) +
							"-" + SystemServiceImpl.PIN_MAX + " digit number");
					InvalidModeratorPINFormatFaultException exception = new InvalidModeratorPINFormatFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
				room.setModeratorPinSetting("enter");
				room.setRoomModeratorPIN(moderatorPIN);
			}
		}

		String groupName = ws_room.getGroupName();
		Group group;
		try {
			group = groupService.getGroupByName(groupName);
			room.setGroupID(group.getGroupID());
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Group not found for name = " + groupName);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String ownerName = ws_room.getOwnerName();
		int memberId = memberService.getMemberID(ownerName, TenantContext.getTenantId());
		if(memberId != 0) {
			room.setMemberID(memberId);
		} else {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Member not found for ownerName = " + ownerName);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (ws_room.getRoomMode().getIsLocked()) {
			room.setRoomLocked(1);
		}

		int roomId=roomService.insertRoom(room);

		AddRoomResponse resp = new AddRoomResponse();
		resp.setOK(OK_type0.OK);

		if(addRoomRequest.getReturnObjectInResponse() != null && "true" == addRoomRequest.getReturnObjectInResponse().getValue()){
			if (logger.isDebugEnabled()){
				logger.debug("Return Object is requested , so passing object back to responese.Room id is  "+roomId);
			}
			EntityID entityId=new EntityID();
			entityId.setEntityID(roomId);
			ws_room.setRoomID(entityId);
			Room roomDetails = roomService.getRoom(roomId);
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			String requestScheme = request.getScheme();
			ws_room = getWSRoomFromBORoom(roomDetails, requestScheme);
			resp.setRoom(ws_room);
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting addRoom() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param createRoomPINRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public CreateRoomPINResponse createRoomPIN
		(
			CreateRoomPINRequest createRoomPINRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering createRoomPIN() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = createRoomPINRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;

		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null || TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid RoomID " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String roomPIN = createRoomPINRequest.getPIN();
		if (roomPIN == null || roomPIN.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("PIN not provided");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		} else {
			if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), roomPIN)) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("PIN should be a " +
						systemService.getMinPINLengthForTenant(TenantContext.getTenantId()) +
						"-" + SystemServiceImpl.PIN_MAX + " digit number");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
			room.setPinSetting("enter");
			room.setRoomPIN(roomPIN);
		}

		roomService.updateRoomPIN(room);

		CreateRoomPINResponse resp = new CreateRoomPINResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting createRoomPIN() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getGroupRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GroupNotFoundFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetGroupResponse getGroup
		(
			GetGroupRequest getGroupRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		GroupNotFoundFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getGroup() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int groupId = getGroupRequest.getGroupID().getEntityID();
		// Get the GroupBO based on the input Id
		com.vidyo.bo.Group group;
		try {
			group = groupService.getGroup(groupId);
		} catch (java.lang.Exception e) {
			GroupNotFoundFault fault = new GroupNotFoundFault();
			fault.setErrorMessage("Group not found for groupID = " + groupId);
			GroupNotFoundFaultException exception = new GroupNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (group == null) {
			GroupNotFoundFault fault = new GroupNotFoundFault();
			fault.setErrorMessage("Group not found for groupID = " + groupId);
			GroupNotFoundFaultException exception = new GroupNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != group.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Group for groupID = " + groupId + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		com.vidyo.portal.admin.v1_1.Group ws_group = getWSGroupFromBOGroup(group);

		GetGroupResponse resp = new GetGroupResponse();
		resp.setGroup(ws_group);

		if (logger.isDebugEnabled())
			logger.debug("Exiting getGroup() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getLicenseDataRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetLicenseDataResponse getLicenseData
		(
			GetLicenseDataRequest getLicenseDataRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getLicenseData() of Admin API v.1.1");

		int tenantId = TenantContext.getTenantId();

		List<SystemLicenseFeature> list = licenseService.getTenantLicense(tenantId);

		GetLicenseDataResponse resp = new GetLicenseDataResponse();

		List<String> listOfNames = new ArrayList<String>(10);
		listOfNames.add("Start Date");
		listOfNames.add("Expiry Date");
		listOfNames.add("SeatStartDate");
		listOfNames.add("SeatExpiry");
		listOfNames.add("Seats");
		listOfNames.add("Ports");
		listOfNames.add("Installs");
		listOfNames.add("LimitTypeExecutiveSystem");
		listOfNames.add("LimitTypePanoramaSystem");

		String licVersion = licenseService.getSystemLicenseFeature("Version").getLicensedValue();
		boolean isLinesModel = false;
		if (licVersion != null && (licVersion.equals(LicensingServiceImpl.LIC_VERSION_21) || licVersion.equals(LicensingServiceImpl.LIC_VERSION_22))) {
			isLinesModel = true;
		}

		for (SystemLicenseFeature feature : list) {
			if (listOfNames.contains(feature.getName())) {
				LicenseFeatureData param = new LicenseFeatureData();
				if(feature.getName().equals("Ports") && isLinesModel) {
					param.setName("Lines");
				} else {
					param.setName(feature.getName());
				}
				param.setMaxValue(feature.getLicensedValue());
				param.setCurrentValue(feature.getCurrentValue());

				resp.addLicenseFeature(param);
			}
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting getLicenseData() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getPortalVersionRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetPortalVersionResponse getPortalVersion
		(
			GetPortalVersionRequest getPortalVersionRequest
		)
		throws
		InvalidArgumentFaultException,
		NotLicensedFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering portalVersion() of Admin API v.1.1");

		String portalVersion = systemService.getPortalVersion();

		GetPortalVersionResponse resp = new GetPortalVersionResponse();
		resp.setPortalVersion(portalVersion);

		if (logger.isDebugEnabled())
			logger.debug("Exiting portalVersion() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param startRecordingRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws ResourceNotAvailableFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public StartRecordingResponse startRecording
		(
			StartRecordingRequest startRecordingRequest
		)
		throws
		InvalidArgumentFaultException,
		NotLicensedFaultException,
		ResourceNotAvailableFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering recordConference() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = startRecordingRequest.getConferenceID().getEntityID();

		boolean webcast = startRecordingRequest.getWebcast();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null || TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getAllowRecording() == 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("You are not allowed to record the Meeting " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			conferenceService.recordTheConference(room.getMemberID(), roomID, startRecordingRequest.getRecorderPrefix(), webcast ? 1 : 0);
		} catch (JoinConferenceException joinConferenceException) {
			logger.error("JoinConferenceException" + joinConferenceException.getMessage());

			ResourceNotAvailableFault fault = new ResourceNotAvailableFault();
			fault.setErrorMessage("Resource not available to record the Meeting " + roomID);
			ResourceNotAvailableFaultException exception = new ResourceNotAvailableFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		} catch (OutOfPortsException outOfPortsException) {
			logger.error("OutOfPortsException" + outOfPortsException.getMessage());

			ResourceNotAvailableFault fault = new ResourceNotAvailableFault();
			fault.setErrorMessage(outOfPortsException.getMessage());
			ResourceNotAvailableFaultException exception = new ResourceNotAvailableFaultException(outOfPortsException.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		StartRecordingResponse resp = new StartRecordingResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting recordConference() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getRecordingProfilesRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetRecordingProfilesResponse getRecordingProfiles
		(
			GetRecordingProfilesRequest getRecordingProfilesRequest
		)
		throws
		InvalidArgumentFaultException,
		NotLicensedFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getRecordingProfiles() of Admin API v.1.1");

		List<RecorderPrefix> recorders = systemService.getAvailableRecorderPrefixes(null);

		GetRecordingProfilesResponse resp = new GetRecordingProfilesResponse();
		if (recorders != null) {
			for (RecorderPrefix recorderEndpoint : recorders) {
				Recorder recorder = new Recorder();
				recorder.setDescription(recorderEndpoint.getDescription());
				recorder.setRecorderPrefix(recorderEndpoint.getPrefix());
				resp.addRecorder(recorder);
			}
			resp.setTotal(recorders.size());
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting getRecordingProfiles() of Admin API v.1.1");

		return resp;
	}

	@Override
	public ScheduledRoomIsEnabledResponse scheduledRoomIsEnabled(ScheduledRoomIsEnabledRequest scheduledRoomIsEnabledRequest) throws NotLicensedFaultException, RoomNotFoundFaultException, InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled())
			logger.debug("Entering scheduledRoomIsEnabled() of Admin API v.1.1");

		User user = userService.getLoginUser();
		int tenantId = TenantContext.getTenantId();

		String extension = scheduledRoomIsEnabledRequest.getExtension().getExtension_type2();
		Pin_type3 pinObj = scheduledRoomIsEnabledRequest.getPin();
		String pin = null;
		if (pinObj != null) {
			pin = pinObj.getPin_type2();
		}

		ScheduledRoomResponse result = roomService.validateScheduledRoom(extension, pin, tenantId);
		if (result.getStatus() != ScheduledRoomResponse.SUCCESS) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("No such scheduled room found in tenant.");
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		if (result.getRoom().getTenantID() != TenantContext.getTenantId()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Unauthorized, room not owned by this tenant. ");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room roomObj = result.getRoom();
		ScheduledRoomIsEnabledResponse resp = new ScheduledRoomIsEnabledResponse();

		resp.setEnabled(roomObj.getRoomEnabled() == 1);
		if (logger.isDebugEnabled())
			logger.debug("Exiting scheduledRoomIsEnabled() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param pauseRecordingRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public PauseRecordingResponse pauseRecording
		(
			PauseRecordingRequest pauseRecordingRequest
		)
		throws
		InvalidArgumentFaultException,
		NotLicensedFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering pauseRecording() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = pauseRecordingRequest.getConferenceID().getEntityID();
		int endpointID = pauseRecordingRequest.getRecorderID();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null || TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getAllowRecording() == 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("You are not allowed to pause the recording of the Meeting" + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		// check Recorder endpoint
		String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "R");
		if (endpointGUID == null || endpointGUID.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Recorder not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			conferenceService.pauseRecording(endpointGUID);
		} catch (java.lang.Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw new GeneralFaultException(e.getMessage());
		}

		PauseRecordingResponse resp = new PauseRecordingResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting pauseRecording() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param resumeRecordingRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public ResumeRecordingResponse resumeRecording
		(
			ResumeRecordingRequest resumeRecordingRequest
		)
		throws
		InvalidArgumentFaultException,
		NotLicensedFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering resumeRecording() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = resumeRecordingRequest.getConferenceID().getEntityID();
		int endpointID = resumeRecordingRequest.getRecorderID();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null || TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getAllowRecording() == 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("You are not allowed to resume recording of the Meeting" + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "R");
		if (endpointGUID == null || endpointGUID.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("RecorderID not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			conferenceService.resumeRecording(endpointGUID);
		} catch (java.lang.Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw new GeneralFaultException(e.getMessage());
		}

		ResumeRecordingResponse resp = new ResumeRecordingResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting resumeRecording() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param stopRecordingRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public StopRecordingResponse stopRecording
		(
			StopRecordingRequest stopRecordingRequest
		)
		throws
		InvalidArgumentFaultException,
		NotLicensedFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering stopRecording() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = stopRecordingRequest.getConferenceID().getEntityID();
		int endpointID = stopRecordingRequest.getRecorderID();

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("ConferenceID is invalid");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if(room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid RoomID " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room.getAllowRecording() == 0) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("You are not allowed to stop recording the Meeting" + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, "R");
		if (endpointGUID == null || endpointGUID.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("RecorderID not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			conferenceService.leaveTheConference(endpointGUID, roomID, CallCompletionCode.BY_SOMEONE_ELSE);
		} catch (java.lang.Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw new GeneralFaultException(e.getMessage());
		}

		StopRecordingResponse resp = new StopRecordingResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting stopRecording() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Utility method to convert Group Business object to Webservice Response object
	 *
	 * @param group:
	 * @return Group Webservices Group Response Object
	 */
	private com.vidyo.portal.admin.v1_1.Group getWSGroupFromBOGroup
		(
			com.vidyo.bo.Group group
		)
	{
		com.vidyo.portal.admin.v1_1.Group ws_group = new com.vidyo.portal.admin.v1_1.Group();

		EntityID id = new EntityID();

		id.setEntityID(group.getGroupID());
		ws_group.setGroupID(id);
		ws_group.setName(group.getGroupName());
		ws_group.setDescription(group.getGroupDescription());
		ws_group.setRoomMaxUsers(String.valueOf(group.getRoomMaxUsers()));
		ws_group.setUserMaxBandWidthIn(String.valueOf(group.getUserMaxBandWidthIn()));
		ws_group.setUserMaxBandWidthOut(String.valueOf(group.getUserMaxBandWidthOut()));
		ws_group.setAllowRecording((group.getAllowRecording() == 1));

		return ws_group;
	}

	/**
	 * Utility method to convert Room Business object to Webservice Response object
	 *
	 * @param room:
	 * @return Room
	 */
	private com.vidyo.portal.admin.v1_1.Room getWSRoomFromBORoom(com.vidyo.bo.Room room, String requestScheme)
	{
		com.vidyo.portal.admin.v1_1.Room ws_room = new com.vidyo.portal.admin.v1_1.Room();

		EntityID id = new EntityID();

		id.setEntityID(room.getRoomID());
		ws_room.setRoomID(id);
		ws_room.setName(room.getRoomName());
		ws_room.setDescription(room.getRoomDescription());
		ws_room.setGroupName(room.getGroupName());
		ws_room.setExtension(room.getRoomExtNumber());
		ws_room.setOwnerName(room.getOwnerName());

		if (room.getRoomType().equalsIgnoreCase("Personal")) {
			ws_room.setRoomType(RoomType_type0.Personal);
		} else if (room.getRoomType().equalsIgnoreCase("Public")) {
			ws_room.setRoomType(RoomType_type0.Public);
		}

		RoomMode_type0 roomMode = new RoomMode_type0();
		roomMode.setIsLocked(room.getRoomLocked() == 1);

		if (room.getRoomKey() != null && !room.getRoomKey().trim().isEmpty()) {
			StringBuffer path = new StringBuffer();
			Tenant tenant = tenantService.getTenant(room.getTenantID());
			/*path.append(requestScheme + "://").append(tenant.getTenantURL());
			path.append("/flex.html?roomdirect.html&key=");
			path.append(room.getRoomKey());*/

			String joinURL = roomService.getRoomURL(systemService, requestScheme,
					tenant.getTenantURL(), room.getRoomKey());

			path.append(joinURL);
			if (tenantService.isTenantNotAllowingGuests()) {
				path.append("&noguest");
			}

			roomMode.setRoomURL(path.toString());
		}

		if (room.getRoomPIN() != null) {
			roomMode.setHasPIN(true);
			roomMode.setRoomPIN(room.getRoomPIN());
		} else {
			roomMode.setHasPIN(false);
		}

		if (room.getRoomModeratorPIN() != null) {
			roomMode.setHasModeratorPIN(true);
			roomMode.setModeratorPIN(room.getRoomModeratorPIN());
		} else {
			roomMode.setHasModeratorPIN(false);
		}

		ws_room.setRoomMode(roomMode);

		return ws_room;
	}

	/**
	 * Utility method to convert Member Business object to Webservice Response object
	 *
	 * @param member:
	 * @return Member
	 */
	private Member getWSMemberFromBOMember
		(
			com.vidyo.bo.Member member,
			Configuration userImageConf, TenantConfiguration tenantConfiguration
		)
	{
		Member ws_member = new Member();

		EntityID id = new EntityID();

		id.setEntityID(member.getMemberID());
		ws_member.setMemberID(id);
		ws_member.setDisplayName(member.getMemberName());
		ws_member.setName(member.getUsername());
		ws_member.setDescription(member.getDescription());
		ws_member.setExtension(member.getRoomExtNumber());
		ws_member.setEmailAddress(member.getEmailAddress());
		ws_member.setProxyName(member.getProxyName());

		if (member.getGroupName().equals("")) {
			com.vidyo.bo.Group defaultGroup = groupService.getDefaultGroup();
			if (defaultGroup != null) {
				ws_member.setGroupName(defaultGroup.getGroupName());
			} else {
				ws_member.setGroupName("Default");
			}
		} else {
			ws_member.setGroupName(member.getGroupName());
		}

		if (member.getLangID() == 1) {
			ws_member.setLanguage(Language_type0.en);
		} else if (member.getLangID() == 2) {
			ws_member.setLanguage(Language_type0.fr);
		} else if (member.getLangID() == 3) {
			ws_member.setLanguage(Language_type0.ja);
		} else if (member.getLangID() == 4) {
			ws_member.setLanguage(Language_type0.zh_CN);
		} else if (member.getLangID() == 5) {
			ws_member.setLanguage(Language_type0.es);
		} else if (member.getLangID() == 6) {
			ws_member.setLanguage(Language_type0.it);
		} else if (member.getLangID() == 7) {
			ws_member.setLanguage(Language_type0.de);
		} else if (member.getLangID() == 8) {
			ws_member.setLanguage(Language_type0.ko);
		} else if (member.getLangID() == 9) {
			ws_member.setLanguage(Language_type0.pt);
		} else if (member.getLangID() == 10) {
			ws_member.setLanguage(Language_type0.Factory.fromValue(systemService.getSystemLang(member.getTenantID()).getLangCode()));
		} else if (member.getLangID() == 11) {
			ws_member.setLanguage(Language_type0.fi);
		} else if (member.getLangID() == 12) {
			ws_member.setLanguage(Language_type0.pl);
		} else if (member.getLangID() == 13) {
			ws_member.setLanguage(Language_type0.zh_TW);
		} else if (member.getLangID() == 14) {
			ws_member.setLanguage(Language_type0.th);
		} else if (member.getLangID() == 15) {
			ws_member.setLanguage(Language_type0.ru);
		} else if (member.getLangID() == 16) {
			ws_member.setLanguage(Language_type0.tr);
		}

		if (member.getRoleName().equalsIgnoreCase("Admin")) {
			ws_member.setRoleName(RoleName_type0.Admin);
		} else if (member.getRoleName().equalsIgnoreCase("Normal")) {
			ws_member.setRoleName(RoleName_type0.Normal);
		} else if (member.getRoleName().equalsIgnoreCase("Operator")) {
			ws_member.setRoleName(RoleName_type0.Operator);
		} else if (member.getRoleName().equalsIgnoreCase("VidyoRoom")) {
			ws_member.setRoleName(RoleName_type0.VidyoRoom);
		} else if (member.getRoleName().equalsIgnoreCase("Legacy")) {
			ws_member.setRoleName(RoleName_type0.Legacy);
		} else if (member.getRoleName().equalsIgnoreCase("Executive")) {
			ws_member.setRoleName(RoleName_type0.Executive);
		} else if (member.getRoleName().equalsIgnoreCase("VidyoPanorama")) {
			ws_member.setRoleName(RoleName_type0.Panorama);
		} else {
			ws_member.setRoleName(RoleName_type0.Normal);
		}

		ws_member.setCreated(new Date((long) member.getMemberCreated() * 1000));
		ws_member.setAllowCallDirect(true);
		if(member.getCreationTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(member.getCreationTime());
			ws_member.setCreationTime(calendar);
		}
		if(member.getModificationTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(member.getModificationTime());
			ws_member.setModificationTime(calendar);
		}

		if (member.getRoomType().equalsIgnoreCase("Legacy")) {
			ws_member.setAllowPersonalMeeting(false);
		} else {
			ws_member.setAllowPersonalMeeting(true);
		}
		ws_member.setLocationTag(member.getLocationTag());

		if (member.getPhone1() != null && ! member.getPhone1().isEmpty()){
			ws_member.setPhone1(member.getPhone1());
		}

		if (member.getPhone2() != null && ! member.getPhone2().isEmpty()){
			ws_member.setPhone2(member.getPhone2());
		}

		if (member.getPhone3() != null && ! member.getPhone3().isEmpty()){
			ws_member.setPhone3(member.getPhone3());
		}

		if (member.getDepartment() != null && ! member.getDepartment().isEmpty()){
			ws_member.setDepartment(member.getDepartment());
		}

		if (member.getTitle() != null && ! member.getTitle().isEmpty()){
			ws_member.setTitle(member.getTitle());
		}

		if (member.getInstantMessagerID() != null && ! member.getInstantMessagerID().isEmpty()){
			ws_member.setInstantMessagerID(member.getInstantMessagerID());
		}

		if (member.getLocation() != null && ! member.getLocation().isEmpty()){
			ws_member.setLocation(member.getLocation());
		}


		if (userImageConf != null && StringUtils.isNotBlank(userImageConf.getConfigurationValue()) && userImageConf.getConfigurationValue().equalsIgnoreCase("1")){

			if (tenantConfiguration.getUserImage() == 1) {
					if (member.getThumbnailUpdateTime() != null){
						Calendar updateTime = Calendar.getInstance();;
						updateTime.setTime(member.getThumbnailUpdateTime());
						ws_member.setThumbnailUpdateTime(updateTime);
					}
			}
		}
		return ws_member;
	}

	/**
	 * Utility method to convert RoomProfile Business object to Webservice Response object
	 *
	 * @param roomProfile
	 * @return Group Webservices RoomProfile Response Object
	 */
	private com.vidyo.portal.admin.v1_1.RoomProfile getWSRoomProfileFromBORoomProfile
		(
			com.vidyo.bo.profile.RoomProfile roomProfile
		)
	{
		com.vidyo.portal.admin.v1_1.RoomProfile ws_roomProfile = new com.vidyo.portal.admin.v1_1.RoomProfile();

		ws_roomProfile.setRoomProfileName(roomProfile.getProfileName());
		ws_roomProfile.setDescription(roomProfile.getProfileDescription());

		return ws_roomProfile;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getLocationTagsRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetLocationTagsResponse getLocationTags
		(
			GetLocationTagsRequest getLocationTagsRequest
		)
		throws
		InvalidArgumentFaultException,
		NotLicensedFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getLocationTags() of Admin API v.1.1");

		int tenantID = TenantContext.getTenantId();
		Filter_type0 inputFilter = getLocationTagsRequest.getFilter();
		ServiceFilter serviceFilter = new ServiceFilter();
		serviceFilter.setSort("locationTag");//hardcoded
		if (inputFilter != null) {
			if (inputFilter.getStart() >= 0) {
				serviceFilter.setStart(inputFilter.getStart());
			}
			validateAndSetFilterLimitParam(serviceFilter, inputFilter);
			if (inputFilter.getDir() != null) {
				serviceFilter.setDir(inputFilter.getDir().getValue());
			}

			if (inputFilter.getQuery() != null) {
				serviceFilter.setServiceName(inputFilter.getQuery());
			}
		} else {
			serviceFilter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
		}

		GetLocationTagsResponse resp = new GetLocationTagsResponse();

		List<Location> locationTags = serviceService.getSelectedLocationTags(serviceFilter, tenantID);
		for (Location location : locationTags) {
			com.vidyo.portal.admin.v1_1.Location loc = new com.vidyo.portal.admin.v1_1.Location();
			loc.setLocationTag(location.getLocationTag());
			resp.addLocation(loc);
		}
		Long count = serviceService.getCountLocations(serviceFilter,tenantID);
		resp.setTotal(count.intValue());

		if (logger.isDebugEnabled())
			logger.debug("Exiting getLocationTags() of Admin API v.1.1");

		return resp;
	}

	@Override
	public DismissRaisedHandResponse dismissRaisedHand(DismissRaisedHandRequest dismissRaisedHandRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {

		if (logger.isDebugEnabled())
			logger.debug("Entering dismissRaisedHand() of Admin API v.1.1");

		int roomEntityID = dismissRaisedHandRequest.getConferenceID().getEntityID();

		String moderatorPIN =  "noModeratorPIN";

		int endpointID = dismissRaisedHandRequest.getParticipantID().getEntityID();

		LectureModeParticipantControlRequest serviceRequest = new LectureModeParticipantControlRequest();
		serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
		serviceRequest.setTenantID(TenantContext.getTenantId());
		serviceRequest.setRoomID(roomEntityID);
		serviceRequest.setModeratorPIN(moderatorPIN);
		serviceRequest.setEndpointID(endpointID);

		LectureModeControlResponse serviceResponse = lectureModeService.dismissRaisedHand(serviceRequest);

		int status =  serviceResponse.getStatus();
		if (status == ConferenceControlResponse.INVALID_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceId");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.PARTICIPANT_NOT_IN_CONFERENCE) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.LECTURE_MODE_NOT_STARTED) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Lecture mode is not on for conference");
			GeneralFaultException exception = new GeneralFaultException("Lecture mode is not on for conference");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Server internal error");
			GeneralFaultException exception = new GeneralFaultException("Server internal error");
			exception.setFaultMessage(fault);
			throw exception;
		}

		DismissRaisedHandResponse response = new DismissRaisedHandResponse();
		response.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting dismissRaisedHand() of Admin API v.1.1");

		return response;
	}

	/**
	 * Returns the webcast URL and pin indicator for a Room
	 *
	 * @param getWebcastURLRequest:
	 * @throws NotLicensedFaultException
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */
	@Override
	public GetWebcastURLResponse getWebcastURL
		(
			GetWebcastURLRequest getWebcastURLRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getWebcastURL() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		Integer roomID = getWebcastURLRequest.getRoomID().getEntityID();
		boolean invalidRoom = false;
		Room room = null;
		if (roomID == null) {
			invalidRoom = true;
		} else {
			try {
				room = roomService.getRoomDetailsForConference(roomID);
			} catch (java.lang.Exception e) {
				invalidRoom = true;
			}
		}

		if (invalidRoom || room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid Room " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		boolean throwGeneralFault = false;
		String generalFaultMessg = null;
		try {
				replayService.getWebCastURLandPIN(room);
		} catch (RemoteException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage(e.getMessage());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (throwGeneralFault) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(generalFaultMessg);
			GeneralFaultException exception = new GeneralFaultException(generalFaultMessg);
			exception.setFaultMessage(fault);
			throw exception;
		}

		GetWebcastURLResponse resp = new GetWebcastURLResponse();
		resp.setHasWebCastPIN((room.getWebCastPinned() == 1));
		String webcastUrl = room.getWebCastURL() == null ? "" : room.getWebCastURL().trim();
		if (webcastUrl != null
			&& webcastUrl.indexOf("=") > 0
			&& webcastUrl.substring(webcastUrl.indexOf("=") + 1).length() <= 0)
		{
			webcastUrl = "";
		}
		resp.setWebCastURL(webcastUrl);

		if (logger.isDebugEnabled())
			logger.debug("Exiting getWebcastURL() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param removeWebcastPINRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public RemoveWebcastPINResponse removeWebcastPIN
		(
			RemoveWebcastPINRequest removeWebcastPINRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering removeWebcastPIN() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = removeWebcastPINRequest.getRoomID().getEntityID();
		boolean invalidRoom = false;
		Room room = null;
		if (roomID <= 0) {
			invalidRoom = true;
		} else {
			try {
				room = roomService.getRoomDetailsForConference(roomID);
			} catch (java.lang.Exception e) {
				invalidRoom = true;
			}
		}

		if (invalidRoom || room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid Room " + roomID);
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		if (!RoomUtils.canMemberAccessRoom(user.getMemberID(), this.userService.getLoginUserRole(), room, "noModeratorPIN")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("You are not an owner of room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		boolean throwGeneralFault = false;
		String generalFaultMessg = null;
		try {
				replayService.updateWebCastPIN(room, "");
		} catch (RemoteException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (NoVidyoReplayException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage(e.getMessage());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (throwGeneralFault) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(generalFaultMessg);
			GeneralFaultException exception = new GeneralFaultException(generalFaultMessg);
			exception.setFaultMessage(fault);
			throw exception;
		}

		RemoveWebcastPINResponse response = new RemoveWebcastPINResponse();
		response.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting removeWebcastPIN() of Admin API v.1.1");

		return response;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param createWebcastPINRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public CreateWebcastPINResponse createWebcastPIN
		(
			CreateWebcastPINRequest createWebcastPINRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering createWebcastPIN() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		String erroMessg = null;
		boolean errorCondition = false;

		if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), createWebcastPINRequest.getPIN())) {
			errorCondition = true;
			erroMessg = "Invalid PIN";
		}

		if (errorCondition) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage(erroMessg);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(erroMessg);
			exception.setFaultMessage(fault);
			throw exception;
		}

		boolean invalidRoom = false;
		Room room = null;
		if (createWebcastPINRequest.getRoomID().getEntityID() <= 0) {
			invalidRoom = true;
		} else {
			try {
				room = roomService.getRoomDetailsForConference(createWebcastPINRequest.getRoomID().getEntityID());
			} catch (java.lang.Exception e) {
				invalidRoom = true;
			}
		}

		if (invalidRoom || room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room " + createWebcastPINRequest.getRoomID().getEntityID());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid Room " + createWebcastPINRequest.getRoomID().getEntityID());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		if (!RoomUtils.canMemberAccessRoom(user.getMemberID(), this.userService.getLoginUserRole(), room, "noModeratorPIN")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("You are not an owner of room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		boolean throwGeneralFault = false;
		String generalFaultMessg = null;
		try {
				replayService.updateWebCastPIN(room, createWebcastPINRequest.getPIN());
		} catch (RemoteException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (NoVidyoReplayException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage(e.getMessage());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (throwGeneralFault) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(generalFaultMessg);
			GeneralFaultException exception = new GeneralFaultException(generalFaultMessg);
			exception.setFaultMessage(fault);
			throw exception;
		}

		CreateWebcastPINResponse resp = new CreateWebcastPINResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting createWebcastPIN() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param removeWebcastURLRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public RemoveWebcastURLResponse removeWebcastURL
		(
			RemoveWebcastURLRequest removeWebcastURLRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering removeWebcastURL() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = removeWebcastURLRequest.getRoomID().getEntityID();
		Room room = null;
		boolean invalidRoom = false;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			invalidRoom = true;
		}

		if (invalidRoom || room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		if (!RoomUtils.canMemberAccessRoom(user.getMemberID(), this.userService.getLoginUserRole(), room, "noModeratorPIN")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("You are not an owner of room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		boolean throwGeneralFault = false;
		String generalFaultMessg = null;
		try {
				replayService.deleteWebCastURL(room);
		} catch (RemoteException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (NoVidyoReplayException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage(e.getMessage());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (throwGeneralFault) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(generalFaultMessg);
			GeneralFaultException exception = new GeneralFaultException(generalFaultMessg);
			exception.setFaultMessage(fault);
			throw exception;
		}

		RemoveWebcastURLResponse resp = new RemoveWebcastURLResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting removeWebcastURL() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param createWebcastURLRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public CreateWebcastURLResponse createWebcastURL
		(
			CreateWebcastURLRequest createWebcastURLRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering createWebcastURL() of Admin API v.1.1");

		User user = this.userService.getLoginUser();
		int roomId = createWebcastURLRequest.getRoomID().getEntityID();
		boolean invalidRoom = false;
		Room room = null;
		
		try {
			room = roomService.getRoomDetailsForConference(roomId);
		} catch (java.lang.Exception e) {
			invalidRoom = true;
		}

		if (invalidRoom || room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Room " + roomId);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomId + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		if (!RoomUtils.canMemberAccessRoom(user.getMemberID(), this.userService.getLoginUserRole(), room, "noModeratorPIN")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("You are not an owner of room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		boolean throwGeneralFault = false;
		String generalFaultMessg = null;
		try {
				replayService.generateWebCastURL(room);
		} catch (RemoteException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (NoVidyoReplayException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
			throwGeneralFault = true;
			generalFaultMessg = e.getMessage();
		} catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage(e.getMessage());
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (throwGeneralFault) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(generalFaultMessg);
			GeneralFaultException exception = new GeneralFaultException(generalFaultMessg);
			exception.setFaultMessage(fault);
			throw exception;
		}

		CreateWebcastURLResponse resp = new CreateWebcastURLResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting createWebcastURL() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getRoomProfilesRequest :
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetRoomProfilesResponse getRoomProfiles
		(
			GetRoomProfilesRequest getRoomProfilesRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getRoomProfiles() of Admin API v.1.1");

		GetRoomProfilesResponse resp = new GetRoomProfilesResponse();

		List<RoomProfile> roomProfiles = roomService.getRoomProfiles();
		for (RoomProfile roomProfile : roomProfiles) {
			resp.addRoomProfile(getWSRoomProfileFromBORoomProfile(roomProfile));
		}
		resp.setTotal(roomProfiles.size());

		if (logger.isDebugEnabled())
			logger.debug("Exiting getRoomProfiles() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param setRoomProfileRequest :
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public SetRoomProfileResponse setRoomProfile
		(
			SetRoomProfileRequest setRoomProfileRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering setRoomProfile() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = setRoomProfileRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String profileName = setRoomProfileRequest.getRoomProfileName();
		if (profileName == null || profileName.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("profileName not provided");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		roomService.updateRoomProfile(room.getRoomID(), profileName);

		SetRoomProfileResponse resp = new SetRoomProfileResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting setRoomProfile() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param removeRoomProfileRequest :
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public RemoveRoomProfileResponse removeRoomProfile
		(
			RemoveRoomProfileRequest removeRoomProfileRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering removeRoomProfile() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = removeRoomProfileRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		try {
			int rc = roomService.removeRoomProfile(room.getRoomID());
		} catch (java.lang.Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		RemoveRoomProfileResponse resp = new RemoveRoomProfileResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting removeRoomProfile() of Admin API v.1.1");

		return resp;
	}

	@Override
	public RoomIsEnabledResponse roomIsEnabled(RoomIsEnabledRequest roomIsEnabledRequest) throws NotLicensedFaultException, RoomNotFoundFaultException, InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled())
			logger.debug("Entering roomIsEnabled() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = roomIsEnabledRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("RoomID is invalid " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("Room not found for roomID = " + roomID);
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("Room not found for roomID = " + roomID);
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		RoomIsEnabledResponse resp = new RoomIsEnabledResponse();

		resp.setEnabled(room.getRoomEnabled() == 1);

		if (logger.isDebugEnabled())
			logger.debug("Exiting roomIsEnabled() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getRoomProfileRequest :
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public GetRoomProfileResponse getRoomProfile
		(
			GetRoomProfileRequest getRoomProfileRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering getRoomProfile() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = getRoomProfileRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null || TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		RoomProfile roomProfile = null;
		try {
			roomProfile = roomService.getRoomProfile(room.getRoomID());
		} catch (java.lang.Exception ignored) { }

		GetRoomProfileResponse resp = new GetRoomProfileResponse();
		if (roomProfile != null) {
			com.vidyo.portal.admin.v1_1.RoomProfile ws_roomprofile = this.getWSRoomProfileFromBORoomProfile(roomProfile);
			resp.setRoomProfile(ws_roomprofile);
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting getRoomProfile() of Admin API v.1.1");

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param createModeratorPINRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws InvalidModeratorPINFormatFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public CreateModeratorPINResponse createModeratorPIN
		(
			CreateModeratorPINRequest createModeratorPINRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException,
		InvalidModeratorPINFormatFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering createModeratorPIN() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = createModeratorPINRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null || TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String PIN = createModeratorPINRequest.getPIN();

		if (PIN == null || PIN.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("PIN not provided");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), PIN)) {
			InvalidModeratorPINFormatFault fault = new InvalidModeratorPINFormatFault();
			fault.setErrorMessage("PIN should be a " +
					systemService.getMinPINLengthForTenant(TenantContext.getTenantId()) +
					"-" + SystemServiceImpl.PIN_MAX + " digit number");
			InvalidModeratorPINFormatFaultException exception = new InvalidModeratorPINFormatFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		room.setModeratorPinSetting("enter");
		room.setRoomModeratorPIN(PIN);
		roomService.updateRoomModeratorPIN(room);

		CreateModeratorPINResponse resp = new CreateModeratorPINResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting createModeratorPIN() of Admin API v.1.1");

		return resp;
	}

    @Override
    public CreateScheduledRoomResponse createScheduledRoom(CreateScheduledRoomRequest createScheduledRoomRequest) throws ScheduledRoomCreationFaultException, NotLicensedFaultException, InvalidArgumentFaultException, InvalidModeratorPINFormatFaultException, GeneralFaultException {
        if (logger.isDebugEnabled())
            logger.debug("Entering createScheduledRoom() of Admin API v.1.1");

        User user = userService.getLoginUser();
        if (user == null) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid User");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String ownerName = createScheduledRoomRequest.getOwnerName();
        int adminTenantId = TenantContext.getTenantId();

        com.vidyo.bo.Member member = memberService.getMemberByName(ownerName, adminTenantId);
        if (member == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("User not found in tenant, ownerName: " + ownerName);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        // check moderator pin
        String moderatorPIN = createScheduledRoomRequest.getModeratorPIN();
        if (!StringUtils.isEmpty(moderatorPIN)) {
            if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), moderatorPIN)) {
                InvalidModeratorPINFormatFault fault = new InvalidModeratorPINFormatFault();
                fault.setErrorMessage("ModeratorPIN should be a " +
						systemService.getMinPINLengthForTenant(TenantContext.getTenantId()) +
						"-" + SystemServiceImpl.PIN_MAX + " digit number");
                InvalidModeratorPINFormatFaultException exception = new InvalidModeratorPINFormatFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        }

        int recurring = 0;
        if (createScheduledRoomRequest.getRecurring() != null) {
            recurring = createScheduledRoomRequest.getRecurring().getRecurring_type0();
        }

        SchRoomCreationRequest schRoomCreationRequest = new SchRoomCreationRequest();
        schRoomCreationRequest.setTenantId(member.getTenantID());
        schRoomCreationRequest.setMemberId(member.getMemberID());
        schRoomCreationRequest.setGroupId(member.getGroupID());
        schRoomCreationRequest.setMemberName(member.getMemberName());
        schRoomCreationRequest.setRecurring(recurring);
        schRoomCreationRequest.setPinRequired(createScheduledRoomRequest.getSetPIN());
        RoomCreationResponse roomCreationResponse = roomService.createScheduledRoom(schRoomCreationRequest);

        // If the response status is non-zero [failure], check the status code
        // and return fault
        if (roomCreationResponse.getStatus() != 0) {
            ScheduledRoomCreationFault fault = new ScheduledRoomCreationFault();
            fault.setErrorMessage(roomCreationResponse.getMessage());
            ScheduledRoomCreationFaultException exception = new ScheduledRoomCreationFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        CreateScheduledRoomResponse createScheduledRoomResponse = new CreateScheduledRoomResponse();
        Extension_type1 extn = new Extension_type1();
        extn.setExtension_type0(roomCreationResponse.getExtensionValue());
        createScheduledRoomResponse.setExtension(extn);

        // Scheduled Room - values customization
        roomCreationResponse.getRoom().setRoomName(roomCreationResponse.getExtensionValue());
        Locale l = Locale.getDefault();
		String content = this.systemService.getTenantInvitationEmailContent();
		if (content == null || content.trim().length() == 0) {
			content = this.systemService.getSuperInvitationEmailContent();
			if (content == null || content.trim().length() == 0) {
				content = this.ms.getMessage("defaultInvitationEmailContent", null, "", l);
			}
		}
        String inviteContent = constructEmailInviteContent(roomCreationResponse.getRoom(),
                roomCreationResponse.getTenant().getTenantDialIn(),
                (roomCreationResponse.getTenant().getGuestLogin() == 1),
                roomCreationResponse.getTenant().getTenantURL(), roomCreationResponse.getTenant().getVidyoGatewayControllerDns(), false,
                content, false);
        createScheduledRoomResponse.setInviteContent(inviteContent);
        String inviteSubject = systemService.constructEmailInviteSubjectForInviteRoom(Locale.getDefault());
        createScheduledRoomResponse.setInviteSubject(inviteSubject);

        URI uri = null;
        try {
            String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
            String joinURL = roomService.getRoomURL(systemService, transportName,
					roomCreationResponse.getTenant().getTenantURL(), roomCreationResponse.getRoom().getRoomKey());

			uri = new URI(joinURL);
        } catch (URI.MalformedURIException e) {
            logger.error("Error", e);
        }
        createScheduledRoomResponse.setRoomURL(uri);
        if(roomCreationResponse.getRoom().getRoomPIN() != null) {
            Pin_type1 pin = new Pin_type1();
            pin.setPin_type0(roomCreationResponse.getRoom().getRoomPIN());
            createScheduledRoomResponse.setPin(pin);
        }

        // set moderator pin
        if (!StringUtils.isEmpty(moderatorPIN)) {
            roomCreationResponse.getRoom().setModeratorPinSetting("enter");
            roomCreationResponse.getRoom().setRoomModeratorPIN(moderatorPIN);
            roomService.updateRoomModeratorPIN(roomCreationResponse.getRoom());
        }
        
     // Add the HTML invite content to the Response if it is requested to be included
        if (createScheduledRoomRequest.getReturnHtmlContent()){
        	// Get tenant level html content
        	String emailContentHtml = systemService.getConfigValue(TenantContext.getTenantId(), "InvitationEmailContentHtml");
        	if (StringUtils.isBlank(emailContentHtml)) {
        		// Since tenant does not have if, get it from super
        		emailContentHtml = this.systemService.getConfigValue(1, "SuperInvitationEmailContentHtml");
    			if (StringUtils.isBlank(emailContentHtml)) {
    				emailContentHtml = this.ms.getMessage("defaultInvitationEmailContentHtml", null, "", Locale.getDefault());
    			}
    		}
       		// As the html content is configured, contruct the content and replace tags
        	emailContentHtml = constructEmailInviteContent(roomCreationResponse.getRoom(), roomCreationResponse.getTenant().getTenantDialIn(), 
        			(roomCreationResponse.getTenant().getGuestLogin() == 1),
        			roomCreationResponse.getTenant().getTenantURL(), roomCreationResponse.getTenant().getVidyoGatewayControllerDns(), false, emailContentHtml, true);
        	createScheduledRoomResponse.setHtmlContent(emailContentHtml);
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting createScheduledRoom() of Admin API v.1.1");

        return createScheduledRoomResponse;
    }

    private String constructEmailInviteContent(Room room,
                                               String tenantDialIn, boolean guestsAllowed, String tenantUrl, String gateWayDns, boolean overrideScheduledRoomProperties,
                                               String content, boolean encodeForHtml) {

        InviteEmailContentForInviteRoom inviteEmailContentForInviteRoom = new InviteEmailContentForInviteRoom();
        inviteEmailContentForInviteRoom.setGateWayDns(gateWayDns);
        inviteEmailContentForInviteRoom.setGuestsAllowed(guestsAllowed);
        inviteEmailContentForInviteRoom.setLocale(Locale.getDefault());
        inviteEmailContentForInviteRoom.setOverrideScheduledRoomProperties(overrideScheduledRoomProperties);
        inviteEmailContentForInviteRoom.setRoom(room);
        inviteEmailContentForInviteRoom.setTenantDialIn(tenantDialIn);
        inviteEmailContentForInviteRoom.setTenantUrl(tenantUrl);
        inviteEmailContentForInviteRoom.setTransportName(MessageContext.getCurrentMessageContext().getIncomingTransportName());

        content = this.systemService.constructEmailInviteContentForInviteRoom(inviteEmailContentForInviteRoom, content, encodeForHtml);

        return content;

    }

    /**
	 * Auto generated method signature
	 *
	 * @param removeModeratorPINRequest
	 * @throws NotLicensedFaultException :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException :
	 */
	@Override
	public RemoveModeratorPINResponse removeModeratorPIN
		(
			RemoveModeratorPINRequest removeModeratorPINRequest
		)
		throws
		NotLicensedFaultException,
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		if (logger.isDebugEnabled())
			logger.debug("Entering removeModeratorPIN() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = removeModeratorPINRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoomDetailsForConference(roomID);
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room not exist for roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if(room == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid roomID = " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		room.setModeratorPinSetting("remove");
		roomService.updateRoomModeratorPIN(room);

		RemoveModeratorPINResponse resp = new RemoveModeratorPINResponse();
		resp.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting removeModeratorPIN() of Admin API v.1.1");

		return resp;
	}

	@Override
	public EnableRoomResponse enableRoom(EnableRoomRequest enableRoomRequest) throws NotLicensedFaultException, RoomNotFoundFaultException, InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled())
			logger.debug("Entering enableRoom() of Admin API v.1.1");

		User user = this.userService.getLoginUser();

		int roomID = 0;
		try {
			roomID = enableRoomRequest.getRoomID().getEntityID();
		} catch (java.lang.Exception e) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("RoomID is invalid " + roomID);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		Room room;
		try {
			room = roomService.getRoom(roomID);
		} catch (java.lang.Exception e) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("Room not found for roomID = " + roomID);
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (room == null) {
			RoomNotFoundFault fault = new RoomNotFoundFault();
			fault.setErrorMessage("Room not found for roomID = " + roomID);
			RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Room for roomID = " + roomID + " belongs to other tenant");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		room.setRoomEnabled(enableRoomRequest.getEnabled() ? 1 : 0);

		EnableRoomResponse resp = new EnableRoomResponse();

		if (roomService.updateRoom(roomID, room) > 1) {
			resp.setOK(OK_type0.OK);
		} else {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Unable to update room");
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting enableRoom() of Admin API v.1.1");

		return resp;
	}

	/**
     * Auto generated method signature
     *
     * @param getConferenceIDRequest36
     * @return getConferenceIDResponse37
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     */
    @Override
    public GetConferenceIDResponse getConferenceID(GetConferenceIDRequest getConferenceIDRequest36)
        throws NotLicensedFaultException,InvalidArgumentFaultException,InPointToPointCallFaultException,GeneralFaultException{
		if (logger.isDebugEnabled()) {
			logger.debug("Entering getConferenceID() of Admin API v.1.1");
		}

		try {
			int memberID = 0;
			String userName = getConferenceIDRequest36.getUserName();
			if(userName != null && userName.length() > 0) {
				try {
					com.vidyo.bo.Member member = memberService.getMemberByName(userName);
					memberID = member.getMemberID();
				} catch(Exception ex) {
					logger.error("Failed to get member based on userName = " + userName + " in getConferenceID.", ex);

					InvalidArgumentFault fault = new InvalidArgumentFault();
					fault.setErrorMessage("Failed to get member based on userName = " + userName + " in getConferenceID.");
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
					exception.setFaultMessage(fault);
					throw exception;
				}
			} else {
				User user = userService.getLoginUser();
				memberID = user.getMemberID();
			}
			GetConferenceIDResponse response = new GetConferenceIDResponse();

			Control control = conferenceService.getControlForMember(memberID, null);
			if(control.getConferenceType().equalsIgnoreCase("P")) {
				InPointToPointCallFault fault = new InPointToPointCallFault();
				fault.setErrorMessage("User is in P2P call");
				InPointToPointCallFaultException ex = new InPointToPointCallFaultException(fault.getErrorMessage());
				ex.setFaultMessage(fault);
				throw ex;
			}

			EntityID entityID = new EntityID();
			entityID.setEntityID(control.getRoomID());

			response.setConferenceID(entityID);

			if (logger.isDebugEnabled()) {
				logger.debug("Exiting getConferenceID() of Admin API v.1.1");
			}

			return response;
		} catch(InPointToPointCallFaultException ex) {
			logger.error("Failed to getConferenceID.", ex);
			throw ex;
		} catch(InvalidArgumentFaultException iafEx) {
			throw iafEx;
		} catch(Exception anyEx) {
			logger.error("Failed to getConferenceID.", anyEx);

			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Failed to getConferenceID.");
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
    }

	/**
	 * Returns an indicator if the scheduled room feature is enabled for the
	 * tenant.
	 *
	 * @throws NotLicensedFaultException
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */
	@Override
	public ScheduledRoomEnabledResponse isScheduledRoomEnabled(
			ScheduledRoomEnabledRequest scheduledRoomEnabledRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException {

		Configuration scheduledRoomconfig = systemService
				.getConfiguration("SCHEDULED_ROOM_PREFIX");

		ScheduledRoomEnabledResponse scheduledRoomEnabledResponse = new ScheduledRoomEnabledResponse();

		// If no prefix is set, return false
		if (scheduledRoomconfig == null
				|| scheduledRoomconfig.getConfigurationValue() == null
				|| scheduledRoomconfig.getConfigurationValue().trim().length() == 0) {
			scheduledRoomEnabledResponse.setScheduledRoomEnabled(false);
			return scheduledRoomEnabledResponse;
		}

		// If prefix is available, check the Tenant setting
		MessageContext messageContext = MessageContext
				.getCurrentMessageContext();
		// Getting HttpServletRequest from Message Context
		Object requestProperty = messageContext
				.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		if (requestProperty != null
				&& requestProperty instanceof HttpServletRequest) {
			Integer tenantId = TenantContext.getTenantId();
			if (tenantId != null) {
				Tenant tenant = tenantService.getTenant(tenantId);
				scheduledRoomEnabledResponse.setScheduledRoomEnabled(tenant
						.getScheduledRoomEnabled() == 1);
			}
		}

		return scheduledRoomEnabledResponse;
	}

	/**
	 * Enables/Disables Scheduled Room feature for the Tenant.
	 *
	 * @throws NotLicensedFaultException
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */
	@Override
	public DisableScheduledRoomResponse disableScheduledRoom(
			DisableScheduledRoomRequest disableScheduledRoomRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException {

		int disableSchRoom = disableScheduledRoomRequest
				.getDisableScheduledRoom() ? 0 : 1;

		Integer tenantId = TenantContext.getTenantId();

		int updateCount = 0;

		if (tenantId != null) {
			updateCount = tenantService.updateTenantScheduledRoomFeature(disableSchRoom,
					tenantId);
		}

		if (updateCount == 0) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Failed to Update the Tenant");
			GeneralFaultException exception = new GeneralFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		DisableScheduledRoomResponse disableScheduledRoomResponse = new DisableScheduledRoomResponse();
		disableScheduledRoomResponse.setOK(OK_type0.OK);

		return disableScheduledRoomResponse;
	}

	@Override
	public SetPresenterResponse setPresenter(SetPresenterRequest setPresenterRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {
		if (logger.isDebugEnabled())
			logger.debug("Entering setPresenter() of Admin API v.1.1");

		int roomEntityID = setPresenterRequest.getConferenceID().getEntityID();

		String moderatorPIN = "noModeratorPIN";

		int endpointID = setPresenterRequest.getParticipantID().getEntityID();

		LectureModeParticipantControlRequest serviceRequest = new LectureModeParticipantControlRequest();
		serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
		serviceRequest.setTenantID(TenantContext.getTenantId());
		serviceRequest.setRoomID(roomEntityID);
		serviceRequest.setModeratorPIN(moderatorPIN);
		serviceRequest.setEndpointID(endpointID);

		LectureModeControlResponse serviceResponse = lectureModeService.setPresenter(serviceRequest);

		int status =  serviceResponse.getStatus();
		if (status == ConferenceControlResponse.INVALID_ROOM) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid ConferenceId");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.PARTICIPANT_NOT_IN_CONFERENCE) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Participant not found in Conference");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status == LectureModeControlResponse.LECTURE_MODE_NOT_STARTED) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Lecture mode is not on for conference");
			GeneralFaultException exception = new GeneralFaultException("Lecture mode is not on for conference");
			exception.setFaultMessage(fault);
			throw exception;
		} else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Server internal error");
			GeneralFaultException exception = new GeneralFaultException("Server internal error");
			exception.setFaultMessage(fault);
			throw exception;
		}

		SetPresenterResponse response = new SetPresenterResponse();
		response.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting setPresenter() of Admin API v.1.1");

		return response;

	}

    @Override
    public SetChatStateAdminResponse setChatStateAdmin(SetChatStateAdminRequest setChatStateAdminRequest)
        throws NotLicensedFaultException,GeneralFaultException,ChatNotAvailableInSuperFaultException{

    	if (logger.isDebugEnabled())
			logger.debug("Entering setChatStateAdmin() of Admin API v.1.1");

    	if(!systemService.isChatAvailable()) {
    		logger.error("Chat was set to \"Unavailable\" by the Super Admin and therefore chat states cannot be updated.");
    		ChatNotAvailableInSuperFault fault = new ChatNotAvailableInSuperFault();
			fault.setErrorMessage("Chat was set to \"Unavailable\" by the Super Admin and therefore chat states cannot be updated.");
			ChatNotAvailableInSuperFaultException exception = new ChatNotAvailableInSuperFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
    	}

		User user = this.userService.getLoginUser();
		int tenantID = TenantContext.getTenantId();

		Integer setPrivateChatState = null;
		if(setChatStateAdminRequest.isSetPrivateChatStateSpecified()) {
			if(setChatStateAdminRequest.getSetPrivateChatState()) {
				setPrivateChatState = 1;
			} else {
				setPrivateChatState = 0;
			}
		}

		Integer setPublicChatState = null;
		if(setChatStateAdminRequest.isSetPublicChatStateSpecified()) {
			if(setChatStateAdminRequest.getSetPublicChatState()) {
				setPublicChatState = 1;
			} else {
				setPublicChatState = 0;
			}
		}

		if(setPrivateChatState != null || setPublicChatState != null) {
			TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(tenantID);

			int endpointPrivateChat = tenantConfiguration.getEndpointPrivateChat();
			int endpointPublicChat = tenantConfiguration.getEndpointPublicChat();

			if(setPrivateChatState != null) {
				endpointPrivateChat = setPrivateChatState;
			}

			if(setPublicChatState != null) {
				endpointPublicChat = setPublicChatState;
			}

			int updateCount = tenantService.updateEndpointChatsStatuses(tenantID, endpointPrivateChat, endpointPublicChat);
			if(updateCount == 0) {
				logger.error("Failed to update chat state for Tenant : " + tenantID );
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("Failed to update chat state for the tenant");
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		SetChatStateAdminResponse setChatStateAdminResponse = new SetChatStateAdminResponse();
		setChatStateAdminResponse.setOK(OK_type0.OK);

		if (logger.isDebugEnabled())
			logger.debug("Exiting setChatStateAdmin() of Admin API v.1.1");

		return setChatStateAdminResponse;
    }

    /**
     * Auto generated method signature
     *
     * @param setTenantRoomAttributesRequest
     * @throws NotLicensedFaultException :
     * @throws GeneralFaultException :
     */
    public SetTenantRoomAttributesResponse setTenantRoomAttributes(SetTenantRoomAttributesRequest setTenantRoomAttributesRequest)
        throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException {

        if (logger.isDebugEnabled())
            logger.debug("Entering setTenantRoomAttributes() of Admin API v.1.1");

		TenantConfiguration tenantConfiguration = this.tenantService.getTenantConfiguration(TenantContext.getTenantId());
		if (tenantConfiguration.getLectureModeAllowed() == 0) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Lecture mode feature disabled by tenant admin.");
			GeneralFaultException exception = new GeneralFaultException("Lecture mode feature disabled by tenant admin.");
			exception.setFaultMessage(fault);
			throw exception;
		}

        Integer waitingRoomState = null;
        if(setTenantRoomAttributesRequest.isSetWaitingRoomStateSpecified()) {
            waitingRoomState = setTenantRoomAttributesRequest.getSetWaitingRoomState();
            if(waitingRoomState != 0 && waitingRoomState != 1 && waitingRoomState != 2) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid setWaitingRoomState");
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid setWaitingRoomState");
                exception.setFaultMessage(fault);
                throw exception;
            }
        }

        Boolean supportedClientsOnly = null;
        if(setTenantRoomAttributesRequest.isSupportedClientsOnlySpecified()) {
            supportedClientsOnly = setTenantRoomAttributesRequest.getSupportedClientsOnly();
        }

        if(waitingRoomState != null || supportedClientsOnly != null) {
			int lectureModeAllowed = tenantConfiguration.getLectureModeAllowed();
            int waitingRoomsEnabled = tenantConfiguration.getWaitingRoomsEnabled();
            int waitUntilOwnerJoins = tenantConfiguration.getWaitUntilOwnerJoins();
            int lectureModeStrict = tenantConfiguration.getLectureModeStrict();

            if(waitingRoomState != null) {
                if(waitingRoomState == 0) {
                    waitingRoomsEnabled = 0;
                    waitUntilOwnerJoins = 0;
                } else if(waitingRoomState == 1) {
                    waitingRoomsEnabled = 1;
                    waitUntilOwnerJoins = 1;
                } else if(waitingRoomState == 2) {
                    waitingRoomsEnabled = 1;
                    waitUntilOwnerJoins = 0;
                }
            }

            if(supportedClientsOnly != null) {
                if(supportedClientsOnly) {
                    lectureModeStrict = 1;
                } else {
                    lectureModeStrict = 0;
                }
            }

            int updateCount = tenantService.setTenantRoomAttributes(TenantContext.getTenantId(),
					lectureModeAllowed == 0 ? false : true,
					waitingRoomsEnabled == 0 ? false : true,
                    waitUntilOwnerJoins == 0 ? false : true,
					lectureModeStrict == 0 ? false : true);

            if(updateCount == 0) {
                logger.error("Failed to update room attributes for Tenant : " + TenantContext.getTenantId());
                GeneralFault fault = new GeneralFault();
                fault.setErrorMessage("Failed to update room attributes for the tenant");
                GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }

        }


        if (logger.isDebugEnabled())
            logger.debug("Exiting setTenantRoomAttributes() of Admin API v.1.1");

        SetTenantRoomAttributesResponse response = new SetTenantRoomAttributesResponse();
        response.setOK(OK_type0.OK);

        return response;
    }

	/**
	 *
	 * @return
	 */
    private String getIPAddress() {
        String ipAddress = null;
        try {
           Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();

           while (e.hasMoreElements()) {
              NetworkInterface ni = (NetworkInterface) e.nextElement();
              if (ni.getName().equals("eth0")) {
                 if(logger.isDebugEnabled()) {
                    logger.debug("Net interface: " + ni.getName());
                 }

                 Enumeration<InetAddress> e2 = ni.getInetAddresses();
                 while (e2.hasMoreElements()) {
                    InetAddress ip = (InetAddress) e2.nextElement();
                    if (ip instanceof Inet4Address) {
                       ipAddress = ip.toString().replaceAll("/", "");
                    }
                 }
              }
           }
        } catch (Exception e) {
           logger.error(e.getMessage(), e);
        }

        return ipAddress;
     }

	private void validateAndSetFilterLimitParam(BaseQueryFilter filter, Filter_type0 input_param) throws InvalidArgumentFaultException {
		if (input_param.getLimit() >= 0) {
			if(input_param.getLimit() <= BaseQueryFilter.FILTER_MAX_LIMIT) {
				if(input_param.getLimit() > BaseQueryFilter.FILTER_DEFAULT_LIMIT) {
					logger.info("ATTENTION!!! Usage of limit equal to " + input_param.getLimit() + " MAY HAVE performance impact");
				}
				filter.setLimit(input_param.getLimit());
			} else {
				String errMsg = "Requested limit exceeds the allowed threshold of " + BaseQueryFilter.FILTER_MAX_LIMIT + " entries. Please try again.";

				logger.error(errMsg);

				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage(errMsg);
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		} else {
			filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
		}
	}


	@Override
	public GetTenantDetailsResponse getTenantDetails(
			GetTenantDetailsRequest getTenantDetailsRequest)
			throws InvalidArgumentFaultException, GeneralFaultException,
			NotLicensedFaultException {

		if (logger.isDebugEnabled())
			logger.debug("Entering getTenantDetails() of Admin API v.1.1");

		int tenantID = TenantContext.getTenantId();
		GetTenantDetailsResponse resp = new GetTenantDetailsResponse();
		Tenant tenant = tenantService.getTenant(tenantID);
		// no null condition needed as it is a must that tenant object should be valid
		resp.setName(tenant.getTenantName());
		resp.setPrefix(tenant.getTenantPrefix());
		if (tenant.getEndpointUploadMode() != null && tenant.getEndpointUploadMode().equalsIgnoreCase("External")) {
			resp.setExternalEndpointSoftwareFileserver(true);
		} else {
			resp.setExternalEndpointSoftwareFileserver(false);
		}

		if (logger.isDebugEnabled())
			logger.debug("Exiting getTenantDetails() of Admin API v.1.1");

		return resp;
	}

	@Override
	public CreatePublicRoomResponse createPublicRoom(CreatePublicRoomRequest createPublicRoomRequest)
			throws PublicRoomCreationFaultException, InvalidArgumentFaultException, NotAllowedToCreateFaultException, GeneralFaultException,
			NotLicensedFaultException {

		User user = userService.getLoginUser();
		if (user == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid User");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (createPublicRoomRequest.getDisplayName() == null || createPublicRoomRequest.getDisplayName().trim().isEmpty()
				|| createPublicRoomRequest.getDisplayName().trim().length() > 80) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Display Name (must be 2 - 80 characters).");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		String displayName = createPublicRoomRequest.getDisplayName().trim();

		String ownerName = createPublicRoomRequest.getOwnerName();
		if (ownerName == null || ownerName.trim().isEmpty()) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Owner Name");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		com.vidyo.bo.Member member = memberService.getMemberByName(ownerName);
		if (member == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid User");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}
		//Check if the Tenant is allowing to create
		int roomsAllowedToCreate = roomService.getUserCreatePublicRoomAllowed(member.getTenantID());

		if (roomsAllowedToCreate <= 0) {
			NotAllowedToCreateFault fault = new NotAllowedToCreateFault();
			fault.setErrorMessage("You are not allowed to create room for the users.");
			NotAllowedToCreateFaultException exception = new NotAllowedToCreateFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		int roomsCreated = roomService.getUserCreatePublicRoomCount(member.getTenantID() , member.getMemberID());

		if ((roomsAllowedToCreate - roomsCreated) <= 0){
			PublicRoomCreationFault fault = new PublicRoomCreationFault();
			fault.setErrorMessage("All public rooms are already created for member as per the limit.");
			PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		long roomRemainingCount = roomService.getTenantCreatePublicRoomRemainCount(member.getTenantID());
		if (roomRemainingCount <= 0){
			PublicRoomCreationFault fault = new PublicRoomCreationFault();
			fault.setErrorMessage("All public rooms are already created as per the limit for the Tenant.");
			PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		long totalPublicRoomsRemain = roomService.getSystemCreatePublicRoomRemainCount();
		if (totalPublicRoomsRemain <= 0){
			PublicRoomCreationFault fault = new PublicRoomCreationFault();
			fault.setErrorMessage("All public rooms are already created as per the limit of the System.");
			PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		int displayNameCount = roomService.getDisplayNameCount(displayName, member.getTenantID());
		if (displayNameCount > 0){
			PublicRoomCreationFault fault = new PublicRoomCreationFault();
			fault.setErrorMessage("Display name already exists. Please use different display name.");
			PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		String roomName = displayName.replaceAll(" +", "_").replaceAll("[~`!@#$%^&*\\(\\)+={}\\[\\]\\|\\\\:;\"\'<>,\\?/]", "");
		if (roomName.isEmpty() || roomName.equals("-") || roomName.equals("_") || roomName.length() < 2){
			PublicRoomCreationFault fault = new PublicRoomCreationFault();
			fault.setErrorMessage("Display name contains too few alphanumeric characters.");
			PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		if (roomService.isRoomExistForRoomName(roomName, 0)) {
			PublicRoomCreationFault fault = new PublicRoomCreationFault();
			fault.setErrorMessage("Room exist for name = " + roomName);
			PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		PublicRoomCreateRequest publicRoomCreateRequest = new PublicRoomCreateRequest();
		publicRoomCreateRequest.setTenantId(member.getTenantID());
		publicRoomCreateRequest.setMemberId(member.getMemberID());
		publicRoomCreateRequest.setGroupId(member.getGroupID());
		publicRoomCreateRequest.setMemberName(member.getMemberName());
		publicRoomCreateRequest.setDisplayName(displayName);
		publicRoomCreateRequest.setRoomName(roomName);
		publicRoomCreateRequest.setInMyContacts(createPublicRoomRequest.getSetInMyContacts());
		publicRoomCreateRequest.setLocked(createPublicRoomRequest.getSetLocked());
		publicRoomCreateRequest.setDescription(createPublicRoomRequest.getDescription());

		if (createPublicRoomRequest.getSetPIN() != null && !createPublicRoomRequest.getSetPIN().isEmpty()) {
			if ((createPublicRoomRequest.getSetPIN().replaceAll("[0-9]", "")).length() > 0
					|| !systemService.isPINLengthAcceptable(TenantContext.getTenantId(), createPublicRoomRequest.getSetPIN())){
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("Invalid PIN. PIN should be a "
						+ systemService.getMinPINLengthForTenant(TenantContext.getTenantId())
						+ "-" + SystemServiceImpl.PIN_MAX + " digit number");
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
						fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
			publicRoomCreateRequest.setPinRequired(true);
			publicRoomCreateRequest.setPIN(createPublicRoomRequest.getSetPIN());
		}

		RoomCreationResponse roomCreationResponse = roomService.createPublicRoom(publicRoomCreateRequest);

		// If the response status is non-zero [failure], check the status code
		// and return fault
		if (roomCreationResponse.getStatus() != 0) {
			PublicRoomCreationFault fault = new PublicRoomCreationFault();
			fault.setErrorMessage(roomCreationResponse.getMessage());
			PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
					fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		CreatePublicRoomResponse createPublicRoomResponse = new CreatePublicRoomResponse();

		EntityID entity_id = new EntityID();
		entity_id.setEntityID(roomCreationResponse.getRoom().getRoomID());
		createPublicRoomResponse.setRoomID(entity_id);

		Extension_type5 extn = new Extension_type5();
		extn.setExtension_type4(roomCreationResponse.getExtensionValue());
		createPublicRoomResponse.setExtension(extn);

		// Scheduled Room - values customization
		roomCreationResponse.getRoom().setRoomName(roomCreationResponse.getExtensionValue());

		URI uri = null;
		try {
			String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
			String joinURL = roomService.getRoomURL(systemService, transportName,
					roomCreationResponse.getTenant().getTenantURL(), roomCreationResponse.getRoom().getRoomKey());

			uri = new URI(joinURL);
		} catch (MalformedURIException e) {
			logger.error("Error", e);
		}
		createPublicRoomResponse.setRoomURL(uri);

		// Add Entry in speed dial if flag is set
		if (publicRoomCreateRequest.isInMyContacts()){
			memberService.addSpeedDialEntry(roomCreationResponse.getRoom().getMemberID(), roomCreationResponse.getRoom().getRoomID());
		}
		createPublicRoomResponse.setInMyContacts(publicRoomCreateRequest.isInMyContacts());



		createPublicRoomResponse.setLocked(roomCreationResponse.getRoom().getRoomLocked() == 0 ? false:true);

		if(roomCreationResponse.getRoom().getRoomPIN() != null) {
			createPublicRoomResponse.setHasPIN(true);
		} else {
			createPublicRoomResponse.setHasPIN(false);
		}

		return createPublicRoomResponse;
	}

	@Override
	public SearchMembersResponse searchMembers(SearchMembersRequest searchMembersRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, NotLicensedFaultException {
		com.vidyo.bo.MemberFilter filter = new com.vidyo.bo.MemberFilter();
		if(searchMembersRequest.getMemberFilter() != null) {
			if(StringUtils.isNotBlank(searchMembersRequest.getMemberFilter().getMemberName())) {
				filter.setMemberName(searchMembersRequest.getMemberFilter().getMemberName());
			}
			if(StringUtils.isNotBlank(searchMembersRequest.getMemberFilter().getRoomExtension())) {
				filter.setExt(searchMembersRequest.getMemberFilter().getRoomExtension());
			}
			if (searchMembersRequest.getMemberFilter().getRoleName() != null
					&& StringUtils.isNotBlank(searchMembersRequest.getMemberFilter().getRoleName().getValue())) {
				filter.setType(
						searchMembersRequest.getMemberFilter().getRoleName().getValue().equalsIgnoreCase("Panorama")
								? "VidyoPanorama" : searchMembersRequest.getMemberFilter().getRoleName().getValue());
			}
			if(StringUtils.isNotBlank(searchMembersRequest.getMemberFilter().getStatus())) {
				filter.setUserStatus(searchMembersRequest.getMemberFilter().getStatus());
			}
			if(StringUtils.isNotBlank(searchMembersRequest.getMemberFilter().getGroupName())) {
				filter.setGroupName(searchMembersRequest.getMemberFilter().getGroupName());
			}
		}
		if(searchMembersRequest.getSortDir() != null && StringUtils.isNotBlank(searchMembersRequest.getSortDir().getValue())) {
			filter.setDir(searchMembersRequest.getSortDir().getValue());
		}

		if(StringUtils.isNotBlank(searchMembersRequest.getSortBy())) {
			filter.setSort(searchMembersRequest.getSortBy());
		}

		if(searchMembersRequest.getStart() != null && searchMembersRequest.getStart().intValue() > 0) {
			filter.setStart(searchMembersRequest.getStart().intValue());
		}

		if(searchMembersRequest.getLimit() != null && searchMembersRequest.getLimit().intValue() > 0) {
			filter.setLimit(searchMembersRequest.getLimit().intValue());
		}

		int totalCount = memberService.countMembers(TenantContext.getTenantId(), filter);
		SearchMembersResponse membersResponse = new SearchMembersResponse();
		if(totalCount > 0) {
			Configuration userImageConf = systemService.getConfiguration("USER_IMAGE");
			TenantConfiguration tenantConfiguration = this.tenantService
					.getTenantConfiguration(TenantContext.getTenantId());
			List<com.vidyo.bo.Member> members = memberService.searchMembers(TenantContext.getTenantId(), filter);
			for (com.vidyo.bo.Member member : members) {
				Member ws_member = getWSMemberFromBOMember(member, userImageConf, tenantConfiguration);
				membersResponse.addMember(ws_member);
			}
		}
		membersResponse.setLimit(BigInteger.valueOf(filter.getLimit()));
		membersResponse.setStart(BigInteger.valueOf(filter.getStart()));
		membersResponse.setSortBy(filter.getSort());
		membersResponse.setSortDir(new SortDir(filter.getDir(), false));
		MemberFilter_type0 filter_type0 = new MemberFilter_type0();
		BeanUtils.copyProperties(filter, filter_type0);
		membersResponse.setMemberFilter(filter_type0);
		membersResponse.setTotal(totalCount);
		return membersResponse;
	}

	@Override
	public AddClientVersionResponse addClientVersion(AddClientVersionRequest addClientVersionRequest)
			throws InvalidArgumentFaultException, GeneralFaultException,ExternalModeFaultException {
		if (logger.isDebugEnabled()) {
            logger.debug("Entering addClientVersion() of VidyoPortalAdminServiceSkeleton");
        }

        Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
        String tenantName = tenant != null ? tenant.getTenantName():"Unknown";

		AddClientVersionResponse response = new AddClientVersionResponse();
		// Get input parameters
        ClientType clientType = addClientVersionRequest.getClientType();
        String installURL = addClientVersionRequest.getInstallerURL();
        String currentTag = addClientVersionRequest.getCurrentTag().getEndpointVersionPattern();
        boolean setActive = addClientVersionRequest.getSetActive();

        try {
		    TenantConfiguration endpointUploadModeConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
	    	if (endpointUploadModeConfiguration == null || endpointUploadModeConfiguration.getEndpointUploadMode() == null
	    			|| !endpointUploadModeConfiguration.getEndpointUploadMode().equalsIgnoreCase("External")){
				ExternalModeFault fault = new ExternalModeFault();
	            fault.setErrorMessage("Endpoint Upload mode is not set to External File Server");
	            ExternalModeFaultException ex = new ExternalModeFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
			}

			if (clientType == null || StringUtils.isBlank(clientType.getValue())){
				InvalidArgumentFault fault = new InvalidArgumentFault();
	            fault.setErrorMessage("Client Type can not be blank");
	            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
			}
			if (installURL == null || installURL.length() > 1024 || !ValidatorUtil.isValidHTTPHTTPSURL(installURL)) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
	            fault.setErrorMessage("Invalid install URL.");
	            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
			}
			if (currentTag == null || currentTag.replaceAll("[a-zA-Z0-9._]", "").length() > 0
					|| currentTag.length() > 128){
				InvalidArgumentFault fault = new InvalidArgumentFault();
	            fault.setErrorMessage("Invalid Current Tag");
	            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
			}
        	EndpointUpload endpoint = new EndpointUpload();
    		endpoint.setEndpointUploadFile(installURL);
    		endpoint.setEndpointUploadVersion(currentTag);
    		endpoint.setEndpointUploadType(clientType.getValue().trim());
    		endpoint.setTenantID(TenantContext.getTenantId());

    		int endpointId = endpointUploadService.addEndpointUpload(TenantContext.getTenantId(), endpoint, setActive);

    		response.setEndpointUploadID(java.math.BigInteger.valueOf(endpointId));

    		auditLogTransaction("New client version added by Admin","installURL:"+installURL
    				+ " currentTag:"+ currentTag + " clientType:"+clientType.getValue(),tenantName, SUCCESS);
            if(logger.isDebugEnabled()) {
               logger.debug("Exiting addClientVersion() of VidyoPortalAdminServiceSkeleton");
            }
        } catch(Exception anyEx) {
        	auditLogTransaction("New client version added by Super","installURL:"+installURL
    				+ " currentTag:"+ currentTag + " clientType:"+clientType.getValue(),tenantName, FAILURE);
            logger.error("Failed to  add client version.", anyEx);
            throw anyEx;
        }
        return response;
    }


    /**
     * Auto generated method signature
     * For adding new end point behavior at tenant level
     * @param createEndpointBehaviorRequest
     * @return createEndpointBehaviorResponse
     * @throws InvalidArgumentFaultException
     * @throws EndpointBehaviorAlreadyExistsFaultException
     * @throws EndpointBehaviorDisabledFaultException
     * @throws GeneralFaultException
     */
     public CreateEndpointBehaviorResponse createEndpointBehavior  (
    		 CreateEndpointBehaviorRequest createEndpointBehaviorRequest)
        throws InvalidArgumentFaultException,EndpointBehaviorAlreadyExistsFaultException,EndpointBehaviorDisabledFaultException,GeneralFaultException{
    	 if (logger.isDebugEnabled()) {
             logger.debug("Entering createEndpointBehavior() of VidyoPortalAdminServiceSkeleton");
         }

    	 if (! endpointBehaviorService.isEndpointBehaviorForTenant(TenantContext.getTenantId())) {
    		 EndpointBehaviorDisabledFault fault = new EndpointBehaviorDisabledFault();
    		 fault.setErrorMessage("EndpointBehavior is not enabled for the Tenant.");
    		 EndpointBehaviorDisabledFaultException ex = new EndpointBehaviorDisabledFaultException(fault.getErrorMessage());
    		 ex.setFaultMessage(fault);
    		 throw ex;
    	 }
    	 CreateEndpointBehaviorResponse createEndpointBehaviorResponse = new CreateEndpointBehaviorResponse();

    	 // While creating the EndpointBehavior, ONLY one EndpointBehavior per Tenant is allowed
    	 // This check will complete the validation if the EndpointBehavior is already created for the tenant
    	 EndpointBehavior endpointBehavior = null;
    	 List<EndpointBehavior> endpointBehaviors = endpointBehaviorService.getEndpointBehaviorByTenant(TenantContext.getTenantId());
		   if (endpointBehaviors != null && endpointBehaviors.size() > 0){
			   endpointBehavior = endpointBehaviors.get(0);
		   }

    	 if (endpointBehavior != null ){
    		 EndpointBehaviorAlreadyExistsFault fault = new EndpointBehaviorAlreadyExistsFault();
    		 fault.setErrorMessage("EndpointBehavior already exists for Tenant.");
    		 EndpointBehaviorAlreadyExistsFaultException ex = new EndpointBehaviorAlreadyExistsFaultException(fault.getErrorMessage());
    		 ex.setFaultMessage(fault);
    		 throw ex;
    	 }

    	 EndpointBehaviorDataType endpointBehaviorDataType = validateEndpointBehavior (createEndpointBehaviorRequest.getEndpointBehavior(), Operation.CREATE);

    	 // EndpointBehavior is valid and can be created
    	 endpointBehavior = endpointBehaviorService.makeEndpointBehaviorFromDataType(endpointBehaviorDataType, new EndpointBehavior());

    	 endpointBehavior.setEndpointBehaviorKey(endpointBehaviorService.generateEndpointBehaviorKey());
    	 endpointBehavior.setTenant(tenantService.getTenant(TenantContext.getTenantId()));
    	 endpointBehavior = endpointBehaviorService.saveEndpointBehavior(endpointBehavior);

    	 // Set the EndpointBehavior Key in response
    	 createEndpointBehaviorResponse.setEndpointBehaviorKey(endpointBehavior.getEndpointBehaviorKey());

    	 return createEndpointBehaviorResponse;
    }


      /**
		* Auto generated method signature
		* For updating end point behavior at tenant level
		* @param updateEndpointBehaviorRequest
		* @return updateEndpointBehaviorResponse
		* @throws InvalidArgumentFaultException
		* @throws NoEndpointBehaviorExistsFaultException
        * @throws EndpointBehaviorDisabledFaultException
		* @throws GeneralFaultException
		*/

      public UpdateEndpointBehaviorResponse updateEndpointBehavior
            (UpdateEndpointBehaviorRequest updateEndpointBehaviorRequest)
         throws InvalidArgumentFaultException,NoEndpointBehaviorExistsFaultException,EndpointBehaviorDisabledFaultException,GeneralFaultException {

     	 if (! endpointBehaviorService.isEndpointBehaviorForTenant(TenantContext.getTenantId())) {
     		EndpointBehaviorDisabledFault fault = new EndpointBehaviorDisabledFault();
	   		 fault.setErrorMessage("EndpointBehavior is not enabled for the Tenant.");
	   		 EndpointBehaviorDisabledFaultException ex = new EndpointBehaviorDisabledFaultException(fault.getErrorMessage());
	   		 ex.setFaultMessage(fault);
    		 throw ex;
    	 }

     	 UpdateEndpointBehaviorResponse updateEndpointBehaviorResponse = new UpdateEndpointBehaviorResponse();

    	  EndpointBehaviorDataType endpointBehaviorDataType = validateEndpointBehavior (updateEndpointBehaviorRequest.getEndpointBehavior(), Operation.UPDATE);

      	 EndpointBehavior ebFromDB = null;

     	  if (StringUtils.isNotBlank(endpointBehaviorDataType.getEndpointBehaviorKey())) {
 			ebFromDB = endpointBehaviorService.getEndpointBehaviorByKey(endpointBehaviorDataType.getEndpointBehaviorKey());
 		} else {
 			List<EndpointBehavior> endpointBehaviors = endpointBehaviorService.getEndpointBehaviorByTenant(TenantContext.getTenantId());
			   if (endpointBehaviors != null && endpointBehaviors.size() > 0){
				   ebFromDB = endpointBehaviors.get(0);
			   }
 		}

	   	  if (ebFromDB == null){
	   		 NoEndpointBehaviorExistsFault fault = new NoEndpointBehaviorExistsFault();
	  		 fault.setErrorMessage("Invalid EndpointBehavior for the Tenant.");
	  		 NoEndpointBehaviorExistsFaultException ex = new NoEndpointBehaviorExistsFaultException(fault.getErrorMessage());
	  		 ex.setFaultMessage(fault);
	  		 throw ex;
	   	  }

			endpointBehaviorDataType.setEndpointBehaviorKey(ebFromDB.getEndpointBehaviorKey());

			// Check if the values for Window Size, Position and Recording Role were specified while send input,
			// if they are not specified then use the one from database to preserve old values as is.
			
			endpointBehaviorDataType.setWindowSizeHeight(!endpointBehaviorDataType.isWindowSizeHeightSpecified() ?
						ebFromDB.getWindowSizeHeight():endpointBehaviorDataType.getWindowSizeHeight());

			endpointBehaviorDataType.setWindowSizeWidth(!endpointBehaviorDataType.isWindowSizeWidthSpecified() ?
						ebFromDB.getWindowSizeWidth():endpointBehaviorDataType.getWindowSizeWidth());

			endpointBehaviorDataType.setWindowPositionTop(!endpointBehaviorDataType.isWindowPositionTopSpecified() ?
						ebFromDB.getWindowPositionTop():endpointBehaviorDataType.getWindowPositionTop());

			endpointBehaviorDataType.setWindowPositionBottom(!endpointBehaviorDataType.isWindowPositionBottomSpecified() ?
						ebFromDB.getWindowPositionBottom():endpointBehaviorDataType.getWindowPositionBottom());

			endpointBehaviorDataType.setWindowPositionLeft(!endpointBehaviorDataType.isWindowPositionLeftSpecified() ? 
						ebFromDB.getWindowPositionLeft():endpointBehaviorDataType.getWindowPositionLeft());

			endpointBehaviorDataType.setWindowPositionRight(!endpointBehaviorDataType.isWindowPositionRightSpecified() ?
						ebFromDB.getWindowPositionRight():endpointBehaviorDataType.getWindowPositionRight());

			endpointBehaviorDataType.setRecordingRole(!endpointBehaviorDataType.isRecordingRoleSpecified() ? 
						ebFromDB.getRecordingRole():endpointBehaviorDataType.getRecordingRole());

			endpointBehaviorDataType.setPreIframeUrl(!endpointBehaviorDataType.isPreIframeUrlSpecified() ? 
						ebFromDB.getPreIframeUrl():endpointBehaviorDataType.getPreIframeUrl());

			endpointBehaviorDataType.setPreIframeSize(!endpointBehaviorDataType.isPreIframeSizeSpecified() ? 
						ebFromDB.getPreIframeSize():endpointBehaviorDataType.getPreIframeSize());

			endpointBehaviorDataType.setTopIframeUrl(!endpointBehaviorDataType.isTopIframeUrlSpecified() ? 
						ebFromDB.getTopIframeUrl():endpointBehaviorDataType.getTopIframeUrl());

			endpointBehaviorDataType.setTopIframeSize(!endpointBehaviorDataType.isTopIframeSizeSpecified() ? 
						ebFromDB.getTopIframeSize():endpointBehaviorDataType.getTopIframeSize());

			endpointBehaviorDataType.setBottomIframeUrl(!endpointBehaviorDataType.isBottomIframeUrlSpecified() ? 
						ebFromDB.getBottomIframeUrl():endpointBehaviorDataType.getBottomIframeUrl());

			endpointBehaviorDataType.setBottomIframeSize(!endpointBehaviorDataType.isBottomIframeSizeSpecified() ? 
						ebFromDB.getBottomIframeSize():endpointBehaviorDataType.getBottomIframeSize());

			endpointBehaviorDataType.setLeftIframeUrl(!endpointBehaviorDataType.isLeftIframeUrlSpecified() ? 
						ebFromDB.getLeftIframeUrl():endpointBehaviorDataType.getLeftIframeUrl());

			endpointBehaviorDataType.setLeftIframeSize(!endpointBehaviorDataType.isLeftIframeSizeSpecified() ? 
						ebFromDB.getLeftIframeSize():endpointBehaviorDataType.getLeftIframeSize());

			endpointBehaviorDataType.setRightIframeUrl(!endpointBehaviorDataType.isRightIframeUrlSpecified() ? 
						ebFromDB.getRightIframeUrl():endpointBehaviorDataType.getRightIframeUrl());

			endpointBehaviorDataType.setRightIframeSize(!endpointBehaviorDataType.isRightIframeSizeSpecified() ?
						ebFromDB.getRightIframeSize():endpointBehaviorDataType.getRightIframeSize());


	  	  // EndpointBehavior is valid and can be updated
	   	  ebFromDB = endpointBehaviorService.makeEndpointBehaviorFromDataType(endpointBehaviorDataType, ebFromDB);


		ebFromDB = endpointBehaviorService.saveEndpointBehavior(ebFromDB);

     	 updateEndpointBehaviorResponse.setOK(OK_type0.OK);

    	return updateEndpointBehaviorResponse;

      }

      /**
       	* Auto generated method signature
       	* For deleting end point behavior at tenant level
       	* @param deleteEndpointBehaviorRequest
       	* @return deleteEndpointBehaviorResponse
		* @throws InvalidArgumentFaultException
		* @throws NoEndpointBehaviorExistsFaultException
        * @throws EndpointBehaviorDisabledFaultException
		* @throws GeneralFaultException
		*/

       public DeleteEndpointBehaviorResponse deleteEndpointBehavior
           (DeleteEndpointBehaviorRequest deleteEndpointBehaviorRequest)
          throws InvalidArgumentFaultException,NoEndpointBehaviorExistsFaultException,EndpointBehaviorDisabledFaultException,GeneralFaultException {

      	 if (! endpointBehaviorService.isEndpointBehaviorForTenant(TenantContext.getTenantId())) {
      		EndpointBehaviorDisabledFault fault = new EndpointBehaviorDisabledFault();
	   		 fault.setErrorMessage("EndpointBehavior is not enabled for the Tenant.");
	   		 EndpointBehaviorDisabledFaultException ex = new EndpointBehaviorDisabledFaultException(fault.getErrorMessage());
	   		 ex.setFaultMessage(fault);
    		 throw ex;
    	 }

      	 DeleteEndpointBehaviorResponse deleteEndpointBehaviorResponse = new DeleteEndpointBehaviorResponse();

    	   String endpointBehaviorKey = deleteEndpointBehaviorRequest.getEndpointBehaviorKey();
    	   boolean isDeleted = false;
    	   if (StringUtils.isBlank(endpointBehaviorKey)){
    		   isDeleted = endpointBehaviorService.deleteEndpointBehaviorByTenantID(TenantContext.getTenantId());
    	   } else {
    		   isDeleted = endpointBehaviorService.deleteEndpointBehavior(endpointBehaviorKey);
    	   }

    	   if (!isDeleted){
    		   InvalidArgumentFault fault = new InvalidArgumentFault();
	            fault.setErrorMessage("Unable to delete EndpointBehavior for Tenant" +
    		                           ((StringUtils.isNotBlank(endpointBehaviorKey) ? " with EndpointBehavior Key :"+endpointBehaviorKey:".")));
	            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
    	   }
    	   deleteEndpointBehaviorResponse.setOK(OK_type0.OK);

    	   return deleteEndpointBehaviorResponse;

       }


      /**
      	* Auto generated method signature
      	* To Fetch end point behavior at tenant level
      	* @param getEndpointBehaviorRequest
      	* @return getEndpointBehaviorResponse
		* @throws InvalidArgumentFaultException
		* @throws NoEndpointBehaviorExistsFaultException
        * @throws EndpointBehaviorDisabledFaultException
		* @throws GeneralFaultException
		*/

      public GetEndpointBehaviorResponse getEndpointBehavior
          (GetEndpointBehaviorRequest getEndpointBehaviorRequest)
         throws InvalidArgumentFaultException,NoEndpointBehaviorExistsFaultException,EndpointBehaviorDisabledFaultException,GeneralFaultException {

     	 if (! endpointBehaviorService.isEndpointBehaviorForTenant(TenantContext.getTenantId())) {
     		EndpointBehaviorDisabledFault fault = new EndpointBehaviorDisabledFault();
    		 fault.setErrorMessage("EndpointBehavior is not enabled for the Tenant.");
    		 EndpointBehaviorDisabledFaultException ex = new EndpointBehaviorDisabledFaultException(fault.getErrorMessage());
    		 ex.setFaultMessage(fault);
    		 throw ex;
    	 }

     	 GetEndpointBehaviorResponse getEndpointBehaviorResponse = new GetEndpointBehaviorResponse();
    	  String endpointBehaviorKey = getEndpointBehaviorRequest.getEndpointBehaviorKey();

		   EndpointBehavior endpointBehavior = null;

		   //Check if the Key is present, if it is, then use key to retrieve the EndpointBehavior
		   if (StringUtils.isBlank(endpointBehaviorKey)){
			   List<EndpointBehavior> endpointBehaviors = endpointBehaviorService.getEndpointBehaviorByTenant(TenantContext.getTenantId());
			   if (endpointBehaviors != null && endpointBehaviors.size() > 0){
				   endpointBehavior = endpointBehaviors.get(0);
			   }
		   } else {
			   endpointBehavior = endpointBehaviorService.getEndpointBehaviorByKey(endpointBehaviorKey);
		   }

		   if (endpointBehavior != null ){
			   // Call to create the Web service response data type using existing EndpointBehavior
			   getEndpointBehaviorResponse.setEndpointBehavior(
					   (EndpointBehaviorDataType) endpointBehaviorService
					   									.makeEndpointBehavioDataType(endpointBehavior, new EndpointBehaviorDataType()));
		   } else {
	       		 NoEndpointBehaviorExistsFault fault = new NoEndpointBehaviorExistsFault();
	      		 fault.setErrorMessage("Invalid EndpointBehavior for the Tenant.");
	      		 NoEndpointBehaviorExistsFaultException ex = new NoEndpointBehaviorExistsFaultException(fault.getErrorMessage());
	      		 ex.setFaultMessage(fault);
	      		 throw ex;
		   }
    	  return getEndpointBehaviorResponse;
      }


    /**
      * To validate EndpointBehavior input values entered by user
      * @param endpointBehavior
      * @return EndpointBehaviorDataType
      * @throws InvalidArgumentFaultException
      * @throws GeneralFaultException
      */
     private EndpointBehaviorDataType validateEndpointBehavior (EndpointBehaviorDataType endpointBehavior, Operation operation)
    		 throws InvalidArgumentFaultException,GeneralFaultException {

    	// Validate RecordingRole
    	// This should be based on description in Recorder Endpoints.
    	if (StringUtils.isNotBlank(endpointBehavior.getRecordingRole())) {
    		List<RecorderEndpoints> recorderEndpoints = componentService.getRecorderEndpointsByDescription(endpointBehavior.getRecordingRole());
    		if (recorderEndpoints == null || recorderEndpoints.size() <= 0){
				InvalidArgumentFault fault = new InvalidArgumentFault();
	            fault.setErrorMessage("Invalid Recording Role.");
	            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
    		}
    	} else {
    		endpointBehavior.setRecordingRole(null);
    	}

    	List<String> OnOffList = Arrays.asList(this.endpointBehaviorService.onOffList);
    	
    	// Validate all the numeric fields suggesting enabled/disabled.
    	// Since all these fields are mandatory, they will going to be present in the request.
    	final List<String> errorMessages = new ArrayList<>();
			List<Method> methods = Arrays.asList(endpointBehavior.getClass().getDeclaredMethods());
			methods.forEach(method -> {
				if (method.getReturnType().getName().equals("int")
						&& OnOffList.contains(method.getName().substring("get".length()).toLowerCase())
						&& errorMessages.size() == 0){
					Integer value = -1;
					try {
						value = (Integer) method.invoke(endpointBehavior);
						if (value < 0 || value > 1){
							errorMessages.add("Invalid value for : " + method.getName().substring("get".length()));
				    	}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// Since the field validation failed throwing error back
						errorMessages.add("Validation failed for : " + method.getName().substring("get".length()));
					}
				} else if (method.getReturnType().getName().equals("java.lang.String")
						&& method.getName().toLowerCase().contains("iframeurl")
						&& errorMessages.size() == 0){
					String value = null;
					try {
						value = (String) method.invoke(endpointBehavior);
						if (StringUtils.isNotBlank(value) && value.length() > 2048){
							errorMessages.add("Invalid value for : " + method.getName().substring("get".length()) + ". Length should be less than or equal to 2048.");
				    	}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// Since the field validation failed throwing error back
						errorMessages.add("Validation failed for : " + method.getName().substring("get".length()));
					}
				} else if (method.getReturnType().getName().equals("int")
						&& method.getName().toLowerCase().contains("iframesize")
						&& errorMessages.size() == 0){
					Integer value = -1;
					try {
						value = (Integer) method.invoke(endpointBehavior);
						Method isSpecifiedMethod = endpointBehavior.getClass().getMethod("is"+method.getName().substring("get".length())+"Specified");
						boolean isSpecified = (Boolean) isSpecifiedMethod.invoke(endpointBehavior);
						if (isSpecified && (value < 0 || value > 100)){
							errorMessages.add("Invalid value for : " + method.getName().substring("get".length())+". It should be between 0 - 100.");
				    	} else if (!isSpecified && operation == Operation.CREATE){
				    		Method setterMethod = endpointBehavior.getClass().getMethod("set"+method.getName().substring("get".length()), int.class);
				    		setterMethod.invoke(endpointBehavior, 0);
				    	}
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// Since the field validation failed throwing error back
						logger.error(e.getMessage(), e);
						errorMessages.add("Validation failed for : " + method.getName().substring("get".length()));
					}
				} else if (method.getReturnType().getName().equals("int")
						&& method.getName().toLowerCase().contains("window")
						&& errorMessages.size() == 0){
					try {
						Method isSpecifiedMethod = endpointBehavior.getClass().getMethod("is"+method.getName().substring("get".length())+"Specified");
						boolean isSpecified = (Boolean) isSpecifiedMethod.invoke(endpointBehavior);
						if (!isSpecified && operation == Operation.CREATE){
				    		Method setterMethod = endpointBehavior.getClass().getMethod("set"+method.getName().substring("get".length()), int.class);
				    		setterMethod.invoke(endpointBehavior, 0);
				    	}
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// Since the field validation failed throwing error back
						logger.error(e.getMessage(), e);
						errorMessages.add("Validation failed for : " + method.getName().substring("get".length()));
					}
				}
			});

			// In case the field has different value than 0 or 1, then the validation will fail throwing the error and field name back.
			if (errorMessages.size() > 0){
				InvalidArgumentFault fault = new InvalidArgumentFault();
	            fault.setErrorMessage(errorMessages.get(0));
	            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;

			}
    	return endpointBehavior;
     }

	private void auditLogTransaction(String tnName, String tnParam, String tenantName, String tnResult) {

		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName(tnName);
		transactionHistory.setTransactionParams(tnParam);
		transactionHistory.setTransactionResult(tnResult);
		transactionHistory.setTenantName(tenantName);
		transaction.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	@Override
	public SearchRoomsResponse searchRooms(SearchRoomsRequest searchRoomsRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, NotLicensedFaultException {
        BigInteger start = searchRoomsRequest.getStart();
        if (start == null) {
            start = new BigInteger("0");
        } else if (start.intValue() > 10000) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("start must be in between 0-10000");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        BigInteger limit =  searchRoomsRequest.getLimit();
        if (limit == null) {
            limit = new BigInteger("10");
        } else if (limit.intValue() > 100) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("limit must be in between 0-100");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String sortBy = StringUtils.trimToNull(searchRoomsRequest.getSortBy());
        if (sortBy == null) {
            sortBy = "roomName";
        } else if (sortBy.equalsIgnoreCase("roomName")) {
            sortBy = "roomName";
        } else if (sortBy.equalsIgnoreCase("extension")) {
            sortBy = "roomExtNumber";
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("sortBy value invalid, must be \"roomName\" or \"extension\"");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String roomType = StringUtils.trimToNull(searchRoomsRequest.getRoomType());
        List<Integer> roomTypes = new ArrayList<Integer>();
        if (roomType == null) {
            roomTypes.add(2);
        } else if (roomType.contains("|")) { // multiple
            String[] parts = roomType.split("\\|");
            int i = 0;
            for (String part : parts) {
                if (part.equalsIgnoreCase("personal")) {
                    roomTypes.add(1);
                } else if (part.equalsIgnoreCase("public")) {
                    roomTypes.add(2);
                } else if (part.equalsIgnoreCase("legacy")) {
                    roomTypes.add(3);
                }
                i++;
                if (i >= 3) {
                    break;
                }
             }
        } else { // single
            if (roomType.equalsIgnoreCase("personal")) {
                roomTypes.add(1);
            } else if (roomType.equalsIgnoreCase("public")) {
                roomTypes.add(2);
            } else if (roomType.equalsIgnoreCase("legacy")) {
                roomTypes.add(3);
            }
        }
        if (roomTypes.size() == 0) {
            roomTypes.add(2);
        }

        SortDir sortDir = searchRoomsRequest.getSortDir();
        if (sortDir == null) {
            sortDir = SortDir.ASC;
        }

        String query = StringUtils.trimToNull(searchRoomsRequest.getQuery());
        if (query == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("query string cannot be empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String originalQuery = query;
        boolean checkQueryLength = true;

        String queryField = StringUtils.trimToNull(searchRoomsRequest.getQueryField());
        if (queryField == null) {
            queryField = "roomName";
        } else if (queryField.equalsIgnoreCase("roomNameOrExtension")) {
            queryField = "roomName";
        } else if (queryField.equalsIgnoreCase("ownerName")) {
            queryField = "memberName";
        } else if (queryField.equalsIgnoreCase("ownerEntityID")) {
            queryField = "memberID";
            try {
                int roomID = Integer.parseInt(query);
                int memberID = this.roomService.getMemberIDForRoomIDIfAllowed(roomID); // convert entityID (roomID) to memberID
                query = "" + memberID;
                checkQueryLength = false;
            } catch (NumberFormatException n) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("query must be consist only of digits when queryField is \"ownerEntityID\"");
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("queryField must be \"roomNameOrExtension\" or \"ownerName\" or \"ownerEntityID\"");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (checkQueryLength && StringUtils.deleteWhitespace(query).length() < 2) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("query string must be at least two characters");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        SearchRoomsResponse response = new SearchRoomsResponse();

        User user = userService.getLoginUser();
        int thisUserMemberID = user.getMemberID();

        int total = this.roomService.findRoomsCount(thisUserMemberID, query, queryField, roomTypes);
        response.setTotal(total);

        if (total > 0 && limit.intValue() > 0) {
            List<RoomMini> rooms = this.roomService.findRooms(thisUserMemberID,
                    query, queryField, roomTypes,
                    start.intValue(), limit.intValue(),
                    sortBy, sortDir.toString());

            List<Integer> memberIDs = new ArrayList<Integer>();
            for (RoomMini room : rooms) {
                if (!"legacy".equals(room.getType())) {
                    memberIDs.add((int) room.getOwnerID());
                }
            }
            Map<Integer,Integer> personalRooms = memberService.getPersonalRoomIds(memberIDs);

            for (RoomMini room : rooms) {
                RoomDetail_type0 r = new RoomDetail_type0();

                EntityID entityID = new EntityID();
                
                entityID.setEntityID((int) room.getRoomID());
                r.setEntityID(entityID);

                EntityID ownerEntityID = new EntityID();
                if ("legacy".equals(room.getType())) {
                    r.setOwnerEntityID(entityID); // no owner for legacy
                } else {
                    ownerEntityID.setEntityID(personalRooms.get((int) room.getOwnerID())); // convert member ids to personal room ids
                    r.setOwnerEntityID(ownerEntityID);
                }

                r.setExtension(room.getExtension());
                r.setName(room.getName());
                r.setDisplayName(room.getDisplayName());
                r.setDescription(room.getDescription());
                r.setOwnerName(room.getOwnerName());
                r.setType(room.getType());
                r.setPinned(room.isPinned());
                r.setLocked(room.isLocked());
                r.setEnabled(room.isEnabled());
                r.setIsInMyContacts(room.isInContacts());

                response.addRoomDetail(r);
            }
        }

        // convert back
        if (sortBy.equals("roomName")) {
            sortBy = "roomName";
        } else if (sortBy.equals("roomExtNumber")) {
            sortBy = "extension";
        } else {
            sortBy = "roomName";
        }

        if (queryField.equals("roomName")) {
            queryField = "roomNameOrExtension";
        } else if (queryField.equals("memberName")) {
            queryField = "ownerName";
        } else if (queryField.equals("memberID")) {
            queryField = "ownerEntityID";
        }

        StringBuilder roomTypeOut = new StringBuilder();
        if (roomTypes.size() > 0) {
            int i = 0;
            for (Integer type : roomTypes) {
                if (i >= 1) {
                    roomTypeOut.append("|");
                }
                if (type == 1) {
                    roomTypeOut.append("personal");
                } else if (type == 2) {
                    roomTypeOut.append("public");
                } else if (type == 3) {
                    roomTypeOut.append("legacy");
                }
                i++;
            }
        }

        response.setStart(start);
        response.setLimit(limit);
        response.setSortBy(sortBy);
        response.setSortDir(sortDir);
        response.setQuery(originalQuery);
        response.setQueryField(queryField);
        response.setRoomType(roomTypeOut.toString());

        return response;
	}
}
