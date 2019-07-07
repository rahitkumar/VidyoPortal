package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class NoVidyoReplayException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public NoVidyoReplayException() {
        super("Failed to Communicate with VidyoReplay");
    }

    /**
    * Create a new <code>NoVidyoManagerException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public NoVidyoReplayException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>NoVidyoReplayException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public NoVidyoReplayException(String msg, Throwable cause) {
        super(msg, cause);
    }

}