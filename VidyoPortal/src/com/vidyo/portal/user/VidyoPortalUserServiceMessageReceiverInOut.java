
/**
 * VidyoPortalUserServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.vidyo.portal.user;

        /**
        *  VidyoPortalUserServiceMessageReceiverInOut message receiver
        */

        public class VidyoPortalUserServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        VidyoPortalUserServiceSkeletonInterface skel = (VidyoPortalUserServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("joinIPCConference".equals(methodName)){
                
                com.vidyo.portal.user.JoinIPCConferenceResponse joinIPCConferenceResponse75 = null;
	                        com.vidyo.portal.user.JoinIPCConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.user.JoinIPCConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.JoinIPCConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               joinIPCConferenceResponse75 =
                                                   
                                                   
                                                         skel.joinIPCConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), joinIPCConferenceResponse75, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "joinIPCConference"));
                                    } else 

            if("searchByEmail".equals(methodName)){
                
                com.vidyo.portal.user.SearchByEmailResponse searchByEmailResponse77 = null;
	                        com.vidyo.portal.user.SearchByEmailRequest wrappedParam =
                                                             (com.vidyo.portal.user.SearchByEmailRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.SearchByEmailRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchByEmailResponse77 =
                                                   
                                                   
                                                         skel.searchByEmail(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchByEmailResponse77, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "searchByEmail"));
                                    } else 

            if("createRoomURL".equals(methodName)){
                
                com.vidyo.portal.user.CreateRoomURLResponse createRoomURLResponse79 = null;
	                        com.vidyo.portal.user.CreateRoomURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.CreateRoomURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.CreateRoomURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomURLResponse79 =
                                                   
                                                   
                                                         skel.createRoomURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomURLResponse79, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "createRoomURL"));
                                    } else 

            if("unmuteAudio".equals(methodName)){
                
                com.vidyo.portal.user.UnmuteAudioResponse unmuteAudioResponse81 = null;
	                        com.vidyo.portal.user.UnmuteAudioRequest wrappedParam =
                                                             (com.vidyo.portal.user.UnmuteAudioRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.UnmuteAudioRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unmuteAudioResponse81 =
                                                   
                                                   
                                                         skel.unmuteAudio(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unmuteAudioResponse81, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "unmuteAudio"));
                                    } else 

            if("setMemberMode".equals(methodName)){
                
                com.vidyo.portal.user.SetMemberModeResponse setMemberModeResponse83 = null;
	                        com.vidyo.portal.user.SetMemberModeRequest wrappedParam =
                                                             (com.vidyo.portal.user.SetMemberModeRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.SetMemberModeRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setMemberModeResponse83 =
                                                   
                                                   
                                                         skel.setMemberMode(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setMemberModeResponse83, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "setMemberMode"));
                                    } else 

            if("search".equals(methodName)){
                
                com.vidyo.portal.user.SearchResponse searchResponse85 = null;
	                        com.vidyo.portal.user.SearchRequest wrappedParam =
                                                             (com.vidyo.portal.user.SearchRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.SearchRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchResponse85 =
                                                   
                                                   
                                                         skel.search(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchResponse85, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "search"));
                                    } else 

            if("createRoom".equals(methodName)){
                
                com.vidyo.portal.user.CreateRoomResponse createRoomResponse87 = null;
	                        com.vidyo.portal.user.CreateRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.CreateRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.CreateRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomResponse87 =
                                                   
                                                   
                                                         skel.createRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomResponse87, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "createRoom"));
                                    } else 

            if("getUserName".equals(methodName)){
                
                com.vidyo.portal.user.GetUserNameResponse getUserNameResponse89 = null;
	                        com.vidyo.portal.user.GetUserNameRequest wrappedParam =
                                                             (com.vidyo.portal.user.GetUserNameRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.GetUserNameRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getUserNameResponse89 =
                                                   
                                                   
                                                         skel.getUserName(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getUserNameResponse89, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "getUserName"));
                                    } else 

            if("inviteToConference".equals(methodName)){
                
                com.vidyo.portal.user.InviteToConferenceResponse inviteToConferenceResponse91 = null;
	                        com.vidyo.portal.user.InviteToConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.user.InviteToConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.InviteToConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               inviteToConferenceResponse91 =
                                                   
                                                   
                                                         skel.inviteToConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), inviteToConferenceResponse91, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "inviteToConference"));
                                    } else 

            if("createRoomPIN".equals(methodName)){
                
                com.vidyo.portal.user.CreateRoomPINResponse createRoomPINResponse93 = null;
	                        com.vidyo.portal.user.CreateRoomPINRequest wrappedParam =
                                                             (com.vidyo.portal.user.CreateRoomPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.CreateRoomPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomPINResponse93 =
                                                   
                                                   
                                                         skel.createRoomPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomPINResponse93, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "createRoomPIN"));
                                    } else 

            if("logIn".equals(methodName)){
                
                com.vidyo.portal.user.LogInResponse logInResponse95 = null;
	                        com.vidyo.portal.user.LogInRequest wrappedParam =
                                                             (com.vidyo.portal.user.LogInRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.LogInRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               logInResponse95 =
                                                   
                                                   
                                                         skel.logIn(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), logInResponse95, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "logIn"));
                                    } else 

            if("muteAudio".equals(methodName)){
                
                com.vidyo.portal.user.MuteAudioResponse muteAudioResponse97 = null;
	                        com.vidyo.portal.user.MuteAudioRequest wrappedParam =
                                                             (com.vidyo.portal.user.MuteAudioRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.MuteAudioRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               muteAudioResponse97 =
                                                   
                                                   
                                                         skel.muteAudio(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), muteAudioResponse97, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "muteAudio"));
                                    } else 

            if("deleteRoom".equals(methodName)){
                
                com.vidyo.portal.user.DeleteRoomResponse deleteRoomResponse99 = null;
	                        com.vidyo.portal.user.DeleteRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.DeleteRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.DeleteRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteRoomResponse99 =
                                                   
                                                   
                                                         skel.deleteRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteRoomResponse99, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "deleteRoom"));
                                    } else 

            if("getPortalVersion".equals(methodName)){
                
                com.vidyo.portal.user.GetPortalVersionResponse getPortalVersionResponse101 = null;
	                        com.vidyo.portal.user.GetPortalVersionRequest wrappedParam =
                                                             (com.vidyo.portal.user.GetPortalVersionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.GetPortalVersionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getPortalVersionResponse101 =
                                                   
                                                   
                                                         skel.getPortalVersion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getPortalVersionResponse101, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "getPortalVersion"));
                                    } else 

            if("unlockRoom".equals(methodName)){
                
                com.vidyo.portal.user.UnlockRoomResponse unlockRoomResponse103 = null;
	                        com.vidyo.portal.user.UnlockRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.UnlockRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.UnlockRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unlockRoomResponse103 =
                                                   
                                                   
                                                         skel.unlockRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unlockRoomResponse103, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "unlockRoom"));
                                    } else 

            if("searchMyContacts".equals(methodName)){
                
                com.vidyo.portal.user.SearchMyContactsResponse searchMyContactsResponse105 = null;
	                        com.vidyo.portal.user.SearchMyContactsRequest wrappedParam =
                                                             (com.vidyo.portal.user.SearchMyContactsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.SearchMyContactsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchMyContactsResponse105 =
                                                   
                                                   
                                                         skel.searchMyContacts(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchMyContactsResponse105, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "searchMyContacts"));
                                    } else 

            if("lockRoom".equals(methodName)){
                
                com.vidyo.portal.user.LockRoomResponse lockRoomResponse107 = null;
	                        com.vidyo.portal.user.LockRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.LockRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.LockRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               lockRoomResponse107 =
                                                   
                                                   
                                                         skel.lockRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), lockRoomResponse107, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "lockRoom"));
                                    } else 

            if("updatePassword".equals(methodName)){
                
                com.vidyo.portal.user.UpdatePasswordResponse updatePasswordResponse109 = null;
	                        com.vidyo.portal.user.UpdatePasswordRequest wrappedParam =
                                                             (com.vidyo.portal.user.UpdatePasswordRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.UpdatePasswordRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updatePasswordResponse109 =
                                                   
                                                   
                                                         skel.updatePassword(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updatePasswordResponse109, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "updatePassword"));
                                    } else 

            if("updateLanguage".equals(methodName)){
                
                com.vidyo.portal.user.UpdateLanguageResponse updateLanguageResponse111 = null;
	                        com.vidyo.portal.user.UpdateLanguageRequest wrappedParam =
                                                             (com.vidyo.portal.user.UpdateLanguageRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.UpdateLanguageRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateLanguageResponse111 =
                                                   
                                                   
                                                         skel.updateLanguage(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateLanguageResponse111, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "updateLanguage"));
                                    } else 

            if("directCall".equals(methodName)){
                
                com.vidyo.portal.user.DirectCallResponse directCallResponse113 = null;
	                        com.vidyo.portal.user.DirectCallRequest wrappedParam =
                                                             (com.vidyo.portal.user.DirectCallRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.DirectCallRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               directCallResponse113 =
                                                   
                                                   
                                                         skel.directCall(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), directCallResponse113, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "directCall"));
                                    } else 

            if("getEntityByRoomKey".equals(methodName)){
                
                com.vidyo.portal.user.GetEntityByRoomKeyResponse getEntityByRoomKeyResponse115 = null;
	                        com.vidyo.portal.user.GetEntityByRoomKeyRequest wrappedParam =
                                                             (com.vidyo.portal.user.GetEntityByRoomKeyRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.GetEntityByRoomKeyRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getEntityByRoomKeyResponse115 =
                                                   
                                                   
                                                         skel.getEntityByRoomKey(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getEntityByRoomKeyResponse115, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "getEntityByRoomKey"));
                                    } else 

            if("linkEndpoint".equals(methodName)){
                
                com.vidyo.portal.user.LinkEndpointResponse linkEndpointResponse117 = null;
	                        com.vidyo.portal.user.LinkEndpointRequest wrappedParam =
                                                             (com.vidyo.portal.user.LinkEndpointRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.LinkEndpointRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               linkEndpointResponse117 =
                                                   
                                                   
                                                         skel.linkEndpoint(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), linkEndpointResponse117, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "linkEndpoint"));
                                    } else 

            if("leaveConference".equals(methodName)){
                
                com.vidyo.portal.user.LeaveConferenceResponse leaveConferenceResponse119 = null;
	                        com.vidyo.portal.user.LeaveConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.user.LeaveConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.LeaveConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               leaveConferenceResponse119 =
                                                   
                                                   
                                                         skel.leaveConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), leaveConferenceResponse119, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "leaveConference"));
                                    } else 

            if("removeRoomURL".equals(methodName)){
                
                com.vidyo.portal.user.RemoveRoomURLResponse removeRoomURLResponse121 = null;
	                        com.vidyo.portal.user.RemoveRoomURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.RemoveRoomURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.RemoveRoomURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomURLResponse121 =
                                                   
                                                   
                                                         skel.removeRoomURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomURLResponse121, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "removeRoomURL"));
                                    } else 

            if("myEndpointStatus".equals(methodName)){
                
                com.vidyo.portal.user.MyEndpointStatusResponse myEndpointStatusResponse123 = null;
	                        com.vidyo.portal.user.MyEndpointStatusRequest wrappedParam =
                                                             (com.vidyo.portal.user.MyEndpointStatusRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.MyEndpointStatusRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               myEndpointStatusResponse123 =
                                                   
                                                   
                                                         skel.myEndpointStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), myEndpointStatusResponse123, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "myEndpointStatus"));
                                    } else 

            if("getInviteContent".equals(methodName)){
                
                com.vidyo.portal.user.GetInviteContentResponse getInviteContentResponse125 = null;
	                        com.vidyo.portal.user.GetInviteContentRequest wrappedParam =
                                                             (com.vidyo.portal.user.GetInviteContentRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.GetInviteContentRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getInviteContentResponse125 =
                                                   
                                                   
                                                         skel.getInviteContent(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getInviteContentResponse125, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "getInviteContent"));
                                    } else 

            if("myAccount".equals(methodName)){
                
                com.vidyo.portal.user.MyAccountResponse myAccountResponse127 = null;
	                        com.vidyo.portal.user.MyAccountRequest wrappedParam =
                                                             (com.vidyo.portal.user.MyAccountRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.MyAccountRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               myAccountResponse127 =
                                                   
                                                   
                                                         skel.myAccount(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), myAccountResponse127, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "myAccount"));
                                    } else 

            if("getEntityByEntityID".equals(methodName)){
                
                com.vidyo.portal.user.GetEntityByEntityIDResponse getEntityByEntityIDResponse129 = null;
	                        com.vidyo.portal.user.GetEntityByEntityIDRequest wrappedParam =
                                                             (com.vidyo.portal.user.GetEntityByEntityIDRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.GetEntityByEntityIDRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getEntityByEntityIDResponse129 =
                                                   
                                                   
                                                         skel.getEntityByEntityID(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getEntityByEntityIDResponse129, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "getEntityByEntityID"));
                                    } else 

            if("getParticipants".equals(methodName)){
                
                com.vidyo.portal.user.GetParticipantsResponse getParticipantsResponse131 = null;
	                        com.vidyo.portal.user.GetParticipantsRequest wrappedParam =
                                                             (com.vidyo.portal.user.GetParticipantsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.GetParticipantsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getParticipantsResponse131 =
                                                   
                                                   
                                                         skel.getParticipants(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getParticipantsResponse131, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "getParticipants"));
                                    } else 

            if("searchByEntityID".equals(methodName)){
                
                com.vidyo.portal.user.SearchByEntityIDResponse searchByEntityIDResponse133 = null;
	                        com.vidyo.portal.user.SearchByEntityIDRequest wrappedParam =
                                                             (com.vidyo.portal.user.SearchByEntityIDRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.SearchByEntityIDRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchByEntityIDResponse133 =
                                                   
                                                   
                                                         skel.searchByEntityID(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchByEntityIDResponse133, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "searchByEntityID"));
                                    } else 

            if("removeRoomPIN".equals(methodName)){
                
                com.vidyo.portal.user.RemoveRoomPINResponse removeRoomPINResponse135 = null;
	                        com.vidyo.portal.user.RemoveRoomPINRequest wrappedParam =
                                                             (com.vidyo.portal.user.RemoveRoomPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.RemoveRoomPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomPINResponse135 =
                                                   
                                                   
                                                         skel.removeRoomPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomPINResponse135, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "removeRoomPIN"));
                                    } else 

            if("startVideo".equals(methodName)){
                
                com.vidyo.portal.user.StartVideoResponse startVideoResponse137 = null;
	                        com.vidyo.portal.user.StartVideoRequest wrappedParam =
                                                             (com.vidyo.portal.user.StartVideoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.StartVideoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startVideoResponse137 =
                                                   
                                                   
                                                         skel.startVideo(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startVideoResponse137, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "startVideo"));
                                    } else 

            if("stopVideo".equals(methodName)){
                
                com.vidyo.portal.user.StopVideoResponse stopVideoResponse139 = null;
	                        com.vidyo.portal.user.StopVideoRequest wrappedParam =
                                                             (com.vidyo.portal.user.StopVideoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.StopVideoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               stopVideoResponse139 =
                                                   
                                                   
                                                         skel.stopVideo(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), stopVideoResponse139, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "stopVideo"));
                                    } else 

            if("addToMyContacts".equals(methodName)){
                
                com.vidyo.portal.user.AddToMyContactsResponse addToMyContactsResponse141 = null;
	                        com.vidyo.portal.user.AddToMyContactsRequest wrappedParam =
                                                             (com.vidyo.portal.user.AddToMyContactsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.AddToMyContactsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addToMyContactsResponse141 =
                                                   
                                                   
                                                         skel.addToMyContacts(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addToMyContactsResponse141, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "addToMyContacts"));
                                    } else 

            if("removeFromMyContacts".equals(methodName)){
                
                com.vidyo.portal.user.RemoveFromMyContactsResponse removeFromMyContactsResponse143 = null;
	                        com.vidyo.portal.user.RemoveFromMyContactsRequest wrappedParam =
                                                             (com.vidyo.portal.user.RemoveFromMyContactsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.RemoveFromMyContactsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeFromMyContactsResponse143 =
                                                   
                                                   
                                                         skel.removeFromMyContacts(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeFromMyContactsResponse143, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "removeFromMyContacts"));
                                    } else 

            if("logOut".equals(methodName)){
                
                com.vidyo.portal.user.LogOutResponse logOutResponse145 = null;
	                        com.vidyo.portal.user.LogOutRequest wrappedParam =
                                                             (com.vidyo.portal.user.LogOutRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.LogOutRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               logOutResponse145 =
                                                   
                                                   
                                                         skel.logOut(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), logOutResponse145, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "logOut"));
                                    } else 

            if("joinConference".equals(methodName)){
                
                com.vidyo.portal.user.JoinConferenceResponse joinConferenceResponse147 = null;
	                        com.vidyo.portal.user.JoinConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.user.JoinConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.JoinConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               joinConferenceResponse147 =
                                                   
                                                   
                                                         skel.joinConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), joinConferenceResponse147, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user",
                                                    "joinConference"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (NotLicensedFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotLicensedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidArgumentFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidArgumentFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (GeneralFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"GeneralFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (ConferenceLockedFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ConferenceLockedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (SeatLicenseExpiredFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"SeatLicenseExpiredFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (WrongPinFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"WrongPinFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
        
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.JoinIPCConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.JoinIPCConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.JoinIPCConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.JoinIPCConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.NotLicensedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.NotLicensedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.ConferenceLockedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.ConferenceLockedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SeatLicenseExpiredFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SeatLicenseExpiredFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.WrongPinFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.WrongPinFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SearchByEmailRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SearchByEmailRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SearchByEmailResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SearchByEmailResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.CreateRoomURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.CreateRoomURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.CreateRoomURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.CreateRoomURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.UnmuteAudioRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.UnmuteAudioRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.UnmuteAudioResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.UnmuteAudioResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SetMemberModeRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SetMemberModeRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SetMemberModeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SetMemberModeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SearchRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SearchRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SearchResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SearchResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.CreateRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.CreateRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.CreateRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.CreateRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetUserNameRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetUserNameRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetUserNameResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetUserNameResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.InviteToConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.InviteToConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.InviteToConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.InviteToConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.CreateRoomPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.CreateRoomPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.CreateRoomPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.CreateRoomPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LogInRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LogInRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LogInResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LogInResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.MuteAudioRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.MuteAudioRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.MuteAudioResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.MuteAudioResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.DeleteRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.DeleteRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.DeleteRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.DeleteRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetPortalVersionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetPortalVersionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetPortalVersionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetPortalVersionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.UnlockRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.UnlockRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.UnlockRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.UnlockRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SearchMyContactsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SearchMyContactsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SearchMyContactsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SearchMyContactsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LockRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LockRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LockRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LockRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.UpdatePasswordRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.UpdatePasswordRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.UpdatePasswordResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.UpdatePasswordResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.UpdateLanguageRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.UpdateLanguageRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.UpdateLanguageResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.UpdateLanguageResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.DirectCallRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.DirectCallRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.DirectCallResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.DirectCallResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetEntityByRoomKeyRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetEntityByRoomKeyRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetEntityByRoomKeyResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetEntityByRoomKeyResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LinkEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LinkEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LinkEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LinkEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LeaveConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LeaveConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LeaveConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LeaveConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.RemoveRoomURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.RemoveRoomURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.RemoveRoomURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.RemoveRoomURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.MyEndpointStatusRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.MyEndpointStatusRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.MyEndpointStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.MyEndpointStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetInviteContentRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetInviteContentRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetInviteContentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetInviteContentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.MyAccountRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.MyAccountRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.MyAccountResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.MyAccountResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetEntityByEntityIDRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetEntityByEntityIDRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetEntityByEntityIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetEntityByEntityIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetParticipantsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetParticipantsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.GetParticipantsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.GetParticipantsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SearchByEntityIDRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SearchByEntityIDRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.SearchByEntityIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.SearchByEntityIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.RemoveRoomPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.RemoveRoomPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.RemoveRoomPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.RemoveRoomPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.StartVideoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.StartVideoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.StartVideoResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.StartVideoResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.StopVideoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.StopVideoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.StopVideoResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.StopVideoResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.AddToMyContactsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.AddToMyContactsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.AddToMyContactsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.AddToMyContactsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.RemoveFromMyContactsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.RemoveFromMyContactsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.RemoveFromMyContactsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.RemoveFromMyContactsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LogOutRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LogOutRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.LogOutResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.LogOutResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.JoinConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.JoinConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.JoinConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.JoinConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.JoinIPCConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.JoinIPCConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.JoinIPCConferenceResponse wrapjoinIPCConference(){
                                com.vidyo.portal.user.JoinIPCConferenceResponse wrappedElement = new com.vidyo.portal.user.JoinIPCConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.SearchByEmailResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.SearchByEmailResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.SearchByEmailResponse wrapsearchByEmail(){
                                com.vidyo.portal.user.SearchByEmailResponse wrappedElement = new com.vidyo.portal.user.SearchByEmailResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.CreateRoomURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.CreateRoomURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.CreateRoomURLResponse wrapcreateRoomURL(){
                                com.vidyo.portal.user.CreateRoomURLResponse wrappedElement = new com.vidyo.portal.user.CreateRoomURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.UnmuteAudioResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.UnmuteAudioResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.UnmuteAudioResponse wrapunmuteAudio(){
                                com.vidyo.portal.user.UnmuteAudioResponse wrappedElement = new com.vidyo.portal.user.UnmuteAudioResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.SetMemberModeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.SetMemberModeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.SetMemberModeResponse wrapsetMemberMode(){
                                com.vidyo.portal.user.SetMemberModeResponse wrappedElement = new com.vidyo.portal.user.SetMemberModeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.SearchResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.SearchResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.SearchResponse wrapsearch(){
                                com.vidyo.portal.user.SearchResponse wrappedElement = new com.vidyo.portal.user.SearchResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.CreateRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.CreateRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.CreateRoomResponse wrapcreateRoom(){
                                com.vidyo.portal.user.CreateRoomResponse wrappedElement = new com.vidyo.portal.user.CreateRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.GetUserNameResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.GetUserNameResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.GetUserNameResponse wrapgetUserName(){
                                com.vidyo.portal.user.GetUserNameResponse wrappedElement = new com.vidyo.portal.user.GetUserNameResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.InviteToConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.InviteToConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.InviteToConferenceResponse wrapinviteToConference(){
                                com.vidyo.portal.user.InviteToConferenceResponse wrappedElement = new com.vidyo.portal.user.InviteToConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.CreateRoomPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.CreateRoomPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.CreateRoomPINResponse wrapcreateRoomPIN(){
                                com.vidyo.portal.user.CreateRoomPINResponse wrappedElement = new com.vidyo.portal.user.CreateRoomPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.LogInResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.LogInResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.LogInResponse wraplogIn(){
                                com.vidyo.portal.user.LogInResponse wrappedElement = new com.vidyo.portal.user.LogInResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.MuteAudioResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.MuteAudioResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.MuteAudioResponse wrapmuteAudio(){
                                com.vidyo.portal.user.MuteAudioResponse wrappedElement = new com.vidyo.portal.user.MuteAudioResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.DeleteRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.DeleteRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.DeleteRoomResponse wrapdeleteRoom(){
                                com.vidyo.portal.user.DeleteRoomResponse wrappedElement = new com.vidyo.portal.user.DeleteRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.GetPortalVersionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.GetPortalVersionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.GetPortalVersionResponse wrapgetPortalVersion(){
                                com.vidyo.portal.user.GetPortalVersionResponse wrappedElement = new com.vidyo.portal.user.GetPortalVersionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.UnlockRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.UnlockRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.UnlockRoomResponse wrapunlockRoom(){
                                com.vidyo.portal.user.UnlockRoomResponse wrappedElement = new com.vidyo.portal.user.UnlockRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.SearchMyContactsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.SearchMyContactsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.SearchMyContactsResponse wrapsearchMyContacts(){
                                com.vidyo.portal.user.SearchMyContactsResponse wrappedElement = new com.vidyo.portal.user.SearchMyContactsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.LockRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.LockRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.LockRoomResponse wraplockRoom(){
                                com.vidyo.portal.user.LockRoomResponse wrappedElement = new com.vidyo.portal.user.LockRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.UpdatePasswordResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.UpdatePasswordResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.UpdatePasswordResponse wrapupdatePassword(){
                                com.vidyo.portal.user.UpdatePasswordResponse wrappedElement = new com.vidyo.portal.user.UpdatePasswordResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.UpdateLanguageResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.UpdateLanguageResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.UpdateLanguageResponse wrapupdateLanguage(){
                                com.vidyo.portal.user.UpdateLanguageResponse wrappedElement = new com.vidyo.portal.user.UpdateLanguageResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.DirectCallResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.DirectCallResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.DirectCallResponse wrapdirectCall(){
                                com.vidyo.portal.user.DirectCallResponse wrappedElement = new com.vidyo.portal.user.DirectCallResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.GetEntityByRoomKeyResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.GetEntityByRoomKeyResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.GetEntityByRoomKeyResponse wrapgetEntityByRoomKey(){
                                com.vidyo.portal.user.GetEntityByRoomKeyResponse wrappedElement = new com.vidyo.portal.user.GetEntityByRoomKeyResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.LinkEndpointResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.LinkEndpointResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.LinkEndpointResponse wraplinkEndpoint(){
                                com.vidyo.portal.user.LinkEndpointResponse wrappedElement = new com.vidyo.portal.user.LinkEndpointResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.LeaveConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.LeaveConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.LeaveConferenceResponse wrapleaveConference(){
                                com.vidyo.portal.user.LeaveConferenceResponse wrappedElement = new com.vidyo.portal.user.LeaveConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.RemoveRoomURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.RemoveRoomURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.RemoveRoomURLResponse wrapremoveRoomURL(){
                                com.vidyo.portal.user.RemoveRoomURLResponse wrappedElement = new com.vidyo.portal.user.RemoveRoomURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.MyEndpointStatusResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.MyEndpointStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.MyEndpointStatusResponse wrapmyEndpointStatus(){
                                com.vidyo.portal.user.MyEndpointStatusResponse wrappedElement = new com.vidyo.portal.user.MyEndpointStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.GetInviteContentResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.GetInviteContentResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.GetInviteContentResponse wrapgetInviteContent(){
                                com.vidyo.portal.user.GetInviteContentResponse wrappedElement = new com.vidyo.portal.user.GetInviteContentResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.MyAccountResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.MyAccountResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.MyAccountResponse wrapmyAccount(){
                                com.vidyo.portal.user.MyAccountResponse wrappedElement = new com.vidyo.portal.user.MyAccountResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.GetEntityByEntityIDResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.GetEntityByEntityIDResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.GetEntityByEntityIDResponse wrapgetEntityByEntityID(){
                                com.vidyo.portal.user.GetEntityByEntityIDResponse wrappedElement = new com.vidyo.portal.user.GetEntityByEntityIDResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.GetParticipantsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.GetParticipantsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.GetParticipantsResponse wrapgetParticipants(){
                                com.vidyo.portal.user.GetParticipantsResponse wrappedElement = new com.vidyo.portal.user.GetParticipantsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.SearchByEntityIDResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.SearchByEntityIDResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.SearchByEntityIDResponse wrapsearchByEntityID(){
                                com.vidyo.portal.user.SearchByEntityIDResponse wrappedElement = new com.vidyo.portal.user.SearchByEntityIDResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.RemoveRoomPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.RemoveRoomPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.RemoveRoomPINResponse wrapremoveRoomPIN(){
                                com.vidyo.portal.user.RemoveRoomPINResponse wrappedElement = new com.vidyo.portal.user.RemoveRoomPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.StartVideoResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.StartVideoResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.StartVideoResponse wrapstartVideo(){
                                com.vidyo.portal.user.StartVideoResponse wrappedElement = new com.vidyo.portal.user.StartVideoResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.StopVideoResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.StopVideoResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.StopVideoResponse wrapstopVideo(){
                                com.vidyo.portal.user.StopVideoResponse wrappedElement = new com.vidyo.portal.user.StopVideoResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.AddToMyContactsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.AddToMyContactsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.AddToMyContactsResponse wrapaddToMyContacts(){
                                com.vidyo.portal.user.AddToMyContactsResponse wrappedElement = new com.vidyo.portal.user.AddToMyContactsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.RemoveFromMyContactsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.RemoveFromMyContactsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.RemoveFromMyContactsResponse wrapremoveFromMyContacts(){
                                com.vidyo.portal.user.RemoveFromMyContactsResponse wrappedElement = new com.vidyo.portal.user.RemoveFromMyContactsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.LogOutResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.LogOutResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.LogOutResponse wraplogOut(){
                                com.vidyo.portal.user.LogOutResponse wrappedElement = new com.vidyo.portal.user.LogOutResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.JoinConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.JoinConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.JoinConferenceResponse wrapjoinConference(){
                                com.vidyo.portal.user.JoinConferenceResponse wrappedElement = new com.vidyo.portal.user.JoinConferenceResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.vidyo.portal.user.JoinIPCConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.JoinIPCConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.JoinIPCConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.JoinIPCConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.ConferenceLockedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.ConferenceLockedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.WrongPinFault.class.equals(type)){
                
                           return com.vidyo.portal.user.WrongPinFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SearchByEmailRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.SearchByEmailRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SearchByEmailResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.SearchByEmailResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.CreateRoomURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.CreateRoomURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.CreateRoomURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.CreateRoomURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.UnmuteAudioRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.UnmuteAudioRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.UnmuteAudioResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.UnmuteAudioResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SetMemberModeRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.SetMemberModeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SetMemberModeResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.SetMemberModeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SearchRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.SearchRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SearchResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.SearchResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.CreateRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.CreateRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.CreateRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.CreateRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetUserNameRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.GetUserNameRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetUserNameResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.GetUserNameResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InviteToConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.InviteToConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InviteToConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.InviteToConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.CreateRoomPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.CreateRoomPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.CreateRoomPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.CreateRoomPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LogInRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.LogInRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LogInResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.LogInResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.MuteAudioRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.MuteAudioRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.MuteAudioResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.MuteAudioResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.DeleteRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.DeleteRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.DeleteRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.DeleteRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetPortalVersionRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.GetPortalVersionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetPortalVersionResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.GetPortalVersionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.UnlockRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.UnlockRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.UnlockRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.UnlockRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SearchMyContactsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.SearchMyContactsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SearchMyContactsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.SearchMyContactsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LockRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.LockRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LockRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.LockRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.UpdatePasswordRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.UpdatePasswordRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.UpdatePasswordResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.UpdatePasswordResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.UpdateLanguageRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.UpdateLanguageRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.UpdateLanguageResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.UpdateLanguageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.DirectCallRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.DirectCallRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.DirectCallResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.DirectCallResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetEntityByRoomKeyRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.GetEntityByRoomKeyRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetEntityByRoomKeyResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.GetEntityByRoomKeyResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LinkEndpointRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.LinkEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LinkEndpointResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.LinkEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LeaveConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.LeaveConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LeaveConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.LeaveConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.RemoveRoomURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.RemoveRoomURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.RemoveRoomURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.RemoveRoomURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.MyEndpointStatusRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.MyEndpointStatusRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.MyEndpointStatusResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.MyEndpointStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetInviteContentRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.GetInviteContentRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetInviteContentResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.GetInviteContentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.MyAccountRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.MyAccountRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.MyAccountResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.MyAccountResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetEntityByEntityIDRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.GetEntityByEntityIDRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetEntityByEntityIDResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.GetEntityByEntityIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetParticipantsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.GetParticipantsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GetParticipantsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.GetParticipantsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SearchByEntityIDRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.SearchByEntityIDRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SearchByEntityIDResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.SearchByEntityIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.RemoveRoomPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.RemoveRoomPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.RemoveRoomPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.RemoveRoomPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.StartVideoRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.StartVideoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.StartVideoResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.StartVideoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.StopVideoRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.StopVideoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.StopVideoResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.StopVideoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.AddToMyContactsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.AddToMyContactsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.AddToMyContactsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.AddToMyContactsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.RemoveFromMyContactsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.RemoveFromMyContactsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.RemoveFromMyContactsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.RemoveFromMyContactsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LogOutRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.LogOutRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.LogOutResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.LogOutResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.JoinConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.JoinConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.JoinConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.JoinConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.ConferenceLockedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.ConferenceLockedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.WrongPinFault.class.equals(type)){
                
                           return com.vidyo.portal.user.WrongPinFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
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

        }//end of class
    