package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class InviteConferenceException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public InviteConferenceException() {
        super(rs.getString("failed.to.invite.to.conference"));
    }

    /**
    * Create a new <code>InviteConferenceException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public InviteConferenceException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>InviteConferenceException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public InviteConferenceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}