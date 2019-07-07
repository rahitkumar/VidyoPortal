package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers.IntegerSerializer;

@Entity
@Table(name = "pool_priority_map")
public class PoolPriorityMap implements Serializable {

	private static final long serialVersionUID = 1L;

	@AttributeOverrides ({
		@AttributeOverride (name="poolId", column=@Column (name = "POOL_ID")),
		@AttributeOverride (name="cloudConfigId", column=@Column (name = "CLOUD_CONFIG_ID")),
		@AttributeOverride (name="priorityListId", column=@Column (name = "PRIORITY_LIST_ID"))
	})
	@EmbeddedId
	private PoolPriorityMapPK poolPriorityMapPK;

	@MapsId ("poolId, cloudConfigId")
	@JoinColumns ({
		@JoinColumn(name = "POOL_ID", referencedColumnName = "ID", nullable = false),
		@JoinColumn(name = "CLOUD_CONFIG_ID", referencedColumnName="CLOUD_CONFIG_ID", nullable = false) })
	@ManyToOne
	private Pool pool;


	@MapsId ("priorityListId")
	@JoinColumn(name = "PRIORITY_LIST_ID", referencedColumnName="ID", nullable = false)
	@ManyToOne
	@JsonIgnore
	private PoolPriorityList poolPriorityList;

	@Column(name = "POOL_ORDER")
	private int order;


	public PoolPriorityMap (){

	}

	public PoolPriorityList getPoolPriorityList() {
		return poolPriorityList;
	}

	public void setPoolPriorityList(PoolPriorityList poolPriorityList) {
		this.poolPriorityList = poolPriorityList;
		if (poolPriorityMapPK == null){
			poolPriorityMapPK = new PoolPriorityMapPK();
		}
		poolPriorityMapPK.setPriorityListId(poolPriorityList.getId());
		poolPriorityMapPK.setCloudConfigId(poolPriorityList.getCloudConfig().getId());
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
		if (poolPriorityMapPK == null){
			poolPriorityMapPK = new PoolPriorityMapPK();
		}
		poolPriorityMapPK.setPoolId(pool.getId());
		poolPriorityMapPK.setCloudConfigId(pool.getCloudConfig().getId());
	}
}
