<%@ page session="false" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"
        %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
        %><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"
        %><%
  final String uri = (String) request.getAttribute("javax.servlet.forward.request_uri");
  final boolean isServiceURL = uri.contains("services") || uri.contains("gatewayService");
  final String errorCode = "" + (Integer) request.getAttribute("javax.servlet.error.status_code");
  if (!isServiceURL) {
%><!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title><%= errorCode %></title>
  <style>
    body { margin: 20px; text-align: center; }
    h1 { font: bold 36px sans-serif; }
    h2 { font: italic 16px sans-serif; }
  </style>
</head>
<body>
<h1><%= errorCode %></h1>
<h2>
<c:choose>
  <c:when test="${requestScope['javax.servlet.error.message'] == 'invalidroom'}">
    <spring:message htmlEscape="true" code="this.is.not.a.valid.room.link"/>
  </c:when>
  <c:when test="${requestScope['javax.servlet.error.message'] == 'guestnotallowed'}">
    <spring:message htmlEscape="true" code="guests.are.not.allowed.in.this.room"/>
  </c:when>
  <c:when test="${requestScope['javax.servlet.error.message'] == 'epicnotenabled'}">
    <spring:message htmlEscape="true" code="epic.is.not.enabled"/>
  </c:when>
  <c:when test="${requestScope['javax.servlet.error.message'] == 'externaldatainvalid'}">
    <spring:message htmlEscape="true" code="external.data.is.invalid"/>
  </c:when>
  <c:when test="${requestScope['javax.servlet.error.message'] == 'externaldatatypeinvalid'}">
    <spring:message htmlEscape="true" code="external.datatype.is.invalid"/>
  </c:when>
  <c:otherwise>
    Unable to process this request.<br />Please check your request and try again.
  </c:otherwise>
</c:choose>
</h2>
</body>
</html>
<% } %>