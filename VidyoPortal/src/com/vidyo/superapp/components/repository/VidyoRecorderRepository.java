package com.vidyo.superapp.components.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.components.bo.VidyoRecorder;


public interface VidyoRecorderRepository extends  JpaRepository<VidyoRecorder, Integer> {
	
	
	@Query("FROM VidyoRecorder WHERE components.id = :compId")
    public VidyoRecorder findRecorderByCompId(@Param("compId") int compId);
	
	@Modifying
	@Transactional
	@Query("Delete from VidyoRecorder where components.id in ( :deleteIDs )")
	public void deleteVidyoRecorderByCompID(@Param("deleteIDs") List<Integer> deleteIDs);
	
}
