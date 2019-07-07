package com.vidyo.web;

import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class SuperLoginController extends AbstractLoginController {

    @Override
    protected void populateHideForgetPasswordFeature(Map<String, Object> model) throws Exception {
        if (hideForgotPasswordGlobally() || system.isSuperPasswordRecoveryDisabled()) {
            model.put("showForgotPassword", Boolean.FALSE);
        } else {
            model.put("showForgotPassword", Boolean.TRUE);
        }
    }

    @Override
    protected String getViewName() {
        return "super";
    }
    
    @Override
    protected String languagePropertyName() {
        return "VidyoPortalSuperLanguage";
    }
}
