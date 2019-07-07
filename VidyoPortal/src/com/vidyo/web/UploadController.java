package com.vidyo.web;

import com.vidyo.bo.EndpointUpload;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.util.ValidatorUtil;
import com.vidyo.service.EndpointUploadService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import com.vidyo.service.SecurityServiceImpl;
import com.vidyo.utils.PortalUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;

import static com.vidyo.web.SettingsController.FAILURE;
import static com.vidyo.web.SettingsController.SUCCESS;
import static org.springframework.web.bind.ServletRequestUtils.getStringParameter;

public abstract class UploadController {

    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    private IUserService user;

    @Autowired
    protected ISystemService system;

    @Autowired
    private CookieLocaleResolver lr;

    @Autowired
    protected EndpointUploadService endpointupload;

    @Autowired
    protected ReloadableResourceBundleMessageSource ms;

    public abstract ModelAndView uploadHtml(String parentDirectory, HttpServletRequest request, HttpServletResponse response) throws Exception;

    @RequestMapping(value = "/upload.ajax",method = RequestMethod.GET)
    public ModelAndView getSuperUploadAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int start = ServletRequestUtils.getIntParameter(request, "start", 0);
        int limit = ServletRequestUtils.getIntParameter(request, "limit", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        Long num = 0L;
        List<EndpointUpload> list = null;

        num = getCountEndpointUpload();
        if (num > 0) {
            list = getEndpointUploads(start, limit);
        }

        model.put("num", num);
        model.put("list", list);

        return new ModelAndView("ajax/upload_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/saveupload.ajax",method = RequestMethod.POST)
    public ModelAndView saveUploadInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();
        int endpointUploadId = 0;


        EndpointUpload client = new EndpointUpload();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(client);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        client.setTenantID(getContextId());

        boolean externalURL = this.endpointupload.isUploadModeExternal(client.getTenantID());

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }
        if (!externalURL){
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            MultipartFile multipartFile = multipartRequest.getFile("client-path");

            File uploadedTempFile = new File(getUploadTempDir() + multipartFile.getOriginalFilename());

            client.setEndpointUploadFile(multipartFile.getOriginalFilename());
            client.setEndpointUploadTime((int)new Date().getTime());
            boolean goodFileName = this.endpointupload.setEndpointUploadType(client);
            if (!goodFileName) {
                FieldError fe = new FieldError("client-path", "client-path", ms.getMessage("incorrect.installer.file.name", null, "", loc));
                errors.add(fe);
            } else {
                multipartFile.transferTo(uploadedTempFile);
                if (PortalUtils.isValidEndpointFile(uploadedTempFile)) {
                    //replacing  file
                    if( 0 == getContextId()) {
                        PortalUtils.moveWebappsFileSecurely(uploadedTempFile, new File(getUploadDir() + uploadedTempFile.getName()));
                        endpointUploadId = this.endpointupload.addEndpointUploadSuper(client, externalURL, true);
                    } else {
                        PortalUtils.moveWebappsFileSecurelyWithGroupWrite(uploadedTempFile, new File(getUploadDir() + uploadedTempFile.getName()));
                        endpointUploadId = this.endpointupload.addEndpointUpload(client.getTenantID(), client, true);
                    }
                } else {
                    FieldError fe = new FieldError("client-path", "client-path", "Invalid installer.");
                    errors.add(fe);
                }
                FileUtils.deleteQuietly(uploadedTempFile);
            }
        } else {
            // validate external URL
            if (!ValidatorUtil.isValidHTTPHTTPSURL(client.getEndpointUploadFile())) {
                FieldError fe = new FieldError("installExternalURL", "installExternalURL", ms.getMessage("invalid.external.url", null, "", loc));
                errors.add(fe);
            }

            String version = client.getEndpointUploadVersion();
            if (StringUtils.isEmpty(version) || version.replaceAll("[a-zA-Z0-9._]", "").length() > 0){
                FieldError fe = new FieldError("endpointUploadVersion", "endpointUploadVersion", ms.getMessage("invalid.endpoint.version", null, "", loc));
                errors.add(fe);
            }

            String platform = client.getEndpointUploadType();
            if (StringUtils.isEmpty(platform)){
                FieldError fe = new FieldError("platform", "platform", ms.getMessage("invalid.endpoint.uploadtype", null, "", loc));
                errors.add(fe);
            }

            if (errors.size() == 0) {
                if(0 == getContextId()) {
                    endpointUploadId = this.endpointupload.addEndpointUploadSuper(client, externalURL, true);
                } else {
                    endpointUploadId = this.endpointupload.addEndpointUpload(client.getTenantID(), client, true);
                }
            }
        }

