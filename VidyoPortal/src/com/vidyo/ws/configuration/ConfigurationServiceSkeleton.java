package com.vidyo.ws.configuration;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.StringUtils;

import com.vidyo.framework.service.ServiceException;
import com.vidyo.parser.xsd.networkconfig.NetworkConfigType;
import com.vidyo.parser.xsd.vmconfig.DatabaseConfigType;
import com.vidyo.parser.xsd.vmconfig.EMCPListenAddressType;
import com.vidyo.parser.xsd.vmconfig.ObjectFactory;
import com.vidyo.parser.xsd.vmconfig.RMCPListenAddressType;
import com.vidyo.parser.xsd.vmconfig.SOAPListenAddressType;
import com.vidyo.parser.xsd.vmconfig.VMConfigType;
import com.vidyo.parser.xsd.vmconfig.VidyoPortalConnectAddressType;
import com.vidyo.parser.xsd.vmconfig.VpURI;
import com.vidyo.parser.xsd.vr2config.CMCPAddressListType;
import com.vidyo.parser.xsd.vr2config.ConnectVMListType;
import com.vidyo.parser.xsd.vr2config.ListenVMListType;
import com.vidyo.parser.xsd.vr2config.MediaAddressMapType;
import com.vidyo.parser.xsd.vr2config.MediaPortRangeType;
import com.vidyo.parser.xsd.vr2config.MediaStreamPrecedenceType;
import com.vidyo.parser.xsd.vr2config.VMAccessType;
import com.vidyo.parser.xsd.vr2config.VRConfigType;
import com.vidyo.parser.xsd.vr2config.VrURI;
import com.vidyo.service.ISecurityService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.system.SystemService;
import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.bo.ComponentType;
import com.vidyo.superapp.components.bo.RouterMediaAddrMap;
import com.vidyo.superapp.components.bo.VidyoGateway;
import com.vidyo.superapp.components.bo.VidyoManager;
import com.vidyo.superapp.components.bo.VidyoRecorder;
import com.vidyo.superapp.components.bo.VidyoReplay;
import com.vidyo.superapp.components.bo.VidyoRouter;
import com.vidyo.superapp.components.service.ComponentsService;

/**
 * ConfigurationServiceSkeleton java skeleton for the axisService
 */
