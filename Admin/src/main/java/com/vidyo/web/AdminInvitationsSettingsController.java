package com.vidyo.web;

import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.web.dto.InvitationsSettingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.web.bind.ServletRequestUtils.getStringParameter;

@Controller
public class AdminInvitationsSettingsController extends InvitationsSettingsController {


    @RequestMapping(value = "/invitationsetting.ajax", method = RequestMethod.GET)
    public ModelAndView getAdminInvitationSettingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        // part 1
        String emailContent = this.system.getTenantInvitationEmailContent();
        if(emailContent.trim().length()==0) {
            emailContent = this.system.getSuperInvitationEmailContent();
            if(emailContent.trim().length() == 0) {
                emailContent = ms.getMessage("defaultInvitationEmailContent", null, "", loc);
            }
        }
        model.put("invitationEmailContent", emailContent);

        // part 2
        String emailContentHtml = this.system.getConfigValue(TenantContext.getTenantId(), "InvitationEmailContentHtml");
        if(emailContentHtml.trim().length()==0) {
            emailContentHtml = this.system.getConfigValue(1, "SuperInvitationEmailContentHtml");
            if(emailContentHtml.trim().length() == 0) {
                emailContentHtml = ms.getMessage("defaultInvitationEmailContentHtml", null, "", loc);
            }
        }
        model.put("invitationEmailContentHtml", emailContentHtml);

        // part 3
        String voiceOnlyContent = this.system.getTenantVoiceOnlyContent();
        if(voiceOnlyContent.trim().length()==0){
            voiceOnlyContent = this.system.getSuperVoiceOnlyContent();
            if(voiceOnlyContent.trim().length() == 0) {
                voiceOnlyContent = ms.getMessage("defaultInvitationVoiceOnlyContent", null, "", loc);
            }
        }
        model.put("voiceOnlyContent", voiceOnlyContent);

        // part 4
        String webcastContent = this.system.getTenantWebcastContent();
        if(webcastContent.trim().length()==0){
            webcastContent = this.system.getSuperWebcastContent();
            if(webcastContent.trim().length() == 0) {
                webcastContent = ms.getMessage("defaultInvitationWebcastContent", null, "", loc);
            }
        }
        model.put("webcastContent", webcastContent);

        // part 5
        String emailSubject = this.system.getTenantInvitationEmailSubject();
        if(emailSubject.trim().length()==0) {
            emailSubject = this.system.getSuperInvitationEmailSubject();
            if(emailSubject.trim().length() == 0) {
                emailSubject = ms.getMessage("defaultInvitationEmailSubject", null, "", loc);
            }
        }
        model.put("invitationEmailSubject", emailSubject);

        return new ModelAndView("ajax/invitation_setting_ajax", "model", model);
    }

    @RequestMapping(value = "/saveinvitationsetting.ajax", method = RequestMethod.POST)
    public ModelAndView saveInvitationSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean followSuper = ServletRequestUtils.getBooleanParameter(request, "followSuper", false);
        InvitationsSettingsDTO settingsDTO;
        if(followSuper) {
            settingsDTO = new InvitationsSettingsDTO.InvitationsDTOBuilder().build();
        } else {
            settingsDTO = buildInvitationsSettings(request);
        }
        return super.saveInvitationSettingTemplate(settingsDTO);
    }

    @Override
    protected boolean handleSaveInvitations(InvitationsSettingsDTO s) {

        int configID = this.system.saveTenantInvitationEmailContent(s.getInvitationEmailContent());
        int configID2 = this.system.saveTenantVoiceOnlyContent(s.getVoiceOnlyContent());
        int configID3 = this.system.saveTenantWebcastContent(s.getWebcastContent());
        int configID4 = this.system.saveTenantInvitationEmailSubject(s.getInvitationEmailSubject());
        int configID5 = this.system.saveOrUpdateConfiguration(TenantContext.getTenantId(), "InvitationEmailContentHtml", s.getInvitationEmailContentHtml());
        //doing same as current implementaion
        int configId6=1;

        if(s.isDialInNumbersGridChanged()){
            try {
                if(s.getDialInNumbers().isEmpty()){
                    //just do the delete only since all recoded is been deleted from the UI
                    tenantDdialInService.deleteTenantDialInCountryForTenant(TenantContext.getTenantId());
                }else{

                    tenantDdialInService.saveUpdateDialInNumbers(s.getDialInNumbers(), TenantContext.getTenantId());
                }
            } catch (Exception e) {
                logger.error("Error occurred while save Dial in Numbers ",e);
                configId6=-1;
            }

        }
        if ((configID > 0) && (configID2 > 0) && (configID3 > 0) && (configID4 > 0) && (configID5 > 0) && (configId6 > 0)) {
           return Boolean.TRUE;
        } else {
            return false;
        }
    }
}
