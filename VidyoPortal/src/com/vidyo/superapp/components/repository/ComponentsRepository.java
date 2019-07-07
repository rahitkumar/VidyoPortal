package com.vidyo.superapp.components.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.components.bo.Component;

public interface ComponentsRepository extends
		JpaRepository<Component, Integer> {

	@Modifying
	@Transactional
	@Query("update Component c set c.status = :status where c.id = :id")
	public Integer saveComponentStatus(@Param("id") int id,
			@Param("status") String status);

	@Query("FROM Component c where c.name LIKE CONCAT('%', :name, '%') and c.compType.name LIKE CONCAT('%', :type, '%') and c.status LIKE CONCAT( :status, '%')")
	public List<Component> getComponentsByStatus(@Param("name") String name,
			@Param("type") String type, @Param("status") String status);

	@Query("FROM Component c where c.name LIKE CONCAT('%', :name, '%') and c.compType.name LIKE CONCAT('%', :type, '%') and c.alarm != '' ")
	public List<Component> getComponentsByAlarm(@Param("name") String name,
			@Param("type") String type);
	
	

	@Query("FROM Component c where c.compID = :compId and c.compType.name = :compType")
	public Component findByCompIDAndCompType(@Param("compId") String compId,
			@Param("compType") String compType);
	
	@Query("FROM Component c where c.compType.name =:compType and c.status != 'INACTIVE'")
	public List<Component> getComponentsByCompType(@Param("compType") String type);
	
	@Modifying
	@Transactional
	@Query("Delete from Component where id in ( :deleteIDs )")
	public void deleteSelectedComponents(@Param("deleteIDs") List<Integer> deleteIDs);

	@Query("Select id FROM Component c where c.localIP = :ipAddress and c.compType.name = :compType")
	public List<Integer> findByLocalIPAndCompType(@Param("ipAddress") String ipAddress, @Param("compType") String compType);

	
	@Query("FROM Component ")
	public List<Component> getAllComponents();
	
	@Query("FROM Component c where c.compType.name =:compType")
	public List<Component> getAllComponentsByCompType(@Param("compType") String type);

}
