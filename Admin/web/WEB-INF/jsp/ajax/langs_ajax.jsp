<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
<c:forEach items="${model.list}" var="l">
	<row>
		<langID><c:out value="${l.langID}"/></langID>
		<langCode><c:out value="${l.langCode}"/></langCode>
		<langName><c:out value="${l.langName}"/></langName>
		<c:if test="${l.langCode == 'en'}">
			<langFlag>ux-flag-us</langFlag>
		</c:if>
		<c:if test="${l.langCode == 'fr'}">
			<langFlag>ux-flag-fr</langFlag>
		</c:if>
		<c:if test="${l.langCode == 'ja'}">
			<langFlag>ux-flag-jp</langFlag>
		</c:if>
		<c:if test="${l.langCode == 'zh_CN'}">
			<langFlag>ux-flag-cn</langFlag>
		</c:if>
		<c:if test="${l.langCode == 'es'}">
			<langFlag>ux-flag-es</langFlag>
		</c:if>
		<c:if test="${l.langCode == 'it'}">
			<langFlag>ux-flag-it</langFlag>
		</c:if>
		<c:if test="${l.langCode == 'de'}">
			<langFlag>ux-flag-de</langFlag>
		</c:if>
		<c:if test="${l.langCode == 'ko'}">
			<langFlag>ux-flag-kr</langFlag>
		</c:if>
		<c:if test="${l.langCode == 'pt'}">
			<langFlag>ux-flag-pt</langFlag>
		</c:if>
	</row>
</c:forEach>
</dataset>