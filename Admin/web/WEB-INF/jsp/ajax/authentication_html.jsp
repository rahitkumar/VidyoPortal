<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
		
	<c:forEach items="${model}" var="entry">
	 <row >
	 	<type><c:out value="${entry.key}"/></type>
	 	<name><c:out value="${entry.value}"/></name>
	 </row>
	
</c:forEach>
	
		
	
</dataset>
