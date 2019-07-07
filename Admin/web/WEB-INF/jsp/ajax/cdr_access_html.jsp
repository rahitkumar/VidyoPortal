<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<CDRFormat><c:out value="${model.CDRFormat}"/></CDRFormat>
		<tenantName><c:out value="${model.tenantName}"/></tenantName>
	</row>
</dataset>