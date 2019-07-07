package com.vidyo.exceptions.tyto;

public class TytoInvalidUserDataException extends IllegalArgumentException  {

    private final String messageCode;
    private final int statusCode;

    public TytoInvalidUserDataException(String messageCode, int statusCode) {
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
