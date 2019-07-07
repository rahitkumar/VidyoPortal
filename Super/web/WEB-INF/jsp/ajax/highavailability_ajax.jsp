<?xml version="1.0" encoding="UTF-8"
?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>
<dataset>
    <results>
        1
    </results>
    <row>
        <showRemoveMaintLink>
            <c:out value="${model.showRemoveMaintLink}"/>
        </showRemoveMaintLink>
        <startTime>
            <c:out value="${model.startTime}"/>
        </startTime>
        <endTime>
            <c:out value="${model.endTime}"/>
        </endTime>
        <frequency>
            <c:out value="${model.frequency}"/>
        </frequency>
        <showToMaintLink>
            <c:out value="${model.showToMaintLink}"/>
        </showToMaintLink>
        <privilegedMode>
            <c:out value="${model.privilegedMode}"/>
        </privilegedMode>
    </row>
</dataset>