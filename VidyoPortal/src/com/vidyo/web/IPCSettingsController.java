package com.vidyo.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.vidyo.bo.TenantIpc;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.interportal.IpcConfigurationService;
import com.vidyo.utils.ValidationUtils;

@Controller
public class IPCSettingsController {

    protected static final Logger logger = LoggerFactory.getLogger(IPCSettingsController.class);

    private final IUserService user;
    private final ISystemService system;
    private final ITenantService tenant;
    private final IpcConfigurationService ipcConfigurationService;
    private final CookieLocaleResolver lr;
    
    @Autowired
    private ReloadableResourceBundleMessageSource ms;

    @Autowired
    public IPCSettingsController(IUserService user,
                                 ISystemService system,
                                 ITenantService tenant,
                                 IpcConfigurationService ipcConfigurationService,
                                 CookieLocaleResolver lr) {
        this.user = user;
        this.system = system;
        this.tenant = tenant;
        this.ipcConfigurationService = ipcConfigurationService;
        this.lr = lr;
    }

    @RequestMapping(value = "/ipcsettings.html", method = RequestMethod.GET)
    public ModelAndView getInterPortalAdminSettingsHtml(
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("Entering getInterPortalAdminSettingsHtml() of SettingsController");
        // Get the IPC Administrator flag from Tenant table
        boolean isIpcSuperManaged = system.isIpcSuperManaged();
        if (isIpcSuperManaged) {
            Map<String, Object> model = new HashMap<String, Object>();
            String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
            model.put("guideLoc", guideLoc);
            return new ModelAndView("admin/license_html", "model", model);
        }
        Map<String, Object> model = new HashMap<String, Object>();
        List<TenantIpc> tenantIpcDetails = tenant
                .getTenantIpcDetails(this.system.getTenantId());
        StringBuilder tenantIpcDetail = null;
        int i = 0;
        for (TenantIpc tenantIpc : tenantIpcDetails) {
            if (tenantIpcDetail == null) {
                tenantIpcDetail = new StringBuilder();
            }
            tenantIpcDetail.append("['").append(tenantIpc.getHostName() == null ? "" : tenantIpc.getHostName())
                    .append("',").append(tenantIpc.getIpcWhiteListId())
                    .append(",").append(tenantIpc.getWhiteList()).append("]");
            if (i >= 0 && i < (tenantIpcDetails.size() - 1)) {
                tenantIpcDetail.append(",");
            }
            i++;
        }
        model.put("tenantIpcDetail", tenantIpcDetail);
        model.put("isIpcSuperManaged", isIpcSuperManaged);
        if (tenantIpcDetails.size() > 0) {
            model.put("tenantID", tenantIpcDetails.get(0).getTenantID());
            model.put("ipcId", tenantIpcDetails.get(0).getIpcID());
        } else {
            model.put("tenantID", this.system.getTenantId());
            model.put("ipcId", ipcConfigurationService
                    .getIpcConfiguration(this.system.getTenantId()).getIpcID());
        }
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        logger.debug("Exiting getInterPortalAdminSettingsHtml() of SettingsController");
        return new ModelAndView("admin/ipcAdminSettings", "model", model);
    }

    @RequestMapping(value = "/ipcsettings.ajax", method = RequestMethod.GET)
    public ModelAndView getInterPortalAdminSettingsAjax(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        logger.debug("Entering getInterPortalAdminSettingsAjax() of SettingsController");
        // Get the IPC Administrator flag from Tenant table
        boolean isIpcSuperManaged = system.isIpcSuperManaged();
        if (isIpcSuperManaged) {
            return null;
        }
        Map<String, Object> model = new HashMap<String, Object>();
        List<TenantIpc> tenantIpcDetails = tenant
                .getTenantIpcDetails(this.system.getTenantId());

        if(tenantIpcDetails == null || tenantIpcDetails.size() == 0) {
            if(tenantIpcDetails == null) {
                tenantIpcDetails = new ArrayList<TenantIpc>();
            }
            TenantIpc tenantIpc = new TenantIpc();
            tenantIpc.setTenantID(this.system.getTenantId());
            tenantIpc.setIpcID(ipcConfigurationService.getIpcConfiguration(
                    this.system.getTenantId()).getIpcID());
            tenantIpcDetails.add(tenantIpc);
        }
        model.put("tenantIpcDetails", tenantIpcDetails);
        logger.debug("Exiting getInterPortalAdminSettingsAjax() of SettingsController");
        return new ModelAndView("ajax/ipcAdminSettings", "model", model);
    }

    @RequestMapping(value = "/saveadminipcsettings.ajax", method = RequestMethod.POST)
    public ModelAndView saveAdminIpcSettingsAjax(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        logger.debug("Entering saveAdminIpcSettingsAjax() of SettingsController");
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
        
        //Check to see if the IPC Portal settings have been changed since the last page load
        if(system.isIpcSuperManaged()) {
            String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
            model.put("guideLoc", guideLoc);
            return new ModelAndView("admin/license_html", "model", model);
        }
        String ipcIdParam = ServletRequestUtils.getStringParameter(request, "ipcId", null);
        int ipcId = Integer.valueOf(ipcIdParam);
        String allowBlockFlag = ServletRequestUtils.getStringParameter(request, "allowBlockGroup", null);
        if(allowBlockFlag == null) {
            model.put("success", false);
            return new ModelAndView("ajax/saveAdminIpcSettings_ajax", "model",
                    model);
        }

        int flag = 0;
        if (allowBlockFlag.equalsIgnoreCase("allow")) {
            flag = 1;
        }

        ipcConfigurationService.updateAllowBlockFlag(flag, this.system.getTenantId());

        //get the deleted list
        String deletedIds = ServletRequestUtils.getStringParameter(request, "deleteIds", "");
        if(deletedIds != null && deletedIds != "") {
            String[] ids = deletedIds.split(",");
            if(ids.length > 0) {
                Integer[] intDeletedIds = new Integer[ids.length];
                for(int i = 0; i < ids.length; i++) {
                    intDeletedIds[i] = Integer.valueOf(ids[i]);
                }
                List<Integer> deletedIpcIds = Arrays.asList(intDeletedIds);
                tenant.deleteTenantIpcDomains(this.system.getTenantId(), deletedIpcIds);
            }
        }
        
        //Process newly added domains
        List<FieldError> fields = new ArrayList<FieldError>();
        String addedDomains = ServletRequestUtils.getStringParameter(request, "addedDomains", "");
        if(addedDomains != "") {
            addedDomains = addedDomains.replaceAll("\"", "");
            String[] domainNames = addedDomains.split(",");
            List<String> domainNamesList = new ArrayList<String>();
            for (String domainName : domainNames) {
            	if ((domainName != null) && !domainName.trim().isEmpty() 
            			&& !ValidationUtils.isValidIPCDomainName(domainName)) {
            		FieldError fe = new FieldError("addedDomains", "addedDomains", 
                			ms.getMessage("valid.domain.address", null, "", loc));
                	fields.add(fe);
            	} else {
            		domainNamesList.add(domainName);
            	}
            }
            if (domainNamesList.size() > 0) {
            	tenant.addTenantIpcDomains(ipcId, domainNamesList, flag);
            }
        }
        
        if (fields.size() > 0) {
        	model.put("success", Boolean.FALSE);
        	model.put("fields", fields);
        } else {
        	model.put("success", true);
        }
        
        logger.debug("Exiting saveAdminIpcSettingsAjax() of SettingsController");
        return new ModelAndView("ajax/saveAdminIpcSettings_ajax", "model",
                model);
    }
}
