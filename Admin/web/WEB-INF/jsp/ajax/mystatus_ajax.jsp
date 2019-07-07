<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<c:if test="${model.currentVersion == model.activeVersion}">
	<!--
	<script type="text/javascript">
		window.location='download.html';
	</script>
	-->
</c:if>

<c:if test="${model.status == '-1'}">
	logout
</c:if>

<c:if test="${model.status == '0'}">
	<strong><spring:message code="offline"/></strong>
</c:if>
<c:if test="${model.status == '1'}">
	<strong	class="online"><spring:message code="online"/></strong>
</c:if>
<c:if test="${model.status == '2'}">
	<strong><spring:message code="busy"/></strong>
</c:if>
<c:if test="${model.status == '3'}">
	<strong><spring:message code="ringing"/></strong>
</c:if>
<c:if test="${model.status == '4'}">
	<strong><spring:message code="ring.accepted"/></strong>
</c:if>
<c:if test="${model.status == '5'}">
	<strong><spring:message code="ring.rejected"/></strong>
</c:if>
<c:if test="${model.status == '6'}">
	<strong><spring:message code="ring.no.answer"/></strong>
</c:if>
<c:if test="${model.status == '7'}">
	<strong><spring:message code="alerting"/></strong>
</c:if>
<c:if test="${model.status == '8'}">
	<strong><spring:message code="alert.cancelled"/></strong>
</c:if>
<c:if test="${model.status == '9'}">
	<strong><spring:message code="busy.in.own.room"/></strong>
</c:if>
