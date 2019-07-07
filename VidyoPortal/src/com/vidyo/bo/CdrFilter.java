package com.vidyo.bo;

import java.util.Date;

public class CdrFilter {
	String oneall = "all";
	String tenantName;
	String dateperiod = "all";
	String startdate;
	String enddate;

	public String getDateperiod() {
		return dateperiod;
	}

	public void setDateperiod(String dateperiod) {
		this.dateperiod = dateperiod;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getOneall() {
		return oneall;
	}

	public void setOneall(String oneall) {
		this.oneall = oneall;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
}