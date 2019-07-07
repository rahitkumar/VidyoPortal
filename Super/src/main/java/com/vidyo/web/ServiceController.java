package com.vidyo.web;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vidyo.bo.gateway.GatewayPrefix;
import com.vidyo.bo.gateway.GatewayPrefixFilter;
import com.vidyo.bo.networkconfig.IpAddressMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Location;
import com.vidyo.bo.NEConfiguration;
import com.vidyo.bo.RecorderEndpoint;
import com.vidyo.bo.RecorderEndpointFilter;
import com.vidyo.bo.Service;
import com.vidyo.bo.ServiceFilter;
import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.bo.VirtualEndpointFilter;
import com.vidyo.bo.networkconfig.RouterPool;
import com.vidyo.bo.tenantservice.TenantServiceMap;
import com.vidyo.service.LicensingService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.license.response.LicenseResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private IServiceService service;

    @Autowired
    private IUserService user;

    @Autowired
    private CookieLocaleResolver lr;

    @Autowired
    private ReloadableResourceBundleMessageSource ms;

    @Autowired
    private LicensingService licensing;

    @Autowired
    private ISystemService systemService;

    @Autowired
    private ITenantService tenantService;

    @RequestMapping(value = "/services.html", method = GET)
	public ModelAndView getServicesHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        model.put("langID", this.user.getLoginUser().getLangID());
        this.user.setLoginUser(model, response);

        //return new ModelAndView("super/services_html", "model", model);
        String guideLoc = systemService.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);        
        return new ModelAndView("super/config_tool_components_html", "model", model);
    }

    @RequestMapping(value = "/managegateways.html", method = GET)
    public ModelAndView getManageGatewaysHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);
        String guideLoc = systemService.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView("super/manage_gateways_html", "model", model);
    }

    @RequestMapping(value = "/services.ajax", method = GET)
    public ModelAndView getServicesAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServiceFilter filter = new ServiceFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        Map<String, Object> model = new HashMap<String, Object>();
        Long num = this.service.getCountServices(filter);
        model.put("num", num);

        String replacementServerNameForLocalhost = request.getServerName();

        List<Service> list = this.service.getServices(filter, replacementServerNameForLocalhost);
        model.put("list", list);

        return new ModelAndView("ajax/services_ajax", "model", model);
    }

    /* VidyoGateway */
    @RequestMapping(value = "/vg.html", method = GET)
    public ModelAndView getVGHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        model.put("serviceID", serviceID);

        if (serviceID != 0) {
            Service service = this.service.getVG(serviceID);
            model.put("serviceName", service.getServiceName());
        } else {
            model.put("serviceName", "");
        }

		if (this.service.isUseNewGatewayServiceInterface()) {
			model.put("showGatewayPrefixes", true);
		} else {
			model.put("showGatewayPrefixes", false);
		}

        return new ModelAndView("super/vg_html", "model", model);
    }

    @RequestMapping(value = "/vg.ajax", method = GET)
    public ModelAndView getVGAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("serviceID", serviceID);

        Service service;
        if (serviceID == 0) {
            service = new Service();
        } else {
            service = this.service.getVG(serviceID);
        }
        model.put("service", service);

        return new ModelAndView("ajax/vg_ajax", "model", model);
    }

    @RequestMapping(value = "/savevg.ajax", method = POST)
    public ModelAndView saveVG(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = lr.resolveLocale(request);
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);
        String serviceName = ServletRequestUtils.getStringParameter(request, "serviceName", "");
        String loginName = ServletRequestUtils.getStringParameter(request, "user", "");

        Service service = new Service();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(service);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        Map<String, Object> model = new HashMap<String, Object>();

        // validations
        if (serviceID == 0 && this.service.isVGExistsWithServiceName(serviceName)) {
            FieldError fe = new FieldError("serviceName", "serviceName", MessageFormat.format(ms.getMessage("duplicate.vg.name", null, "", loc), serviceName));
            results.addError(fe);
        }

        if (this.service.isVGExistsWithLoginName(loginName, serviceID)) {
            FieldError fe = new FieldError("user", "user", MessageFormat.format(ms.getMessage("duplicate.user.name", null, "", loc), loginName));
            results.addError(fe);
        }

        if (results.hasErrors()) {
            List fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);

            model.put("fields", fields);
        } else {
            if (serviceID == 0) {
                String password = ServletRequestUtils.getStringParameter(request, "password1", "password");
                //service.setPassword(SHA1.enc(password)); // encode the password
                service.setPassword(password); // do not encode the password
                serviceID = this.service.insertVG(service);
            } else {
                String password = ServletRequestUtils.getStringParameter(request, "password1", "do_not_update");
                if (password.equalsIgnoreCase("do_not_update")) {
                    service.setPassword(null);
                } else {
                    //service.setPassword(SHA1.enc(password)); // encode the password
                    service.setPassword(password); // do not encode the password
                }
                serviceID = this.service.updateVG(serviceID, service);
            }

            if (serviceID > 0) {
                model.put("success", Boolean.TRUE);
            } else {
                model.put("success", Boolean.FALSE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/deletevg.ajax", method = POST)
    public ModelAndView deleteVG(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        serviceID = this.service.deleteVG(serviceID);

        if (serviceID > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    /* VidyoManager */
    @RequestMapping(value = "/vm.html",method = GET)
    public ModelAndView getVMHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        model.put("serviceID", serviceID);

        if (serviceID != 0) {
            Service service = this.service.getVM(serviceID);
            model.put("serviceName", service.getServiceName());
        } else {
            model.put("serviceName", "");
        }

        return new ModelAndView("super/vm_html", "model", model);
    }

    @RequestMapping(value = "/vm.ajax",method = GET)
    public ModelAndView getVMAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("serviceID", serviceID);

        Service service;
        if (serviceID == 0) {
            service = new Service();
        } else {
            service = this.service.getVM(serviceID);
        }
        model.put("service", service);

        return new ModelAndView("ajax/vm_ajax", "model", model);
    }

    @RequestMapping(value = "/savevm.ajax",method = POST)
    public ModelAndView saveVM(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = lr.resolveLocale(request);
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);
        boolean needValidate = ServletRequestUtils.getBooleanParameter(request, "needValidate", true);

        Service service = new Service();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(service);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        Map<String, Object> model = new HashMap<String, Object>();

        if (results.hasErrors()) {
            List fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
            return new ModelAndView("ajax/result_ajax", "model", model);
        } else {
            List<FieldError> errors = new ArrayList<FieldError>();

            //verify user/pwd should be valid to access VidyoManager, otherwise reject
            String user  = service.getUser();
            String pwd   = ServletRequestUtils.getStringParameter(request, "password1", null);
            if(needValidate) {
                LicenseResponse licenseResponse = licensing.testVMKeyToken(user, pwd);
                
                if(licenseResponse.getStatus() == LicenseResponse.HTTP_UNAUTHORIZED) {
                    FieldError fe = new FieldError("saveVM", ms.getMessage("error", null, "", loc), ms.getMessage("abort.key.and.token.are.not.valid.to.access.vidyomanager", null, "", loc));
                    errors.add(fe);
                    model.put("success", Boolean.FALSE);
                    model.put("fields", errors);                
                    return new ModelAndView("ajax/result_ajax", "model", model);
                }
                
            }

            if (serviceID == 0) {
                String password = ServletRequestUtils.getStringParameter(request, "password1", "password");
                //service.setPassword(SHA1.enc(password)); // encode the password
                service.setPassword(password); // do not encode the password
                service = validateServiceAuth(service, serviceID);
            	
                //Insert the new VidyoManager
                serviceID = this.service.insertVM(service);
                if(serviceID <= 0) {
                    FieldError fe = new FieldError("saveVM", ms.getMessage("error", null, "", loc), ms.getMessage("failed.to.save", null, "", loc));
                    errors.add(fe);
                    model.put("success", Boolean.FALSE);
                    model.put("fields", errors);
                    return new ModelAndView("ajax/result_ajax", "model", model);                    
                }
                postProcessServiceSaveOrUpdate(serviceID);

            } else {
                String password = ServletRequestUtils.getStringParameter(request, "password1", "do_not_update");
                if (password.equalsIgnoreCase("do_not_update")) {
                    service.setPassword(null);
                } else {
                    //service.setPassword(SHA1.enc(password)); // encode the password
                    service.setPassword(password); // do not encode the password
                }
                
                service = validateServiceAuth(service, serviceID);
 
                serviceID = this.service.updateVM(serviceID, service);
                if(serviceID <= 0) {
                    FieldError fe = new FieldError("saveVM", ms.getMessage("error", null, "", loc), ms.getMessage("failed.to.save", null, "", loc));
                    errors.add(fe);
                }
                postProcessServiceSaveOrUpdate(serviceID);
                               
            }
            
            // Dummy call to clear cache and reload VM key/token values
            systemService.getVidyoManagerService();

            if(errors.size()!=0){
                model.put("success", Boolean.FALSE);
                model.put("fields", errors);
            }
            else {
                model.put("success", Boolean.TRUE);
            }

            return new ModelAndView("ajax/result_ajax", "model", model);
        }
    }

    @RequestMapping(value = "/deletevm.ajax",method = POST)
    public ModelAndView deleteVM(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        serviceID = this.service.deleteVM(serviceID);

        if (serviceID > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/virtualendpoints.ajax",method = GET)
    public ModelAndView getVirtualEndpointsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        VirtualEndpointFilter filter = new VirtualEndpointFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        Map<String, Object> model = new HashMap<String, Object>();
        Long num = this.service.getCountVirtualEndpoints(serviceID);
        model.put("num", num);
        List<VirtualEndpoint> list = this.service.getVirtualEndpoints(serviceID, filter);
        model.put("list", list);

        return new ModelAndView("ajax/virtualendpoints_ajax", "model", model);
    }

    @RequestMapping(value = "/gatewayprefixes.ajax",method = GET)
	public ModelAndView getGatewayPrefixesAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

		GatewayPrefixFilter filter = new GatewayPrefixFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			filter = null;
		}

		Map<String, Object> model = new HashMap<String, Object>();
		Long num = this.service.getCountGatewayPrefixes(serviceID);
		model.put("num", num);
		List<GatewayPrefix> list = this.service.getGatewayPrefixes(serviceID, filter);
		model.put("list", list);

		return new ModelAndView("ajax/gatewayprefixes_ajax", "model", model);
	}

    /* VidyoProxy */
    @RequestMapping(value = "/vp.html",method = GET)
    public ModelAndView getVPHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        model.put("serviceID", serviceID);

        if (serviceID != 0) {
            Service service = this.service.getVP(serviceID);
            model.put("serviceName", service.getServiceName());
        } else {
            model.put("serviceName", "");
        }

        return new ModelAndView("super/vp_html", "model", model);
    }

    @RequestMapping(value = "/vp.ajax",method = GET)
    public ModelAndView getVPAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("serviceID", serviceID);

        Service service;
        if (serviceID == 0) {
            service = new Service();
        } else {
            service = this.service.getVP(serviceID);
        }
        model.put("service", service);

        return new ModelAndView("ajax/vp_ajax", "model", model);
    }

    @RequestMapping(value = "/savevp.ajax",method = POST)
    public ModelAndView saveVP(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Service service = new Service();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(service);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        Map<String, Object> model = new HashMap<String, Object>();

        if (results.hasErrors()) {
            List fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);

            model.put("fields", fields);
        } else {
            service.setUser("none");
            service.setPassword("none");

            if (serviceID == 0) {
                serviceID = this.service.insertVP(service);
            } else {
                serviceID = this.service.updateVP(serviceID, service);
            }

            if (serviceID > 0) {
                model.put("success", Boolean.TRUE);
            } else {
                model.put("success", Boolean.FALSE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/deletevp.ajax",method = POST)
    public ModelAndView deleteVP(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        serviceID = this.service.deleteVP(serviceID);

        if (serviceID > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/configtool.html",method = GET)
    public ModelAndView displayConfigToolComponentsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        model.put("langID", this.user.getLoginUser().getLangID());
        this.user.setLoginUser(model, response);
        String guideLoc = systemService.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc); 
        return new ModelAndView("super/config_tool_components_html", "model", model);
    }

    @RequestMapping(value = "/configtoolsession.ajax",method = GET)
    public ModelAndView configToolSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        model.put("success", Boolean.TRUE);
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/networktool.html",method = GET)
    public ModelAndView displayConfigToolNetworkHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        model.put("langID", this.user.getLoginUser().getLangID());
        this.user.setLoginUser(model, response);
        String guideLoc = systemService.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc); 
        return new ModelAndView("super/config_tool_network_html", "model", model);
    }

    @RequestMapping(value = "/locationtool.html",method = GET)
    public ModelAndView displayConfigToolLocationsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        model.put("langID", this.user.getLoginUser().getLangID());
        this.user.setLoginUser(model, response);
        String guideLoc = systemService.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);         
        return new ModelAndView("super/config_tool_locations_html", "model", model);
    }

    @RequestMapping(value = "/configtoolswf.html",method = GET)
    public ModelAndView displayConfigToolSwfHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        model.put("langID", this.user.getLoginUser().getLangID());
        this.user.setLoginUser(model, response);

        String pageStr = ServletRequestUtils.getStringParameter(request, "page", "ManageComponents");  // 'ManageComponents', 'ManageNetwork', 'ManageLocations'
        model.put("page", pageStr);
        String guideLoc = systemService.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);         
        return new ModelAndView("super/config_tool_swf_html", "model", model);
    }

    @RequestMapping(value = "/neconfigurations.ajax",method = GET)
    public ModelAndView getNEConfigurationsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        String fuzzyName = ServletRequestUtils.getStringParameter(request, "name", "");
        fuzzyName += "%";

        String type =  ServletRequestUtils.getStringParameter(request, "type", "");

        boolean all = ServletRequestUtils.getBooleanParameter(request, "all", false); 

        //get records from db
        List<NEConfiguration> list = this.service.getNEConfigurations(fuzzyName, type);

        //check status and re-interpret status to 'Up' 'Down' 'New' 'Disable'
        Iterator it = list.iterator();
        long now = new Date().getTime();
        while (it.hasNext()) {
            NEConfiguration nec = (NEConfiguration)it.next();

            if(!all)
                nec.setData("");

            if(nec.getStatus().equals("ACTIVE")){
                if(nec.getHeartbeat() == null) {
                    nec.setStatus("DOWN");
                }
                else {
                    long last = nec.getHeartbeat().getTime();
                    if((now - last) > 30*1000)
                        nec.setStatus("DOWN");
                    else
                        nec.setStatus("UP");
                }
            }
            if(nec.getStatus().equals("NEW")){
                 nec.setStatus("NEW");
            }
            else if(nec.getStatus().equals("INACTIVE")){
                nec.setStatus("DISABLED");
            }
        }

        model.put("list", list);
        int num = list.size();
        model.put("num", num);

        return new ModelAndView("ajax/ne_configurations_ajax", "model", model);
    }

    @RequestMapping(value = "/singleneconfiguration.ajax",method = GET)
    public ModelAndView getSingleNEConfigurationAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        String identifier =  ServletRequestUtils.getStringParameter(request, "identifier", "");
        String type =  ServletRequestUtils.getStringParameter(request, "type", "");

        //get single record from db by its Identifier
        List<NEConfiguration> list = this.service.getSingleNEConfiguration(identifier, type);

	    //check status and re-interpret status to 'Up' 'Down' 'New' 'Disable'
        Iterator it = list.iterator();
        long now = new Date().getTime();
        while (it.hasNext()) {
            NEConfiguration nec = (NEConfiguration)it.next();

            if(nec.getStatus().equals("ACTIVE")){
                try {
                    long last = nec.getHeartbeat().getTime();
                    //if(nec.getVersion()==0)
                    //     nec.setStatus("NEW");
                    if((now - last) > 30*1000) {
                        nec.setStatus("DOWN");
                    }
                    else {
                        nec.setStatus("UP");
                    }
                }
                catch(Exception anyEx) {
                    nec.setStatus("DOWN");
                }
            }
            if(nec.getStatus().equals("NEW")){
                 nec.setStatus("NEW");
            }
            else if(nec.getStatus().equals("INACTIVE")){
                nec.setStatus("DISABLED");
            }
        }

        model.put("list", list);
        int num = list.size();
        model.put("num", num);

        return new ModelAndView("ajax/ne_configurations_ajax", "model", model);
    }

    @RequestMapping(value = "/checkAllInOne.ajax",method = GET)
    public ModelAndView checkAllInOneAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);
        model.put("AllInOne", this.service.isAllInOne());
        return new ModelAndView("ajax/all_in_one_ajax", "model", model);
    }

    @RequestMapping(value = "/showmanagecloud.ajax",method = GET)
    public ModelAndView showManageCloudAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        model.put("success", Boolean.FALSE);
        this.user.setLoginUser(model, response);

        String action = ServletRequestUtils.getStringParameter(request, "action", "r"); //action is either 'r' or 'w'

        if(action.trim().toLowerCase().equals("r")) {
            model.put("action","r");
            model.put("showManageCloud", this.systemService.getEnableVidyoCloud());
            model.put("success", Boolean.TRUE);
        }
        else {
            Boolean show =  ServletRequestUtils.getBooleanParameter(request, "show");
            this.systemService.saveEnableVidyoCloud(show);
            if(!show) {
                this.service.discardInProgressNetworkConfig();
            }
            model.put("success", Boolean.TRUE);
            model.put("action","w");
        }
        return new ModelAndView("ajax/show_manage_cloud_ajax", "model", model);
    }

    @RequestMapping(value = "/saveneconfiguration.ajax",method = POST)
    public ModelAndView saveNEConfigurationAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        List<FieldError> errors = new ArrayList<FieldError>();
        boolean increaseVersion = ServletRequestUtils.getBooleanParameter(request, "increaseVersion", true);

        NEConfiguration cfg = new NEConfiguration();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(cfg);
        binder.bind(request);

        cfg.setData( cfg.getData().replace("& lt;".subSequence(0, "& lt;".length()), "<") );
        cfg.setData( cfg.getData().replace("& gt;".subSequence(0, "& gt;".length()), ">") );
        cfg.setData( cfg.getData().replace("&nbsp;".subSequence(0, "&nbsp;".length()), " ") );

         if (!this.service.updateNEConfiguration(cfg, increaseVersion)) {
            FieldError fe = new FieldError("SaveNEC", cfg.getIdentifier(), "Failed to save");
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/deleteneconfigurations.ajax",method = POST)
    public ModelAndView deleteNEConfigurationsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        List<FieldError> errors = new ArrayList<FieldError>();

        String replacementName = ServletRequestUtils.getStringParameter(request, "replacementName", null);
        String toBeDeleteName = ServletRequestUtils.getStringParameter(request, "toBeDeleteName", null);
        String typeStr = ServletRequestUtils.getStringParameter(request, "type", null);

        if(replacementName != null && toBeDeleteName != null && typeStr!=null){
            int type = 0;
            if(typeStr.equalsIgnoreCase("VidyoManager"))
                type = 1;
            else if(typeStr.equalsIgnoreCase("VidyoGateway"))
                type = 2;
            else if(typeStr.equalsIgnoreCase("VidyoReplayRecorder"))
                type = 4;
            else if(typeStr.equalsIgnoreCase("VidyoProxy"))
                type = 3;
            else //if type is "VidyoRouterPool"
                type = 0;

            if(!this.service.replaceNetworkComponent(toBeDeleteName, replacementName, type)) {
                FieldError fe = new FieldError("serviceName", toBeDeleteName+":"+replacementName, "Failed to replace network component");
                errors.add(fe);
                logger.info("Failed to replace network component of "+type +": "+toBeDeleteName+" by "+replacementName);
            }
        }

        //selected could be an single Identifier, or several Identifiers with delimiter comma
        String selectedComponents =ServletRequestUtils.getStringParameter(request, "selected");
        StringTokenizer st = new StringTokenizer(selectedComponents, ",");
        List<String> routerIdsInPool = service.getRouterIdsInPool();
		while (st.hasMoreTokens()) {
			String identifier = st.nextToken();
			if (routerIdsInPool != null && routerIdsInPool.contains(identifier.trim())) {
				FieldError fe = new FieldError("serviceName", identifier, ms.getMessage("cannot.delete.router.in.pool",
						new Object[] { identifier }, "Cannot delete router " + identifier
								+ "which is assigned to a Router Pool", lr.resolveLocale(request)));
				errors.add(fe);
			} else {
				boolean deleted = this.service.deleteNEConfiguration(identifier);
				if(!deleted) {
					FieldError fe = new FieldError("serviceName", identifier, "Failed to delete");
					errors.add(fe);
				}
			}
		}

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/enableneconfigurations.ajax",method = POST)
    public ModelAndView enableNEConfigurationsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        List<FieldError> errors = new ArrayList<FieldError>();

        String selectedComponents =ServletRequestUtils.getStringParameter(request, "selected");
        boolean enable =ServletRequestUtils.getBooleanParameter(request, "enable", true);
        StringTokenizer st = new StringTokenizer(selectedComponents, ",");
        while(st.hasMoreTokens()) {
            String identifier = st.nextToken();
            if(!this.service.enableNEConfiguration(identifier, enable)){
                FieldError fe = new FieldError("serviceName", identifier, "Failed to enable/disable.");
                errors.add(fe);
            }
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/networkconfig.ajax",method = GET)
    public ModelAndView getNetworkConfigAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        String status = ServletRequestUtils.getStringParameter(request, "status", "ACTIVE");
        int    ver    = ServletRequestUtils.getIntParameter(request, "version", -1);

        //get records from db
        NEConfiguration ncRow = this.service.getNetworkConfig(status, ver);

        if(ncRow!=null) {
            model.put("success", Boolean.TRUE);
            model.put("networkconfig", ncRow);
        }
        else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/network_config_ajax", "model", model);
    }

    /**
     * Save "INPROGRESS" NetworkConfig, it won't make it "ACTIVE"
     */
    @RequestMapping(value = "/savenetworkconfig.ajax",method = POST)
    public ModelAndView saveNetworkConfigAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        List<FieldError> errors = new ArrayList<FieldError>();

        NEConfiguration cfg = new NEConfiguration();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(cfg);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        cfg.setData( cfg.getData().replace("& lt;".subSequence(0, "& lt;".length()), "<") );
        cfg.setData( cfg.getData().replace("& gt;".subSequence(0, "& gt;".length()), ">") );
        cfg.setData( cfg.getData().replace("&nbsp;".subSequence(0, "&nbsp;".length()), " ") );
        
		List<String> routerPoolIds = service.getRouterPoolIds(cfg.getData());

		Configuration config = systemService
				.getConfiguration("IPC_ROUTER_POOL");
		if (config != null) {
			if (!routerPoolIds.contains(config.getConfigurationValue())) {
				FieldError fe = new FieldError(
						"Save NetworConfig",
						cfg.getIdentifier(),
						"Router Pool Used by IPC, please remove the router pool from IPC and then delete");
				errors.add(fe);
				model.put("fields", errors);
				model.put("success", Boolean.FALSE);
				return new ModelAndView("ajax/result_ajax", "model", model);
			}
		}


        if(!this.service.updateInProgressNetworkConfig(cfg)) {
            FieldError fe = new FieldError("Save NetworConfig", cfg.getIdentifier(), "Failed to modify Network Config");
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/activateNetworkConfig.ajax",method = POST)
    public ModelAndView activateNetworkConfigAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        List<FieldError> errors = new ArrayList<FieldError>();

        if(!this.service.activateNetworkConfig()) {
            FieldError fe = new FieldError("Save NetworConfig", "", "Failed to activate network configuration");
            errors.add(fe);
        }


        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/discardInProgressNetworkConfig.ajax",method = POST)
    public ModelAndView discardInProgressNetworkConfigAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        List<FieldError> errors = new ArrayList<FieldError>();

        if(!this.service.discardInProgressNetworkConfig()) {
            FieldError fe = new FieldError("Discard InProgress NetworConfig", "", "Failed to delete in progress network configuration");
            errors.add(fe);
        }


        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/setfactorydefaults.ajax",method = POST)
    public ModelAndView setFactoryDefaultsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        List<FieldError> errors = new ArrayList<FieldError>();

        NEConfiguration cfg = new NEConfiguration();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(cfg);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        String vmIp = ServletRequestUtils.getStringParameter(request, "vmIp", "localhost");
        String vmId = ServletRequestUtils.getStringParameter(request, "vmId", "");
        String docVer = ServletRequestUtils.getStringParameter(request, "documentVersion", "0");

        if(!this.service.setFactoryDefaultNEConfiguration(cfg, vmIp, vmId, docVer)) {
            FieldError fe = new FieldError("SaveNEC", cfg.getIdentifier(), "Failed to save");
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/getvgbyname.ajax",method = POST)
    public ModelAndView getVGByNameAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = ServletRequestUtils.getStringParameter(request, "serviceName", "");

        Map<String, Object> model = new HashMap<String, Object>();

        Service service = this.service.getVGByName(name);

        if(service == null) {
            service = new Service();
            service.setServiceID(0);
        }

        model.put("service", service);

        return new ModelAndView("ajax/vg_ajax", "model", model);
    }

    @RequestMapping(value = "/getvrecbyname.ajax",method = GET)
    public ModelAndView getVRecByNameAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = ServletRequestUtils.getStringParameter(request, "serviceName", "");

        Map<String, Object> model = new HashMap<String, Object>();

        Service service = this.service.getVRecByName(name);

        if(service == null) {
            service = new Service();
            service.setServiceID(0);
        }

        model.put("service", service);

        return new ModelAndView("ajax/vg_ajax", "model", model);
    }

    @RequestMapping(value = "/getvpbyname.ajax",method = GET)
    public ModelAndView getVPByNameAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = ServletRequestUtils.getStringParameter(request, "serviceName", "");

        Map<String, Object> model = new HashMap<String, Object>();

        Service service = this.service.getVPByName(name);

        if(service == null) {
            service = new Service();
            service.setServiceID(0);
        }

        model.put("service", service);

        return new ModelAndView("ajax/vp_ajax", "model", model);
    }

    @RequestMapping(value = "/getvmbyname.ajax",method = GET)
    public ModelAndView getVMByNameAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = ServletRequestUtils.getStringParameter(request, "serviceName", "");

        Map<String, Object> model = new HashMap<String, Object>();

        Service service = this.service.getVMByName(name);

        if(service == null) {
            service = new Service();
            service.setServiceID(0);
        }

        model.put("service", service);

        return new ModelAndView("ajax/vm_ajax", "model", model);
    }

    @RequestMapping(value = "/supervm.ajax",method = GET)
    public ModelAndView getSuperVMAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        Service service = this.service.getSuperVM();
        if (service == null) {
            service = new Service();
        }

        model.put("serviceID", service.getServiceID());
        model.put("service", service);

        return new ModelAndView("ajax/vm_ajax", "model", model);
    }

    /**
     * Checking if a Network Element is used or not
     */
    @RequestMapping(value = "/checkneusage.ajax",method = GET)
    public ModelAndView checkNEUsageAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = ServletRequestUtils.getStringParameter(request, "serviceName", "");
        String typeStr = ServletRequestUtils.getStringParameter(request, "type", "");

        boolean used = this.service.isServiceUsed(typeStr, name);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("success", used);

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/managerecorders.html",method = GET)
    public ModelAndView getManageRecordersHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);
        String guideLoc = systemService.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc); 
        return new ModelAndView("super/manage_recorders_html", "model", model);
    }

    /* Recorders */
    @RequestMapping(value = "/rec.html",method = GET)
    public ModelAndView getRecHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        model.put("serviceID", serviceID);

        if (serviceID != 0) {
            Service service = this.service.getRec(serviceID);
            model.put("serviceName", service.getServiceName());
        } else {
            model.put("serviceName", "");
        }

        return new ModelAndView("super/rec_html", "model", model);
    }

    @RequestMapping(value = "/rec.ajax",method = GET)
    public ModelAndView getRecAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("serviceID", serviceID);

        Service service;
        if (serviceID == 0) {
            service = new Service();
        } else {
            service = this.service.getRec(serviceID);
        }
        model.put("service", service);

        return new ModelAndView("ajax/rec_ajax", "model", model);
    }

    @RequestMapping(value = "/saverec.ajax",method = POST)
    public ModelAndView saveRec(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = lr.resolveLocale(request);
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);
        String serviceName = ServletRequestUtils.getStringParameter(request, "serviceName", "");
        String loginName = ServletRequestUtils.getStringParameter(request, "user", "");

        Service service = new Service();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(service);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        Map<String, Object> model = new HashMap<String, Object>();

        // validations
        if (serviceID ==0 && this.service.isRecExistsWithServiceName(serviceName)) {
            FieldError fe = new FieldError("serviceName", "serviceName", MessageFormat.format(ms.getMessage("duplicate.rec.name", null, "", loc), serviceName));
            results.addError(fe);
        }

        if (this.service.isRecExistsWithLoginName(loginName, serviceID)) {
            FieldError fe = new FieldError("user", "user", MessageFormat.format(ms.getMessage("duplicate.user.name", null, "", loc), loginName));
            results.addError(fe);
        }

        if (results.hasErrors()) {
            List fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);

            model.put("fields", fields);
        } else {
            if (serviceID == 0) {
                String password = ServletRequestUtils.getStringParameter(request, "password1", "password");
                //service.setPassword(SHA1.enc(password)); // encode the password
                service.setPassword(password); // do not encode the password
                serviceID = this.service.insertRec(service);
            } else {
                String password = ServletRequestUtils.getStringParameter(request, "password1", "do_not_update");
                if (password.equalsIgnoreCase("do_not_update")) {
                    service.setPassword(null);
                } else {
                    //service.setPassword(SHA1.enc(password)); // encode the password
                    service.setPassword(password); // do not encode the password
                }
                serviceID = this.service.updateRec(serviceID, service);
            }

            if (serviceID > 0) {
                model.put("success", Boolean.TRUE);
            } else {
                model.put("success", Boolean.FALSE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/deleterec.ajax",method = POST)
    public ModelAndView deleteRec(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        serviceID = this.service.deleteRec(serviceID);

        if (serviceID > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/recorderendpoints.ajax",method = GET)
    public ModelAndView getRecorderEndpointsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        RecorderEndpointFilter filter = new RecorderEndpointFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        Map<String, Object> model = new HashMap<String, Object>();
        Long num = this.service.getCountRecorderEndpoints(serviceID);
        model.put("num", num);
        List<RecorderEndpoint> list = this.service.getRecorderEndpoints(serviceID, filter);
        model.put("list", list);

        return new ModelAndView("ajax/recorderendpoints_ajax", "model", model);
    }

    @RequestMapping(value = "/managereplays.html",method = GET)
    public ModelAndView getManageReplaysHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);
        String guideLoc = systemService.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc); 
        return new ModelAndView("super/manage_replays_html", "model", model);
    }

    /* Recorders */
    @RequestMapping(value = "/replay.html",method = GET)
    public ModelAndView getReplayHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        model.put("serviceID", serviceID);

        if (serviceID != 0) {
            Service service = this.service.getReplay(serviceID);
            model.put("serviceName", service.getServiceName());
        } else {
            model.put("serviceName", "");
        }

        return new ModelAndView("super/replay_html", "model", model);
    }

    @RequestMapping(value = "/replay.ajax",method = GET)
    public ModelAndView getReplayAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("serviceID", serviceID);

        Service service;
        if (serviceID == 0) {
            service = new Service();
        } else {
            service = this.service.getReplay(serviceID);
        }
        model.put("service", service);

        return new ModelAndView("ajax/replay_ajax", "model", model);
    }

    @RequestMapping(value = "/savereplay.ajax",method = POST)
    public ModelAndView saveReplay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = lr.resolveLocale(request);
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);
        String serviceName = ServletRequestUtils.getStringParameter(request, "serviceName", "");
        String loginName = ServletRequestUtils.getStringParameter(request, "user", "");

        Service service = new Service();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(service);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        Map<String, Object> model = new HashMap<String, Object>();

        // validations
        if (serviceID ==0 && this.service.isReplayExistsWithServiceName(serviceName)) {
            FieldError fe = new FieldError("serviceName", "serviceName", MessageFormat.format(ms.getMessage("duplicate.replay.nem", null, "", loc), serviceName));
            results.addError(fe);
        }

        if (this.service.isReplayExistsWithLoginName(loginName, serviceID)) {
            FieldError fe = new FieldError("user", "user", MessageFormat.format(ms.getMessage("duplicate.user.name", null, "", loc), loginName));
            results.addError(fe);
        }

        if (results.hasErrors()) {
            List fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);

            model.put("fields", fields);
        } else {
            if (serviceID == 0) {
                String password = ServletRequestUtils.getStringParameter(request, "password1", "password");
                //service.setPassword(SHA1.enc(password)); // encode the password
                service.setPassword(password); // do not encode the password
                serviceID = this.service.insertReplay(service);
            } else {
                String password = ServletRequestUtils.getStringParameter(request, "password1", "do_not_update");
                if (password.equalsIgnoreCase("do_not_update")) {
                    service.setPassword(null);
                } else {
                    //service.setPassword(SHA1.enc(password)); // encode the password
                    service.setPassword(password); // do not encode the password
                }
                serviceID = this.service.updateReplay(serviceID, service);
            }

            if (serviceID > 0) {
                model.put("success", Boolean.TRUE);
            } else {
                model.put("success", Boolean.FALSE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/deletereplay.ajax",method = GET)
    public ModelAndView deleteReplay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int serviceID = ServletRequestUtils.getIntParameter(request, "serviceID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        serviceID = this.service.deleteReplay(serviceID);

        if (serviceID > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    /* Locations */

    @RequestMapping(value = "/managelocations.html",method = GET)
    public ModelAndView getManageLocationsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);
        String guideLoc = systemService.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc); 
        return new ModelAndView("super/manage_locations_html", "model", model);
    }

    @RequestMapping(value = "/locations.ajax",method = GET)
    public ModelAndView getLocationsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServiceFilter filter = new ServiceFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        Map<String, Object> model = new HashMap<String, Object>();
        Long num = this.service.getCountLocations(filter);
        model.put("num", num);

        List<Location> list = this.service.getLocations(filter);
        model.put("list", list);

        return new ModelAndView("ajax/locations_ajax", "model", model);
    }

    @RequestMapping(value = "/location.html",method = GET)
    public ModelAndView getLocationHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int locationID = ServletRequestUtils.getIntParameter(request, "locationID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "services");
        this.user.setLoginUser(model, response);

        model.put("locationID", locationID);

        if (locationID != 0) {
            Location location = this.service.getLocation(locationID);
            model.put("locationTag", location.getLocationTag());
        } else {
            model.put("locationTag", "");
        }

        return new ModelAndView("super/location_html", "model", model);
    }

    @RequestMapping(value = "/location.ajax",method = GET)
    public ModelAndView getLocationAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int locationID = ServletRequestUtils.getIntParameter(request, "locationID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("locationID", locationID);

        Location location;
        if (locationID == 0) {
            location = new Location();
        } else {
            location = this.service.getLocation(locationID);
        }
        model.put("location", location);

        return new ModelAndView("ajax/location_ajax", "model", model);
    }

    @RequestMapping(value = "/savelocation.ajax",method = GET)
    public ModelAndView saveLocation(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = lr.resolveLocale(request);
        int locationID = ServletRequestUtils.getIntParameter(request, "locationID", 0);
        String locationTag = ServletRequestUtils.getStringParameter(request, "locationTag", "");

        Location location = new Location();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(location);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        Map<String, Object> model = new HashMap<String, Object>();

        // validations
        if (this.service.isLocationExistsWithLocationTag(locationTag, locationID)) {
            FieldError fe = new FieldError("locationTag", "locationTag", MessageFormat.format(ms.getMessage("duplicate.tag.name", null, "", loc), locationTag));
            results.addError(fe);
        }

        if (results.hasErrors()) {
            List fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            if (locationID == 0) {
                locationID = this.service.insertLocation(location);
            } else {
                locationID = this.service.updateLocation(locationID, location);
            }

            if (locationID > 0) {
                model.put("success", Boolean.TRUE);
            } else {
                model.put("success", Boolean.FALSE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    /***
     * @RequestMapping(value = "/deletelocation.ajax",method = POST)
     *  public ModelAndView deleteLocation(HttpServletRequest request, HttpServletResponse response) - there is a suspect
     *  that this method  is not anymore. At least there is clash because we have the same with same URL in RooterPools
     *  controller. This is comment is left as placeholder and is exptected to be deleted after verification VPTL -8951
     *  and related to locations stuff in Admin app
     */

    @RequestMapping(value = "/routerpools.ajax",method = GET)
	public ModelAndView getRouterPools(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Locale loc = lr.resolveLocale(request);
		List<RouterPool> routerPools = service.getRouterPoolNames();
		Configuration config = systemService
				.getConfiguration("IPC_ROUTER_POOL");
		if (config != null) {
			for (RouterPool routerPool : routerPools) {
				if (routerPool.getRouterPoolID().equalsIgnoreCase(
						config.getConfigurationValue())) {
					routerPool.setIpcFlag(true);
				}
			}
		}
		RouterPool routerPool = new RouterPool();
		routerPool.setRouterPoolID("0");		
		routerPool.setRouterPoolName(ms.getMessage("SuperSystemLicense.none", null, "", loc));
		routerPools.add(0, routerPool);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("routerPools", routerPools);
		return new ModelAndView("ajax/routerpools_ajax", "model", model);
	}

    @RequestMapping(value = "/getvpaddnladdrmap.ajax",method = GET)
    public ModelAndView getVPAddnlAddrMaps(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader ("Expires", 0);
        response.setHeader("Pragma", "no-cache");
		List<IpAddressMap> iPAddrMaps = service.getVPAddnlAddrMaps();

		Map<String, Object> model = new HashMap<String, Object>();
        model.put("size", iPAddrMaps.size());
		model.put("vpAddnlAddrMaps", iPAddrMaps);
		return new ModelAndView("ajax/ipaddrmaps_ajax", "model", model);
	}

    @RequestMapping(value = "/savevpaddnladdrmap.ajax",method = GET)
    public ModelAndView saveVPAddnlAddrMaps(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
        int addrMapId = ServletRequestUtils.getIntParameter(request, "addressmapid", 0);
        String localIp = ServletRequestUtils.getStringParameter(request, "localip", "");
        String remoteIp = ServletRequestUtils.getStringParameter(request, "remoteip", "");
        int updatecnt =0;
        IpAddressMap ipAddressMap = new IpAddressMap();
        ipAddressMap.setAddrMapID(addrMapId);
        ipAddressMap.setLocalAddr(localIp);
        ipAddressMap.setRemoteAddr(remoteIp);
        if(action.equalsIgnoreCase("ADD")) {
           updatecnt = service.addVPAddnlAddrMap(ipAddressMap);
        }else if(action.equalsIgnoreCase("EDIT")) {
           updatecnt = service.updateVPAddnlAddrMap(ipAddressMap);
        }else if(action.equalsIgnoreCase("DELETE")) {
           updatecnt = service.deleteVPAddnlAddrMap(addrMapId);
        }else {
             updatecnt =0;
        }

		Map<String, Object> model = new HashMap<String, Object>();
		 if (updatecnt > 0) {
                model.put("success", Boolean.TRUE);
            } else {
                model.put("success", Boolean.FALSE);
            }

        return new ModelAndView("ajax/result_ajax", "model", model);
	}

    private Service validateServiceAuth(Service service, int serviceID) {
        //Before inserting check for other VidyoManager entries
        ServiceFilter serviceFilter = new ServiceFilter();
        serviceFilter.setType("VidyoManager");
        List<Service> services = this.service.getServices(serviceFilter);
        //Delete the old VidyoManager(s)
        List<Integer> oldServiceIds = new ArrayList<Integer>();
        LicenseResponse licenseResponse = null;

        for(Service serviceEntry : services) {
            if(serviceEntry.getServiceID() != serviceID) {
                //handle update VM case - don't delete the services entry
                oldServiceIds.add(serviceEntry.getServiceID());
            }
            //Validate the authentication details
            licenseResponse = licensing.testVMKeyToken(serviceEntry.getUser(), serviceEntry.getPassword());
            if(licenseResponse.getStatus() == LicenseResponse.HTTP_SUCCESS) {
                service.setUser(serviceEntry.getUser());
                service.setPassword(serviceEntry.getPassword());
            } else if(licenseResponse.getStatus() == LicenseResponse.HTTP_INTERNAL_ERROR) {
                logger.error("VidyoManager is down - portal response code -"+ licenseResponse.getStatus());
            } else {
                logger.error("Error condition - response code invalid -"+ licenseResponse.getStatus());
            }
        }
        if(!oldServiceIds.isEmpty()) {
            this.service.deleteServices(oldServiceIds);
        }
        //Delete the old mappings
        if(serviceID != 0) {
            //Incase of update, delete old mappings and insert new one
            oldServiceIds.add(serviceID);
        }
        if(!oldServiceIds.isEmpty()) {
            this.service.deleteTenantServiceMapping(oldServiceIds);
        }

        return service;
    }


    private void postProcessServiceSaveOrUpdate(int serviceID) {
        List<TenantServiceMap> tenantServiceMaps = null;
        //no mappings exist for the old service ids, get all tenant ids
        List<Integer> tenantIds = tenantService.getAllTenantIds();

        if(tenantIds != null && !tenantIds.isEmpty()) {
            for(Integer id : tenantIds) {
                TenantServiceMap tenantServiceMap = new TenantServiceMap();
                tenantServiceMap.setServiceId(serviceID);
                tenantServiceMap.setTenantId(id);
                if(tenantServiceMaps == null) {
                    tenantServiceMaps = new ArrayList<TenantServiceMap>();
                }
                tenantServiceMaps.add(tenantServiceMap);
            }
            if(tenantServiceMaps != null && !tenantServiceMaps.isEmpty()) {
                //Insert the new mapping for all tenants
                this.service.saveTenantServiceMappings(tenantServiceMaps);
            }
        }
    }
}