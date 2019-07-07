package com.vidyo.rest.gateway;

import com.vidyo.rest.base.RestResponse;

import java.io.Serializable;

public class GetVMConnectInfoResponse extends RestResponse implements Serializable {

    private static final long serialVersionUID = 4434047928225383350L;

    private String vmConnect;

    public String getVmConnect() {
        return vmConnect;
    }

    public void setVmConnect(String vmConnect) {
        this.vmConnect = vmConnect;
    }

}
