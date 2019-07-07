package com.vidyo.service.exceptions;

import java.util.ResourceBundle;



public class DataValidationException extends RuntimeException{
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public DataValidationException() {
        super("Request validation exception");
    }

    /**
     * Create a new <code>DataValidationException</code>
     * with the specified detail message and no root cause.
     * @param msg the detail message
     */
    public DataValidationException(String msg) {
        super(msg);
    }

    /**
     * Create a new <code>DataValidationException</code>
     * with the specified detail message and the given root cause.
     * @param msg the detail message
     * @param cause the root cause
     */
    public DataValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
