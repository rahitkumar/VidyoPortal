package com.vidyo.superapp.routerpools.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidyo.bo.Location;
import com.vidyo.superapp.components.bo.VidyoRouter;
import com.vidyo.superapp.components.repository.VidyoRouterRepository;
import com.vidyo.superapp.routerpools.bo.CloudConfig;
import com.vidyo.superapp.routerpools.bo.Pool;
import com.vidyo.superapp.routerpools.bo.PoolPK;
import com.vidyo.superapp.routerpools.bo.PoolPriorityList;
import com.vidyo.superapp.routerpools.bo.PoolPriorityMap;
import com.vidyo.superapp.routerpools.bo.PoolToPool;
import com.vidyo.superapp.routerpools.bo.RouterPoolMap;
import com.vidyo.superapp.routerpools.bo.Rule;
import com.vidyo.superapp.routerpools.bo.RuleSet;
import com.vidyo.superapp.routerpools.repository.CloudConfigRepository;
import com.vidyo.superapp.routerpools.repository.LocationRepository;
import com.vidyo.superapp.routerpools.repository.PoolPriorityListRepository;
import com.vidyo.superapp.routerpools.repository.PoolPriorityMapRepository;
import com.vidyo.superapp.routerpools.repository.PoolRepository;
import com.vidyo.superapp.routerpools.repository.PoolToPoolRepository;
import com.vidyo.superapp.routerpools.repository.RouterPoolMapRepository;
import com.vidyo.superapp.routerpools.repository.RuleRepository;
import com.vidyo.superapp.routerpools.repository.RuleSetRepository;

@Service(value = "RouterPoolsDAO")
public class RouterPoolsDAOImpl implements RouterPoolsDAO {

	@Autowired
	PoolRepository poolRepository;

	@Autowired
	PoolToPoolRepository poolToPoolRepository;

	@Autowired
	VidyoRouterRepository vidyoRouterRepository;

	@Autowired
	PoolPriorityMapRepository poolPriorityMapRepository;

	@Autowired
	PoolPriorityListRepository poolPriorityListRepository;

	@Autowired
	RuleRepository ruleRepository;

	@Autowired
	CloudConfigRepository cloudConfigRepository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	RouterPoolMapRepository routerPoolMapRepository;

	@Autowired
	RuleSetRepository ruleSetRepository;

	@Override
	public List<Pool> findAllPools(int cloudConfigID) {

		Iterable<Pool> pools = poolRepository.findByPoolsByCloudConfigID(cloudConfigID);

		return (List<Pool>) pools;
	}

	@Override
	public List<PoolToPool> getConnectionList(String status) {

		Iterable<PoolToPool> connections = poolToPoolRepository.findConnectionByStatus(status);

		return (List<PoolToPool>) connections;
	}

	@Override
	public List<PoolToPool> getConnectionListByPool(int poolId, int configId) {

		List<PoolToPool> connections = poolToPoolRepository.findConnectionByPool(poolId, configId);

		return connections;
	}

	@Override
	public List<VidyoRouter> getAvailableRouters(int cloudConfigId) {

		List<VidyoRouter> routersList = vidyoRouterRepository.findAvailableRouters(cloudConfigId);

		return routersList;
	}

	@Override
	public List<VidyoRouter> getActiveRouters(int cloudConfigId) {

		List<VidyoRouter> routersList = vidyoRouterRepository.findActiveRouters(cloudConfigId);

		return routersList;
	}

	@Override
	public List<PoolPriorityList> getAllPriorityLists(int cloudConfigID) {
		Iterable<PoolPriorityList> poolPriorityList = poolPriorityListRepository.findAllPriorityListsByCloudConfigID(cloudConfigID);

		return (List<PoolPriorityList>) poolPriorityList;
	}

	@Override
	public List<PoolPriorityList> getPriorityListWithPools(int cloudConfigID) {
		Iterable<PoolPriorityList> poolPriorityList = poolPriorityListRepository.findPriorityListsWithPoolsByCloudConfigID(cloudConfigID);

		return (List<PoolPriorityList>) poolPriorityList;
	}

	@Override
	public Pool savePool(Pool poolObj) {

		Pool pool = poolRepository.save(poolObj);

		return pool;
	}

	@Override
	public void deletePools(List<Integer>  deleteIds, int configId) {

		poolRepository.deletePools(deleteIds, configId);

	}

	@Override
	public PoolPriorityList savePoolPriorityList(
			PoolPriorityList poolprioritylistObj) {
		PoolPriorityList poolPriorityList = poolPriorityListRepository
				.save(poolprioritylistObj);

		return poolPriorityList;
	}

	@Override
	public void deletePoolPriorityList(List<Integer> deleteIds) {

		poolPriorityListRepository.deletePoolPriorityLists(deleteIds);

	}

