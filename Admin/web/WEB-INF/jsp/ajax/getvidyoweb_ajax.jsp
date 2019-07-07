<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results>1</results>
    <row>
        <vidyoWebVersion><c:out value="${model.vidyoWebVersion}" /></vidyoWebVersion>
        <available><c:out value="${model.available}" /></available>
        <enabled><c:out value="${model.enabled}" /></enabled>
        <zincServer><c:out value="${model.zincServer}" /></zincServer>
        <zincEnabled><c:out value="${model.zincEnabled}" /></zincEnabled>
    </row>
</dataset>