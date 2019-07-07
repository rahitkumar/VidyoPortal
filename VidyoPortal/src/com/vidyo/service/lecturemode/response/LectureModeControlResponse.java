package com.vidyo.service.lecturemode.response;

import com.vidyo.bo.Room;
import com.vidyo.framework.service.BaseServiceResponse;
import com.vidyo.service.conference.response.ConferenceControlResponse;

public class LectureModeControlResponse extends ConferenceControlResponse {

	public static final int PARTICIPANT_NOT_IN_CONFERENCE = 70001;
	public static final int PARTICIPANT_IS_NOT_PRESENTING = 7002;
	public static final int LECTURE_MODE_ALREADY_STARTED = 7003;
	public static final int LECTURE_MODE_ALREADY_STOPPED = 7004;
	public static final int LECTURE_MODE_NOT_STARTED = 7005;
	public static final int LECTURE_MODE_FEATURE_NOT_ALLOWED = 7006;

	private Room room;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
