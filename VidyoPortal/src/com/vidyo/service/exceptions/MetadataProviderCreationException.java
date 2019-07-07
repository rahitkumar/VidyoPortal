package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

public class MetadataProviderCreationException extends Exception {
    static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public MetadataProviderCreationException() {
        super("Error on MetadataProvider creation");
    }

    /**
    * Create a new <code>MetadataProviderCreationException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public MetadataProviderCreationException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>MetadataProviderCreationException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public MetadataProviderCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}