<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="loc">
		<row>
			<locationID><c:out value="${loc.locationID}"/></locationID>
			<locationTag><c:out value="${loc.locationTag}"/></locationTag>
		</row>
	</c:forEach>
</dataset>