package com.vidyo.web;

import com.vidyo.bo.EndpointSettings;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SuperEndpointSettingsController extends EndpointSettingsController {

    @Override
    protected EndpointSettings handleGetEndpointSettings() {
        return system.getSuperEndpointSettings();
    }

    @Override
    public void handleSaveEndpointSettings(EndpointSettings es) {
        system.saveSuperEndpointSettings(es);
    }
}
