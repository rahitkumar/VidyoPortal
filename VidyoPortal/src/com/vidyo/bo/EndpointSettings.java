package com.vidyo.bo;


public class EndpointSettings {

    int dscpVideo = 0;
    int dscpAudio = 0;
    int dscpContent = 0;
    int dscpSignaling = 0;
    int dscpOam = 0;
    int minMediaPort = 50000;
    int maxMediaPort = 50100;
    boolean alwaysUseVidyoProxy = false;

    public int getDscpVideo() {
        return dscpVideo;
    }

    public void setDscpVideo(int dscpVideo) {
        this.dscpVideo = dscpVideo;
    }

    public int getDscpAudio() {
        return dscpAudio;
    }

    public void setDscpAudio(int dscpAudio) {
        this.dscpAudio = dscpAudio;
    }

    public int getDscpContent() {
        return dscpContent;
    }

    public void setDscpContent(int dscpContent) {
        this.dscpContent = dscpContent;
    }

    public int getDscpSignaling() {
        return dscpSignaling;
    }

    public void setDscpSignaling(int dscpSignaling) {
        this.dscpSignaling = dscpSignaling;
    }

    public int getMinMediaPort() {
        return minMediaPort;
    }

    public void setMinMediaPort(int minMediaPort) {
        this.minMediaPort = minMediaPort;
    }

    public int getMaxMediaPort() {
        return maxMediaPort;
    }

    public void setMaxMediaPort(int maxMediaPort) {
        this.maxMediaPort = maxMediaPort;
    }

    public boolean isAlwaysUseVidyoProxy() {
        return alwaysUseVidyoProxy;
    }

    public void setAlwaysUseVidyoProxy(boolean alwaysUseVidyoProxy) {
        this.alwaysUseVidyoProxy = alwaysUseVidyoProxy;
    }

    public int getDscpOam() {
        return dscpOam;
    }

    public void setDscpOam(int dscpOam) {
        this.dscpOam = dscpOam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EndpointSettings that = (EndpointSettings) o;

        if (dscpVideo != that.dscpVideo) return false;
        if (dscpAudio != that.dscpAudio) return false;
        if (dscpContent != that.dscpContent) return false;
        if (dscpSignaling != that.dscpSignaling) return false;
        if (dscpOam != that.dscpOam) return false;
        if (minMediaPort != that.minMediaPort) return false;
        if (maxMediaPort != that.maxMediaPort) return false;
        return alwaysUseVidyoProxy == that.alwaysUseVidyoProxy;

    }

    @Override
    public int hashCode() {
        int result = dscpVideo;
        result = 31 * result + dscpAudio;
        result = 31 * result + dscpContent;
        result = 31 * result + dscpSignaling;
        result = 31 * result + dscpOam;
        result = 31 * result + minMediaPort;
        result = 31 * result + maxMediaPort;
        result = 31 * result + (alwaysUseVidyoProxy ? 1 : 0);
        return result;
    }
}
