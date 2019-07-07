<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<serviceID><c:out value="${model.service.serviceID}"/></serviceID>
		<roleID><c:out value="${model.service.roleID}"/></roleID>
		<roleName><c:out value="${model.service.roleName}"/></roleName>
		<serviceName><c:out value="${model.service.serviceName}"/></serviceName>
		<url><c:out value="${model.service.url}"/></url>
		<user><c:out value="${model.service.user}"/></user>
	</row>
</dataset>