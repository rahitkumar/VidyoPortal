<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<c:if test="${model.guestconf.groupID != 0}">
			<groupID><c:out value="${model.guestconf.groupID}"/></groupID>
		</c:if>
		<proxyID><c:out value="${model.guestconf.proxyID}"/></proxyID>
	</row>
</dataset>