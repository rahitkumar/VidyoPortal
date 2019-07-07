package com.vidyo.webshared;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.service.PortalService;
import com.vidyo.utils.PortalUtils;

@Controller
@RequestMapping("/upload/")
public class DownloadsController {

    protected final Logger logger = LoggerFactory.getLogger(DownloadsController.class.getName());

    @Value("${upload.dir.admin}")
    private String uploadDirAdmin;

    @Value("${upload.dir.super}")
    private String uploadDirSuper;

    @Autowired
    private PortalService portalService;

    public String getUploadDirAdmin() {
        return uploadDirAdmin;
    }

    public String getUploadDirSuper() {
        return uploadDirSuper;
    }

    @RequestMapping(value = "/{installer:.+}", method = RequestMethod.POST)
    public ModelAndView handleUploadLegacy(@PathVariable("installer") String installer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return handleUpload(installer, request, response);
    }

    @RequestMapping(value = "/{installer:.+}", method = RequestMethod.GET)
    public ModelAndView handleUpload(@PathVariable("installer") String installer, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.debug("Reached in DownloadsController");
        return handleRequestInternal(request, response, installer);
    }

    private ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {

        if (isDirectoryTraversalAttempt(fileName) || !isSupportedDownload(fileName)) {
            logger.error("Bad file download request: " + fileName);
            response.setContentType("text/html");
            response.sendError(HttpStatus.NOT_FOUND.value());
            return null;
        }

        File fileToDownload = null;
        File testAdminUploadFile = new File(getUploadDirAdmin() + fileName);
        File testSuperUploadFile = new File(getUploadDirSuper() + fileName);

        if (testAdminUploadFile.exists()) {
            fileToDownload = testAdminUploadFile;
        } else if (testSuperUploadFile.exists()) {
            fileToDownload = testSuperUploadFile;
        }

        if (fileToDownload == null) {
            logger.error("Unknown file download request: " + fileName);
            response.setContentType("text/html");
            response.sendError(HttpStatus.NOT_FOUND.value());
            return null;
        }

        // only send for download what is allowed to be uploaded,
        // in case other files get copied to upload folders
        if (!isSupportedDownload(fileToDownload)) {
            logger.error("Unsupported mime-type file download request: " + fileName);
            response.setContentType("text/html");
            response.sendError(HttpStatus.NOT_FOUND.value());
            return null;
        }

        ServletContext context = request.getServletContext();
        String mimetype = context.getMimeType(fileToDownload.getAbsolutePath());
        response.setContentType(mimetype == null ? "application/octet-stream" : mimetype);
        //VPTL-7796 - Embedding the portalfeatures in the installer and join link.
        String portalFeatures = portalService.getEncodedPortalFeatures();
        long length = fileToDownload.length();
        if (length <= Integer.MAX_VALUE) {
            response.setContentLength((int)length);
        } else {
            response.addHeader("Content-Length", Long.toString(length));
        }

        if (StringUtils.trimToEmpty(request.getParameter("t")).equals("p")) {
            String renamedFile = fileName.substring(0,fileName.length()-4) + "[p=" + request.getScheme() + "&h=" + request.getServerName() + "&f="+portalFeatures+"]";
            if (fileName.toUpperCase().endsWith(".EXE")) {
                renamedFile = renamedFile + ".exe";
            } if (fileName.toUpperCase().endsWith(".DMG")) {
                renamedFile = renamedFile + ".dmg";
            }
            renamedFile = removeAdditionalParametersFromFileName(renamedFile);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + renamedFile + "\"");
        } else if (StringUtils.isNotBlank(request.getParameter("r"))) {
            String roomKey = StringUtils.trimToEmpty(request.getParameter("r"));
            if (looksGood(roomKey)) {
                String renamedFile = fileName.substring(0, fileName.length() - 4) + "[p=" + request.getScheme() + "&h=" + request.getServerName() +
                        "&f="+portalFeatures +
                        "&r=" + roomKey + "]";
                if (fileName.toUpperCase().endsWith(".EXE")) {
                    renamedFile = renamedFile + ".exe";
                }
                if (fileName.toUpperCase().endsWith(".DMG")) {
                    renamedFile = renamedFile + ".dmg";
                }
                renamedFile = removeAdditionalParametersFromFileName(renamedFile);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + renamedFile + "\"");
            }
        }

        try (FileInputStream in = FileUtils.openInputStream(fileToDownload);
             ServletOutputStream out = response.getOutputStream();) {
            IOUtils.copy(in, out);
        }

        return null;
    }
    
    private static String removeAdditionalParametersFromFileName(String fileName) {
    	String result = fileName;
    	
    	if (fileName.contains("TAG_VD_")) { // is UVD - remove params
    		result = fileName.replaceAll(" *\\[.+?\\]", "");
    	}
    	
    	return result;
	}

    private static boolean looksGood(String roomKey) {
        if (StringUtils.isBlank(roomKey)) {
            return false;
        }
        return roomKey.length() < 32 && roomKey.matches("^[a-zA-Z0-9]*$");
    }

    private static boolean isDirectoryTraversalAttempt(String fileName) {
        if (fileName == null) {
            return false;
        }
        return fileName.contains("..");
    }

    private static boolean isSupportedDownload(String fileName) {
        return PortalUtils.isValidImageFileName(fileName) ||
                PortalUtils.isValidGuideFileName(fileName) ||
                PortalUtils.isValidEndpointFileName(fileName) ||
                PortalUtils.isValidRoomBrandingFileName(fileName);
    }

    private static boolean isSupportedDownload(File file) {
        return PortalUtils.isValidImageFile(file) ||
                PortalUtils.isValidGuideFile(file) ||
                PortalUtils.isValidEndpointFile(file) ||
                PortalUtils.isValidRoomBrandingFile(file);
    }
}
