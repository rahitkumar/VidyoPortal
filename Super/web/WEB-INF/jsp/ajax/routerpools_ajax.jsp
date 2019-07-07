<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="include_ajax.jsp" %>

<dataset>

	<results>
	<c:out value="${model.size}"/></results>
	<c:forEach items="${model.routerPools}" var="routerPool">
		<row>
			<routerPoolID><c:out value="${routerPool.routerPoolID}"/></routerPoolID>
			<routerPoolName><c:out value="${routerPool.routerPoolName}"/></routerPoolName>
			<ipc><c:out value="${routerPool.ipcFlag}"/></ipc>
		</row>
	</c:forEach>

</dataset>