package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class SilenceVideoException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public SilenceVideoException() {
        super("Failed to Silence Video");
    }

    /**
    * Create a new <code>SilenceAudioException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public SilenceVideoException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>SilenceAudioException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public SilenceVideoException(String msg, Throwable cause) {
        super(msg, cause);
    }

}