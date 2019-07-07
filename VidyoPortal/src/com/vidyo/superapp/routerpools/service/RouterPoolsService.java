package com.vidyo.superapp.routerpools.service;

import java.util.List;

import com.vidyo.bo.Location;
import com.vidyo.framework.service.ServiceException;
import com.vidyo.service.exceptions.GeneralException;
import com.vidyo.superapp.components.bo.VidyoRouter;
import com.vidyo.superapp.routerpools.bo.CloudConfig;
import com.vidyo.superapp.routerpools.bo.Pool;
import com.vidyo.superapp.routerpools.bo.PoolPriorityList;
import com.vidyo.superapp.routerpools.bo.PoolPriorityMap;
import com.vidyo.superapp.routerpools.bo.PoolToPool;
import com.vidyo.superapp.routerpools.bo.RouterPoolMap;
import com.vidyo.superapp.routerpools.bo.Rule;
import com.vidyo.superapp.routerpools.bo.RuleSet;


public interface RouterPoolsService {
	
	public List<Pool> getPoolList(int cloudConfigID);
	public List<PoolToPool> getConnectionList(String status);
	public List<VidyoRouter> getAvailableRouters(int cloudConfigId);
	public List<VidyoRouter> getActiveRouters(int cloudConfigId);
	public List<PoolPriorityList> getAllPriorityLists(int cloudConfigID);
	public List<PoolPriorityList> getPriorityListsWithPools(int cloudConfigID);
	public Pool savePool(Pool poolObj);
	public void deletePoolPriorityList(List<Integer> deleteID);
	public PoolPriorityMap savePoolPriorityMap(PoolPriorityMap poolPriorityMapObj);
	public void deletePoolPriorityMaps(List<Integer> deleteID, String statusString);
	public PoolToPool saveConnection(PoolToPool connectionObj);
	public void deleteConnections(int pool1, int pool2, String statusString);
	public List<Rule> getRulesList(int cloudConfigID);
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
	public boolean updateStatusByCloudConfigID(int cloudConfigID, String status);
	public RuleSet saveRuleSet(RuleSet ruleSet);
	public boolean isDeletePoolsAllowed(List<Integer> deleteIds, String statusString);
	public void deleteLocations(List<Integer> deleteIds);
	public boolean isDeleteLocationAllowed(List<Integer> deleteIds);
	public boolean isDeletePoolPriorityListAllowed(List<Integer> deleteIds);
	public void deleteConnectionsForPools(List<Integer> deleteIds, String statusString);
	public List<Rule> updateOrderForRuleList(int currentOrder, int newOrder);
	public void deleteRuleSets(List<Integer> deleteIDs);
	public void deletePoolPriorityMapByPoolIds(List<Integer> deleteIDs, String statusString);
	public void deletePoolPriorityMapByPriorityListIds(List<Integer> deleteIDs);
	public void deleteRouterPoolMapsForPools(List<Integer> deleteIDs, String statusString);
	public boolean isRouterPoolPresent();
	public List<CloudConfig> findCloudConfigByStatus(String status);
	public List<PoolToPool> getConnectionListByPool(int poolId, String statusString);
	public boolean makeRouterPoolsConfigActive();
	public Pool saveRouterPool(String deleteIDs [],Pool poolObj);
	public int deletePool(List<Integer> deleteID, String statusString);
	public PoolPriorityList addPoolPriorityList(PoolPriorityList poolprioritylistObj, List<Integer> deleteIds, String statusString);
	
	/**
	 * 
	 * To delete all the cloud configuration based on the status passed.
	 * * <p/>
	 * <p>Note:This is a hard delete.</p>
	 * 
	 * @return 1  if it is success.-1 if no data found
	
	 */
	public int  discardModifiedCoudConfig(String statusString) throws GeneralException;
	
	public CloudConfig makeCopyOfActiveRecords() throws ServiceException;
	
	public List<Location> getLocationByTag(String locationTag);
	
	public Rule addRule(Rule ruleObj, List<Integer> deleteIds);
	
	/**
	 * Returns the list of Assigned Router Pools for the Priority List
	 * @param priorityListId
	 * @return
	 */
	public List<Pool> getAssignedPools(Integer priorityListId, Integer cloudConfigId);
	
	/**
	 * Returns the list of Router Pools not assigned to the Priority List
	 * @param priorityListId
	 * @return
	 */
	public List<Pool> getUnassignedPools(Integer priorityListId, Integer cloudConfigId);

}

