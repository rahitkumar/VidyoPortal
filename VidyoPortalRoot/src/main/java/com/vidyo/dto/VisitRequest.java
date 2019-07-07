package com.vidyo.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class VisitRequest {
	
	@NotBlank
	@Length(max = 100)
	private final String identifier;
	
	@NotBlank
	@Length(max = 64)
	private final String clinicianIdentifier;
	
	@NotBlank
	@Length(max = 64)
	private  final String stationIdentifier;
	
	@JsonCreator
	public VisitRequest(@JsonProperty("identifier") String identifier, 
			@JsonProperty("clinicianIdentifier") String clinicianIdentifier,
			@JsonProperty("stationIdentifier") String stationIdentifier) {
		super();
		this.identifier = identifier;
		this.clinicianIdentifier = clinicianIdentifier;
		this.stationIdentifier = stationIdentifier;
	}

	public String getIdentifier() {
		return identifier;
	}
	
	public String getStationIdentifier() {
		return stationIdentifier;
	}

	public String getClinicianIdentifier() {
		return clinicianIdentifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clinicianIdentifier == null) ? 0 : clinicianIdentifier.hashCode());
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((stationIdentifier == null) ? 0 : stationIdentifier.hashCode());
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
		VisitRequest other = (VisitRequest) obj;
		if (clinicianIdentifier == null) {
			if (other.clinicianIdentifier != null)
				return false;
		} else if (!clinicianIdentifier.equals(other.clinicianIdentifier))
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (stationIdentifier == null) {
			if (other.stationIdentifier != null)
				return false;
		} else if (!stationIdentifier.equals(other.stationIdentifier))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VisitRequest{" +
				"identifier='" + identifier + '\'' +
				", clinicianIdentifier='" + clinicianIdentifier + '\'' +
				", stationIdentifier='" + stationIdentifier + '\'' +
				'}';
	}
}
