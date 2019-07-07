<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results><c:out value="${model.num}"/></results>
    <c:forEach items="${model.list}" var="obj">
        <row>
        	<countryID><c:out value="${obj.countryID}"/></countryID>
            <name><c:out value="${obj.name}"/></name>
            <phoneCode><c:out value="${obj.phoneCode}"/></phoneCode>
            <flagFileName><c:out value="${obj.flagFileName}"/></flagFileName>
       </row>
    </c:forEach>
</dataset>