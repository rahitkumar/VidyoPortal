<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<message success="<c:out value="${model.success}"/>" usercreated="<c:out value="${model.userCreated}"/>">
<c:if test="${not empty model.fields}">
	<errors>
		<c:forEach items="${model.fields}" var="f">
		<field>
			<id><c:out value="${f.field}"/></id>
			<msg><c:out value="${f.defaultMessage}"/></msg>
		</field>
		</c:forEach>
	</errors>
</c:if>
</message>
