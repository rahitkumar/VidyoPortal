<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
    <row>
        <locked><c:out value="${model.room.roomLocked}"/></locked>
        <muted><c:out value="${model.room.roomMuted}"/></muted>
        <silenced><c:out value="${model.room.roomSilenced}"/></silenced>
        <videoMuted><c:out value="${model.room.roomVideoMuted}"/></videoMuted>
        <videoSilenced><c:out value="${model.room.roomVideoSilenced}"/></videoSilenced>
        <lectureMode><c:out value="${model.room.lectureMode}"/></lectureMode>
        <waitingRoom><c:out value="${model.waitingRoom}"/></waitingRoom>
    </row>
</dataset>