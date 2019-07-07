/**
 * 
 */
package com.vidyo.superapp.routerpools.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author ysakurikar
 *
 */

@Embeddable
public class PoolPriorityMapPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private int poolId;
	
	
	private int cloudConfigId;
	
	
	private int priorityListId;
	
	public PoolPriorityMapPK(){
		
	}

	public PoolPriorityMapPK(int poolId, int cloudConfigId, int priorityListId){
		this.poolId = poolId;
		this.cloudConfigId = cloudConfigId;
		this.priorityListId = priorityListId;
	}
	
	public int getPoolId() {
		return poolId;
	}

	public void setPoolId(int poolId) {
		this.poolId = poolId;
	}

	public int getCloudConfigId() {
		return cloudConfigId;
	}

	public void setCloudConfigId(int cloudConfigId) {
		this.cloudConfigId = cloudConfigId;
	}

	public int getPriorityListId() {
		return priorityListId;
	}

	public void setPriorityListId(int priorityListId) {
		this.priorityListId = priorityListId;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PoolPriorityMapPK){
			PoolPriorityMapPK tempObj = (PoolPriorityMapPK) obj;
			if (this.getPoolId() == tempObj.getPoolId() && this.getCloudConfigId() == tempObj.getCloudConfigId() && this.getPriorityListId() == tempObj.getPriorityListId()){
				return true;
			}
			
			return false;
		}
		return false;
	}
	
	
}
