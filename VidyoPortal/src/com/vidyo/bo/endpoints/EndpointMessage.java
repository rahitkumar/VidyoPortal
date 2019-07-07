package com.vidyo.bo.endpoints;

import javax.activation.DataHandler;

public class EndpointMessage {

    private String serviceEndpointGUID;
    private DataHandler joinToLegacyContent;

    public String getServiceEndpointGUID() {
        return serviceEndpointGUID;
    }

    public void setServiceEndpointGUID(String serviceEndpointGUID) {
        this.serviceEndpointGUID = serviceEndpointGUID;
    }

    public DataHandler getJoinToLegacyContent() {
        return joinToLegacyContent;
    }

    public void setJoinToLegacyContent(DataHandler joinToLegacyContent) {
        this.joinToLegacyContent = joinToLegacyContent;
    }

}
