package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class CancelInvitationException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public CancelInvitationException() {
        super(rs.getString("failed.to.cancel.invitation"));
    }

    /**
    * Create a new <code>CancelInvitationException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public CancelInvitationException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>CancelInvitationException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public CancelInvitationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}