/**
 * VidyoPortalSuperServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.vidyo.portal.superapi;

import com.vidyo.bo.*;
import com.vidyo.bo.ipcdomain.IpcDomain;
import com.vidyo.bo.networkconfig.RouterPool;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.dao.BaseQueryFilter;
import com.vidyo.framework.security.htmlcleaner.AntiSamyHtmlCleaner;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.framework.util.RegExValidator;
import com.vidyo.framework.util.ValidatorUtil;
import com.vidyo.replay.update.ReplayUpdateParamServiceStub;
import com.vidyo.service.*;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.bo.VidyoManager;
import com.vidyo.superapp.components.bo.VidyoRecorder;
import com.vidyo.superapp.components.bo.VidyoReplay;
import com.vidyo.superapp.components.service.ComponentsService;
import com.vidyo.superapp.routerpools.bo.Pool;
import com.vidyo.superapp.routerpools.service.RouterPoolsService;
import com.vidyo.utils.CompressionUtils;
import com.vidyo.utils.SecurityUtils;
import com.vidyo.utils.ValidationUtils;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
     *  VidyoPortalSuperServiceSkeleton java skeleton for the axisService
     */
public class VidyoPortalSuperServiceSkeleton implements VidyoPortalSuperServiceSkeletonInterface{

    protected static final Logger logger = LoggerFactory.getLogger(VidyoPortalSuperServiceSkeleton.class.getName());

    //changing the name as the db type name changed in the new table configuration.No changes in the flow. We still skipping VidyoRecorder
    private static final String COMPONENT_TYPE_VIDYO_REPLAY_RECORDER = "VidyoRecorder";
    private static final String TENANT_DEFAULT_SORTING_DIR = "ASC";
    private static final String TENANT_SORTING_DEFAULT_FIELD = "tenantName";
    private static final String TENANT_SORTING_TENANT_URL = "tenantURL";
    private static final String TENANT_SORTING_TENANT_PREFIX = "tenantPrefix";
    private static final String IPC_ACCESS_CONTROL_LEVEL_TENANT = "admin";
    private static final String IPC_ACCESS_CONTROL_LEVEL_SYSTEM = "super";

    private static final int DEFAULT_TENANT_ID = 1;

    private static final int BANNER_TEXT_MAX_LENGTH = 4000;

	private static final String ACTIVE = "ACTIVE";

    private LicensingService license;
    private ITenantService tenantService;
    private IServiceService serviceService;
    @Autowired
    private ComponentsService componentsService;
    @Autowired
	RouterPoolsService routerPoolService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private EndpointUploadService endpointUploadService;
    @Autowired
    private TransactionService transaction;

    @Resource
    private WebServiceContext webserviceContext;

    public void setRouterPoolService(RouterPoolsService routerPoolService) {
		this.routerPoolService = routerPoolService;
	}

	private ISystemService systemService;
    private IpcDomainService ipcDomainService;
    private IMemberService memberService;
    private IUserService userService;
	private ISecurityService securityService;

	private String uploadTempDirSuper;

	public String getUploadTempDirSuper() {
		return uploadTempDirSuper;
	}

	public void setUploadTempDirSuper(String uploadTempDirSuper) {
		this.uploadTempDirSuper = uploadTempDirSuper;
	}


	public void setLicense(LicensingService license) {
    	this.license = license;
    }

