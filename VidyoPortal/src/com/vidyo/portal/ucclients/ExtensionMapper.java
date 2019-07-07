/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:21:18 IST)
 */

package com.vidyo.portal.ucclients;

/**
 * ExtensionMapper class
 */
@SuppressWarnings({ "unchecked", "unused" })
public class ExtensionMapper {

	public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
			java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
			throws java.lang.Exception {

		if ("http://portal.vidyo.com/ucclients".equals(namespaceURI)
				&& "RoomMode_type0".equals(typeName)) {

			return com.vidyo.portal.ucclients.RoomMode_type0.Factory
					.parse(reader);

		}

		if ("http://portal.vidyo.com/ucclients".equals(namespaceURI)
				&& "UserStatus_type0".equals(typeName)) {

			return com.vidyo.portal.ucclients.UserStatus_type0.Factory
					.parse(reader);

		}

		if ("http://portal.vidyo.com/ucclients".equals(namespaceURI)
				&& "Entity_type0".equals(typeName)) {

			return com.vidyo.portal.ucclients.Entity_type0.Factory
					.parse(reader);

		}

		if ("http://portal.vidyo.com/ucclients".equals(namespaceURI)
				&& "EntityID".equals(typeName)) {

			return com.vidyo.portal.ucclients.EntityID.Factory.parse(reader);

		}

		throw new org.apache.axis2.databinding.ADBException("Unsupported type "
				+ namespaceURI + " " + typeName);
	}

}
