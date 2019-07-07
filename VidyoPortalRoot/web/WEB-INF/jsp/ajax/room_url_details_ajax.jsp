<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<room>
    <c:if test="${not empty model.room.roomKey}">
        <roomURL><c:out value="${model.room.roomURL}"/></roomURL>
    </c:if>
    <c:if test="${empty model.room.roomKey}">
        <roomURL/>
    </c:if>
    <c:if test="${model.room.roomPinned != '0'}">
        <hasPin>true</hasPin>
    </c:if>
    <c:if test="${model.room.roomPinned == '0'}">
        <hasPin>false</hasPin>
    </c:if>
</room>