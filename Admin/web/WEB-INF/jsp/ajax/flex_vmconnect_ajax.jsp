<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<results>
	<vmconnect><c:out value="${model.vmconnect}"/></vmconnect>

	<c:if test="${not empty model.novm}">
		<novm>true</novm>
	</c:if>

	<c:if test="${not empty model.guestID}">
		<guestID><c:out value="${model.guestID}"/></guestID>
	</c:if>

	<c:if test="${not empty model.entityID}">
		<entityID><c:out value="${model.entityID}"/></entityID>
		<displayName><c:out value="${model.room.roomName}"/></displayName>
		<c:if test="${model.room.roomPinned != 0}">
			<hasPin>true</hasPin>
		</c:if>
		<c:if test="${model.room.roomPinned == 0}">
			<hasPin>false</hasPin>
		</c:if>
	</c:if>
</results>
