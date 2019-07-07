package com.vidyo.superapp.routerpools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.routerpools.bo.RouterPoolMap;

public interface RouterPoolMapRepository extends JpaRepository<RouterPoolMap, Integer>{
	
	@Modifying
	@Transactional
	@Query("Delete FROM RouterPoolMap WHERE id = :id")
    public Integer deleteRouterPoolMapById(@Param("id") int id);
	
	@Query("FROM RouterPoolMap rmap WHERE rmap.pool.cloudConfig.id = :cloudConfigIDForActive")
	public List<RouterPoolMap> findBycloudConfigID( @Param("cloudConfigIDForActive") int cloudConfigIDForActive);

	@Modifying
	@Transactional
	@Query("Delete from RouterPoolMap where vidyoRouter.id in ( select id from VidyoRouter where components.id in ( :deleteIDs ) )")
	public void deleteRouterPoolMapByCompID(@Param("deleteIDs") List<Integer> deleteIDs);

	@Modifying
	@Transactional
	@Query(value="Delete from router_pool_map where CLOUD_CONFIG_ID = :configId and POOL_ID in ( :deleteIDs ) ", nativeQuery = true)
	public void deleteRouterPoolMapByPoolID(@Param("deleteIDs") List<Integer> deleteIDs, @Param("configId") int configId);

}
	
	

