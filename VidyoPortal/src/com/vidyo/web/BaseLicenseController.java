package com.vidyo.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import com.vidyo.service.LicensingService;

public abstract class BaseLicenseController {

    protected static final Logger logger = LoggerFactory.getLogger(BaseLicenseController.class);

    @Autowired
    protected LicensingService licensing;

    @Autowired
    protected ReloadableResourceBundleMessageSource ms;

    @Autowired
    private IUserService user;

    @Autowired
    private CookieLocaleResolver lr;

    @Autowired
    private ISystemService system;



    @RequestMapping(value = "/license.html",method = RequestMethod.GET)
    public ModelAndView getSuperLicenseHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);

        enrich(model);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView(getBasePath() + "/getlicense_html", "model", model);
    }

    protected abstract void enrich(Map<String, Object> m);

    protected abstract String getBasePath();
}
