package com.vidyo.superapp.routerpools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.routerpools.bo.CloudConfig;

public interface CloudConfigRepository extends JpaRepository<CloudConfig, Integer>{
	
	
	@Query("SELECT id FROM CloudConfig WHERE status = :status")
    public Integer findCloudConfigIDByStatus(@Param("status") String status);
	
	@Query("SELECT max(version) FROM CloudConfig")
    public int findMaxCloudConfigVersion();
	
	@Query("SELECT version FROM CloudConfig WHERE status = :status")
    public Integer findCloudConfigVersionByStatus(@Param("status") String status);
	
	@Modifying
	@Transactional
	@Query("UPDATE CloudConfig set status = :status WHERE id = :cloudConfigID")
	public Integer updateStatusByCloudConfigID(@Param("cloudConfigID") int cloudConfigID,@Param("status") String status);
	
	@Modifying
	@Transactional
	@Query("UPDATE CloudConfig set configData = :configData WHERE id = :cloudConfigID")
	public Integer updateConfigXMLByCloudConfigID(@Param("cloudConfigID") int cloudConfigID,@Param("configData") String configData);
	
	public List<CloudConfig> findByStatus(String status);
	
	
	@Modifying
	@Transactional
	@Query("Delete FROM CloudConfig WHERE id = :id")
    public Integer deletCloudConfigById(@Param("id") int id);
	
}	
