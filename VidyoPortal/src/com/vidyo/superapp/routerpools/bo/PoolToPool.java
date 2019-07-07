package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name = "pool_to_pool")
public class PoolToPool implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	@JsonIgnore
	PoolToPoolPK poolToPoolPK;
    
	@Column(name = "WEIGHT")
    int weight;
    
	@Column(name = "DIRECTION")
    int direction;
	
	public PoolToPool(){
		poolToPoolPK = new PoolToPoolPK();
	}
    
	
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public PoolToPoolPK getPoolToPoolPK() {
		return poolToPoolPK;
	}
	public void setPoolToPoolPK(PoolToPoolPK poolToPoolPK) {
		this.poolToPoolPK = poolToPoolPK;
	}
	
	@JsonGetter ("pool1")
	public int getPool1() {
		if (poolToPoolPK == null){
			poolToPoolPK = new PoolToPoolPK();
		}
		return poolToPoolPK.getPool1();
	}
	
	@JsonSetter ("pool1")
	public void setPool1(int pool1) {
		if (poolToPoolPK == null){
			poolToPoolPK = new PoolToPoolPK();
		}
		this.poolToPoolPK.setPool1(pool1);
	}
	
	@JsonGetter ("pool2")
	public int getPool2() {
		if (poolToPoolPK == null){
			poolToPoolPK = new PoolToPoolPK();
		}
		return poolToPoolPK.getPool2();
	}
	
	@JsonSetter ("pool2")
	public void setPool2(int pool2) {
		if (poolToPoolPK == null){
			poolToPoolPK = new PoolToPoolPK();
		}
		this.poolToPoolPK.setPool2(pool2);
	}
	
	@JsonGetter ("cloudConfigId")
	public int getCloudConfigId() {
		if (poolToPoolPK == null){
			poolToPoolPK = new PoolToPoolPK();
		}
		return poolToPoolPK.getCloudConfigId();
	}
	
	@JsonSetter ("cloudConfigId")
	public void setCloudConfigId(int cloudConfigId) {
		if (poolToPoolPK == null){
			poolToPoolPK = new PoolToPoolPK();
		}
		this.poolToPoolPK.setCloudConfigId(cloudConfigId);
	}
	
	/*@JsonGetter ("id")
	public int getId(){
		return 0;
	}
	
	@JsonSetter ("id")
	public void setId(int id){
	}*/
}
