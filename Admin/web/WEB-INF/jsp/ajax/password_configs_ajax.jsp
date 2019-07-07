<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
        <minPINLength><c:out value="${model.minPINLength}"/></minPINLength>
        <sessionExpPeriod><c:out value="${model.sessionExpPeriod}"/></sessionExpPeriod>
	</row>
</dataset>