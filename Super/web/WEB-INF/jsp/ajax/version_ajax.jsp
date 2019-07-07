<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<response>
   <version>2.0</version>
   <tag>TAG_2_0_0_0</tag>
	<software>
		<windows><c:out value="${model.winInstaller}"/></windows>
		<mac><c:out value="${model.macInstaller}"/></mac>
		<hd100><c:out value="${model.winInstaller100}"/></hd100>
		<hd200><c:out value="${model.winInstaller200}"/></hd200>
	</software>
</response>