package com.vidyo.web;

import com.vidyo.service.ISystemService;
import com.vidyo.service.dialin.TenantDialInService;
import com.vidyo.web.dto.InvitationsSettingsDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.ServletRequestUtils.getStringParameter;

public abstract class InvitationsSettingsController {

    protected static final Logger logger = LoggerFactory.getLogger(InvitationsSettingsController.class);

    @Autowired
    protected ISystemService system;

    @Autowired
    protected TenantDialInService tenantDdialInService;

    @Autowired
    protected ReloadableResourceBundleMessageSource ms;

    protected ModelAndView saveInvitationSettingTemplate(InvitationsSettingsDTO s) {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        if(StringUtils.countMatches(s.getInvitationEmailContent(), "[ROOMLINK]")>1){
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        } else {
            boolean result = handleSaveInvitations(s);
            model.put("success", result);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }
    }

    protected InvitationsSettingsDTO buildInvitationsSettings(HttpServletRequest request) {
        return new InvitationsSettingsDTO.InvitationsDTOBuilder()
                .withInvitationEmailContent(getStringParameter(request, "invitationEmailContent", ""))
                .withInvitationEmailContentHtml(getStringParameter(request, "invitationEmailContentHtml", ""))
                .withVoiceOnlyContent(getStringParameter(request, "voiceOnlyContent", ""))
                .withWebcastContent(getStringParameter(request, "webcastContent", ""))
                .withInvitationEmailSubject(ServletRequestUtils.getStringParameter(request, "invitationEmailSubject", ""))
                .withDialInNumbers(ServletRequestUtils.getStringParameter(request, "dialInNumbers", ""))
                .withDialInNumbersGridChanged(ServletRequestUtils.getBooleanParameter(request, "dialInNumbersGridChanged", false))
                .build();
    }

    protected abstract boolean handleSaveInvitations(InvitationsSettingsDTO base);
}
