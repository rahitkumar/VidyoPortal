package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class ResourceNotAvailableException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public ResourceNotAvailableException() {
        super("Resources not available");
    }

    /**
    * Create a new <code>ResourceNotAvailableException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public ResourceNotAvailableException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>ResourceNotAvailableException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public ResourceNotAvailableException(String msg, Throwable cause) {
        super(msg, cause);
    }

}