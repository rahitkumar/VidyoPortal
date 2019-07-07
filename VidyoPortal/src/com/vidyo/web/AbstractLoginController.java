package com.vidyo.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vidyo.bo.*;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.framework.security.utils.SHA1;
import com.vidyo.service.IMemberService;
import com.vidyo.service.member.response.MemberManagementResponse;
import com.vidyo.utils.HtmlUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import com.vidyo.bo.loginhistory.MemberLoginHistory;
import com.vidyo.service.ILoginService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import com.vidyo.service.loginhistory.MemberLoginHistoryService;
import com.vidyo.service.passwdhistory.MemberPasswordHistoryService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


public abstract class AbstractLoginController {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractLoginController.class);

    @Autowired
    private ILoginService service;

    @Autowired
    protected ISystemService system;

    @Autowired
    private CookieLocaleResolver lr;

    @Autowired
    private ReloadableResourceBundleMessageSource ms;

    private String savedReqUrl = "members.html";

    @Autowired
    private IUserService user;

    @Autowired
    private MemberLoginHistoryService memberLoginHistoryService;

    @Autowired
    private MemberPasswordHistoryService memberPasswordHistoryService;

    @Autowired
    private IMemberService member;


    @RequestMapping(value = "/login.html", method = { GET, POST} )
    public ModelAndView getLoginHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String lang = ServletRequestUtils.getStringParameter(request, "lang", "");
        String theme = ServletRequestUtils.getStringParameter(request, "theme", "");
        String query = request.getQueryString();
        if (lang.equalsIgnoreCase("") && theme.equalsIgnoreCase("")) {
            Language systemlang = this.system.getSystemLang();
            StringBuffer params = new StringBuffer().append("login.html");
            params.append("?lang=").append(systemlang.getLangCode());
            if (query != null) {
                params.append("&").append(query);
            }
            response.sendRedirect(params.toString());
            return null;
        } else {
            Map<String, Object> model = new HashMap<String, Object>();
            List<Language> list = this.system.getLanguages();
            model.put("list", list);
            String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
            model.put("guideLoc", guideLoc);
            javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(languagePropertyName(), lang);
            response.addCookie(cookie);
            Boolean showLoginBanner = this.system.showLoginBanner();
            model.put("showLoginBanner", showLoginBanner);
            if(showLoginBanner){
                String banner = "";
                banner = this.system.getLoginBanner();
                banner = HtmlUtils.stripNewlineCharacters(banner);
                model.put("loginBanner", StringEscapeUtils.escapeJavaScript(banner));
            }
            populateHideForgetPasswordFeature(model);
            return new ModelAndView(getViewName() + "/login_html", "model", model);
        }
    }

    @RequestMapping(value = "/forgot.ajax", method = POST)
    public ModelAndView forgotPasswordAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int rc = 0;
        String email = ServletRequestUtils.getStringParameter(request, "email", "");
        
        if (!email.equalsIgnoreCase("")) {
            rc = this.service.forgotPassword(email, request);
        }

        Map<String, Object> model = new HashMap<String, Object>();

        if (rc > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
			List<FieldError> errors = new ArrayList<FieldError>();
			if(rc == -100) {
				//Add error message - not configured
				FieldError fe = new FieldError("Configuration", "Configuration", ms.getMessage("email.notifications.not.configured", null, "", lr.resolveLocale(request)));
				errors.add(fe);
			}
			
			if(rc == -101) {
				FieldError fe = new FieldError("Configuration", "Configuration",  ms.getMessage("password.reset.invalid.email", null, "", lr.resolveLocale(request)));
				errors.add(fe);
			}
			model.put("fields", errors);						
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/resetpassword.html", method = GET)
    public ModelAndView resetPasswordHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {        int rc = 0;
        String passKey = ServletRequestUtils.getStringParameter(request, "key", "");
        if (!passKey.equalsIgnoreCase("")) {
            rc = this.service.resetPassword(passKey, request);
        }
        Map<String, Object> model = new HashMap<String, Object>();
        if (rc > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
			if(rc == -100) {
				//Add error message - not configured
				FieldError fe = new FieldError("Configuration", "Configuration", ms.getMessage("email.notifications.not.configured", null, "", lr.resolveLocale(request)));
				List<FieldError> errors = new ArrayList<FieldError>();
				errors.add(fe);
				model.put("fields", errors);
			}            
        }
        return new ModelAndView(getViewName() + "/reset_html", "model", model);
    }

    @RequestMapping(value = "/loginhistory.html", method = GET)
	public ModelAndView getLoginHistoryDetailsHtml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		SavedRequest savedRequest = (SavedRequest) request.getSession(true)
				.getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");

		String savedReqUrl = null;
	    Calendar now = null;
		// This check is specifically put in to avoid infinite redirect to the
		// same URL
		if (savedRequest != null
				&& savedRequest.getRedirectUrl().contains(".html")
				&& !savedRequest.getRedirectUrl().contains("index.html")) {
			savedReqUrl = savedRequest.getRedirectUrl();
		} else {
			savedReqUrl = this.savedReqUrl;
		}
		//TODO - Very important
		/*
		 * The above fix is specifically done to avoid the infinite looping issue
		 * in the login history page. This is due to wrong configurations in spring
		 * security and also wrong jsp redirects. This is an interim fix and this has
		 * to be re-factored later
		 */

		this.user.setLoginUser(model, response);
        // Server side show welcome banner check
        Boolean showWelcomeBanner = this.system.showWelcomeBanner();
        if(!showWelcomeBanner){
            sendRedirect(request, response, savedReqUrl);
            return null;
        }
        String banner = "";
        banner = this.system.getWelcomeBanner();
        banner = HtmlUtils.stripNewlineCharacters(banner);
        model.put("welcomeBanner", StringEscapeUtils.escapeJavaScript(banner));
        User loginUser = user.getLoginUser();
		int memberID = loginUser.getMemberID();
		List<MemberLoginHistory> memberLoginHistories = memberLoginHistoryService
				.getLoginHistoryDetails(memberID, loginUser.getTenantID());
		model.put("loginHistories", memberLoginHistories);
		SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(request, null);
		if (sc.isUserInRole("ROLE_AUDIT") && !sc.isUserInRole("ROLE_SUPER")) {
			savedReqUrl = "maintenance.html";
		}

		model.put("savedReqUrl", savedReqUrl);
		Configuration passwdValidityConfig = system.getConfiguration("PASSWORD_VALIDITY_DAYS");
		if (passwdValidityConfig != null) {
			boolean externalAuth = isExternalAuthRole();
		
			try {
				now = memberPasswordHistoryService.getPasswordExpiryDate(passwdValidityConfig, memberID, loginUser.getTenantID());
			} catch(Exception e) {
                logger.error("Error getting password expiry date {}", e.getMessage());
            }
			//External Auth & not SUPER user
			if((!externalAuth || sc.isUserInRole("ROLE_SUPER")) && now != null) {
				model.put("passwdExpDate", now.getTime());
			}

		}

		if (memberLoginHistories.size() != 0) {
			return new ModelAndView(request.getContextPath()
					+ "/loginhistory_html", "model", model);
		}
         sendRedirect(request, response, savedReqUrl);
		return null;

	}

    private boolean isExternalAuthRole() {
	
	Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	UserDetails userDetails = null;
	if (obj instanceof UserDetails) {
		userDetails = (UserDetails) obj;
	}

	List<MemberRoles> authForMemberRoles = system.getToRoles();
	List<String> authForRoles = new LinkedList<String>();
	for (MemberRoles r : authForMemberRoles) {
	    authForRoles.add("ROLE_" + r.getRoleName().toUpperCase());
	}
	boolean externalAuth = false;
	if(userDetails != null) {
	     Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
	    for (GrantedAuthority role : roles) {
	        if (authForRoles.contains(role.getAuthority())) {
	        	externalAuth = true;
	            break;
	        }
	    }
	}
	return externalAuth;
}

    @RequestMapping(value = "/loginhistory.ajax", method = GET)
	public ModelAndView getLoginHistoryDetailsAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Calendar now = null;
		Map<String, Object> model = new HashMap<String, Object>();
		SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(
				request, null);
		this.user.setLoginUser(model, response);
		int memberID = user.getLoginUser().getMemberID();
		List<MemberLoginHistory> memberLoginHistories = memberLoginHistoryService
				.getLoginHistoryDetails(memberID, user.getLoginUser()
						.getTenantID());

		boolean externalAuth = isExternalAuthRole();
		Configuration passwdValidityConfig = system
				.getConfiguration("PASSWORD_VALIDITY_DAYS");
		try {

			now = memberPasswordHistoryService.getPasswordExpiryDate(
					passwdValidityConfig, memberID, user.getLoginUser()
							.getTenantID());
		} catch (Exception e) {
			logger.error("Error getting password expiry date {}",
					e.getMessage());
		}
		// External Auth & not SUPER user
		if ((!externalAuth || sc.isUserInRole("ROLE_SUPER")) && now != null) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
			
			model.put("passwdExpDate", MessageFormat.format(ms.getMessage("your.password.will.expire.on", null, "", lr.resolveLocale(request)), dateFormat.format(now.getTime())));
		}
		model.put("num", memberLoginHistories.size());
		model.put("memberLoginHistory", memberLoginHistories);
		return new ModelAndView("ajax/loginhistory_ajax", "model", model);
	}

    @RequestMapping(value = "/changepassword.html", method = GET)
	public ModelAndView getPasswordChangeHtml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);
		SavedRequest savedRequest = (SavedRequest) request.getSession()
				.getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
		String savedReqUrl = null;
		if (savedRequest != null) {
			savedReqUrl = savedRequest.getRedirectUrl();
		} else {
			savedReqUrl = this.savedReqUrl;
		}
		SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(request, null);
		if (sc.isUserInRole("ROLE_AUDIT") && !sc.isUserInRole("ROLE_SUPER")) {
			savedReqUrl = "maintenance.html";
		}
		model.put("savedReqUrl", savedReqUrl);
		return new ModelAndView(request.getContextPath()
				+ "/changepassword_html", "model", model);

	}

    @RequestMapping(value = "/changepassword.ajax",method = RequestMethod.POST)
    public ModelAndView changePasswordAjax(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        logger.debug("Entering changePasswordAjax() of SettingsController");
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
        String password = ServletRequestUtils.getStringParameter(request,
                "password1", null);
        String password2 = ServletRequestUtils.getStringParameter(request,
                "password2", null);
        List<FieldError> errors = new ArrayList<FieldError>();
        if(password==null || password.isEmpty()|| !password.equalsIgnoreCase(password2)){
            FieldError fe = new FieldError(
                    "password1",
                    ms.getMessage("error", null, "", loc),
                    ms.getMessage("password.cannot.be.empty.or.consist.of.only.whitespace", null, "", loc));
            errors.add(fe);
            if (errors.size() != 0) {
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            return new ModelAndView("ajax/result_ajax", "model", model);
        }
        int memberID = this.user.getLoginUser().getMemberID();

        String currPassword = this.member.getMemberPassword(memberID);
        boolean isValidPassword = true;
        // Validate if the current password is same
        if(currPassword != null && currPassword.contains(":")) {
            //New password mechanism
            // Extract the salt
            String hashedPassword = PasswordHash.createHashWithSalt(password.toCharArray(), currPassword.split(":")[1]);
            if(hashedPassword.equals(currPassword)) {
                isValidPassword = false;
            }
        } else {
            String encodedNewPassword = SHA1.enc(password);
            if(encodedNewPassword.equals(currPassword)) {
                isValidPassword = false;
            }
        }

        if(!isValidPassword) {
            FieldError fe = new FieldError(
                    "password1",
                    ms.getMessage("error", null, "", loc),
                    ms.getMessage("password.cannot.be.same", null, "", loc));
            errors.add(fe);
            if (errors.size() != 0) {
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        User user = this.user.getLoginUser();
        Member member = new Member();
        member.setMemberID(memberID);
        member.setPassword(password);
        member.setUsername(user.getUsername());
        int retVal =0;
        if(user.getUserRole().equalsIgnoreCase("ROLE_SUPER")){
            retVal = this.member.updateSuperPassword(member);
        } else {
            retVal = this.member.updateMemberPassword(member);
        }
        FieldError fe = null;
        if(retVal== MemberManagementResponse.PASSWORD_DOES_NOT_MEET_REQUIREMENTS){
            fe = new FieldError("password1",ms.getMessage("error", null, "", loc),
                    ms.getMessage("password.change.did.not.meet.requirements", null, "", loc));
        }else if (retVal==MemberManagementResponse.PASSWORD_OF_IMPORTED_USER_CAN_NOT_BE_UPDATED){
            fe = new FieldError("password1",ms.getMessage("error", null, "", loc), "The password of imported user cannot be updated");
        }else if (retVal==MemberManagementResponse.PASSWORD_ENCODING_EXCEPTION){
            fe = new FieldError("password1",ms.getMessage("error", null, "", loc),"Password update failed");
        }else if (retVal == 0) {
            fe = new FieldError("password1",ms.getMessage("error", null, "", loc),"Password update failed");
        }
        if(null !=fe){
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }else{
            model.put("success", Boolean.TRUE);
        }
        logger.debug("Exiting changePasswordAjax() of SettingsController");
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    protected void sendRedirect(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException {
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
            if(url.isEmpty()){
              if(request.getContextPath().startsWith("/super")) {
                   url="tenants.html";
              }
               else if(request.getContextPath().startsWith("/admin")) {
                   url="members.html";
              }
            }
            url = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + request.getContextPath()+ "/" + url;
		}
		logger.info("Redirect URL ->" + response.encodeRedirectURL(url));
		response.sendRedirect(response.encodeRedirectURL(url));
	}

    protected boolean hideForgotPasswordGlobally() throws Exception {
        String showforgotpassword = "YES";
		try {
			Configuration config = system.getConfiguration("SHOW_FORGOT_PASSWORD_LINK");
            if(null!=config){
               showforgotpassword = config.getConfigurationValue();
            }
		} catch (Exception ignored) {
		}
        if(null == showforgotpassword) {
            return false;
        }
        else if(showforgotpassword.isEmpty()){
            return false;
        }
        else if(showforgotpassword.equalsIgnoreCase("NEVER")){
           return  true;
        }
        else{
           return false;
        }

    }

    protected  abstract void populateHideForgetPasswordFeature(Map<String, Object> model) throws Exception;

    protected abstract String getViewName();
    
    protected abstract String languagePropertyName();

}