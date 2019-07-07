<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<wsflag><c:out value="${model.Authentication.wsflag}"/></wsflag>
		<wsurl><c:out value="${model.Authentication.wsurl}"/></wsurl>
		<wsusername><c:out value="${model.Authentication.wsusername}"/></wsusername>
		<wspassword><c:out value="${model.Authentication.wspassword}"/></wspassword>

		<ldapflag><c:out value="${model.Authentication.ldapflag}"/></ldapflag>
		<ldapurl><c:out value="${model.Authentication.ldapurl}"/></ldapurl>
		<ldapusername><c:out value="${model.Authentication.ldapusername}"/></ldapusername>
		<ldappassword><c:out value="${model.Authentication.ldappassword}"/></ldappassword>
		<ldapbase><c:out value="${model.Authentication.ldapbase}"/></ldapbase>
		<ldapfilter><c:out value="${model.Authentication.ldapfilter}"/></ldapfilter>
		<ldapscope><c:out value="${model.Authentication.ldapscope}"/></ldapscope>

	</row>
</dataset>