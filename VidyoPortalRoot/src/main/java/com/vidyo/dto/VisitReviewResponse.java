package com.vidyo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitReviewResponse {

	@JsonProperty("reviewerUrl")
	private String reviewerUrl;
	
	public VisitReviewResponse() {
	}

	public String getReviewerUrl() {
		return reviewerUrl;
	}

	public void setReviewerUrl(String reviewerUrl) {
		this.reviewerUrl = reviewerUrl;
	}
}
