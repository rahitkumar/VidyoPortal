package com.vidyo.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.vidyo.bo.gateway.GatewayPrefix;
import com.vidyo.bo.gateway.GatewayPrefixFilter;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.rest.gateway.Prefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.vidyo.bo.Location;
import com.vidyo.bo.NEConfiguration;
import com.vidyo.bo.RecorderEndpoint;
import com.vidyo.bo.RecorderEndpointFilter;
import com.vidyo.bo.Service;
import com.vidyo.bo.ServiceFilter;
import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.bo.VirtualEndpointFilter;
import com.vidyo.bo.networkconfig.IpAddressMap;
import com.vidyo.bo.networkconfig.RouterPool;
import com.vidyo.bo.tenantservice.TenantServiceMap;
import com.vidyo.components.vidyoproxy.schema.ObjectFactory;
import com.vidyo.components.vidyoproxy.schema.VPConfigType;
import com.vidyo.db.IServiceDao;
import com.vidyo.db.idp.ITenantIdpAttributesMappingDao;
import com.vidyo.db.ldap.ITenantLdapAttributesMappingDao;
import com.vidyo.parser.xml.CloudConfig;
import com.vidyo.parser.xml.VMConfig;
import com.vidyo.parser.xml.VRConfig;
import com.vidyo.parser.xml.cloudconfig.GroupType;
import com.vidyo.parser.xml.cloudconfig.NetworkConfigType;
import com.vidyo.parser.xml.vmconfig.VMConfigType;
import com.vidyo.parser.xml.vmconfig.VpURI;

public class ServiceServiceImpl implements IServiceService {

