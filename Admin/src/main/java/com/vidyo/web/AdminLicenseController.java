package com.vidyo.web;

import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.framework.context.TenantContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminLicenseController extends BaseLicenseController {

    @RequestMapping(value = "/license.ajax",method = RequestMethod.GET)
    public ModelAndView getAdminLicenseAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Integer tenantId = TenantContext.getTenantId();
        List<SystemLicenseFeature> featureList =  this.licensing.getTenantLicense(tenantId);
        Long featureCount = (long) featureList.size();

        model.put("num", featureCount);
        for (SystemLicenseFeature feature : featureList) {
            model.put(feature.getName().replace(" ", ""), feature);
        }

        return new ModelAndView("ajax/license_ajax", "model", model);
    }

    @Override
    protected void enrich(Map<String, Object> m) {

    }

    @Override
    protected String getBasePath() {
        return "admin";
    }
}
