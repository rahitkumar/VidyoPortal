<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<memberID><c:out value="${model.member.memberID}"/></memberID>
		<username><c:out value="${model.member.username}"/></username>
		<memberName><c:out value="${model.member.memberName}"/></memberName>
		<emailAddress><c:out value="${model.member.emailAddress}"/></emailAddress>
		<langID><c:out value="${model.member.langID}"/></langID>
		<description><c:out value="${model.member.description}"/></description>
	</row>
</dataset>