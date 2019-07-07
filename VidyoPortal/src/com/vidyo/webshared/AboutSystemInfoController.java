package com.vidyo.webshared;

import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

public abstract class AboutSystemInfoController {

    @Autowired
    private IUserService user;

    @Autowired
    protected ISystemService system;

    @Autowired
    protected ReloadableResourceBundleMessageSource ms;

    @Autowired
    protected CookieLocaleResolver lr;

    @RequestMapping(value = "/about.html", method = GET)
    public ModelAndView getAboutHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        this.user.setLoginUser(model, response);
        return new ModelAndView(getViewName() + "about_html", "model", model);
    }

    @RequestMapping(value = "/about_content.html", method = GET)
    public ModelAndView getAboutContentHtml(HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        this.user.setLoginUser(model, response);
        String about_info = getAboutInfo();
        if(about_info == null || about_info.equals("")) {
        	about_info = ms.getMessage("about.us.full", null, "", lr.resolveLocale(request));
        }
        model.put("about_info", about_info);
        
        return new ModelAndView(getViewName() + "about_content_html", "model", model);
    }

    @RequestMapping(value = "/contact.html", method = GET)
    public ModelAndView getContactHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        this.user.setLoginUser(model, response);
        populateContactInfoModel(model, request);
        return new ModelAndView(getViewName() +"contact_html", "model", model);
    }

    @RequestMapping(value = "/contact_content.html", method = GET)
    public ModelAndView getContactContentHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        this.user.setLoginUser(model, response);
        String contact_info = getContactInfo();
        if(contact_info == null ||contact_info.equals("")) {
            contact_info = ms.getMessage("support.info", null, "", lr.resolveLocale(request));
        }
        model.put("contact_info", contact_info);
        return new ModelAndView(getViewName() + "contact_content_html", "model", model);
    }

    @RequestMapping(value = "/terms.html", method = GET)
    public ModelAndView getTermsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        this.user.setLoginUser(model, response);
        return new ModelAndView(getViewName() +"terms_html", "model", model);
    }

    @RequestMapping(value = "/terms_content.html", method = GET)
    public ModelAndView getTermsContentHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        this.user.setLoginUser(model, response);
        return new ModelAndView(getViewName() + "terms_content_html", "model", model);
    }

    @RequestMapping(value = "/aboutinfo.ajax",method = GET)
    public ModelAndView getAboutInfoAjax(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String aboutInfo = getAboutInfo();
        model.put("aboutInfo", aboutInfo);
        return new ModelAndView("ajax/aboutinfo_ajax", "model", model);
    }

    @RequestMapping(value = "/contactinfo.ajax",method = GET)
    public ModelAndView getContactInfoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String contactInfo = getContactInfo();
        if(contactInfo == null || contactInfo.equals("")) {
            contactInfo = ms.getMessage("support.info", null, "", lr.resolveLocale(request));
        }
        model.put("contactInfo", contactInfo);

        return new ModelAndView("ajax/contactinfo_ajax", "model", model);
    }

    protected abstract String getViewName();

    protected abstract String getAboutInfo();

    protected String getTenantAboutInfo() {
        String aboutInfo = this.system.getTenantAboutInfo();
        if(aboutInfo.trim().length() == 0){
            aboutInfo = this.system.getSuperAboutInfo();
            //To copy super-about-info to tenant-about-info, we must do a save
            //this.system.saveTenantAboutInfo(aboutInfo);
        }
        return aboutInfo;
    }

    protected abstract void populateContactInfoModel(Map<String,Object> model, HttpServletRequest request) throws Exception;

    protected abstract String getContactInfo();

}
