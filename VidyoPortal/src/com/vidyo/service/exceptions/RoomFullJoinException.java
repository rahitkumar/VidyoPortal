package com.vidyo.service.exceptions;

public class RoomFullJoinException extends JoinConferenceException {

    public RoomFullJoinException(String msg) {
        super(msg);
    }
}
