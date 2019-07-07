<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<cdr_id><c:out value="${model.cdr_id}"/></cdr_id>
		<cdr_pwd><c:out value="${model.cdr_pwd}"/></cdr_pwd>
		<cdr_ip><c:out value="${model.cdr_ip}"/></cdr_ip>
		<cdr_allow_delete><c:out value="${model.cdr_allow_delete}"/></cdr_allow_delete>
	</row>
</dataset>