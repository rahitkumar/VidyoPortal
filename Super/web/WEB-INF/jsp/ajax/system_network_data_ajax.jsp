<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<ipAddress><c:out value="${model.ipAddress}"/></ipAddress>
		<subnetMask><c:out value="${model.subnetMask}"/></subnetMask>
		<gateway><c:out value="${model.gateway}"/></gateway>
		<dns1></dns1>
		<dns2></dns2>
		<MACAddress><c:out value="${model.MACAddress}"/></MACAddress>
		<SystemID></SystemID>

		<ipAddress2><c:out value="${model.ipAddress2}"/></ipAddress2>
		<subnetMask2><c:out value="${model.subnetMask2}"/></subnetMask2>

		<HTTPS><c:out value="${model.HTTPS}"/></HTTPS>
		<HTTPS_PORT><c:out value="${model.HTTPS_PORT}"/></HTTPS_PORT>
		<AllInOne><c:out value="${model.AllInOne}"/></AllInOne>

		<fqdn1><c:out value="${model.fqdn}"/></fqdn1>
		<fqdn2><c:out value="${model.fqdn2}"/></fqdn2>
	</row>
</dataset>