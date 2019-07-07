package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class MakeCallException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public MakeCallException() {
        super("Failed to place Direct Call");
    }

    /**
    * Create a new <code>MakeCallException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public MakeCallException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>MakeCallException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public MakeCallException(String msg, Throwable cause) {
        super(msg, cause);
    }

}