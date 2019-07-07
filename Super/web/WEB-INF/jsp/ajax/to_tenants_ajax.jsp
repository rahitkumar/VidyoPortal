<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="tenant">
		<row>
			<tenantID><c:out value="${tenant.tenantID}"/></tenantID>
			<tenantName><c:out value="${tenant.tenantName}"/></tenantName>
		</row>
	</c:forEach>
</dataset>