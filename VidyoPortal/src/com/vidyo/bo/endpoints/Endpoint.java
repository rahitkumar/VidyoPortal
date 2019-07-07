/**
 * 
 */
package com.vidyo.bo.endpoints;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Ganesh
 * 
 */
public class Endpoint {

	/**
	 * 
	 */
	private int endpointID;

	/**
	 * 
	 */
	private int memberID;

	/**
	 * 
	 */
	private int status;

	/**
	 * 
	 */
	private String ipAddress;

	/**
	 * 
	 */
	private String memberName;

	/**
	 * 
	 */
	private String memberType;

	/**
	 * 
	 */
	private String endpointType;

	/**
	 * 
	 */
	private String statusVal;

	/**
	 * 
	 */
	private String guid;

	/**
	 * 
	 */
	private int consumesLine;
	
	/**
	 * 
	 */
	private Date updateTime;

	/**
	 * 
	 */
	private String endpointUploadType = null;
	private int authorized = 0;

	private long sequenceNum;
	
	/**
	 * Data coming in from external integration
	 */
	private String extData;
	
	/**
	 * External Integration Type
	 * 1 -> EPIC
	 */
	private Integer extDataType;
	

	public String getExtData() {
		return extData;
	}

	public void setExtData(String extData) {
		this.extData = extData;
	}

	/**
	 * @return the extDataType
	 */
	public Integer getExtDataType() {
		return extDataType;
	}

	/**
	 * @param extDataType the extDataType to set
	 */
	public void setExtDataType(Integer extDataType) {
		this.extDataType = extDataType;
	}

	/**
	 * @return the statusVal
	 */
	public String getStatusVal() {
		return statusVal;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @param statusVal
	 *            the statusVal to set
	 */
	public void setStatusVal(String statusVal) {
		this.statusVal = statusVal;
	}

	/**
	 * @return the endpointType
	 */
	public String getEndpointType() {
		return endpointType;
	}

	/**
	 * @param endpointType
	 *            the endpointType to set
	 */
	public void setEndpointType(String endpointType) {
		this.endpointType = endpointType;
	}

	/**
	 * @return the endpointID
	 */
	public int getEndpointID() {
		return endpointID;
	}

	/**
	 * @param endpointID
	 *            the endpointID to set
	 */
	public void setEndpointID(int endpointID) {
		this.endpointID = endpointID;
	}

	/**
	 * @return the memberID
	 */
	public int getMemberID() {
		return memberID;
	}

	/**
	 * @param memberID
	 *            the memberID to set
	 */
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName
	 *            the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType
	 *            the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid
	 *            the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the consumesLine
	 */
	public int getConsumesLine() {
		return consumesLine;
	}

	/**
	 * @param consumesLine
	 *            the consumesLine to set
	 */
	public void setConsumesLine(int consumesLine) {
		this.consumesLine = consumesLine;
	}

	public long getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(long sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
	public String getEndpointUploadType() {
		return endpointUploadType;
	}

	public void setEndpointUploadType(String endpointUploadType) {
		this.endpointUploadType = endpointUploadType;
	}


	public int getAuthorized() {
		return authorized;
	}

	public void setAuthorized(int authorized) {
		this.authorized = authorized;
	}
}
