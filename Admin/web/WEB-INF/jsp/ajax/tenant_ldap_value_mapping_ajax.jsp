<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results><c:out value="${model.num}"/></results>
    <c:forEach items="${model.list}" var="ldap">
        <row>
            <valueID><c:out value="${ldap.valueID}"/></valueID>
            <mappingID><c:out value="${ldap.mappingID}"/></mappingID>
            <vidyoValueName><c:out value="${ldap.vidyoValueName}"/></vidyoValueName>
            <ldapValueName><c:out value="${ldap.ldapValueName}"/></ldapValueName>
        </row>
    </c:forEach>
</dataset>