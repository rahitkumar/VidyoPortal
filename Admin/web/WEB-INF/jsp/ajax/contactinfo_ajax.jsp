<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
	<c:if test="${not empty model.contactInfo}">
		<contactInfo><![CDATA[<c:out value="${model.contactInfo}" escapeXml="false"/>]]></contactInfo>
	</c:if>
	</row>
</dataset>