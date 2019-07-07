package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class WrongPinException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public WrongPinException() {
        super("Wrong PIN");
    }

    /**
    * Create a new <code>WrongPinException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public WrongPinException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>WrongPinException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public WrongPinException(String msg, Throwable cause) {
        super(msg, cause);
    }

}