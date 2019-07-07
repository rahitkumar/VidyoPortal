package com.vidyo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetVisitResponse {

	@JsonProperty("tytoIdentifier")
	private String tytoIdentifier;

	@JsonProperty("patientIdentifier")
	private String patientIdentifier;

	@JsonProperty("accountIdentifier")
	private String accountIdentifier;

	@JsonProperty("clinicianIdentifier")
	private String clinicianIdentifier;

	@JsonProperty("isOnline")
	private Boolean isOnline;

	@JsonProperty("identifier")
	private String identifier;

	@JsonProperty("createdOnDate")
	private Date createdOnDate;

	@JsonProperty("status")
	private String status;

	@JsonProperty("statusDate")
	private Date statusDate;

	@JsonProperty("isPatientOnline")
	private Boolean isPatientOnline;

	@JsonProperty("isClinicianOnline")
	private Boolean isClinicianOnline;

	public GetVisitResponse() {
	}

	public GetVisitResponse(GetVisitResponse other, String clinicianIdentifier) {
		this.tytoIdentifier = other.getTytoIdentifier();
		this.patientIdentifier = other.getPatientIdentifier();
		this.accountIdentifier = other.getAccountIdentifier();
		this.clinicianIdentifier = clinicianIdentifier;
		this.isOnline = other.isOnline;
		this.identifier = other.getIdentifier();
		this.createdOnDate = other.createdOnDate;
		this.status = other.getStatus();
		this.statusDate = other.getStatusDate();
		this.isPatientOnline = other.getIsPatientOnline();
		this.isClinicianOnline = other.isClinicianOnline;
	}

	public String getTytoIdentifier() {
		return tytoIdentifier;
	}

	public String getPatientIdentifier() {
		return patientIdentifier;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public String getClinicianIdentifier() {
		return clinicianIdentifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Date getCreatedOnDate() {
		return createdOnDate;
	}

	public String getStatus() {
		return status;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setTytoIdentifier(String tytoIdentifier) {
		this.tytoIdentifier = tytoIdentifier;
	}

	public void setPatientIdentifier(String patientIdentifier) {
		this.patientIdentifier = patientIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	public void setClinicianIdentifier(String clinicianIdentifier) {
		this.clinicianIdentifier = clinicianIdentifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setCreatedOnDate(Date createdOnDate) {
		this.createdOnDate = createdOnDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public Boolean getIsPatientOnline() {
		return isPatientOnline;
	}

	public Boolean getIsClinicianOnline() {
		return isClinicianOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public void setIsPatientOnline(Boolean isPatientOnline) {
		this.isPatientOnline = isPatientOnline;
	}

	public void setIsClinicianOnline(Boolean isClinicianOnline) {
		this.isClinicianOnline = isClinicianOnline;
	}
}
