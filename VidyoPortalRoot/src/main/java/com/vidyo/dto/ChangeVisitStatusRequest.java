package com.vidyo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vidyo.VisitStatus;
import javax.validation.constraints.NotNull;


public class ChangeVisitStatusRequest {

    @NotNull
    private final VisitStatus status;

    @JsonCreator
    public ChangeVisitStatusRequest(@JsonProperty("status") VisitStatus status) {
        this.status = status;
    }

    public VisitStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ChangeVisitStatusRequest{" +
                "status='" + status + '\'' +
                '}';
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChangeVisitStatusRequest other = (ChangeVisitStatusRequest) obj;
		if (status != other.status)
			return false;
		return true;
	}
    
    
}