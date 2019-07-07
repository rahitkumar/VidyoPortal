<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results>1</results>
    <row>
        <lectureModeAllowed><c:out value="${model.lectureModeAllowed}" /></lectureModeAllowed>
        <waitingRoomsEnabled><c:out value="${model.waitingRoomsEnabled}" /></waitingRoomsEnabled>
        <waitUntilOwnerJoins><c:out value="${model.waitUntilOwnerJoins}" /></waitUntilOwnerJoins>
        <lectureModeStrict><c:out value="${model.lectureModeStrict}" /></lectureModeStrict>
        <schRoomEnabledTenantLevel><c:out value="${model.schRoomEnabledTenantLevel}" /></schRoomEnabledTenantLevel>
        <schRoomEnabledSystemLevel><c:out value="${model.schRoomEnabledSystemLevel}" /></schRoomEnabledSystemLevel>
        <publicRoomEnabledGlobal><c:out value="${model.publicRoomEnabledGlobal}" /></publicRoomEnabledGlobal>
        <publicRoomEnabledTenant><c:out value="${model.publicRoomEnabledTenant}" /></publicRoomEnabledTenant>
        <publicRoomMaxRoomNoPerUser><c:out value="${model.publicRoomMaxRoomNoPerUser}" /></publicRoomMaxRoomNoPerUser>
         <publicRoomMaxRoomNoPerUserGlb><c:out value="${model.publicRoomMaxRoomNoPerUserGlb}" /></publicRoomMaxRoomNoPerUserGlb>
        
        
    </row>
</dataset>