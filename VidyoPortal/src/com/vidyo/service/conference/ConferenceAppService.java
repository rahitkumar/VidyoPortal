/**
 * 
 */
package com.vidyo.service.conference;

import com.vidyo.service.conference.request.GuestJoinConfRequest;
import com.vidyo.service.conference.request.InviteToConferenceRequest;
import com.vidyo.service.conference.request.JoinConferenceRequest;
import com.vidyo.service.conference.request.LeaveConferenceRequest;
import com.vidyo.service.conference.response.JoinConferenceResponse;
import com.vidyo.service.conference.response.LeaveConferenceResponse;
import com.vidyo.service.room.ScheduledRoomResponse;

/**
 * @author Ganesh
 * 
 */
public interface ConferenceAppService {

	/**
	 * Allows the user to join the conference in remote portal using
	 * Inter-Portal/Intra-Portal Conferencing feature. Supports both normal and
	 * scheduled rooms.
	 * 
	 * @param joinConferenceRequest
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	public JoinConferenceResponse joinInterPortalRoomConference(JoinConferenceRequest joinConferenceRequest);

	/**
	 * Allows the User to join the conference in the specified room.
	 * 
	 * @param joinConferenceRequest
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	public JoinConferenceResponse joinConferenceInRoom(JoinConferenceRequest joinConferenceRequest);

	/**
	 * Validates the Scheduled Room extension & pin and allows the user to join
	 * the scheduled room.
	 * 
	 * @param joinConferenceRequest
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	public JoinConferenceResponse joinConferenceInScheduledRoom(JoinConferenceRequest joinConferenceRequest);

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
	public ScheduledRoomResponse validateScheduledRoom(String roomExt, String pin);

	/**
	 * Allows the guest user to join the specified room after validation
	 * 
	 * @param guestJoinConfRequest
	 *            Request holding GuestId
	 * @return Response object with status code
	 * @see {@link JoinConferenceResponse}
	 */
	public JoinConferenceResponse joinConferenceAsGuest(GuestJoinConfRequest guestJoinConfRequest);
	
	/**
	 * Delegates the call to appropriate APIs based on the input parameters.<br>
	 * Currently handles conference request based on roomId, Scheduled Room extension and IPC room names.<br>
	 * 
	 * @param joinConferenceRequest
	 * @return
	 */
	public JoinConferenceResponse joinConference(JoinConferenceRequest joinConferenceRequest);
	
	/**
	 * Invites a specific user to the conference. The invitee can be a
	 * Entity(Member/Room) or Legacy device. Delegates the call to appropriate
	 * service APIs based on the input parameters.
	 * 
	 * @param inviteToConferenceRequest
	 * @return
	 */
	public JoinConferenceResponse inviteParticipantToConference(
			InviteToConferenceRequest inviteToConferenceRequest);
	
	public LeaveConferenceResponse leaveConference(
			LeaveConferenceRequest leaveConferenceRequest);	

}
