package com.vidyo.db.endpoints;


import java.io.Serializable;

public class EndpointFeatures implements Serializable {

    private static final long serialVersionUID = 9041199680611724658L;

    private int lectureModeSupport = 0; // tinyint(1) in database

    public int getLectureModeSupport() {
        return this.lectureModeSupport;
    }

    public void setLectureModeSupport(int lectureModeSupport) {
        this.lectureModeSupport = lectureModeSupport;
    }

    // convenience methods
    public boolean isLectureModeSupported() {
        return lectureModeSupport != 0;
    }

    public void setLectureModeSupported(boolean lectureModeSupport) {
        if (lectureModeSupport) {
            this.lectureModeSupport = 1;
        } else {
            this.lectureModeSupport = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EndpointFeatures that = (EndpointFeatures) o;

        if (lectureModeSupport != that.lectureModeSupport) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return lectureModeSupport;
    }


}
