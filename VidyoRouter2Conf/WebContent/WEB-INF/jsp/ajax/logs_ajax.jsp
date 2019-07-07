<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.logFiles}" var="logFile">
		<row>
			<fileName><c:out value="${logFile.fileName}"/></fileName>
			<lastModified><c:out value="${logFile.lastModified}"/></lastModified>
			<size><c:out value="${logFile.length}"/></size>
		</row>
	</c:forEach>
</dataset>