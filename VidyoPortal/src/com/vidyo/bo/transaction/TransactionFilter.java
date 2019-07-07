package com.vidyo.bo.transaction;

public class TransactionFilter {
    String auditperiod = "30";

    String startDate;
	String endDate;

	public String getAuditperiod() {
		return auditperiod;
	}

	public void setAuditperiod(String auditperiod) {
		this.auditperiod = auditperiod;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}