package com.vidyo.service.configuration.enums;

public enum ExternalDataTypeEnum {

	VIDYO, // 0
	EPIC, // 1
	TYTOCARE; // 2 
	
	public static ExternalDataTypeEnum get(String value) {
		return ExternalDataTypeEnum.valueOf(value);
	}

	public static int validateExtDataType(int v) {
	    if (v == VIDYO.ordinal() || v == EPIC.ordinal() || v == TYTOCARE.ordinal()) {
	        return v;
        } else {
	        return VIDYO.ordinal();
        }
    }

	public static Integer validateExtDataType(Object originalValue) {
		if (null != originalValue) {
			try {
				String str = originalValue.toString();
				return validateExtDataType(Integer.parseInt(str));
			} catch (NumberFormatException nfe) {
				return VIDYO.ordinal();
			}

		} else {
			return VIDYO.ordinal();
		}
	}
	
	public static boolean isValidEpicExtDataType(String str) {
		try {
			int value = Integer.parseInt(str);
			return (value == EPIC.ordinal()) ? true : false;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}
