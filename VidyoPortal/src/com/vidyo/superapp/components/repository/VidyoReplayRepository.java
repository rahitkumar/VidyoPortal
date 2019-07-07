package com.vidyo.superapp.components.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.components.bo.VidyoReplay;


public interface VidyoReplayRepository extends  JpaRepository<VidyoReplay, Integer> {
	
	
	@Query("FROM VidyoReplay WHERE components.id = :compId")
    public VidyoReplay findReplayByCompId(@Param("compId") int compId);
	
	@Modifying
	@Transactional
	@Query("Delete from VidyoReplay where components.id in ( :deleteIDs )")
	public void deleteVidyoReplayByCompID(@Param("deleteIDs") List<Integer> deleteIDs);
	
	
	@Query("select replay FROM VidyoReplay replay where replay.components.localIP = (SELECT localIP from Component comp where comp.localIP =:localIP and comp.compType.name ='VidyoReplay')")
	public VidyoReplay getReplayByIpAddress(@Param("localIP") String localIP);
	
}
