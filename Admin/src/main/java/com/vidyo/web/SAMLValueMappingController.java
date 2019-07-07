package com.vidyo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ITenantService;
import com.vidyo.service.idp.TenantIdpAttributesMapping;
import com.vidyo.utils.ValidationUtils;

@Controller
public class SAMLValueMappingController {
    
	private final TenantIdpAttributesMapping idpAttributesMapping;
    private final ITenantService tenant;
    private final ReloadableResourceBundleMessageSource ms;

    @Autowired
    public SAMLValueMappingController (TenantIdpAttributesMapping idpAttributesMapping,
                                       ITenantService tenant,
                                       ReloadableResourceBundleMessageSource ms
                                       ) {
        this.idpAttributesMapping = idpAttributesMapping;
        this.tenant = tenant;
        this.ms = ms;
    }

    @RequestMapping(value = "/samlmapping.ajax", method = RequestMethod.GET)
    public ModelAndView getSamlMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        Integer tenantID = TenantContext.getTenantId();
        List<TenantIdpAttributeMapping> list = this.idpAttributesMapping.getTenantIdpAttributeMapping(tenantID);
        TenantConfiguration configuration= tenant.getTenantConfiguration(tenantID);
        if(configuration.getUserImage()!=1){

            ListIterator<TenantIdpAttributeMapping> iter = list.listIterator();
            while(iter.hasNext()){
                if(iter.next().getVidyoAttributeName().equalsIgnoreCase("Thumbnail Photo")){
                    iter.remove();
                }
            }
        }
        
        for (TenantIdpAttributeMapping item : list) {
        	item.setIdpAttributeName(item.getIdpAttributeName().replaceAll(ValidationUtils.TAG_EXPR,""));
        	item.setDefaultAttributeValue(item.getDefaultAttributeValue().replaceAll(ValidationUtils.TAG_EXPR,"")); 
        }
        
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/tenant_saml_mapping_ajax", "model", model);
    }

    @RequestMapping(value = "/samlvaluemapping.ajax", method = RequestMethod.GET)
    public ModelAndView getSamlValueMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        int mappingID = ServletRequestUtils.getIntParameter(request, "mappingID", 0);

        if (mappingID != 0) {
            Integer tenantId = TenantContext.getTenantId();
            List<TenantIdpAttributeValueMapping> list = this.idpAttributesMapping.getTenantIdpAttributeValueMappingForEdit(tenantId, mappingID);
            
            for (TenantIdpAttributeValueMapping item : list) {
            	item.setIdpValueName(item.getIdpValueName().replaceAll(ValidationUtils.TAG_EXPR,""));  
            }
            		
            model.put("list", list);
            model.put("num", list.size());
        }
        return new ModelAndView("ajax/tenant_saml_value_mapping_ajax", "model", model);
    }

    @RequestMapping(value = "/savesamlvaluemapping.ajax", method = RequestMethod.POST)
    public ModelAndView saveSamlValueMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        TenantIdpAttributeValueMapping tenantIdpAttributeValueMapping = new TenantIdpAttributeValueMapping();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(tenantIdpAttributeValueMapping);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else if (errors.size() != 0) {
                model.put("success", Boolean.FALSE);
                model.put("fields", errors);
        } else {
        	Integer tenantId = TenantContext.getTenantId();
        	tenantIdpAttributeValueMapping.setIdpValueName(
        			tenantIdpAttributeValueMapping.getIdpValueName().replaceAll(ValidationUtils.TAG_EXPR,""));   
        	
            if (this.idpAttributesMapping.saveTenantIdpAttributeValueMappingRecord(tenantId, tenantIdpAttributeValueMapping) == -1) {
                FieldError fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("failed.to.save.notifications.data", null, "", loc));
                errors.add(fe);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }
    
    @RequestMapping(value = "/savesamlmapping.ajax", method = RequestMethod.POST)
    public ModelAndView saveSamlMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        TenantIdpAttributeMapping tenantIdpAttributeMapping = new TenantIdpAttributeMapping();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(tenantIdpAttributeMapping);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else if (errors.size() != 0) {
             model.put("success", Boolean.FALSE);
             model.put("fields", errors);
        } else {
            Integer tenantId = TenantContext.getTenantId();
            tenantIdpAttributeMapping.setIdpAttributeName(
            		tenantIdpAttributeMapping.getIdpAttributeName().replaceAll(ValidationUtils.TAG_EXPR,""));
            tenantIdpAttributeMapping.setDefaultAttributeValue(
            		tenantIdpAttributeMapping.getDefaultAttributeValue().replaceAll(ValidationUtils.TAG_EXPR,""));   
            
            if (this.idpAttributesMapping.updateTenantIdpAttributeMappingRecord(tenantId, tenantIdpAttributeMapping) <= 0) {
                FieldError fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("failed.to.save.notifications.data", null, "", loc));
                errors.add(fe);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/removesamlvaluemapping.ajax", method = RequestMethod.POST)
    public ModelAndView removeSamlValueMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        Locale loc = LocaleContextHolder.getLocale();

        int valueID = ServletRequestUtils.getIntParameter(request, "valueID", -1);

        if (valueID != -1) {
            Integer tenantId = TenantContext.getTenantId();
            if (this.idpAttributesMapping.removeTenantIdpAttributeValueMappingRecord(tenantId, valueID) <= 0) {
                FieldError fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("failed.to.save.notifications.data", null, "", loc));
                errors.add(fe);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }
}
