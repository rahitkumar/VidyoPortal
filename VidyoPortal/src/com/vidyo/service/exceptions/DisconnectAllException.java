package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class DisconnectAllException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public DisconnectAllException() {
        super(rs.getString("problem.disconnecting.all.from.conference"));
    }

    /**
    * Create a new <code>DisconnectAllException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public DisconnectAllException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>DisconnectAllException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public DisconnectAllException(String msg, Throwable cause) {
        super(msg, cause);
    }

}