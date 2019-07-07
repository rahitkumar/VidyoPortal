<%@ page pageEncoding="iso-8859-1" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${model.status == '-1'}">
logout
</c:if>
<c:if test="${model.status == '0'}">
Offline
</c:if>
<c:if test="${model.status == '1'}">
Online
</c:if>
<c:if test="${model.status == '2'}">
Busy
</c:if>
<c:if test="${model.status == '3'}">
Ringing
</c:if>
<c:if test="${model.status == '4'}">
RingAccepted
</c:if>
<c:if test="${model.status == '5'}">
RingRejected
</c:if>
<c:if test="${model.status == '6'}">
RingNoAnswer
</c:if>
<c:if test="${model.status == '7'}">
JoinFailed
</c:if>
