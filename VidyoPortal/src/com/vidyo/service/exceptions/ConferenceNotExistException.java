package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class ConferenceNotExistException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public ConferenceNotExistException() {
        super(rs.getString("conference.does.not.exist"));
    }

    /**
    * Create a new <code>ConferenceNotExistException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public ConferenceNotExistException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>ConferenceNotExistException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public ConferenceNotExistException(String msg, Throwable cause) {
        super(msg, cause);
    }

}