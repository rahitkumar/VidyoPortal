package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class DeleteConferenceException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public DeleteConferenceException() {
        super(rs.getString("problem.deleting.conference"));
    }

    /**
    * Create a new <code>DeleteConferenceException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public DeleteConferenceException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>DeleteConferenceException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public DeleteConferenceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}