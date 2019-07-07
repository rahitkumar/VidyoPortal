package com.vidyo.utils;

import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.framework.security.htmlcleaner.AntiSamyHtmlCleaner;

public class HtmlUtils {
	
	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(HtmlUtils.class);

	public static String stripBreakTags(String input) {

		if (input == null) {
			return input;
		}
		return input.replace("<br />", "").replace("<br/>","").replace("<br>","").replace("<br >","");
	}

	public static String stripNewlineCharacters(String input) {

		if (input == null) {
			return input;
		}
		return input.replaceAll("(\\r|\\n)", "");
	}
	
    public static String sanitize(String aboutInfo) {
        String aboutInfoFinal = "";
        try {
            CleanResults aboutInfoClean = AntiSamyHtmlCleaner.cleanHtml(aboutInfo);
            aboutInfoFinal = aboutInfoClean.getCleanHTML();
        } catch (ScanException se) {
            logger.error(se.getMessage());
        } catch (PolicyException pe) {
            logger.error(pe.getMessage());
        }
        return aboutInfoFinal;
    }
    
    /**
     * Replaces the new line, carriage return and tab characters with the specified replacement character
     * @param input
     * @param replacement
     * @return
     */
	public static String replaceNewlineAndTabCharacters(String input, char replacement) {
		String replacementInput = input != null
				? input.trim().replace('\n', replacement).replace('\r', replacement).replace('\t', replacement)
				: input;
		return replacementInput;
	}

}
