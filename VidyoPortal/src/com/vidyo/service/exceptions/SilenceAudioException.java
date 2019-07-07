package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class SilenceAudioException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public SilenceAudioException() {
        super("Failed to Silence Audio");
    }

    /**
    * Create a new <code>SilenceAudioException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public SilenceAudioException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>SilenceAudioException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public SilenceAudioException(String msg, Throwable cause) {
        super(msg, cause);
    }

}