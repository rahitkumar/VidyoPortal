/**
 * 
 */
package com.vidyo.framework.security.htmlcleaner;

import java.io.IOException;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * @author Ganesh
 * 
 */
public class AntiSamyHtmlCleaner {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(AntiSamyHtmlCleaner.class);

	/**
	 * 
	 */
	private AntiSamyHtmlCleaner() {
		// Private constructor to prevent instances
	}

	/**
	 * Classpath Resource
	 */
	private static Resource resource;

	/**
	 * Policy loaded from policy file
	 */
	private static Policy policy = null;

	/**
	 * 
	 */
	private static AntiSamy antiSamy = null;

	/**
	 * @param resource
	 *            the resource to set
	 */
	public static void setResource(Resource resource) throws Exception {
		if (resource == null) {
			throw new Exception("Policy cannot be null");
		}
		if (AntiSamyHtmlCleaner.resource == null) {
			AntiSamyHtmlCleaner.resource = resource;
			try {
				AntiSamyHtmlCleaner.policy = Policy.getInstance(resource.getInputStream());
			} catch (PolicyException e) {
				logger.error("Policy Exception while loading policy file", e);
			} catch (IOException e) {
				logger.error("IOException while loading policy file", e);
			}
			AntiSamyHtmlCleaner.antiSamy = new AntiSamy(AntiSamyHtmlCleaner.policy);
		} else {
			throw new Exception("Cannot modify policy file, once initialized");
		}
	}

	/**
	 * Sanitizes the user provided input html
	 * 
	 * @param input
	 * @return results with clean html and errors if any
	 * @throws ScanException
	 *             if errors while scanning input
	 * @throws PolicyException
	 *             if errors while reading policy file
	 * 
	 */
	public static CleanResults cleanHtml(String input) throws ScanException, PolicyException {
		CleanResults cleanResults = AntiSamyHtmlCleaner.antiSamy.scan(input);
		return cleanResults;
	}

}