    protected final Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class.getName());

    private IServiceDao dao;
    private ITenantLdapAttributesMappingDao tenantLdapAttributesMappingDao;
    private ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao;
    private IRouterService router;
    private Jaxb2Marshaller cloudMarshaller;
    private Jaxb2Marshaller vmMarshaller;
    private Jaxb2Marshaller vrMarshaller;

    private ISystemService systemService;

    private boolean useNewGatewayServiceInterface = false;

	public boolean isUseNewGatewayServiceInterface() {
		return useNewGatewayServiceInterface;
	}

	public void setUseNewGatewayServiceInterface(boolean useNewGatewayServiceInterface) {
		this.useNewGatewayServiceInterface = useNewGatewayServiceInterface;
	}

	public void setDao(IServiceDao dao) {
        this.dao = dao;
    }

    public void setRouter(IRouterService router) {
        this.router = router;
    }

    public void setCloudMarshaller(Jaxb2Marshaller marshaller) {
        this.cloudMarshaller = marshaller;
    }

    public void setVmMarshaller(Jaxb2Marshaller marshaller) {
        this.vmMarshaller = marshaller;
    }

    public void setVrMarshaller(Jaxb2Marshaller marshaller) {
        this.vrMarshaller = marshaller;
    }

    public void setTenantLdapAttributesMappingDao(ITenantLdapAttributesMappingDao tenantLdapAttributesMappingDao) {
		this.tenantLdapAttributesMappingDao = tenantLdapAttributesMappingDao;
	}

    public void setTenantIdpAttributesMappingDao(ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao) {
    	this.tenantIdpAttributesMappingDao = tenantIdpAttributesMappingDao;
    }

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

    public List<Service> getServices(ServiceFilter filter) {
        return getServices(filter, "localhost");
    }

    public List<Service> getServices(ServiceFilter filter, String defaultServerNameforLocalHost) {
        List<Service> list = this.dao.getServices(filter, defaultServerNameforLocalHost);
        return list;
    }

    public Long getCountServices(ServiceFilter filter) {
        Long number = this.dao.getCountServices(filter);
        return number;
    }

    public Service getServiceByUserName(String serviceUserName, String serviceRole) {
        Service service = this.dao.getServiceByUserName(serviceUserName, serviceRole);
        return service;
    }

    public Integer getCountServiceByUserName(String serviceUserName, String serviceRole, int compID) {
    	Integer serviceCount = this.dao.getCountServiceByUserName(serviceUserName, serviceRole, compID);
        return serviceCount;
    }

    public Service getVG(int serviceID) {
        Service gateway = this.dao.getVG(serviceID);
        return gateway;
    }

    public int updateVG(int serviceID, Service service) {
        int rc = this.dao.updateVG(serviceID, service);
        return rc;
    }

    public int insertVG(Service service) {
        int rc = this.dao.insertVG(service);
        return rc;
    }

    public int deleteVG(int serviceID) {
        int rc = this.dao.deleteVG(serviceID);
        return rc;
    }

    public int getTenantIDforServiceID(int serviceID) {
        return this.dao.getTenantIDforServiceID(serviceID);
    }

	public void resetGatewayPrefixes(int serviceID, String gatewayID, Set<Prefix> prefixes) {
        int tenantID = this.dao.getTenantIDforServiceID(serviceID);
		this.dao.resetGatewayPrefixes(serviceID, gatewayID, prefixes, tenantID);
	}

    public void updateGatewayPrefixesTimestamp(int serviceID) {
        this.dao.updateGatewayPrefixesTimestamp(serviceID);
    }

    public int clearStaleGatewayPrefixesOlderThan(int seconds) {
        return this.dao.clearStaleGatewayPrefixesOlderThan(seconds);
    }

    public Service getVM(int serviceID) {
        Service vidyomanager = this.dao.getVM(serviceID);
        return vidyomanager;
    }

    public int updateVM(int serviceID, Service service) {
        int rc = this.dao.updateVM(serviceID, service);
        return rc;
    }

    public int insertVM(Service service) {
        int rc = this.dao.insertVM(service);
        return rc;
    }

    public int deleteVM(int serviceID) {
        int rc = this.dao.deleteVM(serviceID);
        return rc;
    }

    public Service getVP(int serviceID) {
        Service vidyoproxy = this.dao.getVP(serviceID);
        if(vidyoproxy != null && vidyoproxy.getUrl() != null && !vidyoproxy.getUrl().contains(":")) {
        	vidyoproxy.setUrl(vidyoproxy.getUrl()+ ":443");
        }
        return vidyoproxy;
    }

    public Boolean getTLSProxyConfiguration() {
        return systemService.getTLSProxyConfiguration();
    }

    public int updateVP(int serviceID, Service service) {
    	Service oldVidyoProxy = this.dao.getVP(serviceID);
    	int rc = this.dao.updateVP(serviceID, service);

    	// Update LDAP mapping
        if(!oldVidyoProxy.getServiceName().equalsIgnoreCase(service.getServiceName())) {
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(0,
    				"Proxy", oldVidyoProxy.getServiceName(), service.getServiceName());
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeValueMappingVidyoValueName(0,
    				"Proxy", oldVidyoProxy.getServiceName(), service.getServiceName());

    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(0,
    				"Proxy", oldVidyoProxy.getServiceName(), service.getServiceName());
    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeValueMappingVidyoValueName(0,
    				"Proxy", oldVidyoProxy.getServiceName(), service.getServiceName());
    	}
        return rc;
    }

    public int insertVP(Service service) {
        int rc = this.dao.insertVP(service);
        return rc;
    }

    public int deleteVP(int serviceID) {
    	Service proxy = getVP(serviceID);
    	if(proxy != null && proxy.getServiceName() != null) {
            tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(0,
         			"Proxy", proxy.getServiceName(), "No Proxy");
         	tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecords(0,
         			"Proxy", proxy.getServiceName());

         	tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(0,
         			"Proxy", proxy.getServiceName(), "No Proxy");
         	tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecords(0,
         			"Proxy", proxy.getServiceName());

            // find out all tenants using this location for guest settings and update to default
            systemService.updateGuestProxyId(proxy.getServiceID(), 0);
    	} else {
    		return 0;
    	}
        return 1;
    }

    public String getProxyCSVList(int memberID) {
        String memberProxy = this.dao.getProxyCSVList(memberID);
        // SCIP FQDN will not have port information - hard coded to 443, should be moved to DB
        if(memberProxy != null && !memberProxy.contains(":443")) {
        	memberProxy = memberProxy.concat(":443");
        }
        return memberProxy;
    }

    public List<VirtualEndpoint> getVirtualEndpoints(int serviceID, VirtualEndpointFilter filter) {
        List<VirtualEndpoint> list = this.dao.getVirtualEndpoints(serviceID, filter);
        return list;
    }

    public Long getCountVirtualEndpoints(int serviceID) {
        Long number = this.dao.getCountVirtualEndpoints(serviceID);
        return number;
    }

	public List<GatewayPrefix> getGatewayPrefixes(int serviceID, GatewayPrefixFilter filter) {
		List<GatewayPrefix> list = this.dao.getGatewayPrefixes(serviceID, filter);
		return list;
	}

	public Long getCountGatewayPrefixes(int serviceID) {
		Long number = this.dao.getCountGatewayPrefixes(serviceID);
		return number;
	}

    public int registerVirtualEndpoint(int serviceID, VirtualEndpoint ve) {
        int tenantID = this.dao.getTenantIDforServiceID(serviceID);
        int rc = this.dao.registerVirtualEndpoint(serviceID, ve, tenantID);
        return rc;
    }

    public boolean isVGExistsWithServiceName(String serviceName){
        boolean rc = this.dao.isVGExistsWithServiceName(serviceName);
        return rc;
    }

    public boolean isVGExistsWithLoginName(String loginName, int serviceID){
        boolean rc = this.dao.isVGExistsWithLoginName(loginName, serviceID);
        return rc;
    }

    public List<NEConfiguration> getNEConfigurations(String wildcard, String type) {
        List<NEConfiguration> list = this.dao.getNEConfigurations(wildcard, type);
        return list;
    }

    /**
     *
     * @return  0 - All-In-One, 1 VM and 1VR
     *          1 - Not All-In-One, 1 VM and 1..n VRs, no VR in same box of VM
     *          2 - Not All-In-One, 1 VM and 1..n VRs, 1 VR in same box of VM
     */
    public int isAllInOne() {
        List<NEConfiguration> list = this.dao.getNEConfigurations("%", "VidyoManager");
        String vmId=null;
        for(int i = 0; i<list.size(); i++ ) { //should be one VidyoManager
            NEConfiguration nec = list.get(i);
            if( (nec.getStatus().equalsIgnoreCase("ACTIVE")) && (nec.getIdentifier().indexOf("00VM0001")!=-1) ) {
                vmId = nec.getIdentifier();
                vmId = vmId.substring(0, vmId.indexOf("00VM0001"));
            }
        }
        assert(vmId != null);

        list = this.dao.getNEConfigurations("%", "VidyoRouter");
        int totalActiveVrNumber = 0;
        boolean matched = false;
        String vrId = null;
        for(int i = 0; i<list.size(); i++ ) {
            NEConfiguration nec = list.get(i);
            if( nec.getStatus().equalsIgnoreCase("ACTIVE") && (nec.getIdentifier().indexOf("00VR0001")!=-1)  ) {
                totalActiveVrNumber++;
                vrId = nec.getIdentifier();
                vrId = vrId.substring(0, vrId.indexOf("00VR0001"));
                if(vrId.equals(vmId)) {
                    matched = true;
                }
            }
        }

        if(matched)  {
            if(totalActiveVrNumber == 1)
                return 0;
            else
                return 2;
        }
        else {
            return 1;
        }
    }

    public List<NEConfiguration> getSingleNEConfiguration(String id, String type){
        List<NEConfiguration> list = this.dao.getSingleNEConfiguration(id, type);
        return list;
    }

    public Long getCountNEConfigurations() {
        Long number = this.dao.getCountNEConfigurations();
        return number;
    }

    public boolean updateNEConfiguration(NEConfiguration nec) {
        return this.dao.updateNEConfiguration(  nec,
                                                false,      //true-status becomes "NEW"
                                                true);      //true-increase version, false-version won't be increased
    }

    public boolean updateNEConfiguration(NEConfiguration nec, boolean increaseVersion) {
    	//Incase of VidyoRouter component type, check for embedded and stand-alone case
    	if(nec.getComponentType().equalsIgnoreCase("VidyoRouter")) {
    		List<NEConfiguration> vrList = this.getNEConfigurations("%","VidyoRouter");
    		if(vrList.size() > 1) {
        		List<NEConfiguration> vmList = this.getNEConfigurations("%","VidyoManager");
    			Iterator<NEConfiguration> it = vmList.iterator();
    			String vmIp = null;
    			while (it.hasNext()) {
    				NEConfiguration vmNec = (NEConfiguration) it.next();
    				vmIp = vmNec.getIpAddress(); // Assumption: Single VM. In case of
    											// multiple VM, this line should be
    											// changed
    			}
        		//Incoming request is Stand-Alone router, remove the Embedded Router
    			if(!nec.getIpAddress().equalsIgnoreCase(vmIp)) {
    				removeBuildInVR();
    			}
    		}
    	}
    	//Invoke the clear cache API
    	Integer tenantId = null;
    	try {
    		tenantId = TenantContext.getTenantId();
    	} catch(Exception e) {
    		logger.error("Tenant ThreadLocal not available", e.getMessage());
    	}
    	if(tenantId == null) {
    		tenantId = 1;
    	}
    	systemService.clearVidyoManagerAndLicenseCache(tenantId);
        return this.dao.updateNEConfiguration(  nec,
                                                false,       //true-status becomes "NEW"
                                                increaseVersion);
    }

    public boolean updateInProgressNetworkConfig(NEConfiguration nec) {
       return this.dao.updateInProgressNetworkConfig(nec);
    }

    public boolean activateNetworkConfig() {
        return this.dao.activateNetworkConfig();
    }

    public boolean discardInProgressNetworkConfig() {
        return this.dao.discardInProgressNetworkConfig();
    }
    public boolean deleteNEConfiguration(String id) {
        return this.dao.deleteNEConfiguration(id);
    }

    public boolean enableNEConfiguration(String id, boolean enable){
        return this.dao.enableNEConfiguration(id, enable);
    }

    public NEConfiguration getNetworkConfig(String status, int version) {
        return this.dao.getNetworkConfig(status, version);
    }

    public boolean replaceSystmID(String newID, String type, String ip, String replaceTriggerInYear) {
        return this.dao.replaceSystmID(newID, type, ip, replaceTriggerInYear);
    }

    public boolean setFactoryDefaultNEConfiguration(NEConfiguration nec, String vmIp, String vmId, String docVer){
        String templateFilename;
        String type = nec.getComponentType();

        String data = this.getFactoryDefaultConfig(type, nec.getDisplayName(), vmIp, vmId, docVer);
        if(data.length()==0)
            return false;

        nec.setData(data);

        return this.dao.updateNEConfiguration(nec,
                                              false,        //true-set status of "NEW"
                                              true);       //true-increase version, false-version won't be increased
    }

    public String getFactoryDefaultConfig(String type, String displayName, String vmIp, String vmId, String docVer){
        String templateFilename;

        if(type.equals("VidyoRouter") || type.equals("VidyoManager")) {
            String data;
            if(type.equals("VidyoRouter"))
                templateFilename =  "/opt/vidyo/vidyorouter2/vrconfig.xml.template";
            else
                templateFilename = "/opt/vidyo/vm/vmconfig.xml.template";

            try {
                StringBuffer fileData = new StringBuffer(1024);
                BufferedReader reader = new BufferedReader(new FileReader(templateFilename));
                char[] buf = new char[1024];
                int numRead=0;
                while((numRead=reader.read(buf)) != -1){
                    String readData = String.valueOf(buf, 0, numRead);
                    fileData.append(readData);
                    buf = new char[1024];
                }
                reader.close();
                data = fileData.toString();
                data = data.replaceAll("VERSION", docVer);
                if(type.equals("VidyoRouter")) {
                    data = data.replaceAll("VR_CMCP_PORT", "17990");
                    data = data.replaceAll("VMID@localhost:VM_RMCP_PORT", vmId+"@"+vmIp+":17991");
                    if(displayName!=null)
                        data = data.replaceAll("DEFAULT_NAME", displayName);
                }
                else {
                    data = data.replaceAll("VM_SOAP_PORT", "17995");
                    data = data.replaceAll("VM_EMCP_PORT", "17992");
                    data = data.replaceAll("VM_RMCP_PORT", "17991");
                }
                return data;
            }
            catch(Exception e){
                return "";
            }
        }
        else if(type.equals("VidyoProxy")){
            Boolean tlsProxyEnabled = systemService.getTLSProxyConfiguration();
            if(tlsProxyEnabled){
                return "<" +
                        "Config><URL>localhost:443</URL>" + "<TLSProxy>" + tlsProxyEnabled.toString() + ";" +
                        "" + "</Config>";
            }
            return "<Config><URL>localhost:443</URL></Config>";
        }
        else if(type.equals("VidyoGateway")){
            return "<VGConfig/>";
        }
        else
            return "";
    }

    /**
     * Reconfig vmconfig.xml in database.
     * Reconfig vrconfig.xml in database.
     * Reconfig NetworkConfig.xml in database.
     *
     * @param ipAddress
     * @param fqdn
     * @param portalUriFqdn
     * @throws Exception
     */
    public void reconfigVmVrNetworkConfigForAllInOne(String ipAddress, String fqdn, String portalUriFqdn) throws Exception {
        //Reconfig vmconfig.xml in database.
        List<NEConfiguration> list = this.getNEConfigurations("%", "VidyoManager");
        Iterator it = list.iterator();
        String vmIp=null;
        String vmId=null;
        while (it.hasNext()) {
            NEConfiguration nec = (NEConfiguration)it.next();
            vmIp = nec.getIpAddress();   //Assumption: Single VM.   In case of multiple VM, this line should be changed
            vmId = nec.getIdentifier();  //Assumption: Single VM.   In case of multiple VM, this line should be changed
            String xml = nec.getData();
            VMConfig vmConfig = new VMConfig(vmMarshaller);
            vmConfig.fromXml(xml);
            vmConfig.setIPAddress(ipAddress);
            vmConfig.resetEmcpAddressPort(fqdn, 17992);
            vmConfig.resetRmcpAddress(fqdn);
            vmConfig.resetVidyoPortalUri(portalUriFqdn);
            vmConfig.setDocumentVersion(nec.getVersion()+1);
            xml = vmConfig.toXml();
            nec.setData(xml);
            this.updateNEConfiguration(nec, true);
        }
        //Reconfig vrconfig.xml in database.
        list = this.getNEConfigurations("%", "VidyoRouter");
        it = list.iterator();
        String buildInVidyoRouterID = null;
        while (it.hasNext()) {
            NEConfiguration nec = (NEConfiguration)it.next();
            String xml = nec.getData();
            VRConfig vrConfig = new VRConfig(vrMarshaller);
            vrConfig.fromXml(xml);

            if(nec.getIpAddress().equalsIgnoreCase(vmIp)) {
                //changes made on build-in VR
                vrConfig.resetCMCPAddressPort(fqdn, 17990);
                buildInVidyoRouterID = nec.getIdentifier();
            }

            vrConfig.resetConnectVMUri(vmId, fqdn);     //vmId is no longer optional. Set vmId to each VR at this time
            vrConfig.setDocumentVersion(nec.getVersion()+1);
            xml = vrConfig.toXml();
            nec.setData(xml);
            this.updateNEConfiguration(nec, true);
        }

        //Reconfig NetworkConfig.xml in database.
        if(buildInVidyoRouterID != null) {
            NEConfiguration nec = this.getNetworkConfig("ACTIVE", -1);

            String xml = nec.getData();
            CloudConfig cloudConfig = new CloudConfig(cloudMarshaller);
            cloudConfig.fromXml(xml);
            cloudConfig.setNetworkElementScipById(buildInVidyoRouterID, fqdn, 17990, null);

            xml = cloudConfig.toXml();
            nec.setData(xml);
            this.updateInProgressNetworkConfig(nec);
            this.activateNetworkConfig();
        }
    }

    /**
     * Open existing vmconfig.xml:
     * find <EMCPListenAddress>0.0.0.0:17992</EMCPListenAddress>, and change to given fqdn:443,
     * find <RMCPListenAddress>0.0.0.0:17991</RMCPListenAddress> and change RmcpAddress to ipAddress:17991
     * find VidyoPortalConnectAddress's <Uri>http://localhost</Uri> and change it to portalUriFqdn
     *
     * Open existing vrconfig.xml:
     * set VMID in following if it's missed(VMID was optional in previous version), for example:
     *   <ConnectVM>
     *     <Uri>DW3U14P9G8RMQBUAZTMYUU349DHWEHNHUKD6EJPAB8QGT00VM0001@localhost:17991</Uri>
     *     ...
     *   </ConnectVM>
     *
     * Remove existing build in VR, VP if available
     *
     * @param ipAddress
     * @param fqdn
     * @param portalUriFqdn
     * @throws Exception
     */
    public void reconfigVmVrConfigForNotAllInOne(String ipAddress, String fqdn, String portalUriFqdn) throws Exception {

        List<NEConfiguration> list = this.getNEConfigurations("%", "VidyoManager");
        Iterator it = list.iterator();
        String vmIp=null;
        String vmId=null;
        while (it.hasNext()) {
            NEConfiguration nec = (NEConfiguration)it.next();
            vmIp = nec.getIpAddress();   //Assumption: Single VM.   In case of multiple VM, this line should be changed
            vmId = nec.getIdentifier();  //Assumption: Single VM.   In case of multiple VM, this line should be changed
            String xml = nec.getData();
            VMConfig vmConfig = new VMConfig(vmMarshaller);
            vmConfig.fromXml(xml);
            vmConfig.setIPAddress(ipAddress);
            vmConfig.resetEmcpAddressPort(fqdn, 443);
            vmConfig.resetRmcpAddress(fqdn);
            vmConfig.resetVidyoPortalUri(portalUriFqdn);
            vmConfig.setDocumentVersion(nec.getVersion()+1);
            xml = vmConfig.toXml();
            nec.setData(xml);
            this.updateNEConfiguration(nec, true);
        }

        //automatically set VMID to every routers
        list = this.getNEConfigurations("%", "VidyoRouter");
        it = list.iterator();
        String buildInVidyoRouterID = null;
        while (it.hasNext()) {
            NEConfiguration nec = (NEConfiguration)it.next();
            String xml = nec.getData();
            VRConfig vrConfig = new VRConfig(vrMarshaller);
            vrConfig.fromXml(xml);

            if(nec.getIpAddress().equalsIgnoreCase(vmIp)) {
                buildInVidyoRouterID = nec.getIdentifier();
            }

            vrConfig.resetConnectVMUri(vmId, fqdn);     //vmId is no longer optional. Set vmId to each VR at this time
            vrConfig.setDocumentVersion(nec.getVersion()+1);
            xml = vrConfig.toXml();
            nec.setData(xml);
            this.updateNEConfiguration(nec, true);
        }

        //Find if we have the buildin VidyoProxy
        list = this.getNEConfigurations("%", "VidyoProxy");
        it = list.iterator();
        String buildInVidyoProxyID = null;
        while (it.hasNext()) {
            NEConfiguration nec = (NEConfiguration)it.next();
            if(nec.getIpAddress().equalsIgnoreCase(vmIp)) {
                buildInVidyoProxyID = nec.getIdentifier();
            }
        }

        //remove buildin VR
        if(buildInVidyoRouterID != null) {
            this.deleteNEConfiguration(buildInVidyoRouterID);
        }
        //remove buildin VP
        if(buildInVidyoProxyID != null) {
            this.deleteNEConfiguration(buildInVidyoProxyID);
        }

        //No need to change anything in NetworkConfig.xml because the buildin VR is not used and all standalone VR
        //should have correct SCIP setting before the upgrade.
    }

    public void resetVmconfigPort(int port) throws Exception {
        List<NEConfiguration> list = this.getNEConfigurations("%", "VidyoManager");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NEConfiguration nec = (NEConfiguration)it.next();
            String xml = nec.getData();
            VMConfig vmConfig = new VMConfig(vmMarshaller);
            vmConfig.fromXml(xml);
            vmConfig.resetEmcpAddressPort(null, port);
            vmConfig.setDocumentVersion(nec.getVersion()+1);
            xml = vmConfig.toXml();
            nec.setData(xml);
            this.updateNEConfiguration(nec, true);
        }
    }

    public Service getVGByName(String name) {
        Service gateway = this.dao.getVGByName(name);
        return gateway;
    }

    public Service getVRecByName(String name) {
        Service recorder = this.dao.getVRecByName(name);
        return recorder;
    }

    public Service getVPByName(String name) {
        Service vidyoproxy = this.dao.getVPByName(name);
        return vidyoproxy;
    }

    public Service getVMByName(String name) {
        Service vidyoproxy = this.dao.getVMByName(name);
        return vidyoproxy;
    }

    public Service getSuperVM() {
        Service vidyomanager = this.dao.getSuperVM();
        return vidyomanager;
    }

    public int insertNetworkElementConfig(NEConfiguration nec){
        int rc = this.dao.insertNetworkElementConfig(nec);
        return rc;
    }
    public int touchNetworkElementConfig(NEConfiguration nec){
        return this.dao.touchNetworkElementConfig(nec);
    }

    /**
     * Check if the service is in used by tenant, user, etc
     * @param serviceName   the name of service
     * @param type          1-'VidyoManager', 2-'VidyoGateway', 3-'VidyoProxy' (For Services or TenantXservice related)
     *                      0-'VidyoRouterPool'  (For Routers or TenantXrouter related)
     * @return              true - if it's in use
     *                      false- if no one use it
     */
    public boolean isServiceUsed(String type, String serviceName){
        return this.dao.isServiceUsed(TenantContext.getTenantId(), type, serviceName);
    }

    /**
     * Replace service used by tenants, members.
     * @param toBeDeleteServiceName     the old name of Service record which should be replaced
     * @param replacementServiceName    the new name of Service
     * @param type                      1-'VidyoManager', 2-'VidyoGateway', 3-'VidyoProxy' (For Services or TenantXservice related)
     *                                  0-'VidyoRouterPool'  (For Routers or TenantXrouter related)
     * @return                          true - if successfully replaced
     *                                  false - if failed to replace
     */
    public boolean replaceNetworkComponent(String toBeDeleteServiceName, String replacementServiceName, int type) {
    	if(type > 0){//not router
    		boolean isReplaced = this.dao.replaceNetworkComponent(TenantContext.getTenantId(), type, toBeDeleteServiceName, replacementServiceName);

    		if(type == 3 && isReplaced) {
	    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(0,
		     			"Proxy", toBeDeleteServiceName, replacementServiceName);
		     	tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecords(0,
		     			"Proxy", toBeDeleteServiceName);
    		}

	     	return isReplaced;
        }
    	else{
    		return this.router.replaceRouter(toBeDeleteServiceName, replacementServiceName);
    	}
    }

    public boolean replaceNetworkComponent(String toBeDeleteServiceName, String replacementServiceName, String type) {
   		boolean isReplaced = this.dao.replaceNetworkComponent(TenantContext.getTenantId(), type, toBeDeleteServiceName, replacementServiceName);
		if(type.equalsIgnoreCase("VidyoRouter") && isReplaced) {
	    	// Update LDAP & SAML mapping
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(0,
    				"Proxy", toBeDeleteServiceName, replacementServiceName);
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeValueMappingVidyoValueName(0,
    				"Proxy", toBeDeleteServiceName, replacementServiceName);

    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(0,
    				"Proxy", toBeDeleteServiceName, replacementServiceName);
    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeValueMappingVidyoValueName(0,
    				"Proxy", toBeDeleteServiceName, replacementServiceName);
		}
     	return isReplaced;
    }

    public Service getRec(int serviceID) {
        Service recorder = this.dao.getRec(serviceID);
        return recorder;
    }

    public int updateRec(int serviceID, Service service) {
        int rc = this.dao.updateRec(serviceID, service);
        return rc;
    }

    public int insertRec(Service service) {
        int rc = this.dao.insertRec(service);
        return rc;
    }

    public int deleteRec(int serviceID) {
        int rc = this.dao.deleteRec(serviceID);
        return rc;
    }

    public boolean isRecExistsWithServiceName(String serviceName) {
        boolean rc = this.dao.isRecExistsWithServiceName(serviceName);
        return rc;
    }

    public boolean isRecExistsWithLoginName(String loginName, int serviceID){
        boolean rc = this.dao.isRecExistsWithLoginName(loginName, serviceID);
        return rc;
    }

    public List<RecorderEndpoint> getRecorderEndpoints(int serviceID, RecorderEndpointFilter filter) {
        List<RecorderEndpoint> list = this.dao.getRecorderEndpoints(serviceID, filter);
        return list;
    }

    public Long getCountRecorderEndpoints(int serviceID) {
        Long number = this.dao.getCountRecorderEndpoints(serviceID);
        return number;
    }

    public int registerRecorderEndpoint(int serviceID, RecorderEndpoint re) {
        int rc = this.dao.registerRecorderEndpoint(serviceID, re);
        return rc;
    }

    public int clearRegisterRecorderEndpoint(int serviceID) {
        int rc = this.dao.clearRegisterRecorderEndpoint(serviceID);
        return rc;
    }

    public Service getReplay(int serviceID) {
        Service replay = this.dao.getReplay(serviceID);
        return replay;
    }

    public int updateReplay(int serviceID, Service service) {
        int rc = this.dao.updateReplay(serviceID, service);
        return rc;
    }

    public int insertReplay(Service service) {
        int rc = this.dao.insertReplay(service);
        return rc;
    }

    public int deleteReplay(int serviceID) {
        int rc = this.dao.deleteReplay(serviceID);
        return rc;
    }

    public boolean isReplayExistsWithServiceName(String serviceName) {
        boolean rc = this.dao.isReplayExistsWithServiceName(serviceName);
        return rc;
    }

    public boolean isReplayExistsWithLoginName(String loginName, int serviceID){
        boolean rc = this.dao.isReplayExistsWithLoginName(loginName, serviceID);
        return rc;
    }

    public List<Location> getLocations(ServiceFilter filter) {
        List<Location> list = this.dao.getLocations(filter);
        return list;
    }

    @Override
	public List<Location> getSelectedLocationTags(ServiceFilter serviceFilter,
			int tenantId) {
    	  List<Location> list = this.dao.getSelectedLocationTags(serviceFilter,tenantId);
          return list;
	}
    public Long getCountLocations(ServiceFilter filter) {
        Long number = this.dao.getCountLocations(filter);
        return number;
    }
    public Long getCountLocations(ServiceFilter filter,int tenantId) {
        Long number = this.dao.getCountLocations(filter,tenantId);
        return number;
    }

    public Location getLocation(int locationID) {
        Location location = this.dao.getLocation(locationID);
        return location;
    }

    public int updateLocation(int locationID, Location location) {
        //update cloud config in NetworkConfig table
        String oldLocTag = this.dao.getLocation(locationID).getLocationTag();

        //update Locations table
        int rc = this.dao.updateLocation(locationID, location);

        // Update LDAP and IdP mapping
        if(!oldLocTag.equalsIgnoreCase(location.getLocationTag())) {
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(0,
    				"LocationTag", oldLocTag, location.getLocationTag());
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeValueMappingVidyoValueName(0,
    				"LocationTag", oldLocTag, location.getLocationTag());

    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(0,
    				"LocationTag", oldLocTag, location.getLocationTag());
    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeValueMappingVidyoValueName(0,
    				"LocationTag", oldLocTag, location.getLocationTag());
    	}

        String newLocTag = location.getLocationTag();
        try{

            NEConfiguration nec;
            nec = this.getNetworkConfig("INPROGRESS", -1);
            if(nec == null) {
                nec = this.getNetworkConfig("ACTIVE", -1);
            }

            String xml = nec.getData();
            CloudConfig cloudConfig = new CloudConfig(cloudMarshaller);
            cloudConfig.fromXml(xml);
            cloudConfig.replaceLocationTag(oldLocTag, newLocTag);
            xml = cloudConfig.toXml();
            nec.setData(xml);
            this.updateInProgressNetworkConfig(nec);
            //this.activateNetworkConfig();
        }
        catch(Exception e) {
            //do nothing.
        }

        return rc;
    }

    public int insertLocation(Location location) {
        int rc = this.dao.insertLocation(location);
        return rc;
    }

    public int deleteLocation(int locationID) {
    	Location deletingLocation = getLocation(locationID);
    	int rc = this.dao.deleteLocation(locationID);

        // Update LDAP mappings
        int defaultLocationTagID = systemService.getManageLocationTag();
        Location defaultLocation = getLocation(defaultLocationTagID);

        tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(0,
     			"LocationTag", deletingLocation.getLocationTag(), defaultLocation.getLocationTag());
     	tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecords(0,
     			"LocationTag", deletingLocation.getLocationTag());

     	tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(0,
     			"LocationTag", deletingLocation.getLocationTag(), defaultLocation.getLocationTag());
     	tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecords(0,
     			"LocationTag", deletingLocation.getLocationTag());

        // find out all tenants using this location for guest settings and update to default
        systemService.updateTenantGuestSettingsLocationTag(locationID, defaultLocationTagID);

        return rc;
    }

    public boolean isLocationExistsWithLocationTag(String locationTag, int locationID) {
        boolean rc = this.dao.isLocationExistsWithLocationTag(locationTag, locationID);
        return rc;
    }

    public String getLocationTagForMember(int memberID) {
        String locTag = this.dao.getLocationTagForMember(memberID);
        return locTag;
    }

	public int getLocationIdByLocationTag(String locationTag) {
		int locationID = dao.getLocationIdByLocationTag(locationTag);
		return locationID;
	}

    public String checkVProxyIp() {
        String vp = null;
        try {
            //read
            BufferedReader  br = new BufferedReader (new FileReader("/opt/vidyo/StartVC2.sh"));
            String inLine = null;
            while ((inLine = br.readLine()) != null) {
                if( (inLine.indexOf("/opt/vidyo/vidyoproxy/VPServer")!=-1) &&
                    (inLine.indexOf("-x")!=-1)  ) {
                    StringTokenizer st = new StringTokenizer(inLine);
                    String t;
                    while(st.hasMoreTokens()) {
                        if(st.nextToken().equals("-i")) {
                            return st.nextToken();
                        }
                    }
                }
            }
            br.close();
        }
        catch(Exception e) {
        }
        return vp;
    }

    public void removeBuildInVR() {
        List<NEConfiguration> list = this.dao.getNEConfigurations("%", "VidyoManager");
        String vmId=null;
        for(int i = 0; i<list.size(); i++ ) { //should be one VidyoManager
            NEConfiguration nec = list.get(i);
            if( (nec.getStatus().equalsIgnoreCase("ACTIVE")) && (nec.getIdentifier().indexOf("00VM0001")!=-1) ) {
                vmId = nec.getIdentifier();
                vmId = vmId.substring(0, vmId.indexOf("00VM0001"));
            }
        }
        assert(vmId != null);

        String vrId = vmId+"00VR0001";
        this.dao.deleteNEConfiguration(vrId);
    }

	/**
	 *
	 * @param ipAddress
	 * @param fqdn
	 * @throws Exception
	 */
	public void reconfigVmVrNetworkConfig(String ipAddress, String fqdn)
			throws Exception {
		List<NEConfiguration> list = this.getNEConfigurations("%",
				"VidyoManager");
		Iterator it = list.iterator();
		String vmIp = null;
		String vmId = null;
		String portalOldIp = null;
		String portalOldFqdn = null;
		while (it.hasNext()) {
			NEConfiguration nec = (NEConfiguration) it.next();
			vmIp = nec.getIpAddress(); // Assumption: Single VM. In case of
										// multiple VM, this line should be
										// changed
			vmId = nec.getIdentifier(); // Assumption: Single VM. In case of
										// multiple VM, this line should be
										// changed
			String xml = nec.getData();
			VMConfig vmConfig = new VMConfig(vmMarshaller);
			vmConfig.fromXml(xml);
			// vmConfig.setIPAddress(ipAddress); //Remove IPAddress
			VMConfigType vmConfigType = vmConfig.getVmtype();
			VpURI vpURI = vmConfigType.getVidyoPortalAddressList().getVidyoPortalConnectAddress().get(0);
			portalOldIp = vmIp;
			portalOldFqdn = vpURI.getUri();
			if(portalOldFqdn.contains("://")) {
				portalOldFqdn = portalOldFqdn.substring(portalOldFqdn.indexOf("://") + 3);
			}
			vmConfig.resetEmcpAddressPort(fqdn, 17992); // EMCP 17992 change
			vmConfig.resetRmcpAddress(fqdn);
			vmConfig.resetVidyoPortalUri(fqdn);
			vmConfig.resetSOAPAddress(fqdn);
			//Don't increase the version
			vmConfig.setDocumentVersion(nec.getVersion());
			xml = vmConfig.toXml();
			nec.setData(xml);
			this.updateNEConfiguration(nec, false);
		}

		// automatically set VMID to every routers
		list = this.getNEConfigurations("%", "VidyoRouter");
		it = list.iterator();
		String buildInVidyoRouterID = null;
		while (it.hasNext()) {
			NEConfiguration nec = (NEConfiguration) it.next();
			String xml = nec.getData();
			VRConfig vrConfig = new VRConfig(vrMarshaller);
			vrConfig.fromXml(xml);

			if (nec.getIpAddress().equalsIgnoreCase(vmIp)) {
				buildInVidyoRouterID = nec.getIdentifier();
			}
			vrConfig.resetConnectVMUri(vmId, fqdn); // vmId is no longer
													// optional. Set vmId to
													// each VR at this time
			//Don't increase the version
			vrConfig.setDocumentVersion(nec.getVersion());
			xml = vrConfig.toXml();
			nec.setData(xml);
			this.updateNEConfiguration(nec, false);
		}

		// Reconfig NetworkConfig.xml in database.
		if (buildInVidyoRouterID != null) {
			NEConfiguration nec = this.getNetworkConfig("ACTIVE", -1);

			String xml = nec.getData();
			CloudConfig cloudConfig = new CloudConfig(cloudMarshaller);
			cloudConfig.fromXml(xml);
			cloudConfig.setNetworkElementScipById(buildInVidyoRouterID, fqdn,
					17990, null);

			xml = cloudConfig.toXml();
			nec.setData(xml);
			this.updateInProgressNetworkConfig(nec);
			this.activateNetworkConfig();
		}

		//updateVidyoProxyConfig(ipAddress, fqdn, portalOldIp, portalOldFqdn);
		// update VM IP address to FQDN if applicable
		ServiceFilter serviceFilter = new ServiceFilter();
		List<Service> services = dao.getServices(serviceFilter);
		for (Service service : services) {
			if (service.getRoleName().equalsIgnoreCase("VidyoManager")) {
				// nullify the password, so its not updated
				service.setPassword(null);
				String oldFqdn = null;
				String port = null;
				String scheme = null;
				if(service.getUrl().contains("://")) {
					oldFqdn = service.getUrl().substring(service.getUrl().indexOf("://") + 3);
					scheme = service.getUrl().substring(0, service.getUrl().indexOf("://"));
				} else {
					oldFqdn	= service.getUrl();
				}
				String[] urlPort = oldFqdn.split(":");
				port = urlPort[1];
				scheme = scheme != null ? scheme + "://" : "";
				service.setUrl(scheme + fqdn + ":" + port);
				dao.updateVM(service.getServiceID(), service);
				break;
			}
		}

	}

	/**
	 * Updates the ip address & fqdn of the portal in the proxy config data.
	 *
	 * @param ip
	 * @param fqdn
	 */
	@SuppressWarnings("unchecked")
	/*private void updateVidyoProxyConfig(String ip, String fqdn, String oldIp, String oldFqdn) {
		List<NEConfiguration> list = getNEConfigurations("%", "VidyoProxy");

		for (NEConfiguration neConfiguration : list) {
			InputStream is = new ByteArrayInputStream(neConfiguration.getData()
					.getBytes());
			JAXBElement<VPConfigType> jaxbElement = (JAXBElement<VPConfigType>) vidyoProxyConfigMarshaller
					.unmarshal(new StreamSource(is));
			VPConfigType vpConfigType = (VPConfigType) jaxbElement.getValue();
			int index = 0;
			for(String host: vpConfigType.getHost()) {
				if(host.equalsIgnoreCase(oldIp)) {
					vpConfigType.getHost().set(index, ip);
				}
				if(host.equalsIgnoreCase(oldFqdn)) {
					vpConfigType.getHost().set(index, fqdn);
				}
				index++;
			}
			final StringWriter out = new StringWriter();
			ObjectFactory objectFactory = new ObjectFactory();
			// XSD doesn't explicitly declare VPConfig as root element
			JAXBElement<VPConfigType> je = objectFactory
					.createConfig(vpConfigType);
			vidyoProxyConfigMarshaller.marshal(je, new StreamResult(out));
			neConfiguration.setData(out.toString());
			// Always increase the version, so the stand alone proxies would get
			// the changes.
			updateNEConfiguration(neConfiguration, true);
		}
	}*/

	public void addVmIdToConnectVMUri() throws Exception {
		List<NEConfiguration> list = this.getNEConfigurations("%",
				"VidyoManager");
		Iterator<NEConfiguration> it = list.iterator();
		String vmId = null;
		while (it.hasNext()) {
			NEConfiguration nec = (NEConfiguration) it.next();
			vmId = nec.getIdentifier(); // Assumption: Single VM. In case of
										// multiple VM, this line should be
										// changed
		}

		// automatically set VMID to every routers
		list = this.getNEConfigurations("%", "VidyoRouter");
		it = list.iterator();
		int activeRoutersCount = 0;
		while (it.hasNext()) {
			NEConfiguration nec = (NEConfiguration) it.next();
			if(nec.getStatus().equalsIgnoreCase("ACTIVE")) {
				activeRoutersCount++;
			}
			String xml = nec.getData();
			VRConfig vrConfig = new VRConfig(vrMarshaller);
			vrConfig.fromXml(xml);
			vrConfig.addVmIdToConnectVMUri(vmId); // vmId is no longer
													// optional. Set vmId to
													// each VR at this time
			vrConfig.setDocumentVersion(nec.getVersion() + 1);
			xml = vrConfig.toXml();
			nec.setData(xml);
			this.updateNEConfiguration(nec, true);
		}

		if(activeRoutersCount > 1) {
	        //remove buildin VR
			removeBuildInVR();
		}
	}

    /**
     *
     * @return
     */
	@Override
	@SuppressWarnings("unchecked")
	@Cacheable(cacheName = "routerPoolNamesCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public List<RouterPool> getRouterPoolNames() {
		NEConfiguration neConfiguration = dao.getNetworkConfig("ACTIVE", -1);
		StringReader reader = new StringReader(neConfiguration.getData());
		JAXBElement<NetworkConfigType> networkConfigType = (JAXBElement<NetworkConfigType>) cloudMarshaller
				.unmarshal(new StreamSource(reader));
		List<GroupType> groups = networkConfigType.getValue().getGroup();

		List<RouterPool> routerPools = new ArrayList<RouterPool>();

		for (GroupType groupType : groups) {
			RouterPool routerPool = new RouterPool();
			routerPool.setRouterPoolID(groupType.getID());
			routerPool.setRouterPoolName(groupType.getName());
			routerPools.add(routerPool);
		}
		return routerPools;

	}

	/**
	 * Parses the updated NetworkConfig XML and returns the updated list of
	 * RouterPool Ids
	 *
	 * @param configData
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getRouterPoolIds(String configData) {
		StringReader reader = new StringReader(configData);
		JAXBElement<NetworkConfigType> networkConfig = (JAXBElement<NetworkConfigType>) cloudMarshaller
				.unmarshal(new StreamSource(reader));
		NetworkConfigType networkConfigType = networkConfig.getValue();
		List<GroupType> groups = networkConfigType.getGroup();

		List<String> routerPoolIds = new ArrayList<String>();

		for (GroupType groupType : groups) {
			routerPoolIds.add(groupType.getID());
		}

		return routerPoolIds;
	}

	/**
	 * Deletes the Service Entry Records from the Services table based on the
	 * Ids passed
	 *
	 * @param serviceIds
	 * @return
	 */
	public int deleteServices(List<Integer> serviceIds) {
		return dao.deleteServices(serviceIds);
	}

	/**
	 * Returns the TenantIds mapped to the ServiceIds
	 * @param serviceIds
	 * @return
	 */
	public List<Integer> getTenantIdsByServiceIds(List<Integer> serviceIds) {
		return dao.getTenantIdsByServiceIds(serviceIds);
	}

	/**
	 * Deletes the Tenant Service [TenantXservice] Mapping by ServiceIds
	 *
	 * @param serviceIds
	 * @return
	 */
	public int deleteTenantServiceMapping(List<Integer> serviceIds) {
		return dao.deleteTenantServiceMapping(serviceIds);
	}

	/**
	 * Inserts Tenant to Service mapping in to TenantXservice table
	 *
	 * @param tenantServiceMaps
	 */
	public void saveTenantServiceMappings(
			List<TenantServiceMap> tenantServiceMaps) {
		dao.saveTenantServiceMappings(tenantServiceMaps);
	}

	/**
	 * Returns the list of router ids in the Router pool
	 *
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Cacheable(cacheName = "routerPoolIdsCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public List<String> getRouterIdsInPool() {
		NEConfiguration neConfiguration = dao.getNetworkConfig("ACTIVE", -1);
		StringReader reader = new StringReader(neConfiguration.getData());
		JAXBElement<NetworkConfigType> networkConfigType = (JAXBElement<NetworkConfigType>) cloudMarshaller
				.unmarshal(new StreamSource(reader));
		List<GroupType> groups = networkConfigType.getValue().getGroup();
		List<String> routerIds = null;
		for (GroupType groupType : groups) {
			if (groupType.getNetworkElements() != null
					&& !groupType.getNetworkElements().getIdentifier()
							.isEmpty()) {
				if (routerIds == null) {
					routerIds = new ArrayList<String>();
				}
				routerIds
						.addAll(groupType.getNetworkElements().getIdentifier());
			}
		}
		return routerIds;
	}

    public List<IpAddressMap> getVPAddnlAddrMaps() {
        return dao.getVPAddnlAddrMaps();
    }

    public int addVPAddnlAddrMap(IpAddressMap ipAddressMap){
        return dao.addVPAddnlAddrMap(ipAddressMap);
    }
    public int updateVPAddnlAddrMap(IpAddressMap ipAddressMap){
        return dao.updateVPAddnlAddrMap(ipAddressMap);
    }
    public int deleteVPAddnlAddrMap(int ipAddressMapsID){
       return dao.deleteVPAddnlAddrMap(ipAddressMapsID);
    }


}