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
		<roomDisplayName><c:out value="${model.room.displayName}"/></roomDisplayName>
		<roomName><c:out value="${model.room.roomName}"/></roomName>
		<dialIn><c:out value="${model.room.dialIn}"/></dialIn>
		<roomExtNumber><c:out value="${model.room.roomExtNumber}"/></roomExtNumber>
		<roomDescription><c:out value="${model.room.roomDescription}"/></roomDescription>
		<roomType><c:out value="${model.room.roomType}"/></roomType>
	<enabled><c:out value="${model.room.roomEnabled}"/></enabled>
		<roomEnabled><c:out value="${model.room.roomEnabled}"/></roomEnabled>
	<locked><c:out value="${model.room.roomLocked}"/></locked>
		<roomLocked><c:out value="${model.room.roomLocked}"/></roomLocked>
    <c:if test="${not empty model.room.roomPIN}">
        <roomPIN>****</roomPIN>
    </c:if>
    <c:if test="${empty model.room.roomPIN}">
        <roomPIN/>
    </c:if>
    <c:if test="${not empty model.room.roomModeratorPIN}">
        <roomModeratorPIN>****</roomModeratorPIN>
    </c:if>
    <c:if test="${empty model.room.roomModeratorPIN}">
        <roomModeratorPIN/>
    </c:if>
        <roomKey><c:out value="${model.room.roomKey}"/></roomKey>
		<roomURL><c:out value="${model.room.roomURL}"/></roomURL>
		<tenantID><c:out value="${model.room.tenantID}"/></tenantID>
		<tenantPrefix><c:out value="${model.room.tenantPrefix}"/></tenantPrefix>
		<tenantName><c:out value="${model.room.tenantName}"/></tenantName>
		<displayName><c:out value="${model.room.displayName}"/></displayName>
		<roomTypePersonal><c:out value="${model.roomTypePersonal}"/></roomTypePersonal>
		<roomPinValue><c:out value="${model.roomPinValue}"/></roomPinValue>
		<roomModeratorPinValue><c:out value="${model.roomModeratorPinValue}"/></roomModeratorPinValue>
		<importedUsed><c:out value="${model.importedUsed}"/></importedUsed>
	</row>
</dataset>