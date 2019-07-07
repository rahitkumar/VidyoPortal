package com.vidyo.web;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Language;
import com.vidyo.service.SecurityServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class AdminUISettingsController extends UISettingsController {

    @Value( "${upload.dir.super}" )
    private String upload_dir;

    @RequestMapping(value = "/saveaboutinfo.ajax", method = RequestMethod.POST)
    public ModelAndView saveAboutInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String aboutInfo = ServletRequestUtils.getStringParameter(request, "aboutInfo", "");

        boolean followSuper = ServletRequestUtils.getBooleanParameter(request, "followSuper", false);
        if(followSuper)
            aboutInfo = "";
        return super.saveAboutInfo(aboutInfo);
    }

    @RequestMapping(value = "/savecontactinfo.ajax",method = RequestMethod.POST)
    public ModelAndView saveContactInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contactInfo = ServletRequestUtils.getStringParameter(request, "contactInfo", "");

        boolean followSuper = ServletRequestUtils.getBooleanParameter(request, "followSuper", false);
        if(followSuper) {
            contactInfo = "";
        }
        return saveContactInfoTemplate(contactInfo);
    }

    @RequestMapping(value = "/notification.ajax", method = RequestMethod.GET)
    public ModelAndView getAdminNotificationAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String from = this.system.getNotificationFromEmailAddress();
        String to = this.system.getNotificationToEmailAddress();
        boolean enable = this.system.getEnableNewAccountNotification();

        if(from==null || from.trim().length() == 0) {
            from = this.system.getSuperNotificationFromEmailAddress();

            // To copy super-from-email to tenant-from-email in DB, we must do a save
            this.system.saveNotificationFromEmailAddress(from);
        }
        if(to==null || to.trim().length() == 0) {
            to = this.system.getSuperNotificationToEmailAddress();

            // To copy super-to-email to tenant-to-email in DB, we must do a save
            this.system.saveNotificationToEmailAddress(to);
        }

        //model.put("tenantID", "");
        model.put("fromEmail", from);
        model.put("toEmail", to);
        model.put("enableNewAccountNotification", enable);

        return new ModelAndView("ajax/notification_ajax", "model", model);
    }

    @RequestMapping(value = "/customizeduserportallogo.ajax", method = RequestMethod.GET)
    public ModelAndView getAdminCustomizedDefaultUserPortalLogoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String name = StringUtils.trimToEmpty(this.system.getCustomizedLogoName());
        if((name!=null) && (name.length()!=0)) {
            if(name.contains("/")) {
                name = name.substring(name.lastIndexOf("/") + 1);
            }
            File logoFile = new File(upload_dir + name);
            if(logoFile.exists())
                model.put("upname", "/admin/upload" + logoFile.getName());
        }
        return new ModelAndView("ajax/customizedlogo_ajax", "model", model);
    }

    @RequestMapping(value = "/customization.html", method = RequestMethod.GET)
    public ModelAndView getAdminCustomizationHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);

        // invite text start
        model.put("showVidyoReplay", showVidyoReplay);
        model.put("showVidyoVoice", showVidyoVoice);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);

        Locale loc = LocaleContextHolder.getLocale();
        // part 1
        String emailContent = this.system.getTenantInvitationEmailContent();
        if(emailContent.trim().length()==0) {
            emailContent = this.system.getSuperInvitationEmailContent();
            if(emailContent.trim().length() == 0) {
                emailContent = ms.getMessage("defaultInvitationEmailContent", null, "", loc);
            }
        }
        model.put("invitationEmailContentLen", emailContent.length());

        // part 2
        String voiceOnlyContent = this.system.getTenantVoiceOnlyContent();
        if(voiceOnlyContent.trim().length()==0){
            voiceOnlyContent = this.system.getSuperVoiceOnlyContent();
            if(voiceOnlyContent.trim().length() == 0) {
                voiceOnlyContent = ms.getMessage("defaultInvitationVoiceOnlyContent", null, "", loc);
            }
        }
        model.put("voiceOnlyContentLen", voiceOnlyContent.length());

        // part 3
        String webcastContent = this.system.getTenantWebcastContent();
        if(webcastContent.trim().length()==0){
            webcastContent = this.system.getSuperWebcastContent();
            if(webcastContent.trim().length() == 0) {
                webcastContent = ms.getMessage("defaultInvitationWebcastContent", null, "", loc);
            }
        }
        model.put("webcastContentLen", webcastContent.length());
        ///// invite text end
        File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME + "private/PRIVILEGED_MODE");
        model.put("privilegedMode", (privModeFileHandle != null && privModeFileHandle.exists()));
        return new ModelAndView("admin/customization_html", "model", model);
    }

    @Override
    public ModelAndView renderMainPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);

        HttpSession sess = request.getSession();
        String session_lang = (String)sess.getAttribute("session_lang");
        if (session_lang == null) {
            String lang = ServletRequestUtils.getStringParameter(request, "lang", "");
            if (lang.equalsIgnoreCase("")) {
                Language userlang = this.user.getUserLang();
                response.sendRedirect("index.html?lang=" + userlang.getLangCode());
                return null;
            } else {
                return new ModelAndView("admin/index_html", "model", model);
            }
        } else {
            return new ModelAndView("admin/index_html", "model", model);
        }
    }

    @RequestMapping(value = "/logo.html", method = RequestMethod.GET)
    public ModelAndView getAdminLogoHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);

        try {
            String logopath = this.system.getCustomizedLogoName();
            if (logopath == null || logopath.length() == 0) {
                model.put("logoname", "DEFAULT");
            } else if (logopath.contains("/")) {
                logopath = logopath.substring(logopath.lastIndexOf("/"), logopath.length());
                logopath = upload_dir + logopath;
                model.put("logoname", logopath);
            } else {
                model.put("logoname", logopath);
            }
        }
        catch(Exception e){
            model.put("logoname", "DEFAULT");
        }

        return new ModelAndView("admin/logo_html", "model", model);
    }

    @Override
    protected String getViewName() {
        return "admin";
    }

    @Override
    protected int handleSaveAboutInfo(String aboutInfoFinal) {
        return this.system.saveTenantAboutInfo(aboutInfoFinal);
    }

    @Override
    protected int handleSaveContactInfo(String contactInfoFinal) {
        return this.system.saveTenantContactInfo(contactInfoFinal);
    }

    @Override
    protected ModelAndView populateMenuContent(HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        boolean isIpcSuperManaged = system.isIpcSuperManaged();
        model.put("isIpcSuperManaged", isIpcSuperManaged);
        Configuration config = system.getConfiguration("SCHEDULED_ROOM_PREFIX");
        boolean isScheduledRoomEnabled = false;
        // Check for Prefix to show the link
        if (config != null && config.getConfigurationValue() != null
                && config.getConfigurationValue().trim().length() != 0) {
            isScheduledRoomEnabled = true;
        }

        model.put("isScheduledRoomEnabled", isScheduledRoomEnabled);
        model.put("isVidyoWebEnabledBySuper", this.system.isVidyoWebEnabledBySuper());

        this.user.setLoginUser(model, response);

        return new ModelAndView("admin/menu_content_html", "model", model);
    }

    @Override
    protected String getUploadDir() {
        return upload_dir;
    }
}
