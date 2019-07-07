/**
 * 
 */
package com.vidyo.service.conference;

import java.rmi.RemoteException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Guest;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IFederationConferenceService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.RoomServiceImpl;
import com.vidyo.service.conference.request.GuestJoinConfRequest;
import com.vidyo.service.conference.request.InviteToConferenceRequest;
import com.vidyo.service.conference.request.JoinConferenceRequest;
import com.vidyo.service.conference.request.LeaveConferenceRequest;
import com.vidyo.service.conference.response.JoinConferenceResponse;
import com.vidyo.service.conference.response.LeaveConferenceResponse;
import com.vidyo.service.exceptions.ConferenceLockedException;
import com.vidyo.service.exceptions.EndpointNotExistException;
import com.vidyo.service.exceptions.EndpointNotSupportedException;
import com.vidyo.service.exceptions.FederationNotAllowedException;
import com.vidyo.service.exceptions.InviteConferenceException;
import com.vidyo.service.exceptions.JoinConferenceException;
import com.vidyo.service.exceptions.LeaveConferenceException;
import com.vidyo.service.exceptions.OutOfPortsException;
import com.vidyo.service.exceptions.ResourceNotAvailableException;
import com.vidyo.service.exceptions.UserNotFoundException;
import com.vidyo.service.exceptions.WrongPinException;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.system.SystemService;

/**
 * Service class which handles conference joining functionality.
 * 
 * 
 * @author Ganesh
 * 
 */

public class ConferenceAppServiceImpl implements ConferenceAppService {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(ConferenceAppServiceImpl.class);

	/**
	 * 
	 */
	private SystemService systemService;

	/**
	 * 
	 */
	private IRoomService roomService;

	/**
	 * 
	 */
	private ITenantService tenantService;

	/**
	 * 
	 */
	private IMemberService memberService;

	/**
	 * 
	 */
	private IFederationConferenceService federationConferenceService;

	/**
	 * 
	 */
	private IConferenceService conferenceService;

	/**
	 * 
	 */
	private IUserService userService;

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * @param conferenceService
	 *            the conferenceService to set
	 */
	public void setConferenceService(IConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	/**
	 * @param federationConferenceService
	 *            the federationConferenceService to set
	 */
	public void setFederationConferenceService(IFederationConferenceService federationConferenceService) {
		this.federationConferenceService = federationConferenceService;
	}

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param tenantService
	 *            the tenantService to set
	 */
	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	/**
	 * @param roomService
	 *            the roomService to set
	 */
	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	/**
	 * @param systemService
	 *            the systemService to set
	 */
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * Allows the user to join the conference in remote portal using Inter-Portal/Intra-Portal Conferencing feature.
	 * Supports both normal and scheduled rooms.
	 * 
	 * @param joinConferenceRequest
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	public JoinConferenceResponse joinInterPortalRoomConference(JoinConferenceRequest joinConferenceRequest) {
		// Check if the tenant exists in the portal
		Tenant inputTenant = tenantService.getTenantByURL(joinConferenceRequest.getTenantUrl());

		if (inputTenant == null) {
			// Make call to IPC API
			JoinConferenceResponse joinConferenceResponse = joinRemoteConference(joinConferenceRequest);
			return joinConferenceResponse;
		}
		// Tenant is in the same portal, check if cross tenant calling is
		// enabled or not
		// TODO - IMPORTANT get all room details needed for conference
		// get the room by room name/ext for the tenant
		Room roomToJoin = roomService.getRoomDetailsForConference(joinConferenceRequest.getRoomNameExt(), inputTenant.getTenantID());

		// Return error if there is no room by (roomName+tenantId) or
		// roomExtn
		if (roomToJoin == null) {
			// Check if it is a scheduled room extension - ext@url
			return joinConferenceInScheduledRoom(joinConferenceRequest);
		}

		joinConferenceRequest.setRoomId(roomToJoin.getRoomID());

		Member joiningMember = memberService.getInviterDetails(joinConferenceRequest.getJoiningMemberId());

		if (joiningMember == null) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_MEMBER);
			joinConferenceResponse.setMessageId("Invalid Member");
			joinConferenceResponse.setMessage("invalid.member");
			joinConferenceResponse.setDisplayMessage("Invalid User");
			return joinConferenceResponse;
		}

		joinConferenceRequest.setJoiningMember(joiningMember);

		// If the member is joining a room in other tenant, check for
		// allowed list
		if (inputTenant.getTenantID() != joiningMember.getTenantID()) {
			// If tenant exists in the portal, check if it can be called
			List<Tenant> allowedTenants = tenantService.canCallToTenants(joiningMember.getTenantID());
			boolean canCallTenant = false;
			for (Tenant allowedTenant : allowedTenants) {
				if (allowedTenant.getTenantID() == inputTenant.getTenantID()) {
					canCallTenant = true;
					break;
				}
			}
			// Try IPC if tenant is not in allowed list
			if (!canCallTenant) {
				JoinConferenceResponse joinConferenceResponse = joinRemoteConference(joinConferenceRequest);
				return joinConferenceResponse;
			}
		}

