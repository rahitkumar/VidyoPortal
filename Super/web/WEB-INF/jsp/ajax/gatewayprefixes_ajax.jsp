<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="gf">
		<row>
			<serviceID><c:out value="${gf.serviceID}"/></serviceID>
			<gatewayID><c:out value="${gf.gatewayID}"/></gatewayID>
			<prefix><c:out value="${gf.prefix}"/></prefix>
            <direction><c:out value="${gf.direction}"/></direction>
			<c:if test="${gf.direction == '0'}">
				<directionText>FROM LEGACY</directionText>
			</c:if>
			<c:if test="${gf.direction == '1'}">
				<directionText>TO LEGACY</directionText>
			</c:if>
		</row>
	</c:forEach>
</dataset>