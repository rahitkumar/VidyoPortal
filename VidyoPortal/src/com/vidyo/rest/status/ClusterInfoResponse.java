package com.vidyo.rest.status;

import com.vidyo.rest.base.RestResponse;
import java.io.Serializable;

public class ClusterInfoResponse
extends RestResponse
implements Serializable {
    private static final long serialVersionUID = 7748324534911877140L;
    private String peerStatus;
    private int lastPingOK;
    private int peerLastHeartbeat;
    private int peerHeartbeatCounter;
    private Long lastDBSync;
    private String myStatus;
    private boolean preferred;

    public String getPeerStatus() {
        return this.peerStatus;
    }

    public void setPeerStatus(String peerStatus) {
        this.peerStatus = peerStatus;
    }

    public int getLastPingOK() {
        return this.lastPingOK;
    }

    public void setLastPingOK(int lastPingOK) {
        this.lastPingOK = lastPingOK;
    }

    public int getPeerLastHeartbeat() {
        return this.peerLastHeartbeat;
    }

    public void setPeerLastHeartbeat(int peerLastHeartbeat) {
        this.peerLastHeartbeat = peerLastHeartbeat;
    }

    public int getPeerHeartbeatCounter() {
        return this.peerHeartbeatCounter;
    }

    public void setPeerHeartbeatCounter(int peerHeartbeatCounter) {
        this.peerHeartbeatCounter = peerHeartbeatCounter;
    }

    public Long getLastDBSync() {
        return this.lastDBSync;
    }

    public void setLastDBSync(Long lastDBSync) {
        this.lastDBSync = lastDBSync;
    }

    public String getMyStatus() {
        return this.myStatus;
    }

    public void setMyStatus(String myStatus) {
        this.myStatus = myStatus;
    }

    public boolean isPreferred() {
        return this.preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }
}