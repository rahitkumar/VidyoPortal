/**
 * 
 */
package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ysakurikar
 *
 */

@Embeddable
public class PoolPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8706657597481113907L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	int id;
	
	@Column(name="CLOUD_CONFIG_ID")
	private int cloudConfigId;
	
	public PoolPK(){
	}
	
	public PoolPK( @JsonProperty ("id") int id) {
		this.id = id;
	}

	public PoolPK( int id, int configId) {
		this.id = id;
		this.cloudConfigId = configId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCloudConfigId() {
		return cloudConfigId;
	}

	public void setCloudConfigId(int cloudConfigId) {
		this.cloudConfigId = cloudConfigId;
	}

	@Override
	public int hashCode() {
		return (id +"" + cloudConfigId).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PoolPK){
			PoolPK tempObj = (PoolPK) obj;
			if (this.getId() == tempObj.getId() && this.getCloudConfigId() == tempObj.getCloudConfigId()){
				return true;
			}
			
			return false;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return (id +"-" + cloudConfigId);
	}
	
}
