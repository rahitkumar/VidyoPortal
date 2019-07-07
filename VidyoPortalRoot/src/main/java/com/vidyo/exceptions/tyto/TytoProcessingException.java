package com.vidyo.exceptions.tyto;

public class TytoProcessingException extends IllegalArgumentException {

    private final String messageCode;
    private final int statusCode;

    public TytoProcessingException(String messageCode, int statusCode) {
        this.messageCode = messageCode;
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return statusCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
}