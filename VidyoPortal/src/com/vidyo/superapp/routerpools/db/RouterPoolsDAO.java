package com.vidyo.superapp.routerpools.db;

import java.util.List;

import com.vidyo.bo.Location;
import com.vidyo.superapp.components.bo.VidyoRouter;
import com.vidyo.superapp.routerpools.bo.CloudConfig;
import com.vidyo.superapp.routerpools.bo.Pool;
import com.vidyo.superapp.routerpools.bo.PoolPriorityList;
import com.vidyo.superapp.routerpools.bo.PoolPriorityMap;
import com.vidyo.superapp.routerpools.bo.PoolToPool;
import com.vidyo.superapp.routerpools.bo.RouterPoolMap;
import com.vidyo.superapp.routerpools.bo.Rule;
import com.vidyo.superapp.routerpools.bo.RuleSet;

public interface RouterPoolsDAO {

	public List<Pool> findAllPools(int cloudConfigID);
	public List<PoolToPool> getConnectionList(String status);
	public List<VidyoRouter> getAvailableRouters(int cloudConfigId);
	public List<VidyoRouter> getActiveRouters(int cloudConfigId);
	public List<PoolPriorityList> getAllPriorityLists(int cloudConfigID);
	public List<PoolPriorityList> getPriorityListWithPools(int cloudConfigID);
	public Pool savePool(Pool poolObj);
	public void deletePools(List<Integer> deleteIds, int configId);
	public PoolPriorityList savePoolPriorityList(PoolPriorityList poolprioritylistObj);
	public void deletePoolPriorityList(List<Integer> deleteID);
	public PoolPriorityMap savePoolPriorityMap(PoolPriorityMap poolPriorityMapObj);
	public void deletePoolPriorityMaps(List<Integer> deleteID, int configId);
	public PoolToPool saveConnection(PoolToPool connectionObj);
	public void deleteConnections(int pool1, int pool2, int configId);
	public List<Rule> getRulesList(int cloudConfigID);
	public Rule saveRule(Rule ruleObj);
	public void deleteRules(List<Integer> deleteID);
	public List<PoolPriorityList> getAvailablePoolPriorityList(int cloudConfigID);
	public Integer getCloudConfigIdByStatus(String status);
	public List<Location> getLocations();
	public Location saveLocation(Location locationObj);
	public void deleteRouterPoolMap(int deleteID);
	public List<PoolPriorityMap> getPoolPriorityMapList(String status);
	public int getModifiedPoolID(int poolID, int cloudConfigIDForModified);
	public int getModifiedPriorityListID(int priorityListID, int cloudConfigIDForModified);
	public List<RouterPoolMap> getRouterPoolMapList(int cloudConfigIDForActive);
	public RouterPoolMap saveRouterPoolMap(RouterPoolMap routerPoolMap);
	public int getMaxCloudConfigVersion();
	public CloudConfig saveCloudConfig(CloudConfig cloudConfig);
	public String getLocationTagByID(int locationID);
	public Integer getCloudConfigVersionByStatus(String status);
	public Integer updateStatusByCloudConfigID(int cloudConfigID, String status);
	public RuleSet saveRuleSet(RuleSet ruleSet);
	public Integer getRuleCountAssocitedWithPools(List<Integer> deleteIds, int configId);
	public List<Integer> getPoolIdsNotAllowedForDelete();
	public void deleteLocations(List<Integer> deleteIds);
	public Integer getRuleCountAssocitedWithLocations(List<Integer> deleteIds);
	public Integer getRuleCountAssocitedWithPriorityLists(List<Integer> deleteIds);
	public void deleteConnectionsForPools(List<Integer> deleteIds, int configId);
	public Rule getRuleByOrder(int ruleOrder);
	public List<Rule> updateOrderForRuleList(List<Rule> modifiedRulesList);
	public void deleteRuleSetsForRules(List<Integer> deleteID);
	public void deleteRuleSets(List<Integer> deleteID);
	public void deletePoolPriorityMapByPoolIds(List<Integer> deleteIDs, int configId);
	public void deletePoolPriorityMapByPriorityListIds(List<Integer> deleteIDs);
	public void deleteRouterPoolMapsForPools(List<Integer> deleteIDs, int configId);
	public boolean isRouterPoolPresent();
	public List<CloudConfig> findCloudConfigByStatus(String status);
	public List<PoolToPool> getConnectionListByPool(int poolId, int configId);
	public int deletCloudConfigById(int id);

	public void deletePoolPriorityMapByPoolPriorityIds(int priorityListId, List<Integer> deleteIDs, int configId);

	public Pool getPoolById(int id, int configId);

	public List<Location> getLocationByTag(String locationTag);

	public List<Rule> getRulesByOrder(int lowRuleOrder, int highRuleOrder);

	public List<Rule> getRules(List<Integer> ruleIds);

	public List<Pool> getUnassignedPools(Integer priorityListId, Integer cloudConfigId);

	public List<Pool> getAssignedPools(Integer priorityListId, Integer cloudConfigId);

	public Integer updateConfigXMLByCloudConfigID(int cloudConfigID, String configData);

	public Location getLocationById(Integer locationId);
}
