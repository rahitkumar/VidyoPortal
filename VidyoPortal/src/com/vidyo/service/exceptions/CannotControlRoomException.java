package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class CannotControlRoomException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public CannotControlRoomException() {
        super("Cannot control room.");
    }

    /**
    * Create a new <code>InvalidArgumentException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public CannotControlRoomException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>InvalidArgumentException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public CannotControlRoomException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
