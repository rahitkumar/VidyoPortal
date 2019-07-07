package com.vidyo.bo.authentication;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SamlProvisionType {
	LOCAL		(0), 
	SAML		(1);
	
	private int value;
	
	private static final Map<Integer, SamlProvisionType> lookup = new HashMap<Integer, SamlProvisionType>();

    static {
        for(SamlProvisionType samlProvisionType : EnumSet.allOf(SamlProvisionType.class))
             lookup.put(samlProvisionType.getValue(), samlProvisionType);
    }
	
	private SamlProvisionType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static SamlProvisionType get(int value) {
		return lookup.get(value);
	}
}
