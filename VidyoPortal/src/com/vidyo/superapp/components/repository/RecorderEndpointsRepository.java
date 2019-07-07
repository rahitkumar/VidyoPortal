package com.vidyo.superapp.components.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vidyo.superapp.components.bo.RecorderEndpoints;




public interface RecorderEndpointsRepository extends  JpaRepository<RecorderEndpoints, Integer> {
	
	@Query("from RecorderEndpoints re where re.vidyoRecorder.id =:recorderId and re.status =:status")
	List<RecorderEndpoints> findByVidyoRecorderAndStatus(@Param("recorderId") int recorderId, @Param("status") int status);

	/**
	 * To fetch the RecorderEndpoint based on description
	 * @param description
	 * @return
	 */
	List<RecorderEndpoints> findByDescription(@Param("description") String description);
}
