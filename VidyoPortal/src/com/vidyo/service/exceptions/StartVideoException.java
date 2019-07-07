package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class StartVideoException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public StartVideoException() {
        super("Failed to Start Video");
    }

    /**
    * Create a new <code>StartVideoException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public StartVideoException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>StartVideoException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public StartVideoException(String msg, Throwable cause) {
        super(msg, cause);
    }

}