    public void setCompService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}

	public void setTenantService(ITenantService tenantService) {
    	this.tenantService = tenantService;
    }

    public void setServiceService(IServiceService serviceService) {
    	this.serviceService = serviceService;
    }

    public void setSystemService(ISystemService systemService) {
    	this.systemService = systemService;
    }

    public void setIpcDomainService(IpcDomainService ipcDomainService) {
        this.ipcDomainService = ipcDomainService;
    }

    public void setMemberService(IMemberService memberService) {
    	this.memberService = memberService;
    }

	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

    public void setUserService(IUserService userService) {
    	this.userService = userService;
    }

    public IRoomService getRoomService() {
		return roomService;
	}

	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	/**
      * Auto generated method signature
      * Provides license data. If a tenant is not specified, the license parameters should be provided for all tenants as a Total number
      * @param getLicenseDataRequest0
      * @return getLicenseDataResponse1
      * @throws NotLicensedFaultException
      * @throws GeneralFaultException
      * @throws NotAuthorizedFaultException
      * @throws InvalidTenantFaultException
      */

    public GetLicenseDataResponse getLicenseData(GetLicenseDataRequest getLicenseDataRequest0)
          throws NotLicensedFaultException,GeneralFaultException,NotAuthorizedFaultException,InvalidTenantFaultException {

    	if (logger.isDebugEnabled()) {
    		logger.debug("Entering getLicenseData() of VidyoPortalSuperServiceSkeleton");
    	}

        boolean isPortalAPIAccessEnabled = this.license.isPortalAPIAccessEnabled();
    	if (!isPortalAPIAccessEnabled) {
    		String errMsg = "Portal API Access is not licensed";
    		logger.error(errMsg);
    		NotLicensedFault fault = new NotLicensedFault();
			fault.setErrorMessage(errMsg);
			NotLicensedFaultException ex = new NotLicensedFaultException(fault.getErrorMessage());
			ex.setFaultMessage(fault);
			throw ex;
    	}

    	List<SystemLicenseFeature> licenseFeatures;
    	List<String> licenseNames = new ArrayList<String>();
    	String licVersion = null;
    	SystemLicenseFeature licFeature = new SystemLicenseFeature(null, null, null);
    	if(getLicenseDataRequest0.getTenantId() == null) {
    		try {
    			licenseFeatures = license.getSystemLicenseDetails();
    		} catch (Exception e) {
    			logger.error(e.getMessage());
    			GeneralFault fault = new GeneralFault();
    			fault.setErrorMessage(e.getMessage());
    			GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
    			ex.setFaultMessage(fault);
    			throw ex;
    		}

    		licVersion = getLicenseVersion(licenseFeatures);
    		if(LicensingServiceImpl.LIC_VERSION_20.equals(licVersion)) {
    			licenseNames.add(LicensingServiceImpl.LIC_SEAT_START_DATE);
    			licenseNames.add(LicensingServiceImpl.LIC_SEAT_EXIRY_DATE);

    		} else if (LicensingServiceImpl.LIC_VERSION_21.equals(licVersion) || LicensingServiceImpl.LIC_VERSION_22.equals(licVersion)) {
    			licenseNames.add(LicensingServiceImpl.LIC_LIMIT_TYPE_EXECUTIVE_SYSTEM);
    			licenseNames.add(LicensingServiceImpl.LIC_LIMIT_TYPE_PANORAMA_SYSTEM);
    		}
    		licenseNames.add(LicensingServiceImpl.LIC_START_DATE);
    		licenseNames.add(LicensingServiceImpl.LIC_EXPIRY_DATE);
    		licenseNames.add(LicensingServiceImpl.LIC_VM_SOAP_USER);
    		licenseNames.add(LicensingServiceImpl.LIC_VM_SOAP_PASS);
    		licenseNames.add(LicensingServiceImpl.LIC_SEATS);
    		licenseNames.add(LicensingServiceImpl.LIC_PORTS);
    		licenseNames.add(LicensingServiceImpl.LIC_INSTALLS);
    		licenseNames.add(LicensingServiceImpl.LIC_ENCRIPTION);
    		licenseNames.add(LicensingServiceImpl.LIC_MULTITENANT);
    		licenseNames.add(LicensingServiceImpl.LIC_ALLOW_OCS);
    		licenseNames.add(LicensingServiceImpl.LIC_SN);
    		licenseNames.add(LicensingServiceImpl.LIC_LICENSEE_EMAIL);

    		licFeature.setName(LicensingServiceImpl.LIC_EVENT_LICENSE_EXPIRY);
    		if(licenseFeatures.contains(licFeature)) {
    			licenseNames.add(LicensingServiceImpl.LIC_EVENT_LICENSE_PORTS);
    			licenseNames.add(LicensingServiceImpl.LIC_EVENT_LICENSE_EXPIRY);
    		}

    		if(isAnyAPILicensed(licenseFeatures)) {
    			licenseNames.add(LicensingServiceImpl.LIC_ALLOW_PORTAL_APIS);
    			licenseNames.add(LicensingServiceImpl.LIC_ALLOW_USER_APIS);
    			licenseNames.add(LicensingServiceImpl.LIC_ALLOW_EXT_DB);
    		}
    	} else {
    		int tenantId = getLicenseDataRequest0.getTenantId().getEntityID();
    		Tenant tenant = tenantService.getTenant(tenantId);
    		if (tenantId <= 0 || tenant == null) {
    			InvalidTenantFault fault = new InvalidTenantFault();
    			fault.setErrorMessage("Invalid tenantID");
    			InvalidTenantFaultException ex = new InvalidTenantFaultException(fault.getErrorMessage());
    			ex.setFaultMessage(fault);
    			throw ex;
    		}
    		licenseFeatures = license.getTenantLicense(tenantId);

    		licVersion = getLicenseVersion(licenseFeatures);

    		licenseNames.add(LicensingServiceImpl.LIC_START_DATE);
    		licenseNames.add(LicensingServiceImpl.LIC_EXPIRY_DATE);
    		licenseNames.add(LicensingServiceImpl.LIC_SEATS);
    		licenseNames.add(LicensingServiceImpl.LIC_PORTS);
    		licenseNames.add(LicensingServiceImpl.LIC_INSTALLS);

    		if(LicensingServiceImpl.LIC_VERSION_20.equals(licVersion)) {
    			licenseNames.add(LicensingServiceImpl.LIC_SEAT_START_DATE);
    			licenseNames.add(LicensingServiceImpl.LIC_SEAT_EXIRY_DATE);

    		} else if (LicensingServiceImpl.LIC_VERSION_21.equals(licVersion) || LicensingServiceImpl.LIC_VERSION_22.equals(licVersion)) {
    			licenseNames.add(LicensingServiceImpl.LIC_LIMIT_TYPE_EXECUTIVE_SYSTEM);
    			licenseNames.add(LicensingServiceImpl.LIC_LIMIT_TYPE_PANORAMA_SYSTEM);
    		}
    	}

    	List<LicenseFeatureData> licenseFeatureDatas = new ArrayList<LicenseFeatureData>(licenseNames.size());
    	for (SystemLicenseFeature feature : licenseFeatures) {
			if (licenseNames.contains(feature.getName())) {
				LicenseFeatureData licenseFeatureData = new LicenseFeatureData();
				if (feature.getName().equals(LicensingServiceImpl.LIC_PORTS) && (licVersion.equals(LicensingServiceImpl.LIC_VERSION_21) || licVersion.equals(LicensingServiceImpl.LIC_VERSION_22))) {
					licenseFeatureData.setName("Lines");
				} else {
					licenseFeatureData.setName(feature.getName());
				}
				if(feature.getName().equalsIgnoreCase(LicensingServiceImpl.LIC_ENCRIPTION) && feature.getLicensedValue() == null) {
					licenseFeatureData.setMaxValue(LicensingServiceImpl.LIC_ENCRIPTION_NONE);
				} else {
					licenseFeatureData.setMaxValue(feature.getLicensedValue());
				}
				licenseFeatureData.setCurrentValue(feature.getCurrentValue());

				licenseFeatureDatas.add(licenseFeatureData);
			}
		}

    	LicenseFeatureData [] licenseFeatureDataArr = new LicenseFeatureData[licenseFeatureDatas.size()];
    	licenseFeatureDataArr = licenseFeatureDatas.toArray(licenseFeatureDataArr);

    	GetLicenseDataResponse getLicenseDataResponse = new GetLicenseDataResponse();
    	getLicenseDataResponse.setLicenseFeature(licenseFeatureDataArr);


    	if (logger.isDebugEnabled()) {
    		logger.debug("Exiting getLicenseData() of VidyoPortalSuperServiceSkeleton");
    	}

    	return getLicenseDataResponse;
    }

    /**
      * Auto generated method signature
      * List all network components
      * @param listNetworkComponentsRequest2
      * @return listNetworkComponentsResponse3
      * @throws GeneralFaultException
      */
    public ListNetworkComponentsResponse listNetworkComponents(ListNetworkComponentsRequest listNetworkComponentsRequest2)
			throws GeneralFaultException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering listNetworkComponents() of VidyoPortalSuperServiceSkeleton");
		}

		try {
			String name = listNetworkComponentsRequest2.getComponentName();
			if (name == null) {
				name = "";
			}
			SystemComponentType systemComponentType = listNetworkComponentsRequest2.getComponentType();
			String type = null;
			if (systemComponentType == null	) {
				type = "";
			} else {
				type = systemComponentType.getValue();
			}
			int isAlarm = 0;
			ComponentStatus compStatus = listNetworkComponentsRequest2.getStatus();
			String status = "";

			List<Component> components = componentsService.findAllComponents(
					name, type, status,isAlarm);
			Date systemDate = new Date();
			logger.trace("Exiting getAllComponents");
			ListNetworkComponentsResponse resp = new ListNetworkComponentsResponse();

			Iterator<Component> it = components.iterator();
			ComponentStatus retStatus = null;
			SystemComponentType retSystemComponentType = null;
			while (it.hasNext()) {
				Component nec = it.next();

				if (!nec.getCompType().getName()
						.equalsIgnoreCase(COMPONENT_TYPE_VIDYO_REPLAY_RECORDER)) {
					if (nec.getStatus().equals("ACTIVE")) {

						Date heartBeatDate = nec.getHeartbeatTime();

						if (nec.getHeartbeatTime() == null) {
							retStatus = ComponentStatus.DOWN;
						} else {
							int secondsBetween = (int) ((systemDate.getTime() - heartBeatDate
									.getTime()) / DateUtils.MILLIS_PER_SECOND);

							if (secondsBetween > 30) {
								retStatus = ComponentStatus.DOWN;
							} else {
								retStatus = ComponentStatus.UP;
							}
						}
					} else if (nec.getStatus().equals("NEW")) {
						retStatus = ComponentStatus.NEW;
					} else if (nec.getStatus().equals("INACTIVE")) {
						retStatus = ComponentStatus.DISABLED;
					}

					if (compStatus == null
							|| compStatus.getValue().equalsIgnoreCase(
									retStatus.getValue())) {
						SingleComponentDataType singleComponentDataType = new SingleComponentDataType();

						singleComponentDataType.setIdentifier(nec.getCompID());
						singleComponentDataType.setDisplayName(nec.getName());

						retSystemComponentType = SystemComponentType.Factory
								.fromValue(nec.getCompType().getName());
						singleComponentDataType
								.setComponentType(retSystemComponentType);

						singleComponentDataType.setIpAddress(nec.getLocalIP());
						singleComponentDataType.setAlarm(nec.getAlarm());
						singleComponentDataType.setSwVer(nec
								.getCompSoftwareVersion());

						if (!retSystemComponentType.getValue()
								.equalsIgnoreCase(
										SystemComponentType.VidyoGateway
												.getValue())) {
							singleComponentDataType.setRunningVersion(nec
									.getRunningVersion());
							singleComponentDataType.setVersion(nec
									.getConfigVersion());
						}

						singleComponentDataType.setComponentStatus(retStatus);

						resp.addNetworkComponent(singleComponentDataType);
					}
				}

			}

			if (logger.isDebugEnabled()) {
				logger.debug("Exiting listNetworkComponents() of VidyoPortalSuperServiceSkeleton");
			}

			return resp;
		} catch (Exception anyEx) {
			logger.error("Failed to retrieve components list", anyEx);
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Failed to retrieve components list");
			GeneralFaultException ex = new GeneralFaultException(
					fault.getErrorMessage());
			ex.setFaultMessage(fault);
			throw ex;
		}
	}

    /**
      * Auto generated method signature
      * Provides the list of available service components. If the type is not specified, all lists will be provided. If the type is specified, only the list associated with this type will be provided
      * @param getServiceComponentsDataRequest0
      * @return getServiceComponentsDataResponse1
      * @throws InvalidArgumentFaultException
      * @throws GeneralFaultException
      */

    public GetServiceComponentsDataResponse getServiceComponentsData(GetServiceComponentsDataRequest getServiceComponentsDataRequest0)
       throws InvalidArgumentFaultException,GeneralFaultException{
        if (logger.isDebugEnabled()) {
           logger.debug("Entering getServiceComponentsData() of VidyoPortalSuperServiceSkeleton");
        }

        try {
           ServiceComponentType serviceComponentType = getServiceComponentsDataRequest0.getServiceComponentType();

           List<Component> components = null;
           if(serviceComponentType == null) {
        	   components = componentsService.getAllComponents();
           } else {
              components=  componentsService.getAllComponentsByType(getServiceComponentsDataRequest0.getServiceComponentType().getValue());
           }

           GetServiceComponentsDataResponse response = new GetServiceComponentsDataResponse();
           ComponentData componentData = null;
           String compTypeName = null;
           for (Component comp : components) {
              componentData = new ComponentData();
              componentData.setIdentifier(String.valueOf(comp.getId()));
              compTypeName = comp.getCompType().getName();
              try{
            	  if (comp.getCompType().getName() != null && comp.getCompType().getName().equalsIgnoreCase("VidyoRouter")){
            		  compTypeName = "VidyoProxy";
          		}
            	  componentData.setComponentType(ServiceComponentType.Factory.fromValue(compTypeName));
            	  compTypeName = null;
              }catch(Exception e){
            	  //the way it is being done is erroneous so adding a try catch and in case if any error happen it will skip the loop but not complete failure-  The real fix is correcting in wsdl-Right now VidyoRouter is not defined as ServiceComponentType
            	  continue;
              }
              componentData.setDisplayName(comp.getName());
                 response.addComponent(componentData);
           }
           if (logger.isDebugEnabled()) {
              logger.debug("Exiting getServiceComponentsData() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
        } catch(Exception anyEx) {
            logger.error("Failed to get service components data.", anyEx);
            GeneralFault fault= new GeneralFault();
            fault.setErrorMessage("Failed to get service components data.");
            GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }
    }

    /**
      * Auto generated method signature
      * Provides the list of all tenants in the system with the following filters. All filters are optional and if a filter is not provided the query will use the defaults, which are sorting in a descending order all the tenants by the tenant names.
      * @param listTenantsRequest2
      * @return listTenantsResponse3
      * @throws GeneralFaultException
      */
    public ListTenantsResponse getListOfTenants(ListTenantsRequest listTenantsRequest2)
       throws GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering listTenants() of VidyoPortalSuperServiceSkeleton");
        }

        try {
           TenantFilter filter = new TenantFilter();

           String tenantName = listTenantsRequest2.getTenantName();
           if(tenantName != null) {
              filter.setTenantName(tenantName);
           }

           String tenantUrl = listTenantsRequest2.getTenantURL();
           if(tenantUrl != null) {
              filter.setTenantURL(tenantUrl);
           }

           if(listTenantsRequest2.getDir() == null) {
              filter.setDir(TENANT_DEFAULT_SORTING_DIR);
           } else {
              filter.setDir(listTenantsRequest2.getDir().getValue());
           }

           TenantSortingField tenantSortingField = listTenantsRequest2.getSortBy();
           if(tenantSortingField == null) {
              filter.setSort(TENANT_SORTING_DEFAULT_FIELD);
           } else {
              if(tenantSortingField.equals(TenantSortingField.tenantName)) {
                 filter.setSort(TENANT_SORTING_DEFAULT_FIELD);
              } else if(tenantSortingField.equals(TenantSortingField.extensionPrefix)) {
                 filter.setSort(TENANT_SORTING_TENANT_PREFIX);
              } else if(tenantSortingField.equals(TenantSortingField.tenantURL)){
                 filter.setSort(TENANT_SORTING_TENANT_URL);
              }
           }

           if(listTenantsRequest2.getStart() != null && listTenantsRequest2.getStart().getIntHolder() >= 0) {
              filter.setStart(listTenantsRequest2.getStart().getIntHolder());
           } else if(listTenantsRequest2.getStart() != null && listTenantsRequest2.getStart().getIntHolder() < 0) {
        	   throw new Exception("Invalid start value (It should start from 1).");
           } else {
        	   filter.setStart(0);
           }

           if(listTenantsRequest2.getLimit() != null) {
               int limit = listTenantsRequest2.getLimit().getIntHolder();
               if(limit > BaseQueryFilter.FILTER_MAX_LIMIT) {
                   throw new Exception("Requested limit exceeds the allowed threshold of " + BaseQueryFilter.FILTER_MAX_LIMIT + " entries. Please try again.");
               } else if(limit > BaseQueryFilter.FILTER_DEFAULT_LIMIT){
                   logger.info("ATTENTION!!! Usage of limit equal to " + limit + " MAY HAVE performance impact");
               }
               filter.setLimit(limit);
           }

           List<Tenant> tenants = tenantService.getTenants(filter);

           ListTenantsResponse response = new ListTenantsResponse();
           SingleTenantDataType singleTenantDataType = null;
           for(Tenant tenant : tenants) {
              singleTenantDataType = new SingleTenantDataType();

              String description = tenant.getDescription();
              if(description != null && description.length() > 0) {
                 singleTenantDataType.setDescription(description);
              }

              String dialIn = tenant.getTenantDialIn();
              if(dialIn != null && dialIn.length() > 0) {
                 singleTenantDataType.setDialinNumber(dialIn);
              }

              singleTenantDataType.setExtensionPrefix(tenant.getTenantPrefix());

              EntityID tenantId = new EntityID();
              tenantId.setEntityID(tenant.getTenantID());
              singleTenantDataType.setTenantId(tenantId);

              singleTenantDataType.setTenantName(tenant.getTenantName());

              singleTenantDataType.setVidyoMobileAllowed(tenant.getMobileLogin());

              String replayUrl = tenant.getTenantReplayURL();
              if(replayUrl != null && replayUrl.length() > 0) {
                 singleTenantDataType.setVidyoReplayUrl(replayUrl);
              }

              String dbTenantUrl = tenant.getTenantURL();
              if(dbTenantUrl != null && dbTenantUrl.length() > 0) {
                 singleTenantDataType.setTenantURL(dbTenantUrl);
              }

              response.addTenant(singleTenantDataType);
           }

           if(logger.isDebugEnabled()) {
               logger.debug("Exiting listTenants() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
        } catch(Exception anyEx) {
            logger.error("Failed to get list of tenants.", anyEx);
            GeneralFault fault= new GeneralFault();
            fault.setErrorMessage("Failed to get list of tenants");
            GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }
    }

    /**
      * Auto generated method signature
      * Create a new tenant based on the information provided below.
      * @param createTenantRequest6
      * @return createTenantResponse7
      * @throws NotLicensedFaultException
      * @throws InvalidArgumentFaultException
      * @throws ExistingTenantFaultException
      * @throws GeneralFaultException
      */
    public CreateTenantResponse createTenant (CreateTenantRequest createTenantRequest6)
       throws NotLicensedFaultException,InvalidArgumentFaultException,ExistingTenantFaultException,GeneralFaultException{
        if (logger.isDebugEnabled()) {
            logger.debug("Entering createTenant() of VidyoPortalSuperServiceSkeleton");
        }

        if(!tenantService.isMultiTenant()) {
            logger.error("Not multi tenant.");
            NotLicensedFault fault = new NotLicensedFault();
            fault.setErrorMessage("Not multi tenant");
            NotLicensedFaultException ex = new NotLicensedFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }

        String licenseVersion = license.getSystemLicenseFeature(LicensingServiceImpl.LIC_VERSION).getLicensedValue();
        if(!(licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_21) || licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_22))) {
           logger.error("License version is not correct.");
           NotLicensedFault fault = new NotLicensedFault();
           fault.setErrorMessage("License version is not correct");
           NotLicensedFaultException ex = new NotLicensedFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }

        // Validate input parameters
        final int TENANT_ID = 0;
        TenantDataType tenantData = createTenantRequest6.getTenantData();
        validateTenantDataType(TENANT_ID, tenantData);

        try {
           Tenant tenant = makeTenantFromTenantDataType(0, tenantData, null);

           AdminMember adminMember = tenantData.getAdminUser();

           Member adminUser = makeMemberFromAdminMember(adminMember, tenant);


           int tenantId = tenantService.insertTenant(tenant, adminUser);
            if (tenantId == -1 ) {
                logger.error(" Insert Tenant failed, Password for default tenant Admin user did not passed requirements check");
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Failed to create tenant, default admin  user did not meet password requirements");
                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }
           //super-tenant's system lang
           systemService.saveSystemLang(tenantId, systemService.getSystemLang(1).getLangCode());

           //update VidyoProxy
           if(tenantData.getDefaultGuestLocationTag()!=null){
        	  int i= systemService.saveDefaultGuestLocationTag(tenantId,String.valueOf(tenantData.getDefaultGuestLocationTag()));
        	  if(logger.isDebugEnabled()) {
        	  logger.debug("status after updating DefaultGuestLocationTag "+i );
        	  }

           }
           //update guest location
           if(tenantData.getDefaultGuestProxy()!=null){
        	   int i=systemService.saveDefaultGuestProxy(tenantId,String.valueOf(tenantData.getDefaultGuestProxy()));
        	   if(logger.isDebugEnabled()) {
        	   logger.debug("status after updating DefaultGuestProxy "+i );
        	   }
           }




           CreateTenantResponse response = new CreateTenantResponse();
           response.setOK(OK_type0.OK);
           if(logger.isDebugEnabled()) {
              logger.debug("Exiting createTenant() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
        } catch(InvalidArgumentFaultException illEx) {
           throw illEx;
        } catch(Exception anyEx) {
            logger.error("Failed to create tenant.", anyEx);
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Failed to create tenant");
            GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }
    }

    /**
      * Auto generated method signature
      * Deleting an existing tenant from the system. The default tenant can not be deleted.
      * @param deleteTenantRequest4
      * @return deleteTenantResponse5
      * @throws GeneralFaultException
      * @throws InvalidTenantFaultException
      */
    public DeleteTenantResponse deleteTenant(DeleteTenantRequest deleteTenantRequest4)
       throws GeneralFaultException,InvalidTenantFaultException {
        if(logger.isDebugEnabled()) {
           logger.debug("Entering deleteTenant() of VidyoPortalSuperServiceSkeleton");
        }

        int tenantId = deleteTenantRequest4.getTenantId().getEntityID();
        if(tenantId <= DEFAULT_TENANT_ID) {
           logger.error("The specified Tenant ID doesn't exist or the user is trying to delete the default tenant.");
           InvalidTenantFault fault = new InvalidTenantFault();
           fault.setErrorMessage("The specified Tenant ID doesn't exist or the user is trying to delete the default tenant.");
           InvalidTenantFaultException ex = new InvalidTenantFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }

        try {
           Tenant tenant = null;
           tenant = tenantService.getTenant(tenantId);
           if(tenant == null) {
               logger.error("The specified Tenant ID doesn't exist or the user is trying to delete the default tenant.");
               InvalidTenantFault fault = new InvalidTenantFault();
               fault.setErrorMessage("The specified Tenant ID doesn't exist or the user is trying to delete the default tenant.");
               InvalidTenantFaultException ex = new InvalidTenantFaultException(fault.getErrorMessage());
               ex.setFaultMessage(fault);
               throw ex;
           }

           tenantService.deleteTenant(tenantId);

           DeleteTenantResponse response = new DeleteTenantResponse();
           response.setOK(OK_type0.OK);
           if(logger.isDebugEnabled()) {
              logger.debug("Exiting deleteTenant() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
        } catch(InvalidTenantFaultException itfEx) {
           throw itfEx;
        } catch(Exception anyEx) {
           logger.error("Failed to delete tenant.", anyEx);
           GeneralFault fault = new GeneralFault();
           fault.setErrorMessage("Failed to delete tenant.");
           GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }
    }

    /**
      * Auto generated method signature
      * Get the tenant details based on the tenant ID.
      * @param getTenantDetailsRequest2
      * @return getTenantDetailsResponse3
      * @throws GeneralFaultException
      * @throws InvalidTenantFaultException
      */

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public GetTenantDetailsResponse getTenantDetails(GetTenantDetailsRequest getTenantDetailsRequest2)
       throws GeneralFaultException,InvalidTenantFaultException{
        if(logger.isDebugEnabled()) {
           logger.debug("Entering getTenantDetails() of VidyoPortalSuperServiceSkeleton");
        }

        int tenantId = getTenantDetailsRequest2.getTenantId().getEntityID();
        if(tenantId < DEFAULT_TENANT_ID) {
           logger.error("Wrong tenantID to get tenant details.");
           InvalidTenantFault fault = new InvalidTenantFault();
           fault.setErrorMessage("Wrong tenantID to get tenant details");
           InvalidTenantFaultException ex = new InvalidTenantFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }

        try {
           Tenant tenant = null;
           tenant = tenantService.getTenant(tenantId);
           if(tenant == null) {
              logger.error("Wrong tenantID to get tenant details.");
              InvalidTenantFault fault = new InvalidTenantFault();
              fault.setErrorMessage("Wrong tenantID to get tenant details");
              InvalidTenantFaultException ex = new InvalidTenantFaultException(fault.getErrorMessage());
              ex.setFaultMessage(fault);
              throw ex;
           }

           GetTenantDetailsResponse response = makeGetTenantDetailsResponseFromTenant(tenant);

           if(logger.isDebugEnabled()) {
              logger.debug("Exiting getTenantDetails() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
        } catch (InvalidTenantFaultException itfEx){
           throw itfEx;
        } catch(Exception anyEx) {
           logger.error("Failed to get tenant details.", anyEx);
           GeneralFault fault = new GeneralFault();
           fault.setErrorMessage("Failed to get tenant details.");
           GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }
    }

    /**
      * Auto generated method signature
      * Update an existing tenant by the tenant ID. Each optional parameter that is provided aside from the tenant ID will overwrite the existing parameters.
      * @param updateTenantRequest14
      * @return updateTenantResponse15
      * @throws NotLicensedFaultException
      * @throws InvalidArgumentFaultException
      * @throws ExistingTenantFaultException
      * @throws GeneralFaultException
      */
    public UpdateTenantResponse updateTenant(UpdateTenantRequest updateTenantRequest14)
       throws NotLicensedFaultException,InvalidArgumentFaultException,ExistingTenantFaultException,GeneralFaultException,InvalidTenantFaultException{
        if (logger.isDebugEnabled()) {
           logger.debug("Entering updateTenant() of VidyoPortalSuperServiceSkeleton");
        }

        TenantDataExtType tenantData = updateTenantRequest14.getTenantData();

        int tenantId = tenantData.getTenantID().getEntityID();
        if(tenantId < DEFAULT_TENANT_ID) {
           logger.error("Wrong tenantID to update.");
           InvalidTenantFault fault = new InvalidTenantFault();
           fault.setErrorMessage("Wrong tenantID to update");
           InvalidTenantFaultException ex = new InvalidTenantFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }

        Tenant oldTenant = null;
        oldTenant = tenantService.getTenant(tenantId);
        if(oldTenant == null) {
           logger.error("Wrong tenant to update.");
           InvalidTenantFault fault = new InvalidTenantFault();
           fault.setErrorMessage("Wrong tenantID to update");
           InvalidTenantFaultException ex = new InvalidTenantFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }

        // Validate input parameters
        validateTenantDataType(tenantId, tenantData);

        Tenant tenant = makeTenantFromTenantDataType(tenantId, tenantData, oldTenant);

        // Since the UpdateTenant API hasn't got any Web RTC yet,
        // if there is any web RTC url added from UI, use that to pass it on with the update
        /*if (oldTenant.getTenantWebRTCURL() != null && StringUtils.isNotBlank(oldTenant.getTenantWebRTCURL())){
        	tenant.setTenantWebRTCURL(oldTenant.getTenantWebRTCURL());
        }*/

        AdminMember adminMember = tenantData.getAdminUser();
        Member oldAdminUser = null;
        if(adminMember != null) {
            try {
                oldAdminUser = memberService.getMemberByName(adminMember.getName(), tenantId);
                if(oldAdminUser == null || !MemberRoleEnum.ADMIN.getMemberRole().equalsIgnoreCase(oldAdminUser.getRoleName())) {
                    String errMsg = "Admin user " + adminMember.getName() + " is not found.";
                    logger.error(errMsg);
                    InvalidArgumentFault fault = new InvalidArgumentFault();
                    fault.setErrorMessage(errMsg);
                    InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                    ex.setFaultMessage(fault);
                    throw ex;
                }

                Member adminUser = makeMemberFromAdminMember(adminMember, tenant);

                oldAdminUser.setUsername(adminUser.getUsername());
                oldAdminUser.setMemberName(adminUser.getMemberName());
                oldAdminUser.setEmailAddress(adminUser.getEmailAddress());
                oldAdminUser.setDescription(adminUser.getDescription());
    	    	try {
    	    		String pswd = adminMember.getPassword();
    	    		if(pswd != null && !pswd.isEmpty()) {
    	    			String encodedNewPassword = PasswordHash.createHash(pswd);
    	    			if(!encodedNewPassword.equals(oldAdminUser.getPassword()) &&
    	    					!memberService.isValidMemberPassword(pswd, oldAdminUser.getMemberID())) {
    	    				InvalidArgumentFault fault = new InvalidArgumentFault();
    	    				fault.setErrorMessage("Password does not meet requirements");
    	    				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
    	    				exception.setFaultMessage(fault);
    	    				throw exception;
    	    			}
    	    			oldAdminUser.setPassword(encodedNewPassword);
    	    		} else {
    	    			oldAdminUser.setPassword(null);
    	    		}
    	    	}catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
    	    		logger.error("Exception while encoding the member Password");
    	    		GeneralFault fault = new GeneralFault();
    	    		fault.setErrorMessage("Failed while encoding the member Password");
    	    		GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
    	    		exception.setFaultMessage(fault);
    	    		throw exception;
    	    	}
            } catch(DataAccessException e) {
                String errMsg = "Admin user " + adminMember.getName() + " is not found.";
                logger.error(errMsg);
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage(errMsg);
                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }catch (InvalidArgumentFaultException e) {
                throw e;
            } catch (Exception e) {
                logger.error("Failed to update tenant.", e);
                GeneralFault fault = new GeneralFault();
                fault.setErrorMessage("Failed to update tenant");
                GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }
        }

		ReplayUpdateParamServiceStub replayUpdate = null;

		try {
			replayUpdate = this.systemService.getReplayUpdateParamServiceWithAUTH(tenantId);
			com.vidyo.replay.update.UpdateTenantRequest req = new com.vidyo.replay.update.UpdateTenantRequest();
			req.setOldName(oldTenant.getTenantName());
			req.setNewName(tenant.getTenantName());
			replayUpdate.updateTenant(req);
		} catch (Exception ignored) {
           logger.error("Exception while updating Replay. The exception is ignored.", ignored);
		} finally {
			if (replayUpdate != null) {
				try {
					replayUpdate.cleanup();
				} catch (AxisFault af) {
					// ignore
				}
			}
		}

		if(oldAdminUser != null && adminMember != null) {

		}

        try {
            tenantService.updateTenantAPI(tenantId, oldTenant, tenant, oldAdminUser);

            //super-tenant's system lang
            systemService.saveSystemLang(tenantId, systemService.getSystemLang(1).getLangCode());
            //update guest location
            if(tenantData.getDefaultGuestLocationTag()!=null){
         	  int i= systemService.saveDefaultGuestLocationTag(tenantId,String.valueOf(tenantData.getDefaultGuestLocationTag()));
         	  if(logger.isDebugEnabled()) {
         	  logger.debug("status after updating DefaultGuestLocationTag "+i );
         	  }

            }
            //update vidyoproxy
            if(tenantData.getDefaultGuestProxy()!=null){
         	   int i=systemService.saveDefaultGuestProxy(tenantId,String.valueOf(tenantData.getDefaultGuestProxy()));
         	   if(logger.isDebugEnabled()) {
         	   logger.debug("status after updating DefaultGuestProxy "+i );
         	   }
            }
            UpdateTenantResponse response = new UpdateTenantResponse();
            response.setOK(OK_type0.OK);

            if(logger.isDebugEnabled()) {
                logger.debug("Exiting updateTenant() of VidyoPortalSuperServiceSkeleton");
            }

            return response;
         } catch(Exception anyEx) {
            logger.error("Failed to update tenant.", anyEx);
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Failed to update tenant");
            GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
         }
    }

	/**
      * Auto generated method signature
      * Define IPC control access (Allow / Block) for Tenant and System levels.
      * @param setIpcAccessControlRequest16
      * @return setIpcAccessControlResponse17
      * @throws MissingArgumentFaultException
      * @throws GeneralFaultException
      */

    public SetIpcAccessControlResponse setIpcAccessControl(SetIpcAccessControlRequest setIpcAccessControlRequest16)
       throws InvalidArgumentFaultException,MissingArgumentFaultException,GeneralFaultException{
        if(logger.isDebugEnabled()) {
           logger.debug("Entering setIpcAccessControl() of VidyoPortalSuperServiceSkeleton");
        }

        if(setIpcAccessControlRequest16.getIpcAccessControl().getLevel().equals(IpcAccessControlLevel.System) &&
           setIpcAccessControlRequest16.getIpcAccessControl().getAccessMode() == null) {
           logger.error("Access mode is missing");
           MissingArgumentFault fault = new MissingArgumentFault();
           fault.setErrorMessage("Some required request parameters were not specified / level is set to System but accessMode is not provided");
           MissingArgumentFaultException ex = new MissingArgumentFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }

        try {
           String controlLevel = null;
           if(setIpcAccessControlRequest16.getIpcAccessControl().getLevel().equals(IpcAccessControlLevel.System)) {
              controlLevel = IPC_ACCESS_CONTROL_LEVEL_SYSTEM;
           } else {
              controlLevel = IPC_ACCESS_CONTROL_LEVEL_TENANT;
           }

           systemService.updateIpcAdmin(controlLevel);

           //Below updates happen only if the ipcAdmin is Super
           if(setIpcAccessControlRequest16.getIpcAccessControl().getLevel().equals(IpcAccessControlLevel.System)) {
              int flag = 0;
              if(setIpcAccessControlRequest16.getIpcAccessControl().getAccessMode().equals(IpcAccessControlAccessMode.Allow)) {
                 flag = 1;
              }

              systemService.updateIpcAllowDomainsFlag(flag);
           }

           // Save IPC RouterPool in Configuration Table
           String routerID = setIpcAccessControlRequest16.getIpcAccessControl().getRouterID();
           Configuration config = systemService.getConfiguration("IPC_ROUTER_POOL");
           if(routerID != null && routerID.length() > 0 && !routerID.equalsIgnoreCase("0")) {
              // routerID
              List<RouterPool> routerPools = serviceService.getRouterPoolNames();
              boolean found = false;
              for(RouterPool routerPool : routerPools) {
                 if(routerID.equalsIgnoreCase(routerPool.getRouterPoolID())) {
                    found = true;
                    break;
                 }
              }
              if(!found) {
                 InvalidArgumentFault fault = new InvalidArgumentFault();
                 fault.setErrorMessage("Invalid routerID.");
                 InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                 ex.setFaultMessage(fault);
                 throw ex;
              }
              if(config == null) {
                 systemService.saveSystemConfig("IPC_ROUTER_POOL", routerID, 0);
              } else {
                 if(!config.getConfigurationValue().equalsIgnoreCase(routerID)) {
                    systemService.updateSystemConfig("IPC_ROUTER_POOL", routerID);
                 }
              }
           } else if(config != null) {
              systemService.deleteConfiguration("IPC_ROUTER_POOL");
           }

           SetIpcAccessControlResponse response = new SetIpcAccessControlResponse();
           response.setOK(OK_type0.OK);

           if(logger.isDebugEnabled()) {
              logger.debug("Exiting setIpcAccessControl() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
         } catch (InvalidArgumentFaultException iafEx) {
            throw iafEx;
         } catch(Exception anyEx) {
            logger.error("Failed to set ipc access control.", anyEx);
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Failed to set ipc access control.");
            GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
         }
    }

    /**
      * Auto generated method signature
      * Add and / or remove a list of allowed IPC domains. The user can add and remove IPC domains at the same time.
      * @param setIpcDomainsRequest10
      * @return setIpcDomainsResponse11
      * @throws GeneralFaultException
      */
    public SetIpcDomainsResponse setIpcDomains(SetIpcDomainsRequest setIpcDomainsRequest10)
       throws GeneralFaultException,IncorrectIpcAccessLevelFaultException{
        if(logger.isDebugEnabled()) {
           logger.debug("Entering setIpcDomains() of VidyoPortalSuperServiceSkeleton");
        }

        try {

           boolean isIpcSuperManaged = systemService.isIpcSuperManaged();

           if(!isIpcSuperManaged) {
              logger.error("IpcAccessControlLevel is not System.");
              IncorrectIpcAccessLevelFault fault = new IncorrectIpcAccessLevelFault();
              fault.setErrorMessage("IpcAccessControlLevel is not System.");
              IncorrectIpcAccessLevelFaultException ex = new IncorrectIpcAccessLevelFaultException(fault.getErrorMessage());
              ex.setFaultMessage(fault);
              throw ex;
           }

           NotEmptyString [] addDomainNameArray = setIpcDomainsRequest10.getAddIpcDomainList();
           NotEmptyString [] removeDomainNameArray = setIpcDomainsRequest10.getRemoveIpcDomainList();

           List<String> notAddedDomainsList = new ArrayList<String>();
           List<String> notRemovedDomainsList = new ArrayList<String>();

           // Validate domain Names
           StringBuilder invalidDomains = new StringBuilder();
           String testPattern = "^((?!-)[\\*\\.]*[A-Za-z0-9@-]{1,}(?<!-)\\.)+[A-Za-z]{2,}$";

           if (addDomainNameArray != null) {
	           for (NotEmptyString domainName: addDomainNameArray) {
	         	   if (!domainName.toString().matches(testPattern)){
	         		   // Old regex - ^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$
	         		   if (invalidDomains != null && invalidDomains.length() > 0){
	         			  invalidDomains.append(", ");
	         		   }
	         		  invalidDomains.append(domainName.toString());
	         	   }
	           }
           }

           if (removeDomainNameArray != null) {
	           for (NotEmptyString domainName: removeDomainNameArray) {
	         	   if (!domainName.toString().matches(testPattern)){
	         		  if (invalidDomains != null && invalidDomains.length() > 0){
	         			  invalidDomains.append(", ");
	         		   }
	         		  invalidDomains.append(domainName.toString());
	         	   }
	           }
           }
           if (invalidDomains != null && invalidDomains.length() > 0){
        	   logger.error("Invalid Domain(s) " + invalidDomains.toString());
               IncorrectIpcAccessLevelFault fault = new IncorrectIpcAccessLevelFault();
               fault.setErrorMessage("Invalid Domain(s) " + invalidDomains.toString());
               IncorrectIpcAccessLevelFaultException ex = new IncorrectIpcAccessLevelFaultException(fault.getErrorMessage());
               ex.setFaultMessage(fault);
               throw ex;
           }

           if(addDomainNameArray != null && addDomainNameArray.length > 0 ||
              removeDomainNameArray != null && removeDomainNameArray.length > 0) {

              // remove duplications from add and remove arrays
              Set<String> addDomainSet = new HashSet<String>();
              if(addDomainNameArray != null && addDomainNameArray.length > 0) {
                 for(int i = 0; i < addDomainNameArray.length; i++) {
                    addDomainSet.add(addDomainNameArray[i].getNotEmptyString().replace("\"", ""));
                 }
              }

              Set<String> removeDomainSet = new HashSet<String>();
              if(removeDomainNameArray != null && removeDomainNameArray.length > 0) {
                 for(int i = 0; i < removeDomainNameArray.length; i++) {
                    removeDomainSet.add(removeDomainNameArray[i].getNotEmptyString().replace("\"", ""));
                 }
              }

              // remove common domain names from both add and remove sets
              Set<String> commonDomainsSet = new HashSet<String>();
              for(String domain : addDomainSet) {
                 if(removeDomainSet.contains(domain)) {
                    commonDomainsSet.add(domain);
                 }
              }

              addDomainSet.removeAll(commonDomainsSet);
              removeDomainSet.removeAll(commonDomainsSet);

              notAddedDomainsList.addAll(commonDomainsSet);
              notRemovedDomainsList.addAll(commonDomainsSet);

              // Get existing domains
              List<IpcDomain> existingDomainsList = ipcDomainService.getPortalIpcDomains();
              Set<String> existingDomainNamesSet = new HashSet<String>();

              // check add / remove sets against existing domains
              for(IpcDomain ipcDomain : existingDomainsList) {
                 if(addDomainSet.contains(ipcDomain.getDomainName())) {
                    notAddedDomainsList.add(ipcDomain.getDomainName());
                    addDomainSet.remove(ipcDomain.getDomainName());
                 }
                 existingDomainNamesSet.add(ipcDomain.getDomainName());
              }

              commonDomainsSet.clear();
              for(String removingDomainName : removeDomainSet) {
                 if(!existingDomainNamesSet.contains(removingDomainName)) {
                    commonDomainsSet.add(removingDomainName);
                 }
              }
              removeDomainSet.removeAll(commonDomainsSet);
              notRemovedDomainsList.addAll(commonDomainsSet);

              // remove domain names from DB
              if(removeDomainSet.size() > 0) {
                 String [] removeDomainArray = new String [removeDomainSet.size()];
                 removeDomainArray = removeDomainSet.toArray(removeDomainArray);
                 ipcDomainService.deleteIpcDomainsByName(removeDomainArray);
              }

              // add domain names to DB
              if(addDomainSet.size() > 0) {
                 String [] addDomainArray = new String [addDomainSet.size()];
                 addDomainArray = addDomainSet.toArray(addDomainArray);
                 ipcDomainService.addDomains(addDomainArray, systemService.getIpcAllowDomainsFlag());
              }

           }

           SetIpcDomainsResponse response = new SetIpcDomainsResponse();
           response.setOK(OK_type0.OK);

           if(notRemovedDomainsList.size() > 0) {
              String [] notRemoveDomainArray = new String [notRemovedDomainsList.size()];
              notRemoveDomainArray = notRemovedDomainsList.toArray(notRemoveDomainArray);
              response.setNotRemovedIpcDomainList(notRemoveDomainArray);
           }

           if(notAddedDomainsList.size() > 0) {
              String [] notAddDomainArray = new String [notAddedDomainsList.size()];
              notAddDomainArray = notAddedDomainsList.toArray(notAddDomainArray);
              response.setNotAddedIpcDomainList(notAddDomainArray);
           }

           if(logger.isDebugEnabled()) {
              logger.debug("Exiting setIpcDomains() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
        } catch(IncorrectIpcAccessLevelFaultException iialEx) {
           throw iialEx;
        } catch(Exception anyEx) {
           logger.error("Failed to set ipc domains.", anyEx);
           GeneralFault fault = new GeneralFault();
           fault.setErrorMessage("Failed to set ipc domains.");
           GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }
    }

    /**
      * Auto generated method signature
      * Save all tenant and user information about the entire system, and provides the saved backup file name.
      * @param saveDBRequest14
      * @return saveDBResponse15
      * @throws GeneralFaultException
      */
    public SaveDBResponse saveDb(SaveDBRequest saveDBRequest14) throws GeneralFaultException{
		if(logger.isDebugEnabled()) {
			logger.debug("Entering saveDb() of VidyoPortalSuperServiceSkeleton");
		}

		logger.error("Super service saveDb no longer supported.");
		GeneralFault fault = new GeneralFault();
		fault.setErrorMessage("This operation is no longer supported, please use backupDb operation.");
		GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
		ex.setFaultMessage(fault);
		throw ex;
    }


	@Override
	public BackupDbResponse backupDb(BackupDbRequest backupDbRequest) throws GeneralFaultException, InvalidArgumentFaultException {
		String password = backupDbRequest.getPassword();
		boolean includeThumbNail = backupDbRequest.getIncludeThumbNail();

		if (StringUtils.isBlank(password)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Password cannot be empty.");
			InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
			ex.setFaultMessage(fault);
			throw ex;
		}


		String dbVer = systemService.getDbVersion();
		dbVer = dbVer.replace(".", "");

		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_HHmmss");
		String fileName = "db_"+sdf.format(new Date())+"_v"+dbVer;

		String tmpBackupDirStr = getUploadTempDirSuper() + fileName;
		File tmpBackupDir = new File(tmpBackupDirStr);
		tmpBackupDir.mkdir();

		if (!systemService.backupDatabase(fileName,includeThumbNail==true?1:0)) {
			logger.error("Failed to backup database due to system error (backupDatabase script returned error).");
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Failed to backup database due to system error.");
			GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
			ex.setFaultMessage(fault);
			FileUtils.deleteQuietly(tmpBackupDir);
			throw ex;
		}

		String tarGzFilePath = tmpBackupDirStr + ".tar.gz";
		try {
			CompressionUtils.targzip(tmpBackupDirStr, tarGzFilePath, true);
			SecurityUtils.createSecureArchive(password, tmpBackupDirStr, tarGzFilePath, tmpBackupDirStr + ".veb");
			systemService.moveDatabase(fileName + ".veb");
		} catch (Exception e) {
			logger.error("Failed to backup database due to system error (tarring, encrypting and/or moving backup failed).");
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Failed to backup database due to system error.");
			GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
			ex.setFaultMessage(fault);
			throw ex;
		} finally {
			FileUtils.deleteQuietly(tmpBackupDir);
			FileUtils.deleteQuietly(new File(tarGzFilePath));
		}

		BackupDbResponse response = new BackupDbResponse();
		DatabaseBackup databaseBackup = new DatabaseBackup();

		try {
			File backupFile = new File("/var/backup/" + fileName + ".veb");
			BasicFileAttributes backupFileAttrs = Files.readAttributes(backupFile.toPath(), BasicFileAttributes.class);
			if (backupFile.exists()) {
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTimeInMillis(backupFileAttrs.creationTime().toMillis());
				databaseBackup.setTimestamp(calendar);

				MessageContext msgContext = MessageContext.getCurrentMessageContext();
				HttpServletRequest servletRequest = (HttpServletRequest) msgContext.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
				String url = getBackupURL(servletRequest, fileName + ".veb");

				org.apache.axis2.databinding.types.URI uri = new org.apache.axis2.databinding.types.URI(url);
				databaseBackup.setBackupURL(uri);
			} else {
				logger.error("Expected file not found: /var/backup/" + fileName + ".veb");
			}
		} catch (IOException ioe) {
			logger.error("IOException determine file attributes for: /var/backup/" + fileName + ".veb");
		}

		response.setDatabaseBackup(databaseBackup);
		return response;
	}

	private String getBackupURL(HttpServletRequest servletRequest, String fileName) {
		String scheme = servletRequest.getScheme();
		String host = servletRequest.getServerName();
		List<String> apps = new ArrayList<String>(1);
		apps.add("super");
		List<ApplicationConfiguration> appConfigs = securityService.getAppsPortInfo(apps);
		ApplicationConfiguration superConfig = appConfigs.get(0);
		int port = 80;
		if ("https".equals(scheme)) {
			port = superConfig.getSecurePort();
		} else {
			port = superConfig.getUnsecurePort();
		}
		boolean showPort = true;
		if (port == 80 || port == 443) {
			showPort = false;
		}

		return scheme + "://" + host + (showPort ? ":" + port : "") + "/super/backups/" + fileName;
	}
    /**
      * Auto generated method signature
      * Delete saved database backup from the disk.
      * @param deleteDBRequest16
      * @return deleteDBResponse17
      * @throws InvalidArgumentFaultException
      * @throws GeneralFaultException
      */
    public DeleteDBResponse deleteDb(DeleteDBRequest deleteDBRequest16) throws InvalidArgumentFaultException,GeneralFaultException{
        if(logger.isDebugEnabled()) {
            logger.debug("Entering deleteDb() of VidyoPortalSuperServiceSkeleton");
         }

        try {
            if(!this.systemService.deleteBackupFile(deleteDBRequest16.getDatabaseName())){
               InvalidArgumentFault fault = new InvalidArgumentFault();
               fault.setErrorMessage("Invalid Argument Fault / file doesn't exist");
               InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
               ex.setFaultMessage(fault);
               throw ex;
            }

            DeleteDBResponse response = new DeleteDBResponse();
            response.setOK(OK_type0.OK);

            if(logger.isDebugEnabled()) {
                logger.debug("Exiting deleteDb() of VidyoPortalSuperServiceSkeleton");
            }

            return response;
         } catch (InvalidArgumentFaultException iafEx){
            throw iafEx;
         } catch (Exception anyEx) {
            logger.error("Failed to delete DB backup file.", anyEx);
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Failed to delete DB backup file.");
            GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
         }
    }

    /**
      * Auto generated method signature
      * Provide the level of access that user has to view and modify allowed IPC domains.
      * @param getIpcAccessControlRequest14
      * @return getIpcAccessControlResponse15
      * @throws GeneralFaultException
      */
    public GetIpcAccessControlResponse getIpcAccessControl(GetIpcAccessControlRequest getIpcAccessControlRequest14)
       throws GeneralFaultException{
        if(logger.isDebugEnabled()) {
            logger.debug("Entering getIpcAccessControl() of VidyoPortalSuperServiceSkeleton");
         }

         try {
            boolean isIpcSuperManaged = systemService.isIpcSuperManaged();

            IpcAccessControl ipcAccessControl = new IpcAccessControl();
            if(isIpcSuperManaged) {
               ipcAccessControl.setLevel(IpcAccessControlLevel.System);

               int flag = systemService.getIpcAllowDomainsFlag();

               if(flag == 0) {
                  ipcAccessControl.setAccessMode(IpcAccessControlAccessMode.Block);
               } else {
                  ipcAccessControl.setAccessMode(IpcAccessControlAccessMode.Allow);
               }
            } else {
               ipcAccessControl.setLevel(IpcAccessControlLevel.Tenant);
            }

            Configuration config = systemService.getConfiguration("IPC_ROUTER_POOL");
            if(config != null) {
               ipcAccessControl.setRouterID(config.getConfigurationValue());
            }

            GetIpcAccessControlResponse response = new GetIpcAccessControlResponse();
            response.setIpcAccessControl(ipcAccessControl);

            if(logger.isDebugEnabled()) {
                logger.debug("Exiting getIpcAccessControl() of VidyoPortalSuperServiceSkeleton");
            }

            return response;
         } catch (Exception anyEx) {
            logger.error("Failed to getIpcAccessControl.", anyEx);
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Failed to getIpcAccessControl");
            GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
         }
    }

	@Override
	public ListDbResponse listDb(ListDbRequest listDbRequest) throws GeneralFaultException {
		MessageContext msgContext = MessageContext.getCurrentMessageContext();
		HttpServletRequest servletRequest = (HttpServletRequest) msgContext.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);

		ListDbResponse response = new ListDbResponse();
		File dir = new File("/var/backup");
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) {
				return response;
			}
			Arrays.sort(files, new Comparator<File>(){
				public int compare(File f1, File f2) {
					return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified()); // newest first
				}
			});
			for (File backupFile : files) {
				DatabaseBackup databaseBackup = new DatabaseBackup();
				String fileName = backupFile.getName();
				try {
					BasicFileAttributes backupFileAttrs = Files.readAttributes(backupFile.toPath(), BasicFileAttributes.class);
					if (backupFile.exists()) {
						Calendar calendar = GregorianCalendar.getInstance();
						calendar.setTimeInMillis(backupFileAttrs.creationTime().toMillis());
						databaseBackup.setTimestamp(calendar);

						String url = getBackupURL(servletRequest, fileName);

						org.apache.axis2.databinding.types.URI uri = new org.apache.axis2.databinding.types.URI(url);
						databaseBackup.setBackupURL(uri);
					} else {
						logger.error("Expected file not found: /var/backup/" + fileName);
					}
				} catch (IOException ioe) {
					logger.error("IOException determine file attributes for: /var/backup/" + fileName);
				}
				response.addDatabaseBackups(databaseBackup);
			}
		}
		return response;
	}

	/**
      * Auto generated method signature
      * Get the list of all allowed or blocked IPC domains that are stored the database.
      * @param getIpcDomainListRequest22
      * @return getIpcDomainListResponse23
      * @throws GeneralFaultException
      * @throws IncorrectIpcAccessLevelFaultException
      */
    public GetIpcDomainListResponse getIpcDomainList(GetIpcDomainListRequest getIpcDomainListRequest22)
       throws GeneralFaultException,IncorrectIpcAccessLevelFaultException{
        if(logger.isDebugEnabled()) {
            logger.debug("Entering getIpcDomainList() of VidyoPortalSuperServiceSkeleton");
        }

        try {
           boolean isIpcSuperManaged = systemService.isIpcSuperManaged();

           if(!isIpcSuperManaged) {
              logger.error("IpcAccessControlLevel is not System.");
              IncorrectIpcAccessLevelFault fault = new IncorrectIpcAccessLevelFault();
              fault.setErrorMessage("IpcAccessControlLevel is not System");
              IncorrectIpcAccessLevelFaultException ex = new IncorrectIpcAccessLevelFaultException(fault.getErrorMessage());
              ex.setFaultMessage(fault);
              throw ex;
           }

           List<IpcDomain> ipcDomainList = ipcDomainService.getPortalIpcDomains();

           GetIpcDomainListResponse response = new GetIpcDomainListResponse();
           for(IpcDomain ipcDomain : ipcDomainList) {
              response.addIpcDomainList(ipcDomain.getDomainName());
           }

           if(logger.isDebugEnabled()) {
               logger.debug("Exiting getIpcDomainList() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
        } catch (IncorrectIpcAccessLevelFaultException iialfEx) {
           throw iialfEx;
        } catch (Exception anyEx) {
           logger.error("Failed to getIpcDomainList.", anyEx);
           GeneralFault fault = new GeneralFault();
           fault.setErrorMessage("Failed to getIpcDomainList");
           GeneralFaultException ex= new GeneralFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }
    }

    /**
      * Auto generated method signature
      * Retrieve the pool of routers.
      * @param getRouterPoolListRequest12
      * @return getRouterPoolListResponse13
      * @throws GeneralFaultException
      */
    public GetRouterPoolListResponse getRouterPoolList(GetRouterPoolListRequest getRouterPoolListRequest12)
       throws GeneralFaultException{
        if(logger.isDebugEnabled()) {
           logger.debug("Entering getRouterPoolList() of VidyoPortalSuperServiceSkeleton");
        }

        try {

    		Integer cloudConfigID = routerPoolService.getCloudConfigIdByStatus(ACTIVE);
    		List<Pool> pools = new ArrayList<Pool>();

    		if(null != cloudConfigID){

    			 pools = routerPoolService.getPoolList(cloudConfigID);
    		}
    		else{
    			 logger.error("No active cloud found");
    	         GeneralFault fault = new GeneralFault();
    	         fault.setErrorMessage("No active cloud found");
    	         GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
    	         ex.setFaultMessage(fault);
    	         throw ex;
    		}


           GetRouterPoolListResponse response = new GetRouterPoolListResponse();
           for(Pool routerPool : pools) {
              com.vidyo.portal.superapi.RouterPool rp = new com.vidyo.portal.superapi.RouterPool();
              rp.setRouterID(String.valueOf(routerPool.getPoolKey().getId()));
              rp.setRouterName(routerPool.getName());

              response.addRouterPoolsList(rp);
           }

           if(logger.isDebugEnabled()) {
               logger.debug("Exiting getRouterPoolList() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
        } catch (Exception anyEx) {
           logger.error("Failed to getRouterPoolList.", anyEx);
           GeneralFault fault = new GeneralFault();
           fault.setErrorMessage("Failed to getRouterPoolList");
           GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }
    }

    /**
     * Auto generated method signature
     * Provide the location tags available on the portal.
     * @param getLocationTagsRequest20
     * @return getLocationTagsResponse21
     * @throws GeneralFaultException
     */
    public GetLocationTagsResponse getLocationTags(GetLocationTagsRequest getLocationTagsRequest20)
       throws GeneralFaultException{
        if(logger.isDebugEnabled()) {
            logger.debug("Entering getLocationTags() of VidyoPortalSuperServiceSkeleton");
        }

        try {
           List<Location> locations = this.serviceService.getLocations(null);

           GetLocationTagsResponse response = new GetLocationTagsResponse();
           for(Location location : locations) {
              LocationTag locationTag = new LocationTag();
              locationTag.setLocationTagID(location.getLocationID());
              locationTag.setLocationTagName(location.getLocationTag());

              response.addLocationTagsList(locationTag);
           }

           if(logger.isDebugEnabled()) {
               logger.debug("Exiting getLocationTags() of VidyoPortalSuperServiceSkeleton");
           }

           return response;
        } catch (Exception anyEx) {
           logger.error("Failed to getLocationTags.", anyEx);
           GeneralFault fault = new GeneralFault();
           fault.setErrorMessage("Failed to getLocationTags");
           GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }
    }

    /**
     * Auto generated method signature
     * Set the login and welcome banner parameters on the VidyoPortal tenants and enable / disable the feature altogether.
     * @param setLoginAndWelcomeBannerRequest10
     * @return setLoginAndWelcomeBannerResponse11
     * @throws BannerTextFaultException
     * @throws GeneralFaultException
     */

    public SetLoginAndWelcomeBannerResponse setLoginAndWelcomeBanner(SetLoginAndWelcomeBannerRequest setLoginAndWelcomeBannerRequest10)
        throws BannerTextFaultException,GeneralFaultException{
        if(logger.isDebugEnabled()) {
            logger.debug("Entering setLoginAndWelcomeBanner() of VidyoPortalSuperServiceSkeleton");
        }

        Banners banners = systemService.getBannersInfo();

        banners.setShowLoginBanner(setLoginAndWelcomeBannerRequest10.getBanner().getShowLoginBanner());
        banners.setShowWelcomeBanner(setLoginAndWelcomeBannerRequest10.getBanner().getShowWelcomeBanner());

        String loginBannerText = setLoginAndWelcomeBannerRequest10.getBanner().getLoginBannerText();
        CleanResults cleanResults = null;

        if(loginBannerText != null && !loginBannerText.isEmpty()) {
            try {
                cleanResults = AntiSamyHtmlCleaner.cleanHtml(loginBannerText);
            } catch (ScanException | PolicyException e) {
                logger.error("Failed to clean login banner text.", e);
                GeneralFault fault = new GeneralFault();
                fault.setErrorMessage(e.getMessage());
                GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }
            loginBannerText = cleanResults.getCleanHTML();

            if(loginBannerText.length() > BANNER_TEXT_MAX_LENGTH) {
                BannerTextFault fault = new BannerTextFault();
                fault.setErrorMessage("Provided Login Banner text exceeds character limit.");
                BannerTextFaultException ex = new BannerTextFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }

            if(!loginBannerText.isEmpty()) {
                banners.setLoginBanner(loginBannerText);
            }
        }

        String welcomeBannerText = setLoginAndWelcomeBannerRequest10.getBanner().getWelcomeBannerText();

        if(welcomeBannerText != null && !welcomeBannerText.isEmpty()) {

            try {
                cleanResults = AntiSamyHtmlCleaner.cleanHtml(welcomeBannerText);
            } catch (ScanException | PolicyException e) {
                logger.error("Failed to clean welcome banner.", e);
                GeneralFault fault = new GeneralFault();
                fault.setErrorMessage(e.getMessage());
                GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }
            welcomeBannerText = cleanResults.getCleanHTML();

            if(welcomeBannerText.length() > BANNER_TEXT_MAX_LENGTH) {
                BannerTextFault fault = new BannerTextFault();
                fault.setErrorMessage("Provided Welcome Banner text exceeds character limit.");
                BannerTextFaultException ex = new BannerTextFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }

            if(!welcomeBannerText.isEmpty()) {
                banners.setWelcomeBanner(welcomeBannerText);
            }
        }

        systemService.saveBanners(banners);

        Banner banner = new Banner();
        banner.setShowLoginBanner(banners.getShowLoginBanner());
        banner.setShowWelcomeBanner(banners.getShowWelcomeBanner());
        banner.setLoginBannerText(banners.getLoginBanner());
        banner.setWelcomeBannerText(banners.getWelcomeBanner());

        SetLoginAndWelcomeBannerResponse resp = new SetLoginAndWelcomeBannerResponse();
        resp.setBanner(banner);

        return resp;
    }

    /**
     * Auto generated method signature
     * The objective of this API is to set whether chat is available on the VidyoPortal and the default values for private / public chat
     * on the newly created tenants. Once made unavailable, tenant admins will not be able to enable chat on the VidyoPortal and
     * Vidyo clients joining the conferences on this VidyoPortal won't be able to engage in chat conversations.

     * @param setChatStateSuperRequest
     * @throws GeneralFaultException :
     */
    public SetChatStateSuperResponse setChatStateSuper(SetChatStateSuperRequest setChatStateSuperRequest)
        throws GeneralFaultException {

        if(logger.isDebugEnabled()) {
            logger.debug("Entering setChatStateSuper() of VidyoPortalSuperServiceSkeleton");
        }

        PortalChat portalChat = systemService.getPortalChat();

        if(setChatStateSuperRequest.getChatState().isChatAvailabilitySpecified()) {
            portalChat.setChatAvailable(setChatStateSuperRequest.getChatState().getChatAvailability());
        }

        if(setChatStateSuperRequest.getChatState().isPrivateChatStateSpecified()) {
            portalChat.setDefaultPrivateChatEnabled(setChatStateSuperRequest.getChatState().getPrivateChatState());
        }

        if(setChatStateSuperRequest.getChatState().isPublicChatStateSpecified()) {
            portalChat.setDefaultPublicChatEnabled(setChatStateSuperRequest.getChatState().getPublicChatState());
        }

        systemService.savePortalChat(portalChat);

        SetChatStateSuperResponse setChatStateSuperResponse = new SetChatStateSuperResponse();
        setChatStateSuperResponse.setOK(OK_type0.OK);

        if(logger.isDebugEnabled()) {
            logger.debug("Exiting setChatStateSuper() of VidyoPortalSuperServiceSkeleton");
        }

        return setChatStateSuperResponse;
    }

    /**
     * Auto generated method signature
     * The objective of this API is to get the configured value for chat availability on the VidyoPortal as well as the default values for private and public chat for newly created tenants.
     * @param getChatStateSuperRequest
     * @throws GeneralFaultException :
     */
    public GetChatStateSuperResponse getChatStateSuper(GetChatStateSuperRequest getChatStateSuperRequest)
        throws GeneralFaultException {
        if(logger.isDebugEnabled()) {
            logger.debug("Entering getChatStateSuper() of VidyoPortalSuperServiceSkeleton");
        }

        PortalChat portalChat = systemService.getPortalChat();

        GetChatStateSuperResponse getChatStateSuperResponse = new GetChatStateSuperResponse();

        ChatState chatState = new ChatState();
        chatState.setChatAvailability(portalChat.isChatAvailable());
        chatState.setPrivateChatState(portalChat.isDefaultPrivateChatEnabled());
        chatState.setPublicChatState(portalChat.isDefaultPublicChatEnabled());

        getChatStateSuperResponse.setChatState(chatState);

        if(logger.isDebugEnabled()) {
            logger.debug("Exiting getChatStateSuper() of VidyoPortalSuperServiceSkeleton");
        }

        return getChatStateSuperResponse;
    }

    private Tenant makeTenantFromTenantDataType(final int tenantId, final TenantDataType tenantdata, Tenant oldTenant) throws InvalidArgumentFaultException {
        String services = constructServicesList(tenantId, tenantdata);
        String callTo = constructCallToString(tenantdata);
        String locations = constructLocationsString(tenantdata);

        Tenant tenant = new Tenant();
        if(ArrayUtils.isNotEmpty(tenantdata.getAllowedVidyoGatewayList())) {
        		tenant.setAllowedGateways(Arrays.stream(tenantdata.getAllowedVidyoGatewayList()).boxed().collect(Collectors.toList()));
        } else {
        		tenant.setAllowedGateways(Collections.emptyList());
        }
        
        if(ArrayUtils.isNotEmpty(tenantdata.getAllowedLocationTagList())) {
        		tenant.setAllowedLocationTags(Arrays.stream(tenantdata.getAllowedLocationTagList()).boxed().collect(Collectors.toList()));
        } else {
        		tenant.setAllowedLocationTags(Collections.emptyList());
        }
        
        if(ArrayUtils.isNotEmpty(tenantdata.getAllowedVidyoRepalyList())) {
    			tenant.setAllowedReplays(Arrays.stream(tenantdata.getAllowedVidyoRepalyList()).boxed().collect(Collectors.toList()));
        } else {
        		tenant.setAllowedReplays(Collections.emptyList());
        }
        
        if(ArrayUtils.isNotEmpty(tenantdata.getAllowedVidyoReplayRecorderList())) {
    			tenant.setAllowedRecorders(Arrays.stream(tenantdata.getAllowedVidyoReplayRecorderList()).boxed().collect(Collectors.toList()));
        } else {
        		tenant.setAllowedRecorders(Collections.emptyList());
        }

        if(ArrayUtils.isNotEmpty(tenantdata.getVidyoProxyList())) {
    			tenant.setAllowedProxies(Arrays.stream(tenantdata.getVidyoProxyList()).boxed().collect(Collectors.toList()));
        } else {
        		tenant.setAllowedProxies(Collections.emptyList());
        }
        
        tenant.setCallTo(callTo);
        tenant.setDescription(tenantdata.getDescription());
        tenant.setExecutives(tenantdata.getNumOfExecutives().getNonNegativeInt());
        if(tenantdata.getEnableGuestLogin()) {
           tenant.setGuestLogin(1);
        } else {
           tenant.setGuestLogin(0);
        }
        if(tenantdata.getIpcAllowInbound()) {
           tenant.setInbound(1);
        } else {
           tenant.setInbound(0);
        }
        tenant.setInstalls(tenantdata.getNumOfInstalls().getNonNegativeInt());
        tenant.setLocations(locations);
        if(tenantdata.getVidyoMobileAllowed()) {
           tenant.setMobileLogin(1);
        } else {
           tenant.setMobileLogin(0);
        }
        if(tenantdata.getIpcAllowOutbound()) {
           tenant.setOutbound(1);
        } else {
           tenant.setOutbound(0);
        }
        tenant.setPanoramas(tenantdata.getNumOfPanoramas().getNonNegativeInt());
        tenant.setPorts(tenantdata.getNumOfLines().getNonNegativeInt());
        tenant.setRouters("1");
        tenant.setSeats(tenantdata.getNumOfSeats().getNonNegativeInt());
        tenant.setServices(services);

        if (tenantdata.isDialinNumberSpecified()) {
	        if(tenantdata.getDialinNumber() != null) {
	           tenant.setTenantDialIn(tenantdata.getDialinNumber().getString20());
	        } else {
	           tenant.setTenantDialIn("");
	        }
        } else {
        	if (oldTenant != null){
        		tenant.setTenantDialIn(oldTenant.getTenantDialIn());
        	}
        }
        tenant.setTenantID(tenantId);
        tenant.setTenantName(tenantdata.getTenantName().getTenantNamePattern());
        tenant.setTenantPrefix(tenantdata.getExtensionPrefix().getTenantExtensionPrefixPattern());

        if (tenantdata.isVidyoReplayUrlSpecified()) {
	        if(tenantdata.getVidyoReplayUrl() != null) {
	           tenant.setTenantReplayURL(tenantdata.getVidyoReplayUrl().getTenantUrlPattern());
	        } else {
	           tenant.setTenantReplayURL("");
	        }
        } else {
        	if (oldTenant != null){
        		tenant.setTenantReplayURL(oldTenant.getTenantReplayURL());
        	}
        }
        tenant.setTenantURL(tenantdata.getTenantUrl().getTenantUrlPattern());

        if (tenantdata.isVidyoGatewayControllerDnsSpecified()) {
	        if(tenantdata.getVidyoGatewayControllerDns() != null && tenantdata.getVidyoGatewayControllerDns().getTenantUrlPattern() != null &&
	              !tenantdata.getVidyoGatewayControllerDns().getTenantUrlPattern().isEmpty()) {
	           tenant.setVidyoGatewayControllerDns(tenantdata.getVidyoGatewayControllerDns().getTenantUrlPattern());
	        } else {
	           tenant.setVidyoGatewayControllerDns(null);
	        }
        } else {
        	if (oldTenant != null){
        		tenant.setVidyoGatewayControllerDns(oldTenant.getVidyoGatewayControllerDns());
        	}
        }

        Configuration createPublicRoomconfig = systemService.getConfiguration("CREATE_PUBLIC_ROOM_FLAG");
        if (createPublicRoomconfig != null && createPublicRoomconfig.getConfigurationValue() != null
        		&& createPublicRoomconfig.getConfigurationValue().trim().equals("1")) {
	        if (tenantdata.isNumOfPublicRoomsSpecified()) {
		        if (tenantdata.getNumOfPublicRooms() != null) {
			        long totalPublicRoomCreated = tenantId > 0 ? roomService.getPublicRoomCountForTenentID(tenantId):0;
			        Configuration maxCreatePublicRoomconfig = systemService.getConfiguration("MAX_CREATE_PUBLIC_ROOM");
		        	if (tenantdata.getNumOfPublicRooms().intValue() >= 0 && tenantdata.getNumOfPublicRooms().intValue() >= totalPublicRoomCreated){
		        		long totalCreatePublicRoomCount = maxCreatePublicRoomconfig != null &&
		        				maxCreatePublicRoomconfig.getConfigurationValue() != null ?
		        						Long.parseLong(maxCreatePublicRoomconfig.getConfigurationValue()):100000;
		        		if (tenantdata.getNumOfPublicRooms().intValue() <= totalCreatePublicRoomCount) {
			        		try {
			        			tenant.setPublicRooms(tenantdata.getNumOfPublicRooms().intValue());
			        		} catch(Exception ex){
			        			//ignore
			        		}
		        		} else {
		        			InvalidArgumentFault fault = new InvalidArgumentFault();
			                fault.setErrorMessage("Maximum allowed number of public rooms :" + totalCreatePublicRoomCount);
			                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
			                ex.setFaultMessage(fault);
			                throw ex;
		        		}
		        	} else if (tenantdata.getNumOfPublicRooms().intValue() >= 0 && tenantdata.getNumOfPublicRooms().intValue() < totalPublicRoomCreated) {
		        		InvalidArgumentFault fault = new InvalidArgumentFault();
		                fault.setErrorMessage("Minimum number of public room :" + totalPublicRoomCreated);
		                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
		                ex.setFaultMessage(fault);
		                throw ex;
		        	}
		        } else {
		        	tenant.setPublicRooms(0);
		        }
	        } else {
	        	if (oldTenant != null){
	        		tenant.setPublicRooms(oldTenant.getPublicRooms());
	        	}
	        }
        } else {
        	if (oldTenant != null){
        		tenant.setPublicRooms(oldTenant.getPublicRooms());
        	}
        }

        Configuration vidyoNeoWebRTCGuestConfiguration = systemService.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_GUESTS");
        Configuration vidyoNeoWebRTUserConfiguration = systemService.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
        if  ((vidyoNeoWebRTCGuestConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTCGuestConfiguration.getConfigurationValue())
        		&& vidyoNeoWebRTCGuestConfiguration.getConfigurationValue().equalsIgnoreCase("1"))
        		|| (vidyoNeoWebRTUserConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
        		&& vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1"))) {
	        if (tenantdata.isTenantWebRTCURLSpecified()) {
	        	if (tenantdata.getTenantWebRTCURL() != null) {

			        if (tenantdata.getTenantWebRTCURL().getTenantUrlPattern() != null){
			        	try {
							URL tenantWebRTCURL = new URL(tenantdata.getTenantWebRTCURL().getTenantUrlPattern());
							String protocol = tenantWebRTCURL.getProtocol();
							String hostName = tenantWebRTCURL.getHost();
							if (StringUtils.isEmpty(protocol) || "http".equals(protocol.toLowerCase()) || StringUtils.isEmpty(hostName)) {
								InvalidArgumentFault fault = new InvalidArgumentFault();
				                fault.setErrorMessage("Invalid Web RTC URL. This field requires a valid URL (e.g. https://hostname)");
				                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
				                ex.setFaultMessage(fault);
				                throw ex;
							}
						} catch (MalformedURLException e) {
							InvalidArgumentFault fault = new InvalidArgumentFault();
			                fault.setErrorMessage("Invalid Web RTC URL. This field requires a valid URL (e.g. https://hostname)");
			                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
			                ex.setFaultMessage(fault);
			                throw ex;
						}
			        	tenant.setTenantWebRTCURL(tenantdata.getTenantWebRTCURL().getTenantUrlPattern());
			        } else {
			        	tenant.setTenantWebRTCURL(null);
			        }
		        } else {
		        	tenant.setTenantWebRTCURL(null);
		        }
	        } else {
	        	if (oldTenant != null){
	        		tenant.setTenantWebRTCURL(oldTenant.getTenantWebRTCURL());
	        	}
	        }
        } else {
        	if (oldTenant != null){
        		tenant.setTenantWebRTCURL(oldTenant.getTenantWebRTCURL());
        	}
        }

      if(tenantdata.isEnableCustomRoleSpecified()){
    	tenant.setEndpointBehavior(tenantdata.getEnableCustomRole()?1:0);

      }else{
    	//since this field is optional, if user don't specify it, it shouldn't override the existing value with 0
    	TenantConfiguration tenantConfig=tenantService.getTenantConfiguration(tenantId);
    	tenant.setEndpointBehavior(tenantConfig.getEndpointBehavior());
    }
        if(tenantdata.isEnableEndpointLogAggregationSpecified()){
        	tenant.setLogAggregation(tenantdata.getEnableEndpointLogAggregation()==true?1:0);

        }else{
        	//since this field is optional, if user dont specify it, it shouldnt override the existing value with 0
        	TenantConfiguration tenantConfig=tenantService.getTenantConfiguration(tenantId);
        	tenant.setLogAggregation(tenantConfig.getLogAggregation());
        }
        if (tenantdata.isExternalEndpointSoftwareFileserverSpecified()){
        	if ( tenantdata.getExternalEndpointSoftwareFileserver().getValue().equals("")){
        		InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid value for ExternalEndpointSoftwareFileserver. This field requires a valid 'VidyoPortal' or 'External'.");
                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
        	}
        	tenant.setEndpointUploadMode( tenantdata.getExternalEndpointSoftwareFileserver().getValue().equalsIgnoreCase("External")  ? "External":"VidyoPortal");
        } else {
        	// In case not specified in the request, the case for create tenant is different than update tenant.
        	// As create tenant should inherit super settings, where as in case of the update tenant, we want to preseve old value as is.
        	if (tenantId == 0) {
		        Configuration endpointUploadModeConfiguration = systemService.getConfiguration("MANAGE_ENDPOINT_UPLOAD_MODE");
		        tenant.setEndpointUploadMode( (endpointUploadModeConfiguration != null && endpointUploadModeConfiguration.getConfigurationValue() != null 
						&& endpointUploadModeConfiguration.getConfigurationValue().equalsIgnoreCase("External"))  ? "External":"VidyoPortal");
			} else {
				tenant.setEndpointUploadMode(oldTenant!= null && oldTenant.getEndpointUploadMode() != null ? oldTenant.getEndpointUploadMode():"VidyoPortal");
			}
        }
        
        if (tenantdata.isMobileLoginModeSpecified()){
        	if ( tenantdata.getMobileLoginMode().getValue().equals("")){
        		InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid value for Mobile Login Mode. This field requires a valid 'VidyoMobile' or 'NeoMobile' or 'DISABLED'.");
                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
        	}
        	tenant.setMobileLogin(TenantServiceImpl.MobileMode.forValue(tenantdata.getMobileLoginMode().getValue()).getIntValue());
        } else {
        	// Since the Mobile login mode is not specified, this could be create tenant/ update tenant
        	// Checking first for create tenant case
           	if (tenantId == 0) {
           		Configuration mobiledModeConfiguration = systemService.getConfiguration("MOBILE_LOGIN_MODE");
           		tenant.setMobileLogin( mobiledModeConfiguration != null 
           				&& StringUtils.isNotBlank(mobiledModeConfiguration.getConfigurationValue()) ? 
           						TenantServiceImpl.MobileMode.forIntValue(Integer.parseInt(mobiledModeConfiguration.getConfigurationValue())).getIntValue():TenantServiceImpl.MobileMode.DISABLED.getIntValue());
           	} else {
          		tenant.setMobileLogin(oldTenant!= null ? oldTenant.getMobileLogin():TenantServiceImpl.MobileMode.DISABLED.getIntValue());
        	}
        }
        return tenant;
    }

    private GetTenantDetailsResponse makeGetTenantDetailsResponseFromTenant(final Tenant tenant) {
        List<Tenant> allowedTenantList = tenantService.getToTenants(tenant.getTenantID());
        List<Service> vms = tenantService.getToVMs(tenant.getTenantID());
        List<Service> vps = tenantService.getToVPs(tenant.getTenantID());
        List<Service> vgs = tenantService.getToVGs(tenant.getTenantID());
        List<Service> recorders = tenantService.getToRecs(tenant.getTenantID());
        List<Service> replays = tenantService.getToReplays(tenant.getTenantID());
        List<Location> locations = tenantService.getToLocations(tenant.getTenantID());

        TenantDataExtType tenantdata = new TenantDataExtType();
        if(locations != null && locations.size() > 0) {
           int [] locationsArr = new int[locations.size()];
           for(int i = 0; i < locations.size(); i++) {
              locationsArr[i] = locations.get(i).getLocationID();
           }
           tenantdata.setAllowedLocationTagList(locationsArr);
        }

        if(allowedTenantList != null && allowedTenantList.size() > 0) {
            int [] allowedTenantsArr = new int[allowedTenantList.size()];
            for(int i = 0; i < allowedTenantList.size(); i++) {
               allowedTenantsArr[i] = allowedTenantList.get(i).getTenantID();
            }
            tenantdata.setAllowedTenantList(allowedTenantsArr);
        }

        if(vgs != null && vgs.size() > 0) {
            int [] allowedVGsArr = new int[vgs.size()];
            for(int i = 0; i < vgs.size(); i++) {
               allowedVGsArr[i] = vgs.get(i).getServiceID();
            }
            tenantdata.setAllowedVidyoGatewayList(allowedVGsArr);
        }

        if(replays != null && replays.size() > 0) {
            int [] allowedRepalysArr = new int[replays.size()];
            for(int i = 0; i < replays.size(); i++) {
               allowedRepalysArr[i] = replays.get(i).getServiceID();
            }
            tenantdata.setAllowedVidyoRepalyList(allowedRepalysArr);
        }

        if(recorders != null && recorders.size() > 0) {
            int [] allowedRecordersArr = new int[recorders.size()];
            for(int i = 0; i < recorders.size(); i++) {
               allowedRecordersArr[i] = recorders.get(i).getServiceID();
            }
            tenantdata.setAllowedVidyoReplayRecorderList(allowedRecordersArr);
        }

        tenantdata.setDescription(tenant.getDescription());

        if (tenant.getTenantDialIn() != null && tenant.getTenantDialIn().length() > 0) {
	        String20 dialIn = new String20();
	        dialIn.setString20(tenant.getTenantDialIn());
	        tenantdata.setDialinNumber(dialIn);
        }

        if(tenant.getGuestLogin() == 0) {
           tenantdata.setEnableGuestLogin(false);
        } else {
           tenantdata.setEnableGuestLogin(true);
        }

        TenantExtensionPrefixPattern extPref = new TenantExtensionPrefixPattern();
        extPref.setTenantExtensionPrefixPattern(tenant.getTenantPrefix());
        tenantdata.setExtensionPrefix(extPref);

        if(tenant.getInbound() == 0) {
           tenantdata.setIpcAllowInbound(false);
        } else {
           tenantdata.setIpcAllowInbound(true);
        }
        if(tenant.getOutbound() == 0) {
           tenantdata.setIpcAllowOutbound(false);
        } else {
           tenantdata.setIpcAllowOutbound(true);
        }

        NonNegativeInt executives = new NonNegativeInt();
        executives.setNonNegativeInt(tenant.getExecutives());
        tenantdata.setNumOfExecutives(executives);

        NonNegativeInt installs = new NonNegativeInt();
        installs.setNonNegativeInt(tenant.getInstalls());
        tenantdata.setNumOfInstalls(installs);

        NonNegativeInt lines = new NonNegativeInt();
        lines.setNonNegativeInt(tenant.getPorts());
        tenantdata.setNumOfLines(lines);

        NonNegativeInt panoramas = new NonNegativeInt();
        panoramas.setNonNegativeInt(tenant.getPanoramas());
        tenantdata.setNumOfPanoramas(panoramas);

        NonNegativeInt seats = new NonNegativeInt();
        seats.setNonNegativeInt(tenant.getSeats());
        tenantdata.setNumOfSeats(seats);

        EntityID entityId = new EntityID();
        entityId.setEntityID(tenant.getTenantID());
        tenantdata.setTenantID(entityId);

        TenantNamePattern tenantName = new TenantNamePattern();
        tenantName.setTenantNamePattern(tenant.getTenantName());
        tenantdata.setTenantName(tenantName);

        TenantUrlPattern tenantUrl = new TenantUrlPattern();
        tenantUrl.setTenantUrlPattern(tenant.getTenantURL());
        tenantdata.setTenantUrl(tenantUrl);

        tenantdata.setVidyoManager(vms.get(0).getServiceID());
        if(tenant.getMobileLogin() == 2) {
           tenantdata.setVidyoMobileAllowed(false);
           MobileLoginMode neoMobileLoginMode = new MobileLoginMode(MobileLoginMode._NeoMobile, true);
           tenantdata.setMobileLoginMode(neoMobileLoginMode);
        } else if(tenant.getMobileLogin() == 1) {
           tenantdata.setVidyoMobileAllowed(true);
           MobileLoginMode vidyoMobileLoginMode = new MobileLoginMode(MobileLoginMode._VidyoMobile, true);
           tenantdata.setMobileLoginMode(vidyoMobileLoginMode);
        } else {
        	tenantdata.setVidyoMobileAllowed(false);
            MobileLoginMode disabledMobileLoginMode = new MobileLoginMode(MobileLoginMode._DISABLED, true);
            tenantdata.setMobileLoginMode(disabledMobileLoginMode);
        }

        if(vps != null && vps.size() > 0) {
            int [] allowedVPsArr = new int[vps.size()];
            for(int i = 0; i < vps.size(); i++) {
               allowedVPsArr[i] = vps.get(i).getServiceID();
            }
            tenantdata.setVidyoProxyList(allowedVPsArr);
        }

        String tenantReplayUrl = tenant.getTenantReplayURL();
        if(tenantReplayUrl != null && tenantReplayUrl.length() > 0) {
           TenantUrlPattern vidyoReplayUrl = new TenantUrlPattern();
           vidyoReplayUrl.setTenantUrlPattern(tenant.getTenantReplayURL());
           tenantdata.setVidyoReplayUrl(vidyoReplayUrl);
        }

        String vidyoGatewayControllerDns = tenant.getVidyoGatewayControllerDns();
        if(vidyoGatewayControllerDns != null && vidyoGatewayControllerDns.length() > 0) {
           TenantUrlPattern vidyoGatewayControllerDnsUrl = new TenantUrlPattern();
           vidyoGatewayControllerDnsUrl.setTenantUrlPattern(vidyoGatewayControllerDns);
           tenantdata.setVidyoGatewayControllerDns(vidyoGatewayControllerDnsUrl);
        }

        Configuration createPublicRoomconfig = systemService.getConfiguration("CREATE_PUBLIC_ROOM_FLAG");
        if (createPublicRoomconfig != null && createPublicRoomconfig.getConfigurationValue() != null
        		&& createPublicRoomconfig.getConfigurationValue().trim().equals("1")) {
        	org.apache.axis2.databinding.types.NonNegativeInteger numOfPublicRooms = new org.apache.axis2.databinding.types.NonNegativeInteger(""+tenant.getPublicRooms());
        	tenantdata.setNumOfPublicRooms(numOfPublicRooms);
        }

        Configuration vidyoNeoWebRTCGuestConfiguration = systemService.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_GUESTS");
        Configuration vidyoNeoWebRTUserConfiguration = systemService.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
        if  ((vidyoNeoWebRTCGuestConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTCGuestConfiguration.getConfigurationValue())
        		&& vidyoNeoWebRTCGuestConfiguration.getConfigurationValue().equalsIgnoreCase("1"))
        		|| (vidyoNeoWebRTUserConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
        		&& vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1"))) {
        	if ( tenant.getTenantWebRTCURL() != null && tenant.getTenantWebRTCURL().length() > 0) {
	        	TenantUrlPattern webRTCURL = new TenantUrlPattern();
	        	webRTCURL.setTenantUrlPattern(tenant.getTenantWebRTCURL());
	        	tenantdata.setTenantWebRTCURL(webRTCURL);
        	}
        }
        //guest proxy and location tag
        String guestLocationTag = systemService.getConfigValue(tenant.getTenantID(),"GuestLocationID");
        String guestProxy = systemService.getConfigValue(tenant.getTenantID(),"GuestProxyID");
        if(StringUtils.isNumeric(guestProxy)){


         	tenantdata.setDefaultGuestProxy(ConverterUtil.convertToNonNegativeInteger(guestProxy));
        }
        if( StringUtils.isNumeric(guestLocationTag)){

        	tenantdata.setDefaultGuestLocationTag(ConverterUtil.convertToNonNegativeInteger(guestLocationTag));

        }
        tenantdata.setEnableEndpointLogAggregation(tenant.getLogAggregation()==1?true:false);
         tenantdata.setEnableCustomRole(tenant.getEndpointBehavior()==1?true:false);

        if (tenant.getEndpointUploadMode() != null) {
        	tenantdata.setExternalEndpointSoftwareFileserver(new ExternalEndpointSoftwareFileserver(tenant.getEndpointUploadMode(), true));
        }
        //
        GetTenantDetailsResponse response = new GetTenantDetailsResponse();
        response.setTenantDetail(tenantdata);

        return response;
    }

    private String constructLocationsString(final TenantDataType tenantdata) throws InvalidArgumentFaultException {
        StringBuilder locations = null;

        int [] allowedLocations = tenantdata.getAllowedLocationTagList();
        if(allowedLocations != null) {
           locations = new StringBuilder();
           List<Location> locationLst = tenantService.getFromLocations(0);
           for (int i = 0; i < allowedLocations.length - 1; i++) {
              ;
              if(!containLocation(locationLst, allowedLocations[i])) {
                 logger.error("locationID does not exist.");
                 InvalidArgumentFault fault = new InvalidArgumentFault();
                 fault.setErrorMessage("locationID does not exist");
                 InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                 ex.setFaultMessage(fault);
                 throw ex;
              }
              locations.append(allowedLocations[i] + ",");
           }
           if(allowedLocations.length > 0) {
              if(!containLocation(locationLst, allowedLocations[allowedLocations.length - 1])) {
                 logger.error("locationID does not exist.");
                 InvalidArgumentFault fault = new InvalidArgumentFault();
                 fault.setErrorMessage("locationID does not exist");
                 InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                 ex.setFaultMessage(fault);
                 throw ex;
              }
              locations.append(allowedLocations[allowedLocations.length - 1]);
           }
        }

        if(locations == null) {
           return null;
        } else {
           return locations.toString();
        }
    }

    private boolean containLocation(final List<Location> locationList, final int locationId) {
       boolean isContain = false;
       for(Location location : locationList) {
          if(location.getLocationID() == locationId) {
        	  isContain = true;
        	  break;
          }
       }

       return isContain;
    }

	private String constructCallToString(final TenantDataType tenantdata)
			throws InvalidArgumentFaultException {
		StringBuilder callTo = new StringBuilder();
		Tenant tenant = null;

		int[] allowedTenants = tenantdata.getAllowedTenantList();
		if (allowedTenants != null && allowedTenants.length > 0) {
			for (int i = 0; i < allowedTenants.length; i++) {
				if(allowedTenants[i] > 0) {
					tenant = tenantService.getTenant(allowedTenants[i]);
					if (tenant == null) {
						logger.error("Allowed tenantID does not exist.");
						InvalidArgumentFault fault = new InvalidArgumentFault();
						fault.setErrorMessage("Allowed tenantID does not exist");
						InvalidArgumentFaultException ex = new InvalidArgumentFaultException(
								fault.getErrorMessage());
						ex.setFaultMessage(fault);
						throw ex;
					}
					callTo.append(allowedTenants[i] + ",");
				}
			}
		}
		return callTo.toString();
	}

    private String constructServicesList(final int tenantId, final TenantDataType tenantdata) throws InvalidArgumentFaultException {
        StringBuilder services = new StringBuilder();
        int vidyoManagerId = tenantdata.getVidyoManager();
        // check VM existence

		List<VidyoManager> list = componentsService
				.findManagerByCompID(vidyoManagerId);
		// since new service method wont throw error, doing manually. this
		// is due to the changes in the behavior of the services.
		if (list == null || list.isEmpty() || list.get(0) == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid VidyoManager ID");
			InvalidArgumentFaultException iafEx = new InvalidArgumentFaultException(
					fault.getErrorMessage());
			iafEx.setFaultMessage(fault);
			throw iafEx;
		}
        services.append(vidyoManagerId);

		if (tenantdata.getVidyoProxyList() != null
				&& tenantdata.getVidyoProxyList().length > 0) {
			for (int vidyoProxyId : tenantdata.getVidyoProxyList()) {
				// check VP existence
				if(vidyoProxyId > 0) { // Process only the non-negative values - as the empty xml elements result in default min integer
					try {
						serviceService.getVP(vidyoProxyId);
					} catch (Exception ex) {
						logger.error("Wrong VidyoProxy ID.", ex);
						InvalidArgumentFault fault = new InvalidArgumentFault();
						fault.setErrorMessage("Invalid VidyoProxy ID");
						InvalidArgumentFaultException iafEx = new InvalidArgumentFaultException(
								fault.getErrorMessage());
						iafEx.setFaultMessage(fault);
						throw iafEx;
					}
					services.append("," + vidyoProxyId);
				}

			}
		}

		if (tenantdata.getAllowedVidyoGatewayList() != null
				&& tenantdata.getAllowedVidyoGatewayList().length > 0) {
			for (int vidyoGatewayId : tenantdata.getAllowedVidyoGatewayList()) {
				// check VG existence
				if(vidyoGatewayId > 0) { // Process only the non-negative values - as the empty xml elements result in default min integer
					try {
						serviceService.getVG(vidyoGatewayId);
					} catch (Exception ex) {
						logger.error("Wrong VidyoGateway ID. -> " + vidyoGatewayId);
						InvalidArgumentFault fault = new InvalidArgumentFault();
						fault.setErrorMessage("Invalid VidyoGateway ID - "
								+ vidyoGatewayId);
						InvalidArgumentFaultException iafEx = new InvalidArgumentFaultException(
								fault.getErrorMessage());
						iafEx.setFaultMessage(fault);
						throw iafEx;
					}
					services.append("," + vidyoGatewayId);
				}
			}
		}

        if(tenantdata.getAllowedVidyoRepalyList() != null && tenantdata.getAllowedVidyoRepalyList().length > 0) {
            for(int vidyoReplayId : tenantdata.getAllowedVidyoRepalyList()) {
               // check VidyoReplay existence
            	if(vidyoReplayId > 0) { // Process only the non-negative values - as the empty xml elements result in default min integer
                	VidyoReplay vidyoReplay = componentsService.getReplayByCompId(vidyoReplayId);
                	if(vidyoReplay == null) {
                        InvalidArgumentFault fault = new InvalidArgumentFault();
                        fault.setErrorMessage("Invalid Replay ID");
                        InvalidArgumentFaultException iafEx = new InvalidArgumentFaultException(fault.getErrorMessage());
                        iafEx.setFaultMessage(fault);
                        throw iafEx;
                	}
                    services.append("," + vidyoReplayId);
            	}
            }
        }

		if (tenantdata.getAllowedVidyoReplayRecorderList() != null
				&& tenantdata.getAllowedVidyoReplayRecorderList().length > 0) {
			for (int vidyoReplayRecorderId : tenantdata
					.getAllowedVidyoReplayRecorderList()) {
				// check VidyoRecorder existence
				if(vidyoReplayRecorderId > 0) { // Process only the non-negative values - as the empty xml elements result in default min integer
					VidyoRecorder vidyoRecorder = componentsService.getRecorderByCompId(vidyoReplayRecorderId);
					if(vidyoRecorder == null) {
						InvalidArgumentFault fault = new InvalidArgumentFault();
						fault.setErrorMessage("Invalid Replay Recorder ID");
						InvalidArgumentFaultException iafEx = new InvalidArgumentFaultException(
								fault.getErrorMessage());
						iafEx.setFaultMessage(fault);
						throw iafEx;
					}
					services.append("," + vidyoReplayRecorderId);
				}
			}
		}

		return services.toString();
	}

    private void validateTenantDataType(final int tenantId, final TenantDataType tenantdata)
       throws ExistingTenantFaultException, InvalidArgumentFaultException {

        if(tenantService.isTenantExistForTenantName(tenantdata.getTenantName().getTenantNamePattern(), tenantId)) {
           logger.error("Tenant name exists.");
           ExistingTenantFault fault = new ExistingTenantFault();
           fault.setErrorMessage("Tenant name exists");
           ExistingTenantFaultException ex = new ExistingTenantFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }

        if (tenantdata.getTenantUrl().getTenantUrlPattern().toLowerCase().startsWith("http://")){
        	tenantdata.getTenantUrl().setTenantUrlPattern(tenantdata.getTenantUrl().getTenantUrlPattern().substring("http://".length()));
        } else if (tenantdata.getTenantUrl().getTenantUrlPattern().toLowerCase().startsWith("https://")){
        	tenantdata.getTenantUrl().setTenantUrlPattern(tenantdata.getTenantUrl().getTenantUrlPattern().substring("https://".length()));
        }
        if(tenantService.isTenantUrlExistForTenantUrl(tenantdata.getTenantUrl().getTenantUrlPattern(), tenantId)) {
           logger.error("Tenant url exists.");
           ExistingTenantFault fault = new ExistingTenantFault();
           fault.setErrorMessage("Tenant url exists");
           ExistingTenantFaultException ex = new ExistingTenantFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }

        if (tenantService.isTenantPrefixExistForTenantPrefix(tenantdata.getExtensionPrefix().getTenantExtensionPrefixPattern(), tenantId)) {
           logger.error("Tenant prefix exists.");
           InvalidArgumentFault fault = new InvalidArgumentFault();
           fault.setErrorMessage("Tenant prefix exists");
           InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
           ex.setFaultMessage(fault);
           throw ex;
        }
        
        // Check if the new tenant prefix will collide with any existing room extensions
        // only if the new prefix is different than old
        int count = roomService.isPrefixExisting(tenantdata.getExtensionPrefix().getTenantExtensionPrefixPattern(), tenantId);
    	if(count > 0) {
    		logger.error("Tenant prefix with extension used already.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Tenant prefix with extension used already.");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
    	}

    	// TODO - move these validations to common service layer.
		Configuration schRoomConfig = systemService.getConfiguration("SCHEDULED_ROOM_PREFIX");
		boolean prefixExists = false;

			if (schRoomConfig != null && schRoomConfig.getConfigurationValue() != null) {
				   if (schRoomConfig.getConfigurationValue().length() < tenantdata.getExtensionPrefix().getTenantExtensionPrefixPattern().length() ){
					   prefixExists = schRoomConfig.getConfigurationValue().equals(tenantdata
							   .getExtensionPrefix()
							   .getTenantExtensionPrefixPattern().substring(0, schRoomConfig
									   								.getConfigurationValue().length()));
				   } else {
					   prefixExists = tenantdata
							   .getExtensionPrefix()
							   .getTenantExtensionPrefixPattern().equals(schRoomConfig.getConfigurationValue()
									   .substring(0, tenantdata
											   .getExtensionPrefix()
											   .getTenantExtensionPrefixPattern().length()));
				   }

			}

		if (prefixExists) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Extension matches Schedule Room prefix. Please choose a different extension and try again.");
			InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
			ex.setFaultMessage(fault);
			throw ex;
		}

        if(tenantdata.getVidyoReplayUrl() != null) {
           String replayUrl = tenantdata.getVidyoReplayUrl().getTenantUrlPattern();
           if(replayUrl != null && !"".equals(replayUrl) &&
              tenantService.isTenantReplayUrlExistForTenantReplayUrl(replayUrl, tenantId)) {

              logger.error("Tenant replay url exists.");
              InvalidArgumentFault fault = new InvalidArgumentFault();
              fault.setErrorMessage("Tenant replay url exists");
              InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
              ex.setFaultMessage(fault);
              throw ex;
           }
        }

        if(tenantdata.getVidyoGatewayControllerDns() != null) {
            String vidyoGatewayControllerDns = tenantdata.getVidyoGatewayControllerDns().getTenantUrlPattern();
            if(vidyoGatewayControllerDns != null && !"".equals(vidyoGatewayControllerDns) &&
               tenantService.isVidyoGatewayControllerDnsExist(vidyoGatewayControllerDns, tenantId)) {

               logger.error("VidyoGateway Controller DNS exists.");
               InvalidArgumentFault fault = new InvalidArgumentFault();
               fault.setErrorMessage("VidyoGateway Controller DNS exists");
               InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
               ex.setFaultMessage(fault);
               throw ex;
            }
         }

        int maxInstalls = tenantService.getMaxNumOfInstalls(tenantId);
        int minInstalls = tenantService.getInstallsInUse(tenantId);
        int installs = tenantdata.getNumOfInstalls().getNonNegativeInt();
        if(installs > maxInstalls) {
            logger.error("Installs cannot be greater than MaxInstalls.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Installs cannot be greater than MaxInstalls");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }
        if(installs < minInstalls) {
            logger.error("Installs cannot be less than current installs.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Installs cannot be less than current installs");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }

        int maxSeats = tenantService.getMaxNumOfSeats(tenantId);
        int minSeats = tenantService.getSeatsInUse(tenantId);
        int seats = tenantdata.getNumOfSeats().getNonNegativeInt();
        if(seats > maxSeats) {
            logger.error("Seats cannot be greater than MaxSeats.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Seats cannot be greater than MaxSeats");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }
        if(seats < minSeats) {
            logger.error("Seats cannot be less than current seats.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Seats cannot be less than current seats.");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }

        int maxLines = tenantService.getMaxNumOfPorts(tenantId);
        int lines = tenantdata.getNumOfLines().getNonNegativeInt();
        if(lines > maxLines) {
            logger.error("Lines cannot be greater than MaxLines.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Lines cannot be greater than Maxlines");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }

        int maxExecutives = tenantService.getMaxNumOfExecutives(tenantId);
        int minExecutives = tenantService.getExecutivesInUse(tenantId);
        int executives = tenantdata.getNumOfExecutives().getNonNegativeInt();
        if(executives > maxExecutives) {
            logger.error("Executives cannot be greater than MaxExecutives.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Executives cannot be greater than MaxExecutives");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }
        if(executives < minExecutives) {
            logger.error("Executives cannot be less than current executives.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Executives cannot be less than current executives.");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }

        int maxVidyoPanaramas = tenantService.getMaxNumOfPanoramas(tenantId);
        int minVidyoPanaramas = tenantService.getPanoramasInUse(tenantId);
        int vidyoPanaramas = tenantdata.getNumOfPanoramas().getNonNegativeInt();
        if(vidyoPanaramas > maxVidyoPanaramas) {
            logger.error("VidyoPanaramas cannot be greater than MaxVidyoPanaramas.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("VidyoPanaramas cannot be greater than MaxVidyoPanaramas");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }
        if(vidyoPanaramas < minVidyoPanaramas) {
            logger.error("VidyoPanaramas cannot be less than MinVidyoPanaramas.");
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("VidyoPanaramas cannot be less than current VidyoPanaramas.");
            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
            ex.setFaultMessage(fault);
            throw ex;
        }
		if (tenantdata.isDefaultGuestLocationTagSpecified()) {
			if (tenantdata.getDefaultGuestLocationTag() == null) {
				logger.error("The  defaultGuestLocationTag is empty or null");
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("The  defaultGuestLocationTag is empty or null");
				InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
				ex.setFaultMessage(fault);
				throw ex;
			}
			int[] allowedLocations = tenantdata.getAllowedLocationTagList();
			if (!ArrayUtils.contains(allowedLocations, tenantdata.getDefaultGuestLocationTag().intValue())) {
				logger.error(
						"The provided defaultGuestLocationTag " + tenantdata.getDefaultGuestLocationTag().intValue()
								+ "is not found in the allowedLocationTagList");
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage(
						"The provided defaultGuestLocationTag " + tenantdata.getDefaultGuestLocationTag().intValue()
								+ "is not found in the allowedLocationTagList");
				InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
				ex.setFaultMessage(fault);
				throw ex;
			}

		}

		if (tenantdata.isDefaultGuestProxySpecified()) {
			if (tenantdata.getDefaultGuestProxy() == null) {
				logger.error("The DefaultGuestProxy is empty or null");
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("The DefaultGuestProxy is empty or null");
				InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
				ex.setFaultMessage(fault);
				throw ex;
			}
			int[] allowedProxy = tenantdata.getVidyoProxyList();
			if (!ArrayUtils.contains(allowedProxy, tenantdata.getDefaultGuestProxy().intValue())) {
				logger.error("The provided defaultGuestProxy " + tenantdata.getDefaultGuestLocationTag().intValue()
						+ "is not found in the vidyoProxyList");
				InvalidArgumentFault fault = new InvalidArgumentFault();
				fault.setErrorMessage("The provided defaultGuestProxy "
						+ tenantdata.getDefaultGuestLocationTag().intValue() + "is not found in the vidyoProxyList");
				InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
				ex.setFaultMessage(fault);
				throw ex;
			}

		}
      if(tenantdata.isEnableCustomRoleSpecified()){

     	 Configuration config = systemService.getConfiguration("ENDPOINTBEHAVIOR");
     	 //it cant be null ,that means in super it is not enabled and user want to enable in tenant level
		 if ((config == null || config.getConfigurationValue() == null || config.getConfigurationValue().trim().isEmpty()||config.getConfigurationValue().trim().equalsIgnoreCase("0")) && tenantdata.getEnableCustomRole() ) {

			   logger.error("Custom Role is not enabled in super, so it cannot be enabled at tenant level.");
             InvalidArgumentFault fault = new InvalidArgumentFault();
             fault.setErrorMessage("Custom Role is not enabled in super, so it cannot be enabled at tenant level.");
             InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
             ex.setFaultMessage(fault);
             throw ex;
			}
      }

        if(tenantdata.isEnableEndpointLogAggregationSpecified()){

       	 Configuration config = systemService.getConfiguration("LogAggregationServer");
       	 //it cant be null ,that means in super it is not enabled and user want to enable in tenant level
		 if ((config == null || config.getConfigurationValue() == null || config.getConfigurationValue().trim().isEmpty()) && tenantdata.getEnableEndpointLogAggregation() ) {

			   logger.error("Log aggregation server is not specified in super, so it cannot be enabled at tenant level.");
               InvalidArgumentFault fault = new InvalidArgumentFault();
               fault.setErrorMessage("Log aggregation server is not specified in super, so it cannot be enabled at tenant level.");
               InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
               ex.setFaultMessage(fault);
               throw ex;
			}
        }

    }

    private String getLicenseVersion(final List<SystemLicenseFeature> licenseFeatures) {

		return getLicenseFeatureLicensedValue(LicensingServiceImpl.LIC_VERSION, licenseFeatures);
    }

    private boolean isAnyAPILicensed(final List<SystemLicenseFeature> licenseFeatures) {
    	boolean returnValue = false;

    	String featureLicensedValue = getLicenseFeatureLicensedValue(LicensingServiceImpl.LIC_ALLOW_USER_APIS, licenseFeatures);
    	if(featureLicensedValue != null && featureLicensedValue.equalsIgnoreCase("true")) {
    		returnValue = true;
    	} else {
    		featureLicensedValue = getLicenseFeatureLicensedValue(LicensingServiceImpl.LIC_ALLOW_PORTAL_APIS, licenseFeatures);
    		if(featureLicensedValue != null && featureLicensedValue.equalsIgnoreCase("true")) {
    			returnValue = true;
    		} else  {
    			featureLicensedValue = getLicenseFeatureLicensedValue(LicensingServiceImpl.LIC_ALLOW_EXT_DB, licenseFeatures);
    			if(featureLicensedValue != null && featureLicensedValue.equalsIgnoreCase("true")) {
    				returnValue = true;
    			}
    		}
    	}

    	return returnValue;

    }

    private String getLicenseFeatureLicensedValue(final String featureName, final List<SystemLicenseFeature> licenseFeatures) {
    	int licVersionIndex = -1;
    	String featureLicensedValue = null;
    	SystemLicenseFeature licVersionFeature = new SystemLicenseFeature(featureName, null, null);

    	licVersionIndex = licenseFeatures.indexOf(licVersionFeature);
		if(licVersionIndex != -1) {
			featureLicensedValue = licenseFeatures.get(licVersionIndex).getLicensedValue();
		}

		return featureLicensedValue;
    }

    private Member makeMemberFromAdminMember(AdminMember adminMember, Tenant tenant)
    		throws InvalidArgumentFaultException, Exception {
        String userName = "admin";
        String password = userService.getUserDefaultPassword();;
        String memberName = "AdminFirst AdminLast";
        String emailAddress = "admin@" + tenant.getTenantURL();
        String description = "Default Admin";
        if(adminMember != null) {
           userName = adminMember.getName();
           if (userName.isEmpty() || userName.length() > 80) {   //printabled unicoded username..
               String errMsg = "Invalid UserName. Must be 1 - 80 characters.";
               logger.error(errMsg);
               InvalidArgumentFault fault = new InvalidArgumentFault();
               fault.setErrorMessage(errMsg);
               InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
               ex.setFaultMessage(fault);
               throw ex;
           }
           if (!userName.matches(ValidationUtils.USERNAME_REGEX)) {
                String errMsg = "UserName must start with alphanumeric character, it must not contain any spaces or punctuations except for periods, underscores or dashes.";
                logger.error(errMsg);
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage(errMsg);
                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }
            password = adminMember.getPassword();
            if(password.isEmpty()) {
                String errMsg = "Password is missing";
                logger.error(errMsg);
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage(errMsg);
                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }

            memberName = adminMember.getDisplayName();
            if(memberName.isEmpty() || memberName.length() > 80) {
                String errMsg = "Invalid display name. Must be 1 - 80 characters";
                logger.error(errMsg);
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage(errMsg);
                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }

            emailAddress = adminMember.getEmailAddress();
            if (emailAddress.isEmpty() || !emailAddress.matches(ValidationUtils.EMAIL_REGEX)) {
                String errMsg = "Not a valid email address";
                logger.error(errMsg);
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage(errMsg);
                InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }

            description = adminMember.getDescription();
        }

        Member member = new Member();
        member.setPassword(password);
        member.setUsername(userName);
        member.setEmailAddress(emailAddress);
        member.setMemberName(memberName);
        member.setDescription(description);

        return member;
    }


	@Override
	public SetLogAggregationServerResponse setLogAggregationServer(
			SetLogAggregationServerRequest setLogAggregationServerRequest)
					throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {
		SetLogAggregationServerResponse response = new SetLogAggregationServerResponse();
		if (StringUtils.isBlank(setLogAggregationServerRequest.getLogServer())
				|| !(RegExValidator.validateFQDN(setLogAggregationServerRequest.getLogServer()))) {
			String errMsg = "Not a valid FQDN ";
			logger.error(errMsg);
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage(errMsg);
			InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
			ex.setFaultMessage(fault);
			throw ex;
		}
		int status = systemService.saveSystemConfig("LogAggregationServer",
				setLogAggregationServerRequest.getLogServer(), 0);
		systemService.auditLogTransaction("Log aggregation server is configured", "", status<0?"FAILURE":"SUCCESS");
		if (status < 0) {

			logger.error("Operation failed while saving Log Server");
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Operation failed while saving Log Server");
			GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
			ex.setFaultMessage(fault);
			throw ex;

		}

		response.setOK(OK_type1.OK);

		return response;
	}

	@Override
	public AddClientVersionResponse addClientVersion(AddClientVersionRequest addClientVersionRequest)
			throws InvalidArgumentFaultException, GeneralFaultException,ExternalModeFaultException {
		if (logger.isDebugEnabled()) {
            logger.debug("Entering addClientVersion() of VidyoPortalSuperServiceSkeleton");
        }

        Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
        String tenantName = tenant.getTenantName();

        AddClientVersionResponse response = new AddClientVersionResponse();
        // Get input parameters
        ClientType clientType = addClientVersionRequest.getClientType();
        String installURL = addClientVersionRequest.getInstallerURL();
        String currentTag = addClientVersionRequest.getCurrentTag().getEndpointVersionPattern();
        boolean setActive = addClientVersionRequest.getSetActive();


        try {
		    Configuration endpointUploadModeConfiguration = systemService.getConfiguration("MANAGE_ENDPOINT_UPLOAD_MODE");
			if (endpointUploadModeConfiguration == null || endpointUploadModeConfiguration.getConfigurationValue() == null
					|| !endpointUploadModeConfiguration.getConfigurationValue().equalsIgnoreCase("External")){
				ExternalModeFault fault = new ExternalModeFault();
	            fault.setErrorMessage("Endpoint Upload mode is not set to External File Server");
	            ExternalModeFaultException ex = new ExternalModeFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
			}
			if (clientType == null || StringUtils.isBlank(clientType.getValue())){
				InvalidArgumentFault fault = new InvalidArgumentFault();
	            fault.setErrorMessage("Client Type can not be blank");
	            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
			}
			if (installURL == null || installURL.length() > 1024 || !ValidatorUtil.isValidHTTPHTTPSURL(installURL)) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
	            fault.setErrorMessage("Invalid install URL.");
	            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
			}
			if (currentTag == null || currentTag.replaceAll("[a-zA-Z0-9._]", "").length() > 0
					|| currentTag.length() > 128){
				InvalidArgumentFault fault = new InvalidArgumentFault();
	            fault.setErrorMessage("Invalid Current Tag");
	            InvalidArgumentFaultException ex = new InvalidArgumentFaultException(fault.getErrorMessage());
	            ex.setFaultMessage(fault);
	            throw ex;
			}
        	EndpointUpload endpoint = new EndpointUpload();
       		endpoint.setEndpointUploadFile(installURL);
       		endpoint.setEndpointUploadVersion(currentTag);
       		endpoint.setEndpointUploadType(clientType.getValue().trim());
       		endpoint.setTenantID(0);

    		int endpointId = endpointUploadService.addEndpointUploadSuper(endpoint, true, setActive);
    		response.setEndpointUploadID(java.math.BigInteger.valueOf(endpointId));

    		auditLogTransaction("New client version added by Super","installURL:"+installURL
    				+ ", currentTag:"+ currentTag + ", clientType:"+clientType.getValue(), tenantName, "SUCCESS");
            if(logger.isDebugEnabled()) {
               logger.debug("Exiting addClientVersion() of VidyoPortalSuperServiceSkeleton");
            }

        } catch(Exception anyEx) {
        	auditLogTransaction("New client version added by Super","installURL:"+installURL
    				+ ", currentTag:"+ currentTag + ", clientType:"+clientType.getValue(), tenantName ,"FAILURE");
            logger.error("Failed to  add client version.", anyEx);
            throw anyEx;
        }
        return response;
	}

	private void auditLogTransaction(String tnName, String tnParam, String tenantName, String tnResult) {

		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName(tnName);
		transactionHistory.setTransactionParams(tnParam);
		transactionHistory.setTransactionResult(tnResult);
		transactionHistory.setTransactionTime(Calendar.getInstance()
				.getTime());
		transactionHistory.setTenantName(tenantName);
		transaction.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	@Override
	public SetCustomRoleResponse setCustomRole(SetCustomRoleRequest setCustomRoleRequest)
			throws InvalidArgumentFaultException, GeneralFaultException {

		SetCustomRoleResponse response = new SetCustomRoleResponse();
	
		int status = systemService.saveSystemConfig("ENDPOINTBEHAVIOR",
				setCustomRoleRequest.getEnableCustomRole()?"1":"0", 0);
		systemService.auditLogTransaction("CustomRole is updated ", ""+setCustomRoleRequest.getEnableCustomRole() , status<0?"FAILURE":"SUCCESS");
		if (status < 0) {

			logger.error("Operation failed while enabling/disabling custom role");
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Operation failed while enabling/disabling custom role");
			GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
			ex.setFaultMessage(fault);
			throw ex;

		}
		
		//if it is disabled we need to disable it for all tenants.
		
		if(!setCustomRoleRequest.getEnableCustomRole()){
			tenantService.updateTenantAllCustomRole(0);
		}
		response.setOK(OK_type0.OK);

		return response;
	}
	
	
	 /**
     * Auto generated method signature
     * Set mobile login mode at system level , enforce all tenants to use it.
     * @param setMobileLoginModeRequest 
     * @return setMobileLoginModeResponse 
     * @throws InvalidArgumentFaultException 
     * @throws GeneralFaultException 
     * @throws NotLicensedFaultException 
     */
    
     public com.vidyo.portal.superapi.SetMobileLoginModeResponse setMobileLoginMode
              (com.vidyo.portal.superapi.SetMobileLoginModeRequest setMobileLoginModeRequest)
        throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException {
    	 SetMobileLoginModeResponse response = new SetMobileLoginModeResponse();
    	 int status = -1;	
    	 logger.warn("setMobileLoginModeRequest.getMobileLoginMode() = "+ setMobileLoginModeRequest.getMobileLoginMode());
    	 if( setMobileLoginModeRequest.getMobileLoginMode() != null 
    			 && StringUtils.isNotBlank(setMobileLoginModeRequest.getMobileLoginMode().getValue()) ) {
    		 status = systemService.saveOrUpdateConfiguration(0, "MOBILE_LOGIN_MODE",
    				 ""+TenantServiceImpl.MobileMode.forValue(
    		 					setMobileLoginModeRequest.getMobileLoginMode().getValue()).getIntValue());
    		 if (status > 0) {
    			 status = tenantService.updateTenantMobileAccess(TenantServiceImpl.MobileMode.forValue(
 					setMobileLoginModeRequest.getMobileLoginMode().getValue()).getIntValue());
    		 }
 		}
  		systemService.auditLogTransaction("MobileLoginMode is updated ", 
  				setMobileLoginModeRequest.getMobileLoginMode().getValue() , 
  				status<0?"FAILURE":"SUCCESS");

  		if (status < 0) {
 			logger.error("Operation failed while updating Mobile Login Mode");
 			GeneralFault fault = new GeneralFault();
 			fault.setErrorMessage("Operation failed while updating Mobile Login Mode");
 			GeneralFaultException ex = new GeneralFaultException(fault.getErrorMessage());
 			ex.setFaultMessage(fault);
 			throw ex;
 		}
 		
 		response.setOK(OK_type1.OK);

 		return response;
     }
             
}

