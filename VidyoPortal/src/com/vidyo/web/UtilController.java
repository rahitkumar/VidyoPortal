package com.vidyo.web;

import com.vidyo.bo.*;
import com.vidyo.framework.listeners.ApplicationRestartListener;
import com.vidyo.service.*;
import com.vidyo.utils.HtmlUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

public abstract class UtilController {

    @Autowired
    protected IUserService user;

    @Autowired
    protected ISystemService system;

    @Value("${upload.path}")
    private String upload_path;

    @Autowired
    private ApplicationRestartListener applicationRestartListener;

    @Autowired
    protected CookieLocaleResolver lr;   

    @RequestMapping(value = "/langs.ajax", method = GET)
    public ModelAndView getLangsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Boolean displaySystemLanguage = ServletRequestUtils.getBooleanParameter(request, "displaysystemlanguage", false);
        Map<String, Object> model = new HashMap<String, Object>();
        Long num = this.system.getCountLanguages(displaySystemLanguage);
        model.put("num", num);
        List<Language> list = this.system.getLanguages(displaySystemLanguage);
        model.put("list", list);
        return new ModelAndView("ajax/langs_ajax", "model", model);
    }


    @RequestMapping(value = "/settings.html", method = GET)
    public ModelAndView getSettingsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);
        return new ModelAndView(getViewName() + "/settings_html", "model", model);
    }

    @RequestMapping(value = "/home.html", method = GET)
    public ModelAndView getHomeHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		complementHomeModel(request, response, model);
        Boolean showWelcomeBanner = this.system.showWelcomeBanner();
	    model.put("showWelcomeBanner", showWelcomeBanner);
		String banner = "";
		banner = this.system.getWelcomeBanner();
		banner = HtmlUtils.stripNewlineCharacters(banner);
		model.put("welcomeBanner", StringEscapeUtils.escapeJavaScript(banner));
		String ipAddress = sanitizeIPaddress(getUserIPaddress(request));
		model.put("ipAddress",ipAddress);
		this.user.setLoginUser(model, response);
		HttpSession sess = request.getSession();
		String session_lang = (String)sess.getAttribute("session_lang");
		if (session_lang == null) {
			String lang = ServletRequestUtils.getStringParameter(request, "lang", "");
			if (lang.equalsIgnoreCase("")) {
				javax.servlet.http.Cookie[] cookies = request.getCookies();
				for (javax.servlet.http.Cookie ck:cookies){
					if (ck.getName() != null && ck.getName().equalsIgnoreCase(languagePropertyName())){
						lang = ck.getValue();
						break;
					}
				}
				
				// If the cookie not have the lang code 
				if (lang.equalsIgnoreCase("")) {
					Language userlang = this.user.getUserLang();
					lang = userlang.getLangCode();
				}
				response.sendRedirect("home.html?lang=" + lang);
				
				return null;
			} else {
				return new ModelAndView(getViewName() + "/home_html", "model", model);
			}
		} else {
			return new ModelAndView(getViewName() + "/home_html", "model", model);
		}		
    }

    @RequestMapping(value = "/serverstartedtime.ajax", method = GET)
    public ModelAndView getServerStartedAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("serverStartedTimestamp", this.applicationRestartListener.getServerRestartedTimestamp());
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date(this.applicationRestartListener.getServerRestartedTimestamp()));
        String serverStarted = (cal.get(Calendar.MONTH)+1)+"/"+
        					   cal.get(Calendar.DATE)+"/"+
        					   cal.get(Calendar.YEAR)+" "+
        					   cal.get(Calendar.HOUR_OF_DAY)+":"+
        					   cal.get(Calendar.MINUTE)+":"+
        					   cal.get(Calendar.SECOND)+":"+
        					   cal.get(Calendar.MILLISECOND);
        model.put("serverStarted", serverStarted);
        return new ModelAndView("ajax/server_started_time_ajax", "model", model);
    }

    public ModelAndView getGuideLocation(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> model = new HashMap<String, Object>();
		String langCode = ServletRequestUtils.getStringParameter(request, "langCode", "en");
		String guideType = ServletRequestUtils.getStringParameter(request, "guideType", "admin");
		String guideURL = system.getGuideLocation(langCode, guideType);
		model.put("nav", "settings");
		model.put("guideURL", guideURL);    	
		return new ModelAndView("ajax/guideloc_ajax", "model", model);
    }

    protected abstract String getViewName();

    protected abstract void complementHomeModel(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Map<String, Object> model) throws Exception;

    protected abstract String languagePropertyName();
    
    private String sanitizeIPaddress(String address) {
    	return  org.springframework.web.util.HtmlUtils.htmlEscape(address); 
    }
    
    private String getUserIPaddress(HttpServletRequest request) {
    	String ipAddress;
    	if(null == request.getHeader("X-FORWARDED-FOR") ) {
    		ipAddress = request.getRemoteAddr();
    	}else{
    		ipAddress = request.getHeader("X-FORWARDED-FOR");
    	}
    	return ipAddress;
    }
}