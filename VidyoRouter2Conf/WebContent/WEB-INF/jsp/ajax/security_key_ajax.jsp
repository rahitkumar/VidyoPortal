<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<bits><c:out value="${model.bits}"/></bits>
		<keyHash><c:out value="${model.keyHash}"/></keyHash>
	</row>
</dataset>