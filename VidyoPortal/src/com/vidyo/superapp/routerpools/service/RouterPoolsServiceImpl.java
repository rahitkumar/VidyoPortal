package com.vidyo.superapp.routerpools.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Location;
import com.vidyo.db.IServiceDao;
import com.vidyo.db.ISystemDao;
import com.vidyo.db.idp.ITenantIdpAttributesMappingDao;
import com.vidyo.db.ldap.ITenantLdapAttributesMappingDao;
import com.vidyo.framework.service.BaseServiceImpl;
import com.vidyo.framework.service.ServiceException;
import com.vidyo.parser.xsd.networkconfig.NetworkConfigType;
import com.vidyo.service.IServiceService;
import com.vidyo.service.exceptions.GeneralException;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.superapp.components.bo.VidyoRouter;
import com.vidyo.superapp.components.service.ComponentsService;
import com.vidyo.superapp.routerpools.bo.CloudConfig;
import com.vidyo.superapp.routerpools.bo.Pool;
import com.vidyo.superapp.routerpools.bo.PoolPK;
import com.vidyo.superapp.routerpools.bo.PoolPriorityList;
import com.vidyo.superapp.routerpools.bo.PoolPriorityMap;
import com.vidyo.superapp.routerpools.bo.PoolToPool;
import com.vidyo.superapp.routerpools.bo.RouterPoolMap;
import com.vidyo.superapp.routerpools.bo.Rule;
import com.vidyo.superapp.routerpools.bo.RuleSet;
import com.vidyo.superapp.routerpools.db.RouterPoolsDAO;

@Service("RouterPoolsService")
public class RouterPoolsServiceImpl extends BaseServiceImpl implements RouterPoolsService {

	@Autowired
	RouterPoolsDAO routerPoolsDAO;

	@Autowired
	private IServiceDao serviceDao;

	@Autowired
	private ISystemDao systemDao;

	@Autowired
	private IServiceService serviceService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ComponentsService componentService;

	@Autowired
	private Jaxb2Marshaller networkJaxb2Marshaller;

	@Autowired
    private ITenantLdapAttributesMappingDao tenantLdapAttributesMappingDao;

	@Autowired
    private ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao;

	protected static final Logger logger = LoggerFactory
			.getLogger(RouterPoolsServiceImpl.class);

	private static final String MODIFIED = "MODIFIED";

