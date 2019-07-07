<%@ page session="false"%>

<%@ page pageEncoding="iso-8859-1" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page import="java.util.Locale" %>
 
<head>
  	<script type="text/javascript" src="js/AdminApp/ext-js/ext-all.js"></script> 
	<link rel="stylesheet" type="text/css" href="js/AdminApp/ext-js/classic/theme-triton/resources/theme-triton-all.css"/>
	<script type="text/javascript" src="js/AdminApp/ext-js/classic/theme-triton/theme-triton.js"></script> 
    <link rel="resource" type="application/l10n" href="js/AdminApp/resources/locales/locales.ini" />
    <script type="text/javascript" src="js/AdminApp/resources/l10n_login.js"></script>
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
<body>


<script type="text/javascript">

	var csrfHeaderName = '<c:out value="${_csrf.headerName}" />';
	var csrfToken = '<c:out value="${_csrf.token}" />';
	var csrfFormParamName = '<c:out value="${_csrf.parameterName}" />'; 
</script>
<script type="text/javascript" src="js/resources/locale/locale-<%=language%>.js"></script>
</body>

