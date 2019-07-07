package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.vidyo.framework.beanserializer.WhiteSpaceRemovalDeserializer;

@Entity
@Table(name = "ruleset")
public class RuleSet implements Serializable, Comparator<RuleSet> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	int id;
	
	@Column(name = "RULESET_ORDER")
    int ruleSetOrder;
	
	@Column(name = "PRIVATE_IP")
	@JsonSerialize(using=StringSerializer.class)
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
	String privateIP;
	
	@Column(name = "PRIVATE_IP_CIDR")
	int privateIpCIDR;
	
	@Column(name = "PUBLIC_IP")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
	String publicIP;
	
	@Column(name = "PUBLIC_IP_CIDR")
	int publicIpCIDR;
	
	@Column(name = "LOCATION_TAG_ID")
	int locationTagID;
	
	@Column(name = "ENDPOINT_ID")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
	String endpointID;
	
	@ManyToOne
	@JoinColumn(name = "RULE_ID", nullable = false)
	@JsonIgnore
	Rule rule;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRuleSetOrder() {
		return ruleSetOrder;
	}
	public void setRuleSetOrder(int ruleSetOrder) {
		this.ruleSetOrder = ruleSetOrder;
	}
	public String getPrivateIP() {
		return privateIP;
	}
	public void setPrivateIP(String privateIP) {
		this.privateIP = privateIP;
	}
	public int getPrivateIpCIDR() {
		return privateIpCIDR;
	}
	public void setPrivateIpCIDR(int privateIpCIDR) {
		this.privateIpCIDR = privateIpCIDR;
	}
	public String getPublicIP() {
		return publicIP;
	}
	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}
	public int getPublicIpCIDR() {
		return publicIpCIDR;
	}
	public void setPublicIpCIDR(int publicIpCIDR) {
		this.publicIpCIDR = publicIpCIDR;
	}
	public int getLocationTagID() {
		return locationTagID;
	}
	public void setLocationTagID(int locationTagID) {
		this.locationTagID = locationTagID;
	}
	public String getEndpointID() {
		return endpointID;
	}
	public void setEndpointID(String endpointID) {
		this.endpointID = endpointID;
	}
	public Rule getRule() {
		return rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	@Override
	public int compare(RuleSet o1, RuleSet o2) {
		  return o1.getRuleSetOrder() - (o2.getRuleSetOrder());
	}
}
