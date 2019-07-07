<%@ page session="false"%>

<%@ page pageEncoding="iso-8859-1" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<head>

    <link rel="resource" type="application/l10n" href="js/SuperApp/resources/locales/locales.ini" />
    <script type="text/javascript" src="js/SuperApp/resources/l10n_login.js"></script>
    <script type="text/javascript" src="js/SuperApp/ext-js/ext-all.js"></script> 
	<link rel="stylesheet" type="text/css" href="js/SuperApp/ext-js/classic/theme-triton/resources/theme-triton-all.css"/>
	<script type="text/javascript" src="js/SuperApp/ext-js/classic/theme-triton/theme-triton.js"></script>
	<link rel='shortcut icon' type='image/x-icon' href="<%=request.getContextPath()%>/favicon.ico" />
</head>


<%
Locale locale = LocaleContextHolder.getLocale();
String language = "en";

if (locale.getLanguage().equalsIgnoreCase("zh")){
	language = locale.toString();
} else {
	language = locale.getLanguage();
}
%>

<script type="text/javascript" src="js/resources/locale/locale-<%=language%>.js"></script>