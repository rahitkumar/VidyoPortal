<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="../ajax/include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<security>
			<nav><c:out value="${model.nav}"/></nav>
			<guideLoc><c:out value="${model.guideLoc}"/></guideLoc>
			<sslEnabledFlag><c:out value="${model.sslEnabledFlag}"/></sslEnabledFlag>
			<httpsOnlyFlag><c:out value="${model.httpsOnlyFlag}"/></httpsOnlyFlag>
			<httpsOnlyFlagNoRedirect><c:out value="${model.httpsOnlyFlagNoRedirect}"/></httpsOnlyFlagNoRedirect>
			<useDefaultRootCerts><c:out value="${model.useDefaultRootCerts}"/></useDefaultRootCerts>
			<ocspEnabledFlag><c:out value="${model.ocspEnabledFlag}"/></ocspEnabledFlag>
			<ocspOverrideFlag><c:out value="${model.ocspOverrideFlag}"/></ocspOverrideFlag>
			<ocspDefaultResponder><c:out value="${model.ocspDefaultResponder}"/></ocspDefaultResponder>
			<ocspSupportFlag><c:out value="${model.ocspSupportFlag}"/></ocspSupportFlag>
			<encryptionEnabledFlag><c:out value="${model.encryptionEnabledFlag}"/></encryptionEnabledFlag>
		</security>
	</row>
</dataset>