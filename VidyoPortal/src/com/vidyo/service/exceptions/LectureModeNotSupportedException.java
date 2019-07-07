package com.vidyo.service.exceptions;

public class LectureModeNotSupportedException extends EndpointNotSupportedException {

    public LectureModeNotSupportedException() {
        super("Endpoint does not support lecture mode.");
    }
}
