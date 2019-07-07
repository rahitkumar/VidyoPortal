<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<tenantID><c:out value="${model.tenant.tenantID}"/></tenantID>
		<tenantName><c:out value="${model.tenant.tenantName}"/></tenantName>
		<tenantURL><c:out value="${model.tenant.tenantURL}"/></tenantURL>
		<tenantPrefix><c:out value="${model.tenant.tenantPrefix}"/></tenantPrefix>
		<tenantDialIn><c:out value="${model.tenant.tenantDialIn}"/></tenantDialIn>
		<description><c:out value="${model.tenant.description}"/></description>
		<installs><c:out value="${model.tenant.installs}"/></installs>
		<seats><c:out value="${model.tenant.seats}"/></seats>
		<ports><c:out value="${model.tenant.ports}"/></ports>
		<guestLogin><c:out value="${model.tenant.guestLogin}"/></guestLogin>
		<guestLoginEnable><c:out value="${model.tenant.guestLogin}"/></guestLoginEnable>
	</row>
</dataset>
