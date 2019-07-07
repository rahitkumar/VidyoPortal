package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class StopVideoException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public StopVideoException() {
        super("Failed to Stop Video");
    }

    /**
    * Create a new <code>StopVideoException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public StopVideoException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>StopVideoException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public StopVideoException(String msg, Throwable cause) {
        super(msg, cause);
    }

}