package com.vidyo.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class SecureRandomUtils {
    protected static final Logger logger = LoggerFactory.getLogger(SecureRandomUtils.class);

    private static SecureRandom secureRandomGenerator = null;

    static {
        try {
            secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
            secureRandomGenerator.nextBytes(new byte[128]);
        } catch (GeneralSecurityException e) {
            logger.error("Error while creating secure random", e);
        }
    }

    public static long generateNumericHash() {
        return Math.abs(secureRandomGenerator.nextLong());
    }

    public static String generatePak2(int seriesLength){
        byte[] newSeries = new byte[seriesLength];
        if(secureRandomGenerator == null) {
            return null;
        }
        secureRandomGenerator.nextBytes(newSeries);
        return new String(Base64.encodeBase64(newSeries));
    }

    public static String generateHashKey(int seriesLength) {
        return generatePak2(seriesLength);
    }

    public static String generateRoomKey() {
        // The key is a part of the URL
        String key = generatePak2(20);
        if (key == null) {
            return null;
        }
        // Replace all the URL reserved characters -
        // http://tools.ietf.org/html/rfc3986#section-2
        key = key.replaceAll(":", "").replaceAll("/", "")
                .replaceAll("\\?", "").replaceAll("#", "")
                .replaceAll("\\[", "").replaceAll("\\]", "")
                .replaceAll("@", "").replaceAll("!", "").replaceAll("\\$", "")
                .replaceAll("&", "").replaceAll("'", "").replaceAll("\\(", "")
                .replaceAll("\\)", "").replaceAll("\\*", "")
                .replaceAll("\\+", "").replaceAll(",", "").replaceAll(";", "")
                .replaceAll("=", "");
        // The URL encoding is not done, as it will break the backward
        // compatability for older clients which doesn't do
        // decoding before sending to portal.
        return key;
    }
    
    public static String generateRoomKey(int roomKeyLength) {
    	return RandomStringUtils.random(roomKeyLength, 0, 0, true, true, null, secureRandomGenerator);
    }
    
    public static String generateRoomExtn(int length){
    	byte[] newSeries = new byte[128];
    	if(secureRandomGenerator == null) {
            return null;
        }
    	secureRandomGenerator.nextBytes(newSeries);
    	BigInteger bigInt = new BigInteger(newSeries); 
        return bigInt.abs().toString().substring(0, length);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(generateRoomKey(20));
        }
    }
}
