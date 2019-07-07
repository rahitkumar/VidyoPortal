package com.vidyo.superapp.components.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.vidyo.superapp.components.bo.gateway.GatewayPrefix;

@Entity
@Table(name = "vidyo_gateway_config")
public class VidyoGateway implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "USERNAME")
	private String userName;

	@JsonSerialize(using = StringSerializer.class)
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "SERVICE_ENDPOINT_GUID")
	private String serviceEndpointGuid;

	@Column(name = "TOKEN")
	private String token;

	@Column(name = "SERVICE_REF")
	private String serviceRef;

	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "COMP_ID")
	private Component components;

	@OneToMany(mappedBy = "vidyoGateway", fetch = FetchType.EAGER)
	private List<GatewayPrefix> gatewayPrefixs;

	@OneToMany(mappedBy = "vidyoGateway", fetch = FetchType.EAGER)
	private Set<VirtualEndpoints> virtualEndpoints = new HashSet<VirtualEndpoints>();
    
	@Column(name = "CREATION_TIME")
	private Date creationTime;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Component getComponents() {
		return components;
	}

	public void setComponents(Component components) {
		this.components = components;
	}

	/**
	 * @return the gatewayPrefixs
	 */
	public List<GatewayPrefix> getGatewayPrefixs() {
		return gatewayPrefixs;
	}

	/**
	 * @param gatewayPrefixs
	 *            the gatewayPrefixs to set
	 */
	public void setGatewayPrefixs(List<GatewayPrefix> gatewayPrefixs) {
		this.gatewayPrefixs = gatewayPrefixs;
	}

	/**
	 * @return the serviceEndpointGuid
	 */
	public String getServiceEndpointGuid() {
		return serviceEndpointGuid;
	}

	/**
	 * @param serviceEndpointGuid
	 *            the serviceEndpointGuid to set
	 */
	public void setServiceEndpointGuid(String serviceEndpointGuid) {
		this.serviceEndpointGuid = serviceEndpointGuid;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the serviceRef
	 */
	public String getServiceRef() {
		return serviceRef;
	}

	/**
	 * @param serviceRef
	 *            the serviceRef to set
	 */
	public void setServiceRef(String serviceRef) {
		this.serviceRef = serviceRef;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
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
	 * @return the virtualEndpoints
	 */
	public Set<VirtualEndpoints> getVirtualEndpoints() {
		return virtualEndpoints;
	}

	/**
	 * @param virtualEndpoints
	 *            the virtualEndpoints to set
	 */
	public void setVirtualEndpoints(Set<VirtualEndpoints> virtualEndpoints) {
		this.virtualEndpoints = virtualEndpoints;
	}
}
