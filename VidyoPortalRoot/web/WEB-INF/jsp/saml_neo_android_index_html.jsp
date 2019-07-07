<%@ page session="false"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld"%>
<%@ page import="java.util.Locale"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	Locale locale = LocaleContextHolder.getLocale();
%>
<c:set var="systemlang"><%=locale.toString()%></c:set>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body onload="invokeNeoApp();">
	<script type="text/javascript">
	    function invokeNeoApp() {
	       window.location='<c:url value="${model.url}"/>';
	    }
	</script>
 </body>

</html>