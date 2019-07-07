<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<adminCustomizeDesktopExists><c:out value="${model.adminCustomizeDesktopExists}"/></adminCustomizeDesktopExists>
		<allowTenantOverride><c:out value="${model.allowTenantOverride}"/></allowTenantOverride>
	</row>
</dataset>