<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>
        <c:forEach var="appConfig" items="${model.appConfigs}">
                <row>
                  <appName><c:out value="${appConfig.appName}"/></appName>
                  <networkInterface><c:out value="${appConfig.networkInterface}"/></networkInterface>
                  <c:if test="${appConfig.unsecurePort != 0}">
                        <unsecurePort><c:out value="${appConfig.unsecurePort}"/></unsecurePort>
                  </c:if>
                  <c:if test="${appConfig.securePort != 0}">
                        <securePort><c:out value="${appConfig.securePort}"/></securePort>
                  </c:if>
                        <ocsp><c:out value="${appConfig.ocsp}"/></ocsp>
                </row>
        </c:forEach>
</results>
</dataset>
