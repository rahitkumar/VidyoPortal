package com.vidyo.web;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.EndpointUpload;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantFilter;
import com.vidyo.service.ITenantService;
import com.vidyo.utils.PortalUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.text.MessageFormat;
import java.util.*;

import static com.vidyo.web.SettingsController.FAILURE;
import static com.vidyo.web.SettingsController.SUCCESS;

@Controller
public class SuperUploadController extends UploadController {

    @Value("${upload.temp.dir.super}")
    private String updloadTempDir;
    
    @Value("${upload.dir.super}")
    protected String uploadDirSuper;

    @Autowired
    private ITenantService tenant;

    @Override
    @RequestMapping(value = "/upload.html", method = RequestMethod.GET)
    public ModelAndView uploadHtml(
            String parentDirectory,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response) throws Exception {
        return super.uploadHtmlInternal("super", request, response);
    }

    @RequestMapping(value = "/securedmaint/deleteupload.ajax", method = RequestMethod.POST)
    public ModelAndView deleteUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.deleteUploadInternal(request, response, AppType.SUPER);
    }

    @RequestMapping(value = "/securedmaint/activateupload.ajax",method = RequestMethod.POST)
    public ModelAndView setSuperActiveUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int endpointUploadID = ServletRequestUtils.getIntParameter(request, "endpointUploadID", 0);

        EndpointUpload endpoint = this.endpointupload.getEndpointUpload(endpointUploadID);

        Map<String, Object> model = new HashMap<String, Object>();

        // Check if the super is external / internal
        boolean isSuperExternal = this.endpointupload.isUploadModeExternal(0);

        TenantFilter tf = new TenantFilter();
        tf.setLimit(Integer.MAX_VALUE);
        List<Tenant> tenants = this.tenant.getTenants(tf);

        EndpointUpload super_endpoint = this.endpointupload.getEndpointUploadForFileName(0, endpoint.getEndpointUploadFile(), endpoint.getEndpointUploadType(),
                endpoint.getEndpointUploadVersion());
        // special record for super
        this.endpointupload.setActiveUpload(0, super_endpoint);

        for (Tenant t : tenants) {
            EndpointUpload tenant_endpoint = this.endpointupload.getEndpointUploadForFileName(t.getTenantID(), endpoint.getEndpointUploadFile(),
                    endpoint.getEndpointUploadType(), endpoint.getEndpointUploadVersion());

            // If the tenant and super endpoint upload modes are matching
            // then only activate the endpoint for tenant, otherwise, let it be inactive
            // for the condition- if admin removed one end point in the admin page then later super set as active the same one from the super page.
            if(tenant_endpoint!=null
                    && !(isSuperExternal ^ this.endpointupload.isUploadModeExternal(t.getTenantID())) ){
                this.endpointupload.setActiveUpload(t.getTenantID(), tenant_endpoint);
            }
        }

        model.put("success", endpointUploadID > 0 ? Boolean.TRUE:Boolean.FALSE);
        system.auditLogTransaction("Endpoint activated by Super.","endpointUploadID="+endpointUploadID, endpointUploadID > 0 ? SUCCESS: FAILURE);
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/savecustomizedimagelogo.ajax", method = RequestMethod.POST)
    public ModelAndView saveSuperCustomizedImageLogo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        //uploading
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("VDLogoArchive");
        String mimeType = multipartFile.getContentType();

        //verify the file extension type
        String fn = multipartFile.getOriginalFilename().toLowerCase();
        if (!PortalUtils.isValidImageFileName(fn)) {
            FieldError fe = new FieldError("CustomizedSuperPortalLogo", ms.getMessage("incorrect.file.type", null, "", loc), MessageFormat.format(ms.getMessage("logo.image.file.must.be.0", null, "", loc), " *.gif, *.jpg, *.png"));
            errors.add(fe);
        } else if (!PortalUtils.isValidImageMimeType(mimeType, fn)) {
            FieldError fe = new FieldError("CustomizedSuperPortalLogo", ms.getMessage("incorrect.file.type", null, "", loc), MessageFormat.format(ms.getMessage("logo.image.file.must.be.0", null, "", loc), " gif, jpg, png"));
            errors.add(fe);
        } else {

            //store the new logo file
            String ext = fn.substring(fn.lastIndexOf('.')+1);
            fn = "0_"+System.currentTimeMillis()+"_logo." + ext;
            File uploadedTempFile = new File(getUploadTempDir() + fn);
            multipartFile.transferTo(uploadedTempFile);

            if (!PortalUtils.isValidImageFile(uploadedTempFile)) {
                logger.info("Invalid mime-type for image upload.");
                FileUtils.deleteQuietly(uploadedTempFile);
                FieldError fe = new FieldError("CustomizedSuperPortalLogo", ms.getMessage("incorrect.file.type", null, "", loc), MessageFormat.format(ms.getMessage("logo.image.file.must.be.0", null, "", loc), " gif, jpg, png"));
                errors.add(fe);
            } else {

                //remove old logo image file
                String oldname = this.system.getCustomizedSuperPortalImageLogoName();
                if ((oldname != null) && (oldname.length() != 0)) {
                    if (oldname.contains("/")) {
                        oldname = oldname.substring(oldname.lastIndexOf("/") + 1);
                    }
                    // Delete the file if it exists
                    FileUtils.deleteQuietly(new File(uploadDirSuper + oldname));
                }

                PortalUtils.moveWebappsFileSecurely(uploadedTempFile, new File(uploadDirSuper + uploadedTempFile.getName()));
                //update the database with new logo filename
                if (-1 == this.system.saveCustomizedSuperPortalImageLogo(uploadDirSuper + fn)) {
                    FieldError fe = new FieldError("CustomizedSuperPortalLogo", ms.getMessage("update.failed", null, "", loc), ms.getMessage("failed.to.save.customized.logo", null, "", loc));
                    errors.add(fe);
                }
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

    @RequestMapping(value = "/savevidyoweb.ajax",method = RequestMethod.POST)
    public ModelAndView saveVidyoWebSettingsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("Entering saveVidyowebSettingsAjax() of SettingsController");
        Map<String, Object> model = new HashMap<String, Object>();
        String vidyoWebAvailable = ServletRequestUtils.getStringParameter(request, "vidyoWebAvailable", "disabled");
        String vidyoWebEnabled = ServletRequestUtils.getStringParameter(request, "vidyoWebEnabled", "disabled");

        if(vidyoWebAvailable != null) {
            if(vidyoWebAvailable.equalsIgnoreCase("enabled")) {
                if (!system.isVidyoWebAvailable()) {
                    system.saveVidyoWebAvailable(true);
                    system.auditLogTransaction("VidyoWeb Available- System wide.", "", SUCCESS);
                }

                if(vidyoWebEnabled != null) {
                    if(vidyoWebEnabled.equalsIgnoreCase("enabled")) {
                        if (!system.isVidyoWebEnabledBySuper()) {
                            system.saveVidyoWebEnabledForSuper(true);
                            system.auditLogTransaction("VidyoWeb enabled- System wide.", "", SUCCESS);
                        }
                    } else {
                        if (system.isVidyoWebEnabledBySuper()) {
                            system.saveVidyoWebEnabledForSuper(false);
                            system.auditLogTransaction("VidyoWeb disabled- System wide.", "", SUCCESS);
                        }
                    }
                }

            } else {
                if (system.isVidyoWebAvailable()) {
                    system.saveVidyoWebAvailable(false);
                    system.auditLogTransaction("VidyoWeb Unavailable- System wide.", "", SUCCESS);
                    if (system.isVidyoWebEnabledBySuper()) {
                        system.saveVidyoWebEnabledForSuper(false);
                        system.auditLogTransaction("VidyoWeb disabled- System wide.", "", SUCCESS);
                    }
                }
            }
        }
        model.put("success", true);
        logger.debug("Exiting saveVidyowebSettingsAjax() of SettingsController");
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    public Long getCountEndpointUpload(){
        return endpointupload.getCountEndpointUploadBySuper();

    }

    public List<EndpointUpload> getEndpointUploads(int start, int limit){
        return endpointupload.getEndpointUploadsBySuper(start, limit);
    }

    protected String getUploadTempDir(){
        return updloadTempDir;
    }
    
    protected String getUploadDir() {
    	return uploadDirSuper;
    }

    @Override
    protected void performDeleteEndpoint(boolean externalURL, int endpointUploadID, EndpointUpload uploaded) {
        this.endpointupload.deleteEndpointUploadFileName(uploaded);
        if (!externalURL) {
            //FileUtils.deleteQuietly(new File(uploadDirAdmin + uploaded.getEndpointUploadFile()));
            FileUtils.deleteQuietly(new File(uploadDirSuper + uploaded.getEndpointUploadFile()));
        }
    }

    @Override
    protected boolean isUploadModeExternal() {
        Configuration endpointUploadModeConfiguration = system.getConfiguration("MANAGE_ENDPOINT_UPLOAD_MODE");
        if (endpointUploadModeConfiguration != null && endpointUploadModeConfiguration.getConfigurationValue() != null
                && endpointUploadModeConfiguration.getConfigurationValue().equalsIgnoreCase("External")){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int getContextId() {
        return 0;
    }

    @Override
    protected String removeCustomizedPortalLogo() {
        return system.removeCustomizedSuperPortalImageLogo();
    }

    @Override
    protected String getName() {
        return "super";
    }

    @Override
    protected String getCustomizedLogoFileName() {
        return system.getCustomizedSuperPortalImageLogoName();
    }
}
