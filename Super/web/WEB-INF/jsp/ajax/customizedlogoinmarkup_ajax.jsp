<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<c:set var="cusLogoPath"><c:out value="${model.name}"/></c:set>

<c:if test="${cusLogoPath != ''}">
	<a href="index.html"><img src="<c:out value="${model.name}"/>" alt=""/></a>
</c:if>

<c:if test="${cusLogoPath == ''}">
	<c:set var="logo"><spring:theme code="logo"/></c:set>
	<a href="index.html"><img src="<c:out value="/super${logo}"/>" alt=""/></a>
</c:if>
