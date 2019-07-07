<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<country><c:out value="${model.country}"/></country>
		<state><c:out value="${model.state}"/></state>
		<city><c:out value="${model.city}"/></city>
		<company><c:out value="${model.company}"/></company>
		<division><c:out value="${model.division}"/></division>
		<domain><c:out value="${model.domain}"/></domain>
		<email><c:out value="${model.email}"/></email>
		<csr><c:out value="${model.csr}"/></csr>
		<csrMatchesKey><c:out value="${model.csrMatchesKey}"/></csrMatchesKey>
	</row>
</dataset>