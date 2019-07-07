package com.vidyo.exceptions.tyto;

public class TytoCommunicationException extends IllegalArgumentException {

    private final int statusCode;
    private final String extData;

    public TytoCommunicationException(String message, int httpStatus, String extData) {
        super(message);
        this.statusCode = httpStatus;
        this.extData = extData;
    }

    public int getErrorCode() {
        return statusCode;
    }

    public String getExtData() {
        return extData;
    }
}
