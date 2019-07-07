package com.vidyo.superapp.routerpools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.routerpools.bo.Rule;


public interface RuleRepository extends JpaRepository<Rule, Integer>{
	
	@Query("FROM Rule WHERE cloudConfig.id = :cloudConfigID")
    public List<Rule> findRulesByCloudConfigID(@Param("cloudConfigID") int cloudConfigID);
	
	@Query("FROM Rule WHERE cloudConfig.status = 'MODIFIED' and ruleOrder = :ruleOrder")
	public Rule findByRuleOrder(@Param("ruleOrder") int ruleOrder);
	
	@Modifying
	@Transactional
	@Query("Delete from Rule where id in ( :deleteIDs )")
	public void deleteRules(@Param("deleteIDs") List<Integer> deleteIDs);
	
	@Query("FROM Rule WHERE cloudConfig.status = 'MODIFIED' and ruleOrder <= :highRuleOrder and ruleOrder >= :lowRuleOrder")
	public List<Rule> findByRulesByOrder(@Param("lowRuleOrder") int lowRuleOrder, @Param("highRuleOrder") int highRuleOrder);
}
