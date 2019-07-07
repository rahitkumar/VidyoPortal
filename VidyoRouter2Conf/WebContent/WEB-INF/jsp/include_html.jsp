<%@ page session="false"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page import="java.util.Locale" %>
<%
    Locale locale = LocaleContextHolder.getLocale();
    String country = locale.getCountry();
    String html_lang = locale.getLanguage();
    html_lang += country.equalsIgnoreCase("") ? "" : "-"+locale.getCountry();
%>
<c:set var="systemlang"><%= locale.toString()%></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%= html_lang%>" lang="<%= html_lang%>">
<c:set var="admin_help"><spring:theme code="admin_help"/></c:set>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
    <title>VidyoRouter Configuration</title>

    <!-- Include Ext stylesheets here: -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/js/resources/css/ext-all.css"/>">

    <!-- Include Ext JS scripts -->
    <script type="text/javascript" src="<c:url value="/js/adapter/ext/ext-base.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/ext-all.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/ext-basex.js"/>"></script>

    <script type="text/javascript" src="<c:url value="/js/resources/locale/ext-lang-${systemlang}.js"/>"></script>


    <c:set var="css"><spring:theme code="css"/></c:set>
    <c:if test="${not empty css}">
           <link rel="stylesheet" href="<c:url value="${css}"/>" type="text/css">
    </c:if>

    <script type="text/javascript">
        Ext.onReady(function(){
            Ext.lib.Ajax.onStatus([401,403], function(statusCode, transaction, response, callback, isAbort) {
                if (statusCode == 401) {
                    window.location = '<c:url value="/login.html?error=2"/>';
                }
                if (statusCode == 403) {
                    window.location = '<c:url value="/login.html?error=3"/>';
                }
            },
            this);

            Ext.Ajax.defaultHeaders = {'${_csrf.headerName}': '${_csrf.token}' };

        });


    </script>
</head>