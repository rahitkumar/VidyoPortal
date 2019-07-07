<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="router">
		<row>
			<routerID><c:out value="${router.routerID}"/></routerID>
			<c:if test="${router.active == '1'}">
				<routerName><c:out value="${router.routerName}"/></routerName>
			</c:if>
			<c:if test="${router.active == '0'}">
				<routerName><c:out value="${router.routerName}"/> - <spring:message code="not.available"/> !!!</routerName>
			</c:if>
			<active><c:out value="${router.active}"/></active>
		</row>
	</c:forEach>
</dataset>