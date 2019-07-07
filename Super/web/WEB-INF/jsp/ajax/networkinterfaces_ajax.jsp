<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>
        <c:forEach var="networkInterface" items="${model.interfaces}">
                <row>
                  <id><c:out value="${networkInterface}"/></id>
                  <interfaceName><c:out value="${networkInterface}"/></interfaceName>
                </row>
        </c:forEach>
</results>
</dataset>