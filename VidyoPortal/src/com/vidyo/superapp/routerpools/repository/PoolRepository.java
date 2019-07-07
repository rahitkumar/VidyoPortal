package com.vidyo.superapp.routerpools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.routerpools.bo.Pool;
import com.vidyo.superapp.routerpools.bo.PoolPK;


public interface PoolRepository extends JpaRepository<Pool, PoolPK>{
	
	@Query("FROM Pool WHERE cloudConfig.id = :cloudConfigID")
    public List<Pool> findByPoolsByCloudConfigID(@Param("cloudConfigID") Integer cloudConfigID);
	
	@Query("select poolKey.id from Pool where poolKey.id = :poolID and cloudConfig.id = :cloudConfigIDForModified ")
    public int getModifiedPoolID(@Param("poolID") int poolID, @Param("cloudConfigIDForModified") Integer cloudConfigIDForModified);
	
	@Modifying
	@Transactional
	@Query("Delete from Pool where poolKey.id in ( :deleteIDs ) and cloudConfig.id = :configId")
	public void deletePools(@Param("deleteIDs") List<Integer> deleteIDs, @Param("configId") int configId);
	
	//@Query(value="select COUNT(id) from rules r where r.POOL_PRIORITY_LIST_ID in (select ID from pool_priority_map where POOL_ID in (:deleteIDs) and Cloud_Config_id = :configId)",nativeQuery=true)
	@Query(value="select COUNT(id) from rules r, pool_priority_map ppm where ppm.POOL_ID in (:deleteIDs) and ppm.Cloud_Config_id = :configId and r.POOL_PRIORITY_LIST_ID = ppm.PRIORITY_LIST_ID and r.Cloud_Config_id = ppm.Cloud_Config_id",nativeQuery=true)
	public Integer getRuleCountAssocitedWithPools(@Param("deleteIDs") List<Integer>  deleteIDs, @Param("configId") Integer configId);
	
	@Query(value = "select POOL_ID from pool_priority_map group by PRIORITY_LIST_ID having count(PRIORITY_LIST_ID) = 1", nativeQuery = true)
	public List<Integer> getPoolIdsNotAllowedForDelete();
	
	@Query(value = "select Count(*) from pool", nativeQuery = true)
	public Integer getPoolCount();
	
	@Query(value = "select p from Pool p left join p.poolPriorityMap ppm with ppm.poolPriorityMapPK.priorityListId =:priorityListId where ppm.pool is null and p.cloudConfig.id =:cloudConfigId")
	public List<Pool> getUnassignedPools(@Param("priorityListId")Integer priorityListId, @Param("cloudConfigId") Integer cloudConfigId);
	
	@Query(value = "select p, ppm.order from Pool p join p.poolPriorityMap ppm where ppm.poolPriorityMapPK.priorityListId =:priorityListId and p.cloudConfig.id =:cloudConfigId order by ppm.order asc")
	public List<Object[]> getAssignedPools(@Param("priorityListId")Integer priorityListId, @Param("cloudConfigId") Integer cloudConfigId);

}
