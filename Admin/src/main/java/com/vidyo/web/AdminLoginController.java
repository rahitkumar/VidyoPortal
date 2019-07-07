package com.vidyo.web;

import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.framework.context.TenantContext;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class AdminLoginController extends AbstractLoginController {

    @Override
    protected void populateHideForgetPasswordFeature(Map<String, Object> model) throws Exception {
        if(hideForgotPasswordGlobally())   {
            model.put("showForgotPassword", Boolean.FALSE);
        } else {
            Integer tenantId = TenantContext.getTenantId();
            AuthenticationConfig authConfig = this.system.getAuthenticationConfig(tenantId);
            if (authConfig != null && authConfig.isLdapflag()) { // Authentication using LDAP provided by third party
                model.put("showForgotPassword", Boolean.FALSE);
            } else {
                model.put("showForgotPassword", Boolean.TRUE);
            }
        }
    }

    @Override
    protected String getViewName() {
        return "admin";
    }
    
    @Override
    protected String languagePropertyName() {
        return "VidyoPortalAdminLanguage";
    }
}
