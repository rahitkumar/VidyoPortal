<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<webcast>
   <c:if test="${not empty model.room.webCastURL}">
      <webCastUrl><c:out value="${model.room.webCastURL}"/></webCastUrl>
   </c:if>
   <c:if test="${model.room.webCastPinned != '0'}">
      <hasWebcastPin>true</hasWebcastPin>
   </c:if>
   <c:if test="${model.room.webCastPinned == '0'}">
      <hasWebcastPin>false</hasWebcastPin>
   </c:if>
</webcast>
