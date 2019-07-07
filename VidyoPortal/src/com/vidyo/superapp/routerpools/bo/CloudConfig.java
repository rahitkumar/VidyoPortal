package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers.IntegerSerializer;


@Entity
@Table(name = "cloud_config")
public class CloudConfig implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	int id;

	@Column(name = "VERSION")
    int version;

	@Column(name = "STATUS")
	String status;

	@Column(name = "CONFIG_XML")
	@JsonIgnore
	private String configData;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cloudConfig")
	@JsonIgnore
	Set<Rule> rules = new HashSet<Rule>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cloudConfig")
	@JsonIgnore
	Set<Pool> pools = new HashSet<Pool>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cloudConfig")
	@JsonIgnore
	Set<PoolPriorityList> poolPriorityList = new HashSet<PoolPriorityList>(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

	public Set<Pool> getPools() {
		return pools;
	}

	public void setPools(Set<Pool> pools) {
		this.pools = pools;
	}

	public Set<PoolPriorityList> getPoolPriorityList() {
		return poolPriorityList;
	}

	public void setPoolPriorityList(Set<PoolPriorityList> poolPriorityList) {
		this.poolPriorityList = poolPriorityList;
	}

	/**
	 * @return the configData
	 */
	public String getConfigData() {
		return configData;
	}

	/**
	 * @param configData the configData to set
	 */
	public void setConfigData(String configData) {
		this.configData = configData;
	}


}
