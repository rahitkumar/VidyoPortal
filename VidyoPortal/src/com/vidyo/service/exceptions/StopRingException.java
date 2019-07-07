package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class StopRingException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public StopRingException() {
        super("Failed to stop ringing Invitee");
    }

    /**
    * Create a new <code>StopRingException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public StopRingException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>StopRingException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public StopRingException(String msg, Throwable cause) {
        super(msg, cause);
    }

}