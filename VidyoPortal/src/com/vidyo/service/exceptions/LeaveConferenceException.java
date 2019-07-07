package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class LeaveConferenceException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public LeaveConferenceException() {
        super("Failed to Leave Conference");
    }

    /**
    * Create a new <code>LeaveConferenceException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public LeaveConferenceException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>LeaveConferenceException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public LeaveConferenceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}