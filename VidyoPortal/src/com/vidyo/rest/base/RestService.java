package com.vidyo.rest.base;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class RestService {

	protected final Logger logger = LoggerFactory.getLogger(RestService.class.getName());

	@ExceptionHandler
	public @ResponseBody
	RestResponse handleException(Exception exc) {

		logger.error("///////   REST service exception stacktrace START ///////////");
		logger.error(ExceptionUtils.getStackTrace(exc));
		logger.error("///////   REST service exception stacktrace END /////////////");

		String message = exc.getMessage();
		RestResponse response = new RestResponse();

		if (exc instanceof MethodArgumentNotValidException) {
			response.setStatus("400", message);
		} else {
			response.setStatus("500", message);
		}

		return response;
	}

}