public class ConfigurationServiceSkeleton implements
		ConfigurationServiceSkeletonInterface {

	protected final Logger logger = LoggerFactory
			.getLogger(ConfigurationServiceSkeleton.class.getName());

	private IServiceService service;
	@Autowired
	private ComponentsService componentsService;
	
	private SystemService systemService;
	
	private ISecurityService securityService;

	private Jaxb2Marshaller vmMarshaller;
	private Jaxb2Marshaller vrMarshaller;
	private Jaxb2Marshaller cloudConfigMarshaller;

	public void setVmMarshaller(Jaxb2Marshaller marshaller) {
		this.vmMarshaller = marshaller;
	}

	public void setVrMarshaller(Jaxb2Marshaller marshaller) {
		this.vrMarshaller = marshaller;
	}

	public void setComponentsService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}

	public void setService(IServiceService service) {
		this.service = service;
	}

	public void setCloudConfigMarshaller(Jaxb2Marshaller cloudConfigMarshaller) {
		this.cloudConfigMarshaller = cloudConfigMarshaller;
	}

	/**
	 * Auto generated method signature register a new/existing network element
	 * 
	 * @param req
	 *            :
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws NotEnabledFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws ResourceNotAvailableFaultException
	 *             :
	 * @throws NotConfiguredFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 */
	public com.vidyo.ws.configuration.RegisterResponse register(
			com.vidyo.ws.configuration.RegisterRequest req)
			throws NotLicensedFaultException, NotEnabledFaultException,
			InvalidArgumentFaultException, ResourceNotAvailableFaultException,
			NotConfiguredFaultException, GeneralFaultException {
		String identifier = req.getIdentifier();
		String displayName = req.getName();
		NetworkElementType componentType = req.getNetworkElementType();
		String ipAddress = req.getNonLoopbackIPAddresses().getIPAddress()[0];
		ConfigVersion[] runningVersion = req.getCurrentConfigVersion();
		String[] alarm = req.getAlarm();

		// logging
		if (logger.isDebugEnabled()) {
			StringBuffer logInfo = new StringBuffer();
			logInfo.append("SOAP operation register() with \n identifier=")
					.append(identifier).append("\n");
			logInfo.append(" type=").append(componentType.getValue())
					.append("\n");
			logInfo.append(" reset displayName=").append(displayName)
					.append("\n");
			logInfo.append(" ip=").append(ipAddress).append("\n");
			if (runningVersion != null) {
				for (ConfigVersion aRunningVersion : runningVersion) {
					logInfo.append(" runningVersion=")
							.append(aRunningVersion.getConfigType())
							.append(":").append(aRunningVersion.getVersion())
							.append("\n");
				}
			}
			if (alarm != null) {
				for (String anAlarm : alarm) {
					logInfo.append(" alarm=").append(anAlarm).append("\n");
				}
			}
			logger.debug(logInfo.toString());
		}

		String version = req.getSoftwareVersion();
		int part1 = 0;
		int part2 = 0;
		int part3 = 0;
		if (version != null) {
			if (version.contains(".")) {
				String[] versionParts = version.split("\\.");
				if (versionParts.length > 0) {
					try {
						part1 = Integer.parseInt(versionParts[0]);
					} catch (NumberFormatException nfe) {
						logger.info("Could not parse version major: " + version);
					}
				}
				if (versionParts.length > 1) {
					try {
						part2 = Integer.parseInt(versionParts[1]);
					} catch (NumberFormatException nfe) {
						logger.info("Could not parse version minor: " + version);
					}
				}
				if (versionParts.length > 2) {
					try {
						String part3Str = versionParts[2];
						if (part3Str.contains("(")) {
							part3Str = part3Str.subSequence(0, part3Str.indexOf("(")).toString();
						}
						part3 = Integer.parseInt(part3Str);
					} catch (NumberFormatException nfe) {
						logger.info("Could not parse version maintenance/build: " + version);
					}
				}

			}
			if (componentType == NetworkElementType.VidyoGateway) {
				if (version.endsWith("L")) { // LyncGateway
					if (part1 >= 3) {
						// we're good
					} else {
						throw new GeneralFaultException("Unsupported version, require v3.0+");
					}
				} else {
					if (part1 > 3) {
						// we're good
					} else if (part1 == 3) {
						if (part2 >= 3) {
							// we're good
						} else {
							throw new GeneralFaultException("Unsupported version, require v3.3.0+");
						}
					} else {
						throw new GeneralFaultException("Unsupported version, require v3.3.0+");
					}
				}
			} else if (componentType == NetworkElementType.VidyoReplayRecorder ||
					componentType == NetworkElementType.VidyoReplay) {
				if (part1 > 3) {
					// we're good
				} else if (part1 == 3) {
					if (part2 > 0) {
						// we're good
					} else if (part2 == 0) {
						if (part3 >= 1) {
							// we're good
						} else {
							throw new GeneralFaultException("Unsupported version, require v3.0.1+");
						}
					}
				} else {
					throw new GeneralFaultException("Unsupported version, require v3.0.1+");
				}
			}
		}

	// Mysql supported range is '1000-01-01 00:00:00' to '9999-12-31
		// 23:59:59'
		// If system is just upgrade, year of last heartbeat was set to '1999',
		// we need to
		// replace network component with new SystemID if necessary: ip matched
		// AND type matched
		// synchronized (this) {
		try {
			renewNetworkElementConfigurationSystmID(identifier,
					componentType.getValue(), ipAddress, "1999");
		} catch (Exception ignored) {
		}
		// }

		// Business logic
		if (componentType == NetworkElementType.VidyoManager) {
			RegisterResponse vmResp = null;
			RegisterResponse ncResp = null;
			String systemId = null;
			try {
				systemId = FileUtils.readFileToString(new File(
						"/opt/vidyo/VM_SYSTEM_ID"));
				systemId = systemId.replaceAll("[\\n\\r]", "");
			} catch (IOException e) {
				logger.error("cannot read systemid file /opt/vidyo/VM_SYSTEM_ID");
			}
			if (systemId != null && !identifier.equalsIgnoreCase(systemId)) {
				logger.error("Invalid VidyoManager SystemId: " + identifier
						+ " Expected -" + systemId);
				throw new InvalidArgumentFaultException(
						"Invalid VidyoManager SystemId: " + identifier
								+ " Expected -" + systemId);
			}
			if (runningVersion != null) {
				for (int i = 0; i < runningVersion.length; i++) {
					if (runningVersion[i].getConfigType() == ConfigType.VidyoManagerConfig) {
						vmResp = registerNEConfig(req, i,
								NetworkElementType.VidyoManager);
					} else if (runningVersion[i].getConfigType() == ConfigType.NetworkConfig) {
						ncResp = registerNetworkConfig(req, i);
					}
				}
			}

			if (vmResp != null && ncResp != null
					&& (vmResp.getStatus() == Status_type0.OK)) {
				if (ncResp.getConfiguration() != null
						&& ncResp.getConfiguration().length > 0) {
					vmResp.addConfiguration(ncResp.getConfiguration()[0]);
				}
				return vmResp;
			} else if (vmResp != null) {
				return vmResp;
			} else if (ncResp != null) {
				return ncResp;
			} else {
				logger.error("Invalid Config Type Error");
				throw new InvalidArgumentFaultException("Invalid ConfigType ");
			}
		} else if (componentType == NetworkElementType.VidyoRouter) {
			return registerNEConfig(req, 0, NetworkElementType.VidyoRouter);
		} else if (componentType == NetworkElementType.VidyoGateway) {
			return registerNEConfig(req, 0, NetworkElementType.VidyoGateway);
		} else if (componentType == NetworkElementType.VidyoReplayRecorder) {
			return registerNEConfig(req, 0,
					NetworkElementType.VidyoReplayRecorder);
		} else if (componentType == NetworkElementType.VidyoReplay) {
			return registerNEConfig(req, 0, NetworkElementType.VidyoReplay);
		} else if (componentType == NetworkElementType.VidyoAAMicroservice) {
			return registerNEConfig(req, 0, NetworkElementType.VidyoAAMicroservice);
		} else if (componentType == NetworkElementType.VidyoRegistrationMicroservice) {
			return registerNEConfig(req, 0, NetworkElementType.VidyoRegistrationMicroservice);
		} else if (componentType == NetworkElementType.VidyoPairingMicroservice) {
			return registerNEConfig(req, 0, NetworkElementType.VidyoPairingMicroservice);
		} else {
			throw new InvalidArgumentFaultException(
					"Invalid NetworkElementType: " + componentType.getValue());
		}
	}

	private RegisterResponse registerNEConfig(RegisterRequest req,
			int configIndex, NetworkElementType networkElementType)
			throws ResourceNotAvailableFaultException {
		String identifier = req.getIdentifier();
		NetworkElementType componentType = req.getNetworkElementType();
		String ipAddress = req.getNonLoopbackIPAddresses().getIPAddress()[0];
		String webApplicationURL = req.getWebApplicationURL();
		ConfigVersion[] runningVersion = req.getCurrentConfigVersion();
		String[] alarm = req.getAlarm();
		String swVer = req.getSoftwareVersion();

		RegisterResponse resp = new RegisterResponse();
		resp.setStatus(Status_type0.OK);
		Configuration config = new Configuration();
		ConfigVersion version = new ConfigVersion();

		if (runningVersion != null && runningVersion[configIndex] != null) {
			version.setConfigType(runningVersion[configIndex].getConfigType());
		}
		String type = componentType.getValue();
		// find component by id and type
		Component existingComp = null;
		try {
			if(type.equalsIgnoreCase("VidyoReplayRecorder")) {
				type = "VidyoRecorder";
			}
			existingComp = componentsService.findByCompIDAndCompType(
					identifier, type);
			// If replay and the look up fails by Id try ipAddress
			if(type.equalsIgnoreCase("VidyoReplay") && existingComp  == null) {
				VidyoReplay vidyoReplay = componentsService.getReplayByIP(ipAddress);
				if(vidyoReplay != null) {
					existingComp = vidyoReplay.getComponents();
				}
			}
			
		} catch (ServiceException e) {
			logger.error("Error while retrieving component id " + identifier
					+ " type " + componentType.getValue());
		}
				

		if (existingComp == null) {
			logger.debug("no component with in DB ID " + identifier + " Type -"
					+ componentType.getValue());
			logger.debug("new component will be inserted in to the DB");
			// new table insert code
			Component newComponent = new Component();
			newComponent.setCreated(Calendar.getInstance().getTime());
			newComponent.setHeartbeatTime(Calendar.getInstance().getTime());
			newComponent.setCompID(identifier);
			newComponent.setMgmtUrl(webApplicationURL);
			// Status is always "NEW" during first insert
			newComponent.setStatus("NEW");
			newComponent.setLocalIP(ipAddress);
			newComponent.setClusterIP(ipAddress);
			// Set running version as zero for insert
			if (runningVersion != null && runningVersion[configIndex] != null) {
				newComponent.setRunningVersion(0);
			} else {
				newComponent.setRunningVersion(-1);
			}
			// This sometimes has newline or carriage returns - strip them out
			if (alarm != null && alarm.length > 0) {
				String alarmStr = "";
				for (String anAlarm : alarm) {
					if (anAlarm != null) {
						anAlarm = anAlarm.trim().replaceAll("\\n", "");
						anAlarm = anAlarm.replaceAll("\\r", "");
						if (!anAlarm.isEmpty()) {
							alarmStr += (anAlarm + "\n");
						}
					}
				}
				newComponent.setAlarm(alarmStr);
			} else {
				newComponent.setAlarm("");
			}
			// Set config version as zero for insert
			newComponent.setConfigVersion(0);
			newComponent.setCompSoftwareVersion(swVer);
			newComponent.setName("");

			List<ComponentType> compTypeList = null;
			try {
				compTypeList = componentsService
						.findByComponentTypeByName(type);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
			if (compTypeList == null || compTypeList.get(0) == null) {
				logger.error("no parent comp type found - abort registration");
				throw new RuntimeException("Invalid ConfigType");
			}
			logger.debug("Get Component Type by Type Value"
					+ type);
			newComponent.setCompType(compTypeList.get(0));
			logger.debug("Retrieved component type object "
					+ compTypeList.get(0).getName());

			VidyoManager savedVidyoManager = null;
			VidyoRouter savedVidyoRouter = null;
			Component savedComponent = null;
			if (componentType.equals(NetworkElementType.VidyoManager)) {
				logger.debug("Component is of type VidyoManager");
				VidyoManager vidyoManager = new VidyoManager();
				// hard coded values for insert
				vidyoManager.setRmcpport(17991);
				vidyoManager.setSoapport(17995);
				vidyoManager.setEmcpport(17992);
				vidyoManager.setFqdn("");
				vidyoManager.setDscpvalue(0);

				try {
					savedComponent = componentsService
							.saveComponent(newComponent);
					vidyoManager.setComponents(savedComponent);
					savedVidyoManager = componentsService
							.saveVidyoManager(vidyoManager);
				} catch (Exception e) {
					logger.error("Error while saving component/vidyomanager "
							+ e.getMessage());
					throw new RuntimeException(
							"Error while saving component/vidyomanager");
				}
				logger.debug("saved component id ->" + savedComponent.getId());
				logger.debug("saved manager id ->" + savedVidyoManager.getId());
				String vmConfigXml = generateVidyoManagerConfigData(
						savedComponent, savedVidyoManager);
				config.setConfigData(new DataHandler(vmConfigXml,
						"text/xml; charset=UTF-8"));

			} else if (componentType.equals(NetworkElementType.VidyoRouter)) {
				logger.debug("Component is of type VidyoRouter");
				// Check if the VM has registered
				List<Component> vms = componentsService
						.getComponentsByType(NetworkElementType.VidyoManager
								.getValue());
				if (vms.isEmpty()) {
					resp.setStatus(Status_type0.NOT_CONFIGURED);
					// Wait till VM comes up
					return resp;
				}

				// Embedded router check
				List<Component> vrs = componentsService
						.getComponentsByType(NetworkElementType.VidyoRouter
								.getValue());
				for (Component vm : vms) {
					if (vm.getLocalIP().equals(ipAddress)) {
						if (vrs.size() >= 1) {
							// If its a local router and external routers are registered, block registration
							resp.setStatus(Status_type0.NOT_CONFIGURED);
							return resp;
						}
					}
				}

				VidyoRouter vidyoRouter = new VidyoRouter();
				vidyoRouter.setScipPort(17990);
				vidyoRouter.setScipFqdn("0.0.0.0");
				vidyoRouter.setMediaPortStart(50000);
				vidyoRouter.setMediaPortEnd(65535);
				vidyoRouter.setProxyEnabled(1);
				com.vidyo.bo.Configuration configuration = systemService.getConfiguration("TLS_PROXY_ENABLED");
				if(configuration != null && configuration.getConfigurationValue() != null && configuration.getConfigurationValue().equalsIgnoreCase("1")) {
					vidyoRouter.setProxyUseTls(1);	
				}				

				try {
					savedComponent = componentsService
							.saveComponent(newComponent);
					vidyoRouter.setComponents(savedComponent);
					savedVidyoRouter = componentsService
							.saveVidyoRouter(vidyoRouter);
				} catch (Exception e) {
					logger.error("Error while saving component/vidyorouter "
							+ e.getMessage());
					throw new RuntimeException(
							"Error while saving component/vidyorouter");
				}
				logger.debug("saved component id ->" + savedComponent.getId());
				logger.debug("saved router id ->" + savedVidyoRouter.getId());
				String routerConfigXml = generateVidyoRouterConfigData(savedComponent);
				config.setConfigData(new DataHandler(routerConfigXml,
						"text/xml; charset=UTF-8"));
			} else if (componentType.equals(NetworkElementType.VidyoGateway)
					|| componentType.equals(NetworkElementType.VidyoReplay)
					|| componentType
							.equals(NetworkElementType.VidyoReplayRecorder)) {
				try {
					savedComponent = componentsService
							.saveComponent(newComponent);
					logger.debug("saved component id ->"
							+ savedComponent.getId());
					if (componentType.equals(NetworkElementType.VidyoGateway)) {
						VidyoGateway vidyoGateway = new VidyoGateway();
						vidyoGateway.setComponents(savedComponent);
						VidyoGateway savedVidyoGateway = componentsService
								.addVidyoGateway(vidyoGateway);
						logger.debug("saved gateway id ->"
								+ savedVidyoGateway.getId());
					} else if (componentType
							.equals(NetworkElementType.VidyoReplay)) {
						VidyoReplay vidyoReplay = new VidyoReplay();
						vidyoReplay.setComponents(savedComponent);
						VidyoReplay savedVidyoReplay = componentsService
								.addVidyoReplay(vidyoReplay);
						logger.debug("saved replay id ->"
								+ savedVidyoReplay.getId());
					} else if (componentType
							.equals(NetworkElementType.VidyoReplayRecorder)) {
						VidyoRecorder vidyoRecorder = new VidyoRecorder();
						vidyoRecorder.setComponents(savedComponent);
						VidyoRecorder savedVidyoRecorder = componentsService
								.addVidyoRecorder(vidyoRecorder);
						logger.debug("saved recorder id ->"
								+ savedVidyoRecorder.getId());
					}

				} catch (Exception e) {
					logger.error("Error while saving component "
							+ e.getMessage());
					throw new RuntimeException("Error while saving component");
				}
				logger.debug("saved component id ->" + savedComponent.getId());
				String configXml = null;
				if (componentType.equals(NetworkElementType.VidyoGateway)) {
					configXml = "<VGConfig/>";
				} else
					configXml = "";
				config.setConfigData(new DataHandler(configXml,
						"text/xml; charset=UTF-8"));
			} else if (componentType.equals(NetworkElementType.VidyoAAMicroservice)
					|| componentType.equals(NetworkElementType.VidyoRegistrationMicroservice)
					|| componentType.equals(NetworkElementType.VidyoPairingMicroservice)) {
				try {
					newComponent.setName(req.getName());
					savedComponent = componentsService.saveComponent(newComponent);
					logger.debug("saved component id ->" + savedComponent.getId());
				} catch (Exception e) {
					logger.error("Error while saving component " + e.getMessage());
					throw new RuntimeException("Error while saving component");
				}
				logger.debug("saved component id ->" + savedComponent.getId());
				String configXml = null;
				configXml = "";
				config.setConfigData(new DataHandler(configXml, "text/xml; charset=UTF-8"));
			}
			version.setVersion("0");
			resp.setRegistrationExpirySeconds(new UnsignedInt(30));

		} else {
			// new table update code
			existingComp.setCompID(identifier);
			existingComp.setLocalIP(ipAddress);
			existingComp.setClusterIP(ipAddress);
			Date now = new Date();
			existingComp.setHeartbeatTime(now);

			if (runningVersion != null && runningVersion[configIndex] != null) {
				existingComp.setRunningVersion(Integer
						.parseInt(runningVersion[configIndex].getVersion()));
			} else {
				existingComp.setRunningVersion(-1);
			}

			if (alarm != null && alarm.length > 0) {
				String alarmStr = "";
				for (String anAlarm : alarm) {
					anAlarm = anAlarm.trim().replaceAll("\\n", "");
					anAlarm = anAlarm.replaceAll("\\r", "");
					if (!anAlarm.isEmpty()) {
						alarmStr += (anAlarm + "\n");
					}
				}
				existingComp.setAlarm(alarmStr);
			} else {
				existingComp.setAlarm("");
			}

			existingComp.setMgmtUrl(webApplicationURL);
			existingComp.setCompSoftwareVersion(swVer);

			List<ComponentType> compTypeList = new ArrayList<ComponentType>();
			try {
				compTypeList = componentsService
						.findByComponentTypeByName(type);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
			existingComp.setCompType(compTypeList.get(0));

			Component savedComponent = null;
			try {
				savedComponent = componentsService.saveComponent(existingComp);
			} catch (Exception e) {
				logger.error("Error while updating component " + e.getMessage());
				throw new RuntimeException("Error while updating component");
			}
			logger.debug("updated comp id ->" + savedComponent.getId());
			if (componentType.equals(NetworkElementType.VidyoRouter)
					&& savedComponent.getStatus().equals("ACTIVE")) {
				String routerConfigXml = generateVidyoRouterConfigData(savedComponent);
				config.setConfigData(new DataHandler(routerConfigXml,
						"text/xml; charset=UTF-8"));
			} else if (componentType.equals(NetworkElementType.VidyoManager)
					&& savedComponent.getStatus().equals("ACTIVE")) {
				VidyoManager vidyoManager = componentsService
						.findManagerByCompID(savedComponent.getId()).get(0);
				String vmXml = generateVidyoManagerConfigData(savedComponent,
						vidyoManager);
				config.setConfigData(new DataHandler(vmXml,
						"text/xml; charset=UTF-8"));
			}
			version.setVersion("" + savedComponent.getConfigVersion());

			if (savedComponent.getStatus().equals("NEW")) {
				resp.setStatus(Status_type0.NOT_CONFIGURED);
			} else if (savedComponent.getStatus().equals("INACTIVE")) {
				resp.setStatus(Status_type0.NOT_ENABLED);
			}
		}
		config.setConfigVersion(version);

		logger.debug("Response Status " + resp.getStatus());
		logger.debug("Response Version " + version.getVersion());
		try {
			logger.debug("Response XML " + (config.getConfigData() != null ? config.getConfigData().getContent() : config.getConfigData()));
		} catch (Exception e) {
			logger.error("error while logging config xml", e.getMessage());
		}

		if (resp.getStatus() == Status_type0.OK) {
			// if version has no change, no need to set config
			if ((runningVersion != null && runningVersion[configIndex] != null)) {
				if (networkElementType == NetworkElementType.VidyoRouter) {
					resp.addConfiguration(config);
				} else {
					if ((!version.getVersion().equals(
							runningVersion[configIndex].getVersion()))) {
						resp.addConfiguration(config);
					}
				}

			}
		}

		return resp;
	}

	private RegisterResponse registerNetworkConfig(RegisterRequest req,
			int configIndex) throws ResourceNotAvailableFaultException {
		ConfigVersion[] runningVersion = req.getCurrentConfigVersion();
		RegisterResponse resp = new RegisterResponse();
		resp.setStatus(Status_type0.OK);
		Configuration config = new Configuration();
		ConfigVersion version = new ConfigVersion();
		version.setConfigType(runningVersion[configIndex].getConfigType());
		
		NetworkConfigType networkConfigType = componentsService.getActiveNetworkConfig();
		
		final StringWriter out = new StringWriter();
		com.vidyo.parser.xsd.networkconfig.ObjectFactory objectFactory = new com.vidyo.parser.xsd.networkconfig.ObjectFactory();
		JAXBElement<NetworkConfigType> je = objectFactory
				.createNetworkConfig(networkConfigType);
		cloudConfigMarshaller.marshal(je, new StreamResult(out));

		String cloudConfigXml = out.toString();

		logger.debug("new cloud config xml" + cloudConfigXml);

		version.setVersion("" + networkConfigType.getDocumentVersion());

		config.setConfigData(new DataHandler(cloudConfigXml,
				"text/xml; charset=UTF-8"));

		config.setConfigVersion(version);

		// if version has no change, no need to set config
		if (!version.getVersion().equals(
				runningVersion[configIndex].getVersion())) {
			resp.addConfiguration(config);
		}

		return resp;
	}

	/**
	 *
	 * @param newID
	 *            new systemID
	 * @param type
	 *            VidyoManager/VidyoRouter/VidyoProxy/VidyoGateway/
	 *            VidyoReplayRecorder/VidyoReplay
	 * @param ip
	 *            ip address
	 * @param triggerYear
	 *            "1999".
	 */
	private void renewNetworkElementConfigurationSystmID(String newID,
			String type, String ip, String triggerYear) {
		service.replaceSystmID(newID, type, ip, triggerYear);
	}

	private String generateVidyoRouterConfigData(Component savedComponent) {
		VRConfigType vrConfigType = new VRConfigType();
		VidyoRouter vidyoRouter = componentsService.findRouterByCompID(
				savedComponent.getId()).get(0);

		vrConfigType.setDocumentVersion(String.valueOf(savedComponent.getConfigVersion()).trim());

		vrConfigType.setRouterName(!StringUtils.isEmpty(savedComponent
				.getName()) ? savedComponent.getName() : "DEFAULT_NAME");

		CMCPAddressListType cmcpList = new CMCPAddressListType();
		cmcpList.getCMCPListenAddress().add(
				vidyoRouter.getScipFqdn() + ":" + vidyoRouter.getScipPort());
		vrConfigType.setCMCPAddressList(cmcpList);

		MediaPortRangeType range = new MediaPortRangeType();
		range.setStart(vidyoRouter.getMediaPortStart());
		range.setEnd(vidyoRouter.getMediaPortEnd());
		vrConfigType.setMediaPortRange(range);

		if (!StringUtils.isEmpty(vidyoRouter.getStunFqdn())) {
			vrConfigType.setStunServerAddress(vidyoRouter.getStunFqdn() + ":"
					+ vidyoRouter.getStunPort());
		}

		MediaStreamPrecedenceType audioDscp = new MediaStreamPrecedenceType();
		audioDscp.setLowPrioDSCPBits(vidyoRouter.getAudioDscp());
		audioDscp.setMedPrioDSCPBits(vidyoRouter.getAudioDscp());
		audioDscp.setHighPrioDSCPBits(vidyoRouter.getAudioDscp());
		vrConfigType.setAudioPrecedence(audioDscp);

		MediaStreamPrecedenceType vidyoDscp = new MediaStreamPrecedenceType();
		vidyoDscp.setLowPrioDSCPBits(vidyoRouter.getDscpVidyo());
		vidyoDscp.setMedPrioDSCPBits(vidyoRouter.getDscpVidyo());
		vidyoDscp.setHighPrioDSCPBits(vidyoRouter.getDscpVidyo());
		vrConfigType.setVideoPrecedence(vidyoDscp);

		MediaStreamPrecedenceType contentDscp = new MediaStreamPrecedenceType();
		contentDscp.setLowPrioDSCPBits(vidyoRouter.getContentDscp());
		contentDscp.setMedPrioDSCPBits(vidyoRouter.getContentDscp());
		contentDscp.setHighPrioDSCPBits(vidyoRouter.getContentDscp());
		vrConfigType.setAppPrecedence(contentDscp);
		
		vrConfigType.setSignalingPrecedence(Long.valueOf(vidyoRouter.getSingnalingDscp()));

		List<RouterMediaAddrMap> mediaAddressMap = vidyoRouter.getRouterMediaAddrMap();
		for (RouterMediaAddrMap routerMediaAddress : mediaAddressMap) {
			MediaAddressMapType map = new MediaAddressMapType();
			map.setLocalAddress(routerMediaAddress.getLocalIP());
			map.setRemoteAddress(routerMediaAddress.getRemoteIP());
			vrConfigType.getMediaAddressMap().add(map);
		}

		// setting default values as these are mandatory elements in xsd
		vrConfigType.setNumberOfThreads(0);

		vrConfigType.setStat("");

		ConnectVMListType connectVMList = new ConnectVMListType();
		List<Component> comps = componentsService
				.getComponentsByType("VidyoManager");

		boolean localVr = false;
		// There should be ideally one Component for VM
		for (Component comp : comps) {
			// There should be only one VM
			List<VidyoManager> vms = componentsService.findManagerByCompID(comp.getId());
			for(VidyoManager vm : vms) {
				VrURI vrURI = new VrURI();
				vrURI.setUri(comp.getCompID() + "@"
						+ vm.getFqdn() + ":" + vm.getRmcpport());
				connectVMList.getConnectVM().add(vrURI);
				if(comp.getLocalIP().equalsIgnoreCase(vidyoRouter.getComponents().getLocalIP())) {
					localVr = true;
				}
			}
		}
		vrConfigType.setConnectVMList(connectVMList);
		vrConfigType.setListenVMList(new ListenVMListType());
		vrConfigType.setVidyoManagerAccess(VMAccessType.CONNECT);
		
		// set encryption
		com.vidyo.bo.Configuration systemConfig = systemService.getConfiguration("COMPONENTS_ENCRYPTION");
		if(systemConfig != null && systemConfig.getConfigurationValue() != null) {
			vrConfigType.setSecurityEnabled(Boolean.valueOf(systemConfig.getConfigurationValue()));	
		} else {
			vrConfigType.setSecurityEnabled(false);
		}
		
		boolean isSSLEnabled = securityService.isSSLEnabled();
		// If the router is local and ssl is enabled, then disable proxy setting
		if(localVr && isSSLEnabled) {
			vrConfigType.setProxyEnable(false);
			vrConfigType.setProxyUseTls(false);
		} else {
			vrConfigType.setProxyEnable(vidyoRouter.getProxyEnabled() == 1);	
			vrConfigType.setProxyUseTls(vidyoRouter.getProxyUseTls() == 1);
		}
		
		final StringWriter out = new StringWriter();
		com.vidyo.parser.xsd.vr2config.ObjectFactory objectFactory = new com.vidyo.parser.xsd.vr2config.ObjectFactory();
		// XSD doesn't explicitly declare VRConfig as root element
		JAXBElement<VRConfigType> je = objectFactory.createConfig(vrConfigType);
		vrMarshaller.marshal(je, new StreamResult(out));

		return out.toString();
	}

	private String generateVidyoManagerConfigData(Component savedComponent,
			VidyoManager savedVidyoManager) {
		VMConfigType vmConfig = new VMConfigType();

		SOAPListenAddressType soapList = new SOAPListenAddressType();
		soapList.getSOAPListenAddress()
				.add(StringUtils.isEmpty(savedVidyoManager.getFqdn()) ? savedComponent
						.getLocalIP() : savedVidyoManager.getFqdn() + ":"
						+ savedVidyoManager.getSoapport());
		vmConfig.setSOAPAddressList(soapList);

		vmConfig.setDocumentVersion(new Integer(savedComponent
				.getConfigVersion()).toString());

		EMCPListenAddressType emcpList = new EMCPListenAddressType();
		emcpList.getEMCPListenAddress()
				.add(StringUtils.isEmpty(savedVidyoManager.getFqdn()) ? savedComponent
						.getLocalIP() : savedVidyoManager.getFqdn() + ":"
						+ savedVidyoManager.getEmcpport());
		vmConfig.setEMCPAddressList(emcpList);

		RMCPListenAddressType rmcpList = new RMCPListenAddressType();
		rmcpList.getRMCPListenAddress()
				.add(StringUtils.isEmpty(savedVidyoManager.getFqdn()) ? savedComponent
						.getLocalIP() : savedVidyoManager.getFqdn() + ":"
						+ savedVidyoManager.getRmcpport());
		vmConfig.setRMCPAddressList(rmcpList);

		vmConfig.setSignalingPrecedence((long) savedVidyoManager.getDscpvalue());

		// setting default values as these are mandatory elements in xsd
		VidyoPortalConnectAddressType vidyoPortalAddressList = new VidyoPortalConnectAddressType();
		VpURI vpURI = new VpURI();
		boolean forcedSSL = securityService.isForcedHTTPSEnabled();
		String requestScheme = "http://";
		if(forcedSSL) {
			requestScheme = "https://";
		}
		vpURI.setUri(requestScheme
				+ (StringUtils.isEmpty(savedVidyoManager.getFqdn()) ? savedComponent
						.getClusterIP() : savedVidyoManager.getFqdn()));
		vidyoPortalAddressList.getVidyoPortalConnectAddress().add(vpURI);
		vmConfig.setVidyoPortalAddressList(vidyoPortalAddressList);
		vmConfig.setStat("");
		vmConfig.setNumberOfThreads(0);

		DatabaseConfigType databaseConfig = new DatabaseConfigType();
		databaseConfig.setDBAddress("");
		databaseConfig.setOdbcDSN("");
		databaseConfig.setOdbcPwd(new byte[] {});
		databaseConfig.setOdbcType("");
		databaseConfig.setOdbcUID("");
		vmConfig.setDatabaseConfig(databaseConfig);
		
		// set encryption
		com.vidyo.bo.Configuration systemConfig = systemService.getConfiguration("COMPONENTS_ENCRYPTION");
		if(systemConfig != null && systemConfig.getConfigurationValue() != null) {
			vmConfig.setSecurityEnabled(Boolean.valueOf(systemConfig.getConfigurationValue()));	
		} else {
			vmConfig.setSecurityEnabled(false);
		}		

		final StringWriter out = new StringWriter();
		ObjectFactory objectFactory = new ObjectFactory();
		// XSD doesn't explicitly declare VMConfig as root element
		JAXBElement<VMConfigType> je = objectFactory.createConfig(vmConfig);
		vmMarshaller.marshal(je, new StreamResult(out));

		return out.toString();
	}

	/**
	 * @param systemService the systemService to set
	 */
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * @param securityService the securityService to set
	 */
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}
}
