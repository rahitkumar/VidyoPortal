package com.vidyo.web;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Language;
import com.vidyo.bo.clusterinfo.ClusterInfo;
import com.vidyo.bo.email.SmtpConfig;
import com.vidyo.service.SecurityServiceImpl;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.email.EmailService;
import com.vidyo.utils.VendorUtils;

@Controller
public class SuperUISettingsController extends UISettingsController {

    @Value("${upload.dir.super}")
    protected String uploadDir;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/saveaboutinfo.ajax", method = RequestMethod.POST)
    public ModelAndView saveAboutInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String aboutInfo = ServletRequestUtils.getStringParameter(request, "aboutInfo", "");

        boolean followDefault = ServletRequestUtils.getBooleanParameter(request, "followDefault", false);
        if(followDefault){
            aboutInfo = "";
        }
        return super.saveAboutInfo(aboutInfo);
    }

    @RequestMapping(value = "/savecontactinfo.ajax",method = RequestMethod.POST)
    public ModelAndView saveContactInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contactInfo = ServletRequestUtils.getStringParameter(request, "contactInfo", "");

        boolean followDefault = ServletRequestUtils.getBooleanParameter(request, "followDefault", false);
        if(followDefault){
            contactInfo = "";
        }
        return saveContactInfoTemplate(contactInfo);
    }

    @RequestMapping(value = "/notification.ajax",method = RequestMethod.GET)
    public ModelAndView getSuperNotificationAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String from = this.system.getSuperNotificationFromEmailAddress();
        String to = this.system.getSuperNotificationToEmailAddress();

        model.put("fromEmail", from);
        model.put("toEmail", to);


        SmtpConfig smtpConfig = emailService.getSuperSmtpConfig();
        model.put("smtpHost", smtpConfig.getHost());
        model.put("smtpPort", ""+smtpConfig.getPort());
        model.put("smtpSecurity", smtpConfig.getSecurityType());
        model.put("smtpUsername", smtpConfig.getUsername());

        String password = smtpConfig.getPassword();
        if (!StringUtils.isBlank(password)) {
            password = SystemServiceImpl.PASSWORD_UNCHANGED;
        }
        model.put("smtpPassword", password);

        model.put("smtpTrustAllCerts", smtpConfig.isTrustAllCerts());
        model.put("notificationFlag", (smtpConfig.isEmailsOn() ? "on" : "off"));

        return new ModelAndView("ajax/notification_ajax", "model", model);
    }

    @RequestMapping(value = "/customizeduserportallogo.ajax", method = RequestMethod.GET)
    public ModelAndView getSuperCustomizedDefaultUserPortalLogoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String hostname = request.getServerName();
        String name = this.system.getCustomizedDefaultUserPortalLogoName();
        if((name!=null) && (name.length() != 0)) {
            if(name.contains("/")) {
                name = name.substring(name.lastIndexOf("/") + 1);
            }
            model.put("upname", "/super/upload/" + name);
        }
        return new ModelAndView("ajax/customizedlogo_ajax", "model", model);
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
                return new ModelAndView("super/index_html", "model", model);
            }
        } else {
            return new ModelAndView("super/index_html", "model", model);
        }
    }

    @RequestMapping(value = "/customization.html",method = RequestMethod.GET)
    public ModelAndView getCustomizationHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);

        model.put("showVidyoReplay", showVidyoReplay);
        model.put("showVidyoVoice", showVidyoVoice);

        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);

        String emailContent = this.system.getSuperInvitationEmailContent();
        if(emailContent.trim().length()==0) {
            emailContent = ms.getMessage("defaultInvitationEmailContent", null, "", loc);
        }
        model.put("invitationEmailContentLen", emailContent.length());

        String voiceOnlyContent = this.system.getSuperVoiceOnlyContent();
        if(voiceOnlyContent.trim().length()==0) {
            voiceOnlyContent = ms.getMessage("defaultInvitationVoiceOnlyContent", null, "", loc);
        }
        model.put("voiceOnlyContentLen", voiceOnlyContent.length());

        String webcastContent = this.system.getSuperWebcastContent();
        if(webcastContent.trim().length()==0) {
            webcastContent = ms.getMessage("defaultInvitationWebcastContent", null, "", loc);
        }
        model.put("webcastContentLen", webcastContent.length());
        boolean showConfigBanner = false;
        Configuration configBanner = system.getConfiguration("SHOW_CUSTOMIZE_BANNER");
        if(null != configBanner) {
            String val = configBanner.getConfigurationValue();
            if(val.equals("1"))  {
                showConfigBanner = true;
            }
        }
        if(VendorUtils.isDISA()){
            model.put("disaportal", true);
        }else {
            model.put("disaportal", false);
        }
        model.put("showConfigBanner", showConfigBanner);
        File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME + "private/PRIVILEGED_MODE");
        model.put("privilegedMode", (privModeFileHandle != null && privModeFileHandle.exists()));
        return new ModelAndView("super/customization_html", "model", model);
    }

    @Override
    protected String getViewName() {
        return "super";
    }


    @Override
    protected int handleSaveAboutInfo(String aboutInfoFinal) {
        return this.system.saveSuperAboutInfo(aboutInfoFinal);
    }

    @Override
    protected ModelAndView populateMenuContent(HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);

        model.put("showVidyoReplay", showVidyoReplay);
        model.put("showManageCloud", this.system.getEnableVidyoCloud());
        model.put("maintMode", false);
        //Invoke isMaintenance script to show/hide Hotstandby
        int maintStatus = system.isPortalOnMaintenance("");
        if(maintStatus ==  0 || maintStatus == 2) {
            model.put("maintMode", true);
            ClusterInfo clusterInfo = system.getClusterInfo("param");
            if (clusterInfo != null) {
                if (clusterInfo.getMyState() != null && clusterInfo.getMyState().contains("NOSTANDBY")) {
                    model.put("clusterErrorMessage", "WARNING !!! SYSTEM DEGRADED: NO HOT STANDBY DETECTED");
                }
            }
        }
        return new ModelAndView("super/menu_content_html", "model", model);
    }

    @Override
    protected String getUploadDir() {
        return uploadDir;
    }

    @Override
    protected int handleSaveContactInfo(String contactInfoFinal) {
        return this.system.saveSuperContactInfo(contactInfoFinal);
    }
}
