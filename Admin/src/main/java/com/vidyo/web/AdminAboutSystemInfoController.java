package com.vidyo.web;

import com.vidyo.webshared.AboutSystemInfoController;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AdminAboutSystemInfoController extends AboutSystemInfoController {

    @Override
    protected String getViewName() {
        return "admin/";
    }

    @Override
    protected String getAboutInfo() {
        return super.getTenantAboutInfo();
    }

    @Override
    protected void populateContactInfoModel(Map<String, Object> model, HttpServletRequest request) throws Exception {

    }

    @Override
    protected String getContactInfo() {
        String contactInfo = this.system.getTenantContactInfo();
        if(contactInfo.trim().length()==0){
            contactInfo = this.system.getSuperContactInfo();
            //To copy super-contact-info to tenant-contact-info, we must do a save
            //this.system.saveTenantContactInfo(contactInfo);
        }
        return contactInfo;
    }
}
