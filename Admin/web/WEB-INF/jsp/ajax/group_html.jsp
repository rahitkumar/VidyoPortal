<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<nav><c:out value="${model.nav}"/></nav>
		<guideLoc><c:out value="${model.guideLoc}"/></guideLoc>
		<groupID><c:out value="${model.groupID}"/></groupID>
		<groupName><c:out value="${model.groupName}"/></groupName>
		<hasReplay><c:out value="${model.hasReplay}"/></hasReplay>
		<roomMaxUsers><c:out value="${model.roomMaxUsers}"/></roomMaxUsers>
	</row>
</dataset>