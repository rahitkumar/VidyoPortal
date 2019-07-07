package com.vidyo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitReviewTytoRequest {

	@JsonProperty("clinicianIdentifier")
	private String clinicianIdentifier;
	
	@JsonProperty("clinicianRemoteAddress")
	private String clinicianRemoteAddress;

	public VisitReviewTytoRequest() {
	}

	public String getClinicianIdentifier() {
		return clinicianIdentifier;
	}

	public String getClinicianRemoteAddress() {
		return clinicianRemoteAddress;
	}

	public void setClinicianIdentifier(String clinicianIdentifier) {
		this.clinicianIdentifier = clinicianIdentifier;
	}

	public void setClinicianRemoteAddress(String clinicianRemoteAddress) {
		this.clinicianRemoteAddress = clinicianRemoteAddress;
	}

	@Override
	public String toString() {
		return "VisitReviewTytoRequest{" +
				"clinicianIdentifier='" + clinicianIdentifier + '\'' +
				", clinicianRemoteAddress='" + clinicianRemoteAddress + '\'' +
				'}';
	}
}