	@Transactional(readOnly = true)
	@Override
	public List<Pool> getPoolList(int cloudConfigID) throws ServiceException {
		logger.debug("Get All Pools");

		List<Pool> list = routerPoolsDAO.findAllPools(cloudConfigID);
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<PoolToPool> getConnectionList(String status)
			throws ServiceException {
		logger.debug("Get Connection List");

		List<PoolToPool> list = routerPoolsDAO.getConnectionList(status);
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<PoolToPool> getConnectionListByPool(int poolId, String statusString) throws ServiceException {
		logger.debug("Get Connection List");
		int cloudConfigID = routerPoolsDAO.getCloudConfigIdByStatus(statusString);
		List<PoolToPool> list = routerPoolsDAO.getConnectionListByPool(poolId, cloudConfigID);
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<VidyoRouter> getAvailableRouters(int cloudConfigId)
			throws ServiceException {
		logger.debug("Get Available Routers List");

		List<VidyoRouter> list = routerPoolsDAO
				.getAvailableRouters(cloudConfigId);
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<VidyoRouter> getActiveRouters(int cloudConfigId)
			throws ServiceException {
		logger.debug("Get Available Routers List");

		List<VidyoRouter> list = routerPoolsDAO
				.getActiveRouters(cloudConfigId);
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<PoolPriorityList> getAllPriorityLists(int cloudConfigID)
			throws ServiceException {
		logger.debug("Get Pool Priority List");

		List<PoolPriorityList> list = routerPoolsDAO
				.getAllPriorityLists(cloudConfigID);
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<PoolPriorityList> getPriorityListsWithPools(int cloudConfigID)
			throws ServiceException {
		logger.debug("Get Pool Priority List");

		List<PoolPriorityList> list = routerPoolsDAO.getPriorityListWithPools(cloudConfigID);
		return list;
	}

	@Transactional
	@Override
	public Pool savePool(Pool poolObj) {
		logger.debug("Save pool");
		Pool pool = null;
		try {
			pool = routerPoolsDAO.savePool(poolObj);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
		return pool;
	}

	@Transactional
	@Override
	public Pool saveRouterPool(String[] deleteIDs, Pool poolObj) {
		logger.debug("Save pool");
		Pool pool = null;
		try {
			if(deleteIDs.length != 0) {
				for(String i : deleteIDs) {
					if(!i.isEmpty() && null != i)
						routerPoolsDAO.deleteRouterPoolMap(Integer.parseInt(i));
				}
			}

			Set<RouterPoolMap> routerSet = poolObj.getRouterPoolMap();
			Iterator<RouterPoolMap> itr = routerSet.iterator();
			while(itr.hasNext()) {
				RouterPoolMap routerPoolMap = itr.next();
				if(routerPoolMap.getId() >= 0){
					//poolObj.setRouterPoolMap(null);
					routerPoolMap.setPool(poolObj);
					RouterPoolMap map = routerPoolsDAO.saveRouterPoolMap(routerPoolMap);
				}
			}
			pool = routerPoolsDAO.savePool(poolObj);
			String[] transactionDetails = new String[] {"Add Router Pool", "SUCCESS", "Router Pool Name -"+pool.getName() + " Id-"+ pool.getPoolKey().getId()};
			createTransactionHistory(transactionDetails);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
		return poolObj;
	}

	@Transactional
	@Override
	public int deletePool(List<Integer> deleteIds, String statusString) {
		logger.debug("Delete Pool");
		int success = 0;
		try {
			int configId = routerPoolsDAO
					.getCloudConfigIdByStatus(statusString);
			String configValue = getConfigValue(0, "IPC_ROUTER_POOL");

			if (configValue != null && configValue.trim().length() > 0 && deleteIds.contains(Integer.parseInt(configValue))){
				success = 2;
			} else {
				int ruleCount = routerPoolsDAO.getRuleCountAssocitedWithPools(
						deleteIds, configId);
				if (ruleCount == 0) {
					routerPoolsDAO.deleteConnectionsForPools(deleteIds, configId);
					routerPoolsDAO.deletePoolPriorityMapByPoolIds(deleteIds,
							configId);
					routerPoolsDAO
							.deleteRouterPoolMapsForPools(deleteIds, configId);
					// Deletion happens one at a time
					Pool pool = null;
					try {
						pool = routerPoolsDAO.getPoolById(deleteIds.get(0),
								configId);
					} catch (Exception e) {
						logger.error("Pool doesn't exist" + deleteIds.get(0));
					}
					routerPoolsDAO.deletePools(deleteIds, configId);
					success = 1;
					if (pool != null) {
						String[] transactionDetails = new String[] {
								"Delete Router Pool",
								"SUCCESS",
								"Router Pool Name" + pool.getName()
										+ " Router Pool Id(s) -" + deleteIds
										+ " Cloud_Config -" + configId
										+ " Cloud_Config_Status -" + statusString };
						createTransactionHistory(transactionDetails);
					}
				}
			}
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
		return success;
	}

	@Transactional
	@Override
	public PoolPriorityList addPoolPriorityList(PoolPriorityList poolprioritylistObj, List<Integer> deleteIds, String statusString) {
		List<CloudConfig> ccList = routerPoolsDAO
				.findCloudConfigByStatus(statusString);
		int configId= ccList.get(0).getId();
		if(deleteIds.size() != 0) {
			routerPoolsDAO.deletePoolPriorityMapByPoolPriorityIds(poolprioritylistObj.getId(), deleteIds, configId);
		}

		List<PoolPriorityMap> poolPriorityMaplist = poolprioritylistObj.getPoolPriorityMap();

		poolprioritylistObj.setPoolPriorityMap(null);

		PoolPriorityList  dbPoolPriorityList = routerPoolsDAO
				.savePoolPriorityList(poolprioritylistObj);

		if(!poolPriorityMaplist.isEmpty()) {
			for(PoolPriorityMap poolPriorityMap: poolPriorityMaplist){
				if(null != poolPriorityMap){
					poolPriorityMap.setPoolPriorityList(dbPoolPriorityList);
					routerPoolsDAO.savePoolPriorityMap(poolPriorityMap);
				}
			}
		}
		if(dbPoolPriorityList != null) {
			String[] transactionDetails = new String[] {"Add Priority List", "SUCCESS", "Priority List Name -"+dbPoolPriorityList.getPriorityListName() + " Priority List Id -"+dbPoolPriorityList.getId()};
			createTransactionHistory(transactionDetails);
		}
		return dbPoolPriorityList;
	}

	@Transactional
	@Override
	public void deletePoolPriorityList(List<Integer> deleteID) {
		logger.debug("Delete pool priority list");
		try {
			routerPoolsDAO.deletePoolPriorityList(deleteID);
			String[] transactionDetails = new String[] {"Delete Priority List", "SUCCESS", " Priority List Id -"+deleteID};
			createTransactionHistory(transactionDetails);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}

	@Transactional
	@Override
	public PoolPriorityMap savePoolPriorityMap(
			PoolPriorityMap poolPriorityMapObj) {
		logger.debug("Save Pool Priority Map");
		PoolPriorityMap poolPriorityMapObject = null;
		try {
			poolPriorityMapObject = routerPoolsDAO
					.savePoolPriorityMap(poolPriorityMapObj);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
		return poolPriorityMapObject;
	}

	@Transactional
	@Override
	public void deletePoolPriorityMaps(List<Integer> deleteIDs, String status) {
		logger.debug("Delete pool priority map");
		try {
			Integer cloudConfigID = routerPoolsDAO.getCloudConfigIdByStatus(status);
			routerPoolsDAO.deletePoolPriorityMaps(deleteIDs, cloudConfigID);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}

	@Transactional
	@Override
	public PoolToPool saveConnection(PoolToPool connectionObj) {
		logger.debug("Save pool to pool connection");
		PoolToPool poolToPool = null;
		try {
			poolToPool = routerPoolsDAO.saveConnection(connectionObj);
			Pool pool1 = routerPoolsDAO.getPoolById(poolToPool.getPool1(), poolToPool.getCloudConfigId());
			Pool pool2 = routerPoolsDAO.getPoolById(poolToPool.getPool2(), poolToPool.getCloudConfigId());
			if(pool1 != null && pool2 != null) {
				String[] transactionDetails = new String[] {"Create PoolToPool Connection", "SUCCESS", pool1.getName() + " to " + pool2.getName() + " Weight -"+ poolToPool.getWeight()};
				createTransactionHistory(transactionDetails);
			}
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return poolToPool;
	}

	@Transactional
	@Override
	public void deleteConnections(int pool1, int pool2, String statusString) {
		logger.debug("Delete pool to pool connections");
		try {
			int cloudConfigID = routerPoolsDAO.getCloudConfigIdByStatus(statusString);
			Pool poolOne = routerPoolsDAO.getPoolById(pool1, cloudConfigID);
			Pool poolTwo = routerPoolsDAO.getPoolById(pool2, cloudConfigID);
			routerPoolsDAO.deleteConnections(pool1, pool2, cloudConfigID);
			if(poolOne != null && poolTwo != null) {
				String[] transactionDetails = new String[] {"Delete PoolToPool Connection", "SUCCESS", poolOne.getName() + " to " + poolTwo.getName() };
				createTransactionHistory(transactionDetails);
			}
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Rule> getRulesList(int cloudConfigID) {
		logger.debug("Get Rules List");

		List<Rule> rulesList = routerPoolsDAO.getRulesList(cloudConfigID);
		// Order the rule based on RULE_ORDER column
		TreeSet<Rule> treeRuleSet = new TreeSet<Rule>(new Rule());
		treeRuleSet.addAll(rulesList);

		List<Rule> sortedRulesList = new ArrayList<Rule>();
		sortedRulesList.addAll(sortedRulesList);

		return rulesList;
	}

	@Transactional
	@Override
	public Rule addRule(Rule ruleObj, List<Integer> deleteIds) {
		logger.debug("Save Rule");
		Rule dbRule = null;
		try {

			if(deleteIds.size() != 0) {
				routerPoolsDAO.deleteRuleSets(deleteIds);
			}

			Set<RuleSet> ruleSetList = ruleObj.getRuleSet();

			ruleObj.setRuleSet(null);

			dbRule = routerPoolsDAO.saveRule(ruleObj);
			if(dbRule != null) {
				String[] transactionDetails = new String[] {"Add Rule", "SUCCESS", "Rule Name -"+ dbRule.getRuleName() + " Rule Order -"+ dbRule.getRuleOrder() + " Rule Id -"+dbRule.getId()};
				createTransactionHistory(transactionDetails);
			}

			if(!ruleSetList.isEmpty()) {
		    	for(RuleSet ruleSet: ruleSetList) {
		    		if(null != ruleSet){
		    			if((ruleSet.getPrivateIP()=="")){
		    				ruleSet.setPrivateIP(null);
		    			}
		    			if(ruleSet.getPublicIP()==""){
		    				ruleSet.setPublicIP(null);
		    			}
		    			ruleSet.setRule(dbRule);
		    			routerPoolsDAO.saveRuleSet(ruleSet);
		    		}
		    	}
			}
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return dbRule;
	}


	@Transactional
	@Override
	public void deleteRules(List<Integer> deleteIds) {
		logger.debug("Delete Rule");
		try {
			routerPoolsDAO.deleteRuleSetsForRules(deleteIds);
			List<Rule> existingRules = routerPoolsDAO.getRules(deleteIds);
			if(existingRules != null && !existingRules.isEmpty()) {
				StringBuilder builder = new StringBuilder();
				for(Rule rule : existingRules) {
					builder.append("Rule Name -"+ rule.getRuleName() + " Rule Order -"+ rule.getRuleOrder() + " Rule Id -"+rule.getId());
					builder.append("\n");
				}
				String[] transactionDetails = new String[] {"Delete Rule(s)", "SUCCESS", builder.toString()};
				createTransactionHistory(transactionDetails);
			}
			routerPoolsDAO.deleteRules(deleteIds);

			Integer cloudConfigID = routerPoolsDAO
					.getCloudConfigIdByStatus(MODIFIED);
			List<Rule> rules = routerPoolsDAO.getRulesList(cloudConfigID);

			// Order the rule based on RULE_ORDER column
			TreeSet<Rule> treeRuleSet = new TreeSet<Rule>(new Rule());
			List<Rule> modifiedRulesList = new ArrayList<Rule>();
			treeRuleSet.addAll(rules);
			int ruleOrder = 1;
			for (Rule rule : treeRuleSet) {
				rule.setRuleOrder(ruleOrder);
				modifiedRulesList.add(rule);
				ruleOrder++;
			}
			routerPoolsDAO.updateOrderForRuleList(modifiedRulesList);

		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<PoolPriorityList> getAvailablePoolPriorityList(int cloudConfigID)
			throws ServiceException {
		logger.debug("Get Available Pool Priority List");

		List<PoolPriorityList> list = routerPoolsDAO
				.getAvailablePoolPriorityList(cloudConfigID);

		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public Integer getCloudConfigIdByStatus(String status) {
		logger.debug("Get Cloud Config ID for given status");

		Integer cloudConfigID = routerPoolsDAO.getCloudConfigIdByStatus(status);

		return cloudConfigID;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Location> getLocations() throws ServiceException {
		logger.debug("Get Locations");

		List<Location> list = routerPoolsDAO.getLocations();
		return list;
	}

	@Transactional
	@Override
	public void deleteRouterPoolMap(int deleteID) {
		logger.debug("delete RouterPoolMap for given ID");
		try {
			routerPoolsDAO.deleteRouterPoolMap(deleteID);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<PoolPriorityMap> getPoolPriorityMapList(String status)
			throws ServiceException {
		logger.debug("get PoolPriorityMap with given status");
		List<PoolPriorityMap> list = routerPoolsDAO
				.getPoolPriorityMapList(status);
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public int getModifiedPoolID(int poolID, int cloudConfigIDForModified)
			throws ServiceException {
		logger.debug("get PoolID with given Active PoolID");
		int poolId = routerPoolsDAO.getModifiedPoolID(poolID,
				cloudConfigIDForModified);
		return poolId;
	}

	@Transactional(readOnly = true)
	@Override
	public int getModifiedPriorityListID(int priorityListID,
			int cloudConfigIDForModified) throws ServiceException {
		logger.debug("get PoolPriorityID with given Active PoolPriorityID");
		int priorityID = routerPoolsDAO.getModifiedPriorityListID(
				priorityListID, cloudConfigIDForModified);
		return priorityID;
	}

	@Transactional
	@Override
	public Location saveLocation(Location modifiedLocation) {
		logger.debug("Save Location");
		Location location = routerPoolsDAO.getLocationById(modifiedLocation.getLocationID());
		String[] transactionDetails = null;
		if(location != null && !location.getLocationTag().equalsIgnoreCase(modifiedLocation.getLocationTag())) {
			transactionDetails = new String[] {"Update Location","SUCCESS", "location tag -"+ location.getLocationTag() + " location id -"+location.getLocationID()};
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(0,
    				"LocationTag", location.getLocationTag(), modifiedLocation.getLocationTag());
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeValueMappingVidyoValueName(0,
    				"LocationTag", location.getLocationTag(), modifiedLocation.getLocationTag());

    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(0,
    				"LocationTag", location.getLocationTag(), modifiedLocation.getLocationTag());
    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeValueMappingVidyoValueName(0,
    				"LocationTag", location.getLocationTag(), modifiedLocation.getLocationTag());
			location.setLocationTag(modifiedLocation.getLocationTag());
			location = routerPoolsDAO.saveLocation(location);
		} else {
			location = routerPoolsDAO.saveLocation(modifiedLocation);
			transactionDetails = new String[] {"Add Location","SUCCESS", "location tag -"+ location.getLocationTag() + " location id -"+location.getLocationID()};
		}
		createTransactionHistory(transactionDetails);
		return location;
	}

	@Transactional(readOnly = true)
	@Override
	public List<RouterPoolMap> getRouterPoolMapList(int cloudConfigIDForActive) {
		logger.debug("Get RouterPoolMap records for given cloud config id");
		List<RouterPoolMap> list = routerPoolsDAO
				.getRouterPoolMapList(cloudConfigIDForActive);
		return list;
	}

	@Transactional
	@Override
	public RouterPoolMap saveRouterPoolMap(RouterPoolMap routerPoolMap) {
		logger.debug("Save RouterPoolMap");
		RouterPoolMap map = null;
		try {
			map = routerPoolsDAO.saveRouterPoolMap(routerPoolMap);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return map;
	}

	@Transactional(readOnly = true)
	@Override
	public int getMaxCloudConfigVersion() throws ServiceException {
		logger.debug("Get Max Cloud Config Version");
		int version = routerPoolsDAO.getMaxCloudConfigVersion();
		return version;
	}

	@Transactional
	@Override
	public CloudConfig saveCloudConfig(CloudConfig cloudConfig) {
		logger.debug("Save CloudConfig");
		CloudConfig cloudConfigObj = null;
		try {
			cloudConfigObj = routerPoolsDAO.saveCloudConfig(cloudConfig);
			serviceDao.clearNetworkConfig();
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return cloudConfigObj;
	}

	@Transactional(readOnly = true)
	@Override
	public String getLocationTagByID(int locationID) throws ServiceException {
		logger.debug("Get LocationTag from locationID");
		String locationTag = routerPoolsDAO.getLocationTagByID(locationID);
		return locationTag;
	}

	@Transactional(readOnly = true)
	@Override
	public Integer getCloudConfigVersionByStatus(String status)
			throws ServiceException {
		logger.debug("Get CloudConfigVersion from Status");
		Integer cloudConfigVersion = routerPoolsDAO
				.getCloudConfigVersionByStatus(status);
		return cloudConfigVersion;
	}

	@Transactional
	@Override
	public boolean updateStatusByCloudConfigID(int cloudConfigID, String status) {
		logger.debug("update Status for given CloudConfigID");
		try {
			Integer updated = routerPoolsDAO.updateStatusByCloudConfigID(
					cloudConfigID, status);
			serviceDao.clearNetworkConfig();
			if (updated != 1) {
				return false;
			}
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return true;
	}

	@Transactional (rollbackFor = { Exception.class })
	@Override
	public boolean makeRouterPoolsConfigActive() {
		logger.debug("Activate reouter pool configuration");
		boolean success = false;
		int status =0;
		int updated = 0;


			Integer cloudConfigIDForActive = routerPoolsDAO.getCloudConfigIdByStatus("ACTIVE");

			Integer cloudConfigIDForModified = routerPoolsDAO.getCloudConfigIdByStatus("MODIFIED");

		try {

			if(null != cloudConfigIDForActive && null != cloudConfigIDForModified) {

				//update cloud config record with status ACTIVE as status INACTIVE
				updated =  routerPoolsDAO.updateStatusByCloudConfigID(cloudConfigIDForActive, "INACTIVE");
				if (updated != 1) {
					throw new Exception("Error int inactive cloud config id update");
				}
				//update cloud config record with status MODIFIED as status ACTIVE
				logger.warn("Updating the MODIFIED version  as ACTIVE");
				updated = routerPoolsDAO.updateStatusByCloudConfigID(cloudConfigIDForModified, "ACTIVE");
				// call system service to clear cloud config cache
				if (updated != 1) {
					throw new Exception("Error int inactive cloud config id update");
				}
				//removing data from other tables
				List<CloudConfig> ccList = routerPoolsDAO.findCloudConfigByStatus("INACTIVE");
				if (!ccList.isEmpty()) {
					for (CloudConfig config : ccList) {
						int configId = config.getId();
						if (logger.isDebugEnabled()) {
							logger.debug("deleting " + configId);
						}
						List<Integer> roolIds = getRuleIds(routerPoolsDAO.getRulesList(configId));
						if (!roolIds.isEmpty()) {
							routerPoolsDAO.deleteRuleSetsForRules(roolIds);
							routerPoolsDAO.deleteRules(roolIds);
						}
						List<Integer> pListIds = getPoolPriorityListIds(routerPoolsDAO.getAllPriorityLists(configId));
						if (!pListIds.isEmpty()) {
							routerPoolsDAO.deletePoolPriorityMapByPriorityListIds(pListIds);
							routerPoolsDAO.deletePoolPriorityList(pListIds);
						}
						List<Integer> poolIds = getPoolIds(routerPoolsDAO.findAllPools(configId));
						if (!poolIds.isEmpty()) {
							routerPoolsDAO.deleteConnectionsForPools(poolIds, configId);
							routerPoolsDAO.deletePoolPriorityMapByPoolIds(poolIds, configId);
							routerPoolsDAO.deleteRouterPoolMapsForPools(poolIds, configId);
							routerPoolsDAO.deletePools(poolIds, configId);

						}
						// as per the new rule, modified only need to be removed
						// or hard deleted. if it is inactive keep it in the
						// master table...refer vptl-3664

						if (logger.isDebugEnabled()) {
							logger.debug("deletion process completed with status " + status);
						}
					}
				} else {
					logger.error("No  cloud configuration found for the status INACTIVE");
				}
				serviceDao.clearNetworkConfig();
				NetworkConfigType networkConfigType = componentService.getActiveNetworkConfig();
				final StringWriter out = new StringWriter();
				com.vidyo.parser.xsd.networkconfig.ObjectFactory objectFactory = new com.vidyo.parser.xsd.networkconfig.ObjectFactory();
				JAXBElement<NetworkConfigType> je = objectFactory
						.createNetworkConfig(networkConfigType);
				networkJaxb2Marshaller.marshal(je, new StreamResult(out));
				String cloudConfigXml = out.toString();
				// Save the xml in xml data column
				routerPoolsDAO.updateConfigXMLByCloudConfigID(cloudConfigIDForModified, cloudConfigXml);
				success = true;
			}
		} catch (Exception dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}

		String[] transactionDetails = new String[] {"Activate Router Pools", success ? "SUCCESS" : "FAILURE", "Cloud Config Id -"+ cloudConfigIDForModified};
		createTransactionHistory(transactionDetails);

		return success;
	}

	@Transactional
	@Override
	public RuleSet saveRuleSet(RuleSet ruleSet) {
		logger.debug("save RuleSet");
		RuleSet RuleSet = null;
		try {
			RuleSet = routerPoolsDAO.saveRuleSet(ruleSet);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return RuleSet;
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isDeletePoolsAllowed(List<Integer> deleteIDs, String statusString) {
		logger.debug("Validate pool for deletion");
		Integer ruleCount = null;
		try {
			int cloudConfigID = routerPoolsDAO.getCloudConfigIdByStatus(statusString);
			ruleCount = routerPoolsDAO
					.getRuleCountAssocitedWithPools(deleteIDs, cloudConfigID);
			if (ruleCount > 0) {
				return false;
			} else {
				List<Integer> poolIdNotAllowedForDelete = routerPoolsDAO
						.getPoolIdsNotAllowedForDelete();
				for (Integer deleteID : deleteIDs) {
					if (poolIdNotAllowedForDelete.contains(deleteID)) {
						return false;
					}
				}
			}
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return true;
	}

	@Transactional
	@Override
	public void deleteLocations(List<Integer> deleteIds) {
		logger.debug("Delete locations");
		try {
			for(int locationId : deleteIds) {
				Location location = null;
				try {
					location = serviceService.getLocation(locationId);
				} catch (Exception e) {
					logger.error("Location Id" + locationId + " not found in the system");
				}
				serviceService.deleteLocation(locationId);
				if(location != null) {
					String[] transactionDetails = new String[] {"Delete Location","SUCCESS", "location tag -"+ location.getLocationTag() + " location id -"+location.getLocationID()};
					createTransactionHistory(transactionDetails);
			}
			}
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isDeleteLocationAllowed(List<Integer> deleteIds) {
		logger.debug("Validate locations for deletion");
		Integer ruleCount = null;
		try {
			ruleCount = routerPoolsDAO
					.getRuleCountAssocitedWithLocations(deleteIds);
			if (ruleCount > 0) {
				return false;
			}
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return true;
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isDeletePoolPriorityListAllowed(List<Integer> deleteIds) {
		logger.debug("Validate PoolPriorityLists for deletion");
		Integer ruleCount = null;
		try {
			ruleCount = routerPoolsDAO
					.getRuleCountAssocitedWithPriorityLists(deleteIds);
			if (ruleCount > 0) {
				return false;
			}
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return true;
	}

	@Transactional
	@Override
	public void deleteConnectionsForPools(List<Integer> deleteIds, String statusString)
			throws ServiceException {
		logger.debug("Delete all connections for given pools");
		try {
			List<CloudConfig> ccList = routerPoolsDAO
					.findCloudConfigByStatus(statusString);
			int configId= ccList.get(0).getId();
			routerPoolsDAO.deleteConnectionsForPools(deleteIds, configId);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
	}

	@Transactional
	@Override
	public List<Rule> updateOrderForRuleList(int currentOrder, int newOrder) {
		logger.debug("update Order For RuleList");
		List<Rule> updatedRulesList = new ArrayList<Rule>();
		try {
			int low=0, high=0;

			if (newOrder < currentOrder) {
				low=newOrder;
				high=currentOrder;
			} else {
				high=newOrder;
				low=currentOrder;
			}
			List<Rule> rules = routerPoolsDAO.getRulesByOrder(low, high);

			for (Rule rule:rules){
				if (rule.getRuleOrder() == currentOrder){
					rule.setRuleOrder(newOrder);
				} else {
					if (newOrder < currentOrder) {
						rule.setRuleOrder(rule.getRuleOrder() + 1);
					} else {
						rule.setRuleOrder(rule.getRuleOrder() - 1);
					}
				}
			}

			updatedRulesList = routerPoolsDAO
					.updateOrderForRuleList(rules);


		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return updatedRulesList;
	}

	@Transactional
	@Override
	public void deleteRuleSets(List<Integer> deleteIDs) {
		logger.debug("Delete ruleSets by ids");
		try {
			routerPoolsDAO.deleteRuleSets(deleteIDs);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}

	@Transactional
	@Override
	public void deletePoolPriorityMapByPoolIds(List<Integer> deleteIDs, String statusString) {
		logger.debug("Delete PoolPriorityMap by Pool Ids");
		try {
			List<CloudConfig> ccList = routerPoolsDAO
					.findCloudConfigByStatus(statusString);
			int configId= ccList.get(0).getId();
			routerPoolsDAO.deletePoolPriorityMapByPoolIds(deleteIDs, configId);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}

	@Transactional
	@Override
	public void deletePoolPriorityMapByPriorityListIds(List<Integer> deleteIDs) {
		logger.debug("Delete PoolPriorityMap by Priority List Ids");
		try {
			routerPoolsDAO.deletePoolPriorityMapByPriorityListIds(deleteIDs);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}

	@Transactional
	@Override
	public void deleteRouterPoolMapsForPools(List<Integer> deleteIDs, String statusString) {
		logger.debug("Delete RouterPoolMaps by Pool Ids");
		try {
			List<CloudConfig> ccList = routerPoolsDAO
					.findCloudConfigByStatus(statusString);
			int configId= ccList.get(0).getId();
			routerPoolsDAO.deleteRouterPoolMapsForPools(deleteIDs, configId);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}

	}

	@Transactional(readOnly = true)
	@Override
	public boolean isRouterPoolPresent() {

		return routerPoolsDAO.isRouterPoolPresent();
	}

	@Override
	public List<CloudConfig> findCloudConfigByStatus(String status) {
		return routerPoolsDAO.findCloudConfigByStatus(status);
	}

	@Transactional
	@Override
	public int discardModifiedCoudConfig(String statusString) throws GeneralException {
		int status = 0;

			List<CloudConfig> ccList = routerPoolsDAO
					.findCloudConfigByStatus(statusString);
			if (ccList.isEmpty()) {
				status = -1;
			} else {

				for (CloudConfig config : ccList) {
					int configId=config.getId();
					if (logger.isDebugEnabled()) {
						logger.debug("deleting " +configId);
					}
					List<Integer> roolIds = getRuleIds(routerPoolsDAO.getRulesList(configId));
					if(!roolIds.isEmpty()){
						routerPoolsDAO.deleteRuleSetsForRules(roolIds);
						routerPoolsDAO.deleteRules(roolIds);
					}
					List<Integer> pListIds=getPoolPriorityListIds(routerPoolsDAO.getAllPriorityLists(configId));
					if(!pListIds.isEmpty()){
						routerPoolsDAO.deletePoolPriorityMapByPriorityListIds(pListIds);
						routerPoolsDAO.deletePoolPriorityList(pListIds);
					}
					List<Integer> poolIds = getPoolIds(routerPoolsDAO.findAllPools(configId));
					if(!poolIds.isEmpty()){
						routerPoolsDAO.deleteConnectionsForPools(poolIds, configId);
						routerPoolsDAO.deletePoolPriorityMapByPoolIds(poolIds, configId);
						routerPoolsDAO.deleteRouterPoolMapsForPools(poolIds, configId);
						routerPoolsDAO.deletePools(poolIds, configId);

					}
					//as per the new rule, modified only need to be removed or hard deleted. if it is inactive keep it in the master table...refer vptl-3664
					if(statusString.equalsIgnoreCase(MODIFIED)){
						status=routerPoolsDAO.deletCloudConfigById(configId);
						String[] transactionDetails = new String[] {"Discard Modified Cloud Config","SUCCESS", "CloudConfig Id -" + configId};
						createTransactionHistory(transactionDetails);
					}
					if (logger.isDebugEnabled()) {
						logger.debug("deletion process completed with status " +status);
					}
				}
			}
		return status;
	}

	private List<Integer>  getPoolIds(List<Pool> pools) {
		List<Integer> objList=new ArrayList<Integer>();

		for(Pool obj:pools){
			objList.add(obj.getPoolKey().getId());

		}
		return objList;
	}
	private List<Integer> getRuleIds(List<Rule> rules) {
		List<Integer> objList=new ArrayList<Integer>();

		for(Rule obj:rules){
			objList.add(obj.getId());

		}
		return objList;
	}

	private List<Integer> getPoolPriorityListIds(List<PoolPriorityList> setValues) {
		List<Integer> objList=new ArrayList<Integer>();

		for(PoolPriorityList obj:setValues){
			objList.add(obj.getId());

		}
		return objList;
	}

	@Transactional(rollbackFor = { Exception.class })
	public CloudConfig makeCopyOfActiveRecords() throws ServiceException {

		Integer cloudConfigIDForActive = routerPoolsDAO.getCloudConfigIdByStatus("ACTIVE");
		Integer modifiedId = routerPoolsDAO.getCloudConfigIdByStatus("MODIFIED");

		CloudConfig modifiedCloudConfig = null;
		if(modifiedId == null) {
			modifiedCloudConfig = new CloudConfig();
			modifiedCloudConfig.setStatus("MODIFIED");
			int version = routerPoolsDAO.getMaxCloudConfigVersion();
			modifiedCloudConfig.setVersion(version + 1);
			modifiedCloudConfig = routerPoolsDAO.saveCloudConfig(modifiedCloudConfig);
			String[] transactionDetails = new String[] {"Create Modified Router Pools Version","SUCCESS", "CloudConfig Id -" + modifiedCloudConfig.getId()};
			createTransactionHistory(transactionDetails);
		} else {
			// Modified already preset, just return the cloud object
			modifiedCloudConfig = new CloudConfig();
			modifiedCloudConfig.setId(modifiedId);
			modifiedCloudConfig.setStatus("MODIFIED");
			modifiedCloudConfig.setVersion(getCloudConfigVersionByStatus("MODIFIED"));
			return modifiedCloudConfig;
		}

		// 1. copy all modified pools
		List<Pool> pools = routerPoolsDAO.findAllPools(cloudConfigIDForActive);
		Map<String, Pool> modifiedPoolToIdMap = new HashMap<String, Pool>();
		for(Pool pool: pools){
			Pool modifiedPool = new Pool();
			modifiedPool.setPoolKey(pool.getPoolKey());
			modifiedPool.setName(pool.getName());
			modifiedPool.setX(pool.getX());
			modifiedPool.setY(pool.getY());
			modifiedPool.setCloudConfig(modifiedCloudConfig);
			modifiedPool = routerPoolsDAO.savePool(modifiedPool);
			modifiedPoolToIdMap.put(pool.getPoolKey().toString(), modifiedPool);
		}

		//1.1 copy all connections
		List<PoolToPool> connectionList = getConnectionList("ACTIVE");

		for(PoolToPool connection : connectionList){
			PoolToPool poolToPoolConn = new PoolToPool();
			int pool1 = connection.getPoolToPoolPK().getPool1();
			int pool2 = connection.getPoolToPoolPK().getPool2();
			int newPool1 = 0;
			int newPool2 = 0;
			try {
				newPool1 = pool1;  //routerPoolsDAO.getModifiedPoolID(pool1, modifiedCloudConfig.getId());
				newPool2 = pool2;  //routerPoolsDAO.getModifiedPoolID(pool2, modifiedCloudConfig.getId());
			} catch (Exception e) {
				logger.error("Error while getting modified pool ids ",e.getMessage());
			}
			if(pool1 > 0 && pool2 > 0) {
				poolToPoolConn.setDirection(connection.getDirection());
				poolToPoolConn.setWeight(connection.getWeight());
				poolToPoolConn.getPoolToPoolPK().setPool1(newPool1);
				poolToPoolConn.getPoolToPoolPK().setPool2(newPool2);
				poolToPoolConn.getPoolToPoolPK().setCloudConfigId(modifiedCloudConfig.getId());
				routerPoolsDAO.saveConnection(poolToPoolConn);
			}
		}

		//2. copy all modified pool priority lists
        List<PoolPriorityList> poolPriorityList = routerPoolsDAO.getAllPriorityLists(cloudConfigIDForActive);
        Map<Integer, PoolPriorityList> modifiedPriorityListToIdMap = new HashMap<Integer, PoolPriorityList>();
        for(PoolPriorityList priorityList: poolPriorityList){
        	PoolPriorityList modifiedPoolPriorityList = new PoolPriorityList();
        	modifiedPoolPriorityList.setPriorityListName(priorityList.getPriorityListName());
        	modifiedPoolPriorityList.setCloudConfig(modifiedCloudConfig);
        	routerPoolsDAO.savePoolPriorityList(modifiedPoolPriorityList);
        	modifiedPriorityListToIdMap.put(modifiedPoolPriorityList.getId(), modifiedPoolPriorityList);
		}

		// 3. copy all modified pool priority maps
        List<PoolPriorityMap> mapList = routerPoolsDAO.getPoolPriorityMapList("ACTIVE");

		for(PoolPriorityMap poolPriorityMap : mapList){
			PoolPriorityMap modifiedPoolPriorityMap = new PoolPriorityMap();
			PoolPK poolID = poolPriorityMap.getPool().getPoolKey();
			int priorityListID = poolPriorityMap.getPoolPriorityList().getId();
			PoolPK newPoolID = new PoolPK(poolID.getId(),modifiedCloudConfig.getId());
			//newPoolID.setCloudConfigId();
			//routerPoolsDAO.getModifiedPoolID(poolID, modifiedCloudConfig.getId());
			int newPriorityListID = routerPoolsDAO.getModifiedPriorityListID(priorityListID, modifiedCloudConfig.getId());
			if(modifiedPoolToIdMap.get(newPoolID.toString()) != null && modifiedPriorityListToIdMap.get(newPriorityListID) != null) {
				modifiedPoolPriorityMap.setOrder(poolPriorityMap.getOrder());
				modifiedPoolPriorityMap.setPool(modifiedPoolToIdMap.get(newPoolID.toString()));
				modifiedPoolPriorityMap.setPoolPriorityList(modifiedPriorityListToIdMap.get(newPriorityListID));
				routerPoolsDAO.savePoolPriorityMap(modifiedPoolPriorityMap);
			}
		}

        // 4. copy all rules
        List<Rule> ruleList = routerPoolsDAO.getRulesList(cloudConfigIDForActive);
        for(Rule rule: ruleList){
        	Rule modifiedRule = new Rule();
        	modifiedRule.setCloudConfig(modifiedCloudConfig);
        	int priorityListID = rule.getPoolPriorityList().getId();
        	int newPriorityListID = routerPoolsDAO.getModifiedPriorityListID(priorityListID, modifiedCloudConfig.getId());
        	if(modifiedPriorityListToIdMap.get(newPriorityListID) != null) {
        		modifiedRule.setRuleName(rule.getRuleName());
        		modifiedRule.setRuleOrder(rule.getRuleOrder());
            	modifiedRule.setPoolPriorityList(modifiedPriorityListToIdMap.get(newPriorityListID));
            	modifiedRule = routerPoolsDAO.saveRule(modifiedRule);
            	Set<RuleSet> ruleSets = rule.getRuleSet();
            	for(RuleSet ruleSet: ruleSets) {
            		RuleSet modifiedRuleSet = new RuleSet();
            		modifiedRuleSet.setEndpointID(ruleSet.getEndpointID());
            		modifiedRuleSet.setLocationTagID(ruleSet.getLocationTagID());
            		modifiedRuleSet.setPrivateIP(ruleSet.getPrivateIP());
            		modifiedRuleSet.setPublicIP(ruleSet.getPublicIP());
            		modifiedRuleSet.setPrivateIpCIDR(ruleSet.getPrivateIpCIDR());
            		modifiedRuleSet.setPublicIpCIDR(ruleSet.getPublicIpCIDR());
            		modifiedRuleSet.setRule(modifiedRule);
            		modifiedRuleSet.setRuleSetOrder(ruleSet.getRuleSetOrder());
            		routerPoolsDAO.saveRuleSet(modifiedRuleSet);
            	}
        	}
		}

        // 5. copy all modified pool priority maps
        List<RouterPoolMap> routerPoolMapList = routerPoolsDAO.getRouterPoolMapList(cloudConfigIDForActive);

		for(RouterPoolMap routerPoolMap : routerPoolMapList){
			RouterPoolMap modifiedRouterPoolMap = new RouterPoolMap();

			//modifiedRouterPoolMap.setPool(modifiedPoolToIdMap.get(routerPoolMap.getPool().getPoolKey()));
			//modifiedRouterPoolMap.getPool().setCloudConfig(modifiedCloudConfig);
			PoolPK poolID = routerPoolMap.getPool().getPoolKey();
			PoolPK newPoolID = new PoolPK(poolID.getId(),modifiedCloudConfig.getId());
			if(modifiedPoolToIdMap.get(newPoolID.toString()) != null) {
				modifiedRouterPoolMap.setPool(modifiedPoolToIdMap.get(newPoolID.toString()));
				modifiedRouterPoolMap.setVidyoRouter(routerPoolMap.getVidyoRouter());
				routerPoolsDAO.saveRouterPoolMap(modifiedRouterPoolMap);
			}
		}
		return modifiedCloudConfig;
	}

	public List<Location> getLocationByTag(String locationTag) {
		return routerPoolsDAO.getLocationByTag(locationTag);
	}

	@Override
	public List<Pool> getAssignedPools(Integer priorityListId, Integer cloudConfigId) {
		List<Pool> pools = null;
		try {
			pools = routerPoolsDAO.getAssignedPools(priorityListId, cloudConfigId);
		} catch (DataAccessException e) {
			logger.error("Exception while retrieving unassigned router pools ", e);
		}
		return pools;
	}

	@Override
	public List<Pool> getUnassignedPools(Integer priorityListId, Integer cloudConfigId) {
		List<Pool> pools = null;
		try {
			pools = routerPoolsDAO.getUnassignedPools(priorityListId, cloudConfigId);
		} catch (DataAccessException e) {
			logger.error("Exception while retrieving unassigned router pools ", e);
		}
		return pools;
	}

	private String getConfigValue(int tenantID, String configurationName){
		Configuration config = systemDao.getConfiguration(tenantID, configurationName);
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return "";
		}
		return config.getConfigurationValue();

	}
}