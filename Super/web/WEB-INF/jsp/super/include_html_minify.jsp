<!-- this is page is only needed for development in order to download theme and ext js package.-->
<%@ page session="false"%>

<%@ page pageEncoding="iso-8859-1" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
 
<head>
    <script type="text/javascript" src="js/SuperApp/resources/l10n.js"></script>
    <link rel="prefetch" type="application/l10n" href="js/SuperApp/resources/locales/locales.ini" />
	<script type="text/javascript" src="js/SuperApp/ext-js/ext-all-debug.js"></script> 
	<link rel="stylesheet" type="text/css" href="js/SuperApp/ext-js/classic/theme-triton/resources/theme-triton-all.css"/>
	<script type="text/javascript" src="js/SuperApp/ext-js/classic/theme-triton/theme-triton.js"></script>
	<link rel='shortcut icon' type='image/x-icon' href="<%=request.getContextPath()%>/favicon.ico" />
</head>


<%
Locale locale = LocaleContextHolder.getLocale();
%>


<body>

<script type="text/javascript">

	var csrfHeaderName = '<c:out value="${_csrf.headerName}" />';
	var csrfToken = '<c:out value="${_csrf.token}" />';
	var csrfFormParamName = '<c:out value="${_csrf.parameterName}" />'; 
  	function loadApplicationScripts() {
        var fileref=document.createElement('script')
		fileref.setAttribute("type","text/javascript")
		fileref.setAttribute("src", "js/SuperApp/app.js");
		document.getElementsByTagName("head")[0].appendChild(fileref);
	} 
    
</script>
</body>

