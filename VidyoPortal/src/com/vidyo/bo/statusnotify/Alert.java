package com.vidyo.bo.statusnotify;

import java.io.Serializable;

import com.vidyo.service.configuration.enums.ExternalDataTypeEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Alert implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -250743291051712606L;

	private String GUID;
	private String status;
	private int memberId;

	private String confName;

	private String tenantName;

	private String username;
	
	/**
	 * 
	 */
	private ExternalDataTypeEnum extDataType;
	
	/**
	 * 
	 */
	private String extData;

	/**
	 * @return the confName
	 */
	public String getConfName() {
		return confName;
	}

	/**
	 * @param confName the confName to set
	 */
	public void setConfName(String confName) {
		this.confName = confName;
	}

	public Alert(String GUID, String status, int memberId) {
		this.GUID = GUID;
		this.status = status;
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public String getGUID() {
		return GUID;
	}

	public void setGUID(String GUID) {
		this.GUID = GUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the memberId
	 */
	public int getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the extData
	 */
	public String getExtData() {
		return extData;
	}

	/**
	 * @param extData the extData to set
	 */
	public void setExtData(String extData) {
		this.extData = extData;
	}

	/**
	 * @return the extDataType
	 */
	public ExternalDataTypeEnum getExtDataType() {
		return extDataType;
	}

	/**
	 * @param extDataType the extDataType to set
	 */
	public void setExtDataType(ExternalDataTypeEnum extDataType) {
		this.extDataType = extDataType;
	}
}