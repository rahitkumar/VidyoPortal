package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class UnmuteAudioAllException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public UnmuteAudioAllException() {
        super(rs.getString("problem.unmute.audio.all.in.conference"));
    }

    /**
    * Create a new <code>DisconnectAllException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public UnmuteAudioAllException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>DisconnectAllException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public UnmuteAudioAllException(String msg, Throwable cause) {
        super(msg, cause);
    }

}