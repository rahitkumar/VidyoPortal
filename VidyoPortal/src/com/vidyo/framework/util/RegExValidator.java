/**
  * Utility class for validation fqdn, ipv4 and ipv6 validation.
  *  All the reg ex used in this class is from the web.
  *  Also anybody can use their own reg ex using validateStrUsingRegEx method
  * @author hojin
  */
package com.vidyo.framework.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegExValidator implements Serializable {

	private static final long serialVersionUID = -7083994817871136703L;

	public static final String FQDN_REG_EX = "^((?=[a-zA-Z0-9-]{1,63}\\.)[a-zA-Z0-9]+(-[a-zA-Z0-9]+)*\\.)+[a-zA-Z]{1}[a-zA-Z0-9]{1,62}$";
	public static final String FQDN_PORT_REG_EX = "^(((?=[a-zA-Z0-9-]{1,63}\\.)[a-zA-Z0-9]+(-[a-zA-Z0-9]+)*\\.)+[a-zA-Z]{1}[a-zA-Z0-9]{1,62})(\\:\\d{1,5})$";
	public static final String IPV4_REG_EX = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	// private static final String
	// IPV6_REG_EX="^([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4})$";
	// this regex is copied from UI in order to make it same, this is unit
	// tested in FQDNTest.java..
	public static final String IPV6_REG_EX = "^((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?$";

	private RegExValidator() {

	}

	/**
	 * Validate the input value.If it is a valid fqdn returns true, else false
	 *
	 * @param input
	 * @return boolean
	 */
	public static boolean isValidFQDN(final String input) {

		return isValidLength(input) && validateStrUsingRegEx(input, FQDN_REG_EX);

	}

	/**
	 * Returns true if it is a valid FQDN with port number. For example, a valid
	 * input is testfqsn.com:5000 in order to avoid the complexity of the reg
	 * ex, max validation of the port is validated not using reg ex
	 *
	 * @param input
	 * @return boolean
	 */
	public static boolean isValidFQDNWithPort(final String input) {

		boolean result = false;
		if (isValidLength(input) && validateStrUsingRegEx(input, FQDN_PORT_REG_EX)) {
			final String portNoStr = input.split(":")[1];
			final int port = Integer.valueOf(portNoStr);
			result = (port > 0 && port <= 65535) ? true : false;
		}
		return result;
	}

	/**
	 * Return true if the input is a valid ipv4. This method will do max 255
	 * validation as well.
	 *
	 * @param input
	 * @return
	 */
	public static boolean isValidIPv4(final String input) {

		return validateStrUsingRegEx(input, IPV4_REG_EX);

	}

	/**
	 * Return true if the input is a valid ipv6. This is a basic ipv6
	 * validation, please use validateStrUsingRegEx method to use your own
	 * Reg-ex if the requirement to validate complex ipv6 validation rules
	 *
	 * @param input
	 * @return
	 */
	public static boolean isValidIPv6(final String input) {

		return validateStrUsingRegEx(input, IPV6_REG_EX);

	}

	/**
	 * As per Vidyo requirement by Jason(VP), the allowed maximum length
	 * including port number is 253 VPTL-6641
	 *
	 * @param logServer
	 * @return boolean true or false
	 */
	private static boolean isValidLength(String logServer) {
		return logServer.length() <= 253;

	}

	/**
	 * Validate the input using provided regular expression
	 *
	 * @param input
	 * @param regExStr
	 * @return
	 */
	private static boolean validateStrUsingRegEx(final String input, final String regExStr) {
		final Pattern pattern = Pattern.compile(regExStr);
		final Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	public static boolean validateFQDN(String logServer) {
		return isValidFQDN(logServer) || isValidFQDNWithPort(logServer) || isValidIPv4(logServer)
				|| isValidIPv6(logServer);

	}
}