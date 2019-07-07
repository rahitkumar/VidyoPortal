<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<roomID><c:out value="${model.roomID}"/></roomID>
		<guideLoc><c:out value="${model.guideLoc}"/></guideLoc>
		<roomExtNumber><c:out value="${model.roomExtNumber}"/></roomExtNumber>
		<roomName><c:out value="${model.roomName}"/></roomName>
		<tenantPrefix><c:out value="${model.tenantPrefix}"/></tenantPrefix>
		<roomTypePersonal><c:out value="${model.roomTypePersonal}"/></roomTypePersonal>
		<roomPIN><c:out value="${model.roomPIN}"/></roomPIN>
		<roomModeratorPIN><c:out value="${model.roomModeratorPIN}"/></roomModeratorPIN>
		<importedUsed><c:out value="${model.importedUsed}"/></importedUsed>
	</row>
</dataset>