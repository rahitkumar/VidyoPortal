<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<expieryDays><c:out value="${model.expieryDays}"/></expieryDays>
		<inactiveDays><c:out value="${model.inactiveDays}"/></inactiveDays>
        <failCount><c:out value="${model.failCount}"/></failCount>
        <c:if test="${not empty model.passwordComplexity}">
            <passwordComplexity><c:out value="${model.passwordComplexity}"/></passwordComplexity>
        </c:if>
        <disableForgetPasswordSuper><c:out value="${model.disableForgetPasswordSuper}"/></disableForgetPasswordSuper>
        <minPINLength><c:out value="${model.minPINLength}"/></minPINLength>
        <sessionExpPeriod><c:out value="${model.sessionExpPeriod}"/></sessionExpPeriod>
	</row>
</dataset>