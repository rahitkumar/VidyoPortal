<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results>1</results>
    <row>
        <schRoomDisabledTenantLevel><c:out value="${model.schRoomDisabledTenantLevel}" /></schRoomDisabledTenantLevel>
        <schRoomEnabledSystemLevel><c:out value="${model.schRoomEnabledSystemLevel}" /></schRoomEnabledSystemLevel>
    </row>
</dataset>