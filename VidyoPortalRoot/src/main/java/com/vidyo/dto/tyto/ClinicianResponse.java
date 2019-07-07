package com.vidyo.dto.tyto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClinicianResponse {

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("dateOfBirth")
	private String dateOfBirth;
	
	@JsonProperty("sex")
	private String sex;
	
	public ClinicianResponse() {}
	
	public ClinicianResponse(String firstName, String lastName, 
			String dateOfBirth, String email, String sex) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getSex() {
		return sex;
	}
}
