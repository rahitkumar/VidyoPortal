<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<hostport><c:out value="${model.DBProperties.hostport}"/></hostport>
		<username><c:out value="${model.DBProperties.username}"/></username>
		<password><c:out value="${model.DBProperties.password}"/></password>
	</row>
</dataset>