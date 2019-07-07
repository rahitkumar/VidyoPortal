package com.vidyo.superapp.components.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.components.bo.RouterMediaAddrMap;


public interface RouterMediaAddrMapRepository extends  JpaRepository<RouterMediaAddrMap, Integer> {
	
	@Modifying
	@Transactional
	@Query("Delete from RouterMediaAddrMap where id in ( :deleteIDs)  ")
	public void deleteMediaAddressMap(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Modifying
	@Transactional
	@Query("Delete from RouterMediaAddrMap where vidyoRouter.id in ( select id from VidyoRouter where components.id in ( :deleteIDs ) )")
	public void deleteMediaAddressMapByCompID(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Modifying
	@Transactional
	@Query("Delete from RouterMediaAddrMap where id not in ( :deleteIDs)  ")
	public void deleteMediaAddressMapNotIn(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Modifying
	@Transactional
	@Query("Delete from RouterMediaAddrMap where id not in ( :deleteIDs) and vidyoRouter.id = :routerId ")
	public void deleteMediaAddressMapNotIn(@Param("deleteIDs") List<Integer> deleteIDs, @Param("routerId") Integer routerId);
}
