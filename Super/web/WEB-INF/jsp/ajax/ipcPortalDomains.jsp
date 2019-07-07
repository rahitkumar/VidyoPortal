<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="include_ajax.jsp" %>

<dataset>

	<results><c:out value="${model.size}"/></results>
		<c:forEach items="${model.ipcDomains}" var="ipcDomain">
		<row>
			<domainID><c:out value="${ipcDomain.domainID}"/></domainID>
			<domainName><c:out value="${ipcDomain.domainName}"/></domainName>
			
			
		</row>
	</c:forEach>

</dataset>