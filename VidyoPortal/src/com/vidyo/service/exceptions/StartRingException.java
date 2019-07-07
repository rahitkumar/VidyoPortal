package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class StartRingException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public StartRingException() {
        super("Failed to contact Invitee");
    }

    /**
    * Create a new <code>StartRingException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public StartRingException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>StartRingException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public StartRingException(String msg, Throwable cause) {
        super(msg, cause);
    }

}