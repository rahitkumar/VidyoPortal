package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.vidyo.framework.beanserializer.WhiteSpaceRemovalDeserializer;

@Entity
@Table(name = "pool")
@JsonRootName(value = "pool")
@JsonPropertyOrder({ "poolKey", "name", "x", "y", "cloudConfig", "routerPoolMap"})
public class Pool implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@JsonIgnore
	private PoolPK poolKey;
	
	@Column(name = "POOL_NAME")
	@JsonSerialize(using=StringSerializer.class)
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String name;
	
	@Column(name = "X")
	int x;
	
	@Column(name = "Y")
	int y;
	
	@Transient
	private int order;
	
	@MapsId("cloudConfigId")
	@JoinColumn(name = "CLOUD_CONFIG_ID", referencedColumnName="ID")
	@ManyToOne
	CloudConfig cloudConfig;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pool" , cascade=CascadeType.ALL)
	@JsonIgnore
	private List<PoolPriorityMap> poolPriorityMap = new ArrayList<PoolPriorityMap>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pool", cascade=CascadeType.ALL)
	private Set<RouterPoolMap> routerPoolMap = new HashSet<RouterPoolMap>(0);

	@JsonGetter ("id")
	public int getId() {
		if (poolKey == null)
			return 0;
		
		return poolKey.getId();
	}
	
	@JsonSetter ("id")
	public void setId(int id) {
		if (poolKey == null)
			poolKey = new PoolPK();
		poolKey.setId(id);
	}
	
	public Pool(){
		poolKey = new PoolPK();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public CloudConfig getCloudConfig() {
		return cloudConfig;
	}
	public void setCloudConfig(CloudConfig cloudConfig) {
		this.cloudConfig = cloudConfig;
		if (poolKey == null)
			poolKey = new PoolPK();
		poolKey.setCloudConfigId(cloudConfig.getId());
	}
	public List<PoolPriorityMap> getPoolPriorityMap() {
		return poolPriorityMap;
	}
	public void setPoolPriorityMap(List<PoolPriorityMap> poolPriorityMap) {
		this.poolPriorityMap = poolPriorityMap;
	}
	public Set<RouterPoolMap> getRouterPoolMap() {
		return routerPoolMap;
	}
	public void setRouterPoolMap(Set<RouterPoolMap> routerPoolMap) {
		this.routerPoolMap = routerPoolMap;
	}
	public PoolPK getPoolKey() {
		return poolKey;
	}
	public void setPoolKey(PoolPK poolKey) {
		this.poolKey = poolKey;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}