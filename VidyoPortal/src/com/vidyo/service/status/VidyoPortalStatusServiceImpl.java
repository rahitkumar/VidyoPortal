package com.vidyo.service.status;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.clusterinfo.ClusterInfo;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.rest.status.StatusServiceException;

public class VidyoPortalStatusServiceImpl implements VidyoPortalStatusService {
    private static final String HTTP_500 = "500";

	private static final String cluster_service_script = "/opt/vidyo/ha/dr/cluster_service.sh";

	protected final Logger logger = LoggerFactory.getLogger((String)VidyoPortalStatusServiceImpl.class.getName());
    
    private String uploadTempDirSuper;

	public String getUploadTempDirSuper() {
		return uploadTempDirSuper;
	}

	public void setUploadTempDirSuper(String uploadTempDirSuper) {
		this.uploadTempDirSuper = uploadTempDirSuper;
	}

	//this will be re-factored
	public ClusterInfo getHotStandByStatus() {
 	String script = null;
	script = "sudo -n /opt/vidyo/ha/bin/clusterinfo.sh";
	List<String> stdOutLines;
	try {
			ShellCapture capture = ShellExecutor.execute(script);
			stdOutLines = capture.getStdOutLines();
	}catch (ShellExecutorException e) {
		logger.error("error in calling clusterinfo.sh",e);
			return null;
	}

		ClusterInfo clusterInfo = null;
		// Parse the putput string
		if (stdOutLines.size() > 0) {
			Map<String, String> tokensMap = new HashMap<String, String>();
			for (String token : stdOutLines) {
				String[] tokens = token.split("=");
				if (tokens.length == 2) {
					tokensMap.put(
							StringUtils.uncapitalize(tokens[0].trim()),
							tokens[1].trim());
				}

				}
			clusterInfo = new ClusterInfo();
			try {
				BeanUtils.populate(clusterInfo, tokensMap);
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException while populating bean", e);
			} catch (InvocationTargetException e) {
				logger.error("InvocationTargetException while populating bean", e);
			}
			logger.debug("tokensMap--->" + tokensMap);
			return clusterInfo;
		}
		return clusterInfo;
	
    }

	@Override
	public Map<String, String> clusterRegistration(String mapKeys) {
		return exectueScript(writeToFile(mapKeys));
	}

	@Override
	public Map<String, String> clusterRegistration(Map<String, Object> mapKeys) {
		return exectueScript(writeToFile(mapKeys));
	}
	private Map<String, String> exectueScript(String fileName) {
		Map<String, String> tokensMap=null;
		if (fileName == null) {
			throw new StatusServiceException(HTTP_500,"Unexpected error. File name returned null");
		}
		try{
				String[] cmd = { "sudo", "-n", cluster_service_script, fileName };
				ShellCapture capture = ShellExecutor.execute(cmd);
		//		if (capture.isSuccessExitCode()) {
					tokensMap=extractObject(capture.getStdOutLines());					
			
					return tokensMap;
			//	} else {
		//			throw new StatusServiceException(HTTP_500,capture.getStdErr());
		//		}

		}catch (ShellExecutorException e) {
				throw new StatusServiceException(HTTP_500,e);
		}

	}
	private Map<String, String> extractObject(List<String> stdOutLines) {
		// Parse the output string-existing code
		if (stdOutLines.size() > 0) {
			Map<String, String> tokensMap = new HashMap<String, String>();
			for (String token : stdOutLines) {
				String[] tokens = token.split("=");
				if (tokens.length == 2) {
					tokensMap.put(StringUtils.uncapitalize(tokens[0].trim()),tokens[1].trim());
				}
			}
		return	tokensMap;
		}
		throw  new StatusServiceException(HTTP_500,"No information returned from the script");
	}
	private String writeToFile(Map<String, Object> mapKeys) {
	    List<String> pairs=new ArrayList<String>();
		File tempFile=null;
		mapKeys.forEach((k,v) -> pairs.add(k+"="+v));
		try {
			tempFile=new File(uploadTempDirSuper +RandomStringUtils.randomAlphabetic(16));
			Files.write(Paths.get(tempFile.getAbsolutePath()), pairs, StandardCharsets.UTF_8);
			return tempFile.getName();
		} catch (IOException e) {
			throw  new StatusServiceException(HTTP_500,e);
		}
		
	}
	private String writeToFile(String mapKeys) {

		File tempFile=null;

		try {
			tempFile=new File(uploadTempDirSuper +RandomStringUtils.randomAlphabetic(16));
			Files.write(Paths.get(tempFile.getAbsolutePath()), mapKeys.getBytes(),StandardOpenOption.CREATE);
			return tempFile.getName();
		} catch (IOException e) {
			throw  new StatusServiceException(HTTP_500,e);
		}
		
	}
}