package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

/**
 * Created by pkumar on 23/06/17.
 */
public class ResourceNotFoundException extends RuntimeException {

    static ResourceBundle rs;

    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public ResourceNotFoundException() {
        super("Access is restricted");
    }

    /**
     * Create a new <code>ResourceNotFoundException</code>
     * with the specified detail message and no root cause.
     *
     * @param msg the detail message
     */
    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Create a new <code>ResourceNotFoundException</code>
     * with the specified detail message and the given root cause.
     *
     * @param msg   the detail message
     * @param cause the root cause
     */
    public ResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
