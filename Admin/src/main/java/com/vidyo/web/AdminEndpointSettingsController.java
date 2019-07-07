package com.vidyo.web;

import com.vidyo.bo.EndpointSettings;
import com.vidyo.framework.context.TenantContext;
import org.springframework.stereotype.Controller;

@Controller
public class AdminEndpointSettingsController extends EndpointSettingsController {

    @Override
    protected EndpointSettings handleGetEndpointSettings() {
        return system.getAdminEndpointSettings(TenantContext.getTenantId());
    }

    @Override
    public void handleSaveEndpointSettings(EndpointSettings es) {
        system.saveAdminEndpointSettings(es, TenantContext.getTenantId());
    }
}
