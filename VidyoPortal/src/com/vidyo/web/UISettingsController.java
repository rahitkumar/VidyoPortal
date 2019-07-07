package com.vidyo.web;

import com.vidyo.bo.Language;
import com.vidyo.framework.security.htmlcleaner.AntiSamyHtmlCleaner;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import com.vidyo.utils.HtmlUtils;

import org.apache.commons.lang.StringUtils;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

public abstract class UISettingsController {

    protected static final Logger logger = LoggerFactory.getLogger(UISettingsController.class);

    @Value("${vidyoreplay}")
    protected boolean showVidyoReplay;

    @Value("${vidyovoice}")
    protected boolean showVidyoVoice;

    @Autowired
    protected IUserService user;

    @Autowired
    protected ISystemService system;

    @Autowired
    protected CookieLocaleResolver lr;

    @Autowired
    protected ReloadableResourceBundleMessageSource ms;

    @RequestMapping(value = "/systemlang.html",method = GET)
    public ModelAndView getSystemLangHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView(getViewName() +"/systemlang_html", "model", model);
    }

    @RequestMapping(value = "/systemlang.ajax",method = GET)
    public ModelAndView getSystemLangAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Language systemlang = this.system.getSystemLang();
        model.put("systemlang", systemlang);

        return new ModelAndView("ajax/systemlang_ajax", "model", model);
    }

    @RequestMapping(value = "/savesystemlang.ajax",method = RequestMethod.POST)
    public ModelAndView saveSystemLang(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String langCode = ServletRequestUtils.getStringParameter(request, "langCode", "en");
        Map<String, Object> model = new HashMap<String, Object>();
        int configID = this.system.saveSystemLang(langCode);
        model.put("userLangID", this.user.getUserLang().getLangID());
        if (configID > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/menu_content.html", method = GET)
    public ModelAndView getMenuContentHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return populateMenuContent(response);
    }

    @RequestMapping(value = "/index.html", method = GET)
    public ModelAndView redirectToMainPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return renderMainPage(request, response);
    }

    @RequestMapping(value = "/", method = GET)
    protected abstract ModelAndView renderMainPage(HttpServletRequest request, HttpServletResponse response) throws Exception;

    @RequestMapping(value = "/customizedlogoinmarkup.ajax", method = GET)
    public ModelAndView getCustomizedLogoInMarkupAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("name", "");
        String name = this.system.getCustomizedSuperPortalLogoName();
        if(StringUtils.isNotBlank(name)) {
            if(name.contains("/")) {
                name = name.substring(name.lastIndexOf("/"), name.length());
            }
            File logoFile = new File(getUploadDir() + name);
            if(logoFile.exists())
                model.put("name", "/" + getViewName() + "/upload" + name+ "?version="+Math.random());

        }
        return new ModelAndView("ajax/customizedlogoinmarkup_ajax", "model", model);
    }


    protected ModelAndView saveAboutInfo(String aboutInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        String aboutInfoFinal = HtmlUtils.sanitize(aboutInfo);
        int configID = handleSaveAboutInfo(aboutInfoFinal);
        if (configID > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);

    }

    protected ModelAndView saveContactInfoTemplate(String contactInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        String contactInfoFinal = HtmlUtils.sanitize(contactInfo);
        int configID = handleSaveContactInfo(contactInfoFinal);
        if (configID > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    protected abstract String getViewName();

    protected abstract int handleSaveAboutInfo(String s);



    protected abstract int handleSaveContactInfo(String contactInfoFinal);

    protected abstract ModelAndView populateMenuContent(HttpServletResponse response) throws Exception;

    protected abstract String getUploadDir();

}
