package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class JoinConferenceException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public JoinConferenceException() {
        super("Failed to Join Conference");
    }

    /**
    * Create a new <code>JoinConferenceException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public JoinConferenceException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>JoinConferenceException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public JoinConferenceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}