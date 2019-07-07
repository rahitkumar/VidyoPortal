<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<response total="<c:out value="${model.num}"/>">
    <c:forEach items="${model.list}" var="re">
        <profile>
            <prefix><c:out value="${re.prefix}"/></prefix>
            <displayName><c:out value="${re.description}"/></displayName>
        </profile>
    </c:forEach>
</response>