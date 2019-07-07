package com.vidyo.rest.status;
public class StatusServiceException extends RuntimeException {

    /**
	 * this is used for throwing errors with status ,so that super class can catch it and do accordingly.
	 */
	private static final long serialVersionUID = 1L;
	private String statusCode;
    private String statusMessage;

    public StatusServiceException(String statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.statusMessage = message;
    }

    public StatusServiceException(String statusCode, Exception wrappedException) {
        super(wrappedException);
        this.statusCode = statusCode;
        this.statusMessage = wrappedException.getMessage();
    }

    public StatusServiceException(String statusCode, String message, Exception wrappedException) {
        super(message, wrappedException);
        this.statusCode = statusCode;
        this.statusMessage = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

}
