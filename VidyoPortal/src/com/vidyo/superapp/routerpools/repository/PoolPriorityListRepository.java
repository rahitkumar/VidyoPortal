package com.vidyo.superapp.routerpools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.routerpools.bo.PoolPriorityList;


public interface PoolPriorityListRepository extends JpaRepository<PoolPriorityList, Integer>{
	
	
	@Query("FROM PoolPriorityList WHERE cloudConfig.id = :cloudConfigID and id not in (select poolPriorityList.id from  Rule)")
    public List<PoolPriorityList> getAvailablePriorityList(@Param("cloudConfigID") Integer cloudConfigID);
	
	@Query("select ppl FROM PoolPriorityList ppl join ppl.poolPriorityMap WHERE ppl.cloudConfig.id = :cloudConfigID")
    public List<PoolPriorityList> findPriorityListsWithPoolsByCloudConfigID(@Param("cloudConfigID") Integer cloudConfigID);
	
	@Query("FROM PoolPriorityList ppl WHERE ppl.cloudConfig.id = :cloudConfigID")
    public List<PoolPriorityList> findAllPriorityListsByCloudConfigID(@Param("cloudConfigID") Integer cloudConfigID);
	
	@Query("select id from PoolPriorityList where priorityListName = (select priorityListName from PoolPriorityList where id = :priorityListID) and cloudConfig.id = :cloudConfigIDForModified ")
    public int getModifiedPriorityListID(@Param("priorityListID") int priorityListID, @Param("cloudConfigIDForModified") Integer cloudConfigIDForModified);
	
	@Modifying
	@Transactional
	@Query("Delete from PoolPriorityList where id in ( :deleteIDs )")
	public void deletePoolPriorityLists(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Query("select count(id) from Rule where poolPriorityList.id in ( :deleteIDs )")
	public Integer getRuleCountAssocitedWithPoolPriorityLists(@Param("deleteIDs") List<Integer> deleteIDs);

}
