package com.vidyo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.service.LicensingServiceImpl;

@Controller
public class SuperLicenseController extends BaseLicenseController {

    @RequestMapping(value = "/license.ajax", method = RequestMethod.GET)
    public ModelAndView getSuperLicenseAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        List<SystemLicenseFeature> featureList = null;
        try {
            featureList =  this.licensing.getSystemLicenseDetails();
        } catch(Exception e) {
            logger.error(e.getMessage());
            featureList = new ArrayList<>();
        }
        Long featureCount = (long) featureList.size();

        model.put("num", featureCount);
        for (SystemLicenseFeature feature : featureList) {
            model.put(feature.getName().replace(" ", ""), feature);
        }
        return new ModelAndView("ajax/license_ajax", "model", model);
    }



    @Override
    protected void enrich(Map<String, Object> model) {
        Locale loc = LocaleContextHolder.getLocale();
        SystemLicenseFeature feature = null;
        try {
            feature = this.licensing.getSystemLicenseFeature("Version");
        } catch (Exception e) {
            logger.error("Error while accessing Tenant license detail {}", e.getMessage());
        }

        if (feature != null && (feature.getLicensedValue().equals(LicensingServiceImpl.LIC_VERSION_21) ||
                feature.getLicensedValue().equals(LicensingServiceImpl.LIC_VERSION_22))) {
            model.put("licenseType", ms.getMessage("lines.license.model", null, "", loc));
        } else {
            model.put("licenseType", ms.getMessage("ports.license.model", null, "", loc));
        }

    }

    @Override
    protected String getBasePath() {
        return "super";
    }
}
