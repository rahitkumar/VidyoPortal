package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class AccessRestrictedException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public AccessRestrictedException() {
        super("Access is restricted");
    }

    /**
    * Create a new <code>InvalidArgumentException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public AccessRestrictedException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>InvalidArgumentException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public AccessRestrictedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
