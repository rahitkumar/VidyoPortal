package com.vidyo.superapp.routerpools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.bo.Location;


public interface LocationRepository extends JpaRepository<Location, Integer>{
	
	@Query("SELECT locationTag FROM Location WHERE locationID = :locationID")
	public String findLocationTagByID(@Param("locationID") int locationID);

	@Modifying
	@Transactional
	@Query("Delete from Location where id in ( :deleteIDs )")
	public void deleteLocations(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Query("select count(r.id) from Rule r, CloudConfig cc where r.id in (select rule.id from RuleSet where locationTagID in :deleteIDs) and r.cloudConfig.status in ('ACTIVE' , 'MODIFIED')")
	public Integer getRuleCountAssocitedWithLocations(@Param("deleteIDs") List<Integer> deleteIDs);
	
	public List<Location> findByLocationTag(String locationTag);

}
