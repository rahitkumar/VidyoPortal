/**
 * 
 */
package com.vidyo.superapp.components.repository.gateway;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.components.bo.VidyoGateway;

/**
 * @author ganesh
 *
 */
public interface GatewayRepository extends JpaRepository<VidyoGateway, Integer> {
	
	@Query("FROM VidyoGateway WHERE components.id = :compId")
    public VidyoGateway findGatewayByCompId(@Param("compId") int compId);

	@Modifying
	@Transactional
	@Query("Delete from VidyoGateway where components.id in ( :deleteIDs )")
	public void deleteVidyoGatewayByCompID(@Param("deleteIDs") List<Integer> deleteIDs);

}
