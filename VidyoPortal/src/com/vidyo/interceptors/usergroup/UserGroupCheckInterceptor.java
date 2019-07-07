package com.vidyo.interceptors.usergroup;

import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.portal.guest.GuestJoinConferenceRequest;
import com.vidyo.portal.guest.LinkEndpointToGuestRequest;
import com.vidyo.portal.guest.LogInAsGuestRequest;
import com.vidyo.portal.user.v1_1.*;
import com.vidyo.service.*;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.usergroup.UserGroupService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGroupCheckInterceptor implements MethodInterceptor {

	protected static final Logger logger = LoggerFactory.getLogger(UserGroupCheckInterceptor.class);

	private IUserService userService;

	private ITenantService tenantService;


	@Autowired
    private IMemberService memberService;

	private IRoomService roomService;

	private IConferenceService conferenceService;

	@Autowired
	private UserGroupService userGroupService;

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	public void setConferenceService(IConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}


	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		for (Object obj : invocation.getArguments()) {
			if (obj instanceof JoinConferenceRequest) {
				JoinConferenceRequest joinConferenceRequest = (JoinConferenceRequest) obj;

				Integer roomId = null;

				if (joinConferenceRequest.getJoinConferenceRequestChoice_type0().getConferenceID() != null && StringUtils.isNotBlank(joinConferenceRequest.getJoinConferenceRequestChoice_type0()
						.getConferenceID().getEntityID()) && StringUtils.isNumeric(joinConferenceRequest.getJoinConferenceRequestChoice_type0()
								.getConferenceID().getEntityID())) {
					roomId = Integer.valueOf(joinConferenceRequest.getJoinConferenceRequestChoice_type0()
							.getConferenceID().getEntityID());
				}

				String roomNameExt = null;
				if (joinConferenceRequest.getJoinConferenceRequestChoice_type0().getExtension() != null) {
					roomNameExt = joinConferenceRequest.getJoinConferenceRequestChoice_type0().getExtension();
				}

				String pin = joinConferenceRequest.getPIN();
				if (pin == null) {
					pin = "";
				}

				if (roomId != null) {
					applyUserGroupChecks(roomId, APIType.USER);
				} else if (StringUtils.isNotBlank(roomNameExt)) {
					roomId = findRoomByRoomNameExtAndPin(roomNameExt, pin);
					if(roomId != null){
						applyUserGroupChecks(roomId, APIType.USER);
					}
				}
				break;
			} else if (obj instanceof InviteToConferenceRequest) {

				InviteToConferenceRequest inviteToConferenceRequest = (InviteToConferenceRequest) obj;
				String strRoomId = inviteToConferenceRequest.getConferenceID().getEntityID();

				InviteToConferenceRequestChoice_type0 inviteToConferenceRequestChoice = inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0();
				com.vidyo.portal.user.v1_1.EntityID entityID = inviteToConferenceRequestChoice.getEntityID();
				//apply the validation for restricted rooms for both the invitee and the logged in user.
				applyUserGroupChecks(strRoomId, APIType.USER);
				if (entityID != null && StringUtils.isNotBlank(entityID.getEntityID()) && StringUtils.isNumeric(entityID.getEntityID())) {
			        Member invitee = memberService.getInviteeDetails(Integer.parseInt(entityID.getEntityID()));
					applyUserGroupCheckForInvitee(Integer.parseInt(strRoomId), invitee.getMemberID(), APIType.USER);
				}
				break;
			} else if (obj instanceof DisconnectConferenceAllRequest) {
				DisconnectConferenceAllRequest disconnectConferenceAllRequest = (DisconnectConferenceAllRequest) obj;
				String strRoomId = disconnectConferenceAllRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof LeaveConferenceRequest) {
				LeaveConferenceRequest leaveConferenceRequest = (LeaveConferenceRequest) obj;
				String strRoomId = leaveConferenceRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof MuteAudioRequest) {
				MuteAudioRequest muteAudioRequest = (MuteAudioRequest) obj;
				String strRoomId = muteAudioRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof MuteAudioClientAllRequest) {
				MuteAudioClientAllRequest muteAudioClientAllRequest = (MuteAudioClientAllRequest) obj;
				String strRoomId = muteAudioClientAllRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof MuteAudioServerAllRequest) {
				MuteAudioServerAllRequest muteAudioServerAllRequest = (MuteAudioServerAllRequest) obj;
				String strRoomId = muteAudioServerAllRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof MuteVideoClientAllRequest) {
				MuteVideoClientAllRequest muteVideoClientAllRequest = (MuteVideoClientAllRequest) obj;
				String strRoomId = muteVideoClientAllRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof MuteVideoServerAllRequest) {
				MuteVideoServerAllRequest muteVideoServerAllRequest = (MuteVideoServerAllRequest) obj;
				String strRoomId = muteVideoServerAllRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof PauseRecordingRequest) {
				PauseRecordingRequest pauseRecordingRequest = (PauseRecordingRequest) obj;
				String strRoomId = pauseRecordingRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof ResumeRecordingRequest) {
				ResumeRecordingRequest resumeRecordingRequest = (ResumeRecordingRequest) obj;
				String strRoomId = resumeRecordingRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof SilenceSpeakerServerRequest) {
				SilenceSpeakerServerRequest silenceSpeakerServerRequest = (SilenceSpeakerServerRequest) obj;
				String strRoomId = 	silenceSpeakerServerRequest.getSilenceSpeakerServerRequest().getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof SilenceSpeakerServerAllRequest) {
				SilenceSpeakerServerAllRequest silenceSpeakerServerAllRequest = (SilenceSpeakerServerAllRequest) obj;
				String strRoomId = silenceSpeakerServerAllRequest.getSilenceSpeakerServerAllRequest().getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof StartLectureModeRequest) {
				StartLectureModeRequest startLectureModeRequest = (StartLectureModeRequest) obj;
				String strRoomId = startLectureModeRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof StartRecordingRequest) {
				StartRecordingRequest startRecordingRequest = (StartRecordingRequest) obj;
				String strRoomId = startRecordingRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof StartVideoRequest) {
				StartVideoRequest startVideoRequest = (StartVideoRequest) obj;
				String strRoomId = startVideoRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof StopLectureModeRequest) {
				StopLectureModeRequest stopLectureModeRequest = (StopLectureModeRequest) obj;
				String strRoomId = stopLectureModeRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof StopRecordingRequest) {
				StopRecordingRequest stopRecordingRequest = (StopRecordingRequest) obj;
				String strRoomId = stopRecordingRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof StopVideoRequest) {
				StopVideoRequest stopVideoRequest = (StopVideoRequest) obj;
				String strRoomId = stopVideoRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof UnmuteAudioRequest) {
				UnmuteAudioRequest unmuteAudioRequest = (UnmuteAudioRequest) obj;
				String strRoomId = unmuteAudioRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof SetPresenterRequest) {
				SetPresenterRequest setPresenterRequest = (SetPresenterRequest) obj;
				String strRoomId = setPresenterRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(strRoomId, APIType.USER);
				break;
			} else if (obj instanceof com.vidyo.portal.admin.v1_1.InviteToConferenceRequest){
				com.vidyo.portal.admin.v1_1.InviteToConferenceRequest inviteToConferenceRequest = (com.vidyo.portal.admin.v1_1.InviteToConferenceRequest) obj;
				int roomId = inviteToConferenceRequest.getConferenceID().getEntityID();
				com.vidyo.portal.admin.v1_1.InviteToConferenceRequestChoice_type0 inviteToConferenceRequestChoice = inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0();
				com.vidyo.portal.admin.v1_1.EntityID entityID = inviteToConferenceRequestChoice.getEntityID();

				applyUserGroupChecks(roomId, APIType.ADMIN);
				//apply the validation for restricted rooms for both the invitee and the logged in user.
				if (entityID != null ) {
					Member invitee = memberService.getInviteeDetails(entityID.getEntityID());
					applyUserGroupCheckForInvitee(roomId, invitee.getMemberID(), APIType.ADMIN);
				}
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.LeaveConferenceRequest){
				com.vidyo.portal.admin.v1_1.LeaveConferenceRequest leaveConferenceRequest = (com.vidyo.portal.admin.v1_1.LeaveConferenceRequest)obj;
				int roomId = leaveConferenceRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.MuteAudioRequest){
				com.vidyo.portal.admin.v1_1.MuteAudioRequest muteAudioRequest = (com.vidyo.portal.admin.v1_1.MuteAudioRequest)obj;
				int roomId = muteAudioRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.PauseRecordingRequest){
				com.vidyo.portal.admin.v1_1.PauseRecordingRequest pauseRecordingRequest = (com.vidyo.portal.admin.v1_1.PauseRecordingRequest)obj;
				int roomId = pauseRecordingRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.RemovePresenterRequest){
				com.vidyo.portal.admin.v1_1.RemovePresenterRequest removePresenterRequest = (com.vidyo.portal.admin.v1_1.RemovePresenterRequest)obj;
				int roomId = removePresenterRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest){
				com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest removeRoomPINRequest = (com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest)obj;
				int roomId = removeRoomPINRequest.getRoomID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			}  else if ( obj instanceof com.vidyo.portal.admin.v1_1.SetPresenterRequest){
				com.vidyo.portal.admin.v1_1.SetPresenterRequest setPresenterRequest = (com.vidyo.portal.admin.v1_1.SetPresenterRequest)obj;
				int roomId = setPresenterRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.StartLectureModeRequest){
				com.vidyo.portal.admin.v1_1.StartLectureModeRequest startLectureModeRequest = (com.vidyo.portal.admin.v1_1.StartLectureModeRequest)obj;
				int roomId = startLectureModeRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.StartRecordingRequest){
				com.vidyo.portal.admin.v1_1.StartRecordingRequest startRecordingRequest = (com.vidyo.portal.admin.v1_1.StartRecordingRequest)obj;
				int roomId = startRecordingRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.StartVideoRequest){
				com.vidyo.portal.admin.v1_1.StartVideoRequest startVideoRequest = (com.vidyo.portal.admin.v1_1.StartVideoRequest)obj;
				int roomId = startVideoRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.StopLectureModeRequest){
				com.vidyo.portal.admin.v1_1.StopLectureModeRequest stopLectureModeRequest = (com.vidyo.portal.admin.v1_1.StopLectureModeRequest)obj;
				int roomId = stopLectureModeRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.StopRecordingRequest){
				com.vidyo.portal.admin.v1_1.StopRecordingRequest stopRecordingRequest = (com.vidyo.portal.admin.v1_1.StopRecordingRequest)obj;
				int roomId = stopRecordingRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.StopVideoRequest){
				com.vidyo.portal.admin.v1_1.StopVideoRequest stopVideoRequest = (com.vidyo.portal.admin.v1_1.StopVideoRequest)obj;
				int roomId = stopVideoRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.TransferParticipantRequest){
				com.vidyo.portal.admin.v1_1.TransferParticipantRequest transferParticipantRequest = (com.vidyo.portal.admin.v1_1.TransferParticipantRequest)obj;
				int roomId = transferParticipantRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.UnmuteAudioRequest){
				com.vidyo.portal.admin.v1_1.UnmuteAudioRequest unmuteAudioRequest = (com.vidyo.portal.admin.v1_1.UnmuteAudioRequest)obj;
				int roomId = unmuteAudioRequest.getConferenceID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if ( obj instanceof com.vidyo.portal.admin.v1_1.UpdateRoomRequest){
				com.vidyo.portal.admin.v1_1.UpdateRoomRequest updateRoomRequest = (com.vidyo.portal.admin.v1_1.UpdateRoomRequest)obj;
				int roomId = updateRoomRequest.getRoomID().getEntityID();
				applyUserGroupChecks(roomId, APIType.ADMIN);
				break;
			} else if (obj instanceof GuestJoinConferenceRequest) {
				GuestJoinConferenceRequest guestJoinConferenceRequest = (GuestJoinConferenceRequest) obj;
				User guestUser = userService.getUserForGuestID(guestJoinConferenceRequest.getGuestID());
				if (guestUser != null) {
					int roomId = guestUser.getRoomID();
					applyUserGroupChecksForGuest(roomId, APIType.GUEST);
				}
				break;
			} else if (obj instanceof LinkEndpointToGuestRequest) {
				LinkEndpointToGuestRequest linkEndpointToGuestRequest = (LinkEndpointToGuestRequest) obj;
				String endpointGUID = linkEndpointToGuestRequest.getEID();
				if(!endpointGUID.equalsIgnoreCase("")) {
					int guestID = linkEndpointToGuestRequest.getGuestID();
					if(guestID > 0) {
						int endpointID = conferenceService.getEndpointIDForGUID(endpointGUID, "D");
			            int roomID = conferenceService.getRoomIDForEndpointID(endpointID);
						applyUserGroupChecksForGuest(roomID, APIType.GUEST);
					}
				}
				break;
			} else if (obj instanceof LogInAsGuestRequest) {
				LogInAsGuestRequest logInAsGuestRequest = (LogInAsGuestRequest) obj;
				String roomKey = logInAsGuestRequest.getRoomKey();
				Room room;
				if (!(roomKey == null || roomKey.equalsIgnoreCase(""))) {
					room = roomService.getRoomDetailsByRoomKey(roomKey, TenantContext.getTenantId());
					if (room != null) {
						applyUserGroupChecksForGuest(room.getRoomID(), APIType.GUEST);
					}
				}
				break;
			}
		}
		return invocation.proceed();
	}


	private Integer findRoomByRoomNameExtAndPin(String roomNameExt, String pin) {

		if (StringUtils.isBlank(roomNameExt)) {
			return null;
		}

		Room room = null;
		if (org.springframework.util.StringUtils.countOccurrencesOf(roomNameExt, "@") == 1) {
			String[] parsedInput = roomNameExt.trim().split("@");
			if (parsedInput.length != 2) {
				logger.error("Invalid Room Extension : " + roomNameExt);
				return null;
			}
			String roomNameExtension = parsedInput[0];
			String tenantUrl = parsedInput[1];

			Tenant inputTenant = tenantService.getTenantByURL(tenantUrl);

			if (inputTenant != null) {
				room = roomService.getRoomDetailsForConference(roomNameExtension, inputTenant.getTenantID());
			}
		} else {
			ScheduledRoomResponse scheduledRoomResponse = roomService.validateScheduledRoom(roomNameExt, pin, 0);
			if(scheduledRoomResponse != null) {
				room = scheduledRoomResponse.getRoom();
			}
		}

		if (room != null) {
			return room.getRoomID();
		}

		return null;
	}

	private void applyUserGroupChecks(String strRoomId, APIType apiType) throws Throwable {
		if (StringUtils.isNumeric(strRoomId)) {
			applyUserGroupChecks(Integer.parseInt(strRoomId), apiType);
		}
	}

	/**
	 * This method is to check if the invitee can join the conference and the room and the invitee should have a usergroup in common.
	 *
	 * @param roomId
	 * @param entityId
	 * @param apiType
	 * @throws Throwable
	 */
	private void applyUserGroupCheckForInvitee(int roomId, int entityId, APIType apiType) throws Throwable {
		if ( ! userGroupService.canMemberAccessRoom(roomId, entityId)){
			logger.error(String.format("Member, %d, and Room, %d, have no user groups in common", entityId, roomId));
			throwInvalidArgumentException(apiType);
		}

	}

	private void applyUserGroupChecks(Integer roomId, APIType apiType) throws Throwable {
			User user = userService.getLoginUser();
			int memberId = user.getMemberID();
		if ( ! userGroupService.canMemberAccessRoom(roomId, memberId)){
			logger.error(String.format("Member, %d, and Room, %d, have no user groups in common", memberId,	roomId));
			throwInvalidArgumentException(apiType);
		}
	}

	private void applyUserGroupChecksForGuest(Integer roomId, APIType apiType) throws Throwable {
		if (! userGroupService.canGuestAccessRoom(roomId)) {
			logger.error(String.format("Guests cannot access Restricted Room %d", roomId));
			throwInvalidArgumentException(apiType);
		}
	}

	private void throwInvalidArgumentException(APIType apiType) throws Throwable {
		switch (apiType) {
		case ADMIN:
			throwInvalidArgumentExceptionForAdminAPIs();
		case USER:
			throwInvalidArgumentExceptionForUserAPIs();
		case GUEST:
			throwInvalidArgumentExceptionForGuestAPIs();
		}
	}

	private void throwInvalidArgumentExceptionForAdminAPIs()
			throws com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException {
		com.vidyo.portal.admin.v1_1.InvalidArgumentFault fault = new com.vidyo.portal.admin.v1_1.InvalidArgumentFault();
		fault.setErrorMessage("Cannot access room");
		com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException exception = new com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException(
				fault.getErrorMessage());
		exception.setFaultMessage(fault);
		throw exception;
	}

	private void throwInvalidArgumentExceptionForUserAPIs() throws InvalidArgumentFaultException {
		InvalidArgumentFault fault = new InvalidArgumentFault();
		fault.setErrorMessage("Cannot access room");
		InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
		exception.setFaultMessage(fault);
		throw exception;
	}

	private void throwInvalidArgumentExceptionForGuestAPIs()
			throws com.vidyo.portal.guest.InvalidArgumentFaultException {
		com.vidyo.portal.guest.InvalidArgumentFault fault = new com.vidyo.portal.guest.InvalidArgumentFault();
		fault.setErrorMessage("Cannot access room");
		com.vidyo.portal.guest.InvalidArgumentFaultException exception = new com.vidyo.portal.guest.InvalidArgumentFaultException(
				fault.getErrorMessage());
		exception.setFaultMessage(fault);
		throw exception;
	}

	enum APIType {
		ADMIN, USER, GUEST
	}

}
