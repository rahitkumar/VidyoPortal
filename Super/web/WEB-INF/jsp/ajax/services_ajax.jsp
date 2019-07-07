<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="s">
		<row>
			<serviceID><c:out value="${s.serviceID}"/></serviceID>
			<roleID><c:out value="${s.roleID}"/></roleID>
			<roleName><c:out value="${s.roleName}"/></roleName>
			<serviceName><c:out value="${s.serviceName}"/></serviceName>
			<url><c:out value="${s.url}"/></url>
		</row>
	</c:forEach>
</dataset>