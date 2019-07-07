<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results><c:out value="${model.num}"/></results>
    <c:forEach items="${model.list}" var="obj">
        <row>
         
            <countryID><c:out value="${obj.country.countryID}"/></countryID>
            <name><c:out value="${obj.country.name}"/></name>    
            <phoneCode><c:out value="${obj.country.phoneCode}"/></phoneCode>
            <dialInNumber><c:out value="${obj.tenantDialInCountryPK.dialInNumber}"/></dialInNumber>
            <dialInLabel><c:out value="${obj.dialInLabel}"/></dialInLabel>
            <flagFileName><c:out value="${obj.country.flagFileName}"/></flagFileName>      
           
        </row>
    </c:forEach>
</dataset>