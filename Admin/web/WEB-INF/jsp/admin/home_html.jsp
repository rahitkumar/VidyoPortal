<!DOCTYPE html>
<!-- added as per the minification process. this will only needed for this page. we dynamically change this html based on build type. if it is prod /testing content is different.if it is development we need to load files which is not required in minified version -->
<%@ include file="include_html_minify.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page import="java.util.Locale" %>
<head>
	 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	 <title>Vidyo Admin</title>
	 <link rel="shortcut icon" type="image/ico" href="favicon.ico" />
	    <script id="microloader" type="text/javascript" src="microloader.js"></script>


     <link rel="stylesheet" type="text/css" href="js/AdminApp/resources/vidyo-ext.css"></link>
        <script type="text/javascript" src="js/AdminApp/resources/l10n.js"></script>
		<link rel="prefetch" type="application/l10n" href="js/AdminApp/resources/locales/locales.ini" />
   
   
  	
  	<script type ="text/javascript">
	  	var url = '<c:url value="networkconfig.ajax"/>';
		var lang = sessionStorage.getItem('lang');
	    document.webL10n.setLanguage(lang);
	    var l10n = document.webL10n.get;
	    var guideLoc = '<c:out value="${model.guideLoc}"/>';
	  	var appFolder = "js/AdminApp/app";
  	    		
  		var csrfHeaderName = '<c:out value="${_csrf.headerName}" />';
  		var csrfToken = '<c:out value="${_csrf.token}" />';
  		var csrfFormParamName = '<c:out value="${_csrf.parameterName}" />';
  		var ipAddress = '[<c:out value="${model.ipAddress}" />]';
        var adminUserName = '<c:out value="${model.user.memberName}"/>';
        var userRole = '<c:out value="${model.userRole}"/>';
        var welcomeBannerTitle = 'Welcome <c:out value="${requestScope.USER_NAME}" />';
        var showWelcomeBanner = '<c:out value="${model.showWelcomeBanner}" escapeXml="false"/>'
        var welcomeBanner = '<c:out value="${model.welcomeBanner}" escapeXml="false"/>'
      
  	</script>
</head>
<body>
<div id="loading-ind-div" style="background-image: url('/admin/js/AdminApp/resources/images/ajax-loader.gif');background-repeat: no-repeat;height: 700px;width: 100%;background-position: center center"></div>
</body>
