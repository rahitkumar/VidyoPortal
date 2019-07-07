<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<c:if test="${model.Version.licensedValue == '20'}">
		<results>3</results>
	</c:if>
	<c:if test="${model.Version.licensedValue == '21' || model.Version.licensedValue == '22' }">
		<results>5</results>
	</c:if>

	<row>
		<feature><spring:message code="SuperSystemLicense.num.of.seats"/></feature>
		<license><c:out value="${model.Seats.currentValue}"/>/<c:out value="${model.Seats.licensedValue}"/> ( <spring:message code="AdminLicense.used.licensed"/> )</license>
		<inuse><c:out value="${model.Seats.currentValue}"/></inuse>
	</row>
	<row>
		<c:if test="${model.Version.licensedValue == '20'}">
			<feature><spring:message code="SuperSystemLicense.num.of.ports"/></feature>
		</c:if>
		<c:if test="${model.Version.licensedValue == '21'}">
			<feature><spring:message code="SuperSystemLicense.number.of.lines"/></feature>
		</c:if>
		<c:if test="${model.Version.licensedValue == '22'}">
			<feature><spring:message code="SuperSystemLicense.number.of.lines"/> (UVL)</feature>
		</c:if>
		<license><c:out value="${model.Ports.currentValue}"/>/<c:out value="${model.Ports.licensedValue}"/> ( <spring:message code="AdminLicense.used.licensed"/> )</license>
		<inuse><c:out value="${model.Ports.currentValue}"/></inuse>
	</row>
	<row>
		<feature><spring:message code="SuperSystemLicense.num.of.installs"/></feature>
		<license><c:out value="${model.Installs.currentValue}"/>/<c:out value="${model.Installs.licensedValue}"/> ( <spring:message code="AdminLicense.used.licensed"/> )</license>
		<inuse><c:out value="${model.Installs.currentValue}"/></inuse>
	</row>

	<c:if test="${model.Version.licensedValue == '21' || model.Version.licensedValue == '22'}">
		<row>
			<feature><spring:message code="SuperSystemLicense.number.of.executive.systems"/></feature>
			<license><c:out value="${model.LimitTypeExecutiveSystem.currentValue}"/>/<c:out value="${model.LimitTypeExecutiveSystem.licensedValue}"/> ( <spring:message code="AdminLicense.used.licensed"/> )</license>
			<inuse><c:out value="${model.LimitTypeExecutiveSystem.currentValue}"/></inuse>
		</row>
		<row>
			<feature><spring:message code="number.of.panorama.systems"/></feature>
			<license><c:out value="${model.LimitTypePanoramaSystem.currentValue}"/>/<c:out value="${model.LimitTypePanoramaSystem.licensedValue}"/> ( <spring:message code="AdminLicense.used.licensed"/> )</license>
			<inuse><c:out value="${model.LimitTypePanoramaSystem.currentValue}"/></inuse>
		</row>
	</c:if>

</dataset>