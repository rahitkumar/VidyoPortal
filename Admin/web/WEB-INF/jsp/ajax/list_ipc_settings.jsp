<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results>1</results>
    <row>
        <tenantID><c:out value="${model.tenantID}" /></tenantID>
        <ipcId><c:out value="${model.ipcId}" /></ipcId>
        <tenantIpcDetail><c:out value="${model.tenantIpcDetail}" /></tenantIpcDetail>
        <isIpcSuperManaged><c:out value="${model.isIpcSuperManaged}" /></isIpcSuperManaged>
    </row>
</dataset>