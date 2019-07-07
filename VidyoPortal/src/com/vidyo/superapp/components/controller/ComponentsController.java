package com.vidyo.superapp.components.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.framework.service.ServiceException;
import com.vidyo.service.INetworkService;
import com.vidyo.service.IServiceService;
import com.vidyo.superapp.components.bo.*;
import com.vidyo.superapp.components.bo.gateway.GatewayPrefix;
import com.vidyo.superapp.components.service.ComponentsService;
import com.vidyo.superapp.components.service.gateway.GatewayCompService;
import com.vidyo.superapp.exceptions.InvalidRequestException;
import com.vidyo.superapp.routerpools.service.RouterPoolsService;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.ws.configuration.NetworkElementType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class ComponentsController {

	private static final Logger logger = LoggerFactory
			.getLogger(ComponentsController.class);


	@Autowired
	private ComponentsService componentsService;
	
	@Autowired
	private GatewayCompService gatewayCompService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IServiceService service;

	@Autowired
	private INetworkService network;

	@Autowired
	private RouterPoolsService routerPoolsService;

	@RequestMapping(value = "getcomponents.ajax", method = RequestMethod.GET)
	public @ResponseBody List<Component> getAllComponents(
			@RequestParam String name, @RequestParam String type,
			@RequestParam String status) throws ServiceException {
		logger.trace("Entering getAllComponents");
		int isAlarm = 0;
		if (type.equalsIgnoreCase("All"))
			type = "";

		if (status.equalsIgnoreCase("All"))
			status = "";

		if (status.equalsIgnoreCase("ALARMs")) {
			status = "";
			isAlarm = 1;
		}

		List<Component> components = componentsService.findAllComponents(name,
				type, status, isAlarm);

		Date systemDate = new Date();

		for (int i = 0; i < components.size(); i++) {
			Date HeartBeatDate = components.get(i).getHeartbeatTime();
			int secondsBetween = (int) ((systemDate.getTime() - HeartBeatDate
					.getTime()) / DateUtils.MILLIS_PER_SECOND);

			if (secondsBetween > 30) {
				components.get(i).setCompStatus("DOWN");
			} else {
				components.get(i).setCompStatus("UP");
			}
		}

		logger.trace("Exiting getAllComponents");

		return components;
	}

	@RequestMapping(value = "getcomponenttypes.ajax", method = RequestMethod.GET)
	public @ResponseBody List<ComponentType> getAllComponentsTypes()
			throws ServiceException {
		logger.trace("Entering getAllComponentsTypes");

		List<ComponentType> componentsType = componentsService
				.findAllComponentsTypes();

		// Exclude the microservice types from the result
		Set<String> microserviceTypes = new HashSet<>();
		microserviceTypes.add(NetworkElementType.VidyoAAMicroservice.getValue());
		microserviceTypes.add(NetworkElementType.VidyoRegistrationMicroservice.getValue());
		microserviceTypes.add(NetworkElementType.VidyoPairingMicroservice.getValue());

		CollectionUtils.filter(componentsType, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return !(microserviceTypes.contains(((ComponentType) object).getName()));
			}
		});

		logger.trace("Exiting getAllComponentsTypes");

		return componentsType;
	}

	@RequestMapping(value = "getvidyomanager.ajax", method = RequestMethod.GET)
	public @ResponseBody List<VidyoManager> getVidyoManager(@RequestParam int id)
			throws ServiceException {
		logger.trace("Entering getVidyoManager");

		List<VidyoManager> list = componentsService.findManagerByCompID(id);

		logger.trace("Exiting getVidyoManager");

		return list;
	}

	@RequestMapping(value = "getvidyorouter.ajax", method = RequestMethod.GET)
	public @ResponseBody List<VidyoRouter> getVidyoRouter(@RequestParam int id)
			throws ServiceException {
		logger.trace("Entering getVidyoRouter");

		List<VidyoRouter> list = componentsService.findRouterByCompID(id);
		
		logger.trace("Exiting getVidyoRouter");

		return list;
	}

	@RequestMapping(value = "deletecomponents.ajax", method = RequestMethod.POST)
	public @ResponseBody List<Integer> deleteSelectedComponents(
			@RequestBody String[] id) throws ServiceException {
		logger.trace("Entering deleteSelectedComponents");

		List<Integer> deleteIds = new ArrayList<Integer>();
		deleteIds.add(Integer.parseInt(id[1]));
		deleteIds = componentsService.deleteComponents(deleteIds, id[2], id[0]);
		
		return deleteIds;
	}

	@RequestMapping(value = "getcomponentbyid.ajax", method = RequestMethod.GET)
	public @ResponseBody Component getComponentByID(@RequestParam int id)
			throws ServiceException {
		logger.trace("Entering getComponentByID");

		Component components = componentsService.findComponentByID(id);

		logger.trace("Exiting getComponentByID");

		return components;
	}

	@RequestMapping(value = "updatevidyomanager.ajax", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateVidyoManager(
			@RequestBody VidyoManager data) {
		logger.trace("Entering updatVidyoManager");
		boolean success = false;
		if (data.getId() != 0) {
			success = componentsService.updateVidyoManager(data);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("message", success ? "Config data has been saved"
				: "Config Data save failed");
		logger.trace("Exiting updatVidyoManager");
		return response;
	}

	@RequestMapping(value = "updatevidyorouter.ajax", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateVidyoRouter(
			@RequestBody VidyoRouter router) {
		logger.trace("Entering updateVidyoRouter");
		boolean success = false;

		if (router.getId() != 0) {
			success = componentsService.updateVidyoRouter(router);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		// flag to indicate if modified cloud config is present or not
		boolean isRouterPoolPresent = routerPoolsService.isRouterPoolPresent();
		
		response.put("success", success);
		response.put("message", success ? (isRouterPoolPresent ? "Config data has been saved.  Network Config changes will be applied after activation in RouterPools page." : "Config data has been saved")
				: "Config Data save failed");
		response.put("isRouterPoolPresent", isRouterPoolPresent);
		
		logger.trace("Exiting updateVidyoRouter");
		return response;
	}

	@RequestMapping(value = "updatecomponent.ajax", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> updateComponent(
			@RequestBody String[] data, BindingResult bindingResult)
			throws JsonParseException, JsonMappingException, IOException,
			ServiceException {
		logger.trace("Entering updateComponent");

		HashMap<String, String> response = new HashMap<String, String>();
		Component updatedComponent = new ObjectMapper().readValue(data[0],
				Component.class);
		
		String userName = null;
		boolean checkUser = false;
		if(updatedComponent.getCompType().getName().equals(NetworkElementType.VidyoGateway.getValue())) {
			VidyoGateway updatedVidyoGateway = new ObjectMapper().readValue(data[1], VidyoGateway.class);
			userName = updatedVidyoGateway.getUserName();
			checkUser = true;
		} else if(updatedComponent.getCompType().getName().equals(NetworkElementType.VidyoReplay.getValue())) {
			VidyoReplay updatedVidyoReplay = new ObjectMapper().readValue(data[1], VidyoReplay.class);
			userName = updatedVidyoReplay.getUserName();
			checkUser = true;
		} else if(updatedComponent.getCompType().getName().equals("VidyoRecorder")) {
			VidyoRecorder updatedVidyoRecorder = new ObjectMapper().readValue(data[1],
					VidyoRecorder.class);
			userName = updatedVidyoRecorder.getUserName();
			checkUser = true;
		} 
    	if (checkUser && userName != null ){
    		if (userName.isEmpty() || userName.length() > 128 || 
    				!userName.matches(ValidationUtils.USERNAME_REGEX)) {   //printabled unicoded username
    			response.put("success", "false");
    			response.put("message", "invaliduser");
    			return response;
     		}
    	
    		// Make the validation for username if the component service and user name existis.
    		// Each service and user name should be unique
    		
    		Integer componentServiceCount = service.getCountServiceByUserName(userName, updatedComponent.getCompType().getName(), updatedComponent.getId());
    		
    		if (componentServiceCount != null && componentServiceCount.intValue() > 0){
    			response.put("success", "false");
    			response.put("message", "duplicateuser");
    			return response;
    		}
            
        }

		if (bindingResult.hasErrors()) {
			throw new InvalidRequestException("Invalid Component",
					bindingResult);
		}

		Component componentObj = null;
		componentObj = componentsService.updateComponentData(updatedComponent, data[1]);
		
		logger.trace("Exiting updateComponent");
		response.put("success", "true");
		return response;
	}

	@RequestMapping(value = "enablecomponent.ajax", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> enableComponent(
			@RequestBody String id) throws NumberFormatException,
			ServiceException {
		logger.trace("Entering enableComponent");

		HashMap<String, String> map = new HashMap<String, String>();
		Date systemDate = new Date();

			Component components = componentsService.findComponentByID(Integer
				.parseInt(id));
			Date HeartBeatDate = components.getHeartbeatTime();
			int secondsBetween = (int) ((HeartBeatDate.getTime() - systemDate
					.getTime()) / DateUtils.MILLIS_PER_SECOND);

			if (secondsBetween < 30)
			map.put(id, "UP");
			else
			map.put(id, "DOWN");

		componentsService.saveComponentStatus(Integer.parseInt(id),
					"ACTIVE");
		componentsService.triggerNetworkConfigXmlUpdate();
		logger.trace("Exiting enableComponent");

		return map;
	}

	@RequestMapping(value = "disablecomponent.ajax", method = RequestMethod.POST)
	public @ResponseBody boolean disableComponent(@RequestBody String id)
			throws NumberFormatException, ServiceException {
		logger.trace("Entering disableComponent");

		boolean updated = false;

		updated =	componentsService.saveComponentStatus(Integer.parseInt(id),
					"INACTIVE");
		componentsService.triggerNetworkConfigXmlUpdate();
		logger.trace("Exiting disableComponent");

		return updated;
	}
	
	@RequestMapping(value = "getvidyogateway.ajax", method = RequestMethod.GET)
	public @ResponseBody VidyoGateway getVidyoGateway(@RequestParam int id)
			throws ServiceException {
		logger.trace("Entering getVidyoGateway of ComponentsController");

		VidyoGateway vidyoGateway = gatewayCompService.getGatewayByCompId(id);
		
		if(vidyoGateway != null && vidyoGateway.getPassword() != null) {
			vidyoGateway.setPassword("PASSWORD_UNCHANGED");
		}

		logger.trace("Exiting getVidyoGateway of ComponentsController");

		return vidyoGateway;
	}
	
	@RequestMapping(value = "getvidyoreplay.ajax", method = RequestMethod.GET)
	public @ResponseBody VidyoReplay getVidyoReplay(@RequestParam int id)
			throws ServiceException {
		logger.trace("Entering getVidyoReplay of ComponentsController");

		VidyoReplay vidyoReplay = componentsService.getReplayByCompId(id);
		
		logger.trace("Exiting getVidyoReplay of ComponentsController");

		return vidyoReplay;
	}
	
	@RequestMapping(value = "getvidyorecorder.ajax", method = RequestMethod.GET)
	public @ResponseBody VidyoRecorder getVidyoRecorder(@RequestParam int id)
			throws ServiceException {
		logger.trace("Entering getVidyoRecorder of ComponentsController");

		VidyoRecorder vidyoRecorder = componentsService.getRecorderByCompId(id);

		logger.trace("Exiting getVidyoRecorder of ComponentsController");

		return vidyoRecorder;
	}

	@RequestMapping(value = "setfactorydefaultforcomponent.ajax", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> setFactoryDefaultConfiguration(@RequestParam String id)
			throws ServiceException, JsonParseException, JsonMappingException, IOException {
		logger.trace("Entering setFactoryDefaultConfiguration of ComponentsController");
		
		HashMap<String, String> map = new HashMap<String, String>();
		/*Component componentObj = new ObjectMapper().readValue(component,
				Component.class);*/

		Component defaultComponent = componentsService.setFactoryDefaultConfiguration(id);
		if(null != defaultComponent) {
			map.put("success", "true");
		}

		logger.trace("Exiting setFactoryDefaultConfiguration of ComponentsController");

		return map;
	}
	
	@RequestMapping(value = "getgatewayprefixes.ajax", method = RequestMethod.GET)
	public @ResponseBody List<GatewayPrefix> getVidyoGatewayPrefixes(@RequestParam int id)
			throws ServiceException {
		logger.trace("Entering getVidyoGatewayPrefixes of ComponentsController");

		List<GatewayPrefix> gatewayPrefixes = gatewayCompService.getGatewayPrefixesById(id);

		logger.trace("Exiting getVidyoGatewayPrefixes of ComponentsController");

		return gatewayPrefixes;
	}
	
	@RequestMapping(value = "getrecorderendpoints.ajax", method = RequestMethod.GET)
	public @ResponseBody List<RecorderEndpoints> getRecorderEndpoints(@RequestParam int id)
			throws ServiceException {
		logger.trace("Entering getRecorderEndpoints of ComponentsController");

		List<RecorderEndpoints> recorderEndpoints = componentsService.getRecorderEndpointsByRecorderId(id);

		logger.trace("Exiting getRecorderEndpoints of ComponentsController");

		return recorderEndpoints;
	}

}