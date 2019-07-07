/**
 * 
 */
package com.vidyo.framework.security.utils;

/**
 * @author Ganesh
 *
 */
public interface TokenGenerator {

	/**
	 * 
	 * @return
	 */
	public String generateTokenData();
	
	/**
	 * 
	 * @return
	 */
	public String generateSeriesData();
	
	/**
	 * Inverse operation of decodeCookie.
	 * 
	 * @param tokens
	 *            the tokens to be encoded.
	 * @return base64 encoding of the tokens concatenated with the ":"
	 *         delimiter.
	 */
	public String encodeToken(String[] tokens);	
}
