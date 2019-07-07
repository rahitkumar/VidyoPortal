<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results><c:out value="${model.num}"/></results>
    <c:forEach items="${model.list}" var="saml">
        <row>
            <valueID><c:out value="${saml.valueID}"/></valueID>
            <mappingID><c:out value="${saml.mappingID}"/></mappingID>
            <vidyoValueName><c:out value="${saml.vidyoValueName}"/></vidyoValueName>
            <idpValueName><c:out value="${saml.idpValueName}"/></idpValueName>
        </row>
    </c:forEach>
</dataset>