<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<wsflag><c:out value="${model.Authentication.wsflag}"/></wsflag>
		<wsurl><c:out value="${model.Authentication.wsurl}"/></wsurl>
		<wsusername><c:out value="${model.Authentication.wsusername}"/></wsusername>
		<wspassword><c:out value="${model.Authentication.wspassword}"/></wspassword>
		<ldapflag><c:out value="${model.Authentication.ldapflag}"/></ldapflag>
		<c:if test="${model.Authentication.ldapurl == ''}">
		<ldapurl>ldap://</ldapurl>
		</c:if>
		<c:if test="${model.Authentication.ldapurl != ''}">
		<ldapurl><c:out value="${model.Authentication.ldapurl}"/></ldapurl>
		</c:if>
		<ldapusername><c:out value="${model.Authentication.ldapusername}"/></ldapusername>
		<ldappassword><c:out value="${model.Authentication.ldappassword}"/></ldappassword>
		<ldapbase><c:out value="${model.Authentication.ldapbase}"/></ldapbase>
		<ldapfilter><c:out value="${model.Authentication.ldapfilter}"/></ldapfilter>
		<ldapscope><c:out value="${model.Authentication.ldapscope}"/></ldapscope>
		<enableAdminAPI><c:out value="${model.enableAdminAPI}"/></enableAdminAPI>
        <ldapmappingflag><c:out value="${model.Authentication.ldapmappingflag}"/></ldapmappingflag>
        <samlflag><c:out value="${model.Authentication.samlflag}"/></samlflag>
        <samlIdpMetadata><c:out value="${model.Authentication.samlIdpMetadata}"/></samlIdpMetadata>
        <samlSpEntityId><c:out value="${model.Authentication.samlSpEntityId}"/></samlSpEntityId>
        <samlSecurityProfile><c:out value="${model.Authentication.samlSecurityProfile}"/></samlSecurityProfile>
        <samlSSLProfile><c:out value="${model.Authentication.samlSSLProfile}"/></samlSSLProfile>
        <samlSignMetadata><c:out value="${model.Authentication.samlSignMetadata}"/></samlSignMetadata>
        <samlmappingflag><c:out value="${model.Authentication.samlAuthProvisionType.value}"/></samlmappingflag>
        <idpAttributeForUsername><c:out value="${model.Authentication.idpAttributeForUsername}"/></idpAttributeForUsername>
        <sessionExpPeriod><c:out value="${model.sessionExpPeriod}"/></sessionExpPeriod>
        <cacflag><c:out value="${model.Authentication.cacflag}" /></cacflag>
        <userNameExtractfrom><c:out value="${model.Authentication.userNameExtractfrom}" /></userNameExtractfrom>
        <ocspcheck><c:out value="${model.Authentication.ocspcheck}" /></ocspcheck>
        <ocsprespondercheck><c:out value="${model.Authentication.ocsprespondercheck}" /></ocsprespondercheck>
        <ocspresponder><c:out value="${model.Authentication.ocspresponder}" /></ocspresponder>
        <ocspnonce><c:out value="${model.Authentication.ocspnonce}" /></ocspnonce>
        <cacldapflag><c:out value="${model.Authentication.cacldapflag}"/></cacldapflag>	
        <pkiCertReviewPending><c:out value="${model.pkiCertReviewPending}" /></pkiCertReviewPending>	
         <webServerRestartNeeded><c:out value="${model.webServerRestartNeeded}" /></webServerRestartNeeded>	
        <isSSLEnabled><c:out value="${model.isSSLEnabled}"/></isSSLEnabled>	
        <restFlag><c:out value="${model.Authentication.restFlag}"/></restFlag>
		<restUrl><c:out value="${model.Authentication.restUrl}"/></restUrl>
        
    </row>
</dataset>