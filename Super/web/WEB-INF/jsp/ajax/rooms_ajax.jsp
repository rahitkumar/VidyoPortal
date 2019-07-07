<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="r">
		<row>
			<roomID><c:out value="${r.roomID}"/></roomID>
			<tenantID><c:out value="${r.tenantID}"/></tenantID>
			<roomName><c:out value="${r.roomName}"/></roomName>
			<roomExtNumber><c:out value="${r.roomExtNumber}"/></roomExtNumber>
			<roomType><c:out value="${r.roomType}"/></roomType>
			<roomEnabled><c:out value="${r.roomEnabled}"/></roomEnabled>
			<roomLocked><c:out value="${r.roomLocked}"/></roomLocked>
			<roomPIN><c:out value="${r.roomPIN}"/></roomPIN>
			<roomKey><c:out value="${r.roomKey}"/></roomKey>
			<numParticipants><c:out value="${r.numParticipants}"/></numParticipants>
			<roomStatus><c:out value="${r.roomStatus}"/></roomStatus>
		</row>
	</c:forEach>
</dataset>