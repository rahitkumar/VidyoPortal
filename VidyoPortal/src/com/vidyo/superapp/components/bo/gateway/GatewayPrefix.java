package com.vidyo.superapp.components.bo.gateway;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vidyo.superapp.components.bo.VidyoGateway;

/**
 * Entity implementation class for Entity: GatewayPrefix
 *
 */
@Entity
@Table(name = "GatewayPrefixes")
public class GatewayPrefix implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int prefixID;

	private int tenantID;

	private String gatewayID;

	private String prefix;

	private int direction;
	
	@Transient
	private String callDirection;

	private Date updateTime;
	
	@ManyToOne
	@JoinColumn(name = "gatewayID", referencedColumnName = "SERVICE_REF" ,insertable = false, updatable = false)
	@JsonIgnore
	private VidyoGateway vidyoGateway;

	/**
	 * @return the prefixID
	 */
	public int getPrefixID() {
		return prefixID;
	}

	/**
	 * @param prefixID
	 *            the prefixID to set
	 */
	public void setPrefixID(int prefixID) {
		this.prefixID = prefixID;
	}

	/**
	 * @return the tenantID
	 */
	public int getTenantID() {
		return tenantID;
	}

	/**
	 * @param tenantID
	 *            the tenantID to set
	 */
	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * @return the gatewayID
	 */
	public String getGatewayID() {
		return gatewayID;
	}

	/**
	 * @param gatewayID
	 *            the gatewayID to set
	 */
	public void setGatewayID(String gatewayID) {
		this.gatewayID = gatewayID;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the callDirection
	 */
	public String getCallDirection() {
		return callDirection;
	}

	/**
	 * @param callDirection the callDirection to set
	 */
	public void setCallDirection(String callDirection) {
		this.callDirection = callDirection;
	}

}
