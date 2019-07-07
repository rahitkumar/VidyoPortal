/**
 *
 */
package com.vidyo.service.conference.response;

import java.util.ArrayList;
import java.util.List;

import com.vidyo.framework.service.BaseServiceResponse;


public class ConferenceControlResponse extends BaseServiceResponse {

    public class NotInterruptibleError {
        private String messageID;
        private String message;

        public NotInterruptibleError(String messageID, String message) {
            this.messageID = messageID;
            this.message = message;
        }

        public String getMessageID() {
            return messageID;
        }
        public String getMessage() {
            return message;
        }
    }

    private List<NotInterruptibleError> niErrs = new ArrayList<NotInterruptibleError>();
    /**
     *
     */
    public static final int OK_WITH_ERRORS = 6101;
    /**
     *
     */
    public static final int INVALID_ROOM = 6102;

    /**
     *
     */
    public static final int DISCONNECT_ALL_FAILED = 6103;

    /**
     *
     */
    public static final int CANNOT_CONTROL_ROOM = 6104;

    /**
     *
     */
    public static final int MUTE_AUDIO_SERVER_ALL_FAILED = 6105;

    /**
     *
     */
    public static final int UNMUTE_AUDIO_SERVER_ALL_FAILED = 6106;

    /**
     *
     */
    public static final int MUTE_AUDIO_CLIENT_ALL_FAILED = 6107;

    /**
     *
     */
    public static final int MUTE_VIDEO_SERVER_ALL_FAILED = 6108;

    /**
     *
     */
    public static final int UNMUTE_VIDEO_SERVER_ALL_FAILED = 6109;

    /**
     *
     */
    public static final int MUTE_VIDEO_CLIENT_ALL_FAILED = 6110;

    /**
     *
     */
    public static final int CANNOT_CONTROL_ENDPOINT = 6111;

    /**
     *
     */
    public static final int ROOM_STATE_NO_CHANGE = 6112;

    public void addNotInterruptibleError(String messageID, String message) {
        NotInterruptibleError niErr = new NotInterruptibleError(messageID, message);
        niErrs.add(niErr);
    }

    public List<NotInterruptibleError> retrieveNotInterruptibleError() {
        return niErrs;
    }


}
