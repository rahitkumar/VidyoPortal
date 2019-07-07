package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class NoVidyoFederationException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public NoVidyoFederationException() {
        super("Failed to Communicate with remote host");
    }

    /**
    * Create a new <code>NoVidyoFederationException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public NoVidyoFederationException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>NoVidyoFederationException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public NoVidyoFederationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}