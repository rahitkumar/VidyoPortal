<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<caCertParsed><![CDATA[
				<div style="width: 100%; height: 250px; overflow-y: scroll;">
			<table>
			<c:forEach var="cert" items="${model.caCerts}" varStatus="certNum">
				<tr>
					<td colspan=2 style="border-bottom: 1px solid black;"><spring:message code="super.security.ssl.certificate.certificate"/> <c:out value="${certNum.index + 1}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.version"/></td>
					<td><c:out value="${cert.version}" /></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.fingerprint"/></td>
					<td><c:out value="${cert.fingerprint}" /></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.serial.number"/></td>
					<td><c:out value="${cert.serial}" /></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.not.valid.before"/></td>
					<td><c:out value="${cert.notBefore}" /></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.not.valid.after"/></td>
					<td><c:out value="${cert.notAfter}" /></td>
				</tr>
				<tr>
					<td colspan=2><spring:message code="super.security.ssl.certificate.issuer"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.organization.name"/></td>
					<td><c:out value="${cert.issuerO}" /></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.common.name"/></td>
					<td><c:out value="${cert.issuerCN}" /></td>
				</tr>
				<tr>
					<td colspan=2><spring:message code="super.security.ssl.certificate.subject"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.organization.name"/></td>
					<td><c:out value="${cert.subjectO}" /></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.common.name"/></td>
					<td><c:out value="${cert.subjectCN}" /></td>
				</tr>
			</c:forEach>
			</table>
			</div>
			]]></caCertParsed>
		<caCert><c:out value="${model.caCert}"/></caCert>
	</row>
</dataset>