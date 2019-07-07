<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>
 	<c:out value="${model.num}"/></results>
	<c:forEach var="entry" items="${model}">
		<row>
		   <code><c:out value="${entry.key}"/></code>
		  <key><spring:message><jsp:attribute name="code"><c:out value="${entry.key}"/></jsp:attribute></spring:message></key>
		  <value><c:out value="${entry.value}"/></value>
	  	</row>
	</c:forEach>
</dataset>
