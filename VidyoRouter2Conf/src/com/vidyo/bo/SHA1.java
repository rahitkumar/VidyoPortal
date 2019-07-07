package com.vidyo.bo;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;

public class SHA1 {

    public static String enc (String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (text == null) {
            return null;
        }
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash;
        md.update(text.getBytes("UTF-8"), 0, text.getBytes("UTF-8").length);
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (byte aData : data) {
            int halfbyte = (aData >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = aData & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String generateKey(int total_chars){
        StringBuffer rc = new StringBuffer();
		String salt = "abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
		int len = salt.length();
		for (int i = 0; i < total_chars; i++){
            int startIndex = 0;
            do {
                startIndex = (int)(Math.random()*1000) % len;
                if (startIndex <= len - 1) break;
            } while (true);
			rc.append(salt.substring(startIndex, startIndex+1));
		}
		return rc.toString();
	}

}
