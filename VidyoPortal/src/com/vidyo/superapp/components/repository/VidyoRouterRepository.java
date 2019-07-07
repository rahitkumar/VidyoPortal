package com.vidyo.superapp.components.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.components.bo.VidyoRouter;


public interface VidyoRouterRepository extends  JpaRepository<VidyoRouter, Integer> {
	
	@Query("FROM VidyoRouter WHERE components.id = :compID")
    public VidyoRouter findRouterByCompID(@Param("compID") int compID);
	
	@Query("FROM VidyoRouter router where router.components.status in ('NEW','ACTIVE') and router.id NOT IN (SELECT vidyoRouter.id from RouterPoolMap rMap where rMap.pool.cloudConfig.id = :cloudConfigId)")
    public List<VidyoRouter> findAvailableRouters(@Param("cloudConfigId") int cloudConfigId);
	
	@Query("FROM VidyoRouter router where router.components.status in ('ACTIVE') and router.id NOT IN (SELECT vidyoRouter.id from RouterPoolMap rMap where rMap.pool.cloudConfig.id = :cloudConfigId)")
    public List<VidyoRouter> findActiveRouters(@Param("cloudConfigId") int cloudConfigId);	
	
	public VidyoRouter findById(int id);
	
	@Query("FROM VidyoRouter router where components.status in ('NEW','ACTIVE')")
	public List<VidyoRouter> getAvailableRouters();
	
	@Modifying
	@Transactional
	@Query("Delete from VidyoRouter where components.id in ( :deleteIDs )")
	public void deleteVidyoRouterByCompID(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Query("select components.id FROM VidyoRouter router where router.id IN (SELECT vidyoRouter.id from RouterPoolMap) and router.components.id in ( :deleteIDs ) ")
	public List<Integer> getComponentIDsAssociatedWithPools(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Query("select router FROM VidyoRouter router where router.components.localIP = (SELECT localIP from Component comp where comp.localIP =:localIP and comp.compType.name ='VidyoManager')")
	public VidyoRouter getRouterByIpAddress(@Param("localIP") String localIP);
	
	@Query("FROM VidyoRouter router where components.status in ('ACTIVE')")
	public List<VidyoRouter> getAllActiveRouters();
}
