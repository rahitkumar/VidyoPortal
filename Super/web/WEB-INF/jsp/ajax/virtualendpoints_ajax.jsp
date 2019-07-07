<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="ve">
		<row>
			<endpointID><c:out value="${ve.endpointID}"/></endpointID>
			<serviceID><c:out value="${ve.serviceID}"/></serviceID>
			<gatewayID><c:out value="${ve.gatewayID}"/></gatewayID>
			<endpointGUID><c:out value="${ve.endpointGUID}"/></endpointGUID>
			<prefix><c:out value="${ve.prefix}"/></prefix>
			<status><c:out value="${ve.status}"/></status>

			<c:if test="${ve.status == '0'}">
				<statusText>Offline</statusText>
			</c:if>
			<c:if test="${ve.status == '1'}">
				<statusText>Online</statusText>
			</c:if>
			<c:if test="${ve.status == '2'}">
				<statusText>Busy</statusText>
			</c:if>
			<c:if test="${ve.status == '3'}">
				<statusText>Ringing</statusText>
			</c:if>
			<c:if test="${ve.status == '4'}">
				<statusText>RingAccepted</statusText>
			</c:if>
			<c:if test="${ve.status == '5'}">
				<statusText>RingRejected</statusText>
			</c:if>
			<c:if test="${ve.status == '6'}">
				<statusText>RingNoAnswer</statusText>
			</c:if>
			<c:if test="${ve.status == '7'}">
				<statusText>JoinFailed</statusText>
			</c:if>

			<c:if test="${ve.direction == '0'}">
				<direction>from Legacy Device</direction>
			</c:if>
			<c:if test="${ve.direction == '1'}">
				<direction>to Legacy Device</direction>
			</c:if>
		</row>
	</c:forEach>
</dataset>