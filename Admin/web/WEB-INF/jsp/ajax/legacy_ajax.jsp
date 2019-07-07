<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<memberID><c:out value="${model.member.memberID}"/></memberID>
		<roleID><c:out value="${model.member.roleID}"/></roleID>
		<roleName><c:out value="${model.member.roleName}"/></roleName>
		<tenantID><c:out value="${model.member.tenantID}"/></tenantID>
		<groupID><c:out value="${model.member.groupID}"/></groupID>
		<groupName><c:out value="${model.member.groupName}"/></groupName>
		<langID><c:out value="${model.member.langID}"/></langID>
		<profileID><c:out value="${model.member.profileID}"/></profileID>
		<username><c:out value="${model.member.username}"/></username>
		<password><c:out value="${model.member.password}"/></password>
		<memberName><c:out value="${model.member.memberName}"/></memberName>
		<enable><c:out value="${model.member.active}"/></enable>
		<emailAddress><c:out value="${model.member.emailAddress}"/></emailAddress>
		<memberCreated><c:out value="${model.member.memberCreated}"/></memberCreated>
		<location><c:out value="${model.member.location}"/></location>
		<description><c:out value="${model.member.description}"/></description>
		<roomID><c:out value="${model.member.roomID}"/></roomID>
		<roomTypeID><c:out value="${model.member.roomTypeID}"/></roomTypeID>
		<roomType><c:out value="${model.member.roomType}"/></roomType>
		<roomName><c:out value="${model.member.roomName}"/></roomName>
		<roomExtNumber><c:out value="${model.member.roomExtNumber}"/></roomExtNumber>
	</row>
</dataset>