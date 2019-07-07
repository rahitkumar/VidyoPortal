<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<row>
		<proxyID>0</proxyID>
		<proxyName><spring:message code="no.proxy"/></proxyName>
	</row>
	<c:forEach items="${model.list}" var="p">
		<row>
			<proxyID><c:out value="${p.proxyID}"/></proxyID>
			<proxyName><c:out value="${p.proxyName}"/></proxyName>
		</row>
	</c:forEach>
</dataset>