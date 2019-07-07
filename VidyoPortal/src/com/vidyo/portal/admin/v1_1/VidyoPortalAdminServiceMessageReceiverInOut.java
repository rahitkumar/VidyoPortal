
/**
 * VidyoPortalAdminServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.vidyo.portal.admin.v1_1;

        /**
        *  VidyoPortalAdminServiceMessageReceiverInOut message receiver
        */

        public class VidyoPortalAdminServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        VidyoPortalAdminServiceSkeletonInterface skel = (VidyoPortalAdminServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("removeRoomURL".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse removeRoomURLResponse451 = null;
	                        com.vidyo.portal.admin.v1_1.RemoveRoomURLRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.RemoveRoomURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.RemoveRoomURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomURLResponse451 =
                                                   
                                                   
                                                         skel.removeRoomURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomURLResponse451, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "removeRoomURL"));
                                    } else 

            if("dismissAllRaisedHand".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse dismissAllRaisedHandResponse453 = null;
	                        com.vidyo.portal.admin.v1_1.DismissAllRaisedHandRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.DismissAllRaisedHandRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.DismissAllRaisedHandRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               dismissAllRaisedHandResponse453 =
                                                   
                                                   
                                                         skel.dismissAllRaisedHand(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), dismissAllRaisedHandResponse453, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "dismissAllRaisedHand"));
                                    } else 

            if("getParticipants".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetParticipantsResponse getParticipantsResponse455 = null;
	                        com.vidyo.portal.admin.v1_1.GetParticipantsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetParticipantsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetParticipantsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getParticipantsResponse455 =
                                                   
                                                   
                                                         skel.getParticipants(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getParticipantsResponse455, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getParticipants"));
                                    } else 

            if("getPortalVersion".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetPortalVersionResponse getPortalVersionResponse457 = null;
	                        com.vidyo.portal.admin.v1_1.GetPortalVersionRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetPortalVersionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetPortalVersionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getPortalVersionResponse457 =
                                                   
                                                   
                                                         skel.getPortalVersion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getPortalVersionResponse457, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getPortalVersion"));
                                    } else 

            if("createScheduledRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse createScheduledRoomResponse459 = null;
	                        com.vidyo.portal.admin.v1_1.CreateScheduledRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.CreateScheduledRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.CreateScheduledRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createScheduledRoomResponse459 =
                                                   
                                                   
                                                         skel.createScheduledRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createScheduledRoomResponse459, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "createScheduledRoom"));
                                    } else 

            if("getWebcastURL".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetWebcastURLResponse getWebcastURLResponse461 = null;
	                        com.vidyo.portal.admin.v1_1.GetWebcastURLRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetWebcastURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetWebcastURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getWebcastURLResponse461 =
                                                   
                                                   
                                                         skel.getWebcastURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getWebcastURLResponse461, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getWebcastURL"));
                                    } else 

            if("getLectureModeParticipants".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse getLectureModeParticipantsResponse463 = null;
	                        com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLectureModeParticipantsResponse463 =
                                                   
                                                   
                                                         skel.getLectureModeParticipants(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLectureModeParticipantsResponse463, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getLectureModeParticipants"));
                                    } else 

            if("enableScheduledRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse enableScheduledRoomResponse465 = null;
	                        com.vidyo.portal.admin.v1_1.EnableScheduledRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.EnableScheduledRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.EnableScheduledRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               enableScheduledRoomResponse465 =
                                                   
                                                   
                                                         skel.enableScheduledRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), enableScheduledRoomResponse465, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "enableScheduledRoom"));
                                    } else 

            if("addMember".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.AddMemberResponse addMemberResponse467 = null;
	                        com.vidyo.portal.admin.v1_1.AddMemberRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.AddMemberRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.AddMemberRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addMemberResponse467 =
                                                   
                                                   
                                                         skel.addMember(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addMemberResponse467, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "addMember"));
                                    } else 

            if("getRecordingProfiles".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse getRecordingProfilesResponse469 = null;
	                        com.vidyo.portal.admin.v1_1.GetRecordingProfilesRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetRecordingProfilesRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetRecordingProfilesRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRecordingProfilesResponse469 =
                                                   
                                                   
                                                         skel.getRecordingProfiles(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRecordingProfilesResponse469, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getRecordingProfiles"));
                                    } else 

            if("removePresenter".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.RemovePresenterResponse removePresenterResponse471 = null;
	                        com.vidyo.portal.admin.v1_1.RemovePresenterRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.RemovePresenterRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.RemovePresenterRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removePresenterResponse471 =
                                                   
                                                   
                                                         skel.removePresenter(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removePresenterResponse471, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "removePresenter"));
                                    } else 

            if("getTenantDetails".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse getTenantDetailsResponse473 = null;
	                        com.vidyo.portal.admin.v1_1.GetTenantDetailsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetTenantDetailsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetTenantDetailsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getTenantDetailsResponse473 =
                                                   
                                                   
                                                         skel.getTenantDetails(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getTenantDetailsResponse473, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getTenantDetails"));
                                    } else 

            if("searchMembers".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.SearchMembersResponse searchMembersResponse475 = null;
	                        com.vidyo.portal.admin.v1_1.SearchMembersRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.SearchMembersRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.SearchMembersRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchMembersResponse475 =
                                                   
                                                   
                                                         skel.searchMembers(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchMembersResponse475, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "searchMembers"));
                                    } else 

            if("getRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetRoomResponse getRoomResponse477 = null;
	                        com.vidyo.portal.admin.v1_1.GetRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRoomResponse477 =
                                                   
                                                   
                                                         skel.getRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRoomResponse477, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getRoom"));
                                    } else 

            if("createPublicRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse createPublicRoomResponse479 = null;
	                        com.vidyo.portal.admin.v1_1.CreatePublicRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.CreatePublicRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.CreatePublicRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createPublicRoomResponse479 =
                                                   
                                                   
                                                         skel.createPublicRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createPublicRoomResponse479, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "createPublicRoom"));
                                    } else 

            if("addClientVersion".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.AddClientVersionResponse addClientVersionResponse481 = null;
	                        com.vidyo.portal.admin.v1_1.AddClientVersionRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.AddClientVersionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.AddClientVersionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addClientVersionResponse481 =
                                                   
                                                   
                                                         skel.addClientVersion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addClientVersionResponse481, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "addClientVersion"));
                                    } else 

            if("transferParticipant".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.TransferParticipantResponse transferParticipantResponse483 = null;
	                        com.vidyo.portal.admin.v1_1.TransferParticipantRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.TransferParticipantRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.TransferParticipantRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               transferParticipantResponse483 =
                                                   
                                                   
                                                         skel.transferParticipant(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), transferParticipantResponse483, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "transferParticipant"));
                                    } else 

            if("createRoomURL".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.CreateRoomURLResponse createRoomURLResponse485 = null;
	                        com.vidyo.portal.admin.v1_1.CreateRoomURLRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.CreateRoomURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.CreateRoomURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomURLResponse485 =
                                                   
                                                   
                                                         skel.createRoomURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomURLResponse485, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "createRoomURL"));
                                    } else 

            if("stopRecording".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.StopRecordingResponse stopRecordingResponse487 = null;
	                        com.vidyo.portal.admin.v1_1.StopRecordingRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.StopRecordingRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.StopRecordingRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               stopRecordingResponse487 =
                                                   
                                                   
                                                         skel.stopRecording(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), stopRecordingResponse487, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "stopRecording"));
                                    } else 

            if("unmuteAudio".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.UnmuteAudioResponse unmuteAudioResponse489 = null;
	                        com.vidyo.portal.admin.v1_1.UnmuteAudioRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.UnmuteAudioRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.UnmuteAudioRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unmuteAudioResponse489 =
                                                   
                                                   
                                                         skel.unmuteAudio(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unmuteAudioResponse489, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "unmuteAudio"));
                                    } else 

            if("roomIsEnabled".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse roomIsEnabledResponse491 = null;
	                        com.vidyo.portal.admin.v1_1.RoomIsEnabledRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.RoomIsEnabledRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.RoomIsEnabledRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               roomIsEnabledResponse491 =
                                                   
                                                   
                                                         skel.roomIsEnabled(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), roomIsEnabledResponse491, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "roomIsEnabled"));
                                    } else 

            if("addGroup".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.AddGroupResponse addGroupResponse493 = null;
	                        com.vidyo.portal.admin.v1_1.AddGroupRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.AddGroupRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.AddGroupRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addGroupResponse493 =
                                                   
                                                   
                                                         skel.addGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addGroupResponse493, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "addGroup"));
                                    } else 

            if("deleteEndpointBehavior".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse deleteEndpointBehaviorResponse495 = null;
	                        com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteEndpointBehaviorResponse495 =
                                                   
                                                   
                                                         skel.deleteEndpointBehavior(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteEndpointBehaviorResponse495, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "deleteEndpointBehavior"));
                                    } else 

            if("getRooms".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetRoomsResponse getRoomsResponse497 = null;
	                        com.vidyo.portal.admin.v1_1.GetRoomsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetRoomsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetRoomsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRoomsResponse497 =
                                                   
                                                   
                                                         skel.getRooms(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRoomsResponse497, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getRooms"));
                                    } else 

            if("deleteGroup".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.DeleteGroupResponse deleteGroupResponse499 = null;
	                        com.vidyo.portal.admin.v1_1.DeleteGroupRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.DeleteGroupRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.DeleteGroupRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteGroupResponse499 =
                                                   
                                                   
                                                         skel.deleteGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteGroupResponse499, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "deleteGroup"));
                                    } else 

            if("startLectureMode".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.StartLectureModeResponse startLectureModeResponse501 = null;
	                        com.vidyo.portal.admin.v1_1.StartLectureModeRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.StartLectureModeRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.StartLectureModeRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startLectureModeResponse501 =
                                                   
                                                   
                                                         skel.startLectureMode(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startLectureModeResponse501, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "startLectureMode"));
                                    } else 

            if("createWebcastURL".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse createWebcastURLResponse503 = null;
	                        com.vidyo.portal.admin.v1_1.CreateWebcastURLRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.CreateWebcastURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.CreateWebcastURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createWebcastURLResponse503 =
                                                   
                                                   
                                                         skel.createWebcastURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createWebcastURLResponse503, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "createWebcastURL"));
                                    } else 

            if("inviteToConference".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.InviteToConferenceResponse inviteToConferenceResponse505 = null;
	                        com.vidyo.portal.admin.v1_1.InviteToConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.InviteToConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.InviteToConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               inviteToConferenceResponse505 =
                                                   
                                                   
                                                         skel.inviteToConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), inviteToConferenceResponse505, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "inviteToConference"));
                                    } else 

            if("removeModeratorPIN".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse removeModeratorPINResponse507 = null;
	                        com.vidyo.portal.admin.v1_1.RemoveModeratorPINRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.RemoveModeratorPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.RemoveModeratorPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeModeratorPINResponse507 =
                                                   
                                                   
                                                         skel.removeModeratorPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeModeratorPINResponse507, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "removeModeratorPIN"));
                                    } else 

            if("getRoomProfile".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetRoomProfileResponse getRoomProfileResponse509 = null;
	                        com.vidyo.portal.admin.v1_1.GetRoomProfileRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetRoomProfileRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetRoomProfileRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRoomProfileResponse509 =
                                                   
                                                   
                                                         skel.getRoomProfile(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRoomProfileResponse509, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getRoomProfile"));
                                    } else 

            if("getLicenseData".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetLicenseDataResponse getLicenseDataResponse511 = null;
	                        com.vidyo.portal.admin.v1_1.GetLicenseDataRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetLicenseDataRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetLicenseDataRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLicenseDataResponse511 =
                                                   
                                                   
                                                         skel.getLicenseData(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLicenseDataResponse511, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getLicenseData"));
                                    } else 

            if("setTenantRoomAttributes".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse setTenantRoomAttributesResponse513 = null;
	                        com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setTenantRoomAttributesResponse513 =
                                                   
                                                   
                                                         skel.setTenantRoomAttributes(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setTenantRoomAttributesResponse513, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "setTenantRoomAttributes"));
                                    } else 

            if("updateGroup".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.UpdateGroupResponse updateGroupResponse515 = null;
	                        com.vidyo.portal.admin.v1_1.UpdateGroupRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.UpdateGroupRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.UpdateGroupRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateGroupResponse515 =
                                                   
                                                   
                                                         skel.updateGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateGroupResponse515, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "updateGroup"));
                                    } else 

            if("isScheduledRoomEnabled".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse scheduledRoomEnabledResponse517 = null;
	                        com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               scheduledRoomEnabledResponse517 =
                                                   
                                                   
                                                         skel.isScheduledRoomEnabled(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), scheduledRoomEnabledResponse517, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "isScheduledRoomEnabled"));
                                    } else 

            if("getMember".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetMemberResponse getMemberResponse519 = null;
	                        com.vidyo.portal.admin.v1_1.GetMemberRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetMemberRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetMemberRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getMemberResponse519 =
                                                   
                                                   
                                                         skel.getMember(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getMemberResponse519, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getMember"));
                                    } else 

            if("startRecording".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.StartRecordingResponse startRecordingResponse521 = null;
	                        com.vidyo.portal.admin.v1_1.StartRecordingRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.StartRecordingRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.StartRecordingRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startRecordingResponse521 =
                                                   
                                                   
                                                         skel.startRecording(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startRecordingResponse521, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "startRecording"));
                                    } else 

            if("getGroup".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetGroupResponse getGroupResponse523 = null;
	                        com.vidyo.portal.admin.v1_1.GetGroupRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetGroupRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetGroupRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getGroupResponse523 =
                                                   
                                                   
                                                         skel.getGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getGroupResponse523, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getGroup"));
                                    } else 

            if("leaveConference".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.LeaveConferenceResponse leaveConferenceResponse525 = null;
	                        com.vidyo.portal.admin.v1_1.LeaveConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.LeaveConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.LeaveConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               leaveConferenceResponse525 =
                                                   
                                                   
                                                         skel.leaveConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), leaveConferenceResponse525, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "leaveConference"));
                                    } else 

            if("stopLectureMode".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.StopLectureModeResponse stopLectureModeResponse527 = null;
	                        com.vidyo.portal.admin.v1_1.StopLectureModeRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.StopLectureModeRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.StopLectureModeRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               stopLectureModeResponse527 =
                                                   
                                                   
                                                         skel.stopLectureMode(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), stopLectureModeResponse527, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "stopLectureMode"));
                                    } else 

            if("setRoomProfile".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.SetRoomProfileResponse setRoomProfileResponse529 = null;
	                        com.vidyo.portal.admin.v1_1.SetRoomProfileRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.SetRoomProfileRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.SetRoomProfileRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setRoomProfileResponse529 =
                                                   
                                                   
                                                         skel.setRoomProfile(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setRoomProfileResponse529, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "setRoomProfile"));
                                    } else 

            if("muteAudio".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.MuteAudioResponse muteAudioResponse531 = null;
	                        com.vidyo.portal.admin.v1_1.MuteAudioRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.MuteAudioRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.MuteAudioRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               muteAudioResponse531 =
                                                   
                                                   
                                                         skel.muteAudio(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), muteAudioResponse531, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "muteAudio"));
                                    } else 

            if("getMembers".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetMembersResponse getMembersResponse533 = null;
	                        com.vidyo.portal.admin.v1_1.GetMembersRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetMembersRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetMembersRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getMembersResponse533 =
                                                   
                                                   
                                                         skel.getMembers(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getMembersResponse533, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getMembers"));
                                    } else 

            if("stopVideo".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.StopVideoResponse stopVideoResponse535 = null;
	                        com.vidyo.portal.admin.v1_1.StopVideoRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.StopVideoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.StopVideoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               stopVideoResponse535 =
                                                   
                                                   
                                                         skel.stopVideo(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), stopVideoResponse535, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "stopVideo"));
                                    } else 

            if("deleteMember".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.DeleteMemberResponse deleteMemberResponse537 = null;
	                        com.vidyo.portal.admin.v1_1.DeleteMemberRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.DeleteMemberRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.DeleteMemberRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteMemberResponse537 =
                                                   
                                                   
                                                         skel.deleteMember(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteMemberResponse537, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "deleteMember"));
                                    } else 

            if("updateEndpointBehavior".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse updateEndpointBehaviorResponse539 = null;
	                        com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateEndpointBehaviorResponse539 =
                                                   
                                                   
                                                         skel.updateEndpointBehavior(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateEndpointBehaviorResponse539, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "updateEndpointBehavior"));
                                    } else 

            if("createRoomPIN".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.CreateRoomPINResponse createRoomPINResponse541 = null;
	                        com.vidyo.portal.admin.v1_1.CreateRoomPINRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.CreateRoomPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.CreateRoomPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomPINResponse541 =
                                                   
                                                   
                                                         skel.createRoomPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomPINResponse541, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "createRoomPIN"));
                                    } else 

            if("getConferenceID".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetConferenceIDResponse getConferenceIDResponse543 = null;
	                        com.vidyo.portal.admin.v1_1.GetConferenceIDRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetConferenceIDRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetConferenceIDRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getConferenceIDResponse543 =
                                                   
                                                   
                                                         skel.getConferenceID(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getConferenceIDResponse543, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getConferenceID"));
                                    } else 

            if("pauseRecording".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.PauseRecordingResponse pauseRecordingResponse545 = null;
	                        com.vidyo.portal.admin.v1_1.PauseRecordingRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.PauseRecordingRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.PauseRecordingRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               pauseRecordingResponse545 =
                                                   
                                                   
                                                         skel.pauseRecording(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), pauseRecordingResponse545, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "pauseRecording"));
                                    } else 

            if("removeWebcastURL".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse removeWebcastURLResponse547 = null;
	                        com.vidyo.portal.admin.v1_1.RemoveWebcastURLRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.RemoveWebcastURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.RemoveWebcastURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeWebcastURLResponse547 =
                                                   
                                                   
                                                         skel.removeWebcastURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeWebcastURLResponse547, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "removeWebcastURL"));
                                    } else 

            if("updateMember".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.UpdateMemberResponse updateMemberResponse549 = null;
	                        com.vidyo.portal.admin.v1_1.UpdateMemberRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.UpdateMemberRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.UpdateMemberRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateMemberResponse549 =
                                                   
                                                   
                                                         skel.updateMember(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateMemberResponse549, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "updateMember"));
                                    } else 

            if("createWebcastPIN".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse createWebcastPINResponse551 = null;
	                        com.vidyo.portal.admin.v1_1.CreateWebcastPINRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.CreateWebcastPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.CreateWebcastPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createWebcastPINResponse551 =
                                                   
                                                   
                                                         skel.createWebcastPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createWebcastPINResponse551, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "createWebcastPIN"));
                                    } else 

            if("deleteScheduledRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse deleteScheduledRoomResponse553 = null;
	                        com.vidyo.portal.admin.v1_1.DeleteScheduledRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.DeleteScheduledRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.DeleteScheduledRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteScheduledRoomResponse553 =
                                                   
                                                   
                                                         skel.deleteScheduledRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteScheduledRoomResponse553, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "deleteScheduledRoom"));
                                    } else 

            if("setPresenter".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.SetPresenterResponse setPresenterResponse555 = null;
	                        com.vidyo.portal.admin.v1_1.SetPresenterRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.SetPresenterRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.SetPresenterRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setPresenterResponse555 =
                                                   
                                                   
                                                         skel.setPresenter(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setPresenterResponse555, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "setPresenter"));
                                    } else 

            if("enableRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.EnableRoomResponse enableRoomResponse557 = null;
	                        com.vidyo.portal.admin.v1_1.EnableRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.EnableRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.EnableRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               enableRoomResponse557 =
                                                   
                                                   
                                                         skel.enableRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), enableRoomResponse557, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "enableRoom"));
                                    } else 

            if("setChatStateAdmin".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse setChatStateAdminResponse559 = null;
	                        com.vidyo.portal.admin.v1_1.SetChatStateAdminRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.SetChatStateAdminRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.SetChatStateAdminRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setChatStateAdminResponse559 =
                                                   
                                                   
                                                         skel.setChatStateAdmin(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setChatStateAdminResponse559, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "setChatStateAdmin"));
                                    } else 

            if("removeRoomPIN".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse removeRoomPINResponse561 = null;
	                        com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomPINResponse561 =
                                                   
                                                   
                                                         skel.removeRoomPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomPINResponse561, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "removeRoomPIN"));
                                    } else 

            if("addRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.AddRoomResponse addRoomResponse563 = null;
	                        com.vidyo.portal.admin.v1_1.AddRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.AddRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.AddRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addRoomResponse563 =
                                                   
                                                   
                                                         skel.addRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addRoomResponse563, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "addRoom"));
                                    } else 

            if("createModeratorPIN".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse createModeratorPINResponse565 = null;
	                        com.vidyo.portal.admin.v1_1.CreateModeratorPINRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.CreateModeratorPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.CreateModeratorPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createModeratorPINResponse565 =
                                                   
                                                   
                                                         skel.createModeratorPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createModeratorPINResponse565, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "createModeratorPIN"));
                                    } else 

            if("searchRooms".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.SearchRoomsResponse searchRoomsResponse567 = null;
	                        com.vidyo.portal.admin.v1_1.SearchRoomsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.SearchRoomsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.SearchRoomsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               searchRoomsResponse567 =
                                                   
                                                   
                                                         skel.searchRooms(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), searchRoomsResponse567, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "searchRooms"));
                                    } else 

            if("cancelOutboundCall".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse cancelOutboundCallResponse569 = null;
	                        com.vidyo.portal.admin.v1_1.CancelOutboundCallRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.CancelOutboundCallRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.CancelOutboundCallRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               cancelOutboundCallResponse569 =
                                                   
                                                   
                                                         skel.cancelOutboundCall(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), cancelOutboundCallResponse569, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "cancelOutboundCall"));
                                    } else 

            if("getGroups".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetGroupsResponse getGroupsResponse571 = null;
	                        com.vidyo.portal.admin.v1_1.GetGroupsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetGroupsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetGroupsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getGroupsResponse571 =
                                                   
                                                   
                                                         skel.getGroups(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getGroupsResponse571, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getGroups"));
                                    } else 

            if("updateRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.UpdateRoomResponse updateRoomResponse573 = null;
	                        com.vidyo.portal.admin.v1_1.UpdateRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.UpdateRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.UpdateRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateRoomResponse573 =
                                                   
                                                   
                                                         skel.updateRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateRoomResponse573, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "updateRoom"));
                                    } else 

            if("disableScheduledRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse disableScheduledRoomResponse575 = null;
	                        com.vidyo.portal.admin.v1_1.DisableScheduledRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.DisableScheduledRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.DisableScheduledRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               disableScheduledRoomResponse575 =
                                                   
                                                   
                                                         skel.disableScheduledRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), disableScheduledRoomResponse575, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "disableScheduledRoom"));
                                    } else 

            if("dismissRaisedHand".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse dismissRaisedHandResponse577 = null;
	                        com.vidyo.portal.admin.v1_1.DismissRaisedHandRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.DismissRaisedHandRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.DismissRaisedHandRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               dismissRaisedHandResponse577 =
                                                   
                                                   
                                                         skel.dismissRaisedHand(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), dismissRaisedHandResponse577, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "dismissRaisedHand"));
                                    } else 

            if("getLocationTags".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetLocationTagsResponse getLocationTagsResponse579 = null;
	                        com.vidyo.portal.admin.v1_1.GetLocationTagsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetLocationTagsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetLocationTagsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLocationTagsResponse579 =
                                                   
                                                   
                                                         skel.getLocationTags(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLocationTagsResponse579, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getLocationTags"));
                                    } else 

            if("createEndpointBehavior".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse createEndpointBehaviorResponse581 = null;
	                        com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createEndpointBehaviorResponse581 =
                                                   
                                                   
                                                         skel.createEndpointBehavior(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createEndpointBehaviorResponse581, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "createEndpointBehavior"));
                                    } else 

            if("removeWebcastPIN".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse removeWebcastPINResponse583 = null;
	                        com.vidyo.portal.admin.v1_1.RemoveWebcastPINRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.RemoveWebcastPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.RemoveWebcastPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeWebcastPINResponse583 =
                                                   
                                                   
                                                         skel.removeWebcastPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeWebcastPINResponse583, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "removeWebcastPIN"));
                                    } else 

            if("removeRoomProfile".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse removeRoomProfileResponse585 = null;
	                        com.vidyo.portal.admin.v1_1.RemoveRoomProfileRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.RemoveRoomProfileRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.RemoveRoomProfileRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomProfileResponse585 =
                                                   
                                                   
                                                         skel.removeRoomProfile(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomProfileResponse585, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "removeRoomProfile"));
                                    } else 

            if("scheduledRoomIsEnabled".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse scheduledRoomIsEnabledResponse587 = null;
	                        com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               scheduledRoomIsEnabledResponse587 =
                                                   
                                                   
                                                         skel.scheduledRoomIsEnabled(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), scheduledRoomIsEnabledResponse587, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "scheduledRoomIsEnabled"));
                                    } else 

            if("resumeRecording".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.ResumeRecordingResponse resumeRecordingResponse589 = null;
	                        com.vidyo.portal.admin.v1_1.ResumeRecordingRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.ResumeRecordingRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.ResumeRecordingRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               resumeRecordingResponse589 =
                                                   
                                                   
                                                         skel.resumeRecording(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), resumeRecordingResponse589, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "resumeRecording"));
                                    } else 

            if("startVideo".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.StartVideoResponse startVideoResponse591 = null;
	                        com.vidyo.portal.admin.v1_1.StartVideoRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.StartVideoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.StartVideoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startVideoResponse591 =
                                                   
                                                   
                                                         skel.startVideo(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startVideoResponse591, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "startVideo"));
                                    } else 

            if("getEndpointBehavior".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse getEndpointBehaviorResponse593 = null;
	                        com.vidyo.portal.admin.v1_1.GetEndpointBehaviorRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetEndpointBehaviorRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetEndpointBehaviorRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getEndpointBehaviorResponse593 =
                                                   
                                                   
                                                         skel.getEndpointBehavior(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getEndpointBehaviorResponse593, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getEndpointBehavior"));
                                    } else 

            if("deleteRoom".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.DeleteRoomResponse deleteRoomResponse595 = null;
	                        com.vidyo.portal.admin.v1_1.DeleteRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.DeleteRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.DeleteRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteRoomResponse595 =
                                                   
                                                   
                                                         skel.deleteRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteRoomResponse595, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "deleteRoom"));
                                    } else 

            if("getRoomProfiles".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse getRoomProfilesResponse597 = null;
	                        com.vidyo.portal.admin.v1_1.GetRoomProfilesRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.GetRoomProfilesRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.GetRoomProfilesRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRoomProfilesResponse597 =
                                                   
                                                   
                                                         skel.getRoomProfiles(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRoomProfilesResponse597, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "getRoomProfiles"));
                                    } else 

            if("setLayout".equals(methodName)){
                
                com.vidyo.portal.admin.v1_1.SetLayoutResponse setLayoutResponse599 = null;
	                        com.vidyo.portal.admin.v1_1.SetLayoutRequest wrappedParam =
                                                             (com.vidyo.portal.admin.v1_1.SetLayoutRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.v1_1.SetLayoutRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setLayoutResponse599 =
                                                   
                                                   
                                                         skel.setLayout(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setLayoutResponse599, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                    "setLayout"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (RoomAlreadyExistsFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"RoomAlreadyExistsFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (PublicRoomCreationFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"PublicRoomCreationFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (GroupAlreadyExistsFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"GroupAlreadyExistsFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (MemberAlreadyExistsFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"MemberAlreadyExistsFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (NoEndpointBehaviorExistsFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NoEndpointBehaviorExistsFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (EndpointBehaviorAlreadyExistsFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"EndpointBehaviorAlreadyExistsFault");
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
         catch (EndpointBehaviorDisabledFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"EndpointBehaviorDisabledFault");
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
         catch (InPointToPointCallFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InPointToPointCallFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (GroupNotFoundFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"GroupNotFoundFault");
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
         catch (NotAllowedToCreateFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotAllowedToCreateFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (ChatNotAvailableInSuperFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ChatNotAvailableInSuperFault");
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
         catch (ExternalModeFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ExternalModeFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (MemberNotFoundFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"MemberNotFoundFault");
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
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveRoomURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveRoomURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.NotLicensedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.NotLicensedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DismissAllRaisedHandRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DismissAllRaisedHandRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetParticipantsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetParticipantsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetParticipantsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetParticipantsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetPortalVersionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetPortalVersionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetPortalVersionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetPortalVersionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateScheduledRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateScheduledRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ScheduledRoomCreationFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ScheduledRoomCreationFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetWebcastURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetWebcastURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetWebcastURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetWebcastURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.EnableScheduledRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.EnableScheduledRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RoomNotFoundFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RoomNotFoundFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.AddMemberRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.AddMemberRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.AddMemberResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.AddMemberResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.MemberAlreadyExistsFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.MemberAlreadyExistsFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRecordingProfilesRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRecordingProfilesRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemovePresenterRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemovePresenterRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemovePresenterResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemovePresenterResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetTenantDetailsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetTenantDetailsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SearchMembersRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SearchMembersRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SearchMembersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SearchMembersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreatePublicRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreatePublicRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.PublicRoomCreationFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.PublicRoomCreationFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.NotAllowedToCreateFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.NotAllowedToCreateFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.AddClientVersionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.AddClientVersionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.AddClientVersionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.AddClientVersionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ExternalModeFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ExternalModeFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.TransferParticipantRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.TransferParticipantRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.TransferParticipantResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.TransferParticipantResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateRoomURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateRoomURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateRoomURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateRoomURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StopRecordingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StopRecordingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StopRecordingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StopRecordingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UnmuteAudioRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UnmuteAudioRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UnmuteAudioResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UnmuteAudioResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RoomIsEnabledRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RoomIsEnabledRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.AddGroupRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.AddGroupRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.AddGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.AddGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GroupAlreadyExistsFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GroupAlreadyExistsFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.NoEndpointBehaviorExistsFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.NoEndpointBehaviorExistsFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRoomsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRoomsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteGroupRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteGroupRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GroupNotFoundFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GroupNotFoundFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StartLectureModeRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StartLectureModeRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StartLectureModeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StartLectureModeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateWebcastURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateWebcastURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.InviteToConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.InviteToConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.InviteToConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.InviteToConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveModeratorPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveModeratorPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRoomProfileRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomProfileRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRoomProfileResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomProfileResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetLicenseDataRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetLicenseDataRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetLicenseDataResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetLicenseDataResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UpdateGroupRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateGroupRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UpdateGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetMemberRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetMemberRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetMemberResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetMemberResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.MemberNotFoundFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.MemberNotFoundFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StartRecordingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StartRecordingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StartRecordingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StartRecordingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ResourceNotAvailableFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ResourceNotAvailableFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetGroupRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetGroupRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.LeaveConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.LeaveConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.LeaveConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.LeaveConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StopLectureModeRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StopLectureModeRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StopLectureModeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StopLectureModeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetRoomProfileRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetRoomProfileRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetRoomProfileResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetRoomProfileResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.MuteAudioRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.MuteAudioRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.MuteAudioResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.MuteAudioResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetMembersRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetMembersRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetMembersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetMembersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StopVideoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StopVideoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StopVideoResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StopVideoResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteMemberRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteMemberRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteMemberResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteMemberResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateRoomPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateRoomPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateRoomPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateRoomPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetConferenceIDRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetConferenceIDRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetConferenceIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetConferenceIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.InPointToPointCallFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.InPointToPointCallFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.PauseRecordingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.PauseRecordingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.PauseRecordingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.PauseRecordingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveWebcastURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveWebcastURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UpdateMemberRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateMemberRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UpdateMemberResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateMemberResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateWebcastPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateWebcastPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteScheduledRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteScheduledRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetPresenterRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetPresenterRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetPresenterResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetPresenterResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.EnableRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.EnableRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.EnableRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.EnableRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetChatStateAdminRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetChatStateAdminRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ChatNotAvailableInSuperFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ChatNotAvailableInSuperFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.AddRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.AddRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.AddRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.AddRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RoomAlreadyExistsFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RoomAlreadyExistsFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateModeratorPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateModeratorPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SearchRoomsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SearchRoomsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SearchRoomsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SearchRoomsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CancelOutboundCallRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CancelOutboundCallRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetGroupsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetGroupsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetGroupsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetGroupsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UpdateRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.UpdateRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DisableScheduledRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DisableScheduledRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DismissRaisedHandRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DismissRaisedHandRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetLocationTagsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetLocationTagsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetLocationTagsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetLocationTagsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.EndpointBehaviorAlreadyExistsFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.EndpointBehaviorAlreadyExistsFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveWebcastPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveWebcastPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveRoomProfileRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveRoomProfileRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ResumeRecordingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ResumeRecordingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.ResumeRecordingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.ResumeRecordingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StartVideoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StartVideoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.StartVideoResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.StartVideoResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetEndpointBehaviorRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetEndpointBehaviorRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.DeleteRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRoomProfilesRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomProfilesRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetLayoutRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetLayoutRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.v1_1.SetLayoutResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.v1_1.SetLayoutResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse wrapremoveRoomURL(){
                                com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse wrappedElement = new com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse wrapdismissAllRaisedHand(){
                                com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse wrappedElement = new com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetParticipantsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetParticipantsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetParticipantsResponse wrapgetParticipants(){
                                com.vidyo.portal.admin.v1_1.GetParticipantsResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetParticipantsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetPortalVersionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetPortalVersionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetPortalVersionResponse wrapgetPortalVersion(){
                                com.vidyo.portal.admin.v1_1.GetPortalVersionResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetPortalVersionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse wrapcreateScheduledRoom(){
                                com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetWebcastURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetWebcastURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetWebcastURLResponse wrapgetWebcastURL(){
                                com.vidyo.portal.admin.v1_1.GetWebcastURLResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetWebcastURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse wrapgetLectureModeParticipants(){
                                com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse wrapenableScheduledRoom(){
                                com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.AddMemberResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.AddMemberResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.AddMemberResponse wrapaddMember(){
                                com.vidyo.portal.admin.v1_1.AddMemberResponse wrappedElement = new com.vidyo.portal.admin.v1_1.AddMemberResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse wrapgetRecordingProfiles(){
                                com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.RemovePresenterResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.RemovePresenterResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.RemovePresenterResponse wrapremovePresenter(){
                                com.vidyo.portal.admin.v1_1.RemovePresenterResponse wrappedElement = new com.vidyo.portal.admin.v1_1.RemovePresenterResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse wrapgetTenantDetails(){
                                com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.SearchMembersResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.SearchMembersResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.SearchMembersResponse wrapsearchMembers(){
                                com.vidyo.portal.admin.v1_1.SearchMembersResponse wrappedElement = new com.vidyo.portal.admin.v1_1.SearchMembersResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetRoomResponse wrapgetRoom(){
                                com.vidyo.portal.admin.v1_1.GetRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse wrapcreatePublicRoom(){
                                com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.AddClientVersionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.AddClientVersionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.AddClientVersionResponse wrapaddClientVersion(){
                                com.vidyo.portal.admin.v1_1.AddClientVersionResponse wrappedElement = new com.vidyo.portal.admin.v1_1.AddClientVersionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.TransferParticipantResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.TransferParticipantResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.TransferParticipantResponse wraptransferParticipant(){
                                com.vidyo.portal.admin.v1_1.TransferParticipantResponse wrappedElement = new com.vidyo.portal.admin.v1_1.TransferParticipantResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.CreateRoomURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.CreateRoomURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.CreateRoomURLResponse wrapcreateRoomURL(){
                                com.vidyo.portal.admin.v1_1.CreateRoomURLResponse wrappedElement = new com.vidyo.portal.admin.v1_1.CreateRoomURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.StopRecordingResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.StopRecordingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.StopRecordingResponse wrapstopRecording(){
                                com.vidyo.portal.admin.v1_1.StopRecordingResponse wrappedElement = new com.vidyo.portal.admin.v1_1.StopRecordingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.UnmuteAudioResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.UnmuteAudioResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.UnmuteAudioResponse wrapunmuteAudio(){
                                com.vidyo.portal.admin.v1_1.UnmuteAudioResponse wrappedElement = new com.vidyo.portal.admin.v1_1.UnmuteAudioResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse wraproomIsEnabled(){
                                com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse wrappedElement = new com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.AddGroupResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.AddGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.AddGroupResponse wrapaddGroup(){
                                com.vidyo.portal.admin.v1_1.AddGroupResponse wrappedElement = new com.vidyo.portal.admin.v1_1.AddGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse wrapdeleteEndpointBehavior(){
                                com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse wrappedElement = new com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetRoomsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetRoomsResponse wrapgetRooms(){
                                com.vidyo.portal.admin.v1_1.GetRoomsResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetRoomsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.DeleteGroupResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.DeleteGroupResponse wrapdeleteGroup(){
                                com.vidyo.portal.admin.v1_1.DeleteGroupResponse wrappedElement = new com.vidyo.portal.admin.v1_1.DeleteGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.StartLectureModeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.StartLectureModeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.StartLectureModeResponse wrapstartLectureMode(){
                                com.vidyo.portal.admin.v1_1.StartLectureModeResponse wrappedElement = new com.vidyo.portal.admin.v1_1.StartLectureModeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse wrapcreateWebcastURL(){
                                com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse wrappedElement = new com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.InviteToConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.InviteToConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.InviteToConferenceResponse wrapinviteToConference(){
                                com.vidyo.portal.admin.v1_1.InviteToConferenceResponse wrappedElement = new com.vidyo.portal.admin.v1_1.InviteToConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse wrapremoveModeratorPIN(){
                                com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse wrappedElement = new com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetRoomProfileResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomProfileResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetRoomProfileResponse wrapgetRoomProfile(){
                                com.vidyo.portal.admin.v1_1.GetRoomProfileResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetRoomProfileResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetLicenseDataResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetLicenseDataResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetLicenseDataResponse wrapgetLicenseData(){
                                com.vidyo.portal.admin.v1_1.GetLicenseDataResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetLicenseDataResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse wrapsetTenantRoomAttributes(){
                                com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse wrappedElement = new com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.UpdateGroupResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.UpdateGroupResponse wrapupdateGroup(){
                                com.vidyo.portal.admin.v1_1.UpdateGroupResponse wrappedElement = new com.vidyo.portal.admin.v1_1.UpdateGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse wrapisScheduledRoomEnabled(){
                                com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse wrappedElement = new com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetMemberResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetMemberResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetMemberResponse wrapgetMember(){
                                com.vidyo.portal.admin.v1_1.GetMemberResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetMemberResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.StartRecordingResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.StartRecordingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.StartRecordingResponse wrapstartRecording(){
                                com.vidyo.portal.admin.v1_1.StartRecordingResponse wrappedElement = new com.vidyo.portal.admin.v1_1.StartRecordingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetGroupResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetGroupResponse wrapgetGroup(){
                                com.vidyo.portal.admin.v1_1.GetGroupResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.LeaveConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.LeaveConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.LeaveConferenceResponse wrapleaveConference(){
                                com.vidyo.portal.admin.v1_1.LeaveConferenceResponse wrappedElement = new com.vidyo.portal.admin.v1_1.LeaveConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.StopLectureModeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.StopLectureModeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.StopLectureModeResponse wrapstopLectureMode(){
                                com.vidyo.portal.admin.v1_1.StopLectureModeResponse wrappedElement = new com.vidyo.portal.admin.v1_1.StopLectureModeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.SetRoomProfileResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.SetRoomProfileResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.SetRoomProfileResponse wrapsetRoomProfile(){
                                com.vidyo.portal.admin.v1_1.SetRoomProfileResponse wrappedElement = new com.vidyo.portal.admin.v1_1.SetRoomProfileResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.MuteAudioResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.MuteAudioResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.MuteAudioResponse wrapmuteAudio(){
                                com.vidyo.portal.admin.v1_1.MuteAudioResponse wrappedElement = new com.vidyo.portal.admin.v1_1.MuteAudioResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetMembersResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetMembersResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetMembersResponse wrapgetMembers(){
                                com.vidyo.portal.admin.v1_1.GetMembersResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetMembersResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.StopVideoResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.StopVideoResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.StopVideoResponse wrapstopVideo(){
                                com.vidyo.portal.admin.v1_1.StopVideoResponse wrappedElement = new com.vidyo.portal.admin.v1_1.StopVideoResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.DeleteMemberResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteMemberResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.DeleteMemberResponse wrapdeleteMember(){
                                com.vidyo.portal.admin.v1_1.DeleteMemberResponse wrappedElement = new com.vidyo.portal.admin.v1_1.DeleteMemberResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse wrapupdateEndpointBehavior(){
                                com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse wrappedElement = new com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.CreateRoomPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.CreateRoomPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.CreateRoomPINResponse wrapcreateRoomPIN(){
                                com.vidyo.portal.admin.v1_1.CreateRoomPINResponse wrappedElement = new com.vidyo.portal.admin.v1_1.CreateRoomPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetConferenceIDResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetConferenceIDResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetConferenceIDResponse wrapgetConferenceID(){
                                com.vidyo.portal.admin.v1_1.GetConferenceIDResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetConferenceIDResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.PauseRecordingResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.PauseRecordingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.PauseRecordingResponse wrappauseRecording(){
                                com.vidyo.portal.admin.v1_1.PauseRecordingResponse wrappedElement = new com.vidyo.portal.admin.v1_1.PauseRecordingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse wrapremoveWebcastURL(){
                                com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse wrappedElement = new com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.UpdateMemberResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateMemberResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.UpdateMemberResponse wrapupdateMember(){
                                com.vidyo.portal.admin.v1_1.UpdateMemberResponse wrappedElement = new com.vidyo.portal.admin.v1_1.UpdateMemberResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse wrapcreateWebcastPIN(){
                                com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse wrappedElement = new com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse wrapdeleteScheduledRoom(){
                                com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.SetPresenterResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.SetPresenterResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.SetPresenterResponse wrapsetPresenter(){
                                com.vidyo.portal.admin.v1_1.SetPresenterResponse wrappedElement = new com.vidyo.portal.admin.v1_1.SetPresenterResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.EnableRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.EnableRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.EnableRoomResponse wrapenableRoom(){
                                com.vidyo.portal.admin.v1_1.EnableRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.EnableRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse wrapsetChatStateAdmin(){
                                com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse wrappedElement = new com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse wrapremoveRoomPIN(){
                                com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse wrappedElement = new com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.AddRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.AddRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.AddRoomResponse wrapaddRoom(){
                                com.vidyo.portal.admin.v1_1.AddRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.AddRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse wrapcreateModeratorPIN(){
                                com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse wrappedElement = new com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.SearchRoomsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.SearchRoomsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.SearchRoomsResponse wrapsearchRooms(){
                                com.vidyo.portal.admin.v1_1.SearchRoomsResponse wrappedElement = new com.vidyo.portal.admin.v1_1.SearchRoomsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse wrapcancelOutboundCall(){
                                com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse wrappedElement = new com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetGroupsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetGroupsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetGroupsResponse wrapgetGroups(){
                                com.vidyo.portal.admin.v1_1.GetGroupsResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetGroupsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.UpdateRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.UpdateRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.UpdateRoomResponse wrapupdateRoom(){
                                com.vidyo.portal.admin.v1_1.UpdateRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.UpdateRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse wrapdisableScheduledRoom(){
                                com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse wrapdismissRaisedHand(){
                                com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse wrappedElement = new com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetLocationTagsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetLocationTagsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetLocationTagsResponse wrapgetLocationTags(){
                                com.vidyo.portal.admin.v1_1.GetLocationTagsResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetLocationTagsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse wrapcreateEndpointBehavior(){
                                com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse wrappedElement = new com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse wrapremoveWebcastPIN(){
                                com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse wrappedElement = new com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse wrapremoveRoomProfile(){
                                com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse wrappedElement = new com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse wrapscheduledRoomIsEnabled(){
                                com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse wrappedElement = new com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.ResumeRecordingResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.ResumeRecordingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.ResumeRecordingResponse wrapresumeRecording(){
                                com.vidyo.portal.admin.v1_1.ResumeRecordingResponse wrappedElement = new com.vidyo.portal.admin.v1_1.ResumeRecordingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.StartVideoResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.StartVideoResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.StartVideoResponse wrapstartVideo(){
                                com.vidyo.portal.admin.v1_1.StartVideoResponse wrappedElement = new com.vidyo.portal.admin.v1_1.StartVideoResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse wrapgetEndpointBehavior(){
                                com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.DeleteRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.DeleteRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.DeleteRoomResponse wrapdeleteRoom(){
                                com.vidyo.portal.admin.v1_1.DeleteRoomResponse wrappedElement = new com.vidyo.portal.admin.v1_1.DeleteRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse wrapgetRoomProfiles(){
                                com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse wrappedElement = new com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.v1_1.SetLayoutResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.v1_1.SetLayoutResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.v1_1.SetLayoutResponse wrapsetLayout(){
                                com.vidyo.portal.admin.v1_1.SetLayoutResponse wrappedElement = new com.vidyo.portal.admin.v1_1.SetLayoutResponse();
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
        
                if (com.vidyo.portal.admin.v1_1.RemoveRoomURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveRoomURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DismissAllRaisedHandRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DismissAllRaisedHandRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetParticipantsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetParticipantsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetParticipantsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetParticipantsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetPortalVersionRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetPortalVersionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetPortalVersionResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetPortalVersionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateScheduledRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateScheduledRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ScheduledRoomCreationFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ScheduledRoomCreationFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetWebcastURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetWebcastURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetWebcastURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetWebcastURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.EnableScheduledRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.EnableScheduledRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.AddMemberRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.AddMemberRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.AddMemberResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.AddMemberResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.MemberAlreadyExistsFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.MemberAlreadyExistsFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRecordingProfilesRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRecordingProfilesRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemovePresenterRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemovePresenterRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemovePresenterResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemovePresenterResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetTenantDetailsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetTenantDetailsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SearchMembersRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SearchMembersRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SearchMembersResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SearchMembersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreatePublicRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreatePublicRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.PublicRoomCreationFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.PublicRoomCreationFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotAllowedToCreateFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotAllowedToCreateFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.AddClientVersionRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.AddClientVersionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.AddClientVersionResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.AddClientVersionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ExternalModeFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ExternalModeFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.TransferParticipantRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.TransferParticipantRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.TransferParticipantResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.TransferParticipantResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateRoomURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateRoomURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateRoomURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateRoomURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StopRecordingRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StopRecordingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StopRecordingResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StopRecordingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UnmuteAudioRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UnmuteAudioRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UnmuteAudioResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UnmuteAudioResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomIsEnabledRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomIsEnabledRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.AddGroupRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.AddGroupRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.AddGroupResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.AddGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GroupAlreadyExistsFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GroupAlreadyExistsFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NoEndpointBehaviorExistsFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NoEndpointBehaviorExistsFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRoomsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRoomsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRoomsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRoomsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteGroupRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteGroupRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteGroupResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GroupNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GroupNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StartLectureModeRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StartLectureModeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StartLectureModeResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StartLectureModeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateWebcastURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateWebcastURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InviteToConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InviteToConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InviteToConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InviteToConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveModeratorPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveModeratorPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRoomProfileRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRoomProfileRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRoomProfileResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRoomProfileResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetLicenseDataRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetLicenseDataRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetLicenseDataResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetLicenseDataResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UpdateGroupRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UpdateGroupRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UpdateGroupResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UpdateGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GroupNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GroupNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetMemberRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetMemberRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetMemberResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetMemberResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.MemberNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.MemberNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StartRecordingRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StartRecordingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StartRecordingResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StartRecordingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetGroupRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetGroupRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetGroupResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GroupNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GroupNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.LeaveConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.LeaveConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.LeaveConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.LeaveConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StopLectureModeRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StopLectureModeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StopLectureModeResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StopLectureModeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetRoomProfileRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetRoomProfileRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetRoomProfileResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetRoomProfileResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.MuteAudioRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.MuteAudioRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.MuteAudioResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.MuteAudioResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetMembersRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetMembersRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetMembersResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetMembersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StopVideoRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StopVideoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StopVideoResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StopVideoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteMemberRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteMemberRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteMemberResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteMemberResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.MemberNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.MemberNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NoEndpointBehaviorExistsFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NoEndpointBehaviorExistsFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateRoomPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateRoomPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateRoomPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateRoomPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetConferenceIDRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetConferenceIDRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetConferenceIDResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetConferenceIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InPointToPointCallFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InPointToPointCallFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.PauseRecordingRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.PauseRecordingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.PauseRecordingResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.PauseRecordingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveWebcastURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveWebcastURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UpdateMemberRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UpdateMemberRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UpdateMemberResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UpdateMemberResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.MemberNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.MemberNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateWebcastPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateWebcastPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteScheduledRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteScheduledRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetPresenterRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetPresenterRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetPresenterResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetPresenterResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.EnableRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.EnableRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.EnableRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.EnableRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetChatStateAdminRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetChatStateAdminRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ChatNotAvailableInSuperFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ChatNotAvailableInSuperFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.AddRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.AddRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.AddRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.AddRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomAlreadyExistsFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomAlreadyExistsFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateModeratorPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateModeratorPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SearchRoomsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SearchRoomsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SearchRoomsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SearchRoomsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CancelOutboundCallRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CancelOutboundCallRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetGroupsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetGroupsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetGroupsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetGroupsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UpdateRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UpdateRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.UpdateRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.UpdateRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomAlreadyExistsFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomAlreadyExistsFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidModeratorPINFormatFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DisableScheduledRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DisableScheduledRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DismissRaisedHandRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DismissRaisedHandRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetLocationTagsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetLocationTagsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetLocationTagsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetLocationTagsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.EndpointBehaviorAlreadyExistsFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.EndpointBehaviorAlreadyExistsFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveWebcastPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveWebcastPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveRoomProfileRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveRoomProfileRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ResumeRecordingRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ResumeRecordingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.ResumeRecordingResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.ResumeRecordingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StartVideoRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StartVideoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.StartVideoResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.StartVideoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetEndpointBehaviorRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetEndpointBehaviorRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NoEndpointBehaviorExistsFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NoEndpointBehaviorExistsFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.EndpointBehaviorDisabledFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.DeleteRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.DeleteRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.RoomNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.RoomNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRoomProfilesRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRoomProfilesRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetLayoutRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetLayoutRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.SetLayoutResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.SetLayoutResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.v1_1.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.v1_1.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    