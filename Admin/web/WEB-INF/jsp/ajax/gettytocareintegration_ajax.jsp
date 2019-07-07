<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results>1</results>
    <row>
        <enableTytoCareIntegration><c:out value="${model.enableTytoCareIntegration}" /></enableTytoCareIntegration>
        <tytoUrl><c:out value="${model.tytoUrl}" /></tytoUrl>
        <tytoUsername><c:out value="${model.tytoUsername}" /></tytoUsername>
        <tytoPassword><c:out value="${model.tytoPassword}" /></tytoPassword>
    </row>
</dataset>