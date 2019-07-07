package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class NoVidyoManagerException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public NoVidyoManagerException() {
        super("Failed to Communicate with VidyoManager");
    }

    /**
    * Create a new <code>NoVidyoManagerException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public NoVidyoManagerException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>NoVidyoManagerException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public NoVidyoManagerException(String msg, Throwable cause) {
        super(msg, cause);
    }

}