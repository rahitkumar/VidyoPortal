<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>
<response>
	<EID><c:out value="${model.endpointGUID}"/></EID>
	<linked><c:out value="${model.success}"/></linked>

	<c:if test="${model.status == '0'}">
		<status>Offline</status>
	</c:if>
	<c:if test="${model.status == '1'}">
		<status>Online</status>
	</c:if>
	<c:if test="${model.status == '2'}">
		<status>Busy</status>
	</c:if>
	<c:if test="${model.status == '3'}">
		<status>Ringing</status>
	</c:if>
	<c:if test="${model.status == '4'}">
		<status>RingAccepted</status>
	</c:if>
	<c:if test="${model.status == '5'}">
		<status>RingRejected</status>
	</c:if>
	<c:if test="${model.status == '6'}">
		<status>RingNoAnswer</status>
	</c:if>
	<c:if test="${model.status == '7'}">
		<status>Alerting</status>
	</c:if>
	<c:if test="${model.status == '8'}">
		<status>AlertCancelled</status>
	</c:if>
	<c:if test="${model.status == '9'}">
		<status>BusyInOwnRoom</status>
	</c:if>

	<currentVersion><c:out value="${model.currentVersion}"/></currentVersion>
	<activeVersion><c:out value="${model.activeVersion}"/></activeVersion>
	<downloadLink><c:out value="${model.fileName}"/></downloadLink>

	<portalVersion><c:out value="${model.portalVersion}"/></portalVersion>
</response>

