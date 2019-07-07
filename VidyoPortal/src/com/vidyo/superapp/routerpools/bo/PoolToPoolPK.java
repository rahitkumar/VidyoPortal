/**
 *
 */
package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers.IntegerSerializer;

/**
 * @author ysakurikar
 *
 */

@Embeddable
public class PoolToPoolPK implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CLOUD_CONFIG_ID", nullable = false)
	int cloudConfigId;

	@Column(name = "POOL1")
    int pool1;

	@Column(name = "POOL2")
    int pool2;


	public PoolToPoolPK(){

	}

	public PoolToPoolPK(int pool1, int pool2, int cloudConfigId){
		this.pool1 = pool1;
		this.pool2 = pool2;
		this.cloudConfigId = cloudConfigId;
	}

	@JsonGetter ("pool1")
	public int getPool1() {
		return pool1;
	}

	@JsonSetter ("pool1")
	public void setPool1(int pool1) {
		this.pool1 = pool1;
	}

	@JsonGetter ("pool2")
	public int getPool2() {
		return pool2;
	}

	@JsonSetter ("pool2")
	public void setPool2(int pool2) {
		this.pool2 = pool2;
	}

	@JsonGetter ("CloudConfigId")
	public int getCloudConfigId() {
		return cloudConfigId;
	}

	@JsonSetter ("cloudConfigId")
	public void setCloudConfigId(int cloudConfigId) {
		this.cloudConfigId = cloudConfigId;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PoolToPoolPK){
			PoolToPoolPK tempObj = (PoolToPoolPK) obj;
			if (this.getPool1() == tempObj.getPool1() && this.getPool2() == tempObj.getPool2() && this.getCloudConfigId() == tempObj.getCloudConfigId()){
				return true;
			}

			return false;
		}
		return false;
	}
}
