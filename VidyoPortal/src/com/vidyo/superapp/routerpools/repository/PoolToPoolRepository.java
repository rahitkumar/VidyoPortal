package com.vidyo.superapp.routerpools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.routerpools.bo.PoolToPool;
import com.vidyo.superapp.routerpools.bo.PoolToPoolPK;


public interface PoolToPoolRepository extends JpaRepository<PoolToPool, PoolToPoolPK>{
	
	@Query("FROM PoolToPool plpl WHERE plpl.poolToPoolPK.cloudConfigId in (select plc.cloudConfig.id from Pool plc where plc.cloudConfig.status = :status) and "
			+ " (plpl.poolToPoolPK.pool1 in (SELECT pl.poolKey.id FROM Pool pl where pl.cloudConfig.status = :status) or "
			+ "plpl.poolToPoolPK.pool2 in (SELECT pl.poolKey.id FROM Pool pl where pl.cloudConfig.status = :status))")
    public List<PoolToPool> findConnectionByStatus(@Param("status") String status);
	
	@Query("FROM PoolToPool plpl WHERE plpl.poolToPoolPK.cloudConfigId = :configId and "
			+ " (plpl.poolToPoolPK.pool1 in (SELECT pl.poolKey.id FROM Pool pl where pl.poolKey.id = :poolId and "
			+ " pl.poolKey.cloudConfigId = :configId) or plpl.poolToPoolPK.pool2 in (SELECT pl.poolKey.id FROM Pool pl where pl.poolKey.id = :poolId and pl.poolKey.cloudConfigId = :configId))")
    public List<PoolToPool> findConnectionByPool(@Param("poolId") int poolId, @Param("configId") Integer configId);

	/*@Query("FROM PoolToPool WHERE pool1 = :poolId OR pool2 = :poolId")
    public List<PoolToPool> findConnectionByPool(@Param("poolId") int poolId);*/
	
	@Modifying
	@Transactional
	@Query("Delete from PoolToPool plpl where (plpl.poolToPoolPK.pool1 in ( :deleteIDs ) or plpl.poolToPoolPK.pool2 in ( :deleteIDs )) and plpl.poolToPoolPK.cloudConfigId = :configId")
	public void deleteConnectionsForPools(@Param("deleteIDs") List<Integer> deleteIDs, @Param("configId") Integer configId);

	@Modifying
	@Transactional
	@Query("Delete from PoolToPool where ((poolToPoolPK.pool1 = :pool1Id and poolToPoolPK.pool2 = :pool2Id) or (poolToPoolPK.pool2 = :pool1Id and poolToPoolPK.pool1 = :pool2Id)) and poolToPoolPK.cloudConfigId = :configId")
	public void deleteConnections(@Param("pool1Id") Integer pool1, @Param("pool2Id") Integer pool2, @Param("configId") Integer configId);

}
