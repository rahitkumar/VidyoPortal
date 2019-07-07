package com.vidyo.service.exceptions;

import java.util.ResourceBundle;


public class ScheduledRoomCreationException extends Exception  {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public ScheduledRoomCreationException() {
        super("Scheduled Room could not be created for Test Call.");
    }

    /**
     * Create a new <code>ScheduledRoomCreationException</code>
     * with the specified detail message and no root cause.
     * @param msg the detail message
     */
    public ScheduledRoomCreationException(String msg) {
        super(msg);
    }

    /**
     * Create a new <code>ScheduledRoomCreationException</code>
     * with the specified detail message and the given root cause.
     * @param msg the detail message
     * @param cause the root cause
     */
    public ScheduledRoomCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
