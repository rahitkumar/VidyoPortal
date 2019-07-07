package com.vidyo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Group;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.Location;
import com.vidyo.bo.Member;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.Router;
import com.vidyo.bo.Service;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.TenantFilter;
import com.vidyo.bo.TenantIpc;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.db.EndpointUploadDao;
import com.vidyo.db.IGroupDao;
import com.vidyo.db.IRoomDao;
import com.vidyo.db.ISystemDao;
import com.vidyo.db.ITenantDao;
import com.vidyo.db.TenantConfigurationDao;
import com.vidyo.db.idp.ITenantIdpAttributesMappingDao;
import com.vidyo.db.ldap.ITenantLdapAttributesMappingDao;
import com.vidyo.db.repository.tenant.TenantConfigurationRepository;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.service.exceptions.ConferenceNotExistException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.exceptions.NotLicensedException;
import com.vidyo.service.exceptions.ResourceNotAvailableException;
import com.vidyo.service.interportal.IpcConfigurationService;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.member.response.MemberManagementResponse;
import com.vidyo.service.room.RoomUpdateResponse;
import com.vidyo.service.tenant.TenantUpdateResponse;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.utils.VendorUtils;
import com.vidyo.utils.room.RoomUtils;

public class TenantServiceImpl implements ITenantService {

