<%@ page session="false" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>

<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	Locale locale = LocaleContextHolder.getLocale();
%>
<c:set var="systemlang"><%= locale.toString()%></c:set>

<html>
<head>
	<title></title>

	<link rel="stylesheet" href="themes/vidyo/common.css">
</head>
<body>


	<div id="main">
		<div id="container">
			<div class="row-header">
				<div id="header">
					<c:if test="${model.logoUrl != ''}">
						<img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/>
					</c:if>
					<c:if test="${model.logoUrl == ''}">
						<div id="logo"></div>
					</c:if>
				</div>
			</div>

			<div id="row-title">
				<div id="infoIconExclamation"></div>
				<div id="infoMsg">
					<spring:message code="this.page.is.no.longer.supported"/><br />
				</div>
			</div>
			<div style="font-size: 20px; text-align: center; padding: 50px 0px 10px 0px; color: #535353;"><spring:message code="for.enhanced.security.flash.based.functionality.is.no.longer.supported.by.this.system"/><br /><spring:message code="instead.this.system.now.supports.html.based.functionality"/>
				<br /><spring:message code="please.contact.your.system.administrator.for.details"/></div>
			<div id="row-help">
				<div style="text-align: center; padding-top : 30px; padding-bottom : 30px; font-size: 14px; font-weight: 200; color: #707070;">
					<vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="need.help"/></vidyo:replaceString> <a href="<c:url value='/contact.html'/>" style="font-size: 14px;font-weight: 300;color:#7EACDF;text-decoration: inherit;" target="_blank"><vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="contact.support"/></vidyo:replaceString></a>
				</div>
			</div>

			<div id="copyright" style="	text-align: center; padding-top : 20px; padding-bottom : 20px; font-size: 10px; color: #aaaaaa; width: 800px; margin: auto;">&copy;2008-2019 Vidyo</div>
		</div>
	</div>

</body>
</html>