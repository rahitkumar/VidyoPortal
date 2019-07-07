package com.vidyo.web.portal;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import com.vidyo.utils.PortalUtils;
import com.vidyo.webshared.AboutSystemInfoController;

@Controller
public class SystemInfoController extends AboutSystemInfoController {

    @Override
    protected String getViewName() {
        return "";
    }

    @Override
    protected String getAboutInfo() {
        return super.getTenantAboutInfo();
    }

    @Override
    protected void populateContactInfoModel(Map<String, Object> model, HttpServletRequest request) {
        String contact_info = this.system.getTenantContactInfo();
        if (contact_info.length() == 0) {
            contact_info = this.system.getSuperContactInfo();
        }

        if(contact_info == null ||contact_info.equals("")) {
            contact_info = ms.getMessage("support.info", null, "", lr.resolveLocale(request));
        }
        prepareLogo(model);
        model.put("contact_info", contact_info);
    }
    
	private void prepareLogo(Map<String, Object> model) {
		String logo = PortalUtils.prepareLogo(this.system);
        model.put("logoUrl", logo);
	}

    @Override
    protected String getContactInfo() {
        String contact_info = this.system.getTenantContactInfo();
        if (contact_info.length() == 0) {
            contact_info = this.system.getSuperContactInfo();
        }
        return contact_info;
    }
}
