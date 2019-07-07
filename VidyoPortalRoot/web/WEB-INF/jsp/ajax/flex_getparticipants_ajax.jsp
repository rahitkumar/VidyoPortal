<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<participants
        total="<c:out value="${model.num}"/>"
        <c:if test="${not empty model.recorderID}">
            recorderID="<c:out value="${model.recorderID}"/>"
        </c:if>
        <c:if test="${not empty model.recorderName}">
            recorderName="<c:out value="${model.recorderName}"/>"
        </c:if>
        <c:if test="${not empty model.isPaused}">
            isPaused="<c:out value="${model.isPaused}"/>"
        </c:if>
        <c:if test="${not empty model.isWebcast}">
            isWebcast="<c:out value="${model.isWebcast}"/>"
        </c:if>
        >
    <c:forEach items="${model.list}" var="ctl">
        <participant>
            <entityID><c:out value="${ctl.roomID}"/></entityID>
            <endpointID><c:out value="${ctl.endpointID}"/></endpointID>
            <endpointGUID><c:out value="${ctl.endpointGUID}"/></endpointGUID>
            <endpointType><c:out value="${ctl.endpointType}"/></endpointType>
            <c:if test="${ctl.name == null}">
                <displayName>(orphaned)</displayName>
            </c:if>
            <c:if test="${ctl.name != null}">
                <displayName><c:out value="${ctl.name}"/></displayName>
            </c:if>
            <dialIn><c:out value="${ctl.dialIn}"/></dialIn>
            <extension><c:out value="${ctl.ext}"/></extension>

        <c:if test="${ctl.video == '1'}">
            <video>true</video>
        </c:if>
        <c:if test="${ctl.video == '0'}">
            <video>false</video>
        </c:if>

        <c:if test="${ctl.audio == '1'}">
            <audio>true</audio>
        </c:if>
        <c:if test="${ctl.audio == '0'}">
            <audio>false</audio>
        </c:if>

        <c:if test="${ctl.connect == '1'}">
            <connect>true</connect>
        </c:if>
        <c:if test="${ctl.connect == '0'}">
            <connect>false</connect>
        </c:if>
        <c:if test="${ctl.presenter == 1}">
            <presenter>true</presenter>
        </c:if>
        <c:if test="${ctl.presenter == 0}">
             <presenter>false</presenter>
        </c:if>
        <c:if test="${ctl.handRaised == 1}">
             <handRaised>true</handRaised>
        </c:if>
        <c:if test="${ctl.handRaised == 0}">
              <handRaised>false</handRaised>
        </c:if>

        </participant>
    </c:forEach>
</participants>