package com.vidyo.service.lecturemode;

import com.vidyo.service.lecturemode.request.HandUpdateRequest;
import com.vidyo.service.lecturemode.request.LectureModeControlRequest;
import com.vidyo.service.lecturemode.request.LectureModeParticipantControlRequest;
import com.vidyo.service.lecturemode.request.LectureModeParticipantsRequest;
import com.vidyo.service.lecturemode.response.HandUpdateResponse;
import com.vidyo.service.lecturemode.response.LectureModeControlResponse;
import com.vidyo.service.lecturemode.response.LectureModeParticipantsResponse;

public interface LectureModeService {

	// user or guest operation
	public HandUpdateResponse updateHand(HandUpdateRequest request);

	// moderator or admin control meeting operations
	public LectureModeControlResponse startLectureMode(LectureModeControlRequest request);
	public LectureModeControlResponse stopLectureMode(LectureModeControlRequest request);
	public LectureModeControlResponse dismissAllRaisedHands(LectureModeControlRequest request);

	public LectureModeControlResponse dismissRaisedHand(LectureModeParticipantControlRequest request);
	public LectureModeControlResponse setPresenter(LectureModeParticipantControlRequest request);
	public LectureModeControlResponse removePresenter(LectureModeParticipantControlRequest request);

	public LectureModeParticipantsResponse getLectureModeParticipants(LectureModeParticipantsRequest request);

    public int getCountEndpointsWithoutLectureModeSupportInRoom(int roomID);


}
