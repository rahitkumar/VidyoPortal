<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="mr">
		<row>
			<roleID><c:out value="${mr.roleID}"/></roleID>
			<roleName><c:out value="${mr.roleName}"/></roleName>
			<roleDescription><c:out value="${mr.roleDescription}"/></roleDescription>
		</row>
	</c:forEach>
</dataset>