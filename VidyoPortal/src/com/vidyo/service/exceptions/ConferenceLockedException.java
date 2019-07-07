package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class ConferenceLockedException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public ConferenceLockedException() {
        super("Room is locked");
    }

    /**
    * Create a new <code>ConferenceLockedException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public ConferenceLockedException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>ConferenceLockedException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public ConferenceLockedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}