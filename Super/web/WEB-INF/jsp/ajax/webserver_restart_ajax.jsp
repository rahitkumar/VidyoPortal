<?wexml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>

    <c:forEach items="${model.tenants}" var="tenant">
    <row>
     <tenantID><c:out value="${tenant.tenantID}"/></tenantID>
        <tenantName><c:out value="${tenant.tenantName}"/></tenantName>
        <tenantURL><c:out value="${tenant.tenantURL}"/></tenantURL>
    </row>
  </c:forEach>
</dataset>