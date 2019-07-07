package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.vidyo.framework.beanserializer.WhiteSpaceRemovalDeserializer;

@Entity
@Table(name = "priority_list")
public class PoolPriorityList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	int id;
	
	@Column(name = "PRIORITY_LIST_NAME")
	@JsonSerialize(using=StringSerializer.class)
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String priorityListName;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "poolPriorityList", cascade=CascadeType.ALL)
	@OrderBy("poolPriorityList.id, order ASC")
	private List<PoolPriorityMap> poolPriorityMap = new ArrayList<PoolPriorityMap>(0);
	
	/*@OneToOne(fetch = FetchType.EAGER, mappedBy = "poolPriorityList", cascade=CascadeType.ALL)
	@JsonIgnore
	Rule rules;*/
	
	@ManyToOne
	@JoinColumn(name = "CLOUD_CONFIG_ID", nullable = false)
	CloudConfig cloudConfig;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPriorityListName() {
		return priorityListName;
	}
	public void setPriorityListName(String priorityListName) {
		this.priorityListName = priorityListName;
	}
	public List<PoolPriorityMap> getPoolPriorityMap() {
		return poolPriorityMap;
	}
	public void setPoolPriorityMap(List<PoolPriorityMap> poolPriorityMap) {
		this.poolPriorityMap = poolPriorityMap;
	}
	public CloudConfig getCloudConfig() {
		return cloudConfig;
	}
	public void setCloudConfig(CloudConfig cloudConfig) {
		this.cloudConfig = cloudConfig;
	}
	/*public Rule getRules() {
		return rules;
	}
	public void setRules(Rule rules) {
		this.rules = rules;
	}*/
	
	
}