		return joinConferenceInRoom(joinConferenceRequest);

	}

	/**
	 * Allows the User to join the conference in the specified room.
	 * 
	 * @param joinConferenceRequest
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	public JoinConferenceResponse joinConferenceInRoom(JoinConferenceRequest joinConferenceRequest) {
		// If member is invalid, return error
		if (joinConferenceRequest.getJoiningMember() == null) {
			Member joiningMember = memberService.getMember(joinConferenceRequest.getJoiningMemberId());
			if (joiningMember == null) {
				JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
				joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_MEMBER);
				joinConferenceResponse.setMessageId("Invalid Member");
				joinConferenceResponse.setMessage("invalid.member");
				joinConferenceResponse.setDisplayMessage("Invalid User");
				return joinConferenceResponse;
			}
			joinConferenceRequest.setJoiningMember(joiningMember);
		}

		Room roomToJoin = roomService.getRoomDetailsForConference(joinConferenceRequest.getRoomId());

		if (roomToJoin == null) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_ROOM);
			joinConferenceResponse.setMessage("invalid.room");
			joinConferenceResponse.setMessageId("Room");
			joinConferenceResponse.setDisplayMessage("Invalid Room");
			return joinConferenceResponse;
		}
		// Scheduled Room - Check for PIN
		if (roomToJoin.getRoomType().equalsIgnoreCase("Scheduled")) {
			logger.debug("Room to be joined is a scheduled room");
			// Check if the scheduled is disabled at tenant level
			Tenant tenant = tenantService.getTenant(roomToJoin.getTenantID());
			if (tenant == null || tenant.getScheduledRoomEnabled() == 0) {
				JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
				joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_ROOM);
				joinConferenceResponse.setMessage("invalid.room");
				joinConferenceResponse.setMessageId("Room");
				joinConferenceResponse.setDisplayMessage("Invalid Room");
				// Checking is done only at tenant level, as global disabling would do a hard delete of the rooms
				logger.warn("Scheduled room feature is disabled at the Tenant level, {}", roomToJoin.getTenantID());
				return joinConferenceResponse;
			}
			// Starting 18.1.0 - Scheduled rooms follow Tenant prefix
			// Handle New Scheduled Scheme which has Tenant Prefix instead of dedicated Scheduled Room Prefix 
	        Configuration configuration = systemService.getConfiguration(RoomServiceImpl.SCHEDULED_ROOM_PREFIX);
	        String dedciatedSchRoomPrefix = null;
	        if(configuration != null && configuration.getConfigurationValue() != null) {
	        		dedciatedSchRoomPrefix = configuration.getConfigurationValue();
	        }
	        // If the room is having dedicated scheduled room prefix
			if(org.apache.commons.lang.StringUtils.isNotBlank(dedciatedSchRoomPrefix)  && roomToJoin.getRoomExtNumber().startsWith(dedciatedSchRoomPrefix)) {
				String pin = (roomToJoin.getRoomPIN() == null) ? "0" : roomToJoin.getRoomPIN();
				// Use the Tenant prefix of the Room for this comparison not the joining user's tenant prefix
				// This room is based on new scheme, do not use the old decrypt mechanism
				ScheduledRoomResponse scheduledRoomResponse = roomService.generateSchRoomExtPin(roomToJoin.getMemberID(),
						Integer.parseInt(pin));

				if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.SUCCESS) {
					logger.debug("Scheduled room - {}, PIN - {}", scheduledRoomResponse.getRoomExtn(),
							scheduledRoomResponse.getPin());
					if (roomToJoin.getRoomPIN() != null) {
						roomToJoin.setRoomPIN(String.valueOf(scheduledRoomResponse.getPin()));
						joinConferenceRequest.setSchRoomConfName(scheduledRoomResponse.getRoomExtn()
								+ scheduledRoomResponse.getPin());
					}
				} else {
					JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
					joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_ROOM);
					joinConferenceResponse.setMessage("invalid.room");
					joinConferenceResponse.setMessageId("Room");
					joinConferenceResponse.setDisplayMessage("Invalid Room");
					logger.error("Scheduled room decryption failed {}, response code {}", roomToJoin.getRoomID(),
							joinConferenceResponse.getStatus());
					return joinConferenceResponse;
				}
			}
		}

		if (joinConferenceRequest.getSchRoomConfName() != null) {
			roomToJoin.setSchRoomConfName(joinConferenceRequest.getSchRoomConfName());
		}

		if (joinConferenceRequest.isCheckCrossTenant()
				&& joinConferenceRequest.getJoiningMember().getTenantID() != roomToJoin.getTenantID()) {
			List<Tenant> allowedTenants = tenantService.canCallToTenants(joinConferenceRequest.getJoiningMember()
					.getTenantID());
			boolean canCallTenant = false;
			for (Tenant allowedTenant : allowedTenants) {
				if (allowedTenant.getTenantID() == roomToJoin.getTenantID()) {
					canCallTenant = true;
					break;
				}
			}
			if (!canCallTenant) {
				// If cross tenant calling is disabled, return error
				JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
				joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_ROOM);
				joinConferenceResponse.setMessage("invalid.room");
				joinConferenceResponse.setMessageId("Room");
				joinConferenceResponse.setDisplayMessage("Invalid Room");
				return joinConferenceResponse;
			}
		}

		// Room locked
		if ((joinConferenceRequest.getJoiningMemberId() != roomToJoin.getMemberID()) && roomToJoin.getRoomLocked() == 1) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.ROOM_LOCKED);
			joinConferenceResponse.setMessage("room.is.locked");
			joinConferenceResponse.setMessageId("Lock");
			joinConferenceResponse.setDisplayMessage("Room is locked");
			return joinConferenceResponse;
		}

		// Disabled Room Check
		if (roomToJoin.getRoomEnabled() != 1) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.ROOM_DISABLED);
			joinConferenceResponse.setMessage("room.is.disabled");
			joinConferenceResponse.setMessageId("RoomDisabled");
			joinConferenceResponse.setDisplayMessage("Room is disabled");
			return joinConferenceResponse;
		}

		// Check PIN
		if (joinConferenceRequest.isCheckPin()
				&& (joinConferenceRequest.getJoiningMemberId() != roomToJoin.getMemberID())
				&& roomToJoin.getRoomPIN() != null
				&& !roomToJoin.getRoomPIN().equalsIgnoreCase(joinConferenceRequest.getPin())) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.WRONG_PIN);
			joinConferenceResponse.setMessage("wrong.pin");
			joinConferenceResponse.setMessageId("Pin");
			joinConferenceResponse.setDisplayMessage("Wrong PIN");
			return joinConferenceResponse;
		}

		// Check the room capacity based on group
		if (roomToJoin.getOccupantsCount() >= roomToJoin.getRoomMaxUsers()) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.ROOM_CAPACITY_REACHED);
			joinConferenceResponse.setMessage("room.capacity.reached");
			joinConferenceResponse.setMessageId("Room Capacity Reached");
			joinConferenceResponse.setDisplayMessage("Room capacity has been reached");
			return joinConferenceResponse;
		}

		JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();

		try {
			conferenceService.joinTheConference(roomToJoin, joinConferenceRequest.getJoiningMember());
		} catch (OutOfPortsException e) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.OUT_OF_PORTS_ERROR);
			// TODO get the message translation
			joinConferenceResponse.setMessage("out.of.ports");
			joinConferenceResponse.setMessageId("OutOfPorts");
			joinConferenceResponse.setDisplayMessage("All Lines in use. Please try again later.");
		} catch (JoinConferenceException e) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.JOIN_CONF_FAILED);
			// TODO get the message translation
			joinConferenceResponse.setMessage("join.conf.error");
			joinConferenceResponse.setMessageId("JoinConference");
			joinConferenceResponse.setDisplayMessage("Unable to join the conference");
		} catch (EndpointNotSupportedException e) {
            joinConferenceResponse.setStatus(JoinConferenceResponse.JOIN_CONF_FAILED);
            joinConferenceResponse.setMessage("join.conf.error");
            joinConferenceResponse.setMessageId("JoinConference");
            joinConferenceResponse.setDisplayMessage("Unable to join the conference - endpoint not supported");
        }

		return joinConferenceResponse;
	}

	/**
	 * Utility method to invoke the FederationConferenceService and return status codes.
	 * 
	 * @param joinConferenceRequest
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	private JoinConferenceResponse joinRemoteConference(JoinConferenceRequest joinConferenceRequest) {
		JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
		try {
			try {
				federationConferenceService.joinRemoteConference(joinConferenceRequest.getJoiningMemberId(),
						joinConferenceRequest.getRoomNameExt(), joinConferenceRequest.getTenantUrl(),
						joinConferenceRequest.getPin(), false);
			} catch (RemoteException e) {
				federationConferenceService.joinRemoteConference(joinConferenceRequest.getJoiningMemberId(),
						joinConferenceRequest.getRoomNameExt(), joinConferenceRequest.getTenantUrl(),
						joinConferenceRequest.getPin(), true);
			}
		} catch (RemoteException e) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.REMOTE_JOIN_CONF_FAILED);
			joinConferenceResponse.setMessage("failed.to.join.conference.with.remote.vidyoconferencing.system");
			joinConferenceResponse.setDisplayMessage("Failed to join conference with remote VidyoConferencing system");
			joinConferenceResponse.setMessageId("JoinConference");
		} catch (ResourceNotAvailableException rnae) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.RESOURCE_NOT_AVAILABLE_FOR_CONF);
			joinConferenceResponse.setMessage("router.pool.not.configured");
			joinConferenceResponse.setDisplayMessage("Resource Not Available");
			joinConferenceResponse.setMessageId("Resource Not Available");
		} catch (OutOfPortsException e) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.OUT_OF_PORTS_ERROR);
			joinConferenceResponse.setMessage("out.of.ports");
			joinConferenceResponse.setMessageId("OutOfPorts");
			joinConferenceResponse.setDisplayMessage("All lines in use");
		} catch (JoinConferenceException e) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.JOIN_CONF_FAILED);
			joinConferenceResponse.setMessage("join.conf.error");
			joinConferenceResponse.setMessageId("JoinConference");
			joinConferenceResponse.setDisplayMessage("Joining Conference Failed");
		} catch (FederationNotAllowedException e) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.REMOTE_CONF_NOT_ALLOWED);
			joinConferenceResponse.setMessage("inter.portal.communication.not.allowed.or.incorrect.domain.address");
			joinConferenceResponse.setMessageId("FederationNotAllowed");
			joinConferenceResponse
					.setDisplayMessage("Inter-Portal Communication not allowed or incorrect domain address");
		} catch (UserNotFoundException e) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.REMOTE_USER_NOT_FOUND);
			joinConferenceResponse.setMessage("user.not.found");
			joinConferenceResponse.setMessageId("UserNotFound");
			joinConferenceResponse.setDisplayMessage("Invalid Room or Extension");
		} catch (WrongPinException e) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.WRONG_PIN);
			joinConferenceResponse.setMessage("wrong.pin");
			joinConferenceResponse.setMessageId("Pin");
			joinConferenceResponse.setDisplayMessage("Wrong PIN");
		} catch (ConferenceLockedException e) {
			joinConferenceResponse.setStatus(JoinConferenceResponse.ROOM_LOCKED);
			joinConferenceResponse.setMessage("room.is.locked");
			joinConferenceResponse.setMessageId("Lock");
			joinConferenceResponse.setDisplayMessage("Room is locked");
		}
		return joinConferenceResponse;
	}

	/**
	 * Validates the Scheduled Room extension & pin and allows the user to join the scheduled room.
	 * 
	 * @param joinConferenceRequest
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	public JoinConferenceResponse joinConferenceInScheduledRoom(JoinConferenceRequest joinConferenceRequest) {
		ScheduledRoomResponse scheduledRoomResponse = validateScheduledRoom(joinConferenceRequest.getRoomNameExt(),
				joinConferenceRequest.getPin());

		if (scheduledRoomResponse.getStatus() != 0) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setMessage(scheduledRoomResponse.getMessage());
			joinConferenceResponse.setMessageId(scheduledRoomResponse.getMessageId());
			joinConferenceResponse.setStatus(scheduledRoomResponse.getStatus());
			joinConferenceResponse.setDisplayMessage(scheduledRoomResponse.getDisplayMessage());
			return joinConferenceResponse;
		}

		// Concat user entered extension & pin and set as Scheduled Room
		// Conference Name
		joinConferenceRequest.setSchRoomConfName(joinConferenceRequest.getRoomNameExt()
				+ joinConferenceRequest.getPin());
		joinConferenceRequest.setRoomId(scheduledRoomResponse.getRoom().getRoomID());
		// Don't have to validate pin from this point
		joinConferenceRequest.setCheckPin(false);
		JoinConferenceResponse joinConferenceResponse = joinConferenceInRoom(joinConferenceRequest);
		return joinConferenceResponse;
	}

	/**
	 * Validates the Scheduled Room Extension and Pin value.
	 * 
	 * @param roomExt
	 *            Extension number of the room
	 * @param pin
	 *            room's security pin
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	public ScheduledRoomResponse validateScheduledRoom(String roomExt, String pin) {
		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();

		// Tenant Cross Calling check should not be invoked for Scheduled Room
		scheduledRoomResponse = roomService.validateScheduledRoom(roomExt, pin, 0);

		// If the scheduled room is invalid, return error
		if (scheduledRoomResponse.getStatus() != 0) {
			// If the extension starts with Prefix and the extn + pin
			// combination is invalid - return wrong pin error
			if (!StringUtils.isEmpty(scheduledRoomResponse.getSchRoomPrefix())
					&& roomExt.startsWith(scheduledRoomResponse
							.getSchRoomPrefix())) {
				scheduledRoomResponse
						.setStatus(ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN);
				scheduledRoomResponse.setMessage("wrong.pin");
				scheduledRoomResponse.setDisplayMessage("Wrong PIN");
				scheduledRoomResponse.setMessageId("Pin");
				return scheduledRoomResponse;
			}
			return scheduledRoomResponse;
		}

		return scheduledRoomResponse;
	}

	/**
	 * Allows the guest user to join the specified room after validation
	 * 
	 * @param joinConferenceRequest
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	@Override
	public JoinConferenceResponse joinConferenceAsGuest(GuestJoinConfRequest guestJoinConfRequest) {

		boolean isGuestAllowed = roomService.areGuestAllowedToThisRoom();

		if (!isGuestAllowed) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.GUESTS_NOT_ALLOWED);
			joinConferenceResponse.setMessageId("Guests not allowed");
			joinConferenceResponse.setMessage("guests.not.allowed.to.the.room");
			return joinConferenceResponse;
		}

		User guestUser = userService.getUserForGuestID(guestJoinConfRequest.getGuestId());

		// If guest id is invalid, return error
		if (guestUser == null) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_GUEST);
			joinConferenceResponse.setMessageId("Invalid Guest");
			joinConferenceResponse.setMessage("invalid.guest");
			return joinConferenceResponse;
		}

		int roomId = guestJoinConfRequest.getRoomId();

		if (roomId <= 0) {
			roomId = guestUser.getRoomID();
		}

		Room roomToJoin = roomService.getRoom(roomId);
		// If room is invalid, return error
		if (roomToJoin == null) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_ROOM);
			joinConferenceResponse.setMessage("invalid.room");
			joinConferenceResponse.setMessageId("Room");
			return joinConferenceResponse;
		}

		if (roomToJoin.getTenantID() != guestJoinConfRequest.getTenantId()) {
			logger.error(
					"Un-Authorized [hacking] cross tenant guest access guestID -{} from Tenant -{}, trying to access roomID -{} from Tenant - {}",
					guestJoinConfRequest.getGuestId(), guestJoinConfRequest.getTenantId(),
					guestJoinConfRequest.getRoomId(), roomToJoin.getTenantID());
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_ROOM);
			joinConferenceResponse.setMessage("invalid.room");
			joinConferenceResponse.setMessageId("Room");
			return joinConferenceResponse;
		}

		// TODO IMPORTANT - merge getUserForGuest & getGuest method calls
		Guest guest = memberService.getGuest(guestJoinConfRequest.getGuestId());

		// If guest id is invalid, return error
		if (guest == null) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.INVALID_GUEST);
			joinConferenceResponse.setMessageId("Invalid Guest");
			joinConferenceResponse.setMessage("invalid.guest");
			return joinConferenceResponse;
		}

		if (guest.getEndpointGUID() == null || guest.getEndpointGUID().trim().length() <= 0) {
			JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
			joinConferenceResponse.setStatus(JoinConferenceResponse.GUESTS_NOT_LINKED);
			joinConferenceResponse.setMessageId("Guest not linked");
			joinConferenceResponse.setMessage("guest.is.not.linked.to.endpoint");
			return joinConferenceResponse;
		}

		// Create Member object for further processing
		Member joiningMember = new Member();
		joiningMember.setEndpointGUID(guest.getEndpointGUID());
		joiningMember.setMemberName(guest.getGuestName());
		joiningMember.setMemberID(guest.getGuestID());
		joiningMember.setRoleName("Guest");
		joiningMember.setTenantID(roomToJoin.getTenantID());

		JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();
		joinConferenceRequest.setJoiningMember(joiningMember);
		joinConferenceRequest.setPin(guestJoinConfRequest.getPin());
		ScheduledRoomResponse scheduledRoomResponse = null;
		// If Scheduled Room Type, invoke the Scheduled Room method
		if (roomToJoin.getRoomType().equalsIgnoreCase("Scheduled") && roomToJoin.getRoomPIN() != null) {
			// Starting 18.1.0 - Scheduled rooms follow Tenant prefix
			// Handle New Scheduled Scheme which has Tenant Prefix instead of dedicated Scheduled Room Prefix
	        Configuration configuration = systemService.getConfiguration(RoomServiceImpl.SCHEDULED_ROOM_PREFIX);
	        String dedciatedSchRoomPrefix = null;
	        if(configuration != null && configuration.getConfigurationValue() != null) {
	        		dedciatedSchRoomPrefix = configuration.getConfigurationValue();
	        }
	        // If the room is having dedicated scheduled room prefix
			if(org.apache.commons.lang.StringUtils.isNotBlank(dedciatedSchRoomPrefix)  && roomToJoin.getRoomExtNumber().startsWith(dedciatedSchRoomPrefix)) {
				scheduledRoomResponse = roomService.generateSchRoomExtPin(roomToJoin.getMemberID(),
						Integer.parseInt(roomToJoin.getRoomPIN()));
				if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.SUCCESS) {
					roomToJoin.setRoomPIN(String.valueOf(scheduledRoomResponse.getPin()));
					// If scheduled room, override the conference name
					joinConferenceRequest.setSchRoomConfName(scheduledRoomResponse.getRoomExtn()
							+ joinConferenceRequest.getPin());
					// For Client Installations table to have the right room name
					roomToJoin.setRoomName(scheduledRoomResponse.getRoomExtn()
							+ joinConferenceRequest.getPin());
				}
			}
		}

		// If scheduled room, override the pin value in the room object and
		// validate with user provided pin

		if (roomToJoin.getRoomPIN() != null
				&& !roomToJoin.getRoomPIN().equalsIgnoreCase(joinConferenceRequest.getPin())){
			scheduledRoomResponse = roomService.validateScheduledRoom(roomToJoin.getRoomExtNumber(), roomToJoin.getRoomPIN(), roomToJoin.getTenantID());
			if (scheduledRoomResponse == null || ( scheduledRoomResponse.getStatus() != ScheduledRoomResponse.SUCCESS  ||
						scheduledRoomResponse.getRoom() == null || scheduledRoomResponse.getRoom().getRoomPIN() == null || ! scheduledRoomResponse.getRoom().getRoomPIN().equalsIgnoreCase(joinConferenceRequest.getPin()))) {
					JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
				joinConferenceResponse.setStatus(JoinConferenceResponse.WRONG_PIN);
				joinConferenceResponse.setMessage("wrong.pin");
				joinConferenceResponse.setMessageId("Pin");
				return joinConferenceResponse;
			}
		}

		joinConferenceRequest.setRoomId(roomToJoin.getRoomID());
		// PIN has been validated, skip PIN check after this point
		joinConferenceRequest.setCheckPin(false);
		JoinConferenceResponse joinConferenceResponse = joinConferenceInRoom(joinConferenceRequest);
		// Update the License details
		userService.updateRegisterLicenseForGuest(guestUser.getUsername(), guestUser.getMemberName(), roomToJoin);
		return joinConferenceResponse;
	}

	/**
	 * Delegates the call to appropriate APIs based on the input parameters.<br>
	 * Currently handles conference request based on roomId, Scheduled Room extension and IPC room names.<br>
	 * 
	 * @param joinConferenceRequest
	 * @return
	 */
	@Override
	public JoinConferenceResponse joinConference(JoinConferenceRequest joinConferenceRequest) {
		JoinConferenceResponse joinConferenceResponse = null;
		// Handling the IPC case & the case when the user types the room name
		// with Tenant URL & scheduled room
		if (joinConferenceRequest.getRoomId() == -1 && joinConferenceRequest.getRoomNameExt() != null
				&& !joinConferenceRequest.getRoomNameExt().equals("")) {
			String roomNameExt = null;
			// Starting 2.4, Join button will be enabled for scheduled rooms
			// also as opposed to IPC only in prior releases
			// Validate the input string, if there is only one @ symbol,
			// follow IPC else scheduled room
			if (StringUtils.countOccurrencesOf(joinConferenceRequest.getRoomNameExt(), "@") == 1) {
				// Parse the input string
				String[] parsedInput = joinConferenceRequest.getRoomNameExt().trim().split("@");
				if (parsedInput.length != 2) {
					joinConferenceResponse = new JoinConferenceResponse();
					joinConferenceResponse.setStatus(ScheduledRoomResponse.INVALID_SCHEDULED_ROOM);
					joinConferenceResponse.setDisplayMessage("Invalid Extension");
					return joinConferenceResponse;
				}
				// Array length is 2 now, arr[0] will be the roomname or extn
				// and arr[1] will be the tenant url
				roomNameExt = parsedInput[0];
				String tenantUrl = parsedInput[1];
				joinConferenceRequest.setTenantUrl(tenantUrl);
				joinConferenceRequest.setRoomNameExt(roomNameExt);
				joinConferenceResponse = joinInterPortalRoomConference(joinConferenceRequest);

			} else {
				// The input may be scheduled room extension
				joinConferenceResponse = joinConferenceInScheduledRoom(joinConferenceRequest);
			}

		} else {
			// Conference taking place in a room with a proper RoomId
			joinConferenceResponse = joinConferenceInRoom(joinConferenceRequest);
		}
		return joinConferenceResponse;
	}

	/**
	 * Invites a specific user to the conference. The invitee can be a
	 * Entity(Member/Room) or Legacy device. Delegates the call to appropriate
	 * service APIs based on the input parameters.
	 * 
	 * @param inviteToConferenceRequest
	 * @return
	 */
	public JoinConferenceResponse inviteParticipantToConference(
			InviteToConferenceRequest inviteToConferenceRequest) {
		JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
		logger.debug("Entering inviteParticpantToConference(InviteToConferenceRequest) of ConferenceAppService");
		// Either EntityId or Input should be present
		if (inviteToConferenceRequest.getInviteeId() <= 0
				&& StringUtils.isEmpty(inviteToConferenceRequest.getLegacy())) {
			joinConferenceResponse
					.setStatus(JoinConferenceResponse.REQUIRED_PARAMS_NOT_PRESENT);
			joinConferenceResponse.setDisplayMessage("EntityId/Input is invalid");
			return joinConferenceResponse;
		}
		// Validate the Room to Join
		Room roomToJoin = roomService
				.getRoomDetailsForConference(inviteToConferenceRequest
						.getRoomId());

		joinConferenceResponse = validateRoom(roomToJoin,
				inviteToConferenceRequest.getModeratorPin());
		if (joinConferenceResponse.getStatus() != JoinConferenceResponse.SUCCESS) {
			return joinConferenceResponse;
		}

		boolean legacy = false;
		Member invitee = null;
		// RoomId takes precedence over Legacy input
		if (inviteToConferenceRequest.getInviteeId() > 0) {
			invitee = memberService
					.getInviteeDetails(inviteToConferenceRequest.getInviteeId());
			if (invitee == null) {
				joinConferenceResponse
						.setStatus(JoinConferenceResponse.INVALID_MEMBER);
				joinConferenceResponse.setDisplayMessage("Invalid Member");					
				return joinConferenceResponse;
			}
			
			if (invitee.getRoleName().equalsIgnoreCase("Legacy")) {
				legacy = true;
			} else {
				if (invitee.getEndpointGUID() == null) {
					joinConferenceResponse
							.setStatus(JoinConferenceResponse.INVITED_MEMBER_NOT_ONLINE);
					joinConferenceResponse.setDisplayMessage("Status of invited member is not Online");
					return joinConferenceResponse;
				}				
				Member inviter = memberService.getInviterDetails(userService
						.getLoginUser().getMemberID());
				try {
					conferenceService.inviteParticipantToConference(roomToJoin,
							inviter, invitee, "D");
				} catch (OutOfPortsException | EndpointNotExistException | EndpointNotSupportedException
						| InviteConferenceException e) {
					joinConferenceResponse
							.setStatus(JoinConferenceResponse.JOIN_CONF_FAILED);
					joinConferenceResponse
					.setDisplayMessage("Failed to Invite");						
					return joinConferenceResponse;
				}
			}
		}

		if (legacy
				|| !StringUtils.isEmpty(inviteToConferenceRequest.getLegacy())) {
			Member inviter = memberService.getInviterDetails(userService
					.getLoginUser().getMemberID());
			// Create Legacy Details
			if(invitee == null) {
				invitee = new Member();
				invitee.setMemberName(inviteToConferenceRequest.getLegacy());
				invitee.setRoomExtNumber(inviteToConferenceRequest.getLegacy());
				invitee.setRoleName("Legacy");
				invitee.setTenantID(roomToJoin.getTenantID());
				if(!StringUtils.isEmpty(inviteToConferenceRequest.getCallFromIdentifier())) {
					inviter.setCallFromIdentifier(inviteToConferenceRequest.getCallFromIdentifier());
				}
			}
			try {
				conferenceService.inviteLegacyToConference(roomToJoin, inviter,
						invitee);
			} catch (InviteConferenceException | EndpointNotExistException
					| OutOfPortsException e) {
				joinConferenceResponse
						.setStatus(JoinConferenceResponse.JOIN_CONF_FAILED);
				joinConferenceResponse
				.setDisplayMessage("Failed to Invite");					
				return joinConferenceResponse;
			}
		}

		logger.debug("Exiting inviteParticpantToConference(InviteToConferenceRequest) of ConferenceAppService");
		return joinConferenceResponse;
	}

	/**
	 * Validates the Room before join conference.
	 * @param roomToJoin
	 * @param inputModeratorPin
	 * @return
	 */
	protected JoinConferenceResponse validateRoom(Room roomToJoin,
			String inputModeratorPin) {
		JoinConferenceResponse joinConferenceResponse = new JoinConferenceResponse();
		if (roomToJoin == null) {
			joinConferenceResponse
					.setStatus(JoinConferenceResponse.INVALID_ROOM);
			joinConferenceResponse.setMessage("invalid.room");
			joinConferenceResponse.setMessageId("Room");
			joinConferenceResponse.setDisplayMessage("Invalid Room");			
			return joinConferenceResponse;
		}

		if (roomToJoin.getRoomLocked() == 1) {
			joinConferenceResponse
					.setStatus(JoinConferenceResponse.ROOM_LOCKED);
			joinConferenceResponse.setMessage("room.is.locked");
			joinConferenceResponse.setMessageId("Lock");
			joinConferenceResponse.setDisplayMessage("Room is locked");			
			return joinConferenceResponse;
		}

		if (roomToJoin.getRoomEnabled() == 0) {
			joinConferenceResponse
					.setStatus(JoinConferenceResponse.ROOM_DISABLED);
			joinConferenceResponse.setMessage("room.is.disabled");
			joinConferenceResponse.setMessageId("RoomDisabled");
			joinConferenceResponse.setDisplayMessage("Room is disabled");			
			return joinConferenceResponse;
		}

		if (roomToJoin.getOccupantsCount() >= roomToJoin.getRoomMaxUsers()) {
			joinConferenceResponse
					.setStatus(JoinConferenceResponse.ROOM_CAPACITY_REACHED);
			joinConferenceResponse.setMessage("room.capacity.reached");
			joinConferenceResponse.setMessageId("Room Capacity Reached");
			joinConferenceResponse
					.setDisplayMessage("Room capacity has been reached");			
			return joinConferenceResponse;
		}

		User loggedInUser = userService.getLoginUser();

		boolean canControlRoom = canMemberControlRoom(
				loggedInUser.getMemberID(), roomToJoin.getRoomID(),
				roomToJoin.getMemberID(), roomToJoin.getRoomModeratorPIN(),
				inputModeratorPin, loggedInUser.getUserRole());

		if (!canControlRoom) {
			joinConferenceResponse
					.setStatus(JoinConferenceResponse.CANNOT_CONTROL_ROOM);
			joinConferenceResponse
			.setDisplayMessage("Control of conference not allowed");			
			return joinConferenceResponse;
		}

		return joinConferenceResponse;
	}

	/**
	 * Checks if the Member can control the room to which he is inviting the
	 * Users. Returns true if the Member is Admin/Operator or if Member's input
	 * moderator pin matches the room's Moderator pin
	 * 
	 * @param controllingMemberID
	 * @param roomID
	 * @param roomOwnerID
	 * @return
	 */
	public static boolean canMemberControlRoom(int controllingMemberID,
			int roomID, int roomOwnerID, String roomModeratorPin,
			String inputModeratorPin, String userRole) {
		boolean canControl = false;
		if (controllingMemberID == roomOwnerID) {
			canControl = true;
		} else {
			if ((userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_OPERATOR")) ||
                    (roomModeratorPin != null && !"".equals(roomModeratorPin) && roomModeratorPin.equalsIgnoreCase(inputModeratorPin))) {
				canControl = true;
			}
		}
		return canControl;
	}
	
	public LeaveConferenceResponse leaveConference(
			LeaveConferenceRequest leaveConferenceRequest) {
		LeaveConferenceResponse leaveConferenceResponse = new LeaveConferenceResponse();
		Room roomToDisconnect = roomService
				.getRoomDetailsForDisconnectParticipant(
						leaveConferenceRequest.getRoomId(),
						TenantContext.getTenantId());
		if (roomToDisconnect == null) {
			leaveConferenceResponse
					.setStatus(JoinConferenceResponse.INVALID_ROOM);
			leaveConferenceResponse.setMessage("invalid.room");
			leaveConferenceResponse.setMessageId("Room");
			leaveConferenceResponse.setDisplayMessage("Invalid Room");
			return leaveConferenceResponse;
		}
		User loggedInUser = userService.getLoginUser();
		boolean canControlRoom = canMemberControlRoom(
				loggedInUser.getMemberID(), roomToDisconnect.getRoomID(),
				roomToDisconnect.getMemberID(),
				roomToDisconnect.getRoomModeratorPIN(),
				leaveConferenceRequest.getModeratorPin(),
				loggedInUser.getUserRole());

		if (!canControlRoom) {
			leaveConferenceResponse
					.setStatus(JoinConferenceResponse.CANNOT_CONTROL_ROOM);
			leaveConferenceResponse
					.setDisplayMessage("Control of conference not allowed");
			return leaveConferenceResponse;
		}

		try {
			conferenceService.disconnectEndpointFromConference(
					leaveConferenceRequest.getEndpointId(), roomToDisconnect,
					CallCompletionCode.BY_SOMEONE_ELSE);
		} catch (LeaveConferenceException e) {
			if(e.getMessage().equalsIgnoreCase("Invalid Participant")) {
				leaveConferenceResponse
				.setDisplayMessage("Invalid Participant Id");
				leaveConferenceResponse
				.setStatus(LeaveConferenceResponse.INVALID_PARTICIPANT);				
			} else {
				leaveConferenceResponse
				.setDisplayMessage("Disconnect Participant failed");
				leaveConferenceResponse
				.setStatus(LeaveConferenceResponse.DISCONNECT_PARTICIPANT_FAILED);
			}
		}
		return leaveConferenceResponse;
	}

}
