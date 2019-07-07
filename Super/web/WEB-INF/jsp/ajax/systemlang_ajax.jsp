<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<langID><c:out value="${model.systemlang.langID}"/></langID>
		<langCode><c:out value="${model.systemlang.langCode}"/></langCode>
	</row>
</dataset>