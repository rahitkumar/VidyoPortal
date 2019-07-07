<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.vrooms}" var="vr">
		<row>
			<memberName><c:out value="${vr.memberName}"/></memberName>
			<ipAddress><c:out value="${vr.ipAddress}"/></ipAddress>
			<status><c:out value="${vr.statusVal}"/></status>
		</row>
	</c:forEach>
</dataset>