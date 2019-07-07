package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers.IntegerSerializer;
import com.vidyo.superapp.components.bo.VidyoRouter;

@Entity
@Table(name = "router_pool_map")
public class RouterPoolMap implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	
	@JoinColumns ({
		@JoinColumn(name = "POOL_ID", referencedColumnName = "ID", nullable = false),
		@JoinColumn(name = "CLOUD_CONFIG_ID", referencedColumnName="CLOUD_CONFIG_ID", nullable = false) })
	@ManyToOne (fetch = FetchType.LAZY)
	@JsonIgnore
	private Pool pool;

	@JoinColumn(name = "VR_ID", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private VidyoRouter vidyoRouter;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Pool getPool() {
		return pool;
	}
	public void setPool(Pool pool) {
		this.pool = pool;
	}
	public VidyoRouter getVidyoRouter() {
		return vidyoRouter;
	}
	public void setVidyoRouter(VidyoRouter vidyoRouter) {
		this.vidyoRouter = vidyoRouter;
	}	
}