	@Override
	public PoolPriorityMap savePoolPriorityMap(
			PoolPriorityMap poolPriorityMapObj) {

		PoolPriorityMap poolPriorityMapObject = poolPriorityMapRepository
				.save(poolPriorityMapObj);

		return poolPriorityMapObject;
	}

	@Override
	public void deletePoolPriorityMaps(List<Integer> deleteIDs, int configId) {

		poolPriorityMapRepository.deletePoolPriorityMapByIds(deleteIDs, configId);

	}

	@Override
	public PoolToPool saveConnection(PoolToPool connectionObj) {
		PoolToPool connection = poolToPoolRepository.save(connectionObj);

		return connection;
	}

	@Override
	public void deleteConnections(int pool1, int pool2, int configId) {

		poolToPoolRepository.deleteConnections(pool1, pool2, configId);

	}

	@Override
	public List<Rule> getRulesList(int cloudConfigID) {
		Iterable<Rule> rulesList = ruleRepository.findRulesByCloudConfigID(cloudConfigID);

		return (List<Rule>) rulesList;
	}

	@Override
	public Rule saveRule(Rule ruleObj) {
		Rule rule = ruleRepository.save(ruleObj);

		return rule;
	}

	@Override
	public void deleteRules(List<Integer> deleteIDs) {

		ruleRepository.deleteRules(deleteIDs);
	}

	@Override
	public List<PoolPriorityList> getAvailablePoolPriorityList(int cloudConfigID) {

		List<PoolPriorityList> list = poolPriorityListRepository.getAvailablePriorityList(cloudConfigID);

		return list;
	}

	@Override
	public Integer getCloudConfigIdByStatus(String status) {

		Integer cloudConfigID = cloudConfigRepository.findCloudConfigIDByStatus(status);

		return cloudConfigID;
	}

	@Override
	public List<Location> getLocations() {

		Iterable<Location> locations = locationRepository.findAll();

		return (List<Location>) locations;
	}

	@Override
	public void deleteRouterPoolMap(int deleteID) {
		routerPoolMapRepository.deleteRouterPoolMapById(deleteID);

	}

	@Override
	public List<PoolPriorityMap> getPoolPriorityMapList(String status) {

		Iterable<PoolPriorityMap> poolPriorityMapList = poolPriorityMapRepository.findPoolPriorityMapByStatus(status);

		return (List<PoolPriorityMap>)poolPriorityMapList;

	}

	@Override
	public int getModifiedPoolID(int poolID, int cloudConfigIDForModified) {

		int poolId = poolRepository.getModifiedPoolID(poolID, cloudConfigIDForModified);

		return poolId;
	}

	@Override
	public int getModifiedPriorityListID(int priorityListID,
			int cloudConfigIDForModified) {

		int poolPriorityListId = poolPriorityListRepository.getModifiedPriorityListID(priorityListID, cloudConfigIDForModified);

		return poolPriorityListId;
	}

	@Override
	public Location saveLocation(Location locationObj) {
		Location location =  locationRepository.save(locationObj);
		return location;
	}

	@Override
	public List<RouterPoolMap> getRouterPoolMapList(int cloudConfigIDForActive) {
		List<RouterPoolMap> routerPoolMapList = routerPoolMapRepository.findBycloudConfigID(cloudConfigIDForActive);
		return routerPoolMapList;
	}

	@Override
	public RouterPoolMap saveRouterPoolMap(RouterPoolMap routerPoolMapObj) {
		RouterPoolMap routerPoolMap = routerPoolMapRepository.save(routerPoolMapObj);
		return routerPoolMap;
	}

	@Override
	public int getMaxCloudConfigVersion() {
		int version = cloudConfigRepository.findMaxCloudConfigVersion();
		return version;
	}

	@Override
	public CloudConfig saveCloudConfig(CloudConfig cloudConfig) {
		CloudConfig cloudConfigObj = cloudConfigRepository.save(cloudConfig);
		return cloudConfigObj;
	}

	@Override
	public String getLocationTagByID(int locationID) {
		String locationTag = locationRepository.findLocationTagByID(locationID);
		return locationTag;
	}

	@Override
	public Integer getCloudConfigVersionByStatus(String status) {
		Integer cloudConfigVersion = cloudConfigRepository.findCloudConfigVersionByStatus(status);
		return cloudConfigVersion;
	}

	@Override
	public Integer updateStatusByCloudConfigID(int cloudConfigID,
			String status) {
		return cloudConfigRepository.updateStatusByCloudConfigID(cloudConfigID, status);
	}

	@Override
	public Integer updateConfigXMLByCloudConfigID(int cloudConfigID, String configData) {
		return cloudConfigRepository.updateConfigXMLByCloudConfigID(cloudConfigID, configData);
	}



