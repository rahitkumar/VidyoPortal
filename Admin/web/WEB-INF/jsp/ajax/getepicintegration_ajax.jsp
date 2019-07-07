<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results>1</results>
    <row>
        <enableEpicIntegration><c:out value="${model.enableEpicIntegration}" /></enableEpicIntegration>
        <sharedSecret><c:out value="${model.sharedSecret}" /></sharedSecret>
		<notificationUrl><c:out value="${model.notificationUrl}" /></notificationUrl>
		<notificationUser><c:out value="${model.notificationUser}" /></notificationUser>
		<notificationPassword><c:out value="${model.notificationPassword}" /></notificationPassword>
    </row>
</dataset>