package com.vidyo.superapp.routerpools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.routerpools.bo.RuleSet;

public interface RuleSetRepository extends JpaRepository<RuleSet, Integer>{
	
	
	@Modifying
	@Transactional
	@Query("Delete from RuleSet where rule.id in ( :deleteIDs )")
	public void deleteRuleSetsForRules(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Modifying
	@Transactional
	@Query("Delete from RuleSet where id in ( :deleteIDs )")
	public void deleteRuleSetsbyIds(@Param("deleteIDs") List<Integer> deleteIDs);
			
}	
