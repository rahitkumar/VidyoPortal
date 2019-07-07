package com.vidyo.db.lecturemode;

import java.util.List;

public interface LectureModeDao {

	// user/service methods

	public int raiseHandForMember(int memberID);
	public int unraiseHandForMember(int memberID);

	public int raiseHandForGuest(int guestID, String username);
	public int unraiseHandForGuest(int guestID, String username);

	// control meeting methods

	public int setLectureModeFlag(int roomID, boolean flag);

	public int clearLectureModeState(int roomID);

	public List<String> getEndpointGUIDsInConference(int roomID);

	public List<String> getEndpointGUIDsWithHandsRaised(int roomID);

	public int dismissAllHands(int roomID);

	public int dismissHand(String handRaisedGUID);

	public String getCurrentPresenterGUID(int roomID);

	public int setPresenterFlag(String currentPresenterGUID, boolean flag);

	public String getRouterParticipantIDForGUID(String newPresenterGUID);

    public List<String> getEndpointsWithoutLectureModeSupportInRoom(int roomID);

    public int getCountEndpointsWithoutLectureModeSupportInRoom(int roomID);
}
