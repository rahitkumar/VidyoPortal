package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class TestCallException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public TestCallException() {
        super("Test call cannot be established");
    }

    /**
    * Create a new <code>TestCallException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public TestCallException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>TestCallException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public TestCallException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
