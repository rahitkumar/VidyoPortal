package com.vidyo.service.conference.control;

import com.vidyo.bo.User;
import com.vidyo.service.conference.response.ConferenceControlResponse;

public interface ConferenceControlService {
    public ConferenceControlResponse disconnectConferenceAll(int loggedMemberID, int disconnectRoomID,
            String moderatorPIN);

    public ConferenceControlResponse muteAudioServerAll(int loggedMemberID, int muteAudioRoomID, String moderatorPIN);

    public ConferenceControlResponse unmuteAudioServerAll(int loggedMemberID, int unmuteAudioRoomID,
            String moderatorPIN);

    public ConferenceControlResponse muteAudioClientAll(int loggedMemberID, int muteAudioRoomID, String moderatorPIN);

    public ConferenceControlResponse muteVideoServerAll(int loggedMemberID, int muteVideoRoomID, String moderatorPIN);

    public ConferenceControlResponse unmuteVideoServerAll(int loggedMemberID, int unmuteVideoRoomID,
            String moderatorPIN);

    public ConferenceControlResponse muteVideoClientAll(int loggedMemberID, int muteVideoRoomID, String moderatorPIN);

    /**
     * Silences/UnSilences the speaker of specific participant or all participants in conference. If the
     * participantId is not sent or zero, silences all participants
     *
     * @param roomID
     * @param participantId
     * @param moderatorPIN
     * @param silence
     * @return
     */
    public ConferenceControlResponse silenceSpeakerServer(User controllingUser, int roomID, int participantId, String moderatorPIN, int silence);
}
