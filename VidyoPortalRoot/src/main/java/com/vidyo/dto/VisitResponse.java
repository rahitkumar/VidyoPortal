package com.vidyo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class VisitResponse {
	
	private final String tytoIdentifier;

	private final String patientUrl;

	private final String clinicianUrl;

	private final boolean isDeviceConnected;

	@JsonCreator
	public VisitResponse(@JsonProperty("tytoIdentifier") String tytoIdentifier,
						 @JsonProperty("patientUrl") String patientUrl,
						 @JsonProperty("clinicianUrl") String clinicianUrl,
						 @JsonProperty("isDeviceConnected") boolean isDeviceConnected) {
		this.tytoIdentifier = tytoIdentifier;
		this.patientUrl = patientUrl;
		this.clinicianUrl = clinicianUrl;
		this.isDeviceConnected = isDeviceConnected;
	}

	public String getTytoIdentifier() {
		return tytoIdentifier;
	}

	public String getPatientUrl() {
		return patientUrl;
	}

	public boolean getIsDeviceConnected() {
		return isDeviceConnected;
	}

	public String getClinicianUrl() {
		return clinicianUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VisitResponse that = (VisitResponse) o;
		return Objects.equals(tytoIdentifier, that.tytoIdentifier) &&
				Objects.equals(patientUrl, that.patientUrl) &&
				Objects.equals(isDeviceConnected, that.isDeviceConnected) &&
				Objects.equals(clinicianUrl, that.clinicianUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(tytoIdentifier, patientUrl, isDeviceConnected, clinicianUrl);
	}
}
