<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<enableVidyoNeoWebRTCGuest><c:out value="${model.enableVidyoNeoWebRTCGuest}"/></enableVidyoNeoWebRTCGuest>
		<enableVidyoNeoWebRTCUser><c:out value="${model.enableVidyoNeoWebRTCUser}"/></enableVidyoNeoWebRTCUser>
	</row>
</dataset>