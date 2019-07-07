package com.vidyo.web.portal;

import com.vidyo.bo.Configuration;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.utils.PortalUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CustomizationController  {

    protected static final Logger logger = LoggerFactory.getLogger(CustomizationController.class.getName());

    private String customizationDir = "/opt/vidyo/portal2/customization";

    @Autowired
    private ISystemService systemService;

    @RequestMapping(value = "/customization/**", method = GET)
    private ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String requestURI = request.getRequestURI();
        if (requestURI == null) {
            return null;
        }

        String fileAndPath = URLDecoder.decode(
                requestURI.
                        replaceFirst("/customization/", ""),
                "UTF-8");

        if (isDirectoryTraversalAttempt(fileAndPath) || !isSupportedDownload(fileAndPath)) {
            logger.warn("Bad file download request: " + fileAndPath);
            response.setContentType("text/html");
            response.sendError(HttpStatus.NOT_FOUND.value());
            return null;
        }

        if ("customization.zip".equals(fileAndPath)) {
            Configuration overrideFlagConfig = systemService.getConfiguration("EndpointCustomizationOverride");
            boolean overrideFlag = true;
            if (overrideFlagConfig != null && !"true".equals(overrideFlagConfig.getConfigurationValue())) {
                overrideFlag = false;
            }
            String tenantPackageName = "" + TenantContext.getTenantId() + "_desktop.zip";
            File tenantPackage = new File(getCustomizationDir() + "/" + tenantPackageName);
            File fileToSend = null;
            if (overrideFlag && tenantPackage.exists()) {
                fileToSend = tenantPackage;
            } else {
                String superPackageName = "0_desktop.zip";
                File superPackage = new File(getCustomizationDir() + "/" + superPackageName);
                if (superPackage.exists()) {
                    fileToSend = superPackage;
                }
            }
            if (fileToSend != null) {
                String inHash = request.getParameter("hash");
                String fileHash = "";
                try {
                    fileHash = FileUtils.readFileToString(new File(fileToSend.getAbsolutePath().replace(".zip", ".sha1")));
                } catch (IOException ioe) {
                    logger.error("IOException reading customization hash file for: " + fileToSend.getAbsolutePath());
                }
                if (StringUtils.isBlank(inHash) || !inHash.equals(fileHash)) {
                    long fileSize = fileToSend.length();

                    response.reset();
                    response.setHeader("Pragma", "public");
                    response.setHeader("Expires","0");
                    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                    response.setHeader("Cache-Control", "public");
                    response.setHeader("Content-Description", "File Transfer");
                    response.setHeader("Content-Disposition", "attachment; filename=\"customization.zip\"");
                    response.setHeader("Content-Transfer-Encoding", "binary");
                    response.addHeader("Content-Length", Long.toString(fileSize));
                    response.setContentType("application/zip");

                    try (ServletOutputStream out = response.getOutputStream();
                         FileInputStream in = new FileInputStream(fileToSend)) {
                        IOUtils.copy(in, out);
                    }
                } else {
                    response.setStatus(304); // not modified based on hash received
                }
                return null;
            }
            response.sendError(HttpStatus.NOT_FOUND.value());
            return null;
        } else if ("customization.sha1".equals(fileAndPath)) {
            Configuration overrideFlagConfig = systemService.getConfiguration("EndpointCustomizationOverride");
            boolean overrideFlag = true;
            if (overrideFlagConfig != null && !"true".equals(overrideFlagConfig.getConfigurationValue())) {
                overrideFlag = false;
            }
            String tenantPackageName = "" + TenantContext.getTenantId() + "_desktop.sha1";
            File tenantPackage = new File(getCustomizationDir() + "/" + tenantPackageName);
            File fileToSend = null;
            if (overrideFlag && tenantPackage.exists()) {
                fileToSend = tenantPackage;
            } else {
                String superPackageName = "0_desktop.sha1";
                File superPackage = new File(getCustomizationDir() + "/" + superPackageName);
                if (superPackage.exists()) {
                    fileToSend = superPackage;
                }
            }
            if (fileToSend != null) {

                long fileSize = fileToSend.length();

                response.reset();
                response.setHeader("Pragma", "public");
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Cache-Control", "public");
                response.addHeader("Content-Length", Long.toString(fileSize));
                response.setContentType("text/plain");

                try (ServletOutputStream out = response.getOutputStream();
                     FileInputStream in = new FileInputStream(fileToSend)) {
                    IOUtils.copy(in, out);
                }
                return null;
            }

            response.sendError(HttpStatus.NOT_FOUND.value());
            return null;
        }

        // send contents of zip file
        if (!(fileAndPath.endsWith(".zip") || fileAndPath.endsWith(".sha1"))) {

            Configuration overrideFlagConfig = systemService.getConfiguration("EndpointCustomizationOverride");
            boolean overrideFlag = true;
            if (overrideFlagConfig != null && !"true".equals(overrideFlagConfig.getConfigurationValue())) {
                overrideFlag = false;
            }
            String tenantPackageName = "" + TenantContext.getTenantId() + "_desktop.zip";
            File tenantPackage = new File(getCustomizationDir() + "/" + tenantPackageName);
            File fileToUse = null;
            if (overrideFlag && tenantPackage.exists()) {
                fileToUse = tenantPackage;
            } else {
                String superPackageName = "0_desktop.zip";
                File superPackage = new File(getCustomizationDir() + "/" + superPackageName);
                if (superPackage.exists()) {
                    fileToUse = superPackage;
                }
            }

            if (fileToUse != null) {
                Map<String, String> env = new HashMap<>();
                URI uri = URI.create("jar:file:" + fileToUse.getAbsolutePath());
                try (FileSystem fs = FileSystems.newFileSystem(uri, env, null)) {
                    Path fileInZip = fs.getPath("/" + fileAndPath);
                    if (fileInZip != null && Files.exists(fileInZip)) {
                        response.setHeader("Cache-Control", "max-age=300"); // cache for 5 min
                        try (ServletOutputStream out = response.getOutputStream()) {
                            Files.copy(fileInZip, out);
                        }
                        return null;
                    }
                } catch (IOException ioe) {
                    logger.error("IOException sending file from customization package: " + fileToUse.getAbsolutePath(), ", path: " + fileAndPath);
                }
            } else {
                response.sendError(HttpStatus.NOT_FOUND.value());
                return null;
            }
        }

        response.sendError(HttpStatus.NOT_FOUND.value());
        return null;
    }


    private static boolean isDirectoryTraversalAttempt(String fileName) {
        if (fileName == null) {
            return false;
        }
        return fileName.contains("..");
    }

    private boolean isSupportedDownload(String fileName) {
        return PortalUtils.isValidImageFileName(fileName) ||
                PortalUtils.isValidCustomizationTextFileName(fileName) ||
                PortalUtils.isValidZipFileName(fileName) ||
                PortalUtils.isValidSha1FileName(fileName);
    }

    private boolean isSupportedDownload(File file) {
        return PortalUtils.isValidImageFile(file) ||
                PortalUtils.isValidCustomizationTextFile(file) ||
                PortalUtils.isValidZipFile(file);
    }

    public void setCustomizationDir(String customizationDir) {
        this.customizationDir = customizationDir;
    }

    public String getCustomizationDir() {
        return customizationDir;
    }

    public ISystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(ISystemService systemService) {
        this.systemService = systemService;
    }

}
