package com.vidyo.web;

import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.EndpointUpload;
import com.vidyo.bo.MemberRoleEnum;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;
import static org.springframework.web.bind.ServletRequestUtils.getStringParameter;


public abstract class EndpointSettingsController {

    @Autowired
    protected ISystemService system;

    @RequestMapping(value = "/getEndpointSettings.ajax", method = RequestMethod.GET)
    public ModelAndView getEndpointSettings() {
        EndpointSettings es = handleGetEndpointSettings();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("dscpVideo", es.getDscpVideo());
        model.put("dscpAudio", es.getDscpAudio());
        model.put("dscpContent", es.getDscpContent());
        model.put("dscpSignaling", es.getDscpSignaling());
        model.put("minMediaPort", es.getMinMediaPort());
        model.put("maxMediaPort", es.getMaxMediaPort());
        model.put("alwaysUseVidyoProxy", (es.isAlwaysUseVidyoProxy() ? "1" : "0"));
        return new ModelAndView("ajax/endpoint_settings", "model", model);
    }

    @RequestMapping(value = "/saveEndpointSettings.ajax",method = RequestMethod.POST)
    public ModelAndView saveEndpointSettings(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        int dscpVideo = getIntParameter(request, "mediavideo", 0);
        int dscpAudio = getIntParameter(request, "mediaaudio", 0);
        int dscpContent = getIntParameter(request, "mediadata", 0);
        int dscpSiganling = getIntParameter(request, "signaling", 0);
        int minMediaPort = getIntParameter(request, "minMediaPort", 50000);
        int maxMediaPort = getIntParameter(request, "maxMediaPort", 50100);
        int alwaysUseVidyoProxy = getIntParameter(request, "alwaysUseVidyoProxy", 0);

        if (minMediaPort > maxMediaPort) {
            FieldError fe = new FieldError("minMediaPort",
                    "minMediaPort", "Minimum port cannot be greater than maximum port.");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {

            EndpointSettings es = new EndpointSettings();
            es.setDscpVideo(dscpVideo);
            es.setDscpAudio(dscpAudio);
            es.setDscpContent(dscpContent);
            es.setDscpSignaling(dscpSiganling);
            es.setMinMediaPort(minMediaPort);
            es.setMaxMediaPort(maxMediaPort);
            es.setAlwaysUseVidyoProxy(alwaysUseVidyoProxy == 1);

            //system.saveAdminEndpointSettings(es, TenantContext.getTenantId());
            handleSaveEndpointSettings(es);

            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    protected abstract EndpointSettings handleGetEndpointSettings();

    protected abstract void handleSaveEndpointSettings(EndpointSettings es);
}
