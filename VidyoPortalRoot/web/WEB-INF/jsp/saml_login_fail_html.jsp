<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page session="false" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>

<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>

<%
Locale locale = LocaleContextHolder.getLocale();
%>
<c:set var="systemlang"><%= locale.toString()%></c:set>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    
    <title><spring:message javaScriptEscape="true" code="oops"/></title>
    
    <link rel="stylesheet" href="themes/vidyo/common.css">

    <link rel="shortcut icon" href="favicon.ico">
</head>


<body>
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
            <div id="infoMsg"><spring:message javaScriptEscape="true" code="oops"/></div>
        </div>
        
        <div id="row-main">
            <div id="closeTab">
                <div id="samlLoginErrorMsg" style="font-weight: bold;">
                    <c:out value="${model.errorMessage}" escapeXml="false"/>
                </div>
                <div id="samlTryAgainBtn">
                    <div class='try-again-item'>
                        <a href='<c:url value="/index.html"/>' style="color: inherit; text-decoration: inherit;"><spring:message javaScriptEscape="true" code="try.again"/></a>
                    </div>
                </div>
            </div>
        </div>
        
        <div id="row-help">
            <div id="helpText">
            <spring:message javaScriptEscape="true" code="need.help"/> <a href="<c:url value='/contact.html'/>" style="color:#8888CC" target="_blank"><spring:message javaScriptEscape="true" code="contact.support"/></a>
            </div>
        </div>
    </div>
</body>
<!--
    <div style="margin: 50px auto 0px auto;">
        <div style="text-align: center;">
            <c:if test="${model.logoUrl != ''}">
                <img src="<c:url value="${model.logoUrl}"/>" border="0" width="228" height="210" />
            </c:if>
            <c:if test="${model.logoUrl == ''}">
                <img src="<c:url value="/themes/vidyo/i/logo.png"/>" alt="Vidyo" width="228" height="210" />
            </c:if>
        </div>
        <div style="margin-top: 70px; text-align: center; font-family: sans-serif;" id="main">
            <c:if test="${model.errorMessage != ''}">
                <spring:message javaScriptEscape="true" code="the.following.error.occurred"/>&nbsp;<c:out value="${model.errorMessage}"></c:out><br>
                <spring:message javaScriptEscape="true" code="please.contact.your.system.administrator"/>
                !--  <script type="text/javascript">
                    window.setTimeout(function redirect() { window.location='/saml/login'}, 5000);
                </script>
                --
            </c:if>
        </div>
    </div>
-->

</html>