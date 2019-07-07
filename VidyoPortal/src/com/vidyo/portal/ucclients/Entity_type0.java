/**
 * Entity_type0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:21:18 IST)
 */

package com.vidyo.portal.ucclients;

/**
 * Entity_type0 bean class
 */
@SuppressWarnings({ "unchecked", "unused" })
public class Entity_type0 implements org.apache.axis2.databinding.ADBBean {
	/*
	 * This type was generated from the piece of schema that had name =
	 * Entity_type0 Namespace URI = http://portal.vidyo.com/ucclients Namespace
	 * Prefix = ns4
	 */

	/**
	 * field for EntityID
	 */

	protected com.vidyo.portal.ucclients.EntityID localEntityID;

	/**
	 * Auto generated getter method
	 * 
	 * @return com.vidyo.portal.ucclients.EntityID
	 */
	public com.vidyo.portal.ucclients.EntityID getEntityID() {
		return localEntityID;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            EntityID
	 */
	public void setEntityID(com.vidyo.portal.ucclients.EntityID param) {

		this.localEntityID = param;

	}

	/**
	 * field for EntityType
	 */

	protected com.vidyo.portal.ucclients.EntityType_type0 localEntityType;

	/**
	 * Auto generated getter method
	 * 
	 * @return com.vidyo.portal.ucclients.EntityType_type0
	 */
	public com.vidyo.portal.ucclients.EntityType_type0 getEntityType() {
		return localEntityType;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            EntityType
	 */
	public void setEntityType(com.vidyo.portal.ucclients.EntityType_type0 param) {

		this.localEntityType = param;

	}

	/**
	 * field for DisplayName
	 */

	protected java.lang.String localDisplayName;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDisplayName() {
		return localDisplayName;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            DisplayName
	 */
	public void setDisplayName(java.lang.String param) {

		this.localDisplayName = param;

	}

	/**
	 * field for Extension
	 */

	protected java.lang.String localExtension;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getExtension() {
		return localExtension;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Extension
	 */
	public void setExtension(java.lang.String param) {

		this.localExtension = param;

	}

	/**
	 * field for Tenant
	 */

	protected java.lang.String localTenant;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTenant() {
		return localTenant;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Tenant
	 */
	public void setTenant(java.lang.String param) {

		this.localTenant = param;

	}

	/**
	 * field for Description
	 */

	protected java.lang.String localDescription;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localDescriptionTracker = false;

	public boolean isDescriptionSpecified() {
		return localDescriptionTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDescription() {
		return localDescription;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Description
	 */
	public void setDescription(java.lang.String param) {
		localDescriptionTracker = param != null;

		this.localDescription = param;

	}

	/**
	 * field for Language
	 */

	protected com.vidyo.portal.ucclients.Language_type0 localLanguage;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localLanguageTracker = false;

	public boolean isLanguageSpecified() {
		return localLanguageTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return com.vidyo.portal.ucclients.Language_type0
	 */
	public com.vidyo.portal.ucclients.Language_type0 getLanguage() {
		return localLanguage;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Language
	 */
	public void setLanguage(com.vidyo.portal.ucclients.Language_type0 param) {
		localLanguageTracker = param != null;

		this.localLanguage = param;

	}

	/**
	 * field for MemberStatus
	 */

	protected com.vidyo.portal.ucclients.MemberStatus_type0 localMemberStatus;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localMemberStatusTracker = false;

	public boolean isMemberStatusSpecified() {
		return localMemberStatusTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return com.vidyo.portal.ucclients.MemberStatus_type0
	 */
	public com.vidyo.portal.ucclients.MemberStatus_type0 getMemberStatus() {
		return localMemberStatus;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            MemberStatus
	 */
	public void setMemberStatus(
			com.vidyo.portal.ucclients.MemberStatus_type0 param) {
		localMemberStatusTracker = param != null;

		this.localMemberStatus = param;

	}

	/**
	 * field for MemberMode
	 */

	protected com.vidyo.portal.ucclients.MemberMode_type0 localMemberMode;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localMemberModeTracker = false;

	public boolean isMemberModeSpecified() {
		return localMemberModeTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return com.vidyo.portal.ucclients.MemberMode_type0
	 */
	public com.vidyo.portal.ucclients.MemberMode_type0 getMemberMode() {
		return localMemberMode;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            MemberMode
	 */
	public void setMemberMode(com.vidyo.portal.ucclients.MemberMode_type0 param) {
		localMemberModeTracker = param != null;

		this.localMemberMode = param;

	}

	/**
	 * field for CanCallDirect
	 */

	protected boolean localCanCallDirect;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localCanCallDirectTracker = false;

	public boolean isCanCallDirectSpecified() {
		return localCanCallDirectTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getCanCallDirect() {
		return localCanCallDirect;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            CanCallDirect
	 */
	public void setCanCallDirect(boolean param) {

		// setting primitive attribute tracker to true
		localCanCallDirectTracker = true;

		this.localCanCallDirect = param;

	}

	/**
	 * field for CanJoinMeeting
	 */

	protected boolean localCanJoinMeeting;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localCanJoinMeetingTracker = false;

	public boolean isCanJoinMeetingSpecified() {
		return localCanJoinMeetingTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getCanJoinMeeting() {
		return localCanJoinMeeting;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            CanJoinMeeting
	 */
	public void setCanJoinMeeting(boolean param) {

		// setting primitive attribute tracker to true
		localCanJoinMeetingTracker = true;

		this.localCanJoinMeeting = param;

	}

	/**
	 * field for RoomStatus
	 */

	protected com.vidyo.portal.ucclients.RoomStatus_type0 localRoomStatus;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localRoomStatusTracker = false;

	public boolean isRoomStatusSpecified() {
		return localRoomStatusTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return com.vidyo.portal.ucclients.RoomStatus_type0
	 */
	public com.vidyo.portal.ucclients.RoomStatus_type0 getRoomStatus() {
		return localRoomStatus;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            RoomStatus
	 */
	public void setRoomStatus(com.vidyo.portal.ucclients.RoomStatus_type0 param) {
		localRoomStatusTracker = param != null;

		this.localRoomStatus = param;

	}

	/**
	 * field for RoomMode
	 */

	protected com.vidyo.portal.ucclients.RoomMode_type0 localRoomMode;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localRoomModeTracker = false;

	public boolean isRoomModeSpecified() {
		return localRoomModeTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return com.vidyo.portal.ucclients.RoomMode_type0
	 */
	public com.vidyo.portal.ucclients.RoomMode_type0 getRoomMode() {
		return localRoomMode;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            RoomMode
	 */
	public void setRoomMode(com.vidyo.portal.ucclients.RoomMode_type0 param) {
		localRoomModeTracker = param != null;

		this.localRoomMode = param;

	}

	/**
	 * field for CanControl
	 */

	protected boolean localCanControl;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localCanControlTracker = false;

	public boolean isCanControlSpecified() {
		return localCanControlTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getCanControl() {
		return localCanControl;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            CanControl
	 */
	public void setCanControl(boolean param) {

		// setting primitive attribute tracker to true
		localCanControlTracker = true;

		this.localCanControl = param;

	}

	/**
	 * field for Audio
	 */

	protected boolean localAudio;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localAudioTracker = false;

	public boolean isAudioSpecified() {
		return localAudioTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getAudio() {
		return localAudio;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Audio
	 */
	public void setAudio(boolean param) {
		localAudioTracker = true;

		this.localAudio = param;

	}

	/**
	 * field for Video
	 */

	protected boolean localVideo;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localVideoTracker = false;

	public boolean isVideoSpecified() {
		return localVideoTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getVideo() {
		return localVideo;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Video
	 */
	public void setVideo(boolean param) {
		localVideoTracker = true;

		this.localVideo = param;

	}

	/**
	 * field for Appshare
	 */

	protected boolean localAppshare;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localAppshareTracker = false;

	public boolean isAppshareSpecified() {
		return localAppshareTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getAppshare() {
		return localAppshare;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Appshare
	 */
	public void setAppshare(boolean param) {
		localAppshareTracker = true;

		this.localAppshare = param;

	}

	/**
	 * 
	 * @param parentQName
	 * @param factory
	 * @return org.apache.axiom.om.OMElement
	 */
	public org.apache.axiom.om.OMElement getOMElement(
			final javax.xml.namespace.QName parentQName,
			final org.apache.axiom.om.OMFactory factory)
			throws org.apache.axis2.databinding.ADBException {

		org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
				this, parentQName);
		return factory.createOMElement(dataSource, parentQName);

	}

	public void serialize(final javax.xml.namespace.QName parentQName,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
		serialize(parentQName, xmlWriter, false);
	}

	public void serialize(final javax.xml.namespace.QName parentQName,
			javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
			throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {

		java.lang.String prefix = null;
		java.lang.String namespace = null;

		prefix = parentQName.getPrefix();
		namespace = parentQName.getNamespaceURI();
		writeStartElement(prefix, namespace, parentQName.getLocalPart(),
				xmlWriter);

		if (serializeType) {

			java.lang.String namespacePrefix = registerPrefix(xmlWriter,
					"http://portal.vidyo.com/ucclients");
			if ((namespacePrefix != null)
					&& (namespacePrefix.trim().length() > 0)) {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						namespacePrefix + ":Entity_type0", xmlWriter);
			} else {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						"Entity_type0", xmlWriter);
			}

		}

		if (localEntityID == null) {
			throw new org.apache.axis2.databinding.ADBException(
					"entityID cannot be null!!");
		}
		localEntityID.serialize(new javax.xml.namespace.QName(
				"http://portal.vidyo.com/ucclients", "entityID"), xmlWriter);

		if (localEntityType == null) {
			throw new org.apache.axis2.databinding.ADBException(
					"EntityType cannot be null!!");
		}
		localEntityType.serialize(new javax.xml.namespace.QName(
				"http://portal.vidyo.com/ucclients", "EntityType"), xmlWriter);

		namespace = "http://portal.vidyo.com/ucclients";
		writeStartElement(null, namespace, "displayName", xmlWriter);

		if (localDisplayName == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"displayName cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localDisplayName);

		}

		xmlWriter.writeEndElement();

		namespace = "http://portal.vidyo.com/ucclients";
		writeStartElement(null, namespace, "extension", xmlWriter);

		if (localExtension == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"extension cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localExtension);

		}

		xmlWriter.writeEndElement();

		namespace = "http://portal.vidyo.com/ucclients";
		writeStartElement(null, namespace, "tenant", xmlWriter);

		if (localTenant == null) {
			// write the nil attribute

			throw new org.apache.axis2.databinding.ADBException(
					"tenant cannot be null!!");

		} else {

			xmlWriter.writeCharacters(localTenant);

		}

		xmlWriter.writeEndElement();
		if (localDescriptionTracker) {
			namespace = "http://portal.vidyo.com/ucclients";
			writeStartElement(null, namespace, "description", xmlWriter);

			if (localDescription == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"description cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localDescription);

			}

			xmlWriter.writeEndElement();
		}
		if (localLanguageTracker) {
			if (localLanguage == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Language cannot be null!!");
			}
			localLanguage
					.serialize(new javax.xml.namespace.QName(
							"http://portal.vidyo.com/ucclients", "Language"),
							xmlWriter);
		}
		if (localMemberStatusTracker) {
			if (localMemberStatus == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MemberStatus cannot be null!!");
			}
			localMemberStatus.serialize(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "MemberStatus"),
					xmlWriter);
		}
		if (localMemberModeTracker) {
			if (localMemberMode == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MemberMode cannot be null!!");
			}
			localMemberMode.serialize(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "MemberMode"),
					xmlWriter);
		}
		if (localCanCallDirectTracker) {
			namespace = "http://portal.vidyo.com/ucclients";
			writeStartElement(null, namespace, "canCallDirect", xmlWriter);

			if (false) {

				throw new org.apache.axis2.databinding.ADBException(
						"canCallDirect cannot be null!!");

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localCanCallDirect));
			}

			xmlWriter.writeEndElement();
		}
		if (localCanJoinMeetingTracker) {
			namespace = "http://portal.vidyo.com/ucclients";
			writeStartElement(null, namespace, "canJoinMeeting", xmlWriter);

			if (false) {

				throw new org.apache.axis2.databinding.ADBException(
						"canJoinMeeting cannot be null!!");

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localCanJoinMeeting));
			}

			xmlWriter.writeEndElement();
		}
		if (localRoomStatusTracker) {
			if (localRoomStatus == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"RoomStatus cannot be null!!");
			}
			localRoomStatus.serialize(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "RoomStatus"),
					xmlWriter);
		}
		if (localRoomModeTracker) {
			if (localRoomMode == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"RoomMode cannot be null!!");
			}
			localRoomMode
					.serialize(new javax.xml.namespace.QName(
							"http://portal.vidyo.com/ucclients", "RoomMode"),
							xmlWriter);
		}
		if (localCanControlTracker) {
			namespace = "http://portal.vidyo.com/ucclients";
			writeStartElement(null, namespace, "canControl", xmlWriter);

			if (false) {

				throw new org.apache.axis2.databinding.ADBException(
						"canControl cannot be null!!");

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localCanControl));
			}

			xmlWriter.writeEndElement();
		}
		if (localAudioTracker) {
			namespace = "http://portal.vidyo.com/ucclients";
			writeStartElement(null, namespace, "audio", xmlWriter);

			if (false) {

				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "nil",
						"1", xmlWriter);

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localAudio));
			}

			xmlWriter.writeEndElement();
		}
		if (localVideoTracker) {
			namespace = "http://portal.vidyo.com/ucclients";
			writeStartElement(null, namespace, "video", xmlWriter);

			if (false) {

				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "nil",
						"1", xmlWriter);

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localVideo));
			}

			xmlWriter.writeEndElement();
		}
		if (localAppshareTracker) {
			namespace = "http://portal.vidyo.com/ucclients";
			writeStartElement(null, namespace, "appshare", xmlWriter);

			if (false) {

				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "nil",
						"1", xmlWriter);

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localAppshare));
			}

			xmlWriter.writeEndElement();
		}
		xmlWriter.writeEndElement();

	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {
		if (namespace.equals("http://portal.vidyo.com/ucclients")) {
			return "ns4";
		}
		return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
	}

	/**
	 * Utility method to write an element start tag.
	 */
	private void writeStartElement(java.lang.String prefix,
			java.lang.String namespace, java.lang.String localPart,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
		if (writerPrefix != null) {
			xmlWriter.writeStartElement(namespace, localPart);
		} else {
			if (namespace.length() == 0) {
				prefix = "";
			} else if (prefix == null) {
				prefix = generatePrefix(namespace);
			}

			xmlWriter.writeStartElement(prefix, localPart, namespace);
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
	}

	/**
	 * Util method to write an attribute with the ns prefix
	 */
	private void writeAttribute(java.lang.String prefix,
			java.lang.String namespace, java.lang.String attName,
			java.lang.String attValue,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		if (xmlWriter.getPrefix(namespace) == null) {
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
		xmlWriter.writeAttribute(namespace, attName, attValue);
	}

	/**
	 * Util method to write an attribute without the ns prefix
	 */
	private void writeAttribute(java.lang.String namespace,
			java.lang.String attName, java.lang.String attValue,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		if (namespace.equals("")) {
			xmlWriter.writeAttribute(attName, attValue);
		} else {
			registerPrefix(xmlWriter, namespace);
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}
	}

	/**
	 * Util method to write an attribute without the ns prefix
	 */
	private void writeQNameAttribute(java.lang.String namespace,
			java.lang.String attName, javax.xml.namespace.QName qname,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {

		java.lang.String attributeNamespace = qname.getNamespaceURI();
		java.lang.String attributePrefix = xmlWriter
				.getPrefix(attributeNamespace);
		if (attributePrefix == null) {
			attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
		}
		java.lang.String attributeValue;
		if (attributePrefix.trim().length() > 0) {
			attributeValue = attributePrefix + ":" + qname.getLocalPart();
		} else {
			attributeValue = qname.getLocalPart();
		}

		if (namespace.equals("")) {
			xmlWriter.writeAttribute(attName, attributeValue);
		} else {
			registerPrefix(xmlWriter, namespace);
			xmlWriter.writeAttribute(namespace, attName, attributeValue);
		}
	}

	/**
	 * method to handle Qnames
	 */

	private void writeQName(javax.xml.namespace.QName qname,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String namespaceURI = qname.getNamespaceURI();
		if (namespaceURI != null) {
			java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
			if (prefix == null) {
				prefix = generatePrefix(namespaceURI);
				xmlWriter.writeNamespace(prefix, namespaceURI);
				xmlWriter.setPrefix(prefix, namespaceURI);
			}

			if (prefix.trim().length() > 0) {
				xmlWriter.writeCharacters(prefix
						+ ":"
						+ org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			} else {
				// i.e this is the default namespace
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}

		} else {
			xmlWriter
					.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(qname));
		}
	}

	private void writeQNames(javax.xml.namespace.QName[] qnames,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {

		if (qnames != null) {
			// we have to store this data until last moment since it is not
			// possible to write any
			// namespace data after writing the charactor data
			java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
			java.lang.String namespaceURI = null;
			java.lang.String prefix = null;

			for (int i = 0; i < qnames.length; i++) {
				if (i > 0) {
					stringToWrite.append(" ");
				}
				namespaceURI = qnames[i].getNamespaceURI();
				if (namespaceURI != null) {
					prefix = xmlWriter.getPrefix(namespaceURI);
					if ((prefix == null) || (prefix.length() == 0)) {
						prefix = generatePrefix(namespaceURI);
						xmlWriter.writeNamespace(prefix, namespaceURI);
						xmlWriter.setPrefix(prefix, namespaceURI);
					}

					if (prefix.trim().length() > 0) {
						stringToWrite
								.append(prefix)
								.append(":")
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				} else {
					stringToWrite
							.append(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qnames[i]));
				}
			}
			xmlWriter.writeCharacters(stringToWrite.toString());
		}

	}

	/**
	 * Register a namespace prefix
	 */
	private java.lang.String registerPrefix(
			javax.xml.stream.XMLStreamWriter xmlWriter,
			java.lang.String namespace)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String prefix = xmlWriter.getPrefix(namespace);
		if (prefix == null) {
			prefix = generatePrefix(namespace);
			while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
				prefix = org.apache.axis2.databinding.utils.BeanUtil
						.getUniquePrefix();
			}
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
		return prefix;
	}

	/**
	 * databinding method to get an XML representation of this object
	 * 
	 */
	public javax.xml.stream.XMLStreamReader getPullParser(
			javax.xml.namespace.QName qName)
			throws org.apache.axis2.databinding.ADBException {

		java.util.ArrayList elementList = new java.util.ArrayList();
		java.util.ArrayList attribList = new java.util.ArrayList();

		elementList.add(new javax.xml.namespace.QName(
				"http://portal.vidyo.com/ucclients", "entityID"));

		if (localEntityID == null) {
			throw new org.apache.axis2.databinding.ADBException(
					"entityID cannot be null!!");
		}
		elementList.add(localEntityID);

		elementList.add(new javax.xml.namespace.QName(
				"http://portal.vidyo.com/ucclients", "EntityType"));

		if (localEntityType == null) {
			throw new org.apache.axis2.databinding.ADBException(
					"EntityType cannot be null!!");
		}
		elementList.add(localEntityType);

		elementList.add(new javax.xml.namespace.QName(
				"http://portal.vidyo.com/ucclients", "displayName"));

		if (localDisplayName != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localDisplayName));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"displayName cannot be null!!");
		}

		elementList.add(new javax.xml.namespace.QName(
				"http://portal.vidyo.com/ucclients", "extension"));

		if (localExtension != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localExtension));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"extension cannot be null!!");
		}

		elementList.add(new javax.xml.namespace.QName(
				"http://portal.vidyo.com/ucclients", "tenant"));

		if (localTenant != null) {
			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localTenant));
		} else {
			throw new org.apache.axis2.databinding.ADBException(
					"tenant cannot be null!!");
		}
		if (localDescriptionTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "description"));

			if (localDescription != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localDescription));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"description cannot be null!!");
			}
		}
		if (localLanguageTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "Language"));

			if (localLanguage == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Language cannot be null!!");
			}
			elementList.add(localLanguage);
		}
		if (localMemberStatusTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "MemberStatus"));

			if (localMemberStatus == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MemberStatus cannot be null!!");
			}
			elementList.add(localMemberStatus);
		}
		if (localMemberModeTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "MemberMode"));

			if (localMemberMode == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MemberMode cannot be null!!");
			}
			elementList.add(localMemberMode);
		}
		if (localCanCallDirectTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "canCallDirect"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localCanCallDirect));
		}
		if (localCanJoinMeetingTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "canJoinMeeting"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localCanJoinMeeting));
		}
		if (localRoomStatusTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "RoomStatus"));

			if (localRoomStatus == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"RoomStatus cannot be null!!");
			}
			elementList.add(localRoomStatus);
		}
		if (localRoomModeTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "RoomMode"));

			if (localRoomMode == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"RoomMode cannot be null!!");
			}
			elementList.add(localRoomMode);
		}
		if (localCanControlTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "canControl"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localCanControl));
		}
		if (localAudioTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "audio"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localAudio));
		}
		if (localVideoTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "video"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localVideo));
		}
		if (localAppshareTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://portal.vidyo.com/ucclients", "appshare"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localAppshare));
		}

		return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
				qName, elementList.toArray(), attribList.toArray());

	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

		/**
		 * static method to create the object Precondition: If this object is an
		 * element, the current or next start element starts this object and any
		 * intervening reader events are ignorable If this object is not an
		 * element, it is a complex type and the reader is at the event just
		 * after the outer start element Postcondition: If this object is an
		 * element, the reader is positioned at its end element If this object
		 * is a complex type, the reader is positioned at the end element of its
		 * outer element
		 */
		public static Entity_type0 parse(javax.xml.stream.XMLStreamReader reader)
				throws java.lang.Exception {
			Entity_type0 object = new Entity_type0();

			int event;
			java.lang.String nillableValue = null;
			java.lang.String prefix = "";
			java.lang.String namespaceuri = "";
			try {

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.getAttributeValue(
						"http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
					java.lang.String fullTypeName = reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type");
					if (fullTypeName != null) {
						java.lang.String nsPrefix = null;
						if (fullTypeName.indexOf(":") > -1) {
							nsPrefix = fullTypeName.substring(0,
									fullTypeName.indexOf(":"));
						}
						nsPrefix = nsPrefix == null ? "" : nsPrefix;

						java.lang.String type = fullTypeName
								.substring(fullTypeName.indexOf(":") + 1);

						if (!"Entity_type0".equals(type)) {
							// find namespace for the prefix
							java.lang.String nsUri = reader
									.getNamespaceContext().getNamespaceURI(
											nsPrefix);
							return (Entity_type0) com.vidyo.portal.ucclients.ExtensionMapper
									.getTypeObject(nsUri, type, reader);
						}

					}

				}

				// Note all attributes that were handled. Used to differ normal
				// attributes
				// from anyAttributes.
				java.util.Vector handledAttributes = new java.util.Vector();

				reader.next();

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients", "entityID")
								.equals(reader.getName())) {

					object.setEntityID(com.vidyo.portal.ucclients.EntityID.Factory
							.parse(reader));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"EntityType").equals(reader.getName())) {

					object.setEntityType(com.vidyo.portal.ucclients.EntityType_type0.Factory
							.parse(reader));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"displayName").equals(reader.getName())) {

					java.lang.String content = reader.getElementText();

					object.setDisplayName(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"extension").equals(reader.getName())) {

					java.lang.String content = reader.getElementText();

					object.setExtension(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients", "tenant")
								.equals(reader.getName())) {

					java.lang.String content = reader.getElementText();

					object.setTenant(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {
					// A start element we are not expecting indicates an invalid
					// parameter was passed
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());
				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"description").equals(reader.getName())) {

					java.lang.String content = reader.getElementText();

					object.setDescription(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients", "Language")
								.equals(reader.getName())) {

					object.setLanguage(com.vidyo.portal.ucclients.Language_type0.Factory
							.parse(reader));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"MemberStatus").equals(reader.getName())) {

					object.setMemberStatus(com.vidyo.portal.ucclients.MemberStatus_type0.Factory
							.parse(reader));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"MemberMode").equals(reader.getName())) {

					object.setMemberMode(com.vidyo.portal.ucclients.MemberMode_type0.Factory
							.parse(reader));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"canCallDirect").equals(reader.getName())) {

					java.lang.String content = reader.getElementText();

					object.setCanCallDirect(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToBoolean(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"canJoinMeeting").equals(reader.getName())) {

					java.lang.String content = reader.getElementText();

					object.setCanJoinMeeting(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToBoolean(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"RoomStatus").equals(reader.getName())) {

					object.setRoomStatus(com.vidyo.portal.ucclients.RoomStatus_type0.Factory
							.parse(reader));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients", "RoomMode")
								.equals(reader.getName())) {

					object.setRoomMode(com.vidyo.portal.ucclients.RoomMode_type0.Factory
							.parse(reader));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients",
								"canControl").equals(reader.getName())) {

					java.lang.String content = reader.getElementText();

					object.setCanControl(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToBoolean(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients", "audio")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if (!"true".equals(nillableValue)
							&& !"1".equals(nillableValue)) {

						java.lang.String content = reader.getElementText();

						object.setAudio(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToBoolean(content));

					} else {

						reader.getElementText(); // throw away text nodes if
													// any.
					}

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients", "video")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if (!"true".equals(nillableValue)
							&& !"1".equals(nillableValue)) {

						java.lang.String content = reader.getElementText();

						object.setVideo(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToBoolean(content));

					} else {

						reader.getElementText(); // throw away text nodes if
													// any.
					}

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://portal.vidyo.com/ucclients", "appshare")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if (!"true".equals(nillableValue)
							&& !"1".equals(nillableValue)) {

						java.lang.String content = reader.getElementText();

						object.setAppshare(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToBoolean(content));

					} else {

						reader.getElementText(); // throw away text nodes if
													// any.
					}

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement())
					// A start element we are not expecting indicates a trailing
					// invalid property
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());

			} catch (javax.xml.stream.XMLStreamException e) {
				throw new java.lang.Exception(e);
			}

			return object;
		}

	}// end of factory class

}
