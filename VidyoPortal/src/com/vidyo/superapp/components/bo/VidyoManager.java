package com.vidyo.superapp.components.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers.IntegerSerializer;
import com.vidyo.framework.beanserializer.WhiteSpaceRemovalDeserializer;

@Entity
@Table(name = "vidyo_manager_config")
public class VidyoManager implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "EMCP_PORT")
	private int emcpport;

	@Column(name = "SOAP_PORT")
	private int soapport;

	@Column(name = "RMCP_PORT")
	private int rmcpport;

	@Column(name = "FQDN")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
	private String fqdn;

	@Column(name = "DSCP")
	private int dscpvalue;

	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "COMP_ID")
	private Component components;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmcpport() {
		return emcpport;
	}

	public void setEmcpport(int emcpport) {
		this.emcpport = emcpport;
	}

	public int getSoapport() {
		return soapport;
	}

	public void setSoapport(int soapport) {
		this.soapport = soapport;
	}

	public int getRmcpport() {
		return rmcpport;
	}

	public String getFqdn() {
		return fqdn;
	}

	public void setFqdn(String fqdn) {
		this.fqdn = fqdn;
	}

	public void setRmcpport(int rmcpport) {
		this.rmcpport = rmcpport;
	}

	public int getDscpvalue() {
		return dscpvalue;
	}

	public void setDscpvalue(int dscpvalue) {
		this.dscpvalue = dscpvalue;
	}

	public Component getComponents() {
		return components;
	}

	public void setComponents(Component components) {
		this.components = components;
	}
}
