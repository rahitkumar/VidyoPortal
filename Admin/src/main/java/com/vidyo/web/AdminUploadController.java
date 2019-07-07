package com.vidyo.web;

import com.vidyo.bo.EndpointUpload;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.utils.PortalUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.vidyo.web.SettingsController.FAILURE;
import static com.vidyo.web.SettingsController.SUCCESS;

@Controller
public class AdminUploadController extends UploadController {

    @Value( "${upload.temp.dir}" )
    private String updloadTempDir;
    
    @Value("${upload.dir.admin}")
    protected String uploadDirAdmin;

    @Override
    @RequestMapping(value = "/upload.html", method = RequestMethod.GET)
    public ModelAndView uploadHtml(String parentDirectory, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        return super.uploadHtmlInternal("admin", request, response);
    }

    @RequestMapping(value = "/securedmaint/deleteupload.ajax", method = RequestMethod.POST)
    public ModelAndView deleteUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.deleteUploadInternal(request, response, AppType.ADMIN);
    }

    @RequestMapping(value = "/securedmaint/activateupload.ajax", method = RequestMethod.POST)
    public ModelAndView setAdminActiveUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int endpointUploadID = ServletRequestUtils.getIntParameter(request, "endpointUploadID", 0);
        EndpointUpload endpoint = this.endpointupload.getEndpointUpload(endpointUploadID);
        Map<String, Object> model = new HashMap<String, Object>();
        this.endpointupload.setActiveUpload(TenantContext.getTenantId(), endpoint);
        model.put("success", endpointUploadID > 0 ? Boolean.TRUE:Boolean.FALSE);
        system.auditLogTransaction("Endpoint activated by Admin.","endpointUploadID="+endpointUploadID, endpointUploadID > 0 ? SUCCESS:FAILURE);
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/savecustomizedimagelogo.ajax", method = RequestMethod.POST)
    public ModelAndView saveAdminCustomizedImageLogo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
        //uploading
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("VDLogoArchive");
        String mimeType = multipartFile.getContentType();

        //verify the file extension type
        String fn = multipartFile.getOriginalFilename().toLowerCase();
        if(!PortalUtils.isValidImageFileName(fn)) {
            FieldError fe = new FieldError("CustomizedLogo", ms.getMessage("incorrect.file.type", null, "", loc), "File name must be *.gif, *.png or *.jpg");
            errors.add(fe);
        } else if (!PortalUtils.isValidImageMimeType(mimeType, fn)) {
            FieldError fe = new FieldError("CustomizedLogo", ms.getMessage("incorrect.file.type", null, "", loc), "File must be a gif, png or jpeg");
            errors.add(fe);
        } else {
            String ext = fn.substring(fn.lastIndexOf('.')+1);
            String tenantId = ""+this.system.getTenantId();
            fn = tenantId+"_"+System.currentTimeMillis()+"_logo." + ext;

            // upload to temp directory
            File uploadedTempFile = new File(getUploadTempDir() + fn);
            multipartFile.transferTo(uploadedTempFile);

            if (!PortalUtils.isValidImageFile(uploadedTempFile)) {
                logger.info("Invalid mime-type for image upload.");
                FileUtils.deleteQuietly(uploadedTempFile);
                FieldError fe = new FieldError("CustomizedLogo", ms.getMessage("incorrect.file.type", null, "", loc), "File must be a gif, png or jpeg");
                errors.add(fe);
            } else {
                //delete old image
                String removableLogoFileName = this.system.removeCustomizedImageLogo();
                if (removableLogoFileName != null) {
                    if (removableLogoFileName.contains("/")) {
                        // backward compatibility
                        removableLogoFileName = removableLogoFileName
                                .substring(removableLogoFileName
                                        .lastIndexOf("/") + 1);
                        //VPTL-8266 - Bug fix - find last index of slash and then add 1.
                    }
                    // Delete the file if it exists
                    FileUtils.deleteQuietly(new File(uploadDirAdmin + removableLogoFileName));
                }

                PortalUtils.moveWebappsFileSecurely(uploadedTempFile, new File(uploadDirAdmin + uploadedTempFile.getName()));
            }
            if(errors.size() > 0) {
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
                return new ModelAndView("ajax/result_ajax", "model", model);
            }

            if(-1 == this.system.saveCustomizedImageLogo(this.uploadDirAdmin + fn)){
                FieldError fe = new FieldError("CustomizedLogo", ms.getMessage("update.failed", null, "", loc), ms.getMessage("failed.to.save.customized.logo", null, "", loc));
                errors.add(fe);
            }
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

    @RequestMapping(value = "/savevidyoweb.ajax", method = RequestMethod.POST)
    public ModelAndView saveVidyoWebSettingsAdminAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("Entering saveVidyowebSettingsAdminAjax() of SettingsController");
        Locale loc = LocaleContextHolder.getLocale();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("success", Boolean.TRUE);
        List<FieldError> errors = new ArrayList<FieldError>();

        String enableVidyoWeb = ServletRequestUtils.getStringParameter(request, "enableVidyoWeb", null);
        String zincServer = ServletRequestUtils.getStringParameter(request, "zincServer", "");
        boolean zincServerEnabled = "enabled".equals(ServletRequestUtils.getStringParameter(request, "zincEnabled", "disabled"));

        String cleanZincURL = StringUtils.trimToEmpty(zincServer);
        if (enableVidyoWeb != null) {
            Integer tenantId = TenantContext.getTenantId();
            if (enableVidyoWeb.equalsIgnoreCase("enabled")) {
                if (!system.isVidyoWebEnabledByAdmin(tenantId)) {
                    system.saveVidyoWebEnabledForAdmin(tenantId, true);
                    system.auditLogTransaction("VidyoWeb enabled.", "", SUCCESS);
                }
                if (zincServerEnabled || !("".equals(cleanZincURL))) {
                    try {
                        URL zincURL = new URL(cleanZincURL);
                        String protocol = zincURL.getProtocol();
                        if (StringUtils.isEmpty(protocol)) {
                            protocol = "http";
                        } else {
                            protocol = protocol.toLowerCase();
                            if (!("http".equals(protocol) || "https".equals(protocol))) {
                                protocol = "http";
                            }
                        }
                        String host = zincURL.getHost();
                        int port = zincURL.getPort();
                        if (port < 0) {
                            cleanZincURL = protocol + "://" + host;
                        } else {
                            if (port != zincURL.getDefaultPort()) {
                                cleanZincURL = protocol + "://" + host + ":" + port;
                            } else {
                                cleanZincURL = protocol + "://" + host;
                            }
                        }
                    } catch (MalformedURLException e) {
                        model.put("success", Boolean.FALSE);
                        FieldError fe = new FieldError("Zinc Config", "Zinc Server",
                                ms.getMessage("zinc.server.is.invalid.e.g.http.hostname.8080", null, "", loc));
                        errors.add(fe);
                        model.put("fields", errors);
                        if (zincServerEnabled) {
                            system.auditLogTransaction("Web RTC (VidyoWeb) for Guest enabled.", "URL- "+ cleanZincURL, FAILURE);
                        } else {
                            system.auditLogTransaction( "Web RTC (VidyoWeb) for Guest disabled.", "", FAILURE);
                        }
                        return new ModelAndView("ajax/result_ajax", "model", model);
                    }
                }

                boolean isZincSettingsChanged = zincServerEnabled != system.isZincEnabledForTenant(tenantId);
                boolean isZincURLChanged = !cleanZincURL.equalsIgnoreCase(system.getZincServerForTenant(tenantId));

                if (isZincSettingsChanged || isZincURLChanged) {
                    system.saveZincSettings(tenantId, cleanZincURL, zincServerEnabled);
                }

                if (zincServerEnabled) {
                    if (isZincSettingsChanged || isZincURLChanged) {
                        system.auditLogTransaction("Web RTC (VidyoWeb) for Guest enabled.", "URL- "+ cleanZincURL, SUCCESS);
                    }
                } else {
                    if (isZincSettingsChanged) {
                        system.auditLogTransaction( "Web RTC (VidyoWeb) for Guest disabled.", "", SUCCESS);
                    }
                }

            } else {
                if (system.isVidyoWebEnabledByAdmin(tenantId)) {
                    system.saveVidyoWebEnabledForAdmin(tenantId, false);
                    system.auditLogTransaction("VidyoWeb disabled.", "", SUCCESS);
                }
            }
        }

        logger.debug("Exiting saveVidyowebSettingsAdminAjax() of SettingsController");
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @Override
    protected void performDeleteEndpoint(boolean externalURL, int endpointUploadID, EndpointUpload uploaded) {
        this.endpointupload.deleteEndpointUpload(endpointUploadID, TenantContext.getTenantId());
        if (!externalURL) {
            Long ref = this.endpointupload.getRefNumberForEndpointFileName(uploaded);
            if (ref == 0) {
                FileUtils.deleteQuietly(new File(uploadDirAdmin + uploaded.getEndpointUploadFile() ));
            }
        }
    }

    @Override
    protected boolean isUploadModeExternal() {
        return endpointupload.isUploadModeExternal(TenantContext.getTenantId());
    }

    @Override
    protected int getContextId() {
        return TenantContext.getTenantId();
    }

    @Override
    protected String removeCustomizedPortalLogo() {
        return system.removeCustomizedImageLogo();
    }

    @Override
    protected String getName() {
        return "admin";
    }

    @Override
    protected String getCustomizedLogoFileName() {
        return this.system.getCustomizedImageLogoName();
    }

    public Long getCountEndpointUpload(){
        return endpointupload.getCountEndpointUpload();

    }

    public List<EndpointUpload> getEndpointUploads(int start, int limit){
        return endpointupload.getEndpointUploads(start, limit);
    }

    protected String getUploadTempDir(){
        return updloadTempDir;
    }
    
    protected String getUploadDir() {
    	return uploadDirAdmin;
    }
}
