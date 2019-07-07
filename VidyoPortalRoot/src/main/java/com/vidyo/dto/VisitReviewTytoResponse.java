package com.vidyo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitReviewTytoResponse {

	@JsonProperty("clinicianUrl")
	private String clinicianUrl;
	
	public VisitReviewTytoResponse() {
	}

	public String getClinicianUrl() {
		return clinicianUrl;
	}

	public void setClinicianUrl(String clinicianUrl) {
		this.clinicianUrl = clinicianUrl;
	}
}
