package com.vidyo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

public class StationSaveRequest {

	@NotBlank
	@JsonProperty("identifier")
	private String identifier;

	@JsonProperty("description")
	private String description;

	public StationSaveRequest() {}
	
	public StationSaveRequest(String identifier, String description) {
		this.identifier = identifier;
		this.description = description;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "StationSaveRequest{" +
				"identifier='" + identifier + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
