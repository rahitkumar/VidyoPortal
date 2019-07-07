<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../ajax/include_ajax.jsp" %>

<dataset>
		<tenant>
			<tenantID><c:out value="${model.tenantID}"/></tenantID>
			<tenantName><c:out value="${model.tenantName}"/></tenantName>
			<mobileLogin><c:out value="${model.mobileLogin}"/></mobileLogin>
			<tenantPrefix><c:out value="${model.tenantPrefix}"/></tenantPrefix>
			<defaultTenantPrefix><c:out value="${model.defaultTenantPrefix}"/></defaultTenantPrefix>
			<vidyoGatewayControllerDns><c:out value="${model.vidyoGatewayControllerDns}"/></vidyoGatewayControllerDns>
			<guideLoc><c:out value="${model.guideLoc}"/></guideLoc>
			<licenseVersion><c:out value="${model.licenseVersion}"/></licenseVersion>
			<maxInstalls><c:out value="${model.maxInstalls}"/></maxInstalls>
			<installsInUse><c:out value="${model.installsInUse}"/></installsInUse>
			<maxSeats><c:out value="${model.maxSeats}"/></maxSeats>
			<seatsInUse><c:out value="${model.seatsInUse}"/></seatsInUse>
			<publicRoomsInUse><c:out value="${model.publicRoomsInUse}"/></publicRoomsInUse>
			<maxPublicRooms><c:out value="${model.maxPublicRooms}"/></maxPublicRooms>
			<maxPorts><c:out value="${model.maxPorts}"/></maxPorts>
			<multiTenant><c:out value="${model.multiTenant}"/></multiTenant>
			<maxExecutives><c:out value="${model.maxExecutives}"/></maxExecutives>
			<executivesInUse><c:out value="${model.executivesInUse}"/></executivesInUse>
			<maxPanoramas><c:out value="${model.maxPanoramas}"/></maxPanoramas>
			<panoramasInUse><c:out value="${model.panoramasInUse}"/></panoramasInUse>
			<showExecutives><c:out value="${model.showExecutives}"/></showExecutives>
			<showVidyoReplay><c:out value="${model.showVidyoReplay}"/></showVidyoReplay>
			<showVidyoNeoWebRTC><c:out value="${model.showVidyoNeoWebRTC}"/></showVidyoNeoWebRTC>
			<showVidyoVoice><c:out value="${model.showVidyoVoice}"/></showVidyoVoice>
			<showPanoramas><c:out value="${model.showPanoramas}"/></showPanoramas>
			<showScheduledRoomConfig><c:out value="${model.showScheduledRoomConfig}"/></showScheduledRoomConfig>
			<inbound><c:out value="${model.inbound}"/></inbound>
			<outbound><c:out value="${model.outbound}"/></outbound>
			<showLogAggr><c:out value="${model.showLogAggr}"/></showLogAggr>
			<showCustomRole><c:out value="${model.showCustomRole}"/></showCustomRole>
			<endpointUploadMode><c:out value="${model.endpointUploadMode}"/></endpointUploadMode>
			<superEndpointUploadMode><c:out value="${model.superEndpointUploadMode}"/></superEndpointUploadMode>
		</tenant>
</dataset>

