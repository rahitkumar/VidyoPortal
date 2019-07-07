package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class MuteAudioAllException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public MuteAudioAllException() {
        super(rs.getString("problem.mute.audio.all.in.conference"));
    }

    /**
    * Create a new <code>DisconnectAllException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public MuteAudioAllException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>DisconnectAllException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public MuteAudioAllException(String msg, Throwable cause) {
        super(msg, cause);
    }

}