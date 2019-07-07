<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page session="false" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>

<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>

<%
Locale locale = LocaleContextHolder.getLocale();
%>
<c:set var="systemlang"><%= locale.toString()%></c:set>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title><c:out value="${model.title}"/></title>
    
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
            <c:if test="${model.isInfoCheck}">
                <div id="infoIconCheck"></div>
            </c:if>
            <c:if test="${!model.isInfoCheck}">
                <div id="infoIconExclamation"></div>
            </c:if>
            <div id="infoMsg"><c:out value="${model.infoMsg}"/></div>
        </div>
        
        <div id="row-main">
            <div id="closeTab">
                <div id="closeTabMsg">
                    <spring:message javaScriptEscape="true" code="please.close.this.tab"/>
                </div>
                <div id="closeTabBackToIndex"><a href="<c:url value='/' />" style="color:#8888CC"><spring:message javaScriptEscape="true" code="back.to.login"/></a></div>
                <div id="closeTabEmptySpace"></div>
            </div>
        </div>
        
        <div id="row-help">
            <div id="helpText">
            <spring:message javaScriptEscape="true" code="need.help"/> <a href="<c:url value='/contact.html'/>" style="color:#8888CC" target="_blank"><spring:message javaScriptEscape="true" code="contact.support"/></a>
            </div>
        </div>
    </div>
</body>

</html>