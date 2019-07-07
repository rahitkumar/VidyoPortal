package com.vidyo.web;

import com.vidyo.service.ISystemService;
import com.vidyo.webshared.AboutSystemInfoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class SuperAboutSystemInfoController extends AboutSystemInfoController {

    @Autowired
    protected ISystemService system;

    @Override
    protected String getViewName() {
        return "super/";
    }

    @Override
    protected String getAboutInfo() {
        return this.system.getSuperAboutInfo();
    }

    @Override
    protected void populateContactInfoModel(Map<String, Object> model, HttpServletRequest request) throws Exception {

    }

    @Override
    protected String getContactInfo() {
        return this.system.getSuperContactInfo();
    }
}
