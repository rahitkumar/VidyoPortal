package com.vidyo.utils;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

	private static final Logger log = LoggerFactory.getLogger(LogUtils.class);

	public static void logValidationError(String candidate, HttpServletRequest request, String entity) {
		log.error("validation error - id: {} is invalid\n" + ", URI: {} \n" + ", method: {} \n" + ", entity: {} \n",
				candidate, 
				request.getRequestURL().toString(), 
				request.getMethod(),
				(entity != null) ? entity : " None");
	}
}
