/**
 * 
 */
package com.vidyo.utils;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * @author ganesh
 *
 */
public class ValidationUtils {
	
	private static InetAddressValidator addressValidator = new InetAddressValidator();
	
	private static DomainValidator domainValidator = DomainValidator.getInstance(true);
	
	private static EmailValidator emailValidator = EmailValidator.getInstance();
	
	public static final String EMAIL_REGEX = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	public static final String MEMBER_EMAIL_REGEX = "^\\w+([\\.!#$%&'*+-/=?^_`{|}~,]\\w+)*@\\w+([.-]\\w+)*\\.\\w{2,}$";

	public static final String USERNAME_REGEX = "^[[^\\p{Punct}]&&[^\\p{Space}]][[[^\\p{Punct}]&&[^\\p{Space}]][_\\-\\.@]]*$";
	
	public static final String GROUP_NAME_REGEX = "^[a-zA-Z0-9-._@]*$";
	
	public static final String TAG_EXPR = "\\<.*?>";
	
	public static boolean isValidAddress(final String inetAddress) {
		return addressValidator.isValid(inetAddress) || domainValidator.isValid(inetAddress);
	}
	
	public static boolean isValidIPCDomainName(final String inetAddress) {
		// replace all * symbols with dummy symbol a
		String domainName = inetAddress.replaceAll("\\*", "a");
		return isValidAddress(domainName);
	}
	
	public static boolean isValidEmailAddress(final String emailAddress) {
		return emailValidator.isValid(emailAddress);
	}
}
