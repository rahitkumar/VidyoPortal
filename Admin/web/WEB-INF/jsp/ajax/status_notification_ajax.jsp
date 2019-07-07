<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<flag><c:out value="${model.StatusNotification.flag}"/></flag>
		<url><c:out value="${model.StatusNotification.url}"/></url>
		<username><c:out value="${model.StatusNotification.username}"/></username>
		<password><c:out value="${model.StatusNotification.password}"/></password>
	</row>
</dataset>