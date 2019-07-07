<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<roomLinkFormat><c:out value="${model.roomLinkFormat}"/></roomLinkFormat>
		<roomKeyLength><c:out value="${model.roomKeyLength}"/></roomKeyLength>
	</row>
</dataset>