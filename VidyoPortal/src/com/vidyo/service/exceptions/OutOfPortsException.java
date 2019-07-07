package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class OutOfPortsException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public OutOfPortsException() {
        super("All Ports in use, please try later");
    }

    /**
    * Create a new <code>OutOfPortsException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public OutOfPortsException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>OutOfPortsException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public OutOfPortsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
