<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<memberID><c:out value="${model.member.memberID}"/></memberID>
		<langID><c:out value="${model.member.langID}"/></langID>
		<profileID><c:out value="${model.member.profileID}"/></profileID>
		<username><c:out value="${model.member.username}"/></username>
		<password><c:out value="${model.member.password}"/></password>
		<memberName><c:out value="${model.member.memberName}"/></memberName>

		<roomPIN><c:out value="${model.room.roomPIN}"/></roomPIN>
		<roomKey><c:out value="${model.room.roomKey}"/></roomKey>

		<homemachine><c:out value="${model.member.homemachine}"/></homemachine>
	</row>
</dataset>