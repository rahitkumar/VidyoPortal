<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>


<message success="<c:out value="${model.success}"/>">
	<Identifier><c:out value="${model.networkconfig.identifier}"/></Identifier>
	<DisplayName><c:out value="${model.networkconfig.displayName}"/></DisplayName>
	<ComponentType><c:out value="${model.networkconfig.componentType}"/></ComponentType>
	<IpAddress><c:out value="${model.networkconfig.ipAddress}"/></IpAddress>
	<Data><c:out value="${model.networkconfig.data}"/></Data>
	<RunningVersion><c:out value="${model.networkconfig.runningVersion}"/></RunningVersion>
	<Version><c:out value="${model.networkconfig.version}"/></Version>
	<ExpirySeconds><c:out value="${model.networkconfig.expirySeconds}"/></ExpirySeconds>
	<Alarm><c:out value="${model.networkconfig.alarm}"/></Alarm>
	<SwVer><c:out value="${model.networkconfig.swVer}"/></SwVer>
	<Status><c:out value="${model.networkconfig.status}"/></Status>
	<LastModified><c:out value="${model.networkconfig.lastModified}"/></LastModified>
</message>