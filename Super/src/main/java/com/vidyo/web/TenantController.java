package com.vidyo.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.vidyo.framework.util.ValidatorUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Location;
import com.vidyo.bo.Member;
import com.vidyo.bo.Router;
import com.vidyo.bo.Service;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.TenantFilter;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.replay.update.ReplayUpdateParamServiceStub;
import com.vidyo.replay.update.UpdateStatusResponse;
import com.vidyo.replay.update.UpdateTenantRequest;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.RoomServiceImpl;
import com.vidyo.service.tenant.TenantUpdateResponse;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.room.RoomUtils;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class TenantController  {

	protected static final Logger logger = LoggerFactory.getLogger(TenantController.class.getName());

	private static final String MAX_CREATE_PUBLIC_ROOM = "MAX_CREATE_PUBLIC_ROOM";
	
	private static final String PASSWORD_UNCHANGED = "PASSWORD_UNCHANGED";

	@Autowired
    private ITenantService service;

	@Autowired
    private IUserService user;

    @Autowired
    private ISystemService system;

    @Autowired
    private CookieLocaleResolver lr;

    @Value("${superShowExecutives}")
    private boolean showExecutives = true;

    @Value("${superShowPanoramas}")
    private boolean showPanoramas = true;

    @Value("${vidyoreplay}")
    private boolean showVidyoReplay = true;

    @Value("${vidyovoice}")
    private boolean showVidyoVoice = true;

    @Autowired
    private ReloadableResourceBundleMessageSource ms;

    @Autowired
    private IRoomService roomService;


    @RequestMapping(value = "/tenants.html", method = GET)
	public ModelAndView getTenantsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "tenants");
		this.user.setLoginUser(model, response);
		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);
		return new ModelAndView("super/tenants_html", "model", model);
    }
    @RequestMapping(value = "/tenants.ajax", method = GET)
    public ModelAndView getTenantsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        TenantFilter filter = new TenantFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        // JSON format for filtering params - tenantname and tenanturl
        final String jsonFilter = filter.getFilter();
        if(jsonFilter != null) {
            try {
				final ObjectNode[] nodes = new ObjectMapper().readValue(jsonFilter, ObjectNode[].class);
				for(ObjectNode node : nodes) {
					if(node.get("property").asText().equals("tenantName") && !StringUtils.isEmpty(node.get("value").asText())) {
						filter.setTenantName(node.get("value").asText());
					}
					if(node.get("property").asText().equals("tenantURL") && !StringUtils.isEmpty(node.get("value").asText())) {
						filter.setTenantURL(node.get("value").asText());
					}
				}
			} catch (Exception e) {
				logger.info("Not a valid JSON format");
			}
        }

        final String jsonSort = filter.getSortProperty();
        if(jsonSort != null) {
            try {
				final ObjectNode[] nodes = new ObjectMapper().readValue(jsonSort, ObjectNode[].class);
				for(ObjectNode node : nodes) {
					if(node.get("property").asText().equals("tenantName") || node.get("property").asText().equals("tenantURL") || node.get("property").asText().equals("description") || node.get("property").asText().equals("tenantPrefix")) {
						filter.setSort(node.get("property").asText());
					}
					if(node.get("direction") != null){
						filter.setDir(node.get("direction").asText());
					}
				}
			} catch (Exception e) {
				logger.info("Not a valid JSON format");
			}
        }

        Map<String, Object> model = new HashMap<String, Object>();

        Long num;
        List<Tenant> list;
        boolean multiTenant = false;
		try {
			multiTenant = this.service.isMultiTenant();
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}

        if(multiTenant){
            num = this.service.getCountTenants(filter);
            list = this.service.getTenants(filter);
        }
        else{
            num = 1L;
            list = new ArrayList<Tenant>();
            Tenant defaultTenant = this.service.getTenant(1);
            list.add(defaultTenant);
        }

        model.put("num", num);
        model.put("list", list);

        return new ModelAndView("ajax/tenants_ajax", "model", model);
    }

    @RequestMapping(value = "/tenant.html", method = GET)
    public ModelAndView getTenantHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);
        Configuration config=null;
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "tenants");
        this.user.setLoginUser(model, response);

        model.put("tenantID", tenantID);

        if (tenantID != 0) {
            Tenant tenant = this.service.getTenant(tenantID);
            model.put("tenantName", tenant.getTenantName());
            model.put("mobileLogin", tenant.getMobileLogin());
            model.put("tenantPrefix", tenant.getTenantPrefix());
            model.put("vidyoGatewayControllerDns", tenant.getVidyoGatewayControllerDns());
            model.put("inbound", tenant.getInbound());
            model.put("outbound", tenant.getOutbound());
        } else {
            model.put("tenantName", "");
            Configuration mobiledModeConfiguration = system.getConfiguration("MOBILE_LOGIN_MODE");
    		
    		if (mobiledModeConfiguration != null 
    				&& org.apache.commons.lang.StringUtils.isNotBlank(mobiledModeConfiguration.getConfigurationValue())) { 
    			model.put("mobileLogin",Integer.parseInt(mobiledModeConfiguration.getConfigurationValue()));
    		} else {
    			model.put("mobileLogin", 0);
    		}
			//Get Default Tenant
			Tenant defaultTenant = this.service.getTenant(1);
			model.put("defaultTenantPrefix", defaultTenant.getTenantPrefix());
			model.put("vidyoGatewayControllerDns", "");
        }
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        model.put("licenseVersion", this.service.getLicenseVersion());
        model.put("maxInstalls", this.service.getMaxNumOfInstalls(tenantID));
        model.put("installsInUse", this.service.getInstallsInUse(tenantID));
        model.put("maxSeats", this.service.getMaxNumOfSeats(tenantID));
        model.put("seatsInUse", this.service.getSeatsInUse(tenantID));
        model.put("publicRoomsInUse", this.roomService.getPublicRoomCountForTenentID(tenantID));
        config= this.system.getConfiguration(MAX_CREATE_PUBLIC_ROOM);
        if(config == null || config.getConfigurationValue() == null || config.getConfigurationValue().trim().isEmpty()){
    	   model.put("maxPublicRooms", "0");
        }else{
    	   model.put("maxPublicRooms", config.getConfigurationValue());//from configuration table

       }
        model.put("maxPorts", this.service.getMaxNumOfPorts(tenantID));
        model.put("multiTenant", this.service.isMultiTenant());
        model.put("maxExecutives", this.service.getMaxNumOfExecutives(tenantID));
        model.put("executivesInUse", this.service.getExecutivesInUse(tenantID));
        model.put("maxPanoramas", this.service.getMaxNumOfPanoramas(tenantID));
        model.put("panoramasInUse", this.service.getPanoramasInUse(tenantID));
        model.put("showExecutives", showExecutives);
        model.put("showVidyoReplay", showVidyoReplay);
        model.put("showVidyoVoice", showVidyoVoice);
		model.put("showPanoramas", showPanoramas);
		TenantConfiguration tenantConfig = this.service.getTenantConfiguration(tenantID);
		model.put("endpointUploadMode", tenantConfig.getEndpointUploadMode());
		Configuration endpointUploadModeConfiguration = system.getConfiguration("MANAGE_ENDPOINT_UPLOAD_MODE");
    	if (endpointUploadModeConfiguration != null && endpointUploadModeConfiguration.getConfigurationValue() != null){
    		model.put("superEndpointUploadMode", endpointUploadModeConfiguration.getConfigurationValue());
    	}
		// Setting the VidyoNeo Web RTC configuration flag
		config= this.system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_GUESTS");
		if(config != null && config.getConfigurationValue() != null
				&& !config.getConfigurationValue().trim().isEmpty()
				&& config.getConfigurationValue().equalsIgnoreCase("1")){
			model.put("showVidyoNeoWebRTC", true);
		} else {
			config= this.system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
			if(config != null && config.getConfigurationValue() != null
					&& !config.getConfigurationValue().trim().isEmpty()
					&& config.getConfigurationValue().equalsIgnoreCase("1")){
				model.put("showVidyoNeoWebRTC", true);
			} else {
				model.put("showVidyoNeoWebRTC", false);
			}
		}

		boolean showScheduledRoomConfig = true;
		 config = system.getConfiguration(RoomServiceImpl.SCHEDULED_ROOM_PREFIX);
		if (config == null || config.getConfigurationValue() == null || config.getConfigurationValue().trim().isEmpty()) {
			showScheduledRoomConfig = false;
		}
		model.put("showScheduledRoomConfig", showScheduledRoomConfig);
		 boolean showLogAggr=true;
		try{
		 config = system.getConfiguration("LogAggregationServer");
		 if (config == null || config.getConfigurationValue() == null || config.getConfigurationValue().trim().isEmpty()) {
			 showLogAggr = false;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
		model.put("showLogAggr", showLogAggr);
		
		boolean showCustomRole=true;
		try{
			 config = system.getConfiguration("ENDPOINTBEHAVIOR");
			 if (config == null || config.getConfigurationValue() == null || config.getConfigurationValue().trim().isEmpty()|| "0".equalsIgnoreCase(config.getConfigurationValue().trim())){
				 showCustomRole = false;
				}
			}catch(Exception e){
				logger.error(e.getMessage());
			}

		model.put("showCustomRole", showCustomRole);

        return new ModelAndView("super/tenant_html", "model", model);
    }

    @RequestMapping(value = "/tenant.ajax", method = GET)
    public ModelAndView getTenantAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("tenantID", tenantID);

        Tenant tenant = null;
        if (tenantID == 0) {
            tenant = new Tenant();
        } else {
            tenant = this.service.getTenant(tenantID);
        }
        model.put("tenant", tenant);
        model.put("tenantPrefix", tenant.getTenantPrefix());

        return new ModelAndView("ajax/tenant_ajax", "model", model);
    }

    @RequestMapping(value = "/fromtenants.ajax", method = GET)
    public ModelAndView getFromTenantsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        List<Tenant> list = this.service.getFromTenants(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/from_tenants_ajax", "model", model);
    }

    @RequestMapping(value = "/totenants.ajax", method = GET)
    public ModelAndView getToTenantsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        List<Tenant> list = this.service.getToTenants(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/to_tenants_ajax", "model", model);
    }

    @RequestMapping(value = "/fromrouters.ajax", method = GET)
    public ModelAndView getFromRoutersAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        List<Router> list = this.service.getFromRouters(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/from_routers_ajax", "model", model);
    }

    @RequestMapping(value = "/torouters.ajax", method = GET)
    public ModelAndView getToRoutersAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        List<Router> list = this.service.getToRouters(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/to_routers_ajax", "model", model);
    }

    @RequestMapping(value = "/fromvms.ajax", method = GET)
    public ModelAndView getFromVMsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        List<Service> list = this.service.getFromVMs(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/from_services_ajax", "model", model);
    }

    @RequestMapping(value = "/tovms.ajax", method = GET)
    public ModelAndView getToVMsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        List<Service> list = this.service.getToVMs(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/to_services_ajax", "model", model);
    }

    @RequestMapping(value = "/fromvps.ajax", method = GET)
    public ModelAndView getFromVPsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        List<Service> list = this.service.getFromVPs(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/from_services_ajax", "model", model);
    }

    @RequestMapping(value = "/tovps.ajax", method = GET)
    public ModelAndView getToVPsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        List<Service> list = this.service.getToVPs(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/to_services_ajax", "model", model);
    }

    @RequestMapping(value = "/fromvgs.ajax", method = GET)
    public ModelAndView getFromVGsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        List<Service> list = this.service.getFromVGs(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/from_services_ajax", "model", model);
    }

    @RequestMapping(value = "/tovgs.ajax", method = GET)
    public ModelAndView getToVGsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        List<Service> list = this.service.getToVGs(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/to_services_ajax", "model", model);
    }

    @RequestMapping(value = "/validatetenantname.ajax", method = GET)
    public ModelAndView validateTenantNameAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = LocaleContextHolder.getLocale();

        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);
        String tenantName = ServletRequestUtils.getStringParameter(request, "value", "");

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        if (!tenantName.equalsIgnoreCase("") && this.service.isTenantExistForTenantName(tenantName, tenantID)) {
            FieldError fe = new FieldError("tenantName", "tenantName", MessageFormat.format(ms.getMessage("duplicate.tenant.name", null, "", loc), tenantName));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/json_result_ajax", "model", model);
    }

    @RequestMapping(value = "/validatetenanturl.ajax", method = GET)
    public ModelAndView validateTenantUrlAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = LocaleContextHolder.getLocale();
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);
        String tenantURL = ServletRequestUtils.getStringParameter(request, "value", "");

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        if (!tenantURL.equalsIgnoreCase("") && this.service.isTenantUrlExistForTenantUrl(tenantURL, tenantID)) {
            FieldError fe = new FieldError("tenantURL", "tenantURL", MessageFormat.format(ms.getMessage("duplicate.tenant.url", null, "", loc), tenantURL));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/json_result_ajax", "model", model);
    }

    @RequestMapping(value = "/validatetenantreplayurl.ajax", method = GET)
    public ModelAndView validateTenantReplayUrlAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = LocaleContextHolder.getLocale();
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);
        String tenantReplayURL = ServletRequestUtils.getStringParameter(request, "value", "");

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        if (!tenantReplayURL.equalsIgnoreCase("") && this.service.isTenantReplayUrlExistForTenantReplayUrl(tenantReplayURL, tenantID)) {
            FieldError fe = new FieldError("tenantReplayURL", "tenantReplayURL", MessageFormat.format(ms.getMessage("duplicate.replay.url", null, "", loc), tenantReplayURL));
            errors.add(fe);
        }
        if ((tenantReplayURL != null) && !tenantReplayURL.trim().isEmpty() && 
				!(ValidatorUtil.isValidHTTPHTTPSURL(tenantReplayURL) ||
						ValidatorUtil.isValidURL(tenantReplayURL))) {
            FieldError fe = new FieldError("tenantReplayURL", "tenantReplayURL", 
            		MessageFormat.format(ms.getMessage("invalid.replay.url", null, "", loc), 
            				HtmlUtils.htmlEscape(tenantReplayURL)));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/json_result_ajax", "model", model);
    }

    @RequestMapping(value = "/validatetenantwebrtcurl.ajax", method = GET)
    public ModelAndView validateTenantWebRTCUrlAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = LocaleContextHolder.getLocale();

        String tenantWebRTCURLStr = ServletRequestUtils.getStringParameter(request, "value", "");

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        if (org.apache.commons.lang.StringUtils.isNotBlank(tenantWebRTCURLStr)) {
			try {
				URL tenantWebRTCURL = new URL(tenantWebRTCURLStr);
				String protocol = tenantWebRTCURL.getProtocol();
				String hostName = tenantWebRTCURL.getHost();
				if (StringUtils.isEmpty(protocol) || "http".equals(protocol.toLowerCase()) || StringUtils.isEmpty(hostName)) {
					FieldError fe = new FieldError("tenantWebRTCURL", "tenantWebRTCURL", ms.getMessage("webrtc.server.is.invalid.e.g.https.hostname.8080", null, "", loc));
					errors.add(fe);
				}
			} catch (MalformedURLException e) {
				FieldError fe = new FieldError("tenantWebRTCURL", "tenantWebRTCURL", ms.getMessage("webrtc.server.is.invalid.e.g.https.hostname.8080", null, "", loc));
				errors.add(fe);
			}
        }
        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/json_result_ajax", "model", model);
    }

    @RequestMapping(value = "/validatevidyogatewaycontrollerdns.ajax", method = GET)
    public ModelAndView validateVidyoGatewayControllerDnsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = LocaleContextHolder.getLocale();
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);
        String vidyoGatewayControllerDns = ServletRequestUtils.getStringParameter(request, "value", "");

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        if (!vidyoGatewayControllerDns.equalsIgnoreCase("") && this.service.isVidyoGatewayControllerDnsExist(vidyoGatewayControllerDns, tenantID)) {
            FieldError fe = new FieldError("vidyoGatewayControllerDns", "vidyoGatewayControllerDns",
            		MessageFormat.format(ms.getMessage("duplicate.vidyogateway.controller.dns", null, "", loc), vidyoGatewayControllerDns));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/json_result_ajax", "model", model);
    }

    @RequestMapping(value = "/validatetenantprefix.ajax", method = GET)
    public ModelAndView validateTenantPrefixAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = LocaleContextHolder.getLocale();
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);
        String tenantPrefix = ServletRequestUtils.getStringParameter(request, "value", "");

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

		String errMsg = validateTenantPrefix(tenantID, tenantPrefix);
		if(errMsg != null) {
			FieldError fe = new FieldError("tenantPrefix", "tenantPrefix", MessageFormat.format(ms.getMessage(errMsg, null, "", loc), tenantPrefix));
			errors.add(fe);
		}

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/json_result_ajax", "model", model);
    }

    private  String validateTenantPrefix(int tenantID, String tenantPrefix) {
        String retMsg = null;
    	boolean prefixInUse = this.service.isTenantPrefixExistForTenantPrefix(tenantPrefix, tenantID);

        if (!tenantPrefix.equalsIgnoreCase("") && prefixInUse) {
            retMsg = "duplicate.ext.prefix";
        }

		if (!prefixInUse) {
			// Check the scheduled room prefix
			int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(system, tenantPrefix);
			if (extExists > 0){
					retMsg = "extn.matches.scheduleroom.prefix";
			}
		}

        return retMsg;
    }

    @RequestMapping(value = "/savetenant.ajax", method = POST)
    public ModelAndView saveTenant(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = LocaleContextHolder.getLocale();
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Tenant tenant = new Tenant();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(tenant);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        String vidyoGatewayControllerDns = tenant.getVidyoGatewayControllerDns();
        if(vidyoGatewayControllerDns != null && vidyoGatewayControllerDns.trim().isEmpty()) {
        	tenant.setVidyoGatewayControllerDns(null);
        }
        
        // validations
        if (!this.service.isMultiTenant() && tenantID == 0) {
            FieldError fe = new FieldError("tenantName", "tenantName", MessageFormat.format(ms.getMessage("not.multi.tenant", null, "", loc), tenant.getTenantName()));
            results.addError(fe);
        }

        String tenantName = tenant.getTenantName();
        if(tenantName == null || tenantName.trim().isEmpty()) {
        	FieldError fe = new FieldError("tenantName", "tenantName", MessageFormat.format(ms.getMessage("you.must.fill.in.the.mandatory.fields", null, "", loc), tenantName));
            results.addError(fe);
        } else if (this.service.isTenantExistForTenantName(tenantName, tenantID)) {
            FieldError fe = new FieldError("tenantName", "tenantName", MessageFormat.format(ms.getMessage("duplicate.tenant.name", null, "", loc), tenantName));
            results.addError(fe);
        }

        String tenantURL = tenant.getTenantURL();
        if(tenantURL == null || tenantURL.trim().isEmpty()) {
        	FieldError fe = new FieldError("tenantURL", "tenantURL", MessageFormat.format(ms.getMessage("you.must.fill.in.the.mandatory.fields", null, "", loc), tenantName));
            results.addError(fe);
        } else if (this.service.isTenantUrlExistForTenantUrl(tenantURL, tenantID)) {
            FieldError fe = new FieldError("tenantURL", "tenantURL", MessageFormat.format(ms.getMessage("duplicate.tenant.url", null, "", loc), tenantURL));
            results.addError(fe);
        }

        String tenantPrefix = tenant.getTenantPrefix();
        String errMsg = null;
        if(isTenantPrefixRequired(tenantID) && (tenantPrefix == null || tenantPrefix.trim().isEmpty()))
        {
        	FieldError fe = new FieldError("tenantPrefix", "tenantPrefix", MessageFormat.format(ms.getMessage("you.must.fill.in.the.mandatory.fields", null, "", loc), tenantName));
            results.addError(fe);
        } else if(tenantPrefix != null) {
        	errMsg = validateTenantPrefix(tenantID, tenantPrefix);
        }
		if(errMsg != null) {
			FieldError fe = new FieldError("tenantPrefix", "tenantPrefix", MessageFormat.format(ms.getMessage(errMsg, null, "", loc), tenantPrefix));
			results.addError(fe);
		}

		String tenantReplayURL= tenant.getTenantReplayURL();
		if ((tenantReplayURL != null) && !tenantReplayURL.trim().isEmpty() && 
				!(ValidatorUtil.isValidHTTPHTTPSURL(tenantReplayURL) ||
						ValidatorUtil.isValidURL(tenantReplayURL))) {
            FieldError fe = new FieldError("tenantReplayURL", "tenantReplayURL", 
            		MessageFormat.format(ms.getMessage("invalid.replay.url", null, "", loc), 
            				HtmlUtils.htmlEscape(tenantReplayURL)));
            results.addError(fe);
        }
		if (tenantReplayURL != null && !tenantReplayURL.trim().isEmpty() && 
				this.service.isTenantReplayUrlExistForTenantReplayUrl(tenantReplayURL, tenantID)) {
            FieldError fe = new FieldError("tenantReplayURL", "tenantReplayURL", 
            		MessageFormat.format(ms.getMessage("duplicate.replay.url", null, "", loc), 
            				HtmlUtils.htmlEscape(tenantReplayURL)));
            results.addError(fe);
        }

		String tenantWebRTCURLStr= tenant.getTenantWebRTCURL();
		if (org.apache.commons.lang.StringUtils.isNotBlank(tenantWebRTCURLStr)) {
			try {
				URL tenantWebRTCURL = new URL(tenantWebRTCURLStr);
				String protocol = tenantWebRTCURL.getProtocol();
				String hostName = tenantWebRTCURL.getHost();
				if (StringUtils.isEmpty(protocol) || "http".equals(protocol.toLowerCase()) || StringUtils.isEmpty(hostName)) {
					FieldError fe = new FieldError("tenantWebRTCURL", "tenantWebRTCURL", ms.getMessage("webrtc.server.is.invalid.e.g.https.hostname.8080", null, "", loc));
		            results.addError(fe);
				}
			} catch (MalformedURLException e) {
				FieldError fe = new FieldError("tenantWebRTCURL", "tenantWebRTCURL", ms.getMessage("webrtc.server.is.invalid.e.g.https.hostname.8080", null, "", loc));
	            results.addError(fe);
			}
		}
		if (vidyoGatewayControllerDns != null && !vidyoGatewayControllerDns.trim().isEmpty() && this.service.isVidyoGatewayControllerDnsExist(vidyoGatewayControllerDns, tenantID)) {
            FieldError fe = new FieldError("vidyoGatewayControllerDns", "vidyoGatewayControllerDns",
            		MessageFormat.format(ms.getMessage("duplicate.vidyogateway.controller.dns", null, "", loc), vidyoGatewayControllerDns));
            results.addError(fe);
        }

		tenant.setRequestScheme(request.getScheme());

        Map<String, Object> model = new HashMap<String, Object>();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            if (tenantID == 0) {
            	String userName = ServletRequestUtils.getStringParameter(request, "username","");
            	if (userName.isEmpty() || userName.length() > 80 || 
            			!userName.matches(ValidationUtils.USERNAME_REGEX)) {   //printabled unicoded username
                    FieldError fe = new FieldError("AddTenant", "username", MessageFormat.format(ms.getMessage("invalid.username.match", null, "", loc), HtmlUtils.htmlEscape(userName)));
                    results.addError(fe);
                }
            	String password1 = ServletRequestUtils.getStringParameter(request, "password1", "");
            	String password2 = ServletRequestUtils.getStringParameter(request, "password2", "");
            	if(password1.isEmpty() || !password1.equals(password2)) {
            		FieldError fe = new FieldError("AddTenant", "password", MessageFormat.format(ms.getMessage("password.not.match", null, "", loc), ""));
                    results.addError(fe);
            	}

            	String memberName = ServletRequestUtils.getStringParameter(request, "memberName","");
            	String emailAddress = ServletRequestUtils.getStringParameter(request, "emailAddress","");
            	if (emailAddress.isEmpty() || !emailAddress.matches(ValidationUtils.EMAIL_REGEX)) {
                    FieldError fe = new FieldError("AddTenant", "email", MessageFormat.format(ms.getMessage("invalid.email.match", null, "", loc), emailAddress));
                    results.addError(fe);
                }

            	String description = ServletRequestUtils.getStringParameter(request, "description","");

            	if (results.hasErrors()) {
                    List<FieldError> fields = results.getFieldErrors();
                    model.put("success", Boolean.FALSE);
                    model.put("fields", fields);

                    model.put("success", Boolean.FALSE);
                    return new ModelAndView("ajax/result_ajax", "model", model);
                } else {
	            	Member member = new Member();

	                member.setUsername(userName);
	            	member.setPassword(password1);
	            	member.setEmailAddress(emailAddress);
	            	member.setMemberName(memberName);
	            	member.setDescription(description);
	                try {
	                	tenantID = this.service.insertTenant(tenant, member);
                        if (tenantID == -1 ) {
                            logger.error(" Insert Tenant failed, Password for default tenant Admin user did not passed requirements check");
                            List<FieldError> errors = new ArrayList<FieldError>();
                            FieldError fe = new FieldError("AddTenant", "AddTenant", ms.getMessage("insert.tenant.failure", null, "", loc) + " - " +  ms.getMessage("password.change.did.not.meet.requirements", null, "", loc));
                            errors.add(fe);
                            model.put("fields", errors);
                            model.put("success", Boolean.FALSE);
                            return new ModelAndView("ajax/result_ajax", "model", model);
                        }
	                } catch (Exception e) {
	                	logger.error("Insert tenant failed." , e);

	                	List<FieldError> errors = new ArrayList<FieldError>();
	                	FieldError fe = new FieldError("AddTenant", "AddTenant", ms.getMessage("insert.tenant.failure", null, "", loc));
	                	errors.add(fe);

	                	model.put("success", Boolean.FALSE);
	                    model.put("fields", errors);

	                    model.put("success", Boolean.FALSE);
	                    return new ModelAndView("ajax/result_ajax", "model", model);
	                }
                }
            } else {
				Tenant oldTenant = this.service.getTenant(tenantID);
				boolean databaseBacked = false;
				// If prefix is modified, create db backup and then proceed
                if(!oldTenant.getTenantPrefix().equalsIgnoreCase(tenant.getTenantPrefix())) {
                	int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(system, tenant.getTenantPrefix());
                	if(extExists > 0) {
                		List<FieldError> errors = new ArrayList<FieldError>();
                        FieldError fe = new FieldError("tenantPrefix", "tenantPrefix", MessageFormat.format(ms.getMessage("extn.matches.scheduleroom.prefix", null, "", loc), tenant.getTenantPrefix()));
                        errors.add(fe);
    		            model.put("success", Boolean.FALSE);
    		            model.put("fields", errors);
    		            return new ModelAndView("ajax/result_ajax", "model", model);
                	}
                	int count = roomService.isPrefixExisting(tenant.getTenantPrefix(), tenantID);
                	if(count > 0) {
                		List<FieldError> errors = new ArrayList<FieldError>();
                        FieldError fe = new FieldError("tenantPrefix", "tenantPrefix", MessageFormat.format(ms.getMessage("cannot.use.ext.prefix", null, "", loc), tenant.getTenantPrefix()));
                        errors.add(fe);
    		            model.put("success", Boolean.FALSE);
    		            model.put("fields", errors);
    		            return new ModelAndView("ajax/result_ajax", "model", model);
                	}
                	// Create DB backup as the prefix is updated
                	List<String> tableNames = new ArrayList<String>();
                	tableNames.add("Tenant");
                	tableNames.add("Room");
                    databaseBacked = system.createTablesBackup(tableNames);
                    if(!databaseBacked) {
                    	List<FieldError> errors = new ArrayList<FieldError>();
    		            FieldError fe = new FieldError("Update Tenant", "Update Tenant", ms.getMessage("failed.to.backup.the.database", null, "", loc));
    		            errors.add(fe);
    		            model.put("success", Boolean.FALSE);
    		            model.put("fields", errors);
    		            return new ModelAndView("ajax/result_ajax", "model", model);
                    }
                }
                
 				TenantUpdateResponse tenantUpdateResponse = service.updateTenant(tenantID, oldTenant, tenant, null);

				if(tenantUpdateResponse.getStatus() != TenantUpdateResponse.SUCCESS) {
					//Restore from backup
					boolean restored = system.restoreFromPartialBackup();
					logger.warn("System restored from partial backup created before tenant update - {}", restored);
					List<FieldError> errors = new ArrayList<FieldError>();
		            FieldError fe = new FieldError("Update Tenant", "Update Tenant", ms.getMessage("update.tenant.failure", null, "", loc));
		            errors.add(fe);
		            model.put("success", Boolean.FALSE);
		            model.put("fields", errors);
		            return new ModelAndView("ajax/result_ajax", "model", model);
				}

				if(databaseBacked) {
					//Delete the backup now
					boolean deleted = system.deletePartialBackup();
					logger.warn("Partial database backup deleted successfully - {}", deleted);
				}

                ReplayUpdateParamServiceStub replayUpdate = null;
				try {
                        replayUpdate = this.system.getReplayUpdateParamServiceWithAUTH(tenantID);
						UpdateTenantRequest req = new UpdateTenantRequest();
						req.setOldName(oldTenant.getTenantName());
						req.setNewName(tenant.getTenantName());
						UpdateStatusResponse resp = replayUpdate.updateTenant(req);
				} catch (Exception ignored) {
					logger.error("Exception while updating Replay", ignored);
                } finally {
                    if (replayUpdate != null) {
                        try {
                            replayUpdate.cleanup();
                        } catch (AxisFault af) {
                            // ignore
                        }
                    }
                }

            }

            //super-tenant's system lang
            this.system.saveSystemLang(tenantID, this.system.getSystemLang(1).getLangCode());

            if (tenantID > 0) {
                model.put("success", Boolean.TRUE);
            } else {
                model.put("success", Boolean.FALSE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    private boolean isTenantPrefixRequired(int tenantId) {
        if (tenantId == 0) {  // new tenant
            return true;
        }
        if (tenantId == 1) { // default tenant
            Tenant existingDefaultTenant = service.getTenant(1);
            if (existingDefaultTenant.getTenantPrefix() == null ||
                    "".equals(existingDefaultTenant.getTenantPrefix())) { // no existing tenant prefix
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    @RequestMapping(value = "/deletetenant.ajax", method = POST)
    public ModelAndView deleteTenant(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);
        Tenant tenant = null;
        if (tenantID > 0) {
            tenant = this.service.getTenant(tenantID);
            this.service.deleteTenant(tenantID);
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/fromrecs.ajax", method = GET)
    public ModelAndView getFromRecsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        List<Service> list = this.service.getFromRecs(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/from_services_ajax", "model", model);
    }

    @RequestMapping(value = "/torecs.ajax", method = GET)
    public ModelAndView getToRecsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        List<Service> list = this.service.getToRecs(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/to_services_ajax", "model", model);
    }

    @RequestMapping(value = "/fromreplays.ajax", method = GET)
    public ModelAndView getFromReplaysAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        List<Service> list = this.service.getFromReplays(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/from_services_ajax", "model", model);
    }

    @RequestMapping(value = "/toreplays.ajax", method = GET)
    public ModelAndView getToReplaysAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        List<Service> list = this.service.getToReplays(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/to_services_ajax", "model", model);
    }

    @RequestMapping(value = "/fromlocations.ajax", method = GET)
    public ModelAndView getFromLocationsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        List<Location> list = this.service.getFromLocations(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/from_locations_ajax", "model", model);
    }

    @RequestMapping(value = "/tolocations.ajax", method = GET)
    public ModelAndView getToLocationsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        List<Location> list = this.service.getToLocations(tenantID);
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/to_locations_ajax", "model", model);
    }
}