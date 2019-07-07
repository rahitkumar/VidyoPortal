<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<participants total="<c:out value="${model.num}"/>">
<c:forEach items="${model.list}" var="ctl">
	<participant>
		<entityID><c:out value="${ctl.roomID}"/></entityID>
		<endpointID><c:out value="${ctl.endpointID}"/></endpointID>
		<endpointGUID><c:out value="${ctl.endpointGUID}"/></endpointGUID>
		<endpointType><c:out value="${ctl.endpointType}"/></endpointType>
		<displayName><c:out value="${ctl.name}"/></displayName>
		<dialIn><c:out value="${ctl.dialIn}"/></dialIn>
		<extension><c:out value="${ctl.ext}"/></extension>

	<c:if test="${ctl.video == '1'}">
		<video>true</video>
	</c:if>
	<c:if test="${ctl.video == '0'}">
		<video>false</video>
	</c:if>

	<c:if test="${ctl.audio == '1'}">
		<audio>true</audio>
	</c:if>
	<c:if test="${ctl.audio == '0'}">
		<audio>false</audio>
	</c:if>

	<c:if test="${ctl.connect == '1'}">
		<connect>true</connect>
	</c:if>
	<c:if test="${ctl.connect == '0'}">
		<connect>false</connect>
	</c:if>

	</participant>
</c:forEach>
</participants>