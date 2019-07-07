<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="g">
		<row>
			<groupID><c:out value="${g.groupID}"/></groupID>
			<tenantID><c:out value="${g.tenantID}"/></tenantID>
			<routerID><c:out value="${g.routerID}"/></routerID>
			<secondaryRouterID><c:out value="${g.secondaryRouterID}"/></secondaryRouterID>
			<groupName><c:out value="${g.groupName}"/></groupName>
			<groupDescription><c:out value="${g.groupDescription}"/></groupDescription>
			<defaultFlag><c:out value="${g.defaultFlag}"/></defaultFlag>
			<roomMaxUsers><c:out value="${g.roomMaxUsers}"/></roomMaxUsers>
			<userMaxBandWidthIn><c:out value="${g.userMaxBandWidthIn}"/></userMaxBandWidthIn>
			<userMaxBandWidthOut><c:out value="${g.userMaxBandWidthOut}"/></userMaxBandWidthOut>
		</row>
	</c:forEach>
</dataset>