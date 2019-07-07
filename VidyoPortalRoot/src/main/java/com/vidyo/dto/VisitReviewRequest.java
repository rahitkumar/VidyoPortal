package com.vidyo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitReviewRequest {

	@JsonProperty("reviewerIdentifier")
	private String reviewerId;
	
	public VisitReviewRequest() {
	}

	public VisitReviewRequest(String reviewerId) {
		this.reviewerId = reviewerId;
	}

	public String getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}
	
	@Override
	public String toString() {
		return "VisitReviewRequest{" +
				"reviewerIdentifier='" + reviewerId + '\'' +
				'}';
	}
}
