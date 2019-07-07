<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<enabled><c:out value="${model.cdr.enabled == 1 ? true : false}"/></enabled>
		<format><c:out value="${model.cdr.format}"/></format>
		<allowdeny><c:out value="${model.cdr.allowdeny == 1 ? true : false}"/></allowdeny>
		<id><c:out value="${model.cdr.id}"/></id>
		<password><c:out value="${model.cdr.password}"/></password>
		<ip><c:out value="${model.cdr.ip}"/></ip>
		<allowdelete><c:out value="${model.cdr.allowdelete == 1 ? true : false}"/></allowdelete>
   	    <fipsEnabled><c:out value="${model.fipsEnabled}"/></fipsEnabled>
	</row>
</dataset>