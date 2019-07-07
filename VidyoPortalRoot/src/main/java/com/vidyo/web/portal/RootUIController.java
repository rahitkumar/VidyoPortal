package com.vidyo.web.portal;

import com.vidyo.bo.*;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import com.vidyo.service.authentication.saml.SamlAuthenticationService;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.utils.TCAndPrivacyPolicyUtils;
import com.vidyo.utils.UserAgentUtils;
import com.vidyo.utils.VendorUtils;
import com.vidyo.web.UtilController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.UriBuilder;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class RootUIController {

    private static final Logger logger = LoggerFactory.getLogger(UtilController.class);

    @Autowired
    protected IUserService user;

    @Autowired
    private SamlAuthenticationService samlAuthenticationService;

    @Autowired
    protected ISystemService system;

    @RequestMapping(value = "/sess.html", method = GET)
    public ModelAndView getSessHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);
        return new ModelAndView("sess_html", "model", model);
    }

    @RequestMapping(value = "/remote.html", method = GET)
    public ModelAndView getRemoteHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);
        model.put("urlval", "vdRemote");
        return new ModelAndView("remote_html", "model", model);
    }

    @RequestMapping(value = "/panremote.html", method = GET)
    public ModelAndView getPanaromaRemoteHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);
        model.put("urlval", "vdRemote");
        model.put("urlval1", "panRemote");
        return new ModelAndView("remote_html", "model", model);
    }

    /**
     * *
     * END DSCP Config
     */
    @RequestMapping(value = "/saml/sp-metadata.html", method = GET)
    public ModelAndView getSPMetadata(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        int tenantId = TenantContext.getTenantId();
        SamlAuthentication samlAuth = samlAuthenticationService.getSamlAuthenticationForTenant(tenantId);
        if(samlAuth != null) {
            try {
                String serviceProviderMetadata = samlAuthenticationService.generateServiceProviderMetadata(tenantId, samlAuth);
                model.put("output", serviceProviderMetadata);
            } catch (Exception e) {
                logger.error("Error during getting sp metadata: " + e.getMessage());
                throw e;
            }
        } else {
            response.setStatus(404);
            return new ModelAndView("404", "model", null);
        }

        return new ModelAndView("sp_metadata_html", "model", model);
    }

    /**
     * this is for wget for VR2conf
     */
    @RequestMapping(value = "/getwelcomebanner.html", method = GET)
    public void getWelcomeBannerText(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String welcomeBanner = "";
        Boolean showWelcomeBanner = this.system.showWelcomeBanner();
        if(showWelcomeBanner){
            welcomeBanner = this.system.getWelcomeBanner();
        }
        PrintWriter out = response.getWriter();
        out.print(welcomeBanner);
    }

    /**
     * this is for wget for VR2conf
     */
    @RequestMapping(value = "/getloginbanner.html", method = GET)
    public void getLoginBannerBannerText(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String loginBanner = "";
        Boolean showLoginBanner = this.system.showLoginBanner();
        if(showLoginBanner){
            loginBanner = this.system.getLoginBanner();
        }
        PrintWriter out = response.getWriter();
        out.print(loginBanner);
    }

    /**
     * When current user send request of http://hostname/logo.html, current tenant logo(swf) file content will
     * be responded back.
     * @return current tenant logo file's binary content
     */
    @RequestMapping(value = "/logo.html", method = RequestMethod.GET)
    public ModelAndView getLogoHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        this.user.setLoginUser(model, response);

        String logopath = "logo.swf"; //default
        try {
            String path = this.system.getCustomizedLogoName();
            if((path != null) && (path.length() > 0) && (new File(request.getServletContext().getRealPath(path))).exists() )
                logopath = path;
            else {
                path = this.system.getCustomizedDefaultUserPortalLogoName();
                if((path != null) && (path.length() > 0) && (new File(request.getServletContext().getRealPath(path))).exists() )
                    logopath = path;
            }

        } catch (Exception ignored) {
        }

        //start download
        File downloadFile = new File(request.getServletContext().getRealPath(logopath));
        long fileSize = downloadFile.length();
        response.reset();
        response.setHeader("Pragma", "public");
        response.setHeader("Expires","0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Cache-Control", "public");
        response.setHeader("Content-Description", "File Transfer");
        //comment out to erase the file download dialog to let browser act in default behavior
        //uncomment if you want to allow browser to download it
        //response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName()  + "\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
        //response.setContentLength((int)fileSize);
        response.addHeader("Content-Length", Long.toString(fileSize));
        response.setContentType("application/x-shockwave-flash;charset=UTF-8");

        ServletOutputStream outPut = response.getOutputStream();

        byte[] bbuf = new byte[4096];
        int length = -1;
        DataInputStream in = new DataInputStream(new FileInputStream(downloadFile));
        while (((length = in.read(bbuf)) != -1)) {
            outPut.write(bbuf,0,length);
        }
        in.close();
        outPut.flush();
        outPut.close();
        return null;
    }
}
