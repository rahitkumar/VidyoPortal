package com.vidyo.framework.util;

import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * This ValidatorUtil utility is responsible to provide the validations needed for the services/controllers/web services.
 * 
 * @author ysakurikar
 *
 */

public class ValidatorUtil {
	
	/**
	 * Holds the UrlValidator object for the http/https schemes only.
	 */
	private static final UrlValidator httpHttpsurlValidator = new UrlValidator(new String[]{"http","https"}, 
			new RegexValidator(RegExValidator.FQDN_REG_EX + "|" + RegExValidator.IPV4_REG_EX + "|" + RegExValidator.IPV6_REG_EX), UrlValidator.ALLOW_2_SLASHES);
	
	/**
	 * Validate the given URL with protocol HTTP / HTTPS only.
	 * @param urlToValidate
	 * @return
	 */
	public static boolean isValidHTTPHTTPSURL(String urlToValidate) {
		return httpHttpsurlValidator.isValid(urlToValidate);
	}
	
	/**
	 * Validate the given URL without protocol.
	 * @param urlToValidate
	 * @return
	 */
	public static boolean isValidURL(String urlToValidate) {
		return RegExValidator.validateFQDN(urlToValidate);
	}
}
