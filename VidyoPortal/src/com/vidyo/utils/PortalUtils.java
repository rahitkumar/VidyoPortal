package com.vidyo.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.bo.authentication.SamlRelayStateParam;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.service.ISystemService;
import com.vidyo.web.WebException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PortalUtils {

    protected static final Logger logger = LoggerFactory.getLogger(PortalUtils.class.getName());

    public static String generateRandomString(int len){
        String str= "QAa0bcLdUK2eHfJgTP8XhiFj61DOklNm9nBoI5pGqYVrs3CtSuMZvwWx4yE7zR";
        StringBuilder sb=new StringBuilder();
        Random r = new Random();
        int te=0;
        for(int i=1;i<=len;i++){
            te=r.nextInt(62);
            sb.append(str.charAt(te));
        }
        return sb.toString();
    }

    public static String generateKey(int total_chars){
        StringBuffer rc = new StringBuffer();
        String salt = "abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
        int len = salt.length();
        for (int i = 0; i < total_chars; i++){
            int startIndex = 0;
            do {
                startIndex = (int)(Math.random()*1000) % len;
                if (startIndex <= len - 1) {
                    break;
                }
            } while (true);
            rc.append(salt.substring(startIndex, startIndex+1));
        }
        return rc.toString();
    }

    public static String generateNumericKey(int total_chars){
        StringBuffer rc = new StringBuffer();
        String salt = "1234567890";
        int len = salt.length();
        for (int i = 0; i < total_chars; i++){
            int startIndex = 0;
            do {
                startIndex = (int)(Math.random()*1000) % len;
                if (startIndex <= len - 1) {
                    break;
                }
            } while (true);
            rc.append(salt.substring(startIndex, startIndex+1));
        }
        return rc.toString();
    }

    public static String generateUniqueCallID(){
        long nanoTime = System.nanoTime();
        String sNanoTime = String.valueOf(nanoTime);
        if (sNanoTime.length() > 20) {
            sNanoTime = sNanoTime.substring(0, 19);
        }
        return sNanoTime;
    }

    // Split single v3 license file to 2 files (vmlicense and syslycence)
    public static String splitLicFile(String pathname) throws IOException {
        File file = new File(pathname);
        String combinedLicContent = "";
        String directoryPath = "";
        combinedLicContent =  FileUtils.readFileToString(file); //fileContents.toString();
        String[] array = combinedLicContent.split("SEPARATOR");
        if(array.length==2){
            directoryPath = pathname.substring(0,pathname.lastIndexOf("/")+1);
            FileUtils.writeStringToFile(new File(directoryPath+"vmlicense"), array[0].toString());
            FileUtils.writeStringToFile(new File(directoryPath+"syslicense"), array[1].toString());
        }
        return directoryPath;
    }


    /**
     * Determines the mime type of a file, returning empty string if it cannot determine the mime type
     * Linux is better than Java's probe via Files.probeContentType()
     * @param theFile
     * @return
     */
    public static String determineFileMimeType(File theFile) {
        if (theFile == null) {
            return "";
        }
        if (!theFile.exists()) {
            return "";
        }
        String filePath = theFile.getPath();

        try {
            String[] command = {"/usr/bin/file", "--brief", "--mime-type", filePath};
            ShellCapture capture = ShellExecutor.execute(command);
            if (capture == null) {
                return "";
            }
            String output = capture.getStdOut();
            if (output == null) {
                return "";
            }
            logger.info("File mime-type output: " + output);
            return output.trim();
        } catch (ShellExecutorException e) {
            logger.error("Could not determine file mime-type: " + theFile.getName());

        }
        return "";
    }

    public static boolean isValidImageFileName(String fileName) {
        if (fileName == null) {
            return false;
        }
        String fileNameLowerCase = fileName.toLowerCase();
        return (fileNameLowerCase.endsWith(".png") || fileNameLowerCase.endsWith(".gif") || fileNameLowerCase.endsWith(".jpg") || fileNameLowerCase.endsWith(".jpeg"));
    }

    public static boolean isValidZipFileName(String fileName) {
        if (fileName == null) {
            return false;
        }
        String fileNameLowerCase = fileName.toLowerCase();
        return fileNameLowerCase.endsWith(".zip");
    }

    public static boolean isValidSha1FileName(String fileName) {
        if (fileName == null) {
            return false;
        }
        String fileNameLowerCase = fileName.toLowerCase();
        return fileNameLowerCase.endsWith(".sha1");
    }

    public static boolean isValidImageMimeType(String mimeType, String fileName) {
        if (mimeType == null) {
            return false;
        }
        String mimeTypeLowerCase = mimeType.toLowerCase();
        if ((fileName.endsWith(".png") && mimeTypeLowerCase.equals("image/png")) ||
                (fileName.endsWith(".gif") && mimeTypeLowerCase.equals("image/gif")) ||
                (fileName.endsWith(".jpg") && mimeType.equals("image/jpeg")) ||
                (fileName.endsWith(".jpeg") && mimeTypeLowerCase.equals("image/jpeg"))) {
            return true;
        } else {
            logger.info("Unexpected mime-type for image: " + mimeType + ", fileName: " + fileName);
            return false;
        }
    }

    public static boolean isValidZipMimeType(String mimeType, String fileName) {
        if (mimeType == null) {
            return false;
        }
        String mimeTypeLowerCase = mimeType.toLowerCase();
        if (fileName.endsWith(".zip") && (mimeTypeLowerCase.equals("application/zip") || mimeTypeLowerCase.equals("application/x-zip-compressed"))) {
            return true;
        } else {
            logger.info("Unexpected mime-type for image: " + mimeType + ", fileName: " + fileName);
            return false;
        }
    }

    public static boolean isValidImageFile(File imageFile) {
        return isValidImageMimeType(determineFileMimeType(imageFile), imageFile.getName());
    }

    public static boolean isValidZipFile(File zipFile) {
        return isValidZipMimeType(determineFileMimeType(zipFile), zipFile.getName());
    }

    public static boolean isValidGuideFileName(String fileName) {
        if (fileName == null) {
            return false;
        }
        String fileNameLowerCase = fileName.toLowerCase();
        return (fileNameLowerCase.endsWith(".pdf") || fileNameLowerCase.endsWith(".doc") || fileNameLowerCase.endsWith(".docx"));
    }

    public static boolean isValidGuideMimeType(String mimeType, String fileName) {
        if (mimeType == null) {
            return false;
        }
        String mimeTypeLowerCase = mimeType.toLowerCase();
        if ((fileName.endsWith(".pdf") && mimeTypeLowerCase.equals("application/pdf")) ||
                (fileName.endsWith(".doc") && mimeTypeLowerCase.equals("application/msword")) ||
                (fileName.endsWith(".docx") && mimeTypeLowerCase.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                ) {
            return true;
        } else {
            logger.info("Unexpected mime-type for guide: " + mimeType + ", fileName: " + fileName);
            return false;
        }
    }

    public static boolean isValidGuideFile(File guideFile) {
        return isValidGuideMimeType(determineFileMimeType(guideFile), guideFile.getName());
    }

    public static boolean isValidEndpointFileName(String fileName) {
        if (fileName == null) {
            return false;
        }
        String fileNameLowerCase = fileName.toLowerCase();
        return (fileNameLowerCase.endsWith(".exe") || fileNameLowerCase.endsWith(".dmg") ||
                fileNameLowerCase.endsWith(".deb") || fileNameLowerCase.endsWith(".rpm"));
    }

    public static boolean isValidEndpointMimeType(String mimeType, String fileName) {

        if (mimeType == null) {
            return false;
        }
        String mimeTypeLowerCase = mimeType.toLowerCase();

        if ((fileName.endsWith(".exe") && mimeTypeLowerCase.equals("application/x-dosexec")) ||
                (fileName.endsWith(".dmg") && (mimeTypeLowerCase.equals("application/octet-stream") || mimeTypeLowerCase.equals("application/x-bzip2"))) ||
                (fileName.endsWith(".deb") && mimeTypeLowerCase.equals("application/x-debian-package")) ||
                (fileName.endsWith(".rpm") && mimeTypeLowerCase.equals("application/x-rpm"))
                ) {
            return true;
        } else {
            logger.error("Unexpected mime-type for endpoint: " + mimeType + ", fileName: " + fileName);
            return false;
        }
    }

    public static boolean isValidEndpointFile(File endpointFile) {
        return isValidEndpointMimeType(determineFileMimeType(endpointFile), endpointFile.getName());
    }

    public static void setWebappsDirectoryOwnerAndPermissions(String dirPath) {
        Set<PosixFilePermission> filePerms = new HashSet<PosixFilePermission>();
        filePerms.add(PosixFilePermission.OWNER_READ);
        filePerms.add(PosixFilePermission.OWNER_WRITE);
        filePerms.add(PosixFilePermission.OWNER_EXECUTE);
        filePerms.add(PosixFilePermission.GROUP_READ);
        filePerms.add(PosixFilePermission.GROUP_EXECUTE);
        filePerms.add(PosixFilePermission.OTHERS_READ);
        filePerms.add(PosixFilePermission.OTHERS_EXECUTE);
        try {
            Path dir = Paths.get(dirPath);
            GroupPrincipal group = dir.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByGroupName("webapps");
            Files.getFileAttributeView(dir, PosixFileAttributeView.class).setGroup(group);
            Files.setPosixFilePermissions(Paths.get(dirPath), filePerms);
        } catch (IOException ioe) {
            logger.error("IOException setting dir permissions: " + dirPath);
        }
    }
    public static void setWebappsDirectoryOwnerAndPermissionsWithOutOthers(String dirPath) {
        Set<PosixFilePermission> filePerms = new HashSet<PosixFilePermission>();
        filePerms.add(PosixFilePermission.OWNER_READ);
        filePerms.add(PosixFilePermission.OWNER_WRITE);
        filePerms.add(PosixFilePermission.OWNER_EXECUTE);
        filePerms.add(PosixFilePermission.GROUP_READ);
        filePerms.add(PosixFilePermission.GROUP_WRITE);
        filePerms.add(PosixFilePermission.GROUP_EXECUTE);
      
        try {
            Path dir = Paths.get(dirPath);
            GroupPrincipal group = dir.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByGroupName("webapps");
            Files.getFileAttributeView(dir, PosixFileAttributeView.class).setGroup(group);
            Files.setPosixFilePermissions(Paths.get(dirPath), filePerms);
        } catch (IOException ioe) {
            logger.error("IOException setting dir permissions: " + dirPath);
        }
    }
    public static void setWebappsFileOwnerAndPermissions(String filePath) {
        Set<PosixFilePermission> filePerms = new HashSet<PosixFilePermission>();
        filePerms.add(PosixFilePermission.OWNER_READ);
        filePerms.add(PosixFilePermission.OWNER_WRITE);
        filePerms.add(PosixFilePermission.GROUP_READ);
        try {
            Path file = Paths.get(filePath);
            GroupPrincipal group = file.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByGroupName("webapps");
            Files.getFileAttributeView(file, PosixFileAttributeView.class).setGroup(group);
            Files.setPosixFilePermissions(file, filePerms);
        } catch (IOException ioe) {
            logger.error("IOException setting file permissions: " + filePath);
        }
    }

    public static void setWebappsFileOwnerAndPermissionsWithGroupWrite(String filePath) {
        Set<PosixFilePermission> filePerms = new HashSet<PosixFilePermission>();
        filePerms.add(PosixFilePermission.OWNER_READ);
        filePerms.add(PosixFilePermission.OWNER_WRITE);
        filePerms.add(PosixFilePermission.GROUP_READ);
        filePerms.add(PosixFilePermission.GROUP_WRITE);
        try {
            Path file = Paths.get(filePath);
            GroupPrincipal group = file.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByGroupName("webapps");
            Files.getFileAttributeView(file, PosixFileAttributeView.class).setGroup(group);
            Files.setPosixFilePermissions(file, filePerms);
        } catch (IOException ioe) {
            logger.error("IOException setting file permissions: " + filePath);
        }
    }

    public static void moveWebappsFileSecurely(File src, File dest) throws IOException {
        if (dest.exists()) {
            FileUtils.deleteQuietly(dest);
        }
        FileUtils.moveFile(src, dest);
        setWebappsFileOwnerAndPermissions(dest.getAbsolutePath());
    }

    public static void moveWebappsFileSecurelyWithGroupWrite(File src, File dest) throws IOException {
        if (dest.exists()) {
            FileUtils.deleteQuietly(dest);
        }
        FileUtils.moveFile(src, dest);
        setWebappsFileOwnerAndPermissionsWithGroupWrite(dest.getAbsolutePath());
    }

    public static boolean isValidCustomizationTextFileName(String fileName) {
        if (fileName == null) {
            return false;
        }
        String fileNameLowerCase = fileName.toLowerCase();
        return (fileNameLowerCase.endsWith(".css") || fileNameLowerCase.endsWith(".svg") ||
                fileNameLowerCase.endsWith(".json") || fileNameLowerCase.endsWith(".xml")) ||
                fileNameLowerCase.endsWith(".xliff");
    }

    public static boolean isValidCustomizationTextFile(File file) {
        if (file == null) {
            return false;
        }
        try {
            String mimeType = Files.probeContentType(file.toPath());
            String mimeTypeLowerCase = mimeType.toLowerCase();
            String fileName = file.getName();
            fileName = fileName.toLowerCase();

            if ((fileName.endsWith(".css") && mimeTypeLowerCase.equals("text/plain") || mimeTypeLowerCase.equals("text/css")) ||
                    (fileName.endsWith(".svg") && (mimeTypeLowerCase.equals("text/plain") || mimeTypeLowerCase.equals("image/svg+xml") || mimeTypeLowerCase.equals("text/xml") || mimeTypeLowerCase.equals("application/xml"))) ||
                    (fileName.endsWith(".json") && (mimeTypeLowerCase.equals("text/plain") || mimeTypeLowerCase.equals("application/json"))) ||
                    (fileName.endsWith(".xml") && (mimeTypeLowerCase.equals("text/plain") || mimeTypeLowerCase.equals("text/xml") || mimeTypeLowerCase.equals("application/xml"))) ||
                    (fileName.endsWith(".xliff") && (mimeTypeLowerCase.equals("text/plain") ||mimeTypeLowerCase.equals("text/xml") || mimeTypeLowerCase.equals("application/xml")))
                    ) {
                return true;
            } else {
                logger.error("Unexpected mime-type for customization file type: " + mimeType + ", fileName: " + fileName);
                return false;
            }
        } catch (IOException ioe) {
            logger.error("IOException determine mimeType for: " + file.getAbsolutePath());
        }
        return false;
    }

    public static boolean isValidRoomBrandingFileName(String fileName) {
        if (fileName == null) {
        return false;
        }
        String fileNameLowerCase = fileName.toLowerCase();
        return fileNameLowerCase.endsWith(".logo");

    }

    public static boolean isValidRoomBrandingFile(File file) {
        return isValidRoomBrandingMimeType(determineFileMimeType(file), file.getName());
    }

    private static boolean isValidRoomBrandingMimeType(String mimeType, String fileName) {
        if (mimeType == null) {
            return false;
        }
        String mimeTypeLowerCase = mimeType.toLowerCase();
        if (isValidRoomBrandingFileName(fileName) && mimeTypeLowerCase.equals("application/vnd.ms-cab-compressed")) {
            return true;
        } else {
            logger.info("Unexpected mime-type for room branding file: " + mimeType + ", fileName: " + fileName);
            return false;
        }
    }

	public static String encodeBase64(String inputStr) {
		if (inputStr == null) {
			return null;
		}
		return new String(Base64.encodeBase64(inputStr.getBytes()));
	}
	public static String decodeBase64(String inputStr) {
		if (inputStr == null) {
			return null;
		}
		return new String(Base64.decodeBase64(inputStr.getBytes()));
	}
	
	  /**
     * Method to deserialize JSON content from given JSON content String.
     * 
     This wont throw any exception but exception will be logged. If an exception happen, return null
     */
	public static <T> T JsonStringtoObject(String inputStr,Class<T> valueType) {
	ObjectMapper mapper = new ObjectMapper();
	
	try {
		return (T) mapper.readValue(inputStr, valueType);
	} catch (JsonParseException e) {
		logger.error("Unable to parse to json " , e);
	} catch (JsonMappingException e) {
		logger.error("Unable to parse to json " , e);
	} catch (IOException e) {
		logger.error("Unable to parse to json ", e);
	}
	return null;
	}
	
	public static String prepareLogo(ISystemService system) {
		String logo = org.apache.commons.lang.StringUtils.trimToEmpty(
				system.getCustomizedImageLogoName());
		
		// if no tenant one set, use super one, if if no super one, jsp will use default vidyo logo
		if (org.apache.commons.lang.StringUtils.isEmpty(logo)) {
			logo = org.apache.commons.lang.StringUtils.trimToEmpty(
					system.getCustomizedSuperPortalImageLogoName());
		}
		
		if (!org.apache.commons.lang.StringUtils.isEmpty(logo) && (logo.contains("/"))) {
			// remove physical path to upload folder
			logo = logo.replace("/opt/vidyo/portal2", ""); 
			// map superupload folder path to super/upload url
			logo = logo.replace("superupload", "super/upload"); 
		}
		return logo;
	}

	public static String objectToJsonString(Object inputStr) {
		final ObjectMapper mapper = new ObjectMapper();
		String jsonVal = null;
		try {
			jsonVal = mapper.writeValueAsString(inputStr);
		} catch (JsonProcessingException e) {
			logger.error("Unable to parse to json " + inputStr.toString(), e);
		}
		return jsonVal;
	}	
}
