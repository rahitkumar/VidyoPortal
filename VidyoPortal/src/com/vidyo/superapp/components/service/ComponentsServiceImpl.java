package com.vidyo.superapp.components.service;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.tenantservice.TenantServiceMap;
import com.vidyo.db.IServiceDao;
import com.vidyo.db.idp.ITenantIdpAttributesMappingDao;
import com.vidyo.db.ldap.ITenantLdapAttributesMappingDao;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.framework.service.BaseServiceImpl;
import com.vidyo.framework.service.ServiceException;
import com.vidyo.parser.xsd.networkconfig.BandwidthMapElementType;
import com.vidyo.parser.xsd.networkconfig.BandwidthMapType;
import com.vidyo.parser.xsd.networkconfig.BasicAndType;
import com.vidyo.parser.xsd.networkconfig.BasicRuleSet;
import com.vidyo.parser.xsd.networkconfig.BasicRuleType;
import com.vidyo.parser.xsd.networkconfig.Candidates;
import com.vidyo.parser.xsd.networkconfig.CategoriesType;
import com.vidyo.parser.xsd.networkconfig.EqualToType;
import com.vidyo.parser.xsd.networkconfig.GroupListType;
import com.vidyo.parser.xsd.networkconfig.GroupType;
import com.vidyo.parser.xsd.networkconfig.IPV4SubnetType;
import com.vidyo.parser.xsd.networkconfig.LocationType;
import com.vidyo.parser.xsd.networkconfig.NetworkConfigType;
import com.vidyo.parser.xsd.networkconfig.NetworkElement;
import com.vidyo.parser.xsd.networkconfig.NetworkElementsType;
import com.vidyo.parser.xsd.networkconfig.OrType;
import com.vidyo.parser.xsd.networkconfig.PrioritizedGroupListsType;
import com.vidyo.parser.xsd.networkconfig.RuleSetType;
import com.vidyo.parser.xsd.networkconfig.SCIPListenAddressType;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.system.SystemService;
import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.bo.ComponentType;
import com.vidyo.superapp.components.bo.RecorderEndpoints;
import com.vidyo.superapp.components.bo.RouterMediaAddrMap;
import com.vidyo.superapp.components.bo.VidyoGateway;
import com.vidyo.superapp.components.bo.VidyoManager;
import com.vidyo.superapp.components.bo.VidyoRecorder;
import com.vidyo.superapp.components.bo.VidyoReplay;
import com.vidyo.superapp.components.bo.VidyoRouter;
import com.vidyo.superapp.components.db.ComponentsDAO;
import com.vidyo.superapp.components.service.gateway.GatewayCompService;
import com.vidyo.superapp.routerpools.bo.CloudConfig;
import com.vidyo.superapp.routerpools.bo.Pool;
import com.vidyo.superapp.routerpools.bo.PoolPriorityList;
import com.vidyo.superapp.routerpools.bo.PoolPriorityMap;
import com.vidyo.superapp.routerpools.bo.PoolToPool;
import com.vidyo.superapp.routerpools.bo.RouterPoolMap;
import com.vidyo.superapp.routerpools.bo.Rule;
import com.vidyo.superapp.routerpools.bo.RuleSet;
import com.vidyo.superapp.routerpools.service.RouterPoolsService;
import com.vidyo.ws.configuration.NetworkElementType;

@Service("ComponentService")
public class ComponentsServiceImpl extends BaseServiceImpl implements ComponentsService {

	public static final String PASSWORD_UNCHANGED = "PASSWORD_UNCHANGED";

	@Autowired
	ComponentsDAO componentsDAO;

	@Autowired
	GatewayCompService gatewayCompService;

	@Autowired
	private ITenantService tenantService;

	@Autowired
	private IServiceService serviceService;

	@Autowired
	private RouterPoolsService routerPoolsService;

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private IServiceDao serviceDao;
	
	@Autowired
	private Jaxb2Marshaller networkJaxb2Marshaller;
	
	@Autowired
    private ITenantLdapAttributesMappingDao tenantLdapAttributesMappingDao;
	
	@Autowired
    private ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao;
	
	protected static final Logger logger = LoggerFactory
			.getLogger(ComponentsServiceImpl.class);

