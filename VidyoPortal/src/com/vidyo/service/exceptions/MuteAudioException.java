package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class MuteAudioException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public MuteAudioException() {
        super("Failed to Mute Audio");
    }

    /**
    * Create a new <code>MuteAudioException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public MuteAudioException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>MuteAudioException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public MuteAudioException(String msg, Throwable cause) {
        super(msg, cause);
    }

}