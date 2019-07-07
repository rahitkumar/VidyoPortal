<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	
	<prefix><c:out value="${model.prefix}"/></prefix>
	<isglobalscheduleroomenabled><c:out value="${model.isglobalscheduleroomenabled}"/></isglobalscheduleroomenabled>
</dataset>