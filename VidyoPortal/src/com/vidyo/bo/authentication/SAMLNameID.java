package com.vidyo.bo.authentication;

public enum SAMLNameID {
	EMAIL ("urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress"),
	TRANSIENT ("urn:oasis:names:tc:SAML:2.0:nameid-format:transient"),
	PERSISTENT("urn:oasis:names:tc:SAML:2.0:nameid-format:persistent"),
	UNSPECIFIED("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"),
	X509_SUBJECT("urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName");
	
	private String nameID;
	
	private SAMLNameID(String nameID) {
		this.nameID = nameID;
	}
	
	public String getNameID() {
		return nameID;
	}
	
	public static SAMLNameID fromString(String nameID) {
	    if (nameID != null && !nameID.isEmpty()) {
	      for (SAMLNameID samlNameID : SAMLNameID.values()) {
	        if (nameID.equalsIgnoreCase(samlNameID.nameID)) {
	          return samlNameID;
	        }
	      }
	    }
	    return null;
	  }

}
