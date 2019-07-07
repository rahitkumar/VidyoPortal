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
    String country = locale.getCountry();
    String html_lang = locale.getLanguage();
    html_lang += country.equalsIgnoreCase("") ? "" : "-"+locale.getCountry();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%= html_lang%>" lang="<%= html_lang%>">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <style type="text/css">
        html{
            margin:0;
            padding:0;
        }
        body {
            margin: 0px;
            padding: 0px;
            height:100%;
            background-color: gray;
            background-image: url('themes/vidyo/i/download/bg.png');
            background-image: -webkit-image-set(
                url('themes/vidyo/i/download/bg.png') 1x,
                url('themes/vidyo/i/download/bg@2x.png') 2x);
            font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
            font-weight: 300;
            font-size: 1.0em;
            color: #535353;
        }
        #main {
            margin: 0px auto;
            width: 960px;
        }
        #logoContainer {
            text-align: center;
            padding-top: 50px;
        }
        #vidyoLogo {
            background-image: url('themes/vidyo/i/download/logo.png');
            background-image: -webkit-image-set(
                url('themes/vidyo/i/download/logo.png') 1x,
                url('themes/vidyo/i/download/logo@2x.png') 2x);
            width: 288px;
            height: 104px;
            margin: 0px auto;
        }
        #contactInfo {
            display: block;
            clear: both;
            margin: 0px auto;
            padding-top: 75px;
            width: 960px;
            height: 300px;
            text-align: center;
        }
    </style>
    <title><vidyo:replaceString from="\\\\'" to="'"><spring:message code="title"/></vidyo:replaceString></title>
</head>
<body>
    <div id="main">
        <div id="logoContainer">
            <c:if test="${model.logoUrl != ''}">
                <img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/>
            </c:if>
            <c:if test="${model.logoUrl == ''}">
                <div id="vidyoLogo"></div>
            </c:if>
        </div>
        <div id="contactInfo">
            <%@ include file="contact_content_html.jsp" %>
        </div>
    </div>
</body>
</html>
