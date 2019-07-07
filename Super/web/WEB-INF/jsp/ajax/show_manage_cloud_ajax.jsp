<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<Action><c:out value="${model.action}"/></Action>
	<Success><c:out value="${model.success}"/></Success>
	<ShowManageCloud><c:out value="${model.showManageCloud}"/></ShowManageCloud>
</dataset>