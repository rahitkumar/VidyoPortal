/**
 * 
 */
package com.vidyo.framework.security.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Ganesh
 *
 */
public class TokenGeneratorImpl implements TokenGenerator {
	
    private SecureRandom random;

    public static final int DEFAULT_SERIES_LENGTH = 16;
    public static final int DEFAULT_TOKEN_LENGTH = 16;

    private int seriesLength = DEFAULT_SERIES_LENGTH;
    private int tokenLength = DEFAULT_TOKEN_LENGTH;
    
    public TokenGeneratorImpl() throws NoSuchAlgorithmException {
    	random = SecureRandom.getInstance("SHA1PRNG");
    }

    public String generateSeriesData() {
        byte[] newSeries = new byte[seriesLength];
        random.nextBytes(newSeries);
        return new String(Base64.encodeBase64(newSeries));
    }

    public String generateTokenData() {
        byte[] newToken = new byte[tokenLength];
        random.nextBytes(newToken);
        return new String(Base64.encodeBase64(newToken));
    }
    
	/**
	 * Inverse operation of decodeCookie.
	 * 
	 * @param tokens
	 *            the tokens to be encoded.
	 * @return base64 encoding of the tokens concatenated with the ":"
	 *         delimiter.
	 */
	public String encodeToken(String[] tokens) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tokens.length; i++) {
			sb.append(tokens[i]);
			if (i < tokens.length - 1) {
				sb.append(":");
			}
		}

		String value = sb.toString();
		return value;
	}    
}
