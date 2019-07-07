package com.vidyo.bo;

import java.io.Serializable;

public class Federation implements Serializable {

    String requestID;
    String fromUser;
    String toUser;
    int externalLinkID;

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public int getExternalLinkID() {
        return externalLinkID;
    }

    public void setExternalLinkID(int externalLinkID) {
        this.externalLinkID = externalLinkID;
    }
}
