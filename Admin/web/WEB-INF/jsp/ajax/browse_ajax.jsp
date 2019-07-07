<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
<c:forEach items="${model.list}" var="br">
	<row>
		<roomID><c:out value="${br.roomID}"/></roomID>
		<endpointID><c:out value="${br.endpointID}"/></endpointID>
		<memberID><c:out value="${br.memberID}"/></memberID>
		<name><c:out value="${br.name}"/>@<c:out value="${br.tenantName}"/></name>
		<dialIn><c:out value="${br.dialIn}"/></dialIn>
		<ext><c:out value="${br.ext}"/></ext>
		<roomLocked><c:out value="${br.roomLocked}"/></roomLocked>
		<roomPinned><c:out value="${br.roomPinned}"/></roomPinned>
		<roomEnabled><c:out value="${br.roomEnabled}"/></roomEnabled>
		<memberStatus><c:out value="${br.memberStatus}"/></memberStatus>
	<c:if test="${br.roomType == 'Legacy'}">
		<memberStatusText/>
	</c:if>
	<c:if test="${br.roomType == 'Public'}">
		<memberStatusText/>
	</c:if>
	<c:if test="${br.roomType != 'Legacy' && br.roomType != 'Public'}">
		<c:if test="${br.memberStatus == '0'}">
			<memberStatusText>Offline</memberStatusText>
		</c:if>
		<c:if test="${br.memberStatus == '1'}">
			<memberStatusText>Online</memberStatusText>
		</c:if>
		<c:if test="${br.memberStatus == '2'}">
			<memberStatusText>Busy</memberStatusText>
		</c:if>
		<c:if test="${br.memberStatus == '3'}">
			<memberStatusText>Ringing</memberStatusText>
		</c:if>
		<c:if test="${br.memberStatus == '4'}">
			<memberStatusText>Ring Accepted</memberStatusText>
		</c:if>
		<c:if test="${br.memberStatus == '5'}">
			<memberStatusText>Ring Rejected</memberStatusText>
		</c:if>
		<c:if test="${br.memberStatus == '6'}">
			<memberStatusText>Ring No Answer</memberStatusText>
		</c:if>
		<c:if test="${br.memberStatus == '7'}">
			<memberStatusText>Alerting</memberStatusText>
		</c:if>
		<c:if test="${br.memberStatus == '8'}">
			<memberStatusText>Alert Cancelled</memberStatusText>
		</c:if>
		<c:if test="${br.memberStatus == '9'}">
			<memberStatusText>Busy In Own Room</memberStatusText>
		</c:if>
	</c:if>
		<speedDialID><c:out value="${br.speedDialID}"/></speedDialID>

		<roomTypeID><c:out value="${br.roomTypeID}"/></roomTypeID>
		<roomType><c:out value="${br.roomType}"/></roomType>

	<c:if test="${br.roomType == 'Legacy'}">
		<meetingStatusHide>true</meetingStatusHide>
	</c:if>
	<c:if test="${br.roomType != 'Legacy'}">
		<meetingStatus>icon-empty</meetingStatus>
		<qtipMeetingStatus>Empty</qtipMeetingStatus>
	</c:if>

	<c:if test="${br.roomLocked != 0}">
		<meetingLocked>icon-lock</meetingLocked>
		<qtipMeetingLocked>Locked</qtipMeetingLocked>
		<meetingLockedHide>false</meetingLockedHide>
	</c:if>
	<c:if test="${br.roomLocked == 0}">
		<meetingLockedHide>true</meetingLockedHide>
	</c:if>

	<c:if test="${br.roomPinned != 0}">
		<meetingPinned>icon-pin</meetingPinned>
		<qtipMeetingPinned>Pin</qtipMeetingPinned>
		<meetingPinnedHide>false</meetingPinnedHide>
	</c:if>
	<c:if test="${br.roomPinned == 0}">
		<meetingPinnedHide>true</meetingPinnedHide>
	</c:if>

	<c:if test="${br.roomType == 'Personal'}">
		<c:if test="${br.roomPinned != 0}">
			<actionCallMeeting>icon-join-key</actionCallMeeting>
		</c:if>
		<c:if test="${br.roomPinned == 0}">
			<actionCallMeeting>icon-join</actionCallMeeting>
		</c:if>
		<qtipCallMeeting>Call Meeting Room</qtipCallMeeting>
		<c:if test="${br.roomEnabled == 0}">
			<actionCallMeetingHide>true</actionCallMeetingHide>
		</c:if>

		<c:if test="${br.memberStatus == '1'}">
			<actionCallDirect>icon-call</actionCallDirect>
			<qtipCallDirect>Call Direct</qtipCallDirect>
		</c:if>
		<c:if test="${br.memberStatus != '1'}">
			<actionCallDirectHide>true</actionCallDirectHide>
		</c:if>
	</c:if>
	<c:if test="${br.roomType == 'Public'}">
		<c:if test="${br.roomPinned != 0}">
			<actionCallMeeting>icon-join-key</actionCallMeeting>
		</c:if>
		<c:if test="${br.roomPinned == 0}">
			<actionCallMeeting>icon-join</actionCallMeeting>
		</c:if>
		<qtipCallMeeting>Call Meeting Room</qtipCallMeeting>
		<c:if test="${br.roomEnabled == 0}">
			<actionCallMeetingHide>true</actionCallMeetingHide>
		</c:if>

		<actionCallDirectHide>true</actionCallDirectHide>
	</c:if>
	<c:if test="${br.roomType == 'Legacy'}">
		<actionCallMeetingHide>true</actionCallMeetingHide>

		<actionCallDirect>icon-call</actionCallDirect>
		<qtipCallDirect>Call Direct</qtipCallDirect>
	</c:if>

	<c:if test="${br.speedDialID == 0}">
		<actionSpeedDial>icon-add</actionSpeedDial>
		<qtipSpeedDial>Add to Speed Dial list</qtipSpeedDial>
	</c:if>

	<c:if test="${br.speedDialID != 0}">
		<actionSpeedDial>icon-remove</actionSpeedDial>
		<qtipSpeedDial>Remove from Speed Dial list</qtipSpeedDial>
	</c:if>

	<c:if test="${br.roomOwner != 0}">
		<actionInfoHide>false</actionInfoHide>

		<actionInfo>icon-info</actionInfo>
		<qtipInfo>Your are owner of this room. Click to get additional information.</qtipInfo>
	</c:if>
	<c:if test="${br.roomOwner == 0}">
		<actionInfoHide>true</actionInfoHide>
	</c:if>

		<tenantID><c:out value="${br.tenantID}"/></tenantID>
		<tenantName><c:out value="${br.tenantName}"/></tenantName>
	</row>
</c:forEach>
</dataset>