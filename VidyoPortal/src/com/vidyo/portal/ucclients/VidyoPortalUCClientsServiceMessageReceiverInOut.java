/**
 * VidyoPortalUCClientsServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.portal.ucclients;

/**
 * VidyoPortalUCClientsServiceMessageReceiverInOut message receiver
 */

public class VidyoPortalUCClientsServiceMessageReceiverInOut extends
		org.apache.axis2.receivers.AbstractInOutMessageReceiver {

	public void invokeBusinessLogic(
			org.apache.axis2.context.MessageContext msgContext,
			org.apache.axis2.context.MessageContext newMsgContext)
			throws org.apache.axis2.AxisFault {

		try {

			// get the implementation class for the Web Service
			Object obj = getTheImplementationObject(msgContext);

			VidyoPortalUCClientsServiceSkeletonInterface skel = (VidyoPortalUCClientsServiceSkeletonInterface) obj;
			// Out Envelop
			org.apache.axiom.soap.SOAPEnvelope envelope = null;
			// Find the axisOperation that has been set by the Dispatch phase.
			org.apache.axis2.description.AxisOperation op = msgContext
					.getOperationContext().getAxisOperation();
			if (op == null) {
				throw new org.apache.axis2.AxisFault(
						"Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
			}

			java.lang.String methodName;
			if ((op.getName() != null)
					&& ((methodName = org.apache.axis2.util.JavaUtils
							.xmlNameToJavaIdentifier(op.getName()
									.getLocalPart())) != null)) {

				if ("getUserStatus".equals(methodName)) {

					com.vidyo.portal.ucclients.GetUserStatusResponse getUserStatusResponse15 = null;
					com.vidyo.portal.ucclients.GetUserStatusRequest wrappedParam = (com.vidyo.portal.ucclients.GetUserStatusRequest) fromOM(
							msgContext.getEnvelope().getBody()
									.getFirstElement(),
							com.vidyo.portal.ucclients.GetUserStatusRequest.class,
							getEnvelopeNamespaces(msgContext.getEnvelope()));

					getUserStatusResponse15 =

					skel.getUserStatus(wrappedParam);

					envelope = toEnvelope(getSOAPFactory(msgContext),
							getUserStatusResponse15, false,
							new javax.xml.namespace.QName(
									"http://portal.vidyo.com/ucclients",
									"getUserStatus"));
				} else

				if ("createMyRoomURL".equals(methodName)) {

					com.vidyo.portal.ucclients.CreateMyRoomURLResponse createMyRoomURLResponse17 = null;
					com.vidyo.portal.ucclients.CreateMyRoomURLRequest wrappedParam = (com.vidyo.portal.ucclients.CreateMyRoomURLRequest) fromOM(
							msgContext.getEnvelope().getBody()
									.getFirstElement(),
							com.vidyo.portal.ucclients.CreateMyRoomURLRequest.class,
							getEnvelopeNamespaces(msgContext.getEnvelope()));

					createMyRoomURLResponse17 =

					skel.createMyRoomURL(wrappedParam);

					envelope = toEnvelope(getSOAPFactory(msgContext),
							createMyRoomURLResponse17, false,
							new javax.xml.namespace.QName(
									"http://portal.vidyo.com/ucclients",
									"createMyRoomURL"));
				} else

				if ("startMyConference".equals(methodName)) {

					com.vidyo.portal.ucclients.StartMyConferenceResponse startMyConferenceResponse19 = null;
					com.vidyo.portal.ucclients.StartMyConferenceRequest wrappedParam = (com.vidyo.portal.ucclients.StartMyConferenceRequest) fromOM(
							msgContext.getEnvelope().getBody()
									.getFirstElement(),
							com.vidyo.portal.ucclients.StartMyConferenceRequest.class,
							getEnvelopeNamespaces(msgContext.getEnvelope()));

					startMyConferenceResponse19 =

					skel.startMyConference(wrappedParam);

					envelope = toEnvelope(getSOAPFactory(msgContext),
							startMyConferenceResponse19, false,
							new javax.xml.namespace.QName(
									"http://portal.vidyo.com/ucclients",
									"startMyConference"));
				} else

				if ("getBrowserAccessKey".equals(methodName)) {

					com.vidyo.portal.ucclients.BrowserAccessKeyResponse browserAccessKeyResponse21 = null;
					com.vidyo.portal.ucclients.BrowserAccessKeyRequest wrappedParam = (com.vidyo.portal.ucclients.BrowserAccessKeyRequest) fromOM(
							msgContext.getEnvelope().getBody()
									.getFirstElement(),
							com.vidyo.portal.ucclients.BrowserAccessKeyRequest.class,
							getEnvelopeNamespaces(msgContext.getEnvelope()));

					browserAccessKeyResponse21 =

					skel.getBrowserAccessKey(wrappedParam);

					envelope = toEnvelope(getSOAPFactory(msgContext),
							browserAccessKeyResponse21, false,
							new javax.xml.namespace.QName(
									"http://portal.vidyo.com/ucclients",
									"getBrowserAccessKey"));
				} else

				if ("joinConference".equals(methodName)) {

					com.vidyo.portal.ucclients.JoinConferenceResponse joinConferenceResponse23 = null;
					com.vidyo.portal.ucclients.JoinConferenceRequest wrappedParam = (com.vidyo.portal.ucclients.JoinConferenceRequest) fromOM(
							msgContext.getEnvelope().getBody()
									.getFirstElement(),
							com.vidyo.portal.ucclients.JoinConferenceRequest.class,
							getEnvelopeNamespaces(msgContext.getEnvelope()));

					joinConferenceResponse23 =

					skel.joinConference(wrappedParam);

					envelope = toEnvelope(getSOAPFactory(msgContext),
							joinConferenceResponse23, false,
							new javax.xml.namespace.QName(
									"http://portal.vidyo.com/ucclients",
									"joinConference"));
				} else

				if ("getUserData".equals(methodName)) {

					com.vidyo.portal.ucclients.GetUserDataResponse getUserDataResponse25 = null;
					com.vidyo.portal.ucclients.GetUserDataRequest wrappedParam = (com.vidyo.portal.ucclients.GetUserDataRequest) fromOM(
							msgContext.getEnvelope().getBody()
									.getFirstElement(),
							com.vidyo.portal.ucclients.GetUserDataRequest.class,
							getEnvelopeNamespaces(msgContext.getEnvelope()));

					getUserDataResponse25 =

					skel.getUserData(wrappedParam);

					envelope = toEnvelope(getSOAPFactory(msgContext),
							getUserDataResponse25, false,
							new javax.xml.namespace.QName(
									"http://portal.vidyo.com/ucclients",
									"getUserData"));
				} else

				if ("inviteToConference".equals(methodName)) {

					com.vidyo.portal.ucclients.InviteToConferenceResponse inviteToConferenceResponse27 = null;
					com.vidyo.portal.ucclients.InviteToConferenceRequest wrappedParam = (com.vidyo.portal.ucclients.InviteToConferenceRequest) fromOM(
							msgContext.getEnvelope().getBody()
									.getFirstElement(),
							com.vidyo.portal.ucclients.InviteToConferenceRequest.class,
							getEnvelopeNamespaces(msgContext.getEnvelope()));

					inviteToConferenceResponse27 =

					skel.inviteToConference(wrappedParam);

					envelope = toEnvelope(getSOAPFactory(msgContext),
							inviteToConferenceResponse27, false,
							new javax.xml.namespace.QName(
									"http://portal.vidyo.com/ucclients",
									"inviteToConference"));

				} else {
					throw new java.lang.RuntimeException("method not found");
				}

				newMsgContext.setEnvelope(envelope);
			}
		} catch (NotLicensedFaultException e) {

			msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,
					"NotLicensedFault");
			org.apache.axis2.AxisFault f = createAxisFault(e);
			if (e.getFaultMessage() != null) {
				f.setDetail(toOM(e.getFaultMessage(), false));
			}
			throw f;
		} catch (InvalidArgumentFaultException e) {

			msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,
					"InvalidArgumentFault");
			org.apache.axis2.AxisFault f = createAxisFault(e);
			if (e.getFaultMessage() != null) {
				f.setDetail(toOM(e.getFaultMessage(), false));
			}
			throw f;
		} catch (GeneralFaultException e) {

			msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,
					"GeneralFault");
			org.apache.axis2.AxisFault f = createAxisFault(e);
			if (e.getFaultMessage() != null) {
				f.setDetail(toOM(e.getFaultMessage(), false));
			}
			throw f;
		} catch (RoomDisabledFaultException e) {

			msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,
					"RoomDisabledFault");
			org.apache.axis2.AxisFault f = createAxisFault(e);
			if (e.getFaultMessage() != null) {
				f.setDetail(toOM(e.getFaultMessage(), false));
			}
			throw f;
		} catch (SeatLicenseExpiredFaultException e) {

			msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,
					"SeatLicenseExpiredFault");
			org.apache.axis2.AxisFault f = createAxisFault(e);
			if (e.getFaultMessage() != null) {
				f.setDetail(toOM(e.getFaultMessage(), false));
			}
			throw f;
		}

		catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	//
	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.GetUserStatusRequest param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.GetUserStatusRequest.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.GetUserStatusResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.GetUserStatusResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.NotLicensedFault param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.NotLicensedFault.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.InvalidArgumentFault param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.InvalidArgumentFault.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.GeneralFault param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.GeneralFault.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.SeatLicenseExpiredFault param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							com.vidyo.portal.ucclients.SeatLicenseExpiredFault.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.CreateMyRoomURLRequest param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.CreateMyRoomURLRequest.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.CreateMyRoomURLResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							com.vidyo.portal.ucclients.CreateMyRoomURLResponse.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.StartMyConferenceRequest param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							com.vidyo.portal.ucclients.StartMyConferenceRequest.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.StartMyConferenceResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							com.vidyo.portal.ucclients.StartMyConferenceResponse.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.RoomDisabledFault param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.RoomDisabledFault.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.BrowserAccessKeyRequest param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							com.vidyo.portal.ucclients.BrowserAccessKeyRequest.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.BrowserAccessKeyResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							com.vidyo.portal.ucclients.BrowserAccessKeyResponse.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.JoinConferenceRequest param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.JoinConferenceRequest.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.JoinConferenceResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.JoinConferenceResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.GetUserDataRequest param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.GetUserDataRequest.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.GetUserDataResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.vidyo.portal.ucclients.GetUserDataResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.InviteToConferenceRequest param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							com.vidyo.portal.ucclients.InviteToConferenceRequest.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.vidyo.portal.ucclients.InviteToConferenceResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							com.vidyo.portal.ucclients.InviteToConferenceResponse.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			com.vidyo.portal.ucclients.GetUserStatusResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									com.vidyo.portal.ucclients.GetUserStatusResponse.MY_QNAME,
									factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private com.vidyo.portal.ucclients.GetUserStatusResponse wrapgetUserStatus() {
		com.vidyo.portal.ucclients.GetUserStatusResponse wrappedElement = new com.vidyo.portal.ucclients.GetUserStatusResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			com.vidyo.portal.ucclients.CreateMyRoomURLResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									com.vidyo.portal.ucclients.CreateMyRoomURLResponse.MY_QNAME,
									factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private com.vidyo.portal.ucclients.CreateMyRoomURLResponse wrapcreateMyRoomURL() {
		com.vidyo.portal.ucclients.CreateMyRoomURLResponse wrappedElement = new com.vidyo.portal.ucclients.CreateMyRoomURLResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			com.vidyo.portal.ucclients.StartMyConferenceResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									com.vidyo.portal.ucclients.StartMyConferenceResponse.MY_QNAME,
									factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private com.vidyo.portal.ucclients.StartMyConferenceResponse wrapstartMyConference() {
		com.vidyo.portal.ucclients.StartMyConferenceResponse wrappedElement = new com.vidyo.portal.ucclients.StartMyConferenceResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			com.vidyo.portal.ucclients.BrowserAccessKeyResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									com.vidyo.portal.ucclients.BrowserAccessKeyResponse.MY_QNAME,
									factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private com.vidyo.portal.ucclients.BrowserAccessKeyResponse wrapgetBrowserAccessKey() {
		com.vidyo.portal.ucclients.BrowserAccessKeyResponse wrappedElement = new com.vidyo.portal.ucclients.BrowserAccessKeyResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			com.vidyo.portal.ucclients.JoinConferenceResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									com.vidyo.portal.ucclients.JoinConferenceResponse.MY_QNAME,
									factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private com.vidyo.portal.ucclients.JoinConferenceResponse wrapjoinConference() {
		com.vidyo.portal.ucclients.JoinConferenceResponse wrappedElement = new com.vidyo.portal.ucclients.JoinConferenceResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			com.vidyo.portal.ucclients.GetUserDataResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									com.vidyo.portal.ucclients.GetUserDataResponse.MY_QNAME,
									factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private com.vidyo.portal.ucclients.GetUserDataResponse wrapgetUserData() {
		com.vidyo.portal.ucclients.GetUserDataResponse wrappedElement = new com.vidyo.portal.ucclients.GetUserDataResponse();
		return wrappedElement;
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			com.vidyo.portal.ucclients.InviteToConferenceResponse param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();

			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									com.vidyo.portal.ucclients.InviteToConferenceResponse.MY_QNAME,
									factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private com.vidyo.portal.ucclients.InviteToConferenceResponse wrapinviteToConference() {
		com.vidyo.portal.ucclients.InviteToConferenceResponse wrappedElement = new com.vidyo.portal.ucclients.InviteToConferenceResponse();
		return wrappedElement;
	}

	/**
	 * get the default envelope
	 */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory) {
		return factory.getDefaultEnvelope();
	}

	private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
			java.lang.Class type, java.util.Map extraNamespaces)
			throws org.apache.axis2.AxisFault {

		try {

			if (com.vidyo.portal.ucclients.GetUserStatusRequest.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.GetUserStatusRequest.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GetUserStatusResponse.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.GetUserStatusResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.NotLicensedFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.NotLicensedFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.InvalidArgumentFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.InvalidArgumentFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GeneralFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.GeneralFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.SeatLicenseExpiredFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.SeatLicenseExpiredFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.CreateMyRoomURLRequest.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.CreateMyRoomURLRequest.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.CreateMyRoomURLResponse.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.CreateMyRoomURLResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.NotLicensedFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.NotLicensedFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.InvalidArgumentFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.InvalidArgumentFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GeneralFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.GeneralFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.SeatLicenseExpiredFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.SeatLicenseExpiredFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.StartMyConferenceRequest.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.StartMyConferenceRequest.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.StartMyConferenceResponse.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.StartMyConferenceResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.NotLicensedFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.NotLicensedFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.InvalidArgumentFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.InvalidArgumentFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GeneralFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.GeneralFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.RoomDisabledFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.RoomDisabledFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.SeatLicenseExpiredFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.SeatLicenseExpiredFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.BrowserAccessKeyRequest.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.BrowserAccessKeyRequest.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.BrowserAccessKeyResponse.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.BrowserAccessKeyResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.NotLicensedFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.NotLicensedFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.InvalidArgumentFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.InvalidArgumentFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GeneralFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.GeneralFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.SeatLicenseExpiredFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.SeatLicenseExpiredFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.JoinConferenceRequest.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.JoinConferenceRequest.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.JoinConferenceResponse.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.JoinConferenceResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.NotLicensedFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.NotLicensedFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.InvalidArgumentFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.InvalidArgumentFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GeneralFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.GeneralFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.SeatLicenseExpiredFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.SeatLicenseExpiredFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GetUserDataRequest.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.GetUserDataRequest.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GetUserDataResponse.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.GetUserDataResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.NotLicensedFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.NotLicensedFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.InvalidArgumentFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.InvalidArgumentFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GeneralFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.GeneralFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.SeatLicenseExpiredFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.SeatLicenseExpiredFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.InviteToConferenceRequest.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.InviteToConferenceRequest.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.InviteToConferenceResponse.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.InviteToConferenceResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.NotLicensedFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.NotLicensedFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.InvalidArgumentFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.InvalidArgumentFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.GeneralFault.class.equals(type)) {

				return com.vidyo.portal.ucclients.GeneralFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (com.vidyo.portal.ucclients.SeatLicenseExpiredFault.class
					.equals(type)) {

				return com.vidyo.portal.ucclients.SeatLicenseExpiredFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}

	/**
	 * A utility method that copies the namepaces from the SOAPEnvelope
	 */
	private java.util.Map getEnvelopeNamespaces(
			org.apache.axiom.soap.SOAPEnvelope env) {
		java.util.Map returnMap = new java.util.HashMap();
		java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
		while (namespaceIterator.hasNext()) {
			org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator
					.next();
			returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
		}
		return returnMap;
	}

	private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
		org.apache.axis2.AxisFault f;
		Throwable cause = e.getCause();
		if (cause != null) {
			f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
		} else {
			f = new org.apache.axis2.AxisFault(e.getMessage());
		}

		return f;
	}

}// end of class
