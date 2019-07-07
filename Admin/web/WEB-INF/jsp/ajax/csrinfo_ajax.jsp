<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<CN><c:out value="${model.CN}"/></CN>
		<O><c:out value="${model.O}"/></O>
		<OU><c:out value="${model.OU}"/></OU>
		<L><c:out value="${model.L}"/></L>
		<ST><c:out value="${model.ST}"/></ST>
		<C><c:out value="${model.C}"/></C>
		<KeySize><c:out value="${model.KeySize}"/></KeySize>
		<CSR><c:out value="${model.CSR}"/></CSR>
		<HTTP><c:out value="${model.HTTP}"/></HTTP>
		<HTTP_PORT><c:out value="${model.HTTP_PORT}"/></HTTP_PORT>
		<HTTPS><c:out value="${model.HTTPS}"/></HTTPS>
		<HTTPS_PORT><c:out value="${model.HTTPS_PORT}"/></HTTPS_PORT>
	</row>
</dataset>