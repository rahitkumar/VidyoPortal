package com.vidyo.web.portal;

import com.vidyo.bo.*;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.*;
import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.TCAndPrivacyPolicyUtils;
import com.vidyo.utils.UserAgentUtils;
import com.vidyo.utils.VendorUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.UriBuilder;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class DownloadInstallerController {

    @Autowired
    protected IUserService user;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private EndpointUploadService endpoint;

    @Autowired
    private AppStoreConfig appStoreConfig;

    @Value("${upload.path}")
    private String upload_path;

    @Autowired
    private IRoomService room;

    @Autowired
    protected ISystemService system;

    @RequestMapping(value = "/download.html", method = GET)
    public ModelAndView downloadInstaller(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);
        boolean isUVD = true;
        String forceNeoFlag = ServletRequestUtils.getStringParameter(request, "forceNeo", "false");
        model.put("forceNeoFlag", forceNeoFlag);
        TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(TenantContext.getTenantId());
        boolean isExternal = tenantConfig.getEndpointUploadMode() != null && tenantConfig.getEndpointUploadMode().equalsIgnoreCase("External");
        EndpointUpload endpoint = this.endpoint.getActiveEndpointForType(request);
        if (endpoint != null) {
            String installer = null;
            // Check if the end point is for external URL
            if (!isExternal) {
                UriBuilder uriBuilder = UriBuilder.fromPath("")
                        .path(upload_path).path(endpoint.getEndpointUploadFile());
                if(endpoint.getEndpointUploadFile().contains("TAG_VD_3")) {
                    uriBuilder.queryParam("t", "p");
                }
                installer = uriBuilder.build().toString();
            } else {
                installer = this.endpoint.getCDNInstallerURLWithParam(endpoint.getEndpointUploadFile(),
                        request.getScheme(), request.getHeader("host"), null);
            }
            model.put("installer", installer);
            if (!installer.contains("TAG_VD_3")) {
                isUVD = false;
            }
        } else {
            model.put("installer", "no_installer");
        }

        String page = "download_html";

        // if mobile
        StringBuffer hostUrl = new StringBuffer();
        hostUrl.append(request.getHeader("host"));
        hostUrl.append(request.getContextPath());
        hostUrl.append("/");

        if (request.isSecure()) {
            hostUrl.append("&secure=yes");
        }

        if (tenantService.isTenantNotAllowingGuests()) {
            hostUrl.append("&noguest");
        }

        model.put("hostUrl", hostUrl.toString());
        model.put("guestJoin", false);

        String roomKey = ServletRequestUtils.getStringParameter(request, "roomKey", "");
        model.put("key", roomKey);
        model.put("host", request.getScheme() + "://" + request.getServerName());

        String joinURL = room.getRoomURL(system, request.getScheme(), request.getHeader("host"), roomKey);
        if(tenantService.isTenantNotAllowingGuests()){
            joinURL += "&noguest";
        }


        model.put("roomURLFormated", joinURL);
        prepareLogo(model);

        String userAgent = request.getHeader("user-agent");
        if (userAgent != null) {
            Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
            int mode = tenant.getMobileLogin();
            if (UserAgentUtils.isBrowserEdge(userAgent)) {
                model.put("browser", "edge"); // also reports itself as chrome so do this first
            } else if (UserAgentUtils.isBrowserChrome(userAgent)) {
                model.put("browser", "chrome");
            } else if (UserAgentUtils.isBrowserFirefox(userAgent)) {
                model.put("browser", "firefox");
            } else if (UserAgentUtils.isBrowserSafari(userAgent)) {
                model.put("browser", "safari");
            } else if (UserAgentUtils.isBrowserIE(userAgent)) {
                model.put("browser", "ie");
            }
            if (!isUVD && (UserAgentUtils.isWindows(userAgent) || UserAgentUtils.isMac(userAgent))) {
                page = "download_neo_html";
                model.put("privacyUrl", TCAndPrivacyPolicyUtils.getPrivacyPolicyUrl(system, tenantService));
                model.put("termsUrl", TCAndPrivacyPolicyUtils.getTermsAndConditionsUrl(system, tenantService));
            } else if (UserAgentUtils.isIphone(userAgent) && mode == 1) {  //VPTL-7529 - Neo landing pages for download page - start
                page = "ios/i_guest_mobile_html";
            } else if (UserAgentUtils.isIPad(userAgent)  && mode == 1) {
                page = "ios/i_guest_tablet_html";
            } else if (UserAgentUtils.isAndroid(userAgent) && UserAgentUtils.isMobile(userAgent) && mode == 1) {
                page = "android/a_guest_mobile_html";
            } else if ((UserAgentUtils.isAndroid(userAgent) || UserAgentUtils.isKindle(userAgent)) && mode == 1) {
                page = "android/a_guest_tablet_html";
            } else if (UserAgentUtils.isSupportedTouchDevice(userAgent) && mode == 2){
                model.put("roomKey", null);
                if(UserAgentUtils.isIOS(userAgent)) {
                    model.put("neoMobileDownloadLink", appStoreConfig.getAppleStoreNeomobile());
                    model.put("isAndroid", false);
                    if (UserAgentUtils.isIphone(userAgent)) {
                        page = "neo_mobile_landing_html";
                    } else if (UserAgentUtils.isIPad(userAgent)){
                        page = "neo_tablet_landing_html";
                    }
                } else if (UserAgentUtils.isAndroid(userAgent)){
                    model.put("neoMobileDownloadLink", appStoreConfig.getGoogleStoreNeomobile());
                    model.put("isAndroid", true);
                    if (UserAgentUtils.isMobile(userAgent)) {
                        page = "neo_mobile_landing_html";
                    } else if (UserAgentUtils.isAndroid(userAgent) || UserAgentUtils.isKindle(userAgent)){
                        page = "neo_tablet_landing_html";
                    }
                } //VPTL-7529 - Neo landing pages for download page - end
            } else if (UserAgentUtils.isSupportedTouchDevice(userAgent) && mode == 0){
                page = "neo_mobile_disabled_html";
            } else if (UserAgentUtils.isMac(userAgent)) {
                page = "download_mac_html";
            } else if (UserAgentUtils.isLinux(userAgent)) {
                String installer_path = null;
                if (!isExternal) {
                    installer_path = request.getScheme()+"://"+request.getHeader("host")+request.getContextPath()+this.upload_path;
                } else {
                    installer_path = "";
                }
                Map<String, String> osBitVerMap = new HashMap<>();
                osBitVerMap.put("U", "deb32bit");
                osBitVerMap.put("X", "deb64bit");
                osBitVerMap.put("S", "rpm32bit");
                osBitVerMap.put("T", "rpm64bit");
                for (String osBitVerKey : osBitVerMap.keySet()){
                    endpoint = this.endpoint.getActiveEndpointForType(osBitVerKey); // deb/32bit
                    if (endpoint != null) {
                        model.put(osBitVerMap.get(osBitVerKey), installer_path
                                + (isExternal ?
                                this.endpoint.getCDNInstallerURLWithParam(endpoint.getEndpointUploadFile(),
                                        request.getScheme(), request.getHeader("host"), null)
                                : endpoint.getEndpointUploadFile()));
                    }
                }
                page = "download_linux_html";
            } else if (UserAgentUtils.isWindows(userAgent)) {
                page = "download_pc_html";
            }
        }

        // https://jira.vidyo.com/browse/VPTL-7320 - changes for removing the scriplets from JSP.
        model.put("isVidyoCloud", VendorUtils.isVidyoCloud());

        return new ModelAndView(page, "model", model);
    }

    @RequestMapping(value = "/", method = GET)
    public ModelAndView getIndexAlias(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getIndexHtml(request, response);
    }

    @RequestMapping(value = "/index.html", method = GET)
    public ModelAndView getIndexHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String samlUrl = "/saml/login/";
        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);

        Integer tenantId = TenantContext.getTenantId();

        com.vidyo.bo.authentication.Authentication authType = system.getAuthenticationConfig(tenantId).toAuthentication();

        boolean isSamlAuth = authType instanceof SamlAuthentication;

        // Old VidyoRoom will have SWB in User-Agent. we don't authenticate VidyoRoom against SAML
        String userAgent = request.getHeader("User-Agent");
        boolean isVidyoRoom = UserAgentUtils.isVidyoRoom(userAgent);

        String roomKey = ServletRequestUtils.getStringParameter(request, "roomKey", null);

        boolean webRTCForUsers = false;
        Configuration vidyoNeoWebRTUserConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
        if (vidyoNeoWebRTUserConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
                && vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1")) {
            TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(tenantId);
            webRTCForUsers = tenantConfiguration.getVidyoNeoWebRTCUserEnabled() == 1;
        }

        EndpointUpload endpoint = this.endpoint.getActiveEndpointForType(request);
        boolean noNeoInstaller = true;
        if (endpoint != null) {
            String installerName = endpoint.getEndpointUploadFile();
            if (installerName != null && installerName.contains("TAG_VD_3")) {
                noNeoInstaller = true; // UVD
            } else {
                noNeoInstaller = false; // if not UVD, must be Neo (hopefully)
            }
        }

        Tenant tenant = tenantService.getTenant(tenantId);
        String webrtcURL = tenant.getTenantWebRTCURL();
        boolean urlExists = StringUtils.isNotBlank(webrtcURL);
        prepareLogo(model);
        
        // refer the WebRTC.java junit for more detailed understanding
        if (!isVidyoRoom && webRTCForUsers && urlExists && !UserAgentUtils.isSupportedTouchDevice(userAgent)&&
                ((UserAgentUtils.browserSupportsWebRTC(userAgent) && ((UserAgentUtils.isMac(userAgent) || UserAgentUtils.isWindows(userAgent)) && noNeoInstaller)) ||
                        (UserAgentUtils.browserSupportsWebRTC(userAgent) && (!(UserAgentUtils.isMac(userAgent) || UserAgentUtils.isWindows(userAgent))))
                )) {
            model.put("url", webrtcURL + "/webrtc/conf.htm?portal=" + request.getHeader("host"));
        } else if (!isVidyoRoom && isSamlAuth && (roomKey == null || "".equals(roomKey))) {
            //VPTL-7722 - redirecto to neo download page if the user agent is mobile.
            if (UserAgentUtils.isSupportedTouchDevice(userAgent) && isSamlAuth) {
                String page = "";
                int mode = tenant.getMobileLogin();
                if (mode == 2) {
                    model.put("roomKey", null);
                    if (UserAgentUtils.isIOS(userAgent)) {
                        model.put("neoMobileDownloadLink", appStoreConfig.getAppleStoreNeomobile());
                        model.put("isAndroid", false);
                        if (UserAgentUtils.isIphone(userAgent)) {
                            page = "neo_mobile_landing_html";
                        } else if (UserAgentUtils.isIPad(userAgent)) {
                            page = "neo_tablet_landing_html";
                        }
                    } else if (UserAgentUtils.isAndroid(userAgent)) {
                        model.put("neoMobileDownloadLink", appStoreConfig.getGoogleStoreNeomobile());
                        model.put("isAndroid", true);
                        if (UserAgentUtils.isMobile(userAgent)) {
                            page = "neo_mobile_landing_html";
                        } else if (UserAgentUtils.isAndroid(userAgent) || UserAgentUtils.isKindle(userAgent)) {
                            page = "neo_tablet_landing_html";
                        }
                    }
                    return new ModelAndView(page, "model", model);
                } else if (mode == 0) {
                    page = "neo_mobile_disabled_html";
                    return new ModelAndView(page, "model", model);
                }
            }
            response.sendRedirect(samlUrl);
            return null;
        } else {
            String initialUrl = "/download.html"; // was /flex.html

            HttpSession sess = request.getSession();
            String session_lang = (String)sess.getAttribute("session_lang");
            if (session_lang == null) {
                String lang = ServletRequestUtils.getStringParameter(request, "lang", "");
                if (lang.equalsIgnoreCase("")) {
                    String userlang = this.user.getUserLang().getLangCode();
                    // Since user is Guest and no need to go with Cookie resolver
                    // It is because Cookie resolver will always going to return a language
                    // even if no cookie exists. The default language is 'en' by Cookie resolver

                    // get language and see if supported
	                /*CookieLocaleResolver cookieLocaleResolver = (CookieLocaleResolver) RequestContextUtils.getLocaleResolver(request);
	                if (cookieLocaleResolver != null) {
	                    if (LangUtils.isPortalSupportedLang(cookieLocaleResolver.resolveLocale(request))) {
	                        String userlang2 = cookieLocaleResolver.resolveLocale(request).getLanguage();
	                        if ("zh".equals(userlang2)) {
	                            userlang = cookieLocaleResolver.resolveLocale(request).toString();
	                        } else if (userlang2 != null) {
	                            userlang = userlang2;
	                        }
	                    }
	                }*/
                    initialUrl = initialUrl + "?lang=" + userlang;
                }
            }

            if (!StringUtils.isBlank(roomKey)) {
                if (initialUrl.contains("?")) {
                    initialUrl = initialUrl + "&roomKey=" + roomKey;
                } else {
                    initialUrl = initialUrl + "?roomKey=" + roomKey;
                }
            }
            //append the additional request parameters sent.
            Map<String, String[]> reqParamMap = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : reqParamMap.entrySet()) {
                if(!entry.getKey().equalsIgnoreCase("key")) {
                    //Assumption here is that for every key there will be only one value in the array.
                    initialUrl = initialUrl.concat("&"+entry.getKey() + "=" + entry.getValue()[0]);
                }
            }
            model.put("url", initialUrl);
        }
        return new ModelAndView("index_html", "model", model);
    }

	private void prepareLogo(Map<String, Object> model) {
		String logo = PortalUtils.prepareLogo(this.system);
        model.put("logoUrl", logo);
	}
}