	@Transactional(readOnly = true)
	@Override
	public List<Component> findAllComponents(String name, String type,
			String status, int isAlarm) throws ServiceException {
		logger.debug("Get All Components");

		if (type != null && type.equalsIgnoreCase("VidyoProxy")){
			type = "VidyoRouter";
		}
		if(StringUtils.trimToEmpty(name).equals("%")) {
			name = "\\%".concat(name);
		}
		if(StringUtils.trimToEmpty(type).equals("%")) {
			type = "\\%".concat(type);
		}
		if(StringUtils.trimToEmpty(status).equals("%")) {
			status = "\\%".concat(status);
		}
		List<Component> list = componentsDAO.findAllComponents(name, type,
				status, isAlarm);
		// Remove the Replay from the list as it'll be part of recorder
		 /*for (Iterator<Component> it = list.iterator(); it.hasNext();) {
	        if (it.next().getCompType().getName().equalsIgnoreCase("VidyoReplay")) {
	        	it.remove();
	        }
		 }*/
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ComponentType> findAllComponentsTypes() throws ServiceException {
		logger.debug("Get All Component Types");

		List<ComponentType> list = componentsDAO.findAllComponentsTypes();
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<VidyoManager> findManagerByCompID(int compID)
			throws ServiceException {
		logger.debug("Get Vidyo Manager for given component");

		List<VidyoManager> list = new ArrayList<VidyoManager>();
		VidyoManager vidyoManger = componentsDAO.findManagerByCompID(compID);
		list.add(vidyoManger);
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<VidyoRouter> findRouterByCompID(int compID)
			throws ServiceException {
		logger.debug("Get Vidyo Router for given component");

		List<VidyoRouter> list = new ArrayList<VidyoRouter>();
		VidyoRouter vidyoRouter = componentsDAO.findRouterByCompID(compID);
		vidyoRouter.setRouterPoolPresent(routerPoolsService.isRouterPoolPresent());
		list.add(vidyoRouter);
		return list;
	}

	@Transactional
	@Override
	public void deleteSelectedComponents(List<Integer> componentIDs)
			throws ServiceException {
		logger.debug("Delete components");

		componentsDAO.deleteSelectedComponents(componentIDs);

	}

	@Transactional(readOnly = true)
	@Override
	public Component findComponentByID(int componentID) throws ServiceException {
		logger.debug("Find component, add it to list and return list");

		Component component = componentsDAO.findComponentByID(componentID);

		return component;
	}

	@Transactional
	@Override
	public VidyoManager saveVidyoManager(VidyoManager vidyoManager)
			throws ServiceException {
		logger.debug("Update Vidyo Manger");

		VidyoManager vidyoManagerObj = componentsDAO
				.saveVidyoManager(vidyoManager);
		vidyoManagerObj.setComponents(vidyoManager.getComponents());
		return vidyoManagerObj;
	}

	@Transactional
	@Override
	public VidyoRouter saveVidyoRouter(VidyoRouter vidyoRouter)
			throws ServiceException {
		logger.debug("Update Vidyo Router");

		VidyoRouter vidyoRouterObj = componentsDAO.saveVidyoRouter(vidyoRouter);
		vidyoRouterObj.setComponents(vidyoRouterObj.getComponents());

		return vidyoRouterObj;
	}

	@Transactional
	@Override
	public Component saveComponent(Component component) throws ServiceException {
		logger.debug("Update Component");

		Component componentObj = componentsDAO.saveComponent(component);

		return componentObj;
	}

	@Transactional
	@Override
	public boolean saveComponentStatus(int id, String status)
			throws ServiceException {
		logger.debug("Update Component");

		boolean updated = componentsDAO.saveComponentStatus(id, status);

		return updated;
	}

	@Transactional(readOnly = true)
	@Override
	public Component findByCompIDAndCompType(String compId, String CompType)
			throws ServiceException {
		logger.debug("Find component by compID, compType and return list");

		Component comp = componentsDAO
				.findByCompIDAndCompType(compId, CompType);

		return comp;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ComponentType> findByComponentTypeByName(String compTypeName)
			throws ServiceException {
		List<ComponentType> list = componentsDAO
				.findByComponentTypeName(compTypeName);
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public VidyoManager getVidyoManagerById(int vidyoMangerId)
			throws ServiceException {
		return componentsDAO.getVidyoManagerById(vidyoMangerId);
	}

	@Transactional(readOnly = true)
	@Override
	public VidyoRouter getVidyoRouterById(int vidyoRouterId)
			throws ServiceException {
		return componentsDAO.getVidyoRouterById(vidyoRouterId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean updateVidyoManager(VidyoManager updatedVidyoManager) {

		VidyoManager existingVidyoManager = componentsDAO
				.getVidyoManagerById(updatedVidyoManager.getId());
		if (existingVidyoManager == null) {
			return false;
		}

		Component existingComponent = existingVidyoManager.getComponents();
		existingComponent
				.setConfigVersion(existingComponent.getConfigVersion() + 1);

		existingComponent.setUpdated(Calendar.getInstance().getTime());

		if (existingComponent.getStatus().equals("NEW")) {
			existingComponent.setStatus("ACTIVE");
		}

		if (!StringUtils.equals(updatedVidyoManager.getComponents().getName(),
				existingComponent.getName()))
			existingComponent.setName(updatedVidyoManager.getComponents()
					.getName());

		if (!StringUtils.equals(updatedVidyoManager.getComponents()
				.getMgmtUrl(), existingComponent.getMgmtUrl()))
			existingComponent.setMgmtUrl(updatedVidyoManager.getComponents()
					.getMgmtUrl());

		final String[] excludes = { "id", "fqdn" };
		BeanUtils.copyProperties(updatedVidyoManager, existingVidyoManager,
				excludes);

		try {
			Component savedComponent = componentsDAO
					.saveComponent(existingComponent);
			existingVidyoManager.setComponents(savedComponent);
			componentsDAO.saveVidyoManager(existingVidyoManager);
			serviceDao.clearComponentsUserCache();
			// Update the TenantXServices Mapping
			List<Integer> mappedTenantIds = serviceService
					.getTenantIdsByServiceIds(Arrays
							.asList(existingVidyoManager.getComponents()
									.getId()));
			List<TenantServiceMap> tenantServiceMaps = null;
			List<Integer> tenantIds = tenantService.getAllTenantIds();
			tenantIds.removeAll(mappedTenantIds);
			if (tenantIds != null && !tenantIds.isEmpty()) {
				for (Integer id : tenantIds) {
					TenantServiceMap tenantServiceMap = new TenantServiceMap();
					tenantServiceMap.setServiceId(existingVidyoManager
							.getComponents().getId());
					tenantServiceMap.setTenantId(id);
					if (tenantServiceMaps == null) {
						tenantServiceMaps = new ArrayList<TenantServiceMap>();
					}
					tenantServiceMaps.add(tenantServiceMap);
				}
				if (tenantServiceMaps != null && !tenantServiceMaps.isEmpty()) {
					// Insert the new mapping for tenants which doesn't have the
					// compId
					serviceService.saveTenantServiceMappings(tenantServiceMaps);
				}
			}
		} catch (DataAccessException dae) {
			logger.error(
					"Update VidyoManager and its corresponding component failed",
					dae);
			throw new ServiceException(dae.getMessage());
		}
		String[] transactionDetails = new String[] {"Update VidyoManager", "SUCCESS", "Id -" + existingVidyoManager.getComponents().getCompID()};
		createTransactionHistory(transactionDetails);
		return true;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean addVidyoManager(VidyoManager addedVidyoManager) {

		Component updatedComponent = addedVidyoManager.getComponents();

		Component existingComponent = componentsDAO
				.findComponentByID(updatedComponent.getId());

		existingComponent
				.setConfigVersion(existingComponent.getConfigVersion() + 1);

		existingComponent.setUpdated(Calendar.getInstance().getTime());

		if (existingComponent.getStatus().equals("NEW")) {
			existingComponent.setStatus("ACTIVE");
		}

		if (!StringUtils.equals(updatedComponent.getName(),
				existingComponent.getName()))
			existingComponent.setName(updatedComponent.getName());

		if (!StringUtils.equals(updatedComponent.getMgmtUrl(),
				existingComponent.getMgmtUrl()))
			existingComponent.setMgmtUrl(updatedComponent.getMgmtUrl());

		try {
			Component savedComponent = componentsDAO
					.saveComponent(existingComponent);
			addedVidyoManager.setComponents(savedComponent);
			componentsDAO.saveVidyoManager(addedVidyoManager);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean updateVidyoRouter(VidyoRouter updatedVidyoRouter) {

		VidyoRouter existingVidyoRouter = componentsDAO
				.getVidyoRouterById(updatedVidyoRouter.getId());
		if (existingVidyoRouter == null) {
			return false;
		}
		List<RouterMediaAddrMap> updatedMediaAddressMapList = updatedVidyoRouter
				.getRouterMediaAddrMap();
		Component existingComponent = existingVidyoRouter.getComponents();

		existingComponent
				.setConfigVersion(existingComponent.getConfigVersion() + 1);

		existingComponent.setUpdated(Calendar.getInstance().getTime());

		if (existingComponent.getStatus().equals("NEW")) {
			existingComponent.setStatus("ACTIVE");
		}

		if (!StringUtils.equals(updatedVidyoRouter.getComponents().getName(),
				existingComponent.getName())) {
			// Update LDAP mapping
			tenantLdapAttributesMappingDao
					.updateTenantLdapAttributeMappingDefaultAttributeValue(0,
							"Proxy", existingComponent.getName(),
							updatedVidyoRouter.getComponents().getName());
			tenantLdapAttributesMappingDao
					.updateTenantLdapAttributeValueMappingVidyoValueName(0,
							"Proxy", existingComponent.getName(),
							updatedVidyoRouter.getComponents().getName());

			tenantIdpAttributesMappingDao
					.updateTenantIdpAttributeMappingDefaultAttributeValue(0,
							"Proxy", existingComponent.getName(),
							updatedVidyoRouter.getComponents().getName());
			tenantIdpAttributesMappingDao
					.updateTenantIdpAttributeValueMappingVidyoValueName(0,
							"Proxy", existingComponent.getName(),
							updatedVidyoRouter.getComponents().getName());
			existingComponent.setName(updatedVidyoRouter.getComponents()
					.getName());
		}
		try {
			List<Integer> compId = new ArrayList<Integer>();
			compId.add(existingComponent.getId());
			List<Integer> ids = new ArrayList<Integer>();
			for (RouterMediaAddrMap updatedMediaAddressMap:updatedMediaAddressMapList){
				updatedMediaAddressMap.setVidyoRouter(updatedVidyoRouter);
				if (updatedMediaAddressMap.getId() != 0)
					ids.add(updatedMediaAddressMap.getId());
			}
			//
			if (ids != null && ids.size() > 0) {
				componentsDAO.deleteMediaAddressMapNotIn(ids, updatedVidyoRouter.getId());
			} else {
				componentsDAO.deleteMediaAddressMapByCompId(compId);
			}
			componentsDAO.saveRouterMediaAddressMap(updatedMediaAddressMapList);
		} catch (DataAccessException dae) {
			logger.error("Exception while saving router", dae);
			throw new ServiceException(dae.getMessage());
		}

		final String[] excludes = { "id", "proxyEnabled", "proxyUseTls" };
		BeanUtils.copyProperties(updatedVidyoRouter, existingVidyoRouter,
				excludes);
		if (!StringUtils.equals(
				updatedVidyoRouter.getComponents().getMgmtUrl(),
				existingComponent.getMgmtUrl()))
			existingComponent.setMgmtUrl(updatedVidyoRouter.getComponents()
					.getMgmtUrl());
		componentsDAO.saveComponent(existingComponent);
		existingVidyoRouter.setComponents(existingComponent);
		existingVidyoRouter.getRouterMediaAddrMap().clear();
		componentsDAO.updateVidyoRouter(existingVidyoRouter);	

		// Delete internal router if the external router is updated
		List<Component> vms = componentsDAO
				.getComponentsByType(NetworkElementType.VidyoManager.getValue());

		for (Component comp : vms) {
			// Get the router by VM IP
			VidyoRouter router = componentsDAO.getRouterByIP(comp.getLocalIP());
			if (router != null
					&& !router.getComponents().getLocalIP()
							.equals(existingComponent.getLocalIP())) {
				// Delete the router only if the incoming router is external
				deleteInternalRouter(comp.getLocalIP());
			}
		}

		// Update of Router is success, increment the ACTIVE & MODIFIED cloud
		// config
		// version
		incrementCloudConfigVersion();
		String[] transactionDetails = new String[] {"Update VidyoRouter", "SUCCESS", "Id -" + existingVidyoRouter.getComponents().getCompID()};
		createTransactionHistory(transactionDetails);
		return true;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean addVidyoRouter(VidyoRouter addedVidyoRouter) {

		Component updatedComponent = addedVidyoRouter.getComponents();

		Component existingComponent = componentsDAO
				.findComponentByID(updatedComponent.getId());

		existingComponent
				.setConfigVersion(existingComponent.getConfigVersion() + 1);

		existingComponent.setUpdated(Calendar.getInstance().getTime());

		if (existingComponent.getStatus().equals("NEW")) {
			existingComponent.setStatus("ACTIVE");
		}

		if (!StringUtils.equals(updatedComponent.getName(),
				existingComponent.getName()))
			existingComponent.setName(updatedComponent.getName());

		if (!StringUtils.equals(updatedComponent.getMgmtUrl(),
				existingComponent.getMgmtUrl()))
			existingComponent.setMgmtUrl(updatedComponent.getMgmtUrl());

		try {
			Component savedComponent = componentsDAO
					.saveComponent(existingComponent);
			addedVidyoRouter.setComponents(savedComponent);
			componentsDAO.saveVidyoRouter(addedVidyoRouter);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Component updateComponent(Component updatedComponent) {

		Component existingComponent = componentsDAO
				.findComponentByID(updatedComponent.getId());
		Component savedComponent = null;

		if (existingComponent.getStatus().equals("NEW")) {
			existingComponent.setStatus("ACTIVE");
		}

		if (!StringUtils.equals(updatedComponent.getName(),
				existingComponent.getName()))
			existingComponent.setName(updatedComponent.getName());

		if (!StringUtils.equals(updatedComponent.getMgmtUrl(),
				existingComponent.getMgmtUrl()))
			existingComponent.setMgmtUrl(updatedComponent.getMgmtUrl());

		existingComponent
				.setConfigVersion(existingComponent.getConfigVersion() + 1);

		try {
			savedComponent = componentsDAO.saveComponent(existingComponent);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException(dae.getMessage());
		}
		return savedComponent;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Component updateComponentData(Component updatedComponent, String componentTypeData) {
		Component savedComponent = null;
		if (updatedComponent.getName() == null || (updatedComponent.getName() != null && updatedComponent.getName().length() == 0)) {
			throw new ServiceException("Invalid Component name");
		}
		try {

			Component existingComponent = componentsDAO
					.findComponentByID(updatedComponent.getId());

			if (existingComponent.getStatus().equals("NEW")) {
				existingComponent.setStatus("ACTIVE");
			}

			if (!StringUtils.equals(updatedComponent.getName(),
					existingComponent.getName()))
				existingComponent.setName(updatedComponent.getName());

			if (!StringUtils.equals(updatedComponent.getMgmtUrl(),
					existingComponent.getMgmtUrl()))
				existingComponent.setMgmtUrl(updatedComponent.getMgmtUrl());

			existingComponent
					.setConfigVersion(existingComponent.getConfigVersion() + 1);

			savedComponent = componentsDAO.saveComponent(existingComponent);
				
			if(updatedComponent.getCompType().getName().equals(NetworkElementType.VidyoGateway.getValue())) {
				VidyoGateway updatedVidyoGateway = new ObjectMapper().readValue(componentTypeData,	VidyoGateway.class);
				
				VidyoGateway vidyoGateway = gatewayCompService.getGatewayByCompId(updatedComponent.getId());

				if (null != updatedVidyoGateway.getPassword()
						&& !updatedVidyoGateway.getPassword()
								.equals(PASSWORD_UNCHANGED)) {
					vidyoGateway.setPassword(VidyoUtil.encrypt(updatedVidyoGateway
							.getPassword().trim()));
				}

				if (null != updatedVidyoGateway.getUserName()
						&& !updatedVidyoGateway.getUserName().equals(
								vidyoGateway.getUserName())) {
					vidyoGateway.setUserName(updatedVidyoGateway.getUserName());
				}
				serviceDao.clearComponentsUserCache();
				String[] transactionDetails = new String[] {"Update VidyoGateway", "SUCCESS", "Id -" + updatedComponent.getId()};
				createTransactionHistory(transactionDetails);
				vidyoGateway = componentsDAO.updateVidyoGateway(vidyoGateway);
				
			} else if(updatedComponent.getCompType().getName().equals(NetworkElementType.VidyoReplay.getValue())) {
				VidyoReplay updatedVidyoReplay = new ObjectMapper().readValue(componentTypeData,
						VidyoReplay.class);
				
				VidyoReplay vidyoReplay = componentsDAO
						.getReplayByCompId(updatedComponent.getId());

				if (vidyoReplay == null) {
					return null;
				}

				if (null != updatedVidyoReplay.getPassword()
						&& !updatedVidyoReplay.getPassword().equals(PASSWORD_UNCHANGED)) {
					vidyoReplay.setPassword(VidyoUtil.encrypt(updatedVidyoReplay
							.getPassword().trim()));
				}

				if (null != updatedVidyoReplay.getUserName()
						&& !updatedVidyoReplay.getUserName().equals(
								vidyoReplay.getUserName())) {
					vidyoReplay.setUserName(updatedVidyoReplay.getUserName());
				}
				serviceDao.clearComponentsUserCache();
				String[] transactionDetails = new String[] {"Update VidyoReplay", "SUCCESS", "Id -" + updatedComponent.getId()};
				createTransactionHistory(transactionDetails);
				vidyoReplay = componentsDAO.updateVidyoReplay(vidyoReplay);
				
			} else if(updatedComponent.getCompType().getName().equals("VidyoRecorder")) {
				VidyoRecorder updatedVidyoRecorder = new ObjectMapper().readValue(componentTypeData,
						VidyoRecorder.class);
				if(updatedVidyoRecorder != null) {
					VidyoRecorder vidyoRecorder = componentsDAO
							.getRecorderByCompId(updatedComponent.getId());

					if (null != updatedVidyoRecorder.getPassword()
							&& !updatedVidyoRecorder.getPassword().equals(
									PASSWORD_UNCHANGED)) {
						vidyoRecorder.setPassword(VidyoUtil.encrypt(updatedVidyoRecorder
								.getPassword().trim()));
					}

					if (null != updatedVidyoRecorder.getUserName()
							&& !updatedVidyoRecorder.getUserName().equals(
									vidyoRecorder.getUserName())) {
						vidyoRecorder.setUserName(updatedVidyoRecorder.getUserName());
					}
					serviceDao.clearComponentsUserCache();
					String[] transactionDetails = new String[] {"Update VidyoRecorder", "SUCCESS", "Id -" + updatedComponent.getId()};
					createTransactionHistory(transactionDetails);
					updatedVidyoRecorder = componentsDAO.updateVidyoRecorder(vidyoRecorder);			
				}
			}
		}catch (DataAccessException | IOException dae) {
			logger.error(
					"data access error while updating components by type ",
					dae.getMessage());
			throw new ServiceException(dae.getMessage());
		}
		return savedComponent;
	}
	

	@Transactional(readOnly = true)
	@Override
	public List<VidyoRouter> getAvailableRouters() {
		List<VidyoRouter> routers = null;
		try {
			routers = componentsDAO.getAvailableRouters();
		} catch (DataAccessException dae) {
			logger.error("data access error while retrieving routers ",
					dae.getMessage());
			throw new ServiceException(dae.getMessage());
		}
		return routers;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Component> getComponentsByType(String type) {
		List<Component> comps = null;
		try {
			comps = componentsDAO.getComponentsByType(type);
		} catch (DataAccessException dae) {
			logger.error(
					"data access error while retrieving components by type ",
					dae.getMessage());
			throw new ServiceException(dae.getMessage());
		}
		return comps;
	}

	@Transactional
	@Override
	public void deleteVidyoRouter(List<Integer> deleteIds)
			throws ServiceException {
		logger.debug("Delete Vidyo Routers for given compIDs");
		// The proxy mappings have to be updated
		for(int routerId : deleteIds) {
			serviceService.deleteVP(routerId);
		}
		componentsDAO.deleteVidyoRouter(deleteIds);
	}

	@Transactional
	@Override
	public void deleteVidyoManager(List<Integer> deleteIds)
			throws ServiceException {
		logger.debug("Delete Vidyo Managers for given compIDs");

		componentsDAO.deleteVidyoManager(deleteIds);

	}

	@Transactional
	@Override
	public List<Integer> getComponentIDsAssociatedWithPools(
			List<Integer> deleteIds) {
		logger.debug("get ComponentIDs Associated With Pools");
		List<Integer> compIDs = componentsDAO
				.getComponentIDsAssociatedWithPools(deleteIds);
		return compIDs;
	}

	@Transactional
	@Override
	public void deletRouterMediaAddressMap(List<Integer> deleteIds)
			throws ServiceException {
		logger.debug("Delete Media Address Maps for given compIDs");

		componentsDAO.deletRouterMediaAddressMap(deleteIds);
	}

	@Transactional
	@Override
	public void deleteRouterPoolMap(List<Integer> deleteIds)
			throws ServiceException {
		logger.debug("Delete RouterPoolMaps for given compIDs");

		componentsDAO.deleteRouterPoolMap(deleteIds);
	}

	@Transactional(readOnly = true)
	@Override
	public String getManagerFQDN() {
		logger.debug("get Vidyo Manager FQDN");

		return componentsDAO.getManagerFQDN();
	}

	@Transactional
	@Override
	public VidyoGateway updateVidyoGateway(VidyoGateway updatedVidyoGateway)
			throws ServiceException {
		logger.debug("update VidyoGateway");

		VidyoGateway vidyoGateway = gatewayCompService
				.getGatewayByCompId(updatedVidyoGateway.getComponents().getId());

		if (null != updatedVidyoGateway.getPassword()
				&& !updatedVidyoGateway.getPassword()
						.equals(PASSWORD_UNCHANGED)) {
			vidyoGateway.setPassword(VidyoUtil.encrypt(updatedVidyoGateway
					.getPassword().trim()));
		}

		if (null != updatedVidyoGateway.getUserName()
				&& !updatedVidyoGateway.getUserName().equals(
						vidyoGateway.getUserName())) {
			vidyoGateway.setUserName(updatedVidyoGateway.getUserName());
		}
		serviceDao.clearComponentsUserCache();
		String[] transactionDetails = new String[] {"Update VidyoGateway", "SUCCESS", "Id -" + vidyoGateway.getComponents().getCompID()};
		createTransactionHistory(transactionDetails);
		return componentsDAO.updateVidyoGateway(vidyoGateway);
	}

	@Transactional
	@Override
	public VidyoReplay updateVidyoReplay(VidyoReplay updatedVidyoReplay)
			throws ServiceException {
		logger.debug("update VidyoReplay");

		VidyoReplay vidyoReplay = componentsDAO
				.getReplayByCompId(updatedVidyoReplay.getComponents().getId());

		if (vidyoReplay == null) {
			return null;
		}

		if (null != updatedVidyoReplay.getPassword()
				&& !updatedVidyoReplay.getPassword().equals(PASSWORD_UNCHANGED)) {
			vidyoReplay.setPassword(VidyoUtil.encrypt(updatedVidyoReplay
					.getPassword().trim()));
		}

		if (null != updatedVidyoReplay.getUserName()
				&& !updatedVidyoReplay.getUserName().equals(
						vidyoReplay.getUserName())) {
			vidyoReplay.setUserName(updatedVidyoReplay.getUserName());
		}
		serviceDao.clearComponentsUserCache();
		String[] transactionDetails = new String[] {"Update VidyoReplay", "SUCCESS", "Id -" + vidyoReplay.getComponents().getCompID()};
		createTransactionHistory(transactionDetails);
		return componentsDAO.updateVidyoReplay(vidyoReplay);
	}

	@Transactional
	@Override
	public VidyoRecorder updateVidyoRecorder(VidyoRecorder updatedVidyoRecorder)
			throws ServiceException {
		logger.debug("update VidyoRecorder");

		VidyoRecorder vidyoRecorder = componentsDAO
				.getRecorderByCompId(updatedVidyoRecorder.getComponents()
						.getId());

		if (null != updatedVidyoRecorder.getPassword()
				&& !updatedVidyoRecorder.getPassword().equals(
						PASSWORD_UNCHANGED)) {
			vidyoRecorder.setPassword(VidyoUtil.encrypt(updatedVidyoRecorder
					.getPassword().trim()));
		}

		if (null != updatedVidyoRecorder.getUserName()
				&& !updatedVidyoRecorder.getUserName().equals(
						vidyoRecorder.getUserName())) {
			vidyoRecorder.setUserName(updatedVidyoRecorder.getUserName());
		}
		serviceDao.clearComponentsUserCache();
		String[] transactionDetails = new String[] {"Update VidyoRecorder", "SUCCESS", "Id -" + vidyoRecorder.getComponents().getCompID()};
		createTransactionHistory(transactionDetails);
		return componentsDAO.updateVidyoRecorder(vidyoRecorder);
	}

	@Transactional
	@Override
	public void deleteInternalRouter(String ipAddress) {
		logger.debug("delete Internal Router and it's Component, Media Address Map, Router Pool");

		List<Integer> componentID = componentsDAO
				.getComponentIDForInternalRouter(ipAddress);
		if (!componentID.isEmpty()) {
			componentsDAO.deletRouterMediaAddressMap(componentID);
			componentsDAO.deleteRouterPoolMap(componentID);
			componentsDAO.deleteVidyoRouter(componentID);
			componentsDAO.deleteSelectedComponents(componentID);
		}
	}

	@Override
	public VidyoRecorder getRecorderByCompId(int id) {
		logger.debug("get VidyoRecorder");

		VidyoRecorder vidyoRecorder = componentsDAO.getRecorderByCompId(id);

		if (vidyoRecorder != null && vidyoRecorder.getPassword() != null
				&& !vidyoRecorder.getPassword().isEmpty()) {
			vidyoRecorder.setPassword(PASSWORD_UNCHANGED);
		}

		return vidyoRecorder;
	}

	@Override
	public VidyoReplay getReplayByCompId(int id) {
		logger.debug("get VidyoReplay");

		VidyoReplay vidyoReplay = componentsDAO.getReplayByCompId(id);

		if (vidyoReplay != null && vidyoReplay.getPassword() != null
				&& !vidyoReplay.getPassword().isEmpty()) {
			vidyoReplay.setPassword(PASSWORD_UNCHANGED);
		}

		return vidyoReplay;
	}

	@Transactional
	@Override
	public void deleteVidyoGateway(List<Integer> componentIDs)
			throws ServiceException {
		logger.debug("Delete VidyoGateway");

		componentsDAO.deleteVidyoGateway(componentIDs);
	}

	@Transactional
	@Override
	public void deleteVidyoReplay(List<Integer> componentIDs)
			throws ServiceException {
		logger.debug("Delete VidyoReplay");

		componentsDAO.deleteVidyoReplay(componentIDs);
	}

	@Transactional
	@Override
	public void deleteVidyoRecorder(List<Integer> componentIDs)
			throws ServiceException {
		logger.debug("Delete VidyoRecorder");

		componentsDAO.deleteVidyoRecorder(componentIDs);
	}

	@Transactional
	@Override
	public VidyoGateway addVidyoGateway(VidyoGateway gateway)
			throws ServiceException {
		return componentsDAO.addVidyoGateway(gateway);
	}

	@Transactional
	@Override
	public VidyoRecorder addVidyoRecorder(VidyoRecorder recorder)
			throws ServiceException {
		return componentsDAO.addVidyoRecorder(recorder);
	}

	@Transactional
	@Override
	public VidyoReplay addVidyoReplay(VidyoReplay replay)
			throws ServiceException {
		return componentsDAO.addVidyoReplay(replay);
	}

	@Transactional
	@Override
	public Component setFactoryDefaultConfiguration(String componentId)
			throws ServiceException {
		Component savedComponent = null;

		if (componentId == null){
			logger.error("Component Id is null");
		} else {
			Component componentObj = componentsDAO.findComponentByID(Integer.parseInt(componentId));
	
			componentObj.setConfigVersion(componentObj.getConfigVersion() + 1);
			componentObj.setUpdated(Calendar.getInstance().getTime());
			componentObj.setName("");
	
			if (componentObj.getCompType().getName().equals("VidyoRouter")) {
				componentObj.setMgmtUrl("http://Router IP:80/vr2conf");

				VidyoRouter vidyoRouter = componentsDAO
						.findRouterByCompID(componentObj.getId());
	
				vidyoRouter.setScipPort(17990);
				vidyoRouter.setScipFqdn("0.0.0.0");
				vidyoRouter.setMediaPortStart(50000);
				vidyoRouter.setMediaPortEnd(65535);
				vidyoRouter.setStunFqdn("");
				vidyoRouter.setStunPort(0);
				vidyoRouter.setAudioDscp(0);
				vidyoRouter.setDscpVidyo(0);
				vidyoRouter.setContentDscp(0);
				vidyoRouter.setSingnalingDscp(0);
	
				savedComponent = componentsDAO.saveComponent(componentObj);
				vidyoRouter.setComponents(savedComponent);
				VidyoRouter savedVidyoRouter = componentsDAO
						.saveVidyoRouter(vidyoRouter);
				List<Integer> deleteIDs = new ArrayList<>();
				deleteIDs.add(componentObj.getId());
				
				componentsDAO.deleteMediaAddressMapByCompId(deleteIDs);
				logger.debug("factory default done for router id ->"
						+ savedVidyoRouter.getId());
			} else {
				componentObj.setMgmtUrl("http://localhost");
	
				VidyoManager vidyoManager = componentsDAO
						.findManagerByCompID(componentObj.getId());
				vidyoManager.setSoapport(17995);
				vidyoManager.setEmcpport(17992);
				vidyoManager.setRmcpport(17991);
				vidyoManager.setFqdn("");
				vidyoManager.setDscpvalue(0);
	
				savedComponent = componentsDAO.saveComponent(componentObj);
				vidyoManager.setComponents(savedComponent);
				VidyoManager savedVidyoManager = componentsDAO
						.saveVidyoManager(vidyoManager);
				logger.debug("factory default done for manager id ->"
						+ savedVidyoManager.getId());
			}
		}
		return savedComponent;
	}

	@Override
	@Cacheable(cacheName = "networkConfigDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public NetworkConfigType getActiveNetworkConfig() {
		logger.warn("Generating ACTIVE cloud config data");
		// get the active cloud config version
		List<CloudConfig> activeCloudConfigs = routerPoolsService.findCloudConfigByStatus("ACTIVE");
		NetworkConfigType networkConfigType = new NetworkConfigType();

		if (activeCloudConfigs.isEmpty() || activeCloudConfigs.get(0) == null) {
			// no active cloud config in DB so its a new system, generate a
			// default xml and saveCloudConfig
			CloudConfig cloudConfig = new CloudConfig();
			cloudConfig.setStatus("ACTIVE");
			cloudConfig.setVersion(1);
			routerPoolsService.saveCloudConfig(cloudConfig);

			networkConfigType.setDocumentVersion("1");
			networkConfigType.setCategories(new CategoriesType());
			networkConfigType.setBandwidthMap(new BandwidthMapType());
			Candidates candidates = new Candidates();
			candidates.getCandidate().add("EID");
			candidates.getCandidate().add("LocalIP");
			candidates.getCandidate().add("ExternalIP");
			candidates.getCandidate().add("LocationTag");
			networkConfigType.setLocationCandidates(candidates);
			List<VidyoRouter> routers = getAllActiveRouters();
			for (VidyoRouter vidyoRouter : routers) {
				NetworkElement networkElement = new NetworkElement();
				networkElement.setIdentifier(vidyoRouter.getComponents()
						.getCompID());
				networkElement.setName(vidyoRouter.getComponents().getName());
				networkElement.setType(vidyoRouter.getComponents()
						.getCompType().getName());
				SCIPListenAddressType addressList = new SCIPListenAddressType();
				String scipListenAddress = "scip:"
						+ vidyoRouter.getScipFqdn() + ":"
						+ vidyoRouter.getScipPort() + ";transport=";
				Configuration systemConfig = systemService
						.getConfiguration("COMPONENTS_ENCRYPTION");
				if (systemConfig != null
						&& systemConfig.getConfigurationValue() != null
						&& Boolean
								.valueOf(systemConfig.getConfigurationValue())) {
					scipListenAddress = scipListenAddress + "TLS";
				} else {
					scipListenAddress = scipListenAddress + "TCP";
				}
				addressList.getSCIPListenAddress().add(scipListenAddress);
				networkElement.setSCIPAddressList(addressList);
				networkConfigType.getNetworkElement().add(networkElement);
			}

		} else {
			// fetch data for ACTIVE cloud config id and generate xml from cloud
			// config tables data
			Integer cloudConfigId = activeCloudConfigs.get(0).getId();
			networkConfigType.setDocumentVersion(routerPoolsService
					.getCloudConfigVersionByStatus("ACTIVE").toString());
			networkConfigType.setCategories(new CategoriesType());

			Candidates candidates = new Candidates();
			candidates.getCandidate().add("EID");
			candidates.getCandidate().add("LocalIP");
			candidates.getCandidate().add("ExternalIP");
			candidates.getCandidate().add("LocationTag");
			networkConfigType.setLocationCandidates(candidates);

			// set NetworkElement from Vidyo_router_config
			List<VidyoRouter> routers = getAllActiveRouters();
			for (VidyoRouter vidyoRouter : routers) {
				NetworkElement networkElement = new NetworkElement();
				networkElement.setIdentifier(vidyoRouter.getComponents()
						.getCompID());
				networkElement.setName(vidyoRouter.getComponents().getName());
				networkElement.setType(vidyoRouter.getComponents()
						.getCompType().getName());
				SCIPListenAddressType addressList = new SCIPListenAddressType();
				String scipListenAddress = "scip:"
						+ vidyoRouter.getScipFqdn() + ":"
						+ vidyoRouter.getScipPort() + ";transport=";
				Configuration systemConfig = systemService
						.getConfiguration("COMPONENTS_ENCRYPTION");
				if (systemConfig != null
						&& systemConfig.getConfigurationValue() != null
						&& Boolean
								.valueOf(systemConfig.getConfigurationValue())) {
					scipListenAddress = scipListenAddress + "TLS";
				} else {
					scipListenAddress = scipListenAddress + "TCP";
				}
				addressList.getSCIPListenAddress().add(scipListenAddress);
				networkElement.setSCIPAddressList(addressList);
				networkConfigType.getNetworkElement().add(networkElement);
			}

			// set BandwidthMap from PoolToPool
			List<PoolToPool> connectionList = routerPoolsService
					.getConnectionList("ACTIVE");
			BandwidthMapType bandwidthMapType = new BandwidthMapType();
			for (PoolToPool connection : connectionList) {
				BandwidthMapElementType bandwidthMapElement = new BandwidthMapElementType();
				bandwidthMapElement.setFromRouterGroupID(new Integer(connection.getPoolToPoolPK()
						.getPool1()).toString());
				bandwidthMapElement.setToRouterGroupID(new Integer(connection.getPoolToPoolPK()
						.getPool2()).toString());
				bandwidthMapElement.setWeightedBandwidth(new Long(connection
						.getWeight()));
				bandwidthMapElement.setWeightedBandwidth(connection.getWeight());
				switch (connection.getDirection()) {
				case 0:
					// Nothing to set incase of bi-directional
					break;
				case 1:
					bandwidthMapElement.setConnDirection("ToDest");
					break;
				case 2:
					bandwidthMapElement.setConnDirection("FromDest");
					break;
				default:
					logger.warn("No direction available for the connection Pool 1 " + connection.getPoolToPoolPK().getPool1() + " and Pool 2 "+ connection.getPoolToPoolPK().getPool2());
					break;
				}
				bandwidthMapType.getBandwidthMapElement().add(
						bandwidthMapElement);
			}
			networkConfigType.setBandwidthMap(bandwidthMapType);

			// set Group from Pool
			List<Pool> poolList = routerPoolsService.getPoolList(cloudConfigId);
			for (Pool pool : poolList) {
				GroupType groupType = new GroupType();
				groupType.setName(pool.getName());
				groupType.setID(new Integer(pool.getPoolKey().getId()).toString());
				List<VidyoRouter> associatedRouters = new ArrayList<VidyoRouter>();
				for (RouterPoolMap routerPoolMap : pool.getRouterPoolMap()) {
					associatedRouters.add(routerPoolMap.getVidyoRouter());
				}
				NetworkElementsType networkElements = new NetworkElementsType();
				for (VidyoRouter vidyoRouter : associatedRouters) {
					networkElements.getIdentifier().add(
							vidyoRouter.getComponents().getCompID());
				}
				groupType.setNetworkElements(networkElements);
				groupType.setCategories(new CategoriesType());
				networkConfigType.getGroup().add(groupType);
			}

			// set Location from Rules
			List<Rule> rulesList = routerPoolsService
					.getRulesList(cloudConfigId);
			// Order the rule based on RULE_ORDER column
			TreeSet<Rule> treeRuleSet = new TreeSet<Rule>(new Rule());
			treeRuleSet.addAll(rulesList);

			for (Rule rule : treeRuleSet) {
				PoolPriorityList poolPriorityList = rule.getPoolPriorityList();
				List<PoolPriorityMap> poolPriorityMap = poolPriorityList
						.getPoolPriorityMap();
				LocationType locationType = new LocationType();

				PrioritizedGroupListsType prioritizedGroupLists = new PrioritizedGroupListsType();

				for (PoolPriorityMap poolMap : poolPriorityMap) {
					GroupListType groupList = new GroupListType();
					groupList.getGroupID()
							.add((new Integer(poolMap.getPool().getId())
									.toString()));
					prioritizedGroupLists.getGroupList().add(groupList);
				}

				locationType.setPrioritizedGroupLists(prioritizedGroupLists);

				locationType.setID(new Integer(rule.getId()).toString());
				locationType.setName(rule.getRuleName());

				Set<RuleSet> fetchedRuleSet = rule.getRuleSet();
				// Order the ruleSet based on RULESET_ORDER column
				TreeSet<RuleSet> treeSet = new TreeSet<RuleSet>(new RuleSet());
				treeSet.addAll(fetchedRuleSet);

				RuleSetType ruleSetType = new RuleSetType();
				OrType orTYpe = new OrType();
				for (RuleSet ruleSet : treeSet) {
					BasicRuleSet basicRuleSet = new BasicRuleSet();
					if (null != ruleSet.getPrivateIP()
							&& null == ruleSet.getPublicIP()) {
						BasicAndType basicAndType = new BasicAndType();
						BasicRuleType basicRuleType = new BasicRuleType();

						IPV4SubnetType ipV4SubnetType = new IPV4SubnetType();
						ipV4SubnetType.setCandidate("LocalIP");
						ipV4SubnetType.setIPAddr(ruleSet.getPrivateIP());
						ipV4SubnetType.setCIDRBits(BigInteger.valueOf(ruleSet
								.getPrivateIpCIDR()));
						basicRuleType.setIPSubnet(ipV4SubnetType);

						basicAndType.getArg().add(basicRuleType);
						basicRuleSet.setBasicAnd(basicAndType);
					}
					if (null != ruleSet.getPublicIP()
							&& null == ruleSet.getPrivateIP()) {
						BasicAndType basicAndType = new BasicAndType();
						BasicRuleType basicRuleType = new BasicRuleType();

						IPV4SubnetType ipV4SubnetType = new IPV4SubnetType();
						ipV4SubnetType.setCandidate("ExternalIP");
						ipV4SubnetType.setIPAddr(ruleSet.getPublicIP());
						ipV4SubnetType.setCIDRBits(BigInteger.valueOf(ruleSet
								.getPublicIpCIDR()));
						basicRuleType.setIPSubnet(ipV4SubnetType);

						basicAndType.getArg().add(basicRuleType);
						basicRuleSet.setBasicAnd(basicAndType);
					}
					if (null != ruleSet.getPublicIP()
							&& null != ruleSet.getPrivateIP()) {
						BasicAndType basicAndType = new BasicAndType();
						BasicRuleType basicRuleType1 = new BasicRuleType();
						BasicRuleType basicRuleType2 = new BasicRuleType();

						IPV4SubnetType ipV4SubnetType1 = new IPV4SubnetType();
						ipV4SubnetType1.setCandidate("LocalIP");
						ipV4SubnetType1.setIPAddr(ruleSet.getPrivateIP());
						ipV4SubnetType1.setCIDRBits(BigInteger.valueOf(ruleSet
								.getPrivateIpCIDR()));
						basicRuleType1.setIPSubnet(ipV4SubnetType1);

						IPV4SubnetType ipV4SubnetType2 = new IPV4SubnetType();
						ipV4SubnetType2.setCandidate("ExternalIP");
						ipV4SubnetType2.setIPAddr(ruleSet.getPublicIP());
						ipV4SubnetType2.setCIDRBits(BigInteger.valueOf(ruleSet
								.getPublicIpCIDR()));
						basicRuleType2.setIPSubnet(ipV4SubnetType2);

						basicAndType.getArg().add(basicRuleType1);
						basicAndType.getArg().add(basicRuleType2);
						basicRuleSet.setBasicAnd(basicAndType);
					}
					if (null != new Integer(ruleSet.getLocationTagID())
							&& ruleSet.getLocationTagID() != 0) {
						BasicAndType basicAndType = new BasicAndType();
						BasicRuleType basicRuleType = new BasicRuleType();

						EqualToType equalToType = new EqualToType();
						equalToType.setCandidate("LocationTag");
						equalToType
								.setValue(routerPoolsService
										.getLocationTagByID(ruleSet
												.getLocationTagID()));
						basicRuleType.setEqualTo(equalToType);

						basicAndType.getArg().add(basicRuleType);
						basicRuleSet.setBasicAnd(basicAndType);
					}
					if (null != ruleSet.getEndpointID()) {
						BasicAndType basicAndType = new BasicAndType();
						BasicRuleType basicRuleType = new BasicRuleType();

						EqualToType equalToType = new EqualToType();
						equalToType.setCandidate("EID");
						equalToType.setValue(ruleSet.getEndpointID());
						basicRuleType.setEqualTo(equalToType);

						basicAndType.getArg().add(basicRuleType);
						basicRuleSet.setBasicAnd(basicAndType);
					}
					orTYpe.getArg().add(basicRuleSet);
				}
				// Add only if the ruleset is not empty
				if(!orTYpe.getArg().isEmpty()) {
					ruleSetType.setOr(orTYpe);
					locationType.setRuleSet(ruleSetType);
					networkConfigType.getLocation().add(locationType);
				}
			}
		}

		return networkConfigType;
	}

	@Override
	public int enableDisableComponentsEncryption(boolean value) {
		int updated = systemService.updateComponentsEncryption(value);
		if (updated > 0) {
			Iterable<Component> components = componentsDAO.findAll();
			// Update the version number & clear cache, so that the components
			// pick up the change
			for (Component component : components) {
				if (component.getCompType().getName()
						.equalsIgnoreCase("VidyoManager")
						|| component.getCompType().getName()
								.equalsIgnoreCase("VidyoRouter")) {
					component
							.setConfigVersion(component.getConfigVersion() + 1);
				}
			}
			componentsDAO.updateComponents(components);
			// Update the config version of ACTIVE and MODIFIED & clear cache
			incrementCloudConfigVersion();
			serviceDao.clearCache("key");
		}
		return updated;
	}
	
	@Override
	public void triggerNetworkConfigXmlUpdate(){
		incrementCloudConfigVersion();
		serviceDao.clearCache("key");
	}

	private void incrementCloudConfigVersion() {
		List<CloudConfig> modifiedCloudConfigs = routerPoolsService
				.findCloudConfigByStatus("MODIFIED");
		if (!modifiedCloudConfigs.isEmpty()) {
			// There should be only one in modified status or else log an error
			logger.warn("There is a modified version available, so modified config won't get incremented and VidyoManager won't restart. Modified Version number - " + modifiedCloudConfigs.get(0).getVersion());
		} else {
			logger.warn("There is no modified version available, creating a new modified version");
			routerPoolsService.makeCopyOfActiveRecords();
		}
	}

	@Override
	public int updateRouterProxyConfig(boolean enabled) {
		return componentsDAO.updateRouterProxyConfig(enabled);
	}

	@Override
	public VidyoReplay updateVidyoReplay(VidyoRecorder replayRecorder, String ipAddress)
			throws ServiceException {
		if(replayRecorder != null && replayRecorder.getCompType().getName().equalsIgnoreCase("VidyoReplay")) {
			// check if the replay is in components for the IP
			VidyoReplay vidyoReplay = componentsDAO.getReplayByIP(ipAddress);
			if(vidyoReplay == null) {
				// Create a new components entry and replay config entry
				Component newComponent = new Component();
				newComponent.setCreated(Calendar.getInstance().getTime());
				newComponent.setHeartbeatTime(Calendar.getInstance().getTime());
				newComponent.setCompID("VidyoReplay-Default");
				newComponent.setMgmtUrl("");
				// Status is always "NEW" during first insert
				newComponent.setStatus("NEW");
				newComponent.setLocalIP(ipAddress);
				newComponent.setClusterIP(ipAddress);
				// Set running version as zero for insert
				newComponent.setRunningVersion(0);
				newComponent.setAlarm("");
				// Set config version as zero for insert
				newComponent.setConfigVersion(0);
				newComponent.setCompSoftwareVersion("");
				newComponent.setName("Replay-Default");
				List<ComponentType> compTypeList = null;
				try {
					compTypeList = findByComponentTypeByName("VidyoReplay");
				} catch (ServiceException e1) {
					e1.printStackTrace();
				}
				if (compTypeList == null || compTypeList.get(0) == null) {
					logger.error("no parent comp type found - abort registration");
					throw new RuntimeException("Invalid ConfigType");
				}
				newComponent.setCompType(compTypeList.get(0));
				newComponent = saveComponent(newComponent);
				vidyoReplay = new VidyoReplay();
				vidyoReplay.setComponents(newComponent);
			}
			vidyoReplay.getComponents().setStatus("ACTIVE");
			vidyoReplay.setUserName(replayRecorder.getUserName());
			vidyoReplay.setPassword(VidyoUtil.encrypt(replayRecorder.getPassword()));
			String[] transactionDetails = new String[] {"Update VidyoReplay", "SUCCESS", "Id -" + vidyoReplay.getComponents().getCompID()};
			createTransactionHistory(transactionDetails);
			return componentsDAO.addVidyoReplay(vidyoReplay);
		}
		return null;
	}
	
	@Override
	public VidyoReplay getReplayByIP(String ipAddress) {
		return componentsDAO.getReplayByIP(ipAddress);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<VidyoRouter> getAllActiveRouters() {
		List<VidyoRouter> routers = null;
		try {
			routers = componentsDAO.getAllActiveRouters();
		} catch (DataAccessException dae) {
			logger.error("data access error while retrieving routers ",
					dae.getMessage());
			throw new ServiceException(dae.getMessage());
		}
		return routers;
	}

	@Override
	public List<Component> getAllComponents() {
		return componentsDAO.getAllComponents();
	}

	@Override
	public List<Component> getAllComponentsByType(String compTypeName) {
		if (compTypeName != null && compTypeName.equalsIgnoreCase("VidyoProxy")){
			compTypeName = "VidyoRouter";
		}
		return componentsDAO.getAllComponentsByType(compTypeName);
	}
	
	@Override
	public List<RecorderEndpoints> getRecorderEndpointsByRecorderId(int recorderId) {
		List<RecorderEndpoints> recorderEndpoints = componentsDAO.getRecorderEndpointsByRecorderId(recorderId);
		return recorderEndpoints;
	}
	
	@Override
	@Cacheable(cacheName = "vmConnectAddressCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public String getVidyoManagerConnectAddress() {
		StringBuilder builder = new StringBuilder();
		List<Component> components = componentsDAO.getComponentsByType("VidyoManager");
		if (components.size() != 1) {
			logger.error("Invalid System Configuration - More than one active VidyoManager. No VMConnect Address");
			return null;
		}
		Component vidyoManagerComp = components.get(0);
		if (vidyoManagerComp != null) {
			VidyoManager vidyoManagerConfig = componentsDAO.findManagerByCompID(vidyoManagerComp.getId());
			if (vidyoManagerConfig != null) {
				builder.append(vidyoManagerComp.getCompID()).append("@").append(vidyoManagerConfig.getFqdn())
						.append(":").append(vidyoManagerConfig.getEmcpport()).append(";transport=");
			}
		}
		Configuration configuration = systemService.getConfiguration("COMPONENTS_ENCRYPTION");
		if (configuration != null && Boolean.valueOf(configuration.getConfigurationValue())) {
			builder.append("TLS");
		} else {
			builder.append("TCP");
		}
		return builder.toString();
	}
	
	@Transactional
	@Override
	public List<Integer> deleteComponents(List<Integer> componentIDsToDelete, String toBeReplaceWith, String confirmation){
		if (componentIDsToDelete == null || componentIDsToDelete.size() == 0 || componentIDsToDelete.size() > 1)
			return null;
		Component toBeDeleteComponent = findComponentByID(componentIDsToDelete.get(0));
		
		if(!toBeReplaceWith.equals("0") && null != toBeReplaceWith && !toBeReplaceWith.isEmpty()) {
			Component replacementComponent = findComponentByID(Integer.parseInt(toBeReplaceWith));
			
			String replacementName = replacementComponent.getName();
		        String toBeDeleteName = toBeDeleteComponent.getName();
		        String typeStr = toBeDeleteComponent.getCompType().getName();
	
	        if(replacementName != null && toBeDeleteName != null && typeStr!=null){
	            serviceService.replaceNetworkComponent(toBeDeleteName, replacementName, typeStr);
			}
		}
		
		if (confirmation.equalsIgnoreCase("false")) {
			
				if(toBeDeleteComponent.getCompType().getName().equalsIgnoreCase(NetworkElementType.VidyoManager.getValue())) {
					componentsDAO.deleteVidyoManager(componentIDsToDelete);
				} else if(toBeDeleteComponent.getCompType().getName().equalsIgnoreCase(NetworkElementType.VidyoRouter.getValue())) {
					List<Integer> routerComponentsAssociatedWithPool = componentsDAO
							.getComponentIDsAssociatedWithPools(componentIDsToDelete);
					if (null != routerComponentsAssociatedWithPool && !routerComponentsAssociatedWithPool.isEmpty()) {
						return routerComponentsAssociatedWithPool;
					} else {
						componentsDAO.deletRouterMediaAddressMap(componentIDsToDelete);
						componentsDAO.deleteRouterPoolMap(componentIDsToDelete);
						componentsDAO.deleteVidyoRouter(componentIDsToDelete);
				}
				} else if(toBeDeleteComponent.getCompType().getName().equalsIgnoreCase(NetworkElementType.VidyoGateway.getValue())) {
					componentsDAO.deleteVidyoGateway(componentIDsToDelete);
				} else if(toBeDeleteComponent.getCompType().getName().equalsIgnoreCase("VidyoRecorder")){
					componentsDAO.deleteVidyoRecorder(componentIDsToDelete);
				} else if(toBeDeleteComponent.getCompType().getName().equalsIgnoreCase(NetworkElementType.VidyoReplay.getValue())) {
					componentsDAO.deleteVidyoReplay(componentIDsToDelete);
			}
				componentsDAO.deleteSelectedComponents(componentIDsToDelete);
			
		} else {
			if (!componentIDsToDelete.isEmpty()) {
				componentsDAO.deletRouterMediaAddressMap(componentIDsToDelete);
				componentsDAO.deleteRouterPoolMap(componentIDsToDelete);
				componentsDAO.deleteVidyoRouter(componentIDsToDelete);
				componentsDAO.deleteSelectedComponents(componentIDsToDelete);
				
			}
		}
		if (toBeDeleteComponent.getCompType().getName() != null
				&& (toBeDeleteComponent
						.getCompType()
						.getName()
						.equalsIgnoreCase(
								NetworkElementType.VidyoRouter.getValue()) || toBeDeleteComponent
						.getCompType()
						.getName()
						.equalsIgnoreCase(
								NetworkElementType.VidyoManager.getValue()))) {
			triggerNetworkConfigXmlUpdate();
		}

		return null;
	}
	
	/**
	 * To fetch the recorderEndpoint using description 
	 * @param description
	 * @return
	 */
	@Override
	public List<RecorderEndpoints> getRecorderEndpointsByDescription(String description){
		return componentsDAO.getRecorderEndpointsByDescription(description);
	}
}