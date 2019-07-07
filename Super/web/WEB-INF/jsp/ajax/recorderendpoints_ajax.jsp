<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="re">
		<row>
			<endpointID><c:out value="${re.endpointID}"/></endpointID>
			<serviceID><c:out value="${re.serviceID}"/></serviceID>
			<recID><c:out value="${re.recID}"/></recID>
			<endpointGUID><c:out value="${re.endpointGUID}"/></endpointGUID>
			<prefix><c:out value="${re.prefix}"/></prefix>
			<description><c:out value="${re.description}"/></description>
			<status><c:out value="${re.status}"/></status>

			<c:if test="${re.status == '0'}">
				<statusText>Offline</statusText>
			</c:if>
			<c:if test="${re.status == '1'}">
				<statusText>Online</statusText>
			</c:if>
			<c:if test="${re.status == '2'}">
				<statusText>Busy</statusText>
			</c:if>
			<c:if test="${re.status == '3'}">
				<statusText>Ringing</statusText>
			</c:if>
			<c:if test="${re.status == '4'}">
				<statusText>RingAccepted</statusText>
			</c:if>
			<c:if test="${re.status == '5'}">
				<statusText>RingRejected</statusText>
			</c:if>
			<c:if test="${re.status == '6'}">
				<statusText>RingNoAnswer</statusText>
			</c:if>
			<c:if test="${re.status == '7'}">
				<statusText>JoinFailed</statusText>
			</c:if>
		</row>
	</c:forEach>
</dataset>