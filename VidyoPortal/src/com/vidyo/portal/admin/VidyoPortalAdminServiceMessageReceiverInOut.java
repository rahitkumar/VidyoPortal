
/**
 * VidyoPortalAdminServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
        package com.vidyo.portal.admin;

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


        

            if("getLicenseData".equals(methodName)){
                
                com.vidyo.portal.admin.GetLicenseDataResponse getLicenseDataResponse57 = null;
	                        com.vidyo.portal.admin.GetLicenseDataRequest wrappedParam =
                                                             (com.vidyo.portal.admin.GetLicenseDataRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.GetLicenseDataRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLicenseDataResponse57 =
                                                   
                                                   
                                                         skel.getLicenseData(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLicenseDataResponse57, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "getLicenseData"));
                                    } else 

            if("removeRoomPIN".equals(methodName)){
                
                com.vidyo.portal.admin.RemoveRoomPINResponse removeRoomPINResponse59 = null;
	                        com.vidyo.portal.admin.RemoveRoomPINRequest wrappedParam =
                                                             (com.vidyo.portal.admin.RemoveRoomPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.RemoveRoomPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomPINResponse59 =
                                                   
                                                   
                                                         skel.removeRoomPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomPINResponse59, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "removeRoomPIN"));
                                    } else 

            if("getRooms".equals(methodName)){
                
                com.vidyo.portal.admin.GetRoomsResponse getRoomsResponse61 = null;
	                        com.vidyo.portal.admin.GetRoomsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.GetRoomsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.GetRoomsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRoomsResponse61 =
                                                   
                                                   
                                                         skel.getRooms(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRoomsResponse61, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "getRooms"));
                                    } else 

            if("updateGroup".equals(methodName)){
                
                com.vidyo.portal.admin.UpdateGroupResponse updateGroupResponse63 = null;
	                        com.vidyo.portal.admin.UpdateGroupRequest wrappedParam =
                                                             (com.vidyo.portal.admin.UpdateGroupRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.UpdateGroupRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateGroupResponse63 =
                                                   
                                                   
                                                         skel.updateGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateGroupResponse63, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "updateGroup"));
                                    } else 

            if("getRoom".equals(methodName)){
                
                com.vidyo.portal.admin.GetRoomResponse getRoomResponse65 = null;
	                        com.vidyo.portal.admin.GetRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.GetRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.GetRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRoomResponse65 =
                                                   
                                                   
                                                         skel.getRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRoomResponse65, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "getRoom"));
                                    } else 

            if("deleteRoom".equals(methodName)){
                
                com.vidyo.portal.admin.DeleteRoomResponse deleteRoomResponse67 = null;
	                        com.vidyo.portal.admin.DeleteRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.DeleteRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.DeleteRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteRoomResponse67 =
                                                   
                                                   
                                                         skel.deleteRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteRoomResponse67, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "deleteRoom"));
                                    } else 

            if("muteAudio".equals(methodName)){
                
                com.vidyo.portal.admin.MuteAudioResponse muteAudioResponse69 = null;
	                        com.vidyo.portal.admin.MuteAudioRequest wrappedParam =
                                                             (com.vidyo.portal.admin.MuteAudioRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.MuteAudioRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               muteAudioResponse69 =
                                                   
                                                   
                                                         skel.muteAudio(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), muteAudioResponse69, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "muteAudio"));
                                    } else 

            if("getParticipants".equals(methodName)){
                
                com.vidyo.portal.admin.GetParticipantsResponse getParticipantsResponse71 = null;
	                        com.vidyo.portal.admin.GetParticipantsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.GetParticipantsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.GetParticipantsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getParticipantsResponse71 =
                                                   
                                                   
                                                         skel.getParticipants(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getParticipantsResponse71, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "getParticipants"));
                                    } else 

            if("unmuteAudio".equals(methodName)){
                
                com.vidyo.portal.admin.UnmuteAudioResponse unmuteAudioResponse73 = null;
	                        com.vidyo.portal.admin.UnmuteAudioRequest wrappedParam =
                                                             (com.vidyo.portal.admin.UnmuteAudioRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.UnmuteAudioRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unmuteAudioResponse73 =
                                                   
                                                   
                                                         skel.unmuteAudio(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unmuteAudioResponse73, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "unmuteAudio"));
                                    } else 

            if("getGroups".equals(methodName)){
                
                com.vidyo.portal.admin.GetGroupsResponse getGroupsResponse75 = null;
	                        com.vidyo.portal.admin.GetGroupsRequest wrappedParam =
                                                             (com.vidyo.portal.admin.GetGroupsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.GetGroupsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getGroupsResponse75 =
                                                   
                                                   
                                                         skel.getGroups(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getGroupsResponse75, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "getGroups"));
                                    } else 

            if("addMember".equals(methodName)){
                
                com.vidyo.portal.admin.AddMemberResponse addMemberResponse77 = null;
	                        com.vidyo.portal.admin.AddMemberRequest wrappedParam =
                                                             (com.vidyo.portal.admin.AddMemberRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.AddMemberRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addMemberResponse77 =
                                                   
                                                   
                                                         skel.addMember(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addMemberResponse77, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "addMember"));
                                    } else 

            if("startVideo".equals(methodName)){
                
                com.vidyo.portal.admin.StartVideoResponse startVideoResponse79 = null;
	                        com.vidyo.portal.admin.StartVideoRequest wrappedParam =
                                                             (com.vidyo.portal.admin.StartVideoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.StartVideoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startVideoResponse79 =
                                                   
                                                   
                                                         skel.startVideo(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startVideoResponse79, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "startVideo"));
                                    } else 

            if("createRoomPIN".equals(methodName)){
                
                com.vidyo.portal.admin.CreateRoomPINResponse createRoomPINResponse81 = null;
	                        com.vidyo.portal.admin.CreateRoomPINRequest wrappedParam =
                                                             (com.vidyo.portal.admin.CreateRoomPINRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.CreateRoomPINRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomPINResponse81 =
                                                   
                                                   
                                                         skel.createRoomPIN(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomPINResponse81, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "createRoomPIN"));
                                    } else 

            if("deleteGroup".equals(methodName)){
                
                com.vidyo.portal.admin.DeleteGroupResponse deleteGroupResponse83 = null;
	                        com.vidyo.portal.admin.DeleteGroupRequest wrappedParam =
                                                             (com.vidyo.portal.admin.DeleteGroupRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.DeleteGroupRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteGroupResponse83 =
                                                   
                                                   
                                                         skel.deleteGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteGroupResponse83, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "deleteGroup"));
                                    } else 

            if("removeRoomURL".equals(methodName)){
                
                com.vidyo.portal.admin.RemoveRoomURLResponse removeRoomURLResponse85 = null;
	                        com.vidyo.portal.admin.RemoveRoomURLRequest wrappedParam =
                                                             (com.vidyo.portal.admin.RemoveRoomURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.RemoveRoomURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               removeRoomURLResponse85 =
                                                   
                                                   
                                                         skel.removeRoomURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), removeRoomURLResponse85, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "removeRoomURL"));
                                    } else 

            if("addRoom".equals(methodName)){
                
                com.vidyo.portal.admin.AddRoomResponse addRoomResponse87 = null;
	                        com.vidyo.portal.admin.AddRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.AddRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.AddRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addRoomResponse87 =
                                                   
                                                   
                                                         skel.addRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addRoomResponse87, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "addRoom"));
                                    } else 

            if("updateMember".equals(methodName)){
                
                com.vidyo.portal.admin.UpdateMemberResponse updateMemberResponse89 = null;
	                        com.vidyo.portal.admin.UpdateMemberRequest wrappedParam =
                                                             (com.vidyo.portal.admin.UpdateMemberRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.UpdateMemberRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateMemberResponse89 =
                                                   
                                                   
                                                         skel.updateMember(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateMemberResponse89, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "updateMember"));
                                    } else 

            if("deleteMember".equals(methodName)){
                
                com.vidyo.portal.admin.DeleteMemberResponse deleteMemberResponse91 = null;
	                        com.vidyo.portal.admin.DeleteMemberRequest wrappedParam =
                                                             (com.vidyo.portal.admin.DeleteMemberRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.DeleteMemberRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteMemberResponse91 =
                                                   
                                                   
                                                         skel.deleteMember(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteMemberResponse91, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "deleteMember"));
                                    } else 

            if("inviteToConference".equals(methodName)){
                
                com.vidyo.portal.admin.InviteToConferenceResponse inviteToConferenceResponse93 = null;
	                        com.vidyo.portal.admin.InviteToConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.admin.InviteToConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.InviteToConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               inviteToConferenceResponse93 =
                                                   
                                                   
                                                         skel.inviteToConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), inviteToConferenceResponse93, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "inviteToConference"));
                                    } else 

            if("getMembers".equals(methodName)){
                
                com.vidyo.portal.admin.GetMembersResponse getMembersResponse95 = null;
	                        com.vidyo.portal.admin.GetMembersRequest wrappedParam =
                                                             (com.vidyo.portal.admin.GetMembersRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.GetMembersRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getMembersResponse95 =
                                                   
                                                   
                                                         skel.getMembers(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getMembersResponse95, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "getMembers"));
                                    } else 

            if("addGroup".equals(methodName)){
                
                com.vidyo.portal.admin.AddGroupResponse addGroupResponse97 = null;
	                        com.vidyo.portal.admin.AddGroupRequest wrappedParam =
                                                             (com.vidyo.portal.admin.AddGroupRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.AddGroupRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addGroupResponse97 =
                                                   
                                                   
                                                         skel.addGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addGroupResponse97, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "addGroup"));
                                    } else 

            if("getGroup".equals(methodName)){
                
                com.vidyo.portal.admin.GetGroupResponse getGroupResponse99 = null;
	                        com.vidyo.portal.admin.GetGroupRequest wrappedParam =
                                                             (com.vidyo.portal.admin.GetGroupRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.GetGroupRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getGroupResponse99 =
                                                   
                                                   
                                                         skel.getGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getGroupResponse99, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "getGroup"));
                                    } else 

            if("getPortalVersion".equals(methodName)){
                
                com.vidyo.portal.admin.GetPortalVersionResponse getPortalVersionResponse101 = null;
	                        com.vidyo.portal.admin.GetPortalVersionRequest wrappedParam =
                                                             (com.vidyo.portal.admin.GetPortalVersionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.GetPortalVersionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getPortalVersionResponse101 =
                                                   
                                                   
                                                         skel.getPortalVersion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getPortalVersionResponse101, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "getPortalVersion"));
                                    } else 

            if("updateRoom".equals(methodName)){
                
                com.vidyo.portal.admin.UpdateRoomResponse updateRoomResponse103 = null;
	                        com.vidyo.portal.admin.UpdateRoomRequest wrappedParam =
                                                             (com.vidyo.portal.admin.UpdateRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.UpdateRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateRoomResponse103 =
                                                   
                                                   
                                                         skel.updateRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateRoomResponse103, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "updateRoom"));
                                    } else 

            if("createRoomURL".equals(methodName)){
                
                com.vidyo.portal.admin.CreateRoomURLResponse createRoomURLResponse105 = null;
	                        com.vidyo.portal.admin.CreateRoomURLRequest wrappedParam =
                                                             (com.vidyo.portal.admin.CreateRoomURLRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.CreateRoomURLRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createRoomURLResponse105 =
                                                   
                                                   
                                                         skel.createRoomURL(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createRoomURLResponse105, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "createRoomURL"));
                                    } else 

            if("stopVideo".equals(methodName)){
                
                com.vidyo.portal.admin.StopVideoResponse stopVideoResponse107 = null;
	                        com.vidyo.portal.admin.StopVideoRequest wrappedParam =
                                                             (com.vidyo.portal.admin.StopVideoRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.StopVideoRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               stopVideoResponse107 =
                                                   
                                                   
                                                         skel.stopVideo(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), stopVideoResponse107, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "stopVideo"));
                                    } else 

            if("leaveConference".equals(methodName)){
                
                com.vidyo.portal.admin.LeaveConferenceResponse leaveConferenceResponse109 = null;
	                        com.vidyo.portal.admin.LeaveConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.admin.LeaveConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.LeaveConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               leaveConferenceResponse109 =
                                                   
                                                   
                                                         skel.leaveConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), leaveConferenceResponse109, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "leaveConference"));
                                    } else 

            if("getMember".equals(methodName)){
                
                com.vidyo.portal.admin.GetMemberResponse getMemberResponse111 = null;
	                        com.vidyo.portal.admin.GetMemberRequest wrappedParam =
                                                             (com.vidyo.portal.admin.GetMemberRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.admin.GetMemberRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getMemberResponse111 =
                                                   
                                                   
                                                         skel.getMember(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getMemberResponse111, false, new javax.xml.namespace.QName("http://portal.vidyo.com/admin",
                                                    "getMember"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (NotLicensedExceptionException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotLicensedException");
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
         catch (MemberAlreadyExistsExceptionException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"MemberAlreadyExistsException");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (RoomNotFoundExceptionException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"RoomNotFoundException");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (MemberNotFoundExceptionException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"MemberNotFoundException");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (GroupNotFoundExceptionException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"GroupNotFoundException");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (GroupAlreadyExistsExceptionException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"GroupAlreadyExistsException");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (RoomAlreadyExistsExceptionException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"RoomAlreadyExistsException");
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
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetLicenseDataRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetLicenseDataRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetLicenseDataResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetLicenseDataResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.NotLicensedExceptionE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.NotLicensedExceptionE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.RemoveRoomPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.RemoveRoomPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.RemoveRoomPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.RemoveRoomPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetRoomsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetRoomsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetRoomsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetRoomsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.UpdateGroupRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.UpdateGroupRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.UpdateGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.UpdateGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GroupNotFoundExceptionE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GroupNotFoundExceptionE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.RoomNotFoundExceptionE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.RoomNotFoundExceptionE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.DeleteRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.DeleteRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.DeleteRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.DeleteRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.MuteAudioRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.MuteAudioRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.MuteAudioResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.MuteAudioResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetParticipantsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetParticipantsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetParticipantsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetParticipantsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.UnmuteAudioRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.UnmuteAudioRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.UnmuteAudioResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.UnmuteAudioResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetGroupsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetGroupsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetGroupsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetGroupsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.AddMemberRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.AddMemberRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.AddMemberResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.AddMemberResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.MemberAlreadyExistsExceptionE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.MemberAlreadyExistsExceptionE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.StartVideoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.StartVideoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.StartVideoResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.StartVideoResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.CreateRoomPINRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.CreateRoomPINRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.CreateRoomPINResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.CreateRoomPINResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.DeleteGroupRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.DeleteGroupRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.DeleteGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.DeleteGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.RemoveRoomURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.RemoveRoomURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.RemoveRoomURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.RemoveRoomURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.AddRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.AddRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.AddRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.AddRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.RoomAlreadyExistsExceptionE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.RoomAlreadyExistsExceptionE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.UpdateMemberRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.UpdateMemberRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.UpdateMemberResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.UpdateMemberResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.MemberNotFoundExceptionE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.MemberNotFoundExceptionE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.DeleteMemberRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.DeleteMemberRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.DeleteMemberResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.DeleteMemberResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.InviteToConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.InviteToConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.InviteToConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.InviteToConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetMembersRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetMembersRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetMembersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetMembersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.AddGroupRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.AddGroupRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.AddGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.AddGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GroupAlreadyExistsExceptionE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GroupAlreadyExistsExceptionE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetGroupRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetGroupRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetPortalVersionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetPortalVersionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetPortalVersionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetPortalVersionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.UpdateRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.UpdateRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.UpdateRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.UpdateRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.CreateRoomURLRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.CreateRoomURLRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.CreateRoomURLResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.CreateRoomURLResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.StopVideoRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.StopVideoRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.StopVideoResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.StopVideoResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.LeaveConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.LeaveConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.LeaveConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.LeaveConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetMemberRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetMemberRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.admin.GetMemberResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.admin.GetMemberResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.GetLicenseDataResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.GetLicenseDataResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.GetLicenseDataResponse wrapgetLicenseData(){
                                com.vidyo.portal.admin.GetLicenseDataResponse wrappedElement = new com.vidyo.portal.admin.GetLicenseDataResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.RemoveRoomPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.RemoveRoomPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.RemoveRoomPINResponse wrapremoveRoomPIN(){
                                com.vidyo.portal.admin.RemoveRoomPINResponse wrappedElement = new com.vidyo.portal.admin.RemoveRoomPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.GetRoomsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.GetRoomsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.GetRoomsResponse wrapgetRooms(){
                                com.vidyo.portal.admin.GetRoomsResponse wrappedElement = new com.vidyo.portal.admin.GetRoomsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.UpdateGroupResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.UpdateGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.UpdateGroupResponse wrapupdateGroup(){
                                com.vidyo.portal.admin.UpdateGroupResponse wrappedElement = new com.vidyo.portal.admin.UpdateGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.GetRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.GetRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.GetRoomResponse wrapgetRoom(){
                                com.vidyo.portal.admin.GetRoomResponse wrappedElement = new com.vidyo.portal.admin.GetRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.DeleteRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.DeleteRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.DeleteRoomResponse wrapdeleteRoom(){
                                com.vidyo.portal.admin.DeleteRoomResponse wrappedElement = new com.vidyo.portal.admin.DeleteRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.MuteAudioResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.MuteAudioResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.MuteAudioResponse wrapmuteAudio(){
                                com.vidyo.portal.admin.MuteAudioResponse wrappedElement = new com.vidyo.portal.admin.MuteAudioResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.GetParticipantsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.GetParticipantsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.GetParticipantsResponse wrapgetParticipants(){
                                com.vidyo.portal.admin.GetParticipantsResponse wrappedElement = new com.vidyo.portal.admin.GetParticipantsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.UnmuteAudioResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.UnmuteAudioResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.UnmuteAudioResponse wrapunmuteAudio(){
                                com.vidyo.portal.admin.UnmuteAudioResponse wrappedElement = new com.vidyo.portal.admin.UnmuteAudioResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.GetGroupsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.GetGroupsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.GetGroupsResponse wrapgetGroups(){
                                com.vidyo.portal.admin.GetGroupsResponse wrappedElement = new com.vidyo.portal.admin.GetGroupsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.AddMemberResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.AddMemberResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.AddMemberResponse wrapaddMember(){
                                com.vidyo.portal.admin.AddMemberResponse wrappedElement = new com.vidyo.portal.admin.AddMemberResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.StartVideoResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.StartVideoResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.StartVideoResponse wrapstartVideo(){
                                com.vidyo.portal.admin.StartVideoResponse wrappedElement = new com.vidyo.portal.admin.StartVideoResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.CreateRoomPINResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.CreateRoomPINResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.CreateRoomPINResponse wrapcreateRoomPIN(){
                                com.vidyo.portal.admin.CreateRoomPINResponse wrappedElement = new com.vidyo.portal.admin.CreateRoomPINResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.DeleteGroupResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.DeleteGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.DeleteGroupResponse wrapdeleteGroup(){
                                com.vidyo.portal.admin.DeleteGroupResponse wrappedElement = new com.vidyo.portal.admin.DeleteGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.RemoveRoomURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.RemoveRoomURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.RemoveRoomURLResponse wrapremoveRoomURL(){
                                com.vidyo.portal.admin.RemoveRoomURLResponse wrappedElement = new com.vidyo.portal.admin.RemoveRoomURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.AddRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.AddRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.AddRoomResponse wrapaddRoom(){
                                com.vidyo.portal.admin.AddRoomResponse wrappedElement = new com.vidyo.portal.admin.AddRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.UpdateMemberResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.UpdateMemberResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.UpdateMemberResponse wrapupdateMember(){
                                com.vidyo.portal.admin.UpdateMemberResponse wrappedElement = new com.vidyo.portal.admin.UpdateMemberResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.DeleteMemberResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.DeleteMemberResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.DeleteMemberResponse wrapdeleteMember(){
                                com.vidyo.portal.admin.DeleteMemberResponse wrappedElement = new com.vidyo.portal.admin.DeleteMemberResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.InviteToConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.InviteToConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.InviteToConferenceResponse wrapinviteToConference(){
                                com.vidyo.portal.admin.InviteToConferenceResponse wrappedElement = new com.vidyo.portal.admin.InviteToConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.GetMembersResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.GetMembersResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.GetMembersResponse wrapgetMembers(){
                                com.vidyo.portal.admin.GetMembersResponse wrappedElement = new com.vidyo.portal.admin.GetMembersResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.AddGroupResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.AddGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.AddGroupResponse wrapaddGroup(){
                                com.vidyo.portal.admin.AddGroupResponse wrappedElement = new com.vidyo.portal.admin.AddGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.GetGroupResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.GetGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.GetGroupResponse wrapgetGroup(){
                                com.vidyo.portal.admin.GetGroupResponse wrappedElement = new com.vidyo.portal.admin.GetGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.GetPortalVersionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.GetPortalVersionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.GetPortalVersionResponse wrapgetPortalVersion(){
                                com.vidyo.portal.admin.GetPortalVersionResponse wrappedElement = new com.vidyo.portal.admin.GetPortalVersionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.UpdateRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.UpdateRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.UpdateRoomResponse wrapupdateRoom(){
                                com.vidyo.portal.admin.UpdateRoomResponse wrappedElement = new com.vidyo.portal.admin.UpdateRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.CreateRoomURLResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.CreateRoomURLResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.CreateRoomURLResponse wrapcreateRoomURL(){
                                com.vidyo.portal.admin.CreateRoomURLResponse wrappedElement = new com.vidyo.portal.admin.CreateRoomURLResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.StopVideoResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.StopVideoResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.StopVideoResponse wrapstopVideo(){
                                com.vidyo.portal.admin.StopVideoResponse wrappedElement = new com.vidyo.portal.admin.StopVideoResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.LeaveConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.LeaveConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.LeaveConferenceResponse wrapleaveConference(){
                                com.vidyo.portal.admin.LeaveConferenceResponse wrappedElement = new com.vidyo.portal.admin.LeaveConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.admin.GetMemberResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.admin.GetMemberResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.admin.GetMemberResponse wrapgetMember(){
                                com.vidyo.portal.admin.GetMemberResponse wrappedElement = new com.vidyo.portal.admin.GetMemberResponse();
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
        
                if (com.vidyo.portal.admin.GetLicenseDataRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetLicenseDataRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetLicenseDataResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetLicenseDataResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.RemoveRoomPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.RemoveRoomPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.RemoveRoomPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.RemoveRoomPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetRoomsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetRoomsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetRoomsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetRoomsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.UpdateGroupRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.UpdateGroupRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.UpdateGroupResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.UpdateGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GroupNotFoundExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.GroupNotFoundExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.RoomNotFoundExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.RoomNotFoundExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.DeleteRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.DeleteRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.DeleteRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.DeleteRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.RoomNotFoundExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.RoomNotFoundExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.MuteAudioRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.MuteAudioRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.MuteAudioResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.MuteAudioResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetParticipantsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetParticipantsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetParticipantsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetParticipantsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.UnmuteAudioRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.UnmuteAudioRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.UnmuteAudioResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.UnmuteAudioResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetGroupsRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetGroupsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetGroupsResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetGroupsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.AddMemberRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.AddMemberRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.AddMemberResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.AddMemberResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.MemberAlreadyExistsExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.MemberAlreadyExistsExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.StartVideoRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.StartVideoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.StartVideoResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.StartVideoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.CreateRoomPINRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.CreateRoomPINRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.CreateRoomPINResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.CreateRoomPINResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.DeleteGroupRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.DeleteGroupRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.DeleteGroupResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.DeleteGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GroupNotFoundExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.GroupNotFoundExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.RemoveRoomURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.RemoveRoomURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.RemoveRoomURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.RemoveRoomURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.AddRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.AddRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.AddRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.AddRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.RoomAlreadyExistsExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.RoomAlreadyExistsExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.UpdateMemberRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.UpdateMemberRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.UpdateMemberResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.UpdateMemberResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.MemberNotFoundExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.MemberNotFoundExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.DeleteMemberRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.DeleteMemberRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.DeleteMemberResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.DeleteMemberResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.MemberNotFoundExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.MemberNotFoundExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InviteToConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.InviteToConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InviteToConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.InviteToConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetMembersRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetMembersRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetMembersResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetMembersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.AddGroupRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.AddGroupRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.AddGroupResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.AddGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GroupAlreadyExistsExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.GroupAlreadyExistsExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetGroupRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetGroupRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetGroupResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GroupNotFoundExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.GroupNotFoundExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetPortalVersionRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetPortalVersionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetPortalVersionResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetPortalVersionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.UpdateRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.UpdateRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.UpdateRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.UpdateRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.RoomNotFoundExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.RoomNotFoundExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.RoomAlreadyExistsExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.RoomAlreadyExistsExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.CreateRoomURLRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.CreateRoomURLRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.CreateRoomURLResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.CreateRoomURLResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.StopVideoRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.StopVideoRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.StopVideoResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.StopVideoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.LeaveConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.LeaveConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.LeaveConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.LeaveConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetMemberRequest.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetMemberRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GetMemberResponse.class.equals(type)){
                
                           return com.vidyo.portal.admin.GetMemberResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.NotLicensedExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.NotLicensedExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.admin.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.admin.MemberNotFoundExceptionE.class.equals(type)){
                
                           return com.vidyo.portal.admin.MemberNotFoundExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    