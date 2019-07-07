package com.vidyo.web;

import com.vidyo.bo.Member;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.LdapAuthentication;
import com.vidyo.bo.ldap.TenantLdapAttributeValueMapping;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ldap.ITenantLdapAttributesMapping;
import com.vidyo.utils.ValidationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;

@Controller
public class LDAPValueMappingController {

    protected static final Logger logger = LoggerFactory.getLogger(LDAPValueMappingController.class);

    private ITenantLdapAttributesMapping ldapAttributesMapping;

    private ReloadableResourceBundleMessageSource ms;

    private ISystemService system;

    @Autowired
    public LDAPValueMappingController(ITenantLdapAttributesMapping ldapAttributesMapping,
                                      ReloadableResourceBundleMessageSource ms,
                                      ISystemService system) {
        this.ldapAttributesMapping = ldapAttributesMapping;
        this.ms = ms;
        this.system = system;
    }

    @RequestMapping(value = "/ldapvaluemapping.ajax" , method = RequestMethod.GET)
    public ModelAndView getLdapValueMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        int mappingID = ServletRequestUtils.getIntParameter(request, "mappingID", 0);

        if (mappingID != 0) {
            Integer tenantId = TenantContext.getTenantId();
            List<TenantLdapAttributeValueMapping> list = ldapAttributesMapping.getTenantLdapAttributeValueMappingForEdit(tenantId, mappingID);
            
            for (TenantLdapAttributeValueMapping item : list) {
            	item.setLdapValueName(item.getLdapValueName().replaceAll(ValidationUtils.TAG_EXPR,""));  
            }
            
            model.put("list", list);
            model.put("num", list.size());
        }
        return new ModelAndView("ajax/tenant_ldap_value_mapping_ajax", "model", model);
    }

    @RequestMapping(value = "/saveldapvaluemapping.ajax", method = RequestMethod.POST)
    public ModelAndView saveLdapValueMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        Locale loc = LocaleContextHolder.getLocale();

        TenantLdapAttributeValueMapping tenantLdapAttributeValueMapping = new TenantLdapAttributeValueMapping();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(tenantLdapAttributeValueMapping);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            Integer tenantId = TenantContext.getTenantId();
            tenantLdapAttributeValueMapping.setLdapValueName(
            		tenantLdapAttributeValueMapping.getLdapValueName().replaceAll(ValidationUtils.TAG_EXPR,""));   
            
            if (this.ldapAttributesMapping.saveTenantLdapAttributeValueMappingRecord(tenantId, tenantLdapAttributeValueMapping) <= 0) {
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


    @RequestMapping(value = "/removeldapvaluemapping.ajax", method = RequestMethod.POST)
    public ModelAndView removeLdapValueMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        Locale loc = LocaleContextHolder.getLocale();

        int valueID = ServletRequestUtils.getIntParameter(request, "valueID", -1);

        if (valueID != -1) {
            Integer tenantId = TenantContext.getTenantId();
            if (this.ldapAttributesMapping.removeTenantLdapAttributeValueMappingRecord(tenantId, valueID) <= 0) {
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

    @RequestMapping(value = "/testldapusermapping.ajax", method = RequestMethod.POST)
    public ModelAndView testLDAPUserMapping(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        Locale loc = LocaleContextHolder.getLocale();

        AuthenticationConfig authConfig = new AuthenticationConfig();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(authConfig);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            Integer tenantID = TenantContext.getTenantId();
            Member ldapMember = this.system.testLDAPUserMappingService(tenantID, (LdapAuthentication) authConfig.toAuthentication());
            if (ldapMember == null) {
                FieldError fe = new FieldError("LDAPAuthentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("user.authentication.failed", null, "", loc));
                errors.add(fe);
            } else {
                try{
                    if(ldapMember.getThumbNailImage()!=null){
                        BufferedImage imag=ImageIO.read(new ByteArrayInputStream(ldapMember.getThumbNailImage()));
                        if(imag!=null){
                            model.put("thumbNailImage", new String(Base64.getEncoder().encode(ldapMember.getThumbNailImage())));
                        }
                    }
                }catch(Exception e){
                    logger.error("error while converting to base 64 encoded",e);
                }
                model.put("member", ldapMember);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }

        return new ModelAndView("ajax/test_ldap_mapping_ajax", "model", model);
    }
}
