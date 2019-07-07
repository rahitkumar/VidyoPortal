<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<ipAddress><c:out value="${model.network.ipAddress}"/></ipAddress>
		<subnetMask><c:out value="${model.network.subnetMask}"/></subnetMask>
		<gateway><c:out value="${model.network.gateway}"/></gateway>
		<dns1><c:out value="${model.network.dns1}"/></dns1>
		<dns2><c:out value="${model.network.dns2}"/></dns2>
		<MACAddress><c:out value="${model.network.MACAddress}"/></MACAddress>

		<ipAddress2><c:out value="${model.network.ipAddress2}"/></ipAddress2>
		<subnetMask2><c:out value="${model.network.subnetMask2}"/></subnetMask2>
		<fqdn1><c:out value="${model.network.fqdn}"/></fqdn1>
		<fqdn2><c:out value="${model.network.fqdn2}"/></fqdn2>
	</row>
</dataset>