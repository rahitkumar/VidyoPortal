<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
<c:forEach items="${model.list}" var="ctl">
	<row>
		<memberID><c:out value="${ctl.memberID}"/></memberID>
		<endpointID><c:out value="${ctl.endpointID}"/></endpointID>
		<name><c:out value="${ctl.name}"/>@<c:out value="${ctl.tenantName}"/></name>
		<ext><c:out value="${ctl.ext}"/></ext>
		<tenantID><c:out value="${ctl.tenantID}"/></tenantID>
		<tenantName><c:out value="${ctl.tenantName}"/></tenantName>
	</row>
</c:forEach>
</dataset>