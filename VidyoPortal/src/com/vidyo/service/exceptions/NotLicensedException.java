package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class NotLicensedException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public NotLicensedException() {
        super("Operation is not licensed");
    }

    /**
    * Create a new <code>NotLicensedException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public NotLicensedException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>NotLicensedException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public NotLicensedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}