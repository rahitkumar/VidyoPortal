package com.vidyo.framework.security.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {
	
	protected static final Logger logger = LoggerFactory.getLogger(SHA1.class);

    public static String enc (String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash;
        md.update(text.getBytes("UTF-8"), 0, text.getBytes("UTF-8").length);
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer rc = new StringBuffer();
        for (byte aData : data) {
            int halfbyte = (aData >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    rc.append((char) ('0' + halfbyte));
				} else {
                    rc.append((char) ('a' + (halfbyte - 10)));
				}
                halfbyte = aData & 0x0F;
            } while (two_halfs++ < 1);
        }
        return rc.toString();
    }

	public static String convertHexToString(String hex){
		StringBuffer rc = new StringBuffer();
		for (int i = 0; i < hex.length() - 1; i += 2) {
			//grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			//convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			//convert the decimal to character
			rc.append((char)decimal);
		}
		return rc.toString();
	}

}