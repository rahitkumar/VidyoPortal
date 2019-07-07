package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class FederationNotAllowedException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public FederationNotAllowedException() {
        super("Federation not allowed for this tenant");
    }

    /**
    * Create a new <code>FederationNotAllowedException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public FederationNotAllowedException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>FederationNotAllowedException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public FederationNotAllowedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}