package com.vidyo.rest.gateway;

import java.io.Serializable;

public class EndpointFeature implements Serializable  {

    private static final long serialVersionUID = 7616113642753152427L;

    private String feature;
    private Boolean enable = Boolean.FALSE;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;

    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EndpointFeature that = (EndpointFeature) o;

        if (enable != null ? !enable.equals(that.enable) : that.enable != null) return false;
        if (feature != null ? !feature.equals(that.feature) : that.feature != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = feature != null ? feature.hashCode() : 0;
        result = 31 * result + (enable != null ? enable.hashCode() : 0);
        return result;
    }
}
