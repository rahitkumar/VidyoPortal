<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<groupID><c:out value="${model.group.groupID}"/></groupID>
		<tenantID><c:out value="${model.group.tenantID}"/></tenantID>
		<routerID><c:out value="${model.group.routerID}"/></routerID>
		<secondaryRouterID><c:out value="${model.group.secondaryRouterID}"/></secondaryRouterID>
		<groupName><c:out value="${model.group.groupName}"/></groupName>
		<groupDescription><c:out value="${model.group.groupDescription}"/></groupDescription>
		<defaultFlag><c:out value="${model.group.defaultFlag}"/></defaultFlag>
		<roomMaxUsers><c:out value="${model.group.roomMaxUsers}"/></roomMaxUsers>
		<userMaxBandWidthIn><c:out value="${model.group.userMaxBandWidthIn}"/></userMaxBandWidthIn>
		<userMaxBandWidthOut><c:out value="${model.group.userMaxBandWidthOut}"/></userMaxBandWidthOut>
	</row>
</dataset>