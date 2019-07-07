<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="include_html_minify.jsp" %>


<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page import="java.util.Locale" %>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title><spring:message code="title.super"/></title>
    <link rel="stylesheet" type="text/css" href="js/SuperApp/resources/css/vidyo-ext.css"></link>
  	<link rel="stylesheet" type="text/css" href='<c:url value="/js/SuperApp/resources/css/D3RouterPools.css"/>'>
    <script src='<c:url value="/js/SuperApp/resources/js/d3/d3.v3.min.js"/>'></script>
  	<script type ="text/javascript" src='<c:url value="/js/SuperApp/resources/js/d3/D3RouterPools.js"/>'></script>
    <script type ="text/javascript">
  	var url = '<c:url value="networkconfig.ajax"/>';
  	</script>
  	<script type ="text/javascript">
  	var Wrapper=new Object();
  	var appFolder = "js/SuperApp/app";
  	var lang = sessionStorage.getItem('lang');
    document.webL10n.setLanguage(lang);
    var l10n = document.webL10n.get;
    setTimeout(function() {
    	var fileref=document.createElement('script')
    	fileref.setAttribute("id","microloader")
   		fileref.setAttribute("type","text/javascript")
   		fileref.setAttribute("src", "microloader.js");
   		document.getElementsByTagName("head")[0].appendChild(fileref); 
   	}, 600);
	 
        var csrfHeaderName = '<c:out value="${_csrf.headerName}" />';
        var csrfToken = '<c:out value="${_csrf.token}" />';
        var csrfFormParamName = '<c:out value="${_csrf.parameterName}" />';        
        var ipAddress = '[<c:out value="${model.ipAddress}" />]';
        var superusername = '<c:out value="${model.user.memberName}"/>';
        var username = '<c:out value="${model.user.memberName}"/>';
		var welcomeBannerTitle = 'Welcome <c:out value="${requestScope.USER_NAME}" />';
        var showWelcomeBanner = '<c:out value="${model.showWelcomeBanner}" escapeXml="false"/>'
        var welcomeBanner = '<c:out value="${model.welcomeBanner}" escapeXml="false"/>'
  	
  	</script>
  	
  	  	</head>
  	  <body> <div id="loading-ind-div" style="background-image: url('/super/js/SuperApp/resources/images/ajax-loader.gif');background-repeat: no-repeat;height: 700px;width: 100%;background-position: center center"></div> </body>