	protected static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class.getName());

	public static enum MobileMode {
		DISABLED("DISABLED", 0), VIDYOMOBILE("VidyoMobile", 1), NEOMOBILE("NeoMobile", 2);

		private final String value;
		private final int intValue;

		MobileMode(String value, int num) {
			this.value = value;
			this.intValue = num;
		}

		public static MobileMode forValue(String value) {
			if ("DISABLED".equals(value)) {
				return MobileMode.DISABLED;
			} else if ("VidyoMobile".equals(value)) {
				return MobileMode.VIDYOMOBILE;
			} else if ("NeoMobile".equals(value)) {
				return MobileMode.NEOMOBILE;
			} else {
				throw new IllegalArgumentException(("Invalid MobileMode: " + value));
			}
		}

		public static MobileMode forIntValue(int value) {
			if (value == 0) {
				return MobileMode.DISABLED;
			} else if (value == 1) {
				return MobileMode.VIDYOMOBILE;
			} else if (value == 2) {
				return MobileMode.NEOMOBILE;
			} else {
				throw new IllegalArgumentException(("Invalid MobileMode: " + value));
			}
		}

		public String toValue() {
			return this.value;
		}

		public int getIntValue() {
			return this.intValue;
		}
	}

	private ITenantDao dao;
	private ITenantLdapAttributesMappingDao tenantLdapAttributesMappingDao;
	private ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao;
	private LicensingService license;

	private ISystemDao systemDao;

	private IGroupDao groupDao;
	private IRoomDao roomDao;
	private EndpointUploadDao endpointUploadDao;
	private TenantConfigurationDao tenantConfigurationDao;
	
	@Autowired
	private TenantConfigurationRepository tenantConfigurationRepository;

	/**
	 * InterPortalConferenceService
	 */
	private IpcConfigurationService ipcConfigurationService;

	/**
	 * Room Service
	 */
	private IRoomService roomService;

	private ISystemService systemService;

	private IServiceService serviceService;

	private IMemberService memberService;

	private List<String> ignoreTenantUrls;

	private MemberService memberService2;

	private IConferenceService conferenceService;

	private TransactionService transactionService;

	public void setConferenceService(IConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public void setDao(ITenantDao dao) {
		this.dao = dao;
	}

	public void setTenantLdapAttributesMappingDao(ITenantLdapAttributesMappingDao tenantLdapAttributesMappingDao) {
		this.tenantLdapAttributesMappingDao = tenantLdapAttributesMappingDao;
	}

	public void setTenantIdpAttributesMappingDao(ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao) {
		this.tenantIdpAttributesMappingDao = tenantIdpAttributesMappingDao;
	}

	public void setLicense(LicensingService license) {
		this.license = license;
	}

	public ISystemDao getSystemDao() {
		return systemDao;
	}

	public void setSystemDao(ISystemDao systemDao) {
		this.systemDao = systemDao;
	}

	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setRoomDao(IRoomDao roomDao) {
		this.roomDao = roomDao;
	}

	public void setEndpointUploadDao(EndpointUploadDao endpointUploadDao) {
		this.endpointUploadDao = endpointUploadDao;
	}

	public void setTenantConfigurationDao(TenantConfigurationDao tenantConfigurationDao) {
		this.tenantConfigurationDao = tenantConfigurationDao;
	}

	public List<Tenant> getTenants(TenantFilter filter) {
		List<Tenant> list = this.dao.getTenants(filter);
		return list;
	}

	public Long getCountTenants(TenantFilter filter) {
		Long number = this.dao.getCountTenants(filter);
		return number;
	}

	/**
	 * @param roomService
	 *            the roomService to set
	 */
	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public void setServiceService(IServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	public Tenant getTenantForRequest() {
		Tenant rc = getTenant(TenantContext.getTenantId());
		return rc;
	}

	/**
	 * Returns Tenant by Tenant Id
	 *
	 * @param tenantID
	 *            Id of the Tenant
	 */
	@Override
	public Tenant getTenant(int tenantID) {
		Tenant tenant = null;
		try {
			tenant = this.dao.getTenant(tenantID);
			TenantConfiguration config = this.tenantConfigurationDao.getTenantConfiguration(tenantID);
			if (config != null) {
				tenant.setPublicRooms(config.getMaxCreatePublicRoomTenant());
				tenant.setLogAggregation(config.getLogAggregation());
				tenant.setEndpointUploadMode(config.getEndpointUploadMode());
				tenant.setEndpointBehavior(config.getEndpointBehavior());
			}
		} catch (DataAccessException dae) {
			logger.error("Error while getting tenant by tenant id {}, {}", tenantID, dae.getMessage());
		}
		return tenant;
	}

	/**
	 * Returns Tenant by Tenant Name.
	 *
	 * @param tenantName
	 *            Name of the Tenant
	 */
	@Override
	public Tenant getTenant(String tenantName) {
		Tenant tenant = null;
		try {
			tenant = this.dao.getTenant(tenantName);
		} catch (DataAccessException dae) {
			logger.error("Error while getting tenant by tenant name {}, {}", tenantName, dae.getMessage());
		}
		return tenant;
	}

	/**
	 * Returns Tenant by Tenant Url.
	 *
	 * @param url
	 *            Tenant Url
	 */
	@Override
	public Tenant getTenantByURL(String url) {
		Tenant tenant = null;
		try {
			tenant = this.dao.getTenantByURL(url);
		} catch (DataAccessException dae) {
			if (!ignoreTenantUrls.contains(url)) {
				logger.info("Error while getting tenant by tenant url {}, {}", url, dae.getMessage());
			}
		}
		return tenant;
	}

	/**
	 * Returns Tenant by Replay Url.
	 *
	 * @param url
	 *            Replay Url
	 */
	@Override
	public Tenant getTenantByReplayURL(String url) {
		if ("non-existent".equals(url)) { // used by replay to test connection, do not want to fill up logs
			return null;
		}
		Tenant tenant = null;
		try {
			tenant = dao.getTenantByReplayURL(url);
		} catch (DataAccessException dae) {
			logger.error("Error while getting tenant by tenant replay url {}, {}", url, dae.getMessage());
		}
		return tenant;
	}

	public List<Tenant> getFromTenants(int tenantID) {
		List<Tenant> list = this.dao.getFromTenants(tenantID);
		return list;
	}

	public List<Tenant> getToTenants(int tenantID) {
		List<Tenant> list = this.dao.getToTenants(tenantID);
		return list;
	}

	public List<Router> getFromRouters(int tenantID) {
		List<Router> list = this.dao.getFromRouters(tenantID);
		return list;
	}

	public List<Router> getToRouters(int tenantID) {
		List<Router> list = this.dao.getToRouters(tenantID);
		return list;
	}

	public List<Service> getFromVMs(int tenantID) {
		List<Service> list = this.dao.getFromVMs(tenantID);
		return list;
	}

	public List<Service> getToVMs(int tenantID) {
		List<Service> list = this.dao.getToVMs(tenantID);
		return list;
	}

	public List<Service> getFromVPs(int tenantID) {
		List<Service> list = this.dao.getFromVPs(tenantID);
		return list;
	}

	public List<Service> getToVPs(int tenantID) {
		List<Service> list = this.dao.getToVPs(tenantID);
		return list;
	}

	public List<Service> getFromVGs(int tenantID) {
		List<Service> list = this.dao.getFromVGs(tenantID);
		return list;
	}

	public List<Service> getToVGs(int tenantID) {
		List<Service> list = this.dao.getToVGs(tenantID);
		return list;
	}

	public int updateRegularLicense(int installs, int seats, int ports, int executives) {
		this.dao.setRegularLicense(installs, seats, ports, executives);
		return 0;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public TenantUpdateResponse updateTenant(int tenantID, Tenant oldTenant, Tenant tenant, Member adminUser) {
		RoomUpdateResponse roomUpdateResponse = null;
		// Update extension only if the prefix has changed
		if (!oldTenant.getTenantPrefix().equalsIgnoreCase(tenant.getTenantPrefix())) {
			roomUpdateResponse = roomService.updateRoomPrefix(oldTenant.getTenantPrefix(), tenantID,
					tenant.getTenantPrefix());
		}

		TenantUpdateResponse tenantUpdateResponse = new TenantUpdateResponse();

		if (roomUpdateResponse != null && roomUpdateResponse.getStatus() != RoomUpdateResponse.SUCCESS) {
			tenantUpdateResponse.setStatus(roomUpdateResponse.getStatus());
			tenantUpdateResponse.setMessage("update.tenant.failed");
			return tenantUpdateResponse;
		}

		// clean up Tenant cache
		clearCache(tenantID);

		// XSS special characters
		String dialin = tenant.getTenantDialIn();
		if (dialin != null) { // for clients with no VidyoVoice capability
			tenant.setTenantDialIn(dialin);
		}

		List<Service> vgs = getToVGs(tenantID);
		for (Service vg : vgs) {
			int serviceID = vg.getServiceID();
			if (this.serviceService.isUseNewGatewayServiceInterface()) {
				this.dao.updateTenantOfGatewayPrefixes(serviceID, 1);
			} else {
				this.dao.updateTenantOfVirtualEndpoints(serviceID, 1);
			}
		}

		int rc = this.dao.updateTenant(tenantID, tenant);
		if (adminUser != null) {
			adminUser.setRoomExtNumber(
					adminUser.getRoomExtNumber().replaceFirst(oldTenant.getTenantPrefix(), tenant.getTenantPrefix()));
			try {
				memberService.updateMember(tenantID, adminUser.getMemberID(), adminUser);
			} catch (AccessRestrictedException e) {
				// Error is already logged.
			}
		}

		// update ClientInstallations and ClientInstallations2 and ConferenceCall2
		if (rc == 1) {
			this.dao.updateTenantInClientInstallations(tenant.getTenantName(), oldTenant.getTenantName());

			TenantConfiguration config = this.tenantConfigurationDao.getTenantConfiguration(tenantID);
			if (config == null) {
				config = new TenantConfiguration();
				config.setTenantId(TenantContext.getTenantId());
			}
			config.setMaxCreatePublicRoomTenant(tenant.getPublicRooms());
			config.setLogAggregation(tenant.getLogAggregation());
			config.setEndpointBehavior(tenant.getEndpointBehavior());
			if (tenant.getEndpointUploadMode() != null) {
				config.setEndpointUploadMode(tenant.getEndpointUploadMode());
			}
			// Process External Integration Settings
			config = processExternalIntegrationSettings(config, tenant);			
			
			// configuring the personal room for tenant - VPTL-8174
			// If vidyocloud, then do not update the personal room config based on global
			// flag, leave the existing config
			if (!VendorUtils.isVidyoCloud()) {
				Configuration personalRoomConfig = systemService.getConfiguration("ENABLE_PERSONAL_ROOM_FLAG");
				if (personalRoomConfig != null && StringUtils.isNotBlank(personalRoomConfig.getConfigurationValue())
						&& StringUtils.isNumeric(personalRoomConfig.getConfigurationValue())) {
					config.setPersonalRoom(Integer.parseInt(personalRoomConfig.getConfigurationValue()));
					logger.info("updating  the personalRoom flag :: " + personalRoomConfig.getConfigurationValue()
							+ "for tenantId :: " + tenantID);
				}
			}
			this.tenantConfigurationDao.updateTenantConfiguration(tenantID, config);
			/*
			 * if (!oldTenant.getEndpointUploadMode().equalsIgnoreCase(tenant.
			 * getEndpointUploadMode())) {
			 * this.endpointUploadDao.deactivateEndpoints(tenantID); }
			 */
		}

		if (StringUtils.isNotBlank(tenant.getCallTo())) {
			String[] callToTenantIds = null;
			if (tenant.getCallTo().trim().contains(",")) {
				callToTenantIds = tenant.getCallTo().split("\\s*,\\s*");
			} else {
				callToTenantIds = new String[] { tenant.getCallTo() };
			}
			if (callToTenantIds != null && callToTenantIds.length > 0) {
				Set<Integer> incomingCallToTenantsSet = Arrays.asList(callToTenantIds).stream().map(Integer::parseInt)
						.collect(Collectors.toSet());
				// Add self since the retrieved list will have one.
				incomingCallToTenantsSet.add(tenantID);
				List<Integer> existingCanCallToTenantList = dao.canCallToTenantIds(tenantID);
				Set<Integer> existingCanCallToTenantSet = existingCanCallToTenantList.stream()
						.collect(Collectors.toSet());
				// Identify the new additions
				Set<Integer> newlyAddedTenants = new HashSet<Integer>();
				newlyAddedTenants.addAll(incomingCallToTenantsSet);
				newlyAddedTenants.removeAll(existingCanCallToTenantSet);
				// Identify the deletions - this new set may not be necessary (review)
				Set<Integer> removedTenants = new HashSet<Integer>();
				removedTenants.addAll(existingCanCallToTenantSet);
				removedTenants.removeAll(incomingCallToTenantsSet);
				if (!removedTenants.isEmpty()) {
					dao.deleteTenantXCall(tenantID, removedTenants);
				}
				if (!newlyAddedTenants.isEmpty()) {
					dao.insertTenantXCall(tenantID, newlyAddedTenants);
				}
			}
		} else {
			// If blank (super has removed all mappings), existing mappings have to be
			// deleted except for self mapping
			dao.deleteTenantXCallMappingExceptSelf(tenantID);
		}

		// remove all Routers records
		this.dao.deleteTenantXrouter(tenantID);
		// add new Routers records for selected affinity
		if (tenant.getRouters() != null) {
			Stack<String> stack = new Stack<String>();
			StringTokenizer tokenizer = new StringTokenizer(tenant.getRouters(), ",");
			while (tokenizer.hasMoreTokens()) {
				stack.push((String) tokenizer.nextElement());
			}
			while (!stack.empty()) {
				String routerID = stack.pop();
				this.dao.insertTenantXrouter(tenantID, Integer.parseInt(routerID));
			}
		}

		List<Service> vmServiceIdList = dao.getToVMs(tenantID);

		Integer vmServiceId = null;

		if (vmServiceIdList != null && vmServiceIdList.size() > 0) {
			vmServiceId = vmServiceIdList.get(0).getServiceID();
		}

		List<Service> oldVidyoProxies = dao.getToVPs(tenantID);
		if (vmServiceId == null) {
			// remove all Services records
			this.dao.deleteTenantXservice(tenantID);
		} else {
			// remove non VM Services records
			this.dao.deleteTenantXserviceNonVM(tenantID);
		}
		// add new Routers records for selected services

		if (tenant.getServices() != null) {
			Stack<String> stack = new Stack<String>();
			StringTokenizer tokenizer = new StringTokenizer(tenant.getServices(), ",");
			while (tokenizer.hasMoreTokens()) {
				String serviceId = (String) tokenizer.nextElement();
				if (vmServiceId != null && vmServiceId != Integer.parseInt(serviceId)) {
					stack.push(serviceId);
				}
			}
			while (!stack.empty()) {
				String serviceID = stack.pop();
				this.dao.insertTenantXservice(tenantID, Integer.parseInt(serviceID));
				removeVidyoProxyFromList(Integer.parseInt(serviceID), oldVidyoProxies);
			}
		}

		// Update the members if the existing selected proxy is no more associated with
		// the Tenant
		this.dao.updateMembersForTenant(tenantID, tenant.getServices() == null ? "0" : tenant.getServices(), true);
		this.dao.updateGuetsForTenant(tenantID, tenant.getServices() == null ? "0" : tenant.getServices(), true);

		// Update LDAP And IdP mappings for VidyoProxy
		String defaultProxy = "No Proxy";
		for (Service oldVidyoProxy : oldVidyoProxies) {
			tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(tenantID, "Proxy",
					oldVidyoProxy.getServiceName(), defaultProxy);
			tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecords(tenantID, "Proxy",
					oldVidyoProxy.getServiceName());

			tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(tenantID, "Proxy",
					oldVidyoProxy.getServiceName(), defaultProxy);
			tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecords(tenantID, "Proxy",
					oldVidyoProxy.getServiceName());
		}

		List<Location> oldLocations = dao.getToLocations(tenantID);
		// remove all Locations records
		this.dao.deleteTenantXlocation(tenantID);
		// add new Locations records for selected locations
		if (tenant.getLocations() != null) {
			Stack<String> stack = new Stack<String>();
			StringTokenizer tokenizer = new StringTokenizer(tenant.getLocations(), ",");
			while (tokenizer.hasMoreTokens()) {
				stack.push((String) tokenizer.nextElement());
			}
			while (!stack.empty()) {
				String locationID = stack.pop();
				this.dao.insertTenantXlocation(tenantID, Integer.parseInt(locationID));
				removeLocationFromList(Integer.parseInt(locationID), oldLocations);
			}
		}

		// Update the members if the existing selected proxy is no more associated with
		// the Tenant
		this.dao.updateMembersForTenant(tenantID, tenant.getLocations() == null ? "1" : tenant.getLocations(), false);
		this.dao.updateGuetsForTenant(tenantID, tenant.getLocations() == null ? "1" : tenant.getLocations(), false);

		// Update LDAP and IdP mappings for Location
		int defaultLocationTagID = systemService.getManageLocationTag();
		Location defaultLocation = serviceService.getLocation(defaultLocationTagID);
		for (Location oldLocation : oldLocations) {
			tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(tenantID,
					"LocationTag", oldLocation.getLocationTag(), defaultLocation.getLocationTag());
			tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecords(tenantID, "LocationTag",
					oldLocation.getLocationTag());

			tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(tenantID, "LocationTag",
					oldLocation.getLocationTag(), defaultLocation.getLocationTag());
			tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecords(tenantID, "LocationTag",
					oldLocation.getLocationTag());
		}

		vgs = getToVGs(tenantID);
		for (Service vg : vgs) {
			int serviceID = vg.getServiceID();
			if (this.serviceService.isUseNewGatewayServiceInterface()) {
				this.dao.updateTenantOfGatewayPrefixes(serviceID, tenantID);
			} else {
				this.dao.updateTenantOfVirtualEndpoints(serviceID, tenantID);
			}
		}

		this.dao.setInstalls(tenant, tenantID);
		this.dao.setSeats(tenant, tenantID);
		this.dao.setPorts(tenant, tenantID);
		this.dao.setGuestLogin(tenant, tenantID);
		this.dao.setExecutives(tenant, tenantID);
		this.dao.setPanoramas(tenant, tenantID);

		// update IPC details
		ipcConfigurationService.updateIpcConfiguration(tenantID, tenant.getInbound(), tenant.getOutbound(), 0, 0);

		// Update global mobile settings
		/*
		 * List<Integer> tenantsMobileAccess = getMobileAccessDetail(); String
		 * globalMobileAccess = "0"; if (tenantsMobileAccess.size() == 1) { if
		 * (tenantsMobileAccess.get(0) == 1) { globalMobileAccess = "1"; } else
		 * if(tenantsMobileAccess.get(0) == 2) { globalMobileAccess = "2"; } } else {
		 * globalMobileAccess = "3"; } systemService.saveOrUpdateConfiguration(0,
		 * "MOBILE_LOGIN_MODE", globalMobileAccess);
		 */

		return tenantUpdateResponse;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public TenantUpdateResponse updateTenantAPI(int tenantID, Tenant oldTenant, Tenant tenant, Member adminUser) {
		// clean up Tenant cache
		clearCache(tenantID);
		RoomUpdateResponse roomUpdateResponse = null;
		// Update extension only if the prefix has changed
		if (!oldTenant.getTenantPrefix().equalsIgnoreCase(tenant.getTenantPrefix())) {
			roomUpdateResponse = roomService.updateRoomPrefix(oldTenant.getTenantPrefix(), tenantID,
					tenant.getTenantPrefix());
		}

		TenantUpdateResponse tenantUpdateResponse = new TenantUpdateResponse();

		if (roomUpdateResponse != null && roomUpdateResponse.getStatus() != RoomUpdateResponse.SUCCESS) {
			tenantUpdateResponse.setStatus(roomUpdateResponse.getStatus());
			tenantUpdateResponse.setMessage("update.tenant.failed");
			return tenantUpdateResponse;
		}

		// XSS special characters
		String dialin = tenant.getTenantDialIn();
		if (dialin != null) { // for clients with no VidyoVoice capability
			tenant.setTenantDialIn(dialin);
		}

		int rc = this.dao.updateTenant(tenantID, tenant);
		if (adminUser != null) {
			adminUser.setRoomExtNumber(
					adminUser.getRoomExtNumber().replaceFirst(oldTenant.getTenantPrefix(), tenant.getTenantPrefix()));
			try {
				memberService.updateMember(tenantID, adminUser.getMemberID(), adminUser);
			} catch (AccessRestrictedException e) {
				// Error is already logged.
			}
		}

		// update ClientInstallations and ClientInstallations2 and ConferenceCall2
		if (rc == 1) {
			this.dao.updateTenantInClientInstallations(tenant.getTenantName(), oldTenant.getTenantName());

			TenantConfiguration config = this.tenantConfigurationDao.getTenantConfiguration(tenantID);
			if (config == null) {
				config = new TenantConfiguration();
				config.setTenantId(TenantContext.getTenantId());
			}
			config.setMaxCreatePublicRoomTenant(tenant.getPublicRooms());
			config.setLogAggregation(tenant.getLogAggregation());
			config.setEndpointBehavior(tenant.getEndpointBehavior());
			if (tenant.getEndpointUploadMode() != null) {
				config.setEndpointUploadMode(tenant.getEndpointUploadMode());
			}
			// configuring the personal room for tenant - VPTL-8174
			// If vidyocloud, then do not update the personal room config based on global
			// flag, leave the existing config
			if (!VendorUtils.isVidyoCloud()) {
				Configuration personalRoomConfig = systemService.getConfiguration("ENABLE_PERSONAL_ROOM_FLAG");
				if (personalRoomConfig != null && StringUtils.isNotBlank(personalRoomConfig.getConfigurationValue())
						&& StringUtils.isNumeric(personalRoomConfig.getConfigurationValue())) {
					config.setPersonalRoom(Integer.parseInt(personalRoomConfig.getConfigurationValue()));
					logger.info("updating  the personalRoom flag :: " + personalRoomConfig.getConfigurationValue()
							+ "for tenantId :: " + tenantID);
				}
			}
			this.tenantConfigurationDao.updateTenantConfiguration(tenantID, config);
			/*
			 * if (!oldTenant.getEndpointUploadMode().equalsIgnoreCase(tenant.
			 * getEndpointUploadMode())) {
			 * this.endpointUploadDao.deactivateEndpoints(tenantID); }
			 */
		}
		// Handle Inter Tenant calling list
		if (StringUtils.isNotBlank(tenant.getCallTo())) {
			String[] callToTenantIds = null;
			if (tenant.getCallTo().trim().contains(",")) {
				callToTenantIds = tenant.getCallTo().split("\\s*,\\s*");
			} else {
				callToTenantIds = new String[] { tenant.getCallTo() };
			}
			if (callToTenantIds != null && callToTenantIds.length > 0) {
				Set<Integer> incomingCallToTenantsSet = Arrays.asList(callToTenantIds).stream().map(Integer::parseInt)
						.collect(Collectors.toSet());
				// Add self since the retrieved list will have one.
				incomingCallToTenantsSet.add(tenantID);
				List<Integer> existingCanCallToTenantList = dao.canCallToTenantIds(tenantID);
				Set<Integer> existingCanCallToTenantSet = existingCanCallToTenantList.stream()
						.collect(Collectors.toSet());
				// Identify the new additions
				Set<Integer> newlyAddedTenants = new HashSet<Integer>();
				newlyAddedTenants.addAll(incomingCallToTenantsSet);
				newlyAddedTenants.removeAll(existingCanCallToTenantSet);
				// Identify the deletions - this new set may not be necessary (review)
				Set<Integer> removedTenants = new HashSet<Integer>();
				removedTenants.addAll(existingCanCallToTenantSet);
				removedTenants.removeAll(incomingCallToTenantsSet);
				if (!removedTenants.isEmpty()) {
					dao.deleteTenantXCall(tenantID, removedTenants);
				}
				if (!newlyAddedTenants.isEmpty()) {
					dao.insertTenantXCall(tenantID, newlyAddedTenants);
				}
			}
		} else {
			// If blank (super has removed all mappings), existing mappings have to be
			// deleted except for self mapping
			dao.deleteTenantXCallMappingExceptSelf(tenantID);
		}

		// Handle Router/Proxy List
		// Process the incoming list of Router/Proxies
		Set<Integer> incomingProxySet = tenant.getAllowedProxies().stream().collect(Collectors.toSet()); // To avoid
																											// duplicates
		// Validate if the incoming list of Ids are Router/Proxy Ids
		List<Service> existingProxyList = dao.getToVPs(tenantID);
		Set<Integer> existingProxySet = existingProxyList.stream().map(Service::getServiceID)
				.collect(Collectors.toSet());
		// Identify the new additions
		Set<Integer> newlyAddedProxies = new HashSet<Integer>();
		newlyAddedProxies.addAll(incomingProxySet);
		newlyAddedProxies.removeAll(existingProxySet);
		// Identify the deletions - this new set may not be necessary (review)
		Set<Integer> removedProxies = new HashSet<Integer>();
		removedProxies.addAll(existingProxySet);
		removedProxies.removeAll(incomingProxySet);
		if (!removedProxies.isEmpty()) {
			// Remove the tenant to service mapping
			dao.deleteTenantXServiceMapping(tenantID, removedProxies);
			// If the tenant is externally authenticated and attribute mapped, update the
			// proxy to default if applicable
			// Auth mode, never changes in this workflow, so cached AuthConfig is reliable
			// data
			AuthenticationConfig authenticationConfig = systemService.getAuthenticationConfig(tenantID);
			// Get the proxy names of the proxies which are removed for this tenant
			Set<String> removedProxyNames = existingProxyList.stream()
					.filter(s -> removedProxies.contains(s.getServiceID())).map(Service::getServiceName)
					.collect(Collectors.toSet());

			// Update the Proxy attribute only if the auth type is external and mapping is
			// configured for the tenant
			final String defaultProxy = "No Proxy";
			for (String removedProxyName : removedProxyNames) {
				if (authenticationConfig.isLdapmappingflag()) {
					tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(tenantID,
							"Proxy", removedProxyName, defaultProxy);
					tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecords(tenantID, "Proxy",
							removedProxyName);
				}
				if (authenticationConfig.isSamlflag()) {
					tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(tenantID,
							"Proxy", removedProxyName, defaultProxy);
					tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecords(tenantID, "Proxy",
							removedProxyName);
				}
			}
			// Update Member's proxy id to default "0" for the removed proxy ids
			dao.updateProxyIdToDefaultForMembers(tenantID, removedProxies);
			dao.updateProxyConfigForGuests(tenantID, removedProxies);
		}
		if (!newlyAddedProxies.isEmpty()) {
			dao.insertTenantXServiceMapping(tenantID, newlyAddedProxies);
		}

		// Handle Location Tags
		Set<Integer> incomingLocationIds = tenant.getAllowedLocationTags().stream().collect(Collectors.toSet()); // To
																													// avoid
																													// duplicates
		List<Location> existingLocations = dao.getToLocations(tenantID);
		Set<Integer> existingLocationIds = existingLocations.stream().map(Location::getLocationID)
				.collect(Collectors.toSet());
		// Identify the new additions
		Set<Integer> newlyAddedLocationIds = new HashSet<Integer>();
		newlyAddedLocationIds.addAll(incomingLocationIds);
		newlyAddedLocationIds.removeAll(existingLocationIds);
		// Identify the deletions - this new set may not be necessary (review)
		Set<Integer> removedLocationIds = new HashSet<Integer>();
		removedLocationIds.addAll(existingLocationIds);
		removedLocationIds.removeAll(incomingLocationIds);
		if (!removedLocationIds.isEmpty()) {
			// Remove the tenant to location mapping
			dao.deleteTenantXlocation(tenantID, removedLocationIds);
			// Update LDAP and IdP mappings for Location
			int defaultLocationTagID = systemService.getManageLocationTag();
			Location defaultLocation = serviceService.getLocation(defaultLocationTagID);
			Set<String> removedLocationTagNames = existingLocations.stream()
					.filter(s -> removedLocationIds.contains(s.getLocationID())).map(Location::getLocationTag)
					.collect(Collectors.toSet());
			for (String removedLocationTag : removedLocationTagNames) {
				tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(tenantID,
						"LocationTag", removedLocationTag, defaultLocation.getLocationTag());
				tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecords(tenantID, "LocationTag",
						removedLocationTag);

				tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(tenantID,
						"LocationTag", removedLocationTag, defaultLocation.getLocationTag());
				tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecords(tenantID, "LocationTag",
						removedLocationTag);
			}
			// Update Member's locaton tag
			dao.updateLocationIdToDefaultForMembers(tenantID, removedLocationIds);
			// Update Guest Configuration for Location tag
			dao.updateLocationConfigForGuests(tenantID, removedLocationIds);
		}

		if (!newlyAddedLocationIds.isEmpty()) {
			dao.insertTenantXlocations(tenantID, newlyAddedLocationIds);
		}

		// Handle Gateways
		Set<Integer> incomingGatewayIds = tenant.getAllowedGateways().stream().collect(Collectors.toSet());
		// Validate if the incoming list of Ids are Gateway Ids
		List<Service> existingGatewayServices = dao.getToVGs(tenantID);
		Set<Integer> existingGatewaySet = existingGatewayServices.stream().map(Service::getServiceID)
				.collect(Collectors.toSet());
		// Identify the new additions
		Set<Integer> newlyAddedGateways = new HashSet<Integer>();
		newlyAddedGateways.addAll(incomingGatewayIds);
		newlyAddedGateways.removeAll(existingGatewaySet);
		// Identify the deletions - this new set may not be necessary (review)
		Set<Integer> removedGateways = new HashSet<Integer>();
		removedGateways.addAll(existingGatewaySet);
		removedGateways.removeAll(incomingGatewayIds);
		if (!removedGateways.isEmpty()) {
			// Remove the tenant to service mapping
			dao.deleteTenantXServiceMapping(tenantID, removedGateways);
		}
		if (!newlyAddedGateways.isEmpty()) {
			dao.insertTenantXServiceMapping(tenantID, newlyAddedGateways);
		}
		// Handle VidyoReplay List
		Set<Integer> incomingReplays = tenant.getAllowedReplays().stream().collect(Collectors.toSet());
		// Validate if the incoming list of Ids are Replay Ids
		List<Service> existingReplayServices = dao.getToReplays(tenantID);
		Set<Integer> existingReplaySet = existingReplayServices.stream().map(Service::getServiceID)
				.collect(Collectors.toSet());
		// Identify the new additions
		Set<Integer> newlyAddedReplays = new HashSet<Integer>();
		newlyAddedReplays.addAll(incomingReplays);
		newlyAddedReplays.removeAll(existingReplaySet);
		// Identify the deletions - this new set may not be necessary (review)
		Set<Integer> removedReplays = new HashSet<Integer>();
		removedReplays.addAll(existingReplaySet);
		removedReplays.removeAll(incomingReplays);
		if (!removedReplays.isEmpty()) {
			// Remove the tenant to service mapping
			dao.deleteTenantXServiceMapping(tenantID, removedReplays);
		}
		if (!newlyAddedReplays.isEmpty()) {
			dao.insertTenantXServiceMapping(tenantID, newlyAddedReplays);
		}
		// Handle VidyoRecorder List
		Set<Integer> incomingRecorders = tenant.getAllowedRecorders().stream().collect(Collectors.toSet());
		// Validate if the incoming list of Ids are Replay Ids
		List<Service> existingRecorderServices = dao.getToRecs(tenantID);
		Set<Integer> existingRecorderSet = existingRecorderServices.stream().map(Service::getServiceID)
				.collect(Collectors.toSet());
		// Identify the new additions
		Set<Integer> newlyAddedRecorders = new HashSet<Integer>();
		newlyAddedRecorders.addAll(incomingRecorders);
		newlyAddedRecorders.removeAll(existingRecorderSet);
		// Identify the deletions - this new set may not be necessary (review)
		Set<Integer> removedRecorders = new HashSet<Integer>();
		removedRecorders.addAll(existingRecorderSet);
		removedRecorders.removeAll(incomingRecorders);
		if (!removedRecorders.isEmpty()) {
			// Remove the tenant to service mapping
			dao.deleteTenantXServiceMapping(tenantID, removedRecorders);
		}
		if (!newlyAddedRecorders.isEmpty()) {
			dao.insertTenantXServiceMapping(tenantID, newlyAddedRecorders);
		}

		this.dao.setInstalls(tenant, tenantID);
		this.dao.setSeats(tenant, tenantID);
		this.dao.setPorts(tenant, tenantID);
		this.dao.setGuestLogin(tenant, tenantID);
		this.dao.setExecutives(tenant, tenantID);
		this.dao.setPanoramas(tenant, tenantID);

		// update IPC details
		ipcConfigurationService.updateIpcConfiguration(tenantID, tenant.getInbound(), tenant.getOutbound(), 0, 0);
		// clean up Tenant cache
		clearCache(tenantID);		
		return tenantUpdateResponse;
	}

	private void removeLocationFromList(int removableLocationID, List<Location> locationList) {
		for (Location location : locationList) {
			if (location.getLocationID() == removableLocationID) {
				locationList.remove(location);
				break;
			}
		}
	}

	private void removeVidyoProxyFromList(int removableServiceID, List<Service> vidyoProxyList) {
		for (Service vidyoProxy : vidyoProxyList) {
			if (vidyoProxy.getServiceID() == removableServiceID) {
				vidyoProxyList.remove(vidyoProxy);
				break;
			}
		}
	}

	public int insertTenant(Tenant tenant, Member adminUser) throws Exception {

		if (!this.memberService.isValidMemberPassword(adminUser.getPassword(), adminUser.getMemberID())) {
			return -1;
		}

		// XSS special characters
		String dialin = tenant.getTenantDialIn();
		if (dialin != null) { // for clients with no VidyoVoice capability
			tenant.setTenantDialIn(dialin);
		}

		int tenantID = this.dao.insertTenant(tenant);

		// Create TenantConfiguration
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(tenantID);
		PortalChat portalChat = systemService.getPortalChat();
		if (portalChat.isDefaultPrivateChatEnabled()) {
			tenantConfiguration.setEndpointPrivateChat(1);
		} else {
			tenantConfiguration.setEndpointPrivateChat(0);
		}
		if (portalChat.isDefaultPublicChatEnabled()) {
			tenantConfiguration.setEndpointPublicChat(1);
		} else {
			tenantConfiguration.setEndpointPublicChat(0);
		}

		// set the default config for session expiration
		Configuration config = systemService.getConfiguration("SESSION_EXP_PERIOD");
		// added a new field as part of public room
		tenantConfiguration.setMaxCreatePublicRoomTenant(tenant.getPublicRooms());

		// adding the endpoint upload mode to tenant config
		if (tenant.getEndpointUploadMode() != null)
			tenantConfiguration.setEndpointUploadMode(tenant.getEndpointUploadMode());

		if (config != null) {
			tenantConfiguration.setSessionExpirationPeriod(Integer.valueOf(config.getConfigurationValue()));

		}
		// if a new tenant added and super already enabled thumbnail image, then by
		// default it should be enabled in tenant level.
		Configuration userImageConfig = systemService.getConfiguration("USER_IMAGE");
		if (userImageConfig != null && userImageConfig.getConfigurationValue() != null
				&& "1".equalsIgnoreCase(userImageConfig.getConfigurationValue())) {
			tenantConfiguration.setUserImage(1);
		} else {
			tenantConfiguration.setUserImage(0);
		}

		tenantConfiguration.setLogAggregation(tenant.getLogAggregation());
		tenantConfiguration.setEndpointBehavior(tenant.getEndpointBehavior());
		// configuring the personal room for tenant - VPTL-8174
		Configuration personalRoomConfig = systemService.getConfiguration("ENABLE_PERSONAL_ROOM_FLAG");
		if (personalRoomConfig != null && StringUtils.isNotBlank(personalRoomConfig.getConfigurationValue())
				&& StringUtils.isNumeric(personalRoomConfig.getConfigurationValue())) {
			tenantConfiguration.setPersonalRoom(Integer.parseInt(personalRoomConfig.getConfigurationValue()));
			logger.info("Setting  the personalRoom flag :: " + personalRoomConfig.getConfigurationValue()
					+ "for tenantId :: " + tenantID);
		}
		// Process the external integration settings - 1.Epic
		tenantConfiguration = processExternalIntegrationSettings(tenantConfiguration, tenant);
		tenantConfigurationDao.insertTenantConfiguration(tenantConfiguration);

		// create a Default Group for Tenant
		Group defaultGroup = new Group();
		defaultGroup.setRouterID(1);
		defaultGroup.setSecondaryRouterID(1);
		defaultGroup.setGroupName("Default");
		defaultGroup.setGroupDescription("Default Group");
		defaultGroup.setDefaultFlag(1);
		defaultGroup.setRoomMaxUsers(10);
		defaultGroup.setUserMaxBandWidthIn(100000);
		defaultGroup.setUserMaxBandWidthOut(100000);
		defaultGroup.setAllowRecording(0);

		int groupID = groupDao.insertGroup(tenantID, defaultGroup);

		// create guest configuration group
		systemDao.updateConfiguration(tenantID, "GuestGroupID", String.valueOf(groupID));

		// copy records for uploaded clients from default tenant to new
		endpointUploadDao.copyEndpointUploadsFromDefaultTenantToNew(tenantID);

		// remove all Cross Call records
		this.dao.deleteTenantXCall(tenantID);
		// add new Cross Call records for self
		this.dao.insertTenantXCall(tenantID, tenantID);
		// add new Cross Call records for selected tenants
		if (tenant.getCallTo() != null) {
			StringTokenizer tokenizer = new StringTokenizer(tenant.getCallTo(), ",");

			while (tokenizer.hasMoreTokens()) {
				this.dao.insertTenantXCall(tenantID, Integer.parseInt((String) tokenizer.nextElement()));
			}
		}

		// remove all Routers records
		this.dao.deleteTenantXrouter(tenantID);
		// add new Routers records for selected affinity
		if (tenant.getRouters() != null) {
			StringTokenizer tokenizer = new StringTokenizer(tenant.getRouters(), ",");

			while (tokenizer.hasMoreTokens()) {
				this.dao.insertTenantXrouter(tenantID, Integer.parseInt((String) tokenizer.nextElement()));
			}
		}

		List<Integer> proxyIDs = new ArrayList<>();
		// remove all Services records
		this.dao.deleteTenantXservice(tenantID);
		// add new Routers records for selected services
		if (tenant.getServices() != null) {
			StringTokenizer tokenizer = new StringTokenizer(tenant.getServices(), ",");

			while (tokenizer.hasMoreTokens()) {
				int serviceID = Integer.parseInt((String) tokenizer.nextElement());
				this.dao.insertTenantXservice(tenantID, serviceID);
				try {
					serviceService.getVP(serviceID);
					proxyIDs.add(serviceID);
				} catch (Exception ignore) {
					// ignore, we are looking for VidyoProxy among services
				}
			}
		}

		int proxyID = 0;
		if (proxyIDs.size() > 0) {
			proxyID = proxyIDs.get(0);
		}

		// create guest default configuration ID. proxyID = 0 = "No Proxy"
		systemDao.updateConfiguration(tenantID, "GuestProxyID", String.valueOf(proxyID));

		// remove all Locations records
		this.dao.deleteTenantXlocation(tenantID);
		// add new Locations records for selected locations
		if (tenant.getLocations() != null) {
			StringTokenizer tokenizer = new StringTokenizer(tenant.getLocations(), ",");
			int locationID = -1;
			while (tokenizer.hasMoreTokens()) {
				locationID = Integer.parseInt((String) tokenizer.nextElement());
				this.dao.insertTenantXlocation(tenantID, locationID);
			}

			// create guest default location id. The first locationId in the above token
			// will be used as default
			systemDao.updateConfiguration(tenantID, "GuestLocationID", String.valueOf(locationID));
		}

		this.dao.setInstalls(tenant, tenantID);
		this.dao.setSeats(tenant, tenantID);
		this.dao.setPorts(tenant, tenantID);
		this.dao.setGuestLogin(tenant, tenantID);
		this.dao.setExecutives(tenant, tenantID);
		this.dao.setPanoramas(tenant, tenantID);

		// insert IPC details
		IpcConfiguration interPortalConference = new IpcConfiguration();
		interPortalConference.setTenantID(tenantID);
		if (tenant.getInbound() == null) {
			interPortalConference.setInbound(0);
		} else {
			interPortalConference.setInbound(tenant.getInbound());
		}

		if (tenant.getOutbound() == null) {
			interPortalConference.setOutbound(0);
		} else {
			interPortalConference.setOutbound(tenant.getOutbound());
		}

		ipcConfigurationService.saveIpcConfiguration(interPortalConference);

		// vidyoweb
		if (systemService.isVidyoWebAvailable() && systemService.isVidyoWebEnabledBySuper()) {
			systemService.saveVidyoWebEnabledForAdmin(tenantID, true);
		} else {
			systemService.saveVidyoWebEnabledForAdmin(tenantID, false);
		}

		if (adminUser != null) {
			// create an Admin user for Tenant
			Member adminMember = new Member();
			adminMember.setGroupName("Default");
			adminMember.setLangID(10);
			adminMember.setProfileID(0);
			adminMember.setUsername(adminUser.getUsername());
			adminMember.setPassword(adminUser.getPassword());
			adminMember.setMemberName(adminUser.getMemberName());
			adminMember.setActive(1);
			adminMember.setLoginFailureCount(0);
			adminMember.setAllowedToParticipate(1);
			adminMember.setEmailAddress(adminUser.getEmailAddress());
			adminMember.setDescription(adminUser.getDescription());
			adminMember.setProxyID(proxyID);
			adminMember.setRoleName("Admin");
			adminMember.setLocationTag("Default");
			adminMember.setRoomTypeID(1); // personal room
			// Changing the extension process
			// adminMember.setRoomExtNumber(tenant.getTenantPrefix() + "1234");
			String extn = roomService.generateRoomExt(tenant.getTenantPrefix());
			int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(systemService, extn);
			boolean roomExist = this.roomService.isRoomExistForRoomExtNumber(extn, 0);
			int count = 0;
			while (!roomExist && extExists > 0) {
				// If extn is available, created room or continue the loop
				extn = roomService.generateRoomExt(tenant.getTenantPrefix());
				extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(systemService, extn);
				roomExist = this.roomService.isRoomExistForRoomExtNumber(extn, 0);
				// Try 20 times before throwing the old way as it is required to have the admin
				// user gets created.
				if (count > 20) {
					extn = tenant.getTenantPrefix() + "1234";
					break;
				}
				count++;
			}
			adminMember.setRoomExtNumber(extn);
			int origTenantId = TenantContext.getTenantId();
			MemberManagementResponse managementResponse = null;
			try {
				// Change the Thread local tenant Id to the actual created TenantId for
				// Member/Room creation purpose
				TenantContext.setTenantId(tenantID);
				managementResponse = memberService2.addMember(tenantID, adminMember, tenant.getRequestScheme(),
						tenant.getTenantURL());
			} catch (Exception e) {
				logger.error("Exception while creating default admin user", e.getMessage());
			} finally {
				// Reset to original TenantId after admin user creation.
				TenantContext.setTenantId(origTenantId);
			}

			if (managementResponse == null || managementResponse.getStatus() != MemberManagementResponse.SUCCESS) {
				// Delete the Tenant and return error
				deleteTenant(tenantID);
				throw new RuntimeException("Tenant Creation Failed");
			}
		}

		return tenantID;
	}
	

	private TenantConfiguration processExternalIntegrationSettings(TenantConfiguration tenantConfiguration, Tenant tenant) {
		// save epic enable flag and shared secret
		Configuration epicSystemConfig = systemService.getConfiguration("EPIC_INTEGRATION_SUPPORT");
		if ((epicSystemConfig != null) && (epicSystemConfig.getConfigurationValue() != null)
				&& !epicSystemConfig.getConfigurationValue().trim().isEmpty()
				&& epicSystemConfig.getConfigurationValue().equalsIgnoreCase("1")) {
			// Set the mode to value 1 - EPIC
			if ("1".equals(tenant.getEnableEpic()) && StringUtils.isNotBlank(tenant.getSharedSecret())) {
				tenantConfiguration.setExternalIntegrationMode(1);
			}
			if("0".equals(tenant.getEnableEpic())) {
				tenantConfiguration.setExternalIntegrationMode(0);
			}
			tenantConfiguration.setExtIntegrationSharedSecret(tenant.getSharedSecret());
			tenantConfiguration.setExternalNotificationUrl(tenant.getNotificationUrl());
			tenantConfiguration.setExternalUsername(tenant.getNotificationUser());
			
			String password = tenant.getNotificationPassword();
			if (password != null && !password.isEmpty() && !"PASSWORD_UNCHANGED".equals(password)) {
				password = VidyoUtil.encrypt(password);
			} else if(StringUtils.isEmpty(password) && StringUtils.isEmpty(tenant.getNotificationUser())){
				// Removing the username/password only if both are empty
				password = "";
			} else {
				// If both are not empty, set the old username and password
				password = tenantConfiguration.getExternalPassword();
				tenantConfiguration.setExternalUsername(tenantConfiguration.getExternalUsername());
			}
			tenantConfiguration.setExternalPassword(password);
		}
		return tenantConfiguration;
	}
	
	@Transactional
	public int deleteTenant(int tenantID) {
		// clear cache by all possible params
		clearCache(tenantID);

		// remove Rooms, Members, Groups etc. for the tenant
		this.dao.deleteRoomsForTenant(tenantID);
		this.dao.deleteMembersForTenant(tenantID);
		// delete endpoint and endpoint upload for tenant
		this.dao.deleteEndpointsForTenant(tenantID);
		this.endpointUploadDao.deleteEndpointUploads(tenantID);
		
		this.dao.deleteUserGroupsForTenant(tenantID);
		this.dao.deleteGroupsForTenant(tenantID);
		this.dao.deleteConfigurationForTenant(tenantID);
		this.dao.deleteServicesForTenant(tenantID);

		tenantConfigurationDao.deleteTenantConfiguration(tenantID);

		tenantLdapAttributesMappingDao.removeAllTenantLdapMappingAttributesWithValues(tenantID);
		tenantIdpAttributesMappingDao.removeAllTenantIdpMappingAttributesWithValues(tenantID);

		int rc = this.dao.deleteTenant(tenantID);

		// remove all Cross Call records
		this.dao.deleteAllTenantXCall(tenantID);
		// remove all Routers records
		this.dao.deleteTenantXrouter(tenantID);

		// Delete IPC Configuration
		ipcConfigurationService.deleteIpcConfiguration(tenantID);

		// removing image folder for the tenant if it is exist
		memberService.deleteImageFolder(tenantID);
		return rc;
	}

	public int getMaxNumOfInstalls(int tenantID) {
		int licensedValue = 10;
		SystemLicenseFeature licensedInstalls = null;
		try {
			licensedInstalls = this.license.getSystemLicenseFeature("Installs");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}
		if (licensedInstalls != null) {
			licensedValue = Integer.valueOf(licensedInstalls.getLicensedValue());
		}
		int otherTenantsInstalls = this.dao.getOtherTenantsInstalls(tenantID);
		return licensedValue - otherTenantsInstalls;
	}

	@Override
	public int getInstallsInUse(int tenantID) {
		return memberService.getCountInstalls(tenantID).intValue();
	}

	public int getMaxNumOfSeats(int tenantID) {
		int licensedValue = 10;
		SystemLicenseFeature licensedSeats = null;
		try {
			licensedSeats = this.license.getSystemLicenseFeature("Seats");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}
		if (licensedSeats != null) {
			licensedValue = Integer.valueOf(licensedSeats.getLicensedValue());
		}
		int otherTenantsSeats = this.dao.getOtherTenantsSeats(tenantID);
		return licensedValue - otherTenantsSeats;
	}

	@Override
	public int getSeatsInUse(int tenantID) {
		return memberService.getCountSeats(tenantID).intValue();
	}

	public int getMaxNumOfPorts(int tenantID) {
		int licensedValue = 2;
		SystemLicenseFeature licensedPorts = null;
		try {
			licensedPorts = this.license.getSystemLicenseFeature("Ports");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}
		if (licensedPorts != null) {
			licensedValue = Integer.valueOf(licensedPorts.getLicensedValue());
		}
		return licensedValue; // special value number of ports is shared between all tenants
	}

	public int getMaxNumOfExecutives(int tenantID) {
		int licensedValue = 0;
		SystemLicenseFeature licensedExecutives = null;
		try {
			licensedExecutives = this.license.getSystemLicenseFeature("LimitTypeExecutiveSystem");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}
		if (licensedExecutives != null) {
			licensedValue = Integer.valueOf(licensedExecutives.getLicensedValue());
		}
		int otherTenantsExecutives = this.dao.getOtherTenantsExecutives(tenantID);
		return licensedValue - otherTenantsExecutives;
	}

	@Override
	public int getExecutivesInUse(int tenantID) {
		return memberService.getCountExecutives(tenantID).intValue();
	}

	public int getMaxNumOfPanoramas(int tenantID) {
		int licensedValue = 0;
		SystemLicenseFeature licensedPanoramas = null;
		try {
			licensedPanoramas = this.license.getSystemLicenseFeature("LimitTypePanoramaSystem");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}
		if (licensedPanoramas != null) {
			licensedValue = Integer.valueOf(licensedPanoramas.getLicensedValue());
		}
		int otherTenantsPanoramas = this.dao.getOtherTenantsPanoramas(tenantID);
		return licensedValue - otherTenantsPanoramas;
	}

	@Override
	public int getPanoramasInUse(int tenantID) {
		return memberService.getCountPanoramas(tenantID).intValue();
	}

	public boolean isMultiTenant() {
		SystemLicenseFeature mt = null;
		try {
			mt = this.license.getSystemLicenseFeature("MultiTenant");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}
		return (mt != null) && mt.getLicensedValue().equalsIgnoreCase("true");
	}

	public int getUsedNumOfPorts(int tenantID) {
		String lic_version = getLicenseVersion();
		int usedPorts = this.dao.getUsedNumOfPorts(tenantID, lic_version);
		return usedPorts;
	}

	public List<Tenant> canCallToTenants(int tenantID) {
		List<Tenant> rc = this.dao.canCallToTenants(tenantID);
		return rc;
	}

	public int getTenantIDforVirtualEndpoint(String GUID) {
		int rc = this.dao.getTenantIDforVirtualEndpoint(GUID);
		return rc;
	}

	public boolean isTenantExistForTenantName(String tenantName, int tenantID) {
		boolean rc = this.dao.isTenantExistForTenantName(tenantName, tenantID);
		return rc;
	}

	public boolean isVidyoProxyExistTenantLevel(int tenantID, int serviceId) {
		boolean rc = this.dao.isVidyoProxyExistTenantLevel(tenantID, serviceId);
		return rc;
	}

	public boolean isLocationExistTenantLevel(int tenantID, int locationId) {
		boolean rc = this.dao.isLocationExistTenantLevel(tenantID, locationId);
		return rc;
	}

	public boolean isTenantUrlExistForTenantUrl(String tenantUrl, int tenantID) {
		boolean rc = this.dao.isTenantUrlExistForTenantUrl(tenantUrl, tenantID);
		return rc;
	}

	public boolean isTenantReplayUrlExistForTenantReplayUrl(String tenantReplayUrl, int tenantID) {
		boolean rc = this.dao.isTenantReplayUrlExistForTenantReplayUrl(tenantReplayUrl, tenantID);
		return rc;
	}

	public boolean isTenantPrefixExistForTenantPrefix(String tenantPrefix, int tenantID) {
		boolean rc = this.dao.isTenantPrefixExistForTenantPrefix(tenantPrefix, tenantID);
		return rc;
	}

	public boolean isTenantPrefixExistForTenantPrefixLike(String tenantPrefix) {
		boolean rc = this.dao.isTenantPrefixExistForTenantPrefixLike(tenantPrefix);
		return rc;
	}

	public boolean hasTenantReplayComponent(int tenantID) {
		boolean rc = this.dao.hasTenantReplayComponent(tenantID);
		return rc;
	}

	public List<Service> getFromRecs(int tenantID) {
		List<Service> list = this.dao.getFromRecs(tenantID);
		return list;
	}

	public List<Service> getToRecs(int tenantID) {
		List<Service> list = this.dao.getToRecs(tenantID);
		return list;
	}

	public List<Service> getFromReplays(int tenantID) {
		List<Service> list = this.dao.getFromReplays(tenantID);
		return list;
	}

	public List<Service> getToReplays(int tenantID) {
		List<Service> list = this.dao.getToReplays(tenantID);
		return list;
	}

	public boolean isTenantAuthenticatedWithLDAP(int tenant) {
		boolean rc = this.dao.isTenantAuthenticatedWithLDAP(tenant);
		return rc;
	}

	public List<Location> getFromLocations(int tenantID) {
		List<Location> list = this.dao.getFromLocations(tenantID);
		return list;
	}

	public List<Location> getToLocations(int tenantID) {
		List<Location> list = this.dao.getToLocations(tenantID);
		return list;
	}

	public String getLicenseVersion() {
		String licVer = LicensingServiceImpl.LIC_VERSION_20;

		try {
			SystemLicenseFeature f = this.license.getSystemLicenseFeature("Version");
			if (f != null) {
				licVer = f.getLicensedValue();
			}
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}

		return licVer;
	}

	public boolean isTenantNotAllowingGuests(int tenantID) {
		return dao.isTenantNotAllowingGuests(tenantID);
	}

	public boolean isTenantNotAllowingGuests() {
		return dao.isTenantNotAllowingGuests(TenantContext.getTenantId());
	}

	/**
	 *
	 * @return
	 */
	public boolean isIpcSuperManaged() {
		return dao.isIpcSuperManaged();
	}

	/**
	 *
	 * @param ipcAdmin
	 * @return
	 */
	public int updateIpcAdmin(String ipcAdmin) {
		int ipcAdminFlag = ipcAdmin.equalsIgnoreCase("admin") ? 0 : 1;
		int updatedCount = dao.updateIpcAdmin(ipcAdminFlag);
		return updatedCount;
	}

	/**
	 *
	 * @param tenantID
	 * @return
	 */
	public List<TenantIpc> getTenantIpcDetails(int tenantID) {
		return dao.getIpcDetails(tenantID);
	}

	/**
	 *
	 * @param tenantId
	 * @param flag
	 * @return
	 */
	public int updateIpcWhitelistFlag(int tenantId, int flag) {
		return dao.updateWhiteListFlag(tenantId, flag);
	}

	/**
	 *
	 * @param tenantId
	 * @param deletedIds
	 * @return
	 */
	public int deleteTenantIpcDomains(int tenantId, List<Integer> deletedIds) {
		return dao.deleteTenantIpcDomains(tenantId, deletedIds);
	}

	/**
	 *
	 * @param ipcId
	 * @param ipcDomains
	 * @param flag
	 */
	public void addTenantIpcDomains(int ipcId, List<String> ipcDomains, int flag) {
		dao.addTenantIpcDomains(ipcId, ipcDomains, flag);
	}

	/**
	 * Update mobile access configuration for all Tenants.
	 * 
	 * @param mobileAccess
	 * @return
	 */
	public int updateTenantMobileAccess(int mobileAccess) {
		int updateCount = dao.updateTenantMobileAccess(mobileAccess);
		TransactionHistory transactionHistory = new TransactionHistory();
		Tenant tenant = getTenant(TenantContext.getTenantId());
		if (tenant != null && tenant.getTenantName() != null) {
			transactionHistory.setTenantName(tenant.getTenantName());
		}
		if (mobileAccess == 0) {
			transactionHistory.setTransactionName("Mobile Access Disabled");
		}
		if (mobileAccess == 1) {
			transactionHistory.setTransactionName("VidyoMobile Access Enabled");
		}
		if (mobileAccess == 2) {
			transactionHistory.setTransactionName("NeoMobile Access Enabled");
		}
		transactionHistory.setTransactionParams("Mobile Access Settings");
		transactionHistory.setTransactionResult(updateCount > 0 ? "SUCCESS" : "FAILURE");
		transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
		return updateCount;
	}

	/**
	 *
	 * @param ipcConfigurationService
	 */
	public void setIpcConfigurationService(IpcConfigurationService ipcConfigurationService) {
		this.ipcConfigurationService = ipcConfigurationService;
	}

	/**
	 * Returns the aggregated list of VidyoMobileAccess indicators
	 *
	 * @return mobileAcessDetail aggregated list of Indicators
	 */
	@Override
	public List<Integer> getMobileAccessDetail() {
		List<Integer> mobileAccessDetail = dao.getMobileAccessDetail();
		if (logger.isDebugEnabled()) {
			logger.debug("Mobile Access Detail " + mobileAccessDetail);
		}
		return mobileAccessDetail;
	}

	/**
	 * Returns the list of all TenantIds
	 *
	 * @return
	 */
	@Override
	public List<Integer> getAllTenantIds() {
		return dao.getAllTenantIds();
	}

	@Override
	public boolean isVidyoGatewayControllerDnsExist(String vidyoGatewayControllerDns, int tenantID) {
		if (vidyoGatewayControllerDns == null || vidyoGatewayControllerDns.isEmpty()) {
			return false;
		}

		return dao.isVidyoGatewayControllerDnsExist(vidyoGatewayControllerDns, tenantID);
	}

	/**
	 * Enables/Disables Tenant's Scheduled Room Feature
	 *
	 * @param schRoomAccess
	 * @param tenantId
	 * @return
	 */
	@Override
	public int updateTenantScheduledRoomFeature(int schRoomDisabled, int tenantId) {
		// clear cache
		clearCache(tenantId);
		TransactionHistory transactionHistory = new TransactionHistory();
		Tenant tenant = getTenant(tenantId);
		if (tenant != null) {
			transactionHistory.setTenantName(tenant.getTenantName());
		}

		transactionHistory.setTransactionName(schRoomDisabled == 0 ? "Scheduled Room Feature Disabled - Tenant Level"
				: "Scheduled Room Feature Enabled - Tenant Level");
		transactionHistory.setTransactionParams(
				"Tenant Id - " + tenantId + "; Tenant Name - " + tenant != null ? tenant.getTenantName() : "Unknown");
		int count = dao.updateTenantScheduledRoomFeature(schRoomDisabled, tenantId);
		transactionHistory.setTransactionResult(count == 0 ? "FAILURE" : "SUCCESS");
		transactionHistory.setTransactionTime(Calendar.getInstance().getTime());
		transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
		return count;
	}

	/**
	 * Returns only the callTo tenant ids
	 *
	 * @param tenantId
	 * @return
	 */
	@Override
	public List<Integer> canCallToTenantIds(int tenantId) {
		List<Integer> tenantIds = this.dao.canCallToTenantIds(tenantId);
		return tenantIds;
	}

	/**
	 * Utility to clear cache by invoking AOP intercepted methods
	 *
	 * @param tenantId
	 */
	@Override
	public void clearCache(int tenantId) {
		Tenant tenant = getTenant(tenantId);

		if (tenant == null) {
			return;
		}

		// clear cache by id/name/url/replayurl
		dao.updateTenantById(tenantId);
		dao.updateTenantByName(tenant.getTenantName());
		dao.updateTenantByUrl(tenant.getTenantURL());
		if (tenant.getTenantReplayURL() != null && !tenant.getTenantReplayURL().isEmpty()) {
			String url = tenant.getTenantReplayURL().toLowerCase();
			if (url.contains("http://")) {
				url = url.replace("http://", "");
			} else if (url.contains("https://")) {
				url = url.replace("https://", "");
			}
			dao.updateTenantByReplayUrl(url);
		}
		dao.clearTenantCache(tenantId);
	}

	@Override
	public TenantConfiguration getTenantConfiguration(int tenantId) {
		TenantConfiguration tenantConfiguration = null;

		try {
			tenantConfiguration = tenantConfigurationDao.getTenantConfiguration(tenantId);
		} catch (DataAccessException ex) {
			logger.error("Failed to get TenantConfiguration for tenantId = " + tenantId + ". Error msg : "
					+ ex.getMessage());
		}

		// should always have a config for every tenant, therefore, return the default
		// config
		if (tenantConfiguration == null) {
			tenantConfiguration = new TenantConfiguration();
			tenantConfiguration.setTenantId(tenantId);
		}

		return tenantConfiguration;
	}

	@Override
	public int updateTenantConfiguration(TenantConfiguration tenantConfiguration) {
		int count = 0;
		int tenantId = tenantConfiguration.getTenantId();
		// clear cache
		clearCache(tenantId);
		roomDao.clearRoomCounterCache(tenantId);

		try {
			count = tenantConfigurationDao.updateTenantConfiguration(tenantId, tenantConfiguration);
		} catch (DataAccessException e) {
			logger.error("Failed to update tenant configuration for tenantId = " + tenantId + ". Error msg: "
					+ e.getMessage());
		}
		return count;
	}

	@Override
	public int updateEndpointChatsStatuses(int tenantId, int endpointPrivateChat, int endpointPublicChat) {
		TenantConfiguration tenantConfiguration = getTenantConfiguration(tenantId);
		if (tenantConfiguration == null) {
			logger.error("Failed to update endpoint chats statuses for tenantId = " + tenantId
					+ ". TenantConfiguration is not found.");
			return 0;
		}

		tenantConfiguration.setEndpointPrivateChat(endpointPrivateChat);
		tenantConfiguration.setEndpointPublicChat(endpointPublicChat);

		return updateTenantConfiguration(tenantConfiguration);
	}

	@Override
	public String getTenantReplayURL(int tenantId) {
		String rc = "";
		if (hasTenantReplayComponent(tenantId)) {
			Tenant t = getTenant(tenantId);
			if (t != null) {
				if (t.getTenantReplayURL() != null && !t.getTenantReplayURL().equalsIgnoreCase("")) {
					String url = t.getTenantReplayURL();
					if (url.contains("http") || url.contains("https")) {
						rc = t.getTenantReplayURL();
					} else {
						rc = "http://" + t.getTenantReplayURL();
					}
				}
			}
		}
		return rc;
	}

	public void setTenantRoomsLectureModeState(int tenantId, boolean flag) {
		this.roomDao.setTenantRoomsLectureModeState(tenantId, flag);
	}

	/**
	 * @param ignoreTenantUrls
	 *            the ignoreTenantUrls to set
	 */
	public void setIgnoreTenantUrls(List<String> ignoreTenantUrls) {
		this.ignoreTenantUrls = ignoreTenantUrls;
	}

	/**
	 * @param memberService2
	 *            the memberService2 to set
	 */
	public void setMemberService2(MemberService memberService2) {
		this.memberService2 = memberService2;
	}

	@Override
	public int setTenantRoomAttributes(int tenantID, boolean lectureModeAllowed, boolean waitingRoomsEnabled,
			boolean waitUntilOwnerJoins, boolean lectureModeStrict) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Room Attributes");
		transactionHistory.setTenantName(getTenant(tenantID).getTenantName());
		StringBuilder transactionParams = new StringBuilder();

		// changing room state, need a clean slate, disconnect all conferences for this
		// tenant
		try {
			conferenceService.disconnectAllConferencesForTenant(tenantID);
			transactionParams.append("Save: Disconnect Calls");
		} catch (NoVidyoManagerException e) {
			logger.error("could not disconnect all conferences for tenant, no VidyoManager exception");
		} catch (ConferenceNotExistException e) {
			logger.error("could not disconnect all conferences for tenant, a certain conference no longer exists");
		} catch (NotLicensedException e) {
			logger.error("could not disconnect all conferences for tenant, not licensed exception");
		} catch (ResourceNotAvailableException e) {
			logger.error("could not disconnect all conferences for tenant, resource not found exception");
		}

		TenantConfiguration tenantConfig = getTenantConfiguration(tenantID);

		if (lectureModeAllowed) {
			tenantConfig.setLectureModeAllowed(1);
			transactionParams.append(";Lecture Mode: Enabled");
		} else {
			tenantConfig.setLectureModeAllowed(0);
			transactionParams.append(";Lecture Mode: Disabled");

			waitingRoomsEnabled = false;
			waitUntilOwnerJoins = false;
			lectureModeStrict = false;
		}

		if (waitingRoomsEnabled) {
			tenantConfig.setWaitingRoomsEnabled(1);
			setTenantRoomsLectureModeState(tenantID, true);
			transactionParams.append(";Waiting Rooms: Enabled");
		} else {
			tenantConfig.setWaitingRoomsEnabled(0);
			setTenantRoomsLectureModeState(tenantID, false);
			transactionParams.append(";Waiting Rooms: Disabled");
		}

		if (waitUntilOwnerJoins) {
			tenantConfig.setWaitUntilOwnerJoins(1);
			transactionParams.append(";Wait until owner joins: Enabled");
		} else {
			tenantConfig.setWaitUntilOwnerJoins(0);
			transactionParams.append(";Wait until owner joins: Disabled");
		}

		if (lectureModeStrict) {
			tenantConfig.setLectureModeStrict(1);
			transactionParams.append(";Presenter mode supported endpoints only: Enabled");
		} else {
			tenantConfig.setLectureModeStrict(0);
			transactionParams.append(";Presenter mode supported endpoints only: Disabled");
		}

		int count = updateTenantConfiguration(tenantConfig);
		if (count > 0) {
			transactionHistory.setTransactionResult("SUCCESS");
		} else {
			transactionHistory.setTransactionResult("FAILURE");
		}

		transactionHistory.setTransactionParams(transactionParams.toString());
		transactionService.addTransactionHistoryWithUserLookup(transactionHistory);

		return count;
	}

	@Override
	public int updateSessionExpirationConfig(int tenantId, int sessionExpPeriod) {
		int updateCount = 0;
		try {
			updateCount = tenantConfigurationDao.updateSessionExpConfig(tenantId, sessionExpPeriod);
			TransactionHistory transactionHistory = new TransactionHistory();
			transactionHistory.setTransactionName("UPDATE CLIENT SESSION EXPIRY PERIOD");
			transactionHistory.setTransactionParams(sessionExpPeriod + " Hours");
			transactionHistory.setTransactionResult("SUCCESS");
			transactionService.addTransactionHistoryWithUserAndTenantLookup(transactionHistory);
		} catch (DataAccessException e) {
			logger.error("Error while updating session expiration config", e.getMessage());
		}
		return updateCount;
	}

	@Override
	public int resetTenantConfigPblcRoomSttngs(String publicRoomEnabledGlobal, String publicRoomMaxRoomNoPerUser) {

		if ("0".equalsIgnoreCase(publicRoomEnabledGlobal)) {
			return tenantConfigurationDao.updateTenantCnfPblcRoomSettings(0, 0);
		} else {
			return tenantConfigurationDao
					.updateTenantCnfPblcRoomSettingsResetMax(Integer.valueOf(publicRoomMaxRoomNoPerUser));
		}

	}

	@Override
	public int updateTenantCnfUserAttributes(String userImage, String userImageUpload) {

		if ("0".equalsIgnoreCase(userImage)) {
			return tenantConfigurationDao.updateTenantCnfUserAttributes(userImage,
					userImageUpload == null ? "0" : userImageUpload);
		} else {
			if ("0".equalsIgnoreCase(userImageUpload)) {
				return tenantConfigurationDao.updateTenantCnfUserAttrUsrUploadUsage(userImageUpload);
			}
		}
		return 1;
	}

	@Override
	public int updateTenantAllLogAggregation(int logAggregation) {
		return tenantConfigurationDao.updateTenantAllLogAggregation(logAggregation);
	}

	@Override
	public int updateTenantAllCustomRole(int customRoleFlag) {
		return tenantConfigurationDao.updateTenantAllCustomRole(customRoleFlag);
	}

	@Override
	@Transactional
	public int updateTenantAllEpicIntegration(int epicFlag) {
		return tenantConfigurationRepository.updateTenantAllEpicIntegration(epicFlag);
	}
	
	@Override
	@Transactional
	public int updateTenantAllTytoCareIntegration(int tytocareFlag) {
		return tenantConfigurationRepository.updateTenantAllTytoCareIntegration(tytocareFlag);
	}
}