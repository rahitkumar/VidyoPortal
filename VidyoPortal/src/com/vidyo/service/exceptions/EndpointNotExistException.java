package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class EndpointNotExistException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public EndpointNotExistException() {
        super(rs.getString("endpoint.not.found"));
    }

    /**
    * Create a new <code>EndpointNotExistException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public EndpointNotExistException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>EndpointNotExistException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public EndpointNotExistException(String msg, Throwable cause) {
        super(msg, cause);
    }

}