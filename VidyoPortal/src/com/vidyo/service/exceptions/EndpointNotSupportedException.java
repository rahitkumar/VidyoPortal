package com.vidyo.service.exceptions;

public class EndpointNotSupportedException extends Exception {

    public EndpointNotSupportedException() {
        super("Endpoint is not supported");
    }

    public EndpointNotSupportedException(String message) {
        super(message);
    }

    public EndpointNotSupportedException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
