<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results><c:out value="${model.num}"/></results>
        <c:forEach items="${model.patches}" var="patch">
    <row>
        <patch><c:out value="${patch.fileName}"/></patch>
        <timestamp><c:out value="${patch.timestamp}"/></timestamp>
    </row>
        </c:forEach>
</dataset>