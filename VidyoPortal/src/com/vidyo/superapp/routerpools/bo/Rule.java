package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.vidyo.framework.beanserializer.WhiteSpaceRemovalDeserializer;

@Entity
@Table(name = "rules")
public class Rule implements Serializable, Comparator<Rule> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	int id;
	
	@Column(name = "RULE_NAME")
	@JsonSerialize(using=StringSerializer.class)
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String ruleName;
	
	@Column(name = "RULE_ORDER")
    int ruleOrder;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "POOL_PRIORITY_LIST_ID", referencedColumnName = "ID", nullable = false)
	PoolPriorityList poolPriorityList;	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "rule")
	Set<RuleSet> ruleSet = new HashSet<RuleSet>(0);
	
	@ManyToOne
	@JoinColumn(name = "CLOUD_CONFIG_ID",referencedColumnName = "ID", nullable = false)
	CloudConfig cloudConfig;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public int getRuleOrder() {
		return ruleOrder;
	}
	public void setRuleOrder(int ruleOrder) {
		this.ruleOrder = ruleOrder;
	}
	public PoolPriorityList getPoolPriorityList() {
		return poolPriorityList;
	}
	public void setPoolPriorityList(PoolPriorityList poolPriorityList) {
		this.poolPriorityList = poolPriorityList;
	}
	public CloudConfig getCloudConfig() {
		return cloudConfig;
	}
	public void setCloudConfig(CloudConfig cloudConfig) {
		this.cloudConfig = cloudConfig;
	}
	public Set<RuleSet> getRuleSet() {
		return ruleSet;
	}
	public void setRuleSet(Set<RuleSet> ruleSet) {
		this.ruleSet = ruleSet;
	}
	@Override
	public int compare(Rule o1, Rule o2) {
		  return o1.getRuleOrder() - (o2.getRuleOrder());
	}
}
