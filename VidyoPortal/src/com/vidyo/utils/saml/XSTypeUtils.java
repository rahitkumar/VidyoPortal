package com.vidyo.utils.saml;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSAny;
import org.opensaml.xml.schema.XSBase64Binary;
import org.opensaml.xml.schema.XSBoolean;
import org.opensaml.xml.schema.XSDateTime;
import org.opensaml.xml.schema.XSInteger;
import org.opensaml.xml.schema.XSQName;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.XSURI;

public class XSTypeUtils {

	public static String getValue(XMLObject xmlObject) {
		String retVal = "";
		
		if(xmlObject instanceof XSAny) {
			retVal = ((XSAny)xmlObject).getTextContent();
		} else if (xmlObject instanceof XSBase64Binary) {
			retVal = ((XSBase64Binary)xmlObject).getValue();
		} else if (xmlObject instanceof XSBoolean) {
			retVal = ((XSBoolean)xmlObject).getValue().getValue().toString();
		} else if (xmlObject instanceof XSDateTime) {
			retVal = ((XSDateTime)xmlObject).getValue().toString();
		} else if (xmlObject instanceof XSInteger) {
			retVal = ((XSInteger)xmlObject).getValue().toString();
		} else if (xmlObject instanceof XSQName) {
			retVal = ((XSQName)xmlObject).getValue().toString();
		} else if (xmlObject instanceof XSString) {
			retVal = ((XSString)xmlObject).getValue();
		} else if (xmlObject instanceof XSURI) {
			retVal = ((XSURI)xmlObject).getValue();
		} 
		
		return retVal;
	}
}
