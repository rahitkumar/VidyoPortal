<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="sd">
		<row>
			<name><c:out value="${sd.name}"/></name>
			<roomID><c:out value="${sd.roomID}"/></roomID>
			<endpointID><c:out value="${sd.endpointID}"/></endpointID>
			<roomTypeID><c:out value="${sd.roomTypeID}"/></roomTypeID>
			<roomType><c:out value="${sd.roomType}"/></roomType>
			<roomLocked><c:out value="${sd.roomLocked}"/></roomLocked>
			<roomPinned><c:out value="${sd.roomPinned}"/></roomPinned>
			<roomName><c:out value="${sd.roomName}"/></roomName>
			<dialIn><c:out value="${sd.dialIn}"/></dialIn>
			<roomExtNumber><c:out value="${sd.roomExtNumber}"/></roomExtNumber>
			<memberStatus><c:out value="${sd.memberStatus}"/></memberStatus>

			<c:if test="${sd.roomType == 'Personal'}">
				<c:if test="${sd.roomPinned != 0}">
					<actionCallMeeting>icon-join-key</actionCallMeeting>
				</c:if>
				<c:if test="${sd.roomPinned == 0}">
					<actionCallMeeting>icon-join</actionCallMeeting>
				</c:if>
				<qtipCallMeeting><spring:message code="call.meeting.room"/></qtipCallMeeting>
			   	<c:if test="${sd.roomEnabled == 0}">
					<actionCallMeetingHide>true</actionCallMeetingHide>
				</c:if>

				<c:if test="${sd.memberStatus == '1'}">
					<actionCallDirect>icon-call</actionCallDirect>
					<qtipCallDirect><spring:message code="call.direct"/></qtipCallDirect>
				</c:if>
				<c:if test="${sd.memberStatus != '1'}">
					<actionCallDirectHide>true</actionCallDirectHide>
				</c:if>

			</c:if>
			<c:if test="${sd.roomType == 'Public'}">
				<c:if test="${sd.roomPinned != 0}">
					<actionCallMeeting>icon-join-key</actionCallMeeting>
				</c:if>
				<c:if test="${sd.roomPinned == 0}">
					<actionCallMeeting>icon-join</actionCallMeeting>
				</c:if>
				<qtipCallMeeting><spring:message code="call.meeting.room"/></qtipCallMeeting>
			   	<c:if test="${sd.roomEnabled == 0}">
					<actionCallMeetingHide>true</actionCallMeetingHide>
				</c:if>

				<actionCallDirectHide>true</actionCallDirectHide>
			</c:if>
			<c:if test="${sd.roomType == 'Legacy'}">
				<actionCallMeetingHide>true</actionCallMeetingHide>

				<actionCallDirect>icon-call</actionCallDirect>
				<qtipCallDirect><spring:message code="call.direct"/></qtipCallDirect>
			</c:if>
		</row>
	</c:forEach>
</dataset>