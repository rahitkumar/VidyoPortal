<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<splogoname><c:out value="${model.spname}"/></splogoname>
		<uplogoname><c:out value="${model.upname}"/></uplogoname>
        <vdlogoname><c:out value="${model.vdname}"/></vdlogoname>
	</row>
</dataset>