package com.vidyo.framework.security.utils;

import com.vidyo.framework.security.utils.SHA1;

import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class VidyoHash {

    private static final String LIC_HASH_SHARED_KEY = "6jUlr37bKdO4wLcV";

    public static void main(String[] args) {
        //String mmm = fnXorChallengeResponse("fGz0dA26Kl1", "zbnxjhdHJHmndjhaj", 12);
        //String mmm = fnXorChallengeResponse("a", "a", 1);
        //String mmm = fnXorChallengeResponse("aa", "aa", 2);
        //System.out.println(mmm);

        String lac = null;
        try {
            lac = SHA1.enc(args[0]);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(lac);
    	
    	//String ret = RegisterLicenseResponse("ganesh", "              ");
    	//System.out.println(ret);    	
    }

    public static String getBindUserChallengeResponse(String challenge, String PAK) {
        String rc = "";
        try {
            rc = fnXorChallengeResponse(challenge, PAK, challenge.length());
        } catch (UnsupportedEncodingException ignored) {
        }
        return rc;
    }

    public static String getRegisterLicenseResponse(String username, String EID) {
        String rc = "";
        try {
            rc = fnXorLAC(username, EID, LIC_HASH_SHARED_KEY, LIC_HASH_SHARED_KEY.length());
        } catch (UnsupportedEncodingException ignored) {
        }
        return rc;
    }

    private static String fnXorLAC(String aStr1, String aStr2, String aStr3, int aLimitStrSize) throws UnsupportedEncodingException {

        // Make sure all strings have a value to operate against...
        if(aStr1.length() == 0 && aStr2.length() == 0 && aStr3.length() == 0) {
            return "";
        }

        // if the caller of this fn requires both strings to be a certain size,
        // extend the strings by concatenating the string with itself (left to right)
        // up to the required length (if string is smaller)
        // or chop the right side of the string off (if string is larger)...
        if (aLimitStrSize == 0) {
            return "";
        } else {
            byte holdVal;
            byte[] hash = stringHashVersionA(fnXORAdjustStrSize(aStr1.getBytes("UTF-8"), aLimitStrSize));
            byte[] str2 = fnXORAdjustStrSize(aStr2.getBytes("UTF-8"), aLimitStrSize);
            byte[] str3 = fnXORAdjustStrSize(aStr3.getBytes("UTF-8"), aLimitStrSize);
            for(int i = 0; i < aLimitStrSize; i++) {
                holdVal = (byte) (str2[i] ^ str3[i]);
                hash[i] = (byte) (holdVal ^ hash[i]);
            }

            return convertToHex(hash);
        }
    }

    private static String fnXorChallengeResponse(String aStr1, String aStr2, int aLimitStrSize) throws UnsupportedEncodingException {

        // Make sure all strings have a value to operate against...
        if(aStr1.length() == 0 && aStr2.length() == 0) {
            return "";
        }

        // if the caller of this fn requires both strings to be a certain size,
        // extend the strings by concatenating the string with itself (left to right)
        // up to the required length (if string is smaller)
        // or chop the right side of the string off (if string is larger)...
        if (aLimitStrSize == 0) {
            return "";		// ERROR - string lenght must be of some sort of significant size...
        } else {
            // Prior to applying the XOR - hash the challengeStringKey into a new value
            byte[] hash = stringHashVersionA(fnXORAdjustStrSize(aStr1.getBytes("UTF-8"), aLimitStrSize));
            byte[] xor_str = fnXORAdjustStrSize(aStr2.getBytes("UTF-8"), aLimitStrSize);

            for(int i = 0; i < aLimitStrSize; i++) {
                hash[i] = (byte) (hash[i] ^ xor_str[i]);
            }

            return convertToHex(hash);
        }
    }

    private static byte[] stringHashVersionA(byte[] aStringToHash) {
        // Limit the hash to 16 bytes worth...
        byte[] aHashedStr = new byte[aStringToHash.length];

        /********************************************************************/
        /*** Take the sum of the ascii values for all chars in the string ***/
        /*** - Will be used to generate the hash of the 1st character in  ***/
        /***   the result string                                          ***/
        /********************************************************************/
        for (byte anAStr : aStringToHash) {
            int sum = aHashedStr[0] + anAStr;
            aHashedStr[0] = (byte) sum;
        }
        /********************************************************************/
        /*** Following formula will generate a ascii range value between  ***/
        /*** hex x00 and xFF                                              ***/
        /********************************************************************/
        int holdResult = (aHashedStr[0] * 53) % 256;
        aHashedStr[0] = (byte) holdResult; //  Assign result to 1st array element

        /********************************************************************/
        /*** Starting w/ second input array element - add value of the    ***/
        /*** result array[index-1] to the inputted strings [index] value, ***/
        /*** then, multiply that value by the prime, and modulus it       ***/
        /*** against 256 to generate a "random" ascii range value for the ***/
        /*** new result array[index] value.
        /********************************************************************/
        for (int i = 1; i < aStringToHash.length; i++) {
            holdResult = ((aStringToHash[i] + aHashedStr[i-1]) * 53) % 256;
            aHashedStr[i] = (byte) holdResult;
        }

        return aHashedStr;
    }

    private static byte[] fnXORAdjustStrSize(byte[] aInStr, int aAdjustToSize) throws UnsupportedEncodingException {
        byte[] aOutBytes = new byte[aAdjustToSize];

        ArrayList<Byte> aBuf = new ArrayList<Byte>(aAdjustToSize);
        while (aBuf.size() < aAdjustToSize) {
        	if(aInStr.length == 0) {
        		break;
        	}
            for (byte anAInStr : aInStr) {
                aBuf.add(anAInStr);
                if (aBuf.size() == aAdjustToSize) {
                    break;
                }
            }
        }

        if(aBuf.size() > 0) {
            for (int i = 0; i < aAdjustToSize; i++) {
                aOutBytes[i] = aBuf.get(i);
            }        	
        }

        return aOutBytes;
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

}