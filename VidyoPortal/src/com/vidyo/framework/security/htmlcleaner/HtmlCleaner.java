/**
 * 
 */
package com.vidyo.framework.security.htmlcleaner;

import org.owasp.validator.html.CleanResults;

/**
 * @author Ganesh
 * 
 */
public interface HtmlCleaner {

	/**
	 * Sanitizes the user provided input html
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public CleanResults cleanHtml(String input) throws Exception;

}
