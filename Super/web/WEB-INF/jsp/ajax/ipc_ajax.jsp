<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="include_ajax.jsp" %>

<dataset>
	<c:choose>
		<c:when test='${model.tenantID != 0}'>
			<results><c:out value="${model.num}"/></results>
			<row>
				<tenantID><c:out value="${model.tenantID}"/></tenantID>
				<inbound><c:out value="${model.inbound}"/></inbound>
				<outbound><c:out value="${model.outbound}"/></outbound>
				<inboundLines><c:out value="${model.inboundLines}"/></inboundLines>
				<outboundLines><c:out value="${model.outboundLines}"/></outboundLines>
			</row>
		</c:when>
		<c:otherwise>
			<results>0</results>
		</c:otherwise>
	</c:choose>	
</dataset>
