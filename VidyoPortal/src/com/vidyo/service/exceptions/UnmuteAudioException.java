package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class UnmuteAudioException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public UnmuteAudioException() {
        super("Failed to Unmute Audio");
    }

    /**
    * Create a new <code>UnmuteAudioException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public UnmuteAudioException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>UnmuteAudioException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public UnmuteAudioException(String msg, Throwable cause) {
        super(msg, cause);
    }

}