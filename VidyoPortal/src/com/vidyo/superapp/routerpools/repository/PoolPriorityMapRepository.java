package com.vidyo.superapp.routerpools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.routerpools.bo.PoolPriorityMap;
import com.vidyo.superapp.routerpools.bo.PoolPriorityMapPK;


public interface PoolPriorityMapRepository extends JpaRepository<PoolPriorityMap, PoolPriorityMapPK> { 
	
	@Modifying
	@Transactional
	@Query("Delete FROM PoolPriorityMap ppMap WHERE ppMap.poolPriorityMapPK.priorityListId in ( :deleteIDs ) and ppMap.poolPriorityMapPK.cloudConfigId = :configId")
    public Integer deletePoolPriorityMapByIds(@Param("deleteIDs") List<Integer> deleteIDs, @Param("configId") Integer configId);
	
	@Query(value="select * from pool_priority_map where POOL_ID in (SELECT pl.id from pool pl, cloud_config cloudconfig where pl.CLOUD_CONFIG_ID = cloudconfig.id and cloudconfig.status = :status)", nativeQuery=true)
    public List<PoolPriorityMap> findPoolPriorityMapByStatus(@Param("status") String status);

	@Modifying
	@Transactional
	@Query(value="Delete from pool_priority_map where POOL_ID in ( :deleteIDs ) and CLOUD_CONFIG_ID = :configId", nativeQuery=true)
    public Integer deletePoolPriorityMapByPoolIds(@Param("deleteIDs") List<Integer> deleteIDs, @Param("configId") Integer configId);
	
	@Modifying
	@Transactional
	@Query(value="Delete from pool_priority_map where POOL_ID in ( :deleteIDs ) and CLOUD_CONFIG_ID = :configId and PRIORITY_LIST_ID = :priorityListId", nativeQuery=true)
    public Integer deletePoolPriorityMapByPoolPriorityIds(@Param("priorityListId") int priorityListId, @Param("deleteIDs") List<Integer> deleteIDs, @Param("configId") Integer configId);
	
	@Modifying
	@Transactional
	@Query("Delete FROM PoolPriorityMap ppMap WHERE ppMap.poolPriorityMapPK.priorityListId in ( :deleteIDs )")
    public Integer deletePoolPriorityMapByPriorityListIds(@Param("deleteIDs") List<Integer> deleteIDs);

}
