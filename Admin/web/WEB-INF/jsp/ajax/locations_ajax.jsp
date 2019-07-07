<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
<c:forEach items="${model.list}" var="l">
	<row>
		<locationID><c:out value="${l.locationID}"/></locationID>
		<locationTag><c:out value="${l.locationTag}"/></locationTag>
	</row>
</c:forEach>
</dataset>