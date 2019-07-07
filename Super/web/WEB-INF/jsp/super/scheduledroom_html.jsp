<?xml version="1.0" encoding="UTF-8"?>
<%@ include file="../ajax/include_ajax.jsp" %>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<dataset>
    <results>1</results>
    <row>
        <ScheduledRoomEnabled><c:out value="${model.schRoomEnabled}"/></ScheduledRoomEnabled>
        <ScheduledRoomPrefix><c:out value="${model.prefix}"/></ScheduledRoomPrefix>
        <ScheduledRoomHelp><c:url value="${admin_help}"/></ScheduledRoomHelp>

        <publicRoomEnabledGlobal><c:out value="${model.publicRoomEnabledGlobal}"/></publicRoomEnabledGlobal>
        <publicRoomMaxRoomNoPerUser><c:out value="${model.publicRoomMaxRoomNoPerUser}"/></publicRoomMaxRoomNoPerUser>
        <publicRoomMinNoExt><c:out value="${model.publicRoomMinNoExt}"/></publicRoomMinNoExt>
        <ScheduledRoomHelp><c:url value="${admin_help}"/></ScheduledRoomHelp>
        <ScheduleRoomEnabledLabel><spring:message code="super.schroom.feature.enable.disable.label"/></ScheduleRoomEnabledLabel>
        <ScheduleRoomPrefixLabel><spring:message code="super.scheduled.room.prefix"/></ScheduleRoomPrefixLabel>
        <ScheduleRoomNoChangeSubmitDesc><spring:message code="super.schroom.feature.no.changes.made"/></ScheduleRoomNoChangeSubmitDesc>
        <ScheduleRoomConfirmMsgBoxDesc><spring:message code="super.schroom.prefix.change.confirmation.message"/></ScheduleRoomConfirmMsgBoxDesc>
    </row>
</dataset>