        model.put("success", errors.size() != 0 ? Boolean.FALSE:Boolean.TRUE);
        if (errors.size() > 0){
            model.put("fields", errors);
        }
        system.auditLogTransaction("Endpoint Upload saved by " + (getContextId() == 0 ? "Super" : "Admin"),
                "endpointUploadId:"+endpointUploadId,
                errors.size() != 0 ? FAILURE:SUCCESS);
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/uploadmode.ajax", method = RequestMethod.GET)
    public ModelAndView getSuperUploadModeAjax (HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        String uploadMode = "VidyoPortal";
        if (isUploadModeExternal()){
            uploadMode = "External";
        }
        model.put("uploadMode", uploadMode);
        return new ModelAndView("ajax/uploadmode_ajax", "model", model);
    }

    @RequestMapping(value = "/checkendpointexistence.ajax",method = RequestMethod.GET)
    public ModelAndView checkSuperUploadExistence(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String endpointFileName = getStringParameter(request, "endpointFileName", "");
        String endpointUploadType = getStringParameter(request, "endpointUploadType", null);
        String endpointUploadVersion = getStringParameter(request, "endpointUploadVersion", null);

        EndpointUpload existingEndpointUpload = null;
        boolean endpointExist = false;
        existingEndpointUpload = endpointupload.getEndpointUploadForFileName(getContextId(), endpointFileName,
                endpointUploadType, endpointUploadVersion);

        if(existingEndpointUpload != null) {
            endpointExist = true;
        }

        model.put("success", endpointExist);
        return new ModelAndView("ajax/endpointexistence_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/removecustomizedimagelogo.ajax", method = RequestMethod.POST)
    public ModelAndView removeSuperCustomizedImageLogo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
        String removableLogoFileName = removeCustomizedPortalLogo();
        if((removableLogoFileName!=null) && (removableLogoFileName.length()!=0)) {
            if(removableLogoFileName.contains("/")) {
                removableLogoFileName = removableLogoFileName.substring(removableLogoFileName.lastIndexOf("/") + 1);
            }

            FileUtils.deleteQuietly(new File(getUploadDir() + removableLogoFileName));
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/removedesktopcustomization.ajax", method = RequestMethod.POST)
    public ModelAndView superRemoveDesktopCustomization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        FileUtils.deleteQuietly(new File("/opt/vidyo/portal2/customization/" + getContextId() + "_desktop.zip"));
        FileUtils.deleteQuietly(new File("/opt/vidyo/portal2/customization/"  + getContextId() + "_desktop.sha1"));

        model.put("success", Boolean.TRUE);
        system.auditLogTransaction(getName() + "DesktopEndpointCustomizationRemove", "", SUCCESS);

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/customizedimagelogo.ajax", method = RequestMethod.GET)
    public ModelAndView getCustomizedImageLogoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String name = StringUtils.trimToEmpty(getCustomizedLogoFileName());
        if(StringUtils.isNotBlank(name) ) {
            if(name.contains("/")) {
                name = name.substring(name.lastIndexOf("/") + 1);
            }
            File logoFile = new File(getUploadDir() + name);
            if(logoFile.exists())
                model.put("vdname", "/" + getName() + "/upload/" + logoFile.getName());
        }

        return new ModelAndView("ajax/customizedlogo_ajax", "model", model);
    }

    protected abstract Long getCountEndpointUpload();

    protected abstract List<EndpointUpload> getEndpointUploads(int start, int limit);

    protected abstract String getUploadTempDir();
    
    protected abstract String getUploadDir();

    protected abstract void performDeleteEndpoint(boolean isExternalURL, int endpointUploadID, EndpointUpload uploaded);

    protected abstract boolean isUploadModeExternal();

    protected abstract int getContextId();

    protected abstract String removeCustomizedPortalLogo();

    protected abstract String getName();

    protected abstract String getCustomizedLogoFileName();

    protected ModelAndView uploadHtmlInternal(String parentDirectory, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME + "private/PRIVILEGED_MODE");
        model.put("privilegedMode", (privModeFileHandle != null && privModeFileHandle.exists()));
        return new ModelAndView(parentDirectory + "/upload_html", "model", model);
    }

    protected ModelAndView deleteUploadInternal(HttpServletRequest request, HttpServletResponse response, AppType appType) throws Exception {
        int endpointUploadID = ServletRequestUtils.getIntParameter(request, "endpointUploadID", 0);
        boolean externalURL = this.endpointupload.isUploadModeExternal(appType == AppType.SUPER ? 0 : TenantContext.getTenantId());

        EndpointUpload uploaded = this.endpointupload.getEndpointUpload(endpointUploadID);
        performDeleteEndpoint(externalURL, endpointUploadID, uploaded);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("success", endpointUploadID > 0 ? Boolean.TRUE:Boolean.FALSE);
        system.auditLogTransaction("Endpoint deleted by " + appType,"endpointUploadID="+endpointUploadID, endpointUploadID > 0 ? SUCCESS:FAILURE);
        return new ModelAndView("ajax/result_ajax", "model", model);
    }
}
