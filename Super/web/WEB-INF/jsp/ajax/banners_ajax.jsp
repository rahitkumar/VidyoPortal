<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<showLoginBanner><c:out value="${model.showLoginBanner}"/></showLoginBanner>
		<showWelcomeBanner><c:out value="${model.showWelcomeBanner}"/></showWelcomeBanner>
		<loginBanner><c:out value="${model.loginBanner}"/></loginBanner>
        <welcomeBanner><c:out value="${model.welcomeBanner}"/></welcomeBanner>
	</row>
</dataset>