package com.vidyo.superapp.components.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.components.bo.VidyoManager;


public interface VidyoManagerRepository extends  JpaRepository<VidyoManager, Integer> {
	
	@Query("FROM VidyoManager WHERE components.id = :compID")
    public VidyoManager findManagerByCompID(@Param("compID") int compID);
	
	public VidyoManager findById(int id);

	@Modifying
	@Transactional
	@Query("Delete from VidyoManager where components.id in ( :deleteIDs )")
	public void deleteVidyoManagersByCompIDs(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Query("select fqdn FROM VidyoManager")
    public List<String> getManagerFQDN();
	
}
