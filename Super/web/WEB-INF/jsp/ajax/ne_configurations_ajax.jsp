<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="cfg">
		<row>
			<Identifier><c:out value="${cfg.identifier}"/></Identifier>
			<DisplayName><c:out value="${cfg.displayName}"/></DisplayName>
			<ComponentType><c:out value="${cfg.componentType}"/></ComponentType>
			<IpAddress><c:out value="${cfg.ipAddress}"/></IpAddress>
            <WebApplicationURL><c:out value="${cfg.webApplicationURL}"/></WebApplicationURL>
			<Data><c:out value="${cfg.data}"/></Data>
			<RunningVersion><c:out value="${cfg.runningVersion}"/></RunningVersion>
			<Version><c:out value="${cfg.version}"/></Version>
			<ExpirySeconds><c:out value="${cfg.expirySeconds}"/></ExpirySeconds>
			<Alarm><c:out value="${cfg.alarm}"/></Alarm>
			<SwVer><c:out value="${cfg.swVer}"/></SwVer>
			<Status><c:out value="${cfg.status}"/></Status>
			<LastModified><c:out value="${cfg.lastModified}"/></LastModified>
			<Heartbeat><c:out value="${cfg.heartbeat}"/></Heartbeat>

            // new fields
            <URL><c:out value="${cfg.URL}"/></URL>

		</row>
	</c:forEach>
</dataset>