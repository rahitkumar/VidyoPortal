<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results><c:out value="${model.num}"/></results>
        <c:forEach items="${model.reports}" var="report">
    <row>
        <fileName><c:out value="${report.fileName}"/></fileName>
        <timestamp><c:out value="${report.timestamp}"/></timestamp>
    </row>
        </c:forEach>
</dataset>