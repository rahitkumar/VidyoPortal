<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<response portalVersion="<c:out value="${model.portalVersion}"/>">
	<systemLanguage><c:out value="${model.systemLanguage}"/></systemLanguage>
	<customLogo><c:out value="${model.customLogo}"/></customLogo>
    <myRoom>
    <c:if test="${not empty model.control}">
    	<conferenceEntityId><c:out value="${model.control.roomID}"/></conferenceEntityId>
    	<conferenceType><c:out value="${model.control.conferenceType}"/></conferenceType>    	
    </c:if>   
    
        <entityID><c:out value="${model.myroom.roomID}"/></entityID>
        <tenantID><c:out value="${model.myroom.tenantID}"/></tenantID>
        <tenantName><c:out value="${model.myroom.tenantName}"/></tenantName>
        <displayName><c:out value="${model.myroom.name}"/></displayName>
        <dialIn><c:out value="${model.myroom.dialIn}"/></dialIn>
        <extension><c:out value="${model.myroom.ext}"/></extension>

        <c:if test="${model.myroom.roomType == 'Personal'}">
            <name><c:out value="${model.myroom.username}"/></name>
            <entityType>Member</entityType>
            <langCode><c:out value="${model.myroom.langCode}"/></langCode>
            <langName><c:out value="${model.myroom.langName}"/></langName>
        </c:if>
        <c:if test="${model.myroom.roomType == 'Public'}">
            <name/>
            <entityType>Room</entityType>
            <langCode/>
        </c:if>
        <c:if test="${model.myroom.roomType == 'Legacy'}">
            <name/>
            <entityType>Legacy</entityType>
            <langCode/>
        </c:if>

        <c:if test="${model.myroom.roomType == 'Legacy'}">
            <memberStatus/>
            <canJoinMeeting>false</canJoinMeeting>
            <canCallDirect>true</canCallDirect>
        </c:if>
        <c:if test="${model.myroom.roomType == 'Public'}">
            <memberStatus/>
            <c:if test="${model.myroom.roomEnabled != '0'}">
                <canJoinMeeting>true</canJoinMeeting>
            </c:if>
            <c:if test="${model.myroom.roomEnabled == '0'}">
                <canJoinMeeting>false</canJoinMeeting>
            </c:if>
            <canCallDirect>false</canCallDirect>
        </c:if>
        <c:if test="${model.myroom.roomType != 'Legacy' && model.myroom.roomType != 'Public'}">
            <c:if test="${model.myroom.memberStatus == '0'}">
                <memberStatus>Offline</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '1'}">
                <memberStatus>Online</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '2'}">
                <memberStatus>Busy</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '3'}">
                <memberStatus>Ringing</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '4'}">
                <memberStatus>RingAccepted</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '5'}">
                <memberStatus>RingRejected</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '6'}">
                <memberStatus>RingNoAnswer</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '7'}">
                <memberStatus>Alerting</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '8'}">
                <memberStatus>AlertCancelled</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '9'}">
                <memberStatus>BusyInOwnRoom</memberStatus>
            </c:if>
            <c:if test="${model.myroom.memberStatus == '12'}">
                <memberStatus>WaitJoinConfirm</memberStatus>
            </c:if>

            <c:if test="${model.myroom.roomEnabled != '0'}">
                <canJoinMeeting>true</canJoinMeeting>
            </c:if>
            <c:if test="${model.myroom.roomEnabled == '0'}">
                <canJoinMeeting>false</canJoinMeeting>
            </c:if>
            <canCallDirect>true</canCallDirect>
        </c:if>

        <c:if test="${model.myroom.roomType != 'Legacy'}">
            <c:if test="${model.myroom.roomStatus == '0'}">
                <roomStatus>Empty</roomStatus>
            </c:if>
            <c:if test="${model.myroom.roomStatus == '1'}">
                <roomStatus>Occupied</roomStatus>
            </c:if>
            <c:if test="${model.myroom.roomStatus == '2'}">
                <roomStatus>Full</roomStatus>
            </c:if>
        </c:if>

        <roomMode>
            <c:if test="${not empty model.myroom.roomKey}">
                 <roomURL><c:out value="${model.roomURLFormated}"/></roomURL>
            </c:if>
            <c:if test="${empty model.myroom.roomKey}">
                <roomURL/>
            </c:if>

            <c:if test="${model.myroom.roomPinned != '0'}">
                <hasPin>true</hasPin>
            </c:if>
            <c:if test="${model.myroom.roomPinned == '0'}">
                <hasPin>false</hasPin>
            </c:if>
            <c:if test="${model.myroom.roomLocked != '0'}">
                <isLocked>true</isLocked>
            </c:if>
            <c:if test="${model.myroom.roomLocked == '0'}">
                <isLocked>false</isLocked>
            </c:if>

            <maxUsers><c:out value="${model.myroom.roomMaxUsers}"/></maxUsers>
            <up><c:out value="${model.myroom.userMaxBandWidthOut}"/></up>
            <down><c:out value="${model.myroom.userMaxBandWidthIn}"/></down>

            <c:if test="${not empty model.myroom.webCastURL}">
                <webCastUrl><c:out value="${model.myroom.webCastURL}"/></webCastUrl>
            </c:if>
            <c:if test="${model.myroom.webCastPinned != '0'}">
                <hasWebcastPin>true</hasWebcastPin>
            </c:if>
            <c:if test="${model.myroom.webCastPinned == '0'}">
                <hasWebcastPin>false</hasWebcastPin>
            </c:if>

            <c:if test="${model.myroom.roomModeratorPinned != '0'}">
                <hasModeratorPin>true</hasModeratorPin>
            </c:if>
            <c:if test="${model.myroom.roomModeratorPinned == '0'}">
                <hasModeratorPin>false</hasModeratorPin>
            </c:if>

        </roomMode>

        <c:if test="${model.myroom.roomOwner != '0'}">
            <canControlMeeting>true</canControlMeeting>
        </c:if>
        <c:if test="${model.myroom.roomOwner == '0'}">
            <canControlMeeting>false</canControlMeeting>
        </c:if>

        <c:if test="${model.myroom.allowRecording != '0'}">
            <canRecordMeeting>true</canRecordMeeting>
        </c:if>
        <c:if test="${model.myroom.allowRecording == '0'}">
            <canRecordMeeting>false</canRecordMeeting>
        </c:if>

        <c:if test="${model.myroom.speedDialID != '0'}">
            <isInMyContacts>true</isInMyContacts>
        </c:if>
        <c:if test="${model.myroom.speedDialID == '0'}">
            <isInMyContacts>false</isInMyContacts>
        </c:if>

        <EID><c:out value="${model.myroom.endpointGUID}"/></EID>
    </myRoom>

    <entities total="<c:out value="${model.num}"/>">
        <c:forEach items="${model.list}" var="entity">
            <entity>
                <entityID><c:out value="${entity.roomID}"/></entityID>
                <tenantID><c:out value="${entity.tenantID}"/></tenantID>
                <tenantName><c:out value="${entity.tenantName}"/></tenantName>
                <displayName><c:out value="${entity.name}"/></displayName>
                <dialIn><c:out value="${entity.dialIn}"/></dialIn>
                <extension><c:out value="${entity.ext}"/></extension>

                <c:if test="${entity.roomType == 'Personal'}">
                    <name><c:out value="${entity.username}"/></name>
                    <entityType>Member</entityType>
                </c:if>
                <c:if test="${entity.roomType == 'Public'}">
                    <name/>
                    <entityType>Room</entityType>
                </c:if>
                <c:if test="${entity.roomType == 'Legacy'}">
                    <name/>
                    <entityType>Legacy</entityType>
                </c:if>

                <c:if test="${entity.roomType == 'Legacy'}">
                    <memberStatus/>
                    <canJoinMeeting>false</canJoinMeeting>
                    <canCallDirect>true</canCallDirect>
                </c:if>
                <c:if test="${entity.roomType == 'Public'}">
                    <memberStatus/>
                    <c:if test="${entity.roomEnabled != '0'}">
                        <canJoinMeeting>true</canJoinMeeting>
                    </c:if>
                    <c:if test="${entity.roomEnabled == '0'}">
                        <canJoinMeeting>false</canJoinMeeting>
                    </c:if>
                    <canCallDirect>false</canCallDirect>
                </c:if>
                <c:if test="${entity.roomType != 'Legacy' && entity.roomType != 'Public'}">
                    <c:if test="${entity.memberStatus == '0'}">
                        <memberStatus>Offline</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '1'}">
                        <memberStatus>Online</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '2'}">
                        <memberStatus>Busy</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '3'}">
                        <memberStatus>Ringing</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '4'}">
                        <memberStatus>RingAccepted</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '5'}">
                        <memberStatus>RingRejected</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '6'}">
                        <memberStatus>RingNoAnswer</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '7'}">
                        <memberStatus>Alerting</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '8'}">
                        <memberStatus>AlertCancelled</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '9'}">
                        <memberStatus>BusyInOwnRoom</memberStatus>
                    </c:if>
                    <c:if test="${entity.memberStatus == '12'}">
                        <memberStatus>WaitJoinConfirm</memberStatus>
                    </c:if>

                    <c:if test="${entity.roomEnabled != '0'}">
                        <canJoinMeeting>true</canJoinMeeting>
                    </c:if>
                    <c:if test="${entity.roomEnabled == '0'}">
                        <canJoinMeeting>false</canJoinMeeting>
                    </c:if>
                    <canCallDirect>true</canCallDirect>
                </c:if>

                <c:if test="${entity.roomType != 'Legacy'}">
                    <c:if test="${entity.roomStatus == '0'}">
                        <roomStatus>Empty</roomStatus>
                    </c:if>
                    <c:if test="${entity.roomStatus == '1'}">
                        <roomStatus>Occupied</roomStatus>
                    </c:if>
                    <c:if test="${entity.roomStatus == '2'}">
                        <roomStatus>Full</roomStatus>
                    </c:if>
                </c:if>

                <roomMode>
                    <c:if test="${not empty entity.roomKey}">
                        <roomURL><c:out value="${model.roomURLFormated}"/></roomURL>
                    </c:if>
                    <c:if test="${empty entity.roomKey}">
                        <roomURL/>
                    </c:if>

                    <c:if test="${entity.roomPinned != '0'}">
                        <hasPin>true</hasPin>
                    </c:if>
                    <c:if test="${entity.roomPinned == '0'}">
                        <hasPin>false</hasPin>
                    </c:if>
                    <c:if test="${entity.roomLocked != '0'}">
                        <isLocked>true</isLocked>
                    </c:if>
                    <c:if test="${entity.roomLocked == '0'}">
                        <isLocked>false</isLocked>
                    </c:if>

                    <maxUsers><c:out value="${entity.roomMaxUsers}"/></maxUsers>
                    <up><c:out value="${entity.userMaxBandWidthOut}"/></up>
                    <down><c:out value="${entity.userMaxBandWidthIn}"/></down>

                    <c:if test="${not empty entity.webCastURL}">
                        <webCastUrl><c:out value="${entity.webCastURL}"/></webCastUrl>
                    </c:if>
                    <c:if test="${entity.webCastPinned != '0'}">
                        <hasWebcastPin>true</hasWebcastPin>
                    </c:if>
                    <c:if test="${entity.webCastPinned == '0'}">
                        <hasWebcastPin>false</hasWebcastPin>
                    </c:if>

                    <c:if test="${entity.roomModeratorPinned != '0'}">
                        <hasModeratorPin>true</hasModeratorPin>
                    </c:if>
                    <c:if test="${entity.roomModeratorPinned == '0'}">
                        <hasModeratorPin>false</hasModeratorPin>
                    </c:if>

                </roomMode>

                <c:if test="${entity.roomOwner != '0'}">
                    <canControlMeeting>true</canControlMeeting>
                </c:if>
                <c:if test="${entity.roomOwner == '0'}">
                    <canControlMeeting>false</canControlMeeting>
                </c:if>

                <c:if test="${entity.allowRecording != '0'}">
                    <canRecordMeeting>true</canRecordMeeting>
                </c:if>
                <c:if test="${entity.allowRecording == '0'}">
                    <canRecordMeeting>false</canRecordMeeting>
                </c:if>

                <c:if test="${entity.speedDialID != '0'}">
                    <isInMyContacts>true</isInMyContacts>
                </c:if>
                <c:if test="${entity.speedDialID == '0'}">
                    <isInMyContacts>false</isInMyContacts>
                </c:if>

            </entity>
        </c:forEach>
    </entities>
</response>