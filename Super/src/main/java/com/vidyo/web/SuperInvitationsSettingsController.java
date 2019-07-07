package com.vidyo.web;

import com.vidyo.web.dto.InvitationsSettingsDTO;
import org.springframework.context.i18n.LocaleContextHolder;
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

@Controller
public class SuperInvitationsSettingsController extends InvitationsSettingsController {

    @RequestMapping(value = "/invitationsetting.ajax",method = RequestMethod.GET)
    public ModelAndView getSuperInvitationSettingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        //
        String emailContent = this.system.getSuperInvitationEmailContent();
        if(emailContent.trim().length()==0) {
            emailContent = ms.getMessage("defaultInvitationEmailContent", null, "", loc);
        }
        model.put("invitationEmailContent", emailContent);

        //
        String emailContentHtml = this.system.getConfigValue(1, "SuperInvitationEmailContentHtml");
        if(emailContent.trim().length()==0) {
            emailContent = ms.getMessage("defaultInvitationEmailContentHtml", null, "", loc);
        }
        model.put("invitationEmailContentHtml", emailContentHtml);

        //
        String voiceOnlyContent = this.system.getSuperVoiceOnlyContent();
        if(voiceOnlyContent.trim().length()==0) {
            voiceOnlyContent = ms.getMessage("defaultInvitationVoiceOnlyContent", null, "", loc);
        }
        model.put("voiceOnlyContent", voiceOnlyContent);

        //
        String webcastContent = this.system.getSuperWebcastContent();
        if(webcastContent.trim().length()==0) {
            webcastContent = ms.getMessage("defaultInvitationWebcastContent", null, "", loc);
        }
        model.put("webcastContent", webcastContent);

        //
        String emailSubject = this.system.getSuperInvitationEmailSubject();
        if(emailSubject.trim().length()==0) {
            emailSubject = ms.getMessage("defaultInvitationEmailSubject", null, "", loc);
        }
        model.put("invitationEmailSubject", emailSubject);

        return new ModelAndView("ajax/invitation_setting_ajax", "model", model);
    }

    @RequestMapping(value = "/saveinvitationsetting.ajax", method = RequestMethod.POST)
    public ModelAndView saveInvitationSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean followDefault = ServletRequestUtils.getBooleanParameter(request, "followDefault", false);
        InvitationsSettingsDTO settingsDTO;
        if(followDefault) {
            settingsDTO = new InvitationsSettingsDTO.InvitationsDTOBuilder().build();
        } else {
            settingsDTO = buildInvitationsSettings(request);
        }
        return super.saveInvitationSettingTemplate(settingsDTO);
    }


    @Override
    protected boolean handleSaveInvitations(InvitationsSettingsDTO s) {

        int configID = this.system.saveSuperInvitationEmailContent(s.getInvitationEmailContent());
        int configID2 = this.system.saveSuperVoiceOnlyContent(s.getVoiceOnlyContent());
        int configID3 = this.system.saveSuperWebcastContent(s.getWebcastContent());
        int configID4 = this.system.saveSuperInvitationEmailSubject(s.getInvitationEmailSubject());
        int configID5 = this.system.saveOrUpdateConfiguration(1, "SuperInvitationEmailContentHtml", s.getInvitationEmailContentHtml());
        //doing same as current implementaion
        int configId6=1;

        if(s.isDialInNumbersGridChanged()){
            try {
                if(s.getDialInNumbers().isEmpty()){
                    //just do the delete only since all recoded is been deleted from the UI
                    tenantDdialInService.deleteTenantDialInCountryForTenant(0);
                }else{

                    tenantDdialInService.saveUpdateDialInNumbers(s.getDialInNumbers(), 0);
                }

            } catch (Exception e) {
                logger.error("Error occurred while save Dial in Numbers ",e);
                configId6=-1;
            }

        }

        if ((configID > 0) && (configID2 > 0) && (configID3 > 0) && (configID4 > 0) && (configID5 > 0) && (configId6 > 0)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