	@Override
	public RuleSet saveRuleSet(RuleSet ruleSet) {
		return ruleSetRepository.save(ruleSet);
	}

	@Override
	public Integer getRuleCountAssocitedWithPools(List<Integer>  deleteIds, int configId) {
		return poolRepository.getRuleCountAssocitedWithPools(deleteIds, configId);
	}

	@Override
	public List<Integer> getPoolIdsNotAllowedForDelete() {
		return poolRepository.getPoolIdsNotAllowedForDelete();
	}

	@Override
	public void deleteLocations(List<Integer> deleteIds) {
		locationRepository.deleteLocations(deleteIds);

	}

	@Override
	public Integer getRuleCountAssocitedWithLocations(List<Integer> deleteIds) {
		return locationRepository.getRuleCountAssocitedWithLocations(deleteIds);
	}

	@Override
	public Integer getRuleCountAssocitedWithPriorityLists(
			List<Integer> deleteIds) {
		return poolPriorityListRepository.getRuleCountAssocitedWithPoolPriorityLists(deleteIds);
	}

	@Override
	public void deleteConnectionsForPools(List<Integer> deleteIds, int configId) {
		poolToPoolRepository.deleteConnectionsForPools(deleteIds, configId);
	}

	@Override
	public Rule getRuleByOrder(int ruleOrder) {
		return ruleRepository.findByRuleOrder(ruleOrder);
	}

	@Override
	public List<Rule> getRulesByOrder(int lowRuleOrder, int highRuleOrder) {
		return ruleRepository.findByRulesByOrder(lowRuleOrder, highRuleOrder);
	}

	@Override
	public List<Rule> updateOrderForRuleList(List<Rule> modifiedRulesList) {
		Iterable<Rule> updatedrulesList = ruleRepository.save(modifiedRulesList);

		return (List<Rule>)updatedrulesList;
	}

	@Override
	public void deleteRuleSetsForRules(List<Integer> deleteIDs) {
		ruleSetRepository.deleteRuleSetsForRules(deleteIDs);

	}

	@Override
	public void deleteRuleSets(List<Integer> deleteIDs) {
		ruleSetRepository.deleteRuleSetsbyIds(deleteIDs);

	}

	@Override
	public void deletePoolPriorityMapByPoolIds( List<Integer> deleteIDs, int configId) {
		poolPriorityMapRepository.deletePoolPriorityMapByPoolIds(deleteIDs, configId);

	}

	@Override
	public void deletePoolPriorityMapByPoolPriorityIds(int priorityListId, List<Integer> deleteIDs, int configId) {
		poolPriorityMapRepository.deletePoolPriorityMapByPoolPriorityIds(priorityListId, deleteIDs, configId);

	}

	@Override
	public void deletePoolPriorityMapByPriorityListIds(List<Integer> deleteIDs) {
		poolPriorityMapRepository.deletePoolPriorityMapByPriorityListIds(deleteIDs);

	}

	@Override
	public void deleteRouterPoolMapsForPools(List<Integer> deleteIDs, int configId) {
		routerPoolMapRepository.deleteRouterPoolMapByPoolID(deleteIDs, configId);

	}

	/**
	 * If modified config is present, return true
	 */
	@Override
	public boolean isRouterPoolPresent() {
		return (!findCloudConfigByStatus("MODIFIED").isEmpty());
	}

	@Override
	public List<CloudConfig> findCloudConfigByStatus(String status) {
		return cloudConfigRepository.findByStatus(status);
	}
	@Override
	public int deletCloudConfigById(int id) {
		return cloudConfigRepository.deletCloudConfigById(id);
	}

	@Override
	public Pool getPoolById(int id, int configId) {
		return poolRepository.findOne(new PoolPK(id,configId));
	}

	@Override
	public List<Location> getLocationByTag(String locationTag) {
		return locationRepository.findByLocationTag(locationTag);
	}

	@Override
	public List<Rule> getRules(List<Integer> ruleIds) {
		return ruleRepository.findAll(ruleIds);
	}

	@Override
	public List<Pool> getUnassignedPools(Integer priorityListId, Integer cloudConfigId) {
		return poolRepository.getUnassignedPools(priorityListId, cloudConfigId);
	}

	@Override
	public List<Pool> getAssignedPools(Integer priorityListId, Integer cloudConfigId) {
		List<Pool> pools = new ArrayList<Pool>();
		List<Object[]> objs = poolRepository.getAssignedPools(priorityListId, cloudConfigId);
		for(Object[] obj : objs) {
			Pool pool = new Pool();
			pool = (Pool)obj[0];
			pool.setOrder((Integer)obj[1]);
			pools.add(pool);
		}
		return pools;
	}

	@Override
	public Location getLocationById(Integer locationId) {
		Location location = locationRepository.findOne(locationId);
		return location;
	}

}
