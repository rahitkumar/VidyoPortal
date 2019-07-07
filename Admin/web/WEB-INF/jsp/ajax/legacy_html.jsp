<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<memberID><c:out value="${model.member.memberID}"/></memberID>
		<memberName><c:out value="${model.member.memberName}"/></memberName>
	</row>
</dataset>