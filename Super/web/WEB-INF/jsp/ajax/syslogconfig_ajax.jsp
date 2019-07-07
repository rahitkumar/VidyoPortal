<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>
 	  <row>
		<c:forEach var="entry" items="${model}">
		  <<c:out value="${entry.key}"/>><c:out value="${entry.value}"/></<c:out value="${entry.key}"/>>	
		</c:forEach>
	  </row>
</results>
</dataset>