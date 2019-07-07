<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<certParsed><![CDATA[
				<table>
				<tr>
					<td colspan=2><spring:message code="super.security.ssl.certificate.certificate"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.version"/></td>
					<td><c:out value="${model.version}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.fingerprint"/></td>
					<td><c:out value="${model.fingerprint}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.serial.number"/></td>
					<td><c:out value="${model.serial}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.not.valid.before"/></td>
					<td><c:out value="${model.notBefore}"/>
					<c:if test='${model.notValidYet == "1"}'>
                        &nbsp;<span style="color: red; font-weight: bold;">(not yet valid)</span>
					</c:if>
					</td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.not.valid.after"/></td>
					<td><c:out value="${model.notAfter}"/>
                    <c:if test='${model.expired == "1"}'>
                        &nbsp;<span style="color: red; font-weight: bold;">(expired)</span>
					</c:if>
					</td>
				</tr>
				<tr>
					<td colspan=2><spring:message code="super.security.ssl.certificate.issuer"/></td>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.country.name"/></td>
					<td><c:out value="${model.issuerCountry}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.state.name"/></td>
					<td><c:out value="${model.issuerState}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.locality.name"/></td>
					<td><c:out value="${model.issuerCity}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.organization.name"/></td>
					<td><c:out value="${model.issuerCompany}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.organization.unit.name"/></td>
					<td><c:out value="${model.issuerDivision}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.common.name"/></td>
					<td><c:out value="${model.issuerDomain}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.email.address"/></td>
					<td><c:out value="${model.issuerEmail}"/></td>
				</tr>
				<tr>
					 <td colspan=2><spring:message code="super.security.ssl.certificate.subject"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.country.name"/></td>
					<td><c:out value="${model.subjectCountry}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.state.name"/></td>
					<td><c:out value="${model.subjectState}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.locality.name"/></td>
					<td><c:out value="${model.subjectCity}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.organization.name"/></td>
					<td><c:out value="${model.subjectCompany}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.organization.unit.name"/></td>
					<td><c:out value="${model.subjectDivision}"/></td>
				</tr>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.common.name"/></td>
					<td><c:out value="${model.subjectDomain}"/></td>
				</tr>
				<c:if test="${not empty model.san}">
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;Alternative Name:</td>
					<td><c:out value="${model.san}"/></td>
				</tr>
				</c:if>
				<tr>
					<td style="font-weight:bold;text-align:right;padding-right:2px;">&nbsp;&nbsp;&nbsp;<spring:message code="super.security.ssl.certificate.email.address"/></td>
					<td><c:out value="${model.subjectEmail}"/></td>
				</tr>
				</table>
				]]></certParsed>
		<cert><c:out value="${model.cert}"/></cert>
		<crtMatchesKey><c:out value="${model.crtMatchesKey}"/></crtMatchesKey>
		<crtMatchesDomain><c:out value="${model.crtMatchesFQDN}"/></crtMatchesDomain>
		<fqdn><c:out value="${model.fqdn}"/></fqdn>
	</row>
</dataset>