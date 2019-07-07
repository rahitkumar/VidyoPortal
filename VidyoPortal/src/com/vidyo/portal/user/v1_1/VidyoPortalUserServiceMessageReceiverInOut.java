
/**
 * VidyoPortalUserServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.vidyo.portal.user.v1_1;

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


        

            if("inviteToConference".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.InviteToConferenceResponse inviteToConferenceResponse609 = null;
	                        com.vidyo.portal.user.v1_1.InviteToConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.InviteToConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.InviteToConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               inviteToConferenceResponse609 =
                                                   
                                                   
                                                         skel.inviteToConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), inviteToConferenceResponse609, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "inviteToConference"));
                                    } else 

            if("searchMembers".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SearchMembersResponse searchMembersResponse611 = null;
	                        com.vidyo.portal.user.v1_1.SearchMembersRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SearchMembersRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SearchMembersRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchMembersResponse611 =
                                                   
                                                   
                                                         skel.searchMembers(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchMembersResponse611, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "searchMembers"));
                                    } else 

            if("raiseHand".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RaiseHandResponse raiseHandResponse613 = null;
	                        com.vidyo.portal.user.v1_1.RaiseHandRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RaiseHandRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RaiseHandRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               raiseHandResponse613 =
                                                   
                                                   
                                                         skel.raiseHand(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), raiseHandResponse613, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "raiseHand"));
                                    } else 

            if("search".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SearchResponse searchResponse615 = null;
	                        com.vidyo.portal.user.v1_1.SearchRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SearchRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SearchRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchResponse615 =
                                                   
                                                   
                                                         skel.search(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchResponse615, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "search"));
                                    } else 

            if("createPublicRoom".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreatePublicRoomResponse createPublicRoomResponse617 = null;
	                        com.vidyo.portal.user.v1_1.CreatePublicRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreatePublicRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreatePublicRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createPublicRoomResponse617 =
                                                   
                                                   
                                                         skel.createPublicRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createPublicRoomResponse617, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createPublicRoom"));
                                    } else 

            if("muteVideoServerAll".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse muteVideoServerAllResponse619 = null;
	                        com.vidyo.portal.user.v1_1.MuteVideoServerAllRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.MuteVideoServerAllRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.MuteVideoServerAllRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               muteVideoServerAllResponse619 =
                                                   
                                                   
                                                         skel.muteVideoServerAll(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), muteVideoServerAllResponse619, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "muteVideoServerAll"));
                                    } else 

            if("stopRecording".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.StopRecordingResponse stopRecordingResponse621 = null;
	                        com.vidyo.portal.user.v1_1.StopRecordingRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.StopRecordingRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.StopRecordingRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               stopRecordingResponse621 =
                                                   
                                                   
                                                         skel.stopRecording(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), stopRecordingResponse621, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "stopRecording"));
                                    } else 

            if("unmuteAudio".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.UnmuteAudioResponse unmuteAudioResponse623 = null;
	                        com.vidyo.portal.user.v1_1.UnmuteAudioRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.UnmuteAudioRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.UnmuteAudioRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unmuteAudioResponse623 =
                                                   
                                                   
                                                         skel.unmuteAudio(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unmuteAudioResponse623, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "unmuteAudio"));
                                    } else 

            if("createScheduledRoom".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse createScheduledRoomResponse625 = null;
	                        com.vidyo.portal.user.v1_1.CreateScheduledRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreateScheduledRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreateScheduledRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createScheduledRoomResponse625 =
                                                   
                                                   
                                                         skel.createScheduledRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createScheduledRoomResponse625, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createScheduledRoom"));
                                    } else 

            if("getLectureModeParticipants".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse getLectureModeParticipantsResponse627 = null;
	                        com.vidyo.portal.user.v1_1.GetLectureModeParticipantsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetLectureModeParticipantsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetLectureModeParticipantsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLectureModeParticipantsResponse627 =
                                                   
                                                   
                                                         skel.getLectureModeParticipants(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLectureModeParticipantsResponse627, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getLectureModeParticipants"));
                                    } else 

            if("getPortalPrefix".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.PortalPrefixResponse portalPrefixResponse629 = null;
	                        com.vidyo.portal.user.v1_1.PortalPrefixRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.PortalPrefixRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.PortalPrefixRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               portalPrefixResponse629 =
                                                   
                                                   
                                                         skel.getPortalPrefix(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), portalPrefixResponse629, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getPortalPrefix"));
                                    } else 

            if("getWebcastURL".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetWebcastURLResponse getWebcastURLResponse631 = null;
	                        com.vidyo.portal.user.v1_1.GetWebcastURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetWebcastURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetWebcastURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getWebcastURLResponse631 =
                                                   
                                                   
                                                         skel.getWebcastURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getWebcastURLResponse631, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getWebcastURL"));
                                    } else 

            if("getPINLengthRange".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse getPINLengthRangeResponse633 = null;
	                        com.vidyo.portal.user.v1_1.GetPINLengthRangeRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetPINLengthRangeRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetPINLengthRangeRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getPINLengthRangeResponse633 =
                                                   
                                                   
                                                         skel.getPINLengthRange(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getPINLengthRangeResponse633, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getPINLengthRange"));
                                    } else 

            if("getRecordingProfiles".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse getRecordingProfilesResponse635 = null;
	                        com.vidyo.portal.user.v1_1.GetRecordingProfilesRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetRecordingProfilesRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetRecordingProfilesRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRecordingProfilesResponse635 =
                                                   
                                                   
                                                         skel.getRecordingProfiles(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRecordingProfilesResponse635, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getRecordingProfiles"));
                                    } else 

            if("createRoom".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreateRoomResponse createRoomResponse637 = null;
	                        com.vidyo.portal.user.v1_1.CreateRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreateRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreateRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomResponse637 =
                                                   
                                                   
                                                         skel.createRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomResponse637, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createRoom"));
                                    } else 

            if("removePresenter".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RemovePresenterResponse removePresenterResponse639 = null;
	                        com.vidyo.portal.user.v1_1.RemovePresenterRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RemovePresenterRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RemovePresenterRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removePresenterResponse639 =
                                                   
                                                   
                                                         skel.removePresenter(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removePresenterResponse639, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "removePresenter"));
                                    } else 

            if("searchMyContacts".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SearchMyContactsResponse searchMyContactsResponse641 = null;
	                        com.vidyo.portal.user.v1_1.SearchMyContactsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SearchMyContactsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SearchMyContactsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchMyContactsResponse641 =
                                                   
                                                   
                                                         skel.searchMyContacts(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchMyContactsResponse641, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "searchMyContacts"));
                                    } else 

            if("lockRoom".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.LockRoomResponse lockRoomResponse643 = null;
	                        com.vidyo.portal.user.v1_1.LockRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.LockRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.LockRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               lockRoomResponse643 =
                                                   
                                                   
                                                         skel.lockRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), lockRoomResponse643, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "lockRoom"));
                                    } else 

            if("myAccount".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.MyAccountResponse myAccountResponse645 = null;
	                        com.vidyo.portal.user.v1_1.MyAccountRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.MyAccountRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.MyAccountRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               myAccountResponse645 =
                                                   
                                                   
                                                         skel.myAccount(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), myAccountResponse645, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "myAccount"));
                                    } else 

            if("dismissAllRaisedHand".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse dismissAllRaisedHandResponse647 = null;
	                        com.vidyo.portal.user.v1_1.DismissAllRaisedHandRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.DismissAllRaisedHandRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.DismissAllRaisedHandRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               dismissAllRaisedHandResponse647 =
                                                   
                                                   
                                                         skel.dismissAllRaisedHand(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), dismissAllRaisedHandResponse647, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "dismissAllRaisedHand"));
                                    } else 

            if("getRoomAccessOptions".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse roomAccessOptionsResponse649 = null;
	                        com.vidyo.portal.user.v1_1.RoomAccessOptionsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RoomAccessOptionsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RoomAccessOptionsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               roomAccessOptionsResponse649 =
                                                   
                                                   
                                                         skel.getRoomAccessOptions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), roomAccessOptionsResponse649, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getRoomAccessOptions"));
                                    } else 

            if("muteVideoClientAll".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse muteVideoClientAllResponse651 = null;
	                        com.vidyo.portal.user.v1_1.MuteVideoClientAllRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.MuteVideoClientAllRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.MuteVideoClientAllRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               muteVideoClientAllResponse651 =
                                                   
                                                   
                                                         skel.muteVideoClientAll(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), muteVideoClientAllResponse651, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "muteVideoClientAll"));
                                    } else 

            if("resumeRecording".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.ResumeRecordingResponse resumeRecordingResponse653 = null;
	                        com.vidyo.portal.user.v1_1.ResumeRecordingRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.ResumeRecordingRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.ResumeRecordingRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               resumeRecordingResponse653 =
                                                   
                                                   
                                                         skel.resumeRecording(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), resumeRecordingResponse653, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "resumeRecording"));
                                    } else 

            if("getRoomProfiles".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetRoomProfilesResponse getRoomProfilesResponse655 = null;
	                        com.vidyo.portal.user.v1_1.GetRoomProfilesRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetRoomProfilesRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetRoomProfilesRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRoomProfilesResponse655 =
                                                   
                                                   
                                                         skel.getRoomProfiles(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRoomProfilesResponse655, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getRoomProfiles"));
                                    } else 

            if("deleteRoom".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.DeleteRoomResponse deleteRoomResponse657 = null;
	                        com.vidyo.portal.user.v1_1.DeleteRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.DeleteRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.DeleteRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteRoomResponse657 =
                                                   
                                                   
                                                         skel.deleteRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteRoomResponse657, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "deleteRoom"));
                                    } else 

            if("getEntityByRoomKey".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse getEntityByRoomKeyResponse659 = null;
	                        com.vidyo.portal.user.v1_1.GetEntityByRoomKeyRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetEntityByRoomKeyRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetEntityByRoomKeyRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getEntityByRoomKeyResponse659 =
                                                   
                                                   
                                                         skel.getEntityByRoomKey(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getEntityByRoomKeyResponse659, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getEntityByRoomKey"));
                                    } else 

            if("muteAudioLocal".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.ParticipantStatusResponse participantStatusResponse661 = null;
	                        com.vidyo.portal.user.v1_1.MuteAudioRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.MuteAudioRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.MuteAudioRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               participantStatusResponse661 =
                                                   
                                                   
                                                         skel.muteAudioLocal(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), participantStatusResponse661, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "muteAudioLocal"));
                                    } else 

            if("removeRoomPIN".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RemoveRoomPINResponse removeRoomPINResponse663 = null;
	                        com.vidyo.portal.user.v1_1.RemoveRoomPINRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RemoveRoomPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RemoveRoomPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomPINResponse663 =
                                                   
                                                   
                                                         skel.removeRoomPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomPINResponse663, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "removeRoomPIN"));
                                    } else 

            if("searchRooms".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SearchRoomsResponse searchRoomsResponse665 = null;
	                        com.vidyo.portal.user.v1_1.SearchRoomsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SearchRoomsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SearchRoomsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchRoomsResponse665 =
                                                   
                                                   
                                                         skel.searchRooms(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchRoomsResponse665, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "searchRooms"));
                                    } else 

            if("cancelOutboundCall".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CancelOutboundCallResponse cancelOutboundCallResponse667 = null;
	                        com.vidyo.portal.user.v1_1.CancelOutboundCallRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CancelOutboundCallRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CancelOutboundCallRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               cancelOutboundCallResponse667 =
                                                   
                                                   
                                                         skel.cancelOutboundCall(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), cancelOutboundCallResponse667, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "cancelOutboundCall"));
                                    } else 

            if("dismissRaisedHand".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.DismissRaisedHandResponse dismissRaisedHandResponse669 = null;
	                        com.vidyo.portal.user.v1_1.DismissRaisedHandRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.DismissRaisedHandRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.DismissRaisedHandRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               dismissRaisedHandResponse669 =
                                                   
                                                   
                                                         skel.dismissRaisedHand(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), dismissRaisedHandResponse669, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "dismissRaisedHand"));
                                    } else 

            if("joinConference".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.JoinConferenceResponse joinConferenceResponse671 = null;
	                        com.vidyo.portal.user.v1_1.JoinConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.JoinConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.JoinConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               joinConferenceResponse671 =
                                                   
                                                   
                                                         skel.joinConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), joinConferenceResponse671, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "joinConference"));
                                    } else 

            if("getModeratorURLWithToken".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse getModeratorURLWithTokenResponse673 = null;
	                        com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getModeratorURLWithTokenResponse673 =
                                                   
                                                   
                                                         skel.getModeratorURLWithToken(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getModeratorURLWithTokenResponse673, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getModeratorURLWithToken"));
                                    } else 

            if("linkEndpoint".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.LinkEndpointResponse linkEndpointResponse675 = null;
	                        com.vidyo.portal.user.v1_1.LinkEndpointRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.LinkEndpointRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.LinkEndpointRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               linkEndpointResponse675 =
                                                   
                                                   
                                                         skel.linkEndpoint(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), linkEndpointResponse675, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "linkEndpoint"));
                                    } else 

            if("getUserAccountType".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse getUserAccountTypeResponse677 = null;
	                        com.vidyo.portal.user.v1_1.GetUserAccountTypeRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetUserAccountTypeRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetUserAccountTypeRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getUserAccountTypeResponse677 =
                                                   
                                                   
                                                         skel.getUserAccountType(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getUserAccountTypeResponse677, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getUserAccountType"));
                                    } else 

            if("createWebcastPIN".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreateWebcastPINResponse createWebcastPINResponse679 = null;
	                        com.vidyo.portal.user.v1_1.CreateWebcastPINRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreateWebcastPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreateWebcastPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createWebcastPINResponse679 =
                                                   
                                                   
                                                         skel.createWebcastPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createWebcastPINResponse679, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createWebcastPIN"));
                                    } else 

            if("setMemberMode".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SetMemberModeResponse setMemberModeResponse681 = null;
	                        com.vidyo.portal.user.v1_1.SetMemberModeRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SetMemberModeRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SetMemberModeRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setMemberModeResponse681 =
                                                   
                                                   
                                                         skel.setMemberMode(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setMemberModeResponse681, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "setMemberMode"));
                                    } else 

            if("createTestcallRoom".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse createTestcallRoomResponse683 = null;
	                        com.vidyo.portal.user.v1_1.CreateTestcallRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreateTestcallRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreateTestcallRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createTestcallRoomResponse683 =
                                                   
                                                   
                                                         skel.createTestcallRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createTestcallRoomResponse683, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createTestcallRoom"));
                                    } else 

            if("deleteScheduledRoom".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse deleteScheduledRoomResponse685 = null;
	                        com.vidyo.portal.user.v1_1.DeleteScheduledRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.DeleteScheduledRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.DeleteScheduledRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteScheduledRoomResponse685 =
                                                   
                                                   
                                                         skel.deleteScheduledRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteScheduledRoomResponse685, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "deleteScheduledRoom"));
                                    } else 

            if("leaveConference".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.LeaveConferenceResponse leaveConferenceResponse687 = null;
	                        com.vidyo.portal.user.v1_1.LeaveConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.LeaveConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.LeaveConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               leaveConferenceResponse687 =
                                                   
                                                   
                                                         skel.leaveConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), leaveConferenceResponse687, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "leaveConference"));
                                    } else 

            if("unraiseHand".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.UnraiseHandResponse unraiseHandResponse689 = null;
	                        com.vidyo.portal.user.v1_1.UnraiseHandRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.UnraiseHandRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.UnraiseHandRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unraiseHandResponse689 =
                                                   
                                                   
                                                         skel.unraiseHand(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unraiseHandResponse689, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "unraiseHand"));
                                    } else 

            if("stopVideo".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.StopVideoResponse stopVideoResponse691 = null;
	                        com.vidyo.portal.user.v1_1.StopVideoRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.StopVideoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.StopVideoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               stopVideoResponse691 =
                                                   
                                                   
                                                         skel.stopVideo(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), stopVideoResponse691, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "stopVideo"));
                                    } else 

            if("myEndpointStatus".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.MyEndpointStatusResponse myEndpointStatusResponse693 = null;
	                        com.vidyo.portal.user.v1_1.MyEndpointStatusRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.MyEndpointStatusRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.MyEndpointStatusRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               myEndpointStatusResponse693 =
                                                   
                                                   
                                                         skel.myEndpointStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), myEndpointStatusResponse693, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "myEndpointStatus"));
                                    } else 

            if("getEntityByEntityID".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse getEntityByEntityIDResponse695 = null;
	                        com.vidyo.portal.user.v1_1.GetEntityByEntityIDRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetEntityByEntityIDRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetEntityByEntityIDRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getEntityByEntityIDResponse695 =
                                                   
                                                   
                                                         skel.getEntityByEntityID(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getEntityByEntityIDResponse695, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getEntityByEntityID"));
                                    } else 

            if("createRoomPIN".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreateRoomPINResponse createRoomPINResponse697 = null;
	                        com.vidyo.portal.user.v1_1.CreateRoomPINRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreateRoomPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreateRoomPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomPINResponse697 =
                                                   
                                                   
                                                         skel.createRoomPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomPINResponse697, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createRoomPIN"));
                                    } else 

            if("getConferenceID".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetConferenceIDResponse getConferenceIDResponse699 = null;
	                        com.vidyo.portal.user.v1_1.GetConferenceIDRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetConferenceIDRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetConferenceIDRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getConferenceIDResponse699 =
                                                   
                                                   
                                                         skel.getConferenceID(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getConferenceIDResponse699, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getConferenceID"));
                                    } else 

            if("logoutAllOtherSessions".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse logoutAllOtherSessionsResponse701 = null;
	                        com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               logoutAllOtherSessionsResponse701 =
                                                   
                                                   
                                                         skel.logoutAllOtherSessions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), logoutAllOtherSessionsResponse701, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "logoutAllOtherSessions"));
                                    } else 

            if("getOnetimeAccessUrl".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.OnetimeAccessResponse onetimeAccessResponse703 = null;
	                        com.vidyo.portal.user.v1_1.OnetimeAccessRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.OnetimeAccessRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.OnetimeAccessRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               onetimeAccessResponse703 =
                                                   
                                                   
                                                         skel.getOnetimeAccessUrl(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), onetimeAccessResponse703, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getOnetimeAccessUrl"));
                                    } else 

            if("createWebcastURL".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreateWebcastURLResponse createWebcastURLResponse705 = null;
	                        com.vidyo.portal.user.v1_1.CreateWebcastURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreateWebcastURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreateWebcastURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createWebcastURLResponse705 =
                                                   
                                                   
                                                         skel.createWebcastURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createWebcastURLResponse705, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createWebcastURL"));
                                    } else 

            if("getRoomProfile".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetRoomProfileResponse getRoomProfileResponse707 = null;
	                        com.vidyo.portal.user.v1_1.GetRoomProfileRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetRoomProfileRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetRoomProfileRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRoomProfileResponse707 =
                                                   
                                                   
                                                         skel.getRoomProfile(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRoomProfileResponse707, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getRoomProfile"));
                                    } else 

            if("removeModeratorPIN".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse removeModeratorPINResponse709 = null;
	                        com.vidyo.portal.user.v1_1.RemoveModeratorPINRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RemoveModeratorPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RemoveModeratorPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeModeratorPINResponse709 =
                                                   
                                                   
                                                         skel.removeModeratorPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeModeratorPINResponse709, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "removeModeratorPIN"));
                                    } else 

            if("silenceSpeakerServer".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse silenceSpeakerServerResponse711 = null;
	                        com.vidyo.portal.user.v1_1.SilenceSpeakerServerRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SilenceSpeakerServerRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SilenceSpeakerServerRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               silenceSpeakerServerResponse711 =
                                                   
                                                   
                                                         skel.silenceSpeakerServer(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), silenceSpeakerServerResponse711, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "silenceSpeakerServer"));
                                    } else 

            if("getLoginAndWelcomeBanner".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse getLoginAndWelcomeBannerResponse713 = null;
	                        com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLoginAndWelcomeBannerResponse713 =
                                                   
                                                   
                                                         skel.getLoginAndWelcomeBanner(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLoginAndWelcomeBannerResponse713, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getLoginAndWelcomeBanner"));
                                    } else 

            if("createRoomURL".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreateRoomURLResponse createRoomURLResponse715 = null;
	                        com.vidyo.portal.user.v1_1.CreateRoomURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreateRoomURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreateRoomURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomURLResponse715 =
                                                   
                                                   
                                                         skel.createRoomURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomURLResponse715, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createRoomURL"));
                                    } else 

            if("getChangePasswordHtmlUrlWithToken".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse getChangePasswordHtmlUrlWithTokenResponse717 = null;
	                        com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getChangePasswordHtmlUrlWithTokenResponse717 =
                                                   
                                                   
                                                         skel.getChangePasswordHtmlUrlWithToken(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getChangePasswordHtmlUrlWithTokenResponse717, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getChangePasswordHtmlUrlWithToken"));
                                    } else 

            if("updatePublicRoomDescription".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse updatePublicRoomDescriptionResponse719 = null;
	                        com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updatePublicRoomDescriptionResponse719 =
                                                   
                                                   
                                                         skel.updatePublicRoomDescription(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updatePublicRoomDescriptionResponse719, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "updatePublicRoomDescription"));
                                    } else 

            if("getPortalFeatures".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse getPortalFeaturesResponse721 = null;
	                        com.vidyo.portal.user.v1_1.GetPortalFeaturesRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetPortalFeaturesRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetPortalFeaturesRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getPortalFeaturesResponse721 =
                                                   
                                                   
                                                         skel.getPortalFeatures(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getPortalFeaturesResponse721, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getPortalFeatures"));
                                    } else 

            if("startLectureMode".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.StartLectureModeResponse startLectureModeResponse723 = null;
	                        com.vidyo.portal.user.v1_1.StartLectureModeRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.StartLectureModeRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.StartLectureModeRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startLectureModeResponse723 =
                                                   
                                                   
                                                         skel.startLectureMode(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startLectureModeResponse723, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "startLectureMode"));
                                    } else 

            if("generateAuthToken".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse generateAuthTokenResponse725 = null;
	                        com.vidyo.portal.user.v1_1.GenerateAuthTokenRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GenerateAuthTokenRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GenerateAuthTokenRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               generateAuthTokenResponse725 =
                                                   
                                                   
                                                         skel.generateAuthToken(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), generateAuthTokenResponse725, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "generateAuthToken"));
                                    } else 

            if("getPortalVersion".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetPortalVersionResponse getPortalVersionResponse727 = null;
	                        com.vidyo.portal.user.v1_1.GetPortalVersionRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetPortalVersionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetPortalVersionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getPortalVersionResponse727 =
                                                   
                                                   
                                                         skel.getPortalVersion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getPortalVersionResponse727, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getPortalVersion"));
                                    } else 

            if("getParticipants".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetParticipantsResponse getParticipantsResponse729 = null;
	                        com.vidyo.portal.user.v1_1.GetParticipantsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetParticipantsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetParticipantsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getParticipantsResponse729 =
                                                   
                                                   
                                                         skel.getParticipants(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getParticipantsResponse729, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getParticipants"));
                                    } else 

            if("addToMyContacts".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.AddToMyContactsResponse addToMyContactsResponse731 = null;
	                        com.vidyo.portal.user.v1_1.AddToMyContactsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.AddToMyContactsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.AddToMyContactsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addToMyContactsResponse731 =
                                                   
                                                   
                                                         skel.addToMyContacts(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addToMyContactsResponse731, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "addToMyContacts"));
                                    } else 

            if("updatePassword".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.UpdatePasswordResponse updatePasswordResponse733 = null;
	                        com.vidyo.portal.user.v1_1.UpdatePasswordRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.UpdatePasswordRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.UpdatePasswordRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updatePasswordResponse733 =
                                                   
                                                   
                                                         skel.updatePassword(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updatePasswordResponse733, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "updatePassword"));
                                    } else 

            if("disconnectConferenceAll".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse disconnectConferenceAllResponse735 = null;
	                        com.vidyo.portal.user.v1_1.DisconnectConferenceAllRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.DisconnectConferenceAllRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.DisconnectConferenceAllRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               disconnectConferenceAllResponse735 =
                                                   
                                                   
                                                         skel.disconnectConferenceAll(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), disconnectConferenceAllResponse735, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "disconnectConferenceAll"));
                                    } else 

            if("setEndpointDetails".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse setEndpointDetailsResponse737 = null;
	                        com.vidyo.portal.user.v1_1.SetEndpointDetailsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SetEndpointDetailsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SetEndpointDetailsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setEndpointDetailsResponse737 =
                                                   
                                                   
                                                         skel.setEndpointDetails(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setEndpointDetailsResponse737, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "setEndpointDetails"));
                                    } else 

            if("muteVideoLocal".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.ParticipantStatusResponse participantStatusResponse739 = null;
	                        com.vidyo.portal.user.v1_1.MuteVideoRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.MuteVideoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.MuteVideoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               participantStatusResponse739 =
                                                   
                                                   
                                                         skel.muteVideoLocal(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), participantStatusResponse739, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "muteVideoLocal"));
                                    } else 

            if("muteAudioServerAll".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse muteAudioServerAllResponse741 = null;
	                        com.vidyo.portal.user.v1_1.MuteAudioServerAllRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.MuteAudioServerAllRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.MuteAudioServerAllRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               muteAudioServerAllResponse741 =
                                                   
                                                   
                                                         skel.muteAudioServerAll(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), muteAudioServerAllResponse741, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "muteAudioServerAll"));
                                    } else 

            if("getLogAggregationServer".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse getLogAggregationServerResponse743 = null;
	                        com.vidyo.portal.user.v1_1.GetLogAggregationServerRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetLogAggregationServerRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetLogAggregationServerRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLogAggregationServerResponse743 =
                                                   
                                                   
                                                         skel.getLogAggregationServer(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLogAggregationServerResponse743, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getLogAggregationServer"));
                                    } else 

            if("removeRoomURL".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RemoveRoomURLResponse removeRoomURLResponse745 = null;
	                        com.vidyo.portal.user.v1_1.RemoveRoomURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RemoveRoomURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RemoveRoomURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomURLResponse745 =
                                                   
                                                   
                                                         skel.removeRoomURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomURLResponse745, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "removeRoomURL"));
                                    } else 

            if("updateLanguage".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.UpdateLanguageResponse updateLanguageResponse747 = null;
	                        com.vidyo.portal.user.v1_1.UpdateLanguageRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.UpdateLanguageRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.UpdateLanguageRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateLanguageResponse747 =
                                                   
                                                   
                                                         skel.updateLanguage(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateLanguageResponse747, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "updateLanguage"));
                                    } else 

            if("logOut".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.LogOutResponse logOutResponse749 = null;
	                        com.vidyo.portal.user.v1_1.LogOutRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.LogOutRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.LogOutRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               logOutResponse749 =
                                                   
                                                   
                                                         skel.logOut(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), logOutResponse749, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "logOut"));
                                    } else 

            if("getEntityDetailsByEntityID".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse getEntityDetailsByEntityIDResponse751 = null;
	                        com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getEntityDetailsByEntityIDResponse751 =
                                                   
                                                   
                                                         skel.getEntityDetailsByEntityID(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getEntityDetailsByEntityIDResponse751, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getEntityDetailsByEntityID"));
                                    } else 

            if("unlockRoom".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.UnlockRoomResponse unlockRoomResponse753 = null;
	                        com.vidyo.portal.user.v1_1.UnlockRoomRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.UnlockRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.UnlockRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unlockRoomResponse753 =
                                                   
                                                   
                                                         skel.unlockRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unlockRoomResponse753, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "unlockRoom"));
                                    } else 

            if("removeModeratorURL".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse removeModeratorURLResponse755 = null;
	                        com.vidyo.portal.user.v1_1.RemoveModeratorURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RemoveModeratorURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RemoveModeratorURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeModeratorURLResponse755 =
                                                   
                                                   
                                                         skel.removeModeratorURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeModeratorURLResponse755, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "removeModeratorURL"));
                                    } else 

            if("removeWebcastPIN".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse removeWebcastPINResponse757 = null;
	                        com.vidyo.portal.user.v1_1.RemoveWebcastPINRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RemoveWebcastPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RemoveWebcastPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeWebcastPINResponse757 =
                                                   
                                                   
                                                         skel.removeWebcastPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeWebcastPINResponse757, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "removeWebcastPIN"));
                                    } else 

            if("removeRoomProfile".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse removeRoomProfileResponse759 = null;
	                        com.vidyo.portal.user.v1_1.RemoveRoomProfileRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RemoveRoomProfileRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RemoveRoomProfileRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomProfileResponse759 =
                                                   
                                                   
                                                         skel.removeRoomProfile(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomProfileResponse759, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "removeRoomProfile"));
                                    } else 

            if("whatIsMyIPAddress".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse whatIsMyIPAddressResponse761 = null;
	                        com.vidyo.portal.user.v1_1.WhatIsMyIPAddressRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.WhatIsMyIPAddressRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.WhatIsMyIPAddressRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               whatIsMyIPAddressResponse761 =
                                                   
                                                   
                                                         skel.whatIsMyIPAddress(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), whatIsMyIPAddressResponse761, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "whatIsMyIPAddress"));
                                    } else 

            if("joinIPCConference".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse joinIPCConferenceResponse763 = null;
	                        com.vidyo.portal.user.v1_1.JoinIPCConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.JoinIPCConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.JoinIPCConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               joinIPCConferenceResponse763 =
                                                   
                                                   
                                                         skel.joinIPCConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), joinIPCConferenceResponse763, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "joinIPCConference"));
                                    } else 

            if("muteAudioClientAll".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse muteAudioClientAllResponse765 = null;
	                        com.vidyo.portal.user.v1_1.MuteAudioClientAllRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.MuteAudioClientAllRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.MuteAudioClientAllRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               muteAudioClientAllResponse765 =
                                                   
                                                   
                                                         skel.muteAudioClientAll(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), muteAudioClientAllResponse765, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "muteAudioClientAll"));
                                    } else 

            if("startVideo".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.StartVideoResponse startVideoResponse767 = null;
	                        com.vidyo.portal.user.v1_1.StartVideoRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.StartVideoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.StartVideoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startVideoResponse767 =
                                                   
                                                   
                                                         skel.startVideo(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startVideoResponse767, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "startVideo"));
                                    } else 

            if("searchByEntityID".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SearchByEntityIDResponse searchByEntityIDResponse769 = null;
	                        com.vidyo.portal.user.v1_1.SearchByEntityIDRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SearchByEntityIDRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SearchByEntityIDRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchByEntityIDResponse769 =
                                                   
                                                   
                                                         skel.searchByEntityID(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchByEntityIDResponse769, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "searchByEntityID"));
                                    } else 

            if("getModeratorURL".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetModeratorURLResponse getModeratorURLResponse771 = null;
	                        com.vidyo.portal.user.v1_1.GetModeratorURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetModeratorURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetModeratorURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getModeratorURLResponse771 =
                                                   
                                                   
                                                         skel.getModeratorURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getModeratorURLResponse771, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getModeratorURL"));
                                    } else 

            if("getVidyoReplayLibrary".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse getVidyoReplayLibraryResponse773 = null;
	                        com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getVidyoReplayLibraryResponse773 =
                                                   
                                                   
                                                         skel.getVidyoReplayLibrary(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getVidyoReplayLibraryResponse773, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getVidyoReplayLibrary"));
                                    } else 

            if("silenceSpeakerServerAll".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse silenceSpeakerServerAllResponse775 = null;
	                        com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               silenceSpeakerServerAllResponse775 =
                                                   
                                                   
                                                         skel.silenceSpeakerServerAll(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), silenceSpeakerServerAllResponse775, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "silenceSpeakerServerAll"));
                                    } else 

            if("createModeratorPIN".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreateModeratorPINResponse createModeratorPINResponse777 = null;
	                        com.vidyo.portal.user.v1_1.CreateModeratorPINRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreateModeratorPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreateModeratorPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createModeratorPINResponse777 =
                                                   
                                                   
                                                         skel.createModeratorPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createModeratorPINResponse777, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createModeratorPIN"));
                                    } else 

            if("getUserName".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetUserNameResponse getUserNameResponse779 = null;
	                        com.vidyo.portal.user.v1_1.GetUserNameRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetUserNameRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetUserNameRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getUserNameResponse779 =
                                                   
                                                   
                                                         skel.getUserName(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getUserNameResponse779, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getUserName"));
                                    } else 

            if("pauseRecording".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.PauseRecordingResponse pauseRecordingResponse781 = null;
	                        com.vidyo.portal.user.v1_1.PauseRecordingRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.PauseRecordingRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.PauseRecordingRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               pauseRecordingResponse781 =
                                                   
                                                   
                                                         skel.pauseRecording(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), pauseRecordingResponse781, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "pauseRecording"));
                                    } else 

            if("removeWebcastURL".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse removeWebcastURLResponse783 = null;
	                        com.vidyo.portal.user.v1_1.RemoveWebcastURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RemoveWebcastURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RemoveWebcastURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeWebcastURLResponse783 =
                                                   
                                                   
                                                         skel.removeWebcastURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeWebcastURLResponse783, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "removeWebcastURL"));
                                    } else 

            if("logIn".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.LogInResponse logInResponse785 = null;
	                        com.vidyo.portal.user.v1_1.LogInRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.LogInRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.LogInRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               logInResponse785 =
                                                   
                                                   
                                                         skel.logIn(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), logInResponse785, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "logIn"));
                                    } else 

            if("setPresenter".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SetPresenterResponse setPresenterResponse787 = null;
	                        com.vidyo.portal.user.v1_1.SetPresenterRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SetPresenterRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SetPresenterRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setPresenterResponse787 =
                                                   
                                                   
                                                         skel.setPresenter(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setPresenterResponse787, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "setPresenter"));
                                    } else 

            if("searchByEmail".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SearchByEmailResponse searchByEmailResponse789 = null;
	                        com.vidyo.portal.user.v1_1.SearchByEmailRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SearchByEmailRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SearchByEmailRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchByEmailResponse789 =
                                                   
                                                   
                                                         skel.searchByEmail(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchByEmailResponse789, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "searchByEmail"));
                                    } else 

            if("setThumbnailPhoto".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse setThumbnailPhotoResponse791 = null;
	                        com.vidyo.portal.user.v1_1.SetThumbnailPhotoRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SetThumbnailPhotoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SetThumbnailPhotoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setThumbnailPhotoResponse791 =
                                                   
                                                   
                                                         skel.setThumbnailPhoto(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setThumbnailPhotoResponse791, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "setThumbnailPhoto"));
                                    } else 

            if("directCall".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.DirectCallResponse directCallResponse793 = null;
	                        com.vidyo.portal.user.v1_1.DirectCallRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.DirectCallRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.DirectCallRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               directCallResponse793 =
                                                   
                                                   
                                                         skel.directCall(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), directCallResponse793, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "directCall"));
                                    } else 

            if("startRecording".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.StartRecordingResponse startRecordingResponse795 = null;
	                        com.vidyo.portal.user.v1_1.StartRecordingRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.StartRecordingRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.StartRecordingRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startRecordingResponse795 =
                                                   
                                                   
                                                         skel.startRecording(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startRecordingResponse795, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "startRecording"));
                                    } else 

            if("getActiveSessions".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetActiveSessionsResponse getActiveSessionsResponse797 = null;
	                        com.vidyo.portal.user.v1_1.GetActiveSessionsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetActiveSessionsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetActiveSessionsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getActiveSessionsResponse797 =
                                                   
                                                   
                                                         skel.getActiveSessions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getActiveSessionsResponse797, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getActiveSessions"));
                                    } else 

            if("stopLectureMode".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.StopLectureModeResponse stopLectureModeResponse799 = null;
	                        com.vidyo.portal.user.v1_1.StopLectureModeRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.StopLectureModeRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.StopLectureModeRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               stopLectureModeResponse799 =
                                                   
                                                   
                                                         skel.stopLectureMode(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), stopLectureModeResponse799, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "stopLectureMode"));
                                    } else 

            if("setRoomProfile".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.SetRoomProfileResponse setRoomProfileResponse801 = null;
	                        com.vidyo.portal.user.v1_1.SetRoomProfileRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.SetRoomProfileRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.SetRoomProfileRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setRoomProfileResponse801 =
                                                   
                                                   
                                                         skel.setRoomProfile(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setRoomProfileResponse801, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "setRoomProfile"));
                                    } else 

            if("getInviteContent".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.GetInviteContentResponse getInviteContentResponse803 = null;
	                        com.vidyo.portal.user.v1_1.GetInviteContentRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.GetInviteContentRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.GetInviteContentRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getInviteContentResponse803 =
                                                   
                                                   
                                                         skel.getInviteContent(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getInviteContentResponse803, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "getInviteContent"));
                                    } else 

            if("muteAudio".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.MuteAudioResponse muteAudioResponse805 = null;
	                        com.vidyo.portal.user.v1_1.MuteAudioRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.MuteAudioRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.MuteAudioRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               muteAudioResponse805 =
                                                   
                                                   
                                                         skel.muteAudio(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), muteAudioResponse805, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "muteAudio"));
                                    } else 

            if("removeFromMyContacts".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse removeFromMyContactsResponse807 = null;
	                        com.vidyo.portal.user.v1_1.RemoveFromMyContactsRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.RemoveFromMyContactsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.RemoveFromMyContactsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeFromMyContactsResponse807 =
                                                   
                                                   
                                                         skel.removeFromMyContacts(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeFromMyContactsResponse807, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "removeFromMyContacts"));
                                    } else 

            if("createModeratorURL".equals(methodName)){
                
                com.vidyo.portal.user.v1_1.CreateModeratorURLResponse createModeratorURLResponse809 = null;
	                        com.vidyo.portal.user.v1_1.CreateModeratorURLRequest wrappedParam =
                                                             (com.vidyo.portal.user.v1_1.CreateModeratorURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.user.v1_1.CreateModeratorURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createModeratorURLResponse809 =
                                                   
                                                   
                                                         skel.createModeratorURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createModeratorURLResponse809, false, new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                    "createModeratorURL"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (PublicRoomCreationFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"PublicRoomCreationFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (VidyoReplayNotAvailableFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"VidyoReplayNotAvailableFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (ControlMeetingFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ControlMeetingFault");
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
         catch (ScheduledRoomCreationFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ScheduledRoomCreationFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (PrefixNotConfiguredFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"PrefixNotConfiguredFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (LogAggregationDisabledFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"LogAggregationDisabledFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (FileTooLargeFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"FileTooLargeFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidParticipantFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidParticipantFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (EndpointNotBoundFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"EndpointNotBoundFault");
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
         catch (ResourceNotAvailableFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ResourceNotAvailableFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (TestcallRoomCreationFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"TestcallRoomCreationFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (NotAllowedThumbnailPhotoFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotAllowedThumbnailPhotoFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidModeratorPINFormatFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidModeratorPINFormatFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (RoomNotFoundFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"RoomNotFoundFault");
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
         catch (FeatureNotAvailableFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"FeatureNotAvailableFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (AccessRestrictedFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"AccessRestrictedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InPointToPointCallFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InPointToPointCallFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (WrongPINFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"WrongPINFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidConferenceFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidConferenceFault");
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
         catch (NotAllowedToCreateFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotAllowedToCreateFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (PublicRoomDescUpdationFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"PublicRoomDescUpdationFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (NotLicensedFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotLicensedFault");
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
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.InviteToConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.InviteToConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.InviteToConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.InviteToConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.ControlMeetingFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.ControlMeetingFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.NotLicensedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.NotLicensedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchMembersRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchMembersRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchMembersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchMembersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RaiseHandRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RaiseHandRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RaiseHandResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RaiseHandResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreatePublicRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreatePublicRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreatePublicRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreatePublicRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.PublicRoomCreationFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.PublicRoomCreationFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.NotAllowedToCreateFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.NotAllowedToCreateFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteVideoServerAllRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteVideoServerAllRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StopRecordingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StopRecordingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StopRecordingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StopRecordingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UnmuteAudioRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UnmuteAudioRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UnmuteAudioResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UnmuteAudioResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateScheduledRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateScheduledRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.ScheduledRoomCreationFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.ScheduledRoomCreationFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetLectureModeParticipantsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetLectureModeParticipantsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.PortalPrefixRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.PortalPrefixRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.PortalPrefixResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.PortalPrefixResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.PrefixNotConfiguredFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.PrefixNotConfiguredFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetWebcastURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetWebcastURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetWebcastURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetWebcastURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetPINLengthRangeRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetPINLengthRangeRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetRecordingProfilesRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetRecordingProfilesRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemovePresenterRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemovePresenterRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemovePresenterResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemovePresenterResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchMyContactsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchMyContactsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchMyContactsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchMyContactsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LockRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LockRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LockRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LockRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MyAccountRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MyAccountRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MyAccountResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MyAccountResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DismissAllRaisedHandRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DismissAllRaisedHandRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RoomAccessOptionsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RoomAccessOptionsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteVideoClientAllRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteVideoClientAllRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.ResumeRecordingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.ResumeRecordingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.ResumeRecordingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.ResumeRecordingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetRoomProfilesRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetRoomProfilesRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetRoomProfilesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetRoomProfilesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DeleteRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DeleteRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DeleteRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DeleteRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetEntityByRoomKeyRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetEntityByRoomKeyRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteAudioRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteAudioRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.ParticipantStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.ParticipantStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveRoomPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveRoomPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveRoomPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveRoomPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchRoomsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchRoomsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchRoomsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchRoomsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CancelOutboundCallRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CancelOutboundCallRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CancelOutboundCallResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CancelOutboundCallResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DismissRaisedHandRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DismissRaisedHandRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DismissRaisedHandResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DismissRaisedHandResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.JoinConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.JoinConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.JoinConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.JoinConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.WrongPINFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.WrongPINFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.ConferenceLockedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.ConferenceLockedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LinkEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LinkEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LinkEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LinkEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.AccessRestrictedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.AccessRestrictedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetUserAccountTypeRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetUserAccountTypeRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateWebcastPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateWebcastPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateWebcastPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateWebcastPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetMemberModeRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetMemberModeRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetMemberModeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetMemberModeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateTestcallRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateTestcallRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.TestcallRoomCreationFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.TestcallRoomCreationFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DeleteScheduledRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DeleteScheduledRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RoomNotFoundFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RoomNotFoundFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LeaveConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LeaveConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LeaveConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LeaveConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UnraiseHandRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UnraiseHandRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UnraiseHandResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UnraiseHandResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StopVideoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StopVideoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StopVideoResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StopVideoResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MyEndpointStatusRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MyEndpointStatusRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MyEndpointStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MyEndpointStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetEntityByEntityIDRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetEntityByEntityIDRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateRoomPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateRoomPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateRoomPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateRoomPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetConferenceIDRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetConferenceIDRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetConferenceIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetConferenceIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.InPointToPointCallFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.InPointToPointCallFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.OnetimeAccessRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.OnetimeAccessRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.OnetimeAccessResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.OnetimeAccessResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateWebcastURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateWebcastURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateWebcastURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateWebcastURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetRoomProfileRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetRoomProfileRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetRoomProfileResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetRoomProfileResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveModeratorPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveModeratorPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SilenceSpeakerServerRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SilenceSpeakerServerRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.InvalidConferenceFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.InvalidConferenceFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.InvalidParticipantFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.InvalidParticipantFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.FeatureNotAvailableFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.FeatureNotAvailableFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateRoomURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateRoomURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateRoomURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateRoomURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.PublicRoomDescUpdationFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.PublicRoomDescUpdationFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetPortalFeaturesRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetPortalFeaturesRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StartLectureModeRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StartLectureModeRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StartLectureModeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StartLectureModeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GenerateAuthTokenRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GenerateAuthTokenRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.EndpointNotBoundFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.EndpointNotBoundFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetPortalVersionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetPortalVersionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetPortalVersionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetPortalVersionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetParticipantsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetParticipantsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetParticipantsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetParticipantsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.AddToMyContactsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.AddToMyContactsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.AddToMyContactsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.AddToMyContactsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UpdatePasswordRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UpdatePasswordRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UpdatePasswordResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UpdatePasswordResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DisconnectConferenceAllRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DisconnectConferenceAllRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetEndpointDetailsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetEndpointDetailsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteVideoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteVideoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteAudioServerAllRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteAudioServerAllRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetLogAggregationServerRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetLogAggregationServerRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LogAggregationDisabledFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LogAggregationDisabledFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveRoomURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveRoomURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveRoomURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveRoomURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UpdateLanguageRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UpdateLanguageRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UpdateLanguageResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UpdateLanguageResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LogOutRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LogOutRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LogOutResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LogOutResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UnlockRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UnlockRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.UnlockRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.UnlockRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveModeratorURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveModeratorURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveWebcastPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveWebcastPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveRoomProfileRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveRoomProfileRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.WhatIsMyIPAddressRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.WhatIsMyIPAddressRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.JoinIPCConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.JoinIPCConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteAudioClientAllRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteAudioClientAllRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StartVideoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StartVideoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StartVideoResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StartVideoResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchByEntityIDRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchByEntityIDRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchByEntityIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchByEntityIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetModeratorURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetModeratorURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetModeratorURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetModeratorURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.VidyoReplayNotAvailableFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.VidyoReplayNotAvailableFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateModeratorPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateModeratorPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateModeratorPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateModeratorPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.InvalidModeratorPINFormatFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.InvalidModeratorPINFormatFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetUserNameRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetUserNameRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetUserNameResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetUserNameResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.PauseRecordingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.PauseRecordingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.PauseRecordingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.PauseRecordingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveWebcastURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveWebcastURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LogInRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LogInRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.LogInResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.LogInResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetPresenterRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetPresenterRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetPresenterResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetPresenterResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchByEmailRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchByEmailRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SearchByEmailResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SearchByEmailResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetThumbnailPhotoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetThumbnailPhotoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.FileTooLargeFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.FileTooLargeFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.NotAllowedThumbnailPhotoFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.NotAllowedThumbnailPhotoFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DirectCallRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DirectCallRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.DirectCallResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.DirectCallResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StartRecordingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StartRecordingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StartRecordingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StartRecordingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.ResourceNotAvailableFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.ResourceNotAvailableFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetActiveSessionsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetActiveSessionsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetActiveSessionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetActiveSessionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StopLectureModeRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StopLectureModeRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.StopLectureModeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.StopLectureModeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetRoomProfileRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetRoomProfileRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.SetRoomProfileResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.SetRoomProfileResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetInviteContentRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetInviteContentRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.GetInviteContentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.GetInviteContentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.MuteAudioResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.MuteAudioResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveFromMyContactsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveFromMyContactsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateModeratorURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateModeratorURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.user.v1_1.CreateModeratorURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.user.v1_1.CreateModeratorURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.InviteToConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.InviteToConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.InviteToConferenceResponse wrapinviteToConference(){
                                com.vidyo.portal.user.v1_1.InviteToConferenceResponse wrappedElement = new com.vidyo.portal.user.v1_1.InviteToConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SearchMembersResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SearchMembersResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SearchMembersResponse wrapsearchMembers(){
                                com.vidyo.portal.user.v1_1.SearchMembersResponse wrappedElement = new com.vidyo.portal.user.v1_1.SearchMembersResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RaiseHandResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RaiseHandResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RaiseHandResponse wrapraiseHand(){
                                com.vidyo.portal.user.v1_1.RaiseHandResponse wrappedElement = new com.vidyo.portal.user.v1_1.RaiseHandResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SearchResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SearchResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SearchResponse wrapsearch(){
                                com.vidyo.portal.user.v1_1.SearchResponse wrappedElement = new com.vidyo.portal.user.v1_1.SearchResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreatePublicRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreatePublicRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreatePublicRoomResponse wrapcreatePublicRoom(){
                                com.vidyo.portal.user.v1_1.CreatePublicRoomResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreatePublicRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse wrapmuteVideoServerAll(){
                                com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse wrappedElement = new com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.StopRecordingResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.StopRecordingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.StopRecordingResponse wrapstopRecording(){
                                com.vidyo.portal.user.v1_1.StopRecordingResponse wrappedElement = new com.vidyo.portal.user.v1_1.StopRecordingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.UnmuteAudioResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.UnmuteAudioResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.UnmuteAudioResponse wrapunmuteAudio(){
                                com.vidyo.portal.user.v1_1.UnmuteAudioResponse wrappedElement = new com.vidyo.portal.user.v1_1.UnmuteAudioResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse wrapcreateScheduledRoom(){
                                com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse wrapgetLectureModeParticipants(){
                                com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.PortalPrefixResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.PortalPrefixResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.PortalPrefixResponse wrapgetPortalPrefix(){
                                com.vidyo.portal.user.v1_1.PortalPrefixResponse wrappedElement = new com.vidyo.portal.user.v1_1.PortalPrefixResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetWebcastURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetWebcastURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetWebcastURLResponse wrapgetWebcastURL(){
                                com.vidyo.portal.user.v1_1.GetWebcastURLResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetWebcastURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse wrapgetPINLengthRange(){
                                com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse wrapgetRecordingProfiles(){
                                com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreateRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreateRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreateRoomResponse wrapcreateRoom(){
                                com.vidyo.portal.user.v1_1.CreateRoomResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreateRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RemovePresenterResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RemovePresenterResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RemovePresenterResponse wrapremovePresenter(){
                                com.vidyo.portal.user.v1_1.RemovePresenterResponse wrappedElement = new com.vidyo.portal.user.v1_1.RemovePresenterResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SearchMyContactsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SearchMyContactsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SearchMyContactsResponse wrapsearchMyContacts(){
                                com.vidyo.portal.user.v1_1.SearchMyContactsResponse wrappedElement = new com.vidyo.portal.user.v1_1.SearchMyContactsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.LockRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.LockRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.LockRoomResponse wraplockRoom(){
                                com.vidyo.portal.user.v1_1.LockRoomResponse wrappedElement = new com.vidyo.portal.user.v1_1.LockRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.MyAccountResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.MyAccountResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.MyAccountResponse wrapmyAccount(){
                                com.vidyo.portal.user.v1_1.MyAccountResponse wrappedElement = new com.vidyo.portal.user.v1_1.MyAccountResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse wrapdismissAllRaisedHand(){
                                com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse wrappedElement = new com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse wrapgetRoomAccessOptions(){
                                com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse wrappedElement = new com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse wrapmuteVideoClientAll(){
                                com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse wrappedElement = new com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.ResumeRecordingResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.ResumeRecordingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.ResumeRecordingResponse wrapresumeRecording(){
                                com.vidyo.portal.user.v1_1.ResumeRecordingResponse wrappedElement = new com.vidyo.portal.user.v1_1.ResumeRecordingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetRoomProfilesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetRoomProfilesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetRoomProfilesResponse wrapgetRoomProfiles(){
                                com.vidyo.portal.user.v1_1.GetRoomProfilesResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetRoomProfilesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.DeleteRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.DeleteRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.DeleteRoomResponse wrapdeleteRoom(){
                                com.vidyo.portal.user.v1_1.DeleteRoomResponse wrappedElement = new com.vidyo.portal.user.v1_1.DeleteRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse wrapgetEntityByRoomKey(){
                                com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.ParticipantStatusResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.ParticipantStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.ParticipantStatusResponse wrapmuteAudioLocal(){
                                com.vidyo.portal.user.v1_1.ParticipantStatusResponse wrappedElement = new com.vidyo.portal.user.v1_1.ParticipantStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RemoveRoomPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RemoveRoomPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RemoveRoomPINResponse wrapremoveRoomPIN(){
                                com.vidyo.portal.user.v1_1.RemoveRoomPINResponse wrappedElement = new com.vidyo.portal.user.v1_1.RemoveRoomPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SearchRoomsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SearchRoomsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SearchRoomsResponse wrapsearchRooms(){
                                com.vidyo.portal.user.v1_1.SearchRoomsResponse wrappedElement = new com.vidyo.portal.user.v1_1.SearchRoomsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CancelOutboundCallResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CancelOutboundCallResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CancelOutboundCallResponse wrapcancelOutboundCall(){
                                com.vidyo.portal.user.v1_1.CancelOutboundCallResponse wrappedElement = new com.vidyo.portal.user.v1_1.CancelOutboundCallResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.DismissRaisedHandResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.DismissRaisedHandResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.DismissRaisedHandResponse wrapdismissRaisedHand(){
                                com.vidyo.portal.user.v1_1.DismissRaisedHandResponse wrappedElement = new com.vidyo.portal.user.v1_1.DismissRaisedHandResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.JoinConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.JoinConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.JoinConferenceResponse wrapjoinConference(){
                                com.vidyo.portal.user.v1_1.JoinConferenceResponse wrappedElement = new com.vidyo.portal.user.v1_1.JoinConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse wrapgetModeratorURLWithToken(){
                                com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.LinkEndpointResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.LinkEndpointResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.LinkEndpointResponse wraplinkEndpoint(){
                                com.vidyo.portal.user.v1_1.LinkEndpointResponse wrappedElement = new com.vidyo.portal.user.v1_1.LinkEndpointResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse wrapgetUserAccountType(){
                                com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreateWebcastPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreateWebcastPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreateWebcastPINResponse wrapcreateWebcastPIN(){
                                com.vidyo.portal.user.v1_1.CreateWebcastPINResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreateWebcastPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SetMemberModeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SetMemberModeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SetMemberModeResponse wrapsetMemberMode(){
                                com.vidyo.portal.user.v1_1.SetMemberModeResponse wrappedElement = new com.vidyo.portal.user.v1_1.SetMemberModeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse wrapcreateTestcallRoom(){
                                com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse wrapdeleteScheduledRoom(){
                                com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse wrappedElement = new com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.LeaveConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.LeaveConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.LeaveConferenceResponse wrapleaveConference(){
                                com.vidyo.portal.user.v1_1.LeaveConferenceResponse wrappedElement = new com.vidyo.portal.user.v1_1.LeaveConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.UnraiseHandResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.UnraiseHandResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.UnraiseHandResponse wrapunraiseHand(){
                                com.vidyo.portal.user.v1_1.UnraiseHandResponse wrappedElement = new com.vidyo.portal.user.v1_1.UnraiseHandResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.StopVideoResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.StopVideoResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.StopVideoResponse wrapstopVideo(){
                                com.vidyo.portal.user.v1_1.StopVideoResponse wrappedElement = new com.vidyo.portal.user.v1_1.StopVideoResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.MyEndpointStatusResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.MyEndpointStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.MyEndpointStatusResponse wrapmyEndpointStatus(){
                                com.vidyo.portal.user.v1_1.MyEndpointStatusResponse wrappedElement = new com.vidyo.portal.user.v1_1.MyEndpointStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse wrapgetEntityByEntityID(){
                                com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreateRoomPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreateRoomPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreateRoomPINResponse wrapcreateRoomPIN(){
                                com.vidyo.portal.user.v1_1.CreateRoomPINResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreateRoomPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetConferenceIDResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetConferenceIDResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetConferenceIDResponse wrapgetConferenceID(){
                                com.vidyo.portal.user.v1_1.GetConferenceIDResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetConferenceIDResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse wraplogoutAllOtherSessions(){
                                com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse wrappedElement = new com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.OnetimeAccessResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.OnetimeAccessResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.OnetimeAccessResponse wrapgetOnetimeAccessUrl(){
                                com.vidyo.portal.user.v1_1.OnetimeAccessResponse wrappedElement = new com.vidyo.portal.user.v1_1.OnetimeAccessResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreateWebcastURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreateWebcastURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreateWebcastURLResponse wrapcreateWebcastURL(){
                                com.vidyo.portal.user.v1_1.CreateWebcastURLResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreateWebcastURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetRoomProfileResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetRoomProfileResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetRoomProfileResponse wrapgetRoomProfile(){
                                com.vidyo.portal.user.v1_1.GetRoomProfileResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetRoomProfileResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse wrapremoveModeratorPIN(){
                                com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse wrappedElement = new com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse wrapsilenceSpeakerServer(){
                                com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse wrappedElement = new com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse wrapgetLoginAndWelcomeBanner(){
                                com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreateRoomURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreateRoomURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreateRoomURLResponse wrapcreateRoomURL(){
                                com.vidyo.portal.user.v1_1.CreateRoomURLResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreateRoomURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse wrapgetChangePasswordHtmlUrlWithToken(){
                                com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse wrapupdatePublicRoomDescription(){
                                com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse wrappedElement = new com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse wrapgetPortalFeatures(){
                                com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.StartLectureModeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.StartLectureModeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.StartLectureModeResponse wrapstartLectureMode(){
                                com.vidyo.portal.user.v1_1.StartLectureModeResponse wrappedElement = new com.vidyo.portal.user.v1_1.StartLectureModeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse wrapgenerateAuthToken(){
                                com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse wrappedElement = new com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetPortalVersionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetPortalVersionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetPortalVersionResponse wrapgetPortalVersion(){
                                com.vidyo.portal.user.v1_1.GetPortalVersionResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetPortalVersionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetParticipantsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetParticipantsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetParticipantsResponse wrapgetParticipants(){
                                com.vidyo.portal.user.v1_1.GetParticipantsResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetParticipantsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.AddToMyContactsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.AddToMyContactsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.AddToMyContactsResponse wrapaddToMyContacts(){
                                com.vidyo.portal.user.v1_1.AddToMyContactsResponse wrappedElement = new com.vidyo.portal.user.v1_1.AddToMyContactsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.UpdatePasswordResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.UpdatePasswordResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.UpdatePasswordResponse wrapupdatePassword(){
                                com.vidyo.portal.user.v1_1.UpdatePasswordResponse wrappedElement = new com.vidyo.portal.user.v1_1.UpdatePasswordResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse wrapdisconnectConferenceAll(){
                                com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse wrappedElement = new com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse wrapsetEndpointDetails(){
                                com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse wrappedElement = new com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse();
                                return wrappedElement;
                         }
                    
                         private com.vidyo.portal.user.v1_1.ParticipantStatusResponse wrapmuteVideoLocal(){
                                com.vidyo.portal.user.v1_1.ParticipantStatusResponse wrappedElement = new com.vidyo.portal.user.v1_1.ParticipantStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse wrapmuteAudioServerAll(){
                                com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse wrappedElement = new com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse wrapgetLogAggregationServer(){
                                com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RemoveRoomURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RemoveRoomURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RemoveRoomURLResponse wrapremoveRoomURL(){
                                com.vidyo.portal.user.v1_1.RemoveRoomURLResponse wrappedElement = new com.vidyo.portal.user.v1_1.RemoveRoomURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.UpdateLanguageResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.UpdateLanguageResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.UpdateLanguageResponse wrapupdateLanguage(){
                                com.vidyo.portal.user.v1_1.UpdateLanguageResponse wrappedElement = new com.vidyo.portal.user.v1_1.UpdateLanguageResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.LogOutResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.LogOutResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.LogOutResponse wraplogOut(){
                                com.vidyo.portal.user.v1_1.LogOutResponse wrappedElement = new com.vidyo.portal.user.v1_1.LogOutResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse wrapgetEntityDetailsByEntityID(){
                                com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.UnlockRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.UnlockRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.UnlockRoomResponse wrapunlockRoom(){
                                com.vidyo.portal.user.v1_1.UnlockRoomResponse wrappedElement = new com.vidyo.portal.user.v1_1.UnlockRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse wrapremoveModeratorURL(){
                                com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse wrappedElement = new com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse wrapremoveWebcastPIN(){
                                com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse wrappedElement = new com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse wrapremoveRoomProfile(){
                                com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse wrappedElement = new com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse wrapwhatIsMyIPAddress(){
                                com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse wrappedElement = new com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse wrapjoinIPCConference(){
                                com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse wrappedElement = new com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse wrapmuteAudioClientAll(){
                                com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse wrappedElement = new com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.StartVideoResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.StartVideoResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.StartVideoResponse wrapstartVideo(){
                                com.vidyo.portal.user.v1_1.StartVideoResponse wrappedElement = new com.vidyo.portal.user.v1_1.StartVideoResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SearchByEntityIDResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SearchByEntityIDResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SearchByEntityIDResponse wrapsearchByEntityID(){
                                com.vidyo.portal.user.v1_1.SearchByEntityIDResponse wrappedElement = new com.vidyo.portal.user.v1_1.SearchByEntityIDResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetModeratorURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetModeratorURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetModeratorURLResponse wrapgetModeratorURL(){
                                com.vidyo.portal.user.v1_1.GetModeratorURLResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetModeratorURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse wrapgetVidyoReplayLibrary(){
                                com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse wrapsilenceSpeakerServerAll(){
                                com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse wrappedElement = new com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreateModeratorPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreateModeratorPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreateModeratorPINResponse wrapcreateModeratorPIN(){
                                com.vidyo.portal.user.v1_1.CreateModeratorPINResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreateModeratorPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetUserNameResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetUserNameResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetUserNameResponse wrapgetUserName(){
                                com.vidyo.portal.user.v1_1.GetUserNameResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetUserNameResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.PauseRecordingResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.PauseRecordingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.PauseRecordingResponse wrappauseRecording(){
                                com.vidyo.portal.user.v1_1.PauseRecordingResponse wrappedElement = new com.vidyo.portal.user.v1_1.PauseRecordingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse wrapremoveWebcastURL(){
                                com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse wrappedElement = new com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.LogInResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.LogInResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.LogInResponse wraplogIn(){
                                com.vidyo.portal.user.v1_1.LogInResponse wrappedElement = new com.vidyo.portal.user.v1_1.LogInResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SetPresenterResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SetPresenterResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SetPresenterResponse wrapsetPresenter(){
                                com.vidyo.portal.user.v1_1.SetPresenterResponse wrappedElement = new com.vidyo.portal.user.v1_1.SetPresenterResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SearchByEmailResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SearchByEmailResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SearchByEmailResponse wrapsearchByEmail(){
                                com.vidyo.portal.user.v1_1.SearchByEmailResponse wrappedElement = new com.vidyo.portal.user.v1_1.SearchByEmailResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse wrapsetThumbnailPhoto(){
                                com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse wrappedElement = new com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.DirectCallResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.DirectCallResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.DirectCallResponse wrapdirectCall(){
                                com.vidyo.portal.user.v1_1.DirectCallResponse wrappedElement = new com.vidyo.portal.user.v1_1.DirectCallResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.StartRecordingResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.StartRecordingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.StartRecordingResponse wrapstartRecording(){
                                com.vidyo.portal.user.v1_1.StartRecordingResponse wrappedElement = new com.vidyo.portal.user.v1_1.StartRecordingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetActiveSessionsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetActiveSessionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetActiveSessionsResponse wrapgetActiveSessions(){
                                com.vidyo.portal.user.v1_1.GetActiveSessionsResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetActiveSessionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.StopLectureModeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.StopLectureModeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.StopLectureModeResponse wrapstopLectureMode(){
                                com.vidyo.portal.user.v1_1.StopLectureModeResponse wrappedElement = new com.vidyo.portal.user.v1_1.StopLectureModeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.SetRoomProfileResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.SetRoomProfileResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.SetRoomProfileResponse wrapsetRoomProfile(){
                                com.vidyo.portal.user.v1_1.SetRoomProfileResponse wrappedElement = new com.vidyo.portal.user.v1_1.SetRoomProfileResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.GetInviteContentResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.GetInviteContentResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.GetInviteContentResponse wrapgetInviteContent(){
                                com.vidyo.portal.user.v1_1.GetInviteContentResponse wrappedElement = new com.vidyo.portal.user.v1_1.GetInviteContentResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.MuteAudioResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.MuteAudioResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.MuteAudioResponse wrapmuteAudio(){
                                com.vidyo.portal.user.v1_1.MuteAudioResponse wrappedElement = new com.vidyo.portal.user.v1_1.MuteAudioResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse wrapremoveFromMyContacts(){
                                com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse wrappedElement = new com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.user.v1_1.CreateModeratorURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.user.v1_1.CreateModeratorURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.user.v1_1.CreateModeratorURLResponse wrapcreateModeratorURL(){
                                com.vidyo.portal.user.v1_1.CreateModeratorURLResponse wrappedElement = new com.vidyo.portal.user.v1_1.CreateModeratorURLResponse();
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
        
                if (com.vidyo.portal.user.v1_1.InviteToConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InviteToConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InviteToConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InviteToConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchMembersRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchMembersRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchMembersResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchMembersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RaiseHandRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RaiseHandRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RaiseHandResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RaiseHandResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreatePublicRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreatePublicRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreatePublicRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreatePublicRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.PublicRoomCreationFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.PublicRoomCreationFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotAllowedToCreateFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotAllowedToCreateFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteVideoServerAllRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteVideoServerAllRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StopRecordingRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StopRecordingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StopRecordingResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StopRecordingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UnmuteAudioRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UnmuteAudioRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UnmuteAudioResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UnmuteAudioResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateScheduledRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateScheduledRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ScheduledRoomCreationFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ScheduledRoomCreationFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetLectureModeParticipantsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetLectureModeParticipantsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.PortalPrefixRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.PortalPrefixRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.PortalPrefixResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.PortalPrefixResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.PrefixNotConfiguredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.PrefixNotConfiguredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetWebcastURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetWebcastURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetWebcastURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetWebcastURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetPINLengthRangeRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetPINLengthRangeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetRecordingProfilesRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetRecordingProfilesRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemovePresenterRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemovePresenterRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemovePresenterResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemovePresenterResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchMyContactsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchMyContactsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchMyContactsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchMyContactsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LockRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LockRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LockRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LockRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MyAccountRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MyAccountRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MyAccountResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MyAccountResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DismissAllRaisedHandRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DismissAllRaisedHandRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RoomAccessOptionsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RoomAccessOptionsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteVideoClientAllRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteVideoClientAllRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ResumeRecordingRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ResumeRecordingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ResumeRecordingResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ResumeRecordingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetRoomProfilesRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetRoomProfilesRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetRoomProfilesResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetRoomProfilesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DeleteRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DeleteRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DeleteRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DeleteRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetEntityByRoomKeyRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetEntityByRoomKeyRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteAudioRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteAudioRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ParticipantStatusResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ParticipantStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveRoomPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveRoomPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveRoomPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveRoomPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchRoomsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchRoomsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchRoomsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchRoomsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CancelOutboundCallRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CancelOutboundCallRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CancelOutboundCallResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CancelOutboundCallResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DismissRaisedHandRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DismissRaisedHandRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DismissRaisedHandResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DismissRaisedHandResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.JoinConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.JoinConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.JoinConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.JoinConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.WrongPINFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.WrongPINFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ConferenceLockedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ConferenceLockedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LinkEndpointRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LinkEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LinkEndpointResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LinkEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.AccessRestrictedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.AccessRestrictedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetUserAccountTypeRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetUserAccountTypeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateWebcastPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateWebcastPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateWebcastPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateWebcastPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetMemberModeRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetMemberModeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetMemberModeResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetMemberModeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateTestcallRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateTestcallRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.TestcallRoomCreationFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.TestcallRoomCreationFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DeleteScheduledRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DeleteScheduledRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LeaveConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LeaveConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LeaveConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LeaveConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UnraiseHandRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UnraiseHandRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UnraiseHandResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UnraiseHandResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StopVideoRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StopVideoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StopVideoResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StopVideoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MyEndpointStatusRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MyEndpointStatusRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MyEndpointStatusResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MyEndpointStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetEntityByEntityIDRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetEntityByEntityIDRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateRoomPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateRoomPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateRoomPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateRoomPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetConferenceIDRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetConferenceIDRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetConferenceIDResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetConferenceIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InPointToPointCallFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InPointToPointCallFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.OnetimeAccessRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.OnetimeAccessRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.OnetimeAccessResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.OnetimeAccessResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateWebcastURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateWebcastURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateWebcastURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateWebcastURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetRoomProfileRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetRoomProfileRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetRoomProfileResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetRoomProfileResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveModeratorPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveModeratorPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SilenceSpeakerServerRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SilenceSpeakerServerRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidConferenceFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidConferenceFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidParticipantFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidParticipantFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.FeatureNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.FeatureNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateRoomURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateRoomURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateRoomURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateRoomURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.PublicRoomDescUpdationFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.PublicRoomDescUpdationFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetPortalFeaturesRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetPortalFeaturesRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StartLectureModeRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StartLectureModeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StartLectureModeResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StartLectureModeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GenerateAuthTokenRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GenerateAuthTokenRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.EndpointNotBoundFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.EndpointNotBoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetPortalVersionRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetPortalVersionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetPortalVersionResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetPortalVersionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetParticipantsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetParticipantsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetParticipantsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetParticipantsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.AddToMyContactsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.AddToMyContactsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.AddToMyContactsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.AddToMyContactsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UpdatePasswordRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UpdatePasswordRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UpdatePasswordResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UpdatePasswordResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DisconnectConferenceAllRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DisconnectConferenceAllRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetEndpointDetailsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetEndpointDetailsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.EndpointNotBoundFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.EndpointNotBoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteVideoRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteVideoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ParticipantStatusResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ParticipantStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteAudioServerAllRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteAudioServerAllRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetLogAggregationServerRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetLogAggregationServerRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LogAggregationDisabledFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LogAggregationDisabledFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveRoomURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveRoomURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveRoomURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveRoomURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UpdateLanguageRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UpdateLanguageRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UpdateLanguageResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UpdateLanguageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LogOutRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LogOutRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LogOutResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LogOutResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UnlockRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UnlockRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.UnlockRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.UnlockRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveModeratorURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveModeratorURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveWebcastPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveWebcastPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveRoomProfileRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveRoomProfileRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.WhatIsMyIPAddressRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.WhatIsMyIPAddressRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.JoinIPCConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.JoinIPCConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.WrongPINFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.WrongPINFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ConferenceLockedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ConferenceLockedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteAudioClientAllRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteAudioClientAllRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StartVideoRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StartVideoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StartVideoResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StartVideoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchByEntityIDRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchByEntityIDRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchByEntityIDResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchByEntityIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetModeratorURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetModeratorURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetModeratorURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetModeratorURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.VidyoReplayNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.VidyoReplayNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidConferenceFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidConferenceFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateModeratorPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateModeratorPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateModeratorPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateModeratorPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidModeratorPINFormatFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidModeratorPINFormatFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetUserNameRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetUserNameRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetUserNameResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetUserNameResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.PauseRecordingRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.PauseRecordingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.PauseRecordingResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.PauseRecordingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveWebcastURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveWebcastURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LogInRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LogInRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.LogInResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.LogInResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetPresenterRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetPresenterRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetPresenterResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetPresenterResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchByEmailRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchByEmailRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SearchByEmailResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SearchByEmailResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetThumbnailPhotoRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetThumbnailPhotoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.FileTooLargeFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.FileTooLargeFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotAllowedThumbnailPhotoFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotAllowedThumbnailPhotoFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DirectCallRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DirectCallRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.DirectCallResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.DirectCallResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StartRecordingRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StartRecordingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StartRecordingResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StartRecordingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetActiveSessionsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetActiveSessionsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetActiveSessionsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetActiveSessionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StopLectureModeRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StopLectureModeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.StopLectureModeResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.StopLectureModeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetRoomProfileRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetRoomProfileRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SetRoomProfileResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SetRoomProfileResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetInviteContentRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetInviteContentRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GetInviteContentResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GetInviteContentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteAudioRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteAudioRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.MuteAudioResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.MuteAudioResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveFromMyContactsRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveFromMyContactsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.SeatLicenseExpiredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateModeratorURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateModeratorURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.CreateModeratorURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.CreateModeratorURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.ControlMeetingFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.ControlMeetingFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.user.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.user.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    