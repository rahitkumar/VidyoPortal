package com.vidyo.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CDRinfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String ConferenceName;
    String CallerName;
    String TenantName;
    String ConferenceType;
    String EndpointType;
    String Caller;
    String CallState;

    @Override
    public String toString() {
        //return this.ConferenceName + " / " + this.CallerName + " / " + this.Caller;
    	return new ReflectionToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).toString();
    }

    public String getConferenceName() {
        return ConferenceName;
    }

    public void setConferenceName(String conferenceName) {
        ConferenceName = conferenceName;
    }

    public String getCallerName() {
        return CallerName;
    }

    public void setCallerName(String callerName) {
        CallerName = callerName;
    }

    public String getTenantName() {
        return TenantName;
    }

    public void setTenantName(String tenantName) {
        TenantName = tenantName;
    }

    public String getConferenceType() {
        return ConferenceType;
    }

    public void setConferenceType(String conferenceType) {
        ConferenceType = conferenceType;
    }

    public String getEndpointType() {
        return EndpointType;
    }

    public void setEndpointType(String endpointType) {
        EndpointType = endpointType;
    }

    public String getCaller() {
        return Caller;
    }

    public void setCaller(String caller) {
        Caller = caller;
    }

    public String getCallState() {
        return CallState;
    }

    public void setCallState(String callState) {
        CallState = callState;
    }
}