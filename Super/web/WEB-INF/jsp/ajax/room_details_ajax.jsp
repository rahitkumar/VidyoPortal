<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<roomID><c:out value="${model.room.roomID}"/></roomID>
		<roomTypeID><c:out value="${model.room.roomTypeID}"/></roomTypeID>
		<memberID><c:out value="${model.room.memberID}"/></memberID>
		<groupID><c:out value="${model.room.groupID}"/></groupID>
		<roomName><c:out value="${model.room.roomName}"/></roomName>
		<dialIn><c:out value="${model.room.dialIn}"/></dialIn>
		<roomExtNumber><c:out value="${model.room.roomExtNumber}"/></roomExtNumber>
		<roomDescription><c:out value="${model.room.roomDescription}"/></roomDescription>
		<roomType><c:out value="${model.room.roomType}"/></roomType>
	<enabled><c:out value="${model.room.roomEnabled}"/></enabled>
		<roomEnabled><c:out value="${model.room.roomEnabled}"/></roomEnabled>
	<locked><c:out value="${model.room.roomLocked}"/></locked>
		<roomLocked><c:out value="${model.room.roomLocked}"/></roomLocked>
		<roomPIN><c:out value="${model.room.roomPIN}"/></roomPIN>
		<roomKey><c:out value="${model.room.roomKey}"/></roomKey>
		<roomURL><c:out value="${model.room.roomURL}"/></roomURL>
		<tenantID><c:out value="${model.room.tenantID}"/></tenantID>
		<roomNameAndExtNumber><c:out value="${model.room.roomName}"/> <c:if test="${model.room.dialIn == ''}">[<c:out value="${model.room.roomExtNumber}"/>]</c:if><c:if test="${model.room.dialIn != ''}">[<c:out value="${model.room.dialIn}"/> x <c:out value="${model.room.roomExtNumber}"/>]</c:if></roomNameAndExtNumber>
	</row>
</dataset>