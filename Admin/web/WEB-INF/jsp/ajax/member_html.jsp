<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<nav><c:out value="${model.nav}"/></nav>
		<guideLoc><c:out value="${model.guideLoc}"/></guideLoc>
		<memberID><c:out value="${model.memberID}"/></memberID>
		<tenantPrefix><c:out value="${model.tenantPrefix}"/></tenantPrefix>
		<roleID><c:out value="${model.roleID}"/></roleID>
		<roomExtNumber><c:out value="${model.roomExtNumber}"/></roomExtNumber>
		<importedUsed><c:out value="${model.importedUsed}"/></importedUsed>
		<ldap><c:out value="${model.ldap}"/></ldap>
		<defaultGroupExists><c:out value="${model.defaultGroupExists}"/></defaultGroupExists>
	</row>
</dataset>