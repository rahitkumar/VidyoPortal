<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="ctl">
		<row>
			<endpointID><c:out value="${ctl.endpointID}"/></endpointID>
			<endpointGUID><c:out value="${ctl.endpointGUID}"/></endpointGUID>
			<endpointType><c:out value="${ctl.endpointType}"/></endpointType>
			<dialIn><c:out value="${ctl.dialIn}"/></dialIn>
			<name><c:out value="${ctl.name}"/></name>
			<ext><c:out value="${ctl.ext}"/></ext>

		<c:if test="${ctl.video == '1'}">
			<actionVideo>icon-video-on</actionVideo>
			<qtipVideo><spring:message code="stop.video"/></qtipVideo>
		</c:if>
		<c:if test="${ctl.video == '0'}">
			<actionVideo>icon-video-off</actionVideo>
			<qtipVideo><spring:message code="start.video"/></qtipVideo>
		</c:if>

		<c:if test="${ctl.audio == '1'}">
			<actionAudio>icon-audio-on</actionAudio>
			<qtipAudio><spring:message code="mute.mic"/></qtipAudio>
		</c:if>
		<c:if test="${ctl.audio == '0'}">
			<actionAudio>icon-audio-off</actionAudio>
			<qtipAudio><spring:message code="unmute.mic"/></qtipAudio>
		</c:if>

		<c:if test="${ctl.connect == '1'}">
			<actionConnect>icon-connect-on</actionConnect>
			<qtipConnect><spring:message code="disconnect"/></qtipConnect>
		</c:if>
		<c:if test="${ctl.connect == '0'}">
			<actionConnect>icon-connect-off</actionConnect>
			<qtipConnect><spring:message code="connect"/></qtipConnect>
		</c:if>

			<conferenceName><c:out value="${ctl.conferenceName}"/></conferenceName>
			<conferenceType><c:out value="${ctl.conferenceType}"/></conferenceType>
			<tenantName><c:out value="${ctl.tenantName}"/></tenantName>

			<vrName><c:out value="${ctl.vrName}"/></vrName>
			<groupName><c:out value="${ctl.groupName}"/></groupName>

		</row>
	</c:forEach>
</dataset>