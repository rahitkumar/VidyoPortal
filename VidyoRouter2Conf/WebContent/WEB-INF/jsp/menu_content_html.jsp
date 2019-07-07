<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<ul id="menu">


<c:if test="${not empty param.settings}">
	<li><a href='<c:url value="maintenance.html"/>' <c:if test="${not empty param.maintenance}">class="selected"</c:if>><vidyo:replaceString from="\\\\'" to="'"><spring:message code="maintenance"/></vidyo:replaceString></a></li>
	<li><a href='<c:url value="network.html"/>' <c:if test="${not empty param.network}">class="selected"</c:if>><vidyo:replaceString from="\\\\'" to="'"><spring:message code="platform.network.settings"/></vidyo:replaceString></a></li>
	<c:if test="${model.standaloneRouter}">
		<li><a href='<c:url value="security.html"/>' <c:if test="${not empty param.security}">class="selected"</c:if>><vidyo:replaceString from="\\\\'" to="'"><spring:message code="security"/></vidyo:replaceString></a></li>
	</c:if>
</c:if>
</ul>
