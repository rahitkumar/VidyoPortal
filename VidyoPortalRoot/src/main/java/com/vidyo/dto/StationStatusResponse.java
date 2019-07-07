package com.vidyo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationStatusResponse {

	@JsonProperty("identifier")
	private String identifier;

	@JsonProperty("description")
	private String description;

	@JsonProperty("isActive")
	private Boolean isActive;

	@JsonProperty("isPaired")
	private Boolean isPaired;
	
	@JsonProperty("isDeviceOnline")
	private Boolean isDeviceOnline;

	public StationStatusResponse() {}
	
	public StationStatusResponse(String identifier, String description, 
			Boolean isActive, Boolean isPaired, Boolean isDeviceOnline) {
		this.identifier = identifier;
		this.description = description;
		this.isActive = isActive;
		this.isPaired = isPaired;
		this.isDeviceOnline = isDeviceOnline;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getDescription() {
		return description;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public Boolean getIsPaired() {
		return isPaired;
	}

	public Boolean getIsDeviceOnline() {
		return isDeviceOnline;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setIsPaired(Boolean isPaired) {
		this.isPaired = isPaired;
	}

	public void setIsDeviceOnline(Boolean isDeviceOnline) {
		this.isDeviceOnline = isDeviceOnline;
	}
}
