<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../ajax/include_ajax.jsp" %>

<dataset>
   	<results><c:out value="${model.num}"/></results>
   	<passwdExpDate><c:out value="${model.passwdExpDate}"/></passwdExpDate>
	<c:forEach items="${model.memberLoginHistory}" var="history">
		<row>
			<transactionResult><c:out value="${history.transactionResult}"/></transactionResult>
			<sourceIP><c:out value="${history.sourceIP}"/></sourceIP>
			<transactionTime><fmt:formatDate value="${history.transactionTime}" pattern="MM/dd/yyyy HH:mm:ss a" /></transactionTime>
		</row>
	</c:forEach>
</dataset>