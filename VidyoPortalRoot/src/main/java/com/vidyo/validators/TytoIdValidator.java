package com.vidyo.validators;

public class TytoIdValidator {
	
    public static final String ALLOWED_PATTERN = "^[A-Za-z0-9\\.:_\\-@]*$";

    public static boolean isValid(String candidate) {
        return isValid(candidate, ALLOWED_PATTERN);
    }

    public static boolean isValid(String candidate, String pattern) {
        if (null == candidate) {
            return false;
        } else {
            return candidate.matches(pattern);
        }
    }

    public static String truncateStationGUID(String s) {
        if (null == s) {
            return "";
        } else {
            String truncated = s.trim();
            if (truncated.length() > 40) {
                truncated = truncated.replaceFirst(".+?-","");
                return truncated.substring(0, truncated.length() > 40 ? 40: truncated.length());
            } else {
                return truncated;
            }
        }
    }
}
