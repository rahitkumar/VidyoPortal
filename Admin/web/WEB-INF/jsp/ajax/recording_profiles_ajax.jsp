<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="re">
		<row>
			<prefix><c:out value="${re.prefix}"/></prefix>
			<displayName><c:out value="${re.description}"/></displayName>
		</row>
	</c:forEach>
</dataset>