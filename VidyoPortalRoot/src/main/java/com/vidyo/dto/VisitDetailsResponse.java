package com.vidyo.dto;

public class VisitDetailsResponse {

	private String clinicianIdentifier;
	
	private String patientIdentifier;
	
	private String identifier;
	
	private String isOnline;
	
	private String createdOnDate;
	
	private String status;
	
	private String statusDate;
	
	private String isPatientOnline;
	
	private String isClinicianOnline;
	
	public VisitDetailsResponse(String clinicianIdentifier, String patientIdentifier, String identifier,
			String isOnline, String createdOnDate, String status, String statusDate, String isPatientOnline,
			String isClinicianOnline) {
		
		this.clinicianIdentifier = clinicianIdentifier;
		this.patientIdentifier = patientIdentifier;
		this.identifier = identifier;
		this.isOnline = isOnline;
		this.createdOnDate = createdOnDate;
		this.status = status;
		this.statusDate = statusDate;
		this.isPatientOnline = isPatientOnline;
		this.isClinicianOnline = isClinicianOnline;
	}
	public VisitDetailsResponse() {
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clinicianIdentifier == null) ? 0 : clinicianIdentifier.hashCode());
		result = prime * result + ((createdOnDate == null) ? 0 : createdOnDate.hashCode());
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((isClinicianOnline == null) ? 0 : isClinicianOnline.hashCode());
		result = prime * result + ((isOnline == null) ? 0 : isOnline.hashCode());
		result = prime * result + ((isPatientOnline == null) ? 0 : isPatientOnline.hashCode());
		result = prime * result + ((patientIdentifier == null) ? 0 : patientIdentifier.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((statusDate == null) ? 0 : statusDate.hashCode());
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
		VisitDetailsResponse other = (VisitDetailsResponse) obj;
		if (clinicianIdentifier == null) {
			if (other.clinicianIdentifier != null)
				return false;
		} else if (!clinicianIdentifier.equals(other.clinicianIdentifier))
			return false;
		if (createdOnDate == null) {
			if (other.createdOnDate != null)
				return false;
		} else if (!createdOnDate.equals(other.createdOnDate))
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (isClinicianOnline == null) {
			if (other.isClinicianOnline != null)
				return false;
		} else if (!isClinicianOnline.equals(other.isClinicianOnline))
			return false;
		if (isOnline == null) {
			if (other.isOnline != null)
				return false;
		} else if (!isOnline.equals(other.isOnline))
			return false;
		if (isPatientOnline == null) {
			if (other.isPatientOnline != null)
				return false;
		} else if (!isPatientOnline.equals(other.isPatientOnline))
			return false;
		if (patientIdentifier == null) {
			if (other.patientIdentifier != null)
				return false;
		} else if (!patientIdentifier.equals(other.patientIdentifier))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusDate == null) {
			if (other.statusDate != null)
				return false;
		} else if (!statusDate.equals(other.statusDate))
			return false;
		return true;
	}
	
	
	
}
