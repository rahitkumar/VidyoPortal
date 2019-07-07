<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
        <c:if test="${not empty model.aboutInfo}">
                <aboutInfo><![CDATA[<c:out value="${model.aboutInfo}" escapeXml="false"/>]]></aboutInfo>
        </c:if>
        <c:if test="${empty model.aboutInfo}">
                <aboutInfo><![CDATA[<spring:message code="about.us.full"/>]]></aboutInfo>
        </c:if>
	</row>
</dataset>