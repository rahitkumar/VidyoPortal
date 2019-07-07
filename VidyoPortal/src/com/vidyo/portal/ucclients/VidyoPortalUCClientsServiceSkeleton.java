/**
 * VidyoPortalUCClientsServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.portal.ucclients;

import com.vidyo.bo.*;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.service.*;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.conference.request.JoinConferenceRequest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VidyoPortalUCClientsServiceSkeleton java skeleton for the axisService
 */
public class VidyoPortalUCClientsServiceSkeleton implements
		VidyoPortalUCClientsServiceSkeletonInterface {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(VidyoPortalUCClientsServiceSkeleton.class.getName());

	private IUserService user;
	private IMemberService member;
	private IRoomService room;
	private IConferenceService conference;
	private LicensingService license;
	private ISystemService system;
	private ITenantService tenantService;
	
	private ConferenceAppService conferenceAppService;

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

	public void setLicense(LicensingService license) {
		this.license = license;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	private void checkLicenseForAllowUCClients()
			throws NotLicensedFaultException {
		SystemLicenseFeature licensed = this.license
				.getSystemLicenseFeature("AllowOCS");
		if (licensed == null
				|| licensed.getLicensedValue().equalsIgnoreCase("false")) {
			throw new NotLicensedFaultException("Operation is not licensed");
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getUserStatusRequest
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
	public com.vidyo.portal.ucclients.GetUserStatusResponse getUserStatus(
			com.vidyo.portal.ucclients.GetUserStatusRequest getUserStatusRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUCClients();

		GetUserStatusResponse resp = new GetUserStatusResponse();
		User user = this.user.getLoginUser();

		String userName = getUserStatusRequest.getUsername();
		String tenant = getUserStatusRequest.getTenant();

		if (tenant != null && !tenant.equalsIgnoreCase("")) {
			Member member = this.member.getMemberByName(userName, tenant);
			com.vidyo.bo.Entity entity = this.member.getContact(member
					.getMemberID());

			UserStatus_type0 userStatus = new UserStatus_type0();
			userStatus.setUsername(entity.getUsername());
			userStatus.setTenant(entity.getTenantName());
			if (entity.getMemberStatus() == 0) {
				userStatus.setMemberStatus(MemberStatus_type0.Offline);
			} else if (entity.getMemberStatus() == 1) {
				userStatus.setMemberStatus(MemberStatus_type0.Online);
			} else if (entity.getMemberStatus() == 2) {
				userStatus.setMemberStatus(MemberStatus_type0.Busy);
			} else if (entity.getMemberStatus() == 3) {
				userStatus.setMemberStatus(MemberStatus_type0.Ringing);
			} else if (entity.getMemberStatus() == 4) {
				userStatus.setMemberStatus(MemberStatus_type0.RingAccepted);
			} else if (entity.getMemberStatus() == 5) {
				userStatus.setMemberStatus(MemberStatus_type0.RingRejected);
			} else if (entity.getMemberStatus() == 6) {
				userStatus.setMemberStatus(MemberStatus_type0.RingNoAnswer);
			} else if (entity.getMemberStatus() == 7) {
				userStatus.setMemberStatus(MemberStatus_type0.Alerting);
			} else if (entity.getMemberStatus() == 8) {
				userStatus.setMemberStatus(MemberStatus_type0.AlertCancelled);
			} else if (entity.getMemberStatus() == 9) {
				userStatus.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
			} else if (entity.getMemberStatus() == 12) {
				userStatus.setMemberStatus(MemberStatus_type0.Busy); // WaitForConfirm
			}

			resp.addUserStatus(userStatus);
		} else {
			EntityFilter filter = new EntityFilter();
			filter.setLimit(Integer.MAX_VALUE);
			filter.setQuery(userName);

			List<com.vidyo.bo.Entity> users = this.room.getEntity(filter, user);

			for (com.vidyo.bo.Entity entity : users) {
				UserStatus_type0 userStatus = new UserStatus_type0();
				userStatus.setUsername(entity.getUsername());
				userStatus.setTenant(entity.getTenantName());
				if (entity.getMemberStatus() == 0) {
					userStatus.setMemberStatus(MemberStatus_type0.Offline);
				} else if (entity.getMemberStatus() == 1) {
					userStatus.setMemberStatus(MemberStatus_type0.Online);
				} else if (entity.getMemberStatus() == 2) {
					userStatus.setMemberStatus(MemberStatus_type0.Busy);
				} else if (entity.getMemberStatus() == 3) {
					userStatus.setMemberStatus(MemberStatus_type0.Ringing);
				} else if (entity.getMemberStatus() == 4) {
					userStatus.setMemberStatus(MemberStatus_type0.RingAccepted);
				} else if (entity.getMemberStatus() == 5) {
					userStatus.setMemberStatus(MemberStatus_type0.RingRejected);
				} else if (entity.getMemberStatus() == 6) {
					userStatus.setMemberStatus(MemberStatus_type0.RingNoAnswer);
				} else if (entity.getMemberStatus() == 7) {
					userStatus.setMemberStatus(MemberStatus_type0.Alerting);
				} else if (entity.getMemberStatus() == 8) {
					userStatus
							.setMemberStatus(MemberStatus_type0.AlertCancelled);
				} else if (entity.getMemberStatus() == 9) {
					userStatus
							.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
				} else if (entity.getMemberStatus() == 12) {
					userStatus.setMemberStatus(MemberStatus_type0.Busy); // WaitForConfirm
				}

				resp.addUserStatus(userStatus);
			}
		}
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param createMyRoomURLRequest
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
	public com.vidyo.portal.ucclients.CreateMyRoomURLResponse createMyRoomURL(
			com.vidyo.portal.ucclients.CreateMyRoomURLRequest createMyRoomURLRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUCClients();

		CreateMyRoomURLResponse resp = new CreateMyRoomURLResponse();
		User user = this.user.getLoginUser();

		Member member = this.member.getMember(user.getMemberID());
		Room room = this.room.getRoom(member.getRoomID());

		if (room != null) {
			if (room.getMemberID() != user.getMemberID()) {
				throw new InvalidArgumentFaultException(
						"You are not an owner of room for roomID = "
								+ room.getRoomID());
			}
			this.room.generateRoomKey(room);
		}

		resp.setStatus(Status_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param startMyConferenceRequest
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws RoomDisabledFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.ucclients.StartMyConferenceResponse startMyConference(
			com.vidyo.portal.ucclients.StartMyConferenceRequest startMyConferenceRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, RoomDisabledFaultException,
			SeatLicenseExpiredFaultException {
		checkLicenseForAllowUCClients();

		StartMyConferenceResponse resp = new StartMyConferenceResponse();
		User user = this.user.getLoginUser();

		int memberID = user.getMemberID();
		Member joiningMember = member.getInviterDetails(memberID);
		JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();
		joinConferenceRequest.setJoiningMember(joiningMember);
		joinConferenceRequest.setJoiningMemberId(memberID);
		joinConferenceRequest.setRoomId(joiningMember.getRoomID());
		
		com.vidyo.service.conference.response.JoinConferenceResponse joinConferenceResponse = conferenceAppService.joinConferenceInRoom(joinConferenceRequest);
		
		if(joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.ROOM_DISABLED) {
			throw new RoomDisabledFaultException();
		} else if(joinConferenceResponse.getStatus() != com.vidyo.service.conference.response.JoinConferenceResponse.SUCCESS) {
			GeneralFault generalFault = new GeneralFault();
			generalFault.setErrorMessage(joinConferenceResponse.getMessage());
			GeneralFaultException generalFaultException = new GeneralFaultException();
			generalFaultException.setFaultMessage(generalFault);
			throw generalFaultException;
		}

		resp.setStatus(Status_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature get Browser Access Key
	 * 
	 * @param browserAccessKeyRequest
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
	public com.vidyo.portal.ucclients.BrowserAccessKeyResponse getBrowserAccessKey(
			com.vidyo.portal.ucclients.BrowserAccessKeyRequest browserAccessKeyRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUCClients();

		BrowserAccessKeyResponse resp = new BrowserAccessKeyResponse();
		User user = this.user.getLoginUser();

		// Adding the legacy BAK type to maintain backward compatibility of the API.
		// The bak parameter in flex.html isn't supported any more.
		String BAK = this.user.generateBAKforMember(user.getMemberID(), MemberBAKType.Legacy);

		StringBuffer path = new StringBuffer();
		Tenant tenant = this.tenantService.getTenant(user.getTenantID());
		path.append("http://").append(tenant.getTenantURL());
		path.append("/flex.html?bak=");
		path.append(BAK);

		resp.setBrowserAccessKey(path.toString());
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
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */
	public com.vidyo.portal.ucclients.JoinConferenceResponse joinConference(
			com.vidyo.portal.ucclients.JoinConferenceRequest joinConferenceRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUCClients();

		JoinConferenceResponse resp = new JoinConferenceResponse();
		User user = this.user.getLoginUser();

		String toUserName = joinConferenceRequest.getUsername();
		String toTenant = joinConferenceRequest.getTenant();

		Member toMember = this.member.getMemberByName(toUserName, toTenant);

		int roomIdToJoin = toMember.getRoomID();
		JoinConferenceRequest conferenceRequest = new JoinConferenceRequest();
		conferenceRequest.setJoiningMemberId(user.getMemberID());
		conferenceRequest.setRoomId(roomIdToJoin);
		com.vidyo.service.conference.response.JoinConferenceResponse conferenceResponse = conferenceAppService.joinConferenceInRoom(conferenceRequest);
		
		if (conferenceResponse.getStatus() != com.vidyo.service.conference.response.JoinConferenceResponse.SUCCESS) {
			GeneralFault generalFault = new GeneralFault();
			generalFault.setErrorMessage(conferenceResponse.getMessage());
			GeneralFaultException generalFaultException = new GeneralFaultException();
			generalFaultException.setFaultMessage(generalFault);
			throw generalFaultException;
		}

		resp.setStatus(Status_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getUserDataRequest
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
	public com.vidyo.portal.ucclients.GetUserDataResponse getUserData(
			com.vidyo.portal.ucclients.GetUserDataRequest getUserDataRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		checkLicenseForAllowUCClients();

		GetUserDataResponse resp = new GetUserDataResponse();
		User user = this.user.getLoginUser();

		String EID = getUserDataRequest.getEID().getEID_type0();
		if (EID.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Bad EID = " + EID);
		}

		Member member = this.member.getMember(user.getMemberID());
		if (!member.getEndpointGUID().equalsIgnoreCase(EID)) {
			throw new InvalidArgumentFaultException("User '"
					+ user.getUsername() + "' is not linked to EID '" + EID
					+ "'");
		}

		try {
			com.vidyo.bo.Entity entity = this.member.getContact(user
					.getMemberID());
			Entity_type0 ws_entity = new Entity_type0();
			EntityID id = new EntityID();
			id.setEntityID(entity.getRoomID());
			ws_entity.setEntityID(id);
			ws_entity.setEntityType(EntityType_type0.Member);
			ws_entity.setDisplayName(entity.getName());
			ws_entity.setExtension(entity.getExt());
			ws_entity.setDescription(entity.getDescription());
			ws_entity.setTenant(entity.getTenantName());

			if (user.getLangID() == 1) {
				ws_entity.setLanguage(Language_type0.en);
			} else if (user.getLangID() == 2) {
				ws_entity.setLanguage(Language_type0.fr);
			} else if (user.getLangID() == 3) {
				ws_entity.setLanguage(Language_type0.ja);
			} else if (user.getLangID() == 4) {
				ws_entity.setLanguage(Language_type0.zh_CN);
			} else if (user.getLangID() == 5) {
				ws_entity.setLanguage(Language_type0.es);
			} else if (user.getLangID() == 6) {
				ws_entity.setLanguage(Language_type0.it);
			} else if (user.getLangID() == 7) {
				ws_entity.setLanguage(Language_type0.de);
			} else if (user.getLangID() == 8) {
				ws_entity.setLanguage(Language_type0.ko);
			} else if (user.getLangID() == 9) {
				ws_entity.setLanguage(Language_type0.pt);
			} else if (member.getLangID() == 10) {
				ws_entity.setLanguage(Language_type0.Factory
						.fromValue(this.system.getSystemLang(
								member.getTenantID()).getLangCode()));
			} else if (member.getLangID() == 11) {
				ws_entity.setLanguage(Language_type0.fi);
			} else if (member.getLangID() == 12) {
				ws_entity.setLanguage(Language_type0.pl);
			} else if (member.getLangID() == 13) {
				ws_entity.setLanguage(Language_type0.zh_TW);
			} else if (member.getLangID() == 14) {
				ws_entity.setLanguage(Language_type0.th);
			}

			if (entity.getModeID() == 1) {
				ws_entity.setMemberMode(MemberMode_type0.Available);
			} else if (entity.getModeID() == 2) {
				ws_entity.setMemberMode(MemberMode_type0.Away);
			} else if (entity.getModeID() == 3) {
				ws_entity.setMemberMode(MemberMode_type0.DoNotDisturb);
			}

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
			if (entity.getRoomPIN() != null) {
				roomMode.setRoomPIN(entity.getRoomPIN());
			}
			if (entity.getRoomKey() != null) {
				StringBuffer path = new StringBuffer();
				Tenant tenant = this.tenantService.getTenant(entity
						.getTenantID());
				/*path.append("http://").append(tenant.getTenantURL());
				path.append("/flex.html?roomdirect.html&key=");
				path.append(entity.getRoomKey());*/

				String joinURL = this.room.getRoomURL(system, "http", 
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

			resp.setEntity(ws_entity);

			return resp;

		} catch (Exception e) {
			throw new GeneralFaultException(e.getMessage());
		}
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
	public com.vidyo.portal.ucclients.InviteToConferenceResponse inviteToConference(
			com.vidyo.portal.ucclients.InviteToConferenceRequest inviteToConferenceRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException {
		InviteToConferenceResponse resp = new InviteToConferenceResponse();
		User user = this.user.getLoginUser();

		Member fromMember = this.member.getMember(user.getMemberID());

		Invite invite = new Invite();
		invite.setFromMemberID(fromMember.getMemberID());
		invite.setFromRoomID(fromMember.getRoomID());

		String toUserName = inviteToConferenceRequest.getUsername();
		String toTenant = inviteToConferenceRequest.getTenant();

		Member toMember = this.member.getMemberByName(toUserName, toTenant);

		invite.setToMemberID(toMember.getMemberID());

		int toEndpointID = this.conference.getEndpointIDForMemberID(toMember
				.getMemberID());
		invite.setToEndpointID(toEndpointID);

		// check if status of Endpoint is Online
		int status = this.conference.getEndpointStatus(toEndpointID);
		if (status != 1) {
			throw new InvalidArgumentFaultException(
					"Status of invited member is not Online.");
		}

		try {
			this.conference.inviteToConference(invite);
		} catch (Exception e) {
			throw new GeneralFaultException(e.getMessage());
		}

		resp.setStatus(Status_type1.OK);
		return resp;
	}

}
